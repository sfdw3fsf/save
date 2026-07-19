package io.swagger.api;

import io.swagger.model.dto.DanhMuc;
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
@RequestMapping(value = "/cdsDiaBan/v2")
public interface CDSDiaBanV2Api {

    @Operation(summary = "CSKH - Lấy danh mục KRI được phép xem", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/dsKri", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<Kri>>> dsKri();

    @Operation(summary = "TTKD_KD_001 - TTKD_KD_007: Lấy danh sách dịch vụ", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/dsDichVu", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDichVu();

    @Operation(summary = "TTKD_KD_001 - TTKD_KD_007: Tổng hợp dịch vụ", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttkd_kd_001_007", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TongHopKR001_007>>> ttkd_kd_001_007_TongHop(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                                 @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_001 - TTKD_KD_007: Chi tiết dịch vụ", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/chiTiet_ttkd_kd_001_007", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<ChiTietKR001_007>>> ttkd_kd_001_007_ChiTiet(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
                                                                                 @Parameter(in = ParameterIn.QUERY, description = "Dịch vụ", required = true, schema = @Schema()) @Valid @RequestParam(value = "service", required = false) String service,
                                                                                 @Parameter(in = ParameterIn.QUERY, description = "Account", required = true, schema = @Schema()) @Valid @RequestParam(value = "account", required = false) String account,
                                                                                 @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                                 @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_013: Thống kê lịch sử hẹn xử lý", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_013", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_013>>> ttvt_kt_013(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_004: Thống kê theo dõi hợp đồng dùng thử", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_004", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_004>>> ttvt_kt_004(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_043: Thống kê theo dõi số lượng phiếu khảo sát khách hàng B2A", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_043", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_043>>> ttvt_kt_043(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_016: Quản lý thông tin báo hỏng của thuê bao từ 2 lần trở lên trong vòng 30 ngày", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_016", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_016>>> ttvt_kt_016(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_018: Quản lý các thuê bao hỏng còn tồn trên hệ thống", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_018", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_018>>> ttvt_kt_018(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_006: Quản lý theo dõi thống kê chi tiết máy lắp đặt mới", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_006", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_006>>> ttvt_kt_006(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_031: Quản lý theo dõi bảo dưỡng mạng ngoại vi", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_031", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_031>>> ttvt_kt_031(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_011: Quản lý thống kê thời gian xử lý lắp đặt mới dịch vụ theo nhân viên DVVT", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_011", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_011>>> ttvt_kt_011(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_TTKT_005: Quản lý theo dõi thống kê số lượng phiếu xử lý sự cố mạng ngoại vi", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_ttkt_005", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_005>>> ttvt_ttkt_005(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                  @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_020: Quản lý thống kê thời gian xử lý dịch vụ theo nhân viên DVVT", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_020", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_020>>> ttvt_kt_020(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_033: Quản lý theo dõi thống kê số lượng phiếu báo hỏng giao, phiếu báo hỏng thực hiện", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_033", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_033>>> ttvt_kt_033(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_033: Quản lý theo dõi thống kê số lượng phiếu báo hỏng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_024", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_024>>> ttvt_kt_024(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_035: Quản lý theo dõi thống kê số lượng phiếu báo hỏng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_035", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_035>>> ttvt_kt_035(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_TTKT_004: Quản lý theo dõi thống kê số lượng phiếu bảo dưỡng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_ttkt_004", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_004>>> ttvt_ttkt_004(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_TTKT_003: Quản lý theo dõi thống kê số lượng phiếu báo hỏng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_ttkt_003", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_003>>> ttvt_ttkt_003(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_041: Quản lý theo dõi thống kê số lượng phiếu thu thập thông tin thị trường của đối thủ", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_041", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_041>>> ttvt_kt_041(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_007: Quản lý các thuê bao lắp đặt mới còn tồn trên hệ thống", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_007", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_007>> ttvt_kt_007(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_012: Quản lý theo dõi thống kê số lượng phiếu báo hỏng được giao, phiếu đã xử lý", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_012", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_012>>> ttvt_kt_012(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_008: Thuê bao trả sau cài đặt My VNPT cần được thông báo cước hàng tháng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttkd_kd_008", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_008>> ttkd_kd_008(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri);

    @Operation(summary = "TTKD_KD_009: Thuê bao trả sau cài đặt My VNPT cần được thông báo cước hàng tháng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttkd_kd_009", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_009>> ttkd_kd_009(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_010: Thuê bao trả sau cài đặt My VNPT cần được thông báo cước hàng tháng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttkd_kd_010", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_010>> ttkd_kd_010(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Chu kỳ", required = true, schema = @Schema()) @Valid @RequestParam(value = "chuKy", required = false) String chuKy);

    @Operation(summary = "TTVT_TTKT_002: Quản lý theo dõi thống kê số lượng phiếu lắp đặt mới", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_ttkt_002", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_002>>> ttvt_ttkt_002(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_TTKT_006: Quản lý theo dõi thống kê số lượng phiếu chuyển tổ", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_ttkt_006", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_006>>> ttvt_ttkt_006(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_010: Quản lý chi tiết các thuê bao đến hạn/quá hạn lắp đặt (xét quá hạn theo thời gian xử lý; hẹn lắp đặt)", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_010", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_010>>> ttvt_kt_010(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_018: Quản lý theo dõi kết quả thu cước, thù lao.", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttkd_kd_018", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_018>> ttkd_kd_018(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "kieu", required = true, schema = @Schema()) @Valid @RequestParam(value = "kieu", required = false) String kieu,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "ngay", required = false) String ngay);

    @Operation(summary = "TTKD_KD_019: Quản lý theo dõi kết quả thu cước, thù lao.", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttkd_kd_019", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_019>> ttkd_kd_019(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Kỳ hóa đơn", required = true, schema = @Schema()) @Valid @RequestParam(value = "kyHD", required = false) String kyHD);

    @Operation(summary = "TTVT_TTKT_001: Quản lý theo dõi thống kê tình hình báo cáo/kiểm tra CCDC", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_ttkt_001", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_TTKT_001>>> ttvt_ttkt_001(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_AM_002: Theo dõi thông tin thu cước", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttkd_am_002", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTKD_AM_002>>> ttkd_am_002(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri);

    @Operation(summary = "TTVT_KT_039: Theo dõi thống kê kết quả thu thập thông tin hạ tầng mạng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_039", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_039>>> ttvt_kt_039(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_019: Quản lý chi tiết các thuê bao đến hạn/quá hạn lắp đặt", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_019", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_019>>> ttvt_kt_019(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_023: Theo dõi thống kê đánh giá của khách hàng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_023", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_023>>> ttvt_kt_023(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_029: Theo dõi thống kê phiếu sự cố được giao, phiếu đã được xử lý", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_029", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_029>>> ttvt_kt_029(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_030: Theo dõi thống kê công tác bảo dưỡng dịch vụ", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_030", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_030>>> ttvt_kt_030(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_015: Theo dõi kết quả thực hiện thác nghiệp", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttkd_kd_015", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTKD_KD_015>>> ttkd_kd_015(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_005: Theo dõi thống kê thiết bị phải được dán ID chi tiết, quản lý Serial.", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_005", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_005>> ttvt_kt_005(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_026: Theo dõi thống kê thực hiện Thu hồi thiết bị khi thuê bao rời mạng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_026", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_026>>> ttvt_kt_026(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_027: Theo dõi thống kê thực hiện Thu hồi thiết bị khi thuê bao rời mạng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_027", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_027>> ttvt_kt_027(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_032: Theo dõi thống kê số lượng phiếu thi công mới, phiếu giao, phiếu thực hiện", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_032", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTVT_KT_032>> ttvt_kt_032(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_040: Theo dõi thống kê kết quả thu thập thông tin hạ tầng mạng của đối thủ", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttvt_kt_040", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<TTVT_KT_040>>> ttvt_kt_040(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_017: Theo dõi, đánh giá để xác định việc sử dụng, phân loại theo từng mục đích tương tác MyTV OTT", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttkd_kd_017", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_017>> ttkd_kd_017(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_017: Check sử dụng MyTV OTT", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttkd_kd_017_checkMyTVOTT", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_017_MyTVOTT>> ttkd_kd_017_checkMyTVOTT(@Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                               @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTKD_KD_011 - TTKD_KD_014: Lấy danh sách dịch vụ CSKH", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/dsDichVuCSKH", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> dsDichVuCSKH();

    @Operation(summary = "TTKD_KD_011_014: Chăm sóc khách hàng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/ttkd_kd_011_014", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<TTKD_KD_011_014>> ttkd_kd_011_014(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Dịch vụ", required = true, schema = @Schema()) @Valid @RequestParam(value = "service", required = false) String service,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                          @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_044 + TTKD_KD_023 + TTVT_TTKT_008: Dữ liệu địa bàn - Tổng quát", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/dlDiaBan/tongQuat", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanTongQuatHuy>>> dlDiaBanTongQuat(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
                                                                             @Parameter(in = ParameterIn.QUERY, description = "Loại nhân viên", required = true, schema = @Schema()) @Valid @RequestParam(value = "loaiNV", required = false) String loaiNV,
                                                                             @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                             @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_044 + TTKD_KD_023 + TTVT_TTKT_008: Dữ liệu địa bàn - Nguyên nhân hủy", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/dlDiaBan/nguyenNhanHuy", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanNguyenNhanHuy>>> dlDiaBanNguyenNhanHuy(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Loại nhân viên", required = true, schema = @Schema()) @Valid @RequestParam(value = "loaiNV", required = false) String loaiNV,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_044 + TTKD_KD_023 + TTVT_TTKT_008: Dữ liệu địa bàn - Lý do hủy", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/dlDiaBan/lyDoHuy", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanLyDoHuy>>> dlDiaBanLyDoHuy(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Loại nhân viên", required = true, schema = @Schema()) @Valid @RequestParam(value = "loaiNV", required = false) String loaiNV,
                                                                        @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                        @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_045 + TTKD_KD_024 + TTVT_TTKT_009: Dữ liệu địa bàn - Tổng quát ngưng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/dlDiaBan/tongQuatNgung", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanTongQuatNgung>>> dlDiaBanTongQuatNgung(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Loại nhân viên", required = true, schema = @Schema()) @Valid @RequestParam(value = "loaiNV", required = false) String loaiNV,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                                    @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_045 + TTKD_KD_024 + TTVT_TTKT_009: Dữ liệu địa bàn - Nguyên nhân ngưng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/dlDiaBan/nguyenNhanNgung", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanNguyenNhanNgung>>> dlDiaBanNguyenNhanNgung(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Loại nhân viên", required = true, schema = @Schema()) @Valid @RequestParam(value = "loaiNV", required = false) String loaiNV,
                                                                                        @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                                        @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);

    @Operation(summary = "TTVT_KT_045 + TTKD_KD_024 + TTVT_TTKT_009: Dữ liệu địa bàn - Lý do ngưng", tags = {"CDS-DIABAN-V2"})
    @RequestMapping(value = "/dlDiaBan/lyDoNgung", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DLDiaBanLyDoNgung>>> dlDiaBanLyDoNgung(@Parameter(in = ParameterIn.QUERY, description = "Mã KRI", required = true, schema = @Schema()) @Valid @RequestParam(value = "kri", required = false) String kri,
            @Parameter(in = ParameterIn.QUERY, description = "Loại nhân viên", required = true, schema = @Schema()) @Valid @RequestParam(value = "loaiNV", required = false) String loaiNV,
                                                                            @Parameter(in = ParameterIn.QUERY, description = "Từ ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "fdate", required = false) String fdate,
                                                                            @Parameter(in = ParameterIn.QUERY, description = "Đến ngày", required = true, schema = @Schema()) @Valid @RequestParam(value = "tdate", required = false) String tdate);
}
