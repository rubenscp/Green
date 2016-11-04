package br.edu.green.web.entity.enumerate;

/**
 * <p>
 * <b>This enumerate defines all the possible actions that can be execute in a form.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 19/02/2016 (creation date)
 */

public enum ActionEnum {
	/**
	 * The values of form actions.
	 */
	UNDEFINED(1), //
	NEW(2), //
	UPDATE(3), //
	DELETE(4), //
	VIEW(5), //
	SHOW_LIST(6), //
	IMAGES_UPLOAD(7), //

	SHOW_LIST_FOR_APPROVAL(8), //
	SUBMIT_APPROVAL_EXPERIMENT_SCHEDULE(9), //
	APPROVE_EXPERIMENT_SCHEDULE(10), //
	CLICK(11), //
	MARK(12), //
	UNMARK(13), //
	;

	public static final String ACTION_NAME = "action";
	private int action;

	/**
	 * Constructor with one parameter.
	 * 
	 * @param action
	 */
	private ActionEnum(int action) {
		this.action = action;
	}

	/**
	 * Returns the action value.
	 * 
	 * @return short The action value.
	 */
	public int getAction() {
		return this.action;
	}

	/**
	 * Returns the action value in the string type.
	 * 
	 * @return String The action value.
	 */
	public String getActionIntoString() {
		return Integer.toString(this.action);
	}

	/**
	 * Returns the text corresponding of the value of enumerate.
	 * 
	 * @return String - The text corresponding of the value of enumerate.
	 */
	@Override
	public String toString() {
		switch (action) {
			case 1:
				return "Indefinida";
			case 2:
				return "Nova";
			case 3:
				return "Alteracao";
			case 4:
				return "Exclusao";
			case 5:
				return "Visualizar";
			case 6:
				return "Listar";
			case 7:
				return "Upload de imagens";
			case 8:
				return "Listar Agendamentos do Experimento para Aprovacao";
			case 9:
				return "Submeter Aprovação do Experimento";
			case 10:
				return "Aprovar Agendamento do Experimento";
			case 11:
				return "Clique";
			case 12:
				return "Marcar";
			case 13:
				return "Desmarcar";
			default:
				return null;
		}
	}

	/**
	 * Returns the new instance of the enumerate according the method parameter.
	 * 
	 * @param action
	 *            The value of status desired.
	 * @return FormActionEnum - The instance of the enumerate according the method parameter.
	 */
	public static ActionEnum getEnum(int action) {
		switch (action) {
			case 1:
				return UNDEFINED;
			case 2:
				return NEW;
			case 3:
				return UPDATE;
			case 4:
				return DELETE;
			case 5:
				return VIEW;
			case 6:
				return SHOW_LIST;
			case 7:
				return IMAGES_UPLOAD;
			case 8:
				return SHOW_LIST_FOR_APPROVAL;
			case 9:
				return SUBMIT_APPROVAL_EXPERIMENT_SCHEDULE;
			case 10:
				return APPROVE_EXPERIMENT_SCHEDULE;
			case 11:
				return CLICK;
			case 12:
				return MARK;
			case 13:
				return UNMARK;
			default:
				return null;
		}
	}

}
