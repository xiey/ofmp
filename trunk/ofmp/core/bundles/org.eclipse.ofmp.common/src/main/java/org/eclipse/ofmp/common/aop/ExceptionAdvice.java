package org.eclipse.ofmp.common.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.common.exception.StatusFactory;

public class ExceptionAdvice implements MethodInterceptor
{
    private Logger log = Logger.getLogger(getClass());

    public void afterThrowing(Method aMethod, Object[] aArguments, Object aTarget, OFMPException aException)
    {
    }

    public Object invoke(MethodInvocation aInvocation) throws Throwable
    {
        try
        {
            return aInvocation.proceed();
        }
        catch (Throwable aEx)
        {
            Throwable throwable;

            if (aEx instanceof OFMPException)
                throwable = aEx;
            else
                throwable = new OFMPException(StatusFactory.GENERAL_ERROR, aEx);

            log.error("method " + aInvocation.getMethod() + " generated an exception: " + throwable.toString(),
                    throwable);

            throw throwable;
        }
    }
}
