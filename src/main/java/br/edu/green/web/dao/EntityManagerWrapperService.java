package br.edu.green.web.dao;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.Query;

/**
 * <p>
 * <b> This class implements the services used to manipulate the SITIS Web database.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015 (creation date)
 */

@Local
public interface EntityManagerWrapperService {

	public <T> T create(T t) throws Exception;

	public <T> T update(T t) throws Exception;

	public void remove(Object t) throws Exception;

	public <T> T find(Class<T> type, Object id) throws Exception;

	public <T> List<T> findAll(String sql) throws Exception;

	public Query createQuery(String sql) throws Exception;

	public Query createNativeQuery(String sql) throws Exception;

	public int executeUpdateQuery(String sql) throws Exception;

}
