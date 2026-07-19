package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.*;
import vn.vnpt.vnptit.ecms.service.idc.ThiCongRackService;

import javax.validation.Valid;


@RestController
@RequestMapping("/thicong-rack")
public class ThiCongRackController {
    private final ThiCongRackService thiCongRackService;

    public ThiCongRackController(ThiCongRackService thiCongRackService){
        this.thiCongRackService = thiCongRackService;
    }

    @Operation(summary = "Thêm hoặc cập nhật Rack và thi công Rack")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody UpsertThiCongRackRequest request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thiCongRackService.upsertThiCongRack(request.getThiCongRack(), request.getThongTinRack()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thêm hoặc cập nhật thi công Rack lẻ")
    @PostMapping("/upsert-thicong-rack")
    public ResponseEntity<ApiResult> upsertTcRack(@RequestBody ThiCongRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thiCongRackService.upsertThiCongRackSingle(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thi cong chọn Rack tháo dỡ")
    @PostMapping("/chon-rack-thaodo")
    public ResponseEntity<ApiResult> chonRackThaoDo(@Valid @RequestBody CapPhatRackRequestDTO request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thiCongRackService.chonRackThaoDo(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thi cong tháo dỡ Rack")
    @PostMapping("/thicong-thaodo-rack")
    public ResponseEntity<ApiResult> thicongThaodoRack(@Valid @RequestBody ThaoDoRackRequestDto request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thiCongRackService.thiCongThaoDoRack(request));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa Thi công Rack theo ID")
    @PostMapping("/delete-thicong-rack/{id}")
    public ResponseEntity<ApiResult> delete(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thiCongRackService.deleteThiCongRack(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hoan thanh thi cong")
    @PostMapping("/hoanthanh-lapdat-rack-idc/{id}")
    public ResponseEntity<ApiResult> hoanThanhLapDat(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thiCongRackService.hoanThanhLapDatRack(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Hoan thanh thi cong")
    @PostMapping("/hoanthanh-thaodo-rack-idc/{id}")
    public ResponseEntity<ApiResult> hoanThanhThaoDo(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thiCongRackService.hoanThanhThaoDoRack(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Hoan thanh thi cong")
    @PostMapping("/hoanthanh-thaydoi-rack-idc/{id}")
    public ResponseEntity<ApiResult> hoanThanhThayDoi(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thiCongRackService.hoanThanhThayDoiRack(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "DS ket qua thi cong thay đổi Rack")
    @PostMapping("/ds-ketqua-thicong-rack")
    public ResponseEntity<ApiResult> dsKetQuaThiCongRack(@RequestBody KetQuaThiCongRackDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thiCongRackService.dsKetQuaThiCongRack(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lay ds Rack trong phieu thi cong")
    @PostMapping("/ds-rack-trong-phieutc/{id}")
    public ResponseEntity<ApiResult> dsRacksDaCapPhat(@PathVariable(name = "id") Long idPhieuThiCong) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thiCongRackService.dsRackTrongPhieutc(idPhieuThiCong));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật trạng thái phiếu thi công Rack")
    @PostMapping("/capnhat-trangthai-phieutc")
    public ResponseEntity<ApiResult> capnhatTrangthaiPhieutc(@RequestBody CapNhatThongTinPhieuTCDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thiCongRackService.capNhatPhieuTC(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
