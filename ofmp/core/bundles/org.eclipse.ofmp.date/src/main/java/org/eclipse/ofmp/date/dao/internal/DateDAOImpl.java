package org.eclipse.ofmp.date.dao.internal;

import java.sql.SQLException;
import java.util.Date;

import org.eclipse.ofmp.date.dao.DateDAO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class DateDAOImpl extends SqlMapClientDaoSupport implements DateDAO
{

    public Date readBusinessDay() throws SQLException
    {
        return (Date) getSqlMapClient().queryForObject("Date.selectBusinessDay");
    }

    public void writeBusinessDay(Date aDate) throws SQLException
    {
        getSqlMapClient().delete("Date.deleteBusinessDay");
        getSqlMapClient().insert("Date.insertBusinessDay", aDate);
    }
}
