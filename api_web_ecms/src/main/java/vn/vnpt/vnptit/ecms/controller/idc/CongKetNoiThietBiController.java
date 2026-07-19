package vn.vnpt.vnptit.ecms.controller.idc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.BaseMap;
import vn.vnpt.vnptit.ecms.dto.idc.CongKetNoiThietBiDto;
import vn.vnpt.vnptit.ecms.dto.idc.CongKetNoiThietBiDtoV2;
import vn.vnpt.vnptit.ecms.service.idc.CongKetNoiThietBiService;

@RestController
@RequestMapping("tainguyen-idc/cong-ketnoi-thietbi")
public class CongKetNoiThietBiController {

    @Autowired
    private CongKetNoiThietBiService service;

    @Operation(summary = "Sinh cổng theo thiết bị và phân loại")
    @PostMapping("/them-cong")
    public ResponseEntity<ApiResult> insertPort(@RequestBody CongKetNoiThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.insertPort(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/danh-sach")
    public ResponseEntity<ApiResult> dsCongThietBi(@RequestBody Map<String, Object> params) {
        BaseMap map = new BaseMap(params);
        map.validNumber("thietBiId", "param thietBiId là bắt buộc")
                .validNumber("phanLoai", "param phanLoai là bắt buộc")
                .validComplate();
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.danhSachPortThietBi(
                    map.get("thietBiId", Long.class),
                    map.get("phanLoai", Integer.class),
                    map.get("kheCam", Integer.class, 0)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Update thông tin cổng kết nối")
    @PostMapping("/update-cong")
    public ResponseEntity<ApiResult> updatePort(@RequestBody CongKetNoiThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.updatePort(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kết nối thiết bị/card vào cổng/khe cắm")
    @PostMapping("/ket-noi-thiet-bi")
    public ResponseEntity<ApiResult> connectDeviceToPort(@RequestBody CongKetNoiThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.connectDeviceToPort(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Ngắt kết nối thiết bị/card ra khỏi cổng/khe cắm")
    @PostMapping("/ngat-ket-noi-thiet-bi")
    public ResponseEntity<ApiResult> disconnectDeviceFromPort(@RequestBody CongKetNoiThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.disconnectDeviceFromPort(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách thiêt bị card chưa kết nối")
    @PostMapping("/ds-thiet-bi-chua-ket-noi")
    public ResponseEntity<ApiResult> listDevicesNotConnected(@RequestBody CongKetNoiThietBiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.listDevicesNotConnected(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Sinh cổng theo thiết bị và phân loại")
    @PostMapping("/them-cong-v2")
    public ResponseEntity<ApiResult> insertPortV2(@RequestBody CongKetNoiThietBiDtoV2 dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.insertPortV2(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
