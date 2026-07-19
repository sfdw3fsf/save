package io.swagger.api;

import io.swagger.model.dto.DanhMuc;
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
import org.springframework.web.bind.annotation.RequestParam;
import vn.vnptit.sapi.oneapp.model.request.*;
import vn.vnptit.sapi.oneapp.model.response.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Validated
@RequestMapping(value = "/cskh_vnpt")
public interface CSKHApi {

    @Operation(summary = "CSKH-Lấy danh sách phiếu chăm sóc khách hàng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/layDsPhieuCSKH", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<PhieuCSKHResponse>>> layDsPhieuCSKH();

    @Operation(summary = "CSKH-Lấy danh sách danh sách tác nghiệp", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/layDsTacNghiep", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Object>>> layDsTacNghiep(@Parameter(in = ParameterIn.QUERY, description = "Phiếu tác nghiệp Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "phieutnId", required = false) Long phieutnId,
                                                              @Parameter(in = ParameterIn.QUERY, description = "Kiểu(1: DS CSKH; 2: TU VAN DUNG THU MYTV)", required = true, schema = @Schema()) @Valid @RequestParam(value = "kieu", required = false) Integer kieu);

    @Operation(summary = "CSKH-Lấy danh sách danh mục CSKH 36 tháng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/layDsDanhMucCSKH36", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Object>>> layDsDanhMucCSKH36(@Parameter(in = ParameterIn.QUERY, description = "Loại", required = true, schema = @Schema()) @Valid @RequestParam(value = "loai", required = false) Integer loai,
                                                                  @Parameter(in = ParameterIn.QUERY, description = "Param", required = true, schema = @Schema()) @Valid @RequestParam(value = "param", required = false) Long param);

    @Operation(summary = "CSKH-Cập nhật sticker báo hỏng đã chụp", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/capNhatStickerBH", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> capNhatStickerBH(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CapNhatStickerBHRequest requestBody);

    @Operation(summary = "CSKH-Cập nhật thông tin địa chỉ", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/capNhatThongTinDiaChi", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> capNhatThongTinDiaChi(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CapNhatThongTinDiaChiRequest requestBody);

    @Operation(summary = "CSKH-Lưu lại thông tin CSKH 36 tháng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/luuThongTinCSKH36", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> luuThongTinCSKH36(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody LuuThongTinCSKH36Request requestBody);

    @Operation(summary = "CSKH-Lấy thông tin khách hàng doanh nghiệp", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/layThongTinKHDN", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<ThongTinKHDNResponse>>> layThongTinKHDN(@Parameter(in = ParameterIn.QUERY, description = "Đối tượng chăm sóc Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "doituongcsId", required = false) Long doituongcsId,
                                                                             @Parameter(in = ParameterIn.QUERY, description = "Mã khách hàng", required = true, schema = @Schema()) @Valid @RequestParam(value = "makh", required = false) String makh);

    @Operation(summary = "CSKH-Lấy thông tin liên hệ", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/layThongTinLienHe", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<ThongTinLienHeResponse>> layThongTinLienHe(@Parameter(in = ParameterIn.QUERY, description = "Hợp đồng khách hàng Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "hdkhId", required = false) Long hdkhId);

    @Operation(summary = "CSKH-Lấy danh sách cài app", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/layDsCaiApp", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<CaiAppResponse>>> layDsCaiApp(@Parameter(in = ParameterIn.QUERY, description = "Phiếu tác nghiệp Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "phieutnId", required = false) Long phieutnId);

    @Operation(summary = "CSKH-Lấy thông tin mục đích Email", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/layThongTinMucDichEmail", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<MucDichEmailResponse>>> layThongTinMucDichEmail(@Parameter(in = ParameterIn.QUERY, description = "Hợp đồng liên hệ Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "hdlhId", required = false) Long hdlhId);

