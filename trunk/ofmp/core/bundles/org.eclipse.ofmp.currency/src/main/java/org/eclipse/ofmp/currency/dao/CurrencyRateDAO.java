package org.eclipse.ofmp.currency.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.eclipse.ofmp.currency.business.CurrencyRateQuery;
import org.eclipse.ofmp.currency.dom.Currency;
import org.eclipse.ofmp.currency.dom.CurrencyRate;

public interface CurrencyRateDAO
{
    public CurrencyRate create(CurrencyRate currencyRate) throws SQLException;

    public CurrencyRate update(CurrencyRate currencyRate) throws SQLException;

    public CurrencyRate find(Currency currency, Date fixingDate) throws SQLException;

    public CurrencyRate findNearest(Currency currency, Date fixingDate, int inequality) throws SQLException;

    public CurrencyRate findLatest(Currency aCurrency) throws SQLException;

    public List<CurrencyRate> select(CurrencyRateQuery query) throws SQLException;

    public void delete(Currency currency, Date fixingDate) throws SQLException;
}
