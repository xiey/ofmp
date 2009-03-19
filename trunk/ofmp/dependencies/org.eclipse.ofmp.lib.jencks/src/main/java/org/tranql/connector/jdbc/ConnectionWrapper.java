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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 * ConnectionWrapper provides a simple wrapper around a physical connection
 * object. This wrappering allows for calls to prepareStatement calls to be
 * intercepted and provides for additional monitoring of connection level
 * statistics.
 */

/**
 * ConnectionWrapper provides additional connection level capabilities on top of
 * a regular connection.  The current set of capabilities include:
 *
 * StatementCaching
 * IsolationLevel caching
 *
 * @author hogstrom
 *
 */
public class ConnectionWrapper implements Connection {
	private final static org.apache.commons.logging.Log log = LogFactory.getLog(Log.class);

    private boolean ISOLATION_CACHING_ENABLED = false;

    static final int DEFAULT_STMT_CACHE_SIZE = 0;

	Connection connection;

	private HashMap pStmtCache;
	private int     maxCacheSize = 0;
	private boolean caching = false;
	private int     cacheSize = 0;
    private int     isolationLevel = 0;

    /**
     * Constructs a new ConnectionWrapper object.  This constructor creates a connection wrapper
     * that does not provide a prepared statement cache.
     *
     * @param connection
     */
    public ConnectionWrapper(Connection connection) {
		this(connection, 0);
	}

    /**
     * Creates a connection wrapper that adds teh ability to cache prepared statements.
     *
     * @param connection
     * @param cacheSize
     */

    public ConnectionWrapper(Connection connection, int cacheSize) {
		caching = false;
		this.connection = connection;
		maxCacheSize = cacheSize <= 0 ? 0 : cacheSize;
		if (maxCacheSize > 0) {
			caching = true;
			pStmtCache = new HashMap(maxCacheSize * 2);
		}
        try {
            isolationLevel = connection.getTransactionIsolation();
            ISOLATION_CACHING_ENABLED = true;
        } catch (SQLException e) {
        }
    }

