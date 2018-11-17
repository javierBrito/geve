package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvEstadoDocumentoDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvEstadoDocumento;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito on 18/06/2015.
 */
@Stateless
public class GvEstadoDocumentoDAOImpl extends GenericOracleAsignacionesDAOImpl<GvEstadoDocumento, Long> implements GvEstadoDocumentoDAO{
    @Override
    public GvEstadoDocumento buscarPorCodigo(long idEstadoDocumento) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvEstadoDocumento> criteria = cb.createQuery(GvEstadoDocumento.class);
            Root<GvEstadoDocumento> gvEstadoDocumento = criteria.from(GvEstadoDocumento.class);
            criteria.select(gvEstadoDocumento)
                    .where(
                            cb.equal(gvEstadoDocumento.get("idEstadoDocumento"), idEstadoDocumento)
                    	  )
                    .orderBy(cb.asc(gvEstadoDocumento.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	@Override
    public GvEstadoDocumento buscarPorNombre(String descripcion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvEstadoDocumento> criteria = cb.createQuery(GvEstadoDocumento.class);
            Root<GvEstadoDocumento> gvEstadoDocumento = criteria.from(GvEstadoDocumento.class);
            criteria.select(gvEstadoDocumento)
                    .where(
                            cb.equal(gvEstadoDocumento.get("descripcion"), descripcion)
                    	  )
                    .orderBy(cb.asc(gvEstadoDocumento.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvEstadoDocumento> buscarGvEstadoDocumentos() {
		try{
	        Query query = em.createQuery("select ed from GvEstadoDocumento ed ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvEstadoDocumento(GvEstadoDocumento gvEstadoDocumento) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvEstadoDocumento);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
