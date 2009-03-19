package org.eclipse.ofmp.currency.business.internal;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.common.exception.StatusFactory;
import org.eclipse.ofmp.currency.business.CurrencyRateQuery;
import org.eclipse.ofmp.currency.business.CurrencyRateService;
import org.eclipse.ofmp.currency.dao.CurrencyRateDAO;
import org.eclipse.ofmp.currency.dom.Currency;
import org.eclipse.ofmp.currency.dom.CurrencyRate;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = OFMPException.class)
public class CurrencyRateServiceImpl implements CurrencyRateService
{

    private final Logger log = Logger.getLogger(getClass());

    private CurrencyRateDAO m_CurrencyRateDAO;

    private final Map<Currency, CurrencyRate> m_LatestCache;

    public CurrencyRateServiceImpl()
    {
        m_LatestCache = new HashMap<Currency, CurrencyRate>();
    }

    public void setCurrencyRateDAO(CurrencyRateDAO aCurrencyRateDAO)
    {
        m_CurrencyRateDAO = aCurrencyRateDAO;
    }

    public CurrencyRate create(CurrencyRate aCurrencyRate) throws OFMPException
    {
        log.debug("Creating currencyRate " + aCurrencyRate);

        CurrencyRateManager.getInstance().validate(aCurrencyRate);

        try
        {
            m_CurrencyRateDAO.create(aCurrencyRate);

            CurrencyRate old = m_LatestCache.get(aCurrencyRate);
            if (old != null)
            {
                if (aCurrencyRate.getFixingDate().after(old.getFixingDate()))
                    m_LatestCache.put(aCurrencyRate.getCurrency(), aCurrencyRate);
            }

        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }

        return find(aCurrencyRate.getCurrency(), aCurrencyRate.getFixingDate());
    }

    public CurrencyRate update(CurrencyRate aCurrencyRate) throws OFMPException
    {
        log.debug("Updating currencyRate " + aCurrencyRate);

        CurrencyRate original = find(aCurrencyRate.getCurrency(), aCurrencyRate.getFixingDate());
        if (original == null)
            throw new OFMPException(StatusFactory.OBJECT_NOT_FOUND);

        if (!original.getGeneration().equals(aCurrencyRate.getGeneration()))
            throw new OFMPException(StatusFactory.CONCURRENT_MODIFICATION);

        CurrencyRateManager.getInstance().validate(aCurrencyRate);

        try
        {
            m_CurrencyRateDAO.update(aCurrencyRate);
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }

        CurrencyRate old = m_LatestCache.get(aCurrencyRate.getCurrency());
        if (old != null && old.getFixingDate().equals(aCurrencyRate.getFixingDate()))
            m_LatestCache.put(aCurrencyRate.getCurrency(), aCurrencyRate);

        return find(aCurrencyRate.getCurrency(), aCurrencyRate.getFixingDate());
    }

    public CurrencyRate find(Currency currency, Date fixingDate) throws OFMPException
    {
        CurrencyRate result = null;

        try
        {
            result = m_CurrencyRateDAO.find(currency, fixingDate);
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }

        return result;
    }

    public CurrencyRate findNearest(Currency currency, Date fixingDate, int inequality) throws OFMPException
    {
        CurrencyRate result = null;

        try
        {
            result = m_CurrencyRateDAO.findNearest(currency, fixingDate, inequality);
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }

        return result;
    }

    public List<CurrencyRate> enumerate(CurrencyRateQuery query) throws OFMPException
    {
        try
        {
            return m_CurrencyRateDAO.select(query);
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }
    }

    public void delete(Currency currency, Date fixingDate) throws OFMPException
    {
        try
        {
            m_CurrencyRateDAO.delete(currency, fixingDate);

            CurrencyRate old = m_LatestCache.get(currency);
            if (old != null && old.getFixingDate().equals(fixingDate))
                m_LatestCache.remove(currency);

        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }
    }

    public CurrencyRate findLatest(Currency aCurrency) throws OFMPException
    {
        try
        {
            CurrencyRate rate = m_LatestCache.get(aCurrency);
            if (rate == null)
            {
                rate = m_CurrencyRateDAO.findLatest(aCurrency);
                if (rate == null)
                {
                    log.error("Cannot find latest currency rate for currency " + aCurrency);
                    rate = new CurrencyRate(aCurrency);
                }
                else
                    m_LatestCache.put(rate.getCurrency(), rate);
            }

            return rate;

        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }
    }

}