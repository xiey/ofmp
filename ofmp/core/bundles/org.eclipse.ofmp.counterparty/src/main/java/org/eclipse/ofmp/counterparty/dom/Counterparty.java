package org.eclipse.ofmp.counterparty.dom;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.ofmp.common.dom.Entity;

public abstract class Counterparty extends Entity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String m_Name;

    private Type m_Type;

    public enum Type
    {
        CUSTOMER, BANK
    }

    public Counterparty()
    {
    }

    public Counterparty(final Counterparty aCounterparty)
    {
        super(aCounterparty);

        m_Name = aCounterparty.m_Name;
        m_Type = aCounterparty.m_Type;
    }

    public Type getType()
    {
        return m_Type;
    }

    public void setType(Type aType)
    {
        m_Type = aType;
    }

    public String getName()
    {
        return m_Name;
    }

    public void setName(String aName)
    {
        m_Name = aName;
    }

    @Override
    public boolean equals(final Object aOther)
    {
        if (aOther instanceof Counterparty == false)
            return false;

        if (this == aOther)
            return true;

        Counterparty rhs = (Counterparty) aOther;

        return new EqualsBuilder().appendSuper(super.equals(aOther)).append(m_Name, rhs.m_Name).append(m_Type,
                rhs.m_Type).isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(5, 7).appendSuper(super.hashCode()).append(m_Name).append(m_Type).toHashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("ShortName", m_Name).append("DateFormat",
                m_Type).toString();
    }
}