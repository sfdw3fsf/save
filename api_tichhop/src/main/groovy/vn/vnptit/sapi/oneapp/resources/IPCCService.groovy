package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.ipcc.ChangeAgentStateReq
import vn.vnptit.sapi.oneapp.models.ipcc.CallObReq

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import java.nio.charset.StandardCharsets

@RestController
@RequestMapping("ipcc")
class IPCCService {

	@Autowired
	LoggingManaged loggingManaged

	@Autowired
	HttpManaged httpManaged

	@Value("\${spring.httpservices.ipcc.url}")
	String urlIPCC

	@Value("\${spring.httpservices.ipcc.key}")
	String key

	@Value("\${spring.httpservices.ipcc.header}")
	String header64

	@Value("\${spring.httpservices.ipcc.iss}")
	String iss

	String getBearerToken(Object input) {
		List<String> segments = new ArrayList<>()

		// Part 1
		segments.add(header64)

		// Part 2
		Map<String, Object> ipccPayload = [:]
		ipccPayload["parameters"] = input
		ipccPayload["iss"] = iss
		byte[] payloadBytes = new Gson().toJson(ipccPayload).getBytes(StandardCharsets.UTF_8)
		segments.add(Base64.encodeBase64URLSafeString(payloadBytes))
		String stringToSign = String.join(".", segments)
		byte[] bytesToSign = stringToSign.getBytes(StandardCharsets.UTF_8)

		// Part 3
		byte[] signature
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8)
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256")
		sha256_HMAC.init(new SecretKeySpec(keyBytes, "HmacSHA256"))
		signature = sha256_HMAC.doFinal(bytesToSign)
		segments.add(Base64.encodeBase64URLSafeString(signature))
		String bearer = String.join(".", segments)

		return bearer
	}

	@PostMapping("changeAgentState")
	CommonResponse changeAgentState(@Valid @RequestBody ChangeAgentStateReq input) {
		try {
			Map<String, String> headers = [:]
			headers.put("Authorization", "Bearer " + getBearerToken(input))
			String resp = httpManaged.post(["url": urlIPCC + "extension/change-agent-state"], headers, "")
			def ret
			try {
				ret = new JsonSlurper().parseText(resp)
			} catch (Exception e) {
				ret = new JsonSlurper().parseText(resp.substring(1))
			}
			if (ret["success"].toString() == "true") {
				return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret["data"])
			} else {
				return new CommonResponse(errorCode: 1, faultCode: ret["error_code"], faultString: ret["message"], data: ret["data"])
			}
		} catch (Exception ex) {
			return CommonResponse.resolveException(ex, loggingManaged)
		}
	}
	
	@PostMapping("callOb")
	CommonResponse callOb(@Valid @RequestBody CallObReq input) {
		try {
			Map<String, String> headers = [:]
			headers.put("Authorization", "Bearer " + getBearerToken(input))
			String resp = httpManaged.post(["url": urlIPCC + "call/call-ob"], headers, "")
			def ret
			try {
				ret = new JsonSlurper().parseText(resp)
			} catch (Exception e) {
				ret = new JsonSlurper().parseText(resp.substring(1))
			}
			if (ret["success"].toString() == "true") {
				return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret["data"])
			} else {
				return new CommonResponse(errorCode: 1, faultCode: ret["error_code"], faultString: ret["message"], data: ret["data"])
			}
		} catch (Exception ex) {
			return CommonResponse.resolveException(ex, loggingManaged)
		}
	}

	@PostMapping("getRecordingFile")
	void getRecordingFile(HttpServletResponse response, @Valid @RequestBody Map input) {
		byte[] fileData = null
		try {
			String urlStr = "http://10.144.83.125:82/file/get-recording-file"
			fileData = downloadFileAsBytes(urlStr, input)
			response.setContentType(input.contentType)
		} catch (Exception ex) {
			fileData = ex.getMessage().getBytes(StandardCharsets.UTF_8)
			response.setContentType("text/plain")
		}

		response.setContentLength(fileData.length)
		OutputStream outputStream = null
		try {
			outputStream = response.getOutputStream()
			outputStream.write(fileData)
			outputStream.flush()
		} finally {
			if (outputStream != null) outputStream.close()
		}
	}

	byte[] downloadFileAsBytes(String urlStr, Map input) throws IOException {
		HttpURLConnection httpConn = null
		BufferedInputStream inStream = null
		ByteArrayOutputStream byteStream = null

		try {
			URL url = new URL(urlStr)
			httpConn = (HttpURLConnection) url.openConnection()
			httpConn.setRequestMethod("GET")

			httpConn.setRequestProperty("Authorization", "Bearer " + getBearerToken(input))
			httpConn.setRequestProperty("Accept", "application/octet-stream")

			int responseCode = httpConn.getResponseCode()
			if (responseCode == HttpURLConnection.HTTP_OK) {
				inStream = new BufferedInputStream(httpConn.getInputStream())
				byteStream = new ByteArrayOutputStream()

				byte[] buffer = new byte[1024]
				int bytesRead
				while ((bytesRead = inStream.read(buffer)) != -1) {
					byteStream.write(buffer, 0, bytesRead)
				}

				return byteStream.toByteArray()
			} else {
				return null
			}
		} finally {
			if (inStream != null) inStream.close()
			if (byteStream != null) byteStream.close()
			if (httpConn != null) httpConn.disconnect()
		}
	}
	
}
