package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucDanhSachChungDto;
import vn.vnpt.vnptit.ecms.dto.hatang.QuanLyZoneDto;
import vn.vnpt.vnptit.ecms.service.hatang.QuanLyZoneService;

@RestController
@RequestMapping("danhmuc-quanly-zone")
public class QuanLyZoneController {
    private final QuanLyZoneService service;

    public QuanLyZoneController(QuanLyZoneService service) {
        this.service = service;
    }

    @Operation(summary = "Thêm hoặc cập nhật Zone")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody QuanLyZoneDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertZone(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy ds Zone có search")
    @PostMapping("/get-ds-zone")
    public ResponseEntity<ApiResult> getDsZone(@RequestBody(required = false) QuanLyZoneDto dto) {
        ApiResult rs = new ApiResult();
        try {
            // Nếu không truyền body hoặc body rỗng → dto sẽ là null → tạo mới để tất cả param null
            if (dto == null) {
                dto = new QuanLyZoneDto();
            }
            rs.setData(service.searchZoneList(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục Zone")
    @PostMapping("/get-danhmuc-zone")
    public ResponseEntity<ApiResult> getDanhmucZone(@RequestBody DanhMucDanhSachChungDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhmucZone(dto.getLoaiDanhMuc().toUpperCase(), dto.getThamSo1(),
                    dto.getThamSo2()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa Zone theo ID")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody QuanLyZoneDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteZone(dto.getZoneId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }

        return rs.getResponseEntity();
    }
}
