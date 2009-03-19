package org.eclipse.ofmp.common.dom;

import java.math.BigDecimal;
import java.util.Date;

public interface ForexSimpleTradeMessage extends ForexTradeMessage
{

    public abstract BigDecimal getRate();

    public abstract void setRate(BigDecimal aRate);

    public abstract BigDecimal getNegociatedAmount();

    public abstract void setNegociatedAmount(BigDecimal aNegociatedAmount);

    public abstract BigDecimal getSecondAmount();

    public abstract void setSecondAmount(BigDecimal aSecondAmount);

    public abstract Date getValueDate();

    public abstract void setValueDate(Date aValueDate);

}
