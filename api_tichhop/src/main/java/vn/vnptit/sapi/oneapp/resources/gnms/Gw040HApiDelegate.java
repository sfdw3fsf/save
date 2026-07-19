package vn.vnptit.sapi.oneapp.resources.gnms;

import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWirelessAdvV2;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWirelessAdvV2ListResponse;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWlan;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWlanListResponse;
import java.util.List;
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
 * A delegate to be called by the {@link Gw040HApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface Gw040HApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /device/v2/GW040H/getListWlan
     * API lấy dang sách wlan hiện có của GW040H
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return Kết quả trả về là object Json chứa kết quả (status code 200)
     * @see Gw040HApi#deviceGetListWlanV2
     */
    default ResponseEntity<DeviceWlanListResponse> deviceGetListWlanV2(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"authMode\" : \"authMode\", \"password\" : \"password\", \"enable\" : true, \"encrypt\" : \"encrypt\", \"index\" : 0, \"type\" : \"type\", \"ssid\" : \"ssid\" }, { \"authMode\" : \"authMode\", \"password\" : \"password\", \"enable\" : true, \"encrypt\" : \"encrypt\", \"index\" : 0, \"type\" : \"type\", \"ssid\" : \"ssid\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/v2/GW040H/getWirelessAdvance
     * API lấy danh sách các wlan advance cho GW040H
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return Kết quả trả về là object Json chứa thông tin cấu hình wif (status code 200)
     * @see Gw040HApi#deviceGetWirelessAdvanceV2
     */
    default ResponseEntity<DeviceWirelessAdvV2ListResponse> deviceGetWirelessAdvanceV2(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"autoChannelTimer\" : 0.8008281904610115, \"instance\" : \"instance\", \"enable\" : true, \"channelWidth\" : \"channelWidth\", \"channel\" : \"channel\", \"transmitPower\" : 6.027456183070403 }, { \"autoChannelTimer\" : 0.8008281904610115, \"instance\" : \"instance\", \"enable\" : true, \"channelWidth\" : \"channelWidth\", \"channel\" : \"channel\", \"transmitPower\" : 6.027456183070403 } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /device/v2/GW040H/setWirelessAdvance
     * API cập nhật thông tin wlan advance cho GW040H
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param newConfig Object cấu hình wifi advance (required)
     * @return Kết quả trả về thành công là chuỗi json (status code 200)
     * @see Gw040HApi#deviceSetWirelessAdvanceV2
     */
    default ResponseEntity<BaseResponse> deviceSetWirelessAdvanceV2(String optical,
        DeviceWirelessAdvV2 newConfig) {
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
     * POST /device/v2/GW040H/updateWifi
     * API cập nhật thông tin wlan cho GW040H
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param wifis List các object wifi cần bật/tắt (required)
     * @return OK (status code 200)
     * @see Gw040HApi#deviceUpdateWifiV2
     */
    default ResponseEntity<BaseResponse> deviceUpdateWifiV2(String optical,
        List<DeviceWlan> wifis) {
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
