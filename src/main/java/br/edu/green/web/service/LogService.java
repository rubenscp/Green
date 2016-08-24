package br.edu.green.web.service;

import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;

import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;

/**
 * <br>
 * <p>
 * <b>Class responsible for writing and retrieve messages in the log file.</b>
 * </p>
 * <p>
 * The log is stored in a text file (asc ii) whose location and name are configured on the system configuration file. By default it is in the log folder with
 * the name sitis.log.
 * </p>
 * <p>
 * The system log level is also set in the configuration file. It may contain the following levels, from least restrictive to most restrictive: <br>
 * <br>
 * <tt>ALL -> DEBUG -> INFO -> WARNING -> ERROR -> FATAL.</tt><br>
 * <br>
 * If prompted to write a message from a lower level (less restrictive) than the level set for the system log, it will simply be discarded. For example, say
 * that the level set for the system is "ERROR", then only messages with the level "ERROR" and "FATAL" will be written in the log.
 * </p>
 * <br>
 * <p>
 * <i>The system log is implemented using the framework Log4J.</i>
 * </p>
 * 
 * @author Sergio Lopes Jr.
 * @version 0.2
 */
public class LogService {

	// constants
	public static final String SITISWEB_LOG_FILE_NAME = "sitisweb.log.file.name";
	public static final String SITISWEB_LOG_SIZE_MAXIMUM = "sitisweb.log.size.maximum";
	public static final String SITISWEB_LOG_LEVEL = "sitisweb.log.level";
	public static final String SITISWEB_LOG_PATTERN = "sitisweb.log.pattern";
	public static final String SITISWEB_LOG_HEADER = "sitisweb.log.header";

	public static final int LEVEL_OFF = 0;
	public static final int LEVEL_DEBUG = 1;
	public static final int LEVEL_INFO = 2;
	public static final int LEVEL_WARNING = 3;
	public static final int LEVEL_ERROR = 4;
	public static final int LEVEL_FATAL = 5;
	public static final int LEVEL_ALL = 6;
	public static final String MANDATORY_PATTERN_LOG = "%2d{dd/MM/yyyy HH:mm} ; %5p ; ";
	public static final int MANDATORY_PATTERN_LOG_SIZE = 19;

	public static final String SEPARATOR = ";--------------------------------------------------------------------------------";
	public static final String SPACE = "";
	public static final String CHARSET = "UTF-8";
	public static final String LINE_FEED = "\n";

	public static final int DATE_START_POSITION = 0;
	public static final int DATE_END_POSITION = 10;
	public static final int LEVEL_START_POSITION = 17;
	public static final int LEVEL_END_POSITION = 22;

	private static Appender logAppender;

	private ApplicationConfigurationService applicationConfiguration;
	private ApplicationMessageService applicationMessage;
	private Logger logger;
	private Map<Integer, Level> levels;

	/**
	 * Public constructor with the object that will use the log. The name of the object class is recovered and used to identify the messages written to the log.
	 * 
	 * @param logClient
	 *            Object that will use this instance of LogService.
	 * @throws GeneralException
	 *             The general exception object
	 */
	public LogService(Object logClient) throws GeneralException {

		try {
			// recovering the parameters of configuration of the system loading current messages file
			this.applicationConfiguration = ApplicationConfigurationService.getInstanceof();

			// recovering the messages of the system
			this.applicationMessage = ApplicationMessageService.getInstanceof();

			// mapping the log file levels to the values used in class
			this.levels = new HashMap<Integer, Level>();
			this.levels.put(LogService.LEVEL_OFF, Level.OFF);
			this.levels.put(LogService.LEVEL_DEBUG, Level.DEBUG);
			this.levels.put(LogService.LEVEL_INFO, Level.INFO);
			this.levels.put(LogService.LEVEL_WARNING, Level.WARN);
			this.levels.put(LogService.LEVEL_ERROR, Level.ERROR);
			this.levels.put(LogService.LEVEL_FATAL, Level.FATAL);
			this.levels.put(LogService.LEVEL_ALL, Level.ALL);

			// creating the log4j logger
			String className = logClient.getClass().getSimpleName();
			this.logger = Logger.getLogger(className);

			// setting the level of the log
			this.setLevel(this.applicationConfiguration.getIntegerValue(LogService.SITISWEB_LOG_LEVEL));

			// ATTENTION: the method below dooesn't need to run because the logger object already contains root looger by default.
			// configuring the console layout
			// this.getConsoleAppender().setLayout(this.getLogLayout());

			// adding the file appender to log
			// this.logger.addAppender(this.getFileAppender());

			// writing the initialization information log
			// this.info(this.applicationMessage.getMessage(ProcessingResult.Code.INITIALIZING_SERVICE_INFORMATION_SUCCESS.name(), className));

		} catch (GeneralException ge) {
			// writing log message
			System.out.println(ge.toString());

			// throwing general exception
			throw ge;

		} catch (Exception e) {
			// Creating new instance of general exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE, this.applicationMessage.getMessage(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE.name())));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("Constructor LogService");
			ge.setExceptionOriginalMessage(e.getMessage()); // the original exception message

			// writing log message
			System.out.println(ge.toString());

			// throwing general exception
			throw ge;
		}
	}

