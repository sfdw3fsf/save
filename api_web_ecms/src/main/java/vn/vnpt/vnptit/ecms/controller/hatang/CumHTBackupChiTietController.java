package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.CumHTBackupChiTietDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.DichVuBackupDto;
import vn.vnpt.vnptit.ecms.dto.hatang.NangLucCumHaTangBackupDTO;
import vn.vnpt.vnptit.ecms.service.hatang.CumHTBackupChiTietService;

import javax.validation.Valid;

@RestController
@RequestMapping("cum-htbk/thong-tin-chung")
public class CumHTBackupChiTietController {
    private final CumHTBackupChiTietService service;

    public CumHTBackupChiTietController(CumHTBackupChiTietService service) {
        this.service = service;
    }

    @RequestMapping(value = "/chitiet/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getChiTietCumHTBackup(@PathVariable("id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getChiTietCumHaTangBackup(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/upsert", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themCumHTBackup(@RequestBody @Valid CumHTBackupChiTietDTO dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertCumHTBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/xoa/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaCumHTBackup(@PathVariable("id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteCumHaTangBackup(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "dich vu backup cho cum ha tang")
    @PostMapping("/tai-nguyen")
    public ResponseEntity<ApiResult> dichVuBackupThuocCumHaTang(@RequestBody DichVuBackupDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsDVBackupThuocCumHaTang(dto.getCumHtDichId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/nang-luc/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getNangLucCumHTBackup(@PathVariable("id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getNangLucCumHaTangBackup(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/nang-luc/upsert", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themNangLucCumHTBackup(@RequestBody @Valid NangLucCumHaTangBackupDTO dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertNangLucCumHTBackup(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
