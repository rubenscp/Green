package br.edu.green.web.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.edu.green.web.dao.EntityManagerWrapperService;
import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.util.SitisWebLog;

/**
 * This class defines the Person service which contains all methods that access
 * SITIS Data Base.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 18/08/2015
 * 
 */

@Stateless
public class PersonServiceBean implements PersonService {

	@EJB
	EntityManagerWrapperService emws;

	// defining the work objects
	private SitisWebLog log;

	// constructor method
	public PersonServiceBean() {
		// creating work objects
		this.log = SitisWebLog.getInstanceof();
		this.log.info("PersonServiceBean - construtor executado com sucesso");
	}

	@Override
	public PersonEntity save(PersonEntity person) {

		if (person.getId() == 0) {
			return this.emws.create(person);
		} else {
			return this.emws.update(person);
		}
	}

	@Override
	public void delete(PersonEntity person) {
		this.emws.remove(person);
	}

	@Override
	public PersonEntity findById(long id) {
		String sql = "select p from PersonEntity p where p.id = :id";
		return this.findOneObject(sql, "id", Long.toString(id));
	}

	@Override
	public PersonEntity findByLogin(String login) {
		String sql = "select p from PersonEntity p where p.login = :login";

		this.log.info("PersonServiceBean - findByLogin - login: " + login);

		return this.findOneObject(sql, "login", login);
	}

	@Override
	public List<PersonEntity> findAll(String orderBy) {
		String sql = "select p from PersonEntity p where p.login = :login";
		if (orderBy != null) {
			sql = sql + " order by " + orderBy;
		}
		return this.emws.findAll(sql);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PersonEntity> findAllByName(String name) {
		String sql = "select p from PersonEntity p where upper(p.name) like :name";
		Query query = emws.createQuery(sql);
		query.setParameter("name", "%" + name.toUpperCase() + "%");
		List<PersonEntity> persons = query.getResultList();
		return persons;
	}

	@SuppressWarnings("unchecked")
	private PersonEntity findOneObject(String sql, String nameKey, String valueKey) {
		Query query = this.emws.createQuery(sql);
		query.setParameter(nameKey, valueKey);
		query.setHint("org.hibernate.cacheable", Boolean.TRUE);
		List<PersonEntity> persons = query.getResultList();
		if (persons.size() > 0) {
			return persons.get(0);
		} else {
			return null;
		}
	}

}
