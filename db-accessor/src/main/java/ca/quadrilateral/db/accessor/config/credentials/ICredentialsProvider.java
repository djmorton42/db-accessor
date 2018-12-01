package ca.quadrilateral.db.accessor.config.credentials;

public interface ICredentialsProvider {
	public String getUsername();
	public String getPassword();
	
	@Override
	public int hashCode();
	
	@Override
	public boolean equals(Object obj);
}
