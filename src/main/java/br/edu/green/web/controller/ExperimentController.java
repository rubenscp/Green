package br.edu.green.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.green.web.entity.ExperimentEntity;
import br.edu.green.web.entity.PersonEntity;
import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.entity.enumerate.ActionEnum;
import br.edu.green.web.entity.enumerate.FilterEnum;
import br.edu.green.web.exception.GeneralException;
import br.edu.green.web.service.ExperimentService;
import br.edu.green.web.service.ExperimentServiceBean;
import br.edu.green.web.service.PersonService;
import br.edu.green.web.util.FacesUtil;

/**
 * <p>
 * <b> This class controls the operations related to one GREEN experiment.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 23/09/2016 (creation date)
 */

@ManagedBean(name = "experimentController")
@ViewScoped
public class ExperimentController extends GeneralController implements Serializable {

	private static final long serialVersionUID = -1729697488185282155L;

	// injecting beans in the class
	@EJB
	private ExperimentService experimentService;

	@EJB
	private PersonService personService;

	// defining class attributes
	private List<ExperimentEntity> experiments = new ArrayList<ExperimentEntity>();
	private ExperimentEntity experimentSelected;
	private PersonEntity loggedPerson;

	private String functionTitle;
	private FilterEnum filter;

	private String emptyDatatableMessage;

	private List<String> experimentContent;

	private ActionEnum action;

	// variables that controls the rendering
	// private boolean msgsRender;

	private boolean pnlExperimentListRender;
	private boolean pnlExperimentNewEditRender;
	private boolean pnlExperimentViewRender;

	private boolean buttonNewExperimentRender;
	private boolean buttonDeleteRender;
	private boolean buttonReturnRender;

	private boolean dataChanged;

	// private boolean buttonSaveRender;
	// private boolean buttonCancelRender;

	// ***************************************************************
	// Initializers Methods
	// ***************************************************************

	/**
	 * The default constructor initializes some objects of class.
	 */
	public ExperimentController() {
		super();
		this.experiments = new ArrayList<ExperimentEntity>(0);
		this.experimentSelected = new ExperimentEntity();
		this.loggedPerson = new PersonEntity();
		this.experimentContent = new ArrayList<String>(0);
		this.action = ActionEnum.UNDEFINED;
	}

	/**
	 * Prepares the environment of the controller class.
	 */
	@PostConstruct
	public void initForm() {
		try {
			// defining of auxiliary variables
			String actionParameter;
			String filterParameter;

			// initializing services
			this.initializeServices();

			// recovering logged person
			this.loggedPerson = FacesUtil.recoverObjectInSession(new PersonEntity());

			// recovering the first parameter passed from URL
			this.filter = FilterEnum.UNDEFINED;
			filterParameter = (String) FacesUtil.getParameterMap(FilterEnum.FILTER_NAME);
			if (filterParameter != null && !filterParameter.isEmpty()) {
				if (filterParameter.equals(FilterEnum.MY.getFilterIntoString())) {
					this.filter = FilterEnum.MY;
				}
				if (filterParameter.equals(FilterEnum.ALL.getFilterIntoString())) {
					this.filter = FilterEnum.ALL;
				}
			} else {
				this.filter = FilterEnum.MY;
			}

			this.action = ActionEnum.UNDEFINED;
			actionParameter = (String) FacesUtil.getParameterMap(ActionEnum.ACTION_NAME);
			if (actionParameter != null && !actionParameter.isEmpty()) {
				// preparing to create a new experiment schedule
				if (actionParameter.equals(ActionEnum.NEW.getActionIntoString())) {
					this.action = ActionEnum.NEW;
					this.prepareNewExperiment();
					return;
				} else {
					// preparing list of experiments schedules
					if (actionParameter.equals(ActionEnum.SHOW_LIST.getActionIntoString())) {
						this.action = ActionEnum.SHOW_LIST;
						this.prepareListOfExperiments();
					}
				}
			}

			// preparing the processing result
			this.preparePresentationProcessingResults();

		} catch (GeneralException ge) {
			// handling general exception of GREEN Web
			this.handleGeneralException(ge);
		}
	}

	/**
	 * Defining possible actions over each experiment of the list according to logged person.
	 */
	private void defineActionsOverExperiment() {
		for (ExperimentEntity experiment : this.experiments) {
			// configuring default actions
			experiment.setEditAction(GeneralController.RENDERED);
			experiment.setDeleteAction(GeneralController.RENDERED);
			experiment.setViewAction(GeneralController.RENDERED);
		}
	}

