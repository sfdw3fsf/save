package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.service.idc.DaiIPService;

import vn.vnpt.vnptit.ecms.dto.idc.DaiIPDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/idc/daiIP")
public class DaiIPController {

    @Autowired
    DaiIPService daiIPService;

    @Operation(summary = "Lấy Trạng thái dải IP")
    @RequestMapping(value = "/lay-ds-trangthai", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_trangthai() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.layDsTrangThai());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Subnet Mask")
    @RequestMapping(value = "/lay-ds-subnetmask", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_subnetmask() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.layDsSubnetMark());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Loại IP")
    @RequestMapping(value = "/lay-ds-loai-IP", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_dsLoaiIP() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.lay_dsLoaiIP());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Đơn vị IDC")
    @RequestMapping(value = "/lay-ds-idc-donviId", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_dsIdc_DonviId(@RequestBody Integer donviId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.lay_dsIdc_DonviId(donviId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Đơn vị IDC")
    @RequestMapping(value = "/lay-ds-donvi-idc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_dsdonviIdc() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.lay_dsdonviIdc());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dải IP")
    @RequestMapping(value = "/lay-ds-daiIP-DonviId", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_dsDaiIP(@RequestBody Integer donviId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.laydsDaiIP_DonviId(donviId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dải IP cha")
    @RequestMapping(value = "/lay-ds-daiIP-cha", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_dsDaiIPCha() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.laydsDaiIPCha());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dải IP")
    @RequestMapping(value = "/lay-ds-daiIP", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_danhsach() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.layDsDaiIP());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật Dải IP")
    @PostMapping("/update-daiIP")
    public ResponseEntity<ApiResult> updateDaiIP(@RequestBody DaiIPDto daiIPDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.daiIPService.updateDaiIP(daiIPDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xóa Dải IP")
    @RequestMapping(value = "/delete-daiIP", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteDaiIP(@Valid @RequestBody DaiIPDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.daiIPService.deleteDaiIP(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dải IP con")
    @RequestMapping(value = "/lay-ds-daiIP-con", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_danhsach_con(@Valid @RequestBody DaiIPDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.layDsDaiIP_con(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy Chi tiết dải IP")
    @RequestMapping(value = "/lay-chitiet-daiIP", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_chitiet_DaiIP(@Valid @RequestBody DaiIPDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.lay_chitiet_DaiIP(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm dải IP")
    @RequestMapping(value = "/search-daiIP", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> search_DaiIP(@RequestBody DaiIPDto daiIPDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.searchDaiIP(daiIPDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy Lịch sử tác động")
    @RequestMapping(value = "/lay-lichsu-tacdong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_lichsu_tacdong(@Valid @RequestBody DaiIPDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(daiIPService.laylichsu_tacdong(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
