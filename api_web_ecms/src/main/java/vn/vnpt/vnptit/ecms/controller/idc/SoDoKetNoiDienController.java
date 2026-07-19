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
import vn.vnpt.vnptit.ecms.dto.idc.SoDoKetNoiDienDto;
import vn.vnpt.vnptit.ecms.service.idc.SoDoKetNoiDienService;

@RestController
@RequestMapping("/sodo-ketnoi-dien")
public class SoDoKetNoiDienController {

    @Autowired
    SoDoKetNoiDienService service;

    @Operation(summary = "Danh sách thiêt bị điện chưa kết nối")
    @PostMapping("/ds-thietbi-chua-ket-noi")
    public ResponseEntity<ApiResult> listDevicesNotConnected(@RequestBody SoDoKetNoiDienDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.listDevicesNotConnected(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách thiêt bị điện chưa kết nối theo đối tượng")
    @PostMapping("/ds-thietbi-chua-ket-noi-theo-doituong")
    public ResponseEntity<ApiResult> listDevicesNotConnectedByObject(@RequestBody SoDoKetNoiDienDto dto) {
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
    public ResponseEntity<ApiResult> listPortsNotConnected(@RequestBody SoDoKetNoiDienDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.listPortsNotConnected(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Sơ đồ kết nối điện IDC")
    @PostMapping("/sodo-ketnoi-dien-idc")
    public ResponseEntity<ApiResult> soDoKetNoiDienIdc(@RequestBody SoDoKetNoiDienDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.soDoKetNoiDienIdc(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Gỡ kết nối điện thiết bị")
    @PostMapping("/go-ket-noi-dien-thiet-bi")
    public ResponseEntity<ApiResult> removeDevicePowerConnection(@RequestBody SoDoKetNoiDienDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.goKetNoiDienThietBi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kết nối điện thiết bị")
    @PostMapping("/ket-noi-dien-thiet-bi")
    public ResponseEntity<ApiResult> connectDevicePower(@RequestBody SoDoKetNoiDienDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.ketNoiDienThietBi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách thiết bị điện đã kết nối theo phòng")
    @PostMapping("/ds-thietbi-da-ket-noi-theo-phong")
    public ResponseEntity<ApiResult> listDevicesConnectedByRoom(@RequestBody SoDoKetNoiDienDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.danhSachKetNoiDienThietBiTheoPhong(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy kết nối điện theo đối tượng và id")
    @PostMapping("/lay-ket-noi-dien-theo-doituong-va-id")
    public ResponseEntity<ApiResult> getPowerConnectionByObjectAndId(@RequestBody SoDoKetNoiDienDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getScopeDiagram(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy sơ đồ đấu nối của TẤT CẢ thiết bị điện trong một IDC")
    @PostMapping("/sodo-daunoi-tatca-thietbi-dien")
    public ResponseEntity<ApiResult> getAllDevicesDiagramByIdc(@RequestBody SoDoKetNoiDienDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getAllDevicesDiagramByIdc(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}