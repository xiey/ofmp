package org.eclipse.ofmp.security.business;

import org.springframework.security.Authentication;

public interface AuthenticationService
{
    public Authentication authenticate(Authentication aAuthentication);
}
