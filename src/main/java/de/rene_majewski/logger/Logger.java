package de.rene_majewski.logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.FilterComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import de.rene_majewski.logger.selection.DebugSelection;
import de.rene_majewski.logger.selection.ErrorSelection;
import de.rene_majewski.logger.selection.InformationSelection;
import de.rene_majewski.logger.utils.CombinedResourceBundle;

/**
 * Logs the various messages.
 * 
 * @version 1.0
 * @since 0.1.0
 * @author René Majewski
 */
public class Logger {
  /**
   * Specifies the beginning of the property files that are included in this
   * project.
   */
  public static final String RES_BUNDLE = "messages/logger";

  /**
   * Specifies the directory and name of the configuration file of versions
   * informations.
   */
  public static final String RES_CONFIG_FILE = "config/logger_version.propertie";

  /**
   * Saves the instance of this class.
   */
  private Logger instance;

  /**
   * Saves the directory where the logs are to be saved.
   */
  private static String logPath;

  /**
   * Saves the directory for message files.
   */
  private static String messagesPath;

  /**
   * Which log level should be used for the root logger?
   */
  private static Level levelRoot;

  /**
   * Should be output via stdout?
   * 
   * {@code true}, if output via stdout is desired. {@code false} if not.
   */
  private static boolean stdout;

  /**
   * Which log level should be used for stdout?
   * 
   * @see Level
   */
  private static Level levelStdout;

  /**
   * Should the logger write the output to a text file?
   * 
   * {@code true}, if output via stdout is desired. {@code false} if not.
   */
  private static boolean txtFile;

  /**
   * Which log level should be used for the text file?
   * 
   * @see Level
   */
  private static Level levelTxtFile;

  /**
   * Should the logger write the output to an HTML file?
   * 
   * {@code true}, if output via stdout is desired. {@code false} if not.
   */
  private static boolean htmlFile;

  /**
   * Which log level should be used for the html file?
   */
  private static Level levelHtmlFile;

  /**
   * Which environment is used?
   */
  private static String environment;

  /**
   * Resource Bundle of the logger messages.
   */
  private ResourceBundle messages;

