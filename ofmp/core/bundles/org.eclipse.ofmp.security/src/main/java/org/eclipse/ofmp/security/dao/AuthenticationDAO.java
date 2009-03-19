package org.eclipse.ofmp.security.dao;

import java.sql.SQLException;
import java.util.Collection;

import org.eclipse.ofmp.security.dom.User;

public interface AuthenticationDAO
{
    public User getUserByLogin(String aUserName) throws SQLException;

    public Collection<String> getUserRoles(Integer aUserid) throws SQLException;

}
