package vn.vnptit.sapi.oneapp.service.vnface

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import vn.vnptit.sapi.oneapp.converter.StreamToStringConverter
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceCheckinHistoryFilterRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceDeviceLicenseListRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceNotifyAccountRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceCreateAccountRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceAccessTokenRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceDeleteAccountListRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceGetAccountListRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceLockAccountRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceUpdateAccountRequest
import vn.vnptit.sapi.oneapp.models.vnface.VnFaceUpdateAccountListRequest

import javax.validation.ConstraintViolation
import javax.validation.Validator

@Service
class VnFaceIntegrationService {

    private static final String CHECKIN_HISTORY_PATH = "/checkin-service/external/his-checkin/list-filter"
    private static final String NOTIFY_ACCOUNT_PATH = "/checkin-service/external/notify/account"
    private static final String CREATE_ACCOUNT_PATH = "/checkin-service/external/account"
    private static final String UPDATE_ACCOUNT_PATH_V2 = "/checkin-service/external/account/update"
    private static final String ACCESS_TOKEN_PATH = "/checkin-service/external/account/access-token"
    private static final String UPDATE_ACCOUNT_PATH = "/checkin-service/external/account/list"
    private static final String DEVICE_LICENSE_LIST_PATH = "/checkin-service/external/device-license/list"
    private static final String HIS_CHECKIN_TRANS_ID_PATH = "/checkin-service/external/his-checkin/trans-id"
    private static final String ACCOUNT_LOCK_PATH = "/checkin-service/external/account/lock"
    private static final String GROUP_PATH = "/checkin-service/external/group"
    private static final String SHIFTS_PATH = "/checkin-service/external/shifts"
    private static final String HIS_CHECKIN_PATH = "/checkin-service/external/his-checkin"

    @Value("\${spring.httpservices.vnface.url}")
    String vnFaceUrl

    @Value("\${spring.httpservices.vnface.token-channel}")
    String vnFaceTokenChannel

    @Value("\${spring.httpservices.vnface.access-token}")
    String vnFaceAccessToken

    @Autowired
    HttpManaged httpManaged

    @Autowired
    ObjectMapper objectMapper

    @Autowired
    Validator validator

    Object getCheckinHistory(VnFaceCheckinHistoryFilterRequest request) {
        validateBean(request)

        String url = buildCheckinHistoryUrl(request)
        String responseBody = httpManaged.get(url, buildHeaders())
        return parseResponse(responseBody)
    }

    Object updateEmployeeList(VnFaceUpdateAccountListRequest request) {
        validateBean(request)
        String url = normalizeBaseUrl(vnFaceUrl) + UPDATE_ACCOUNT_PATH
        String requestBody = objectMapper.writeValueAsString(request)
        String responseBody = httpManaged.put([url: url], buildHeaders(), requestBody)
        return parseResponse(responseBody)
    }

    Object createEmployeeList(VnFaceUpdateAccountListRequest request) {
        validateBean(request)
        String url = normalizeBaseUrl(vnFaceUrl) + UPDATE_ACCOUNT_PATH
        String requestBody = objectMapper.writeValueAsString(request)
        String responseBody = httpManaged.post([url: url], buildHeaders(), requestBody)
        return parseResponse(responseBody)
    }

    Object createEmployee(VnFaceCreateAccountRequest request) {
        String url = normalizeBaseUrl(vnFaceUrl) + CREATE_ACCOUNT_PATH
        Map<String, Object> formData = buildCreateAccountFormData(request)
        String responseBody = httpManaged.uploadMultipartFile(url, buildMultipartHeaders(), "imageFile", request.imageFile, formData)
        return parseResponse(responseBody)
    }

    Object updateEmployee(VnFaceUpdateAccountRequest request) {
        String url = normalizeBaseUrl(vnFaceUrl) + UPDATE_ACCOUNT_PATH_V2
        Map<String, Object> formData = buildUpdateAccountFormData(request)
        String responseBody = httpManaged.uploadMultipartFile(url, buildMultipartHeaders(), "imageFile", request.imageFile, formData)
        return parseResponse(responseBody)
    }

