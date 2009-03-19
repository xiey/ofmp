package org.eclipse.ofmp.security.business;

import java.util.Set;

import org.eclipse.ofmp.security.dom.Dealer;
import org.eclipse.ofmp.security.dom.Role;
import org.eclipse.ofmp.security.dom.User;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationCredentialsNotFoundException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.AbstractAuthenticationToken;

public class OFMPAuthenticationToken extends AbstractAuthenticationToken
{
    private static final long serialVersionUID = 1L;

    private final String m_SessionId;
    private final Set<Role> m_Roles;

    private final User m_User;
    private Dealer m_Dealer;

    public OFMPAuthenticationToken(User aUser, Dealer aDealer, String aSessionId, GrantedAuthority[] aAuthorities,
            Set<Role> aRoles)
    {
        super(aAuthorities);

        m_Roles = aRoles;
        m_SessionId = aSessionId;
        m_User = aUser;
        m_Dealer = aDealer;
    }

    public boolean isInRole(Role aRole)
    {
        return m_Roles.contains(aRole);
    }

    public boolean isTrader()
    {
        return m_Roles.contains(Role.FX_TRADER) || m_Roles.contains(Role.MM_TRADER) || m_Roles.contains(Role.CM_TRADER);
    }

    public Object getCredentials()
    {
        return m_SessionId;
    }

    public Object getPrincipal()
    {
        return m_User;
    }

    public Dealer getDealer()
    {
        return m_Dealer;
    }

    public void setDealer(Dealer aDealer)
    {
        m_Dealer = aDealer;
    }

    public User getUser()
    {
        return m_User;
    }

    public static boolean isCurrentInRole(Role aRole)
    {
        return getCurrentAuthentication().isInRole(aRole);
    }

    public static OFMPAuthenticationToken getCurrentAuthentication()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth instanceof OFMPAuthenticationToken)
            return (OFMPAuthenticationToken) auth;
        else
            throw new AuthenticationCredentialsNotFoundException("Missing authentication");
    }

    /**
     * @see Object.equals()
     */
    @Override
    public boolean equals(final Object aOther)
    {
        if (aOther instanceof OFMPAuthenticationToken == false)
            return false;

        if (this == aOther)
            return true;

        OFMPAuthenticationToken rhs = (OFMPAuthenticationToken) aOther;

        return m_User.equals(rhs.m_User);
    }

}
