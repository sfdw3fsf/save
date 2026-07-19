package vn.vnpt.vnptit.ecms.controller.idc;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.idc.DeletePhongZoneDto;
import vn.vnpt.vnptit.ecms.dto.idc.PhongZoneDto;
import vn.vnpt.vnptit.ecms.service.idc.ThongTinPhongZoneService;

@RestController
@RequestMapping("idc/phong-zone")
public class DanhMucPhongZoneController {
    private final ThongTinPhongZoneService service;

    public DanhMucPhongZoneController(ThongTinPhongZoneService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách phòng/zone")
    @GetMapping("")
    public ResponseEntity<ApiResult> listPhongZone() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getListPhongZone());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật phòng/zone (upsert)")
    @PostMapping("")
    public ResponseEntity<ApiResult> upsertPhongZone(@RequestBody PhongZoneDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertPhongZone(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhiều phòng/zone")
    @DeleteMapping("/delete-multi")
    public ResponseEntity<ApiResult> deletePhongZoneMulti(@RequestBody DeletePhongZoneDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deletePhongZoneMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật nhiều phòng/zone (upsert multi)")
    @PostMapping("/upsert-multi")
    public ResponseEntity<ApiResult> upsertPhongZoneMulti(@RequestBody List<PhongZoneDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertPhongZoneMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate nhiều phòng/zone (validate multi)")
    @PostMapping("/validate-multi")
    public ResponseEntity<ApiResult> checkValidPhongZoneMulti(@RequestBody List<PhongZoneDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.checkValidPhongZoneMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết phòng zone theo ID")
    @PostMapping("/get-by-id")
    public ResponseEntity<ApiResult> getById(@RequestBody DataKeyInput item) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getById(item.getId()));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị thuộc phòng")
    @PostMapping("/get-thiet-bi-by-phong-zone-id")
    public ResponseEntity<ApiResult> getThietBiByPhongZoneId(@RequestBody DataKeyInput item) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getThietBiByPhongZoneId(item.getId()));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
}
