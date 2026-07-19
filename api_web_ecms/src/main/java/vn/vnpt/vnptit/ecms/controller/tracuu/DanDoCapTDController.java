package vn.vnpt.vnptit.ecms.controller.tracuu;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.DanDoCapTDDtoIn;
import vn.vnpt.vnptit.ecms.service.tracuu.DanDoCapTDService;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("tracuu")
public class DanDoCapTDController {
    private final DanDoCapTDService danDoCapTDService;
    public DanDoCapTDController(DanDoCapTDService danDoCapTDService)
    {
        this.danDoCapTDService = danDoCapTDService;
    }

    @Operation(summary = "Lấy danh sách các loại thiết bị cha")
    @RequestMapping(value = "/truyendan/ds_loaithietbi_cha", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> danhsach_loai_tbi_cha() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danDoCapTDService.danhsach_loai_tbi_cha());
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thiết bị cha")
    @RequestMapping(value = "/truyendan/lay_tbi_cha", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_tbi_cha(@Valid @RequestBody DanDoCapTDDtoIn.ThietBiChaIn input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(danDoCapTDService.lay_tbi_cha(phanvung_id,input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thiết bị con")
    @RequestMapping(value = "/truyendan/ds_tbi_con", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_tbi_con(@Valid @RequestBody DanDoCapTDDtoIn.ThietBiConIn input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(danDoCapTDService.lay_tbi_con(phanvung_id,input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách cổng thiết bị")
    @RequestMapping(value = "/truyendan/ds_cong_tbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ds_cong_tbi(@RequestBody DanDoCapTDDtoIn.CongThietBiIn input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(danDoCapTDService.ds_cong_tbi(phanvung_id,input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin cổng thiết bị")
    @RequestMapping(value = "/truyendan/cong_tbi_theo_portid/{port_id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> load_cong_tbi_theo_portid(@PathVariable int port_id) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(danDoCapTDService.load_cong_tbi_theo_portid(phanvung_id,port_id));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy thông tin cổng thiết bị")
    @RequestMapping(value = "/truyendan/tt_cong_tbi_theo_dk", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> tt_cong_tbi_theo_dk(@RequestBody DanDoCapTDDtoIn.TraCuuDanDo input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(danDoCapTDService.tt_cong_tbi_theo_dk(phanvung_id,input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
  /*  @Operation(summary = "Đổi đầu DownLink thành Up Link")
    @RequestMapping(value = "/truyendan/down2uplink", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> down2uplink(@RequestBody DanDoCapTDDtoIn.TTCongThietBiIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danDoCapTDService.down2uplink(input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Đổi đầu DownLink thành Up Link")
    @RequestMapping(value = "/truyendan/up2downlink", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> up2downlink(@RequestBody DanDoCapTDDtoIn.TTCongThietBiIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danDoCapTDService.up2downlink(input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }*/
    @Operation(summary = "Tìm kiếm thiết bị theo tên")
    @RequestMapping(value = "/truyendan/timkiem_tbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> timkiem_tbi(@RequestParam String name) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(danDoCapTDService.timkiem_tbi(phanvung_id,name));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách trung tâm viễn thông")
    @RequestMapping(value = "/truyendan/ds_ttvt", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ds_ttvt() {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(danDoCapTDService.ds_ttvt(phanvung_id));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách tổ viễn thông")
    @RequestMapping(value = "/truyendan/ds_tovt/{ttvt_id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ds_tovt(@PathVariable int ttvt_id) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(danDoCapTDService.ds_tovt(phanvung_id,ttvt_id));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách trạm thuê bao")
    @RequestMapping(value = "/truyendan/ds_tramvt/{tovt_id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ds_tramvt(@PathVariable int tovt_id) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(danDoCapTDService.ds_tramvt(phanvung_id,tovt_id));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy thông tin dãn đồ cáp truyền dẫn")
    @RequestMapping(value = "/truyendan/dandocaptd", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dandocaptd(@Valid @RequestBody DanDoCapTDDtoIn.TraCuuDanDo input) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(danDoCapTDService.dandocaptd(phanvung_id,input));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
