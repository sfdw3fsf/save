package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.CapNhatThongTinBTSDtoIn;
import vn.vnpt.vnptit.ecms.dto.DsBTSNodebDtoIn;
import vn.vnpt.vnptit.ecms.dto.DsKhuVucKhoanDiaBanDtoIn;
import vn.vnpt.vnptit.ecms.dto.DsKhuVucKhoanDiaBanXlscDtoIn;
import vn.vnpt.vnptit.ecms.dto.DsNhanVienTheoDonViDtoIn;
import vn.vnpt.vnptit.ecms.service.BTSService;

@RestController
@RequestMapping("/bts")
public class BTSController {

    private BTSService btsService;

    public BTSController(BTSService btsService){
        this.btsService = btsService;
    }

    @Operation(summary = "Danh sach bts nodeb", description = "Lấy danh sách bts nodeb")
    @RequestMapping(value = "/lay-ds-bts-nodeb", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_bts_nodeb(@RequestBody DsBTSNodebDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(btsService.lay_ds_bts_nodeb(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lay thong tin thiet bi bts", description = "Lấy thông tin thiết bị bts")
    @RequestMapping(value = "/lay-tt-thietbi-bts", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_tt_tbi_bts(@RequestBody DsBTSNodebDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(btsService.lay_tt_tbi_bts(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sach bts nodeb", description = "Lấy danh sách bts nodeb")
    @RequestMapping(value = "/lay-danhmuc-bts-nodeb", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_danhmuc_bts_nodeb() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(btsService.lay_danhmuc_bts_nodeb());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cap nhat thong tin bts", description = "Cập nhật thông tin bts")
    @RequestMapping(value = "/capnhat-thongtin-bts", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_thongtin_bts(@RequestBody CapNhatThongTinBTSDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(btsService.capnhat_thongtin_bts(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "lay chi tiet bts enodeb", description = "Lấy chi tiết bts enodeb")
    @RequestMapping(value = "/lay-chitiet-bts", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_chitiet_bts(@RequestBody DsBTSNodebDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(btsService.lay_chitiet_bts(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "lay_ds_khuvuc_khoandiaban", description = "lay_ds_khuvuc_khoandiaban")
    @RequestMapping(value = "/lay_ds_khuvuc_khoandiaban", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_khuvuc_khoandiaban(@RequestBody DsKhuVucKhoanDiaBanDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(btsService.lay_ds_khuvuc_khoandiaban(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "sp_lay_ds_khuvuc_khoandiaban_xlsc", description = "lay_ds_khuvuc_khoandiaban")
    @RequestMapping(value = "/lay_ds_khuvuc_khoandiaban_xlsc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_khuvuc_khoandiaban_xlsc(@RequestBody DsKhuVucKhoanDiaBanXlscDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(btsService.sp_lay_ds_khuvuc_khoandiaban_xlsc(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "SP_LAY_DS_NHANVIEN_THEO_DONVI", description = "lay_ds_khuvuc_khoandiaban")
    @RequestMapping(value = "/lay_ds_nhanvien_theo_donvi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_nhanvien_theo_donvi(@Valid @RequestBody DsNhanVienTheoDonViDtoIn data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(btsService.lay_ds_nhanvien_theo_donvi(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layds_donvi_xlsc", description = "layds_donvi_xlsc")
    @RequestMapping(value = "/layds_donvi_xlsc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layds_donvi_xlsc() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(btsService.layds_donvi_xlsc());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "layds_donvi_quanly_bts", description = "layds_donvi_quanly_bts")
    @RequestMapping(value = "/layds_donvi_quanly_bts", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layds_donvi_quanly_bts() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(btsService.layds_donvi_quanly_bts());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
