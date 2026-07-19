package vn.vnpt.vnptit.qlsc.controller.cntt;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.dto.cntt.KhoaPhieuCnttDto;
import vn.vnpt.vnptit.qlsc.service.cntt.NghiemThuSuCoCNTTService;

import javax.validation.Valid;

@RestController
@RequestMapping("qlsc-cntt/nghiem-thu")
public class NghiemThuSuCoCNTTController {

    private final NghiemThuSuCoCNTTService nghiemThuSuCoCNTTService;

    public NghiemThuSuCoCNTTController(NghiemThuSuCoCNTTService nghiemThuSuCoCNTTService) {
        this.nghiemThuSuCoCNTTService = nghiemThuSuCoCNTTService;
    }

    @Operation(summary = "Nạp danh sách nguồn sự cố")
    @RequestMapping(value = "/napDsNguonSc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDsNguon() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.napDsNguonSC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nạp danh sách mức độ sự cố")
    @RequestMapping(value = "/napDsMucDoSc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDsMucDo() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.napDsMucDoSC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nạp danh sách tình trạng sự cố")
    @RequestMapping(value = "/napDsTinhTrangSc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDsTinhTrang() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.napDsTinhTrangSC());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nạp danh sách nhóm sự cố")
    @RequestMapping(value = "/napDsNhomSc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDsNhom(){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.napDsNhomSc());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách phieu dieu nt")
    @RequestMapping(value = "/layDsPhieuDieuNt", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsPhieuDieuNt(@RequestParam("suCoId") int suCoId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.layDsPhieuDieuNt(suCoId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy ảnh chụp phiếu nghiệm thu sự cố")
    @RequestMapping(value = "/layAnhChupPhieuNghiemThuSc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layAnhChupPhieuNghiemThuSc(@RequestParam("phieuId") int phieuId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.layAnhChupPhieuNghiemThuSc(phieuId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách phiếu nghiệm thu sự cố")
    @RequestMapping(value = "/layDsPhieuNghiemThuSc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsPhieuNghiemThuSc(@RequestBody DanhSachPhieuNghiemThu danhSachPhieuNghiemThu){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.layDsPhieuNghiemThuSc(danhSachPhieuNghiemThu));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nghiệm thu xử lý sự cố")
    @RequestMapping(value = "/nghiemThuSc", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> nghiemThuSuCotd(@RequestBody NghiemThuSuCo nghiemThuSuCotd){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.nghiemThuSuCotd(nghiemThuSuCotd));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Trả phiếu nghiệm thu")
    @RequestMapping(value = "/traPhieuNghiemThu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> traPhieuNghiemThu(@RequestBody @Valid TraPhieuSuCoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(nghiemThuSuCoCNTTService.traPhieuNghiemThu(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm danh sách thiết bị")
    @RequestMapping(value = "/timKiemDsThietBi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> timKiemDsThietBi(@RequestBody TimKiemDsThietBiDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.timKiemDsThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Khóa phiếu")
    @RequestMapping(value = "/khoa-phieu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> khoaPhieu(@Valid @RequestBody KhoaPhieuDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(nghiemThuSuCoCNTTService.khoaPhieu(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy số điện thoại nhắc việc")
    @RequestMapping(value = "/lay-so-dt-nhac-viec", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laySoDtNhacViec(@RequestParam("phieu_id") Long phieuId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.laySoDtNhacViec(phieuId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nhắc việc qua SMS")
    @RequestMapping(value = "/xuLyGiaoViec", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xuLyGiaoViec(@Valid @RequestBody XuLyGiaoViecInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.xuLyGiaoViec(input.getPhieu_id(), input.getNoiDung()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Nhắc việc qua SMS")
    @RequestMapping(value = "/xuLySmsTraPhieu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xuLySmsTralai(@Valid @RequestBody XuLyGiaoViecInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(nghiemThuSuCoCNTTService.xuLySmsTraPhieu(input.getPhieu_id(), input.getNoiDung()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/nghiemthu-suco-cntt", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> nghiemThuSucoCntt(@Valid @RequestBody KhoaPhieuCnttDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(nghiemThuSuCoCNTTService.nghiemThu(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
