package org.eclipse.ofmp.security.test;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class SpringContextTest extends
		AbstractDependencyInjectionSpringContextTests {

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"security-context-test.xml", "META-INF/spring/security-context.xml"};
	}
	public void testSpringContext() throws Exception {
		assertNotNull(getApplicationContext());
	}
	
}
