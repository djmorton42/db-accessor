package ca.quadrilateral.db.accessor.config.credentials;

public class DirectConnectionSystemPropertyCredentialsProvider implements ICredentialsProvider {
    private final String usernameSystemProperty;
    private final String passwordSystemProperty;

    public DirectConnectionSystemPropertyCredentialsProvider(
            final String usernameSystemProperty,
            final String passwordSystemProperty) {
        this.usernameSystemProperty = usernameSystemProperty;
        this.passwordSystemProperty = passwordSystemProperty;
    }

    @Override
    public String getUsername() {
        return System.getProperty(usernameSystemProperty);
    }

    @Override
    public String getPassword() {
        return System.getProperty(passwordSystemProperty);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((passwordSystemProperty == null) ? 0 : passwordSystemProperty.hashCode());
        result = prime * result + ((usernameSystemProperty == null) ? 0 : usernameSystemProperty.hashCode());
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

        final DirectConnectionSystemPropertyCredentialsProvider other = 
                (DirectConnectionSystemPropertyCredentialsProvider)obj;

        if (passwordSystemProperty == null) {
            if (other.passwordSystemProperty != null) {
                return false;
            }
        } else if (!passwordSystemProperty.equals(other.passwordSystemProperty)) {
            return false;
        }

        if (usernameSystemProperty == null) {
            if (other.usernameSystemProperty != null) {
                return false;
            }
        } else if (!usernameSystemProperty.equals(other.usernameSystemProperty)) {
            return false;
        }

        return true;
    }
}
