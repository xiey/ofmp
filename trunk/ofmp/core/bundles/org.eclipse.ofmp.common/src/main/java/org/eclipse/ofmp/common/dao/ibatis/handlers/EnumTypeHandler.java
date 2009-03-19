package org.eclipse.ofmp.common.dao.ibatis.handlers;

import java.sql.SQLException;
import java.sql.Types;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public abstract class EnumTypeHandler implements TypeHandlerCallback
{

    @SuppressWarnings("unchecked")
    protected abstract Enum resolve(String aId);

    @SuppressWarnings("unchecked")
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException
    {
        if (parameter == null)
        {
            setter.setNull(Types.VARCHAR);
        }
        else
        {
            Enum enumeration = (Enum) parameter;
            setter.setString(enumeration.toString());
        }
    }

    public Object getResult(ResultGetter getter) throws SQLException
    {
        String value = getter.getString();
        if (value == null)
            return null;

        return resolve(value);
    }

    public Object valueOf(String s)
    {
        return s;
    }
}