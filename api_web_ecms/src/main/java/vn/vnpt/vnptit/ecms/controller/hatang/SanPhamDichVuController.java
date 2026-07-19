package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.SanPhamDichVuService;

@RestController
@RequestMapping("hatang/san-pham-dich-vu")
public class SanPhamDichVuController {

    @Autowired
    private SanPhamDichVuService service;

    @Operation(summary = "Lay danh sach san pham dich vu")
    @GetMapping(value = "")
    public ResponseEntity<ApiResult> getSPDV() {
        ApiResult rs = new ApiResult();
        try {
            Object data = service.dsSPDV();
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }

        return rs.getResponseEntity();
    }
}
