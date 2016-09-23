package br.edu.green.web.entity.enumerate;

/**
 * <p>
 * <b>This enumerate defines all the possible filters that can be execute in a form.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 16/08/2016 (creation date)
 */

public enum FilterEnum {
	/**
	 * The values of form filters.
	 */
	UNDEFINED(1), //
	MY(2), //
	ALL(3), //
	;
	public static final String FILTER_NAME = "filter";
	private int filter;

	/**
	 * Constructor with one parameter.
	 * 
	 * @param filter
	 */
	private FilterEnum(int filter) {
		this.filter = filter;
	}

	/**
	 * Returns the filter value.
	 * 
	 * @return int The filter filter value.
	 */
	public int getFilter() {
		return this.filter;
	}

	/**
	 * Returns the filter value in the string type.
	 * 
	 * @return String The filter value.
	 */
	public String getFilterIntoString() {
		return Integer.toString(this.filter);
	}

	/**
	 * Returns the text corresponding of the value of enumerate.
	 * 
	 * @return String - The text corresponding of the value of enumerate.
	 */
	@Override
	public String toString() {
		switch (filter) {
			case 1:
				return "Indefinida";
			case 2:
				return "Meus";
			case 3:
				return "Todos";
			default:
				return null;
		}
	}

	/**
	 * Returns the new instance of the enumerate according the method parameter.
	 * 
	 * @param filter
	 *            The value of status desired.
	 * @return FilterEnum - The instance of the enumerate according the method parameter.
	 */
	public static FilterEnum getEnum(int filter) {
		switch (filter) {
			case 1:
				return UNDEFINED;
			case 2:
				return MY;
			case 3:
				return ALL;
			default:
				return null;
		}
	}
}
