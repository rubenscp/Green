package br.edu.green.web.service.ws;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.service.PersonService;
import br.edu.green.web.webservice.domain.AccessTokenResponse;
import br.edu.green.web.webservice.domain.EntityWrapperSingle;
import br.edu.green.web.webservice.domain.JsonAuthenticatedPerson;
import br.gov.embrapa.criptografia.Encrypt;

@Stateless
// @TransactionTimeout(value=5400)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class WebserviceCorporativoServiceBean implements WebServiceCorporativeService {

	// injecting beans
	@EJB
	private PersonService personService;

	// constants
	private final String ACCESS_TOKEN_URL = "https://sistemas.sede.embrapa.br/token";
	private final String AUTHENTICATE_PERSON_LDAP = "https://sistemas.sede.embrapa.br/t/embrapa.dti/servicos/segurancaws/autenticarusuarioldap?";

	// form objects to use web services Embrapa security
	private Form formProduction;
	private Form formHomologation;

	// defining the work objects
	private SitisWebLog log;

	@PostConstruct
	private void init() {
		// creating work objects
		this.log = SitisWebLog.getInstanceof();
		this.log.info("WebserviceCorporativoServiceBean - construtor executado com sucesso");

		// initializing form params on the DTI production environment
		this.formProduction = new Form();
		this.formProduction.param("grant_type", "client_credentials");
		this.formProduction.param("client_id", "A DEFINIR");
		this.formProduction.param("client_secret", "A DEFINIR");

		// initializing form params on the DTI homologationo environment
		this.formHomologation = new Form();
		this.formHomologation.param("grant_type", "client_credentials");
		this.formHomologation.param("client_id", "l_fWltg9XFbPVwNjUkxSoTcxauUa");
		this.formHomologation.param("client_secret", "2o3zr2EkoTqEI8cDl_HWm4nZgqka");
	}

	@Override
	public PersonEntity authenticatePerson(String login, String password) throws AuthenticatePersonException {

		// defining work objects
		PersonEntity personEntity = null;
		ResteasyClient resteasyClient = new ResteasyClientBuilder().build();
		Response response = null;

		try {

			// obtaining access token of web service corporative security
			ResteasyWebTarget webTarget = resteasyClient.target(ACCESS_TOKEN_URL);
			AccessTokenResponse accessTokenResponse = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.form(this.formHomologation), AccessTokenResponse.class);

			// preparing login and password to be encrypted
			String credentials = login.concat(";").concat(password).concat(";");

			this.log.info("login: " + login);
			this.log.info("password: " + password);
			this.log.info("credentials: " + credentials);
			this.log.info("accessTokenResponse: " + accessTokenResponse.getAccessToken());

			// creating encrypted credentials
			Encrypt encrypt = new Encrypt();
			String encryptedCredentials = encrypt.encrypt(credentials);

			this.log.info("encryptedCredentials: " + encryptedCredentials);

			// preparing URL to authenticate person
			String url = this.AUTHENTICATE_PERSON_LDAP + "key=" + encryptedCredentials;

			// calling web service to authenticate person
			response = resteasyClient.target(url).request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenResponse.getAccessToken()).get();

			// verifying web service response
			if (response.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new AuthenticatePersonException(response.getStatus());
			} else {

				// obtaining data of authenticate person
				EntityWrapperSingle<JsonAuthenticatedPerson> personJson = response.readEntity(new GenericType<EntityWrapperSingle<JsonAuthenticatedPerson>>() {
				});

				// creating instance of person entity
				personEntity = new PersonEntity();

				// loading attributes of person entity
				personEntity.setLogin(personJson.getAuthenticatePerson().getLogin());
				personEntity.setName(personJson.getAuthenticatePerson().getName());
				personEntity.setPassword(password);
				personEntity.setWorkplaceName(personJson.getAuthenticatePerson().getWorkplaceName());
				personEntity.setWorkplaceInitials(personJson.getAuthenticatePerson().getWorkplaceInitials());
				personEntity.setEmail(personJson.getAuthenticatePerson().getEmail());

			}
		} catch (Exception e) {
			throw new AuthenticatePersonException(e);

		} finally {

			if (resteasyClient != null) {
				if (!resteasyClient.isClosed()) {
					resteasyClient.close();
				}
			}
			if (response != null) {
				response.close();
			}
		}

		return personEntity;
	}

}