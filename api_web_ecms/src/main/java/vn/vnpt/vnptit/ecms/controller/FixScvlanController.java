package vn.vnpt.vnptit.ecms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import vn.vnpt.vnptit.ecms.dto.CapNhatFixScvlanDtoIn;
import vn.vnpt.vnptit.ecms.dto.ThemFixScvlanDtoIn;
import vn.vnpt.vnptit.ecms.dto.XoaFixScvlanDtoIn;
import vn.vnpt.vnptit.ecms.service.FixScvlanService;

import java.util.List;
@RestController
@RequestMapping("/fixscvlan")
public class FixScvlanController {
    @Autowired
    FixScvlanService fixScvlanService;
    @Operation(summary = "LAY_DS_PORT_QUYHOACH")
    @RequestMapping(value = "/lay-ds-port-quyhoach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_port_quyhoach(Long dslamId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(fixScvlanService.lay_ds_port_quyhoach(dslamId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "CAPNHAT_DS_PORT_QUYHOACH")
    @RequestMapping(value = "/capnhat-ds-port-quyhoach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_ds_port_quyhoach(@RequestBody List<CapNhatFixScvlanDtoIn> portQuyhoachList){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(fixScvlanService.capnhat_ds_port_quyhoach(portQuyhoachList)));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (JsonProcessingException e) {}
        return rs.getResponseEntity();
    }
    @Operation(summary = "THEM_DS_PORT_QUYHOACH")
    @RequestMapping(value = "/them-ds-port-quyhoach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> them_ds_port_quyhoach(@RequestBody List<ThemFixScvlanDtoIn> portQuyhoachList){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(fixScvlanService.them_ds_port_quyhoach(portQuyhoachList)));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (JsonProcessingException e) {}
        return rs.getResponseEntity();
    }
    @Operation(summary = "XOA_DS_PORT_QUYHOACH")
    @RequestMapping(value = "/xoa-ds-port-quyhoach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa_ds_port_quyhoach(@RequestBody List<XoaFixScvlanDtoIn> portQuyhoachList){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(fixScvlanService.xoa_ds_port_quyhoach(portQuyhoachList)));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (JsonProcessingException e) {}
        return rs.getResponseEntity();
    }
    @Operation(summary = "LẤY DANH SÁCH QUY HOẠCH")
    @RequestMapping(value = "/dm-quyhoach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dm_quyhoach(Long dslamId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(fixScvlanService.dm_quyhoach(dslamId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
