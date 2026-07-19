package com.vn.vnptit.Oneapp_Com.service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import com.vn.vnptit.Oneapp_Com.common.DbHelper;
import com.vn.vnptit.Oneapp_Com.common.HttpClientUtil;
import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.managed.LoggingManaged;
import com.vn.vnptit.Oneapp_Com.managed.OracleManaged;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.vn.vnptit.Oneapp_Com.model.DataSource;
import com.vn.vnptit.Oneapp_Com.model.DataSourceProperties;
import com.vn.vnptit.Oneapp_Com.model.FrmInputModel;
import com.vn.vnptit.Oneapp_Com.model.OracleParam;
import com.vn.vnptit.Oneapp_Com.model.Response;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;

@Service
public class FmsService {
	@Autowired
	DataSourceProperties dataSourceProperties;
	@Autowired
	LoggingManaged loggingManaged;
	private final JsonMapper mapper = new JsonMapper();
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final HttpClientUtil httpClientUtil = new HttpClientUtil();
	private final Logger logger = Logger.getLogger(getClass().getName());

	public ResponseEntity<Object> post_net_dhsx_to_tts(String uuid, FrmInputModel body, String token) {
		try {
			logger.info("[post_net_dhsx_to_tts] START - Body received: " + mapper.writeValueAsString(body));
		} catch (Exception e) {
			logger.info("[post_net_dhsx_to_tts] START - Body received (raw): " + body.getJsonData());
		}

		ResponseObject modelResponse = new ResponseObject();
		modelResponse.setRequest_id(uuid);

		try {
			// 1. Parse JSON body
			String jsonDataStr = body.getJsonData();
			if (jsonDataStr != null && !jsonDataStr.trim().isEmpty()) {
				Map<String, Object> parsedData = objectMapper.readValue(jsonDataStr,
						new TypeReference<Map<String, Object>>() {
						});

				// Retrieve case-insensitive values for rules
				String maQuyTrinh = String
						.valueOf(parsedData.getOrDefault("MA_QUYTRINH", parsedData.get("ma_quytrinh")));

				// 2. Check for "cellclk" workflow
				if (maQuyTrinh != null && maQuyTrinh.toUpperCase().contains("C3_CLM_03")) {

					String maTinh = String.valueOf(parsedData.getOrDefault("MA_TINH", parsedData.get("ma_tinh")))
							.trim();
					Object cssIdObj = parsedData.getOrDefault("CSS_ID", parsedData.get("css_id"));
					Long cssId = null;
					if (cssIdObj != null && !cssIdObj.toString().equals("null") && !cssIdObj.toString().isEmpty()) {
						cssId = Long.parseLong(cssIdObj.toString());
					}

					if (!maTinh.equals("null") && !maTinh.isEmpty() && cssId != null) {
						Map<String, Object> dbData = new HashMap<>();
						// Get connection from css db via DbHelper
						try {
							DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
							String commandText = "qlsc.PKG_SAVECSSAPI.GET_DATA_FOR_SAVECSSAPI";

							ArrayList<OracleParam> parameters = new ArrayList<>();
							parameters.add(new OracleParam("p_id_css", ConstantString.NUMBER, String.valueOf(cssId),
									OracleParam.OracleDirect.INPUT));
							parameters.add(new OracleParam("p_ma_tinh", ConstantString.VARCHAR2, maTinh,
									OracleParam.OracleDirect.INPUT));
							parameters.add(new OracleParam("p_result", ConstantString.OUTPUT, null,
									OracleParam.OracleDirect.OUPUT));

							DbHelper dbHelper = new DbHelper();
							Response result = dbHelper.openRS(commandText, parameters, oneApp, false);

							if (result.getError() == null && result.getData() != null && !result.getData().isEmpty()) {
								Object firstRowObj = result.getData().get(0);
								if (firstRowObj instanceof Map) {
									Map<String, Object> firstRow = (Map<String, Object>) firstRowObj;
									for (Map.Entry<String, Object> entry : firstRow.entrySet()) {
										dbData.put(entry.getKey().toUpperCase(),
												entry.getValue() != null ? String.valueOf(entry.getValue()) : "");
									}
								}
							}
						} catch (Exception ex) {
							Map<String, String> logErr = new HashMap<>();
							logErr.put("request_id", uuid);
							logErr.put("action", "[FmsService] - [post_net_dhsx_to_tts] - DB Failed");
							logErr.put("message", ex.getMessage());
							loggingManaged.info(logErr.toString());
							logger.info("DB Error in post_net_dhsx_to_tts: " + ex.getMessage());
						}

						// 3. Merge DB data over parsedData (parsedData takes priority if not empty)
						if (!dbData.isEmpty()) {
							Map<String, Object> finalData = new HashMap<>();
							for (Map.Entry<String, Object> entry : parsedData.entrySet()) {
								finalData.put(entry.getKey().toUpperCase(), entry.getValue());
							}
							for (Map.Entry<String, Object> entry : dbData.entrySet()) {
								String upperKey = entry.getKey().toUpperCase();
								Object dbVal = entry.getValue();

								Object existingVal = finalData.get(upperKey);
								if (existingVal == null || existingVal.toString().trim().isEmpty()
										|| existingVal.toString().equals("null")
										|| existingVal.toString().equals("0")) {
									finalData.put(upperKey, dbVal);
								}
							}

							// Rewrite JSON to model
							body.setJsonData(objectMapper.writeValueAsString(finalData));
							try {
								logger.info("[post_net_dhsx_to_tts] C3_CLM_03 - Body after mapping: "
										+ mapper.writeValueAsString(body));
							} catch (Exception e) {
								logger.info("[post_net_dhsx_to_tts] C3_CLM_03 - Body after mapping (raw data): "
										+ body.getJsonData());
							}
						}
					}
				} else if (maQuyTrinh != null && Stream.of("QT1.4","QT1.6.1","QTXLSC_ACCESS","QTXLSC_MANE").anyMatch(maQuyTrinh.toUpperCase()::contains)) {
					String maTinh = String.valueOf(parsedData.getOrDefault("MA_TINH", parsedData.get("ma_tinh")))
							.trim();
					Object cssIdObj = parsedData.getOrDefault("CSS_ID", parsedData.get("css_id"));
					Long cssId = null;
					if (cssIdObj != null && !cssIdObj.toString().equals("null") && !cssIdObj.toString().isEmpty()) {
						cssId = Long.parseLong(cssIdObj.toString());
					}

					if (!maTinh.equals("null") && !maTinh.isEmpty() && cssId != null) {
						try {
							DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
							String commandText = "qlsc.PKG_SAVECSSAPI.GET_DATA_FOR_SAVECSSAPI";

							ArrayList<OracleParam> parameters = new ArrayList<>();
							parameters.add(new OracleParam("p_id_css", ConstantString.NUMBER, String.valueOf(cssId),
									OracleParam.OracleDirect.INPUT));
							parameters.add(new OracleParam("p_ma_tinh", ConstantString.VARCHAR2, maTinh,
									OracleParam.OracleDirect.INPUT));
							parameters.add(new OracleParam("p_result", ConstantString.OUTPUT, null,
									OracleParam.OracleDirect.OUPUT));

							DbHelper dbHelper = new DbHelper();
							Response result = dbHelper.openRS(commandText, parameters, oneApp, false);

							if (result.getError() == null && result.getData() != null && !result.getData().isEmpty()) {
								Object firstRowObj = result.getData().get(0);
								if (firstRowObj instanceof Map) {
									Map<String, Object> firstRow = (Map<String, Object>) firstRowObj;
									Object maDonViXuLyHrm = firstRow.get("ma_donvi_xuly_hrm");
									Object sla_date = firstRow.get("sla_date");
									Object sla_process_time = firstRow.get("sla_process_time");
									Object quahan_taibuoc = firstRow.get("quahan_taibuoc");
									Object sla = firstRow.get("sla");
									Object quahan_toantrinh = firstRow.get("quahan_toantrinh");

									parsedData.put("MA_DONVI_XULY_HRM", String.valueOf(maDonViXuLyHrm));
									parsedData.put("sla_date", String.valueOf(sla_date));
									parsedData.put("sla_process_time", String.valueOf(sla_process_time));
									parsedData.put("quahan_taibuoc", String.valueOf(quahan_taibuoc));
									parsedData.put("sla", String.valueOf(sla));
									parsedData.put("quahan_toantrinh", String.valueOf(quahan_toantrinh));


									body.setJsonData(objectMapper.writeValueAsString(parsedData));
									try {
										logger.info("[post_net_dhsx_to_tts] maQuyTrinh - Body after mapping: "
												+ mapper.writeValueAsString(body));
									} catch (Exception e) {
										logger.info(
												"[post_net_dhsx_to_tts] maQuyTrinh - Body after mapping (raw data): "
														+ body.getJsonData());
									}
								}
							}
						} catch (Exception ex) {
							Map<String, String> logErr = new HashMap<>();
							logErr.put("request_id", uuid);
							logErr.put("action", "[FmsService] - [post_net_dhsx_to_tts] - DB Failed");
							logErr.put("message", ex.getMessage());
							loggingManaged.info(logErr.toString());
							logger.info("DB Error in post_net_dhsx_to_tts: " + ex.getMessage());
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.info("Error parsing or querying for MA_QUYTRINH: " + ex.getMessage());
		}

		String endpoint = "http://tichhop/fms_alarms/post_net_dhsx_to_tts";
		Map<String, String> header = new HashMap<>();

		header.put("Authorization", token);

		Object data = httpClientUtil.doPost(endpoint, header, body, Object.class);
		String idenficode = JwtService.getIdentifyFromToken(token.split(" ")[1]);
		String username = JwtService.getUsernameFromToken(token.split(" ")[1]);
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, endpoint);
		log.put(ConstantString.IDENTIFYCODE, idenficode);
		log.put(ConstantString.USERNAME, username);
		try {
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
			log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
		} catch (JsonProcessingException e) {
			logger.info(e.getMessage());
		}
		loggingManaged.info(log.toString());

		return ResponseEntity.status(Integer.parseInt("200")).body(data);
	}
}