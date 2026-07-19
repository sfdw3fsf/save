package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.ThiCongThietBiDto;
import vn.vnpt.vnptit.ecms.service.idc.ThiCongThietBiService;

@RestController
@RequestMapping("thicong-tbi")
public class ThiCongThietBiController {

    private final ThiCongThietBiService service;

    public ThiCongThietBiController(ThiCongThietBiService service) {
        this.service = service;
    }

    @PostMapping("/thongtin-phieu")
    @Operation(summary = "Lấy thông tin phiếu thi công thiết bị")
    public ResponseEntity<ApiResult> getThongTinPhieu(@RequestBody ThiCongThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getThongTinPhieu(dto));
        } catch (Exception e) {
            rs.setError(e.getMessage());
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/luu-phieu")
    @Operation(summary = "Lưu phiếu thi công thiết bị")
    public ResponseEntity<ApiResult> luuPhieu(@RequestBody ThiCongThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.luuPhieu(dto));
        } catch (Exception e) {
            rs.setError(e.getMessage());
        }
        return rs.getResponseEntity();
    }
}
