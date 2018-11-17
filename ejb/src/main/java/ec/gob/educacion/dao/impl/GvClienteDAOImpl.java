package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvClienteDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvCliente;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito on 18/06/2015.
 */
@Stateless
public class GvClienteDAOImpl extends GenericOracleAsignacionesDAOImpl<GvCliente, Long> implements GvClienteDAO{
    @Override
    public GvCliente buscarPorCodigo(long idCliente) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvCliente> criteria = cb.createQuery(GvCliente.class);
            Root<GvCliente> gvCliente = criteria.from(GvCliente.class);
            criteria.select(gvCliente)
                    .where(
                            cb.equal(gvCliente.get("idCliente"), idCliente)
                    	  )
                    .orderBy(cb.asc(gvCliente.get("nombres")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	public GvCliente buscarPorNombres(String nombres, String apellidos) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvCliente> criteria = cb.createQuery(GvCliente.class);
            Root<GvCliente> gvCliente = criteria.from(GvCliente.class);
            criteria.select(gvCliente)
                    .where(
                            cb.equal(gvCliente.get("nombres"), nombres)
                    	  )
                    .orderBy(cb.asc(gvCliente.get("nombres")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvCliente> buscarListaPorNombres(GvCliente gvCliente) {
		try{
			String sql =  "select c from GvCliente c "
						+ " where c.idEmpresa = :idEmpresa "
						+ "   and c.activo = 1 ";

			if (gvCliente != null && !gvCliente.getNombres().equals("")) {
				sql = sql + " and c.nombres like :nombres";
			}

			if (gvCliente != null && !gvCliente.getApellidos().equals("")) {
				sql = sql + " and c.apellidos like :apellidos";
			}
			sql = sql + " order by c.apellidos asc";

			Query query = getEntityManager().createQuery(sql);
			if (gvCliente != null && !gvCliente.getNombres().equals("")) {
				query.setParameter("nombres", '%' + gvCliente.getNombres() + '%');
			}
			if (gvCliente != null && !gvCliente.getApellidos().equals("")) {
				query.setParameter("apellidos", '%' + gvCliente.getApellidos() + '%');
			}
			query.setParameter("idEmpresa", gvCliente.getIdEmpresa());

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

	@Override
    public GvCliente buscarPorDni(String dni) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvCliente> criteria = cb.createQuery(GvCliente.class);
            Root<GvCliente> gvCliente = criteria.from(GvCliente.class);
            criteria.select(gvCliente)
                    .where(
                            cb.equal(gvCliente.get("dni"), dni)
                    	  )
                    .orderBy(cb.asc(gvCliente.get("dni")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	public List<GvCliente> buscarGvClientes(GvCliente gvCliente) {
		try {
			String sql =  "select c from GvCliente c "
						+ " where c.idEmpresa = :idEmpresa ";
			
			Query query = getEntityManager().createQuery(sql);
			query.setParameter("idEmpresa", gvCliente.getIdEmpresa());

			return query.getResultList();
		} catch(Exception exc) {
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvCliente(GvCliente gvCliente) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvCliente);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
