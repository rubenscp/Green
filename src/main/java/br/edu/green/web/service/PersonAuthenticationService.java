package br.edu.green.web.service;

import javax.ejb.Local;

import br.edu.green.web.validate.Result;

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

	public Result validate(String corporateAccount, String password, String language);

	public boolean disconnectPerson();

}
