package io.github.elishaSword.library.passwordHash;

/**
 * 
 * transforms an unhashed string into a hashed one, marked safe for inclusion in the database.
 * 
 * i recommend making a default-visibility concrete implementation and a public factory
 *
 */
public interface PasswordHasher {
	/**
	 * 
	 * @param unhashedPassword
	 * @return a password marked safe for permanent storage
	 */
	public HashedPassword hash(String unhashedPassword);
}
