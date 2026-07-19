package vn.vnpt.vnptit.qlsc.controller.vattu;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.qlsc.dto.cntt.CommentCNTTInput;
import vn.vnpt.vnptit.qlsc.service.vattu.XuLyVatTuService;

import java.util.Map;

@RestController
@RequestMapping(value = "/xuly-vattu")
public class XuLyVTController {
    private static XuLyVatTuService service;
    public XuLyVTController(XuLyVatTuService service) {
        this.service = service;
    }

    @Operation(summary = "Cập nhật yêu cầu vật tư")
    @RequestMapping(value = "/capnhat-yc-vattu", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhatYeucauVattu(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.capnhat_yc_vattu(Long.parseLong(input.get("yeucau_vattu_id")), Long.parseLong(input.get("soluong_yc"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xoá yêu cầu vật tư")
    @RequestMapping(value = "/xoa-yc-vattu", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaYeucauVattu(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.xoa_yc_vattu(Long.parseLong(input.get("yeucau_vattu_id"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
