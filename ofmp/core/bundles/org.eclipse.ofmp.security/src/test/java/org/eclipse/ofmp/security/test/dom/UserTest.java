package org.eclipse.ofmp.security.test.dom;

import junit.framework.TestCase;

import org.eclipse.ofmp.security.dom.User;

import com.gargoylesoftware.base.testing.EqualsTester;


public class UserTest extends TestCase
{

    public final void testEqualHashCode()
    {
        User user1 = new User();
        User user2 = new User();

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    public final void testDifferentHashCode()
    {
        User user1 = new User();
        User user2 = new User();
        user2.setId(1);

        assertFalse(user1.hashCode() == user2.hashCode());
    }

    public final void testEquals()
    {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user3.setId(1);

        Object user4 = new Object();

        // a is a control object against which the other three objects are to be compared.
        // b is a different object in memory than a but equal to a according to its value.
        // c is expected not to be equal to a.
        // If the class you are testing is final—cannot be subclassed—then d ought to
        // be null; otherwise, d represents an object that looks equal to a but is not. By
        // “looks equal,” we mean (for example) that d has the same properties as a, but
        // because d has additional properties through subclassing, it is not equal to a.
        new EqualsTester(user1, user2, user3, user4);
    }

}
