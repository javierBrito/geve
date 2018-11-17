package ec.gob.educacion.model.audit;

import org.hibernate.envers.RevisionListener;

public class AuditListener implements RevisionListener {
	
	@Override
	public void newRevision(Object revisionEntity) {
		RevEntity revEntity = (RevEntity) revisionEntity;
		revEntity.setUsuario("GEVE");
	}

}
