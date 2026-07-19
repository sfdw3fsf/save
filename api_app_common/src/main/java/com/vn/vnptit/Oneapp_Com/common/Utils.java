package com.vn.vnptit.Oneapp_Com.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.util.TextUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vn.vnptit.Oneapp_Com.model.Response;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;

public class Utils {

	public static ResponseObject accessDenied(String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		modelResponse.setError("401");
		modelResponse.setMessage("User chưa đăng nhập");
		modelResponse.setError_code(ErrorCode.BSS_00004001);
		modelResponse.setRequest_id(uuid);
		return modelResponse; 
	}

	public static ResponseObject notFound(String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		modelResponse.setError("404");
		modelResponse.setMessage("Không có thông tin");
		modelResponse.setError_code(ErrorCode.BSS_00004000);
		modelResponse.setRequest_id(uuid);
		return modelResponse;
	}

	public static ResponseObject badRequest(String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		modelResponse.setError("400");
		modelResponse.setMessage("Lỗi");
		modelResponse.setError_code(ErrorCode.BSS_00004002);
		modelResponse.setRequest_id(uuid);
		return modelResponse;
	}

	public static ResponseObject critcalReponse(String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		modelResponse.setError("500");
		modelResponse.setMessage("Lỗi");
		modelResponse.setError_code(ErrorCode.BSS_00000500);
		modelResponse.setRequest_id(uuid);
		return modelResponse;
	}

	public static ResponseObject badRequestAppSecret(String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		modelResponse.setError("400");
		modelResponse.setMessage("Request không hợp lệ (App-secret)");
		modelResponse.setError_code(ErrorCode.BSS_00004002);
		modelResponse.setRequest_id(uuid);
		return modelResponse;
	}

	public static String clobToString(Clob data) {
		if (data != null) {
			StringBuilder sb = new StringBuilder();
			try {
				Reader reader = data.getCharacterStream();
				BufferedReader br = new BufferedReader(reader);

				String line;
				while (null != (line = br.readLine())) {
					sb.append(line);
				}
				br.close();
			} catch (SQLException e) {
				return e.getMessage();
			} catch (IOException e) {
				return e.getMessage();
			}
			return sb.toString();

		} else {
			return null;
		}
	}

	public static int[] addX(int n, int[] arr, int x) {
		int i;

		// create a new array of size n+1
		int[] newarr = new int[n + 1];

		// insert the elements from
		// the old array into the new array
		// insert all elements till n
		// then insert x at n+1
		for (i = 0; i < n; i++)
			newarr[i] = arr[i];

		newarr[n] = x;

		return newarr;
	}

	public ResponseObject classifyError(Response result, String uuid) {
		ResponseObject modelResponse;
		if (result.getError() != null) {
			if (result.getError().equals("2")) {
				modelResponse = Utils.badRequest(uuid);
			} else {
				modelResponse = Utils.critcalReponse(uuid);
			}
			modelResponse.setMessage(result.getMessage());
		} else {
			modelResponse = new ResponseObject();
		}
		return modelResponse;
	}

	public static String convertSpecialCharacter(String xmlData) {
		String rv = xmlData;
		rv = rv.replace("&", "&amp;");
		rv = rv.replace("<", "&lt;");
		rv = rv.replace(">", "&gt;");
		rv = rv.replace("[", "");
		rv = rv.replace("]", "");
		rv = rv.replace("!", "");
		return rv;
	}

	public static List<Object> convertHashmaptoList(HashMap<String, Object> map) {

		Map<String, Object> a = new HashMap<>();

		List<Entry> entryList = new ArrayList<>(map.entrySet());

		for (Entry s : entryList) {
			a.put(s.getKey().toString(), s.getValue());
		}
		ArrayList<Object> returnList = new ArrayList<>();
		returnList.add(a);

		return returnList;

	}

	public static String stringToBot(String token, String uuid, String pathApi, String pathDB, Object dataParam,
			String status, String message) {
		String stringMessage;
		if (StringUtils.isEmpty(status) || (!StringUtils.isEmpty(status) && status.equals("BSS-00000000"))) {
			stringMessage = " 👍 👍 👍 " + "\n";
		} else {
			stringMessage = " 👎 👎 👎 " + "\n";
		}

		String user = "";
		String idenfycode = "";
		if (!StringUtils.isEmpty(token)) {
			user = JwtService.getUsernameFromToken(token.split(" ")[1]);
			idenfycode = JwtService.getIdentifyFromToken(token.split(" ")[1]);
		}

		stringMessage = stringMessage + "Path: " + pathApi + "\n";
		stringMessage = stringMessage + "User: " + user + "\n";
		stringMessage = stringMessage + "PathDB: " + pathDB + "\n";
		stringMessage = stringMessage + "IdentifyCode: " + idenfycode + "\n";
		stringMessage = stringMessage + "RequestId: " + uuid + "\n";
		stringMessage = stringMessage + "Status: " + status + "\n";
		stringMessage = stringMessage + "Message: " + message + "\n";

		JsonMapper mapper = new JsonMapper();
		try {
			stringMessage = stringMessage + "Input: "
					+ (mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataParam)).replaceAll("[{|}]", "");
		} catch (JsonProcessingException e) {

		}

		return stringMessage;
	}

	public static String replaceForXlm(String input) {

		if (input == null) {
			return null;
		}
		input = input.replace("'", "&apos;");
		input = input.replace("&", "&amp;");
		input = input.replace(">", "&gt;");
		input = input.replace("<", "&lt;");
		return input;

	}

	public static boolean checkData(String data) {
		return !TextUtils.isEmpty(data) && !TextUtils.isEmpty(data.trim());
	}

}
