package org.eclipse.ofmp.security.test.dom;

import junit.framework.TestCase;

import org.eclipse.ofmp.security.dom.UserRole;

import com.gargoylesoftware.base.testing.EqualsTester;

public class UserRoleTest extends TestCase
{

    public final void testEqualHashCode()
    {
        UserRole userRole1 = new UserRole();
        UserRole userRole2 = new UserRole();

        assertEquals(userRole1.hashCode(), userRole2.hashCode());
    }

    public final void testDifferentHashCode()
    {
        UserRole userRole1 = new UserRole();
        UserRole userRole2 = new UserRole();
        userRole2.setId(1);

        assertFalse(userRole1.hashCode() == userRole2.hashCode());
    }

    public final void testEquals()
    {
        UserRole userRole1 = new UserRole();
        UserRole userRole2 = new UserRole();
        UserRole userRole3 = new UserRole();
        userRole3.setId(1);

        Object userRole4 = new Object();

        // a is a control object against which the other three objects are to be compared.
        // b is a different object in memory than a but equal to a according to its value.
        // c is expected not to be equal to a.
        // If the class you are testing is final—cannot be subclassed—then d ought to
        // be null; otherwise, d represents an object that looks equal to a but is not. By
        // “looks equal,” we mean (for example) that d has the same properties as a, but
        // because d has additional properties through subclassing, it is not equal to a.
        new EqualsTester(userRole1, userRole2, userRole3, userRole4);
    }

}
