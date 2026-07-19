package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.ThuHoiIPService;

@RestController
@RequestMapping("/hatang/thu-hoi-ip")
public class ThuHoiIPController {
    @Autowired
    private ThuHoiIPService thuHoiIPService;

    @GetMapping(value = "/get-all")
    public ResponseEntity<ApiResult> danhSachThuHoiMayAo() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thuHoiIPService.getDSThuHoiIP());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/danhsach-yeucau/{id}")
    public ResponseEntity<ApiResult> danhSachYeuCau(@PathVariable(value = "id") Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thuHoiIPService.getDSYeuCauThuHoi(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/hoanthanh-thuhoi-ip")
    public ResponseEntity<ApiResult> hoanThanhThuHoiMayAo(@RequestParam(value = "id") Number id,
                                                          @RequestParam(value = "trangthai_thuhoi") String trangthai_thuhoi) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thuHoiIPService.hoanThanhThuHoiIP(id, trangthai_thuhoi));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}