	/**
	 * Configuring environment to show the list of experiments
	 */
	public void prepareListOfExperiments() {
		// configuring the action
		this.action = ActionEnum.SHOW_LIST;

		// configuring function title
		this.configureFunctionTitle();

		// initializing message
		this.emptyDatatableMessage = "";

		// configuring indicator of rendering of the messages components
		// this.msgsRender = GeneralController.RENDERED;

		// loading list of experiments
		this.loadExperiments();

		// configuring visibility of the panels
		this.pnlExperimentListRender = GeneralController.RENDERED;
		this.pnlExperimentNewEditRender = !GeneralController.RENDERED;
		this.pnlExperimentViewRender = !GeneralController.RENDERED;
		this.buttonNewExperimentRender = GeneralController.RENDERED;
	}

	/**
	 * Configuring environment to create a new experiment schedule
	 */
	public void prepareNewExperiment() {
		// configuring the action
		this.action = ActionEnum.NEW;

		// creating new instance of experiment
		this.experimentSelected = new ExperimentEntity();

		// configuring attributes of the new experiment
		this.experimentSelected.setOwnerPerson(this.loggedPerson);
		this.experimentSelected.setPublicIdentifier(this.loggedPerson.getLastExperimentPublicIdentifier() + 1);

		// configuring function title
		this.configureFunctionTitle();

		// configuring visibility of the panels
		this.pnlExperimentListRender = !GeneralController.RENDERED;
		this.pnlExperimentNewEditRender = GeneralController.RENDERED;
		this.pnlExperimentViewRender = !GeneralController.RENDERED;
		this.buttonNewExperimentRender = !GeneralController.RENDERED;
	}

	/**
	 * Configuring environment to update a experiment schedule
	 */
	public void prepareUpdateExperiment(ExperimentEntity experiment) {
		// configuring the action
		this.action = ActionEnum.UPDATE;

		// updating the experiment selected
		this.experimentSelected = experiment;

		// configuring function title
		this.configureFunctionTitle();

		// configuring visibility of the panels
		this.pnlExperimentListRender = !GeneralController.RENDERED;
		this.pnlExperimentNewEditRender = GeneralController.RENDERED;
		this.pnlExperimentViewRender = !GeneralController.RENDERED;
		this.buttonNewExperimentRender = !GeneralController.RENDERED;
	}

	/**
	 * Configuring environment to query a experiment schedule.
	 */
	public void prepareDeleteExperiment(ExperimentEntity experiment) {
		try {
			// defining the current action
			this.action = ActionEnum.DELETE;

			// clearing the processing result list
			this.processingResultsList.clear();

			// setting experiment object to delete
			this.experimentSelected = experiment;

			// initialize list of experiment contents
			this.experimentContent.clear();
			this.experimentContent.add(this.labels.getLabel("form.dialog.delete.experiment.identification"));

			// showing dialog form to confirm the delete operation
			FacesUtil.getRequestContext().execute("PF('wdlgConfirmDeleteExperiment').show();");

		} catch (GeneralException ge) {
			// handling general exception of GREEN Web
			this.handleGeneralException(ge);
		}
	}

	/**
	 * Configuring environment to query a experiment schedule.
	 */
	public void prepareViewExperiment(ExperimentEntity experiment) {
		// defining the current action
		this.action = ActionEnum.VIEW;

		// setting experiment selected
		this.experimentSelected = experiment;

		// configuring the labels of experiment tabs
		// this.prepareExperimentTabs();

		// configuring function title
		this.configureFunctionTitle();

		// configuring visibility of the panels and buttons
		this.pnlExperimentListRender = !GeneralController.RENDERED;
		this.pnlExperimentNewEditRender = !GeneralController.RENDERED;
		this.pnlExperimentViewRender = GeneralController.RENDERED;
		this.buttonReturnRender = GeneralController.RENDERED;
	}

	/**
	 * Loading list of experiments according filter type
	 */
	private void loadExperiments() {
		try {
			if (this.filter == null) {
				this.filter = FilterEnum.ALL;
			}

			this.filter = FilterEnum.ALL;
			if (this.filter.equals(FilterEnum.MY)) {
				// loading list of the GREEN experiments that belong to logged person
				this.experiments = this.experimentService.findExperimentsByOwner(this.loggedPerson, ExperimentServiceBean.PUBLIC_IDENTIFIER, ExperimentServiceBean.DESCENDING);
			} else {
				// loading list with all GREEN experiments
				this.experiments = this.experimentService.findAll(ExperimentServiceBean.PUBLIC_IDENTIFIER, ExperimentServiceBean.DESCENDING);
			}

			// initializing message
			this.emptyDatatableMessage = "";

			// verifying empty list
			if (this.experiments.isEmpty()) {
				// setting message of empty datatable
				if (this.filter.equals(FilterEnum.MY)) {
					this.emptyDatatableMessage = this.applicationMessage.getMessage(Code.EXPERIMENT_LIST_WARNING_EMPTY_LIST_WITH_PERSON.name(), this.loggedPerson.getName());
				} else {
					this.emptyDatatableMessage = this.applicationMessage.getMessage(Code.EXPERIMENT_LIST_WARNING_EMPTY_LIST.name());
				}
				// return with error
				return;
			}

			// defining rendering of the experiment actions
			this.defineActionsOverExperiment();

		} catch (GeneralException ge) {
			// handling general exception of GREEN Web
			this.handleGeneralException(ge);
		}
	}

