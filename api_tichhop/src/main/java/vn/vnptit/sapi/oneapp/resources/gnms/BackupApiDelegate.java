package vn.vnptit.sapi.oneapp.resources.gnms;

import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
import java.math.BigDecimal;
import vn.vnptit.sapi.oneapp.models.gnms.FileBackup;
import vn.vnptit.sapi.oneapp.models.gnms.FileBackupListResponse;
import java.util.List;
import vn.vnptit.sapi.oneapp.models.gnms.ListReportWifiResponse;
import vn.vnptit.sapi.oneapp.models.gnms.TemplateInf;
import vn.vnptit.sapi.oneapp.models.gnms.TemplateInfResponse;
import vn.vnptit.sapi.oneapp.models.gnms.TemplateZerotouchListResponse;
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
 * A delegate to be called by the {@link BackupApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface BackupApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /backup/backup
     * Backup cấu hình thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param backupName Tên file backup (required)
     * @return Đường dẫn file đã được backup (status code 200)
     * @see BackupApi#backup
     */
    default ResponseEntity<BaseResponse> backup(String optical,
        String backupName) {
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
     * GET /backup/delAll
     * Xóa toàn bộ file backup của một thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return Kết quả trả về là một Json object dạng List, mỗi object là log cho từng thiết bị (status code 200)
     * @see BackupApi#backupDelAll
     */
    default ResponseEntity<BaseResponse> backupDelAll(String optical) {
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
     * GET /backup/getJsonReportWifi
     * Lấy Report Wifi
     *
     * @param areaId Area Id. Nếu rỗng kết quả trả về sẽ là tất cả Area có trên hệ thống (required)
     * @param listSerialNumber Danh sách serial number cách nhau bởi dấu phẩy “,”. Nếu rỗng kết quả trả về sẽ là tất cả Searial Number có trên hệ thống (required)
     * @param start Vị trí bản ghi đầu tiên muốn lấy. Đối với lần lấy đầu tiên start &#x3D; 0. Với lần lấy tiếp theo check trường start trong kết quả response. Nếu start &#x3D; -1 có nghĩa là người dùng đã lấy được tất cả các bản ghi cần lấy &#x3D;&gt; Hoàn thành. Với start khác -1, lấy giá trị start trong response của request trước để làm input của request tiếp theo. Đối với lần lấy thứ n. Kết quả trả về của start sẽ là vị trí đầu tiên của lần lấy thứ n+1. (required)
     * @param limit Số lượng bản ghi muốn lấy (mặc định là 1000, giá trị cho phép là 0 – 2000). (required)
     * @param startDate Thời gian bắt đầu của báo cáo. (required)
     * @param endDate Thời gian kết thúc. Nếu để rỗng, hệ thống sẽ lấy mặc định là thời gian hiện tại. (optional)
     * @return Kết quả trả về là object Json chứa danh sách thiết bị trong báo cáo (status code 200)
     * @see BackupApi#backupGetJsonReportWifi
     */
    default ResponseEntity<ListReportWifiResponse> backupGetJsonReportWifi(BigDecimal areaId,
        String listSerialNumber,
        BigDecimal start,
        BigDecimal limit,
        String startDate,
        String endDate) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"total\" : 0, \"start\" : 6, \"limit\" : 1, \"listReportWifi\" : [ { \"productID\" : 9, \"channel\" : \"channel\", \"airtimeFairness\" : \"airtimeFairness\", \"description\" : \"description\", \"enableTraficSchedule\" : \"enableTraficSchedule\", \"wifiMode\" : \"wifiMode\", \"ssid\" : \"ssid\", \"disableWmm\" : \"disableWmm\", \"txPower\" : \"txPower\", \"encryption\" : \"encryption\", \"opticalSerial\" : \"opticalSerial\", \"areaName\" : \"areaName\", \"countryCode\" : \"countryCode\", \"startTime\" : 5.637376656633329, \"id\" : 5, \"autoChannelTimer\" : \"autoChannelTimer\", \"bandwidth\" : \"bandwidth\", \"dnsIfName\" : \"dnsIfName\", \"wifiInstance\" : 3, \"flagInValid\" : true, \"internetTime\" : \"internetTime\", \"modelName\" : \"modelName\", \"staticDNS\" : \"staticDNS\", \"areaId\" : 7, \"enableMulticast\" : \"enableMulticast\", \"endTime\" : 2.3021358869347655, \"networkAuth\" : \"networkAuth\", \"softwareVersion\" : \"softwareVersion\" }, { \"productID\" : 9, \"channel\" : \"channel\", \"airtimeFairness\" : \"airtimeFairness\", \"description\" : \"description\", \"enableTraficSchedule\" : \"enableTraficSchedule\", \"wifiMode\" : \"wifiMode\", \"ssid\" : \"ssid\", \"disableWmm\" : \"disableWmm\", \"txPower\" : \"txPower\", \"encryption\" : \"encryption\", \"opticalSerial\" : \"opticalSerial\", \"areaName\" : \"areaName\", \"countryCode\" : \"countryCode\", \"startTime\" : 5.637376656633329, \"id\" : 5, \"autoChannelTimer\" : \"autoChannelTimer\", \"bandwidth\" : \"bandwidth\", \"dnsIfName\" : \"dnsIfName\", \"wifiInstance\" : 3, \"flagInValid\" : true, \"internetTime\" : \"internetTime\", \"modelName\" : \"modelName\", \"staticDNS\" : \"staticDNS\", \"areaId\" : 7, \"enableMulticast\" : \"enableMulticast\", \"endTime\" : 2.3021358869347655, \"networkAuth\" : \"networkAuth\", \"softwareVersion\" : \"softwareVersion\" } ] } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /backup/getTemplate
     * Lấy chuỗi cấu hình cho từng template
     *
     * @param templateCode Template code sử dụng (optional)
     * @return Kết quả trả về là object Json chứa các tham số cần để cấu hình cho template, chú ý tham số serial dùng để lưu trường Serial của thiết bị (status code 200)
     * @see BackupApi#backupGetTemplate
     */
    default ResponseEntity<TemplateInfResponse> backupGetTemplate(String templateCode) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"pppoe_user\" : \"pppoe_user\", \"pppoe_vlanid\" : \"pppoe_vlanid\", \"wifi_mytv_pass\" : \"wifi_mytv_pass\", \"wifi_name\" : \"wifi_name\", \"wifi_5g_pass\" : \"wifi_5g_pass\", \"serial\" : \"serial\", \"bridge_vlanid\" : \"bridge_vlanid\", \"wifi_mytv_name\" : \"wifi_mytv_name\", \"wifi_pass\" : \"wifi_pass\", \"pppoe_pass\" : \"pppoe_pass\", \"wifi_5g_name\" : \"wifi_5g_name\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /backup/getTemplateImport
     * Lấy file import cấu hình ZeroTouch
     *
     * @param templateCode Template Code cần để lấy import file (optional)
     * @return Kết quả trả về thành công file excel (.xls) để người dùng cấu hình dịch vụ cho thiết bị. (status code 200)
     * @see BackupApi#backupGetTemplateImport
     */
    default ResponseEntity<org.springframework.core.io.Resource> backupGetTemplateImport(String templateCode) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /backup/importZerotouch
     * Cấu hình zero touch cho 1 thiết bị
     *
     * @param templateCode Template code sử dụng (required)
     * @param objectConfig Thông tin cấu hình zero touch (required)
     * @return Kết quả trả về thành công là url file backup đã tạo cho thiết bị (status code 200)
     * @see BackupApi#backupImportZerotouch
     */
    default ResponseEntity<BaseResponse> backupImportZerotouch(String templateCode,
        TemplateInf objectConfig) {
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
     * GET /backup/searchTemplate
     * Tìm kiếm template ZeroTouch
     *
     * @param templateName Lọc kết quả template theo template Name (optional)
     * @param modelName Lọc kết quả template theo model name dòng thiết bị (optional)
     * @param templateCode Lọc kết quả template theo template Code (optional)
     * @return Kết quả trả về thành công là chuỗi json chứa list các template ZeroTouch tìm kiếm thành công (status code 200)
     * @see BackupApi#backupSearchTemplate
     */
    default ResponseEntity<TemplateZerotouchListResponse> backupSearchTemplate(String templateName,
        String modelName,
        String templateCode) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"templateName\" : \"templateName\", \"modelname\" : \"modelname\", \"description\" : \"description\", \"id\" : 0.8008281904610115, \"templateCode\" : \"templateCode\", \"inputFormat\" : \"inputFormat\", \"url\" : \"url\", \"softwareVersion\" : \"softwareVersion\", \"createDate\" : \"createDate\", \"status\" : 6.027456183070403 }, { \"templateName\" : \"templateName\", \"modelname\" : \"modelname\", \"description\" : \"description\", \"id\" : 0.8008281904610115, \"templateCode\" : \"templateCode\", \"inputFormat\" : \"inputFormat\", \"url\" : \"url\", \"softwareVersion\" : \"softwareVersion\", \"createDate\" : \"createDate\", \"status\" : 6.027456183070403 } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /backup/templateUpload
     * Import cấu hình ZeroTouch cho nhiều thiết bị
     *
     * @param fileExcel File excel chứa cấu hình zerotouch của nhiều thiết bị (optional)
     * @return Kết quả trả về thành công là file excel (.xls) có thêm cột result chứa url file zerotouch (là file backup) vừa được tạo trên hệ thống (status code 200)
     * @see BackupApi#backupTemplateUpload
     */
    default ResponseEntity<org.springframework.core.io.Resource> backupTemplateUpload(MultipartFile fileExcel) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /backup/delxml
     * Xóa file backup cấu hình
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param body Chuỗi JSON định danh các object file backup muốn xóa (required)
     * @return OK (status code 200)
     * @see BackupApi#delxml
     */
    default ResponseEntity<BaseResponse> delxml(String optical,
        List<FileBackup> body) {
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
     * GET /backup/getListBackup
     * Lấy danh sách file backup của thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return OK (status code 200)
     * @see BackupApi#getListBackup
     */
    default ResponseEntity<FileBackupListResponse> getListBackup(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"areaID\" : \"areaID\", \"opticalSerial\" : \"opticalSerial\", \"areaName\" : \"areaName\", \"type\" : 0, \"backup_date\" : \"backup_date\", \"url\" : \"url\", \"softwareVersion\" : \"softwareVersion\", \"menID\" : \"menID\" }, { \"areaID\" : \"areaID\", \"opticalSerial\" : \"opticalSerial\", \"areaName\" : \"areaName\", \"type\" : 0, \"backup_date\" : \"backup_date\", \"url\" : \"url\", \"softwareVersion\" : \"softwareVersion\", \"menID\" : \"menID\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /backup/restore
     * Khôi phục cấu hình thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param body Chuỗi JSON định danh file backup cần backup (required)
     * @return OK (status code 200)
     * @see BackupApi#restore
     */
    default ResponseEntity<BaseResponse> restore(String optical,
        FileBackup body) {
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
