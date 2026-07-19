package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.hatang.CumHaTangAoHoaCnttDto;
import vn.vnpt.vnptit.ecms.service.hatang.CumHaTangAoHoaCnttService;

import javax.validation.Valid;

@Api(value="Cụm hạ tầng ảo hóa Controller", description = "Cụm hạ tầng ảo hóa")
@RestController
@RequestMapping("ha-tang/cum-aohoa")
public class CumHaTangAoHoaCnttController {
    private final CumHaTangAoHoaCnttService service;
    public CumHaTangAoHoaCnttController(CumHaTangAoHoaCnttService service) {
        this.service = service;
    }

    /*----------DANH MUC*/
    @Operation(summary = "Lấy danh mục dmphanloaihatang")
    @GetMapping("/dm-phanloai-hatang")
    public ResponseEntity<ApiResult> listdmphanloaihatang() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dmphanloaihatang());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh mục dmloaihatang")
    @GetMapping("/dm-loai-hatang")
    public ResponseEntity<ApiResult> listdmloaihatang() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dmloaihatang());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh mục dmhinhthucdautu")
    @GetMapping("/dm-hinhthuc-dautu")
    public ResponseEntity<ApiResult> listdmhinhthucdautu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dmhinhthucdautu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh mục dmidc")
    @GetMapping("/dm-idc")
    public ResponseEntity<ApiResult> listdmidc() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dmidc());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh mục dmhatangmang")
    @GetMapping("/dm-hatang-mang")
    public ResponseEntity<ApiResult> listdmhatangmang() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dmhatangmang());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh mục dmcapdohatang")
    @GetMapping("/dm-capdo-hatang")
    public ResponseEntity<ApiResult> listdmcapdohatang() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dmcapdohatang());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh mục dmcongnghehatang")
    @GetMapping("/dm-congnghe-hatang")
    public ResponseEntity<ApiResult> listdmcongnghehatang() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dmcongnghehatang());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh mục dmtrangthaiccdv")
    @GetMapping("/dm-trangthai-ccdv")
    public ResponseEntity<ApiResult> listdmtrangthaiccdv() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.dmtrangthaiccdv());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Cụm ảo hóa 2:VMWare , 3: Smartcloud ")
    //@GetMapping("/list-cum-aohoa")
    @PostMapping("/list")
    //@RequestMapping(value = "/list-cum-aohoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> listCumaohoa(@RequestParam(defaultValue = "2")  Integer  loai) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getCumaohoa(loai));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Xóa cum ảo hoa")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaCumaohoa(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.service.delCumaohoa(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thêm và ddieuf chỉnh CUm ảo hóa")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> upCumaohoa(@Valid @RequestBody CumHaTangAoHoaCnttDto item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(
                    this.service.upCumaohoa(item)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
