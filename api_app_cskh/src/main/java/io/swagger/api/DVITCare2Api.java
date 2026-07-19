package io.swagger.api;

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

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Validated
@RequestMapping(value = "/dvitcare2")
public interface DVITCare2Api {
    @Operation(summary = "Load danh sách file upload", tags = {"Load danh sách file upload"})
    @RequestMapping(value = "/lay_ds_file_hs_itcare_smp", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> lay_ds_file_hs_itcare_smp(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody LayDSFileHoSoITCareRequest requestBody);

    @Operation(summary = "Cập nhật kết quả upload hồ sơ", tags = {"Cập nhật kết quả upload hồ sơ"})
    @RequestMapping(value = "/insert_hoso_itcare_smp", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> insert_hoso_itcare_smp(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody INSERT_HOSO_ITCARE_SMPRequest requestBody);

    @Operation(summary = "Xóa hồ sơ", tags = {"Xóa hồ sơ"})
    @RequestMapping(value = "/huygan_hoso_itcare_smp", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> huygan_hoso_itcare_smp(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody HuyGanHoSoITCareRequest requestBody);

    @Operation(summary = "Kiểm tra upload file hồ sơ", tags = {"Kiểm tra upload file hồ sơ"})
    @RequestMapping(value = "/kiemtra_upload_hs_itcaresmp", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> kiemtra_upload_hs_itcaresmp(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody KiemTraUploadFileHSRequest requestBody);

    @Operation(summary = "Xem trước mẫu file", tags = {"Xem trước mẫu file"})
    @RequestMapping(value = "/get_datafile_itcare", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<?> get_datafile_itcare(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody GetDataFileITCareRequest requestBody);

    @Operation(summary = "Gửi file cho khách hàng", tags = {"Gửi file cho khách hàng"})
    @RequestMapping(value = "/gui_file_khachhang", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> gui_file_khachhang(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody GuiFileKhachHangRequest requestBody);
}
