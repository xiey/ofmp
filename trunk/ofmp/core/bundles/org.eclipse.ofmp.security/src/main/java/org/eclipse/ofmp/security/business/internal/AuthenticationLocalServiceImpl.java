package org.eclipse.ofmp.security.business.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.ofmp.security.business.OFMPAuthenticationToken;
import org.eclipse.ofmp.security.dom.Role;
import org.eclipse.ofmp.security.dom.User;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.providers.AuthenticationProvider;

public class AuthenticationLocalServiceImpl implements AuthenticationLocalService
{
    private SessionService m_SessionService;

    private AuthenticationProvider m_LoginProvider;

    @Required
    public void setLoginProvider(AuthenticationProvider aProvider)
    {
        m_LoginProvider = aProvider;
    }

    public Authentication authenticate(Authentication aAuthentication)
    {
        OFMPAuthenticationToken authentication = (OFMPAuthenticationToken) m_LoginProvider
                .authenticate(aAuthentication);

        return authentication;
    }

    private final AuthorityMapper m_Mapper = new AuthorityMapper();

    private OFMPAuthenticationToken getAuthentication(String aUserName, Set<Role> aRoles)
    {
        User user = new User();
        user.setName(aUserName);
        user.setOSUser("$" + aUserName);

        String sessionId = m_SessionService.generateSessionId();

        OFMPAuthenticationToken result = new OFMPAuthenticationToken(user, null, sessionId, m_Mapper
                .getAuthorities(aRoles), aRoles);

        m_SessionService.registerSession(result);

        return result;
    }

    public OFMPAuthenticationToken getTOFAuthentication()
    {
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.TOFLISTENER);

        return getAuthentication("Reuters", roles);
    }

    public Authentication getBackOfficeAuthentication()
    {
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.BACKOFFICECONNECTOR);

        return getAuthentication("BackOffice", roles);
    }

    public Authentication getJMXAuthentication()
    {
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.JMXUSER);

        return getAuthentication("JMX", roles);
    }

    public Authentication getWebServiceAuthentication()
    {
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.WEBSERVICE);

        return getAuthentication("Web Service", roles);
    }

    public Authentication getSchedulerAuthentication()
    {
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.SYSTEMSCHEDULER);

        return getAuthentication("Scheduler", roles);
    }

    public Authentication getPortfolioManagementAuthentication()
    {
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.PORTFOLIOMANAGEMENT);

        return getAuthentication("Positionkeeping", roles);
    }

    public Authentication getHistoryPositionListenerAuthentication()
    {
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.HISTORY_POSITION_LISTENER);

        return getAuthentication("HistoryPositionListener", roles);
    }

    public void setSessionService(SessionService aSessionService)
    {
        m_SessionService = aSessionService;
    }

}
