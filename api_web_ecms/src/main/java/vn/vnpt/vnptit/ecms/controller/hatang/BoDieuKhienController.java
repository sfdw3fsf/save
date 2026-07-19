package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.BoDieuKhienService;

@RestController
@RequestMapping("ha-tang/bo-dieu-khien")
public class BoDieuKhienController {
    private final BoDieuKhienService service;

    public BoDieuKhienController(BoDieuKhienService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy danh sách bộ điều khiển")
    @GetMapping(value = "")
    public ResponseEntity<ApiResult> getBoDieuKhien(@RequestParam(value = "tb_id") Integer tb_id) {
        ApiResult res = new ApiResult();
        try {
          res.setData(this.service.GetBoDieuKhien(tb_id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
}
