package org.eclipse.ofmp.currency.test;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class SpringContextTest extends
		AbstractDependencyInjectionSpringContextTests {

	@Override
	protected String[] getConfigLocations() {
		return new String[] {"currency-context-test.xml", "META-INF/spring/currency-context.xml"};
	}
	public void testSpringContext() throws Exception {
		assertNotNull(getApplicationContext());
	}
	
}
