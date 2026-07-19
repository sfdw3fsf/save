package vn.vnpt.vnptit.qlsc.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.qlsc.service.DanhMucHopDongDauTuService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("danhmuc-hopdong-dautu")
public class DanhMucHopDongDauTuController {
    private final DanhMucHopDongDauTuService danhMucHopDongDauTuService;
    public DanhMucHopDongDauTuController(DanhMucHopDongDauTuService danhMucHopDongDauTuService) {
        this.danhMucHopDongDauTuService = danhMucHopDongDauTuService;
    }

    @Operation(summary = "Danh mục hợp đồng")
    @RequestMapping(value = "/danhmuc-hopdong", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhMucHopDong(@RequestParam("chuoi") String chuoi) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucHopDongDauTuService.danhMucHopDong(chuoi));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách hợp đồng đầu tư")
    @RequestMapping(value = "/danhsach-hopdong-dautu", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachHopDongDauTu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucHopDongDauTuService.dsHopDongDauTu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
