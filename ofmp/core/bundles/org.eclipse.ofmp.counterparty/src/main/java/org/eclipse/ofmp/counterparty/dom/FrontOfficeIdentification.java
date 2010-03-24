package org.eclipse.ofmp.counterparty.dom;

import java.util.HashSet;

public class FrontOfficeIdentification extends HashSet<String>
{
    private static final long serialVersionUID = 1L;

    private String m_SystemId;

    public FrontOfficeIdentification(FrontOfficeIdentification aOther)
    {
        m_SystemId = aOther.m_SystemId;
        addAll(aOther);
    }

    public FrontOfficeIdentification(String aSystemId)
    {
        m_SystemId = aSystemId;
    }

    public String getSystemId()
    {
        return m_SystemId;
    }

    public void setSystemId(String aSystemId)
    {
        m_SystemId = aSystemId;
    }
}
