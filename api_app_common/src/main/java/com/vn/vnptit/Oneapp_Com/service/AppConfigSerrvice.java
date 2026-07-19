package com.vn.vnptit.Oneapp_Com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vn.vnptit.Oneapp_Com.common.CallHttp;
import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import com.vn.vnptit.Oneapp_Com.common.DbHelper;
import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.common.Utils;
import com.vn.vnptit.Oneapp_Com.managed.LoggingManaged;
import com.vn.vnptit.Oneapp_Com.managed.MahoaMd5;
import com.vn.vnptit.Oneapp_Com.model.AccountParam;
import com.vn.vnptit.Oneapp_Com.model.AppConfig;
import com.vn.vnptit.Oneapp_Com.model.AppConfigTranfer;
import com.vn.vnptit.Oneapp_Com.model.DataSource;
import com.vn.vnptit.Oneapp_Com.model.DataSourceProperties;
import com.vn.vnptit.Oneapp_Com.model.OracleParam;
import com.vn.vnptit.Oneapp_Com.model.Response;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;

/**
 * @author DanhNQ
 *
 */
@Service
public class AppConfigSerrvice {
	@Autowired
	DataSourceProperties dataSourceProperties;
	@Autowired
	LoggingManaged loggingManaged;
	private JsonMapper mapper = new JsonMapper();

	private Logger logger = Logger.getLogger(getClass().getName());

	@Value("${ekyc.username:luattd@vnpt.vn}")
	private String ekycUsername;

	@Value("${ekyc.password:Luattd1@vnpt.vn}")
	private String ekycPassword;

	@Value("${ekyc.client-id:5_hour}")
	private String ekycClientId;

	@Value("${ekyc.grant-type:password}")
	private String ekycGrantType;

	@Value("${ekyc.client-secret:password}")
	private String ekycClientSecret;

	/**
	 * Find danh muc by id and nguondulieu
	 *
	 * @return list danh muc
	 */
	public ResponseEntity<Object> appConfig(String token, String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.pkg_app_config.sp_app_config";

		String idenficode = JwtService.getIdentifyFromToken(token.split(" ")[1]);
		String username = JwtService.getUsernameFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null, OracleParam.OracleDirect.OUPUT));
		parameters.add(new OracleParam("p_identify_code", ConstantString.VARCHAR2, idenficode,
				OracleParam.OracleDirect.INPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp, true);
		Map<String, String> log = new HashMap<>();
		log.put(ConstantString.REQUEST_ID, uuid.toString());
		log.put(ConstantString.ACTION, "appConfig");
		log.put(ConstantString.IDENTIFYCODE, idenficode);
		log.put(ConstantString.USERNAME, username);
		log.put(ConstantString.LOG_RESPONE, result.getMessage());
		try {
			log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(token));
			log.put(ConstantString.OUTPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
		} catch (JsonProcessingException e1) {

			logger.info(e1.getMessage());
		}
		loggingManaged.info(log.toString());
		modelResponse.setData(result.getData());

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				Object dataUser = result.getData().get(0);
				ObjectMapper oMapper = new ObjectMapper();
				AppConfig khoitao = oMapper.convertValue(dataUser, AppConfig.class);

				AppConfigTranfer khoitao_tam = new AppConfigTranfer(khoitao);

				modelResponse.setData(khoitao_tam);
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok(modelResponse);
			}
		}
		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> kiemtraUserPass(String uuid, AccountParam pr) {
		MahoaMd5 utils = new MahoaMd5();
		String key = dataSourceProperties.getKeymahoa();
		String newpass = utils.encrypt(pr.getPass(), key);
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(null);
		String commandText = "common.pkg_log_user.sp_kiemtra_user_pass";

		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters
				.add(new OracleParam("p_user", ConstantString.VARCHAR2, pr.getUser(), OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_pass", ConstantString.VARCHAR2, newpass, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null, OracleParam.OracleDirect.OUPUT));

		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp, true);

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {
				modelResponse.setData(result.getData().get(0));
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok(modelResponse);
			}
		}

		return ResponseEntity.status(Integer.parseInt(modelResponse.getError())).body(modelResponse);
	}

	public ResponseEntity<Object> token_ekyc(String uuid) {

		String accessToken = null;

		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json");
		String url = "https://api.idg.vnpt.vn/auth/oauth/token";
		String urlCheckRegister = "https://api.idg.vnpt.vn/bill-service/project-service-plan/sub/check-register";

		Map<String, String> dataRaw = new HashMap<>();
		dataRaw.put(ConstantString.USERNAME_STRING, ekycUsername);
		dataRaw.put(ConstantString.PASS_STRING, ekycPassword);
		dataRaw.put("client_id", ekycClientId);
		dataRaw.put("grant_type", ekycGrantType);
		dataRaw.put("client_secret", ekycClientSecret);

		try {
			ResponseObject modelResponse = CallHttp.httpPostBody(header, dataRaw, url);
			@SuppressWarnings("unchecked")
			Map<String, Object> idgRes = (Map<String, Object>) modelResponse.getData();
			accessToken = (String) idgRes.get("access_token");
		} catch (Exception e) {
			logger.info("Loi lay token tu IDG: " + e.getMessage());
		}

		if (accessToken == null) {
			ResponseObject errResponse = Utils.badRequest(uuid);
			errResponse.setMessage("Lỗi lấy token");
			return ResponseEntity.status(Integer.parseInt(errResponse.getError())).body(errResponse);
		}

		header.put("Authorization", "Bearer " + accessToken);
		Map<String, String> dataRaw2 = new HashMap<>();
		dataRaw2.put("channelCode", "eKYC");

		try {
			// Second request to check register
			CallHttp.httpPostBody(header, dataRaw2, urlCheckRegister);
		} catch (Exception e) {
			logger.info("Loi check register eKYC: " + e.getMessage());
			ResponseObject errResponse = Utils.badRequest(uuid);
			errResponse.setMessage("Lỗi lấy token");
			return ResponseEntity.status(Integer.parseInt(errResponse.getError())).body(errResponse);
		}

		ResponseObject successResponse = new ResponseObject();
		successResponse.setError("200");
		successResponse.setRequest_id(uuid);
		successResponse.setData("Bearer " + accessToken);

		return ResponseEntity.ok(successResponse);
	}
}
