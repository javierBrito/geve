package ec.gob.educacion.dao.impl;

import java.util.List;
import ec.gob.educacion.dao.GvCajaDetalleDAO;
import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.model.geve.GvCajaDetalle;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito.
 */
@Stateless
public class GvCajaDetalleDAOImpl extends GenericDAOImpl<GvCajaDetalle, Long> implements GvCajaDetalleDAO {
    @Override
    public GvCajaDetalle buscarPorCodigo(long idCajaDetalle) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvCajaDetalle> criteria = cb.createQuery(GvCajaDetalle.class);
            Root<GvCajaDetalle> gvCajaDetalle = criteria.from(GvCajaDetalle.class);
            criteria.select(gvCajaDetalle)
                    .where(
                            cb.equal(gvCajaDetalle.get("idCajaDetalle"), idCajaDetalle)
                    	  )
                    .orderBy(cb.asc(gvCajaDetalle.get("idCajaDetalle")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvCajaDetalle> buscarListaPorParametros(GvCajaDetalle gvCajaDetalle) {
		try{
			String sql =  "select cd from GvCajaDetalle cd "
						+ "  join cd.gvCaja c "
						+ " where cd.estado=1 ";

			if (gvCajaDetalle.getGvCaja() != null && !gvCajaDetalle.getGvCaja().equals("")) {
				sql = sql + " and c.idCaja = :idCaja";
			}
			sql = sql + " order by c.observaciones asc";

			Query query = getEntityManager().createQuery(sql);
			if (gvCajaDetalle.getGvCaja() != null && !gvCajaDetalle.getGvCaja().equals("")) {
				query.setParameter("idCaja", gvCajaDetalle.getGvCaja().getIdCaja());
			}

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvCajaDetalle> buscarGvCajaDetalles() {
		try{
	        Query query = em.createQuery("select cd from GvCajaDetalle cd ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvCajaDetalle> buscarListaPorCaja(GvCaja gvCaja) {
		try{
			String sql =  "select cd from GvCajaDetalle cd "
						+ "  join cd.gvCaja c "
						+ " where cd.estado=1 ";

			if (gvCaja.getIdCaja() != 0) {
				sql = sql + " and c.idCaja = :idCaja ";
			}
			sql = sql + " order by cd.idCajaDetalle asc";

			Query query = getEntityManager().createQuery(sql);
			if (gvCaja.getIdCaja() != 0) {
				query.setParameter("idCaja", gvCaja.getIdCaja());
			}

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
}
