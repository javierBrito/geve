package ec.gob.educacion.dao.impl;

import java.util.List;
import ec.gob.educacion.dao.GvTipoMovimientoDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvTipoMovimiento;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito on 18/06/2015.
 */
@Stateless
public class GvTipoMovimientoDAOImpl extends GenericOracleAsignacionesDAOImpl<GvTipoMovimiento, Long> implements GvTipoMovimientoDAO{
    @Override
    public GvTipoMovimiento buscarPorCodigo(long idTipoMovimiento) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvTipoMovimiento> criteria = cb.createQuery(GvTipoMovimiento.class);
            Root<GvTipoMovimiento> gvTipoMovimiento = criteria.from(GvTipoMovimiento.class);
            criteria.select(gvTipoMovimiento)
                    .where(
                            cb.equal(gvTipoMovimiento.get("idTipoMovimiento"), idTipoMovimiento)
                    	  )
                    .orderBy(cb.asc(gvTipoMovimiento.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	@Override
    public GvTipoMovimiento buscarPorNombre(String descripcion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvTipoMovimiento> criteria = cb.createQuery(GvTipoMovimiento.class);
            Root<GvTipoMovimiento> gvTipoMovimiento = criteria.from(GvTipoMovimiento.class);
            criteria.select(gvTipoMovimiento)
                    .where(
                            cb.equal(gvTipoMovimiento.get("descripcion"), descripcion)
                    	  )
                    .orderBy(cb.asc(gvTipoMovimiento.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvTipoMovimiento> buscarGvTipoMovimientos() {
		try{
	        Query query = em.createQuery("select td from GvTipoMovimiento td ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvTipoMovimiento(GvTipoMovimiento gvTipoMovimiento) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvTipoMovimiento);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
