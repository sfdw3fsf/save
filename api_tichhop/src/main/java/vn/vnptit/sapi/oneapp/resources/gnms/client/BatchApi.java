package vn.vnptit.sapi.oneapp.resources.gnms.client;

import vn.vnptit.sapi.oneapp.invoker.gnms.ApiClient;

import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
import vn.vnptit.sapi.oneapp.models.gnms.BatchInput;
import vn.vnptit.sapi.oneapp.models.gnms.BatchLog;

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
public class BatchApi {
    private ApiClient apiClient;

    public BatchApi() {
        this(new ApiClient());
    }

    @Autowired
    public BatchApi(ApiClient apiClient) {
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
     * reboot/reset/xóa file backup thiết bị hàng loạt
     * <p><b>200</b> - url trỏ tới api check kết quả thực hiện
     * @param body Chuỗi JSON định danh file backup cần backup
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> batchAction(BatchInput body) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling batchAction", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

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
        return apiClient.invokeAPI("/batch/action", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Kết quả log batch (reboot, reset, …)
     * <p><b>200</b> - Kết quả trả về là một Json object dạng List, mỗi object là log cho từng thiết bị
     * @param batchID Batch session ID của lệnh cần tra cứu log
     * @return List&lt;BatchLog&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<BatchLog> batchResult(Integer batchID) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'batchID' is set
        if (batchID == null) {
            throw new WebClientResponseException("Missing the required parameter 'batchID' when calling batchResult", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "batchID", batchID));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<BatchLog> localVarReturnType = new ParameterizedTypeReference<BatchLog>() {};
        return apiClient.invokeFluxAPI("/batch/result", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
