package io.swagger.api;

import io.swagger.model.dto.DanhMuc;
import io.swagger.model.dto.ThongTinMangNet;
import io.swagger.model.dto.cdsdiaban.ChiTietKR001_007;
import io.swagger.model.dto.cdsdiaban.Kri;
import io.swagger.model.dto.cdsdiaban.TongHopKR001_007;
import io.swagger.model.response.BaseResponse;
import io.swagger.model.response.cdsdiaban.*;
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

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Validated
@RequestMapping(value = "/cdsDiaBan")
public interface CDSDiaBanApi {

    @Operation(summary = "CSKH - Lấy danh mục KRI được phép xem", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dsKri", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Kri>>> dsKri();

    @Operation(summary = "CSKH - Lấy dữ liệu Dashboard tổng hợp", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dsDashboardTH", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDashboardTH>>> dsDashboardTH();

    @Operation(summary = "CSKH - Lấy danh mục KRI được phép xem", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dsKriV2", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Kri>>> dsKriV2();

    @Operation(summary = "TTKD_KD_001 - TTKD_KD_007: Lấy danh sách dịch vụ", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dsDichVu", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDichVu();

    @Operation(summary = "TTKD_KD_001 - TTKD_KD_007: Tổng hợp dịch vụ", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_001_007", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TongHopKR001_007>>> ttkd_kd_001_007_TongHop(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                                 @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_001 - TTKD_KD_007: Chi tiết dịch vụ", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/chiTiet_ttkd_kd_001_007", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<ChiTietKR001_007>>> ttkd_kd_001_007_ChiTiet(@Parameter(in = ParameterIn.QUERY, description = "Dịch vụ", required = true, schema = @Schema()) @Valid @RequestParam(value = "service", required = false) String service,
                                                                                 @Parameter(in = ParameterIn.QUERY, description = "Account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = false) String account,
                                                                                 @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                                 @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_013: Thống kê lịch sử hẹn xử lý", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_013", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_013>>> ttvt_kt_013(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_004: Thống kê theo dõi hợp đồng dùng thử", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_004", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_004>>> ttvt_kt_004(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_043: Thống kê theo dõi số lượng phiếu khảo sát khách hàng B2A", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_043", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_043>>> ttvt_kt_043(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_016: Quản lý thông tin báo hỏng của thuê bao từ 2 lần trở lên trong vòng 30 ngày", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_016", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_016>>> ttvt_kt_016(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_018: Quản lý các thuê bao hỏng còn tồn trên hệ thống", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_018", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_018>>> ttvt_kt_018(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_006: Quản lý theo dõi thống kê chi tiết máy lắp đặt mới", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_006", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_006>>> ttvt_kt_006(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);


    @Operation(summary = "TTVT_KT_006: Quản lý theo dõi thống kê chi tiết máy lắp đặt mới", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_006_v2", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_006>>> ttvt_kt_006_v2(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                   @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate,
                                                                   @Parameter(in = ParameterIn.QUERY, description = "Dịch vụ", required = true, schema = @Schema()) @Valid @RequestParam(value = "service", required = false) String service);


    @Operation(summary = "TTVT_KT_031: Quản lý theo dõi bảo dưỡng mạng ngoại vi", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_031", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_031>>> ttvt_kt_031(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_011: Quản lý thống kê thời gian xử lý lắp đặt mới dịch vụ theo nhân viên DVVT", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_011", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_011>>> ttvt_kt_011(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_TTKT_005: Quản lý theo dõi thống kê số lượng phiếu xử lý sự cố mạng ngoại vi", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_ttkt_005", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_005>>> ttvt_ttkt_005(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_020: Quản lý thống kê thời gian xử lý dịch vụ theo nhân viên DVVT", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_020", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_020>>> ttvt_kt_020(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_033: Quản lý theo dõi thống kê số lượng phiếu báo hỏng giao, phiếu báo hỏng thực hiện", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_033", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_033>>> ttvt_kt_033(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_033: Quản lý theo dõi thống kê số lượng phiếu báo hỏng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_024", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_024>>> ttvt_kt_024(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_035: Quản lý theo dõi thống kê số lượng phiếu báo hỏng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_035", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_035>>> ttvt_kt_035(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_TTKT_004: Quản lý theo dõi thống kê số lượng phiếu bảo dưỡng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_ttkt_004", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_004>>> ttvt_ttkt_004(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_TTKT_003: Quản lý theo dõi thống kê số lượng phiếu báo hỏng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_ttkt_003", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_003>>> ttvt_ttkt_003(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_041: Quản lý theo dõi thống kê số lượng phiếu thu thập thông tin thị trường của đối thủ", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_041", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_041>>> ttvt_kt_041(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_007: Quản lý các thuê bao lắp đặt mới còn tồn trên hệ thống", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_007", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_007>> ttvt_kt_007(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_007: Quản lý các thuê bao lắp đặt mới còn tồn trên hệ thống", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_007_v2", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_007>> ttvt_kt_007_v2(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                             @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate,
                                                             @Parameter(in = ParameterIn.QUERY, description = "Dịch vụ", required = true, schema = @Schema()) @Valid @RequestParam(value = "service", required = false) String service);


    @Operation(summary = "TTVT_KT_012: Quản lý theo dõi thống kê số lượng phiếu báo hỏng được giao, phiếu đã xử lý", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_012", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_012>>> ttvt_kt_012(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_008: Thuê bao trả sau cài đặt My VNPT cần được thông báo cước hàng tháng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_008", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_008>> ttkd_kd_008();

    @Operation(summary = "TTKD_KD_009: Thuê bao trả sau cài đặt My VNPT cần được thông báo cước hàng tháng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_009", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_009>> ttkd_kd_009(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_010: Thuê bao trả sau cài đặt My VNPT cần được thông báo cước hàng tháng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_010", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_010>> ttkd_kd_010(@Parameter(in = ParameterIn.QUERY, description = "Chu kỳ", required = true, schema = @Schema()) @Valid @RequestParam(value = "chuKy", required = false) String chuKy);

    @Operation(summary = "TTVT_TTKT_002: Quản lý theo dõi thống kê số lượng phiếu lắp đặt mới", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_ttkt_002", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_002>>> ttvt_ttkt_002(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_TTKT_006: Quản lý theo dõi thống kê số lượng phiếu chuyển tổ", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_ttkt_006", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_006>>> ttvt_ttkt_006(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_010: Quản lý chi tiết các thuê bao đến hạn/quá hạn lắp đặt (xét quá hạn theo thời gian xử lý; hẹn lắp đặt)", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_010", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_010>>> ttvt_kt_010(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_018: Quản lý theo dõi kết quả thu cước, thù lao.", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_018", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_018>> ttkd_kd_018(@Parameter(in = ParameterIn.QUERY, description = "kieu", required = true, schema = @Schema()) @Valid @RequestParam(value = "kieu", required = false) String kieu,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "ngay", required = false) String ngay);

    @Operation(summary = "TTKD_KD_019: Quản lý theo dõi kết quả thu cước, thù lao.", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_019", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_019>> ttkd_kd_019(@Parameter(in = ParameterIn.QUERY, description = "Kỳ hóa đơn", required = true, schema = @Schema()) @Valid @RequestParam(value = "kyHD", required = false) String kyHD);

    @Operation(summary = "TTVT_TTKT_001: Quản lý theo dõi thống kê tình hình báo cáo/kiểm tra CCDC", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_ttkt_001", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_001>>> ttvt_ttkt_001(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_AM_002: Theo dõi thông tin thu cước", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_am_002", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTKD_AM_002>>> ttkd_am_002();

    @Operation(summary = "TTVT_KT_039: Theo dõi thống kê kết quả thu thập thông tin hạ tầng mạng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_039", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_039>>> ttvt_kt_039(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_019: Quản lý chi tiết các thuê bao đến hạn/quá hạn lắp đặt", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_019", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_019>>> ttvt_kt_019(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_023: Theo dõi thống kê đánh giá của khách hàng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_023", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_023>>> ttvt_kt_023(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_029: Theo dõi thống kê phiếu sự cố được giao, phiếu đã được xử lý", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_029", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_029>>> ttvt_kt_029(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_030: Theo dõi thống kê công tác bảo dưỡng dịch vụ", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_030", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_030>>> ttvt_kt_030(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_015: Theo dõi kết quả thực hiện thác nghiệp", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_015", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTKD_KD_015>>> ttkd_kd_015(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_005: Theo dõi thống kê thiết bị phải được dán ID chi tiết, quản lý Serial.", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_005", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_005>> ttvt_kt_005(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_026: Theo dõi thống kê thực hiện Thu hồi thiết bị khi thuê bao rời mạng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_026", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_026>>> ttvt_kt_026(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_027: Theo dõi thống kê thực hiện Thu hồi thiết bị khi thuê bao rời mạng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_027", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_027>> ttvt_kt_027(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_032: Theo dõi thống kê số lượng phiếu thi công mới, phiếu giao, phiếu thực hiện", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_032", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_032>> ttvt_kt_032(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_040: Theo dõi thống kê kết quả thu thập thông tin hạ tầng mạng của đối thủ", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_040", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_040>>> ttvt_kt_040(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_017: Theo dõi, đánh giá để xác định việc sử dụng, phân loại theo từng mục đích tương tác MyTV OTT", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_017", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_017>> ttkd_kd_017(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_017: Check sử dụng MyTV OTT", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_017_checkMyTVOTT", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_017_MyTVOTT>> ttkd_kd_017_checkMyTVOTT(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                               @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_011 - TTKD_KD_014: Lấy danh sách dịch vụ CSKH", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dsDichVuCSKH", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDichVuCSKH();

    @Operation(summary = "TTKD_KD_011_014: Chăm sóc khách hàng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_011_014", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_011_014>> ttkd_kd_011_014(@Parameter(in = ParameterIn.QUERY, description = "Dịch vụ", required = true, schema = @Schema()) @Valid @RequestParam(value = "service", required = false) String service,
                                                                  @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                  @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "Ghi log truy cập", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/logKri", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<Void>> logKri(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri);

    @Operation(summary = "TTVT_KT_044 + TTKD_KD_023 + TTVT_TTKT_008: Dữ liệu địa bàn - Tổng quát hủy", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/tongQuat", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanTongQuatHuy>>> dlDiaBanTongQuat(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                             @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_044 + TTKD_KD_023 + TTVT_TTKT_008: Dữ liệu địa bàn - Nguyên nhân hủy", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/nguyenNhanHuy", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanNguyenNhanHuy>>> dlDiaBanNguyenNhanHuy(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_044 + TTKD_KD_023 + TTVT_TTKT_008: Dữ liệu địa bàn - Lý do hủy", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/lyDoHuy", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanLyDoHuy>>> dlDiaBanLyDoHuy(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                        @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_045 + TTKD_KD_024 + TTVT_TTKT_009: Dữ liệu địa bàn - Tổng quát ngưng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/tongQuatNgung", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanTongQuatNgung>>> dlDiaBanTongQuatNgung(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_045 + TTKD_KD_024 + TTVT_TTKT_009: Dữ liệu địa bàn - Nguyên nhân ngưng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/nguyenNhanNgung", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanNguyenNhanNgung>>> dlDiaBanNguyenNhanNgung(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                                        @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_045 + TTKD_KD_024 + TTVT_TTKT_009: Dữ liệu địa bàn - Lý do ngưng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/lyDoNgung", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanLyDoNgung>>> dlDiaBanLyDoNgung(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                            @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_TTKT_010: Tổ trưởng tổ kỹ thuật theo dõi trắc nghiệm đầu giờ", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/ttvt_ttkt_010", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_010>>> dlTTVT_TTKT_010(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                      @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_TTKT_011: Tổ trưởng tổ kỹ thuật theo dõi thống kê tình hình cập nhật Serial thiết bị khi lắp đặt cho khách hàng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/ttvt_ttkt_011", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_011>>> dlTTVT_TTKT_011(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                      @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_TTKT_013,TTVT_KT_047,TTKD_KD_026 : Chỉ số theo dõi khiếu nại của thuê bao BRCĐ", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/khieunai_brcd", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<khieunai_brcd>>> dl_khieunai_brcd(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                       @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);


    @Operation(summary = "TTVT_TTKT_014,TTVT_KT_048,TTKD_KD_027 : Chỉ số theo dõi thuê bao BRCĐ dự kiến không phát sinh cước", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/brcd_khong_psc", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<BRCD_KHONG_PSC>>> dlBRCDKhongPSC(@Parameter(in = ParameterIn.QUERY, description = "Tháng (yyyyMM)", required = true, schema = @Schema()) @Valid @RequestParam(value = "thang", required = false) String thang);


    @Operation(summary = "TTVT_KT_003: 100% nhân viên kỹ thuật sử dụng OneApp là công cụ để tra cứu số liệu vùng phủ sóng của VNPT và của đối thủ - [App] - Vẽ giao diện, tích hợp biểu đồc", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/net/listProvince", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> listProvince();

    @Operation(summary = "TTVT_KT_003: 100% nhân viên kỹ thuật sử dụng OneApp là công cụ để tra cứu số liệu vùng phủ sóng của VNPT và của đối thủ - [App] - Vẽ giao diện, tích hợp biểu đồc", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/net/positionCoverage", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<DanhMuc>> positionCoverage(@Parameter(in = ParameterIn.QUERY, description = "vĩ độ", required = true, schema = @Schema()) @Valid @RequestParam(value = "latitude", required = false) String latitude,
                                                           @Parameter(in = ParameterIn.QUERY, description = "kinh độ", required = true, schema = @Schema()) @Valid @RequestParam(value = "longitude", required = false) String longitude,
                                                           @Parameter(in = ParameterIn.QUERY, description = "Tinh", required = true, schema = @Schema()) @Valid @RequestParam(value = "tinhtp_id", required = false) String tinhtp_id);

    @Operation(summary = "TTVT_TTKT_012,TTVT_KT_046,TTKD_KD_025 : Chỉ số thuê bao BRCĐ không phát sinh lưu lượng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/brcd_khong_psll", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<BRCD_KHONG_PSLL>> dlBrcdKhongPsll(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                  @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_003: 100% nhân viên kỹ thuật sử dụng OneApp là công cụ để tra cứu số liệu vùng phủ sóng của VNPT và của đối thủ - [App] - Vẽ giao diện, tích hợp biểu đồc", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/net/coverageByDistance", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<ThongTinMangNetRespones>> coverageByDistance(@Parameter(in = ParameterIn.QUERY, description = "vĩ độ", required = true, schema = @Schema()) @Valid @RequestParam(value = "latitude", required = false) String latitude,
                                                                             @Parameter(in = ParameterIn.QUERY, description = "kinh độ", required = true, schema = @Schema()) @Valid @RequestParam(value = "longitude", required = false) String longitude,
                                                                             @Parameter(in = ParameterIn.QUERY, description = "Bán kính", required = true, schema = @Schema()) @Valid @RequestParam(value = "distance", required = false) String distance);

    @Operation(summary = "Danh sách nhân viên", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/dsNhanVien", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsNhanVien();

    @Operation(summary = "TTVT_KT_061 - Điều chỉnh chỉ tiêu TTVT_KT_045 - Bổ sung báo cáo số liệu chi tiết", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/dsChiTietNgungHuy", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<ThongTinChiTietTbNgungHuy>>> dsChiTietNgungHuy(@Parameter(in = ParameterIn.QUERY, description = "Ngày bắt đầu lấy dữ liệu", required = true, schema = @Schema()) @Valid @RequestParam(value = "vtungay", required = false) String vtungay,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "Ngày kết thúc lấy dữ liệu", required = true, schema = @Schema()) @Valid @RequestParam(value = "vdenngay", required = false) String vdenngay,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "Phân loại", required = true, schema = @Schema()) @Valid @RequestParam(value = "vloai", required = false) String vloai,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "Mã nhân viên lấy dữ liệu", required = true, schema = @Schema()) @Valid @RequestParam(value = "vmanvtimkiem", required = false) String vmanv_timkiem,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "1 - tất cả, 0 - cá nhân", required = true, schema = @Schema()) @Valid @RequestParam(value = "vis_all", required = false) String vis_all,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "Mã thuê bao", required = true, schema = @Schema()) @Valid @RequestParam(value = "vma_tb", required = false) String vma_tb,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "Mã thanh toán", required = true, schema = @Schema()) @Valid @RequestParam(value = "vma_tt", required = false) String vma_tt);

    @Operation(summary = "TTVT_KT_063 - Điều chỉnh chỉ tiêu TTKD_KD_019 - Bổ sung báo cáo số liệu chi tiết", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/dsThuCuocGachNoChiTiet", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<ThongTinChiTietThuCuocGachNo>>> dsThuCuocGachNoChiTiet(@Parameter(in = ParameterIn.QUERY, description = "Kỳ hóa đơn", required = true, schema = @Schema()) @Valid @RequestParam(value = "kyhoadon", required = false) String kyhoadon,
                                                                                            @Parameter(in = ParameterIn.QUERY, description = "Mã thuê bao", required = true, schema = @Schema()) @Valid @RequestParam(value = "ma_tb", required = false) String ma_tb,
                                                                                            @Parameter(in = ParameterIn.QUERY, description = "Mã thanh toán", required = true, schema = @Schema()) @Valid @RequestParam(value = "ma_tt", required = false) String ma_tt,
                                                                                            @Parameter(in = ParameterIn.QUERY, description = "Mã người dùng cần tìm kiếm", required = true, schema = @Schema()) @Valid @RequestParam(value = "mand_timkiem", required = false) String mand_timkiem,
                                                                                            @Parameter(in = ParameterIn.QUERY, description = "Tất cả", required = true, schema = @Schema()) @Valid @RequestParam(value = "is_all", required = false) String is_all);

    @Operation(summary = "TTVT_KT_065 - Điều chỉnh chỉ tiêu TTVT_KT_048 - Bổ sung báo cáo số liệu chi tiết", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/dsBRCDkhongPSCchiTiet", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<BRCD_KHONG_PSC_CT>>> dsBRCDkhongPSCchiTiet(@Parameter(in = ParameterIn.QUERY, description = "Kỳ hóa đơn", required = true, schema = @Schema()) @Valid @RequestParam(value = "kyhoadon", required = false) String kyhoadon,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "Mã thuê bao", required = true, schema = @Schema()) @Valid @RequestParam(value = "ma_tb", required = false) String ma_tb,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "Mã thanh toán", required = true, schema = @Schema()) @Valid @RequestParam(value = "ma_tt", required = false) String ma_tt,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "Loại", required = true, schema = @Schema()) @Valid @RequestParam(value = "loai", required = false) String loai,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "Mã nhân viên tìm kiếm", required = true, schema = @Schema()) @Valid @RequestParam(value = "manv_timkiem", required = false) String manv_timkiem,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "Tất cả hay không", required = true, schema = @Schema()) @Valid @RequestParam(value = "is_all", required = false) String is_all);

    @Operation(summary = "TTVT_KT_064 - Điều chỉnh chỉ tiêu TTVT_KT_046 - Bổ sung báo cáo số liệu chi tiết", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/dlDiaBan/dsBRCDkhongPSLLchiTiet", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<BRCD_KHONG_PSLL_CT>>> dsBRCDkhongPSLLchiTiet(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tungay", required = false) String tungay,
                                                                                  @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "denngay", required = false) String denngay,
                                                                                  @Parameter(in = ParameterIn.QUERY, description = "Mã thuê bao", required = true, schema = @Schema()) @Valid @RequestParam(value = "ma_tb", required = false) String ma_tb,
                                                                                  @Parameter(in = ParameterIn.QUERY, description = "Mã thanh toán", required = true, schema = @Schema()) @Valid @RequestParam(value = "ma_tt", required = false) String ma_tt,
                                                                                  @Parameter(in = ParameterIn.QUERY, description = "Kiểu", required = true, schema = @Schema()) @Valid @RequestParam(value = "kieu", required = false) String kieu,
                                                                                  @Parameter(in = ParameterIn.QUERY, description = "Mã nhân viên tìm kiếm", required = true, schema = @Schema()) @Valid @RequestParam(value = "manv_timkiem", required = false) String manv_timkiem,
                                                                                  @Parameter(in = ParameterIn.QUERY, description = "Tất cả hay không", required = true, schema = @Schema()) @Valid @RequestParam(value = "is_all", required = false) String is_all);

    @Operation(summary = "TTVT_KT_052 - Bảo dưỡng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_052", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_052>> ttvt_kt_052();

    @Operation(summary = "TTVT_KT_056 - Bảo dưỡng hạ tầng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_056", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_052>> ttvt_kt_056();

    @Operation(summary = "TTKD_KD_029 - Tăng trưởng Phát triển mới dịch vụ", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_029", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_029>> ttkd_kd_029(@Parameter(in = ParameterIn.QUERY, description = "Tháng", required = true, schema = @Schema()) @Valid @RequestParam(value = "thang", required = true) String thang);

    @Operation(summary = "TTKT_KD_030 -  Gia hạn: Fiber,TTKT_KD_032 - Gia hạn: MyTV,TTKT_KD_034 - Gia hạn: Cố định,TTKT_KD_035 - Gia hạn: Thuê bao CNTT", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_030_032_034_035", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTKD_KD_030_032_034_035>>> ttkd_kd_030_032_034_035(@Parameter(in = ParameterIn.QUERY, description = "Dịch vụ", required = true, schema = @Schema()) @Valid @RequestParam(value = "dichvu", required = true) String dichvu,
                                                                                        @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = true) String fdate,
                                                                                        @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = true) String tdate);

    @Operation(summary = "TTVT_KT_054 - Thu hồi TBĐC", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_054", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_054>> ttvt_kt_054(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = true) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = true) String tdate);

    @Operation(summary = "TTVT_KT_053 - Xác minh thuê bao rời mạng", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttvt_kt_053", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_053>>> ttvt_kt_053(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = true) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = true) String tdate);

    @Operation(summary = "TTKT_KD_031 - Gia hạn: Fiber gia hạn đúng hạn, TTKT_KD_033 - Gia hạn: Mytv gia hạn đúng hạn", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_031_033", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTKD_KD_030_032_034_035>>> ttkd_kd_031_033(@Parameter(in = ParameterIn.QUERY, description = "Dịch vụ", required = true, schema = @Schema()) @Valid @RequestParam(value = "dichvu", required = true) String dichvu,
                                                                                @Parameter(in = ParameterIn.QUERY, description = "Tháng", required = true, schema = @Schema()) @Valid @RequestParam(value = "thang", required = true) String thang);

    @Operation(summary = "TTKD_KD_021 - 100% NVKD tư vấn khách hàng đăng ký gói và đánh giá được kết quả", tags = {"CDS-DIABAN"})
    @RequestMapping(value = "/ttkd_kd_021", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTKD_KD_021>>> ttkd_kd_021(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = true) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = true) String tdate);


}
