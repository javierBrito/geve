package ec.gob.educacion.dao.impl;

import java.util.List;

import ec.gob.educacion.dao.GvTipoClienteDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvTipoCliente;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito on 18/06/2015.
 */
@Stateless
public class GvTipoClienteDAOImpl extends GenericOracleAsignacionesDAOImpl<GvTipoCliente, Long> implements GvTipoClienteDAO{
    @Override
    public GvTipoCliente buscarPorCodigo(long idTipoCliente) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvTipoCliente> criteria = cb.createQuery(GvTipoCliente.class);
            Root<GvTipoCliente> gvTipoCliente = criteria.from(GvTipoCliente.class);
            criteria.select(gvTipoCliente)
                    .where(
                            cb.equal(gvTipoCliente.get("idTipoCliente"), idTipoCliente)
                    	  )
                    .orderBy(cb.asc(gvTipoCliente.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

	@Override
    public GvTipoCliente buscarPorNombre(String descripcion) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvTipoCliente> criteria = cb.createQuery(GvTipoCliente.class);
            Root<GvTipoCliente> gvTipoCliente = criteria.from(GvTipoCliente.class);
            criteria.select(gvTipoCliente)
                    .where(
                            cb.equal(gvTipoCliente.get("descripcion"), descripcion)
                    	  )
                    .orderBy(cb.asc(gvTipoCliente.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvTipoCliente> buscarGvTipoClientes() {
		try{
	        Query query = em.createQuery("select m from GvTipoCliente m ");
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvTipoCliente(GvTipoCliente gvTipoCliente) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvTipoCliente);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
