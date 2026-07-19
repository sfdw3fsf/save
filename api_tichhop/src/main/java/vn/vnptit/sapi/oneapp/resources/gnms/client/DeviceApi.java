package vn.vnptit.sapi.oneapp.resources.gnms.client;

import vn.vnptit.sapi.oneapp.invoker.gnms.ApiClient;

import vn.vnptit.sapi.oneapp.models.gnms.AccWebGui;
import vn.vnptit.sapi.oneapp.models.gnms.AddInterfaceGroupingRequest;
import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
import vn.vnptit.sapi.oneapp.models.gnms.BoundInterfacesList;
import vn.vnptit.sapi.oneapp.models.gnms.ConnectedUser;
import vn.vnptit.sapi.oneapp.models.gnms.DeleteInterfaceGroupingRequest;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceParam;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWan;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWirelessAdv;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWlan;
import vn.vnptit.sapi.oneapp.models.gnms.DiagnosticResponse;
import vn.vnptit.sapi.oneapp.models.gnms.GNMSResponse;
import vn.vnptit.sapi.oneapp.models.gnms.GetVoiceList;
import vn.vnptit.sapi.oneapp.models.gnms.InlineObject;
import vn.vnptit.sapi.oneapp.models.gnms.InterfaceGroupingList;
import vn.vnptit.sapi.oneapp.models.gnms.LanInterfacesResponse;
import vn.vnptit.sapi.oneapp.models.gnms.ListAdslDevice;
import vn.vnptit.sapi.oneapp.models.gnms.OpticalInfo;
import vn.vnptit.sapi.oneapp.models.gnms.PingResult;
import vn.vnptit.sapi.oneapp.models.gnms.WanInterfacesResponse;

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
public class DeviceApi {
    private ApiClient apiClient;

    public DeviceApi() {
        this(new ApiClient());
    }

