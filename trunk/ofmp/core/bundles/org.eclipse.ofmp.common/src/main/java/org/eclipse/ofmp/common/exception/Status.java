package org.eclipse.ofmp.common.exception;

import java.io.Serializable;

public class Status implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String m_Code;
    private Severity m_Severity;
    private String m_DefaultMessage;

    Status(String aCode, Severity aSeverity, String aDefaultMessage)
    {
        m_Code = aCode;
        m_Severity = aSeverity;
        m_DefaultMessage = aDefaultMessage;
    }

    public String getCode()
    {
        return m_Code;
    }

    public String getDefaultMessage()
    {
        return m_DefaultMessage;
    }

    public Severity getSeverity()
    {
        return m_Severity;
    }

    public enum Severity
    {
        ERROR, WARN
    }

    public String toString()
    {
        return m_Severity + "-" + m_Code + " (" + m_DefaultMessage + ")";
    }

    public boolean equals(Object aOther)
    {
        if (aOther instanceof Status == false)
            return false;

        Status other = (Status) aOther;

        return m_Code.equals(other.m_Code);
    }

    public int hashCode()
    {
        return m_Code.hashCode();
    }
}
