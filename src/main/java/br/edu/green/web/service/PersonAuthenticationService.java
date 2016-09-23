package br.edu.green.web.service;

import javax.ejb.Local;

import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.exception.GeneralException;

/**
 * LoginService.java: This class defines methods to authenticate an user to access SITIS Web.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 26/08/2015
 * 
 */

@Local
public interface PersonAuthenticationService {

	public ProcessingResultEntity validate(String login, String password, String language) throws GeneralException;

	public void disconnectPerson() throws GeneralException;
}
