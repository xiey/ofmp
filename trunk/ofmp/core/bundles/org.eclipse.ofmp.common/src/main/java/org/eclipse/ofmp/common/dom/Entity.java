package org.eclipse.ofmp.common.dom;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.ofmp.common.utils.OFMPToStringBuilder;

public abstract class Entity implements Serializable
{
	private static final long serialVersionUID = 1L;

	protected Integer m_Id, m_Generation;

    protected String PREFIX_OWNER = "VT_OWNER";

    public Entity()
    {
        m_Generation = 0;
    }

    /**
     * A copy constructor to clone this object.
     * 
     * @param position
     *            the position to be cloned
     */
    public Entity(final Entity aEntity)
    {
        this.m_Id = aEntity.getId();
        this.m_Generation = aEntity.getGeneration();
    }

    public Integer getId()
    {
        return m_Id;
    }

    public void setId(Integer aId)
    {
        m_Id = aId;
    }

    public boolean isNew()
    {
        return m_Id == null;
    }

    public Integer getGeneration()
    {
        return m_Generation;
    }

    public void setGeneration(Integer aGeneration)
    {
        m_Generation = aGeneration;
    }

    public String getPrefixOwner()
    {
        return PREFIX_OWNER;
    }

    /**
     * @see Object.equals()
     */
    @Override
    public boolean equals(final Object aOther)
    {
        if (aOther instanceof Entity == false)
            return false;

        if (this == aOther)
            return true;

        Entity rhs = (Entity) aOther;

        return new EqualsBuilder().append(m_Id, rhs.m_Id).isEquals();

    }

    /**
     * @see Object.hashcode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(5, 7).append(m_Id).toHashCode();
    }

    @Override
    public String toString()
    {
        return build(new OFMPToStringBuilder()).toString();
    }

    public OFMPToStringBuilder build(OFMPToStringBuilder aBuilder)
    {
        aBuilder.append("Id", m_Id);

        return aBuilder;
    }
}
