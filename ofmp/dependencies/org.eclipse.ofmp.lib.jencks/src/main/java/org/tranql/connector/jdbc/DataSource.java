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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LazyAssociatableConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;

import org.tranql.connector.DissociatableConnectionHandle;
import org.tranql.connector.DissociatableConnectionHandleFactory;
import org.tranql.connector.UserPasswordHandleFactoryRequestInfo;

/**
 * DataSource connection factory for JDBC Connections.
 *
 * @version $Revision: 1.1 $ $Date: 2008/11/13 03:25:10 $
 */
public class DataSource implements javax.sql.DataSource, javax.naming.Referenceable, javax.resource.Referenceable {
    private final ManagedConnectionFactory mcf;
    private final ConnectionManager cm;
    private final UserPasswordHandleFactoryRequestInfo containerRequestInfo;
    private Reference ref;
    private final DissociatableConnectionHandleFactory handleFactory;

    public DataSource(ManagedConnectionFactory mcf, ConnectionManager connectionManager) {
        this.mcf = mcf;
        this.cm = connectionManager;
        handleFactory = new HandleFactory(cm, mcf);
        containerRequestInfo = new UserPasswordHandleFactoryRequestInfo(handleFactory, null, null);
    }

    public Connection getConnection() throws SQLException {
        try {
            return (Connection) cm.allocateConnection(mcf, containerRequestInfo);
        } catch (ResourceException e) {
            if (e.getCause() instanceof SQLException) {
                throw (SQLException) e.getCause();
            } else {
                throw (SQLException) new SQLException().initCause(e);
            }
        }
    }

    public Connection getConnection(String user, String password) throws SQLException {
        try {
            UserPasswordHandleFactoryRequestInfo cri = new UserPasswordHandleFactoryRequestInfo(handleFactory, user, password);
            return (Connection) cm.allocateConnection(mcf, cri);
        } catch (ResourceException e) {
            if (e.getCause() instanceof SQLException) {
                throw (SQLException) e.getCause();
            } else {
                throw (SQLException) new SQLException().initCause(e);
            }
        }
    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public PrintWriter getLogWriter() throws SQLException {
        try {
            return mcf.getLogWriter();
        } catch (ResourceException e) {
            throw (SQLException) new SQLException().initCause(e);
        }
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        // throw an unchecked exception here as code should not be calling this
        throw new UnsupportedOperationException("Cannot set loginTimeout on a connection factory");
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        // throw an unchecked exception here as code should not be calling this
        throw new UnsupportedOperationException("Cannot set logWriter on a connection factory");
    }

    public Reference getReference() throws NamingException {
        return ref;
    }

    public void setReference(Reference reference) {
        this.ref = reference;
    }

    private static class HandleFactory implements DissociatableConnectionHandleFactory {

        private final LazyAssociatableConnectionManager connectionManager;
        private final ManagedConnectionFactory managedConnectionFactory;

        HandleFactory(ConnectionManager connectionManager, ManagedConnectionFactory managedConnectionFactory) {
            if (connectionManager instanceof LazyAssociatableConnectionManager) {
                this.connectionManager = (LazyAssociatableConnectionManager)connectionManager;
            } else {
                this.connectionManager = null;
            }
            this.managedConnectionFactory = managedConnectionFactory;
        }

        public DissociatableConnectionHandle newHandle(ConnectionRequestInfo connectionRequestInfo) {
            return new ConnectionHandle(connectionManager, managedConnectionFactory, connectionRequestInfo);
        }

    }
}
