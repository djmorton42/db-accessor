package ca.quadrilateral.db.accessor.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class Configuration {
	public static IConfiguration loadConfiguration() {
		try (final InputStream inputStream = 
				Configuration.class.getClassLoader().getResourceAsStream("db-accessor.properties")) {
			
			final Properties properties = new Properties();
			properties.load(inputStream);
			
			final String connectionMode = properties.getProperty("mode");
			
			if ("DIRECT".equals(connectionMode)) {
				return DirectConnectionConfiguration.forProperties(properties);
			} else if ("JNDI".equals(connectionMode)) {
				return JNDIConfiguration.forProperties(properties);
			} else {
				throw new ConfigurationException("Invalid configuration type: " + connectionMode);
			}			
		} catch (final IOException e) {
			throw new ConfigurationException("Error loading db accessor configuration", e);
		}		
	}
}