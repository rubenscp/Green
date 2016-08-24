package br.edu.green.web.entity.enumerate;

/**
 * <p>
 * <b>This enumerate defines the Green person profile.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 06/05/2016 (creation date)
 */

public enum GreenPersonProfileEnum {
	/**
	 * The values of profile of experiment.
	 */
	USER("USR"), //
	GREEN_SYSTEM_ADMINISTRATOR("GAS"), //
	;

	private String profile;

	/**
	 * Constructor with one parameter.
	 * 
	 * @param profile
	 */
	private GreenPersonProfileEnum(String profile) {
		this.profile = profile;
	}

	/**
	 * Returns the profile value.
	 * 
	 * @return String - The profile value.
	 */
	public String getProfile() {
		return this.profile;
	}

	/**
	 * Returns the text corresponding of the value of enumerate.
	 * 
	 * @return String - The text corresponding of the value of enumerate.
	 */
	@Override
	public String toString() {
		switch (profile) {
			case "USR":
				return "Usuario";
			case "GAS":
				return "Administrador do Sistema Green";
			default:
				return null;
		}
	}

	/**
	 * Returns the new instance of the enumerate according the method parameter.
	 * 
	 * @param profile
	 *            The value of profile desired.
	 * @return SitisPersonProfileEnum - The instance of the enumerate according the method parameter.
	 */
	public static GreenPersonProfileEnum getEnum(String profile) {
		switch (profile) {
			case "USR":
				return USER;
			case "AS":
				return GREEN_SYSTEM_ADMINISTRATOR;
			default:
				return null;
		}
	}

}
