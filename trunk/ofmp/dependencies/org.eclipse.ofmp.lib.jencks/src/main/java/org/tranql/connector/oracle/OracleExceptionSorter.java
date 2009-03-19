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

import org.tranql.connector.ExceptionSorter;

/**
 * Exception sorter for Oracle.
 *
 * @version $Revision: 1.1 $ $Date: 2008/11/13 03:25:10 $
 */
public class OracleExceptionSorter implements ExceptionSorter  {
    /**
     * Return true if the connection raising this connection is no longer usable.
     * Checks for ORA-00600 values.
     *  
     * @param e the exception to check
     * @return true if the connection should be discarded
     */
    public boolean isExceptionFatal(Exception e) {
        if (e instanceof SQLException) {
            SQLException sqlException = (SQLException) e;
            int errorCode = sqlException.getErrorCode();
            return errorCode == 600;
        }
        return false;
    }
}
