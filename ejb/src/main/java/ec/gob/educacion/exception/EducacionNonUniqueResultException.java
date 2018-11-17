package ec.gob.educacion.exception;

import javax.ejb.ApplicationException;

/**
 * 
 * @author j.ponce.
 */
@ApplicationException(rollback = false)
public class EducacionNonUniqueResultException extends Exception {

	private static final long serialVersionUID = 971044079612567355L;

	public EducacionNonUniqueResultException() {
		super();
	}

	public EducacionNonUniqueResultException(String message, Throwable cause) {
		super(message, cause);
	}

	public EducacionNonUniqueResultException(String message) {
		super(message);
	}

	public EducacionNonUniqueResultException(Throwable cause) {
		super(cause);
	}
}
