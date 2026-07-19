package vn.vnpt.vnptit.qlsc.controller.vattu;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.qlsc.service.cntt.DanhMucCNTTService;
import vn.vnpt.vnptit.qlsc.service.vattu.DanhMucVTService;

import java.util.Map;

@RestController
@RequestMapping(value = "/danhmuc-vattu")
public class DanhMucVTController {
    private static DanhMucVTService service;
    public DanhMucVTController(DanhMucVTService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách kho NET")
    @RequestMapping(value = "/ds-kho-net", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachKhoNET(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsKhoNet(Long.parseLong(input.get("is_net"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách sự cố chờ duyệt")
    @RequestMapping(value = "/ds-suco-choduyet", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachSuCoChoDuyet(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsSuCoChoDuyet(Long.parseLong(input.get("is_net"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Danh sách vật tư chờ duyệt")
    @RequestMapping(value = "/ds-vattu-choduyet", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachVatTuChoDuyet(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsVatTuChoDuyet(Long.parseLong(input.get("suco_vattu_id"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách vật tư trong khho")
    @RequestMapping(value = "/ds-vattu-trongkho", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachVatTuTrongKho(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsVatTuTrongKho(Long.parseLong(input.get("vattu_id")), Long.parseLong(input.get("kho_id"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách cấp vật tư trong khho")
    @RequestMapping(value = "/ds-cap-vattu-trongkho", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachCapVatTuTrongKho(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsCapVatTuTrongKho(Long.parseLong(input.get("nghiepvu_id")), Long.parseLong(input.get("kho_id"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách cấp vật tư đã cấp")
    @RequestMapping(value = "/ds-kho", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachKhoVt(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsKhoVt());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách cấp vật tư đã cấp")
    @RequestMapping(value = "/ds-cap-vattu-dacap", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> DanhSachVatTuDaCap(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsCapVatTuDaCap(Long.parseLong(input.get("suco_id"))));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách cấp vật tư đã cấp")
    @RequestMapping(value = "/timkiem-vt-by-serial", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> timKiemVtBySerial(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.timKiemVatTuBySerial(input.get("serial")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
