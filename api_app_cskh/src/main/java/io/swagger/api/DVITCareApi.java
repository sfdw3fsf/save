package io.swagger.api;

import io.swagger.model.dto.DanhMuc;
import io.swagger.model.request.*;
import io.swagger.model.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.vnptit.sapi.oneapp.model.request.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Validated
@RequestMapping(value = "/dvitcare")
public interface DVITCareApi {

    @Operation(summary = "Danh sách phiếu yêu cầu khảo sát", tags = {"Danh sách phiếu yêu cầu khảo sát"})
    @RequestMapping(value = "/lay_ds_hdtb_tiepnhan_ks", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_ds_hdtb_tiepnhan_ks (@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayDsHdtbTiepnhanKsRequest requestBody);

    @Operation(summary = "Load thông tin danh bạ", tags = {"Load thông tin danh bạ"})
    @RequestMapping(value = "/lay_db_theo_matb", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_db_theo_matb(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayDbTheoMatbRequest requestBody);

    @Operation(summary = "Combobox Loại giấy tờ", description = "Combobox Loại giấy tờ", tags = {"Combobox Loại giấy tờ"})
    @RequestMapping(value = "/dsloaigiayto", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsloaigiayto();

    @Operation(summary = "Combobox Giới tính", description = "Combobox Giới tính", tags = {"Combobox Giới tính"})
    @RequestMapping(value = "/dsgioitinh", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsgioitinh();

    @Operation(summary = "combobox Quốc tịch", tags = {"combobox Quốc tịch"})
    @RequestMapping(value = "/dsquoctich", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse> dsquoctich();

    @Operation(summary = "combobox Ngành nghề", tags = {"Ngành nghề"})
    @RequestMapping(value = "/dsnganhnghe", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse> dsnganhnghe();

    @Operation(summary = "combobox Dịch vụ VT", tags = {"combobox Dịch vụ VT"})
    @RequestMapping(value = "/dsdichvu_vt", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse> dsdichvu_vt();

    @Operation(summary = "combobox Loại hình TB", tags = {"combobox Loại hình TB"})
    @RequestMapping(value = "/dsloaihinh_tb", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse> dsloaihinh_tb();

    @Operation(summary = "combobox Kiểu yêu cầu", tags = {"combobox Kiểu yêu cầu"})
    @RequestMapping(value = "/dskieuyeucau", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse> dskieuyeucau();

    @Operation(summary = "combobox Đơn vị xử lý", tags = {"combobox Đơn vị xử lý"})
    @RequestMapping(value = "/dsdonvixuly", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> dsdonvixuly(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareDSDonvXulyRequest requestBody);

    @Operation(summary = "combobox Đối tượng ", tags = {"combobox Đối tượng"})
    @RequestMapping(value = "/dsdoituong", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse> dsdoituong();

    @Operation(summary = "Xóa phiếu", tags = {"Xóa phiếu"})
    @RequestMapping(value = "/lap_hop_dong_xoa_hdkh", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lap_hop_dong_xoa_hdkh(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLapHopDongXoaHDKHRequest requestBody);

    @Operation(summary = "Danh sách lắp mới dịch vụ CNTT", tags = {"Danh sách lắp mới dịch vụ CNTT"})
    @RequestMapping(value = "/lay_danhsach_hd_ks_cntt", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_danhsach_hd_ks_cntt(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayDanhSachHDKSCNTTRequest requestBody);

    @Operation(summary = "Khảo sát dịch vụ CNTT", tags = {"Khảo sát dịch vụ CNTT"})
    @RequestMapping(value = "/capnhat_hoanthanh_ks_cntt", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> capnhat_hoanthanh_ks_cntt(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareCapnhatHoanthanhKSCNTTRequest requestBody);

    @Operation(summary = "Danh sách lắp đặt mới dịch vụ CNTT", tags = {"Danh sách lắp đặt mới dịch vụ CNTT"})
    @RequestMapping(value = "/lay_ds_thuebao_thicong_cntt_mobile", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_ds_thuebao_thicong_cntt_mobile(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayDSThuebaoThicongCNTTMobileRequest requestBody);

    @Operation(summary = "Chi tiết phiếu thi công", tags = {"Chi tiết phiếu thi công"})
    @RequestMapping(value = "/lay_ct_thuebao_thicong_mobile", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_ct_thuebao_thicong_mobile(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayCTThuebaoThicongMobileRequest requestBody);

    @Operation(summary = "combobox Loại khách hàng", tags = {"combobox Loại khách hàng"})
    @RequestMapping(value = "/danhmuc_tracuu_kh_donhang_smp", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> danhmuc_tracuu_kh_donhang_smp(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareDanhmucTracuuKHDonhangSMPRequest requestBody);

    @Operation(summary = "Chi tiết tác nghiệp", tags = {"Chi tiết tác nghiệp"})
    @RequestMapping(value = "/chaytuyen_nvtc_thuno", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> chaytuyen_nvtc_thuno(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareChayTuyenNvtcThunoRequest requestBody);

    @Operation(summary = "5.3 Chưa thu được - 1. Load thông tin cập nhật cũ", tags = {"1. Load thông tin cập nhật cũ"})
    @RequestMapping(value = "/quayvong_lay_tt_phieu_nvtc", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> quayvong_lay_tt_phieu_nvtc(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareQuayVongLayTTPhieuNvtcRequest requestBody);

    @Operation(summary = "5.3 Chưa thu được - 2. Load combo treeview kết quả thực hiện", tags = {"2. Load combo treeview kết quả thực hiện"})
    @RequestMapping(value = "/lay_nhom_lydo_tratruoc", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_nhom_lydo_tratruoc(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayNhomLydoTratruocRequest requestBody);

    @Operation(summary = "5.3 Chưa thu được -  3. Cập nhật kết quả", tags = {" 3. Cập nhật kết quả"})
    @RequestMapping(value = "/capnhat_kq_quayvong_itcare", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> capnhat_kq_quayvong_itcare(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareCapnhatKqQuayvongItCareRequest requestBody);

    @Operation(summary = "5.4 Không thu được -  3. Cập nhật kết quả", tags = {" 3. Cập nhật kết quả"})
    @RequestMapping(value = "/capnhat_thanhly_quayvong_itcare", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> capnhat_thanhly_quayvong_itcare(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareCapnhatThanhlyQuayvongItCareRequest requestBody);

    @Operation(summary = "3.4 Phối hợp thi công -  1.", tags = {" 1. kiemtra_luong_thaotac_it_care"})
    @RequestMapping(value = "/kiemtra_luong_thaotac_it_care", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> kiemtra_luong_thaotac_it_care(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareKiemtraLuongThaotacITCareRequest requestBody);

    @Operation(summary = "3.4 Phối hợp thi công -  2. Lấy đơn vị phối hợp", tags = {" 2. Lấy đơn vị phối hợp"})
    @RequestMapping(value = "/lay_donvi_phoihop_itcare", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_donvi_phoihop_itcare(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayDonviPhoihopITCareRequest requestBody);

    @Operation(summary = "3.4 Phối hợp thi công -  3. Lấy danh sách phiếu phối hợp", tags = {" 3. Lấy danh sách phiếu phối hợp"})
    @RequestMapping(value = "/lay_ds_phieu_phoihop_it_care", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_ds_phieu_phoihop_it_care(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayDsPhieuPhoihopITCareRequest requestBody);

    @Operation(summary = "1.3 Thời gian hẹn", tags = {"1.3 Thời gian hẹn"})
    @RequestMapping(value = "/capnhat_lichhen_khaosat", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> capnhat_lichhen_khaosat(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareCapnhatLichhenKhaosatRequest requestBody);


    @Operation(summary = "3.5 Xác nhận bảo trì - 1. Lấy danh sách câu hỏi", tags = {"1. Lấy danh sách câu hỏi"})
    @RequestMapping(value = "/lay_ds_cauhoi_theoloainv", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_ds_cauhoi_theoloainv(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayDsCauhoiTheoLoaiNVRequest requestBody);

    @Operation(summary = "3.5 Xác nhận bảo trì - 2. Lấy thông tin thuê bao", tags = {"2. Lấy thông tin thuê bao"})
    @RequestMapping(value = "/lay_thuebao_xacnhan_baotri", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_thuebao_xacnhan_baotri(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayThuebaoXacnhanBaotriRequest requestBody);

    @Operation(summary = "3.5 Xác nhận bảo trì - 3. Lấy kết quả trả lời", tags = {"3. Lấy kết quả trả lời"})
    @RequestMapping(value = "/lay_ketqua_traloi_itcare", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_ketqua_traloi_itcare(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayKetquaTraloiITCareRequest requestBody);

    @Operation(summary = "3.5 Xác nhận bảo trì - 4. Ghi lại", tags = {"4. Ghi lại"})
    @RequestMapping(value = "/capnhat_kq_itcare_mt", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> capnhat_kq_itcare_mt(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareCapnhatKQITCareMTRequest requestBody);

    @Operation(summary = "2. Giao việc 2.1. Load combobox đơn vị", tags = {"2.1. Load combobox đơn vị"})
    @RequestMapping(value = "/danhmuc_thongtin_gnv", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> danhmuc_thongtin_gnv(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareDanhMucThongTinGNVRequest requestBody);

    @Operation(summary = "2. Giao việc 2.4. Load danh sách phiếu giao", tags = {"2.4. Load danh sách phiếu giao"})
    @RequestMapping(value = "/lay_ds_gv_theo_phieu_id", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_ds_gv_theo_phieu_id(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayDsGVTheoPhieuIDRequest requestBody);

    @Operation(summary = "2. Giao việc 2.5. Giao việc và xóa giao việc", tags = {"2.4. Load danh sách phiếu giao"})
    @RequestMapping(value = "/giaoviec_thuquayvong_nvkt", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> giaoviec_thuquayvong_nvkt(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareGiaoviecThuquayvongNvktRequest requestBody);

    @Operation(summary = "3.6 Tổng hợp bảo trì 1. Load các thông tin hiển thị", tags = {"1. Load các thông tin hiển thị"})
    @RequestMapping(value = "/lay_thongtin_baotri_tonghop", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_thongtin_baotri_tonghop(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayThongtinBaotriTonghopRequest requestBody);

    @Operation(summary = "3.6 Tổng hợp bảo trì 2. Load thông tin zalo và email phục vụ gửi mẫu file", tags = {"2. Load thông tin zalo và email phục vụ gửi mẫu file"})
    @RequestMapping(value = "/lay_thongtin_baotri_lienhe", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_thongtin_baotri_lienhe(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayThongtinBaotriLienheRequest requestBody);

    @Operation(summary = "3.6 Tổng hợp bảo trì 3. Lưu kết quả cập nhật ", tags = {"3. Lưu kết quả cập nhật "})
    @RequestMapping(value = "/capnhat_kq_itcare_mt03", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> capnhat_kq_itcare_mt03(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareCapnhatKqITCareMT03Request requestBody);

    @Operation(summary = "4. Giao phiếu 4.1. Lấy thông tin để thực hiện kiểm tra các điều kiện", tags = {"4.1. Lấy thông tin để thực hiện kiểm tra các điều kiện"})
    @RequestMapping(value = "/kiemtra_dk_giao_phoihop", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> kiemtra_dk_giao_phoihop(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareKiemtraDKGiaoPhoihopRequest requestBody);

    @Operation(summary = "4. Giao phiếu 4.2. Thực hiện giao phiếu phối hợp", tags = {"4.2. Thực hiện giao phiếu phối hợp"})
    @RequestMapping(value = "/giaophieu_phoihop_thicong", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> giaophieu_phoihop_thicong(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareGiaophieuPhoihopThicongRequest requestBody);


    @Operation(summary = "3.3 Chi tiết hợp đồng CNTT 1. Form thuộc tính chi tiết", tags = {"1. Form thuộc tính chi tiết"})
    @RequestMapping(value = "/hienthi_thongtin_dvcntt", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> hienthi_thongtin_dvcntt(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareHienthiThongtinDVCNTTRequest requestBody);

    @Operation(summary = "3.3 Chi tiết hợp đồng CNTT 2. Form Thông số KT 2.1. Load thông tin", tags = {"2.1. Load thông tin"})
    @RequestMapping(value = "/lay_ds_thuoctinh_sub_v2", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_ds_thuoctinh_sub_v2(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayDSThuocTinhSubV2Request requestBody);

    @Operation(summary = "3.3 Chi tiết hợp đồng CNTT 2. Form Thông số KT 2.2. Thêm/Sửa thông tin", tags = {"2.2. Thêm/Sửa thông tin"})
    @RequestMapping(value = "/ghilai_thuoctinh_sub", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> ghilai_thuoctinh_sub(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareGhilaiThuoctinhSUBRequest requestBody);


    @Operation(summary = "3.3 Chi tiết hợp đồng CNTT 2.3. Xóa thông tin sub", tags = {"2.3. Xóa thông tin sub"})
    @RequestMapping(value = "/xoa_hdtb_cntt_sub", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> xoa_hdtb_cntt_sub(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareXoaHdtbCnttSubRequest requestBody);

    @Operation(summary = "20. Load thông tin khu vực", tags = {"20. Load thông tin khu vực"})
    @RequestMapping(value = "/lay_ds_khuvuc_theo_hd_db", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_ds_khuvuc_theo_hd_db(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayDsKhuvucTheoHdDbRequest requestBody);

    @Operation(summary = "5. Hủy giao phiếu phối hợp", tags = {"5. Hủy giao phiếu phối hợp"})
    @RequestMapping(value = "/huy_giao_phieu_phoihop", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> huy_giao_phieu_phoihop(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareHuyGiaoPhieuPhoihopRequest requestBody);

    @Operation(summary = "2.2. Lấy thông tin nhân viên yc và số điện thoại ", tags = {"2.2. Lấy thông tin nhân viên yc và số điện thoại"})
    @RequestMapping(value = "/lay_tt_nhanvien_yc_khaosat", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_tt_nhanvien_yc_khaosat(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayTTNhanvienYCKhaosatRequest requestBody);

    @Operation(summary = "4.5 Tổng hợp bảo trì 4. Xem trước mẫu file", tags = {"4.5 Tổng hợp bảo trì 4. Xem trước mẫu file"})
    @RequestMapping(value = "/lay_thongtin_baotri", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_thongtin_baotri(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayThongtinBaotriRequest requestBody);

    @Operation(summary = "4.3 Chi tiết hợp đồng CNTT 1. Load các thông tin thuộc tính", tags = {"4.3 Chi tiết hợp đồng CNTT 1. Load các thông tin thuộc tính"})
    @RequestMapping(value = "/hienthi_thongtin_dvcntt_bddv", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> hienthi_thongtin_dvcntt_bddv(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareHiethiThongtinDVCNTTBDDVRequest requestBody);

    @Operation(summary = "4.3 Chi tiết hợp đồng CNTT 2. Form Thông số KT 2.1. Load thông tin ", tags = {"2. Form Thông số KT 2.1. Load thông tin "})
    @RequestMapping(value = "/lay_ds_thuoctinh_sub_bddv", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_ds_thuoctinh_sub_bddv(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareLayDSThuoctinhSUBBDDVRequest requestBody);

    @Operation(summary = "4.3 Chi tiết hợp đồng CNTT 2. Form Thông số KT 2.2. Thêm/Sửa/Xóa thông tin", tags = {"2. Form Thông số KT 2.2. Thêm/Sửa/Xóa thông tin"})
    @RequestMapping(value = "/ghilai_thuoctinh_sub_bddv", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> ghilai_thuoctinh_sub_bddv(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareGhilaiThuoctinhSUBBDDVRequest requestBody);

    @Operation(summary = "1.5 Hoàn thành phiếu", tags = {"1.5 Hoàn thành phiếu"})
    @RequestMapping(value = "/giaophieu_ks_lapdatmoi", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> giaophieu_ks_lapdatmoi(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareGiaophieuKSLapDatMoiRequest requestBody);

    @Operation(summary = "1.2 - 19. Button Ghi lại", tags = {"1.2 - 19. Button Ghi lại"})
    @RequestMapping(value = "/themmoiphieuks_datmoi", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> themmoiphieuks_datmoi(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DVITCareObjCapNhat_KhaoSatRequest requestBody);

    @Operation(summary = "Cập nhật kết quả upload hồ sơ", tags = {"Cập nhật kết quả upload hồ sơ it-care"})
    @RequestMapping(value = "/insert_hoso_itcare_smp", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse> insert_hoso_itcare_smp(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody INSERT_HOSO_ITCARE_SMPRequest requestBody);
}
