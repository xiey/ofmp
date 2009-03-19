package org.eclipse.ofmp.test.internal;

import org.eclipse.ofmp.test.TestService;

public class TestServiceImpl implements TestService {

	private DatabaseManager m_DatabaseManager;

	public void cleanUp() throws Exception {
		m_DatabaseManager.cleanUp(DatabaseManager.files);
	}

	public void cleanUp(String[] DBUnitDatasetFiles) throws Exception {
		m_DatabaseManager.cleanUp(DBUnitDatasetFiles);
	}

	public void initialize() throws Exception {
		m_DatabaseManager.initialize(DatabaseManager.files);
	}

	public void initialize(String[] DBUnitDatasetFiles) throws Exception {
		m_DatabaseManager.initialize(DBUnitDatasetFiles);
	}

	public void setDBManager(DatabaseManager aManager) {
		m_DatabaseManager = aManager;
	}
	
}
