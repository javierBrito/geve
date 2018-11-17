package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvClaseDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvClase;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito on 18/06/2015.
 */
@Stateless
public class GvClaseDAOImpl extends GenericOracleAsignacionesDAOImpl<GvClase, Long> implements GvClaseDAO{
    @Override
    public GvClase buscarPorCodigo(long idClase) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvClase> criteria = cb.createQuery(GvClase.class);
            Root<GvClase> gvClase = criteria.from(GvClase.class);
            criteria.select(gvClase)
                    .where(
                            cb.equal(gvClase.get("idClase"), idClase)
                    	  )
                    .orderBy(cb.asc(gvClase.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	@Override
    public GvClase buscarPorNombre(String descripcion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvClase> criteria = cb.createQuery(GvClase.class);
            Root<GvClase> gvClase = criteria.from(GvClase.class);
            criteria.select(gvClase)
                    .where(
                            cb.equal(gvClase.get("descripcion"), descripcion)
                    	  )
                    .orderBy(cb.asc(gvClase.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvClase> buscarGvClases() {
		try{
	        Query query = em.createQuery("select c from GvClase c ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvClase(GvClase gvClase) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvClase);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
