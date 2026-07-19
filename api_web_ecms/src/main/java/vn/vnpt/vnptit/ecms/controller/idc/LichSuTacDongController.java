package vn.vnpt.vnptit.ecms.controller.idc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.LichSuTacDongDto;
import vn.vnpt.vnptit.ecms.service.idc.LichSuTacDongService;

@RestController
@RequestMapping("idc/lichsu-tacdong")
public class LichSuTacDongController {
    private final LichSuTacDongService service;

    public LichSuTacDongController(LichSuTacDongService service) {
        this.service = service;
    }

    // Tên bảng, Tên cột, Khóa dữ liệu (VD: THIETBI_ID)
    @Operation(summary = "Danh sách lịch sử tác động")
    @PostMapping("/danhsach")
    public ResponseEntity<ApiResult> listLichSuTacDong(@RequestBody LichSuTacDongDto searchDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getListSuTacDong(searchDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    // Khóa dữ liệu (VD: THIETBI_ID)
    @Operation(summary = "Danh sách lịch sử Công suất")
    @PostMapping("/congsuat")
    public ResponseEntity<ApiResult> listLichSuCongSuat(@RequestBody LichSuTacDongDto searchDto) {
        ApiResult rs = new ApiResult();
        try {
            Long thietBiId = Long.valueOf(searchDto.getKhoaDuLieu());
            rs.setData(service.getListSuCongSuat(thietBiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    // Khóa dữ liệu (VD: THIETBI_ID)
    @Operation(summary = "Danh sách lịch sử Cấp phát")
    @PostMapping("/capphat")
    public ResponseEntity<ApiResult> listLichSuCapPhat(@RequestBody LichSuTacDongDto searchDto) {
        ApiResult rs = new ApiResult();
        try {
            Long thietBiId = Long.valueOf(searchDto.getKhoaDuLieu());
            rs.setData(service.getListSuCapPhat(thietBiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}