package br.edu.green.web.service.ws;

import javax.ejb.Local;

import br.edu.green.web.entity.PersonEntity;

@Local
public interface WebServiceCorporativeService {
	public PersonEntity authenticatePerson(String login, String password) throws AuthenticatePersonException;
}
