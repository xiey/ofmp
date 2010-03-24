package org.eclipse.ofmp.counterparty.dom;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Customer extends Counterparty implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String m_ShortName;

    public Customer()
    {
        setType(Type.CUSTOMER);
    }

    public Customer(final Customer aCustomer)
    {
        super(aCustomer);

        m_ShortName = aCustomer.getShortName() == null ? null : new String(aCustomer.getShortName());
    }

    public String getShortName()
    {
        return m_ShortName;
    }

    public void setShortName(String aShortName)
    {
        m_ShortName = aShortName;
    }

    public boolean equals(Object aOther)
    {
        if (aOther instanceof Customer == false)
            return false;

        if (this == aOther)
            return true;

        Customer rhs = (Customer) aOther;

        return new EqualsBuilder().appendSuper(super.equals(aOther)).append(m_ShortName, rhs.m_ShortName).isEquals();
    }

    public int hashCode()
    {
        return new HashCodeBuilder(7, 43).appendSuper(super.hashCode()).append(m_ShortName).toHashCode();
    }

    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("ShortName", m_ShortName).toString();
    }

}
