package ec.gob.educacion.exception;

import javax.ejb.ApplicationException;

/**
 * 
 * @author j.ponce.
 */
@ApplicationException(rollback = true)
public class EducacionPersistException extends EducacionDAOException {

	private static final long serialVersionUID = -8715590066147130737L;
	
	public static final String MSG = "Error insertando registro en la tabla %s, entidad %s.";

	public EducacionPersistException(Object entity) {
		super(EducacionDAOException.format(MSG, entity));
	}

	public EducacionPersistException(Object entity, Throwable ex) {
		super(EducacionDAOException.format(MSG, entity), ex);
		
	}
}
