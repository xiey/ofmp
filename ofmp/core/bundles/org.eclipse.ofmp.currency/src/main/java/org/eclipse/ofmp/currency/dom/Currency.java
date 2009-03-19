package org.eclipse.ofmp.currency.dom;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.eclipse.ofmp.common.utils.OFMPToStringBuilder;

public class Currency implements Comparable<Currency>, Serializable
{
    private static final long serialVersionUID = 1L;

    /*
     * Source: http://en.wikipedia.org/wiki/ISO_4217
     */
    private static final Set<String> oneDecimalDigitCurrencies = new HashSet<String>();
    private static final Set<String> threeDecimalDigitCurrencies = new HashSet<String>();

    static
    {
        oneDecimalDigitCurrencies.addAll(Arrays.asList(new String[]
        { "BIF", "BYR", "CLF", "CLP", "DJF", "GNF", "ISK", "JPY", "KMF", "KRW", "MGA", "PYG", "RWF", "VUV", "XAF" }));
        threeDecimalDigitCurrencies.addAll(Arrays.asList(new String[]
        { "BHD", "IQD", "JOD", "KWD", "LYD", "OMR", "TND" }));
    }

    // ISO 4217
    private String m_ISOCode;
    private String m_Name;
    private Boolean m_CLS;

    public static final Currency NO_CURRENCY = new Currency("XXX", "No currency");

    public Currency()
    {
        m_ISOCode = NO_CURRENCY.m_ISOCode;
        m_Name = NO_CURRENCY.m_Name;
        m_CLS = new Boolean(false);
    }

    public Currency(String aISOCode)
    {
        m_ISOCode = aISOCode;
        m_Name = aISOCode;
        m_CLS = new Boolean(false);
    }

    public Currency(String aISOCode, String aName)
    {
        m_ISOCode = aISOCode;
        m_Name = aName;
        m_CLS = new Boolean(false);
    }

    public Currency(String aISOCode, String aName, Boolean isCls)
    {
        m_ISOCode = aISOCode;
        m_Name = aName;
        m_CLS = isCls;
    }

    /**
     * A copy constructor to clone this object.
     * 
     * @param aCurrency
     *            the currency to be cloned
     */
    public Currency(final Currency aCurrency)
    {
        m_ISOCode = new String(aCurrency.getISOCode());
        m_Name = new String(aCurrency.getName());
        m_CLS = new Boolean(aCurrency.isCLS());
    }

    public String getISOCode()
    {
        return m_ISOCode;
    }

    public void setISOCode(String aCurrencyCode)
    {
        m_ISOCode = aCurrencyCode;
    }

    @Override
    public boolean equals(Object aOther)
    {
        if (aOther instanceof Currency == false)
            return false;

        Currency other = (Currency) aOther;

        return new EqualsBuilder().append(m_ISOCode, other.m_ISOCode).isEquals();

    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(31, 7).append(m_ISOCode).toHashCode();
    }

    @Override
    public String toString()
    {
        return build(new OFMPToStringBuilder()).toString();
    }

    public OFMPToStringBuilder build(OFMPToStringBuilder aBuilder)
    {
        aBuilder.append("ISO Code", m_ISOCode).append("Name", m_Name).append("CLS", m_CLS);

        return aBuilder;
    }

    public String getName()
    {
        return m_Name;
    }

    public void setName(String aName)
    {
        m_Name = aName;
    }

    public final Boolean isCLS()
    {
        return m_CLS;
    }

    public final void setCLS(Boolean CLS)
    {
        this.m_CLS = CLS;
    }

    /**
     * Gets the default number of fraction digits used with this currency. For example, the default number of fraction
     * digits for the Euro is 2, while for the Japanese Yen it's 0.
     * 
     * @return the default number of fraction digits used with this currency
     */
    public int getDefaultFractionDigits()
    {
        if (oneDecimalDigitCurrencies.contains(m_ISOCode))
            return 1;
        else if (threeDecimalDigitCurrencies.contains(m_ISOCode))
            return 3;
        else
            return 2;
    }

    public int compareTo(Currency aCurrency)
    {
        return m_ISOCode.compareTo(aCurrency.getISOCode());
    }

}
