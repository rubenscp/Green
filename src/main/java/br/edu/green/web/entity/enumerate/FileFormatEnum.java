package br.edu.green.web.entity.enumerate;

/**
 * <p>
 * <b>This enumerate defines the format type of image.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 20/11/2016 (creation date)
 */

public enum FileFormatEnum {
	/**
	 * The values of profile of experiment.
	 */
	JPEG("jpeg"), //
	JPG("jpg"), //
	GIF("gif"), //
	PNG("png"), //
	;

	private String fileFormat;

	/**
	 * Constructor with one parameter.
	 * 
	 * @param fileFormat
	 */
	private FileFormatEnum(String fileFormat) {
		this.fileFormat = fileFormat.toLowerCase();
	}

	/**
	 * Returns the image file format value.
	 * 
	 * @return String - The profile value.
	 */
	public String getProfile() {
		return this.fileFormat;
	}

	/**
	 * Returns the text corresponding of the value of enumerate.
	 * 
	 * @return String - The text corresponding of the value of enumerate.
	 */
	@Override
	public String toString() {
		switch (fileFormat) {
			case "jpeg":
				return "JPEG";
			case "jpg":
				return "JPG";
			case "gif":
				return "GIF";
			case "png":
				return "PNG";
			default:
				return null;
		}
	}

	/**
	 * Returns the new instance of the enumerate according the method parameter.
	 * 
	 * @param fileFormat
	 *            The value of image file format desired.
	 * @return FileFormatEnum - The instance of the enumerate according the method parameter.
	 */
	public static FileFormatEnum getEnum(String fileFormat) {
		fileFormat = fileFormat.toLowerCase();
		switch (fileFormat) {
			case "jpeg":
				return JPEG;
			case "jpg":
				return JPG;
			case "gif":
				return JPEG;
			case "png":
				return PNG;
			default:
				return null;
		}
	}
}
