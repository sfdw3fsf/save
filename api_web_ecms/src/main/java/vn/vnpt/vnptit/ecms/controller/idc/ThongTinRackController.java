package vn.vnpt.vnptit.ecms.controller.idc;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucRackDto;
import vn.vnpt.vnptit.ecms.dto.idc.RackImportResponseDto;
import vn.vnpt.vnptit.ecms.dto.idc.RackImportRowDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThongTinRackDto;
import vn.vnpt.vnptit.ecms.service.idc.ThongTinRackService;

@RestController
@RequestMapping("/thongtin-rack")
public class ThongTinRackController {
    private final ThongTinRackService thongTinRackService;

    public ThongTinRackController(ThongTinRackService thongTinRackService) {
        this.thongTinRackService = thongTinRackService;
    }

    @Operation(summary = "Thêm hoặc cập nhật Rack")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody ThongTinRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinRackService.upsertRack(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Loại Rack")
    @PostMapping("/get-danhmuc-rack")
    public ResponseEntity<ApiResult> getDanhmucRack(@RequestBody DanhMucRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinRackService.getDanhmucRack(dto.getLoaiDanhMuc().toUpperCase(), dto.getThamSo1(),
                    dto.getThamSo2()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin Rack")
    @PostMapping("/get-tt-rack")
    public ResponseEntity<ApiResult> getLopKN() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinRackService.getThongTinRack());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay thong tin Rack co the cap phat")
    @PostMapping("/get-available-racks")
    public ResponseEntity<ApiResult> getAvailableRacks() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinRackService.getAvailableRacks());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa Rack theo ID")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody ThongTinRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinRackService.deleteRack(dto.getRackId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }

        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin Rack theo ID")
    @PostMapping("/get-by-id")
    public ResponseEntity<ApiResult> getById(@RequestBody ThongTinRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinRackService.getRackById(dto.getRackId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Rack theo phòng")
    @PostMapping("/get-by-room")
    public ResponseEntity<ApiResult> getByRoom(@RequestBody ThongTinRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinRackService.getRacksByRoomId(dto.getPhongId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách U theo phòng")
    @PostMapping("/get-u-by-room")
    public ResponseEntity<ApiResult> getUByRoom(@RequestBody ThongTinRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinRackService.getUByRoomId(dto.getPhongId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách U theo rack")
    @PostMapping("/get-u-by-rack")
    public ResponseEntity<ApiResult> getUByRack(@RequestBody ThongTinRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinRackService.getUByRackId(dto.getRackId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin mặt trước sau rack")
    @PostMapping("/get-front-back-info")
    public ResponseEntity<ApiResult> getFrontBackInfo(@RequestBody ThongTinRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinRackService.getFrontBackInfo(dto.getRackId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách U 2 mặt theo rack")
    @PostMapping("/get-u-2-mat-theo-rack")
    public ResponseEntity<ApiResult> getU2MatTheoRack(@RequestBody ThongTinRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinRackService.getU2MatTheoRack(dto.getRackId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Import Rack từ file Excel (không dùng JSON, loop từng dòng)")
    @PostMapping("/import")
    public ResponseEntity<ApiResult> importRack(@Valid @RequestBody List<RackImportRowDto> danhSachRack) {
        ApiResult rs = new ApiResult();
        try {
            RackImportResponseDto result = thongTinRackService.importRackRows(danhSachRack);
            rs.setData(result);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate Import Rack từ file Excel (chỉ kiểm tra, không insert)")
    @PostMapping("/validate-import")
    public ResponseEntity<ApiResult> validateImportRack(@Valid @RequestBody List<RackImportRowDto> danhSachRack) {
        ApiResult rs = new ApiResult();
        try {
            RackImportResponseDto result = thongTinRackService.validateImportRackRows(danhSachRack);
            rs.setData(result);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}

