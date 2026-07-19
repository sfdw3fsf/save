/**
 * 
 */
package vn.vnpt.vnptit.ecms.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

import vn.vnpt.Utils.ConstantString;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.log.LoggingManaged;

/**
 * 
 */
@Service
@Transactional
public class LogSerrvice {
	 
	 private final  LoggingManaged loggingManaged;
	 private JsonMapper mapper = new JsonMapper();
	 private Logger logger = Logger.getLogger(getClass().getName());
	    
	public LogSerrvice(LoggingManaged loggingManaged) {
		super();
		this.loggingManaged = loggingManaged;
	}
	 
	
	 public void log (String requestId, String path, Object input, Object output) {

		 String request = requestId != null ? requestId : UUID.randomUUID().toString();
		 Map<String, String> log = new HashMap<>();
		 
			log.put(ConstantString.REQUEST_ID,request);
			log.put(ConstantString.ACTION, path);
			log.put(ConstantString.IDENTIFYCODE, AppRequestContext.getCurrent().getIdentityCode());
			log.put(ConstantString.USERNAME, AppRequestContext.getCurrent().getUserName());

			try {
				log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(input));
				log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(output));
			} catch (JsonProcessingException e1) {
				 
				logger.info(e1.getMessage());
			}
			loggingManaged.info(log.toString());
	 }
}
