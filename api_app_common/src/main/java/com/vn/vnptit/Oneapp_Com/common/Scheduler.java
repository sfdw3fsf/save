package com.vn.vnptit.Oneapp_Com.common;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vn.vnptit.Oneapp_Com.model.KeyEKYCModel;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;
import com.vn.vnptit.Oneapp_Com.service.SchedulerService;


/**
 * @author Danhnq
 *
 */
@Component
public class Scheduler {
	@Autowired
	SchedulerService schedulerService;

	// second, minute, hour, day of month, month, day(s) of week
	@Scheduled(cron = "0 0 1 * * MON")
//	 @Scheduled(cron = "1/15 * * * * *")
	public void cronJobGetTokenekyc() {
		String accessToken;
		String tokenKey;
		String tokenId;

		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", "application/json");
		String url = "https://api.idg.vnpt.vn/auth/oauth/token";
		String urlCheckRegister = "https://api.idg.vnpt.vn/bill-service/project-service-plan/sub/check-register";

		Map<String, String> dataRaw = new HashMap<>();
		dataRaw.put(ConstantString.USERNAME_STRING, "luattd@vnpt.vn");
		dataRaw.put(ConstantString.PASS_STRING, "Luattd1@vnpt.vn");
		dataRaw.put("client_id", "validity_2_weeks");
		dataRaw.put("grant_type", "password");
		dataRaw.put("client_secret", "password");

		ResponseObject modelResponse;
		try {
			modelResponse = CallHttp.httpPostBody(header, dataRaw, url);

			Map<String, Object> idgRes = (Map<String, Object>) modelResponse
					.getData();
			accessToken = (String) idgRes.get("access_token");
		} catch (Exception e) {
			accessToken = null;
		}

		if (accessToken != null) {
			header.put("Authorization", "Bearer " + accessToken);

			Map<String, String> dataRaw2 = new HashMap<>();
			dataRaw2.put("channelCode", "eKYC");
			try {
				modelResponse = CallHttp.httpPostBody(header, dataRaw2,
						urlCheckRegister);

				@SuppressWarnings("unused")
				Map<String, Object> idgRes2 = (Map<String, Object>) modelResponse
						.getData();

				Map<String, Object> idgRes3 = (Map<String, Object>) idgRes2
						.get("object");

				tokenKey = (String) idgRes3.get("publicKey");
				tokenId = (String) idgRes3.get("uuidProjectServicePlan");
			} catch (Exception e) {
				tokenKey = null;
				tokenId = null;
			}
			if (tokenKey!=null) {
				KeyEKYCModel ekycModel = new KeyEKYCModel(
						"Bearer " + accessToken, tokenId, tokenKey);

				schedulerService.CapnhatKeyEkyc(ekycModel);
			}
		}
	}
}
