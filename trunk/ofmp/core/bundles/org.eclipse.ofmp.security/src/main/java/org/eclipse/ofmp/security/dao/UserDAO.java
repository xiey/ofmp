package org.eclipse.ofmp.security.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.eclipse.ofmp.security.dom.Dealer;
import org.eclipse.ofmp.security.dom.ExternalCode;
import org.eclipse.ofmp.security.dom.Role;
import org.eclipse.ofmp.security.dom.User;
import org.eclipse.ofmp.security.dom.UserRole;
import org.eclipse.ofmp.security.dom.Dealer.DealerType;

public interface UserDAO
{
    User insertUser(User user) throws SQLException;

    User updateUser(User user) throws SQLException;

    User findUser(Integer userId) throws SQLException;

    User findUserByOSName(String OSUser) throws SQLException;

    List<User> selectUsers() throws SQLException;

    UserRole insertRole(UserRole userRole) throws SQLException;

    void deleteRole(UserRole userRole) throws SQLException;

    void deleteRoles(Integer userId) throws SQLException;

    UserRole findRole(UserRole userRole) throws SQLException;

    Set<Role> selectUserRoles(Integer userId) throws SQLException;

    void insertDealer(Dealer dealer) throws SQLException;

    Dealer updateDealer(Dealer dealer) throws SQLException;

    Dealer findDealer(Integer dealerId) throws SQLException;

    Dealer findDealer(ExternalCode aId) throws SQLException;

    void deleteFrontOfficeIds(Integer aDealerId) throws SQLException;

    void insertFrontOfficeId(ExternalCode aParam) throws SQLException;

    List<ExternalCode> selectFrontOfficeIds(Integer aDealer) throws SQLException;

    List<Dealer> selectDealersByType(DealerType aDealerType) throws SQLException;
}
