package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.NhaCungCapDTO;
import vn.vnpt.vnptit.ecms.service.danhmuc.NhaCungCapService;

@RestController
@RequestMapping("danhmuc/nhacungcap")
public class NhaCungCapController {
    private final NhaCungCapService nhaCungCapService;
    public NhaCungCapController(NhaCungCapService service) {
        this.nhaCungCapService = service;
    }


    @Operation(summary = "Liệt kê danh sách nhà cung cấp")
    @RequestMapping(value = "/danhsach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachNhaCungCap() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nhaCungCapService.dsNhaCungCap());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thêm mới sửa nhà cung cấp")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatNhaCungCap(@RequestBody NhaCungCapDTO input) {
        ApiResult rs = new ApiResult();
        try {
            nhaCungCapService.setNhaCungCap(input);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Xoa nhà cung cấp")
    @RequestMapping(value = "/xoa/{Id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaNhaCungCap(@PathVariable(value = "Id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            nhaCungCapService.deleteNhaCungCap(id);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
