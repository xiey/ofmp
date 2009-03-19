package org.eclipse.ofmp.common.dom;

import java.math.BigDecimal;

public interface ForexForwardTradeMessage extends ForexSimpleTradeMessage
{

    public abstract BigDecimal getSwapPoints();

    public abstract void setSwapPoints(BigDecimal aSwapPoints);

}
