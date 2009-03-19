package org.eclipse.ofmp.security.dao.internal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.ofmp.security.dao.UserDAO;
import org.eclipse.ofmp.security.dom.Dealer;
import org.eclipse.ofmp.security.dom.ExternalCode;
import org.eclipse.ofmp.security.dom.Role;
import org.eclipse.ofmp.security.dom.User;
import org.eclipse.ofmp.security.dom.UserRole;
import org.eclipse.ofmp.security.dom.Dealer.DealerType;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UserDAOImpl extends SqlMapClientDaoSupport implements UserDAO
{
    public User insertUser(User aUser) throws SQLException
    {
        Integer pk = (Integer) getSqlMapClient().insert("User.insertUser", aUser);
        aUser.setId(pk);

        return aUser;
    }

    public User updateUser(User aUser) throws SQLException
    {
        getSqlMapClient().update("User.updateUser", aUser);

        return findUser(aUser.getId());
    }

    public User findUser(Integer userId) throws SQLException
    {
        return (User) getSqlMapClient().queryForObject("User.selectUsers", userId);
    }

    public User findUserByOSName(String OSUser) throws SQLException
    {
        return (User) getSqlMapClient().queryForObject("User.selectUserByOSUser", OSUser);
    }

    @SuppressWarnings("unchecked")
    public List<User> selectUsers() throws SQLException
    {
        return getSqlMapClient().queryForList("User.selectUsers");
    }

    public UserRole insertRole(UserRole userRole) throws SQLException
    {
        Integer pk = (Integer) getSqlMapClient().insert("User.insertRole", userRole);
        userRole.setId(pk);

        return userRole;
    }

    public void deleteRole(UserRole userRole) throws SQLException
    {
        deleteRoles(userRole.getId());
    }

    public void deleteRoles(Integer userId) throws SQLException
    {
        getSqlMapClient().delete("User.deleteRoles", userId);
    }

    @SuppressWarnings("unchecked")
    public Set<Role> selectUserRoles(Integer userId) throws SQLException
    {
        Set<Role> result = new HashSet<Role>();

        List<UserRole> list = getSqlMapClient().queryForList("User.selectUserRoles", userId);

        for (UserRole userRole : list)
            result.add(userRole.getRole());

        return result;
    }

    public void insertDealer(Dealer aDealer) throws SQLException
    {
        getSqlMapClient().insert("User.insertDealer", aDealer);
    }

    public Dealer updateDealer(Dealer aDealer) throws SQLException
    {
        getSqlMapClient().update("User.updateDealer", aDealer);

        return findDealer(aDealer.getId());
    }

    public Dealer findDealer(Integer dealerId) throws SQLException
    {
        return (Dealer) getSqlMapClient().queryForObject("User.selectDealers", dealerId);
    }

    public Dealer findDealer(ExternalCode aId) throws SQLException
    {
        return (Dealer) getSqlMapClient().queryForObject("User.selectDealerByExternalId", aId);
    }

    public UserRole findRole(UserRole userRole) throws SQLException
    {
        return (UserRole) getSqlMapClient().queryForObject("User.selectRoles", userRole);
    }

    @SuppressWarnings("unchecked")
    public List<Dealer> selectDealersByType(DealerType aDealerType) throws SQLException
    {
        List<Dealer> result = new ArrayList<Dealer>();

        if (aDealerType != null)
            result = getSqlMapClient().queryForList("User.selectDealersByType", aDealerType.getRole().toString());
        else
            result = getSqlMapClient().queryForList("User.selectDealersByType");

        return result;
    }

    public void deleteFrontOfficeIds(Integer aDealerId) throws SQLException
    {
        getSqlMapClient().delete("User.deleteFrontOfficeIds", aDealerId);
    }

    public void insertFrontOfficeId(ExternalCode aParameter) throws SQLException
    {
        getSqlMapClient().insert("User.insertFrontOfficeId", aParameter);
    }

    @SuppressWarnings("unchecked")
    public List<ExternalCode> selectFrontOfficeIds(Integer aDealer) throws SQLException
    {
        return getSqlMapClient().queryForList("User.selectFrontOfficeIds", aDealer);
    }

}
