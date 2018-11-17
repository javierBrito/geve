package ec.gob.educacion.dao.impl;

import java.util.List;
import ec.gob.educacion.dao.GvDocumentoDAO;
import ec.gob.educacion.model.geve.GvDocumento;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito.
 */
@Stateless
public class GvDocumentoDAOImpl extends GenericDAOImpl<GvDocumento, Long> implements GvDocumentoDAO {
    @Override
    public GvDocumento buscarPorCodigo(long idDocumento) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvDocumento> criteria = cb.createQuery(GvDocumento.class);
            Root<GvDocumento> gvDocumento = criteria.from(GvDocumento.class);
            criteria.select(gvDocumento)
                    .where(
                            cb.equal(gvDocumento.get("idDocumento"), idDocumento)
                    	  )
                    .orderBy(cb.asc(gvDocumento.get("observaciones")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvDocumento> buscarListaPorParametros(GvDocumento gvDocumento) {
		try{
			String sql =  "select d from GvDocumento d "
						+ "  join d.gvCliente c "
						+ "  join d.gvTipoDocumento td "
						+ "  join d.gvEstadoDocumento ed "
						+ " where d.idEmpresa = :idEmpresa "
						+ "   and d.estado = 1 ";

			if (gvDocumento.getGvCliente() != null && !gvDocumento.getGvCliente().equals("")) {
				sql = sql + " and c.idCliente = :idCliente";
			}
			if (gvDocumento.getGvTipoDocumento() != null && !gvDocumento.getGvTipoDocumento().equals("")) {
				sql = sql + " and td.idTipoDocumento = :idTipoDocumento";
			}
			if (gvDocumento.getGvEstadoDocumento() != null && !gvDocumento.getGvEstadoDocumento().equals("")) {
				sql = sql + " and ed.idEstadoDocumento = :idEstadoDocumento";
			}
			if (gvDocumento != null && !gvDocumento.getNomUsuarioRegistra().equals("")) {
				sql = sql + " and d.nomUsuarioRegistra = :nomUsuarioRegistra";
			}
			if (gvDocumento != null && !gvDocumento.getFechaRegistra().equals("")) {
				sql = sql + " and d.fechaRegistra = :fechaRegistra";
			}
			sql = sql + " order by d.observaciones asc";

			Query query = getEntityManager().createQuery(sql);
			if (gvDocumento.getGvCliente() != null && !gvDocumento.getGvCliente().equals("")) {
				query.setParameter("idCliente", gvDocumento.getGvCliente().getIdCliente());
			}
			if (gvDocumento.getGvTipoDocumento() != null && !gvDocumento.getGvTipoDocumento().equals("")) {
				query.setParameter("idTipoDocumento", gvDocumento.getGvTipoDocumento().getIdTipoDocumento());
			}
			if (gvDocumento.getGvEstadoDocumento() != null && !gvDocumento.getGvEstadoDocumento().equals("")) {
				query.setParameter("idEstadoDocumento", gvDocumento.getGvEstadoDocumento().getIdEstadoDocumento());
			}
			if (gvDocumento != null && !gvDocumento.getNomUsuarioRegistra().equals("")) {
				query.setParameter("nomUsuarioRegistra", gvDocumento.getNomUsuarioRegistra());
			}
			if (gvDocumento != null && !gvDocumento.getFechaRegistra().equals("")) {
				query.setParameter("fechaRegistra", gvDocumento.getFechaRegistra());
			}
			query.setParameter("idEmpresa", gvDocumento.getIdEmpresa());

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvDocumento> buscarGvDocumentos(GvDocumento gvDocumento) {
		try{
			String sql =  "select d from GvDocumento d "
						+ " where d.idEmpresa = :idEmpresa ";
			Query query = getEntityManager().createQuery(sql);
			query.setParameter("idEmpresa", gvDocumento.getIdEmpresa());

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
}