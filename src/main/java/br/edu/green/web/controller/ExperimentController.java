package br.edu.green.web.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
	private boolean msgsRender;

	private boolean pnlExperimentListRendered;
	private boolean pnlExperimentRendered;

	private boolean buttonNewExperimentRender;
	private boolean buttonDeleteRendered;
	private boolean buttonReturnRendered;

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
					// this.prepareImportExperiment();
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
		this.msgsRender = GeneralController.RENDERED;

		// loading list of experiments
		this.loadExperiments();

		// configuring visibility of the panels
		this.pnlExperimentListRendered = GeneralController.RENDERED;
		this.pnlExperimentRendered = !GeneralController.RENDERED;
		this.buttonNewExperimentRender = GeneralController.RENDERED;
	}

	/**
	 * Configuring environment to create a new experiment schedule
	 */
	public void prepareNewExperiment() {
	}

	/**
	 * Configuring environment to update a experiment schedule
	 */
	public void prepareUpdateExperiment(ExperimentEntity experiment) {
		this.experimentSelected = experiment;
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
		// setting experiment selected
		this.experimentSelected = experiment;

		// configuring the labels of experiment tabs
		// this.prepareExperimentTabs();

		// configuring visibility of the panels and buttons
		this.pnlExperimentListRendered = !GeneralController.RENDERED;
		this.pnlExperimentRendered = GeneralController.RENDERED;
		this.buttonReturnRendered = GeneralController.RENDERED;
	}

	/**
	 * Loading list of experiments according filter type
	 */
	private void loadExperiments() {
		try {
			if (this.filter == null) {
				this.filter = FilterEnum.ALL;
			}

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

			case VIEW:
				this.functionTitle = this.labels.getLabel("form.experiment.list.function.title.view.experiment").trim() + ": " + this.experimentSelected.getShortTitle();
				break;

			default:
				this.functionTitle = "ATENÇÃO: ERRO NO T�?TULO";
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
	public boolean isMsgsRender() {
		return msgsRender;
	}

	/**
	 * @return the pnlExperimentListRendered
	 */
	public boolean isPnlExperimentListRendered() {
		return pnlExperimentListRendered;
	}

	/**
	 * @return the pnlExperimentRendered
	 */
	public boolean isPnlExperimentRendered() {
		return pnlExperimentRendered;
	}

	/**
	 * @return the buttonNewExperimentRender
	 */
	public boolean isButtonNewExperimentRender() {
		return buttonNewExperimentRender;
	}

	/**
	 * @return the buttonDeleteRendered
	 */
	public boolean isButtonDeleteRendered() {
		return buttonDeleteRendered;
	}

	/**
	 * @return the buttonReturnRendered
	 */
	public boolean isButtonReturnRendered() {
		return buttonReturnRendered;
	}

	// ***************************************************************
	// Operations of the controller
	// ***************************************************************

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
	 * Returns to experiments list
	 */
	public void returnExperimentList() {
		this.pnlExperimentListRendered = true;
		this.pnlExperimentRendered = false;
	}
}
