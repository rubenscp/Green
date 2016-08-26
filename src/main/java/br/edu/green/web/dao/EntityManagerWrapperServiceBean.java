package br.edu.green.web.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.edu.green.web.service.GeneralService;

/**
 * <p>
 * <b> This class implements the services used to manipulate the Green database.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015 (creation date)
 */

@Stateless
public class EntityManagerWrapperServiceBean extends GeneralService implements EntityManagerWrapperService {

	// defining serial version
	private static final long serialVersionUID = 3013890146343111825L;

	// defining persistence object
	@PersistenceContext(unitName = "green-pu")
	private EntityManager em;

	/**
	 * Default constructor.
	 * 
	 * @throws Exception
	 *             The exception object
	 */
	public EntityManagerWrapperServiceBean() throws Exception {
		super();

		// initializing services
		this.initializeServices();
	}

	/**
	 * Creates a new line in the table of the Green data base.
	 * 
	 * @param t
	 *            The generic object
	 * @return T The generic object
	 * @throws Exception
	 *             The exception object
	 */
	@Override
	public <T> T create(T t) throws Exception {
		// persisting entity object
		this.em.persist(t);

		// returning nothing
		return null;
	}

	/**
	 * Updates a line in the table of the SITIS Web data base.
	 * 
	 * @param t
	 *            The generic object
	 * @return T The generic object
	 * @throws Exception
	 *             The exception object
	 */
	@Override
	public <T> T update(T t) throws Exception {
		// updating entity object
		return this.em.merge(t);
	}

	/**
	 * Deletes a line in the table of the SITIS Web data base.
	 * 
	 * @param t
	 *            The generic object
	 * @throws Exception
	 *             The exception object
	 */
	@Override
	public void remove(Object t) throws Exception {
		// removing entity object
		t = em.merge(t);
		em.remove(t);
	}

	/**
	 * Find a line in the table of the SITIS Web data base using an filter.
	 * 
	 * @param type
	 *            The object type
	 * @param id
	 *            The object identification
	 * @return T The generic object
	 * @throws Exception
	 *             The exception object
	 */
	@Override
	public <T> T find(Class<T> type, Object id) throws Exception {
		return em.find(type, id);
	}

	/**
	 * Find all lines in the table of the SITIS Web data base using an SQL instruction.
	 * 
	 * @param sql
	 *            The SQL instruction.
	 * @return List<T> - List of objects
	 * @throws Exception
	 *             The exception object
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll(String sql) throws Exception {
		// finding all entity object
		return this.em.createQuery(sql).getResultList();
	}

	/**
	 * Creates a query using an SQL instruction.
	 * 
	 * @param sql
	 *            The SQL instruction.
	 * @return Query - The Query object
	 * @throws Exception
	 *             The exception object
	 */
	@Override
	public Query createQuery(String sql) throws Exception {
		Query q = this.em.createQuery(sql);
		return q;
	}

	/**
	 * Creates a native query using an SQL instruction.
	 * 
	 * @param sql
	 *            The SQL instruction.
	 * @return Query - The Query object
	 * @throws Exception
	 *             The exception object
	 */
	@Override
	public Query createNativeQuery(String sql) throws Exception {
		return em.createNativeQuery(sql);
	}

	/**
	 * Updates a query using an SQL instruction.
	 * 
	 * @param sql
	 *            The SQL instruction.
	 * @return int - The number of lines updated.
	 * @throws Exception
	 *             The exception object
	 */
	@Override
	public int executeUpdateQuery(String sql) throws Exception {
		return this.em.createQuery(sql).executeUpdate();
	}

}
