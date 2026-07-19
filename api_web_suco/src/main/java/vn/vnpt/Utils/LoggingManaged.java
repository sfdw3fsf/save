package vn.vnpt.Utils;

import com.google.common.collect.ImmutableMap;
import org.fluentd.logger.FluentLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    NativeWebRequest request;

    private static FluentLogger LOG = null; //FluentLogger.getLogger("app");
    private static Logger rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    Queue<QueueItem> queueLogs = new ArrayBlockingQueue<>(512);

    ScheduledExecutorService servicePushLog = null;

    static class QueueItem {
        String logLevel;
        Map<String, Object> logData;
    }

    @PostConstruct
    void postConstruct() {
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (MODE_FLUENTD.equals(mode)) {
            LOG = FluentLogger.getLogger(appname, host, port);

            servicePushLog = Executors.newScheduledThreadPool(1);
            servicePushLog.scheduleWithFixedDelay(() -> {
                while (queueLogs.size() > 0) {
                    QueueItem queueItem = queueLogs.poll();
                    if (queueItem != null) {
                        LOG.log(queueItem.logLevel, queueItem.logData);
                    }
                }
            }, 1000, 200, TimeUnit.MILLISECONDS);
        }
    }

    @PreDestroy
    void preDestroy() {
        if (MODE_FLUENTD.equals(mode)) {
            LOG.close();
            servicePushLog.shutdown();
        }
    }

    public void info(String message) {
        log(Level.INFO, message, null);
    }

    public void info(String message, Throwable throwable) {
        log(Level.INFO, message, throwable);
    }

    @Async
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
        String ex_message = throwable != null ? (throwable.getMessage() == null ? throwable.getCause().getMessage() : throwable.getMessage()) : "";
        if (MODE_FLUENTD.equals(mode)) {
            Map<String, Object> dat = new ImmutableMap.Builder<String, Object>()
                    .put("message", message)
                    //.put("@timestamp", sdf.get().format(new Date()))
                    .put("timestamp", Instant.now().toString())
                    .put("ex_message", ex_message != null ? ex_message : "")
                    .put("level", level.toString())
                    //.put("ip", localHost)
                    .put("IP", localHost)
                    .put("realIp", getIp())
                    .build();
            //LOG.log(level.toString(), dat);

            QueueItem qi = new QueueItem();
            qi.logLevel = level.toString();
            qi.logData = dat;

            queueLogs.offer(qi);
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
        try {
            if (MODE_FLUENTD.equals(mode)) {
                Map<String, Object> dat = new ImmutableMap.Builder<String, Object>()
                        .putAll(data)
                        .put("@timestamp", sdf.format(new Date()))
                        .put("timestamp", Instant.now().toString())
                        .put("ex_message", throwable != null ? throwable.getMessage() : "")
                        .put("level", level.toString())
                        .put("ip", localHost)
                        .put("IP", localHost)
//                        .put("realIp", getIp())
                        .build();
                //LOG.log(level.toString(), dat);

                QueueItem qi = new QueueItem();
                qi.logLevel = level.toString();
                qi.logData = dat;

                queueLogs.offer(qi);
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
        } catch (Exception e) {

        }
    }

    public void log(Level level, String message, Map<String, Object> data, Throwable throwable) {
        try {
            if (MODE_FLUENTD.equals(mode)) {
                Map<String, Object> dat = new ImmutableMap.Builder<String, Object>()
                        .put("message", message)
                        .putAll(data)
                        .put("@timestamp", sdf.format(new Date()))
                        .put("timestamp", Instant.now().toString())
                        .put("ex_message", throwable != null ? throwable.getMessage() : "")
                        .put("level", level.toString())
                        .put("ip", localHost)
                        .put("IP", localHost)
                        .put("realIp", getIp())
                        .build();
                //LOG.log(level.toString(), dat);

                QueueItem qi = new QueueItem();
                qi.logLevel = level.toString();
                qi.logData = dat;

                queueLogs.offer(qi);
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
        } catch (Exception e) {

        }
    }

    String getIp() {
        String ip = "N/A";
        try {
            ip = request.getHeader("X-IP-Source");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-CLIENT-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_FORWARDED");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_VIA");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("REMOTE_ADDR");
            }
        } catch (Exception ex) {
        }

        if (ip == null || ip.isEmpty()) {
            ip = "N/A";
        }

        return ip;
    }
}
