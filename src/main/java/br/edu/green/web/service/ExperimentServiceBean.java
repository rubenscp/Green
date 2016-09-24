package br.edu.green.web.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Query;

import br.edu.green.web.dao.EntityManagerWrapperService;
import br.edu.green.web.entity.ExperimentEntity;
import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.entity.enumerate.ActionEnum;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.validate.ProcessingResultsList;

/**
 * <p>
 * <b>This class defines the Experiment services which contains all operations about experiments.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 23/09/2016 (creation date)
 */

@Stateless
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ExperimentServiceBean extends GeneralService implements ExperimentService {

	// defining serial version
	private static final long serialVersionUID = -6623054735226159563L;

	// defining constants
	public static final String ASCENDING = "asc";
	public static final String DESCENDING = "desc";
	public static final String PUBLIC_IDENTIFIER = "p.publicIdentifier";

	// EJB objects
	@EJB
	EntityManagerWrapperService emws;

	// classes attributes
	private ActionEnum action;

	/**
	 * Default constructor
	 * 
	 * @throws GeneralException
	 *             The general exception object
	 */
	public ExperimentServiceBean() throws GeneralException {
		super();

		// initializing services
		this.initializeServices();
	}

	/**
	 * Saves (insert or update) an experiment at the data base. This method implements all business rules related to experiment.
	 * 
	 * @param experiment
	 *            The experiment schedule object to be save
	 * @return ProcessingResultMap - The processing result map
	 * @throws GeneralException
	 *             The generic exception of SITIS
	 */
	public ProcessingResultsList save(ExperimentEntity experiment) throws GeneralException {
		// clear the processing result map
		super.processingResultMap.clear();

		// consisting experiment schedule
		// if (!this.consistSave(experiment)) {
		// // returning operation performed with error
		// return this.processingResultMap;
		// }

		// defining action
		this.action = (experiment.getId() == 0 ? ActionEnum.NEW : ActionEnum.UPDATE);

		try {
			// getting user transaction object
			this.userTransaction = this.ejbContext.getUserTransaction();

			// starting bean transaction
			this.userTransaction.begin();

			// saving experiment schedule
			experiment = this.saveExperiment(experiment);

			// defining proper message related to result with success
			if (action.equals(ActionEnum.NEW)) {
				super.processingResultMap.add(new ProcessingResultEntity(Code.EXPERIMENT_INFORMATION_SUCCESS_NEW_EXPERIMENT, applicationMessage.getMessage(Code.EXPERIMENT_INFORMATION_SUCCESS_NEW_EXPERIMENT.name())));
			} else {
				super.processingResultMap.add(new ProcessingResultEntity(Code.EXPERIMENT_INFORMATION_SUCCESS_UPDATE_EXPERIMENT, applicationMessage.getMessage(Code.EXPERIMENT_INFORMATION_SUCCESS_UPDATE_EXPERIMENT.name())));
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
	 * @param experiment
	 *            The experiment.
	 * @return ExperimentEntity The experiment.
	 * @throws GeneralException
	 *             The general exception object
	 */
	private ExperimentEntity saveExperiment(ExperimentEntity experiment) throws GeneralException {
		try {
			if (experiment.getId() == 0) {
				this.emws.create(experiment);
				return experiment;
			} else {
				return this.emws.update(experiment);
			}

		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "saveExperiment", ((experiment.getId() == 0) ? Code.DAO_EXCEPTION_CREATE : Code.DAO_EXCEPTION_UPDATE), this.getClass().getSimpleName());
		}
	}

	/**
	 * Creates a new Green experiment.
	 * 
	 * @param experiment
	 *            The Green experiment
	 * @return ExperimentEntity The Green experiment with its updated attributes
	 * @throws GeneralException
	 *             The general exception object
	 */
	// @Override
	// public ExperimentEntity create(ExperimentEntity experiment) throws GeneralException {
	// try {
	// // returning the experiment entity instance
	// return this.emws.create(experiment);
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "create", Code.DAO_EXCEPTION_CREATE, this.getClass().getSimpleName());
	// }
	// }

	/**
	 * Updates a Green experiment.
	 * 
	 * @param experiment
	 *            The Green experiment
	 * @return ExperimentEntity The Green experiment with its updated attributes
	 * @throws GeneralException
	 *             The general exception object
	 */
	// @Override
	// public ExperimentEntity update(ExperimentEntity experiment) throws GeneralException {
	// try {
	// return this.emws.update(experiment);
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// throw this.handleException(e, this.getClass().getSimpleName(), "update", Code.DAO_EXCEPTION_UPDATE, this.getClass().getSimpleName());
	// }
	// }

	/**
	 * Deletes an experiment schedule at the data base checking all dependencies.
	 * 
	 * @param experiment
	 *            The experiment object to be delete
	 * @return ProcessingResultMap - The processing result map
	 * @throws GeneralException
	 *             The generic exception of Green
	 */
	@Override
	public ProcessingResultsList delete(ExperimentEntity experiment) throws GeneralException {
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
			String title = experiment.getTitle();

			// deleting experiment schedule
			this.deleteExperiment(experiment);

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
	 * Deletes a Green experiment.
	 * 
	 * @param experiment
	 *            The Green experiment
	 * @throws GeneralException
	 *             The general exception object
	 */
	private void deleteExperiment(ExperimentEntity experiment) throws GeneralException {
		try {
			this.emws.find(ExperimentEntity.class, experiment.getId());
			this.emws.remove(experiment);
		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "deleteExperiment", Code.DAO_EXCEPTION_DELETE, this.getClass().getSimpleName());
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
	@Override
	public List<ExperimentEntity> findAll(String firstFieldOrder, String firstFieldDirection) throws GeneralException {
		try {
			String sql = "select p from ExperimentEntity p";
			if (firstFieldOrder != null) {
				sql += " order by " + firstFieldOrder;
			}
			if (firstFieldDirection != null) {
				sql += " " + firstFieldDirection;
			}

			List<ExperimentEntity> experiments = this.emws.findAll(sql);
			return experiments;

		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "findAll", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());
		}
	}

	/**
	 * Finds all Green experiment where one person is owner by experiment.
	 * 
	 * @param ownerPerson
	 *            The owner person of the experiment
	 * @return List<ExperimentEntity> The Green experiments list
	 * @throws GeneralException
	 *             The general exception object
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ExperimentEntity> findExperimentsByOwner(PersonEntity ownerPerson, String firstFieldOrder, String firstFieldDirection) throws GeneralException {
		try {
			// getting user transaction object
			this.userTransaction = this.ejbContext.getUserTransaction();

			// starting bean transaction
			this.userTransaction.begin();

			// String x = "select * from experiment as e left join experiment_team_member as t on e.id = t.experiment_id where t.person_id = 10 AND
			// t.experiment_profile_initials = 'RCE';

			// String sql =
			// "select p from ExperimentEntity p left outer join ExperimentTeamMemberEntity t on p.id = t.pk.experiment.id where t.pk.person.id = :personId AND t.experimentProfileInitials = :profileExperimentResponsible";

			// String sql =
			// "select p.id from ExperimentEntity p inner join ExperimentTeamMemberEntity t on p.id = t.pk.experiment.id where t.pk.person.id = :personId AND t.experimentProfileInitials = :profileExperimentResponsible";

			// NOTA: Revisar a implementação HQL utilizando LEFT JOIN.
			// String sql =
			// "select p from ExperimentEntity p, ExperimentTeamMemberEntity t where p.id = t.pk.experiment.id  AND  t.pk.person.id = :personId  AND  t.experimentProfileInitials = :profileExperimentResponsible";
			String sql = "select p from ExperimentEntity p, ExperimentTeamMemberEntity t where p.id = t.pk.experiment.id  AND  t.pk.person.id = :personId  AND  t.experimentProfileInitials = :profileExperimentResponsible";
			if (firstFieldOrder != null) {
				sql += " order by " + firstFieldOrder;
			}
			if (firstFieldDirection != null) {
				sql += " " + firstFieldDirection;
			}

			Query query = emws.createQuery(sql);
			query.setParameter("personId", ownerPerson.getId());
			query.setParameter("profileExperimentResponsible", "RE");
			List<ExperimentEntity> experiments = query.getResultList();
			return experiments;
		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			throw this.handleException(e, this.getClass().getSimpleName(), "findByExperimentResponsible", Code.DAO_EXCEPTION_FIND, this.getClass().getSimpleName());

		} finally {
			try {
				// committing bean transaction
				if (this.userTransaction != null) {
					this.userTransaction.commit();
				}
			} catch (Exception e) {
				// configuring and throwing details of the actual exception
				throw this.handleException(e, this.getClass().getSimpleName(), "findByExperimentResponsible", Code.EJB_EXCEPTION, this.getClass().getSimpleName());
			}
		}
	}
}
