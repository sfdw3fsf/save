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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/doicong-danhsach")
public class DoiCongTheoDanhSachController {
    @Autowired
    DoiCongTheoDanhSachService doiCongTheoDanhSachService;
    @Operation(summary = "Thêm hồ sơ cắt chuyển")
    @RequestMapping(value = "/them-hoso-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> them_hoso_catchuyen(@Valid  @RequestBody ThemHoSoCatChuyenDtoIn themHoSoCatChuyenDtoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.them_hoso_catchuyen(themHoSoCatChuyenDtoIn));
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
            rs.setData(doiCongTheoDanhSachService.capnhat_hoso_catchuyen(hoSoCatChuyen));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách hồ sơ")
    @RequestMapping(value = "/lay-ds-hoso", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> nap_ds_hoso(@Valid  @RequestBody NapDanhSachHoSoCusDtoIn napDanhSachHoSo) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.nap_ds_hoso(napDanhSachHoSo));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách hồ sơ - update 28/12/2022 - BSS-77693")
    @RequestMapping(value = "/lay-ds-hoso1", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> nap_ds_hoso1(@Valid  @RequestBody NapDanhSachHoSoCusDtoIn napDanhSachHoSo) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.nap_ds_hoso1(napDanhSachHoSo));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thue bao")
    @RequestMapping(value = "/lay-ds-thuebao", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_thuebao(@RequestParam("matb") String matb) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.lay_dsacc_tbao(matb));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hồ sơ cắt chuyển")
    @RequestMapping(value = "/lay-ds-hoso-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> load_hoso_catchuyen(@Valid @RequestBody NapDanhSachHoSoCusDtoIn napDanhSachHoSo) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.load_hoso_catchuyen(napDanhSachHoSo));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách hồ sơ cắt chuyển - update")
    @RequestMapping(value = "/lay-ds-hoso-catchuyen1", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> load_hoso_catchuyen1(@Valid @RequestBody NapDanhSachHoSoCusDtoIn napDanhSachHoSo) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.load_hoso_catchuyen1(napDanhSachHoSo));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Kiểm tra danh sach truoc khi xoa")
    @RequestMapping(value = "/check-infor-before-xoads-tab2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> check_infor_before_xoa_ds_tab2(@Valid @RequestBody List<CheckXoaDanhSachCatChuyenDtoIn> maTBList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.check_infor_before_xoa_ds_tab2(maTBList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Xóa danh sách cắt chuyển")
    @RequestMapping(value = "/xoa-ds-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa_ds_catchuyen(@Valid @RequestBody List<XoaDanhSachCatChuyenDtoIn> catChuyenDtoInList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.xoa_ds_catchuyen(catChuyenDtoInList));
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
            rs.setData(new ResultOutput(doiCongTheoDanhSachService.xoa_phieu_tab1(hsccId)));
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
            rs.setData(doiCongTheoDanhSachService.nap_ds_thuebao_catchuyen_tab2(hsccId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "lay_ds_tb_catchuyen_thaydoi_internet")
    @RequestMapping(value = "/lay_ds_tb_catchuyen_thaydoi_internet", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_tb_catchuyen_thaydoi_internet(@Valid @NotNull Long hsccId, @Valid @NotNull String vma_tb) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.lay_ds_tb_catchuyen_thaydoi_internet(hsccId, vma_tb));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "lay_ds_thuebao_capcong_thaydoi_ts_internet")
    @RequestMapping(value = "/lay_ds_thuebao_capcong_thaydoi_ts_internet", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_thuebao_capcong_thaydoi_ts_internet(@Valid @NotNull Long hsccId, @Valid @NotNull String vma_tb) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.lay_ds_thuebao_capcong_thaydoi_ts_internet(hsccId, vma_tb));
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
            rs.setData(new ResultOutput(doiCongTheoDanhSachService.them_ds_madoicap(hsccId, cungDoiCap, danhSachMaDoiCapDtoInList)));
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
            rs.setData(doiCongTheoDanhSachService.kiemtra_matb1_madoicap_ins_tab2(maTBList));
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
            rs.setData(doiCongTheoDanhSachService.kiemtra_malt1_madoicap_ins_tab2(maLTList));
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
            rs.setData(doiCongTheoDanhSachService.kiemtra_matb2_madoicap_ins_tab2(maTBList));
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
            rs.setData(doiCongTheoDanhSachService.kiemtra_malt2_madoicap_ins_tab2(maLTList));
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
            rs.setData(doiCongTheoDanhSachService.kiemtra_matb3_madoicap_ins_tab2(maTBList));
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
            rs.setData(doiCongTheoDanhSachService.kiemtra_malt3_madoicap_ins_tab2(maLTList));
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
            rs.setData(doiCongTheoDanhSachService.get_infor_matb_madoicap_ins_tab2(maTBList));
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
            rs.setData(doiCongTheoDanhSachService.get_infor_malt_madoicap_ins_tab2(maLTList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Giữ Vci Cpi")
    @RequestMapping(value = "/giu-vci-cpi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giu_vci_cpi(@Valid @RequestBody List<GiuVciCpiDtoIn> giuVciCpiDtoInList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.giu_vci_cpi(giuVciCpiDtoInList));
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
            rs.setData(doiCongTheoDanhSachService.nap_ds_thuebao_catchuyen_tab3(hsccId));
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
            rs.setData(doiCongTheoDanhSachService.nap_ds_tramtbi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách trang thai vci vpi")
    @RequestMapping(value = "/lay-ds-trangthai-vcivpi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> nap_ds_trangthai_vcivpi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.nap_ds_trangthai_vcivpi());
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
            rs.setData(doiCongTheoDanhSachService.nap_ds_dslam(tramtbId));
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
            rs.setData(doiCongTheoDanhSachService.nap_ds_port_fix(dslamId));
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
            rs.setData(doiCongTheoDanhSachService.nap_ds_luoi_port(dslamId, loaitbId));
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
            rs.setData(doiCongTheoDanhSachService.nap_ds_luoi_vcivpi(danhSachLuoiVciVpiDtoIn));
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
            rs.setData(doiCongTheoDanhSachService.nap_cbo_trangthai_cc());
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
            rs.setData( new ResultOutput(doiCongTheoDanhSachService.capnhat_gan_cong_catchuyen(kieu, hsccId, congCatChuyenList)));
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
            rs.setData(new ResultOutput(doiCongTheoDanhSachService.capnhat_go_cong_catchuyen(kieu, hsccId, congCatChuyenList)));
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
            rs.setData(doiCongTheoDanhSachService.hoanthien_catchuyen(hsccId, hoanThienCatChuyenDtoInList));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách ho so cắt chuyển")
    @RequestMapping(value = "/nap_cbo_hoso_cc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> nap_cbo_hoso_cc() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.nap_cbo_hoso_cc());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách kieu cắt chuyển")
    @RequestMapping(value = "/nap_cbo_kieu_cc", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> nap_cbo_kieu_cc() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.nap_cbo_kieu_cc());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách kieu cắt chuyển")
    @RequestMapping(value = "/lay_trangthai_catchuyen_id", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_trangthai_catchuyen_id(@RequestParam("hscc_id") String hscc_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.lay_ttcc_id(hscc_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cap nhat DK cắt chuyển")
    @RequestMapping(value = "/capnhat-dk-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_dk_catchuyen(@RequestParam Long dkcc_id, Integer type) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(doiCongTheoDanhSachService.capnhat_dk_catchuyen(dkcc_id, type)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "insert Log")
    @RequestMapping(value = "/them-log-catchuyen", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> them_log_catchuyen(@Valid  @RequestBody LogCatChuyenDtoIn themHoSoCatChuyenDtoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.them_log_catchuyen(themHoSoCatChuyenDtoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "cau hinh bnm")
    @RequestMapping(value = "/cauhinh-bnm", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> cauhinh_bnm(@Valid @RequestBody CauHinhBNMDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.cauHinhBNM(obj));
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
            rs.setData(doiCongTheoDanhSachService.getPassVisa(maTB));
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
            rs.setData(doiCongTheoDanhSachService.UpdateDeviceInfo(iptvac, devicename, portID));
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
            rs.setData(doiCongTheoDanhSachService.check_ttcc_donghoso_tab3(hscc_id));
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
            rs.setData(doiCongTheoDanhSachService.donghoso_tab3(hscc_id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "cap nhat dkcc khi hoan thien Tab3")
    @RequestMapping(value = "/capnhat9-catchuyen-hoanthien-tab3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat9_catchuyen_hoanthien_tab3(@Valid @RequestParam Long dkcc_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.capnhat9_catchuyen_hoanthien_tab3(dkcc_id));
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
            rs.setData(doiCongTheoDanhSachService.capnhat10_catchuyen_hoanthien_tab3(dkcc_id));
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
            rs.setData(doiCongTheoDanhSachService.capnhat11_catchuyen_huycauhinh_tab3(dkcc_id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy dữ liệu visa Tab3")
    @RequestMapping(value = "/tao-dulieu-visa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tao_dulieu_visa(@Valid @RequestParam Long dkcc_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.tao_dulieu_visa(dkcc_id));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy dữ liệu visa cũ Tab3")
    @RequestMapping(value = "/tao-dulieu-visa-cu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tao_dulieu_visa_cu(@Valid @RequestBody List<DuLieuViSaDtoIn> dkccIDList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.tao_dulieu_visa_cu(dkccIDList));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        catch (JsonProcessingException e) {
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra danh sach TB cho truong hop go cong tab3")
    @RequestMapping(value = "/kiemtra-list-inp-gocong-tab3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemtra_list_inp_gocong_tab3(@Valid @RequestBody List<DkccDtoIn> maLTList) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.kiemtra_list_inp_gocong_tab3(maLTList));
        } catch (AppSqlException e) {
            rs.setException(e);
        }catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy dữ liệu visa cũ Tab3")
    @RequestMapping(value = "/tao-dulieu-visa-cu_1", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tao_dulieu_visa_cu_1(@Valid @RequestBody  MaLTDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.tao_dulieu_visa_cu1(input));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        catch (JsonProcessingException e) {
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Get Key rp_id")
    @RequestMapping(value = "/get_keys_rp", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> get_keys_rp() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.get_key_rp());
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật gán cổng cắt chuyển")
    @RequestMapping(value = "/capnhat-gan-cong-catchuyen_1", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat_gan_cong_catchuyen_1(Integer kieu, Long hsccId,@Valid @RequestBody  CapNhatGanCongCatChuyen input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData( new ResultOutput(doiCongTheoDanhSachService.capnhat_gan_cong_catchuyen_1(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (JsonProcessingException e){}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Kiểm tra thông tin thuê bao dịch vụ danh bạ theo matb")
    @RequestMapping(value = "/kiemtra_thongtin_matb_dichvu_danhba", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> kiemtra_thongtin_matb_dichvu_danhba(@RequestParam String ma_tb, @RequestParam String dichvuvt_ids) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.kiemtra_thongtin_matb_dichvu_danhba(ma_tb, dichvuvt_ids));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Kiểm tra hồ sơ cắt chuyển theo matb")
    @RequestMapping(value = "/kiemtra_hoso_theo_matb", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> kiemtra_hoso_theo_matb(@RequestParam String ma_tb) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.kiemtra_hoso_theo_matb(ma_tb));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Kiểm tra điều kiện đổi cổng theo matb")
    @RequestMapping(value = "/kiemtra_dieukien_doicong_theo_matb", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> kiemtra_dieukien_doicong_theo_matb(@RequestParam String ma_tb) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.kiemtra_dieukien_doicong_theo_matb(ma_tb));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách thuê bao đổi cổng internet")
    @RequestMapping(value = "/lay_ds_thuebao_doicong_internet", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_thuebao_doicong_internet(@RequestParam String ma_tb) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(doiCongTheoDanhSachService.lay_ds_thuebao_doicong_internet(ma_tb));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Kiểm tra giữ VCI_VPI")
    @RequestMapping(value = "/kiemtra_giu_vci_vpi", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> kiemtra_giu_vci_vpi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(doiCongTheoDanhSachService.kiemtra_giu_vci_vpi()));
        }
        catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
}
