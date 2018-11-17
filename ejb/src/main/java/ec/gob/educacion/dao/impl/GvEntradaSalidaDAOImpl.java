package ec.gob.educacion.dao.impl;

import java.util.List;
import ec.gob.educacion.dao.GvEntradaSalidaDAO;
import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.model.geve.GvEntradaSalida;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito.
 */
@Stateless
public class GvEntradaSalidaDAOImpl extends GenericDAOImpl<GvEntradaSalida, Long> implements GvEntradaSalidaDAO {
    @Override
    public GvEntradaSalida buscarPorCodigo(long idCajaDetalle) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvEntradaSalida> criteria = cb.createQuery(GvEntradaSalida.class);
            Root<GvEntradaSalida> gvEntradaSalida = criteria.from(GvEntradaSalida.class);
            criteria.select(gvEntradaSalida)
                    .where(
                            cb.equal(gvEntradaSalida.get("idEntradaSalida"), idCajaDetalle)
                    	  )
                    .orderBy(cb.asc(gvEntradaSalida.get("idEntradaSalida")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvEntradaSalida> buscarListaPorParametros(GvEntradaSalida gvEntradaSalida) {
		try{
			String sql =  "select es from GvEntradaSalida es "
						+ "  join es.gvCaja c "
						+ " where es.estado=1 ";

			if (gvEntradaSalida.getGvCaja() != null && !gvEntradaSalida.getGvCaja().equals("")) {
				sql = sql + " and c.idCaja = :idCaja";
			}
			sql = sql + " order by c.observaciones asc";

			Query query = getEntityManager().createQuery(sql);
			if (gvEntradaSalida.getGvCaja() != null && !gvEntradaSalida.getGvCaja().equals("")) {
				query.setParameter("idCaja", gvEntradaSalida.getGvCaja().getIdCaja());
			}

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvEntradaSalida> buscarGvEntradaSalidas() {
		try{
	        Query query = em.createQuery("select es from GvEntradaSalida es ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvEntradaSalida> buscarListaPorCaja(GvCaja gvCaja) {
		try{
			String sql =  "select es from GvEntradaSalida es "
						+ "  join es.gvCaja c "
						+ " where es.estado=1 ";

			if (gvCaja.getIdCaja() != 0) {
				sql = sql + " and c.idCaja = :idCaja ";
			}
			sql = sql + " order by es.idEntradaSalida asc";

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