package vn.vnptit.sapi.oneapp.resources.gnms;

import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
import vn.vnptit.sapi.oneapp.models.gnms.BatchInput;
import vn.vnptit.sapi.oneapp.models.gnms.BatchLogListResponse;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A delegate to be called by the {@link BatchApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface BatchApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /batch/action
     * reboot/reset/xóa file backup thiết bị hàng loạt
     *
     * @param body Chuỗi JSON định danh file backup cần backup (required)
     * @return url trỏ tới api check kết quả thực hiện (status code 200)
     * @see BatchApi#batchAction
     */
    default ResponseEntity<BaseResponse> batchAction(BatchInput body) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /batch/result
     * Kết quả log batch (reboot, reset, …)
     *
     * @param batchID Batch session ID của lệnh cần tra cứu log (required)
     * @return Kết quả trả về là một Json object dạng List, mỗi object là log cho từng thiết bị (status code 200)
     * @see BatchApi#batchResult
     */
    default ResponseEntity<BatchLogListResponse> batchResult(Integer batchID) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"opticalSerial\" : \"opticalSerial\", \"timeInsert\" : 5.637376656633329, \"description\" : \"description\", \"id\" : 0, \"batchID\" : 6, \"type\" : 5, \"timeFinish\" : 2.3021358869347655, \"status\" : 1, \"Result\" : \"Result\" }, { \"opticalSerial\" : \"opticalSerial\", \"timeInsert\" : 5.637376656633329, \"description\" : \"description\", \"id\" : 0, \"batchID\" : 6, \"type\" : 5, \"timeFinish\" : 2.3021358869347655, \"status\" : 1, \"Result\" : \"Result\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