	/**
	 * Retrieves the current level set to the system log. Only messages compatible with this level are written to the log file.
	 * 
	 * @return int - The current level set to the system log.
	 */
	public int getLevel() {
		return this.logger.getLevel().toInt();
	}

	/**
	 * Sets the desired level for the system log. Only messages compatible with this level is written to the system log file.
	 * 
	 * @param level
	 *            Desired level for the system log.
	 */
	public void setLevel(int level) {
		Level levelLog = this.levels.get(level);
		if (levelLog != null) {
			this.logger.setLevel(levelLog);
		}
	}

	/**
	 * Write a line filled by hyphens ("-") in the system log file.
	 */
	public void separator() {
		this.logger.debug(LogService.SEPARATOR);
	}

	/**
	 * Write a blank line in the system log file.
	 */
	public void space() {
		this.logger.debug(LogService.SPACE);
	}

	/**
	 * Write the message in the system log file with the level "DEBUG". If the current level set to the system log is not compatible with that level, the
	 * message is discarded.
	 * 
	 * @param message
	 *            Message to be written to the system log file.
	 */
	public void debug(String message) {
		this.logger.debug(this.removeLineFeed(message));
	}

	/**
	 * Write the message in the system log file with the level "INFO". If the current level set to the system log is not compatible with that level, the message
	 * is discarded.
	 * 
	 * @param message
	 *            Message to be written to the system log file.
	 */
	public void info(String message) {
		// this.logger.info(this.removeLineFeed(message));
		this.logger.info(message);
	}

	/**
	 * Write the message in the system log file with the level "WARNING". If the current level set to the system log is not compatible with that level, the
	 * message is discarded.
	 * 
	 * @param message
	 *            Message to be written to the system log file.
	 */
	public void warning(String message) {
		this.logger.warn(this.removeLineFeed(message));
	}

	/**
	 * Write the message in the system log file with the level "ERROR". If the current level set to the system log is not compatible with that level, the
	 * message is discarded.
	 * 
	 * @param message
	 *            Message to be written to the system log file.
	 */
	public void error(String message) {
		this.logger.error(this.removeLineFeed(message));
	}

	/**
	 * Write the message in the system log file with the level "FATAL". If the current level set to the system log is not compatible with that level, the
	 * message is discarded.
	 * 
	 * @param message
	 *            Message to be written to the system log file.
	 */
	public void fatal(String message) {
		this.logger.fatal(this.removeLineFeed(message));
	}

	/**
	 * Retrieves messages stored in the system log file with the given level.
	 * 
	 * @param level
	 *            Level associated with the message during your written to the system log file.
	 * @return The messages stored in the system log file with the given level. Each message is separated by a line end break ("\ n").
	 * @throws GeneralException
	 *             The general exception object
	 */
	public String list(int level) throws GeneralException {
		return this.list(level, null, null);
	}