    Object notifyAccount(VnFaceNotifyAccountRequest request) {
        validateBean(request)
        String url = normalizeBaseUrl(vnFaceUrl) + NOTIFY_ACCOUNT_PATH
        String requestBody = objectMapper.writeValueAsString(request)
        String responseBody = httpManaged.post([url: url], buildHeaders(), requestBody)
        return parseResponse(responseBody)
    }

    Object getAccountAccessToken(VnFaceAccessTokenRequest request) {
        validateBean(request)
        String url = normalizeBaseUrl(vnFaceUrl) + ACCESS_TOKEN_PATH
        String requestBody = objectMapper.writeValueAsString(request)
        String responseBody = httpManaged.post([url: url], buildHeaders(), requestBody)
        return parseResponse(responseBody)
    }

    Object deleteEmployeeList(VnFaceDeleteAccountListRequest request) {
        String url = normalizeBaseUrl(vnFaceUrl) + UPDATE_ACCOUNT_PATH
        String requestBody = objectMapper.writeValueAsString(request)
        // HttpURLConnection khong co helper delete(body) san, nen dung postOrPut voi method DELETE.
        String responseBody = httpManaged.postOrPut([url: url], buildHeaders(), new StreamToStringConverter(), requestBody, "DELETE")
        return parseResponse(responseBody)
    }

    Object getEmployeeList(VnFaceGetAccountListRequest request) {
        String url = buildAccountListUrl(request)
        String responseBody = httpManaged.get(url, buildHeaders())
        return parseResponse(responseBody)
    }

    Object getDeviceLicenseList(VnFaceDeviceLicenseListRequest request) {
        validateBean(request)
        String url = normalizeBaseUrl(vnFaceUrl) + DEVICE_LICENSE_LIST_PATH
        String requestBody = objectMapper.writeValueAsString(request)
        String responseBody = httpManaged.post([url: url], buildHeaders(), requestBody)
        return parseResponse(responseBody)
    }

    Object getCheckinHistoryByTransId(String transId) {
        assertHasText(transId, "transId khong duoc de trong")
        String url = normalizeBaseUrl(vnFaceUrl) + HIS_CHECKIN_TRANS_ID_PATH + "/" + toPathSegment(transId)
        String responseBody = httpManaged.get(url, buildHeaders())
        return parseResponse(responseBody)
    }

    Object lockAccount(VnFaceLockAccountRequest request) {
        validateBean(request)
        String url = normalizeBaseUrl(vnFaceUrl) + ACCOUNT_LOCK_PATH
        String requestBody = objectMapper.writeValueAsString(request)
        String responseBody = httpManaged.put([url: url], buildHeaders(), requestBody)
        return parseResponse(responseBody)
    }

    Object getAccountDetail(String userCode) {
        assertHasText(userCode, "userCode khong duoc de trong")
        String url = normalizeBaseUrl(vnFaceUrl) + CREATE_ACCOUNT_PATH + "/" + toPathSegment(userCode)
        String responseBody = httpManaged.get(url, buildHeaders())
        return parseResponse(responseBody)
    }

    Object getGroups() {
        String url = normalizeBaseUrl(vnFaceUrl) + GROUP_PATH
        String responseBody = httpManaged.get(url, buildHeaders())
        return parseResponse(responseBody)
    }

    Object getShifts() {
        String url = normalizeBaseUrl(vnFaceUrl) + SHIFTS_PATH
        String responseBody = httpManaged.get(url, buildHeaders())
        return parseResponse(responseBody)
    }

    Object getShiftDetail(String uuidShift) {
        assertHasText(uuidShift, "uuidShift khong duoc de trong")
        String url = normalizeBaseUrl(vnFaceUrl) + SHIFTS_PATH + "/" + toPathSegment(uuidShift)
        String responseBody = httpManaged.get(url, buildHeaders())
        return parseResponse(responseBody)
    }

