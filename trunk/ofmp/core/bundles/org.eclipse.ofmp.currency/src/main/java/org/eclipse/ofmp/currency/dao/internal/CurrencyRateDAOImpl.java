package org.eclipse.ofmp.currency.dao.internal;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.eclipse.ofmp.common.utils.DateUtils;
import org.eclipse.ofmp.currency.business.CurrencyRateQuery;
import org.eclipse.ofmp.currency.business.CurrencyRateService;
import org.eclipse.ofmp.currency.dao.CurrencyRateDAO;
import org.eclipse.ofmp.currency.dom.Currency;
import org.eclipse.ofmp.currency.dom.CurrencyRate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CurrencyRateDAOImpl extends SqlMapClientDaoSupport implements CurrencyRateDAO
{

    public CurrencyRate create(CurrencyRate aCurrencyRates) throws SQLException
    {
        getSqlMapClient().insert("CurrencyRate.insertCurrencyRate", aCurrencyRates);

        return aCurrencyRates;
    }

    public CurrencyRate update(CurrencyRate aCurrencyRate) throws SQLException
    {
        getSqlMapClient().update("CurrencyRate.updateCurrencyRate", aCurrencyRate);

        return find(aCurrencyRate.getCurrency(), aCurrencyRate.getFixingDate());
    }

    public CurrencyRate find(Currency aCurrency, Date fixingDate) throws SQLException
    {
        fixingDate = DateUtils.getDate(fixingDate);

        CurrencyRateQuery query = new CurrencyRateQuery();

        query.setCurrency(aCurrency);
        query.setFixingDateFrom(fixingDate);
        query.setFixingDateTo(fixingDate);

        return (CurrencyRate) getSqlMapClient().queryForObject("CurrencyRate.selectCurrencyRates", query);
    }

    @SuppressWarnings("unchecked")
    public List<CurrencyRate> select(CurrencyRateQuery query) throws SQLException
    {
        if (query != null)
        {
            if (query.getFixingDateFrom() != null)
                query.setFixingDateFrom(DateUtils.getDate(query.getFixingDateFrom()));
            if (query.getFixingDateTo() != null)
                query.setFixingDateTo(DateUtils.getDate(query.getFixingDateTo()));
        }

        return getSqlMapClient().queryForList("CurrencyRate.selectCurrencyRates", query, 0, 200);
    }

    @SuppressWarnings("unchecked")
    public void delete(Currency currency, Date fixingDate) throws SQLException
    {
        fixingDate = DateUtils.getDate(fixingDate);

        HashMap map = new HashMap();
        map.put("ISOCODE", currency.getISOCode());
        map.put("FIXING_DATE", fixingDate);

        getSqlMapClient().delete("CurrencyRate.deleteCurrencyRate", map);
    }

    public CurrencyRate findNearest(Currency currency, Date fixingDate, int inequality) throws SQLException
    {
        fixingDate = DateUtils.getDate(fixingDate);

        CurrencyRateQuery query = new CurrencyRateQuery();

        query.setCurrency(currency);
        query.setFixingDateFrom(fixingDate);

        if (inequality == CurrencyRateService.GREATER_EQUAL)
            return (CurrencyRate) getSqlMapClient().queryForObject("CurrencyRate.selectNearestGreaterCurrencyRate",
                    query);
        else
            return (CurrencyRate) getSqlMapClient()
                    .queryForObject("CurrencyRate.selectNearestLowerCurrencyRate", query);
    }

    public CurrencyRate findLatest(Currency aCurrency) throws SQLException
    {
        CurrencyRateQuery query = new CurrencyRateQuery();
        query.setCurrency(aCurrency);

        return (CurrencyRate) getSqlMapClient().queryForObject("CurrencyRate.selectLatestCurrencyRate", query);
    }

}
