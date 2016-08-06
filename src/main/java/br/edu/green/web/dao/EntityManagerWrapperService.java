package br.edu.green.web.dao;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.Query;

/**
 * This class defines some basic services used to manipulate the SITIS data
 * base.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015
 * 
 */

@Local
public interface EntityManagerWrapperService {

	public <T> T create(T t);

	public <T> T update(T t);

	public void remove(Object t);

	public <T> T find(Class<T> type, Object id);

	public <T> List<T> findAll(String sql);

	public Query createQuery(String sql);

	public Query createNativeQuery(String sql);

	public int executeUpdateQuery(String sql);

}
