
package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DonGiaHaTangDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.DonGiaHaTangService;
import javax.validation.Valid;

@RestController
@RequestMapping("danhmuc/don-gia-ha-tang")
public class DonGiaHaTangController {
    private final DonGiaHaTangService service;

    public DonGiaHaTangController(DonGiaHaTangService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách Đơn giá hạ tầng ")
    @GetMapping("/danh-sach")
    public ResponseEntity<ApiResult> danhSachModule() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getData());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật Danh mục  Đơn giá hạ tầng")
    @PostMapping("/cap-nhat")
    public ResponseEntity<ApiResult> capNhatDonGiaHaTang(@Valid @RequestBody DonGiaHaTangDtoIn input) throws AppSqlException {
        return service.capNhatDonGiaHaTang(input);
    }
    @Operation(summary = "Xóa Danh mục  Đơn giá hạ tầng")
    @PostMapping("/xoa")
    public ResponseEntity<ApiResult> xoaDonGiaHaTang(@RequestBody DonGiaHaTangDtoIn input) throws AppSqlException {
        return service.xoaDonGiaHaTang(input);
    }

}
