package org.eclipse.ofmp.security.dom;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.ofmp.common.dom.Entity;

public class User extends Entity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String m_Name, m_OSUser;
    private Set<Role> m_Roles;
    private Boolean m_Enabled;

    public User()
    {
        m_Roles = new HashSet<Role>();
        m_Enabled = true;
    }

    public String getName()
    {
        return m_Name;
    }

    public void setName(String aName)
    {
        m_Name = aName;
    }

    public String getOSUser()
    {
        return m_OSUser;
    }

    public void setOSUser(String aUser)
    {
        m_OSUser = aUser;
    }

    public final Set<Role> getRoles()
    {
        return m_Roles;
    }

    public final void setRoles(Set<Role> roles)
    {
        this.m_Roles = roles;
    }

    public final Boolean getEnabled()
    {
        return m_Enabled;
    }

    public final void setEnabled(Boolean enabled)
    {
        this.m_Enabled = enabled;
    }

    public String getText()
    {
        return getName();
    }

    @Override
    public boolean equals(final Object aOther)
    {
        if (aOther instanceof User == false)
            return false;

        if (this == aOther)
            return true;

        User rhs = (User) aOther;

        return new EqualsBuilder().appendSuper(super.equals(aOther)).append(m_Name, rhs.m_Name).append(m_OSUser,
                rhs.m_OSUser).append(m_Roles, rhs.m_Roles).isEquals();
    }

    /**
     * @see Object.hashcode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(7, 11).appendSuper(super.hashCode()).append(m_Name).append(m_OSUser).append(m_Roles)
                .toHashCode();
    }

    @Override
    public String toString()
    {
        if (m_Name != null && m_Name.trim().length() > 0)
            return m_Name;
        else
            return m_OSUser;
    }
}
