package vn.vnpt.vnptit.ecms.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.service.KhoHaTangIDGService;

@RestController
@RequestMapping("/khohatang-idg")
public class KhoHaTangIDGController {
    @Autowired
    private KhoHaTangIDGService service;

    @Operation(summary = "Lấy danh sách")
    @RequestMapping(value = "/get-list-khohatang-idg", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_danhsach(){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getListKhoHaTangIDG());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
	
    @Operation(summary = "Lấy chi tiết hạ tầng IDG theo id")
    @RequestMapping(value = "/get-khohatang-idg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getKhoHaTangIDG(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getKhoHaTangIDG(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "insert and update kho hạ tầng IDG")
    @RequestMapping(value = "/update-idg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateIDG(@RequestBody Map<String, String> input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.updateIpStatus(input.get("p_ds"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xóa IDG")
    @RequestMapping(value = "/delete-idg/{Id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteIDG(@PathVariable(value = "Id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.deleteIDG(id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng ảo hoá theo IDC")
    @RequestMapping(value = "/hatang-aohoa-theo-idc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> hatang_aohoa_theo_idc(@PathVariable(value = "Id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.hatangAohoaTheoIDC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng SmartCloud theo IDC")
    @RequestMapping(value = "/hatang-smartcloud-theo-idc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> hatang_smartcloud_theo_idc(@PathVariable(value = "Id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.hatangSmartCloudTheoIDC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng IDG theo IDC")
    @RequestMapping(value = "/hatang-idg-theo-idc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> hatang_idg_theo_idc(@PathVariable(value = "Id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.hatangIDGTheoIDC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy lịch sử")
    @RequestMapping(value = "/lichsu-hatangidg", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lichsu_hatangidg(@PathVariable(value = "Id") Long id){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lichsuHatangIDG());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
