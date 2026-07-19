package io.swagger.api;

import io.swagger.model.dto.CauHoiKS;
import io.swagger.model.dto.HopDongDGNV;
import io.swagger.model.request.CapNhatKSRequest;
import io.swagger.model.response.BaseResponse;
import io.swagger.model.dto.CauHoiKhaoSatData;
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

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Validated
@RequestMapping(value = "/danhGiaNV")
public interface DanhGiaNhanVienApi {

    @Operation(summary = "Lấy danh sách hợp đồng", tags = {"Đánh giá nhân viên"})
    @RequestMapping(value = "/dsHopDong", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<HopDongDGNV>>> dsHopDong(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Trạng thái hợp đồng id", required = true, schema = @Schema()) @Valid @RequestParam(value = "trangThaiId", required = true) Long trangThaiId,
                                                              @NotNull @Parameter(in = ParameterIn.QUERY, description = "Tháng - yyyymm", required = true, schema = @Schema()) @Valid @RequestParam(value = "thang", required = true) String thang);

    @Operation(summary = "Lấy danh sách câu hỏi khảo sát", tags = {"Đánh giá nhân viên"})
    @RequestMapping(value = "/dsCauHoiKS", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<CauHoiKhaoSatData>> dsCauHoiKS();

    @Operation(summary = "Lấy danh sách câu hỏi khảo sát", tags = {"Đánh giá nhân viên"})
    @RequestMapping(value = "/dsCauHoiKSv2", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<CauHoiKS>>> dsCauHoiKSv2();

    @Operation(summary = "Lấy danh sách câu đã trả lời", tags = {"Đánh giá nhân viên"})
    @RequestMapping(value = "/dsDaTraLoi", produces = {"application/json"}, method = RequestMethod.GET)
    ResponseEntity<BaseResponse<List<CauHoiKS>>> dsDaTraLoi(@NotNull @Parameter(in = ParameterIn.QUERY, description = "tltbId", required = true, schema = @Schema()) @Valid @RequestParam(value = "tltbId", required = true) Long tltbId);

    @Operation(summary = "Đánh giá nhân viên", description = "Đánh giá nhân viên", tags = {"Đánh giá nhân viên"})
    @RequestMapping(value = "/cnDanhGia", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse<Void>> cnDanhGia(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody CapNhatKSRequest body);
}
