package br.edu.green.web.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.menu.DynamicMenuModel;

import br.edu.green.web.entity.NavigationHistoryEntity;
import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.service.PersonService;
import br.edu.green.web.util.FacesUtil;
import br.edu.green.web.util.Util;

/**
 * <p>
 * <b>This class defines attributes that will maintained during a current session of the logged person.</b>
 * </p>
 * <p>
 * The attributes and services provides are:
 * <ul>
 * <li>The current language used in all labels and messages of the interface human-machine;</li>
 * <li>The current theme;</li>
 * <li>The dynamic menu with the navigation history.</li>
 * </ul>
 * </p>
 * 
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 25/08/2015 (creation date)
 */

@ManagedBean(name = "sessionController")
@SessionScoped
public class SessionController extends GeneralController implements Serializable {

	private static final long serialVersionUID = 4742262988364358523L;

	// defining constants
	public static final String DEFAULT_LANGUAGE = "pt_BR";
	// public static final String[] POSSIBLE_LANGUAGES = { "pt_BR", "en_US" };
	public static final String[] POSSIBLE_LANGUAGES = { "pt_BR" };

	// public static final String[] POSSIBLE_THEMES = { "afterdark", "afternoon", "afterwork", "aristo", "black-tie", "blitzer", "bluesky", "casablanca",
	// "cruze", "cupertino", "dark-hive", "dot-luv", "eggplant", "excite-bike", "flick", "glass-x", "home", "hot-sneaks", "humanity", "le-frog", "midnight",
	// "mint-choc", "overcast", "pepper-grinder", "redmond", "rocket", "sam", "smoothness", "south-street", "start", "sunny", "swanky-purse", "trontastic",
	// "twitter bootstrap", "ui-darkness", "ui-lightness", "vader" };
	public static final String DEFAULT_THEME = "redmond";
	public static final String[] POSSIBLE_THEMES = { "aristo", "black-tie", "blitzer", "casablanca", "cruze", "cupertino", "dark-hive", "dot-luv", "eggplant", "excite-bike", "flick", "glass-x", "home", "hot-sneaks", "humanity", "le-frog", "midnight", "mint-choc", "overcast", "pepper-grinder", "redmond", "rocket", "sam", "smoothness", "south-street", "start", "sunny", "swanky-purse", "trontastic", "ui-darkness", "ui-lightness", "vader" };

	// defining class attributes
	private boolean sitisWebEnabled;
	private PersonEntity personEntity;
	private String functionTitle;
	private boolean personLogged;

	// injecting EJB components
	@EJB
	private PersonService personService;

	// defining navigation history menu entity
	// private NavigationHistoryEntity navigationHistoryEntity;

	/**
	 * Default constructor.
	 */
	public SessionController() {
		super();
	}

	/**
	 * Constructor method with parameter 'sitisWebEnabled' indicating its operation status.
	 * 
	 * @param sitisWebEnabled
	 */
	public SessionController(boolean sitisWebEnabled) throws GeneralException {
		this();

		// initialing class attributes
		this.sitisWebEnabled = sitisWebEnabled;
		this.personEntity = new PersonEntity();
		this.personLogged = false;
		// this.navigationHistoryEntity = new NavigationHistoryEntity();
	}

	/**
	 * Initializes some class objects and runs after rendering the JSF page.
	 */
	@PostConstruct
	public void init() {

		try {
			// initializing services
			this.initializeServices();

			// recovering person entity of the session context
			try {
				this.personEntity = FacesUtil.recoverObjectInSession(new PersonEntity());
			} catch (GeneralException e) {
				// nothing to do
			}

			// evaluating person entity object
			if (this.personEntity == null) {
				// initializing person entity
				this.personEntity = new PersonEntity();

				// setting the environment language and theme with default values
				this.personEntity.setLanguage(this.applicationConfiguration.getStringValue("sitisweb.language"));
				this.personEntity.setTheme(this.applicationConfiguration.getStringValue("sitisweb.theme"));
			}

		} catch (GeneralException ge) {
			// handling general exception of SITIS Web.
			this.handleGeneralException(ge);
		}
	}

