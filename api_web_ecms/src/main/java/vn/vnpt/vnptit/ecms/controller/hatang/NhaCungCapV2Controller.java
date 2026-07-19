package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucNhaCungCapV2DTO;
import vn.vnpt.vnptit.ecms.dto.hatang.NhaCungCapV2RequestDTO;
import vn.vnpt.vnptit.ecms.service.hatang.NhaCungCapV2Service;

import java.util.List;

@RestController
@RequestMapping("/nha-cung-cap-v2")
public class NhaCungCapV2Controller {
    private final NhaCungCapV2Service service;

    public NhaCungCapV2Controller(NhaCungCapV2Service service) {
        this.service = service;
    }

    @RequestMapping(value = "/get-danhmuc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhMuc(@RequestBody DanhMucNhaCungCapV2DTO dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDanhMuc(dto.getLoaiDanhMuc().toUpperCase(), dto.getThamSo1()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/upsert", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> upsertNhaCC(@RequestBody NhaCungCapV2RequestDTO dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertNhaCungCap(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/xoa/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteNhaCC(@PathVariable("id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteNhaCungCap(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatchNhaCC(@RequestBody List<NhaCungCapV2RequestDTO> dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateBatchNhaCC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/import-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> importBatchNhaCC(@RequestBody List<NhaCungCapV2RequestDTO> dto){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.batchInsertNhaCC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
