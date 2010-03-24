package org.eclipse.ofmp.currency.business;

import java.util.Collection;

import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.currency.dom.Currency;

public interface CurrencyService
{
    public Currency findCurrency(String aCurrencyCode) throws OFMPException;

    public Collection<Currency> enumerate(Boolean excludeReportingCurrency) throws OFMPException;

    public Currency getReportingCurrency() throws OFMPException;
}
