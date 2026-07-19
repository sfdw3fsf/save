package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucDanhSachChungDto;
import vn.vnpt.vnptit.ecms.dto.hatang.QuanLyVrfDto;
import vn.vnpt.vnptit.ecms.service.hatang.QuanLyVrfService;


@RestController
@RequestMapping("danhmuc-quanly-vrf")
public class QuanLyVrfController {
    private final QuanLyVrfService service;

    public QuanLyVrfController(QuanLyVrfService service) {
        this.service = service;
    }

    @Operation(summary = "Thêm hoặc cập nhật Vrf")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody QuanLyVrfDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertVrf(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy ds Vrf có search")
    @PostMapping("/get-ds-vrf")
    public ResponseEntity<ApiResult> getDsVrf(@RequestBody(required = false) QuanLyVrfDto dto) {
        ApiResult rs = new ApiResult();
        try {
            // Nếu không truyền body hoặc body rỗng → dto sẽ là null → tạo mới để tất cả param null
            if (dto == null) {
                dto = new QuanLyVrfDto();
            }
            rs.setData(service.searchVrfList(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục Vrf")
    @PostMapping("/get-danhmuc-vrf")
    public ResponseEntity<ApiResult> getDanhmucVrf(@RequestBody DanhMucDanhSachChungDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhmucVrf(dto.getLoaiDanhMuc().toUpperCase(), dto.getThamSo1(),
                    dto.getThamSo2()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa Zone theo ID")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody QuanLyVrfDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteVrf(dto.getVrfId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }

        return rs.getResponseEntity();
    }
}
