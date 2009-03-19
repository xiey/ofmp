package org.eclipse.ofmp.common.dom;

import java.math.BigDecimal;
import java.util.Date;

public abstract class ForexSimpleTradeMessageImpl extends ForexTradeMessageImpl implements ForexSimpleTradeMessage
{
    public ForexSimpleTradeMessageImpl()
    {
    }

    private static final long serialVersionUID = 1L;

    private BigDecimal m_NegociatedAmount;

    private BigDecimal m_SecondAmount;
    private Date m_ValueDate;
    private BigDecimal m_Rate;

    /* (non-Javadoc)
     * @see org.eclipse.ofmp.service.common.dom.ForexSimpleTradeMessage#getRate()
     */
    public BigDecimal getRate()
    {
        return m_Rate;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ofmp.service.common.dom.ForexSimpleTradeMessage#setRate(java.math.BigDecimal)
     */
    public void setRate(BigDecimal aRate)
    {
        m_Rate = aRate;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ofmp.service.common.dom.ForexSimpleTradeMessage#getNegociatedAmount()
     */
    public BigDecimal getNegociatedAmount()
    {
        return m_NegociatedAmount;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ofmp.service.common.dom.ForexSimpleTradeMessage#setNegociatedAmount(java.math.BigDecimal)
     */
    public void setNegociatedAmount(BigDecimal aNegociatedAmount)
    {
        m_NegociatedAmount = aNegociatedAmount;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ofmp.service.common.dom.ForexSimpleTradeMessage#getSecondAmount()
     */
    public BigDecimal getSecondAmount()
    {
        return m_SecondAmount;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ofmp.service.common.dom.ForexSimpleTradeMessage#setSecondAmount(java.math.BigDecimal)
     */
    public void setSecondAmount(BigDecimal aSecondAmount)
    {
        m_SecondAmount = aSecondAmount;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ofmp.service.common.dom.ForexSimpleTradeMessage#getValueDate()
     */
    public Date getValueDate()
    {
        return m_ValueDate;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ofmp.service.common.dom.ForexSimpleTradeMessage#setValueDate(java.util.Date)
     */
    public void setValueDate(Date aValueDate)
    {
        m_ValueDate = aValueDate;
    }
}
