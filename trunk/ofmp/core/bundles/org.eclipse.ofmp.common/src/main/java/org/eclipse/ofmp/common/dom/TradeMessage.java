package org.eclipse.ofmp.common.dom;

import java.io.Serializable;
import java.util.Date;

import org.eclipse.ofmp.common.dom.ForexTradeMessage.BuySellIndicator;
import org.eclipse.ofmp.common.dom.ForexTradeMessage.RateDirection;

public interface TradeMessage extends Serializable
{
    public enum Product
    {
        SPOT, FORWARD, SWAP, LOAN, DEPOSIT, TRANSFER
    }

    public enum Status
    {
        NEW, VALIDATED, CLOSED, CANCELLED, REVERTED;
    }

    public abstract BuySellIndicator getBuySellIndicator();

    public abstract void setBuySellIndicator(BuySellIndicator aBuySellIndicator);

    public abstract String getCounterparty();

    public abstract void setCounterparty(String aCounterparty);

    public abstract Date getTradeDate();

    public abstract void setTradeDate(Date aTradeDate);

    public abstract RateDirection getRateDirection();

    public abstract void setRateDirection(RateDirection aRateDirection);

    public abstract Status getStatus();

    public abstract void setStatus(Status aStatus);

    public abstract String getNegociatedCurrency();

    public abstract void setNegociatedCurrency(String aNegociatedCurrency);

    public abstract String getSecondCurrency();

    public abstract void setSecondCurrency(String aSecondCurrency);

}
