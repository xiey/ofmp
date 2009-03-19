package org.eclipse.ofmp.common.exception;

public class OFMPException extends Exception
{
    private static final long serialVersionUID = 1L;

    private Status m_Status;

    private transient Throwable m_Cause;

    public OFMPException(Status aStatus)
    {
        m_Status = aStatus;
    }

    public OFMPException(Status aStatus, Throwable aCause)
    {
        super();

        m_Cause = aCause;
        m_Status = aStatus;
    }

    public Throwable getCause()
    {
        return m_Cause;
    }

    public Status getStatus()
    {
        return m_Status;
    }

    public String toString()
    {
        return m_Status.toString();
    }
}
