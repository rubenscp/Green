package br.edu.green.web.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.edu.green.web.dao.EntityManagerWrapperService;
import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;

/**
 * <p>
 * <b> This class defines the Person service which contains all methods that access SITIS Data Base.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015 (creation date)
 */

@Stateless
public class PersonServiceBean extends GeneralService implements PersonService {

	// defining serial version
	private static final long serialVersionUID = -3699004851333393226L;

	// EJB objects
	@EJB
	EntityManagerWrapperService emws;

	/**
	 * Default constructor initializing the basics services.
	 * 
	 * @throws GeneralException
	 *             The general exception object
	 */
	public PersonServiceBean() throws GeneralException {
		super();

		// initializing services
		this.initializeServices();
	}

	/**
	 * Saves the data of the person. This action can be an operation of creating or updating.
	 * 
	 * @param person
	 *            The person object.
	 * @return PersonEntity - The new person object with id actualized or simply the person object.
	 * @throws GeneralException
	 *             The general exception object
	 */
	// @Override
	public PersonEntity save(PersonEntity person) throws GeneralException {
		try {
			if (person.getId() == 0) {
				this.emws.create(person);
				return person;
			} else {
				return this.emws.update(person);
			}

		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "save", ((person.getId() == 0) ? Code.DAO_EXCEPTION_CREATE : Code.DAO_EXCEPTION_UPDATE), this.getClass().getSimpleName());
		}
	}

	/**
	 * Deletes the person.
	 * 
	 * @param person
	 *            The person.
	 * @throws GeneralException
	 *             The general exception object
	 */
	@Override
	public void delete(PersonEntity person) throws GeneralException {
		try {
			this.emws.remove(person);
		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "delete", Code.DAO_EXCEPTION_DELETE, this.getClass().getSimpleName());
		}
	}

	@Override
	public PersonEntity findById(long id) throws GeneralException {
		try {
			String sql = "select p from PersonEntity p where p.id = :id";
			return this.findOneObject(sql, "id", Long.toString(id));
		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "findById", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());
		}
	}

	/**
	 * Finds a person by its email.
	 * 
	 * @param userName
	 *            The login of the person.
	 * @return PersonEntity - The person.
	 * @throws GeneralException
	 *             The general exception object
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PersonEntity findByUserName(String userName) throws GeneralException {
		try {
			String sql = "select p from PersonEntity p where p.userName = :user_name";
			Query query = this.emws.createQuery(sql);
			query.setParameter("user_name", userName);
			query.setHint("org.hibernate.cacheable", Boolean.TRUE);
			List<PersonEntity> persons = query.getResultList();
			if (persons.size() > 0) {
				return persons.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "findByUSerName", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());
		}
	}

	/**
	 * Finds a person by its email.
	 * 
	 * @param email
	 *            The login of the person.
	 * @return PersonEntity - The person.
	 * @throws GeneralException
	 *             The general exception object
	 */
	@Override
	@SuppressWarnings("unchecked")
	public PersonEntity findByEmail(String email) throws GeneralException {
		try {
			String sql = "select p from PersonEntity p where p.email = :email";
			Query query = this.emws.createQuery(sql);
			query.setParameter("email", email);
			query.setHint("org.hibernate.cacheable", Boolean.TRUE);
			List<PersonEntity> persons = query.getResultList();
			if (persons.size() > 0) {
				return persons.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "findByEmail", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());
		}
	}

	// @Override
	// @SuppressWarnings("unchecked")
	// public PersonEntity findByRegistration(long registration) throws GeneralException {
	// try {
	// String sql = "select p from PersonEntity p where p.registration = :registration";
	// Query query = this.emws.createQuery(sql);
	// query.setParameter("registration", registration);
	// query.setHint("org.hibernate.cacheable", Boolean.TRUE);
	// List<PersonEntity> persons = query.getResultList();
	// if (persons.size() > 0) {
	// return persons.get(0);
	// } else {
	// return null;
	// }
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "findByRegistration", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());
	// }
	// }

	/**
	 * Finds all persons according a order criteria.
	 * 
	 * @param orderBy
	 *            The order criteria.
	 * @return List<PersonEntity> - The list of persons.
	 * @throws GeneralException
	 *             The general exception object
	 */
	@Override
	public List<PersonEntity> findAll(String orderBy) throws GeneralException {
		try {
			String sql = "select p from PersonEntity p where p.login = :login";
			if (orderBy != null) {
				sql = sql + " order by " + orderBy;
			}
			return this.emws.findAll(sql);

		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "findAll", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());
		}
	}

	/**
	 * Finds all persons according a partial name.
	 * 
	 * @param name
	 *            The partial name.
	 * @return List<PersonEntity> - The list of persons.
	 * @throws GeneralException
	 *             The general exception object
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<PersonEntity> findAllByName(String name) throws GeneralException {
		try {
			String sql = "select p from PersonEntity p where upper(p.name) like :name";
			Query query = emws.createQuery(sql);
			query.setParameter("name", "%" + name.toUpperCase() + "%");
			List<PersonEntity> persons = query.getResultList();
			return persons;
		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "findAll", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());
		}
	}

	/**
	 * Finds a person according some attributes.
	 * 
	 * @param sql
	 *            The SQL instruction.
	 * @param nameKey
	 *            The name of the attribute to search
	 * @param valueKey
	 *            The value of the attribute to search
	 * @return PersonEntity The person.
	 * @throws GeneralException
	 *             The general exception object
	 */
	@SuppressWarnings("unchecked")
	private PersonEntity findOneObject(String sql, String nameKey, String valueKey) throws GeneralException {
		try {
			Query query = this.emws.createQuery(sql);
			query.setParameter(nameKey, valueKey);
			query.setHint("org.hibernate.cacheable", Boolean.TRUE);
			List<PersonEntity> persons = query.getResultList();
			if (persons.size() > 0) {
				return persons.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "findOneObject", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());
		}

	}
}