	/**
	 * Retrieves messages stored in the system log file with the given level and the time informed.
	 * 
	 * 
	 * @param level
	 *            Level associated with the message during your written to the system log file.
	 * @param startDate
	 *            Date (dd/mm/yyyy) from which you want to retrieve messages.
	 * @param endDate
	 *            Date (dd/mm/yyyy) until which still want to retrieve the messages.
	 * @return The messages stored in the system log file with the level and the time informed. Each message is separated by a line end break ("\ n").
	 * @throws GeneralException
	 *             The general exception object
	 */
	public String list(int level, Date startDate, Date endDate) throws GeneralException {

		// reading the log file
		StringBuilder logText = new StringBuilder();
		Scanner log = null;
		String logLine;
		try {
			log = new Scanner(new FileInputStream(this.applicationConfiguration.getStringValue(LogService.SITISWEB_LOG_FILE_NAME)));
			while (log.hasNextLine()) {
				// reading next line of the file
				logLine = log.nextLine();

				// adding the line read the return string, if you have the level of log and desired date
				if (this.isDesiredLevel(logLine, level) && this.isDesiredDate(logLine, startDate, endDate)) {
					logText.append(logLine + LogService.LINE_FEED);
				}
			}
			// TODO
			// } catch (SitisEmbeddedParameterUnknownException e1) {
			// throw new SitisEmbeddedServiceException(this.applicationMessage.getResumeMessage("LOG_LogService_003"), null,
			// SitisEmbeddedActionStatus.ERROR_GENERAL_PARAM_UNKNOWN, this.getClass().getSimpleName(), "list", e1.getMessage());
			// } catch (FileNotFoundException e2) {
			// throw new SitisEmbeddedServiceException(this.applicationMessage.getResumeMessage("LOG_LogService_003"), null,
			// SitisEmbeddedActionStatus.ERROR_GENERAL_FILE_LOG, this.getClass().getSimpleName(), "list", e2.getMessage());
		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE, this.applicationMessage.getMessage(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE.name(), "LogService")));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("list");
			ge.setExceptionOriginalMessage(e.getMessage()); // the original exception message
			System.out.println("rubens logservice " + ge.getMessage());

		} finally {
			// closing the log file
			log.close();
		}

		// returned the string containing the lines read with the desired level of log
		return logText.toString();
	}

	/**
	 * Clears the log contents.
	 */
	public void clear() {

		if (LogService.logAppender != null) {
			((RollingFileAppender) LogService.logAppender).rollOver();
		}
	}

	/**
	 * Checks if the message level on the line is the informed level.
	 * 
	 * @param logLine
	 *            Row retrieved from the system log file.
	 * @param level
	 *            Desired level for the log message.
	 * @return True, if the message in line is informed level.
	 */
	private boolean isDesiredLevel(String logLine, int level) {

		// checking that the desired level is valid
		Level desiredLevel = null;
		switch (level) {
			case LogService.LEVEL_ALL:
				return true;
			case LogService.LEVEL_OFF:
				return false;
			default:
				desiredLevel = this.levels.get(level);
				if (desiredLevel == null) {
					return false;
				}
		}

		// recovering the level log of the read line
		String logLevel = logLine.substring(LogService.LEVEL_START_POSITION, LogService.LEVEL_END_POSITION).trim();

		// checking the level log of the read line
		if (logLevel.equals(desiredLevel.toString())) {
			return true;
		}

		// does not match the desired level
		return false;
	}

	/**
	 * Checks whether the date message on the line is comprised within the reporting period.
	 * 
	 * @param lineLog
	 *            Row retrieved from the system log file.
	 * @param startDate
	 *            Date (dd/mm/yyyy) from which you want to retrieve messages.
	 * @param endDate
	 *            Date (dd/mm/yyyy) until which still want to retrieve the messages.
	 * @return True, if the message date in the line is comprised within the reporting period.
	 */
	private boolean isDesiredDate(String lineLog, Date startDate, Date endDate) {

		// If the dates are invalid, automatically the method returns true
		if (startDate == null || endDate == null) {
			return true;
		}

		// recovering the level log of the read line
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		Date logDate;
		try {
			logDate = (Date) dateFormatter.parse(lineLog.substring(LogService.DATE_START_POSITION, LogService.DATE_END_POSITION));
		} catch (ParseException e) {
			this.error(this.applicationMessage.getMessage("LOG_LogService_004", e.getMessage()));
			return false;
		}

		// checking the date of the read line
		if (logDate.compareTo(startDate) >= 0 && logDate.compareTo(endDate) <= 0) {
			return true;
		}

		// does not match the desired date
		return false;
	}

	/**
	 * Remove return e line feed characters of the message to be written in the log.
	 * 
	 * @param message
	 *            Message to be written to the log.
	 * @return Message with the return and line feed characters removed.
	 */
	private String removeLineFeed(String message) {
		message = message.replaceAll("\\r+", "\\\\r");
		message = message.replaceAll("\\n+", "\\\\n");
		return message;
	}

	/**
	 * Gets ConsoleAppender object from logger.
	 * 
	 * @return ConsoleAppender object from logger.
	 */
	private Appender getConsoleAppender() {
		Logger rootLogger = Logger.getRootLogger();
		String xxxx = rootLogger.getName();
		Enumeration<Appender> appenders = rootLogger.getAllAppenders();
		Appender appender = appenders.nextElement();
		return (Appender) Logger.getRootLogger().getAllAppenders().nextElement();
	}

	/**
	 * Gets Appender object associated to log file.
	 * 
	 * @return Appender object associated to log file.
	 * @throws GeneralException
	 *             The general exception object
	 */
	private Appender getFileAppender() throws GeneralException {

		String logFile = "";
		try {
			if (LogService.logAppender == null) {

				// adding a root appender with output to a text file
				logFile = this.applicationConfiguration.getStringValue(LogService.SITISWEB_LOG_FILE_NAME);
				PatternLayout logLayout = new SitisPatternLayout(LogService.MANDATORY_PATTERN_LOG + this.applicationConfiguration.getStringValue(LogService.SITISWEB_LOG_PATTERN));
				LogService.logAppender = new RollingFileAppender(logLayout, logFile);
				((RollingFileAppender) LogService.logAppender).setMaxBackupIndex(0);
				((RollingFileAppender) LogService.logAppender).setMaxFileSize(this.applicationConfiguration.getStringValue(LogService.SITISWEB_LOG_SIZE_MAXIMUM));

				// setting the charset of the log file
				((FileAppender) LogService.logAppender).setEncoding(LogService.CHARSET);
			}

			// returning the appender to the log file
			return LogService.logAppender;

		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE, this.applicationMessage.getMessage(Code.INITIALIZING_SERVICE_EXCEPTION_FAILURE.name(), "LogService")));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("getFileAppender");
			ge.setExceptionOriginalMessage(e.getMessage()); // the original exception message
			System.out.println("rubens 1 logservice " + ge.toString());
			throw ge;
		}
	}

	/**
	 * Gets Layout object from logger.
	 * 
	 * @return Layout object from logger.
	 * @throws GeneralException
	 *             The general exception object
	 */
	private Layout getLogLayout() throws GeneralException {
		return this.getFileAppender().getLayout();
	}

}

/**
 * Nested class for logging formatting.
 * 
 */
class SitisPatternLayout extends PatternLayout {

	private SimpleDateFormat dateFormatter;
	private ApplicationConfigurationService applicationConfiguration;

	/**
	 * Public constructor with the string pattern will use the logging formatting.
	 * 
	 * @throws SitisEmbeddedServiceException
	 *             Occurrence of any problems at start of system service.
	 */
	public SitisPatternLayout(String pattern) throws GeneralException {
		super(pattern);

		// initializing variables
		this.applicationConfiguration = ApplicationConfigurationService.getInstanceof();
		this.dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ; ");
	}

	@Override
	public String format(LoggingEvent event) {

		// changing the date of the event
		try {
			return this.dateFormatter.format(new Date()) + super.format(event).substring(LogService.MANDATORY_PATTERN_LOG_SIZE);
		} catch (Exception e) {
			return this.dateFormatter.format(new Date()) + super.format(event);
		}
	}

	@Override
	public String getHeader() {
		try {
			return this.applicationConfiguration.getStringValue(LogService.SITISWEB_LOG_HEADER) + "\n";
		} catch (Exception e) {
		}
		return "\n";
	}

}
