package vn.vnpt.vnptit.qlsc.controller.cntt;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.qlsc.dto.cntt.TraCuuSuCoCNTTRequestDTO;
import vn.vnpt.vnptit.qlsc.model.ThongTinNguoiDung;
import vn.vnpt.vnptit.qlsc.service.cntt.TraCuuSuCoCNTTService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/tracuu-suco-cntt")
public class TraCuuSuCoCNTTController {

    private ThongTinNguoiDung ttnd = new ThongTinNguoiDung();
    private final TraCuuSuCoCNTTService service;

    public TraCuuSuCoCNTTController(TraCuuSuCoCNTTService service) {
        this.service = service;
    }

    @Operation(summary = "Danh mục dịch vụ CNTT")
    @RequestMapping(value = "/danhmuc-dichvu", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> danhMucDichVu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.danhMucDichVu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "danh sách sự cố")
    @RequestMapping(value = "/danhsach-suco", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachSuCo(@Valid @RequestBody TraCuuSuCoCNTTRequestDTO input) {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            ttnd.setNhanVienId(AppRequestContext.getCurrent().getNhanVienID());
            rs.setData(service.danhSachSuCo(input, ttnd.getPhanVungId(), ttnd.getNhanVienId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thông tin chi tiết sự cố", description = "Thông tin chi tiết sự cố")
    @RequestMapping(value = "/suco", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> thongtin_suco(@RequestParam("suco_id") int suco_id,
                                                   @RequestParam("phanvungId") int phanvungId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.chiTietSuCo(suco_id, phanvungId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách cổng bị ảnh hưởng sự cố", description = "Danh sách cổng bị ảnh hưởng sự cố")
    @RequestMapping(value = "/ds-cong-bi-anhhuong", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsCongBiAnhHuong(@RequestParam("suco_id") int suco_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsCongBiAnhHuong(suco_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách nhân viên xử lý", description = "Danh sách nhân viên xử lý")
    @RequestMapping(value = "/ds-nhanvien-xuly", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsNhanVienXuLy(@RequestParam("phieu_id") int phieu_id,@RequestParam("suco_ptm_id") int suco_ptm_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsNhanVienXuLy(phieu_id,suco_ptm_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách báo tồn", description = "Danh sách báo tồn")
    @RequestMapping(value = "/ds-bao-ton", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsBaoTon(@RequestParam("phieu_id") int phieu_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsBaoTon(phieu_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
