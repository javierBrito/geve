package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvCajaDAO;
import ec.gob.educacion.model.geve.GvCaja;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito.
 */
@Stateless
public class GvCajaDAOImpl extends GenericDAOImpl<GvCaja, Long> implements GvCajaDAO {
    @Override
    public GvCaja buscarPorCodigo(long idCaja) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvCaja> criteria = cb.createQuery(GvCaja.class);
            Root<GvCaja> gvCaja = criteria.from(GvCaja.class);
            criteria.select(gvCaja)
                    .where(
                            cb.equal(gvCaja.get("idCaja"), idCaja)
                    	  )
                    .orderBy(cb.asc(gvCaja.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvCaja> buscarListaPorParametros(GvCaja gvCaja) {
		try{
			String sql =  "select c from GvCaja c "
						+ " where c.idEmpresa = :idEmpresa "
						+ "   and c.estado = 1 ";

			if (gvCaja != null && !gvCaja.getNomUsuarioRegistra().equals("")) {
				sql = sql + " and c.nomUsuarioRegistra = :nomUsuarioRegistra";
			}

			if (gvCaja != null && !gvCaja.getFechaRegistra().equals("")) {
				sql = sql + " and c.fechaRegistra between :fechaRegistra and :fechaRegistra ";
			}
			sql = sql + " order by c.fechaRegistra asc";

			Query query = getEntityManager().createQuery(sql);
			if (gvCaja != null && !gvCaja.getNomUsuarioRegistra().equals("")) {
				query.setParameter("nomUsuarioRegistra", gvCaja.getNomUsuarioRegistra());
			}
			if (gvCaja != null && !gvCaja.getFechaRegistra().equals("")) {
				query.setParameter("fechaRegistra", gvCaja.getFechaRegistra());
			}
			query.setParameter("idEmpresa", gvCaja.getIdEmpresa());

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

	@SuppressWarnings("unchecked")
	@Override
    public List<GvCaja> buscarUltimoRegistro(GvCaja gvCaja) {
		try{
			String sql =  "select c from GvCaja c "
						+ " where c.idEmpresa = :idEmpresa "
						+ "   and c.estado = 1 ";

			if (gvCaja != null && !gvCaja.getNomUsuarioRegistra().equals("")) {
				sql = sql + " and c.nomUsuarioRegistra = :nomUsuarioRegistra";
			}
			sql = sql + " order by c.idCaja desc";

			Query query = getEntityManager().createQuery(sql);
			if (gvCaja != null && !gvCaja.getNomUsuarioRegistra().equals("")) {
				query.setParameter("nomUsuarioRegistra", gvCaja.getNomUsuarioRegistra());
			}
			query.setParameter("idEmpresa", gvCaja.getIdEmpresa());

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvCaja> buscarGvCajas(GvCaja gvCaja) {
		try {
			String sql =  "select c from GvCaja c "
						+ " where c.idEmpresa = :idEmpresa ";

			Query query = getEntityManager().createQuery(sql);
			query.setParameter("idEmpresa", gvCaja.getIdEmpresa());
	        
	        return query.getResultList();
		} catch(Exception exc) {
            exc.printStackTrace();
            return null;
		}
    }
}