    /**
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
		if (!caching)
			return connection.prepareStatement(sql);

		PreparedStatementKey psk = new PreparedStatementKey(this, sql);
		PreparedStatementWrapper psw = (PreparedStatementWrapper) pStmtCache
				.get(psk);
		if (psw == null) {
			long startTime = System.currentTimeMillis();
			PreparedStatement ps = connection.prepareStatement(sql);
			long endTime = System.currentTimeMillis();
			psw = new PreparedStatementWrapper(this, sql, ps, endTime - startTime);
			psk.setPreparedStatementWrapper(psw);
			addStatementToCache(psk, psw);
		}
		psw.checkOutStatement();
		return psw;
	}

    /**
     *
     * @param sql
     * @param resultSetType
     * @param resultSetConcurrency
     * @return PreparedStatementWrapper
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		if (!caching)
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);

		PreparedStatementKey psk = new PreparedStatementKey(this, sql, resultSetType, resultSetConcurrency);
		PreparedStatementWrapper psw = (PreparedStatementWrapper) pStmtCache.get(psk);
		if (psw == null) {
			long startTime = System.currentTimeMillis();
			PreparedStatement ps = connection.prepareStatement(sql, resultSetType,
					resultSetConcurrency);
			long endTime = System.currentTimeMillis();
			psw = new PreparedStatementWrapper(this, sql, ps, endTime - startTime);
			psk.setPreparedStatementWrapper(psw);
			addStatementToCache(psk, psw);
		}
		psw.checkOutStatement();
		return psw;
	}

    /**
     *
     * @param sql
     * @param resultSetType
     * @param resultSetConcurrency
     * @param resultSetHoldability
     * @return PreparedStatementWrapper
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		if (!caching)
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency,
					resultSetHoldability);

		PreparedStatementKey psk = new PreparedStatementKey(this, sql, resultSetType,	resultSetConcurrency, resultSetHoldability);
		PreparedStatementWrapper psw = (PreparedStatementWrapper) pStmtCache
				.get(psk);
		if (psw == null) {
			long startTime = System.currentTimeMillis();
			PreparedStatement ps = connection.prepareStatement(sql, resultSetType,
					resultSetConcurrency, resultSetHoldability);
			long endTime = System.currentTimeMillis();
			psw = new PreparedStatementWrapper(this, sql, ps, endTime - startTime);
			psk.setPreparedStatementWrapper(psw);
			addStatementToCache(psk, psw);
		}
		psw.checkOutStatement();
		return psw;
	}

    /**
     *
     * @param sql
     * @param autoGeneratedKeys
     * @return PreparedStatementWrapper
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		PreparedStatementKey psk = new PreparedStatementKey(this, sql, autoGeneratedKeys);
		PreparedStatementWrapper psw = (PreparedStatementWrapper) pStmtCache
				.get(psk);
		if (psw == null) {
			long startTime = System.currentTimeMillis();
			PreparedStatement ps = connection.prepareStatement(sql, autoGeneratedKeys);
			long endTime = System.currentTimeMillis();
			psw = new PreparedStatementWrapper(this, sql, ps, endTime - startTime);
			psk.setPreparedStatementWrapper(psw);
			addStatementToCache(psk, psw);
		}
		psw.checkOutStatement();
		return psw;
	}

    /**
     *
     * @param sql
     * @param columnIndexes
     * @return PreparedStatementWrapper
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String sql, int columnIndexes[])
			throws SQLException {
		if (!caching)
			return connection.prepareStatement(sql, columnIndexes);

		PreparedStatementKey psk = new PreparedStatementKey(this, sql, columnIndexes);
		PreparedStatementWrapper psw = (PreparedStatementWrapper) pStmtCache.get(psk);
		if (psw == null) {
			long startTime = System.currentTimeMillis();
			PreparedStatement ps = connection.prepareStatement(sql, columnIndexes);
			long endTime = System.currentTimeMillis();
			psw = new PreparedStatementWrapper(this, sql, ps, endTime - startTime);
			psk.setPreparedStatementWrapper(psw);
			addStatementToCache(psk, psw);
		}
		psw.checkOutStatement();
		return psw;
	}

	public PreparedStatement prepareStatement(String sql, String columnNames[])
			throws SQLException {
		if (!caching)
			return connection.prepareStatement(sql, columnNames);

		PreparedStatementKey psk = new PreparedStatementKey(this, sql, columnNames);
		PreparedStatementWrapper psw = (PreparedStatementWrapper) pStmtCache.get(psk);

		if (psw == null) {
			long startTime = System.currentTimeMillis();
			PreparedStatement ps = connection.prepareStatement(sql, columnNames);
			long endTime = System.currentTimeMillis();
			psw = new PreparedStatementWrapper(this, sql, ps, endTime - startTime);
			psk.setPreparedStatementWrapper(psw);
			addStatementToCache(psk, psw);
		}
		psw.checkOutStatement();
		return psw;
	}

    /**
     *
     * @param psk
     * @param psw
     *
     */
    void addStatementToCache(PreparedStatementKey psk, PreparedStatementWrapper psw) {
		if (!caching)
			return;

		if (cacheSize >= maxCacheSize) evictStatement();
		pStmtCache.put(psk, psw);
		cacheSize++;
	}

    /**
     * evictStatement looks for the statement that is the least used and oldest.  It removes this statement from the
     * cache.
     */
    private void evictStatement() {
		Iterator keyList = pStmtCache.keySet().iterator();

        PreparedStatementKey     oldestPsk 	= null;
		PreparedStatementKey     currentPsk = null;
		PreparedStatementWrapper oldestPsw 	= null;
		PreparedStatementWrapper currentPsw = null;

        do {
			if (keyList.hasNext()) {
                currentPsk = (PreparedStatementKey) keyList.next();
            } else {
                if (oldestPsk != null) {
					pStmtCache.remove(oldestPsk);
					oldestPsw.closeStatement();
					break;
				}
			}

			if (oldestPsk == null) {
				oldestPsk = currentPsk;
			} else {
				oldestPsw  = oldestPsk.getPreparedStatementWrapper();
				currentPsw = currentPsk.getPreparedStatementWrapper();

				// Let's keep the statement that's been used the most.
				if (oldestPsw.getTimesUsed() > currentPsw.getTimesUsed()) {
					oldestPsk = currentPsk;
				} else {
					// If they've been used an equal number of times keep the one
					// most recently used.
					if (oldestPsw.getTimesUsed() == currentPsw.getTimesUsed() &&
                            oldestPsw.getLastTimeUsed() > currentPsw.getLastTimeUsed())
						oldestPsk = currentPsk;
				}
			}
		} while (true);
		
	}

