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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LazyAssociatableConnectionManager;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnectionFactory;

import org.tranql.connector.DissociatableConnectionHandle;
import org.tranql.connector.ManagedConnectionHandle;

/**
 *
 *
 * @version $Revision: 1.1 $ $Date: 2008/11/13 03:25:10 $
 */
public class ConnectionHandle implements Connection, DissociatableConnectionHandle {
    protected final LazyAssociatableConnectionManager cm;
    protected final ManagedConnectionFactory mcf;
    protected final ConnectionRequestInfo cri;

    protected ManagedConnectionHandle mc;
    protected boolean closed;

    public ConnectionHandle(LazyAssociatableConnectionManager cm, ManagedConnectionFactory mcf, ConnectionRequestInfo cri) {
        this.cm = cm;
        this.mcf = mcf;
        this.cri = cri;
    }

    protected ManagedConnectionHandle getManagedConnection() throws SQLException {
        if (closed) {
            throw new SQLException("Connection has been closed");
        }

        if (mc == null && cm != null) {
            try {
                cm.associateConnection(this, mcf, cri);
            } catch (ResourceException e) {
                if (e.getCause() instanceof SQLException) {
                    throw (SQLException) e.getCause();
                } else {
                    throw (SQLException) new SQLException("Failed lazy association with ManagedConnection").initCause(e);
                }
            }
            if (mc == null) {
                throw new SQLException("Failed lazy association with ManagedConnection");
            }
        }
        assert mc != null;
        return mc;
    }

    public void setAssociation(ManagedConnectionHandle mc) {
        this.mc = mc;
    }

    public ManagedConnectionHandle getAssociation() {
        return mc;
    }

    public boolean isClosed() throws SQLException {
        return closed;
    }

    public void close() throws SQLException {
        if (closed) {
            return;
        }
        if (mc != null) {
            mc.connectionClosed(this);
        }
        closed = true;
    }

    void connectionError(SQLException e) {
        mc.connectionError(e);
    }

    public void commit() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        Connection c = (Connection) mc.getPhysicalConnection();
        if (c.getAutoCommit()) {
            return;
        }

