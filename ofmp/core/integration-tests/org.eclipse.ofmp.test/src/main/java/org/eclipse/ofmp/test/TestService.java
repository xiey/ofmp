package org.eclipse.ofmp.test;

public interface TestService {
	
	/**
	 * Clean up existing database entities using default DBUnit data sets  
	 * @throws Exception 
	 */
	public void cleanUp() throws Exception;

	/**
	 * Clean up existing database entities using provided DBUnit data sets  
	 * @throws Exception 
	 */
	public void cleanUp(String[] DBUnitDatasetFiles) throws Exception;

	/**
	 * Load existing database entities with predefined data using default DBUnit data sets  
	 * @throws Exception 
	 */
	public void initialize() throws Exception;

	/**
	 * Load existing database entities with predefined data using provided DBUnit data sets  
	 * @throws Exception 
	 */
	public void initialize(String[] DBUnitDatasetFiles) throws Exception;

}