  /**
   * Initializes the logger.
   * 
   * For more detailed configuration information see {@link #setConfiguration}.
   */
  private Logger() {
    if (environment == null) {
      environment = "production";
    }

    createConfiguration();

    if (messagesPath != null) {
      try {
        List<String> list = new ArrayList<>();
        list.add(RES_BUNDLE);
        if (Logger.messagesPath != null && !Logger.messagesPath.isEmpty()) {
          list.add(Logger.messagesPath);
        }
        messages = new CombinedResourceBundle(list);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    log(Level.INFO, this, InformationSelection.INFO_START_LOGGER, null);

    Properties prop = readVersionProperties();

    if (prop.size() > 0) {
      log(Level.INFO, this, InformationSelection.LOGGER_INFO_VERSION, new String[] {prop.getProperty("project.version")});
      log(Level.INFO, this, InformationSelection.LOGGER_INFO_BRANCH, new String[] {prop.getProperty("build.branch")});
      log(Level.INFO, this, InformationSelection.LOGGER_INFO_COMMIT, new String[] {prop.getProperty("build.number")});
      log(Level.INFO, this, InformationSelection.LOGGER_INFO_TIMESTAMP, new String[] {prop.getProperty("build.time")});
    }
  }

  /**
   * Returns the instance of the logger.
   * 
   * @return Instance of the logger.
   */
  public synchronized Logger getInstance() {
    if (instance == null) {
      instance = new Logger();
    }
    return instance;
  }

  /**
   * Creates the configuration.
   */
  // TODO Read configuration from properties file.
  protected void createConfiguration() {
    if (levelRoot == null) {
      levelRoot = Level.DEBUG;
    }

    if (levelStdout == null) {
      levelStdout = Level.DEBUG;
    }

    if (levelTxtFile == null) {
      levelHtmlFile = Level.DEBUG;
    }

    if (levelHtmlFile == null) {
      levelHtmlFile = Level.WARN;
    }

    ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationFactory.newConfigurationBuilder();
    RootLoggerComponentBuilder rootLogger = builder.newRootLogger(levelRoot);

    // Layouts
    LayoutComponentBuilder standard = builder.newLayout("patternLayout");
    standard.addAttribute("pattern", "%d{dd.MM.yyyy HH:mm:ss:SSS} %-5p [%t] %c: %m%n");
    standard.addAttribute("charset", "UTF-8");

    LayoutComponentBuilder htmlError = builder.newLayout("HtmlLayout");
    htmlError.addAttribute("charset", "UTF-8");
    htmlError.addAttribute("title", "Error-Report");
    htmlError.addAttribute("fontSize", "12");

    // Filter
    FilterComponentBuilder filterHtml = builder.newFilter(
      "ThresholdFilter", 
      Filter.Result.ACCEPT, 
      Filter.Result.DENY
    );

    if (environment.equals("development")) {
      filterHtml.addAttribute("level", Level.ALL);
    } else {
      filterHtml.addAttribute("level", Level.WARN);
    }

    // stdout
    if (Logger.stdout) {
      AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
      console.add(standard);
      builder.add(console);
      rootLogger.add(builder.newAppenderRef("stdout"));
    }

    // Text file
    if (Logger.txtFile) {
      AppenderComponentBuilder file = builder.newAppender("fileText", "File");
      file.addAttribute("fileName", Paths.get(logPath, environment + ".txt"));
      file.addAttribute("append", false);
      file.add(standard);
      builder.add(file);
      rootLogger.add(builder.newAppenderRef("fileText"));
    }

    // HTML file
    if (Logger.htmlFile) {
      AppenderComponentBuilder html = builder.newAppender("fileHtml", "File");
      html.addAttribute("fileName", Paths.get(logPath, environment + ".html"));
      html.addAttribute("append", false);
      html.add(htmlError);
      html.add(filterHtml);
      builder.add(html);
      rootLogger.add(builder.newAppenderRef("fileHtml"));
    }

    Configurator.initialize(builder.build());
  }

  /**
   * Read the configuration file for version informations and saves into
   * property.
   * 
   * @return The configuration properties for version informations.
   */
  protected synchronized Properties readVersionProperties() {
    Properties result = new Properties();

    try {
      InputStream inp = getClass().getResourceAsStream(RES_CONFIG_FILE);
      if (inp != null) {
        result.load(new InputStreamReader(inp, "UTF-8"));
      }
    } catch (IOException e) {
      getInstance().error(this, e);
    }

    return result;
  }

  /**
   * Generates an error message from the transferred error.
   * 
   * @param level Level of the message.
   * 
   * @param caller Class that generates the error message.
   * 
   * @param err Error from which the error message should be created.
   */
  protected synchronized void writeErrorMessage(Level level, Object caller, Exception err) {
    StringBuffer buffer = new StringBuffer();

    for (StackTraceElement el : err.getStackTrace()) {
      buffer.append(el.toString());
      buffer.append("\n");
    }

    log(level, caller, ErrorSelection.ERROR, new String[] {err.getMessage(), buffer.toString()});
  }

  /**
   * Sets the directory for the log files.
   * 
   * @param path Directory for the log files.
   */
  public static void setLogPath(String path) {
    Logger.logPath = path;
  }

  /**
   * Serts the directory for the messages files.
   * 
   * @param path Directory for the messages files.
   */
  public static void setMessagesPath(String path) {
    Logger.messagesPath = path;
  }

  /**
   * Set the configuration.
   * 
   * @param rootLevel Whicht log level should be used for the root logger?
   * 
   * @param stdout Should be output via stdout?
   * 
   * @param stdoutLevel Which log level should be used for stdout?
   * 
   * @param txtFile Should the logger write the output to a text file?
   * 
   * @param txtFileLevel Which log level should be used for text file?
   * 
   * @param htmlFile Should the logger write the output to a html file?
   * 
   * @param htmlFileLevel Which log level should be used for html file?
   */
  public static void setConfiguration(Level rootLevel, boolean stdout,
                                      Level stdoutLevel, boolean txtFile,
                                      Level txtFileLevel, boolean htmlFile,
                                      Level htmlFileLevel,
                                      String environment) {
    Logger.levelRoot = rootLevel;
    Logger.stdout = stdout;
    Logger.levelStdout = stdoutLevel;
    Logger.txtFile = txtFile;
    Logger.levelTxtFile = txtFileLevel;
    Logger.htmlFile = htmlFile;
    Logger.levelHtmlFile = htmlFileLevel;
    Logger.environment = environment;
  }

  /**
   * Logs the message.
   * 
   * @param level Level of the message.
   * 
   * @param caller Class that created the message.
   * 
   * @param message Message to be logged.
   */
  public synchronized void log(Level level, Object caller, String message) {
    org.apache.logging.log4j.Logger log4j = LogManager.getLogger(caller.getClass().getName());

    if (level == Level.TRACE || level == Level.ALL) {
      log4j.trace(message);
    } else if (level == Level.DEBUG) {
      log4j.debug(message);
    } else if (level == Level.INFO) {
      log4j.info(message);
    } else if (level == Level.WARN) {
      log4j.warn(message);
    } else if (level == Level.ERROR) {
      log4j.error(message);
    } else if (level == Level.FATAL) {
      log4j.fatal(message);
    }
  }


  /**
   *  Logs the message with the given ID.
   * 
   * @param level Level of the message.
   * 
   * @param caller Class that created the message.
   * 
   * @param id ID of the message to be logged.
   * 
   * @param params Parameters to be passed to the message.
   */
  public synchronized void log(Level level, Object caller, String id, Object[] params) {
    String message = id;
    if (messages != null) {
      try {
        message = messages.getString(id);
      } catch (MissingResourceException e) {
        error(this, "The resource ID '" + id + "' was not found");
      }
    }

    if (params != null) {
      message = MessageFormat.format(message, params);
    }

    log(level, caller, message);
  }

  /**
   * Generates a debug message.
   * 
   * @param caller Class that created the message.
   * 
   * @param id ID of the message to be logged.
   * 
   * @param params Parameters to be passed to the message.
   */
  public synchronized void debug(Object caller, String id, Object[] params) {
    log(Level.DEBUG, caller, id, params);
  }

  /**
   * Generates a debug message.
   * 
   * @param caller Class that created the message.
   * 
   * @param id ID of the message to be logged.
   * 
   * @param params Parameters to be passed to the message.
   */
  public synchronized void debug(Object caller, String variableName, String variableValue) {
    log(Level.DEBUG, caller, DebugSelection.DEBUG_OUT, new String[] {variableName, variableValue});
  }

  /**
   * Generates a debug message.
   * 
   * @param caller Class that created the message.
   * 
   * @param params Parameters to be passed to the message.
   */
  public synchronized void debug(Object caller, String message) {
    log(Level.DEBUG, caller, message);
  }

  /**
   * Generates an information message.
   * 
   * @param caller Class that created the message.
   * 
   * @param id ID of the message to be logged.
   * 
   * @param params Parameters to be passed to the message.
   */
  public synchronized void info(Object caller, String id, Object[] params) {
    log(Level.INFO, caller, id, params);
  }

  /**
   * Generates an information mesage.
   * 
   * @param caller Class that created the message.
   * 
   * @param id ID of the message to be logged.
   * 
   * @param params Parameters to be passed to the message.
   */
  public synchronized void info(Object caller, String message) {
    log(Level.INFO, caller, message);
  }

  /**
   * Generates a warning message.
   * 
   * @param caller Class that created the message.
   * 
   * @param id ID of the message to be logged.
   * 
   * @param params Parameters to be passed to the message.
   */
  public synchronized void warn(Object caller, String id, Object[] params) {
    log(Level.WARN, caller, id, params);
  }

  /**
   * Generates a warninung message.
   * 
   * @param caller Class that created the message.
   * 
   * @param params Parameters to be passed to the message.
   */
  public synchronized void warn(Object caller, String message) {
    log(Level.WARN, caller, message);
  }

  /**
   * Generates a error message.
   * 
   * @param caller Class that created the message.
   * 
   * @param id ID of the message to be logged.
   * 
   * @param params Parameters to be passed to the message.
   */
  public synchronized void error(Object caller, String id, Object[] params) {
    log(Level.ERROR, caller, id, params);
  }

  /**
   * Generates a error message.
   * 
   * @param caller Class that created the message.
   * 
   * @param err Error from which the error message should be created.
   */
  public synchronized void error(Object caller, Exception err) {
    writeErrorMessage(Level.ERROR, caller, err);
  }

  /**
   * Generates a error message.
   * 
   * @param caller Class that created the message.
   * 
   * @param message The error message to be logged.
   */
  public synchronized void error(Object caller, String message) {
    LogManager.getLogger(caller.getClass().getName()).error(message);
  }

  /**
   * Generates a fatal error message
   * 
   * @param caller Class that created the message.
   * 
   * @param id ID of the message to be logged.
   * 
   * @param params Parameters to be passed to the message.
   */
  public synchronized void fatal(Object caller, String id, Object[] params) {
    log(Level.FATAL, caller, id, params);
  }

  /**
   * Generates a fatal error Message
   * 
   * @param caller Class that created the message.
   * 
   * @param err Error from which the error message should be created.
   */
  public synchronized void fatal(Object caller, Exception err) {
    writeErrorMessage(Level.FATAL, caller, err);
  }

  /**
   * Generates a fatal error message.
   * 
   * @param caller Class that created the message.
   * 
   * @param message The error message to be logged.
   */
  public synchronized void fatal(Object caller, String message) {
    LogManager.getLogger(caller.getClass().getName()).fatal(message);
  }
}
