package org.eclipse.ofmp.security.test;

import junit.framework.TestCase;

import org.eclipse.ofmp.security.business.internal.GrantedAuthorityFactory;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

public class GrantedAuthorityFactoryTest extends TestCase
{
    public void testGetAuthorities()
    {
        GrantedAuthority[] result = GrantedAuthorityFactory.getAuthorities(new String[]
        { "TEST1", "TEST2" });

        assertEquals(2, result.length);
        assertEquals(result[0], new GrantedAuthorityImpl(GrantedAuthorityFactory.s_RolesPrefix + "TEST1"));
        assertEquals(result[1], new GrantedAuthorityImpl(GrantedAuthorityFactory.s_RolesPrefix + "TEST2"));
    }

}
