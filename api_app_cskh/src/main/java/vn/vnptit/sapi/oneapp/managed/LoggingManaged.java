package vn.vnptit.sapi.oneapp.managed;

import com.google.common.collect.ImmutableMap;
import org.fluentd.logger.FluentLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class LoggingManaged {
    final static String MODE_FLUENTD = "fluentd";
    @Value("${logging.mode}")
    String mode;

    private String localHost = "unknown";
    @Value("${logging.fluentd.host}")
    String host;
    @Value("${logging.fluentd.port}")
    int port;
    @Value("${logging.fluentd.tag}")
    String appname;

    private static FluentLogger LOG = null; //FluentLogger.getLogger("app");
    private static Logger rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    static ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));

    @PostConstruct
    void postConstruct() {
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (MODE_FLUENTD.equals(mode)) {
            LOG = FluentLogger.getLogger(appname, host, port);
        }
    }

    @PreDestroy
    void preDestroy() {
        if (MODE_FLUENTD.equals(mode)) {
            LOG.close();
        }
    }
    @Async
    public void info(String message) {
        log(Level.INFO, message, null);
    }
    @Async
    public void info(String message, Throwable throwable) {
        log(Level.INFO, message, throwable);
    }
    @Async
    public void info(Map<String, Object> data) {
        log(Level.INFO, data, null);
    }
    @Async
    public void info(String message, Map<String, Object> data) {
        log(Level.INFO, message, data, null);
    }
    @Async
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

    @Async
    public void error(String message) {
        log(Level.ERROR, message, null);
    }
    @Async
    public void error(String message, Throwable throwable) {
        log(Level.ERROR, message, throwable);
    }
    @Async
    public void error(Map<String, Object> data) {
        log(Level.ERROR, data, null);
    }
    @Async
    public void error(Map<String, Object> data, Throwable throwable) {
        log(Level.ERROR, data, throwable);
    }

    public void log(Level level,String message, Throwable throwable) {
        if (MODE_FLUENTD.equals(mode)) {
            Map<String, Object> dat = new ImmutableMap.Builder<String, Object>()
                    .put("message", message)
                    //.put("@timestamp", sdf.get().format(new Date()))
                    .put("timestamp",  Instant.now().toString())
                    .put("ex_message", throwable != null ? throwable.getMessage() : "")
                    .put("level", level.toString())
                    //.put("ip", localHost)
                    .put("IP", localHost)
                    .build();
            LOG.log(level.toString(), dat);
        }

        if (throwable != null)
            switch (level) {
                case INFO:
                    rootLogger.info(message, throwable);
                    break;
                case WARN:
                    rootLogger.warn(message, throwable);
                    break;
                case TRACE:
                    rootLogger.trace(message, throwable);
                    break;
                case ERROR:
                    rootLogger.error(message, throwable);
                    break;
                case DEBUG:
                    rootLogger.debug(message, throwable);
                    break;
                default:
                    break;
            }
        else
            switch (level) {
                case INFO:
                    rootLogger.info(message);
                    break;
                case WARN:
                    rootLogger.warn(message);
                    break;
                case TRACE:
                    rootLogger.trace(message);
                    break;
                case ERROR:
                    rootLogger.error(message);
                    break;
                case DEBUG:
                    rootLogger.debug(message);
                    break;
                default:
                    break;
            }
    }

    public void log(Level level, Map<String, Object> data, Throwable throwable) {
        if (MODE_FLUENTD.equals(mode)) {
            Map<String, Object> dat = new ImmutableMap.Builder<String, Object>()
                    .putAll(data)
                    .put("@timestamp", sdf.get().format(new Date()))
                    .put("timestamp",  Instant.now().toString())
                    .put("ex_message", throwable != null ? throwable.getMessage() : "")
                    .put("level", level.toString())
                    .put("ip", localHost)
                    .put("IP", localHost)
                    .build();
            LOG.log(level.toString(), dat);
        }

        if (throwable != null)
            switch (level) {
                case INFO:
                    rootLogger.info(data.toString(), throwable);
                    break;
                case WARN:
                    rootLogger.warn(data.toString(), throwable);
                    break;
                case TRACE:
                    rootLogger.trace(data.toString(), throwable);
                    break;
                case ERROR:
                    rootLogger.error(data.toString(), throwable);
                    break;
                case DEBUG:
                    rootLogger.debug(data.toString(), throwable);
                    break;
                default:
                    break;
            }
        else
            switch (level) {
                case INFO:
                    rootLogger.info(data.toString());
                    break;
                case WARN:
                    rootLogger.warn(data.toString());
                    break;
                case TRACE:
                    rootLogger.trace(data.toString());
                    break;
                case ERROR:
                    rootLogger.error(data.toString());
                    break;
                case DEBUG:
                    rootLogger.debug(data.toString());
                    break;
                default:
                    break;
            }
    }

    public void log(Level level, String message, Map<String, Object> data, Throwable throwable) {
        if (MODE_FLUENTD.equals(mode)) {
            Map<String, Object> dat = new ImmutableMap.Builder<String, Object>()
                    .put("message", message)
                    .putAll(data)
                    .put("@timestamp", sdf.get().format(new Date()))
                    .put("timestamp",  Instant.now().toString())
                    .put("ex_message", throwable != null ? throwable.getMessage() : "")
                    .put("level", level.toString())
                    .put("ip", localHost)
                    .put("IP", localHost)
                    .build();
            LOG.log(level.toString(), dat);
        }

        if (throwable != null)
            switch (level) {
                case INFO:
                    rootLogger.info(data.toString(), throwable);
                    break;
                case WARN:
                    rootLogger.warn(data.toString(), throwable);
                    break;
                case TRACE:
                    rootLogger.trace(data.toString(), throwable);
                    break;
                case ERROR:
                    rootLogger.error(data.toString(), throwable);
                    break;
                case DEBUG:
                    rootLogger.debug(data.toString(), throwable);
                    break;
                default:
                    break;
            }
        else
            switch (level) {
                case INFO:
                    rootLogger.info(data.toString());
                    break;
                case WARN:
                    rootLogger.warn(data.toString());
                    break;
                case TRACE:
                    rootLogger.trace(data.toString());
                    break;
                case ERROR:
                    rootLogger.error(data.toString());
                    break;
                case DEBUG:
                    rootLogger.debug(data.toString());
                    break;
                default:
                    break;
            }
    }
}
