package org.eclipse.ofmp.common.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor
{
    private PlatformTransactionManager m_TransactionManager;

    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        Object result = null;

        TransactionStatus status = m_TransactionManager.getTransaction(new DefaultTransactionDefinition(
                TransactionDefinition.PROPAGATION_REQUIRES_NEW));

        try
        {
            result = invocation.proceed();

            m_TransactionManager.commit(status);
        }
        catch (Throwable aEx)
        {
            if (status != null)
                m_TransactionManager.rollback(status);

            throw aEx;
        }

        return result;
    }

    public void setPlatformTransactionManager(PlatformTransactionManager aPlatformTransactionManager)
    {
        m_TransactionManager = aPlatformTransactionManager;
    }

}
