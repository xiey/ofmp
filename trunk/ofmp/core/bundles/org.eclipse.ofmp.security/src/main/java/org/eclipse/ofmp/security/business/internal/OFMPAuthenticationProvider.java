package org.eclipse.ofmp.security.business.internal;

import org.eclipse.ofmp.security.business.OFMPAuthenticationToken;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.CredentialsExpiredException;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

public class OFMPAuthenticationProvider implements AuthenticationProvider
{
    private SessionService m_SessionService;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        if (authentication instanceof OFMPAuthenticationToken)
        {
            if (m_SessionService.isTokenValid(authentication))
                return authentication;
            else
                throw new CredentialsExpiredException("");
            
        } 
        else if (authentication instanceof UsernamePasswordAuthenticationToken)
        {
            if (m_SessionService.isTokenValid(authentication))
                return m_SessionService.getRegisteredAuthentication(authentication.getCredentials());
            else
                throw new CredentialsExpiredException("");
        }
        else
            throw new BadCredentialsException("invalid authentication class: " + authentication.getClass());

    }

    @SuppressWarnings("unchecked")
    public boolean supports(Class aAuthentication)
    {
        return true;
    }

    public void setSessionService(SessionService aSessionService)
    {
        m_SessionService = aSessionService;
    }
}
