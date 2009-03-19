package org.eclipse.ofmp.security.dom;

public class ExternalCode
{
    private String m_SystemId, m_Code;
    private Integer m_Entity;

    public String getSystemId()
    {
        return m_SystemId;
    }

    public void setSystemId(String aSystemId)
    {
        m_SystemId = aSystemId;
    }

    public String getCode()
    {
        return m_Code;
    }

    public void setCode(String aCode)
    {
        m_Code = aCode;
    }

    public Integer getEntity()
    {
        return m_Entity;
    }

    public void setEntity(Integer aEntity)
    {
        m_Entity = aEntity;
    }
}
