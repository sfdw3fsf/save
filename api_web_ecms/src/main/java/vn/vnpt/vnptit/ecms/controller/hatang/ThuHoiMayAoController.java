package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.ThuHoiMayAoService;

@RestController
@RequestMapping("hatang/thu-hoi-mayao")
public class ThuHoiMayAoController {
    @Autowired
    private ThuHoiMayAoService thuHoiMayAoService;

    @GetMapping(value = "/get-all")
    public ResponseEntity<ApiResult> danhSachThuHoiMayAo() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thuHoiMayAoService.getDSThuHoiMayAo());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/danhsach-yeucau/{id}")
    public ResponseEntity<ApiResult> danhSachYeuCau(@PathVariable(value = "id") Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thuHoiMayAoService.getDSYeuCauThuHoi(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/hoanthanh-thuhoi-mayao")
    public ResponseEntity<ApiResult> hoanThanhThuHoiMayAo(@RequestParam(value = "id") Number id,
                                                          @RequestParam(value = "ykien") String ykien) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thuHoiMayAoService.hoanThanhThuHoiMayAo(id, ykien));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/yeucau-thuhoi-ip")
    public ResponseEntity<ApiResult> yeuCauThuHoiIP(@RequestParam(value = "id") Number id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thuHoiMayAoService.yeuCauThuHoiIP(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
