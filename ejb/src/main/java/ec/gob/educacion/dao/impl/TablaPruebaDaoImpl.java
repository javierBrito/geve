package ec.gob.educacion.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.educacion.dao.TablaPruebaDao;
import ec.gob.educacion.model.geve.TablaPrueba;

public class TablaPruebaDaoImpl implements TablaPruebaDao {
	//EntityManagerFactory emf = Persistence.createEntityManagerFactory("primary");
	//EntityManager em = emf.createEntityManager();

	@PersistenceContext(unitName = "primary")
    private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<TablaPrueba> lista() {
		try {
			Query query = em.createQuery("select tp from TablaPrueba tp ");
			return query.getResultList();
		} catch (Exception e) {
			System.out.println("error: "+e.getMessage());
			return null;
		}
	}

	@Override
	public void create(TablaPrueba tablaPrueba) {
		try {
			em.getTransaction().begin();
			em.persist(tablaPrueba);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println("error: "+e.getMessage());
		} 
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

	@Override
	public TablaPrueba update(TablaPrueba tablaPrueba) {
		try {
			//em.getTransaction().begin();
			tablaPrueba = em.merge(tablaPrueba);
			//em.getTransaction().commit();
		} catch (Exception e) {
			//em.getTransaction().rollback();
			System.out.println("error: "+e.getMessage());
		} 
		
		return tablaPrueba;
	}

	@Override
	public void delete(TablaPrueba tablaPrueba) {
		try {
			em.getTransaction().begin();
			em.remove(tablaPrueba);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println("error: "+e.getMessage());
		} 
	}
	
}
