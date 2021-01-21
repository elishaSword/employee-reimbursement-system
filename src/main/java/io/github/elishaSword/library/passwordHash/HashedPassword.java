package io.github.elishaSword.library.passwordHash;

public abstract class HashedPassword {
	private final String hashedPassword;
	
	protected HashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	
	public String getHashedPassword() {
		return this.hashedPassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashedPassword == null) ? 0 : hashedPassword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashedPassword other = (HashedPassword) obj;
		if (hashedPassword == null) {
			if (other.hashedPassword != null)
				return false;
		} else if (!hashedPassword.equals(other.hashedPassword))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HashedPassword [hashedPassword=" + hashedPassword + "]";
	}

}
