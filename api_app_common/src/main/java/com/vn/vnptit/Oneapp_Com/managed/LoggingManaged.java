package com.vn.vnptit.Oneapp_Com.managed;

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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.vn.vnptit.Oneapp_Com.common.ConstantString;


import org.fluentd.logger.FluentLogger;
import org.fluentd.logger.sender.Reconnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.collect.ImmutableMap;

public class LoggingManaged {
	final static String						MODE_FLUENTD	= "fluentd";
	@Value("${logging.mode}")
	String									mode;

	private String							localHost		= "unknown";
	@Value("${logging.fluentd.host}")
	String									host;
	@Value("${logging.fluentd.port}")
	int										port;
	@Value("${logging.fluentd.tag}")
	String									appname;
	@Value("${logging.fluentd.timeout:-1}")
    int timeout;
    @Value("${logging.fluentd.capacity:-1}")
    int capacity;		

	private FluentLogger				log				= null;			// FluentLogger.getLogger("app");
	private static Logger					rootLogger		= LoggerFactory
			.getLogger(Logger.ROOT_LOGGER_NAME);
	static ThreadLocal<SimpleDateFormat>	sdf				= ThreadLocal
			.withInitial(() -> new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
	    ScheduledExecutorService servicePushLog = null;
	    Queue<QueueItem> queueLogs = new ArrayBlockingQueue<>(512);
	    static class QueueItem {
	        String logLevel;
	        Map<String, Object> logData;
	    }
	    
    @PostConstruct
    void postConstruct() {
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        	log.log("UnknownHostException", (Map<String, Object>) e);
        }
        if (MODE_FLUENTD.equals(mode)) {
            if (timeout > 0 && capacity > 0)
                log = FluentLogger.getLogger(appname, host, port, timeout, capacity, new Reconnector() {
                    @Override
                    public void clearErrorHistory() {

                    }

                    @Override
                    public void addErrorHistory(long l) {

                    }

                    @Override
                    public boolean isErrorHistoryEmpty() {
                        return false;
                    }

                    @Override
                    public boolean enableReconnection(long l) {
                        return true;
                    }
                });
            else
                log = FluentLogger.getLogger(appname, host, port);

            servicePushLog = Executors.newScheduledThreadPool(1);
            servicePushLog.scheduleWithFixedDelay(() -> {
                while (queueLogs.size() > 0) {
                    QueueItem queueItem = queueLogs.poll();
                    if (queueItem != null) {
                        log.log(queueItem.logLevel, queueItem.logData);
                    }
                }
            }, 1000, 500, TimeUnit.MILLISECONDS);
        }
    }

	@PreDestroy
	void preDestroy() {
		if (MODE_FLUENTD.equals(mode)) {
			log.close();
		}
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
		String podName = System.getenv("POD_NAME");
		if (MODE_FLUENTD.equals(mode)) {
			try {
			Map<String, Object> dat = new ImmutableMap.Builder<String, Object>()
					.put(ConstantString.LOG_MESSAGE, message)
					.put(ConstantString.LOG_TIMESTAMP, Instant.now().toString())
					.put(ConstantString.LOG_EX_MESSAGE,
							throwable != null ? throwable.getMessage() : "")
					.put(ConstantString.LOG_LEVEL, level.toString())
					.put(ConstantString.PODNAME, podName)
					.put("IP", localHost).build();
			Boolean a= log.log(level.toString(), dat);
			if(a.equals(false)) {
				
				System.out.println("Log kh�ng th�nh c�ng");
				
			}
			}catch (Exception e) {

				System.out.println(ConstantString.LOG_MANAGED);
				System.out.println(e.getMessage());
			}
			
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

	public void log(Level level, Map<String, Object> data,
			Throwable throwable) {
		if (MODE_FLUENTD.equals(mode)) {
			try {
			Map<String, Object> dat = new ImmutableMap.Builder<String, Object>()
					.putAll(data)
					.put(ConstantString.LOG_TIMESTAMP1, sdf.get().format(new Date()))
					.put(ConstantString.LOG_TIMESTAMP, Instant.now().toString())
					.put(ConstantString.LOG_EX_MESSAGE,
							throwable != null ? throwable.getMessage() : "")
					.put(ConstantString.LOG_LEVEL, level.toString()).put("ip", localHost)
					.put("IP", localHost).build();
				log.log(level.toString(), dat);
			}catch (Exception e) {

				System.out.println(ConstantString.LOG_MANAGED);
				System.out.println(e.getMessage());
			}
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

	public void log(Level level, String message, Map<String, Object> data,
			Throwable throwable) {
		if (MODE_FLUENTD.equals(mode)) {
			try {
			Map<String, Object> dat = new ImmutableMap.Builder<String, Object>()
					.put(ConstantString.LOG_MESSAGE, message).putAll(data)
					.put(ConstantString.LOG_TIMESTAMP1, sdf.get().format(new Date()))
					.put(ConstantString.LOG_TIMESTAMP, Instant.now().toString())
					.put(ConstantString.LOG_EX_MESSAGE,
							throwable != null ? throwable.getMessage() : "")
					.put(ConstantString.LOG_LEVEL, level.toString()).put("ip", localHost)
					.put("IP", localHost).build();
				log.log(level.toString(), dat);
			}catch (Exception e) {
				System.out.println(ConstantString.LOG_MANAGED);
				System.out.println(e.getMessage());
				
			}
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
