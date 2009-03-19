package org.eclipse.ofmp.security.business.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jcifs.UniAddress;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbAuthException;
import jcifs.smb.SmbSession;

import org.apache.log4j.Logger;
import org.eclipse.ofmp.security.business.OFMPAuthenticationToken;
import org.eclipse.ofmp.security.dao.AuthenticationDAO;
import org.eclipse.ofmp.security.dao.UserDAO;
import org.eclipse.ofmp.security.dom.Dealer;
import org.eclipse.ofmp.security.dom.Role;
import org.eclipse.ofmp.security.dom.User;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

public class CIFSAuthenticationProvider implements AuthenticationProvider
{
    private final Logger log = Logger.getLogger(getClass());

    private AuthenticationDAO m_AuthenticationDAO;
    private UserDAO m_UserDAO;

    private SessionService m_SessionService;

    private final AuthorityMapper m_Mapper = new AuthorityMapper();

    private String m_PDC, m_Domain;

    public Authentication authenticate(Authentication aAuthentication) throws AuthenticationException
    {
        try
        {
            String username = (String) aAuthentication.getPrincipal();
            String password = (String) aAuthentication.getCredentials();

            User user = m_AuthenticationDAO.getUserByLogin(username);

            if (user == null)
                throw new BadCredentialsException("User doesn't exist in the system.");
            else if (!user.getEnabled())
                throw new BadCredentialsException("The user " + user.getName() + " is currently disabled in the system.");
            else if (NTLMAuthentication(username, password))
            {
                Collection<String> roleStrings = m_AuthenticationDAO.getUserRoles(user.getId());

                Dealer dealer = m_UserDAO.findDealer(user.getId());

                Set<Role> roles = new HashSet<Role>(roleStrings.size());
                for (String rs : roleStrings)
                {
                    try
                    {
                        roles.add(Role.valueOf(rs));
                    }
                    catch (Exception aEx)
                    {
                        log.warn("unknown role: " + rs);
                    }
                }

                String sessionId = m_SessionService.generateSessionId();

                Authentication result = new OFMPAuthenticationToken(user, dealer, sessionId, m_Mapper
                        .getAuthorities(roles), roles);

                m_SessionService.registerSession(result);

                return result;
            }
            else
                return null;
        }
        catch (SmbAuthException aEx)
        {
            switch (aEx.getNtStatus())
            {
                case jcifs.smb.NtStatus.NT_STATUS_LOGON_FAILURE:
                    throw new BadCredentialsException("Invalid password.");
                case jcifs.smb.NtStatus.NT_STATUS_NO_SUCH_USER:
                    throw new BadCredentialsException("Invalid user.");
                case jcifs.smb.NtStatus.NT_STATUS_PASSWORD_EXPIRED:
                    throw new BadCredentialsException("Password has expired.");
                case jcifs.smb.NtStatus.NT_STATUS_WRONG_PASSWORD:
                    throw new BadCredentialsException("Invalid password.");
                case jcifs.smb.NtStatus.NT_STATUS_ACCOUNT_LOCKED_OUT:
                    throw new BadCredentialsException("This account is currently locked out.");
                case jcifs.smb.NtStatus.NT_STATUS_ACCOUNT_DISABLED:
                    throw new BadCredentialsException("This account is currently disabled.");
                default:
                    throw new AuthenticationServiceException("Cannot check user credentials", aEx);
            }
        }
        catch (Exception aEx)
        {
            throw new AuthenticationServiceException("Cannot check user credentials", aEx);
        }
    }

    private boolean NTLMAuthentication(String aUser, String aPassword) throws Exception
    {
        UniAddress pdc = UniAddress.getByName(m_PDC);

        NtlmPasswordAuthentication credentials = new NtlmPasswordAuthentication(m_Domain, aUser, aPassword);

        SmbSession.logon(pdc, credentials);

        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean supports(Class aAuthentication)
    {
        return aAuthentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public void setAuthenticationDAO(AuthenticationDAO aAuthenticationDAO)
    {
        m_AuthenticationDAO = aAuthenticationDAO;
    }

    public void setSessionService(SessionService aSessionService)
    {
        m_SessionService = aSessionService;
    }

    @Required
    public void setPDC(String aPdc)
    {
        m_PDC = aPdc;
    }

    public void setDomain(String aDomain)
    {
        m_Domain = aDomain;
    }

    public void setUserDAO(UserDAO aUserDAO)
    {
        m_UserDAO = aUserDAO;
    }
}
