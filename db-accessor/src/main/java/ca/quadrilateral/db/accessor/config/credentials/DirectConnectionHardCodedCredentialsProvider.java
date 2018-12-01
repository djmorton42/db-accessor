package ca.quadrilateral.db.accessor.config.credentials;

public class DirectConnectionHardCodedCredentialsProvider implements ICredentialsProvider {
    private final String username;
    private final String password;
    
    public DirectConnectionHardCodedCredentialsProvider(
            final String username,
            final String password
            ) {
        this.username = username;
        this.password = password;
    }
    
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        
        final DirectConnectionHardCodedCredentialsProvider other = (DirectConnectionHardCodedCredentialsProvider)obj;
        
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        
        return true;
    }
}
