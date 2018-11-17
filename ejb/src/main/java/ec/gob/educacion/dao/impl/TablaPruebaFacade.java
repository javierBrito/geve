package ec.gob.educacion.dao.impl;

import ec.gob.educacion.dao.TablaPruebaFacadeLocal;
import ec.gob.educacion.model.geve.TablaPrueba;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TablaPruebaFacade extends AbstractFacade<TablaPrueba> implements TablaPruebaFacadeLocal {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TablaPruebaFacade() {
        super(TablaPrueba.class);
    }
	
	public TablaPrueba read(int codigo) {
		try {
			Query query = em.createQuery("select tp from TablaPrueba tp where tp.codigo=:codigo");
			query.setParameter("codigo", codigo);
			return (TablaPrueba) query.getSingleResult();
		} catch (Exception e) {
			System.out.println("error: "+e.getMessage());
			return null;
		}
	}
    
}
