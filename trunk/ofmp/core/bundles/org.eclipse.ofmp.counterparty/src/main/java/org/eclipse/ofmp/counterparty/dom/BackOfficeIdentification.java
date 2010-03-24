package org.eclipse.ofmp.counterparty.dom;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class BackOfficeIdentification implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String m_Id;
    private Integer m_Portfolio;

    public BackOfficeIdentification()
    {

    }

    public BackOfficeIdentification(BackOfficeIdentification aOther)
    {
        m_Id = aOther.m_Id;
        m_Portfolio = aOther.m_Portfolio;
    }

    public String getId()
    {
        return m_Id;
    }

    public void setId(String aCode)
    {
        m_Id = aCode;
    }

    public Integer getPortfolio()
    {
        return m_Portfolio;
    }

    public void setPortfolio(Integer aPortfolio)
    {
        m_Portfolio = aPortfolio;
    }

    public String toString()
    {
        return new ToStringBuilder(super.toString()).append("Id", m_Id).append("Portfolio", m_Portfolio).toString();
    }

    public int hashCode()
    {
        return new HashCodeBuilder(17, 19).append(m_Id).append(m_Portfolio).toHashCode();
    }

    public boolean equals(Object aOther)
    {
        if (aOther instanceof BackOfficeIdentification == false)
            return false;

        if (this == aOther)
            return true;

        BackOfficeIdentification rhs = (BackOfficeIdentification) aOther;

        return new EqualsBuilder().append(m_Id, rhs.m_Id).append(m_Portfolio, rhs.m_Portfolio).isEquals();
    }
}
