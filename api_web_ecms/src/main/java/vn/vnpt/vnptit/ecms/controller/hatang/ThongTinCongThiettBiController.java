package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.ThongTinCongThietBiService;
import vn.vnpt.vnptit.ecms.service.hatang.TrangThaiMayAoService;


@RestController
@RequestMapping("/thongtin-thietbi")
public class ThongTinCongThiettBiController {

    private final ThongTinCongThietBiService service;
    public ThongTinCongThiettBiController(ThongTinCongThietBiService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy tất cả thông tin đấu nối thiết bị")
    @RequestMapping(value = "/daunoi/danhsach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getAll() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getAll());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
