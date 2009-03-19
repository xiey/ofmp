package org.eclipse.ofmp.security.business.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.ofmp.security.dom.Role;
import org.springframework.security.GrantedAuthority;

public class AuthorityMapper
{
    private final HashMap<Role, GrantedAuthority[]> m_Authorities;

    public AuthorityMapper()
    {
        m_Authorities = new HashMap<Role, GrantedAuthority[]>();

        m_Authorities.put(Role.FX_TRADER, GrantedAuthorityFactory.getAuthorities(new String[]
        { "RESUBMIT_TICKET", "VIEW_TICKET", "CANCEL_TICKET", "CREATE_DEAL", "UPDATE_DEAL", "CANCEL_DEAL", "VIEW_DEAL",
                "VIEW_USER", "VIEW_DEALER", "VIEW_COUNTERPARTY", "VIEW_DEAL_HISTORY", "VIEW_POSITION_HISTORY",
                "VIEW_CURRENCY_RATE", "VIEW_PROFIT_LOSS", "VIEW_PORTFOLIO", "VIEW_POSITION", "VIEW_BACKOFFICE_DEAL",
                "VIEW_POSITION_CORRECTION", "CHANGE_BUSINESSLINE_DEAL", "CHANGE_HOLDER_DEAL",
                "CREATE_REFERENCE_POSITION", "UPDATE_REFERENCE_POSITION", "DELETE_REFERENCE_POSITION",
                "VIEW_REFERENCE_POSITION", "CREATE_POSITION_CORRECTION", "DELETE_POSITION_CORRECTION" }));

        m_Authorities.put(Role.MM_TRADER, GrantedAuthorityFactory.getAuthorities(new String[]
        { "RESUBMIT_TICKET", "VIEW_TICKET", "CANCEL_TICKET", "CREATE_DEAL", "UPDATE_DEAL", "CANCEL_DEAL", "VIEW_DEAL",
                "VIEW_USER", "VIEW_DEALER", "VIEW_COUNTERPARTY", "VIEW_DEAL_HISTORY", "VIEW_POSITION_HISTORY",
                "VIEW_CURRENCY_RATE", "VIEW_PORTFOLIO", "VIEW_POSITION", "VIEW_POSITION_CORRECTION",
                "CHANGE_BUSINESSLINE_DEAL", "CHANGE_HOLDER_DEAL", "CREATE_REFERENCE_POSITION",
                "UPDATE_REFERENCE_POSITION", "DELETE_REFERENCE_POSITION", "VIEW_REFERENCE_POSITION",
                "CREATE_POSITION_CORRECTION", "DELETE_POSITION_CORRECTION" }));

        m_Authorities.put(Role.MIDOFFICE, GrantedAuthorityFactory.getAuthorities(new String[]
        { "VIEW_TICKET", "UPDATE_DEAL", "CANCEL_DEAL", "VIEW_DEAL", "VALIDATE_DEAL", "VIEW_USER", "VIEW_DEALER",
                "VIEW_DEAL_HISTORY", "CREATE_COUNTERPARTY", "UPDATE_COUNTERPARTY", "VIEW_COUNTERPARTY",
                "VIEW_CURRENCY_RATE", "VIEW_PROFIT_LOSS", "CREATE_BACKOFFICE_DEAL", "DELETE_BACKOFFICE_DEAL",
                "SUBMIT_BACKOFFICE_DEAL", "RESUBMIT_BACKOFFICE_DEAL", "VIEW_BACKOFFICE_DEAL", "UPDATE_CORRECTED_VALUE",
                "VIEW_PORTFOLIO", "VIEW_POSITION", "VIEW_POSITION_CORRECTION", "CREATE_REFERENCE_POSITION",
                "UPDATE_REFERENCE_POSITION", "VIEW_REFERENCE_POSITION" }));

        m_Authorities.put(Role.BACKOFFICE, GrantedAuthorityFactory.getAuthorities(new String[]
        { "VIEW_DEAL", "CLOSE_DEAL", "VIEW_USER", "VIEW_DEALER", "VIEW_COUNTERPARTY", "CREATE_CURRENCY_RATE",
                "UPDATE_CURRENCY_RATE", "VIEW_CURRENCY_RATE", "DELETE_CURRENCY_RATE", "VIEW_BACKOFFICE_DEAL",
                "REVERT_DEAL" }));

        // TODO Rename to FOGLISTENER
        m_Authorities.put(Role.TOFLISTENER, GrantedAuthorityFactory.getAuthorities(new String[]
        { "CREATE_DEAL", "VIEW_DEAL", "SUBMIT_TICKET", "CREATE_TICKET", "UPDATE_TICKET", "VIEW_TICKET", "VIEW_USER",
                "VIEW_DEALER", "VIEW_COUNTERPARTY" }));

        m_Authorities.put(Role.BACKOFFICECONNECTOR, GrantedAuthorityFactory.getAuthorities(new String[]
        { "CREATE_DEAL", "VIEW_DEAL", "IMPORT_BACKOFFICE_DEAL", "EXPORT_BACKOFFICE_DEAL", "SUBMIT_BACKOFFICE_DEAL",
                "VIEW_BACKOFFICE_DEAL", "DELETE_BACKOFFICE_DEAL", "VIEW_USER", "VIEW_DEALER", "CREATE_COUNTERPARTY",
                "VIEW_COUNTERPARTY", "VIEW_CURRENCY_RATE", "CREATE_PROFIT_LOSS", "UPDATE_PROFIT_LOSS",
                "VIEW_PROFIT_LOSS", "CREATE_DEAL_HISTORY_ENTRY", "VIEW_DEAL_HISTORY" }));

        m_Authorities.put(Role.RISKMANAGER, GrantedAuthorityFactory.getAuthorities(new String[]
        { "VIEW_USER", "VIEW_TICKET", "VIEW_DEAL", "VIEW_BACKOFFICE_DEAL", "VIEW_DEAL_HISTORY",
                "VIEW_POSITION_HISTORY", "VIEW_DEALER", "VIEW_COUNTERPARTY", "VIEW_CURRENCY_RATE", "VIEW_PROFIT_LOSS",
                "VIEW_PORTFOLIO", "VIEW_POSITION", "VIEW_POSITION_CORRECTION", "VIEW_REFERENCE_POSITION" }));

        m_Authorities.put(Role.TOPMANAGEMENT, GrantedAuthorityFactory.getAuthorities(new String[]
        { "VIEW_USER", "VIEW_TICKET", "VIEW_DEAL", "VIEW_BACKOFFICE_DEAL", "VIEW_DEAL_HISTORY",
                "VIEW_POSITION_HISTORY", "VIEW_DEALER", "VIEW_COUNTERPARTY", "VIEW_CURRENCY_RATE", "VIEW_PROFIT_LOSS",
                "VIEW_PORTFOLIO", "VIEW_POSITION", "VIEW_POSITION_CORRECTION" }));

        m_Authorities.put(Role.HELPDESK, GrantedAuthorityFactory.getAuthorities(new String[]
        { "CREATE_USER", "UPDATE_USER", "VIEW_USER", "CREATE_DEALER", "UPDATE_DEALER", "VIEW_DEALER" }));

        m_Authorities.put(Role.FINANCE, GrantedAuthorityFactory.getAuthorities(new String[]
        { "VIEW_TICKET", "VIEW_DEAL", "VIEW_BACKOFFICE_DEAL", "VIEW_DEAL_HISTORY", "VIEW_COUNTERPARTY",
                "VIEW_CURRENCY_RATE", "VIEW_PROFIT_LOSS" }));

        /**
         * Internal roles
         */
        m_Authorities.put(Role.JMXUSER, GrantedAuthorityFactory.getAuthorities(new String[]
        { "VIEW_DEAL", "VIEW_BACKOFFICE_DEAL", "VIEW_DEALER", "VIEW_COUNTERPARTY", "VIEW_CURRENCY_RATE",
                "VIEW_PROFIT_LOSS" }));

        m_Authorities.put(Role.SYSTEMSCHEDULER, GrantedAuthorityFactory.getAuthorities(new String[]
        { "VIEW_DEAL", "VIEW_BACKOFFICE_DEAL", "VIEW_DEALER", "VIEW_COUNTERPARTY", "VIEW_CURRENCY_RATE",
                "VIEW_PROFIT_LOSS", "CREATE_POSITION_CORRECTION" }));

        m_Authorities.put(Role.WEBSERVICE, GrantedAuthorityFactory.getAuthorities(new String[]
        { "VIEW_POSITION" }));

        m_Authorities.put(Role.PORTFOLIOMANAGEMENT, GrantedAuthorityFactory.getAuthorities(new String[]
        { "VIEW_DEALER", "VIEW_USER", "VIEW_CURRENCY_RATE", "VIEW_POSITION", "VIEW_PORTFOLIO", "VIEW_COUNTERPARTY",
                "VIEW_REFERENCE_POSITION", "CREATE_REFERENCE_POSITION", "UPDATE_REFERENCE_POSITION",
                "CREATE_POSITION_CORRECTION", "DELETE_POSITION_CORRECTION" }));

        m_Authorities.put(Role.HISTORY_POSITION_LISTENER, GrantedAuthorityFactory.getAuthorities(new String[]
        { "CREATE_POSITION_HISTORY, VIEW_POSITION_HISTORY" }));

    }

    public GrantedAuthority[] getAuthorities(Role aRole)
    {
        GrantedAuthority[] authorities = m_Authorities.get(aRole);
        if (authorities == null)
            authorities = new GrantedAuthority[] {};

        return authorities;
    }

    public GrantedAuthority[] getAuthorities(Collection<Role> aRoles)
    {
        HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

        for (Role role : aRoles)
            Collections.addAll(authorities, getAuthorities(role));

        return authorities.toArray(new GrantedAuthority[] {});
    }
}
