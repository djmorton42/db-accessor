package ca.quadrilateral.db.accessor.config;

import java.util.Properties;

import ca.quadrilateral.db.accessor.provider.IConnectionProvider;
import ca.quadrilateral.db.accessor.provider.JNDIConnectionProvider;

public class JNDIConfiguration implements IConfiguration {
	final String writeableContextName;
	final String readOnlyContextName;
	
	public JNDIConfiguration(final String writeableContextName) {
		this(writeableContextName, null);
	}
	
	public JNDIConfiguration(final String writeableContextName, final String readOnlyContextName) {
		this.writeableContextName = writeableContextName;
		this.readOnlyContextName = readOnlyContextName;
		
		if (this.writeableContextName == null) {
			throw new ConfigurationException("writeableContextName is required in JNDI Configuration.");
		}
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((readOnlyContextName == null) ? 0 : readOnlyContextName.hashCode());
        result = prime * result + ((writeableContextName == null) ? 0 : writeableContextName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final JNDIConfiguration other = (JNDIConfiguration)obj;
        
        if (readOnlyContextName == null) {
            if (other.readOnlyContextName != null) {
                return false;
            }
        } else if (!readOnlyContextName.equals(other.readOnlyContextName)) {
            return false;
        }
        
        if (writeableContextName == null) {
            if (other.writeableContextName != null) {
                return false;
            }
        } else if (!writeableContextName.equals(other.writeableContextName)) {
            return false;
        }
        
        return true;
    }

    public static JNDIConfiguration forProperties(final Properties properties) {
		final String writeablePath = properties.getProperty("writeable-jndi-path");
		final String readOnlyPath = properties.getProperty("readonly-jndi-path");
		
		return new JNDIConfiguration(writeablePath, readOnlyPath);
	}
	
	@Override
	public IConnectionProvider connectionProvider() {
		return new JNDIConnectionProvider(writeableContextName, readOnlyContextName);
	}

}
