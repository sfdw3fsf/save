package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.service.hatang.CapPhatIPService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("hatang/capphat-ip")
public class CapPhatIPController {
    @Autowired
    CapPhatIPService service;

    @Operation(summary = "Lấy danh sách phiếu cấp phát IP")
    @RequestMapping(value = "/get-list", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_danhsach(){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getList());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "insert and update phiếu cấp phát IP")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update(@RequestBody Map<String, String> input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.update(input.get("p_ds"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xóa phiếu cấp phát IP Address")
    @RequestMapping(value = "/delete/{Id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> delete(@PathVariable(value = "Id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.delete(id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "view phiếu cấp phát IP Address")
    @RequestMapping(value = "/view/{Id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> view(@PathVariable(value = "Id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.view(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xác nhận phiếu cấp phát IP Address")
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> confirm(@RequestBody Map<String, String> input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.confirm(input.get("p_params"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
