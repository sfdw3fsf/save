package vn.vnpt.vnptit.qlsc.controller.cntt;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.qlsc.dto.BaseMap;
import vn.vnpt.vnptit.qlsc.dto.cntt.ChiaSeSuCoCNTTInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.TiepNhanSuCoCnttInput;
import vn.vnpt.vnptit.qlsc.service.cntt.TiepNhanSuCoCNTTService;

import java.io.IOException;

@RestController
@RequestMapping(value = "/tiepnhan-suco-cntt")
public class TiepNhanSuCoCNTTController {

    private final TiepNhanSuCoCNTTService service;

    public TiepNhanSuCoCNTTController(TiepNhanSuCoCNTTService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách sự cố cntt")
    @RequestMapping(value = "/suco-cntt", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachSuCoCNTT(@RequestBody DataKeyInput nguonSc) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsSuCoCntt(nguonSc.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tiếp nhận sự cố")
    @RequestMapping(value = "/tiepNhanSuCoCntt", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tiepNhanSuCoCntt(@Valid @RequestBody TiepNhanSuCoCnttInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.tiepNhanSuCoCntt(item));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy người phụ trách theo loại tb")
    @RequestMapping(value = "/getNguoiPhuTrachByLoaiTbId", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getNguoiPhuTrachByLoaiTbId(@RequestBody DataKeyInput loaitb) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getNguoiPhuTrachByLoaiTbId(loaitb.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tiếp nhận sự cố ver2")
    @RequestMapping(value = "/tiepNhanSuCoCnttV2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tiepNhanSuCoCnttVer2(@Valid @RequestBody TiepNhanSuCoCnttInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.tiepNhanSuCoCnttVer2(item));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chia sẻ sự cố")
    @RequestMapping(value = "/chia-se", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> chiaSe(@Valid @RequestBody ChiaSeSuCoCNTTInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.chiaSe(item));
        } catch (AppSqlException e) {
            rs.setException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/giaoViec", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giaoViec(@RequestBody BaseMap<String, Object> item) {
        item.valid().isNumber("pPhanVungId", "Thuộc tính pPhanVungId là bắt buộc")
                .isNumber("pSuCoId", "Thuộc tính pSuCoId là bắt buộc")
                .isNumber("pDonViTHienId", "Thuộc tính pDonViTHienId là bắt buộc")
                .isNumber("pNhanVienTHienId", "Thuộc tính pNhanVienTHienId là bắt buộc")
                .isEmpty("pNoiDung", "Thuộc tính pNoiDung là bắt buộc")
                .isEmpty("pSDT", "Thuộc tính pSDT là bắt buộc")
                .complete();
        ApiResult rs = new ApiResult();
        try {
            service.tiepNhanGiaoViec(
                    item.getLong("pPhanVungId"),
                    item.getLong("pSuCoId"),
                    item.getInt("pDonViTHienId"),
                    item.getInt("pNhanVienTHienId"),
                    item.getString("pNoiDung"),
                    item.getString("pSDT"),
                    AppRequestContext.getCurrent().getNguoiDungID(),
                    item.getInt("isSMS") == null ? 0 : item.getInt("isSMS")
            );
            rs.setData(true);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
