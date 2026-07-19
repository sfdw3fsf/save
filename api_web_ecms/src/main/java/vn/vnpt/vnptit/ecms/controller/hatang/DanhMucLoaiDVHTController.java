package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DanhMucParam;
import vn.vnpt.vnptit.ecms.dto.hatang.LoaiDVHTRequestDTO;
import vn.vnpt.vnptit.ecms.service.hatang.DanhMucLoaiDVHTService;

import java.util.List;

@RestController
@RequestMapping("/loai-dvht")
public class DanhMucLoaiDVHTController {
    private final DanhMucLoaiDVHTService service;

    public DanhMucLoaiDVHTController(DanhMucLoaiDVHTService service) {
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
    public ResponseEntity<ApiResult> upsertLoaiDVHT(@RequestBody LoaiDVHTRequestDTO dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertLoaiDVHT(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/xoa/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteLoaiDVHT(@PathVariable("id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteLoaiDVHT(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatchLoaiDVHT(@RequestBody List<LoaiDVHTRequestDTO> dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateBatchLoaiDVHT(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/import-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> importBatchLoaiDVHT(@RequestBody List<LoaiDVHTRequestDTO> dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.batchInsertLoaiDVHT(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
