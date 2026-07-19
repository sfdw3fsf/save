package vn.vnpt.vnptit.qlsc.controller;

import io.swagger.v3.oas.annotations.Operation;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.CapNhatHeThongDichVuInput;
import vn.vnpt.vnptit.qlsc.service.DanhMucHeThongThietBiService;
import vn.vnpt.vnptit.qlsc.service.DanhMucService;

import java.io.IOException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/danhmuchethongthietbi")
public class DanhMucHeThongThietBiController {
	private final DanhMucHeThongThietBiService danhMucHeThongThietBiService;

    public DanhMucHeThongThietBiController(DanhMucHeThongThietBiService danhMucHeThongThietBiService) {
        this.danhMucHeThongThietBiService = danhMucHeThongThietBiService;
    }
    
    
    @Operation(summary = "Danh sách hệ thống thiết bị")
    @RequestMapping(value = "/hethong-thietbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachHeThongThietBi(@RequestParam String dviChuQuan) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucHeThongThietBiService.DsHeThongThietBi(dviChuQuan));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Chi tiết hệ thống dịch vụ")
    @RequestMapping(value = "/chitiet-hethong-dichvu", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ChiTietHeThongDichVu(@RequestParam Integer heThongId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucHeThongThietBiService.ChiTietHeThongDichVu(heThongId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Cập nhật hệ thống dịch vụ")
    @RequestMapping(value = "/capnhat-hethong-dichvu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatHeThongDichVu( @RequestBody CapNhatHeThongDichVuInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucHeThongThietBiService.CapNhapHeThongDichVu(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }  catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoá hệ thống dịch vụ")
    @RequestMapping(value = "/xoa-hethong-dichvu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaHeThongDichVu(@RequestParam Integer heThongId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucHeThongThietBiService.XoaHeThongDichVu(heThongId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}