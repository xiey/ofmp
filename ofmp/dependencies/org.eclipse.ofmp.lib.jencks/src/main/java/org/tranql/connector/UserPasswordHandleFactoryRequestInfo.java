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
package org.tranql.connector;



/**
 *
 *
 * @version $Revision: 1.1 $ $Date: 2008/11/13 03:25:10 $
 */
public class UserPasswordHandleFactoryRequestInfo extends ConnectionHandleFactoryRequestInfo {
    private final String user;
    private final String password;

    public UserPasswordHandleFactoryRequestInfo(DissociatableConnectionHandleFactory connectionHandleFactory, String user, String password) {
        super(connectionHandleFactory);
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public boolean equals(Object obj) {
        if (obj instanceof UserPasswordHandleFactoryRequestInfo) {
            UserPasswordHandleFactoryRequestInfo other = (UserPasswordHandleFactoryRequestInfo) obj;
            return (user == null? other.user == null: user.equals(other.user)) &&
                    (password == null? other.password == null: password.equals(other.password));
        }
        return false;
    }

    public int hashCode() {
        return (user == null ? 0 : user.hashCode()) ^ (password == null ? 0 : password.hashCode());
    }

    public String toString() {
        return "DataSourceRequestInfo[" + user + "]";
    }
}
