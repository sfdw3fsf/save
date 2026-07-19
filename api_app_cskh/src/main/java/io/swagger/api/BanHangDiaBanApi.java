package io.swagger.api;

import io.swagger.model.dto.DanhMuc;
import io.swagger.model.dto.NhanVienBH;
import io.swagger.model.dto.ThoiGianKhuVuc;
import io.swagger.model.dto.ThueBaoBaoDuong;
import io.swagger.model.request.ThemCapNhatDotBHRequest;
import io.swagger.model.request.ThemCapNhatNhomBHRequest;
import io.swagger.model.request.ThemKhuVucTacChienRequest;
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
import vn.vnptit.sapi.oneapp.model.response.DsAnhNhomBHResponse;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Validated
@RequestMapping(value = "/banHangDiaBan")
public interface BanHangDiaBanApi {

    @Operation(summary = "Lấy thông tin danh mục, dữ liệu chức năng", tags = {"Bán hàng địa bàn"})
    @RequestMapping(value = "/layThongTinDanhMuc", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<Object>> layThongTinDanhMuc(@Parameter(in = ParameterIn.QUERY, description = "Param", required = true, schema = @Schema()) @Valid @RequestParam(value = "param", required = false) String param,
                                                            @Parameter(in = ParameterIn.QUERY, description = "Kiểu", required = true, schema = @Schema()) @Valid @RequestParam(value = "kieu", required = false) Integer kieu);

    @Operation(summary = "Lấy thời gian bắt đầu, kết thúc mặc định theo đợt chiến dịch", tags = {"Bán hàng địa bàn"})
    @RequestMapping(value = "/layThoiGianTheoDotChienDich", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<ThoiGianKhuVuc>> layThoiGianTheoDotChienDich(@Parameter(in = ParameterIn.QUERY, description = "Đợt bán hàng Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "dotBhId", required = false) Long dotBhId);


    @Operation(summary = "Thêm khu vực tác chiến của đợt chiến dịch", tags = {"Bán hàng địa bàn"})
    @RequestMapping(value = "/themKhuVucTacChien", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> themKhuVucTacChien(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ThemKhuVucTacChienRequest requestBody);

    @Operation(summary = "Thêm và cập nhật đợt bán hàng", tags = {"Bán hàng địa bàn"})
    @RequestMapping(value = "/themCapNhatDotBanHang", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> themCapNhatDotBanHang(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ThemCapNhatDotBHRequest requestBody);

    @Operation(summary = "Danh mục đội trưởng", tags = {"Bán hàng địa bàn"})
    @RequestMapping(value = "/layDanhMucDoiTruong", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DanhMuc>>> layDanhMucDoiTruong(@Parameter(in = ParameterIn.QUERY, description = "Đợt bán hàng Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "dotBhId", required = false) Long dotBhId);

    @Operation(summary = "Lấy danh sách nhân viên bán hàng", tags = {"Bán hàng địa bàn"})
    @RequestMapping(value = "/layDsNhanVien", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<NhanVienBH>>> layDsNhanVien(@Parameter(in = ParameterIn.QUERY, description = "Đơn vị Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "donViId", required = false) Long donViId,
                                                                 @Parameter(in = ParameterIn.QUERY, description = "Đợt bán hàng Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "dotBhId", required = false) Long dotBhId);

    @Operation(summary = "Thêm và cập nhật nhóm bán hàng", tags = {"Bán hàng địa bàn"})
    @RequestMapping(value = "/themCapNhatNhomBanHang", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> themCapNhatNhomBanHang(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody ThemCapNhatNhomBHRequest requestBody);

    @Operation(summary = "Xóa đợt bán hàng", tags = {"Bán hàng địa bàn"})
    @RequestMapping(value = "/xoaDotBanHang", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> xoaDotBanHang(@Parameter(in = ParameterIn.QUERY, description = "Đợt bán hàng Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "dotBhId", required = false) Long dotBhId);

    @Operation(summary = "Xóa nhóm bán hàng", tags = {"Bán hàng địa bàn"})
    @RequestMapping(value = "/xoaNhomBanHang", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> xoaNhomBanHang(@Parameter(in = ParameterIn.QUERY, description = "Nhóm bán hàng Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "nhomBhId", required = false) Long nhomBhId);

    @Operation(summary = "Lấy danh sách thuê bao bảo dưỡng", tags = {"Bán hàng địa bàn"})
    @RequestMapping(value = "/layDsThueBaoBaoDuong", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<ThueBaoBaoDuong>>> layDsThueBaoBaoDuong();

    @Operation(summary = "Lấy danh sách ảnh nhóm bán hàng đã chụp", tags = {"Bán hàng địa bàn"})
    @RequestMapping(value = "/layDsAnhNhomBH", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<DsAnhNhomBHResponse>>> layDsAnhNhomBH(@Parameter(in = ParameterIn.QUERY, description = "Nhóm bán hàng Id", required = true, schema = @Schema()) @Valid @RequestParam(value = "nhomBhId", required = false) Long nhomBhId);


}