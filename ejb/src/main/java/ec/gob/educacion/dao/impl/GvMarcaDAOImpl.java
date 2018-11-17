package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvMarcaDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvMarca;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito on 18/06/2015.
 */
@Stateless
public class GvMarcaDAOImpl extends GenericOracleAsignacionesDAOImpl<GvMarca, Long> implements GvMarcaDAO{
    @Override
    public GvMarca buscarPorCodigo(long idMarca) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvMarca> criteria = cb.createQuery(GvMarca.class);
            Root<GvMarca> gvMarca = criteria.from(GvMarca.class);
            criteria.select(gvMarca)
                    .where(
                            cb.equal(gvMarca.get("idMarca"), idMarca)
                    	  )
                    .orderBy(cb.asc(gvMarca.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	@Override
    public GvMarca buscarPorNombre(String descripcion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvMarca> criteria = cb.createQuery(GvMarca.class);
            Root<GvMarca> gvMarca = criteria.from(GvMarca.class);
            criteria.select(gvMarca)
                    .where(
                            cb.equal(gvMarca.get("descripcion"), descripcion)
                    	  )
                    .orderBy(cb.asc(gvMarca.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvMarca> buscarGvMarcas() {
		try{
	        Query query = em.createQuery("select m from GvMarca m ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvMarca(GvMarca gvMarca) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvMarca);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
