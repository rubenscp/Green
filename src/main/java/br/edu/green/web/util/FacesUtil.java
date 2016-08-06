package br.edu.green.web.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * FacesUtil.java: This class offers static methods related with JSF framework.
 * 
 * @author Sergio Lopes Jr.
 * @version 0.1
 * @since 25/08/2015
 * 
 */

public class FacesUtil {

	/**
	 * 
	 * @return
	 */
	public static FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	/**
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getMessage(String key) {
		FacesContext context = FacesUtil.getContext();
		ResourceBundle resourceBundle = context.getApplication().getResourceBundle(context, "msg");
		try {
			return resourceBundle.getString(key);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		FacesContext context = FacesUtil.getContext();
		ResourceBundle resourceBundle = context.getApplication().getResourceBundle(context, "cfg");
		try {
			return resourceBundle.getString(key);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
	 * @param fieldId
	 * @param resumeMsg
	 * @param detailMsg
	 */
	public static void showSucessMessage(String fieldId, String resumeMsg, String detailMsg) {
		FacesUtil.getContext().addMessage(fieldId, new FacesMessage(FacesMessage.SEVERITY_INFO, resumeMsg, detailMsg));
	}

	/**
	 * 
	 * @param fieldId
	 * @param resumeMsg
	 * @param detailMsg
	 */
	public static void showWarningMessage(String fieldId, String resumeMsg, String detailMsg) {
		FacesUtil.getContext().addMessage(fieldId, new FacesMessage(FacesMessage.SEVERITY_WARN, resumeMsg, detailMsg));
	}

	/**
	 * 
	 * @param fieldId
	 * @param resumeMsg
	 * @param detailMsg
	 */
	public static void showErrorMessage(String fieldId, String resumeMsg, String detailMsg) {
		FacesUtil.getContext().addMessage(fieldId, new FacesMessage(FacesMessage.SEVERITY_ERROR, resumeMsg, detailMsg));
	}
}
