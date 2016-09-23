package br.edu.green.web.controller;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.DynamicMenuModel;
import org.primefaces.model.menu.MenuModel;

import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.entity.enumerate.ActionEnum;
import br.edu.green.web.entity.enumerate.FilterEnum;
import br.edu.green.web.entity.enumerate.GreenPersonProfileEnum;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.util.FacesUtil;

/**
 * <p>
 * <b>This class controls the construction of the SITIS options menu according to the profile of logged person.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 19/08/2015 (creation date)
 */

@ManagedBean(name = "menuController")
@ViewScoped
public class MenuController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String MENU_ITEM_STYLE = "greenMenuItem";

	private static final int DEFAULT_SIZE = 210;

	private PersonEntity personEntity;
	private FacesContext facesContext;
	private MenuModel model;
	private ResourceBundle labels;
	private Locale currentLocale;
	private UIViewRoot uiViewRoot;
	private int size;

	/**
	 * Default constructor
	 */
	public MenuController() {
		super();
		this.uiViewRoot = new UIViewRoot();
	}

	/**
	 * Initializes some class objects and runs after rendering the JSF page.
	 */
	@PostConstruct
	public void init() {

		// set de default size of the left screen
		this.size = MenuController.DEFAULT_SIZE;

		// get FacesContext instance
		this.facesContext = FacesContext.getCurrentInstance();

		// get current locale
		this.currentLocale = this.facesContext.getViewRoot().getLocale();

		// creating instance of Sitis Web properties
		this.labels = ResourceBundle.getBundle("i18n.labels", this.currentLocale);

		// creating instance of menu object
		this.model = new DynamicMenuModel();

		// recovering the current logged person
		try {
			this.personEntity = FacesUtil.recoverObjectInSession(new PersonEntity());
		} catch (GeneralException e) {
		}

		// selecting user profile to build specific menu
		if (this.personEntity == null) {
			this.BuildMenuUSR();
		} else {
			if (this.personEntity.getGreenPersonProfileInitials().equals(GreenPersonProfileEnum.GREEN_SYSTEM_ADMINISTRATOR.getProfile())) {
				this.BuildMenuGAS();
			} else {
				this.BuildMenuUSR();
			}
		}
	}

	/**
	 * Accesses the attribute named 'model' and returns its value.
	 * 
	 * @return DynamicMenuModel - The menu model to rendering in the JSF page.
	 */
	public MenuModel getModel() {
		return model;
	}

	/**
	 * Builds the menu options according to the system administrator profile.
	 */
	private void BuildMenuGAS() {

		DefaultSubMenu experimentsSubMenu = this.SubMenu(model, "menu.experiments");
		ItemSubMenu(experimentsSubMenu, "menu.experimentSchedule", "", "experimentScheduleList.xhtml", "");
		ItemSubMenu(experimentsSubMenu, "menu.experiment", "", "experimentList.xhtml", "ui-icon-home");

		DefaultSubMenu paneControlSubMenu = this.SubMenu(this.model, "menu.controlPanel");
		ItemSubMenu(paneControlSubMenu, "menu.underConstruction", "", "underConstruction.xhtml", "ui-icon-person");

		DefaultSubMenu reportsSubMenu = this.SubMenu(this.model, "menu.reports");
		ItemSubMenu(reportsSubMenu, "menu.underConstruction", "", "underConstruction.xhtml", "ui-icon-person");

		DefaultSubMenu configurationsSubMenu = this.SubMenu(this.model, "menu.configurations");
		ItemSubMenu(configurationsSubMenu, "menu.underConstruction", "", "underConstruction.xhtml", "ui-icon-person");

		DefaultSubMenu helpSubMenu = this.SubMenu(this.model, "menu.help");
		ItemSubMenu(helpSubMenu, "menu.underConstruction", "", "help.xhtml", "ui-icon-help");

	}

	/**
	 * Builds the menu options according to the SITIS platform user profile.
	 */
	private void BuildMenuUSR() {
		DefaultSubMenu experimentsSubMenu = this.SubMenu(model, "menu.experiments");
		ItemSubMenu(experimentsSubMenu, "menu.experiments.new", "/portal/experiments", "experimentManager.xhtml", "", FilterEnum.MY.getFilterIntoString(), ActionEnum.NEW.getActionIntoString());
		ItemSubMenu(experimentsSubMenu, "menu.experiments.my", "/portal/experiments", "experimentManager.xhtml", "", FilterEnum.MY.getFilterIntoString(), ActionEnum.SHOW_LIST.getActionIntoString());
		// ItemSubMenu(experimentsSubMenu, "menu.experiments.all", "/portal/experiments", "experimentManager.xhtml", "", FilterEnum.ALL.getFilterIntoString(),
		// ActionEnum.SHOW_LIST.getActionIntoString());

		// DefaultSubMenu reportsSubMenu = this.SubMenu(this.model, "menu.reports");
		// ItemSubMenu(reportsSubMenu, "menu.underConstruction", "", "underConstruction.xhtml", "ui-icon-person");

		// DefaultSubMenu helpSubMenu = this.SubMenu(this.model, "menu.help");
		// ItemSubMenu(helpSubMenu, "menu.underConstruction", "", "help.xhtml", "ui-icon-help");
	}

	/**
	 * Builds the sub menu options.
	 */
	private DefaultSubMenu SubMenu(MenuModel model2, String titleSubMenu) {
		String label = this.labels.getString(titleSubMenu);
		DefaultSubMenu subMenu = new DefaultSubMenu(label);
		subMenu.setStyleClass(MenuController.MENU_ITEM_STYLE);
		model2.addElement(subMenu);
		return subMenu;
	}

	/**
	 * Builds the items of sub menu options.
	 */
	private void ItemSubMenu(DefaultSubMenu subMenu, String titleItemMenu, String specificFolder, String nameFileXHTML, String icon) {
		this.ItemSubMenu(subMenu, titleItemMenu, specificFolder, nameFileXHTML, icon, null, null);
	}

	private void ItemSubMenu(DefaultSubMenu subMenu, String titleItemMenu, String specificFolder, String nameFileXHTML, String icon, String filter,
			String action) {
		String label = this.labels.getString(titleItemMenu);
		DefaultMenuItem itemMenu = new DefaultMenuItem(label);
		itemMenu.setValue(label);
		itemMenu.setId(this.uiViewRoot.getClientId());
		String href = "";
		if (specificFolder == null || specificFolder == "") {
			href = nameFileXHTML;
		} else {
			href = specificFolder + "/" + nameFileXHTML;
		}
		// if (parameterKey != null && !parameterKey.isEmpty() && parameterValue != null && !parameterValue.isEmpty()) {
		// href += "?" + parameterKey + "=" + parameterValue;
		// }
		if (filter != null && !filter.isEmpty()) {
			href += "?filter=" + filter;
		}
		if (action != null && !action.isEmpty()) {
			href += "&action=" + action;
		}
		itemMenu.setHref(href);
		itemMenu.setIcon(icon);
		itemMenu.setUpdate("panelCenter_SITISWeb");
		itemMenu.setAjax(false);
		subMenu.addElement(itemMenu);
	}

	/**
	 * Returns the size of the left screen
	 * 
	 * @return int - The size of the left screen
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the size of the left screen
	 * 
	 * @param size
	 *            The size of the left screen
	 */
	public void setSize(int size) {
		this.size = size;
	}
}