        try {
            LocalTransaction tx = mc.getClientLocalTransaction();
            tx.commit();
            tx.begin();
        } catch (ResourceException e) {
            if (e.getCause() instanceof SQLException) {
                throw (SQLException) e.getCause();
            } else {
                throw (SQLException) new SQLException().initCause(e);
            }
        }
    }

    public void rollback() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        Connection c = (Connection) mc.getPhysicalConnection();
        if (c.getAutoCommit()) {
            return;
        }

        try {
            LocalTransaction tx = mc.getClientLocalTransaction();
            tx.rollback();
            tx.begin();
        } catch (ResourceException e) {
            if (e.getCause() instanceof SQLException) {
                throw (SQLException) e.getCause();
            } else {
                throw (SQLException) new SQLException().initCause(e);
            }
        }
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        Connection c = (Connection) mc.getPhysicalConnection();
        if (autoCommit == c.getAutoCommit()) {
            // nothing to do
            return;
        }

        try {
            LocalTransaction tx = mc.getClientLocalTransaction();
            if (autoCommit) {
                // reenabling autoCommit - JDBC spec says current transaction is committed
                tx.commit();
            } else {
                // disabling autoCommit
                tx.begin();
            }
        } catch (ResourceException e) {
            if (e.getCause() instanceof SQLException) {
                throw (SQLException) e.getCause();
            } else {
                throw (SQLException) new SQLException().initCause(e);
            }
        }
    }

    public boolean getAutoCommit() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return ((Connection) mc.getPhysicalConnection()).getAutoCommit();
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    protected Statement wrapStatement(Statement s) {
        return new StatementHandle(this, s);
    }

    protected PreparedStatement wrapPreparedStatement(PreparedStatement ps) {
        return new PreparedStatementHandle(this, ps);
    }

    protected CallableStatement wrapCallableStatement(CallableStatement cs) {
        return new CallableStatementHandle(this, cs);
    }

    protected DatabaseMetaData wrapMetaData(DatabaseMetaData dbmd) {
        return new DatabaseMetaDataHandle(this, dbmd);
    }

    public Statement createStatement() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapStatement(((Connection) mc.getPhysicalConnection()).createStatement());
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapStatement(((Connection) mc.getPhysicalConnection()).createStatement(resultSetType, resultSetConcurrency));
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapStatement(((Connection) mc.getPhysicalConnection()).createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapPreparedStatement(((Connection) mc.getPhysicalConnection()).prepareStatement(sql));
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapPreparedStatement(((Connection) mc.getPhysicalConnection()).prepareStatement(sql, autoGeneratedKeys));
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public PreparedStatement prepareStatement(String sql, int columnIndexes[]) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapPreparedStatement(((Connection) mc.getPhysicalConnection()).prepareStatement(sql, columnIndexes));
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public PreparedStatement prepareStatement(String sql, String columnNames[]) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapPreparedStatement(((Connection) mc.getPhysicalConnection()).prepareStatement(sql, columnNames));
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapPreparedStatement(((Connection) mc.getPhysicalConnection()).prepareStatement(sql, resultSetType, resultSetConcurrency));
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapPreparedStatement(((Connection) mc.getPhysicalConnection()).prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapCallableStatement(((Connection) mc.getPhysicalConnection()).prepareCall(sql));
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapCallableStatement(((Connection) mc.getPhysicalConnection()).prepareCall(sql, resultSetType, resultSetConcurrency));
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapCallableStatement(((Connection) mc.getPhysicalConnection()).prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return wrapMetaData(((Connection) mc.getPhysicalConnection()).getMetaData());
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public String getCatalog() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return ((Connection) mc.getPhysicalConnection()).getCatalog();
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public void setCatalog(String catalog) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            ((Connection) mc.getPhysicalConnection()).setCatalog(catalog);
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public int getHoldability() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return ((Connection) mc.getPhysicalConnection()).getHoldability();
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public void setHoldability(int holdability) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            ((Connection) mc.getPhysicalConnection()).setHoldability(holdability);
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public int getTransactionIsolation() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return ((Connection) mc.getPhysicalConnection()).getTransactionIsolation();
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public void setTransactionIsolation(int level) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            ((Connection) mc.getPhysicalConnection()).setTransactionIsolation(level);
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public Map getTypeMap() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return ((Connection) mc.getPhysicalConnection()).getTypeMap();
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public void setTypeMap(Map map) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            ((Connection) mc.getPhysicalConnection()).setTypeMap(map);
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public SQLWarning getWarnings() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return ((Connection) mc.getPhysicalConnection()).getWarnings();
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public void clearWarnings() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            ((Connection) mc.getPhysicalConnection()).clearWarnings();
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public boolean isReadOnly() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return ((Connection) mc.getPhysicalConnection()).isReadOnly();
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            ((Connection) mc.getPhysicalConnection()).setReadOnly(readOnly);
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public Savepoint setSavepoint() throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return ((Connection) mc.getPhysicalConnection()).setSavepoint();
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return ((Connection) mc.getPhysicalConnection()).setSavepoint(name);
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            ((Connection) mc.getPhysicalConnection()).releaseSavepoint(savepoint);
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        // rollback(Savepoint) simply delegates as this does not interact with the transaction
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            ((Connection) mc.getPhysicalConnection()).rollback(savepoint);
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }

    public String nativeSQL(String sql) throws SQLException {
        ManagedConnectionHandle mc = getManagedConnection();
        try {
            return ((Connection) mc.getPhysicalConnection()).nativeSQL(sql);
        } catch (SQLException e) {
            connectionError(e);
            throw e;
        }
    }
}
