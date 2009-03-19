package org.eclipse.ofmp.common.utils;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class OFMPToStringBuilder
{
    private StringBuffer m_Buffer;
    private int m_Indent;

    public OFMPToStringBuilder()
    {
        m_Buffer = new StringBuffer();
        m_Indent = 0;
    }

    private OFMPToStringBuilder(StringBuffer aBuffer, int aIndent)
    {
        m_Buffer = aBuffer;
        m_Indent = aIndent;
    }

    public OFMPToStringBuilder indent()
    {
        return new OFMPToStringBuilder(new StringBuffer(), m_Indent + 4);
    }

    public OFMPToStringBuilder append(String aKey, BigDecimal aNumber)
    {
        return append(aKey, (Object) aNumber);
    }

    public OFMPToStringBuilder append(String aKey, Boolean aValue)
    {
        return append(aKey, aValue != null ? (aValue ? "Yes" : "No") : null);
    }

    public OFMPToStringBuilder append(String aKey, Date aNumber)
    {
        return append(aKey, (Object) aNumber);
    }

    public OFMPToStringBuilder append(String aKey, Object aValue)
    {
        m_Buffer.append(getPropertyPrefix() + aKey + "=" + (aValue != null ? aValue.toString() : "<null>") + "\n");

        return this;
    }

    public OFMPToStringBuilder appendNested(String aString, OFMPToStringBuilder aBuilder)
    {
        StringBuffer nested = new StringBuffer();

        nested.append(getPropertyPrefix() + aString + "=\n");
        nested.append(getPropertyPrefix() + "[\n");
        nested.append(aBuilder.toString());
        nested.append(getPropertyPrefix() + "]\n");
        m_Buffer.append(nested);

        return this;
    }

    private String getPropertyPrefix()
    {
        return StringUtils.repeat(" ", m_Indent);
    }

    public String toString()
    {
        return m_Buffer.toString();
    }
}
