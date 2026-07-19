package vn.vnpt.vnptit.qlsc.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.common.Utils;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.service.GiaoPhieuXuLiSuCoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/giao-phieu-xu-li-su-co")
public class GiaoPhieuXuLiSuCoController {

    private final GiaoPhieuXuLiSuCoService service;
    private final DbContext dbContext;

    public GiaoPhieuXuLiSuCoController(GiaoPhieuXuLiSuCoService service, DbContext dbContext) {
        this.service = service;
        this.dbContext = dbContext;
    }

    @Operation(summary = "Giao phiếu xử lí sự cố")
    @RequestMapping(value = "/giao-phieu", produces = {
            "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giaoPhieu(@RequestBody @Valid GiaoPhieuDto giaoPhieuDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dbContext.executeSpWithCursorToListMap("CSS.PKG_GIAO_PHIEU_SU_CO.SP_GIAO_PHIEU",
                    Utils.process(giaoPhieuDto, AppRequestContext.getCurrent().getPhanVungID())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hủy phiếu xử lí sự cố")
    @RequestMapping(value = "/huy-phieu", produces = {
            "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> huyPhieu(@RequestBody @Valid GiaoPhieuDto.HuyPhieuDto huyPhieuDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dbContext.executeSpWithCursorToListMap("CSS.PKG_GIAO_PHIEU_SU_CO.SP_HUY_PHIEU",
                    Utils.process(huyPhieuDto, AppRequestContext.getCurrent().getPhanVungID())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm phiếu xử lí sự cố đã giao")
    @RequestMapping(value = "/tim-kiem-da-giao", produces = {
            "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> timKiemDaGiao(@RequestBody @Valid GiaoPhieuDto.DsHopDongDaGiaoDto dsHopDongDaGiaoDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dbContext.executeSpWithCursorToListMap("CSS.dieuhanh_thicong.LAY_DS_HDTB_DAGIAO",
                    Utils.process(dsHopDongDaGiaoDto, AppRequestContext.getCurrent().getPhanVungID())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm phiếu xử lí sự cố chưa giao")
    @RequestMapping(value = "/tim-kiem-chua-giao", produces = {
            "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> timKiemChuaGiao(@RequestBody @Valid GiaoPhieuDto.DsHopDongChuaGiaoDto dsHopDongChuaGiaoDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dbContext.executeSpWithCursorToListMap("CSS.PKG_GIAO_PHIEU_SU_CO.SP_LAY_DS_HDTB_CHUAGIAO",
                    Utils.process(dsHopDongChuaGiaoDto, AppRequestContext.getCurrent().getPhanVungID())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách phiếu xử lý")
    @RequestMapping(value = "/ds-phieu-xuly", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsPhieuXuLy(@RequestBody LayDsPhieuXuLyDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsPhieuXuLy(input));
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

    @Operation(summary = "Giao phiếu xử lý sự cố")
    @RequestMapping(value = "/giaophieu-xuly", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giaoPhieuXlSuCo(@RequestBody GiaoPhieuXlSuCoDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.giaoPhieuXlSuCo(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật phiếu xử lý sự cố")
    @RequestMapping(value = "/capnhat-phieu-xuly", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatPhieuXlSuCo(@RequestBody CapNhatPhieuXlSuCoDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.capNhatPhieuXlSuCo(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hủy phiếu xử lý sự cố")
    @RequestMapping(value = "/huy-phieu-xuly", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> huyPhieuXlSuCo(@RequestBody HuyPhieuXlSuCoDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.huyPhieuXlSuCo(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Giao phiếu xử lý sự cố mạng ngoại vi")
    @RequestMapping(value = "/giaophieu-xuly-mnv", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giaoPhieuXlSuCoMNV(@RequestBody GiaoPhieuXlMNVSuCoDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.giaoPhieuXlMNVSuCo(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách nhân viên theo đơn vị")
    @RequestMapping(value = "/nv-theo-dv", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsNhanVien(@RequestBody GiaoPhieuXlMNVSuCoDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsNhanVien(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách tình trạng thiết bị")
    @RequestMapping(value = "/tinhtrang-tb", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsTinhTrangThietBi(@RequestBody GiaoPhieuXlMNVSuCoDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsTinhTrangThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách nghiệp vụ")
    @RequestMapping(value = "/dsNghiepVu", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsNghiepVu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsNghiepVu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách nhóm nguyên nhân")
    @RequestMapping(value = "/dsNhomNN", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsNhomNN() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsNhomNN());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách tình trạng thiết bị")
    @RequestMapping(value = "/dsVatTuNghiepVu", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsVatTuNghiepVu(@RequestBody DanhSachVatTuMNVDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsVatTuNghiepVu(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách tình trạng thiết bị")
    @RequestMapping(value = "/dsVatTuTheoPhieuMNV", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsVatTuPhieuMNV(@RequestBody DanhSachVatTuMNVDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsVatTuPhieuMNV(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách tình trạng thiết bị")
    @RequestMapping(value = "/dsVatTuSuDungMNV", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsVatTuSuDungMNV(@RequestBody DanhSachVatTuMNVDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsVatTuSuDungMNV(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách tình trạng thiết bị")
    @RequestMapping(value = "/capNhatThuHoiVTMNV", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatThuHoiVTMNV(@RequestBody DanhSachVatTuMNVDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.capNhatThuHoiVTMNV(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách tình trạng thiết bị")
    @RequestMapping(value = "/hoanThanhThuHoiVTMNV", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> hoanThanhThuHoiVTMNV(@RequestBody DanhSachVatTuMNVDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.hoanThanhThuHoiVTMNV(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách tình trạng thiết bị")
    @RequestMapping(value = "/capNhatPhieuVatTuThuHoiMNV", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatPhieuThuHoiVTMNV(@RequestBody CapNhatPhieuThuHoiVatTu input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.capNhatPhieuThuHoiVTMNV(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tiếp nhận sự cố tạm mạng ngoại vi")
    @RequestMapping(value = "/tiepNhanSuCoTamMNV", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tiepNhanSuCoTamMNV(@Valid @RequestBody TiepNhanSuCoTamInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.tiepNhanSuCoTamMNV(item));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
