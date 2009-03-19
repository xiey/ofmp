package org.eclipse.ofmp.security.dom;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.ofmp.common.dom.Entity;

/**
 * TODO This class should extend the User class
 */
public class Dealer extends Entity implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Map<String, String> m_FrontOfficeIdMap = new HashMap<String, String>();
    private DealerType m_Type;

    public void setFrontOfficeIdMap(Map<String, String> aMap)
    {
        m_FrontOfficeIdMap = aMap;
    }

    public String getFrontOfficeId(String aSystemId)
    {
        return m_FrontOfficeIdMap.get(aSystemId);
    }

    public Map<String, String> getFrontofficeIds()
    {
        return m_FrontOfficeIdMap;
    }

    public DealerType getType()
    {
        return m_Type;
    }

    public void setType(DealerType aType)
    {
        m_Type = aType;
    }

    /**
     * Every dealer belongs to one of the following desk: Forex, Money Market, Capital Market
     */
    public enum DealerType
    {
        FX(Role.FX_TRADER), MM(Role.MM_TRADER), CM(Role.CM_TRADER);

        private Role m_Role;

        DealerType(Role aRole)
        {
            m_Role = aRole;
        }

        public Role getRole()
        {
            return m_Role;
        }

        public void setRole(Role aRole)
        {
            m_Role = aRole;
        }

    }

}
