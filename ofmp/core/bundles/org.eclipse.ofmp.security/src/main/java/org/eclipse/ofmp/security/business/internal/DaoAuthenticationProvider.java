package org.eclipse.ofmp.security.business.internal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.ofmp.security.business.OFMPAuthenticationToken;
import org.eclipse.ofmp.security.dao.AuthenticationDAO;
import org.eclipse.ofmp.security.dao.UserDAO;
import org.eclipse.ofmp.security.dom.Dealer;
import org.eclipse.ofmp.security.dom.Role;
import org.eclipse.ofmp.security.dom.User;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

public class DaoAuthenticationProvider implements AuthenticationProvider {

	private final Logger log = Logger.getLogger(getClass());

	private AuthenticationDAO m_AuthenticationDAO;
	private UserDAO m_UserDAO;

	private SessionService m_SessionService;

	private final AuthorityMapper m_Mapper = new AuthorityMapper();

	public Authentication authenticate(Authentication aAuthentication)
			throws AuthenticationException {
		
		try 
		{
			String username = (String) aAuthentication.getPrincipal();

			User user = m_AuthenticationDAO.getUserByLogin(username);

			if (user == null)
				throw new BadCredentialsException(
						"User doesn't exist in the system.");
			else if (!user.getEnabled())
				throw new BadCredentialsException("The user " + user.getName()
						+ " is currently disabled in the system.");

			Collection<String> roleStrings = m_AuthenticationDAO
					.getUserRoles(user.getId());

			Dealer dealer = m_UserDAO.findDealer(user.getId());

			Set<Role> roles = new HashSet<Role>(roleStrings.size());
			for (String rs : roleStrings) {
				try {
					roles.add(Role.valueOf(rs));
				} catch (Exception aEx) {
					log.warn("unknown role: " + rs);
				}
			}

			String sessionId = m_SessionService.generateSessionId();

			Authentication result = new OFMPAuthenticationToken(user,
					dealer, sessionId, m_Mapper.getAuthorities(roles), roles);

			m_SessionService.registerSession(result);

			return result;
		} catch (Exception aEx) {
			throw new AuthenticationServiceException(
					"Cannot check user credentials", aEx);
		}
	}

	@SuppressWarnings("unchecked")
	public boolean supports(Class aAuthentication) {
		return aAuthentication
				.equals(UsernamePasswordAuthenticationToken.class);
	}

	public void setAuthenticationDAO(AuthenticationDAO authenticationDAO) {
		m_AuthenticationDAO = authenticationDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		m_UserDAO = userDAO;
	}

	public void setSessionService(SessionService sessionService) {
		m_SessionService = sessionService;
	}
}
