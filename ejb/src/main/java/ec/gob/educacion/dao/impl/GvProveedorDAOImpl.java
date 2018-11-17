package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvProveedorDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvProveedor;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito on 25/10/2016.
 */
@Stateless
public class GvProveedorDAOImpl extends GenericOracleAsignacionesDAOImpl<GvProveedor, Long> implements GvProveedorDAO{
    @Override
    public GvProveedor buscarPorCodigo(long idEmpresa, long idProveedor) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvProveedor> criteria = cb.createQuery(GvProveedor.class);
            Root<GvProveedor> gvProveedor = criteria.from(GvProveedor.class);
            criteria.select(gvProveedor)
                    .where(
                            cb.and(cb.equal(gvProveedor.get("idProveedor"), idProveedor), cb.equal(gvProveedor.get("idEmpresa"), idEmpresa))
                    	  )
                    .orderBy(cb.asc(gvProveedor.get("razonSocial")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	public GvProveedor buscarPorNombres(String razonSocial, String nomContacto) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvProveedor> criteria = cb.createQuery(GvProveedor.class);
            Root<GvProveedor> gvProveedor = criteria.from(GvProveedor.class);
            criteria.select(gvProveedor)
                    .where(
                            cb.equal(gvProveedor.get("razonSocial"), razonSocial)
                    	  )
                    .orderBy(cb.asc(gvProveedor.get("razonSocial")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvProveedor> buscarListaPorNombres(GvProveedor gvProveedor) {
		try{
			String sql =  "select p from GvProveedor p "
						+ " where p.idEmpresa = :idEmpresa "
						+ "   and p.activo = 1 ";

			if (gvProveedor != null && !gvProveedor.getRazonSocial().equals("")) {
				sql = sql + " and p.razonSocial like :razonSocial";
			}

			if (gvProveedor != null && !gvProveedor.getApellidos().equals("")) {
				sql = sql + " and p.nomContacto like :nomContacto";
			}
			sql = sql + " order by p.razonSocial asc";

			Query query = getEntityManager().createQuery(sql);
			if (gvProveedor != null && !gvProveedor.getRazonSocial().equals("")) {
				query.setParameter("razonSocial", '%' + gvProveedor.getRazonSocial() + '%');
			}
			if (gvProveedor != null && !gvProveedor.getApellidos().equals("")) {
				query.setParameter("nomContacto", '%' + gvProveedor.getNomContacto() + '%');
			}
			query.setParameter("idEmpresa", gvProveedor.getIdEmpresa());

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

	@Override
    public GvProveedor buscarPorDni(String dni) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvProveedor> criteria = cb.createQuery(GvProveedor.class);
            Root<GvProveedor> gvProveedor = criteria.from(GvProveedor.class);
            criteria.select(gvProveedor)
                    .where(
                            cb.equal(gvProveedor.get("dni"), dni)
                    	  )
                    .orderBy(cb.asc(gvProveedor.get("dni")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	public List<GvProveedor> buscarGvProveedores(GvProveedor gvProveedor) {
		try {
			String sql =  "select p from GvProveedor p "
						+ " where p.idEmpresa = :idEmpresa ";
			
			Query query = getEntityManager().createQuery(sql);
			query.setParameter("idEmpresa", gvProveedor.getIdEmpresa());

			return query.getResultList();
		} catch(Exception exc) {
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvProveedor(GvProveedor gvProveedor) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvProveedor);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}