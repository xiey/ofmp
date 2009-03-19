package org.eclipse.ofmp.security.dom;

import java.util.ArrayList;
import java.util.List;

public enum Role
{
    FX_DESK_HEAD("Head of FX Desk"), MM_DESK_HEAD("Head of MM Desk"), FX_TRADER("FX Trader"), MM_TRADER("MM Trader"), CM_TRADER(
            "CM Trader"), MIDOFFICE("Middle Office"), BACKOFFICE("Back Office"), HELPDESK("Helpdesk"), RISKMANAGER(
            "Risk Manager"), TOPMANAGEMENT("Top Management"), TOFLISTENER("TOF Listener"), BACKOFFICECONNECTOR(
            "BackOffice Connector"), JMXUSER("JMX User"), SYSTEMSCHEDULER("System Scheduler"), FINANCE("Finance"), ADMIN(
            "Administrator"), WEBSERVICE("Webservice"), PORTFOLIOMANAGEMENT("PortfolioManagement"), HISTORY_POSITION_LISTENER("History Position Listener");

    private String m_Name;

    Role(String aName)
    {
        m_Name = aName;
    }

    public final String getName()
    {
        return m_Name;
    }

    public static final List<Role> getClientRoles()
    {
        ArrayList<Role> result = new ArrayList<Role>();

        result.add(Role.FX_TRADER);
        result.add(Role.FX_DESK_HEAD);

        result.add(Role.MM_TRADER);
        result.add(Role.MM_DESK_HEAD);

        result.add(Role.MIDOFFICE);
        result.add(Role.BACKOFFICE);
        result.add(Role.FINANCE);
        result.add(Role.RISKMANAGER);
        result.add(Role.TOPMANAGEMENT);
        result.add(Role.HELPDESK);

        return result;
    }

}
