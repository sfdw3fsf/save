package vn.vnpt.vnptit.qlsc.controller.mnv;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.BaseMap;
import vn.vnpt.vnptit.qlsc.dto.CayThietBiInput;
import vn.vnpt.vnptit.qlsc.dto.LayDsCongSoiInput;
import vn.vnpt.vnptit.qlsc.dto.LayThongTinThietBiDto;
import vn.vnpt.vnptit.qlsc.dto.mnv.FilterYeuCauHoTroInput;
import vn.vnpt.vnptit.qlsc.dto.mnv.LayBuocXuLyYCHTMNVInput;
import vn.vnpt.vnptit.qlsc.dto.mnv.PhanHoiYeuCauDTO;
import vn.vnpt.vnptit.qlsc.dto.mnv.TrangThaiHoTroDTO;
import vn.vnpt.vnptit.qlsc.service.DanhMucService;
import vn.vnpt.vnptit.qlsc.service.QuanLySuCoService;
import vn.vnpt.vnptit.qlsc.service.mnv.YeuCauHoTroTroService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/suCoMNV")
public class YeuCauHoTroController {

    private final YeuCauHoTroTroService service;
    private final QuanLySuCoService quanLySuCoService;
    private final DanhMucService danhMucService;

    public YeuCauHoTroController(YeuCauHoTroTroService service, QuanLySuCoService quanLySuCoService,
                                 DanhMucService danhMucService) {
        this.service = service;
        this.quanLySuCoService = quanLySuCoService;
        this.danhMucService = danhMucService;
    }

    @RequestMapping(value = "/yeuCauHoTroDanhSach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> yeuCauHoTroDanhSach(@RequestBody FilterYeuCauHoTroInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.yeuCauHoTroDanhSach(
                    item,
                    AppRequestContext.getCurrent().getPhanVungID(),
                    AppRequestContext.getCurrent().getNguoiDungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/yeuCauHoTroChiTiet", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> yeuCauHoTroChiTiet(@RequestBody BaseMap<String, Object> map) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(
                    service.yeuCauHoTroChiTiet(
                            map.getInt("phanVungId"),
                            map.getLong("suCoId"),
                            map.getLong("xuLyId"),
                            AppRequestContext.getCurrent().getNguoiDungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/yeuCauHoTroBuocXuLy", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layBuocXuLyYCHTMNV(@RequestBody LayBuocXuLyYCHTMNVInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layBuocXuLyYCHTMNV(
                    item.getSuCoId(),
                    AppRequestContext.getCurrent().getPhanVungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/trangThaiHoTro", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> trangThaiHoTro() {
        ApiResult rs = new ApiResult();
        List<TrangThaiHoTroDTO> trangThaiList = Arrays.asList(
                new TrangThaiHoTroDTO(0, "Tất cả"),
                new TrangThaiHoTroDTO(1, "Chờ xử lý"),
                new TrangThaiHoTroDTO(2, "Đã tiếp nhận"),
                new TrangThaiHoTroDTO(3, "Từ chối"));
        rs.setData(trangThaiList);
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/yeuCauHoTroPhanHoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> yeuCauHoTroPhanHoi(@RequestBody PhanHoiYeuCauDTO dto) {
        ApiResult rs = new ApiResult();
        try {
            dto.valid();
            rs.setData(service.yeuCauHoTroPhanHoi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/yeuCauHoTroKetQuaDoKiem", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> yeuCauHoTroKetQuaDoKiem(@RequestBody BaseMap<String, Object> map) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(
                    service.yeuCauHoTroKetQuaDoKiem(
                            map.getInt("phanVungId"),
                            map.getLong("xuLyId")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/layDsCongSoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsCongSoi(@RequestBody LayDsCongSoiInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(quanLySuCoService.layDsCongSoi(item.getLoaiThietBiID(), item.getThietBiID())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/layDsThueBaoBiAnhHuong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsThueBaoBiAnhHuong(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(quanLySuCoService.layDsThueBaoBiAnhHuong(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

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
}
