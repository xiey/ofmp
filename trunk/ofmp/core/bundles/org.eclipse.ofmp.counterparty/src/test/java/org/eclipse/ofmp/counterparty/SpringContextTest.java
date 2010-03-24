package org.eclipse.ofmp.counterparty;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class SpringContextTest extends AbstractDependencyInjectionSpringContextTests
{

    @Override
    protected String[] getConfigLocations()
    {
        return new String[]
        { "counterparty-context-test.xml", "META-INF/spring/counterparty-context.xml" };
    }

    public void testSpringContext() throws Exception
    {
        assertNotNull(getApplicationContext());
    }

}
