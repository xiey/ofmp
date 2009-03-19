package org.eclipse.ofmp.security.dao.internal;

import java.sql.SQLException;
import java.util.Collection;

import org.eclipse.ofmp.security.dao.AuthenticationDAO;
import org.eclipse.ofmp.security.dom.User;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class AuthenticationDAOImpl extends SqlMapClientDaoSupport implements AuthenticationDAO
{
    public User getUserByLogin(String aUserName) throws SQLException
    {
        return (User) getSqlMapClient().queryForObject("Auth.findUserByLogin", aUserName);
    }

    @SuppressWarnings("unchecked")
    public Collection<String> getUserRoles(Integer aUserid) throws SQLException
    {
        return getSqlMapClient().queryForList("Auth.selectUserRoles", aUserid);
    }

}
