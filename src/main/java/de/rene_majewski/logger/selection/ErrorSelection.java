package de.rene_majewski.logger.selection;

/**
 * Specifies the selection of error messages.
 * 
 * @version 1.0
 * @since 0.1.0
 * @author René Majewski
 */
public class ErrorSelection {
  /**
   * Logs an error.
   * 
   * The following information must be passed in a string array:
   * 0: The error message.
   * 1: Stack trace of the error.
   * 
   * Example string array: {@code new String[] {"A test error was generated.", "de.rene_majewski.logger.selection.ErrorSelection (ErrorSelction.java:24)"}}.
   * 
   * Example output:
   * The following error occurred: A test error was generated.
   * de.rene_majewski.logger.selection.ErrorSelection (ErrorSelction.java:24)
   */
  public static final String ERROR = "E001";

}
