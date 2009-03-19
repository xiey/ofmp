package org.eclipse.ofmp.common.dom;

import java.io.Serializable;
import java.util.Date;

import org.eclipse.ofmp.common.dom.ForexTradeMessage.BuySellIndicator;
import org.eclipse.ofmp.common.dom.ForexTradeMessage.RateDirection;

public abstract class TradeMessageImpl implements Serializable, TradeMessage
{
    private static final long serialVersionUID = 1L;

    private String m_Counterparty;
    private Date m_TradeDate;
    private BuySellIndicator m_BuySellIndicator;
    private RateDirection m_RateDirection;
    private String m_NegociatedCurrency;
    private String m_SecondCurrency;

    private Status m_Status;

    public TradeMessageImpl()
    {
        m_Status = Status.NEW;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#getBuySellIndicator()
     */
    public BuySellIndicator getBuySellIndicator()
    {
        return m_BuySellIndicator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#setBuySellIndicator(org.eclipse.ofmp.service.common.dom.ForexTradeMessage.BuySellIndicator)
     */
    public void setBuySellIndicator(BuySellIndicator aBuySellIndicator)
    {
        m_BuySellIndicator = aBuySellIndicator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#getCounterparty()
     */
    public String getCounterparty()
    {
        return m_Counterparty;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#setCounterparty(java.lang.String)
     */
    public void setCounterparty(String aCounterparty)
    {
        m_Counterparty = aCounterparty;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#getTradeDate()
     */
    public Date getTradeDate()
    {
        return m_TradeDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#setTradeDate(java.util.Date)
     */
    public void setTradeDate(Date aTradeDate)
    {
        m_TradeDate = aTradeDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#getRateDirection()
     */
    public RateDirection getRateDirection()
    {
        return m_RateDirection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#setRateDirection(org.eclipse.ofmp.service.common.dom.ForexTradeMessage.RateDirection)
     */
    public void setRateDirection(RateDirection aRateDirection)
    {
        m_RateDirection = aRateDirection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#getStatus()
     */
    public Status getStatus()
    {
        return m_Status;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#setStatus(org.eclipse.ofmp.service.common.dom.TradeMessageImpl.Status)
     */
    public void setStatus(Status aStatus)
    {
        m_Status = aStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#getNegociatedCurrency()
     */
    public String getNegociatedCurrency()
    {
        return m_NegociatedCurrency;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#setNegociatedCurrency(java.lang.String)
     */
    public void setNegociatedCurrency(String aNegociatedCurrency)
    {
        m_NegociatedCurrency = aNegociatedCurrency;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#getSecondCurrency()
     */
    public String getSecondCurrency()
    {
        return m_SecondCurrency;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.TradeMessage#setSecondCurrency(java.lang.String)
     */
    public void setSecondCurrency(String aSecondCurrency)
    {
        m_SecondCurrency = aSecondCurrency;
    }
}
