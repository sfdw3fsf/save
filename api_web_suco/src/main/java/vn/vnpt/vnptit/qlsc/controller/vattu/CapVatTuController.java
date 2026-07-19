package vn.vnpt.vnptit.qlsc.controller.vattu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.qlsc.dto.vattu.VatTuCongTrinhV2DtoIn;
import vn.vnpt.vnptit.qlsc.service.vattu.CapPhatVatTuService;
import vn.vnpt.vnptit.qlsc.service.vattu.XuLyVatTuService;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/capphat-vattu")
public class CapVatTuController {
  private final CapPhatVatTuService service;

  public CapVatTuController(CapPhatVatTuService service) {
    this.service = service;
  }

  @Operation(summary = "Ghi lại cấp phát vật tư")
  @RequestMapping(value = "/ghi-lai", method = RequestMethod.POST)
  public ResponseEntity<ApiResult> ghiLaiCapPhatVatTu(@Valid @RequestBody VatTuCongTrinhV2DtoIn vatTuCongTrinhV2DtoIn) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.ghiLaiCapPhatVatTu(vatTuCongTrinhV2DtoIn));
    } catch (AppSqlException e) {
      rs.setException(e);
    } catch (JsonProcessingException e) {
      rs.setException(new AppSqlException(e.getMessage()));
    }
    return rs.getResponseEntity();
  }

  @Operation(summary = "Danh sách cấp vật tư đã cấp")
  @RequestMapping(value = "/xoa", method = RequestMethod.POST)
  public ResponseEntity<ApiResult> xoaVattuDaCap(@RequestBody Map<String, String> input) {
    ApiResult rs = new ApiResult();
    try {
      rs.setData(service.xoaVattuDaCap(Long.parseLong(input.get("phieuvt_id")), Long.parseLong(input.get("tuvt_id"))));
    } catch (AppSqlException e) {
      rs.setException(e);
    }
    return rs.getResponseEntity();
  }
}
