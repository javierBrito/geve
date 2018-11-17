package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvProductoDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvProducto;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito.
 */
@Stateless
public class GvProductoDAOImpl extends GenericOracleAsignacionesDAOImpl<GvProducto, Long> implements GvProductoDAO {
    @Override
    public GvProducto buscarPorCodigo(long idEmpresa, long idProducto) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvProducto> criteria = cb.createQuery(GvProducto.class);
            Root<GvProducto> gvProducto = criteria.from(GvProducto.class);
            
            criteria.select(gvProducto).where(cb.and(cb.equal(gvProducto.get("idProducto"), idProducto), cb.equal(gvProducto.get("idEmpresa"), idEmpresa)));
            
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	public GvProducto buscarPorDescripcion(String descripcion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvProducto> criteria = cb.createQuery(GvProducto.class);
            Root<GvProducto> gvProducto = criteria.from(GvProducto.class);
            criteria.select(gvProducto)
                    .where(
                            cb.equal(gvProducto.get("descripcion"), descripcion)
                    	  );
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvProducto> buscarListaPorDescripcion(GvProducto gvProducto) {
		try{
			String sql =  "select p from GvProducto p "
						+ " where p.idEmpresa = :idEmpresa "
						+ "   and p.estado = 1 ";

			if (gvProducto != null && !gvProducto.getDescripcion().equals("")) {
				sql = sql + " and p.descripcion like :descripcion";
			}
			sql = sql + " order by p.descripcion asc";

			Query query = getEntityManager().createQuery(sql);
			if (gvProducto != null && !gvProducto.getDescripcion().equals("")) {
				query.setParameter("descripcion", '%' + gvProducto.getDescripcion() + '%');
			}
			query.setParameter("idEmpresa", gvProducto.getIdEmpresa());

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

	@Override
    public GvProducto buscarPorCodigo(long idEmpresa, String codigo) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvProducto> criteria = cb.createQuery(GvProducto.class);
            Root<GvProducto> gvProducto = criteria.from(GvProducto.class);
            criteria.select(gvProducto).where(cb.and(cb.equal(gvProducto.get("codigo"), codigo), cb.equal(gvProducto.get("idEmpresa"), idEmpresa)));

            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			//exc.printStackTrace();
			exc.getMessage();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvProducto> buscarGvProductos(GvProducto gvProducto) {
    	System.out.println("gvProducto.getDescripcion() = "+gvProducto.getDescripcion());
		try {
			String sql =  "select p from GvProducto p "
						+ " where p.idEmpresa = :idEmpresa "
						+ "   and p.estado = 1 ";
			if (gvProducto != null && gvProducto.getDescripcion() != null &&  
			   !gvProducto.getDescripcion().equals("")) {
				sql = sql + " and p.descripcion >= :descripcion ";
			}
			sql = sql + " order by p.descripcion ";
			
			Query query = getEntityManager().createQuery(sql);
			query.setParameter("idEmpresa", gvProducto.getIdEmpresa());
			if (gvProducto != null && gvProducto.getDescripcion() != null &&  
			   !gvProducto.getDescripcion().equals("")) {
				query.setParameter("descripcion", gvProducto.getDescripcion());
			}

			return query.getResultList();
		} catch(Exception exc) {
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvProducto(GvProducto gvProducto) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvProducto);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
