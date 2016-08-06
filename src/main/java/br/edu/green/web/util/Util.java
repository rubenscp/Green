package br.edu.green.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util.java: This class contains utiity static methods.
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 24/08/2015
 * 
 */

public class Util {

	// methods that obtaing the class name from the full class name
	public static String className(String classFullName) {

		// localizing the position o the last dot.
		int positionLastPoint = classFullName.lastIndexOf(".") + 1;

		// obtaing class name
		return classFullName.substring(positionLastPoint);

	}

	/**
	 * This method gets the compact name of the registered person composed by first and last name
	 * 
	 * @param String
	 * @return String
	 */
	public static String compactNamePerson(String namePerson) {

		// obtaining all parts of the person name
		String[] partNames = namePerson.split("\\s");
		String firstName = (partNames.length > 0 ? partNames[0] : "??");
		String lastName = (partNames.length > 1 ? partNames[partNames.length - 1] : "");

		// returning first and last parts of the person name
		return (firstName + " " + lastName).trim();
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
	public static String formatterDate(Date date) {
		// checking date
		if (date == null) {
			return "";
		}

		// creating simple date formatter instance
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		// formatting date
		String formattedDate = simpleDateFormat.format(date);

		// return formatted date
		return formattedDate;
	}
}
