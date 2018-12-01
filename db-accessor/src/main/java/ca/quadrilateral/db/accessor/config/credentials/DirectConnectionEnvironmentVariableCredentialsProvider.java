package ca.quadrilateral.db.accessor.config.credentials;

public class DirectConnectionEnvironmentVariableCredentialsProvider implements ICredentialsProvider {
    private final String usernameVariableName;
    private final String passwordVariableName;

    public DirectConnectionEnvironmentVariableCredentialsProvider(
            final String usernameVariableName,
            final String passwordVariableName) {
        this.usernameVariableName = usernameVariableName;
        this.passwordVariableName = passwordVariableName;
    }

    @Override
    public String getUsername() {
        return System.getenv(usernameVariableName);
    }

    @Override
    public String getPassword() {
        return System.getenv(passwordVariableName);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((passwordVariableName == null) ? 0 : passwordVariableName.hashCode());
        result = prime * result + ((usernameVariableName == null) ? 0 : usernameVariableName.hashCode());
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

        final DirectConnectionEnvironmentVariableCredentialsProvider other = 
                (DirectConnectionEnvironmentVariableCredentialsProvider)obj;

        if (passwordVariableName == null) {
            if (other.passwordVariableName != null) {
                return false;
            }
        } else if (!passwordVariableName.equals(other.passwordVariableName)) {
            return false;
        }

        if (usernameVariableName == null) {
            if (other.usernameVariableName != null) {
                return false;
            }
        } else if (!usernameVariableName.equals(other.usernameVariableName)) {
            return false;
        }

        return true;
    }
}
