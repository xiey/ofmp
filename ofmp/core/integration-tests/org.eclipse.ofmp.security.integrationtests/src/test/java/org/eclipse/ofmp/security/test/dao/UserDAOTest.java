package org.eclipse.ofmp.security.test.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.ofmp.security.dao.UserDAO;
import org.eclipse.ofmp.security.dom.Dealer;
import org.eclipse.ofmp.security.dom.ExternalCode;
import org.eclipse.ofmp.security.dom.Role;
import org.eclipse.ofmp.security.dom.User;
import org.eclipse.ofmp.security.dom.UserRole;
import org.eclipse.ofmp.security.dom.Dealer.DealerType;
import org.eclipse.ofmp.test.AbstractOFMPSpringDMTest;
import org.springframework.context.ApplicationContext;

public class UserDAOTest extends AbstractOFMPSpringDMTest {

	private UserDAO m_DAO;
	private ApplicationContext m_SecurityBundleAppCtx;
	
	@Override
	protected String[] getConfigLocations() {
    	return new String[]{ "security-context-test.xml" };
	}
	
	@Override
	protected String getManifestLocation() {
		return "classpath:org/eclipse/ofmp/security/test/MANIFEST.MF";
	}
    
    @Override
    protected String[] getTestBundlesNames() {
    	List<String> list  = new ArrayList<String>(Arrays.asList(super.getTestBundlesNames()));
    	
    	list.add("org.eclipse.ofmp,org.eclipse.ofmp.test,1.0.0-M1");
    	list.add("org.eclipse.ofmp,org.eclipse.ofmp.security,1.0.0-M1");
    	    	
    	return list.toArray(new String[]{});
    }
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();

		m_SecurityBundleAppCtx = (ApplicationContext) applicationContext.getBean("securityBundleAppContext");
		
		m_DAO = (UserDAO) m_SecurityBundleAppCtx.getBean("userDAO");
	}
	
    public void testSelectUsers() throws Exception
    {
        List<User> result = m_DAO.selectUsers();

        assertNotNull(result);
        assertEquals(9, result.size());
    }

    public void testSelectRoles() throws Exception
    {
        Set<Role> result = m_DAO.selectUserRoles(4);

        assertNotNull(result);
        assertEquals(4, result.size());
    }

    public void testFindUser() throws Exception
    {
        User user = m_DAO.findUser(1);

        assertNotNull(user);
        assertEquals("FX Trader", user.getName());
        assertEquals("ofmp-fx-dealer", user.getOSUser());
    }

    public void testFindUserByOSLogin() throws Exception
    {
        User user = m_DAO.findUserByOSName("ofmp-fx-dealer");

        assertNotNull(user);
        assertEquals(new Integer(1), user.getId());
        assertEquals("FX Trader", user.getName());
        assertEquals("ofmp-fx-dealer", user.getOSUser());
    }

    public void testFindDealer() throws Exception
    {
        Dealer dealer = m_DAO.findDealer(1);

        assertNotNull(dealer);
        assertEquals(new Integer(1), dealer.getId());
    }

    public void testFindDealerbyExternalId() throws Exception
    {
        ExternalCode id = new ExternalCode();

        id.setCode("TOM");
        id.setSystemId("REUTERS");

        Dealer dealer = m_DAO.findDealer(id);

        assertNotNull(dealer);
        assertEquals(new Integer(5), dealer.getId());
    }

    public void testFindRole() throws Exception
    {
        UserRole role = new UserRole();

        role.setUser(1);
        role.setRole(Role.FX_TRADER);

        UserRole rolefound = m_DAO.findRole(role);

        assertNotNull(rolefound);
        assertEquals(new Integer(1), rolefound.getUser());
        assertEquals(Role.FX_TRADER, rolefound.getRole());
    }

    public void testCreateUser() throws Exception
    {
        User user = new User();

        user.setName("Some dealer");
        user.setOSUser("osuser");

        User createdUser = m_DAO.insertUser(user);

        assertNotNull(createdUser);
        assertEquals(createdUser, user);
    }

    public void testUpdateUser() throws Exception
    {
        User user = m_DAO.findUser(1);

        user.setName("some other name");
        user.setEnabled(false);

        User updated = m_DAO.updateUser(user);
        assertNotNull(updated);

        assertEquals(updated, user);
    }

    public void testCreateRole() throws Exception
    {
        UserRole role = new UserRole();

        role.setUser(1);
        role.setRole(Role.FX_TRADER);

        UserRole createdRole = m_DAO.insertRole(role);

        assertNotNull(createdRole);
        assertEquals(createdRole, role);
    }

    public void testDeleteRole() throws Exception
    {
        UserRole role = new UserRole();

        role.setUser(1);
        role.setRole(Role.FX_TRADER);

        UserRole rolefound = m_DAO.findRole(role);

        m_DAO.deleteRole(rolefound);

        rolefound = m_DAO.findRole(role);

        assertNull(rolefound);
    }

    public void testDeleteRoles() throws Exception
    {
        m_DAO.deleteRoles(4);

        assertTrue(m_DAO.selectUserRoles(4).size() == 0);
    }

    public void testCreateDealer() throws Exception
    {
        Dealer dealer = new Dealer();

        dealer.setId(2);

        m_DAO.insertDealer(dealer);

        Dealer createdDealer = m_DAO.findDealer(2);

        assertNotNull(createdDealer);
        assertEquals(createdDealer, dealer);
    }

    public void testUpdateDealer() throws Exception
    {
        Dealer dealer = m_DAO.findDealer(1);

        Map<String, String> m = new HashMap<String, String>();
        m.put("REUTERS", "ANEW");
        dealer.setFrontOfficeIdMap(m);

        Dealer updated = m_DAO.updateDealer(dealer);
        assertNotNull(updated);

        assertEquals(updated, dealer);
    }

    public void testDeleteFrontOfficeIds() throws Exception
    {
        m_DAO.deleteFrontOfficeIds(2);
    }

    public void testInsertFrontOfficeId() throws Exception
    {
        ExternalCode ip = new ExternalCode();

        ip.setCode("XXXX");
        ip.setSystemId("YYYYY");
        ip.setEntity(1);

        m_DAO.insertFrontOfficeId(ip);
    }

    public void testSelectFrontOfficeIds() throws Exception
    {
        List<ExternalCode> selectFrontOfficeIds = m_DAO.selectFrontOfficeIds(5);
        assertNotNull(selectFrontOfficeIds);
        assertEquals(4, selectFrontOfficeIds.size());
    }

    public void testSelectDealers() throws Exception
    {
        List<Dealer> dealers = m_DAO.selectDealersByType(null);

        assertEquals(5, dealers.size());

        dealers = m_DAO.selectDealersByType(DealerType.FX);

        assertEquals(3, dealers.size());

        dealers = m_DAO.selectDealersByType(DealerType.MM);

        assertEquals(2, dealers.size());
    }

}
