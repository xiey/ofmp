package org.eclipse.ofmp.currency.business;

import java.util.Date;
import java.util.List;

import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.currency.dom.Currency;
import org.eclipse.ofmp.currency.dom.CurrencyRate;
import org.springframework.security.annotation.Secured;

public interface CurrencyRateService
{
    public final static int LOWER_EQUAL = 1;
    public final static int GREATER_EQUAL = 2;

    @Secured(
    { "ROLE_CREATE_CURRENCY_RATE" })
    public CurrencyRate create(CurrencyRate currencyRate) throws OFMPException;

    @Secured(
    { "ROLE_UPDATE_CURRENCY_RATE" })
    public CurrencyRate update(CurrencyRate currencyRate) throws OFMPException;

    @Secured(
    { "ROLE_VIEW_CURRENCY_RATE" })
    public CurrencyRate find(Currency currency, Date fixingDate) throws OFMPException;

    @Secured(
    { "ROLE_VIEW_CURRENCY_RATE" })
    public CurrencyRate findLatest(Currency currency) throws OFMPException;

    /**
     * Get the currency rate as close as possible to the fixing date provided, depending on the currency and the
     * inequality parameter.
     * 
     * @param currency
     * @param fixingDate
     * @param inequality
     *            Either CurrencyRateService.LOWER_EQUAL or CurrencyRateService.GREATER_EQUAL
     * @return
     * @throws OFMPException
     */
    @Secured(
    { "ROLE_VIEW_CURRENCY_RATE" })
    public CurrencyRate findNearest(Currency currency, Date fixingDate, int inequality) throws OFMPException;

    @Secured(
    { "ROLE_VIEW_CURRENCY_RATE" })
    public List<CurrencyRate> enumerate(CurrencyRateQuery query) throws OFMPException;

    @Secured(
    { "ROLE_DELETE_CURRENCY_RATE" })
    public void delete(Currency currency, Date fixingDate) throws OFMPException;
}
