package io.swagger.api;

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
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Validated
@RequestMapping(value = "/tienich_hotro")
public interface TienIchVaHoTroApi {
    @Operation(summary = "Thêm tính năng nổi bật", tags = {"Thêm tính năng nổi bật"})
    @RequestMapping(value = "/them_tinhnang_noibat", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<String>> them_tinhnang_noibat (@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ThemTinhNangNoiBatRequest requestBody);


    @Operation(summary = "Lấy danh sách tính năng nổi bật", description = "Lấy danh sách tính năng nổi bật", tags = {"Lấy danh sách tính năng nổi bật"})
    @RequestMapping(value = "/ds_tinhnang_noibat", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List>> dsTinhNangNoiBat();

    @Operation(summary = "Cập nhật tính năng nổi bật", tags = {"Cập nhật tính năng nổi bật"})
    @RequestMapping(value = "/capnhat_tinhnang_noibat", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<String>> capnhat_tinhnang_noibat (@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CapNhatTinhNangNoiBatRequest requestBody);

    @Operation(summary = "Xóa tính năng nổi bật", tags = {"Xóa tính năng nổi bật"})
    @RequestMapping(value = "/xoa_tinhnang_noibat", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<String>> xoa_tinhnang_noibat (@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody XoaTinhNangNoiBatRequest requestBody);

    @Operation(summary = "Lấy danh mục nhóm câu hỏi khảo sát mytv", description = "Lấy danh mục nhóm câu hỏi khảo sát mytv", tags = {"Lấy danh mục nhóm câu hỏi khảo sát mytv"})
    @RequestMapping(value = "/danhmuc_nhom_cauhoiks_mytv", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List>> danhMucNhomCauHoiKSMyTV();


    @Operation(summary = "Lấy danh sách câu hỏi khảo sát mytv theo nhóm", description = "Lấy danh sách câu hỏi khảo sát mytv theo nhóm", tags = {"Lấy danh sách câu hỏi khảo sát mytv theo nhóm"})
    @RequestMapping(value = "/lay_ds_cauhoiks_mytv", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> layDSCauHoiKSMyTv(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody LayDSCauHoiMyTVRequest requestBody);

    @Operation(summary = "Lấy chi tiết câu hỏi; danh sách câu trả lời", description = "Lấy chi tiết câu hỏi; danh sách câu trả lời", tags = {" Lấy chi tiết câu hỏi; danh sách câu trả lời"})
    @RequestMapping(value = "/chitiet_cauhoiks_mytv", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> chitiet_cauhoiks_mytv(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ChiTietCauHoiKSMyTV requestBody);

    @Operation(summary = "Hàm thêm/cập nhật thông tin câu hỏi, câu trả lời", description = "Hàm thêm/cập nhật thông tin câu hỏi, câu trả lời", tags = {"Hàm thêm/cập nhật thông tin câu hỏi, câu trả lời"})
    @RequestMapping(value = "/capnhat_cauhoiks_mytv", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<String>> capnhat_cauhoiks_mytv(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody SaveThongTinCauHoiCauTLRequest requestBody);

    @Operation(summary = "Hàm xóa câu hỏi", description = "Hàm xóa câu hỏi", tags = {"Hàm xóa câu hỏi"})
    @RequestMapping(value = "/xoa_cauhoiks_mytv", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<String>> xoa_cauhoiks_mytv(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody XoaCauHoiRequest requestBody);

    @Operation(summary = "Lấy danh sách câu hỏi, câu trả lời theo nhóm", description = "Lấy danh sách câu hỏi, câu trả lời theo nhóm", tags = {"Lấy danh sách câu hỏi, câu trả lời theo nhóm"})
    @RequestMapping(value = "/lay_ds_cauhoiks_theo_nhomch", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> lay_ds_cauhoiks_theo_nhomch(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody LayDSCauHoiKSTheoNhom requestBody);

    @Operation(summary = "Hàm cập nhật thông tin trả lời khảo sát", description = "Hàm cập nhật thông tin trả lời khảo sát", tags = {"Hàm cập nhật thông tin trả lời khảo sát"})
    @RequestMapping(value = "/capnhat_traloiks_mytv", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<String>> capnhat_traloiks_mytv(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CapNhat_traloiks_mytvRequest requestBody);

    @Operation(summary = "Lấy danh sách đơn vị", description = "Lấy danh sách đơn vị", tags = {"Lấy danh sách đơn vị"})
    @RequestMapping(value = "/lay_ds_donviks_mytv", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List>> dsDonViKSMyTV(@Parameter(in = ParameterIn.QUERY, description = "Kiểu", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_kieu", required = false) Long p_kieu);

    @Operation(summary = "Lấy danh sách nhân viên đơn vị", description = "Lấy danh sách nhân viên đơn vị", tags = {"Lấy danh sách nhân viên đơn vị"})
    @RequestMapping(value = "/lay_ds_nhanvien_donvi", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List>> dsNhanVienDonVi(@Parameter(in = ParameterIn.QUERY, description = "Đơn vi", required = true, schema = @Schema()) @Valid @RequestParam(value = "p_donvi_id", required = false) Long p_donvi_id);

    @Operation(summary = "Hàm thống kê tổng hợp", description = "Hàm thống kê tổng hợp", tags = {"Hàm thống kê tổng hợp"})
    @RequestMapping(value = "/baocao_tonghopks_mytv", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> thongKeTongHop(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ThongKeTongHopRequest requestBody);

    @Operation(summary = "Hàm xem chi tiết tỷ lệ chọn", description = "Hàm xem chi tiết tỷ lệ chọn", tags = {"Hàm xem chi tiết tỷ lệ chọn"})
    @RequestMapping(value = "/baocao_tyle_chon_traloi", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> baoCaoTyLeChonTL(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody BaoCaoTyLeChonTraLoiRequest requestBody);

    @Operation(summary = "Lấy danh sách khách hàng đã ks", description = "Lấy danh sách khách hàng đã ks", tags = {"Lấy danh sách khách hàng đã ks"})
    @RequestMapping(value = "/baocao_khachhang_daks", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<List>> baoCaoKHDaKS(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody BaoCaoKHDaKSRequest requestBody);

    @Operation(summary = "Lấy danh sách chi tiết khảo sát", description = "Lấy danh sách chi tiết khảo sát", tags = {"Lấy danh sách chi tiết khảo sát"})
    @RequestMapping(value = "/baocao_chitiet_khaosat", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Map<String,Object>>> BaoCaoChiTietKS(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody BaoCaoChiTietKSRequest requestBody);

    @Operation(summary = "Phân quyền báo cáo khảo sát my tv", description = "Phân quyền báo cáo khảo sát mytv", tags = {"Phân quyền báo cáo khảo sát mytv"})
    @RequestMapping(value = "/phanquyen_baocaoks_mytv", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<String>> phanQuyenBacCaoKSMytv();
}
