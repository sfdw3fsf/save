package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.ImmutableMap
import groovy.sql.Sql
import io.swagger.api.FileApi
import io.swagger.model.request.CapNhatDanhBaFileRequest
import io.swagger.model.response.BaseResponse
import org.apache.tika.Tika
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.Constant
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.MessageUtils
import vn.vnptit.sapi.oneapp.util.Utils

import javax.servlet.http.HttpServletRequest

import static vn.vnptit.sapi.oneapp.resources.GenericApi.getCSS_SCH
import static vn.vnptit.sapi.oneapp.resources.GenericApi.getDEVICE_ID
import static vn.vnptit.sapi.oneapp.resources.GenericApi.getID_TINHTHANH
import static vn.vnptit.sapi.oneapp.resources.GenericApi.getMA_NV

@RestController
class FileController implements FileApi {

    private final HttpServletRequest request
    private final ObjectMapper objectMapper

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    ObjectStoragesManaged objectStoragesManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    GenericApi genericApi
    @Value("\${spring.object-storages.minio-storage.bucket}")
    String bucketName

    @Value("\${spring.defaultconfigs.dhsx_img_url}")
    String dhsx_img_url

    @Autowired
    FileController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper
        this.request = request
    }

    @Override
    ResponseEntity<?> getFile(String folder) {
        try {
            def is = objectStoragesManaged.getObject(bucketName, folder)
            Tika tika = new Tika()
            String type = tika.detect(is)
            InputStreamResource resource = new InputStreamResource(objectStoragesManaged.getObject(bucketName, folder))
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(type)).body(resource)
        } catch (Exception e) {
            e.printStackTrace()
            loggingManaged.error(e.getMessage())
            return new ResponseEntity(e.getMessage(), HttpStatus.OK)
        }
    }

    @Override
    ResponseEntity<BaseResponse<String>> updateFileUrl(CapNhatDanhBaFileRequest requestBody) {
        String ret = null
        String ref = null
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[FileController] - [updateFileUrl] - Request: ", new ImmutableMap.Builder<String, Object>().put("request_id", requestId).build())
            if (requestBody.getpUrls() == null && requestBody.getpUrls().trim().length() == 0) {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, requestBody.getpStorageFile() + " không có danh bạ file đi kèm!", HttpStatus.BAD_REQUEST))
            }
            String newUrl = ""
            if (requestBody.getpUrls() != null && requestBody.getpUrls().trim().length() > 0) {
                String[] urls = requestBody.getpUrls().split(",")
                for (String url : urls) {
                    if (url.contains(Constant.MINIO_STORAGE_KEY)) {
                        try {
                            String url_bs = url.split(Constant.MINIO_STORAGE_KEY)[0].split("/storage-onebss.vnpt.vn/")[1]
                            if(url_bs.startsWith("onebssproduct")){
                                String url_path = url_bs.split("/")[1]
                                newUrl += url_path + "/" + Constant.MINIO_STORAGE_KEY + url.split(Constant.MINIO_STORAGE_KEY)[1].split("\\?")[0] + ","
                            }else {
                                newUrl += Constant.MINIO_STORAGE_KEY + url.split(Constant.MINIO_STORAGE_KEY)[1].split("\\?")[0] + ","
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace()
                            newUrl += url + ","
                        }
                    } else {
                        newUrl += url + ","
                    }
                }
                newUrl = newUrl.substring(0, newUrl.length() - 1)
            }
            Map<String, Object> session_ = genericApi.getSessionData(request);
            String dataSrc = session_.get(CSS_SCH)
            Long phanVungId = Long.parseLong(session_.get(ID_TINHTHANH).toString())
            String mayCn = session_.get(DEVICE_ID)
            String nguoiCn = session_.get(MA_NV)
            String sqlString = "{ call CSS.CAPNHAT_DANHBA_FILE(?,?,?,?,?,?,?,?,?,?) }"
            Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
                sql.call(sqlString, [phanVungId, requestBody.getpStorageFile(), requestBody.getpDbId(), newUrl,
                                     requestBody.getpLoaiFileId(), requestBody.getpNoiThem(), mayCn, nguoiCn, Sql.VARCHAR, Sql.VARCHAR]) { String rsId, String rs ->
                    ret = rs
                    if (rs == "OK") {
                        ref = rsId
                    }
                }
            }
            if (ret != null && ret.trim() == "OK") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(ref != null ? ref : ""))
            }
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, requestBody.getpStorageFile() + " chưa được hỗ trợ!", HttpStatus.BAD_REQUEST))
        } catch (Exception e) {
            loggingManaged.error("[FileController] - [updateFileUrl] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    ResponseEntity<BaseResponse<String>> getFileData(String filePath) {
        String requestId = genericApi.generateRequestId()
        try {
            loggingManaged.info("[FileController] - [getFile] - Request: ", new ImmutableMap.Builder<String, Object>()
                    .put("request_id", requestId).put("filePath", filePath).build())
            String base64Pdf
            byte [] bytesPdf
            if (filePath.indexOf(Constant.MINIO_STORAGE_KEY) == 0) {
                bytesPdf = Utils.downloadUrl(new URL(objectStoragesManaged.getUrl(filePath, bucketName)))
            } else {
                bytesPdf = Utils.downloadUrl(filePath, dhsx_img_url)
            }
            base64Pdf = new String(Base64.getEncoder().encode(bytesPdf))
            if (base64Pdf != null) {
                loggingManaged.info("[FileController] - [getFile] - Response: ", new ImmutableMap.Builder<String, Object>()
                        .put("request_id", requestId).put("base64Pdf", base64Pdf).build())
                return ResponseEntity.ok().body(BaseResponseBuilder.of(base64Pdf, requestId, MessageUtils.SUCCESS, HttpStatus.OK))
            } else {
                loggingManaged.info("[FileController] - [getFile] - Response: ", new ImmutableMap.Builder<String, Object>()
                        .put("request_id", requestId).put("base64Pdf", "").build())
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, MessageUtils.NO_DATA, HttpStatus.NO_CONTENT))
            }
        } catch (Exception e) {
            loggingManaged.error("[FileController] - [getFile] - Error:", e)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, requestId, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }
}
