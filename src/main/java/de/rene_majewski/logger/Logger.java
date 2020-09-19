package de.rene_majewski.logger;

/**
 * Logs the various messages.
 * 
 * @since 0.1.0
 * @author René Majewski
 */
public class Logger {
  /**
   * Saves the instance of this class.
   */
  private Logger instance;

  /**
   * Initializes the logger.
   * 
   * For more detailed configuration information see {@link #setConfiguration}.
   */
  private Logger() {
  }

  /**
   *  Returns the instance of the logger.
   * 
   * @return Instance of the logger.
   */
  public Logger getInstance() {
    if (instance == null) {
      instance = new Logger();
    }
    return instance;
  }

  /**
   * Creates the configuration.
   */
  protected void setConfiguration() {
  }
}
