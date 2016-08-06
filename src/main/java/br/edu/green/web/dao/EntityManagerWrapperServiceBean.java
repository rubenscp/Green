package br.edu.green.web.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * This class implements some basic services used to manipulate the SITIS data base.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015
 * 
 */

@Stateless
public class EntityManagerWrapperServiceBean implements EntityManagerWrapperService {

	@PersistenceContext(unitName = "green-pu")
	private EntityManager em;

	@Override
	public <T> T create(T t) {
		this.em.persist(t);
		return null;
	}

	@Override
	public <T> T update(T t) {
		return this.em.merge(t);
	}

	@Override
	public void remove(Object t) {
		t = em.merge(t);
		em.remove(t);
	}

	@Override
	public <T> T find(Class<T> type, Object id) {
		return em.find(type, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(String sql) {
		return this.em.createQuery(sql).getResultList();
	}

	@Override
	public Query createQuery(String sql) {
		Query q = this.em.createQuery(sql);
		return q;
	}

	@Override
	public Query createNativeQuery(String sql) {
		return em.createNativeQuery(sql);
	}

	@Override
	public int executeUpdateQuery(String sql) {
		return this.em.createQuery(sql).executeUpdate();
	}

}