    Object getCheckinHistoryByUuid(String uuidHisCheckin) {
        assertHasText(uuidHisCheckin, "uuidHisCheckin khong duoc de trong")
        String url = normalizeBaseUrl(vnFaceUrl) + HIS_CHECKIN_PATH + "/" + toPathSegment(uuidHisCheckin)
        String responseBody = httpManaged.get(url, buildHeaders())
        return parseResponse(responseBody)
    }

    private Map<String, String> buildHeaders() {
        Map<String, String> headers = new LinkedHashMap<String, String>()
        headers.put("Content-Type", "application/json")
        headers.put("Authorization", "Bearer " + String.valueOf(vnFaceAccessToken))
        headers.put("Token-Channel", String.valueOf(vnFaceTokenChannel))
        return headers
    }

    private Map<String, String> buildMultipartHeaders() {
        Map<String, String> headers = new LinkedHashMap<String, String>()
        headers.put("Authorization", "Bearer " + String.valueOf(vnFaceAccessToken))
        headers.put("Token-Channel", String.valueOf(vnFaceTokenChannel))
        return headers
    }

    private Map<String, Object> buildCreateAccountFormData(VnFaceCreateAccountRequest request) {
        Map<String, Object> formData = new LinkedHashMap<String, Object>()

        formData.put("userCode", request.userCode?.trim())
        formData.put("fullName", request.fullName?.trim())
        formData.put("email", request.email?.trim())

        putIfHasText(formData, "gender", request.gender)
        putIfHasText(formData, "phoneNumber", request.phoneNumber)
        putIfHasText(formData, "accountChannels", request.accountChannels)
        putIfHasText(formData, "password", request.password)
        putIfHasText(formData, "activeDate", request.activeDate)
        putIfHasText(formData, "expireDate", request.expireDate)
        putIfHasText(formData, "accountReason", request.accountReason)

        Integer type = request.type == null ? 0 : request.type
        formData.put("type", type)

        if (request.groupCodes != null && !request.groupCodes.isEmpty()) {
            List<String> normalizedGroups = request.groupCodes
                    .findAll { it != null && !it.trim().isEmpty() }
                    .collect { it.trim() }
            if (!normalizedGroups.isEmpty()) {
                formData.put("groupCodes", normalizedGroups)
            }
        }

        return formData
    }

    private Map<String, Object> buildUpdateAccountFormData(VnFaceUpdateAccountRequest request) {
        Map<String, Object> formData = new LinkedHashMap<String, Object>()

        formData.put("userCode", request.userCode?.trim())
        formData.put("fullName", request.fullName?.trim())

        putIfHasText(formData, "email", request.email)
        putIfHasText(formData, "gender", request.gender)
        putIfHasText(formData, "phoneNumber", request.phoneNumber)
        putIfHasText(formData, "newUserCode", request.newUserCode)
        putIfHasText(formData, "accountChannels", request.accountChannels)
        putIfHasText(formData, "activeDate", request.activeDate)
        putIfHasText(formData, "expireDate", request.expireDate)
        putIfHasText(formData, "accountReason", request.accountReason)

        if (request.groupCodes != null && !request.groupCodes.isEmpty()) {
            List<String> normalizedGroups = request.groupCodes
                    .findAll { it != null && !it.trim().isEmpty() }
                    .collect { it.trim() }
            if (!normalizedGroups.isEmpty()) {
                formData.put("groupCodes", normalizedGroups)
            }
        }

        return formData
    }

    private void putIfHasText(Map<String, Object> target, String key, String value) {
        if (value == null) {
            return
        }
        String trimmed = value.trim()
        if (trimmed.isEmpty()) {
            return
        }
        target.put(key, trimmed)
    }

    private String buildCheckinHistoryUrl(VnFaceCheckinHistoryFilterRequest request) {
        List<String> query = []
        query << toQueryParam("startDate", request.startDate)
        query << toQueryParam("endDate", request.endDate)
        query << toQueryParam("page", request.page)
        query << toQueryParam("maxSize", request.maxSize)
        query << toQueryParam("userCodes", request.userCodes)
        query << toQueryParam("filterMode", request.filterMode)
        query = query.findAll { it != null }

        String url = normalizeBaseUrl(vnFaceUrl) + CHECKIN_HISTORY_PATH
        if (query.isEmpty()) {
            return url
        }
        return url + "?" + query.join("&")
    }

