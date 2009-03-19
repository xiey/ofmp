package org.eclipse.ofmp.common.dom;

import java.math.BigDecimal;
import java.util.Date;

public interface ForexSwapTradeMessage extends ForexTradeMessage
{
    public enum SwapType
    {
        STANDARD, HEDGE
    }

    public abstract BigDecimal getNearLegNegociatedAmount();

    public abstract void setNearLegNegociatedAmount(BigDecimal aNearLegNegociatedAmount);

    public abstract BigDecimal getNearLegSecondAmount();

    public abstract void setNearLegSecondAmount(BigDecimal aNearLegSecondAmount);

    public abstract BigDecimal getNearLegBasisRate();

    public abstract void setNearLegBasisRate(BigDecimal aNearLegBasisRate);

    public abstract BigDecimal getNearLegSpread();

    public abstract void setNearLegSpread(BigDecimal aNearLegSpread);

    public abstract BigDecimal getNearLegRate();

    public abstract void setNearLegRate(BigDecimal aNearLegRate);

    public abstract Date getNearLegValueDate();

    public abstract void setNearLegValueDate(Date aNearLegValueDate);

    public abstract BigDecimal getFarLegNegociatedAmount();

    public abstract void setFarLegNegociatedAmount(BigDecimal aFarLegNegociatedAmount);

    public abstract BigDecimal getFarLegSecondAmount();

    public abstract void setFarLegSecondAmount(BigDecimal aFarLegSecondAmount);

    public abstract BigDecimal getFarLegSwapPoints();

    public abstract void setFarLegSwapPoints(BigDecimal aFarLegSwapPoints);

    public abstract BigDecimal getFarLegRate();

    public abstract void setFarLegRate(BigDecimal aFarLegRate);

    public abstract Date getFarLegValueDate();

    public abstract void setFarLegValueDate(Date aFarLegValueDate);

    public abstract SwapType getSwapType();

    public abstract void setSwapType(SwapType aSwapType);

}
