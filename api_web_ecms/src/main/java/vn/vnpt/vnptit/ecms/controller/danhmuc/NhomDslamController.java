package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.micrometer.core.lang.Nullable;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.message.ModelValidation;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatDanhMuc;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThemDanhMuc;
import vn.vnpt.vnptit.ecms.service.danhmuc.NhomDslamService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("danhmuc/NhomDslam")
public class NhomDslamController {
    private final NhomDslamService _service;

    public NhomDslamController(NhomDslamService service) {
        this._service = service;
    }

    @Operation(summary = "Liệt kê danh sách nhóm dslam")
    @RequestMapping(value = "/DanhSach", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachNhomDslam(String name) {
        ApiResult rs = new ApiResult();
        try {
            name = name == null ? "" : name;
            rs.setData(_service.DsNhomDslam(name));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chi tiết nhóm dslam")
    @RequestMapping(value = "/ChiTiet/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ChiTietNhomDslam(@PathVariable("id") Integer id) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> r = _service.ChiTietNhomDslam(id.toString());
//            if (r == null){
//                rs.setMessageDetail("id "+ id +" không tồn tại");
//                return rs.getResponseEntity(Message.Codes.BSS_00009491);
//            }
            rs.setData(r);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm nhóm dslam")
    @RequestMapping(value = "/Them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ThemNhomDslam(@Valid @RequestBody ThemDanhMuc input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.ThemNhomDslam(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật nhóm dslam")
    @RequestMapping(value = "/CapNhat", method = RequestMethod.POST )
    public ResponseEntity<ApiResult> CapNhatNhomDslam(@Valid @RequestBody CapNhatDanhMuc input) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> r = _service.ChiTietNhomDslam(input.getId().toString());
            if (r == null){
                rs.setMessageDetail("id "+ input.getId().toString() +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            rs.setData(_service.CapNhatNhomDslam(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhóm dslam")
    @RequestMapping(value = "/Xoa/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaNhomDslam(@PathVariable("id") Integer id) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> r = _service.ChiTietNhomDslam(id.toString());
            if (r == null){
                rs.setMessageDetail("id "+ id +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            rs.setData(new ResultOutput(_service.XoaNhomDslam(id.toString())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
