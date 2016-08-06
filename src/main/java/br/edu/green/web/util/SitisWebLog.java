package br.edu.green.web.util;

import org.apache.log4j.Logger;

public class SitisWebLog {

	// defining instance of this own class to use int the singleton pattern 
	private static SitisWebLog instance;
	
	// defining log object 
	private static Logger logger;
	
	
	public static SitisWebLog getInstanceof() {
	
		// verifying instance of the class
		if (SitisWebLog.instance == null) {
			SitisWebLog.instance = new SitisWebLog();
		}
		
		// returning the unique instance of the class
		return SitisWebLog.instance;		
	}

	// Constructor method
	private SitisWebLog() {
		SitisWebLog.logger = Logger.getLogger(SitisWebLog.class);
	}
	
	// methods that write log messages 
	public void error(String message) {
		logger.error(message);
	}

	public void debug(String message) {
		logger.debug(message);
	}

	public void fatal(String message) {
		logger.fatal(message);
	}

	public void info(String message) {
		logger.info(message);
	}

	public void trace(String message) {
		logger.trace(message);
	}

	public void warning(String message) {
		logger.warn(message);
	}
	
}