	/**
	 * Accesses the attribute named 'sitisWebEnabled' and returns its value.
	 * 
	 * @return boolean - The status of SITIS Web indicating normal or failure operation.
	 */
	public boolean isSitisWebEnabled() {
		return sitisWebEnabled;
	}

	/**
	 * Sets the value of attribute named 'sitisWebEnabled' indicating the operation status of SITIS Web.
	 * 
	 * @param sitisWebEnabled
	 *            The status of SITIS Web indicating normal or failure operation
	 */
	public void setSitisWebEnabled(boolean sitisWebEnabled) {
		this.sitisWebEnabled = sitisWebEnabled;
	}

	/**
	 * Accesses the attribute named 'personEntity' and returns its value.
	 * 
	 * @return personEntity - The current logged person.
	 */
	public PersonEntity getPersonEntity() {
		return personEntity;
	}

	/**
	 * Sets the value of attribute named 'personEntity'.
	 * 
	 * @param personEntity
	 *            The logged person in the SITIS.
	 */
	public void setPersonEntity(PersonEntity personEntity) {
		this.personEntity = personEntity;
	}

	/**
	 * Returns the compact name of the logged person.
	 * 
	 * @return String - The compact name of the logged person.
	 */
	public String getCompactNamePerson() {
		return Util.compactNamePerson(personEntity.getName());
	}

	/**
	 * Returns the current locale object according to the language
	 * 
	 * @return The locale object
	 */
	public Locale getLocale() {
		// returning the locale object according current language
		return new Locale(this.getCurrentLanguage());
	}

	/**
	 * Returns the current language chosen by the logged person.
	 * 
	 * @return String - The current language chosen by the logged person.
	 */
	public String getCurrentLanguage() {

		// defining the local variable
		String currentLanguage = "";

		// verifying person entity object
		if (this.personEntity != null) {
			// setting current language according the person language
			currentLanguage = this.personEntity.getLanguage();
		}

		// evaluating the language obtained from the person object
		if (currentLanguage == null || currentLanguage.equals("")) {
			// setting current language according the default language
			currentLanguage = this.applicationConfiguration.getStringValue("sitisweb.language");
			if (currentLanguage == null) {
				currentLanguage = SessionController.DEFAULT_LANGUAGE;
			}
		}

		// returning the current language
		return currentLanguage;
	}

	/**
	 * Sets the value of attribute named 'currentLanguage'.
	 * 
	 * @param currentLanguage
	 *            The current language of the logged person
	 */
	public void setCurrentLanguage(String currentLanguage) {
		// verifying person entity object
		if (this.personEntity != null) {
			// setting current theme
			this.personEntity.setLanguage(currentLanguage);
		}
	}

	/**
	 * Returns the current theme chosen by the logged person.
	 * 
	 * @return String - The current theme chosen by the logged person.
	 */
	public String getCurrentTheme() {
		// defining the local variable
		String theme = SessionController.DEFAULT_THEME;

		// verifying person entity object
		if (this.personEntity != null) {
			// setting current language according the person language
			theme = this.personEntity.getTheme();
		}

		// evaluating the language obtained from the person object
		if (theme == null || theme.equals("")) {
			// setting current theme according the default theme
			theme = this.applicationConfiguration.getStringValue("sitisweb.theme");
			if (theme == null) {
				theme = SessionController.DEFAULT_THEME;
			}
		}

		// returning the current theme
		return theme;
	}

	/**
	 * Sets the current theme of the logged person.
	 * 
	 * @param theme
	 *            The current theme of the logged person.
	 */
	public void setCurrentTheme(String theme) {
		// verifying person entity object
		if (this.personEntity != null) {
			// setting current theme
			this.personEntity.setTheme(theme);
		}
	}

	/**
	 * Returns all possible languages.
	 * 
	 * @return String[] - Array containing all possible languages.
	 */
	public String[] getLanguages() {
		return (SessionController.POSSIBLE_LANGUAGES);
	}

