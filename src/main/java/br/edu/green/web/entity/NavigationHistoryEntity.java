package br.edu.green.web.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DynamicMenuModel;

/**
 * <p>
 * <b>This class store all navigation history and its used to build a dynamic menu at the central form.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 23/12/2015 (creation date)
 */

public class NavigationHistoryEntity {

	// defining constants
	private static final String MENU_ITEM_STYLE = "sitisNavigationHistoryMenuItem";
	public static final boolean INITIALIZE_MENU = true;
	public static final boolean NOT_INITIALIZE_MENU = false;
	public static final boolean DISABLE_ITEM_MENU = false;
	public static final boolean ENABLE_ITEM_MENU = true;

	// defining list of navigation history menu items
	private Map<Integer, DefaultMenuItem> navigationHistoryItems;

	// defining counter of menu items
	private int counterID;

	/**
	 * Default constructor
	 */
	public NavigationHistoryEntity() {
		super();

		// creating class attributes
		this.navigationHistoryItems = new HashMap<>();

		// initializing counter of ID
		this.counterID = 0;

		// initializing list of navigation history
		this.initializeNavigationHistoryMenu();

	}

	/**
	 * Returns the menu with all the current navigation history
	 * 
	 * @return DynamicMenuModel - the current navigation history menu
	 */
	@SuppressWarnings("rawtypes")
	public DynamicMenuModel getNavigationHistoryMenu() {
		// creating new navigation history menu model
		DynamicMenuModel dynamicMenuModel = new DynamicMenuModel();

		// loading navigation history menu model
		Iterator iterator = this.navigationHistoryItems.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = (Entry) iterator.next();
			DefaultMenuItem defaultMenuItem = (DefaultMenuItem) entry.getValue();
			dynamicMenuModel.addElement(defaultMenuItem);
		}

		// returning navigation history menu model
		return dynamicMenuModel;
	}

	/**
	 * Initializes the navigation history menu.
	 */
	public void initializeNavigationHistoryMenu() {
		// creating new instance of navigation history menu
		this.navigationHistoryItems.clear();

		// initializing counter of ID
		this.counterID = 0;

		// adding the first menu item
		this.addNavigationHistoryItem("SITIS", "activitiesSitisWebProject.xhtml", false);
	}

	/**
	 * Adds a function in the navigation history used in the menu.
	 * 
	 * @param function
	 *            The function name to register in the navigation history
	 * @param url
	 *            The URL name of the XHTML page
	 */
	@SuppressWarnings("rawtypes")
	public void addNavigationHistoryItem(String function, String url, boolean disabled) {

		// defining variables
		int functionKey = 0;

		// verifying if the function is already in the map and after, removing all functions after it.
		Iterator iterator = this.navigationHistoryItems.entrySet().iterator();
		while (iterator.hasNext()) {
			// obtaining an item
			Entry entry = (Entry) iterator.next();
			DefaultMenuItem defaultMenuItem = (DefaultMenuItem) entry.getValue();

			// verifying if it is present in the map
			if (defaultMenuItem.getValue().equals(function)) {

				// loading the function key of the map
				functionKey = (int) entry.getKey();

				// disabling the function
				defaultMenuItem.setDisabled(true);

				// exiting of the loop
				break;
			}

			// enabling the function
			defaultMenuItem.setDisabled(false);
		}

		// verifying if the function already exist in the map
		if (functionKey > 0) {
			// removing all the next functions after the current function
			iterator = this.navigationHistoryItems.entrySet().iterator();
			while (iterator.hasNext()) {
				// obtaining an item
				Entry entry = (Entry) iterator.next();

				// removing function
				if ((int) entry.getKey() > functionKey) {
					iterator.remove();
				}
			}

			// returing with success
			return;

		}

		// enabling all item before add new item and verifying if the new function already exists in the history

		// increment menu item
		++this.counterID;

		// creating a new menu item
		DefaultMenuItem item = new DefaultMenuItem();
		item.setValue(function);
		item.setUrl(url);
		item.setStyleClass(NavigationHistoryEntity.MENU_ITEM_STYLE);
		item.setDisabled(disabled);
		item.setId(Integer.toString(this.counterID));
		item.setAjax(true);

		// adding new menu item in the list
		this.navigationHistoryItems.put(this.counterID, item);
	}
}
