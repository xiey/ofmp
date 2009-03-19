package org.eclipse.ofmp.currency.dao;

import java.sql.SQLException;
import java.util.List;

import org.eclipse.ofmp.currency.dom.Currency;

public interface CurrencyDAO
{
    public List<Currency> select() throws SQLException;

    public Currency find(String aISOCode) throws SQLException;
}
