package vn.vnpt.vnptit.ecms.controller.tracuu;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.*;
import vn.vnpt.vnptit.ecms.service.tracuu.ThongTinMDFService;

import javax.validation.Valid;

@RestController
@RequestMapping("/tracuu/mdf")
public class ThongTinMDFController {
    ThongTinMDFService thongTinMDFService;
    public  ThongTinMDFController(ThongTinMDFService thongTinMDFService){
        this.thongTinMDFService = thongTinMDFService;
    }

    @Operation(summary = "Tra cứu MDF theo số máy/Acc")
    @RequestMapping(value = "/danhsachmdfacc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> danhsachtheoacc(@Valid @RequestBody TracuuMDFAccDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinMDFService.TraCuuMDFTheoSoTB(obj));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tra cứu thuê bao theo phiến dọc")
    @RequestMapping(value = "/danhsachmdfphiendoc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> danhsachmdfphiendoc(@RequestBody TracuuMDFPhienDocDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinMDFService.TraCuuMDFTheoPhienDoc(obj));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy thông tin cap theo mã tb")
    @RequestMapping(value = "/lay_tt_cap_theo_matb", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> Lay_tt_cap_theo_matb(@RequestBody LayTTCapTheoMaTBDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinMDFService.Lay_tt_cap_theo_matb(obj));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin giản đồ theo đấu nối")
    @RequestMapping(value = "/lay_giando_theodaunoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> Lay_giando_theodaunoi(@RequestBody LayGianDoTheoDauNoiDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinMDFService.Lay_giando_theodaunoi(obj));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin tổng đài theo cáp")
    @RequestMapping(value = "/lay_tt_tongdai_theo_cap", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> Lay_tt_tongdai_theo_cap(@RequestBody LayThongTinTongDaiTheoCapDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinMDFService.Lay_tt_tongdai_theo_cap(obj));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách cbo tổng đài")
    @RequestMapping(value = "/lay_ds_cbo_tongdai", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_cbo_tongdai(@RequestParam String loaidv_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinMDFService.Lay_ds_cbo_tongdai(loaidv_id));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
