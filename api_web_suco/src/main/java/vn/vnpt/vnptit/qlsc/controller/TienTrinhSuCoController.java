package vn.vnpt.vnptit.qlsc.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo.*;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo.CapNhatTienTrinh;
import vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo.ThemTienTrinh;
import vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo.TimSuCo;
import vn.vnpt.vnptit.qlsc.dto.TienTrinhSuCo.XoaTienTrinh;
import vn.vnpt.vnptit.qlsc.model.ThongTinNguoiDung;
import vn.vnpt.vnptit.qlsc.service.TienTrinhSuCoService;

import javax.validation.Valid;

@RestController
@RequestMapping("TienTrinhSuCo")
public class TienTrinhSuCoController {
    private final TienTrinhSuCoService _service;
    private ThongTinNguoiDung ttnd = new ThongTinNguoiDung();

    public TienTrinhSuCoController(TienTrinhSuCoService service) {
        this._service = service;
    }

    @Operation(summary = "danh sách nguồn sự cố")
    @RequestMapping(value = "/DanhSachNguonSuCo", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachNguonSuCo() {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            rs.setData(_service.DsNguonSuCo(ttnd.getPhanVungId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "danh sách trạng thái sự cố")
    @RequestMapping(value = "/DanhSachTrangThaiSuCo", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachTrangThaiSuCo() {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            rs.setData(_service.DsTrangThaiSuCo(ttnd.getPhanVungId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "danh sách sự cố")
    @RequestMapping(value = "/DanhSachSuCo", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachSuCo(@Valid @RequestBody TimSuCo input) {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            ttnd.setNhanVienId(AppRequestContext.getCurrent().getNhanVienID());
            rs.setData(_service.DsSuCo(input, ttnd.getPhanVungId(), ttnd.getNhanVienId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "danh sách tiến trình")
    @RequestMapping(value = "/DanhSachTienTrinh", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> DanhSachTienTrinh(@RequestParam Long suCoId) {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            rs.setData(_service.DsTienTrinh(ttnd.getPhanVungId(), suCoId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "kiểm tra tiến trình")
    @RequestMapping(value = "/KiemTraTienTrinh", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> KiemTraTienTrinh(@RequestParam Long tienTrinhId) {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            ttnd.setUserName(AppRequestContext.getCurrent().getUserName());
            rs.setData(new ResultOutput(_service.CheckTienTrinh(tienTrinhId, ttnd)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "thêm tiến trình")
    @RequestMapping(value = "/ThemTienTrinh", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ThemTienTrinh(@Valid @RequestBody ThemTienTrinh input) {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            ttnd.setNhanVienId(AppRequestContext.getCurrent().getNhanVienID());
            ttnd.setDonViId(AppRequestContext.getCurrent().getDonViID());
            ttnd.setIpCN(AppRequestContext.getCurrent().getClientIpAddress());
            ttnd.setMayCN(AppRequestContext.getCurrent().getClientName());
            ttnd.setUserName(AppRequestContext.getCurrent().getUserName());
            rs.setData(_service.ThemTienTrinh(input, ttnd));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "cập nhật tiến trình")
    @RequestMapping(value = "/CapNhatTienTrinh", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatTienTrinh(@Valid @RequestBody CapNhatTienTrinh input) {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            ttnd.setNhanVienId(AppRequestContext.getCurrent().getNhanVienID());
            ttnd.setDonViId(AppRequestContext.getCurrent().getDonViID());
            ttnd.setIpCN(AppRequestContext.getCurrent().getClientIpAddress());
            ttnd.setMayCN(AppRequestContext.getCurrent().getClientName());
            ttnd.setUserName(AppRequestContext.getCurrent().getUserName());
            rs.setData(_service.CapNhatTienTrinh(input, ttnd));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa tiến trình")
    @RequestMapping(value = "/XoaTienTrinh", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaTienTrinh(@Valid @RequestBody XoaTienTrinh input) {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            rs.setData(new ResultOutput(_service.XoaTienTrinh(input, ttnd.getPhanVungId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách số điện thoại")
    @RequestMapping(value = "/ds-sdt", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsSoDienThoai() {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            ttnd.setNhanVienId(AppRequestContext.getCurrent().getNhanVienID());
            rs.setData(_service.layDsSoDienThoai(ttnd));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách sms đã gửi")
    @RequestMapping(value = "/ds-sms-dagui", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsSmsDaGui(Long tienTrinhScId) {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            rs.setData(_service.layDsSmsDaGui(tienTrinhScId, ttnd.getPhanVungId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhân viên nhắn tin")
    @RequestMapping(value = "/ds-nhanvien-nhantin", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsNhanVienNt(Long suCoId) {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            rs.setData(_service.layDsNhanVienNt(suCoId, ttnd.getPhanVungId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nhắn tin tiến trình")
    @RequestMapping(value = "/tientrinh-sms", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> them_tientrinh_sms(@Valid @RequestBody TienTrinhSMS tienTrinhSMS) {
        ApiResult rs = new ApiResult();
        try {
            ttnd.setPhanVungId(AppRequestContext.getCurrent().getPhanVungID());
            ttnd.setNhanVienId(AppRequestContext.getCurrent().getNhanVienID());
            ttnd.setDonViId(AppRequestContext.getCurrent().getDonViID());
            ttnd.setIpCN(AppRequestContext.getCurrent().getClientIpAddress());
            ttnd.setMayCN(AppRequestContext.getCurrent().getClientName());
            ttnd.setUserName(AppRequestContext.getCurrent().getUserName());
            rs.setData(_service.them_tientrinh_sms(tienTrinhSMS, ttnd));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lưu bước xử lý")
    @RequestMapping(value = "/LuuBuocXuLy", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> LuuBuocXuLy(@Valid @RequestBody LuuBuocXuLyInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.LuuBuocXuLy(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đóng giao phiếu thủ tục")
    @RequestMapping(value = "/DongGiaoPhieuThuTuc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DongGiaoPhieuThuTuc(@Valid @RequestBody DongGiaoPhieuThuTucInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.dongGiaoPhieuThuTuc(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
