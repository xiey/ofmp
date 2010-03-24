package org.eclipse.ofmp.mail.test;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class SpringContextTest extends
		AbstractDependencyInjectionSpringContextTests {

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"META-INF/spring/mail-context.xml"};
	}
	public void testSpringContext() throws Exception {
		assertNotNull(getApplicationContext());
	}
	
}
