package br.edu.green.web.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Util.java: This class contains utiity static methods.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 24/08/2015
 * 
 */

public class Util {

	public static final byte FORMAT_DDMMAAAA_HHMMSS = 1;
	public static final byte FORMAT_DDMMAAAA = 2;
	public static final String VALIDATION_FORMAT_DDMMAAAA = "dd/MM/yyyy";
	public static final String FORMAT_NUMBER_0 = "0";
	public static final String FORMAT_NUMBER_00 = "00";
	public static final String FORMAT_NUMBER_000 = "000";
	public static final String FORMAT_NUMBER_0000 = "0000";
	public static final String FORMAT_NUMBER_00000 = "00000";
	public static final String FORMAT_NUMBER_000000 = "000000";
	public static final String FORMAT_NUMBER_0000000 = "0000000";
	public static final String FORMAT_NUMBER_00000000 = "00000000";
	public static final String FORMAT_NUMBER_000000000 = "000000000";
	public static final String FORMAT_NUMBER_0000000000 = "0000000000";

	// methods that obtaining the class name from the full class name
	public static String className(String classFullName) {

		// localizing the position o the last dot.
		int positionLastPoint = classFullName.lastIndexOf(".") + 1;

		// obtaining class name
		return classFullName.substring(positionLastPoint);

	}

	/**
	 * Returns the gets the compact name of the registered person composed by first and last name.
	 * 
	 * @param personName
	 *            The full name of the person
	 * @return String - The compact name of the person
	 */
	public static String compactNamePerson(String personName) {

		// obtaining all parts of the person name
		String[] partNames = personName.split("\\s");
		String firstName = (partNames.length > 0 ? partNames[0] : "??");
		String lastName = (partNames.length > 1 ? partNames[partNames.length - 1] : "");

		// returning first and last parts of the person name
		return (firstName + " " + lastName).trim();
	}

	/**
	 * Returns the first word.
	 * 
	 * @param text
	 *            The text desired.
	 * @return String - The first word of the text.
	 */
	public static String firstWord(String text) {

		// obtaining all parts of the person name
		String[] textParts = text.split("\\s");
		String firstWord = (textParts.length > 0 ? textParts[0] : "??");

		// returning first and last parts of the person name
		return firstWord.trim();
	}

	/**
	 * This method generates a MD5 hash code of a text message
	 * 
	 * @param string
	 *            to produce hash code MD5
	 * @return string converted to hash code MD5
	 */
	public static String generateMD5(String message) {

		String generatedHash = null;

		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// Add password bytes to digest
			md.update(message.getBytes());

			// Get the hash's bytes
			byte[] bytes = md.digest();

			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			// Get complete hashed password in hex format
			generatedHash = sb.toString();

		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		return generatedHash;
	}

	/**
	 * This method formats a date using the Brazilian convention
	 * 
	 * @param date
	 * @return formatted date
	 */
	public static String formatterDate(Date date, byte format) {
		// checking date
		if (date == null) {
			return "";
		}

		// creating simple date formatter instance
		SimpleDateFormat simpleDateFormat;
		switch (format) {
			case 1:
				simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				break;

			case 2:
				simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
				break;

			default:
				simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
				break;
		}

		// formatting date
		String formattedDate = simpleDateFormat.format(date);

		// return formatted date
		return formattedDate;
	}

	/**
	 * Returns an text with the value formatted. The possible pattern are:
	 * 
	 * @param pattern
	 * @param value
	 * @return
	 */
	public static String numberFormat(String pattern, double value) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(value);
	}

	/**
	 * Add or subtract days from one reference date.
	 * 
	 * @param date
	 *            The reference date.
	 * @param days
	 *            Number of days.
	 * @return Date - The new date resulting of the operation.
	 */
	public static Date addDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	/**
	 * Add or subtract years from one reference date.
	 * 
	 * @param date
	 *            The reference date.
	 * @param Number
	 *            of days.
	 * @return Date - The new date resulting of the operation.
	 */
	public static Date addYears(Date date, int years) {
		return Util.addDays(date, years * 365);
	}

	/**
	 * Retrieve a year of a full date
	 * 
	 * @param date
	 *            The full date
	 * @return int - The year of the date
	 */
	public static int getYear(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return year;
	}

	/**
	 * Creates a new date object from day, month and year separates.
	 * 
	 * @param day
	 *            The day
	 * @param month
	 *            The month
	 * @param year
	 *            The year
	 * @return Date - The new date from day, month and year.
	 */
	public static Date newDate(int day, int month, int year) {
		Calendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}

	/**
	 * Validates a date in string format
	 * 
	 * @param dateToValidate
	 * @param dateFormat
	 * @return
	 */
	public static boolean isThisDateValid(String dateToValidate, String dateFormat) {

		if (dateToValidate == null) {
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);

		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Writes an file in a specific folder in the web server.
	 * 
	 * @param inputFile
	 *            The input file to be write into the folder.
	 * @param path
	 *            The full path where the file will be wrote.
	 * @param fullFileName
	 *            The file name with its extension name.
	 * @throws IOException
	 *             The I/O exception.
	 */
	public static void writeFile(InputStream inputFile, String path, String fullFileName) throws IOException {
		// creating objects to be wrote into the folder
		File file = new File(path + fullFileName);
		OutputStream outputFile = new FileOutputStream(file);

		// writing the file byte per byte
		byte buf[] = new byte[1024];
		int length;
		while ((length = inputFile.read(buf)) > 0) {
			outputFile.write(buf, 0, length);
		}

		// closing files
		inputFile.close();
		outputFile.close();
	}
}
