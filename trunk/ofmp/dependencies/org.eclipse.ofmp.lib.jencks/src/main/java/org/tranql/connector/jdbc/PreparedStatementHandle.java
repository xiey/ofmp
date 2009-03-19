/*
 * Copyright (c) 2004 - 2007, Tranql project contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.tranql.connector.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 *
 * @version $Revision: 1.1 $ $Date: 2008/11/13 03:25:10 $
 */
public class PreparedStatementHandle extends StatementHandle implements PreparedStatement {
    public PreparedStatementHandle(ConnectionHandle c, PreparedStatement s) {
        super(c, s);
    }

    public ResultSet executeQuery() throws SQLException {
        try {
            return new ResultSetHandle(this, ((PreparedStatement) s).executeQuery());
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        try {
            return ((PreparedStatement) s).getMetaData();
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        try {
            return ((PreparedStatement) s).getParameterMetaData();
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void addBatch() throws SQLException {
        try {
            ((PreparedStatement) s).addBatch();
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void clearParameters() throws SQLException {
        try {
            ((PreparedStatement) s).clearParameters();
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public boolean execute() throws SQLException {
        try {
            return ((PreparedStatement) s).execute();
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public int executeUpdate() throws SQLException {
        try {
            return ((PreparedStatement) s).executeUpdate();
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setArray(int i, Array x) throws SQLException {
        try {
            ((PreparedStatement) s).setArray(i, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        try {
            ((PreparedStatement) s).setAsciiStream(parameterIndex, x, length);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        try {
            ((PreparedStatement) s).setBigDecimal(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        try {
            ((PreparedStatement) s).setBinaryStream(parameterIndex, x, length);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setBlob(int i, Blob x) throws SQLException {
        try {
            ((PreparedStatement) s).setBlob(i, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        try {
            ((PreparedStatement) s).setBoolean(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        try {
            ((PreparedStatement) s).setByte(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setBytes(int parameterIndex, byte x[]) throws SQLException {
        try {
            ((PreparedStatement) s).setBytes(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        try {
            ((PreparedStatement) s).setCharacterStream(parameterIndex, reader, length);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setClob(int i, Clob x) throws SQLException {
        try {
            ((PreparedStatement) s).setClob(i, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        try {
            ((PreparedStatement) s).setDate(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        try {
            ((PreparedStatement) s).setDate(parameterIndex, x, cal);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        try {
            ((PreparedStatement) s).setDouble(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        try {
            ((PreparedStatement) s).setFloat(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        try {
            ((PreparedStatement) s).setInt(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        try {
            ((PreparedStatement) s).setLong(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        try {
            ((PreparedStatement) s).setNull(parameterIndex, sqlType);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
        try {
            ((PreparedStatement) s).setNull(paramIndex, sqlType, typeName);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        try {
            ((PreparedStatement) s).setObject(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        try {
            ((PreparedStatement) s).setObject(parameterIndex, x, targetSqlType);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
        try {
            ((PreparedStatement) s).setObject(parameterIndex, x, targetSqlType, scale);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setRef(int i, Ref x) throws SQLException {
        try {
            ((PreparedStatement) s).setRef(i, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        try {
            ((PreparedStatement) s).setShort(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        try {
            ((PreparedStatement) s).setString(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        try {
            ((PreparedStatement) s).setTime(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        try {
            ((PreparedStatement) s).setTime(parameterIndex, x, cal);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        try {
            ((PreparedStatement) s).setTimestamp(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        try {
            ((PreparedStatement) s).setTimestamp(parameterIndex, x, cal);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        try {
            ((PreparedStatement) s).setUnicodeStream(parameterIndex, x, length);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        try {
            ((PreparedStatement) s).setURL(parameterIndex, x);
        } catch (SQLException e) {
            c.connectionError(e);
            throw e;
        }
    }
}
