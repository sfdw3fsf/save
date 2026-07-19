package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.KiemTraTrungViTriModuleManeDtoInput;
import vn.vnpt.vnptit.ecms.dto.ManeModuleDtoIn;
import vn.vnpt.vnptit.ecms.service.ManeModuleService;

import javax.validation.Valid;

@RestController
@RequestMapping("mane-module")
public class ManeModuleController {
    @Autowired
    public ManeModuleService maneModuleService;

    @Operation(summary = "Lấy thông tin module mane")
    @GetMapping("/thongtin/{id}")
    public ResponseEntity<ApiResult> getManeModule(@PathVariable(name = "id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(maneModuleService.getManeModule(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy ds loại module")
    @GetMapping("/ds-loaimodule")
    public ResponseEntity<ApiResult> getDsLoaiModule() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(maneModuleService.getDsLoaiModule());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "them moi Mane module")
    @PostMapping("/them-manemodule")
    public ResponseEntity<ApiResult> insert( @RequestBody ManeModuleDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(maneModuleService.insert(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "cap nhat Mane module")
    @PostMapping("/capnhat-manemodule")
    public ResponseEntity<ApiResult> update(@RequestBody ManeModuleDtoIn.ManeModuleDtoInUpd data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(maneModuleService.update(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xoa Mane module")
    @PostMapping("/xoa-manemodule")
    public ResponseEntity<ApiResult> delete(@RequestParam(name = "modulemane_id") Integer modulemane_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(maneModuleService.delete(modulemane_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "tao port Mane module")
    @PostMapping("/createport-manemodule")
    public ResponseEntity<ApiResult> create_port(@RequestParam(name = "modulemane_id") Integer modulemane_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(maneModuleService.create_port(modulemane_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra trùng vị trí module mane")
    @PostMapping("/kiemtra-trung-vitri")
    public ResponseEntity<ApiResult> kiemTraTrungVitri(@RequestBody @Valid KiemTraTrungViTriModuleManeDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(maneModuleService.kiemTraTrungVitri(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra trùng serial module mane")
    @GetMapping("/kiemtra-trung-serial")
    public ResponseEntity<ApiResult> kiemTraTrungSerial(@RequestParam(name = "serial") String serial) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(maneModuleService.kiemTraTrungSerial(serial)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
