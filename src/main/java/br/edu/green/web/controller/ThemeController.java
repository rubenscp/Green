package br.edu.green.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.service.PersonService;
import br.edu.green.web.util.FacesUtil;
import br.edu.green.web.util.Util;

@ManagedBean(name = "themeControllerOld")
@SessionScoped
public class ThemeController implements Serializable {

	private static final long serialVersionUID = 6654673364474728982L;

	public static final String DEFAULT_THEME = "redmond";

	public static final String[] POSSIBLE_THEMES = { "afterdark", "afternoon", "afterwork", "aristo", "black-tie", "blitzer", "bluesky", "casablanca", "cruze", "cupertino", "dark-hive", "dot-luv", "eggplant", "excite-bike", "flick", "glass-x", "home", "hot-sneaks", "humanity", "le-frog", "midnight", "mint-choc", "overcast", "pepper-grinder", "redmond", "rocket", "sam", "smoothness", "south-street", "start", "sunny", "swanky-purse", "trontastic", "twitter bootstrap", "ui-darkness", "ui-lightness", "vader" };

	private String theme;
	private PersonEntity personEntity;

	@EJB
	PersonService personService;

	@PostConstruct
	public void init() {
		this.theme = ThemeController.DEFAULT_THEME;
	}

	public String[] getThemes() {
		return (POSSIBLE_THEMES);
	}

	public String getTheme() {

		// getting person object, if he�s logged
		this.personEntity = this.restorePersonOfSession();
		if (personEntity == null) {
			this.theme = ThemeController.DEFAULT_THEME;
			return this.theme;
		}

		this.theme = this.personEntity.getTheme();

		// assign default theme and update data person
		if (this.theme.isEmpty()) {
			this.theme = ThemeController.DEFAULT_THEME;
			this.personEntity.setTheme(this.theme);
			// this.personService.save(personEntity);
		}
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
		// SitisWebLog.getInstanceof().info("ThemeController - setTheme");

		// getting person object, if he�s logged
		this.personEntity = this.restorePersonOfSession();
		if (personEntity != null) {
			this.personEntity.setTheme(this.theme);
			// this.personService.save(personEntity);
		}
	}

	public void updateTheme(AjaxBehaviorEvent event) {
		// SitisWebLog.getInstanceof().info("ThemeController - updateTheme");

		// getting person object, if he�s logged
		this.personEntity = this.restorePersonOfSession();
		if (personEntity != null) {
			this.personEntity.setTheme(this.theme);
			// this.personService.save(personEntity);
			// SitisWebLog.getInstanceof().info("ThemeController - updateTheme save ");
		}
	}

	private PersonEntity restorePersonOfSession() {
		// SitisWebLog.getInstanceof().info("ThemeController - restorePerson");

		// restoring objects of the session context
		// HttpSession httpSession = FacesUtil.getSession();
		// return (PersonEntity) httpSession.getAttribute(Util.className(PersonEntity.class.getName()));
		return null;
	}

}