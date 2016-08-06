package br.edu.green.web.controller;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.DynamicMenuModel;

@ManagedBean(name = "menuController")
@SessionScoped
public class MenuController implements Serializable {

	private static final long serialVersionUID = 1L;

	// temporary constant about profiles of users Sitis
	private static final String SITIS_ADMINISTRATOR = "AS";
	private static final String SITIS_PLATFORMM_MANAGER = "GPS";
	private static final String SITIS_USER = "USR";

	private static final String MENU_TITLE_STYLE = "sitisMenuTitle";
	private static final String MENU_ITEM_STYLE = "sitisMenuItem";

	private FacesContext facesContext;
	private DynamicMenuModel model;
	private ResourceBundle labels;
	private Locale currentLocale;

	private String shortNameProfilePerson = "USR";

	@PostConstruct
	public void init() {
		// get FacesContext instance
		this.facesContext = FacesContext.getCurrentInstance();

		// get current locale
		this.currentLocale = this.facesContext.getViewRoot().getLocale();

		// creating instance of Sitis Web properties
		this.labels = ResourceBundle.getBundle("i18n.messages",
				this.currentLocale);

		// creating instance of menu object
		this.model = new DynamicMenuModel();

		// selecting user profile to build specific menu
		if (this.shortNameProfilePerson
				.equals(MenuController.SITIS_ADMINISTRATOR)) {
			this.BuildMenuAS();
		} else if (this.shortNameProfilePerson
				.equals(MenuController.SITIS_PLATFORMM_MANAGER)) {
			this.BuildMenuGPS();
		} else if (this.shortNameProfilePerson
				.equals(MenuController.SITIS_USER)) {
			this.BuildMenuUSR();
		} else {
			this.BuildMenuUSR();
		}

	}

	public DynamicMenuModel getModel() {
		return model;
	}

	private void BuildMenuAS() {

		DefaultSubMenu experimentsSubMenu = this.SubMenu(model,
				"menu.experiments");
		ItemSubMenu(experimentsSubMenu, "menu.bookingSoilColumns",
				"bookingSoilColumns.xhtml");
		ItemSubMenu(experimentsSubMenu, "menu.experiment",
				"experimentList.xhtml");

		DefaultSubMenu paneControlSubMenu = this.SubMenu(this.model,
				"menu.controlPanel");
		ItemSubMenu(paneControlSubMenu, "menu.underConstruction",
				"underConstruction.xhtml");

		DefaultSubMenu reportsSubMenu = this
				.SubMenu(this.model, "menu.reports");
		ItemSubMenu(reportsSubMenu, "menu.underConstruction",
				"underConstruction.xhtml");

		DefaultSubMenu configurationsSubMenu = this.SubMenu(this.model,
				"menu.configurations");
		ItemSubMenu(configurationsSubMenu, "menu.underConstruction",
				"underConstruction.xhtml");

		DefaultSubMenu helpSubMenu = this.SubMenu(this.model, "menu.help");
		ItemSubMenu(helpSubMenu, "menu.underConstruction", "help.xhtml");

	}

	private void BuildMenuGPS() {

	}

	private void BuildMenuUSR() {

		DefaultSubMenu experimentsSubMenu = this.SubMenu(model,
				"menu.experiments");
		ItemSubMenu(experimentsSubMenu, "menu.bookingSoilColumns",
				"bookingSoilColumns.xhtml");
		ItemSubMenu(experimentsSubMenu, "menu.experiment",
				"experimentList.xhtml");

		DefaultSubMenu reportsSubMenu = this
				.SubMenu(this.model, "menu.reports");
		ItemSubMenu(reportsSubMenu, "menu.underConstruction",
				"underConstruction.xhtml");

		DefaultSubMenu helpSubMenu = this.SubMenu(this.model, "menu.help");
		ItemSubMenu(helpSubMenu, "menu.underConstruction", "help.xhtml");

	}

	private DefaultSubMenu SubMenu(DynamicMenuModel model, String titleSubMenu) {
		String label = this.labels.getString(titleSubMenu);
		DefaultSubMenu subMenu = new DefaultSubMenu(label);
		subMenu.setStyleClass(MenuController.MENU_TITLE_STYLE);
		model.addElement(subMenu);
		return subMenu;
	}

	private void ItemSubMenu(DefaultSubMenu subMenu, String titleItemMenu,
			String nameFileXHTML) {
		String label = this.labels.getString(titleItemMenu);
		DefaultMenuItem itemMenu = new DefaultMenuItem(label);
		itemMenu.setStyleClass(MenuController.MENU_ITEM_STYLE);
		itemMenu.setValue(label);
		itemMenu.setHref(nameFileXHTML);
		itemMenu.setIcon("ui-icon-home");
		itemMenu.setUpdate("panelCenter_SITISWeb");
		subMenu.addElement(itemMenu);
	}

}