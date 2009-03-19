package org.eclipse.ofmp.currency.business.internal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.common.exception.Status;
import org.eclipse.ofmp.common.exception.StatusFactory;
import org.eclipse.ofmp.currency.business.CurrencyService;
import org.eclipse.ofmp.currency.dao.CurrencyDAO;
import org.eclipse.ofmp.currency.dom.Currency;

public class CurrencyServiceImpl implements CurrencyService
{
    private CurrencyDAO m_DAO;
    private Currency m_ReportingCurrency;
	private String m_CurrencyISOCode;

    public Collection<Currency> enumerate(Boolean excludeReportingCurrency) throws OFMPException
    {
        ArrayList<Currency> result = null;
        try
        {
            result = (ArrayList<Currency>) m_DAO.select();

            if (result != null && excludeReportingCurrency)
                result.remove(getReportingCurrency());
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }

        return result;
    }

    public Currency findCurrency(String aCurrencyCode) throws OFMPException
    {
        try
        {
            return m_DAO.find(aCurrencyCode);
        }
        catch (SQLException aEx)
        {
            throw new OFMPException(StatusFactory.DBMS_ERROR, aEx);
        }
    }

	public Currency getReportingCurrency() throws OFMPException {
		
		if (m_CurrencyISOCode == null)
			throw new OFMPException(StatusFactory.ILLEGAL_ARGUMENT, new IllegalArgumentException("Currency ISOCode is not set."));
		
		if (m_ReportingCurrency == null)
			m_ReportingCurrency = findCurrency(m_CurrencyISOCode);
		
		return m_ReportingCurrency;
	}
    
    public void setCurrencyDAO(CurrencyDAO aDAO)
    {
        m_DAO = aDAO;
    }

    public void setReportingCurrencyISOCode(String aCurrencyISOCode)
    {
        m_CurrencyISOCode = aCurrencyISOCode;
    }

}
