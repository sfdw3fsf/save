package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CapNhatLoaiSfpDtoInput;
import vn.vnpt.vnptit.ecms.dto.CapNhatSerialPortGponDtoInput;
import vn.vnpt.vnptit.ecms.dto.PortGponDtoInput;
import vn.vnpt.vnptit.ecms.service.PortGponService;

import javax.validation.Valid;

@RestController
@RequestMapping("portgpon")
public class PortGponController {
    @Autowired
    public PortGponService portGponService;

    @Operation(summary = "Lấy thông tin port gpon")
    @GetMapping("/thongtin/{id}")
    public ResponseEntity<ApiResult> getPortGpon(@PathVariable(name = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(portGponService.getPortGpon(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin port gpon")
    @GetMapping("/lay-sl-thuebao-doitt-theoportid/{id}")
    public ResponseEntity<ApiResult> getSLThuebaoDoiTT(@PathVariable(name = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(portGponService.getSLThuebaoDoiTT(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy ds thuê bao theo portvl id")
    @GetMapping("/lay_ds_thuebao_theo_portvl_id/{id}")
    public ResponseEntity<ApiResult> getDsThueBaoTheoPort(@PathVariable(name = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(portGponService.getDsThueBaoTheoPort(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chuyển trạng thái port")
    @PostMapping("/chuyen_trangthai_port")
    public ResponseEntity<ApiResult> chuyenTrangThaiPort(@RequestBody @Valid PortGponDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(portGponService.chuyenTrangThaiPort(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra trùng serial")
    @GetMapping("/kiemtra-trung-serial")
    public ResponseEntity<ApiResult> kiemTraTrungSerial(@RequestParam(name = "serial") String serial) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(portGponService.kiemTraTrungSerial(serial)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật serial")
    @PostMapping("/capnhat-serial")
    public ResponseEntity<ApiResult> capNhatSerial(@RequestBody @Valid CapNhatSerialPortGponDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(portGponService.capNhatSerial(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo dữ liệu visa theo portvl_id")
    @GetMapping("/tao-dulieu-visa/{id}")
    public ResponseEntity<ApiResult> taoDuLieuVisa(@PathVariable(name = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(portGponService.taoDuLieuVisa(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Cập nhật Loại SFP")
    @PostMapping("/capnhat-loai-sfp")
    public ResponseEntity<ApiResult> capNhatLoaiSFP(@RequestBody @Valid CapNhatLoaiSfpDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(portGponService.capNhatLoaiSFP(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
