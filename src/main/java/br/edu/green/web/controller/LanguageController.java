package br.edu.green.web.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.service.PersonService;
import br.edu.green.web.util.FacesUtil;
import br.edu.green.web.util.SitisWebLog;
import br.edu.green.web.util.Util;

@ManagedBean(name = "languageController")
@SessionScoped
public class LanguageController implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_LANGUAGE = "pt";
	public static final String[] POSSIBLE_LANGUAGES = { "pt", "en" };

	private String language;
	private Locale locale;

	private PersonEntity personEntity;

	@EJB
	PersonService personService;

	@PostConstruct
	public void init() {
		this.language = LanguageController.DEFAULT_LANGUAGE;
	}

	public String[] getLanguages() {
		return (LanguageController.POSSIBLE_LANGUAGES);
	}

	public void loadLanguage() {
		this.getLanguage();
		this.changeLanguage();
	}
		
	public String changeToPortuguese() {

		// configurando idioma portugues
		// this.selectedLanguage = PORTUGUESE;

		// alterando idioma
		this.changeLanguage();

		return "";
	}

	public String changeToEnglish() {

		// configurando idioma ingles
		// this.selectedLanguage = ENGLISH;

		// alterando idioma
		this.changeLanguage();

		return "";
	}

	public Locale getLocale() {
		if (this.locale == null) {
			this.locale = new Locale(this.getLanguage());
		}
		return this.locale;
	}

	private void storeStatusChangedLanguage() {
		// storing the status of the changed language
		FacesUtil.getSession().setAttribute(Util.className(LanguageController.class.getName()), true);
	}

	public String getLanguage() {
		// getting person object, if he�s logged
		this.personEntity = this.restorePerson();
		if (personEntity == null) {
			this.language = LanguageController.DEFAULT_LANGUAGE;
			return this.language;
		}

		this.language = this.personEntity.getLanguage();

		// assign default theme and update data person
		if (this.language.isEmpty()) {
			this.language = LanguageController.DEFAULT_LANGUAGE;
			this.personEntity.setLanguage(this.language);
			this.personService.save(personEntity);
		}
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;

		// getting person object, if he�s logged
		this.personEntity = this.restorePerson();
		if (personEntity != null) {
			this.personEntity.setLanguage(this.language);
			this.personService.save(personEntity);
		}		
	}
	
	public void updateLanguage(AjaxBehaviorEvent event) {
		SitisWebLog.getInstanceof().info("LanguageController - updateLanguage");

		// getting person object, if he�s logged
		this.personEntity = this.restorePerson();
		if (personEntity != null) {
			this.personEntity.setLanguage(this.language);
			this.personService.save(personEntity);
			SitisWebLog.getInstanceof().info("LanguageController - updateLanguage save ");			
		}		
		
		// update language in the faces context 
		this.changeLanguage();
	}

	private PersonEntity restorePerson() {
		SitisWebLog.getInstanceof().info("LanguageController - restorePerson");

		// restoring objects of the session context
		HttpSession httpSession = FacesUtil.getSession();
		return (PersonEntity) httpSession.getAttribute(Util.className(PersonEntity.class.getName()));
	}

	public void changeLanguage() {

		// configurando o idioma atual
		this.locale = new Locale(this.language);

		// atualizando o idioma junto ao ambiente operacional
		FacesContext instance = FacesContext.getCurrentInstance();
		instance.getViewRoot().setLocale(locale);
	}
	
}
