package vn.vnpt.vnptit.ecms.controller.danhmuc;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.vnptit.ecms.dto.danhmuc.LoaiIPDTO;
import vn.vnpt.vnptit.ecms.dto.danhmuc.TinhTrangIPDTO;
import vn.vnpt.vnptit.ecms.service.danhmuc.TinhTrangIPService;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("danhmuc/tinhtrang-ip")
public class TinhTrangIPController {
    @Autowired
    TinhTrangIPService tinhtrangIPService;

    @Operation(summary = "Lấy danh sách tình trạng IP")
    @RequestMapping(value = "/get-list-ip-status", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_danhsach(){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tinhtrangIPService.getListIPStatus());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "insert and update tình trạng IP")
    @RequestMapping(value = "/update-ip-status", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update(@RequestBody Map<String, String> input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(tinhtrangIPService.update(input.get("p_ds"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
/*
    @Operation(summary = "insert and update tình trạng IP")
    @RequestMapping(value = "/update-ip-status", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateIPStatus(@Valid @RequestBody TinhTrangIPDTO input){

        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(tinhtrangIPService.updateIpStatus(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
*/
    @Operation(summary = "xóa tình trạng ip")
    @RequestMapping(value = "/delete-ip-status/{Id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteIpStatus(@PathVariable(value = "Id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(tinhtrangIPService.deleteIpStatus(id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách danh mục")
    @RequestMapping(value = "/get-danhmuc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_danhsach(@RequestBody Map<String, String> input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tinhtrangIPService.getDSDanhMuc(input.get("ds")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách danh mục")
    @RequestMapping(value = "/get-danhmuc-khac", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_danhsach_khac(@RequestBody Map<String, String> input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tinhtrangIPService.getDSDanhMucKhac(input.get("table"), input.get("conds")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
