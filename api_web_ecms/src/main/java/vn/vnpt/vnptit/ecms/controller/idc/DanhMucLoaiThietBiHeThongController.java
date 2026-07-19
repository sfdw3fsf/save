package vn.vnpt.vnptit.ecms.controller.idc;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLoaiThietBiHeThongDtoIn;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucLoaiThietBiHeThongService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("danh-muc-loai-thiet-bi-he-thong")
public class DanhMucLoaiThietBiHeThongController {
    private final DanhMucLoaiThietBiHeThongService service;
    public DanhMucLoaiThietBiHeThongController(DanhMucLoaiThietBiHeThongService service) {
        this.service = service;
    }
    //hien chuc nang lay theo phan vung id 68
    @GetMapping(value = "/get-all")
    @ApiOperation(value = "Lấy danh sách tất cả các loại thiết bị hệ thống")
    public ResponseEntity<ApiResult> getAll() {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getAll());
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
    @GetMapping(value = "/get-mang-thiet-bi-he-thong")
    @ApiOperation(value = "Lấy danh sách mảng thiết bị hệ thống")
    public ResponseEntity<ApiResult> getMangThietBiHeThong() {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getMangThietBiHeThong());
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
    @PostMapping(value="/insert-update")
    @ApiOperation(value = "Thêm hoặc cập nhật loai thiết bị hệ thống")
    public ResponseEntity<ApiResult> insertOrUpdate(@RequestBody @Valid DanhMucLoaiThietBiHeThongDtoIn dto) {
        ApiResult res = new ApiResult();
        try {
            res = this.service.insertOrUpdate(dto);
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
    @PostMapping(value = "/delete/{id}")
    @ApiOperation(value = "Xoá loại thiết bị hệ thống")
    public ResponseEntity<ApiResult> delete(@PathVariable("id") Long id) {
        try {
            return this.service.delete(id).getResponseEntity();
        } catch (AppSqlException e) {
            ApiResult res = new ApiResult();
            res.setException(e);
            return res.getResponseEntity();
        }
    }

    @GetMapping(value= "/check-ten-loai-thiet-bi")
    @ApiOperation(value = "Kiểm tra tên loại thiết bị hệ thống đã tồn tại")
    public ResponseEntity<ApiResult> checkTenLoaiThietBi(@RequestParam("ten") String ten) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.existsByName(ten));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
    @PostMapping(value = "/validate-list-loai-thiet-bi")
    @ApiOperation(value = "Kiểm tra danh sách loại thiết bị hệ thống")
    public ResponseEntity<ApiResult> validateListLoaiThietBi(@RequestBody @Valid List<DanhMucLoaiThietBiHeThongDtoIn> list) {
        ApiResult res = new ApiResult();
        try {
            res = this.service.checkListThietBi(list);
        } catch (AppSqlException | JsonProcessingException e) {
            res.setException((AppSqlException) e);
        }
        return res.getResponseEntity();
    }
}