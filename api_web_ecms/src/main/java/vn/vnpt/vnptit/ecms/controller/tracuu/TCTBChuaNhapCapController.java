package vn.vnpt.vnptit.ecms.controller.tracuu;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.CapGocDtoIn;
import vn.vnpt.vnptit.ecms.dto.LichSuTacDongCapTheoMaTB;
import vn.vnpt.vnptit.ecms.service.tracuu.TCTBChuaNhapCapService;

@RestController
@RequestMapping("tracuu")
public class TCTBChuaNhapCapController {
    private final TCTBChuaNhapCapService tctbChuaNhapCapService;

    public TCTBChuaNhapCapController(TCTBChuaNhapCapService tctbChuaNhapCapService)
    {
        this.tctbChuaNhapCapService = tctbChuaNhapCapService;
    }

    @Operation(summary = "Liệt kê danh sách thuê bao chưa nhập cáp")
    @RequestMapping(value = "/loadTreeView", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> loadTreeView() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tctbChuaNhapCapService.loadTreeView());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy lịch sử tác động cáp theo mã thuê bao", description = "Lấy lịch sử tác động cáp theo mã thuê bao")
    @RequestMapping(value = "/lichSuTacDongCap", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> suaHuongKetNoiBras(@RequestBody LichSuTacDongCapTheoMaTB lichSuTacDongCapTheoMaTB) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tctbChuaNhapCapService.lay_lichsu_tacdong_cap_theo_ma_tb(lichSuTacDongCapTheoMaTB));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy lịch sử tác động cáp theo mã thuê bao", description = "Lấy lịch sử tác động cáp theo mã thuê bao")
    @RequestMapping(value = "/dsThueBaoChuaNhapCap", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsThueBaoChuaNhapCap() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tctbChuaNhapCapService.dsThueBaoChuaNhapCap());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách cáp gốc", description = "Lấy danh sách cáp gốc")
    @RequestMapping(value = "/layDsCapGoc/{capGocId}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsCapGoc(@PathVariable int capGocId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tctbChuaNhapCapService.layDsCapGoc(capGocId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới cáp gốc", description = "Thêm mới cáp gốc")
    @RequestMapping(value = "/themCapGoc", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themCapGoc(@RequestBody CapGocDtoIn capGocDtoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tctbChuaNhapCapService.themCapGoc(capGocDtoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật cáp gốc", description = "Cập nhật cáp gốc")
    @RequestMapping(value = "/capNhatCapGoc", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatCapGoc(@RequestBody CapGocDtoIn capGocDtoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tctbChuaNhapCapService.capNhatCapGoc(capGocDtoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa cáp gốc", description = "Xóa cáp gốc")
    @RequestMapping(value = "/xoaCapGoc/{capGocId}", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaCapGoc(@PathVariable int capGocId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(tctbChuaNhapCapService.xoaCapGoc(capGocId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
