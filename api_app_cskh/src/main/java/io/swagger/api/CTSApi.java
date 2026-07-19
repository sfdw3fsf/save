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
import org.springframework.web.bind.annotation.RequestParam;
import vn.vnptit.sapi.oneapp.model.request.DoQuangRequest;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-03-16T07:34:18.357Z[GMT]")
@Validated
@RequestMapping(value = "/cts")
public interface CTSApi {
  @Operation(summary = "CTS - Lấy thông tin port của mã thuê bao ", tags = {"CTS"})
  @RequestMapping(value = "/layThongTinPortCuaMaThueBao", produces = {"application/json"}, method = RequestMethod.GET)
  ResponseEntity<BaseResponse<Map<String, Object>>> layThongTinPortCuaMaThueBao(@Parameter(in = ParameterIn.QUERY, description = "Mã Thuê Bao", required = true, schema = @Schema()) @Valid @RequestParam(value = "maTb", required = false) String maThueBao);

  @Operation(summary = "CTS - api Đo Quang", tags = {"CTS"})
  @RequestMapping(value = "/doQuang", produces = {"application/json"}, method = RequestMethod.POST)
  ResponseEntity<BaseResponse<Map<String, Object>>> doQuang(@NotNull @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody DoQuangRequest doQuangRequest);
}