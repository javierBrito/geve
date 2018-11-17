package ec.gob.educacion.dao.impl;

import java.util.List;
import ec.gob.educacion.dao.GvParametroDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvParametro;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito.
 */
@Stateless
public class GvParametroDAOImpl extends GenericOracleAsignacionesDAOImpl<GvParametro, Long> implements GvParametroDAO {
    @Override
    public GvParametro buscarPorCodigo(long idParametro) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvParametro> criteria = cb.createQuery(GvParametro.class);
            Root<GvParametro> gvParametro = criteria.from(GvParametro.class);
            criteria.select(gvParametro)
                    .where(
                            cb.equal(gvParametro.get("idParametro"), idParametro)
                    	  )
                    .orderBy(cb.asc(gvParametro.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvParametro> buscarListaPorParametros(GvParametro gvParametro) {
		try{
			String sql =  "select p from GvParametro p "
						+ " where k.estado=1 ";

			Query query = getEntityManager().createQuery(sql);

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

	public GvParametro buscarPorDescripcion(String descripcion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvParametro> criteria = cb.createQuery(GvParametro.class);
            Root<GvParametro> gvParametro = criteria.from(GvParametro.class);
            criteria.select(gvParametro)
                    .where(
                            cb.equal(gvParametro.get("descripcion"), descripcion)
                    	  )
                    .orderBy(cb.asc(gvParametro.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvParametro> buscarListaPorDescripcion(String descripcion) {
		try{
			String sql = "select p from GvParametro p where p.estado=1 ";

			if (descripcion != null && !descripcion.equals("")) {
				sql = sql + " and p.descripcion like :descripcion";
			}
			sql = sql + " order by p.descripcion asc";

			Query query = getEntityManager().createQuery(sql);
			if (descripcion != null && !descripcion.equals("")) {
				query.setParameter("descripcion", '%' + descripcion + '%');
			}

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

	@Override
    public GvParametro buscarPorNombre(String nombre) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvParametro> criteria = cb.createQuery(GvParametro.class);
            Root<GvParametro> gvParametro = criteria.from(GvParametro.class);
            criteria.select(gvParametro)
                    .where(
                            cb.equal(gvParametro.get("nombre"), nombre)
                    	  )
                    .orderBy(cb.asc(gvParametro.get("nombre")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			//exc.printStackTrace();
			exc.getMessage();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvParametro> buscarGvParametros() {
		try{
	        Query query = em.createQuery("select p from GvParametro p ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvParametro(GvParametro gvParametro) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvParametro);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
