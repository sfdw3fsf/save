package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchIDCRackDto;
import vn.vnpt.vnptit.ecms.service.hatang.DichVuIDCService;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/hatang/dichvu-idc")
public class DichVuIDCController {
    private final DichVuIDCService dichVuIDCService;

    public DichVuIDCController(DichVuIDCService dichVuIDCService) {
        this.dichVuIDCService = dichVuIDCService;
    }

    @Operation(summary = "Lấy danh mục loại khai báo IDC")
    @RequestMapping(value = "/get-loai-khaibao", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getLoaiKhaiBao(@RequestBody Map<String, Object> params) {
        ApiResult rs = new ApiResult();
        try {
            // Lấy p_ccdvid từ JSON body gửi từ Postman/Axios
            Object val = params.get("p_ccdvid");
            Long ccdvid = (val != null) ? Long.valueOf(val.toString()) : null;
            
            rs.setData(this.dichVuIDCService.getLoaiKhaiBao(ccdvid));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (Exception e) {
            rs.setException(new AppSqlException(e.getMessage()));
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách IDC")
    @RequestMapping(value = "/get-ds-idc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsIDC() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDCService.getDanhSachIDC());
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (Exception e) {
            rs.setException(new AppSqlException(e.getMessage()));
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hãng sản xuất")
    @RequestMapping(value = "/get-ds-hang-sx", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsHangSX() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDCService.getDanhSachHangSX());
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (Exception e) {
            rs.setException(new AppSqlException(e.getMessage()));
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tra cứu IDC Rack")
    @RequestMapping(value = "/search-idc-rack", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> searchIDCRack(@RequestBody SearchIDCRackDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDCService.searchIDCRack(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (Exception e) {
            rs.setException(new AppSqlException(e.getMessage()));
        }
        return rs.getResponseEntity();
    }
}
