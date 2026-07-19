package vn.vnptit.sapi.oneapp.ep

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.vng.zalo.sdk.EndPoint
import com.vng.zalo.sdk.oa.ZaloOaClient
import org.springframework.stereotype.Component

/**
 * @author VietNH
 *
 */
@Component
class ZaloClient {

	public String checkExistsZalo(String phone, String oaKey) {
		String messageResp
		try {
			if (phone.startsWith("0")) {
				phone = "84" + phone.substring(1)
			} else if (phone.length() <= 10) {
				phone = "84" + phone
			}
			ZaloOaClient client = new ZaloOaClient()
			Map<String, Object> params = new HashMap<>()
			params.put("access_token", oaKey)
			JsonObject data = new JsonObject()
			data.addProperty("user_id", phone)
			params.put("data", data.toString())

			JsonObject response = client.excuteRequest(EndPoint.OA_GET_USER_PROFILE, "GET", params, null)
			messageResp = response.get("error")
		}
		catch (Exception exception) {
			messageResp = exception.getMessage()
		}
		return messageResp
	}

	public String checkFollowOA(String phone, String oaKey) {
		String messageResp
		try {
			if (phone.startsWith("0")) {
				phone = "84" + phone.substring(1)
			} else if (phone.length() <= 10) {
				phone = "84" + phone
			}
			ZaloOaClient client = new ZaloOaClient()
			Map<String, Object> params = new HashMap<>()
			params.put("access_token", oaKey)
			JsonObject data = new JsonObject()
			data.addProperty("user_id", phone)
			params.put("data", data.toString())
			JsonObject response = client.excuteRequest(EndPoint.OA_GET_USER_PROFILE, "GET", params, null)
			messageResp = response.get("error")
		}
		catch (Exception exception) {
			messageResp = exception.getMessage()
		}
		return messageResp
	}

	public Long getIDZalo(String phone, String oaKey) {
		Long id
		try {
			if (phone.startsWith("0")) {
				phone = "84" + phone.substring(1)
			} else if (phone.length() <= 10) {
				phone = "84" + phone
			}
			ZaloOaClient client = new ZaloOaClient()
			Map<String, Object> params = new HashMap<>()
			params.put("access_token", oaKey)
			JsonObject data = new JsonObject()
			data.addProperty("user_id", phone)
			params.put("data", data.toString())
			JsonObject response = client.excuteRequest(EndPoint.OA_GET_USER_PROFILE, "GET", params, null)
			String messageResp = response.get("error")
			if (messageResp == '0') {
				JsonElement dataResp = response.get("data")
				id = dataResp.getAsJsonObject().get("user_id").getAsLong()
			}
		}
		catch (Exception exception) {
			exception.printStackTrace()
		}
		return id
	}

	public String sendMessage(String phone, String message, String oaKey) {
		String messageResp
		try {
			if (phone.startsWith("0")) {
				phone = "84" + phone.substring(1)
			} else if (phone.length() <= 10) {
				phone = "84" + phone
			}
			Long receiverId = 0L
			ZaloOaClient client = new ZaloOaClient()
			Map<String, Object> params = new HashMap<>()
			params.put("access_token", oaKey)
			JsonObject data = new JsonObject()
			data.addProperty("user_id", phone)
			params.put("data", data.toString())
			JsonObject response = client.excuteRequest(EndPoint.OA_GET_USER_PROFILE, "GET", params, null)
			String messageRespId = response.get("error")
			if (messageRespId == '0') {
				JsonElement dataResp = response.get("data")
				receiverId = dataResp.getAsJsonObject().get("user_id").getAsLong()
			}

			Map<String, Object> paramSendMessages = new HashMap<>()
			paramSendMessages.put("access_token", oaKey)

			JsonObject id = new JsonObject()
			id.addProperty("user_id", receiverId)
			JsonObject text = new JsonObject();
			text.addProperty("text", message)
			JsonObject body = new JsonObject()
			body.add("recipient", id)
			body.add("message", text)
			response = client.excuteRequest(EndPoint.OA_SEND_MESSAGE, "POST", paramSendMessages, body)
			messageResp = response.get("error")
		}
		catch (Exception exception) {
			messageResp = exception.getMessage()
		}
		return messageResp
	}
}
