package ec.gob.educacion.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.educacion.dao.GenericDAO;
import ec.gob.educacion.exception.EducacionDeleteException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.exception.EducacionUpdateException;
import ec.gob.educacion.resources.Constantes;

/**
 * @author
 */
public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

	private final Class<T> persistentClass;
	
	@PersistenceContext
	protected EntityManager em;

	@SuppressWarnings(Constantes.UNCHECKED)
	public GenericDAOImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public T findById(ID id) {
		return getEntityManager().find(persistentClass, id);
	}

	@SuppressWarnings(Constantes.UNCHECKED)
	@Override
	public List<T> findAll() {
		return getEntityManager().createQuery(
				"select o from " + persistentClass.getCanonicalName() + " o")
				.getResultList();
	}

	@SuppressWarnings(Constantes.UNCHECKED)
	@Override
	public List<T> findAllActive() {
		return getEntityManager().createQuery(
				"select o from " + persistentClass.getCanonicalName() + " o where o.stsEstado = 1")
				.getResultList();
	}

	@SuppressWarnings(Constantes.UNCHECKED)
	@Override
	public List<T> findByNamedQuery(String queryName, Object... params) {

		Query qry = getEntityManager().createNamedQuery(queryName);

		int index = 0;

		for (Object p : params) {
			qry.setParameter(index++, p);
		}

		return qry.getResultList();
	}

	@SuppressWarnings(Constantes.UNCHECKED)
	@Override
	public List<T> findByNamedQueryAndNamedParams(String queryName,
			Map<String, ? extends Object> params) {

		Query qry = getEntityManager().createNamedQuery(queryName);

		for (String key : params.keySet()) {
			qry.setParameter(key, params.get(key));
		}

		return qry.getResultList();

	}

	@Override
	public T update(T entity) throws EducacionUpdateException {
		try {
			return getEntityManager().merge(entity);
		} catch (Throwable ex) {
			throw new EducacionUpdateException(entity, ex);
		}
	}

	@Override
	public void delete(T entity) throws EducacionDeleteException {
		try {
			getEntityManager().remove(getEntityManager().merge(entity));
		} catch (Throwable ex) {
			throw new EducacionDeleteException(entity, ex);
		}
	}

	@Override
	public void delete(ID id) throws EducacionDeleteException {
		T obj = findById(id);
		delete(obj);
	}

	@Override
	public void persist(T entity) throws EducacionPersistException {
		try {
			getEntityManager().persist(entity);
		} catch (Throwable ex) {
			throw new EducacionPersistException(entity, ex);
			
		}
	}

	public EntityManager getEntityManager() {
		return this.em;
	}
}