    private String buildAccountListUrl(VnFaceGetAccountListRequest request) {
        List<String> query = []
        query << toQueryParam("startDate", request.startDate)
        query << toQueryParam("endDate", request.endDate)
        query << toQueryParam("page", request.page)
        query << toQueryParam("maxSize", request.maxSize)
        query << toQueryParam("uuidGroup", request.uuidGroup)
        query << toQueryParam("keySearch", request.keySearch)
        query = query.findAll { it != null }

        String url = normalizeBaseUrl(vnFaceUrl) + UPDATE_ACCOUNT_PATH
        if (query.isEmpty()) {
            return url
        }
        return url + "?" + query.join("&")
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (baseUrl == null) {
            return ""
        }
        String trimmed = baseUrl.trim()
        if (trimmed.endsWith("/")) {
            return trimmed.substring(0, trimmed.length() - 1)
        }
        return trimmed
    }

    private String toQueryParam(String key, Object value) {
        if (value == null) {
            return null
        }
        String raw = value.toString().trim()
        if (raw.isEmpty()) {
            return null
        }
        return URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(raw, "UTF-8")
    }

    private String toPathSegment(String value) {
        return URLEncoder.encode(value.trim(), "UTF-8").replace("+", "%20")
    }

    private Object parseResponse(String responseBody) {
        if (responseBody == null || responseBody.trim().isEmpty()) {
            return [:]
        }

        try {
            Object parsed = objectMapper.readValue(responseBody, Object.class)
            if (parsed instanceof Map) {
                throwIfKnownHttpErrorByPayload(parsed as Map)
            }
            return parsed
        } catch (Exception ignored) {
            throwIfKnownHttpErrorByText(responseBody)
            return responseBody
        }
    }

    private void throwIfKnownHttpErrorByPayload(Map payload) {
        Integer status = extractStatusCode(payload)
        if (status == null) {
            return
        }
        if (status == 400) {
            throw new IllegalStateException("Bad Request (400): Du lieu gui sang vnFace khong hop le")
        }
        if (status == 403) {
            throw new IllegalStateException("Forbidden (403): Token khong hop le hoac khong du quyen")
        }
        if (status >= 500) {
            throw new IllegalStateException("Server Error (" + status + "): He thong vnFace dang loi")
        }
    }

    private Integer extractStatusCode(Map payload) {
        def candidates = [payload.status, payload.statusCode, payload.httpStatus, payload.code]
        for (def candidate : candidates) {
            if (candidate == null) {
                continue
            }
            try {
                String raw = candidate.toString().trim()
                if (raw.isEmpty()) {
                    continue
                }

                if (raw.matches('^\\d+$')) {
                    return Integer.parseInt(raw)
                }

                def matcher = raw =~ /^(\\d{3})\b/
                if (matcher.find()) {
                    return Integer.parseInt(matcher.group(1))
                }
            } catch (Exception ignored) {
            }
        }
        return null
    }

    private void throwIfKnownHttpErrorByText(String responseBody) {
        String lower = responseBody.toLowerCase()
        if (lower.contains("bad request") && lower.contains("400")) {
            throw new IllegalStateException("Bad Request (400): Du lieu gui sang vnFace khong hop le")
        }
        if (lower.contains("forbidden") && lower.contains("403")) {
            throw new IllegalStateException("Forbidden (403): Token khong hop le hoac khong du quyen")
        }
        if (lower.contains("internal server error") && lower.contains("500")) {
            throw new IllegalStateException("Server Error (500): He thong vnFace dang loi")
        }
    }

    private void validateBean(Object req) {
        if (req == null) {
            throw new IllegalArgumentException("Request khong duoc null")
        }

        Set<ConstraintViolation<Object>> violations = validator.validate(req)
        if (violations == null || violations.isEmpty()) {
            return
        }

        String message = violations.collect {
            String path = it.propertyPath?.toString() ?: "field"
            return path + ": " + it.message
        }.join("; ")

        throw new IllegalArgumentException(message)
    }

    private void assertHasText(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message)
        }
    }
}