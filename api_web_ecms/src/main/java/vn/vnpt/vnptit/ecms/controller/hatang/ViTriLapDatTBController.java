package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.ViTriLapDatDto;
import vn.vnpt.vnptit.ecms.service.hatang.ViTriLapDatTBService;

@RestController
@RequestMapping("hatang/vi-tri-lap-dat-tb")
public class ViTriLapDatTBController {
    private final ViTriLapDatTBService viTriLapDatTBService;
    public ViTriLapDatTBController(ViTriLapDatTBService viTriLapDatTBService) {
        this.viTriLapDatTBService = viTriLapDatTBService;
    }
    @GetMapping("/cntt/{thietBiId}")
    public ResponseEntity<ApiResult> getViTriLapDatTBByThietBiId(
            @PathVariable("thietBiId") Long thietBiId)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(viTriLapDatTBService.getViTriLapDatTB(thietBiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/dien/{thietBiId}")
    public ResponseEntity<ApiResult> getViTriLapDatTBDienByThietBiId(
            @PathVariable("thietBiId") Long thietBiId
    ){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(viTriLapDatTBService.getViTriLapDatTBDien(thietBiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @GetMapping("/tu-dia/{thietBiId}")
    public ResponseEntity<ApiResult> getViTriLapDatTuDiaByThietBiId(
            @PathVariable("thietBiId") Long thietBiId)  {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(viTriLapDatTBService.getViTriLapDatTuDia(thietBiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @PostMapping("/cntt/{thietBiId}/update")
    public ResponseEntity<ApiResult> updateViTriLapDat(
            @PathVariable("thietBiId") Long thietBiId,
            @RequestBody ViTriLapDatDto dto)  {
        ApiResult rs = new ApiResult();
        try {
            rs = viTriLapDatTBService.updateViTriLapDat(thietBiId,dto);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @PostMapping("/dien/{thietBiId}/update")
    public ResponseEntity<ApiResult> updateViTriLapDatTBDien(
            @PathVariable("thietBiId") Long thietBiId,
            @RequestBody ViTriLapDatDto dto)  {
        ApiResult rs = new ApiResult();
        try {
            rs = viTriLapDatTBService.updateViTriLapDatThietBiDien(thietBiId,dto);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}