package org.eclipse.ofmp.security.dom;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.eclipse.ofmp.common.dom.Entity;

public class UserRole extends Entity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Integer m_User;
    private Role m_Role;

    public UserRole()
    {
    }

    public UserRole(Integer user, Role role)
    {
        m_User = user;
        m_Role = role;
    }

    public final Role getRole()
    {
        return m_Role;
    }

    public final void setRole(Role role)
    {
        this.m_Role = role;
    }

    public final Integer getUser()
    {
        return m_User;
    }

    public final void setUser(Integer user)
    {
        this.m_User = user;
    }

    @Override
    public boolean equals(final Object aOther)
    {
        if (aOther instanceof UserRole == false)
            return false;

        if (this == aOther)
            return true;

        UserRole rhs = (UserRole) aOther;

        return new EqualsBuilder().appendSuper(super.equals(aOther)).append(m_User, rhs.m_User).append(m_Role,
                rhs.m_Role).isEquals();
    }

    /**
     * @see Object.hashcode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(7, 11).appendSuper(super.hashCode()).append(m_User).append(m_Role).toHashCode();
    }

    /**
     * @return the human readable string
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("UserId", m_User).append("Role", m_Role)
                .toString();
    }
}
