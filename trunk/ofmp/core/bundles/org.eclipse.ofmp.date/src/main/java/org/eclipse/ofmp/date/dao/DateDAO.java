package org.eclipse.ofmp.date.dao;

import java.sql.SQLException;
import java.util.Date;

public interface DateDAO
{
    Date readBusinessDay() throws SQLException;

    void writeBusinessDay(Date aDate) throws SQLException;
}
