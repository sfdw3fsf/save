package vn.vnpt.vnptit.ecms.controller.danhmuc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatDanhMuc;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThemDanhMuc;
import vn.vnpt.vnptit.ecms.service.danhmuc.HangSXService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("danhmuc/HangSX")
public class HangSXController {
    private final HangSXService _service;

    public HangSXController(HangSXService service) {
        this._service = service;
    }

    @Operation(summary = "Liệt kê danh sách hãng sản xuất")
    @RequestMapping(value = "/DanhSach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachHangSanXuat(String name) {
        ApiResult rs = new ApiResult();
        name = name == null ? "" : name;
        try {
            rs.setData(_service.DsHangSX(name));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chi tiết hãng sản xuất")
    @RequestMapping(value = "/ChiTiet/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ChiTietHangSanXuat(@PathVariable("id") Integer id) {
        ApiResult rs = new ApiResult();
        try {
              Map<String, Object> r = _service.ChiTietHangSX(id);
//            if (r.size() == 0){
//                rs.setMessageDetail("id "+ id +" không tồn tại");
//                return rs.getResponseEntity(Message.Codes.BSS_00009491);
//            }
            rs.setData(r);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm hãng sản xuất")
    @RequestMapping(value = "/Them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ThemHangSanXuat(@Valid @RequestBody ThemDanhMuc input) {
        ApiResult rs = new ApiResult();
//        if (input.name == null || input.name.isEmpty()) {
//            rs.setMessageDetail("name không được trống");
//            return rs.getResponseEntity(Message.Codes.BSS_00003000);
//        }
        try {
            rs.setData(_service.ThemHangSX(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật hãng sản xuất")
    @RequestMapping(value = "/CapNhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatHangSanXuat(@Valid @RequestBody CapNhatDanhMuc input) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> r = _service.ChiTietHangSX(input.getId());
            if (r == null){
                rs.setMessageDetail("id "+ input.getId().toString() +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            rs.setData(_service.CapNhatHangSX(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa hãng sản xuất")
    @RequestMapping(value = "/Xoa/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaHangSanXuat(@PathVariable("id") Integer id) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> r = _service.ChiTietHangSX(id);
            if (r == null){
                rs.setMessageDetail("id "+ id +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            rs.setData(new ResultOutput(_service.XoaHangSX(id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
