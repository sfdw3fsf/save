package vn.vnpt.vnptit.qlsc.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.HTKH_GIAOPH_CAPNHAT_DONVI_Input;
import vn.vnpt.vnptit.qlsc.dto.HTKH_GIAOPH_DS_DONVI_CHUAGIAO_Input;
import vn.vnpt.vnptit.qlsc.dto.HTKH_GIAOPH_GIAOPHIEU_Input;
import vn.vnpt.vnptit.qlsc.dto.HTKH_GIAOPH_KIEMTRA_GIAO_DONVI_Input;
import vn.vnpt.vnptit.qlsc.service.HtkhService;

@RestController
@RequestMapping(value = "/htkh")
public class HtkhController {

    private final HtkhService htkhService;

    public HtkhController(HtkhService htkhService) {
        this.htkhService = htkhService;
    }

    @Operation(summary = "Lấy thông tin hỗ trợ KH")
    @RequestMapping(value = "/get-thongtin-htkh", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> GetThongTinHTKH(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(htkhService.GetThongTinHTKH(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin hỗ trợ KH theo phiếu")
    @RequestMapping(value = "/get-thongtin-htkh-theo-phieu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> GetThongTinHTKHTheoPhieu(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(htkhService.GetThongTinHTKHTheoPhieu(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hướng giao theo quy trình")
    @RequestMapping(value = "/get-ds-huonggiao", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> GetDsHuongGiao(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(htkhService.GetDsHuongGiao(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị theo hướng giao")
    @RequestMapping(value = "/get-ds-donvi-theo-huonggiao", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> GetDsDonViTheoHuongGiao(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(htkhService.GetDsDonViTheoHuongGiao(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách đơn vị chưa giao theo hướng giao và htkhid")
    @RequestMapping(value = "/get-ds-donvi-chuagiao", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> GetDsDonViChuaGiaoHuongGiao(@Valid @RequestBody HTKH_GIAOPH_DS_DONVI_CHUAGIAO_Input item) {
        ApiResult rs = new ApiResult();
        try {
        	rs.setData(htkhService.GetDsDonViChuaGiaoHuongGiaoPhoiHop());
            //rs.setData(htkhService.GetDsDonViChuaGiaoHuongGiao(item.getHtkhID(), item.getHuongGiaoID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh phiếu đã giao theo HTKH_ID")
    @RequestMapping(value = "/get-ds-phieu-dagiao", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> GetDsPhieuDaGiao(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(htkhService.GetDsPhieuDaGiao(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin trạng thái PH của phiếu")
    @RequestMapping(value = "/get-trangthai-phoihop", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> GetTrangThaiPHCuaPhieu(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(htkhService.GetTrangThaiPHCuaPhieu(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra đã giao đơn vị")
    @RequestMapping(value = "/kiemtra-giao-donvi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> KiemTraGiaoDonVi(@Valid @RequestBody HTKH_GIAOPH_KIEMTRA_GIAO_DONVI_Input item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(htkhService.KiemTraGiaoDonVi(item.getHtkhID(), item.getDonViID())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Giao phiếu")
    @RequestMapping(value = "/giao-phieu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> GiaoPhieu(@Valid @RequestBody HTKH_GIAOPH_GIAOPHIEU_Input item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(htkhService.GiaoPhieu(item.getHtkhID(), item.getNoiDung(), item.getPhieuChaID(), item.getHuongGiaoID(), item.getSoCongVan(), item.getDsDonViNhanID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa phiếu")
    @RequestMapping(value = "/xoa-phieu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaPhieu(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(htkhService.XoaPhieu(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật đơn vị")
    @RequestMapping(value = "/capnhat-donvi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatDonVi(@Valid @RequestBody HTKH_GIAOPH_CAPNHAT_DONVI_Input item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(htkhService.CapNhatDonVi(item.getHtkhID(), item.getHuongGiaoID(), item.getDonViID())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hủy giao phiếu")
    @RequestMapping(value = "/huygiao-phieu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> HuyGiaoPhieu(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(htkhService.HuyGiaoPhieu(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
