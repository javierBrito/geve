package ec.gob.educacion.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class EmailValidator.
 */
public class EmailValidator {
	/** The email. */
	private String email;

	/**
	 * Instantiates a new email validator.
	 * @param email the email
	 */
	public EmailValidator(String email) {
		super();
		this.email = email;
	}

	/**
	 * Checks if is email valid.
	 * @return true, if is email valid
	 */
	public boolean isEmailValid() {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		Pattern pattern = Pattern.compile(ePattern);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}