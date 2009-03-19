package org.eclipse.ofmp.common.dom;

import java.math.BigDecimal;

public class ForexForwardTradeMessageImpl extends ForexSimpleTradeMessageImpl implements ForexForwardTradeMessage
{
    public ForexForwardTradeMessageImpl()
    {
    }

    private static final long serialVersionUID = 1L;

    private BigDecimal m_SwapPoints;

    /* (non-Javadoc)
     * @see org.eclipse.ofmp.service.common.dom.ForexForwardTradeMessage#getSwapPoints()
     */
    public BigDecimal getSwapPoints()
    {
        return m_SwapPoints;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ofmp.service.common.dom.ForexForwardTradeMessage#setSwapPoints(java.math.BigDecimal)
     */
    public void setSwapPoints(BigDecimal aSwapPoints)
    {
        m_SwapPoints = aSwapPoints;
    }

}
