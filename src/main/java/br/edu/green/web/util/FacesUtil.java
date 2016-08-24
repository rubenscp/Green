package br.edu.green.web.util;

import java.io.Serializable;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.service.ApplicationMessageService;

import com.google.gson.Gson;

/**
 * FacesUtil.java: This class offers static methods related with JSF framework.
 * 
 * @author Sergio Lopes Jr.
 * @version 0.1
 * @since 25/08/2015
 * 
 */

public class FacesUtil implements Serializable {

	private static final long serialVersionUID = 8385132482030338481L;

	/**
	 * Returns the context of JSF.
	 * 
	 * @return FacesContext - The context of JSF
	 * @throws GeneralException
	 *             The general exception object
	 */
	public static FacesContext getContext() throws GeneralException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext;
	}

	/**
	 * Returns the external context of JSF.
	 * 
	 * @return ExternalContext - The external context of JSF.
	 * @throws GeneralException
	 *             The general exception object
	 */
	public static ExternalContext getExternalContext() throws GeneralException {
		ExternalContext externalContext = (ExternalContext) FacesContext.getCurrentInstance().getExternalContext();
		return externalContext;
	}

	/**
	 * Returns the http session object in the JSF context.
	 * 
	 * @return HttpSession - The http session object in the JSF context.
	 * @throws GeneralException
	 *             The general exception object
	 */
	public static HttpSession getSession() throws GeneralException {
		HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return httpSession;
	}

	/**
	 * Returns the request object in the JSF context.
	 * 
	 * @return HttpServletRequest - The request object in the JSF context.
	 * @throws GeneralException
	 *             The general exception object
	 */
	public static HttpServletRequest gethttpServeletRequest() throws GeneralException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return httpServletRequest;
	}

	/**
	 * Returns the request context of the JSF.
	 * 
	 * @return HttpServletRequest - The request object in the JSF context.
	 * @throws GeneralException
	 *             The general exception object
	 */
	public static RequestContext getRequestContext() throws GeneralException {
		return RequestContext.getCurrentInstance();
	}

	/**
	 * Stores the object in the session of JSF context.
	 * 
	 * @param <T>
	 *            The object to be stored in the session of JSF context
	 * @param t
	 *            A object to storage at the application context session
	 * @throws GeneralException
	 *             A failure at the storage of the object
	 */
	public static <T> void storeObjectInSession(T t) throws GeneralException {
		try {
			// storing the object like a session attribute
			HttpSession httpSession = FacesUtil.getSession();
			httpSession.setAttribute(Util.className(t.getClass().getName()), t);

		} catch (Exception e) {
			// defining Google JSON object
			Gson gson = new Gson();

			// defining instance of the application message
			ApplicationMessageService applicationMessage = ApplicationMessageService.getInstanceof();

			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_STORE_OBJECT_IN_SESSION, applicationMessage.getMessage(Code.FACES_EXCEPTION_STORE_OBJECT_IN_SESSION.name(), FacesUtil.class.getSimpleName())));
			ge.setClassName(FacesUtil.class.getName());
			ge.setMethodName("storeObjectInSession");
			ge.setExceptionOriginalMessage(gson.toJson(e.getCause()));

			// returning the general exception
			throw ge;
		}
	}

	/**
	 * Recovery the object stored in the session of JSF context.
	 * 
	 * @param t
	 *            The class type of the object to be recovery in the session of JSF context
	 * @return T - The object to storage at the application context session
	 * @throws GeneralException
	 *             A failure at the storage of the object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T recoverObjectInSession(T t) throws GeneralException {
		try {
			// recoverying the object stored
			HttpSession httpSession = FacesUtil.getSession();
			return (T) httpSession.getAttribute(Util.className(t.getClass().getName()));

		} catch (Exception e) {
			// defining Google JSON object
			Gson gson = new Gson();

			// defining instance of the application message
			ApplicationMessageService applicationMessage = ApplicationMessageService.getInstanceof();

			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_RECOVERY_OBJECT_IN_SESSION, applicationMessage.getMessage(Code.FACES_EXCEPTION_RECOVERY_OBJECT_IN_SESSION.name(), FacesUtil.class.getSimpleName())));
			ge.setClassName(FacesUtil.class.getName());
			ge.setMethodName("recoveryObjectInSession");
			ge.setExceptionOriginalMessage(gson.toJson(e.getCause()));

			// returning the general exception
			throw ge;
		}
	}

	// public static String getMessage(String key) throws GeneralException {
	// FacesContext context = FacesUtil.getContext();
	// ResourceBundle resourceBundle = context.getApplication().getResourceBundle(context, "msg");
	// return resourceBundle.getString(key);
	// }
	//
	// public static String getConfig(String key) throws GeneralException {
	// FacesContext context = FacesUtil.getContext();
	// ResourceBundle resourceBundle = context.getApplication().getResourceBundle(context, "cfg");
	// return resourceBundle.getString(key);
	// }
	//
	// public static void showSucessMessage(String fieldId, String resumeMsg, String detailMsg) throws GeneralException {
	// FacesUtil.getContext().addMessage(fieldId, new FacesMessage(FacesMessage.SEVERITY_INFO, resumeMsg, detailMsg));
	// }
	//
	// public static void showWarningMessage(String fieldId, String resumeMsg, String detailMsg) throws GeneralException {
	// FacesUtil.getContext().addMessage(fieldId, new FacesMessage(FacesMessage.SEVERITY_WARN, resumeMsg, detailMsg));
	// }
	//
	// public static void showErrorMessage(String fieldId, String resumeMsg, String detailMsg) throws GeneralException {
	// FacesUtil.getContext().addMessage(fieldId, new FacesMessage(FacesMessage.SEVERITY_ERROR, resumeMsg, detailMsg));
	// }
	//

	/**
	 * Returns the parameter stored in the parameters map of the JSF external context.
	 * 
	 * @return HttpServletRequest - The request object in the JSF context.
	 * @throws GeneralException
	 *             The general exception object
	 */
	// public static String getParam(String name) throws GeneralException {
	// Map<String, String> params = FacesUtil.getExternalContext().getRequestParameterMap();
	// return params.get(name);
	// }

	/**
	 * 
	 * @return
	 * @throws GeneralException
	 *             The general exception object
	 */
	// public static String getFunctionTitle() throws GeneralException {
	// try {
	// String functionTitle = FacesUtil.getParam("functionTitle");
	// if (functionTitle == null) {
	// return "Sem titulo";
	// } else {
	// return functionTitle;
	// }
	// } catch (Exception e) {
	//
	// // setting specific error
	// ApplicationMessageService applicationMessage = ApplicationMessageService.getInstanceof();
	// LogService logService = new LogService(null);
	// ProcessingResult processingResult = new ProcessingResult(Code.IMPORT_SIEXP_EXPERIMENT_ERROR_VARIABLES,
	// applicationMessage.getMessage(Code.IMPORT_SIEXP_EXPERIMENT_ERROR_VARIABLES.name()));
	//
	// // Creating new instance of general exception
	// GeneralException ge = new GeneralException();
	// ge.setProcessingResult(processingResult);
	// ge.setClassName("FacesUtil");
	// ge.setMethodName("getFunctionTitle");
	// ge.setExceptionOriginalMessage(e.getMessage()); // the original exception message
	//
	// // writing in the message log
	// logService.error(ge.toString());
	//
	// // throwing general exception
	// throw ge;
	// }
	// }
	/**
	 * Recovery the object stored in the session of JSF context.
	 * 
	 * @param <T>
	 * 
	 * @param t
	 *            The class type of the object to be recovery in the session of JSF context
	 * @return T - The object to storage at the application context session
	 * @throws GeneralException
	 *             A failure at the storage of the object
	 */

	public static <T> void removeObjectInSession(T t) throws GeneralException {
		try {
			// removing the object stored
			HttpSession httpSession = FacesUtil.getSession();
			httpSession.removeAttribute(Util.className(t.getClass().getName()));

		} catch (Exception e) {
			// defining Google JSON object
			Gson gson = new Gson();

			// defining instance of the application message
			ApplicationMessageService applicationMessage = ApplicationMessageService.getInstanceof();

			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_REMOVE_OBJECT_IN_SESSION, applicationMessage.getMessage(Code.FACES_EXCEPTION_REMOVE_OBJECT_IN_SESSION.name(), FacesUtil.class.getSimpleName())));
			ge.setClassName(FacesUtil.class.getName());
			ge.setMethodName("recoveryObjectInSession");
			ge.setExceptionOriginalMessage(gson.toJson(e.getCause()));

			// returning the general exception
			throw ge;
		}
	}

	/**
	 * Returns the value of parameter defined in name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return String - The value of parameter name.
	 */
	// public static String getParameter(String name) throws GeneralException {
	// try {
	// // recoverying the object stored
	// HttpSession httpSession = FacesUtil.getSession();
	// return (String) httpSession.getAttribute(name);
	//
	// } catch (Exception e) {
	// // defining Google JSON object
	// Gson gson = new Gson();
	//
	// // defining instance of the application message
	// ApplicationMessageService applicationMessage = ApplicationMessageService.getInstanceof();
	//
	// // configuring and throwing details of the actual exception
	// GeneralException ge = new GeneralException();
	// ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_RECOVERY_OBJECT_IN_SESSION,
	// applicationMessage.getMessage(Code.FACES_EXCEPTION_RECOVERY_OBJECT_IN_SESSION.name(), FacesUtil.class.getSimpleName())));
	// ge.setClassName(FacesUtil.class.getName());
	// ge.setMethodName("getParameter");
	// ge.setExceptionOriginalMessage(gson.toJson(e.getCause()));
	//
	// // returning the general exception
	// throw ge;
	// }
	// }

	/**
	 * Returns the value (object) of parameter defined in name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return String - The value of parameter name.
	 */
	public static Object getSessionParameter(String name) throws GeneralException {
		try {
			// returning the object stored in the session context
			return FacesUtil.getSession().getAttribute(name);

		} catch (Exception e) {
			// defining Google JSON object
			Gson gson = new Gson();

			// defining instance of the application message
			ApplicationMessageService applicationMessage = ApplicationMessageService.getInstanceof();

			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_RECOVERY_OBJECT_IN_SESSION, applicationMessage.getMessage(Code.FACES_EXCEPTION_RECOVERY_OBJECT_IN_SESSION.name(), FacesUtil.class.getSimpleName())));
			ge.setClassName(FacesUtil.class.getName());
			ge.setMethodName("getParameter");
			ge.setExceptionOriginalMessage(gson.toJson(e.getCause()));

			// returning the general exception
			throw ge;
		}
	}

	/**
	 * Sets the value of parameter defined in name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @param value
	 *            The value of parameter name.
	 */
	// public static void setParameter(String name, String value) throws GeneralException {
	// try {
	// // storing the object like a session attribute
	// HttpSession httpSession = FacesUtil.getSession();
	// httpSession.setAttribute(name, value);
	//
	// } catch (Exception e) {
	// // defining Google JSON object
	// Gson gson = new Gson();
	//
	// // defining instance of the application message
	// ApplicationMessageService applicationMessage = ApplicationMessageService.getInstanceof();
	//
	// // configuring and throwing details of the actual exception
	// GeneralException ge = new GeneralException();
	// ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_STORE_OBJECT_IN_SESSION,
	// applicationMessage.getMessage(Code.FACES_EXCEPTION_STORE_OBJECT_IN_SESSION.name(), FacesUtil.class.getSimpleName())));
	// ge.setClassName(FacesUtil.class.getName());
	// ge.setMethodName("setParameter");
	// ge.setExceptionOriginalMessage(gson.toJson(e.getCause()));
	//
	// // returning the general exception
	// throw ge;
	// }
	// }

	/**
	 * Sets the value of parameter defined in name.
	 * 
	 * @param name
	 *            The parameter name.
	 * @param value
	 *            The value of parameter name (object).
	 */
	public static void setSessionParameter(String name, Object value) throws GeneralException {
		try {
			// storing the object like a session attribute
			FacesUtil.getSession().setAttribute(name, value);

		} catch (Exception e) {
			// defining Google JSON object
			Gson gson = new Gson();

			// defining instance of the application message
			ApplicationMessageService applicationMessage = ApplicationMessageService.getInstanceof();

			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_STORE_OBJECT_IN_SESSION, applicationMessage.getMessage(Code.FACES_EXCEPTION_STORE_OBJECT_IN_SESSION.name(), FacesUtil.class.getSimpleName())));
			ge.setClassName(FacesUtil.class.getName());
			ge.setMethodName("setParameter");
			ge.setExceptionOriginalMessage(gson.toJson(e.getCause()));

			// returning the general exception
			throw ge;
		}
	}

	/**
	 * Returns the value of parameter name in the parameters map.
	 * 
	 * @param name
	 *            The parameter name.
	 * @return String - The value of parameter name.
	 */
	public static String getParameterMap(String name) throws GeneralException {
		// Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		// String xxx = requestParams.get(name);
		// return xxx;
		// return (String) requestParams.get(name);

		try {
			// recoverying the object stored
			Map<String, String> params = FacesUtil.getExternalContext().getRequestParameterMap();
			String value = (String) params.get(name);
			return value;

		} catch (Exception e) {
			// defining Google JSON object
			Gson gson = new Gson();

			// defining instance of the application message
			ApplicationMessageService applicationMessage = ApplicationMessageService.getInstanceof();

			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_RECOVERY_OBJECT_IN_SESSION, applicationMessage.getMessage(Code.FACES_EXCEPTION_RECOVERY_OBJECT_IN_SESSION.name(), FacesUtil.class.getSimpleName())));
			ge.setClassName(FacesUtil.class.getName());
			ge.setMethodName("getParameterMap");
			ge.setExceptionOriginalMessage(gson.toJson(e.getCause()));

			// returning the general exception
			throw ge;
		}
	}

	// @SuppressWarnings("static-access")
	// public static void updateMessages() throws GeneralException {
	// // FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message Title", "Message body");
	// // RequestContext.getCurrentInstance().showMessageInDialog(message);
	// // RequestContext.getCurrentInstance().update(componentId);
	//
	// try {
	// FacesUtil.getContext().getCurrentInstance().renderResponse();
	// } catch (Exception e) {
	// // defining Google JSON object
	// Gson gson = new Gson();
	//
	// // defining instance of the application message
	// ApplicationMessageService applicationMessage = ApplicationMessageService.getInstanceof();
	//
	// // configuring and throwing details of the actual exception
	// GeneralException ge = new GeneralException();
	// ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_INPUT_OUTPUT,
	// applicationMessage.getMessage(Code.FACES_EXCEPTION_INPUT_OUTPUT.name(), FacesUtil.class.getSimpleName())));
	// ge.setClassName(FacesUtil.class.getName());
	// ge.setMethodName("getParameterMap");
	// ge.setExceptionOriginalMessage(gson.toJson(e.getCause()));
	//
	// // returning the general exception
	// throw ge;
	// }
	// }
}