	/**
	 * Preparing the labels of experiment tabs
	 */
	// private void prepareExperimentTabs() {
	// // initializing entity class attributes
	// this.emptyTeamMembersDatatableMessage = "";
	// this.emptyVariablesDatatableMessage = "";
	// this.emptyTreatmentsDatatableMessage = "";
	// this.emptyRafflesDatatableMessage = "";
	//
	// // initializing the number of team members, variables, treatments and raffles of the experiment
	// this.numberTeamMembers = 0;
	// this.numberVariables = 0;
	// this.numberTreatments = 0;
	// this.numberRaffles = 0;
	//
	// // checking value of experiment
	// if (this.experimentSelected != null) {
	// // checking team members
	// if (this.experimentSelected.getExperimentTeamMembers().size() == 0) {
	// this.emptyTeamMembersDatatableMessage = this.applicationMessage.getMessage(Code.EXPERIMENT_ERROR_NO_TEAM_MEMBERS_IN_DATABASE.name());
	// } else {
	// this.numberTeamMembers = this.experimentSelected.getExperimentTeamMembers().size();
	// }
	// // checking list of variables
	// if (this.experimentSelected.getExperimentVariables().size() == 0) {
	// this.emptyVariablesDatatableMessage = this.applicationMessage.getMessage(Code.EXPERIMENT_ERROR_NO_VARIABLES_IN_DATABASE.name());
	// } else {
	// this.numberVariables = this.experimentSelected.getExperimentVariables().size();
	// }
	// // checking list of treatments
	// if (this.experimentSelected.getExperimentTreatments().size() == 0) {
	// this.emptyTreatmentsDatatableMessage = this.applicationMessage.getMessage(Code.EXPERIMENT_ERROR_NO_TREATMENTS_IN_DATABASE.name());
	// } else {
	// this.numberTreatments = this.experimentSelected.getExperimentTreatments().size();
	// }
	// // checking list of raffles
	// if (this.experimentSelected.getExperimentRaffles().size() == 0) {
	// this.emptyRafflesDatatableMessage = this.applicationMessage.getMessage(Code.EXPERIMENT_ERROR_NO_RAFFLES_IN_DATABASE.name());
	// } else {
	// this.numberRaffles = this.experimentSelected.getExperimentRaffles().size();
	// }
	// }
	// }

	/**
	 * Configures function title according to filter
	 */
	private void configureFunctionTitle() {
		switch (this.action) {
			case SHOW_LIST:
				if (this.filter == null) {
					this.functionTitle = this.labels.getLabel("form.experiment.list.function.title.all.experiments");
				} else {
					if (this.filter.equals(FilterEnum.MY)) {
						this.functionTitle = this.labels.getLabel("form.experiment.list.function.title.my.experiments");
					} else {
						this.functionTitle = this.labels.getLabel("form.experiment.list.function.title.all.experiments");
					}
				}
				break;

			case NEW:
				this.functionTitle = this.labels.getLabel("form.experiment.list.function.title.new.experiment").trim();
				break;

			case UPDATE:
				this.functionTitle = this.labels.getLabel("form.experiment.list.function.title.update.experiment").trim() + " " + this.experimentSelected.getPublicIdentifierAndShortTitle();
				break;

			case VIEW:
				this.functionTitle = this.labels.getLabel("form.experiment.list.function.title.view.experiment").trim() + " " + this.experimentSelected.getPublicIdentifierAndShortTitle();
				break;

			default:
				this.functionTitle = "ATENCAO: ERRO NO TITULO DA FUNCAO";
				break;
		}
	}

	// ***************************************************************
	// Getters and Setters Methods
	// ***************************************************************

	/**
	 * Accesses the attribute named 'experiments' and returns its value.
	 * 
	 * @return List - List of GREEN experiments (ExperimentEntity).
	 */
	public List<ExperimentEntity> getExperiments() {
		return experiments;
	}

