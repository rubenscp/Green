package br.edu.green.web.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import br.edu.green.web.dao.EntityManagerWrapperService;
import br.edu.green.web.entity.ImageEntity;
import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.entity.enumerate.ActionEnum;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.validate.ProcessingResultsList;

/**
 * <p>
 * <b>This class defines the Image services which contains all operations about images.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 15/11/2016 (creation date)
 */

@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ImageServiceBean extends GeneralService implements ImageService {

	// defining serial version
	private static final long serialVersionUID = -6623054735226159563L;

	// defining constants
	public static final String ASCENDING = "asc";
	public static final String DESCENDING = "desc";
	// public static final String PUBLIC_IDENTIFIER = "p.publicIdentifier";

	// EJB objects
	@EJB
	EntityManagerWrapperService emws;

	@EJB
	PersonService personService;

	// classes attributes
	private ActionEnum action;

	/**
	 * Default constructor
	 * 
	 * @throws GeneralException
	 *             The general exception object
	 */
	public ImageServiceBean() throws GeneralException {
		super();

		// initializing services
		this.initializeServices();
	}

	/**
	 * Saves (insert or update) an image at the data base. This method implements all business rules related to image.
	 * 
	 * @param image
	 *            The image object to be save
	 * @return ProcessingResultMap - The processing result map
	 * @throws GeneralException
	 *             The generic exception of SITIS.
	 */
	public ProcessingResultsList save(ImageEntity image) throws GeneralException {
		// clear the processing result map
		super.processingResultMap.clear();

		// consisting experiment schedule
		// if (!this.consistSave(experiment)) {
		// // returning operation performed with error
		// return this.processingResultMap;
		// }

		// defining action
		this.action = (image.getId() == 0 ? ActionEnum.NEW : ActionEnum.UPDATE);

		try {
			// getting user transaction object
			this.userTransaction = this.ejbContext.getUserTransaction();

			// starting bean transaction
			this.userTransaction.begin();

			// saving experiment schedule
			image = this.saveImage(image);

			// defining proper message related to result with success
			if (action.equals(ActionEnum.NEW)) {
				super.processingResultMap.add(new ProcessingResultEntity(Code.IMAGE_INFORMATION_SUCCESS_UPLOAD_IMAGE, applicationMessage.getMessage(Code.IMAGE_INFORMATION_SUCCESS_UPLOAD_IMAGE.name(), image.getExternalName())));
			} else {
				super.processingResultMap.add(new ProcessingResultEntity(Code.IMAGE_INFORMATION_SUCCESS_UPLOAD_IMAGE, applicationMessage.getMessage(Code.IMAGE_INFORMATION_SUCCESS_UPLOAD_IMAGE.name(), image.getExternalName())));
			}

			// returning operation performed with success
			return this.processingResultMap;

		} catch (Exception e) {
			throw this.handleException(e.getCause().getCause().getCause(), this.getClass().getSimpleName(), "save", Code.EJB_EXCEPTION, this.getClass().getSimpleName());

		} finally {
			try {
				// committing bean transaction
				if (this.userTransaction != null) {
					this.userTransaction.commit();
				}
			} catch (Exception e) {
				// configuring and throwing details of the actual exception
				throw this.handleException(e, this.getClass().getSimpleName(), "save", Code.EJB_EXCEPTION, this.getClass().getSimpleName());
			}
		}
	}

	/**
	 * Saves the data of the experiment. This action can be an operation of creating or updating.
	 * 
	 * @param image
	 *            The image.
	 * @return ImageEntity The image.
	 * @throws GeneralException
	 *             The general exception object
	 */
	private ImageEntity saveImage(ImageEntity image) throws GeneralException {
		try {
			if (image.getId() == 0) {
				this.emws.create(image);
				return image;
			} else {
				return this.emws.update(image);
			}

		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "saveImage", ((image.getId() == 0) ? Code.DAO_EXCEPTION_CREATE : Code.DAO_EXCEPTION_UPDATE), this.getClass().getSimpleName());
		}
	}

	/**
	 * Deletes an image.
	 * 
	 * @param image
	 *            The image object to be delete
	 * @return ProcessingResultMap - The processing result map
	 * @throws GeneralException
	 *             The generic exception of Green
	 */
	@Override
	public ProcessingResultsList delete(ImageEntity image) throws GeneralException {
		try {

			// clear the processing result map
			super.processingResultMap.clear();

			// consists experiment schedule
			// if (this.consist(experimentSchedule)) {

			// getting user transaction object
			this.userTransaction = this.ejbContext.getUserTransaction();

			// starting bean transaction
			this.userTransaction.begin();

			// saving public identifier and title of the experiment
			String title = image.getExternalName();

			// deleting experiment schedule
			this.deleteImage(image);

			// defining proper message related to result with success
			super.processingResultMap.add(new ProcessingResultEntity(Code.EXPERIMENT_INFORMATION_SUCCESS_DELETE_EXPERIMENT, applicationMessage.getMessage(Code.EXPERIMENT_INFORMATION_SUCCESS_DELETE_EXPERIMENT.name(), title)));

			// }

			// returning operation performed with success
			return this.processingResultMap;

		} catch (Exception e) {
			throw this.handleException(e.getCause().getCause().getCause(), this.getClass().getSimpleName(), "delete", Code.EJB_EXCEPTION, this.getClass().getSimpleName());

		} finally {
			try {
				// committing bean transaction
				if (this.userTransaction != null) {
					this.userTransaction.commit();
				}
			} catch (Exception e) {
				// configuring and throwing details of the actual exception
				throw this.handleException(e, this.getClass().getSimpleName(), "delete", Code.EJB_EXCEPTION, this.getClass().getSimpleName());
			}
		}
	}

	/**
	 * Deletes a image of experiment.
	 * 
	 * @param image
	 *            The image of experiment.
	 * @throws GeneralException
	 *             The general exception object
	 */
	private void deleteImage(ImageEntity image) throws GeneralException {
		try {
			this.emws.find(ImageEntity.class, image.getId());
			this.emws.remove(image);
		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "deleteImage", Code.DAO_EXCEPTION_DELETE, this.getClass().getSimpleName());
		}
	}

	/**
	 * Finds all Green experiment according to a criteria.
	 * 
	 * @param orderBy
	 *            The order criteria of the search.
	 * @return List<ExperimentEntity> The Green experiments list
	 * @throws GeneralException
	 *             The general exception object
	 */
	// @Override
	// public List<ExperimentEntity> findAll(String firstFieldOrder, String firstFieldDirection) throws GeneralException {
	// try {
	// String sql = "select p from ExperimentEntity p";
	// if (firstFieldOrder != null) {
	// sql += " order by " + firstFieldOrder;
	// }
	// if (firstFieldDirection != null) {
	// sql += " " + firstFieldDirection;
	// }
	//
	// List<ExperimentEntity> experiments = this.emws.findAll(sql);
	// return experiments;
	//
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "findAll", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());
	// }
	// }

	/**
	 * Finds all Green experiment where one person is owner by experiment.
	 * 
	 * @param ownerPerson
	 *            The owner person of the experiment
	 * @return List<ExperimentEntity> The Green experiments list
	 * @throws GeneralException
	 *             The general exception object
	 */
	// @Override
	// @SuppressWarnings("unchecked")
	// public List<ExperimentEntity> findExperimentsByOwner(PersonEntity ownerPerson, String firstFieldOrder, String firstFieldDirection) throws
	// GeneralException {
	// try {
	// // getting user transaction object
	// this.userTransaction = this.ejbContext.getUserTransaction();
	//
	// // starting bean transaction
	// this.userTransaction.begin();
	//
	// String sql =
	// "select p from ExperimentEntity p, ExperimentTeamMemberEntity t where p.id = t.pk.experiment.id  AND  t.pk.person.id = :personId  AND  t.experimentProfileInitials = :profileExperimentResponsible";
	// if (firstFieldOrder != null) {
	// sql += " order by " + firstFieldOrder;
	// }
	// if (firstFieldDirection != null) {
	// sql += " " + firstFieldDirection;
	// }
	//
	// Query query = emws.createQuery(sql);
	// query.setParameter("personId", ownerPerson.getId());
	// query.setParameter("profileExperimentResponsible", "RE");
	// List<ExperimentEntity> experiments = query.getResultList();
	// return experiments;
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "findByExperimentResponsible", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());
	//
	// } finally {
	// try {
	// // committing bean transaction
	// if (this.userTransaction != null) {
	// this.userTransaction.commit();
	// }
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "findByExperimentResponsible", Code.EJB_EXCEPTION, this.getClass().getSimpleName());
	// }
	// }
	// }
}
