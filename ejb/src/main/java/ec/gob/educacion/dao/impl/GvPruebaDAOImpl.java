package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvPruebaDAO;
import ec.gob.educacion.model.geve.GvPrueba;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito on 18/06/2015.
 */
@Stateless
public class GvPruebaDAOImpl extends GenericOracleAsignacionesDAOImpl<GvPrueba, Long> implements GvPruebaDAO {
    @Override
    public GvPrueba buscarPorCodigo(long idPrueba) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvPrueba> criteria = cb.createQuery(GvPrueba.class);
            Root<GvPrueba> gvPrueba = criteria.from(GvPrueba.class);
            criteria.select(gvPrueba)
                    .where(
                            cb.equal(gvPrueba.get("idPrueba"), idPrueba)
                    	  )
                    .orderBy(cb.asc(gvPrueba.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	@Override
    public GvPrueba buscarPorNombre(String descripcion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvPrueba> criteria = cb.createQuery(GvPrueba.class);
            Root<GvPrueba> gvPrueba = criteria.from(GvPrueba.class);
            criteria.select(gvPrueba)
                    .where(
                            cb.equal(gvPrueba.get("descripcion"), descripcion)
                    	  )
                    .orderBy(cb.asc(gvPrueba.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvPrueba> buscarGvPruebas() {
		try{
	        Query query = em.createQuery("select m from GvPrueba m ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
}
