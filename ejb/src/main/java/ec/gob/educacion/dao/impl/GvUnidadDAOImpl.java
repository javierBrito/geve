package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvUnidadDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvUnidad;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito on 18/06/2015.
 */
@Stateless
public class GvUnidadDAOImpl extends GenericOracleAsignacionesDAOImpl<GvUnidad, Long> implements GvUnidadDAO{
    @Override
    public GvUnidad buscarPorCodigo(long idUnidad) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvUnidad> criteria = cb.createQuery(GvUnidad.class);
            Root<GvUnidad> gvUnidad = criteria.from(GvUnidad.class);
            criteria.select(gvUnidad)
                    .where(
                            cb.equal(gvUnidad.get("idUnidad"), idUnidad)
                    	  )
                    .orderBy(cb.asc(gvUnidad.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	@Override
    public GvUnidad buscarPorNombre(String descripcion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvUnidad> criteria = cb.createQuery(GvUnidad.class);
            Root<GvUnidad> gvUnidad = criteria.from(GvUnidad.class);
            criteria.select(gvUnidad)
                    .where(
                            cb.equal(gvUnidad.get("descripcion"), descripcion)
                    	  )
                    .orderBy(cb.asc(gvUnidad.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvUnidad> buscarGvUnidades() {
		try{
	        Query query = em.createQuery("select m from GvUnidad m ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvUnidad(GvUnidad gvUnidad) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvUnidad);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
