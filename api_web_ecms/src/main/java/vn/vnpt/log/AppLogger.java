package vn.vnpt.log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import org.fluentd.logger.FluentLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableMap;

@Configuration
public class AppLogger {
	private static final Logger logger2 = LoggerFactory.getLogger(AppLogger.class);
    final String MODE_FLUENTD = "fluentd";
    @Value("${logging.mode}")
    String mode;

    private String localHost = "unknown";
    @Value("${logging.fluentd.host}")
    String host;
    @Value("${logging.fluentd.port}")
    int port;
    @Value("${logging.fluentd.tag}")
    String appname;

    private FluentLogger LOG = null;
    private Logger loggerInterface = null;
    ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

    public AppLogger() {
    	this(Logger.ROOT_LOGGER_NAME);
    }

    private AppLogger(String name) {
    	this.setLoggerName(name);
    	try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        	logger2.error("NoSuchFieldException occurred while processing field error.", e);
        }
    	if (MODE_FLUENTD.equals(mode)) {
            LOG = FluentLogger.getLogger(appname, host, port);
        }
    }
    
    private void setLoggerName(String name)
    {
    	loggerInterface = LoggerFactory.getLogger(name);
    }
    
    private static AppLogger logger = null;
    
    public static AppLogger getLogger()
    {
    	String name = Logger.ROOT_LOGGER_NAME;
    	StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    	if(stackTraceElements.length > 2)
    		name = stackTraceElements[2].getClassName();

    	if(logger == null)
    		logger = new AppLogger(name);
    	else
    		logger.setLoggerName(name);
    	return logger;
    }
 
    public void info(String message) {
        log(Level.INFO, message, null);
    }
    public void info(String message, Throwable throwable) {
        log(Level.INFO, message, throwable);
    }
    public void info(Map<String, Object> data) {
        log(Level.INFO, data, null);
    }
    public void info(String message, Map<String, Object> data) {
        log(Level.INFO, message, data, null);
    }
    public void info(Map<String, Object> data, Throwable throwable) {
        log(Level.INFO, data, throwable);
    }

    public void trace(String message) {
        log(Level.TRACE, message, null);
    }
    public void trace(String message, Throwable throwable) {
        log(Level.TRACE, message, throwable);
    }
    public void trace(Map<String, Object> data) {
        log(Level.TRACE, data, null);
    }
    public void trace(Map<String, Object> data, Throwable throwable) {
        log(Level.TRACE, data, throwable);
    }

    public void debug(String message) {
        log(Level.DEBUG, message, null);
    }
    public void debug(String message, Throwable throwable) {
        log(Level.DEBUG, message, throwable);
    }
    public void debug(Map<String, Object> data) {
        log(Level.DEBUG, data, null);
    }
    public void debug(Map<String, Object> data, Throwable throwable) {
        log(Level.DEBUG, data, throwable);
    }

    public void warn(String message) {
        log(Level.WARN, message, null);
    }
    public void warn(String message, Throwable throwable) {
        log(Level.WARN, message, throwable);
    }
    public void warn(Map<String, Object> data) {
        log(Level.WARN, data, null);
    }
    public void warn(Map<String, Object> data, Throwable throwable) {
        log(Level.WARN, data, throwable);
    }

    public void error(String message) {
        log(Level.ERROR, message, null);
    }
    public void error(String message, Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }
    public void error(Map<String, Object> data) {
        log(Level.ERROR, data, null);
    }
    public void error(Map<String, Object> data, Throwable throwable) {
        log(Level.ERROR, data, throwable);
    }

    public void log(Level level, String message, Throwable throwable) {
    	this.log(level, message, null, throwable);
    }

    public void log(Level level, Map<String, Object> data, Throwable throwable) {
        this.log(level, null, data, throwable);
    }

    public void log(Level level, String message, Map<String, Object> data, Throwable throwable) {
        if (MODE_FLUENTD.equals(mode)) {
        	ImmutableMap.Builder<String, Object> builder = new ImmutableMap.Builder<String, Object>();
        	if(!(message == null || message.equals("")))
        		builder.put("message", message);
        	if(data != null)
        		builder.putAll(data);
        	Map<String, Object> map = builder.put("@timestamp", sdf.get().format(new Date()))
							                    .put("timestamp",  Instant.now().toString())
							                    .put("ex_message", throwable != null ? throwable.getMessage() : "")
							                    .put("level", level.toString())
							                    .put("ip", localHost)
							                    .put("IP", localHost)
							                    .build();
            LOG.log(level.toString(), map);
        }
        this.doThrow(level, message, throwable);
    }

    private void doThrow(Level level, String message, Throwable throwable)
    {
        if (throwable != null)
        {
            switch (level) {
                case INFO:
                	loggerInterface.info(message, throwable);
                    break;
                case WARN:
                	loggerInterface.warn(message, throwable);
                    break;
                case TRACE:
                	loggerInterface.trace(message, throwable);
                    break;
                case ERROR:
                	loggerInterface.error(message, throwable);
                    break;
                case DEBUG:
                	loggerInterface.debug(message, throwable);
                    break;
                default:
                	break;
            }
        }
        else
        {
            switch (level) {
                case INFO:
                	loggerInterface.info(message);
                    break;
                case WARN:
                	loggerInterface.warn(message);
                    break;
                case TRACE:
                	loggerInterface.trace(message);
                    break;
                case ERROR:
                	loggerInterface.error(message);
                    break;
                case DEBUG:
                	loggerInterface.debug(message);
                    break;
                default:
                	break;
            }
        }
	}
}
