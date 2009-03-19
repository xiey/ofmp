package org.eclipse.ofmp.common.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class TraceAdvice implements MethodInterceptor
{
    private Logger log = Logger.getLogger(getClass());
    
    private boolean m_TraceEnabled;
    private boolean m_TimingEnabled;

    public void setTraceEnabled(boolean aEnabled)
    {
        m_TraceEnabled = aEnabled;
    }

    public void setTimingEnabled(boolean aEnabled)
    {
        m_TimingEnabled = aEnabled;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        if (m_TraceEnabled && log.isDebugEnabled())
            log.debug("method " + invocation.getMethod() + " has been called on object " + invocation.getThis());

        if (m_TimingEnabled && log.isDebugEnabled())
        {
            long start = System.currentTimeMillis();
            Object retVal = invocation.proceed();
            log.debug("Elapsed time=" + (System.currentTimeMillis() - start));
            return retVal;
        }
        else
        {
            return invocation.proceed();
        }

    }
}
