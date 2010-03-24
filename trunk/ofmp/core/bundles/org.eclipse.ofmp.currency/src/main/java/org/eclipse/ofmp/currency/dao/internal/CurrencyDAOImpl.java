package org.eclipse.ofmp.currency.dao.internal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ofmp.currency.dao.CurrencyDAO;
import org.eclipse.ofmp.currency.dom.Currency;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CurrencyDAOImpl extends SqlMapClientDaoSupport implements CurrencyDAO, InitializingBean, DisposableBean
{
    private Map<String, Currency> m_Currencies;

    public List<Currency> select() throws SQLException
    {
        return new ArrayList<Currency>(m_Currencies.values());
    }

    public Currency find(String aISOCode) throws SQLException
    {
        return m_Currencies.get(aISOCode);
    }

    @SuppressWarnings("unchecked")
    public void init() throws Exception
    {
        m_Currencies = new HashMap<String, Currency>();

        List<Currency> currencies = getSqlMapClient().queryForList("Currency.selectCurrencies");

        for (Currency currency : currencies)
            m_Currencies.put(currency.getISOCode(), currency);
    }

    public void destroy() throws Exception
    {
        m_Currencies = null;
    }

}
