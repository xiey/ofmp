/*
 * Copyright (c) 2005 - 2007, Tranql project contributors
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
package org.tranql.connector.oracle;

import java.sql.SQLException;

import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.xa.client.OracleXADataSource;
import org.tranql.connector.jdbc.AbstractXADataSourceMCF;

/**
 * ManagedConnectionFactory for connecting to a Oracle database with XA transactions.
 *
 * @version $Revision: 1.1 $ $Date: 2008/11/13 03:25:10 $
 */
public class XAMCF extends AbstractXADataSourceMCF {
    private final OracleXADataSource ds;
    private String password;

    /**
     * Default constructor for an Oracle XA DataSource.
     *
     * @throws SQLException if there was a problem instantiating an OracleXADataSource
     */
    public XAMCF() throws SQLException {
        super(new OracleXADataSource(), new OracleExceptionSorter());
        ds = (OracleXADataSource) super.xaDataSource;
    }

    public void setDatabaseName(String dbname) {
        ds.setDatabaseName(dbname);
    }

    public String getDatabaseName() {
        return ds.getDatabaseName();
    }

    public void setServiceName(String serviceName) {
        ds.setServiceName(serviceName);
    }

    public String getServiceName() {
        return ds.getServiceName();
    }

    public void setDataSourceName(String dsname) {
        ds.setDataSourceName(dsname);
    }

    public String getDataSourceName() {
        return ds.getDataSourceName();
    }

    public void setDescription(String desc) {
        ds.setDescription(desc);
    }

    public String getDescription() {
        return ds.getDescription();
    }

    public void setNetworkProtocol(String np) {
        ds.setNetworkProtocol(np);
    }

    public String getNetworkProtocol() {
        return ds.getNetworkProtocol();
    }

    public void setPassword(String pwd) {
        this.password = pwd;
        ds.setPassword(pwd);
    }

    public String getPassword() {
        return password;
    }

    public void setPortNumber(Integer pn) {
        ds.setPortNumber(pn == null ? 0 : pn.intValue());
    }

    public Integer getPortNumber() {
        return new Integer(ds.getPortNumber());
    }

    public void setServerName(String sn) {
        ds.setServerName(sn);
    }

    public String getServerName() {
        return ds.getServerName();
    }

    public void setUserName(String user) {
        ds.setUser(user);
    }

    public String getUserName() {
        return ds.getUser();
    }

    public String getDriverType() {
        return ds.getDriverType();
    }

    public void setDriverType(String s) {
        ds.setDriverType(s);
    }

    public String getTNSEntryName() {
        return ds.getTNSEntryName();
    }

    public void setTNSEntryName(String s) {
        ds.setTNSEntryName(s);
    }

    public String getURL() {
        try {
            return ds.getURL();
        } catch (SQLException e) {
            return "<unknown location>";
        }
    }

    public Integer getMaxStatements() throws SQLException {
        return new Integer(ds.getMaxStatements());
    }

    public void setMaxStatements(Integer ms) throws SQLException {
        int i = ms == null ? 0 : ms.intValue();
        ds.setMaxStatements(i);
        ds.setImplicitCachingEnabled(i != 0);
    }

    /**
     * Override the assumption that xa implementations are spec compliant since apparently Oracle xa is not
     * @return
     */
    public Boolean isCommitBeforeAutocommit() {
        return Boolean.TRUE;
    }


    /**
     * Equality is defined in terms of the serverName, portNumber and databaseName properties
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof XAMCF) {
            OracleDataSource other = ((XAMCF) obj).ds;
            return equals(ds.getServerName(), other.getServerName()) &&
                    ds.getPortNumber() == other.getPortNumber() &&
                    equals(ds.getDatabaseName(), other.getDatabaseName());
        } else {
            return false;
        }
    }

    private static boolean equals(String a, String b) {
        return a == b || a != null && a.equals(b);
    }

    public int hashCode() {
        return hashCode(ds.getServerName()) ^ ds.getPortNumber() ^ hashCode(ds.getDatabaseName());
    }

    private static int hashCode(String s) {
        return s == null ? 0 : s.hashCode();
    }

    public String toString() {
        return "XAMCF[" + getURL() + "]";
    }
}
