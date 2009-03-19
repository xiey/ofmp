package org.eclipse.ofmp.test.internal;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.oracle.OracleConnection;
import org.dbunit.operation.DatabaseOperation;
import org.osgi.framework.BundleContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.osgi.context.BundleContextAware;

public class DatabaseManager implements BundleContextAware
{
    private final Logger log = Logger.getLogger(getClass());

	private DataSource m_DataSource;

    public final static String[] context_data_files = new String[]
    { "dataset.counterparties.xml", "dataset.users.xml", "dataset.currency.xml",
            "dataset.businessday.xml" };

    public final static String[] files = new String[]
    { "dataset.counterparties.xml", "dataset.users.xml", "dataset.currency.xml",
            "dataset.currencyrates.xml", "dataset.history.xml", "dataset.businessday.xml",
            "dataset.deal.xml", "dataset.backoffice.xml", "dataset.profitloss.xml",
            "dataset.portfolio.xml" };

	public static final String DATABASE_SCHEMA = "VT_OWNER";

	private BundleContext m_BundleContext;

	private static String s_DatabaseDriverClassName = "oracle.jdbc.driver.OracleDriver";
	private static String s_DatabaseUrl = "jdbc:oracle:thin:@localhost:1521:XE";
	private static String s_DatabaseUser = "VT_USER";
	private static String s_DatabasePassword = "VT_USER";

	public void init() throws Exception {
		
		if (m_DataSource == null)
			m_DataSource = getDataSource();
	}
    
	private DataSource getDataSource() {
		
		DriverManagerDataSource ds = new DriverManagerDataSource();
		
		ds.setDriverClassName(s_DatabaseDriverClassName);
		ds.setUrl(s_DatabaseUrl);
		ds.setUsername(s_DatabaseUser);
		ds.setPassword(s_DatabasePassword);
		
		return ds;
	}

	public static void main(String[] args) throws Exception {
		DatabaseManager dbManager = new DatabaseManager();
		
		dbManager.init();
		
		System.out.println("Initializing database context data.");
		dbManager.cleanUp(files);
		dbManager.initialize(context_data_files);
		System.out.println("Done.");
		
	}
	
	public void cleanUp(String[] DBUnitDatasetFiles) throws Exception
    {
		Connection connection = m_DataSource.getConnection();
		
        IDatabaseConnection DBUnitConnection = new OracleConnection(connection, DATABASE_SCHEMA);

        for (int n = DBUnitDatasetFiles.length - 1; n >= 0; n--)
        {
            log.info("Clean up with " + DBUnitDatasetFiles[n]);
        	DatabaseOperation.DELETE_ALL.execute(DBUnitConnection, new XmlDataSet(getDatasetFile(DBUnitDatasetFiles[n])));
        }

        connection.commit();
        connection.close();
    }

    public void initialize(String[] DBUnitDatasetFiles) throws Exception
    {
		Connection connection = m_DataSource.getConnection();

    	IDatabaseConnection DBUnitConnection = new OracleConnection(connection, DATABASE_SCHEMA);

        for (String file : DBUnitDatasetFiles)
        {
            log.info("Initialize with " + file);
            DatabaseOperation.INSERT.execute(DBUnitConnection, new XmlDataSet(getDatasetFile(file)));
        }

        connection.commit();
        connection.close();
    }

    private InputStream getDatasetFile(String file) throws IOException
    {
    	String filePath = "testdata/" + file;

    	if (m_BundleContext != null)
    		return m_BundleContext.getBundle().getResource(filePath).openStream();
    	else
    		return new FileSystemResource(filePath).getInputStream();
    }

    public void setDataSource(DataSource aDataSource) {
		m_DataSource = aDataSource;
	}

	public void setBundleContext(BundleContext aBundleContext) {
		m_BundleContext = aBundleContext;
	}
}