	/**
	 * Sets the value of attribute named 'experiments'.
	 * 
	 * @param experiments
	 *            The list of GREEN experiments.
	 */
	public void setExperiments(List<ExperimentEntity> experiments) {
		this.experiments = experiments;
	}

	/**
	 * Returns the experiment selected from the list of experiments.
	 * 
	 * @return ExperimentEntity - The experiment selected from the list of experiments.
	 */
	public ExperimentEntity getExperimentSelected() {
		return experimentSelected;
	}

	/**
	 * Sets the experiment selected from the list of experiments.
	 * 
	 * @param experimentSelected
	 *            The experiment selected from the list of experiments.
	 */
	public void setExperimentSelected(ExperimentEntity experimentSelected) {
		this.experimentSelected = experimentSelected;
	}

	/**
	 * Returns the function title with the id and title of the experiment selected
	 * 
	 * @return ExperimentEntity - The experiment object.
	 */
	public String getFunctionTitle() {
		// return this.labels.getLabel("form.experiment.function.title") + this.experimentEntity.getPublicIdentifierAndShortTitle();
		return this.functionTitle;
	}

	/**
	 * Returns the message of empty datatable.
	 * 
	 * @return String - The message of empty datatable.
	 */
	public String getEmptyDatatableMessage() {
		return emptyDatatableMessage;
	}

	/**
	 * @return the experimentContent
	 */
	public List<String> getExperimentContent() {
		return experimentContent;
	}

	/**
	 * @param experimentContent
	 *            the experimentContent to set
	 */
	public void setExperimentContent(List<String> experimentContent) {
		this.experimentContent = experimentContent;
	}

	/**
	 * Returns the indicator of rendering of the component.
	 * 
	 * @return the msgsRender
	 */
	// public boolean isMsgsRender() {
	// return msgsRender;
	// }

	/**
	 * @return the pnlExperimentListRender
	 */
	public boolean isPnlExperimentListRender() {
		return pnlExperimentListRender;
	}

	/**
	 * @return the pnlExperimentNewEditRender
	 */
	public boolean isPnlExperimentNewEditRender() {
		return pnlExperimentNewEditRender;
	}

	/**
	 * @return the pnlExperimentViewRender
	 */
	public boolean isPnlExperimentViewRender() {
		return pnlExperimentViewRender;
	}

	/**
	 * @return the buttonNewExperimentRender
	 */
	public boolean isButtonNewExperimentRender() {
		return buttonNewExperimentRender;
	}

	/**
	 * @return the buttonDeleteRender
	 */
	public boolean isButtonDeleteRender() {
		return buttonDeleteRender;
	}

	/**
	 * @return the buttonReturnRender
	 */
	public boolean isButtonReturnRender() {
		return buttonReturnRender;
	}

	/**
	 * Returns true if the current action is "update" to show the creation date of experiment.
	 * 
	 * @return boolean - The indicator to show the creation date of experiment.
	 */
	public boolean isCreationDateRender() {
		return (this.action.equals(ActionEnum.UPDATE) ? true : false);
	}

	/**
	 * Returns true if the current action is "update" to show the update date of experiment.
	 * 
	 * @return boolean - The indicator to show the update date of experiment.
	 */
	public boolean isLastUpdateDateRender() {
		return (this.action.equals(ActionEnum.UPDATE) ? true : false);
	}

	// ***************************************************************
	// Operations of the controller
	// ***************************************************************
	/**
	 * Save the experiment.
	 */
	public void executeSave() {
		try {
			// consisting fields of the form
			if (this.consist()) {
				// configuring attributes of experiment
				// PersonEntity ownerPerson = this.personService.findByUserName(this.loggedPerson.getUserName());
				// this.experimentSelected.setOwnerPerson(ownerPerson);

				// configuring the last update date
				this.experimentSelected.setLastUpdateDate(new Date());

				// saving experiment
				this.processingResultsList = this.experimentService.save(this.experimentSelected);

				// checking the processing result
				if (!this.processingResultsList.isErrorOrWarningOrException()) {
					// configuring filter to "my" experiments
					this.filter = FilterEnum.MY;

					// configuring action to show list of the experiments
					this.action = ActionEnum.SHOW_LIST;

					// configuring function title
					this.configureFunctionTitle();

					// loading list of experiments
					this.loadExperiments();

					// prepares the return to experiment list
					this.prepareReturnExperimentList();
				}
			}

			// preparing the processing result
			this.preparePresentationProcessingResults();

		} catch (GeneralException ge) {
			// handling general exception of SITIS Web
			this.handleGeneralException(ge);
		}
	}