	/**
	 * Returns all possible themes.
	 * 
	 * @return String[] - Array containing all possible themes.
	 */
	public String[] getThemes() {
		return (SessionController.POSSIBLE_THEMES);
	}

	/**
	 * Configures JSF environment to start a new login. This method removes the person object reference and sets the default language.
	 */
	// public void startNewLogin() {
	// try {
	// // restoring objects of the session context
	// HttpSession httpSession = FacesUtil.getSession();
	// httpSession.removeAttribute(Util.className(PersonEntity.class.getName()));
	// } catch (Exception e) {
	// // nothing to do
	// }
	//
	// languageController.setLanguage(LanguageController.DEFAULT_LANGUAGE);
	// }

	/**
	 * Returns the function title.
	 * 
	 * @return String - The function title.
	 */
	public String getFunctionTitle() {
		return functionTitle;
	}

	/**
	 * Sets the value of attribute named 'functionTitle'.
	 * 
	 * @param functionTitle
	 *            The function title.
	 */
	public void setFunctionTitle(String functionTitle) {
		this.functionTitle = functionTitle;
	}

	/**
	 * @return the personLogged
	 */
	public boolean isPersonLogged() {
		return (this.personEntity != null && this.personEntity.getId().longValue() > 0 ? true : false);
		// return personLogged;
	}

	/**
	 * @param personLogged
	 *            the personLogged to set
	 */
	public void setPersonLogged(boolean personLogged) {
		this.personLogged = personLogged;
	}

	/**
	 * Returns the navigation history model object to compose the presentation of the main form
	 * 
	 * @return DynamicMenuModel the navigation history model object
	 */
	public DynamicMenuModel getNavigationHistoryMenu() {

		// defining navigation history menu entity
		NavigationHistoryEntity navigationHistoryEntity = new NavigationHistoryEntity();

		try {
			// recovering navigation history entity of the session context
			navigationHistoryEntity = FacesUtil.recoverObjectInSession(new NavigationHistoryEntity());
		} catch (GeneralException e1) {
			// nothing to do at this moment
		}

		// evaluating the navigation history object
		if (navigationHistoryEntity == null) {
			// creating a new navigation history
			navigationHistoryEntity = new NavigationHistoryEntity();
			// storing in the session context
			try {
				FacesUtil.storeObjectInSession(navigationHistoryEntity);
			} catch (GeneralException e) {
				// nothing to do
			}
		}

		// returning the current navigation history menu
		return navigationHistoryEntity.getNavigationHistoryMenu();
	}

	/**
	 * Updates the person entity in the SITIS Web data base.
	 */
	public void updatePerson() {
		try {
			// verifying person entity object
			if (this.personEntity != null) {
				this.personService.save(personEntity);
			}
		} catch (GeneralException ge) {
			// handling general exception of SITIS Web
			this.handleGeneralException(ge);
		}
	}

	/**
	 * Logout the logged person
	 * 
	 * @return String The name of the login form
	 */
	@SuppressWarnings("static-access")
	public void logout() {
		try {
			// removing all objects stored in the session context
			FacesUtil.removeObjectInSession(new PersonEntity());
			FacesUtil.removeObjectInSession(new NavigationHistoryEntity());

			// setting invalid the actual JSF session
			FacesUtil.getContext().getCurrentInstance().getExternalContext().invalidateSession();

			// calling login form
			FacesUtil.getContext().getExternalContext().redirect(this.applicationConfiguration.getStringValue("xhtml.form.name.login"));

		} catch (GeneralException ge) {
			// handling general exception of SITIS Web.
			this.handleGeneralException(ge);

		} catch (IOException ioe) {
			// handling IOException with creating new general exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_INPUT_OUTPUT, this.applicationMessage.getMessage(Code.FACES_EXCEPTION_INPUT_OUTPUT.name())));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("logout");
			ge.setExceptionOriginalMessage(ioe.getMessage()); // the original exception message

			// handling general exception of SITIS Web
			this.handleGeneralException(ge);
		}
	}
}
