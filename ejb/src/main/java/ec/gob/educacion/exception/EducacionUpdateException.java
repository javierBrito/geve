package ec.gob.educacion.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author lhfalcones
 */
@ApplicationException(rollback = true)
public class EducacionUpdateException extends EducacionDAOException {

	private static final long serialVersionUID = 4950539500379930647L;
	
	public static final String MSG = "Error actualizando registro en la tabla %s.";

    public EducacionUpdateException(Object entity) {
        super(EducacionDAOException.format(MSG, entity));
    }

    public EducacionUpdateException(Object entity, Throwable ex) {
        super(EducacionDAOException.format(MSG, entity), ex);
    }
    
}
