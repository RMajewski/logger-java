package de.rene_majewski.logger.selection;


/**
 * Specifies the selection of debug messages.
 * 
 * @version 1.0
 * @since 0.1.0
 * @author René Majewski
 */
public class DebugSelection extends SelectionAbstract {
  /**
   * A variable is to be logged.
   * 
   * The following information must be passed in a string array:
   * 0: Name of the variable, 
   * 1: Content of the variable
   * 
   * Example string array: {@code new String[] {"test", "This is a test."}}.
   * 
   * Example output: "test: This is a test".
   */
  public static final String DEBUG_OUT = "D001";

  /**
   * Log the call of a method.
   * 
   * The following information must be passed in a string array:
   * 0: Name of the methoad that was called.
   * 
   * Example string array: {@code new String[] {"test"}}
   * 
   * Example ouput: "The method test was called."
   */
  public static final String DEBUG_RUN_METHODE = "D002";

  /**
   * Logs the click on an item.
   * 
   * The following information must be passed in a string array:
   * 0: Name of the item that was clicked.
   * 
   * Example string array: {@code new String[] {"test"}}.
   * 
   * Example output: "It was clicked on test."
   */
  public static final String DEBUG_CLICK = "D010";

  /**
   * Logs that a directory has been created.
   * 
   * The following information must be passed in a string array:
   * 0: Name of the directory that was created.
   * 
   * Example string array: {@code new String[] {"/home/test"}}.
   * 
   * Example output: "The directory "/home/test" was created."
   */
  public static final String DEBUG_PATH_CREATE = "D020";

  /**
   * Logs that a directory was not created.
   * 
   * The following information must be passed in a string array:
   * 0: Name of the directory that was not created.
   * 
   * Example string array: {@code new String[] {"/home/test"}}.
   * 
   * Example output: "The directory "/home/test" could not be created."
   */
  public static final String DEBUG_PATH_NOT_CREATE = "D021";

  /**
   * Logs that an SQL command was executed.
   * 
   * The following information must be passed in a string array.
   * 0: SQL command that has been executed.
   * 
   * Example string array: {@code new String[] {"SELECT * FROM test"}}.
   * 
   * Example output: "The following SQL command was executed: SELECT * FROM test";
   */
  public static final String DEBUG_SQL_EXEC = "D200";

  /**
   * Logs that a database table was creted.
   * 
   * The following information must be passed in a string array:
   * 0: Name of the database table was created.
   * 1: SQL command that was used to create the database table.
   * 
   * Example string array: {@code new String[] {"test", "CREATE TABLE test(id INTEGER, description TEST)"}}
   * 
   * Example output:
   * The table test was created.
   * CREATE TABLE test(id INTEGER, description TEST)
   */
  public static String DEBUG_SQL_CREATE_TABLE = "D201";

  /**
   * Logs the SQL command for inserting into a database table.
   * 
   * The following information must be passed in a string array:
   * 0: Name of the table into which data has been inserted.
   * 1: SQL command used to insert into the database table.
   * 
   * Example String array: {@code new String[] {"test", "INSERT INTO test(description) VALUES("This is a test")"}}.
   * 
   * 
   * Example output:
   * The records were written to the test table.
   * INSERT INTO test(description) VALUES("This is a test")
   */
  public static String DEBUG_SQL_WRITE_RECORDS = "D202";

  /**
   * Logs the SQL command to delete a database table.
   * 
   * The following information must be passed in a string array:
   * 0: Name of the table that was deleted.
   * 1: SQL command used to delete the database table.
   * 
   * Example string array: {@code new String[] {"test", "DROP TABLE test"}}.
   * 
   * Example output:
   * The table test is deleted.
   * DROP TABLE test
   */
  public static String DEBUG_SQL_DROP_TABLE = "D203";

  /**
   * Logs the SQL command for retrieving records from a database table.
   * 
   * The following information must be passed i a string array:
   * 0: Name of the table from which records are retrieved.
   * 1: SQL command used to retrieve records from the database table.
   * 
   * Example string array: {@code new String[] {"test", "SELECT * FROM test"}}.
   * 
   * Example output:
   * Data is retrieved from table test.
   * SELECT * FROM test
   */
  public static String DEBUG_SQL_SELECT_TABLE = "D204"; 
}