	public void returnStatementToCache(PreparedStatementWrapper psw) {
		if ( psw.decrementUseCount() < 0)
			log.error("Counting error in PreparedStatementCaching System.\n" + psw.toString());
		return;
	}


    public void setTransactionIsolation(int isolationLevel) throws SQLException {
        if (ISOLATION_CACHING_ENABLED && this.isolationLevel == isolationLevel) return;
        
        connection.setTransactionIsolation(isolationLevel);
        this.isolationLevel = isolationLevel;
    }

    public int getTransactionIsolation() throws SQLException {
        return ISOLATION_CACHING_ENABLED ? isolationLevel : connection.getTransactionIsolation();
    }


    /**
     *
     * @return An integer that indicates the maximum number of statements that will be cached.
     */
    public int getMaxCacheSize() {
		return maxCacheSize;
	}



    /**
     *  All statements after this comment are delegated to the actual connection object.
     */

    public Statement createStatement() throws SQLException {
		return connection.createStatement();
	}

	public CallableStatement prepareCall(String arg0) throws SQLException {
		return connection.prepareCall(arg0);
	}

	public String nativeSQL(String arg0) throws SQLException {
		return connection.nativeSQL(arg0);
	}

	public void setAutoCommit(boolean arg0) throws SQLException {
		connection.setAutoCommit(arg0);
	}

	public boolean getAutoCommit() throws SQLException {
		return connection.getAutoCommit();
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	public void rollback() throws SQLException {
		connection.rollback();
	}

	public void close() throws SQLException {
		connection.close();
	}

	public boolean isClosed() throws SQLException {
		return connection.isClosed();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return connection.getMetaData();
	}

	public void setReadOnly(boolean arg0) throws SQLException {
		connection.setReadOnly(arg0);
	}

	public boolean isReadOnly() throws SQLException {
		return connection.isReadOnly();
	}

	public void setCatalog(String arg0) throws SQLException {
		connection.setCatalog(arg0);
	}

	public String getCatalog() throws SQLException {
		return connection.getCatalog();
	}

	public SQLWarning getWarnings() throws SQLException {
		return connection.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		connection.clearWarnings();
	}

	public Statement createStatement(int arg0, int arg1) throws SQLException {
		return connection.createStatement(arg0, arg1);
	}

	public CallableStatement prepareCall(String arg0, int arg1, int arg2)
			throws SQLException {
		return connection.prepareCall(arg0, arg1, arg2);
	}

	public Map getTypeMap() throws SQLException {
		return connection.getTypeMap();
	}

	public void setTypeMap(Map arg0) throws SQLException {
		connection.setTypeMap(arg0);
	}

	public void setHoldability(int arg0) throws SQLException {
		connection.setHoldability(arg0);
	}

	public int getHoldability() throws SQLException {
		return connection.getHoldability();
	}

	public Savepoint setSavepoint() throws SQLException {
		return connection.setSavepoint();
	}

	public Savepoint setSavepoint(String arg0) throws SQLException {
		return connection.setSavepoint(arg0);
	}

	public void rollback(Savepoint arg0) throws SQLException {
		connection.rollback();
	}

	public void releaseSavepoint(Savepoint arg0) throws SQLException {
		connection.releaseSavepoint(arg0);
	}

	public Statement createStatement(int arg0, int arg1, int arg2)
			throws SQLException {
		return connection.createStatement(arg0, arg1, arg2);
	}

	public CallableStatement prepareCall(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		return connection.prepareCall(arg0, arg1, arg2, arg3);
	}
}
