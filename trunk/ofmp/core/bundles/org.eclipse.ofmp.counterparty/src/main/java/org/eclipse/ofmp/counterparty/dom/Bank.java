package org.eclipse.ofmp.counterparty.dom;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Bank extends Counterparty implements Serializable
{
    private static final long serialVersionUID = 1L;

    private boolean m_CLS;

    private BackOfficeIdentification m_BackofficeIdentification, m_CLSBackofficeIdentification;

    private Map<String, FrontOfficeIdentification> m_FrontOfficeIdentifications = new HashMap<String, FrontOfficeIdentification>();

    public Bank()
    {
        setType(Type.BANK);

        m_BackofficeIdentification = new BackOfficeIdentification();
        m_CLSBackofficeIdentification = new BackOfficeIdentification();

        m_FrontOfficeIdentifications = new HashMap<String, FrontOfficeIdentification>();
    }

    public Bank(final Bank aBank)
    {
        super(aBank);

        m_CLS = aBank.m_CLS;

        m_BackofficeIdentification = new BackOfficeIdentification(aBank.m_BackofficeIdentification);
        m_CLSBackofficeIdentification = new BackOfficeIdentification(aBank.m_CLSBackofficeIdentification);

        m_FrontOfficeIdentifications = new HashMap<String, FrontOfficeIdentification>();

        for (Map.Entry<String, FrontOfficeIdentification> foi : aBank.m_FrontOfficeIdentifications.entrySet())
            m_FrontOfficeIdentifications.put(foi.getKey(), foi.getValue());
    }

    @Override
    public boolean equals(Object aOther)
    {
        if (aOther instanceof Bank == false)
            return false;

        if (this == aOther)
            return true;

        Bank rhs = (Bank) aOther;

        return new EqualsBuilder().appendSuper(super.equals(aOther)).append(m_CLS, rhs.m_CLS).append(
                m_BackofficeIdentification, rhs.m_BackofficeIdentification).append(m_CLSBackofficeIdentification,
                rhs.m_CLSBackofficeIdentification).isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(3, 23).appendSuper(super.hashCode()).append(m_CLS)
                .append(m_BackofficeIdentification).append(m_CLSBackofficeIdentification).toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("CLS Compliant", m_CLS).append(
                "BackofficeIdentification", m_BackofficeIdentification).append("CLSBackofficeIdentification",
                m_CLSBackofficeIdentification).append("Identification", m_FrontOfficeIdentifications).toString();
    }

    public boolean isCLS()
    {
        return m_CLS;
    }

    public void setCLS(boolean aComplaint)
    {
        m_CLS = aComplaint;
    }

    public BackOfficeIdentification getBackofficeIdentification()
    {
        return m_BackofficeIdentification;
    }

    public BackOfficeIdentification getCLSBackofficeIdentification()
    {
        return m_CLSBackofficeIdentification;
    }

    public void setBackofficeIdentification(BackOfficeIdentification aIdentification)
    {
        m_BackofficeIdentification = aIdentification;
    }

    public void setCLSBackofficeIdentification(BackOfficeIdentification aIdentification)
    {
        m_CLSBackofficeIdentification = aIdentification;
    }

    public FrontOfficeIdentification getIdentification(String aSystemId)
    {
        return m_FrontOfficeIdentifications.get(aSystemId);
    }

    public void addIdentification(FrontOfficeIdentification aIdentification)
    {
        m_FrontOfficeIdentifications.put(aIdentification.getSystemId(), aIdentification);
    }

    public Map<String, FrontOfficeIdentification> getFrontOfficeIdentifications()
    {
        return m_FrontOfficeIdentifications;
    }

    public void setFrontOfficeIdentifications(Map<String, FrontOfficeIdentification> aFrontOfficeIdentifications)
    {
        m_FrontOfficeIdentifications = aFrontOfficeIdentifications;
    }
}
