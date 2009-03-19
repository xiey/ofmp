package org.eclipse.ofmp.security.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.ofmp.security.test.dao.UserDAOTest;
import org.eclipse.ofmp.security.test.service.AuthenticationTest;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.eclipse.ofmp.security.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(UserDAOTest.class);
		suite.addTestSuite(AuthenticationTest.class);
		//$JUnit-END$
		return suite;
	}

}
