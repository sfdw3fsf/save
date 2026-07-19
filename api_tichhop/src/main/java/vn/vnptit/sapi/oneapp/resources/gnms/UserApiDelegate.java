package vn.vnptit.sapi.oneapp.resources.gnms;

import vn.vnptit.sapi.oneapp.models.gnms.AccConfig;
import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
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
 * A delegate to be called by the {@link UserApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface UserApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /user/configNewPassWord
     * API đổi mật khẩu đăng nhập của thiết bị
     *
     * @param accConfig  (optional)
     * @return Kết quả trả về là object Json chứa kết quả (status code 200)
     * @see UserApi#userConfigNewPassWord
     */
    default ResponseEntity<BaseResponse> userConfigNewPassWord(AccConfig accConfig) {
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

}
