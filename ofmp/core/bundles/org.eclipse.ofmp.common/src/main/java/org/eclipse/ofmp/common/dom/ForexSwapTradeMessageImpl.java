package org.eclipse.ofmp.common.dom;

import java.math.BigDecimal;
import java.util.Date;

public class ForexSwapTradeMessageImpl extends ForexTradeMessageImpl implements ForexSwapTradeMessage
{
    public ForexSwapTradeMessageImpl()
    {
    }

    private static final long serialVersionUID = 1L;

    // NearLeg
    private BigDecimal m_NearLegNegociatedAmount;
    private BigDecimal m_NearLegSecondAmount;
    private BigDecimal m_NearLegBasisRate;
    private BigDecimal m_NearLegSpread;
    private BigDecimal m_NearLegRate;

    private Date m_NearLegValueDate;

    // FarLeg
    private BigDecimal m_FarLegNegociatedAmount;
    private BigDecimal m_FarLegSecondAmount;
    private BigDecimal m_FarLegSwapPoints;
    private BigDecimal m_FarLegRate;

    private Date m_FarLegValueDate;
    private SwapType m_SwapType;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getNearLegNegociatedAmount()
     */
    public BigDecimal getNearLegNegociatedAmount()
    {
        return m_NearLegNegociatedAmount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setNearLegNegociatedAmount(java.math.BigDecimal)
     */
    public void setNearLegNegociatedAmount(BigDecimal aNearLegNegociatedAmount)
    {
        m_NearLegNegociatedAmount = aNearLegNegociatedAmount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getNearLegSecondAmount()
     */
    public BigDecimal getNearLegSecondAmount()
    {
        return m_NearLegSecondAmount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setNearLegSecondAmount(java.math.BigDecimal)
     */
    public void setNearLegSecondAmount(BigDecimal aNearLegSecondAmount)
    {
        m_NearLegSecondAmount = aNearLegSecondAmount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getNearLegBasisRate()
     */
    public BigDecimal getNearLegBasisRate()
    {
        return m_NearLegBasisRate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setNearLegBasisRate(java.math.BigDecimal)
     */
    public void setNearLegBasisRate(BigDecimal aNearLegBasisRate)
    {
        m_NearLegBasisRate = aNearLegBasisRate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getNearLegSpread()
     */
    public BigDecimal getNearLegSpread()
    {
        return m_NearLegSpread;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setNearLegSpread(java.math.BigDecimal)
     */
    public void setNearLegSpread(BigDecimal aNearLegSpread)
    {
        m_NearLegSpread = aNearLegSpread;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getNearLegRate()
     */
    public BigDecimal getNearLegRate()
    {
        return m_NearLegRate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setNearLegRate(java.math.BigDecimal)
     */
    public void setNearLegRate(BigDecimal aNearLegRate)
    {
        m_NearLegRate = aNearLegRate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getNearLegValueDate()
     */
    public Date getNearLegValueDate()
    {
        return m_NearLegValueDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setNearLegValueDate(java.util.Date)
     */
    public void setNearLegValueDate(Date aNearLegValueDate)
    {
        m_NearLegValueDate = aNearLegValueDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getFarLegNegociatedAmount()
     */
    public BigDecimal getFarLegNegociatedAmount()
    {
        return m_FarLegNegociatedAmount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setFarLegNegociatedAmount(java.math.BigDecimal)
     */
    public void setFarLegNegociatedAmount(BigDecimal aFarLegNegociatedAmount)
    {
        m_FarLegNegociatedAmount = aFarLegNegociatedAmount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getFarLegSecondAmount()
     */
    public BigDecimal getFarLegSecondAmount()
    {
        return m_FarLegSecondAmount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setFarLegSecondAmount(java.math.BigDecimal)
     */
    public void setFarLegSecondAmount(BigDecimal aFarLegSecondAmount)
    {
        m_FarLegSecondAmount = aFarLegSecondAmount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getFarLegSwapPoints()
     */
    public BigDecimal getFarLegSwapPoints()
    {
        return m_FarLegSwapPoints;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setFarLegSwapPoints(java.math.BigDecimal)
     */
    public void setFarLegSwapPoints(BigDecimal aFarLegSwapPoints)
    {
        m_FarLegSwapPoints = aFarLegSwapPoints;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getFarLegRate()
     */
    public BigDecimal getFarLegRate()
    {
        return m_FarLegRate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setFarLegRate(java.math.BigDecimal)
     */
    public void setFarLegRate(BigDecimal aFarLegRate)
    {
        m_FarLegRate = aFarLegRate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getFarLegValueDate()
     */
    public Date getFarLegValueDate()
    {
        return m_FarLegValueDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setFarLegValueDate(java.util.Date)
     */
    public void setFarLegValueDate(Date aFarLegValueDate)
    {
        m_FarLegValueDate = aFarLegValueDate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#getSwapType()
     */
    public SwapType getSwapType()
    {
        return m_SwapType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessage#setSwapType(org.eclipse.ofmp.service.common.dom.ForexSwapTradeMessageImpl.SwapType)
     */
    public void setSwapType(SwapType aSwapType)
    {
        m_SwapType = aSwapType;
    }

}
