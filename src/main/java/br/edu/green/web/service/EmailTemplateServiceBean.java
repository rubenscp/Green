package br.edu.green.web.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;

import br.edu.green.web.dao.EntityManagerWrapperService;
import br.edu.green.web.entity.EmailTemplateEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;

/**
 * <p>
 * <b> This class defines the Email Template services which contains all methods that access SITIS Data Base.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 17/01/2017 (creation date)
 */

@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class EmailTemplateServiceBean extends GeneralService implements EmailTemplateService {

	// defining serial version
	private static final long serialVersionUID = 2256518042150495479L;

	@EJB
	EntityManagerWrapperService emws;

	// classes attributes
	// private ActionEnum action;

	/**
	 * Default constructor initializing the basics services.
	 * 
	 * @throws GeneralException
	 *             The generic exception of the SITIS.
	 */
	public EmailTemplateServiceBean() throws GeneralException {
		super();

		// initializing services
		this.initializeServices();
	}

	/**
	 * Saves (insert or update) an experiment schedule at the data base. This method implements all business rules related to experiment schedule.
	 * 
	 * @param greenhouse
	 *            The greenhouse object to be save
	 * @return ProcessingResultsList - The processing result map
	 * @throws GeneralException
	 *             The generic exception of the SITIS.
	 */
	// public ProcessingResultsList save(GreenhouseEntity greenhouse) throws GeneralException {
	// // clear the processing result map
	// super.processingResultMap.clear();
	//
	// // consisting experiment schedule
	// if (!this.consistSave(greenhouse)) {
	// // returning operation performed with error
	// return this.processingResultMap;
	// }
	//
	// // defining action
	// this.action = (greenhouse.getId() == 0 ? ActionEnum.NEW : ActionEnum.UPDATE);
	//
	// try {
	// // getting user transaction object
	// this.userTransaction = this.ejbContext.getUserTransaction();
	//
	// // starting bean transaction
	// this.userTransaction.begin();
	//
	// // saving experiment schedule
	// greenhouse = this.saveGreenhouse(greenhouse);
	//
	// // defining proper message related to result with success
	// if (action.equals(ActionEnum.NEW)) {
	// super.processingResultMap.add(new ProcessingResultEntity(Code.GREENHOUSE_INFORMATION_SUCCESS_NEW,
	// applicationMessage.getMessage(Code.GREENHOUSE_INFORMATION_SUCCESS_NEW.name(), greenhouse.getName())));
	// } else {
	// super.processingResultMap.add(new ProcessingResultEntity(Code.GREENHOUSE_INFORMATION_SUCCESS_UPDATE,
	// applicationMessage.getMessage(Code.GREENHOUSE_INFORMATION_SUCCESS_UPDATE.name(), greenhouse.getName())));
	// }
	//
	// // returning operation performed with success
	// return this.processingResultMap;
	//
	// } catch (Exception e) {
	// throw this.handleException(e.getCause().getCause().getCause(), this.getClass().getSimpleName(), "save", Code.EJB_EXCEPTION,
	// this.getClass().getSimpleName());
	//
	// } finally {
	// try {
	// // committing bean transaction
	// if (this.userTransaction != null) {
	// this.userTransaction.commit();
	// }
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "save", Code.EJB_EXCEPTION, this.getClass().getSimpleName());
	// }
	// }
	// }

	/**
	 * Consists all attributes of the greenhouse and implements the business rule.
	 * 
	 * @param greenhouseEntity
	 *            The experiment schedule object
	 * @return boolean - The logical result
	 * @throws GeneralException
	 *             The generic exception of the SITIS.
	 */
	// private boolean consistSave(GreenhouseEntity greenhouseEntity) throws GeneralException {
	// // error indicator of the processing result
	// boolean correct = true;
	//
	// // checking if experiment schedule is valid
	// if (greenhouseEntity == null) {
	// this.processingResultMap.add(new ProcessingResultEntity(Code.GREENHOUSE_ERROR_NO_GREENHOUSE,
	// this.applicationMessage.getMessage(Code.GREENHOUSE_ERROR_NO_GREENHOUSE.name())));
	// return false;
	// }
	//
	// // checking fields
	// if (greenhouseEntity.getName() == null || greenhouseEntity.getName().isEmpty()) {
	// this.processingResultMap.add(new ProcessingResultEntity(Code.GREENHOUSE_ERROR_NO_NAME,
	// this.applicationMessage.getMessage(Code.GREENHOUSE_ERROR_NO_NAME.name())));
	// correct = false;
	// }
	// if (greenhouseEntity.getAddress() == null || greenhouseEntity.getAddress().isEmpty()) {
	// this.processingResultMap.add(new ProcessingResultEntity(Code.GREENHOUSE_ERROR_NO_ADDRESS,
	// this.applicationMessage.getMessage(Code.GREENHOUSE_ERROR_NO_ADDRESS.name())));
	// correct = false;
	// }
	// if (greenhouseEntity.getZipCode() == 0) {
	// this.processingResultMap.add(new ProcessingResultEntity(Code.GREENHOUSE_ERROR_NO_ZIP_CODE,
	// this.applicationMessage.getMessage(Code.GREENHOUSE_ERROR_NO_ZIP_CODE.name())));
	// correct = false;
	// }
	// if (greenhouseEntity.getCity() == null || greenhouseEntity.getCity().isEmpty()) {
	// this.processingResultMap.add(new ProcessingResultEntity(Code.GREENHOUSE_ERROR_NO_CITY,
	// this.applicationMessage.getMessage(Code.GREENHOUSE_ERROR_NO_CITY.name())));
	// correct = false;
	// }
	// if (greenhouseEntity.getState() == null || greenhouseEntity.getState().isEmpty()) {
	// this.processingResultMap.add(new ProcessingResultEntity(Code.GREENHOUSE_ERROR_NO_STATE,
	// this.applicationMessage.getMessage(Code.GREENHOUSE_ERROR_NO_STATE.name())));
	// correct = false;
	// }
	//
	// // returning the result of consistency
	// return correct;
	// }

	/**
	 * Saves the data of the greenhouse. This action can be an operation of creating or updating.
	 * 
	 * @param greenhouse
	 *            The greenhouse object.
	 * @return GreenhouseEntity - The new greenhouse object with id actualized or simply the greenhouse object.
	 * @throws GeneralException
	 *             The generic exception of the SITIS.
	 */
	// private GreenhouseEntity saveGreenhouse(GreenhouseEntity greenhouse) throws GeneralException {
	// try {
	// if (greenhouse.getId() == 0) {
	// this.emws.create(greenhouse);
	// return greenhouse;
	// } else {
	// return this.emws.update(greenhouse);
	// }
	//
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "save", ((greenhouse.getId() == 0) ? Code.DAO_EXCEPTION_CREATE :
	// Code.DAO_EXCEPTION_UPDATE), this.getClass().getSimpleName());
	// }
	// }

	/**
	 * Deletes an greenhouse at the data base checking all dependencies.
	 * 
	 * @param greenhouse
	 *            The greenhouse object to be delete
	 * @return ProcessingResultsList - The processing result map
	 * @throws GeneralException
	 *             The generic exception of the SITIS.
	 */
	// @Override
	// public ProcessingResultsList delete(GreenhouseEntity greenhouse) throws GeneralException {
	// try {
	// // clear the processing result map
	// super.processingResultMap.clear();
	//
	// // consists experiment schedule
	// // if (this.consist(experimentSchedule)) {
	//
	// // getting user transaction object
	// this.userTransaction = this.ejbContext.getUserTransaction();
	//
	// // starting bean transaction
	// this.userTransaction.begin();
	//
	// // saving public identifier and title of the experiment
	// String greenhouseName = greenhouse.getName();
	//
	// // deleting experiment schedule
	// this.deleteGreenhouse(greenhouse);
	//
	// // defining proper message related to result with success
	// super.processingResultMap.add(new ProcessingResultEntity(Code.GREENHOUSE_INFORMATION_SUCCESS_DELETE,
	// applicationMessage.getMessage(Code.GREENHOUSE_INFORMATION_SUCCESS_DELETE.name(), greenhouseName)));
	//
	// // returning operation performed with success
	// return this.processingResultMap;
	//
	// } catch (Exception e) {
	// throw this.handleException(e.getCause().getCause().getCause(), this.getClass().getSimpleName(), "delete", Code.EJB_EXCEPTION,
	// this.getClass().getSimpleName());
	//
	// } finally {
	// try {
	// // committing bean transaction
	// if (this.userTransaction != null) {
	// this.userTransaction.commit();
	// }
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "delete", Code.EJB_EXCEPTION, this.getClass().getSimpleName());
	// }
	// }
	// }

	/**
	 * Deletes the greenhouse.
	 * 
	 * @param greenhouse
	 *            The greenhouse.
	 * @throws GeneralException
	 *             The generic exception of the SITIS.
	 */
	// private void deleteGreenhouse(GreenhouseEntity greenhouse) throws GeneralException {
	// try {
	// this.emws.remove(greenhouse);
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "deleteGreenhouse", Code.DAO_EXCEPTION_DELETE, this.getClass().getSimpleName());
	// }
	// }

	/**
	 * Finds a greenhouse by its identification.
	 * 
	 * @param id
	 *            The identification of the greenhouse.
	 * @return GreenhouseEntity - The greenhouse.
	 * @throws GeneralException
	 *             The generic exception of the SITIS.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public EmailTemplateEntity findByIdentifier(String identifier) throws GeneralException {
		try {

			// getting user transaction object
			this.userTransaction = this.ejbContext.getUserTransaction();

			// starting bean transaction
			this.userTransaction.begin();

			String sql = "select p from EmailTemplateEntity p where p.identifier = :identifier";
			Query query = this.emws.createQuery(sql);
			query.setParameter("identifier", identifier);
			query.setHint("org.hibernate.cacheable", Boolean.TRUE);
			List<EmailTemplateEntity> emailTemplates = query.getResultList();
			if (emailTemplates.size() > 0) {
				return emailTemplates.get(0);
			} else {
				return null;
			}

		} catch (Exception e) {
			throw this.handleException(e.getCause().getCause().getCause(), this.getClass().getSimpleName(), "findByIdentifier", Code.EJB_EXCEPTION, this.getClass().getSimpleName());

		} finally {
			try {
				// committing bean transaction
				if (this.userTransaction != null) {
					this.userTransaction.commit();
				}
			} catch (Exception e) {
				// configuring and throwing details of the actual exception
				throw this.handleException(e, this.getClass().getSimpleName(), "findByIdentifier", Code.EJB_EXCEPTION, this.getClass().getSimpleName());
			}
		}

	}

	/**
	 * Finds all greenhouses according a order criteria.
	 * 
	 * @param orderBy
	 *            The order criteria.
	 * @return List<GreenhouseEntity> - The list of greenhouses.
	 * @throws GeneralException
	 *             The generic exception of the SITIS.
	 */
	// @Override
	// public List<GreenhouseEntity> findAll(String firstFieldOrder, String firstFieldDirection) throws GeneralException {
	// try {
	// String sql = "select p from GreenhouseEntity p";
	// if (firstFieldOrder != null) {
	// sql += " order by " + firstFieldOrder;
	// }
	// if (firstFieldDirection != null) {
	// sql += " " + firstFieldDirection;
	// }
	// List<GreenhouseEntity> greenhouses = this.emws.findAll(sql);
	// return greenhouses;
	//
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "findAll", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());
	// }
	// }
}
