package vn.vnpt.vnptit.qlsc.controller.cntt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.qlsc.dto.BaseMap;
import vn.vnpt.vnptit.qlsc.dto.LayDsTinhTrangXuLyDto;
import vn.vnpt.vnptit.qlsc.dto.cntt.DanhhMucCnttDto;
import vn.vnpt.vnptit.qlsc.dto.cntt.LayDsNvTheoDvDto;
import vn.vnpt.vnptit.qlsc.service.cntt.DanhMucCNTTService;

@RestController
@RequestMapping(value = "/danhmuc-cntt")
public class DanhMucCNTTController {

    private final DanhMucCNTTService service;

    public DanhMucCNTTController(DanhMucCNTTService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách trạng thái sự cố")
    @RequestMapping(value = "/trangthai-suco", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachTrangThaiSuCo() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsTrangThaiSuCo());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách dịch vụ CNTT")
    @RequestMapping(value = "/dichvu-cntt", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachDichVuCntt() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsDichVuCntt());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách loại hình theo dịch vụ")
    @RequestMapping(value = "/loaihinh-dichvu-cntt", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachLoaiHinhTheoDichVu(@RequestBody DataKeyInput dichVuId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsLoaiHinhTheoDichVu(dichVuId.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách nguồn sự cố")
    @RequestMapping(value = "/nguon-suco", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachNguonSuCo() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsNguonSuCo());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách nguồn sự cố")
    @RequestMapping(value = "/nguon-suco", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachNguonSuCoGet() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsNguonSuCo());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách mức độ khẩn cấp", description = "Danh sách mức độ khẩn cấp")
    @RequestMapping(value = "/mucdo-khancap", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsMucDoKhanCap() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsMucDoKhanCap());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách mức độ sự cố", description = "Danh sách mức độ sự cố")
    @RequestMapping(value = "/mucdo-suco", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsMucDoSuCo() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsMucDoSuCo());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách mức độ sự cố", description = "Danh sách mức độ sự cố")
    @RequestMapping(value = "/mucdo-suco", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsMucDoSuCoGet() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsMucDoSuCo());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách quy trình")
    @RequestMapping(value = "/ds-quytrinh", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsQuyTrinh() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsQuyTrinh());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thao tác giao phiếu")
    @RequestMapping(value = "/ds-thaotac-giaophieu", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsThaoTacGiaoPhieu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsThaoTacGiaoPhieu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hướng giao")
    @RequestMapping(value = "/ds-huong-giao", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsHuongGiao(@RequestBody DanhhMucCnttDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsHuongGiao(input.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị giao")
    @RequestMapping(value = "/ds-donvi-giao", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsDonViGiao(@RequestBody DanhhMucCnttDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsDonViGiao(input.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách tình trạng xử lý")
    @RequestMapping(value = "/ds-tinhtrang-xuly", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsTinhTrangXuLy(@RequestBody LayDsTinhTrangXuLyDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsTinhTrangXuLy(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách nhân viên theo đơn vị")
    @RequestMapping(value = "/ds-nv-dv", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsNvXuLyTheoDv(@RequestBody LayDsNvTheoDvDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsNvXuLyTheoDv(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhóm sự cố theo nhân viên")
    @RequestMapping(value = "/getDsNhomSuCoTheoNhanVien", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsNhomSuCoTheoNhanVien() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDsNhomSuCo(AppRequestContext.getCurrent().getNhanVienID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/dsQuyMoSuCo", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsQuyMoSuCoCNTT() {
        ApiResult rs = new ApiResult();
        rs.setData(service.dsQuyMoSuCoCNTT());
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/dsDonViGiaoViec", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsDonViGiaoViec(@RequestBody BaseMap<String, Object> params) throws AppSqlException {
        ApiResult rs = new ApiResult();
        rs.setData(service.getTiepNhanDonViGiaoViec(
                params.getLong("pPhanVungId"),
                params.getLong("pSuCoId")
        ));
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/dsNhanVienTheoDSDonVi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsNhanVienTheoDSDonVi(@RequestBody BaseMap<String, Object> params) throws AppSqlException {
        ApiResult rs = new ApiResult();
        params.valid()
                .isEmpty("pDSDonVi", "Danh sách đơn vị input không được để trống")
                .isEmpty("pPhanVungId", "Thông tin phân vùng là bắt buộc!")
                .isEmpty("pSuCoId", "Thông tin sự cố ID là bắt buộc!")
                .complete();
        rs.setData(service.dsNhanVienTheoDSDonVi(
                params.getString("pDSDonVi"),
                params.getInt("pPhanVungId"),
                params.getLong("pSuCoId")
        ));
        return rs.getResponseEntity();
    }

}
