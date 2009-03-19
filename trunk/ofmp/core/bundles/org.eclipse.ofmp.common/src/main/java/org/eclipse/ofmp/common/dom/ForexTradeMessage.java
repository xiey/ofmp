package org.eclipse.ofmp.common.dom;

public interface ForexTradeMessage extends TradeMessage
{
    public enum BuySellIndicator
    {
        BUY, SELL;
    }

    public enum RateDirection
    {
        NORMAL, INVERSE;
    }
}
