package io.swagger.api;


import io.swagger.model.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Validated
@RequestMapping(value = "/cdsDiaBan/hcm")
public interface CDSDiaBanHcmApi {

    /*
     * Create by khanh
     * TTVT_2_KT_<001>
     * lấy số lượng thuê bao phát sinh cước còn nợ cước
     * DB: Lương Hoàng Phúc
     * App VNPTEmployee
     * 14/11/2022
     */
    @Operation(summary = "CSKH - Số lượng thuê bao còn nợ cước ở kỳ phát sinh", tags = {"CDS-DIABAN-HCM"})
    @RequestMapping(value = "/laySlTBPscConNoCuoc", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> laySlTBPscConNoCuoc(@Parameter(in = ParameterIn.QUERY, description = "Kỳ cước", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_kycuoc", required = false) String p_kycuoc,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "Loại hình", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_loaihinh", required = false) String p_loaihinh,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "Kiểu báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_kieubc", required = false) String p_kieubc,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "makv", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_makv", required = false) String p_makv,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "User", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_user", required = false) String p_user);


    /*
     * Create by khanhnv.hcm
     * Tạm ngưng
     * Lấy số lượng thuê bao hủy từ trạng thái hoạt động
     * App VNPTEmployee
     * 15/11/2022
     */
    @Operation(summary = "CSKH - Số lượng thuê bao hủy từ trạng thái hoạt động", tags = {"CDS-DIABAN-HCM"})
    @RequestMapping(value = "/laySlTBHuyDangHoatDong", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> laySlTBHuyDangHoatDong(
            @Parameter(in = ParameterIn.QUERY, description = "Loại báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_loaibc", required = false) String p_loaibc,
            @Parameter(in = ParameterIn.QUERY, description = "Loại hình", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_loaihinh", required = false) String p_loaihinh,
            @Parameter(in = ParameterIn.QUERY, description = "Ngày bắt đầu lấy báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_fdate", required = false) String p_fdate,
            @Parameter(in = ParameterIn.QUERY, description = "Ngày kết thúc lấy báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_tdate", required = false) String p_tdate,
            @Parameter(in = ParameterIn.QUERY, description = "User", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_user", required = false) String p_user,
            @Parameter(in = ParameterIn.QUERY, description = "p_makv", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_makv", required = false) String p_makv);


    /*
     * Create by khanhnv.hcm
     * Tạm ngưng
     * Lấy số lượng thuê bao dừng do yêu cầu
     * App VNPTEmployee
     * 15/11/2022
     */
    @Operation(summary = "CSKH - Lấy số lượng thuê bao dừng do yêu cầu", tags = {"CDS-DIABAN-HCM"})
    @RequestMapping(value = "/laySlTBDungDoYeuCau", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> laySlTBDungDoYeuCau(
            @Parameter(in = ParameterIn.QUERY, description = "Loại báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_loaibc", required = false) String p_loaibc,
            @Parameter(in = ParameterIn.QUERY, description = "Loại hình", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_loaihinh", required = false) String p_loaihinh,
            @Parameter(in = ParameterIn.QUERY, description = "Ngày bắt đầu lấy báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_fdate", required = false) String p_fdate,
            @Parameter(in = ParameterIn.QUERY, description = "Ngày kết thúc lấy báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_tdate", required = false) String p_tdate,
            @Parameter(in = ParameterIn.QUERY, description = "User", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_user", required = false) String p_user,
            @Parameter(in = ParameterIn.QUERY, description = "p_makv", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_makv", required = false) String p_makv);


    /*
     * Create by khanhnv.hcm
     * TTVT_2_KT_<002>
     * Lấy tổng quan địa bàn kĩ thuật
     * Lấy số lượng thuê bao theo từng dịch vụ, doanh thu thuê bao theo từng dịch vụ, khách hàng
     * doanh nghiệp , khách hàng cá nhân, số lượng thuê bao trả trước, thuê bao trả sau, sl thuê bao
     * Db PKT:Lê Thị Thu Hương
     * App VNPTEmployee
     * 15/11/2022
     */
    @Operation(summary = "CSKH - Lấy tổng quan địa bàn kĩ thuật", tags = {"CDS-DIABAN-HCM"})
    @RequestMapping(value = "/layTongQuanDianBanKyThuat", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> layTongQuanDianBanKyThuat(@Parameter(in = ParameterIn.QUERY, description = "user", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_user", required = false) String p_user,
                                                                                      @Parameter(in = ParameterIn.QUERY, description = "Tháng lấy dữ liệu", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_thang", required = false) String p_thang);


    /*
     * Create by khanh
     * TTVT_2_KT_<003>
     * lấy chi tiết gia hạn thuê bao trả trước
     * Code Db: Lê Bùi Hải Nam
     * App VNPTEmployee
     * 18/11/2022
     */
    @Operation(summary = "CSKH - Lấy chi tiết gia hạn thuê bao trả trước", tags = {"CDS-DIABAN-HCM"})
    @RequestMapping(value = "/layChiTietGiaHanTbTraTruoc", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> layChiTietGiaHanTbTraTruoc(@Parameter(in = ParameterIn.QUERY, description = "Tháng", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_thang", required = false) String p_thang,
                                                                                       @Parameter(in = ParameterIn.QUERY, description = "Tháng lấy dữ liệu", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_loaihinh", required = false) String p_loaihinh,
                                                                                       @Parameter(in = ParameterIn.QUERY, description = "Tháng lấy dữ liệu", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_kieubc", required = false) String p_kieubc,
                                                                                       @Parameter(in = ParameterIn.QUERY, description = "Tháng lấy dữ liệu", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_makv", required = false) String p_makv,
                                                                                       @Parameter(in = ParameterIn.QUERY, description = "Tháng lấy dữ liệu", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_user", required = false) String p_user);


    /*
     * Create by khanh
     * TTVT_2_KT_<005>
     * Lấy thông tin chỉ số phát triển (Db code by Nguyen Van Khanh)
     * Bao gồm các chỉ số : PSC, KPSC,PTM, Đặc cọc
     * App VNPTEmployee
     * 21/11/2022
     */
    @Operation(summary = "Lấy thông tin chỉ số phát triển (Db code by Nguyen Van Khanh)", tags = {"CDS-DIABAN-HCM"})
    @RequestMapping(value = "/layThongTinChiSoPhatTrien", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> layThongTinChiSoPhatTrien(@Parameter(in = ParameterIn.QUERY, description = "Tháng", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_thang", required = false) String p_thang,
                                                                                      @Parameter(in = ParameterIn.QUERY, description = "User", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_user", required = false) String p_user);

    /*
     * Create by khanh.hcm
     * TTVT_2_KT_<006>
     * Lấy thông tin chỉ số báo hỏng theo ô địa bàn
     * Các TH đếm: 1 Số lượng thuê bao trong ô, 2 số lần báo hỏng theo ô, 3 số lần lặp báo hỏng < 29 ngày
     * Code db: PKT: Lê Thị Thu Hương(Edit by Lương Hoàng Phúc)
     * App VNPTEmployee
     * 25/11/2022
     */
    @Operation(summary = "CSKH - Lấy thông tin chỉ số báo hỏng", tags = {"CDS-DIABAN-HCM"})
    @RequestMapping(value = "/layThongTinChiSoBaoHong", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<?>> layThongTinChiSoBaoHong(
            @Parameter(in = ParameterIn.QUERY, description = "Kiểu báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_kieubc", required = false) Number p_kieubc,
            @Parameter(in = ParameterIn.QUERY, description = "Loại hình", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_loaihinh", required = false) String p_loaihinh,
            @Parameter(in = ParameterIn.QUERY, description = "Tháng lấy báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_thangbc", required = false) String p_thangbc,
            @Parameter(in = ParameterIn.QUERY, description = "User", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_user", required = false) String p_user,
            @Parameter(in = ParameterIn.QUERY, description = "Mã khu vực", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_makv", required = false) String p_makv);

    /*
     * Create by khanhnv.hcm
     * TTVT_2_KT_<004>
     * Lấy tổng hợp và chi tiết thuê bao mở chêm mở dùng
     * Code db by Nguyễn Văn Khánh
     * App VNPTEmployee
     * 28/11/2022
     */
    @Operation(summary = "CSKH - Lấy thông tin thuê bao mở chêm/ mở dừng", tags = {"CDS-DIABAN-HCM"})
    @RequestMapping(value = "/laySlThueBaoMoChemMoTD", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> laySlThueBaoMoChemMoTD(
            @Parameter(in = ParameterIn.QUERY, description = "Kiểu báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_kieubc", required = false) Number p_kieubc,
            @Parameter(in = ParameterIn.QUERY, description = "Loại hình", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_loaihinh", required = false) Number p_loaihinh,
            @Parameter(in = ParameterIn.QUERY, description = "Tháng lấy bc", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_thang", required = false) String p_thang,
            @Parameter(in = ParameterIn.QUERY, description = "User", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_user", required = false) String p_user,
            @Parameter(in = ParameterIn.QUERY, description = "p_makv", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_makv", required = false) String p_makv);

    /*
     * Create by khanhnv.hcm
     * TTVT_2_KT_<007>
     * Lấy tổng hợp và chi tiết thuê bao mở chêm mở dùng
     * Code db by Lương Hoàng Phúc
     * App VNPTEmployee
     * 28/11/2022
     */
    @Operation(summary = "CSKH - Thống kê thực tăng", tags = {"CDS-DIABAN-HCM"})
    @RequestMapping(value = "/thongKeThucTang", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> thongKeThucTang(
            @Parameter(in = ParameterIn.QUERY, description = "Kiểu báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_kieubc", required = false) Number p_kieubc,
            @Parameter(in = ParameterIn.QUERY, description = "Loại hình", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_loaihinh", required = false) String p_loaihinh,
            @Parameter(in = ParameterIn.QUERY, description = "Ngày bắt đầu lấy báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_kycuoc", required = false) String p_kycuoc,
            @Parameter(in = ParameterIn.QUERY, description = "User", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_user", required = false) String p_user,
            @Parameter(in = ParameterIn.QUERY, description = "p_makv", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_makv", required = false) String p_makv);

    /*
     * Create by khanhnv.hcm
     * TTVT_2_KT_<008>
     * Thống kê hợp lực địa bàn
     * Code db by Nguyễn Thị Tuyết
     * App VNPTEmployee
     * 07/12/2022
     */
    @Operation(summary = "CSKH - Thống kê thực tăng", tags = {"CDS-DIABAN-HCM"})
    @RequestMapping(value = "/thongKeHopLucDiaBan", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> thongKeHopLucDiaBan(
            @Parameter(in = ParameterIn.QUERY, description = "Kiểu báo cáo", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_kieubc", required = false) Number p_kieubc,
            @Parameter(in = ParameterIn.QUERY, description = "Loại hình", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_loaihinh", required = false) String p_loaihinh,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_tungay", required = false) String p_tungay,
            @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_denngay", required = false) String p_denngay,
            @Parameter(in = ParameterIn.QUERY, description = "User", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_user", required = false) String p_user,
            @Parameter(in = ParameterIn.QUERY, description = "p_makv", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_makv", required = false) String p_makv);


    /*
     * Create by khanh
     * TTVT_2_KT_<009>
     * lấy danh sách thuê bao chêm dừng
     * DB: Nguyễn Văn Khánh
     * App VNPTEmployee
     * 21/11/2023
     */
    @Operation(summary = "CSKH - Lấy số lượng thuê bao chêm dừng trong tháng", tags = {"CDS-DIABAN-HCM"})
    @RequestMapping(value = "/sp_LayThueBaoChemDungTrongThang", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Map<String, Object>>>> sp_LayThueBaoChemDungTrongThang(@Parameter(in = ParameterIn.QUERY, description = "Người dùng", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_user", required = false) String p_user,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "Loại hình", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_loaihinh", required = false) Number p_loaihinh,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "Kiểu bc", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_kieubc", required = false) Number p_kieubc,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "Mã khu vực", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_makv", required = false) String p_makv);

}