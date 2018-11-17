package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvTipoNominacionDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvTipoNominacion;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito on 18/06/2015.
 */
@Stateless
public class GvTipoNominacionDAOImpl extends GenericOracleAsignacionesDAOImpl<GvTipoNominacion, Long> implements GvTipoNominacionDAO{
    @Override
    public GvTipoNominacion buscarPorCodigo(long idTipoNominacion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvTipoNominacion> criteria = cb.createQuery(GvTipoNominacion.class);
            Root<GvTipoNominacion> gvTipoNominacion = criteria.from(GvTipoNominacion.class);
            criteria.select(gvTipoNominacion)
                    .where(
                            cb.equal(gvTipoNominacion.get("idTipoNominacion"), idTipoNominacion)
                    	  )
                    .orderBy(cb.asc(gvTipoNominacion.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	@Override
    public GvTipoNominacion buscarPorNombre(String descripcion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvTipoNominacion> criteria = cb.createQuery(GvTipoNominacion.class);
            Root<GvTipoNominacion> gvTipoNominacion = criteria.from(GvTipoNominacion.class);
            criteria.select(gvTipoNominacion)
                    .where(
                            cb.equal(gvTipoNominacion.get("descripcion"), descripcion)
                    	  )
                    .orderBy(cb.asc(gvTipoNominacion.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvTipoNominacion> buscarGvTipoNominaciones() {
		try{
	        Query query = em.createQuery("select tn from GvTipoNominacion tn ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvTipoNominacion(GvTipoNominacion gvTipoNominacion) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvTipoNominacion);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