    @Autowired
    public DeviceApi(ApiClient apiClient) {
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
     * Xóa thiết bị
     * <p><b>200</b> - Kết quả trả về là một Json object
     * @param optical Mã Optical Serial number của thiết bị
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> delete(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling delete", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/delete", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API cài đặt Wan bridge
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả
     * @param serviceDescription Brigde name (Ví dụ: br_eth0)
     * @param vlanMux8021Priority Vlan 802.1P Priority
     * @param vlanMuxID Vlan ID
     * @param enableWan Enable wan
     * @param optical Số serial của thiết bị
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceAddBridgeWan(String serviceDescription, Integer vlanMux8021Priority, Integer vlanMuxID, Boolean enableWan, String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'serviceDescription' is set
        if (serviceDescription == null) {
            throw new WebClientResponseException("Missing the required parameter 'serviceDescription' when calling deviceAddBridgeWan", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'vlanMux8021Priority' is set
        if (vlanMux8021Priority == null) {
            throw new WebClientResponseException("Missing the required parameter 'vlanMux8021Priority' when calling deviceAddBridgeWan", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'vlanMuxID' is set
        if (vlanMuxID == null) {
            throw new WebClientResponseException("Missing the required parameter 'vlanMuxID' when calling deviceAddBridgeWan", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'enableWan' is set
        if (enableWan == null) {
            throw new WebClientResponseException("Missing the required parameter 'enableWan' when calling deviceAddBridgeWan", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceAddBridgeWan", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serviceDescription", serviceDescription));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "vlanMux802_1Priority", vlanMux8021Priority));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "vlanMuxID", vlanMuxID));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "enableWan", enableWan));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "optical", optical));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/addBridgeWan", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API add Interface Grouping
     * <p><b>200</b> - OK
     * @param serialNumber Mã Serial number của thiết bị
     * @param body Object InterfaceGrouping
     * @return GNMSResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<GNMSResponse> deviceAddInterfaceGrouping(String serialNumber, AddInterfaceGroupingRequest body) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'serialNumber' is set
        if (serialNumber == null) {
            throw new WebClientResponseException("Missing the required parameter 'serialNumber' when calling deviceAddInterfaceGrouping", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling deviceAddInterfaceGrouping", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serialNumber", serialNumber));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<GNMSResponse> localVarReturnType = new ParameterizedTypeReference<GNMSResponse>() {};
        return apiClient.invokeAPI("/device/addInterfaceGrouping", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API add Wan
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @param body Object WAN
     * @return GNMSResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<GNMSResponse> deviceAddWan(String optical, DeviceWan body) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceAddWan", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling deviceAddWan", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<GNMSResponse> localVarReturnType = new ParameterizedTypeReference<GNMSResponse>() {};
        return apiClient.invokeAPI("/device/addWan", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy cấu hình account đăng nhập thiết bị
     * <p><b>200</b> - Kết quả trả về là object Json chứa message thông báo kết quả
     * @param optical Mã Optical Serial number của thiết bị
     * @param username Username login
     * @param password Password mới
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceConfigWebGuiPass(String optical, String username, String password) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceConfigWebGuiPass", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'username' is set
        if (username == null) {
            throw new WebClientResponseException("Missing the required parameter 'username' when calling deviceConfigWebGuiPass", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'password' is set
        if (password == null) {
            throw new WebClientResponseException("Missing the required parameter 'password' when calling deviceConfigWebGuiPass", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "optical", optical));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "username", username));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "password", password));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/configWebGuiPass", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API xoá wan trên ONT
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả
     * @param optical Mã OpticalNo của thiết bị
     * @param deviceWan The deviceWan parameter
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceDelWan(String optical, DeviceWan deviceWan) throws WebClientResponseException {
        Object postBody = deviceWan;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceDelWan", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/device/delWan", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API xoá ONT
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả
     * @param optical Danh sách thiết bị xoá
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceDeleteDevice(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceDeleteDevice", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/deleteDevice", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API delete Interface Grouping
     * <p><b>200</b> - OK
     * @param serialNumber Mã Serial number của thiết bị
     * @param body Object InterfaceGrouping
     * @return GNMSResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<GNMSResponse> deviceDeleteInterfaceGrouping(String serialNumber, DeleteInterfaceGroupingRequest body) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'serialNumber' is set
        if (serialNumber == null) {
            throw new WebClientResponseException("Missing the required parameter 'serialNumber' when calling deviceDeleteInterfaceGrouping", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling deviceDeleteInterfaceGrouping", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serialNumber", serialNumber));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "application/json"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<GNMSResponse> localVarReturnType = new ParameterizedTypeReference<GNMSResponse>() {};
        return apiClient.invokeAPI("/device/deleteInterfaceGrouping", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Đo kiểm kết nối của thiết bị
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả ping
     * @param optical Mã Optical Serial number của thiết bị
     * @param pingto url hoặc ip của host cần kiểm tra kết nối
     * @param downloadurl url test download file
     * @param uploadurl url upload file test (kích thước 10Mb)
     * @return DiagnosticResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<DiagnosticResponse> deviceDiagnostic(String optical, String pingto, String downloadurl, String uploadurl) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceDiagnostic", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'pingto' is set
        if (pingto == null) {
            throw new WebClientResponseException("Missing the required parameter 'pingto' when calling deviceDiagnostic", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'downloadurl' is set
        if (downloadurl == null) {
            throw new WebClientResponseException("Missing the required parameter 'downloadurl' when calling deviceDiagnostic", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'uploadurl' is set
        if (uploadurl == null) {
            throw new WebClientResponseException("Missing the required parameter 'uploadurl' when calling deviceDiagnostic", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "optical", optical));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "pingto", pingto));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "downloadurl", downloadurl));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "uploadurl", uploadurl));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<DiagnosticResponse> localVarReturnType = new ParameterizedTypeReference<DiagnosticResponse>() {};
        return apiClient.invokeAPI("/device/diagnostic", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API lấy số lượng người dùng trong mạng LAN
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả
     * @param serialNumber Mã OpticalNo của thiết bị(phân biệt hoa thường)
     * @return List&lt;ConnectedUser&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<ConnectedUser> deviceFetchConnectedUserInfor(String serialNumber) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'serialNumber' is set
        if (serialNumber == null) {
            throw new WebClientResponseException("Missing the required parameter 'serialNumber' when calling deviceFetchConnectedUserInfor", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serialNumber", serialNumber));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<ConnectedUser> localVarReturnType = new ParameterizedTypeReference<ConnectedUser>() {};
        return apiClient.invokeFluxAPI("/device/fetchConnectedUserInfor", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API lấy thông tin nhiệt độ, công suất thu phát của module quang trên ONT
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả
     * @param serialNumber Mã OpticalNo của thiết bị(phân biệt hoa thường)
     * @return List&lt;OpticalInfo&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<OpticalInfo> deviceFetchOpticalInfo(String serialNumber) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'serialNumber' is set
        if (serialNumber == null) {
            throw new WebClientResponseException("Missing the required parameter 'serialNumber' when calling deviceFetchOpticalInfo", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serialNumber", serialNumber));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<OpticalInfo> localVarReturnType = new ParameterizedTypeReference<OpticalInfo>() {};
        return apiClient.invokeFluxAPI("/device/fetchOpticalInfo", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy thông tin account đăng nhập WebGui của thiết bị
     * <p><b>200</b> - Kết quả trả về là object Json chứa thông tin account đăng nhập thiết bị
     * @param optical Mã Optical Serial number của thiết bị
     * @return List&lt;AccWebGui&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<AccWebGui> deviceGetAccWebGui(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceGetAccWebGui", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<AccWebGui> localVarReturnType = new ParameterizedTypeReference<AccWebGui>() {};
        return apiClient.invokeFluxAPI("/device/getAccWebGui", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API get bound interfaces list
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @return BoundInterfacesList
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BoundInterfacesList> deviceGetBoundInterfacesList(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceGetBoundInterfacesList", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<BoundInterfacesList> localVarReturnType = new ParameterizedTypeReference<BoundInterfacesList>() {};
        return apiClient.invokeAPI("/device/getBoundInterfacesList", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy danh sách thông tin thiết bị kết nối wifi
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả
     * @param optical Mã Optical Serial number của thiết bị
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceGetInfoDeviceConnect(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceGetInfoDeviceConnect", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/getInfoDeviceConnect", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API get Interface Grouping list
     * <p><b>200</b> - OK
     * @param serialNumber Mã Serial number của thiết bị
     * @return InterfaceGroupingList
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<InterfaceGroupingList> deviceGetInterfaceGroupingList(String serialNumber) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'serialNumber' is set
        if (serialNumber == null) {
            throw new WebClientResponseException("Missing the required parameter 'serialNumber' when calling deviceGetInterfaceGroupingList", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serialNumber", serialNumber));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<InterfaceGroupingList> localVarReturnType = new ParameterizedTypeReference<InterfaceGroupingList>() {};
        return apiClient.invokeAPI("/device/getInterfaceGroupingList", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API get LAN Interfaces list
     * <p><b>200</b> - OK
     * @param serialNumber Mã Serial number của thiết bị
     * @return LanInterfacesResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<LanInterfacesResponse> deviceGetLanInterfacesList(String serialNumber) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'serialNumber' is set
        if (serialNumber == null) {
            throw new WebClientResponseException("Missing the required parameter 'serialNumber' when calling deviceGetLanInterfacesList", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serialNumber", serialNumber));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<LanInterfacesResponse> localVarReturnType = new ParameterizedTypeReference<LanInterfacesResponse>() {};
        return apiClient.invokeAPI("/device/getLanInterfacesList", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy thông tin các WAN của thiết bị
     * <p><b>200</b> - Kết quả trả về là object Json chứa danh sách các WAN của thiết bị
     * @param optical Mã Optical Serial number của thiết bị
     * @return List&lt;DeviceWan&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<DeviceWan> deviceGetListWan(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceGetListWan", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<DeviceWan> localVarReturnType = new ParameterizedTypeReference<DeviceWan>() {};
        return apiClient.invokeFluxAPI("/device/getListWan", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy thông tin wifi
     * <p><b>200</b> - Kết quả trả về là một Json List object các Wlan của thiết bị
     * @param optical Mã Optical Serial number của thiết bị
     * @return List&lt;DeviceWlan&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<DeviceWlan> deviceGetListWlan(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceGetListWlan", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeFluxAPI("/device/getListWlan", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API xoá ONT
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả
     * @param serialNumber Mã OpticalNo của thiết bị
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceGetResultFactoryReset(String serialNumber) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'serialNumber' is set
        if (serialNumber == null) {
            throw new WebClientResponseException("Missing the required parameter 'serialNumber' when calling deviceGetResultFactoryReset", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serialNumber", serialNumber));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/getResultFactoryReset", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API get voice configuration
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @return GetVoiceList
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<GetVoiceList> deviceGetVoice(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceGetVoice", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<GetVoiceList> localVarReturnType = new ParameterizedTypeReference<GetVoiceList>() {};
        return apiClient.invokeAPI("/device/getVoice", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API get WAN Interfaces list
     * <p><b>200</b> - OK
     * @param serialNumber Mã Serial number của thiết bị
     * @return WanInterfacesResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<WanInterfacesResponse> deviceGetWanInterfacesList(String serialNumber) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'serialNumber' is set
        if (serialNumber == null) {
            throw new WebClientResponseException("Missing the required parameter 'serialNumber' when calling deviceGetWanInterfacesList", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serialNumber", serialNumber));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<WanInterfacesResponse> localVarReturnType = new ParameterizedTypeReference<WanInterfacesResponse>() {};
        return apiClient.invokeAPI("/device/getWanInterfacesList", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy thông tin cấu hình advance của wifi (channel, công suất phát, …)
     * <p><b>200</b> - Kết quả trả về là object Json chứa thông tin cấu hình wif
     * @param optical Mã Optical Serial number của thiết bị
     * @return DeviceWirelessAdv
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<DeviceWirelessAdv> deviceGetWirelessAdvance(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceGetWirelessAdvance", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<DeviceWirelessAdv> localVarReturnType = new ParameterizedTypeReference<DeviceWirelessAdv>() {};
        return apiClient.invokeAPI("/device/getWirelessAdvance", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Cấu hình on/off wifi
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @param wifis List các object wifi cần bật/tắt
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceOnOffWifi(String optical, List<DeviceWlan> wifis) throws WebClientResponseException {
        Object postBody = wifis;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceOnOffWifi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'wifis' is set
        if (wifis == null) {
            throw new WebClientResponseException("Missing the required parameter 'wifis' when calling deviceOnOffWifi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/device/onOffWifi", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Kiểm tra kết nối của thiết bị
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả ping
     * @param optical Mã Optical Serial number của thiết bị
     * @param host url hoặc ip của host cần kiểm tra kết nối
     * @return PingResult
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<PingResult> devicePing(String optical, String host) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling devicePing", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'host' is set
        if (host == null) {
            throw new WebClientResponseException("Missing the required parameter 'host' when calling devicePing", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "optical", optical));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "host", host));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<PingResult> localVarReturnType = new ParameterizedTypeReference<PingResult>() {};
        return apiClient.invokeAPI("/device/ping", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API reboot ONT
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả
     * @param serialNumber Mã OpticalNo của thiết bị
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceRebootDevice(String serialNumber) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'serialNumber' is set
        if (serialNumber == null) {
            throw new WebClientResponseException("Missing the required parameter 'serialNumber' when calling deviceRebootDevice", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "serialNumber", serialNumber));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/rebootDevice", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Reset lại thiết bị
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceReset(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceReset", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/reset", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API thống kê ONT
     * <p><b>200</b> - Kết quả trả về là object Json chứa kết quả
     * @param start The start parameter
     * @param limit The limit parameter
     * @param areaName The areaName parameter
     * @param areaId The areaId parameter
     * @return ListAdslDevice
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ListAdslDevice> deviceSearchAdslDeviceAdslFirmware(String start, String limit, String areaName, String areaId) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'start' is set
        if (start == null) {
            throw new WebClientResponseException("Missing the required parameter 'start' when calling deviceSearchAdslDeviceAdslFirmware", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'limit' is set
        if (limit == null) {
            throw new WebClientResponseException("Missing the required parameter 'limit' when calling deviceSearchAdslDeviceAdslFirmware", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'areaName' is set
        if (areaName == null) {
            throw new WebClientResponseException("Missing the required parameter 'areaName' when calling deviceSearchAdslDeviceAdslFirmware", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "start", start));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "limit", limit));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "areaName", areaName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "areaId", areaId));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<ListAdslDevice> localVarReturnType = new ParameterizedTypeReference<ListAdslDevice>() {};
        return apiClient.invokeAPI("/device/searchAdslDeviceAdslFirmware", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API set voice configuration
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @param body The body parameter
     * @return GNMSResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<GNMSResponse> deviceSetVoice(String optical, InlineObject body) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceSetVoice", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling deviceSetVoice", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<GNMSResponse> localVarReturnType = new ParameterizedTypeReference<GNMSResponse>() {};
        return apiClient.invokeAPI("/device/setVoice", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Cấu hình thông tin wifi advance
     * <p><b>200</b> - Kết quả trả về thành công là chuỗi json
     * @param optical Mã Optical Serial number của thiết bị
     * @param newConfig Object cấu hình wifi advance
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceSetWirelessAdvance(String optical, DeviceWirelessAdv newConfig) throws WebClientResponseException {
        Object postBody = newConfig;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceSetWirelessAdvance", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'newConfig' is set
        if (newConfig == null) {
            throw new WebClientResponseException("Missing the required parameter 'newConfig' when calling deviceSetWirelessAdvance", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/device/setWirelessAdvance", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Cấu hình thông tin wifi (wlan)
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @param wifis List các object wifi cần bật/tắt
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceUpdateWifi(String optical, List<DeviceWlan> wifis) throws WebClientResponseException {
        Object postBody = wifis;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceUpdateWifi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'wifis' is set
        if (wifis == null) {
            throw new WebClientResponseException("Missing the required parameter 'wifis' when calling deviceUpdateWifi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/device/updateWifi", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * API cấu hình Wifi
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @param wifis List các object wifi cần bật/tắt
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> deviceUpdateWifiType(String optical, List<DeviceWlan> wifis) throws WebClientResponseException {
        Object postBody = wifis;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling deviceUpdateWifiType", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'wifis' is set
        if (wifis == null) {
            throw new WebClientResponseException("Missing the required parameter 'wifis' when calling deviceUpdateWifiType", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/device/updateWifiType", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy thông tin uptime thiết bị
     * <p><b>200</b> - Thời gian uptime tính theo giây
     * @param optical Mã Optical Serial number của thiết bị
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> getUptime(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling getUptime", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/getUptime", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Khởi động lại thiết bị
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> reboot(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling reboot", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/device/reboot", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy thông tin chi tiết thiết bị
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @return DeviceParam
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<DeviceParam> searchDevice(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling searchDevice", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<DeviceParam> localVarReturnType = new ParameterizedTypeReference<DeviceParam>() {};
        return apiClient.invokeAPI("/device/getByOptical", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
