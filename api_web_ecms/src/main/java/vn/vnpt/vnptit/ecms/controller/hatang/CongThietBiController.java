package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.HangSanXuatDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CongThietBiDTO;
import vn.vnpt.vnptit.ecms.service.hatang.CongThietBiService;

import javax.validation.Valid;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("hatang/cong-thietbi")
public class CongThietBiController {
    private final CongThietBiService congThietBiService;

    public CongThietBiController(CongThietBiService congThietBiService) {
        this.congThietBiService=congThietBiService;
    }


    @Operation(summary = "Lấy thông tin cổng")
    @RequestMapping(value = "/cong", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsThongTinCong(@RequestParam String pTen) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.congThietBiService.dsThongTinCong(pTen));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy thông tin cổng theo id")
    @RequestMapping(value = "/cong/{Id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsThongTinCongId(@PathVariable Long Id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.congThietBiService.dsThongTinCongId(Id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy thông tin cổng")
    @RequestMapping(value = "/thongtin-daunoi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsThongTinKetNoiCong(@RequestParam String pTen) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.congThietBiService.dsThongTinKetNoiCong(pTen));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin cổng")
    @RequestMapping(value = "/chi-tiet/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getCongThietBiByID(@PathVariable("id") int id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.congThietBiService.getCongThietBiByID(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin thiết bị nối cổng")
    @RequestMapping(value = "/thiet-bi-noi-cong/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getThietBiNoiCong(@PathVariable("id") Number id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.congThietBiService.getThietBiNoiCongByIDCong(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin lịch sử cổng")
    @RequestMapping(value = "/lich-su/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getLichSuCong(@PathVariable("id") Number id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.congThietBiService.getLichSuByIDCong(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Update thông tin cổng")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getLichSuCong(@Valid @RequestBody CongThietBiDTO dto) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.congThietBiService.updateCongThietBi(dto));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
}