	private boolean consist() {
		// clearing the processing result list
		this.processingResultsList.clear();

		// checking if there are errors
		if ((this.experimentSelected.getTitle() == null) || (this.experimentSelected.getTitle() != null && this.experimentSelected.getTitle().length() < 10)) {
			// configuring error related to title of the experiment
			this.processingResultsList.add(new ProcessingResultEntity(Code.EXPERIMENT_ERROR_NO_TITLE, this.applicationMessage.getMessage(Code.EXPERIMENT_ERROR_NO_TITLE.name())));
		}
		if ((this.experimentSelected.getDescription() == null) || (this.experimentSelected.getDescription() != null && this.experimentSelected.getDescription().length() < 20)) {
			// configuring error related to description of the experiment
			this.processingResultsList.add(new ProcessingResultEntity(Code.EXPERIMENT_ERROR_NO_DESCRIPTION, this.applicationMessage.getMessage(Code.EXPERIMENT_ERROR_NO_DESCRIPTION.name())));
		}

		// evaluating processing result
		if (this.processingResultsList.isErrorOrWarningOrException()) {
			// returning result with error
			return false;
		} else {
			// returning result with success
			return true;
		}
	}

	/**
	 * Creates a new experiment from one SIEXP experiment
	 */
	// public void importExperiment() {
	// try {
	// // calls form experiment import
	// FacesUtil.getContext().getExternalContext().redirect("../siexpExperimentImport/" +
	// this.applicationConfiguration.getStringValue("xhtml.form.name.siexp.experiment.import.manager"));
	//
	// } catch (GeneralException ge) {
	// // handling general exception of GREEN Web
	// this.handleGeneralException(ge);
	//
	// } catch (IOException ioe) {
	// // handling IOException with creating new general exception
	// GeneralException ge = new GeneralException();
	// ge.setProcessingResult(new ProcessingResultEntity(Code.FACES_EXCEPTION_INPUT_OUTPUT,
	// this.applicationMessage.getMessage(Code.FACES_EXCEPTION_INPUT_OUTPUT.name())));
	// ge.setClassName(this.getClass().getName());
	// ge.setMethodName("newExperiment");
	// ge.setExceptionOriginalMessage(ioe.getMessage()); // the original exception message
	//
	// // handling general exception of GREEN Web
	// this.handleGeneralException(ge);
	//
	// } catch (Exception e) {
	// // handling exception
	// this.handleException(e);
	// }
	// }

	/**
	 * Deletes the experiment and all relationship.
	 */
	public void executeDelete(String dialogFormName) {
		try {
			// clearing the processing result list
			this.processingResultsList.clear();

			// deleting experiment schedule and preparing the processing result
			this.processingResultsList = this.experimentService.delete(this.experimentSelected);

			// preparing the processing result
			this.preparePresentationProcessingResults();

			// configuring new action after current action
			this.action = ActionEnum.SHOW_LIST;

			// loading list of experiments
			this.loadExperiments();

			// closing dialog form
			this.closeDialogForm(dialogFormName);

		} catch (GeneralException ge) {
			// handling general exception of GREEN Web
			this.handleGeneralException(ge);
		}
	}

	/**
	 * Returns the list of experiment
	 */
	public void prepareReturnExperimentList() {
		// configuring the rendering of the forms
		this.pnlExperimentListRender = GeneralController.RENDERED;
		this.pnlExperimentNewEditRender = !GeneralController.RENDERED;
		this.pnlExperimentViewRender = !GeneralController.RENDERED;

		// configuring new action after current action
		this.action = ActionEnum.SHOW_LIST;

		// configuring function title
		this.configureFunctionTitle();
	}

	/**
	 * Cancel the creation or edition of the experiment.
	 */
	public void cancel(String dialogFormName) {
		try {
			// checking changes in the form
			if (this.dataChanged) {
				// showing the dialog panel with the details of experiment schedule
				FacesUtil.getRequestContext().execute("PF('" + dialogFormName + "').show()");
			} else {
				// execute the cancel operation
				this.executeCancel(dialogFormName);
			}

		} catch (GeneralException ge) {
			// handling general exception of SITIS Web
			this.handleGeneralException(ge);
		}
	}

	/**
	 * Executes the cancel of current operation in the form.
	 */
	public void executeCancel(String dialogFormName) {
		// configuring action to show list
		this.action = ActionEnum.SHOW_LIST;

		// clearing all work instances
		this.experimentSelected = null;
		this.dataChanged = false;

		// preparing list of experiment schedules
		this.prepareListOfExperiments();

		// close the the dialog panel
		this.closeDialogForm(dialogFormName);

	}
}
