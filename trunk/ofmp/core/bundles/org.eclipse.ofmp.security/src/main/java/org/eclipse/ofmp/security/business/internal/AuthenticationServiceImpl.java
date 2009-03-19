package org.eclipse.ofmp.security.business.internal;

import org.eclipse.ofmp.security.business.AuthenticationService;
import org.eclipse.ofmp.security.business.OFMPAuthenticationToken;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.providers.AuthenticationProvider;

public class AuthenticationServiceImpl implements AuthenticationService
{
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

}
