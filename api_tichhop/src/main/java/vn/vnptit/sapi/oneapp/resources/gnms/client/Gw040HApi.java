package vn.vnptit.sapi.oneapp.resources.gnms.client;

import vn.vnptit.sapi.oneapp.invoker.gnms.ApiClient;

import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWirelessAdvV2;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWlan;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-05-25T10:46:07.604822600+07:00[Asia/Bangkok]")
public class Gw040HApi {
    private ApiClient apiClient;

    public Gw040HApi() {
        this(new ApiClient());
    }

    @Autowired
    public Gw040HApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * 
     * API lấy dang sách wlan hiện có của GW040H
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả
     * @param optical Mã Optical Serial number của thiết bị
     * @return List&lt;DeviceWlan&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<DeviceWlan> deviceGetListWlanV2(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceGetListWlanV2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "optical", optical));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<DeviceWlan> localVarReturnType = new ParameterizedTypeReference<DeviceWlan>() {};
        return apiClient.invokeFluxAPI("/device/v2/GW040H/getListWlan", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API lấy danh sách các wlan advance cho GW040H
     * <p><b>200</b> - Kết quả trả về là object Json chứa thông tin cấu hình wif
     * @param optical Mã Optical Serial number của thiết bị
     * @return List&lt;DeviceWirelessAdvV2&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<DeviceWirelessAdvV2> deviceGetWirelessAdvanceV2(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceGetWirelessAdvanceV2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "optical", optical));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<DeviceWirelessAdvV2> localVarReturnType = new ParameterizedTypeReference<DeviceWirelessAdvV2>() {};
        return apiClient.invokeFluxAPI("/device/v2/GW040H/getWirelessAdvance", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API cập nhật thông tin wlan advance cho GW040H
     * <p><b>200</b> - Kết quả trả về thành công là chuỗi json
     * @param optical Mã Optical Serial number của thiết bị
     * @param newConfig Object cấu hình wifi advance
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceSetWirelessAdvanceV2(String optical, DeviceWirelessAdvV2 newConfig) throws WebClientResponseException {
        Object postBody = newConfig;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceSetWirelessAdvanceV2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'newConfig' is set
        if (newConfig == null) {
            throw new WebClientResponseException("Missing the required parameter 'newConfig' when calling deviceSetWirelessAdvanceV2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "optical", optical));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/v2/GW040H/setWirelessAdvance", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API cập nhật thông tin wlan cho GW040H
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @param wifis List các object wifi cần bật/tắt
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceUpdateWifiV2(String optical, List<DeviceWlan> wifis) throws WebClientResponseException {
        Object postBody = wifis;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceUpdateWifiV2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'wifis' is set
        if (wifis == null) {
            throw new WebClientResponseException("Missing the required parameter 'wifis' when calling deviceUpdateWifiV2", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "optical", optical));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/v2/GW040H/updateWifi", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
