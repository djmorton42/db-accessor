package ca.quadrilateral.db.accessor.config.credentials;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.quadrilateral.db.accessor.config.ConfigurationException;

public class DirectConnectionCredentialsProvider {
    private static final Logger logger = LoggerFactory.getLogger(DirectConnectionCredentialsProvider.class);
    
    private enum ValidCredentialsPolicies {
        HARD_CODED,
        ENVIRONMENT_VARIABLE,
        SYSTEM_PROPERTY
    }

    public static ICredentialsProvider forProperties(final Properties properties) {
        final String policy = properties.getProperty("credentials-policy"); 
                
        final ValidCredentialsPolicies credentialsPolicy = ValidCredentialsPolicies.valueOf(policy);
        
        logger.info("[DB Accessor] Using credentials policy: " + credentialsPolicy);

        if (credentialsPolicy == null) {
            throw new ConfigurationException(policy + " is an invalid credentials policy");
        }
        
        final String usernameProperty = getUsernameProperty(properties);
        final String passwordProperty = getPasswordProperty(properties);
        
        switch(credentialsPolicy) {
            case HARD_CODED:
                return new DirectConnectionHardCodedCredentialsProvider(
                        usernameProperty,
                        passwordProperty
                );
            case ENVIRONMENT_VARIABLE:
                return new DirectConnectionEnvironmentVariableCredentialsProvider(
                        usernameProperty,
                        passwordProperty
                );
            case SYSTEM_PROPERTY:
                return new DirectConnectionSystemPropertyCredentialsProvider(
                        usernameProperty,
                        passwordProperty
                );
        }
        
        throw new ConfigurationException("Unable to construct Credentials Policy");
    }
    
    private static String getUsernameProperty(final Properties properties) {
        final String usernameProperty = properties.getProperty("username");
        
        if (usernameProperty == null) {
            throw new ConfigurationException(
                    "username property must be populated with a username, " +
                    "an environment variable or a system property depending on the chosen policy."
            );
        }
        
        return usernameProperty;
    }
    
    private static String getPasswordProperty(final Properties properties) {
        final String passwordProperty = properties.getProperty("password");

        if (passwordProperty == null) {
            throw new ConfigurationException(
                    "password property must be populated with a password, " +
                    "an environment variable or a system property depending on the chosen policy."
            );
        }
        
        return passwordProperty;
    }
}
