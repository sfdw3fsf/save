package vn.vnpt.vnptit.ecms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.model.XacNhanToiUuModel;
import vn.vnpt.vnptit.ecms.service.ToiUuTaiNguyenAoHoaService;

@RestController
@RequestMapping("/toi-uu-tai-nguyen-ao-hoa")
public class ToiUuTaiNguyenAoHoaController {
	@Autowired
    private ToiUuTaiNguyenAoHoaService service;

    @Operation(summary = "Danh sách Yêu cầu tối ưu")
    @RequestMapping(value = "/ds-yeu-cau-toi-uu", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DsYeuCauToiUu(String thang, Long loaiToiUu, Long trangThaiXacNhan){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.DsYeuCauToiUu(thang, loaiToiUu, trangThaiXacNhan));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Danh sách Máy chủ tối ưu")
    @RequestMapping(value = "/ds-may-chu-toi-uu", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DsMayChuToiUu(String thang, Long loaiToiUu, Long trangThaiXacNhan){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.DsMayChuToiUu(thang, loaiToiUu, trangThaiXacNhan));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Xác nhận tối ưu")
    @RequestMapping(value = "/xac-nhan-toi-uu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XacNhanToiUu(@RequestBody XacNhanToiUuModel input){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.XacNhanToiUu(input.getLoaiXacNhan(), input.getData())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
