package de.rene_majewski.logger.selection;

/**
 * Specifies the selection of information messages.
 * 
 * @version 1.0
 * @since 0.1.0
 * @author René Majewski
 */
public class InformationSelection extends SelectionAbstract {
  /**
   * Logs that the logger has been started.
   * 
   * No string array must be passed.
   * 
   * Example output: "Logger was started."
   */
  public static final String INFO_START_LOGGER = "I001";

  /**
   * Logs the name of the version.
   * 
   * The following information must be passed in a string array:
   * 0: Information about the version.
   * 
   * Example string array: {@code new String[] {"0.1.0 alpha"}}
   * 
   * Example output: "Version of the logger: 0.1.0 alpha."
   */
  public static final String LOGGER_INFO_VERSION = "I002";

  /**
   * Logs the branch that was used to compile.
   * 
   * The following information must be passed in a string array:
   * 0: Information about the branch.
   * 
   * Example string array: {@code new String[] {"master"}}
   * 
   * Example output: "Branch used to compile the logger: master."
   */
  public static final String LOGGER_INFO_BRANCH = "I003";

  /**
   * Logs the commit that was used to compile.
   * 
   * The following information must be passed in a string array:
   * 0: Details about the commit.
   * 
   * Example string array: {@code new String[] {"master.1.eaf34a"}}
   * 
   * Exmaple output: "Commit used to compile the logger: master.1.eaf34a.".
   */
  public static final String LOGGER_INFO_COMMIT = "I004";

  /**
   * Logs the time at which the app was compiled.
   * 
   * The following information must be passed in a string array:
   * 0: Time when the app was compiled.
   * 
   * Example String array: {@code new String[] {"2020-09-19T12:32:51Z"}}
   * 
   * Example output: "The logger generated on 2020-09-19T12:32:51Z."
   */
  public static final String LOGGER_INFO_TIMESTAMP = "I005";

  /**
   * Logs the name of the app version.
   * 
   * The following information must be passed in a string array:
   * 0: Information about the app version.
   * 
   * Example string array: {@code new String[] {"0.1.0 alpha"}}
   * 
   * Example output: "Version of the app: 0.1.0 alpha."
   */
  public static final String AP_INFO_VERSION = "I006";

  /**
   * Logs the branch that was used to compile the app.
   * 
   * The following information must be passed in a string array:
   * 0: Information about the branch.
   * 
   * Example string array: {@code new String[] {"master"}}
   * 
   * Example output: "Branch used to compile the app: master."
   */
  public static final String AP_INFO_BRANCH = "I007";

  /**
   * Logs the commit that was used to compile the app.
   * 
   * The following information must be passed in a string array:
   * 0: Details about the commit.
   * 
   * Example string array: {@code new String[] {"master.1.eaf34a"}}
   * 
   * Exmaple output: "Commit used to compile the app: master.1.eaf34a.".
   */
  public static final String AP_INFO_COMMIT = "I008";

  /**
   * Logs the time at which the app was compiled.
   * 
   * The following information must be passed in a string array:
   * 0: Time when the app was compiled.
   * 
   * Example String array: {@code new String[] {"2020-09-19T12:32:51Z"}}
   * 
   * Example output: "The app generated on 2020-09-19T12:32:51Z."
   */
  public static final String AP_INFO_TIMESTAMP = "I009";

  /**
   * Logs that the connection to the database was established.
   * 
   * No string array must be passed.
   * 
   * Example output: "Connection to the database was established."
   */
  public static String INFO_SQL_DB_OPEN_CON = "I200";

  /**
   * Logs that the connection to the database could not be established.
   * 
   * No string array must be passed.
   * 
   * Example output: "Connection to the database could not be established."
   */
  public static String INFO_SQL_DB_NOT_OPEND_CON = "I201";

  /**
   * Logs that the connection to the database was closed.
   * 
   * No string array must be passed.
   * 
   * Example output: "The connection to the database has ended."
   */
  public static String INFO_SQL_DB_END_CON = "I202";

}
