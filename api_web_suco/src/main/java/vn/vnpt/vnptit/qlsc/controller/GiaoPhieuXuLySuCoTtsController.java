package vn.vnpt.vnptit.qlsc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.service.GiaoPhieuXuLySuCoTtsService;

@RestController
@RequestMapping("/giao-phieu-xu-ly-su-co-tts")
public class GiaoPhieuXuLySuCoTtsController {

    private final GiaoPhieuXuLySuCoTtsService service;
    private final DbContext dbContext;

    public GiaoPhieuXuLySuCoTtsController(GiaoPhieuXuLySuCoTtsService service, DbContext dbContext) {
        this.service = service;
        this.dbContext = dbContext;
    }

    @Operation(summary = "Lấy danh sách phiếu xử lý NET")
    @RequestMapping(value = "/lay_ds_phieu_xuly_net", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_phieu_xuly_net(@RequestBody LayDsPhieuXuLyNetDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsPhieuXuLyNet(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách quy trình bước xử lý")
    @RequestMapping(value = "/lay_ds_qt_buoc_xuly", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_qt_buoc_xuly(@RequestBody LayDsQtBuocXuLyDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsQtBuocXuLy(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }



    @Operation(summary = "Lấy danh sách quy trình bước xử lý")
    @RequestMapping(value = "/lay_ds_qt_buoc_xuly_v2", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_qt_buoc_xuly_v2(@RequestBody LayDsQtBuocXuLyDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsQtBuocXuLyV2(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách phiếu đã giao")
    @RequestMapping(value = "/lay_ds_phieu_dagiao", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_phieu_dagiao(@RequestParam("suco_id") Long suco_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_ds_phieu_dagiao(suco_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị theo bước xử lý")
    @RequestMapping(value = "/lay_donvi_theo_buoc_xuly", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_donvi_theo_buoc_xuly(@RequestBody LayDsQtBuocXuLyDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lay_donvi_theo_buoc_xuly(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

//    @Operation(summary = "Giao phiếu xử lý NET")
//    @RequestMapping(value = "/giaophieu_xuly_net", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giaophieu_xuly_net(@RequestBody GiaoPhieuXuLyNetDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.giaophieu_xuly_net(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Giao phiếu xử lý NET")
    @RequestMapping(value = "/giaophieu_xuly_net", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giaophieu_xuly_net_v2(@RequestBody GiaoPhieuXuLyNetDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.giaophieu_xuly_net_v2(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Giao phiếu xử lý NET v3 (C3_CLM_03, có phuongan_dexuat)")
    @RequestMapping(value = "/giaophieu_xuly_net_v3", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giaophieu_xuly_net_v3(@RequestBody GiaoPhieuXuLyNetV3Dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.giaophieu_xuly_net_v3(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Giao phiếu xử lý NET v4 (giao phiếu + đẩy thông tin qua app-com)")
    @RequestMapping(value = "/giaophieu_xuly_net_v4", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giaophieu_xuly_net_v4(@RequestBody GiaoPhieuXuLyNetV3Dto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.giaophieu_xuly_net_v4(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy nội dung để gửi tới hệ thống tts")
    @RequestMapping(value = "/get_json_dhsx_send_to_tts", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> get_json_dhsx_send_to_tts(@RequestParam("css_id") Long css_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.get_json_dhsx_send_to_tts(css_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật API NET problems")
    @RequestMapping(value = "/capnhat_api_net_problems", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_api_net_problems(@RequestBody CapNhatApiNetProblemsDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.capnhat_api_net_problems(input.getCss_id()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách bước xử lý có thể kế tiếp theo quy trình từ mã phiếu")
    @RequestMapping(value = "/lay_ds_qt_buoc_xuly_theo_maphieu", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_qt_buoc_xuly_theo_maphieu(@RequestBody LayDsQtBuocXuLyTheoMaPhieuDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsQtBuocXuLyTheoMaPhieu(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}