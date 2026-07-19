package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchPhieuYeuCauTnDto;
import vn.vnpt.vnptit.ecms.dto.hatang.UpsertNoidungTraodoiDto;
import vn.vnpt.vnptit.ecms.dto.idc.PhieuYcTaiNguyenDto;
import vn.vnpt.vnptit.ecms.service.idc.PhieuYcTaiNguyenService;

@RestController
@RequestMapping("phieuyc-tn")
public class PhieuYcTaiNguyenController {

    private final PhieuYcTaiNguyenService service;

    public PhieuYcTaiNguyenController(PhieuYcTaiNguyenService service) {
        this.service = service;
    }

    @PostMapping("/thongtin-chung/chitiet")
    @Operation(summary = "Lấy thông tin chung của phiếu yêu cầu tài nguyên")
    public ResponseEntity<ApiResult> layThongTinChung(@RequestBody PhieuYcTaiNguyenDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getThongTinChung(dto.getPhieuycId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/thongtin-chung")
    @Operation(summary = "Cập nhật/Thêm mới thông tin chung của phiếu yêu cầu tài nguyên")
    public ResponseEntity<ApiResult> thongTinChung(@RequestBody PhieuYcTaiNguyenDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertThongTinChung(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/tainguyen/danhsach")
    @Operation(summary = "Lấy danh sách tài nguyên của phiếu yêu cầu")
    public ResponseEntity<ApiResult> dsTaiNguyen(@RequestBody PhieuYcTaiNguyenDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDsTaiNguyen(dto.getPhieuycId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm danh sách phiếu yêu cầu tài nguyên")
    @PostMapping("/search")
    public ResponseEntity<ApiResult> search(@RequestBody SearchPhieuYeuCauTnDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.search(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nội dung trao đổi theo phiếu yêu cầu")
    @GetMapping("/nd-trao-doi/{phieuYcId}")
    public ResponseEntity<ApiResult> getNoidungTraodoi(
            @PathVariable String phieuYcId,
            @RequestParam(defaultValue = "1") Long pageIndex,
            @RequestParam(defaultValue = "20") Long pageSize) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getNoidungTraodoi(phieuYcId, pageIndex, pageSize));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thêm mới hoặc cập nhật nội dung trao đổi")
    @PostMapping("/nd-trao-doi/upsert")
    public ResponseEntity<ApiResult> upsertNoidungTraodoi(@RequestBody UpsertNoidungTraodoiDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertNoidungTraodoi(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
