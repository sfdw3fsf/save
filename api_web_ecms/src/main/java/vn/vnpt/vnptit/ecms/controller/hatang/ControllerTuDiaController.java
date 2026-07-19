package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.hatang.ControllerTuDiaDto;
import vn.vnpt.vnptit.ecms.dto.hatang.PortThietBiInputDto;
import vn.vnpt.vnptit.ecms.service.hatang.ControllerTuDiaService;

import javax.validation.Valid;

@RestController
@RequestMapping("hatang/controller-tudia")
public class ControllerTuDiaController {
    private final ControllerTuDiaService service;
    public ControllerTuDiaController(ControllerTuDiaService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy thông tin của controller")
    @RequestMapping(value = "/get-thongtin", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getThongTinController(@Valid @RequestBody DataKeyInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getThongTinController(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy Danh sách port theo phân loại")
    @RequestMapping(value = "/get-ds-port-theo-phanloai", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsPortTheoPhanLoai(@Valid @RequestBody ControllerTuDiaDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDsPortTheoPhanLoai(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật Controller")
    @RequestMapping(value = "/capnhat-controller", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhatController(@Valid @RequestBody ControllerTuDiaDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.capnhatController(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin lịch sử thay đổi của controller")
    @RequestMapping(value = "/get-thongtin-lichsu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getThongTinLichSuController(@Valid @RequestBody DataKeyInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getThongTinLichSuController(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy Danh sách port thiết bị theo phân loại")
    @RequestMapping(value = "/get-ds-port-thietbi-theo-phanloai", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsPortThietBiTheoPhanLoai(@Valid @RequestBody PortThietBiInputDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDsPortThietBiTheoPhanLoai(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy Danh sách port bộ điều khiển chủng loại theo phân loại")
    @RequestMapping(value = "/get-ds-port-bodieukhien-chungloai-theo-phanloai", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsPortBoDieuKhienChungLoaiTheoPhanLoai(@Valid @RequestBody PortThietBiInputDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDsPortBoDieuKhienChungLoaiTheoPhanLoai(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy tổng số port chủng loại thiết bị khai báo theo phân loại")
    @RequestMapping(value = "/get-tong-port-chungloai-khaibao-theo-phanloai", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getTongPortChungLoaiKhaiBaoTheoPhanLoai(@Valid @RequestBody PortThietBiInputDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getTongPortChungLoaiKhaiBaoTheoPhanLoai(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
