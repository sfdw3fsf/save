package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DanhMucParam;
import vn.vnpt.vnptit.ecms.dto.idc.NhomHaTangRequestDTO;
import vn.vnpt.vnptit.ecms.service.idc.NhomHaTangService;

import java.util.List;

@RestController
@RequestMapping("/nhom-ha-tang")
public class NhomHaTangController {
    private final NhomHaTangService service;

    public NhomHaTangController(NhomHaTangService service) {
        this.service = service;
    }

    @RequestMapping(value = "/get-danhmuc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhMuc(@RequestBody DanhMucParam dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhMuc(dto.getLoaiDanhMuc().toUpperCase(), dto.getThamSo1()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/upsert", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> upsertNhomHT(@RequestBody NhomHaTangRequestDTO dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertNhomHaTang(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/xoa/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteNhomHT(@PathVariable("id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteNhomHaTang(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatchNhomHT(@RequestBody List<NhomHaTangRequestDTO> dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateBatchNhomHT(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/import-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> importBatchNhomHT(@RequestBody List<NhomHaTangRequestDTO> dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.batchInsertNhomHT(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