    @Operation(summary = "CSKH-Lấy danh sách MXH", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/layDsMXH", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<MXHResponse>>> layDsMXH(@Parameter(in = ParameterIn.QUERY, description = "Hợp đồng liên hệ Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "hdlhId", required = false) Long hdlhId);

    @Operation(summary = "CSKH-Lấy danh sách Thông tin liên hệ cập nhật cho", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/layDsThongTinLienHeCapNhatCho", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<ThongTinLienHeCapNhatChoResponse>>> layDsThongTinLienHeCapNhatCho(@Parameter(in = ParameterIn.QUERY, description = "Thông tin liên hệ Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "ttlh_id", required = false) Long ttlh_id,
                                                                                                       @Parameter(in = ParameterIn.QUERY, description = "Thanh toán Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "thanhtoan_id", required = false) Long thanhtoan_id,
                                                                                                       @Parameter(in = ParameterIn.QUERY, description = "Thuê bao Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "thuebao_id", required = false) Long thuebao_id);
    @Operation(summary = "CSKH-Lấy thông tin phiếu cskh 36t", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/layThongTinPhieuCSKH36", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<ThongTinPhieuCSKH36Response>> layThongTinPhieuCSKH36(@Parameter(in = ParameterIn.QUERY, description = "Phiếu Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "phieuId", required = false) Long phieuId);

    @Operation(summary = "CSKH-Hoàn thiện phiếu CSKH 36 tháng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/cskh36t/hoanThienPhieuCSKH36", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> hoanThienPhieuCSKH36(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody LuuThongTinCSKH36Request requestBody);


    @Operation(summary = "CSKH-Lấy danh mục cảm nhận khách hàng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/layDanhMucCNKH", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDanhMucCNKH();

    @Operation(summary = "CSKH-Lấy danh sách nhóm nguyên nhân khách hàng không hài lòng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/layDsNhomNguyenNhanKHL", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<NhomNguyenNhanKHLResponse>>> layDsNhomNguyenNhanKHL();

    @Operation(summary = "CSKH-Lấy danh sách nguyên nhân khách hàng không hài lòng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/layDsNguyenNhanKHL", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<NguyenNhanKHLResponse>>> layDsNguyenNhanKHL(@Parameter(in = ParameterIn.QUERY, description = "Nhóm nguyên nhân Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "nhomnnId", required = false) Long nhomnnId);

    @Operation(summary = "CSKH-Lấy danh sách thuê bao khảo sát", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/layDsThueBaoKhaoSat", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<ThueBaoKhaoSatResponse>>> layDsThueBaoKhaoSat(@Parameter(in = ParameterIn.QUERY, description = "Khách hàng Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "khachhangId", required = false) Long khachhangId,
                                                                                   @Parameter(in = ParameterIn.QUERY, description = "Phiếu tác nghiệp Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "phieutnId", required = false) Long phieutnId,
                                                                                   @Parameter(in = ParameterIn.QUERY, description = "Loại NC Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "loaincId", required = false) Long loaincId);

    @Operation(summary = "CSKH-Lấy danh sách mạng xã hội", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/layDsMangXaHoi", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsMangXaHoi();

    @Operation(summary = "CSKH-Lấy thông tin liên hệ lưu từ hợp đồng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/layThongTinLienHeHopDong", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<ThongTinLienHeHopDongResponse>>> layThongTinLienHeHopDong(@Parameter(in = ParameterIn.QUERY, description = "Phiếu tác nghiệp Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "phieutnId", required = false) Long phieutnId,
                                                                                               @Parameter(in = ParameterIn.QUERY, description = "Kieu(loainc_id = 20 => kieu = 8;loainc_id = 23 => kieu = 9)", required = true, schema = @Schema()) @Valid @RequestParam(value = "kieu", required = false) Integer kieu);

    @Operation(summary = "CSKH-Lấy danh sách thông tin liên hệ sẽ được cập nhật cho", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/layDsTTLHCapNhatCho", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTLHCapNhatChoResponse>>> layDsTTLHCapNhatCho(@Parameter(in = ParameterIn.QUERY, description = "Phiếu tác nghiệp Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "phieutnId", required = false) Long phieutnId,
                                                                                   @Parameter(in = ParameterIn.QUERY, description = "Loại NC Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "loaincId", required = false) Long loaincId);

    @Operation(summary = "CSKH-Cập nhật phiếu CSKH", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/capNhatPhieuCSKH", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> capNhatPhieuCSKH(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CapNhatPhieuCSKHRequest requestBody);

    @Operation(summary = "CSKH-Hoàn thiện phiếu CSKH", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/hoanThienPhieuCSKH", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> hoanThienPhieuCSKH(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody HoanThienPhieuCSKHRequest requestBody);

    @Operation(summary = "CSKH-Cập nhật Zalo thanh toán", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/capNhatZaloThanhToan", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> capNhatZaloThanhToan(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CapNhatZaloThanhToanRequest requestBody);

    @Operation(summary = "CSKH-Check zalo", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/checkZalo", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<Void>> checkZalo(@Parameter(in = ParameterIn.QUERY, description = "Số điện thoại", required = true, schema = @Schema()) @Valid @RequestParam(value = "sdt", required = false) String sdt,
                                                 @Parameter(in = ParameterIn.QUERY, description = "Kiểu(1: Quan tâm OA VNPT, 2: Không quan tâm)", required = true, schema = @Schema()) @Valid @RequestParam(value = "kieu", required = false) Integer kieu);

    @Operation(summary = "CSKH-Lấy thông tin Zalo thanh toán", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/thuthap_ttlh/layZaloThanhToan", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<ZaloThanhToanResponse>>> layZaloThanhToan(@Parameter(in = ParameterIn.QUERY, description = "Phiếu tác nghiệp Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "phieutnId", required = false) Long phieutnId);

    @Operation(summary = "CSKH-Lấy danh sách dịch vụ tiềm năng đã được thêm", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/dichvu_tiemnang/layDsDichVuTiemNang", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DichVuTiemNangResponse>>> layDsDichVuTiemNang(@Parameter(in = ParameterIn.QUERY, description = "Phiếu tác nghiệp Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "phieutnId", required = false) Long phieutnId);

    @Operation(summary = "CSKH-Lấy danh sách tác nghiệp dịch vụ di động đã thêm", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/dichvu_tiemnang/layDsDichVuDiDong", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DichVuDiDongResponse>>> layDsDichVuDiDong(@Parameter(in = ParameterIn.QUERY, description = "Tiềm năng dịch vụ Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "tiemnangdvId", required = false) Long tiemnangdvId);

    @Operation(summary = "CSKH-Lấy danh sách các dịch vụ", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/dichvu_tiemnang/layDsDichVu", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DichVuResponse>>> layDsDichVu();

    @Operation(summary = "CSKH-Lấy danh sách trạng thái dịch vụ", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/dichvu_tiemnang/layDsTrangThaiDichVu", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsTrangThaiDichVu();

    @Operation(summary = "CSKH-Lấy danh sách nhà cung cấp", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/dichvu_tiemnang/layDsNhaCungCap", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsNhaCungCap();

    @Operation(summary = "CSKH-Lấy danh sách Hình thức thanh toán", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/dichvu_tiemnang/layDsHinhThucThanhToan", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsHinhThucThanhToan();

    @Operation(summary = "CSKH-Lấy danh sách Chất lượng dịch vụ(Internet)", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/dichvu_tiemnang/layDsChatLuongDichVu", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsChatLuongDichVu();

    @Operation(summary = "CSKH-Lấy danh sách Chủng loại TV(Truyền hình)", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/dichvu_tiemnang/layDsChungLoaiTV", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsChungLoaiTV();

    @Operation(summary = "CSKH-Thêm/Cập nhật dịch vụ tiềm năng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/dichvu_tiemnang/capNhatDichVuTiemNang", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Long>> capNhatDichVuTiemNang(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CapNhatDichVuTiemNangRequest requestBody);

    @Operation(summary = "CSKH-Xóa Dịch vụ tiềm năng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/dichvu_tiemnang/xoaDichVuTiemNang", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> xoaDichVuTiemNang(@Parameter(in = ParameterIn.QUERY, description = "Tiềm năng dịch vụ Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "tiemnangDvId", required = false) Long tiemnangDvId);

    @Operation(summary = "CSKH-Lấy thông tin khách hàng tiềm năng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/dichvu_tiemnang/layThongTinKHTN", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<ThongTinKHTNResponse>> layThongTinKHTN(@Parameter(in = ParameterIn.QUERY, description = "Khách hàng Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "khachhangId", required = false) Long khachhangId);

    @Operation(summary = "CSKH-Lấy danh sách kết quả tư vấn", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/tuvan_mytv/layDsKetQuaTuVan", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDsKetQuaTuVan();

    @Operation(summary = "CSKH-Cập nhật kết quả tư vấn", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/tuvan_mytv/capNhatKetQuaTuVan", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<KetQuaTuVanResponse>> capNhatKetQuaTuVan(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CapNhatKetQuaTuVanRequest requestBody);

    @Operation(summary = "CSKH-Chi thiết thông tin KH/TT/TB", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/chitietThongTinKHTTTB", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> chitietThongTinKHTTTB(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ChiTietThongTinKHTTTBRequest requestBody);

    @Operation(summary = "Hàm cập nhật thông tin Khách hàng/ Thanh toán/ Thuê bao theo đối tượng thuê bao tra cứu", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/CapNhatHDTDTTTheoThueBao", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> CapNhatHDTDTTTheoThueBao(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CapNhatHDTDTTTheoThueBaoRequest requestBody);

    @Operation(summary = "Danh sách Tài khoản chính", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/DanhSachTaiKhoanChinh", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> DanhSachTaiKhoanChinh(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DanhSachTaiKhoanChinhRequest requestBody);

    @Operation(summary = "Danh sách Mức cước thuê bao", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/DanhSachMucCuocThueBao", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> DanhSachMucCuocThueBao(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DanhSachMucCuocThueBaoRequest requestBody);

    @Operation(summary = "Danh sách Mức cước thuê bao 2", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/DanhSachMucCuocThueBao2", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> DanhSachMucCuocThueBao2(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DanhSachMucCuocThueBaoRequest requestBody);

    @Operation(summary = "Combo Ds mã đại diện theo thanh toán", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/ComboDSMaDaiDienTheoThanhToan", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> ComboDSMaDaiDienTheoThanhToan(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ComboDSMaDaiDienTheoThanhToanRequest requestBody);

    @Operation(summary = "API kiểm tra chuẩn hóa dữ liệu - phiếu khảo sát B2A", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/ChuanhoaKhacHangB2A", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<?>> ChuanhoaKhacHangB2A(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ChuanhoaKhacHangB2ARequest requestBody);

    @Operation(summary = "API Lấy danh sách tác động mạng lưới, sự cố", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/DanhSachSuCoTacDongMangLuoi", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> DanhSachSuCoTacDongMangLuoi(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DanhSachSuCoTacDongMangLuoiRequest requestBody);

    @Operation(summary = "API Lấy danh sách thuê bao ảnh hưởng của 1 sự cố", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/DanhSachThueBaoBiAnhHuong", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> DanhSachThueBaoBiAnhHuong(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DanhSachThueBaoBiAnhHuongRequest requestBody);

    @Operation(summary = "Danh sách sự cố theo mã tB", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/DanhSachSuCoTheoMaTB", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> DanhSachSuCoTheoMaTB(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DanhSachSuCoTheoMaTBRequest requestBody);

    @Operation(summary = "Danh sách Dịch vụ viễn thông", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/DanhSachDichVuVienThong", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> DanhSachDichVuVienThong(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DanhSachDichVuVienThongRequest requestBody);

    @Operation(summary = "Danh sách Loại hình TB", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/DanhSachLoaiHinhThueBao", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> DanhSachLoaiHinhThueBao(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DanhSachLoaiHinhThueBaoRequest requestBody);

    @Operation(summary = "Hiển thị thông số kỹ thuật", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/HienThiThongSoKyThuat", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> HienThiThongSoKyThuat(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody HienThiThongSoKyThuatRequest requestBody);

    @Operation(summary = "Lấy danh sách thuê bao lắp kèm", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/DanhSachThueBaoLapKem", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> DanhSachThueBaoLapKem(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DanhSachThueBaoLapKemRequest requestBody);

    @Operation(summary = "Lấy danh sách lịch sử hỏng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/DanhSachLichSuHong", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> DanhSachLichSuHong(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DanhSachLichSuHongRequest requestBody);


    @Operation(summary = "Lấy nguyên nhân hỏng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/NguyenNhanHong", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> NguyenNhanHong(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody NguyenNhanHongRequest requestBody);

    @Operation(summary = "Lấy chi tiêt tồn máy hỏng", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/ChiTietTonMayHong", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> ChiTietTonMayHong(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ChiTietTonMayHongRequest requestBody);

    @Operation(summary = "Lấy nhân viên thực hiện", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/NhanVienThucHien", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> NhanVienThucHien(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody NhanVienThucHienRequest requestBody);

    @Operation(summary = "Cập nhật mã vật tư ", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/CapNhatVatTuSuDungVt", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> CapNhatVatTuSuDungVt(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody FN_CAPNHAT_VATTU_SUDUNG_VT_REQUEST requestBody);

    @Operation(summary = "Cập nhật serial vật tư", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/CapNhatDoiTbiSerial", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> CapNhatDoiTbiSerial(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody FN_CAPNHAT_DOI_TBI_SERIAL_REQUEST requestBody);

    @Operation(summary = "SntogponsnWs", tags = {"CSKH_VNPT"})
    @RequestMapping(value = "/SntogponsnWs", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> SntogponsnWs(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody SNTOGPONSN_WS_REQUEST requestBody);
}
