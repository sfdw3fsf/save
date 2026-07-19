package vn.vnpt.vnptit.qlsc.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.BaseMap;
import vn.vnpt.vnptit.qlsc.dto.CayThietBiInput;
import vn.vnpt.vnptit.qlsc.dto.KiemTraDieuKienTiepNhanDto;
import vn.vnpt.vnptit.qlsc.dto.LayThongTinThietBiDto;
import vn.vnpt.vnptit.qlsc.service.DanhMucService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/danhmuc")
public class DanhMucController {

    private final DanhMucService danhMucService;

    public DanhMucController(DanhMucService danhMucService) {
        this.danhMucService = danhMucService;
    }

    @Operation(summary = "Danh sách nguồn sự cố")
    @RequestMapping(value = "/nguon-suco", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachNguonSuCo() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.DsNguonSuCo());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách trạng thái sự cố")
    @RequestMapping(value = "/trangthai-suco", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachTrangThaiSuCo() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.DsTrangThaiSuCo());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách đơn vị theo nhân viên")
    @RequestMapping(value = "/donvi-theo-nhanvien", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> danhSachDonViTheoNV() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.danhSachDonViTheoNV());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách nhóm sự cố", description = "Danh sách nhóm sự cố")
    @RequestMapping(value = "/nhom-suco", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ds_nhom_suco() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.ds_nhom_suco());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách nhóm sự cố tiếp nhận")
    @RequestMapping(value = "/nhom-suco-tiepnhan", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ds_nhom_suco_tiepnhan() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.ds_nhom_suco_tiepnhan());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách nhân viên báo hỏng", description = "Danh sách nhân viên báo hỏng")
    @RequestMapping(value = "/nhanvien-baohong", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsNhanVienBaoHong(@RequestParam String tenNv) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.dsNhanVienBaoHong(tenNv));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách mức độ sự cố", description = "Danh sách mức độ sự cố")
    @RequestMapping(value = "/mucdo-suco", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsMucDoSuCo() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.dsMucDoSuCo());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách mức độ khẩn cấp", description = "Danh sách mức độ khẩn cấp")
    @RequestMapping(value = "/mucdo-khancap", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsMucDoKhanCap() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.dsMucDoKhanCap());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách tình trạng giao việc", description = "Danh sách tình trạng giao việc")
    @RequestMapping(value = "/tinhtrang-giaoviec", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsTinhTrangGiaoViec() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.dsTinhTrangGiaoViec());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách loại truyền dẫn", description = "Lấy danh sách loại truyền dẫn")
    @RequestMapping(value = "/ds-loaitruyendan", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLoaiTruyenDan() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.dsLoaiTruyenDan());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách truyền dẫn", description = "Lấy danh sách truyền dẫn")
    @RequestMapping(value = "/ds-truyendan", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsTruyenDan(Long loaiTuyenTdId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.dsTruyenDan(loaiTuyenTdId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách loại đối tượng", description = "Lấy danh sách loại đối tượng")
    @RequestMapping(value = "/ds-loaidoituong", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsLoaiDoiTuong(Long loaiTuyenTdId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.dsLoaiDoiTuong(loaiTuyenTdId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách đối tượng", description = "Lấy danh sách đối tượng")
    @RequestMapping(value = "/ds-doituong", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsDoiTuong(Long tuyenTdId, Long loaDtId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.dsDoiTuong(tuyenTdId, loaDtId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy cây thiết bị")
    @RequestMapping(value = "/cay-thietbi", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> cayThietBi(@RequestBody @Valid CayThietBiInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.cayThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách lý do trả phiếu")
    @RequestMapping(value = "/ds-lydo-traphieu", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsLyDoTraPhieu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsLyDoTraPhieu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhóm tồn")
    @RequestMapping(value = "/ds-nhomton", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsNhomTon(@RequestParam("nhomsc_id") Integer nhomScId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsNhomTon(nhomScId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nguyên nhân tồn")
    @RequestMapping(value = "/ds-nguyennhan-ton", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsNguyenNhanTon(@RequestParam("nhomton_id") Integer nhomTonId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsNguyenNhanTon(nhomTonId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhân viên theo đơn vị của người đăng nhập")
    @RequestMapping(value = "/ds-nhanvien-donvi-login", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsNhanVienCuaDonViLogin() {
        Integer donViLogin = AppRequestContext.getCurrent().getDonViID();
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsNhanVienCuaDonViLogin(donViLogin));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách quy trình")
    @RequestMapping(value = "/ds-quytrinh", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsQuyTrinh() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsQuyTrinh());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hướng giao")
    @RequestMapping(value = "/ds-huong-giao", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsHuongGiao(@RequestParam("quytrinh_id") Integer quyTrinhId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsHuongGiao(quyTrinhId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị giao")
    @RequestMapping(value = "/ds-donvi-giao", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsDonViGiao(@RequestParam("huonggiao_id") Integer huongGiaoId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsDonViGiao(huongGiaoId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thao tác giao phiếu")
    @RequestMapping(value = "/ds-thaotac-giaophieu", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsThaoTacGiaoPhieu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsThaoTacGiaoPhieu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách kiểu thiết bị")
    @RequestMapping(value = "/ds-kieu-tbi", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsKieuTbi(@RequestParam("loaitbi_id") Integer loaiTbiId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsKieuTbi(loaiTbiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dung lượng thiết bị")
    @RequestMapping(value = "/ds-dungluong-tbi", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsDungLuongTbi(@RequestParam("loaitbi_id") Integer loaiTbiId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsDungLuongTbi(loaiTbiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị dữ liệu của nhân viên")
    @RequestMapping(value = "/ds-donvi-dulieu", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsDonViDuLieu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsDonViDuLieu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhóm nguyên nhân sự cố")
    @RequestMapping(value = "/ds-nhom-nn", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsNhomNguyeNhan() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsNhomNguyeNhan());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nguyên nhân sự cố")
    @RequestMapping(value = "/ds-nguyen-nhan", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsNguyeNhan(@RequestParam("nhomnn_id") Integer nhomNnId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsNguyeNhan(nhomNnId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin thiết bị")
    @RequestMapping(value = "/thongtin-thietbi", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layThongTinThietBi(@RequestBody LayThongTinThietBiDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layThongTinThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hệ thống CNTT")
    @RequestMapping(value = "/ds-hethong-cntt", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsHeThongCntt() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsHeThongCntt());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách tình trạng sự cố CNTT")
    @RequestMapping(value = "/ds-tinhtrang-sc-cntt", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsTinhTrangScCntt() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsTinhTrangScCntt());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách tình trạng sự cố MVT")
    @RequestMapping(value = "/ds-tinhtrang-sc-mvt", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsTinhTrangScMvt() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.layDsTinhTrangScMvt());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra điều kiện tiếp nhận")
    @RequestMapping(value = "/kiemtra-dieukien-tiepnhan", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemTraDieuKienTiepNhan(@RequestBody KiemTraDieuKienTiepNhanDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.kiemTraDieuKienTiepNhan(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/suCoMNVSendSMSDSDonVi")
    public ResponseEntity<ApiResult> suCoMNVSendSMSDSDonVi(@RequestBody BaseMap<String, Object> input) {
        ApiResult rs = new ApiResult();
        try {
            int phanVungId = input.getInt("phanVungId") == null ? AppRequestContext.getCurrent().getPhanVungID() : input.getInt("phanVungId");
            rs.setData(danhMucService.suCoMNVSendSMSDSDonVi(phanVungId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @PostMapping("/suCoMNVSendSMSDSNhanVien")
    public ResponseEntity<ApiResult> suCoMNVSendSMSDSNhanVien(@RequestBody BaseMap<String, Object> input) {
        ApiResult rs = new ApiResult();
        input.valid().isEmpty("dsDonViId", "Đơn vị input là bắt buộc!")
                .complete();
        try {
            String dsDonViId = input.getString("dsDonViId");
            rs.setData(danhMucService.suCoMNVSendSMSDSNhanVien(dsDonViId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @GetMapping("/suCoMNVDSPhanTuLoi")
    public ResponseEntity<ApiResult> suCoMNVDSPhanTuLoi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(danhMucService.suCoMNVDSPhanTuLoi(AppRequestContext.getCurrent().getPhanVungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
