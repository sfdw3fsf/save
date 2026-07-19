package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatLoaiDongBoDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThemLoaiDongBoDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.LoaiDongBoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/danhmuc/loaidongbo")
public class LoaiDongBoController {
    @Autowired
    LoaiDongBoService loaiDongBoService;
    @Operation(summary = "LAY_DS_LOAIDONGBO")
    @RequestMapping(value = "/lay_ds_loaidongbo", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_loaidongbo(){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiDongBoService.lay_ds_loaidongbo());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "LAY_LOAIDONGBO_THEO_LOAIDB_ID")
    @RequestMapping(value = "/lay_loaidongbo_theo_loaidb_id", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_loaidongbo_theo_loaidb_id(Long loaidbId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiDongBoService.lay_loaidongbo_theo_loaidb_id(loaidbId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "TAO_LOAIDONGBO")
    @RequestMapping(value = "/tao_loaidongbo", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tao_loaidongbo(@Valid @RequestBody ThemLoaiDongBoDtoIn loaiDongBo){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiDongBoService.tao_loaidongbo(loaiDongBo));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "CAPNHAT_LOAIDONGBO")
    @RequestMapping(value = "/capnhat_loaidongbo", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_loaidongbo(@Valid @RequestBody CapNhatLoaiDongBoDtoIn loaiDongBo){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiDongBoService.capnhat_loaidongbo(loaiDongBo));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "XOA_LOAIDONGBO")
    @RequestMapping(value = "/xoa_loaidongbo", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa_loaidongbo(Long loaidbId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(loaiDongBoService.xoa_loaidongbo(loaidbId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
