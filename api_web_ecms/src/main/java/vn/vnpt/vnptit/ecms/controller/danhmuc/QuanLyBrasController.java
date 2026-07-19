package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import oracle.sql.NUMBER;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.BrasDtoInput;
import vn.vnpt.vnptit.ecms.service.danhmuc.QuanLyBrasService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@RestController
@RequestMapping("/danhmuc/quanlybras/")
public class QuanLyBrasController {

    private final QuanLyBrasService quanLyBrasService;

    public QuanLyBrasController(QuanLyBrasService quanLyBrasService) {
        this.quanLyBrasService = quanLyBrasService;
    }

    @Operation(summary = "Lấy thông tin chi tiết Bras theo key", description = "Lấy thông tin chi tiết Bras theo key")
    @RequestMapping(value = "/lay_theokey", method = RequestMethod.GET)
        public ResponseEntity<ApiResult> layChitietBrasTheoKey(@RequestParam(value = "id")  BigDecimal id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyBrasService.layBrasTheoKey(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Bras", description = "Lấy danh sách Bras")
    @RequestMapping(value = "/danhsach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layChitietBrasTheoKey() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyBrasService.layBras());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới bras", description = "Thêm mới bras")
    @RequestMapping(value = "/them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themBras(@Valid @RequestBody BrasDtoInput brasDtoInput) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyBrasService.taoBras(brasDtoInput));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật bras", description = "Cập nhật bras")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhatBras(@Valid @RequestBody BrasDtoInput brasDtoInput) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(quanLyBrasService.capNhatBras(brasDtoInput)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa thông tin bras", description = "Xóa thông tin bras")
    @RequestMapping(value = "/xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaBras(@RequestParam("bras_id") long brasId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(quanLyBrasService.xoaBras(brasId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Bras theo tủ rack", description = "Lấy danh sách Bras theo tủ rack")
    @RequestMapping(value = "/danhsach/{turack_id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layChitietBrasTheoTuRack(@PathVariable("turack_id") int turack_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLyBrasService.layBrasTheoTuRack(turack_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
