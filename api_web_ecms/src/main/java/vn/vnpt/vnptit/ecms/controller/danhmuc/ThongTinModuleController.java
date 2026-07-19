package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThongTinModule;
import vn.vnpt.vnptit.ecms.service.danhmuc.ThongTinModuleService;


@RestController
@RequestMapping("danhmuc/thong-tin-module")
public class ThongTinModuleController {
    private final ThongTinModuleService thongTinModuleService;
    public ThongTinModuleController(ThongTinModuleService service) {
        this.thongTinModuleService = service;
    }


    @Operation(summary = "Liệt kê danh sách hãng sản xuất")
    @RequestMapping(value = "/danhsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> danhSachModule() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thongTinModuleService.getDSModule());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thêm mới sửa hãng sản xuất")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatModule(@RequestBody ThongTinModule input) {
        ApiResult rs = new ApiResult();
        try {
            thongTinModuleService.thayDoiThongTinModule(input);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
