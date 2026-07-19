package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.danhmuc.LoaiIPDTO;
import vn.vnpt.vnptit.ecms.service.danhmuc.LoaiIPService;

import javax.validation.Valid;

@RestController
@RequestMapping("/loai-ip")
public class LoaiIPController {

    @Autowired
    LoaiIPService loaiIPService;

    @Operation(summary = "Lấy danh sách loại IP")
    @RequestMapping(value = "/lay-danhsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_danhsach(){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiIPService.layDsLoaiIp());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "insert and update loại IP")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tao(@Valid @RequestBody LoaiIPDTO input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(loaiIPService.updateInsertLoaiIp(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xóa loại ip")
    @RequestMapping(value = "/xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa(@Valid @RequestBody DataKeyInput item){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(loaiIPService.xoaLoaiIp(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
