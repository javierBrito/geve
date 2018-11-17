package ec.gob.educacion.exception;

import javax.ejb.ApplicationException;
import javax.persistence.Table;
import java.lang.annotation.Annotation;

/**
 * 
 * @author j.ponce.
 */
@ApplicationException(rollback = true)
public class EducacionDAOException extends Exception {

	private static final long serialVersionUID = 8258018497802454349L;

	public EducacionDAOException(String msg) {
		super(msg);
	}

	public EducacionDAOException(Throwable ex) {
		super(ex);
	}

	public EducacionDAOException(String msg, Throwable ex) {
		super(msg, ex);
	}

	public static String format(String fmt, Object entity) {
		Annotation[] anotaciones = entity.getClass().getDeclaredAnnotations();
		String tableName = entity.getClass().getSimpleName().toUpperCase();
		for (Annotation an : anotaciones) {
			if (an.getClass().getName().equals(Table.class.getName())) {
				Table tb = (Table) an;
				tableName = tb.name();
			}
		}
		return String.format(fmt, tableName, entity.getClass()
				.getCanonicalName());
	}

}
