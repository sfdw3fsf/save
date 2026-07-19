package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchThietBiDto;
import vn.vnpt.vnptit.ecms.dto.hatang.UpsertThongTinChungThietBiDto;
import vn.vnpt.vnptit.ecms.service.hatang.ThongTinThietBiV2Service;
    
@RestController
@RequestMapping("thietbi-vatly-v2")
public class ThongTinThietBiV2Controller {
    private final ThongTinThietBiV2Service service;

    public ThongTinThietBiV2Controller(ThongTinThietBiV2Service service) {
        this.service = service;
    }

    @Operation(summary = "Lấy danh sách tất cả thông tin thiết bị")
    @PostMapping(value = "")
    public ResponseEntity<ApiResult> getDanhSachThietBi(@RequestBody(required = false) SearchThietBiDto dto) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getDanhSachThietBi(dto));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thông tin thiết bị phân trang")
    @PostMapping(value = "/list-paging")
    public ResponseEntity<ApiResult> getDanhSachThietBiPaging(@RequestBody SearchThietBiDto dto) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getDanhSachThietBiPaging(dto));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // @Operation(summary = "Lấy danh sách thiết bị v2")
    // @PostMapping(value = "/v2")
    // public ResponseEntity<ApiResult> getDanhSachThietBiV2(@RequestBody(required = false) vn.vnpt.vnptit.ecms.dto.hatang.SearchThietBiV2Dto dto) {
    //     ApiResult res = new ApiResult();
    //     try {
    //         res.setData(this.service.getDanhSachThietBiV2(dto));
    //     } catch (AppSqlException e) {
    //         res.setException(e);
    //     }
    //     return res.getResponseEntity();
    // }


    @Operation(summary = "Lấy thông tin chung thiết bị theo ID")
    @GetMapping(value = "/{id}/thong-tin-chung")
    public ResponseEntity<ApiResult> getThongTinChungThietBiById(@PathVariable("id") Number id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getThongTinChungThietBiById(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật thông tin chung thiết bị")
    @PostMapping(value = "/thong-tin-chung")
    public ResponseEntity<ApiResult> upsertThongTinChungThietBi(@RequestBody UpsertThongTinChungThietBiDto dto) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.upsertThongTinChungThietBi(dto));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Xóa thiết bị theo ID")
    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<ApiResult> deleteThietBi(@PathVariable("id") Long id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.deleteThietBi(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
}
