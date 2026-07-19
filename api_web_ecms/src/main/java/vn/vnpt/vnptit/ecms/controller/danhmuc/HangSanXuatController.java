package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.HangSanXuatDto;
import vn.vnpt.vnptit.ecms.dto.danhmuc.NhaCungCapDTO;
import vn.vnpt.vnptit.ecms.service.danhmuc.HangSanXuatService;
import vn.vnpt.vnptit.ecms.service.danhmuc.NhaCungCapService;
@RestController
@RequestMapping("danhmuc/hangsanxuat")
public class HangSanXuatController {
    private final HangSanXuatService hangSanXuatService;
    public HangSanXuatController(HangSanXuatService service) {
        this.hangSanXuatService = service;
    }


    @Operation(summary = "Liệt kê danh sách hãng sản xuất")
    @RequestMapping(value = "/danhsach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachHangSanXuat() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hangSanXuatService.dsHangSanXuat());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thêm mới sửa hãng sản xuất")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatHangSanXuat(@RequestBody HangSanXuatDto input) {
        ApiResult rs = new ApiResult();
        try {
            hangSanXuatService.setHangSanXuat(input);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Xoa hãng sản xuất")
    @RequestMapping(value = "/xoa/{Id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaHangSanXuat(@PathVariable(value = "Id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            hangSanXuatService.deleteHangSanXuat(id);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
