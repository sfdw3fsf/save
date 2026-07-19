package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.SoDoKetNoiMangDto;
import vn.vnpt.vnptit.ecms.service.idc.SoDoKetNoiMangService;

@RestController
@RequestMapping("/sodo-ketnoi-mang")
public class SoDoKetNoiMangController {

    @Autowired
    SoDoKetNoiMangService service;

    @Operation(summary = "Danh sách thiêt bị mạng chưa kết nối")
    @PostMapping("/ds-thietbi-chua-ket-noi")
    public ResponseEntity<ApiResult> listDevicesNotConnected(@RequestBody SoDoKetNoiMangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.listDevicesNotConnected(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách thiêt bị mạng chưa kết nối theo đối tượng")
    @PostMapping("/ds-thietbi-chua-ket-noi-theo-doituong")
    public ResponseEntity<ApiResult> listDevicesNotConnectedByObject(@RequestBody SoDoKetNoiMangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.listDevicesNotConnectedByObject(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách port thiết bị chưa kết nối")
    @PostMapping("/ds-port-thietbi-chua-ket-noi")
    public ResponseEntity<ApiResult> listPortsNotConnected(@RequestBody SoDoKetNoiMangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.listPortsNotConnected(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Gỡ kết nối mạng thiết bị")
    @PostMapping("/go-ket-noi-mang-thiet-bi")
    public ResponseEntity<ApiResult> removeDeviceNetworkConnection(@RequestBody SoDoKetNoiMangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.goKetNoiMangThietBi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kết nối mạng thiết bị")
    @PostMapping("/ket-noi-mang-thiet-bi")
    public ResponseEntity<ApiResult> connectDeviceNetwork(@RequestBody SoDoKetNoiMangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ketNoiMangThietBi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy kết nối mạng theo đối tượng và id")
    @PostMapping("/lay-ket-noi-mang-theo-doituong-va-id")
    public ResponseEntity<ApiResult> getNetworkConnectionByObjectAndId(@RequestBody SoDoKetNoiMangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getScopeDiagram(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy sơ đồ đấu nối của TẤT CẢ thiết bị mạng trong một IDC")
    @PostMapping("/sodo-daunoi-tatca-thietbi-mang")
    public ResponseEntity<ApiResult> getAllDevicesDiagramByIdc(@RequestBody SoDoKetNoiMangDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getAllDevicesDiagramByIdc(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}

