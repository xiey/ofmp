package org.eclipse.ofmp.security.business.internal;

import java.util.ArrayList;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

public class GrantedAuthorityFactory
{
    public static final String s_RolesPrefix = "ROLE_";

    public static GrantedAuthority[] getAuthorities(String[] aRoles)
    {
        ArrayList<GrantedAuthority> result = new ArrayList<GrantedAuthority>();

        for (String role : aRoles)
            result.add(new GrantedAuthorityImpl(s_RolesPrefix + role));

        return result.toArray(new GrantedAuthority[result.size()]);
    }
}
