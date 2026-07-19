package vn.vnpt.vnptit.qlsc.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.Response;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.dto.vattu.TimVatTuInput;
import vn.vnpt.vnptit.qlsc.dto.vattu.XacNhanVatTuSuCoInput;
import vn.vnpt.vnptit.qlsc.service.CSKHService;
import vn.vnpt.vnptit.qlsc.service.DanhMucService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/cskh")
public class CSKHController {
    private final CSKHService service;

    public CSKHController(CSKHService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách phiếu")
    @RequestMapping(value = "/ds_phieu_xuly", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsPhieuXuLy(@Valid  @RequestBody PhieuXuLyRequest input) {
        ApiResult rs = new ApiResult();
        ArrayList<Map<String, Object>> ret = new ArrayList<>();
        try {
            int phanvungId = AppRequestContext.getCurrent().getPhanVungID();
            int nhanvienId = AppRequestContext.getCurrent().getNhanVienID();
//            rs.setData(service.dsPhieuXuLy(phanvungId, nhanvienId, input));
//            return rs.getResponseEntity();
            ret = (ArrayList<Map<String, Object>>) service.dsPhieuXuLy(phanvungId, nhanvienId, input);
        } catch (AppSqlException e) {
//            rs.setException(e);
        }
        List<PhieuXuLy> ds = new ArrayList<>();
        for (Map<String, Object> row : ret) {
            ds.add(new PhieuXuLy().convert(row));
        }
        rs.setData(ds);
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách phiếu")
    @RequestMapping(value = "/ds_phieu_nghiemthu_xuly", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsPhieuNghiemThuXuLy(@Valid  @RequestBody LayDSPhieuNghiemThuXLRequest input) {
        ApiResult rs = new ApiResult();
        ArrayList<Map<String, Object>> ret = new ArrayList<>();
        try {
            int phanvungId= AppRequestContext.getCurrent().getPhanVungID();
            int nhanvienId= AppRequestContext.getCurrent().getNhanVienID();
//            rs.setData(service.dsPhieuNghiemThuXuLy(phanvungId, nhanvienId, input));
            ret = (ArrayList<Map<String, Object>>) service.dsPhieuNghiemThuXuLy(phanvungId, nhanvienId, input);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        List<PhieuNghiemThuXL> ds = new ArrayList<>();
        for (Map<String, Object> row : ret) {
            ds.add(new PhieuNghiemThuXL().convert(row));
        }
        rs.setData(ds);
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách thủ tục")
    @RequestMapping(value = "/ds_thutuc_xuly", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsThutucXuly(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvungId = AppRequestContext.getCurrent().getPhanVungID();
            int nhanvienId = AppRequestContext.getCurrent().getNhanVienID();
            rs.setData(service.dsThuTucXuLy(phanvungId, nhanvienId, Long.parseLong(input.get("phieu_id")), Long.parseLong(input.get("suco_id"))));

        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Tìm kiếm vật tư")
    @RequestMapping(value = "/timkiem-vattu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsVattu(@Valid  @RequestBody TimVatTuInput input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvungId = AppRequestContext.getCurrent().getPhanVungID();
            int nhanvienId = AppRequestContext.getCurrent().getNhanVienID();
            rs.setData(service.dsVattu(phanvungId, nhanvienId, input));

        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xác nhận vật tư")
    @RequestMapping(value = "/xacnhan-vattu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xacnhanVattuSuco(@Valid  @RequestBody XacNhanVatTuSuCoInput input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvungId = AppRequestContext.getCurrent().getPhanVungID();
            int nhanvienId = AppRequestContext.getCurrent().getNhanVienID();
            rs.setData(service.xacnhanVattuSuco(phanvungId, nhanvienId, input));

        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
