package vn.vnpt.vnptit.ecms.controller.hatang;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.hatang.KhoHaTangMangThietBiDTO;
import vn.vnpt.vnptit.ecms.service.hatang.HaTangMangService;

@RestController
@RequestMapping("hatang/thietbi-khohatang-mang")
public class ThietBiHaTangMangController {
    private final HaTangMangService service;

    public ThietBiHaTangMangController(HaTangMangService service) {
        this.service = service;
    }

    @Operation(summary = "Thêm thiết bị hạ tầng IDG")
    @PostMapping(value = "/add-thiet-bi")
    public ResponseEntity<ApiResult> addThietBi(@RequestBody KhoHaTangMangThietBiDTO input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.haTangMangThietBiThem(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị hạ tầng IDG")
    @RequestMapping(value = "/thiet-bi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getThietBi(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.haTangMangThietBi(input.get("p_hatang_id")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoá thiết bị hạ tầng IDG")
    @PostMapping(value = "/delete-thiet-bi")
    public ResponseEntity<ApiResult> deleteThietBi(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.deleteThietBiHaTangMang(input.get("p_id"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
