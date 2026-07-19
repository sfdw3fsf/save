package io.swagger.api;

import io.swagger.model.request.BanHangViDienTuThongTinRequest;
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

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Validated
@RequestMapping(value = "/banHangViDienTuThongTin")
public interface BanHangViDienTuThongTin {


    @Operation(summary = "Xây dựng API thanh toán ví EU", tags = {"Xây dựng API thanh toán ví EU"})
    @RequestMapping(value = "/thanhtoan_vi_eu", produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<BaseResponse> thanhToanViEU(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody BanHangViDienTuThongTinRequest requestBody);
}