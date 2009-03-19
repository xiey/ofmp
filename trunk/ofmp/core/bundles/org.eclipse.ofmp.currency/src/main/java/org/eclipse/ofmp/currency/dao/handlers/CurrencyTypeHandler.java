package org.eclipse.ofmp.currency.dao.handlers;

import java.sql.SQLException;

import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.currency.business.CurrencyService;
import org.eclipse.ofmp.currency.dom.Currency;
import org.springframework.beans.factory.annotation.Configurable;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

@Configurable
public class CurrencyTypeHandler implements TypeHandlerCallback
{
    private CurrencyService m_CurrencyService;


    public Object getResult(ResultGetter aGetter) throws SQLException
    {
        return valueOf(aGetter.getString());
    }

    public void setParameter(ParameterSetter aSetter, Object aParameter) throws SQLException
    {
        Currency currency = (Currency) aParameter;

        aSetter.setString(currency.getISOCode());
    }

    public Object valueOf(String aString)
    {
        try
        {
            return m_CurrencyService.findCurrency(aString);
        }
        catch (OFMPException aEx)
        {
            throw new RuntimeException(aEx);
        }
    }

	public void setCurrencyService(CurrencyService aCurrencyService) {
		m_CurrencyService = aCurrencyService;
	}
}
