
package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DanhMucDtoIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.QuyHoachSubnetService;

@RestController
@RequestMapping("danhmuc/quy-hoach-subnet")
public class QuyHoachSubnetController {
    private final QuyHoachSubnetService service;

    public QuyHoachSubnetController(QuyHoachSubnetService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách Subnet qui hoạch")
    @GetMapping("/danh-sach")
    public ResponseEntity<ApiResult> danhSachQuyHoachSubnet() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getData());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật Danh mục Subnet qui hoạch")
    @PostMapping("/cap-nhat")
    public ResponseEntity<ApiResult> capNhatQuyHoachSubnet(@RequestBody DanhMucDtoIn input) throws AppSqlException {
        return service.capNhatQuyHoachSubnet(input);
    }
    @Operation(summary = "Xóa Danh mục Subnet qui hoạch")
    @PostMapping("/xoa")
    public ResponseEntity<ApiResult> xoaQuyHoachSubnet(@RequestBody DanhMucDtoIn input) throws AppSqlException {
        return service.xoaQuyHoachSubnet(input);
    }

}
