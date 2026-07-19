package vn.vnptit.sapi.oneapp.resources.gnms.client;

import vn.vnptit.sapi.oneapp.invoker.gnms.ApiClient;

import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
import java.math.BigDecimal;
import java.io.File;
import vn.vnptit.sapi.oneapp.models.gnms.FileBackup;
import vn.vnptit.sapi.oneapp.models.gnms.ListReportWifi;
import vn.vnptit.sapi.oneapp.models.gnms.TemplateInf;
import vn.vnptit.sapi.oneapp.models.gnms.TemplateZerotouch;

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
public class BackupApi {
    private ApiClient apiClient;

    public BackupApi() {
        this(new ApiClient());
    }

    @Autowired
    public BackupApi(ApiClient apiClient) {
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
     * Backup cấu hình thiết bị
     * <p><b>200</b> - Đường dẫn file đã được backup
     * @param optical Mã Optical Serial number của thiết bị
     * @param backupName Tên file backup
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> backup(String optical, String backupName) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling backup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'backupName' is set
        if (backupName == null) {
            throw new WebClientResponseException("Missing the required parameter 'backupName' when calling backup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "optical", optical));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "backupName", backupName));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<BaseResponse> localVarReturnType = new ParameterizedTypeReference<BaseResponse>() {};
        return apiClient.invokeAPI("/backup/backup", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Xóa toàn bộ file backup của một thiết bị
     * <p><b>200</b> - Kết quả trả về là một Json object dạng List, mỗi object là log cho từng thiết bị
     * @param optical Mã Optical Serial number của thiết bị
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> backupDelAll(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling backupDelAll", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/backup/delAll", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy Report Wifi
     * <p><b>200</b> - Kết quả trả về là object Json chứa danh sách thiết bị trong báo cáo
     * @param areaId Area Id. Nếu rỗng kết quả trả về sẽ là tất cả Area có trên hệ thống
     * @param listSerialNumber Danh sách serial number cách nhau bởi dấu phẩy “,”. Nếu rỗng kết quả trả về sẽ là tất cả Searial Number có trên hệ thống
     * @param start Vị trí bản ghi đầu tiên muốn lấy. Đối với lần lấy đầu tiên start &#x3D; 0. Với lần lấy tiếp theo check trường start trong kết quả response. Nếu start &#x3D; -1 có nghĩa là người dùng đã lấy được tất cả các bản ghi cần lấy &#x3D;&gt; Hoàn thành. Với start khác -1, lấy giá trị start trong response của request trước để làm input của request tiếp theo. Đối với lần lấy thứ n. Kết quả trả về của start sẽ là vị trí đầu tiên của lần lấy thứ n+1.
     * @param limit Số lượng bản ghi muốn lấy (mặc định là 1000, giá trị cho phép là 0 – 2000).
     * @param startDate Thời gian bắt đầu của báo cáo.
     * @param endDate Thời gian kết thúc. Nếu để rỗng, hệ thống sẽ lấy mặc định là thời gian hiện tại.
     * @return ListReportWifi
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ListReportWifi> backupGetJsonReportWifi(BigDecimal areaId, String listSerialNumber, BigDecimal start, BigDecimal limit, String startDate, String endDate) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'areaId' is set
        if (areaId == null) {
            throw new WebClientResponseException("Missing the required parameter 'areaId' when calling backupGetJsonReportWifi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'listSerialNumber' is set
        if (listSerialNumber == null) {
            throw new WebClientResponseException("Missing the required parameter 'listSerialNumber' when calling backupGetJsonReportWifi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'start' is set
        if (start == null) {
            throw new WebClientResponseException("Missing the required parameter 'start' when calling backupGetJsonReportWifi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'limit' is set
        if (limit == null) {
            throw new WebClientResponseException("Missing the required parameter 'limit' when calling backupGetJsonReportWifi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'startDate' is set
        if (startDate == null) {
            throw new WebClientResponseException("Missing the required parameter 'startDate' when calling backupGetJsonReportWifi", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "areaId", areaId));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "listSerialNumber", listSerialNumber));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "Start", start));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "Limit", limit));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startDate", startDate));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endDate", endDate));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<ListReportWifi> localVarReturnType = new ParameterizedTypeReference<ListReportWifi>() {};
        return apiClient.invokeAPI("/backup/getJsonReportWifi", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy chuỗi cấu hình cho từng template
     * <p><b>200</b> - Kết quả trả về là object Json chứa các tham số cần để cấu hình cho template, chú ý tham số serial dùng để lưu trường Serial của thiết bị
     * @param templateCode Template code sử dụng
     * @return TemplateInf
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<TemplateInf> backupGetTemplate(String templateCode) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateCode", templateCode));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<TemplateInf> localVarReturnType = new ParameterizedTypeReference<TemplateInf>() {};
        return apiClient.invokeAPI("/backup/getTemplate", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy file import cấu hình ZeroTouch
     * <p><b>200</b> - Kết quả trả về thành công file excel (.xls) để người dùng cấu hình dịch vụ cho thiết bị.
     * @param templateCode Template Code cần để lấy import file
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<File> backupGetTemplateImport(String templateCode) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateCode", templateCode));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return apiClient.invokeAPI("/backup/getTemplateImport", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Cấu hình zero touch cho 1 thiết bị
     * <p><b>200</b> - Kết quả trả về thành công là url file backup đã tạo cho thiết bị
     * @param templateCode Template code sử dụng
     * @param objectConfig Thông tin cấu hình zero touch
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> backupImportZerotouch(String templateCode, TemplateInf objectConfig) throws WebClientResponseException {
        Object postBody = objectConfig;
        // verify the required parameter 'templateCode' is set
        if (templateCode == null) {
            throw new WebClientResponseException("Missing the required parameter 'templateCode' when calling backupImportZerotouch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'objectConfig' is set
        if (objectConfig == null) {
            throw new WebClientResponseException("Missing the required parameter 'objectConfig' when calling backupImportZerotouch", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateCode", templateCode));

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
        return apiClient.invokeAPI("/backup/importZerotouch", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Tìm kiếm template ZeroTouch
     * <p><b>200</b> - Kết quả trả về thành công là chuỗi json chứa list các template ZeroTouch tìm kiếm thành công
     * @param templateName Lọc kết quả template theo template Name
     * @param modelName Lọc kết quả template theo model name dòng thiết bị
     * @param templateCode Lọc kết quả template theo template Code
     * @return List&lt;TemplateZerotouch&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<TemplateZerotouch> backupSearchTemplate(String templateName, String modelName, String templateCode) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateName", templateName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "modelName", modelName));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "templateCode", templateCode));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<TemplateZerotouch> localVarReturnType = new ParameterizedTypeReference<TemplateZerotouch>() {};
        return apiClient.invokeFluxAPI("/backup/searchTemplate", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Import cấu hình ZeroTouch cho nhiều thiết bị
     * <p><b>200</b> - Kết quả trả về thành công là file excel (.xls) có thêm cột result chứa url file zerotouch (là file backup) vừa được tạo trên hệ thống
     * @param fileExcel File excel chứa cấu hình zerotouch của nhiều thiết bị
     * @return File
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<File> backupTemplateUpload(File fileExcel) throws WebClientResponseException {
        Object postBody = null;
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        if (fileExcel != null)
            formParams.add("fileExcel", new FileSystemResource(fileExcel));

        final String[] localVarAccepts = { 
            "application/xls"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { 
            "multipart/form-data"
        };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] { "ApiKeyAuth" };

        ParameterizedTypeReference<File> localVarReturnType = new ParameterizedTypeReference<File>() {};
        return apiClient.invokeAPI("/backup/templateUpload", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Xóa file backup cấu hình
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @param body Chuỗi JSON định danh các object file backup muốn xóa
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> delxml(String optical, List<FileBackup> body) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling delxml", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling delxml", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/backup/delxml", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Lấy danh sách file backup của thiết bị
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @return List&lt;FileBackup&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<FileBackup> getListBackup(String optical) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling getListBackup", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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

        ParameterizedTypeReference<FileBackup> localVarReturnType = new ParameterizedTypeReference<FileBackup>() {};
        return apiClient.invokeFluxAPI("/backup/getListBackup", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
    /**
     * 
     * Khôi phục cấu hình thiết bị
     * <p><b>200</b> - OK
     * @param optical Mã Optical Serial number của thiết bị
     * @param body Chuỗi JSON định danh file backup cần backup
     * @return BaseResponse
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<BaseResponse> restore(String optical, FileBackup body) throws WebClientResponseException {
        Object postBody = body;
        // verify the required parameter 'optical' is set
        if (optical == null) {
            throw new WebClientResponseException("Missing the required parameter 'optical' when calling restore", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'body' is set
        if (body == null) {
            throw new WebClientResponseException("Missing the required parameter 'body' when calling restore", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
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
        return apiClient.invokeAPI("/backup/restore", HttpMethod.POST, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }
}
