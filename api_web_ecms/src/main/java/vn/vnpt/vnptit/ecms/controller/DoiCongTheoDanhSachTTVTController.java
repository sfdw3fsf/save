package vn.vnpt.vnptit.ecms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.*;
import vn.vnpt.vnptit.ecms.service.DoiCongTheoDanhSachService;
import vn.vnpt.vnptit.ecms.service.DoiCongTheoDanhSachTTVTService;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/doicong-danhsach-ttvt")
public class DoiCongTheoDanhSachTTVTController {
    @Autowired
    DoiCongTheoDanhSachTTVTService doiCongTheoDanhSachTTVTService;
    @Operation(summary = "Thêm hồ sơ cắt chuyển")
    @RequestMapping(value = "/them-hoso-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> them_hoso_catchuyen(@Valid @RequestBody ThemHoSoCatChuyenDtoIn themHoSoCatChuyenDtoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.them_hoso_catchuyen(themHoSoCatChuyenDtoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật hồ sơ cắt chuyển")
    @RequestMapping(value = "/capnhat-hoso-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_hoso_catchuyen(@Valid  @RequestBody CapNhatHoSoCatChuyenDtoIn hoSoCatChuyen) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.capnhat_hoso_catchuyen(hoSoCatChuyen));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách hồ sơ")
    @RequestMapping(value = "/lay-ds-hoso", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> nap_ds_hoso(@Valid  @RequestBody NapDanhSachHoSoDtoIn napDanhSachHoSo) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.nap_ds_hoso(napDanhSachHoSo));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách hồ sơ cắt chuyển")
    @RequestMapping(value = "/lay-ds-hoso-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> load_hoso_catchuyen(@Valid @RequestBody NapDanhSachHoSoDtoIn napDanhSachHoSo) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.load_hoso_catchuyen(napDanhSachHoSo));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Xóa danh sách cắt chuyển")
    @RequestMapping(value = "/xoa-ds-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa_ds_catchuyen(@Valid @RequestBody List<XoaDanhSachCatChuyenDtoIn> catChuyenDtoInList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.xoa_ds_catchuyen(catChuyenDtoInList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Xóa phiếu hồ sơ cắt chuyển")
    @RequestMapping(value = "/xoa-phieu-tab1", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa_phieu_tab1(Long hsccId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(doiCongTheoDanhSachTTVTService.xoa_phieu_tab1(hsccId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách thuê bao cắt chuyển tab2")
    @RequestMapping(value = "/lay-ds-thuebao-catchuye-tab2", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> nap_ds_thuebao_catchuyen_tab2(Long hsccId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.nap_ds_thuebao_catchuyen_tab2(hsccId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thêm danh sách Mã đổi cáp")
    @RequestMapping(value = "/them-ds-madoicap", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> them_ds_madoicap(Long hsccId, Integer cungDoiCap,@Valid @RequestBody List<ThemDanhSachMaDoiCapDtoIn> danhSachMaDoiCapDtoInList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(doiCongTheoDanhSachTTVTService.them_ds_madoicap(hsccId, cungDoiCap, danhSachMaDoiCapDtoInList)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Kiểm tra mã thuê bao 1 cho thêm danh sách mã đổi cáp")
    @RequestMapping(value = "/kiemtra-matb1-madoicap-ins-tab2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemtra_matb1_madoicap_ins_tab2(@Valid @RequestBody List<MaTBDtoIn> maTBList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.kiemtra_matb1_madoicap_ins_tab2(maTBList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Kiểm tra mã ảo 1 cho thêm danh sách mã đổi cáp")
    @RequestMapping(value = "/kiemtra-malt1-madoicap-ins-tab2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemtra_malt1_madoicap_ins_tab2(@Valid @RequestBody List<MaLTDtoIn> maLTList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.kiemtra_malt1_madoicap_ins_tab2(maLTList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Kiểm tra mã thuê bao 2 cho thêm danh sách mã đổi cáp")
    @RequestMapping(value = "/kiemtra-matb2-madoicap-ins-tab2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemtra_matb2_madoicap_ins_tab2(@Valid @RequestBody List<MaTBDtoIn> maTBList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.kiemtra_matb2_madoicap_ins_tab2(maTBList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Kiểm tra mã ảo 2 cho thêm danh sách mã đổi cáp")
    @RequestMapping(value = "/kiemtra-malt2-madoicap-ins-tab2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemtra_malt2_madoicap_ins_tab2(@Valid @RequestBody List<MaLTDtoIn> maLTList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.kiemtra_malt2_madoicap_ins_tab2(maLTList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Kiểm tra mã thuê bao 3 cho thêm danh sách mã đổi cáp")
    @RequestMapping(value = "/kiemtra-matb3-madoicap-ins-tab2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemtra_matb3_madoicap_ins_tab2(@Valid @RequestBody List<MaTBDtoIn> maTBList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.kiemtra_matb3_madoicap_ins_tab2(maTBList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Kiểm tra mã ảo 3 cho thêm danh sách mã đổi cáp")
    @RequestMapping(value = "/kiemtra-malt3-madoicap-ins-tab2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemtra_malt3_madoicap_ins_tab2(@Valid @RequestBody List<MaLTDtoIn> maLTList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.kiemtra_malt3_madoicap_ins_tab2(maLTList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy thông tin từ mã thuê bao cho thêm danh sách mã đổi cáp")
    @RequestMapping(value = "/get-infor-matb-madoicap-ins-tab2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> get_infor_matb_madoicap_ins_tab2(@Valid @RequestBody List<MaTBDtoIn> maTBList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.get_infor_matb_madoicap_ins_tab2(maTBList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy thông tin từ mã ảo cho thêm danh sách mã đổi cáp")
    @RequestMapping(value = "/get-infor-malt-madoicap-ins-tab2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> get_infor_malt_madoicap_ins_tab2(@Valid @RequestBody List<MaLTDtoIn> maLTList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.get_infor_malt_madoicap_ins_tab2(maLTList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Giữ Vci Cpi")
    @RequestMapping(value = "/giu-vci-cpi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giu_vci_cpi(@Valid @RequestBody List<GiuVciCpiDtoIn> giuVciCpiDtoInList, Long hsccId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.giu_vci_cpi(giuVciCpiDtoInList, hsccId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách thuê bao cắt chuyển tab3")
    @RequestMapping(value = "/lay-ds-thuebao-catchuyen-tab3", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> nap_ds_thuebao_catchuyen_tab3(Long hsccId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.nap_ds_thuebao_catchuyen_tab3(hsccId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách trạm thiết bị")
    @RequestMapping(value = "/lay-ds-tramtbi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> nap_ds_tramtbi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.nap_ds_tramtbi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách dslam")
    @RequestMapping(value = "/lay-ds-dslam", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> nap_ds_dslam(Long tramtbId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.nap_ds_dslam(tramtbId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách port fix")
    @RequestMapping(value = "/lay-ds-port-fix", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> nap_ds_port_fix(Long dslamId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.nap_ds_port_fix(dslamId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách lưới port")
    @RequestMapping(value = "/lay-ds-luoi-port", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> nap_ds_luoi_port(Long dslamId, Long loaitbId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.nap_ds_luoi_port(dslamId, loaitbId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách lưới Vci Vpi")
    @RequestMapping(value = "/nap_ds_luoi_vcivpi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> nap_ds_luoi_vcivpi(@Valid @RequestBody  NapDanhSachLuoiVciVpiDtoIn danhSachLuoiVciVpiDtoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.nap_ds_luoi_vcivpi(danhSachLuoiVciVpiDtoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách trạng thái cắt chuyển")
    @RequestMapping(value = "/lay_ds_trangthai_cc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> nap_cbo_trangthai_cc() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.nap_cbo_trangthai_cc());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật gán cổng cắt chuyển")
    @RequestMapping(value = "/capnhat-gan-cong-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_gan_cong_catchuyen(Integer kieu, Long hsccId,@Valid @RequestBody List<CapNhatCongCatChuyen> congCatChuyenList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData( new ResultOutput(doiCongTheoDanhSachTTVTService.capnhat_gan_cong_catchuyen(kieu, hsccId, congCatChuyenList)));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật gở cổng cắt chuyển")
    @RequestMapping(value = "/capnhat-go-cong-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_go_cong_catchuyen(Integer kieu, Long hsccId,@Valid @RequestBody List<CapNhatCongCatChuyen> congCatChuyenList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData( new ResultOutput(doiCongTheoDanhSachTTVTService.capnhat_go_cong_catchuyen(kieu, hsccId, congCatChuyenList)));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Hoàn thiện cắt chuyển")
    @RequestMapping(value = "/hoanthien-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> hoanthien_catchuyen(Long hsccId,@Valid @RequestBody List<HoanThienCatChuyenDtoIn> hoanThienCatChuyenDtoInList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.hoanthien_catchuyen(hsccId, hoanThienCatChuyenDtoInList));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách kieu cắt chuyển")
    @RequestMapping(value = "/lay_trangthai_catchuyen_id", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_trangthai_catchuyen_id(@RequestParam("hscc_id") String hscc_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.lay_ttcc_id(hscc_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "cap nhat dkcc khi hoan thien Tab3")
    @RequestMapping(value = "/capnhat9-catchuyen-hoanthien-tab3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat9_catchuyen_hoanthien_tab3(@Valid @RequestParam Long dkcc_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.capnhat9_catchuyen_hoanthien_tab3(dkcc_id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "cau hinh bnm")
    @RequestMapping(value = "/cauhinh-bnm", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> cauhinh_bnm(@Valid  @RequestBody CauHinhBNMDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.cauhinh_bnm(obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy mật khẩu Visa")
    @RequestMapping(value = "/func-get-pass-visa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getPassVisa(@Valid String maTB) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.getPassVisa(maTB));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cap nhat device info")
    @RequestMapping(value = "/update-device-info", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateDeviceInfo(@Valid String iptvac, String devicename, String portID) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.UpdateDeviceInfo(iptvac, devicename, portID));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "cap nhat dkcc khi hoan thien Tab3")
    @RequestMapping(value = "/capnhat10-catchuyen-hoanthien-tab3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat10_catchuyen_hoanthien_tab3(@Valid @RequestParam Long dkcc_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.capnhat10_catchuyen_hoanthien_tab3(dkcc_id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "cap nhat dkcc khi huy cau hinh Tab3")
    @RequestMapping(value = "/capnhat11-catchuyen-huycauhinh-tab3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat11_catchuyen_huycauhinh_tab3(@Valid @RequestParam Long dkcc_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.capnhat11_catchuyen_huycauhinh_tab3(dkcc_id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Check dong ho so Tab3")
    @RequestMapping(value = "/check-ttcc-donghoso-tab3", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> check_ttcc_donghoso_tab3(@Valid @RequestParam Long hscc_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.check_ttcc_donghoso_tab3(hscc_id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "dong ho so Tab3")
    @RequestMapping(value = "/donghoso-tab3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> donghoso_tab3(@Valid @RequestParam Long hscc_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachTTVTService.donghoso_tab3(hscc_id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
