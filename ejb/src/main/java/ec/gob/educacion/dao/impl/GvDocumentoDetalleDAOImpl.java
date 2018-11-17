package ec.gob.educacion.dao.impl;

import java.util.List;
import ec.gob.educacion.dao.GvDocumentoDetalleDAO;
import ec.gob.educacion.model.geve.GvDocumento;
import ec.gob.educacion.model.geve.GvDocumentoDetalle;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito.
 */
@Stateless
public class GvDocumentoDetalleDAOImpl extends GenericDAOImpl<GvDocumentoDetalle, Long> implements GvDocumentoDetalleDAO {
    @Override
    public GvDocumentoDetalle buscarPorCodigo(long idDocumentoDetalle) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvDocumentoDetalle> criteria = cb.createQuery(GvDocumentoDetalle.class);
            Root<GvDocumentoDetalle> gvDocumentoDetalle = criteria.from(GvDocumentoDetalle.class);
            criteria.select(gvDocumentoDetalle)
                    .where(
                            cb.equal(gvDocumentoDetalle.get("idDocumentoDetalle"), idDocumentoDetalle)
                    	  )
                    .orderBy(cb.asc(gvDocumentoDetalle.get("idDocumentoDetalle")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvDocumentoDetalle> buscarListaPorParametros(GvDocumentoDetalle gvDocumentoDetalle) {
		try{
			String sql =  "select dd from GvDocumentoDetalle dd "
						+ "  join dd.gvDocumento d "
						+ "  join dd.gvProducto p "
						+ " where dd.estado=1 ";

			if (gvDocumentoDetalle.getGvDocumento() != null && !gvDocumentoDetalle.getGvDocumento().equals("")) {
				sql = sql + " and d.idDocumento = :idDocumento";
			}
			if (gvDocumentoDetalle.getGvProducto() != null && !gvDocumentoDetalle.getGvProducto().equals("")) {
				sql = sql + " and p.idProducto = :idProducto";
			}
			sql = sql + " order by d.observaciones asc";

			Query query = getEntityManager().createQuery(sql);
			if (gvDocumentoDetalle.getGvDocumento() != null && !gvDocumentoDetalle.getGvDocumento().equals("")) {
				query.setParameter("idDocumento", gvDocumentoDetalle.getGvDocumento().getIdDocumento());
			}
			if (gvDocumentoDetalle.getGvProducto() != null && !gvDocumentoDetalle.getGvProducto().equals("")) {
				query.setParameter("idProducto", gvDocumentoDetalle.getGvProducto().getIdProducto());
			}

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvDocumentoDetalle> buscarGvDocumentoDetalles() {
		try{
	        Query query = em.createQuery("select dd from GvDocumentoDetalle dd ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvDocumentoDetalle> buscarListaPorDocumento(GvDocumento gvDocumento) {
		try{
			String sql =  "select dd from GvDocumentoDetalle dd "
						+ "  join dd.gvDocumento d "
						+ " where dd.estado=1 ";

			if (gvDocumento.getIdDocumento() != 0) {
				sql = sql + " and d.idDocumento = :idDocumento ";
			}
			//sql = sql + " order by dd.idDocumentoDetalle asc";

			Query query = getEntityManager().createQuery(sql);
			if (gvDocumento.getIdDocumento() != 0) {
				query.setParameter("idDocumento", gvDocumento.getIdDocumento());
			}

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
}
