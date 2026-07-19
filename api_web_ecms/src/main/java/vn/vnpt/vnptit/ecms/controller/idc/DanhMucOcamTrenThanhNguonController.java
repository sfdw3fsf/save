package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.OcamTrenThanhNguonDto;
import vn.vnpt.vnptit.ecms.service.idc.OcamTrenThanhNguonService;

@RestController
@RequestMapping("/idc/ocam-tren-thanhnguon")
public class DanhMucOcamTrenThanhNguonController {
    
    private final OcamTrenThanhNguonService ocamTrenThanhNguonService;

    public DanhMucOcamTrenThanhNguonController(OcamTrenThanhNguonService ocamTrenThanhNguonService) {
        this.ocamTrenThanhNguonService = ocamTrenThanhNguonService;
    }

    @Operation(summary = "Lấy danh sách ổ cắm trên thanh nguồn")
    @GetMapping("")
    public ResponseEntity<ApiResult> getListOcamTrenThanhNguon() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ocamTrenThanhNguonService.getListOcamTrenThanhNguon());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật ổ cắm trên thanh nguồn")
    @PostMapping("")
    public ResponseEntity<ApiResult> updateOcamTrenThanhNguon(@RequestBody OcamTrenThanhNguonDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(ocamTrenThanhNguonService.updateOcamTrenThanhNguon(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
