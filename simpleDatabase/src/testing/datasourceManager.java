package testing;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

//import simpleDatabase.testing.DataSourceManager;

public class datasourceManager {

	private static BasicDataSource singletonDS = null;

	public synchronized static DataSource getDataSource() throws IOException {
		if (singletonDS == null) {
			System.out.println("Creating Datasource " + new Date());
			Properties props = getPropertiesFromClasspath();

			String url = props.getProperty("url");
			if (url == null || url.isEmpty()) {
				throw new RuntimeException("property 'url' not found in configuration file");
			}

			String id = props.getProperty("id");
			if (id == null || id.isEmpty()) {
				throw new RuntimeException("property 'id' not found in configuration file");
			}

			String passwd = props.getProperty("passwd");
			if (passwd == null || passwd.isEmpty()) {
				throw new RuntimeException("property 'passwd' not found in configuration file");
			}

			singletonDS = new BasicDataSource();
			singletonDS.setUrl(url);
			singletonDS.setUsername(id);
			singletonDS.setPassword(passwd);
			singletonDS.setMaxWaitMillis(5000);
		}
		return singletonDS;
	}

	private static final String propFileName = "testing/dbconfig.properties";

	public static Properties getPropertiesFromClasspath() throws IOException {
		// Load dbconfig.properties from the classpath
		Properties props = new Properties();
		InputStream inputStream = datasourceManager.class.getClassLoader().getResourceAsStream(propFileName);

		if (inputStream == null) {
			throw new RuntimeException("property file '" + propFileName + "' not found in the classpath");
		}

		props.load(inputStream);

		return props;
	}

	public static void main(String args[]) {
		long startTime = System.currentTimeMillis();
		try {
			DataSource ds = datasourceManager.getDataSource();
			for(int idx = 0; idx < 100; idx++) {				
				Connection con = ds.getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select count(*) from simple_database.songs");
				if (rs.next()) {
					System.out.println(idx + " Count: " + rs.getInt(1));
				}
				stmt.close();
				con.close();
			}
			System.out.println("Finished " + (System.currentTimeMillis() - startTime));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}