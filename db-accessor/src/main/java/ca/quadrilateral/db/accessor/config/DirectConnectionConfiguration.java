package ca.quadrilateral.db.accessor.config;

import java.util.Properties;

import ca.quadrilateral.db.accessor.config.credentials.DirectConnectionCredentialsProvider;
import ca.quadrilateral.db.accessor.config.credentials.ICredentialsProvider;
import ca.quadrilateral.db.accessor.provider.DirectConnectionProvider;
import ca.quadrilateral.db.accessor.provider.IConnectionProvider;

public class DirectConnectionConfiguration implements IConfiguration {
	final String driver;
	final String writeableConnectionString;
	final String readOnlyConnectionString;
	final ICredentialsProvider credentialsProvider;
	
	public DirectConnectionConfiguration(
	        final String driver, 
	        final ICredentialsProvider credentialsProvider, 
	        final String writeableConnectionString) {
		this(driver, credentialsProvider, writeableConnectionString, null);
	}
	
	public DirectConnectionConfiguration(
			final String driver, 
			final ICredentialsProvider credentialsProvider,
			final String writeableConnectionString, 
			final String readOnlyConnectionString) {
		
		this.driver = driver;
		this.credentialsProvider = credentialsProvider;
		this.writeableConnectionString = writeableConnectionString;
		this.readOnlyConnectionString = readOnlyConnectionString;

		if (this.driver == null) {
			throw new ConfigurationException("driver property is requird in Direct Connection Configuration");
		}
		if (this.writeableConnectionString == null) {
			throw new ConfigurationException("writeableConnectionString is required in Direct Connection Configuration");
		}
		if (this.credentialsProvider == null) {
		    throw new ConfigurationException("credentialsProvider is required");
		}
	}
	
	public static DirectConnectionConfiguration forProperties(final Properties properties) {
		final ICredentialsProvider credentialsProvider = DirectConnectionCredentialsProvider.forProperties(properties);
		
		return new DirectConnectionConfiguration(
				properties.getProperty("driver"),
				credentialsProvider,
				properties.getProperty("writeable-connection-string"),
				properties.getProperty("readonly-connection-string")
		);
	}
	
	@Override
	public IConnectionProvider connectionProvider() {
		return new DirectConnectionProvider(driver, credentialsProvider, writeableConnectionString, readOnlyConnectionString);
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((credentialsProvider == null) ? 0 : credentialsProvider.hashCode());
        result = prime * result + ((driver == null) ? 0 : driver.hashCode());
        result = prime * result + ((readOnlyConnectionString == null) ? 0 : readOnlyConnectionString.hashCode());
        result = prime * result + ((writeableConnectionString == null) ? 0 : writeableConnectionString.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final DirectConnectionConfiguration other = (DirectConnectionConfiguration)obj;
        
        if (credentialsProvider == null) {
            if (other.credentialsProvider != null) {
                return false;
            }
        } else if (!credentialsProvider.equals(other.credentialsProvider)) {
            return false;
        }
        
        if (driver == null) {
            if (other.driver != null) {
                return false;
            }
        } else if (!driver.equals(other.driver)) {
            return false;
        }
        
        if (readOnlyConnectionString == null) {
            if (other.readOnlyConnectionString != null) {
                return false;
            }
        } else if (!readOnlyConnectionString.equals(other.readOnlyConnectionString)) {
            return false;
        }
        
        if (writeableConnectionString == null) {
            if (other.writeableConnectionString != null) {
                return false;
            }
        } else if (!writeableConnectionString.equals(other.writeableConnectionString)) {
            return false;
        }
        
        return true;
    }
}
