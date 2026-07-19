package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.vnptit.ecms.model.QuyHoachVLANModel;
import vn.vnpt.vnptit.ecms.service.danhmuc.QuyHoachVLANService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("danhmuc/vlan")
public class QuyHoachVLANController {
    @Autowired
    private QuyHoachVLANService quyHoachVLANService;

    @Operation(summary = "Lấy danh sách loại quy hoạch")
    @GetMapping("loaiquyhoach")
    public ResponseEntity<ApiResult> LayDSLoaiQuyHoach() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quyHoachVLANService.LayDSLoaiQuyHoach());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách vùng quy hoạch")
    @GetMapping("vungquyhoach")
    public ResponseEntity<ApiResult> LayDSVungQuyHoach() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quyHoachVLANService.LayDSVungQuyHoach());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dịch vụ")
    @GetMapping("dichvu")
    public ResponseEntity<ApiResult> LayDSDichVu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quyHoachVLANService.LayDSDichVu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách loại dịch vụ")
    @PostMapping("loaidichvu")
    public ResponseEntity<ApiResult> LayLoaiDSDichVu(@RequestBody QuyHoachVLANModel quyHoachVLANModel) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quyHoachVLANService.LayLoaiDSDichVu(quyHoachVLANModel.getDichvu_id()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách trạng thái")
    @GetMapping("trangthai")
    public ResponseEntity<ApiResult> LayDSTrangThai() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quyHoachVLANService.LayDSTrangThai());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách trạng thái")
    @PostMapping("ds-quyhoach")
    public ResponseEntity<ApiResult> LayDSQuyHoachVLAN(@RequestBody QuyHoachVLANModel quyHoachVLANModel) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quyHoachVLANService.LayDSQuyHoachVLAN(quyHoachVLANModel.getKieu(), quyHoachVLANModel.getVlan()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm quy hoạch vlan")
    @PostMapping("them")
    public ResponseEntity<ApiResult> ThemQuyHoachVLAN(@RequestBody QuyHoachVLANModel quyHoachVLANModel) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quyHoachVLANService.ThemQuyHoachVLAN(quyHoachVLANModel));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhât quy hoạch vlan")
    @PostMapping("capnhat/{id}")
    public ResponseEntity<ApiResult> CapNhatQuyHoachVLAN(@RequestBody QuyHoachVLANModel quyHoachVLANModel, @PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            quyHoachVLANModel.setQuyhoach_id(id);
            rs.setData(quyHoachVLANService.CapNhatQuyHoachVLAN(quyHoachVLANModel));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa quy hoạch vlan")
    @PostMapping("xoa/{id}")
    public ResponseEntity<ApiResult> XoaQuyHoachVLAN(@PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            List<Map<String, Object>> kq = quyHoachVLANService.XoaQuyHoachVLAN(id);
            if(Boolean.parseBoolean(kq.get(0).get("KETQUA").toString()) == false) {
                rs.setMessageDetail("ID quy hoạch VLAN không hợp lệ !");
                rs.setMessage(Message.Codes.BSS_00009491);
            }
            rs.setData(kq);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
