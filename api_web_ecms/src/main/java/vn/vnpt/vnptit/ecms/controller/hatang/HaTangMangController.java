
package vn.vnpt.vnptit.ecms.controller.hatang;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.hatang.HaTangMangDtoIn;
import vn.vnpt.vnptit.ecms.service.hatang.HaTangMangService;

@RestController
@RequestMapping("danhmuc/ha-tang-mang")
public class HaTangMangController {
    private final HaTangMangService service;

    public HaTangMangController(HaTangMangService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách hạ tầng mạng")
    @GetMapping("/danh-sach")
    public ResponseEntity<ApiResult> danhSach() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getData());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật hạ tầng mạng")
    @PostMapping("/cap-nhat")
    public ResponseEntity<ApiResult> capNhat(@Valid @RequestBody HaTangMangDtoIn input) throws AppSqlException {
        return service.capNhatHaTangMang(input);
    }
    @Operation(summary = "Xóa Danh mục hạ tầng mạng")
    @PostMapping("/xoa")
    public ResponseEntity<ApiResult> xoa(@RequestBody HaTangMangDtoIn input) throws AppSqlException {
        return service.xoaHaTangMang(input);
    }
    // thông tin hạ tầng mạng by id
    @Operation(summary = "Hạ tầng mạng ảo hóa")
    @GetMapping("/ao-hoa")
    public ResponseEntity<ApiResult> listHaTangMangAoHoa(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getHaTangMangSuDung("AO-HOA", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hạ tầng mạng smartcloud")
    @GetMapping("/smartcloud")
    public ResponseEntity<ApiResult> listHaTangMangSmartCloud(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getHaTangMangSuDung("SMART-CLOUD", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Hạ tầng mạng IDG")
    @GetMapping("/idg")
    public ResponseEntity<ApiResult> listHaTangMangIdg(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getHaTangMangSuDung("IDG", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hạ tầng mạng Subnet")
    @GetMapping("/subnet")
    public ResponseEntity<ApiResult> listHaTangMangSubnet(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getHaTangMangSuDung("SUBNET", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hạ tầng mạng IP Address")
    @GetMapping("/ip-address")
    public ResponseEntity<ApiResult> listHaTangMangIpAddress(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getHaTangMangSuDung("IP", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hạ tầng mạng thiết bị")
    @GetMapping("/thiet-bi")
    public ResponseEntity<ApiResult> listHaTangMangThietBi(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getHaTangMangSuDung("THIET-BI", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Lịch sử tác động Hạ tầng mạng")
    @GetMapping("/history")
    public ResponseEntity<ApiResult> listHaTangMangHistory(@Valid @RequestParam("haTangMangId") Long haTangMangId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getHaTangMangSuDung("HISTORY", haTangMangId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

//    @Operation(summary = "Lấy chi tiết hạ tầng mạng theo id")
//    @PostMapping("/get-by-id")
//    public ResponseEntity<ApiResult> getById(@RequestBody Map<String, Long> request) throws AppSqlException {
//        ApiResult rs = new ApiResult();
//        try {
//            rs.setData(service.getHaTangMangById(request.get("id")));
//        } catch (AppSqlException e) {
//            rs.setException(e);
//        }
//        return rs.getResponseEntity();
//    }

	
    @Operation(summary = "Lấy chi tiết hạ tầng mạng theo id")
    @RequestMapping(value = "/get-hatangmang", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getHaTangMang(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getHaTangMang(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
