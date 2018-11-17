package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvTipoDocumentoDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvTipoDocumento;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito on 18/06/2015.
 */
@Stateless
public class GvTipoDocumentoDAOImpl extends GenericOracleAsignacionesDAOImpl<GvTipoDocumento, Long> implements GvTipoDocumentoDAO{
    @Override
    public GvTipoDocumento buscarPorCodigo(long idTipoDocumento) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvTipoDocumento> criteria = cb.createQuery(GvTipoDocumento.class);
            Root<GvTipoDocumento> gvTipoDocumento = criteria.from(GvTipoDocumento.class);
            criteria.select(gvTipoDocumento)
                    .where(
                            cb.equal(gvTipoDocumento.get("idTipoDocumento"), idTipoDocumento)
                    	  )
                    .orderBy(cb.asc(gvTipoDocumento.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	@Override
    public GvTipoDocumento buscarPorNombre(String descripcion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvTipoDocumento> criteria = cb.createQuery(GvTipoDocumento.class);
            Root<GvTipoDocumento> gvTipoDocumento = criteria.from(GvTipoDocumento.class);
            criteria.select(gvTipoDocumento)
                    .where(
                            cb.equal(gvTipoDocumento.get("descripcion"), descripcion)
                    	  )
                    .orderBy(cb.asc(gvTipoDocumento.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvTipoDocumento> buscarGvTipoDocumentos() {
		try{
	        Query query = em.createQuery("select td from GvTipoDocumento td ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvTipoDocumento(GvTipoDocumento gvTipoDocumento) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvTipoDocumento);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
