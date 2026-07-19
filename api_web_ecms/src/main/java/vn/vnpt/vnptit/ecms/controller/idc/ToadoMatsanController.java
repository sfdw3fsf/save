package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.idc.ToaDoMatsanDto;
import vn.vnpt.vnptit.ecms.service.idc.ToadoMatsanService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/idc/ToadoMatsan")
public class ToadoMatsanController {
    private final ToadoMatsanService _service;

    public ToadoMatsanController(ToadoMatsanService service) {
        this._service = service;
    }

    @Operation(summary = "Liệt kê danh sách IDC")
    @RequestMapping(value = "/DanhSachIDC", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachIDC() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DsIDC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách Tòa nhà")
    @RequestMapping(value = "/DanhSachToaNha", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachToaNha() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DsToaNha());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách Mặt sàn")
    @RequestMapping(value = "/DanhSachMatSan", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachMatSan() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.DsMatsan());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách Tọa độ mặt sàn")
    @RequestMapping(value = "/DanhSachToaDoMatSan", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachToadoMatsan(String name) {
        ApiResult rs = new ApiResult();
        name = name == null ? "" : name;
        try {
            rs.setData(_service.DsToadoMatsan(name));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm Tọa độ mặt sàn")
    @RequestMapping(value = "/Them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ThemToadoMatsan(@Valid @RequestBody ToaDoMatsanDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.ThemToadoMatsan(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật Tọa độ mặt sàn")
    @RequestMapping(value = "/CapNhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatToadoMatsan(@Valid @RequestBody ToaDoMatsanDto input) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> r = _service.ChiTietToadoMatsan(input.getToadoMatsanId());
            if (r == null){
                rs.setMessageDetail("id "+ input.getToadoMatsanId().toString() +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            rs.setData(_service.CapNhatToadoMatsan(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa Tọa độ mặt sàn")
    @RequestMapping(value = "/Xoa/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaToadoMatsan(@PathVariable("id") Integer id) {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> r = _service.ChiTietToadoMatsan(id);
            if (r == null){
                rs.setMessageDetail("id "+ id +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            rs.setData(new ResultOutput(_service.XoaToadoMatsan(id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Import Tọa độ mặt sàn")
    @RequestMapping(value = "/Import", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> importToadoMatsan(@RequestBody List<ToaDoMatsanDto> inputs) {
        ApiResult rs = new ApiResult();
        rs.setData(_service.importToadoMatsan(inputs));
        return rs.getResponseEntity();
    }

    
}




//     @Operation(summary = "Chi tiết Tọa độ mặt sàn")
//     @RequestMapping(value = "/ChiTiet/{id}", method = RequestMethod.GET)
//     public ResponseEntity<ApiResult> ChiTietToadoMatsan(@PathVariable("id") Integer id) {
//         ApiResult rs = new ApiResult();
//         try {
//               Map<String, Object> r = _service.ChiTietToadoMatsan(id);
// //            if (r.size() == 0){
// //                rs.setMessageDetail("id "+ id +" không tồn tại");
// //                return rs.getResponseEntity(Message.Codes.BSS_00009491);
// //            }
//             rs.setData(r);
//         } catch (AppSqlException e) {
//             rs.setException(e);
//         }
//         return rs.getResponseEntity();
//     }

//     @Operation(summary = "Thêm Tọa độ mặt sàn")
//     @RequestMapping(value = "/Them", method = RequestMethod.POST)
//     public ResponseEntity<ApiResult> ThemHangSanXuat(@Valid @RequestBody ThemDanhMuc input) {
//         ApiResult rs = new ApiResult();
// //        if (input.name == null || input.name.isEmpty()) {
// //            rs.setMessageDetail("name không được trống");
// //            return rs.getResponseEntity(Message.Codes.BSS_00003000);
// //        }
//         try {
//             rs.setData(_service.ThemToadoMatsan(input));
//         } catch (AppSqlException e) {
//             rs.setException(e);
//         }
//         return rs.getResponseEntity();
//     }

    // @Operation(summary = "Cập nhật Tọa độ mặt sàn")
    // @RequestMapping(value = "/CapNhat", method = RequestMethod.POST)
    // public ResponseEntity<ApiResult> CapNhatHangSanXuat(@Valid @RequestBody CapNhatDanhMuc input) {
    //     ApiResult rs = new ApiResult();
    //     try {
    //         Map<String, Object> r = _service.ChiTietToadoMatsan(input.getId());
    //         if (r == null){
    //             rs.setMessageDetail("id "+ input.getId().toString() +" không tồn tại");
    //             return rs.getResponseEntity(Message.Codes.BSS_00009491);
    //         }
    //         rs.setData(_service.CapNhatToadoMatsan(input));
    //     } catch (AppSqlException e) {
    //         rs.setException(e);
    //     }
    //     return rs.getResponseEntity();
    // }

    