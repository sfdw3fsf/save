package vn.vnpt.vnptit.ecms.controller.hatang;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;

import vn.vnpt.vnptit.ecms.dto.hatang.IdcImportDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.MatsanImportDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.PhongImportDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.ToanhaImportDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.*;
import vn.vnpt.vnptit.ecms.dto.idc.IDCRackRequestDTO;
import vn.vnpt.vnptit.ecms.service.hatang.ImportTaiNguyenService;

import javax.validation.Valid;

@RestController
@RequestMapping("import-tainguyen")
public class ImportTaiNguyenController {

    @Autowired
    ImportTaiNguyenService service;

    @Operation(summary = "Validate danh sách thiết bị trước khi import")
    @PostMapping(value = "/thietbi/validate")
    public ResponseEntity<ApiResult> validateDanhSachThietBi(@RequestBody List<ImportThietBiItemDto> deviceList) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.validateDanhSachThietBi(deviceList));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Import danh sách thiết bị")
    @PostMapping(value = "/thietbi")
    public ResponseEntity<ApiResult> importDanhSachThietBi(@RequestBody List<ImportThietBiItemDto> deviceList) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.importDanhSachThietBi(deviceList));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Validate danh sách kết nối trước khi import")
    @PostMapping(value = "/ketnoi/validate")
    public ResponseEntity<ApiResult> validateImportKetNoiBatch(@RequestBody List<ImportKetNoiItemDto> ketNoiList) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.validateImportKetNoiBatch(ketNoiList));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Import danh sách kết nối")
    @PostMapping(value = "/ketnoi")
    public ResponseEntity<ApiResult> importDanhSachKetNoi(@RequestBody List<ImportKetNoiItemDto> ketNoiList) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.importDanhSachKetNoi(ketNoiList));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Validate danh sách rack")
    @PostMapping("/idc-rack/validate")
    public ResponseEntity<ApiResult> validateBatchIdcRack(@Valid @RequestBody List<IDCRackRequestDTO> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateIdcRack(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Import danh sách rack")
    @PostMapping("/idc-rack/import")
    public ResponseEntity<ApiResult> inportIdcRack(@Valid @RequestBody List<IDCRackRequestDTO> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.batchInsertIdcRack(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    //  Import IDC
    @Operation(summary = "Kiểm tra dữ liệu import (batch)")
// ok
    @PostMapping("/idc/validate")
    public ResponseEntity<ApiResult> validateImportIdcBatch(@RequestBody List<IdcImportDTO> list) {
        ApiResult rs = new ApiResult();
        try {
//            rs.setData(service.kiemtra_import_batch(list));
            rs.setData(service.validateImportIdcBatch(list));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
//    logSerrvice.log(null, "/api/idc/check-import-batch", list, rs);
        return rs.getResponseEntity();
    }

    @Operation(summary = "Import batch dữ liệu IDC")
    // ok
    @PostMapping("/idc/import")
    public ResponseEntity<ApiResult> importIdc(@RequestBody List<IdcImportDTO> list) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.importIdc(list));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
//        logSerrvice.log(null, "/api/idc/import-batch", list, rs);
        return rs.getResponseEntity();
    }


    //import Toanha
    @Operation(summary = "Kiểm tra dữ liệu import (batch)")
    @PostMapping("toanha/validate")
    public ResponseEntity<ApiResult> validateImportToanhaBatch(@RequestBody List<ToanhaImportDTO> list) {
        ApiResult rs = new ApiResult();
        try {
//            rs.setData(service.kiemtra_import_batch(list));
            rs.setData(service.validateImportToanhaBatch(list));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
//        logSerrvice.log(null, "/api/toanha/check-import-batch", list, rs);
        return rs.getResponseEntity();
    }

    @Operation(summary = "Import batch dữ liệu Tòa Nhà")
    @PostMapping("toanha/import")
    public ResponseEntity<ApiResult> importToanha(@RequestBody List<ToanhaImportDTO> list) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.importToanha(list));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
//        logSerrvice.log(null, "/api/toanha/import-batch", list, rs);
        return rs.getResponseEntity();
    }


    @Operation(summary = "Kiểm tra dữ liệu import (batch)")
    @PostMapping("matsan/validate")
    public ResponseEntity<ApiResult> validateImportMatsanBatch(@RequestBody List<MatsanImportDTO> list) {
        ApiResult rs = new ApiResult();
        try {
//            rs.setData(service.kiemtra_import_batch(list));
            rs.setData(service.validateImportMatsanBatch(list));
        } catch (AppSqlException e) {

            rs.setException(e);
        }
//        logSerrvice.log(null, "/api/matsan/check-import-batch", list, rs);
        return rs.getResponseEntity();
    }

    @Operation(summary = "Import batch dữ liệu Mặt Sàn")
    @PostMapping("matsan/import")
    public ResponseEntity<ApiResult> importMatsan(@RequestBody List<MatsanImportDTO> list) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.importMatsan(list));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
//        logSerrvice.log(null, "/api/matsan/import-batch", list, rs);
        return rs.getResponseEntity();
    }


    @Operation(summary = "Kiểm tra dữ liệu import (batch)")
    @PostMapping("phong/validate")
    public ResponseEntity<ApiResult> validateImportPhongBatch(@RequestBody List<PhongImportDTO> list) {
        ApiResult rs = new ApiResult();
        try {
//            rs.setData(service.kiemtra_import_batch(list));
            rs.setData(service.validateImportPhongBatch(list));
        } catch (AppSqlException e) {

            rs.setException(e);
        }
//        logSerrvice.log(null, "/api/phong/check-import-batch", list, rs);
        return rs.getResponseEntity();
    }

    @Operation(summary = "Import batch dữ liệu Phòng")
    @PostMapping("phong/import")
    public ResponseEntity<ApiResult> importPhong(@RequestBody List<PhongImportDTO> list) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.importPhong(list));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
//        logSerrvice.log(null, "/api/phong/import-batch", list, rs);
        return rs.getResponseEntity();
    }
}
