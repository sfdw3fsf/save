package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.KiemTraTrungViTriDsLamModuleDtoInp;
import vn.vnpt.vnptit.ecms.dto.danhmuc.CapNhatDslamModule;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThemDslamModule;
import vn.vnpt.vnptit.ecms.service.danhmuc.DslamModuleService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("danhmuc/DslamModule")
public class DslamModuleController {
    private final DslamModuleService _service;

    public DslamModuleController(DslamModuleService service) {
        this._service = service;
    }

    @Operation(summary = "Chi tiết dslam module")
    @RequestMapping(value = "/ChiTiet", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> ChiTietDslamModule(@RequestParam Long moduleDslId)
    {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.ChiTietDslamModule(AppRequestContext.getCurrent().getPhanVungID(), moduleDslId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm dslam module")
    @RequestMapping(value = "/Them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> ThemDslamModule(@Valid @RequestBody ThemDslamModule input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.ThemDslamModule(input, AppRequestContext.getCurrent().getPhanVungID()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật dslam module")
    @RequestMapping(value = "/CapNhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> CapNhatDslamModule(@Valid @RequestBody CapNhatDslamModule input) {
        ApiResult rs = new ApiResult();
//        if (input.name == null || input.name.isEmpty()) {
//            rs.setMessageDetail("name không được trống");
//            return rs.getResponseEntity(Message.Codes.BSS_00003000);
//        }
        try {
            Map<String, Object> r = _service.ChiTietDslamModule(AppRequestContext.getCurrent().getPhanVungID(), input.getModuleDslId());
            if (r == null){
                rs.setMessageDetail("id "+ input.getModuleDslId() +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            rs.setData(_service.CapNhatDslamModule(AppRequestContext.getCurrent().getPhanVungID(), input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa dslam module")
    @RequestMapping(value = "/Xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaDslamModule(@RequestParam Long moduleDslId)
    {
        ApiResult rs = new ApiResult();
        try {
            Map<String, Object> r = _service.ChiTietDslamModule(AppRequestContext.getCurrent().getPhanVungID(), moduleDslId);
            if (r == null){
                rs.setMessageDetail("id "+ moduleDslId +" không tồn tại");
                return rs.getResponseEntity(Message.Codes.BSS_00009491);
            }
            rs.setData(new ResultOutput(_service.XoaDslamModule(AppRequestContext.getCurrent().getPhanVungID(), moduleDslId)) );
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra trùng serial")
    @RequestMapping(value = "/kiemtra-trung-serial", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> kiemTraTrungSerial(@RequestParam("serial") String serial) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(_service.kiemTraTrungSerial(serial)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra trùng vị trí cắm card")
    @RequestMapping(value = "/kiemtra-trung-vitri-camcard", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemTraTrungViTriCamCard(@Valid @RequestBody KiemTraTrungViTriDsLamModuleDtoInp obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(_service.kiemTraTrungViTriCamCard(obj)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy dslamm module và port dslam module")
    @RequestMapping(value = "/lay-dslammodule-va-port/{dslmoduleId}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> LayDsLamModuleVaPort(@PathVariable(name = "dslmoduleId", required =  true) Long dslmoduleId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_service.LayDsLamModuleVaPort(dslmoduleId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
