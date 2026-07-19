package vn.vnpt.vnptit.ecms.controller.idc;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucMatSanDtoIn;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucMatSanService;

@RestController
@RequestMapping("danh-muc-mat-san")
public class DanhMucMatSanController {
    private final DanhMucMatSanService service;

    public DanhMucMatSanController(DanhMucMatSanService service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}/detail")
    @ApiOperation(value = "Lấy chi tiết mặt sàn")
    public ResponseEntity<ApiResult> getDetail(@PathVariable("id") Long id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getMatSanDetail(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
    @GetMapping(value = "/get-all")
    @ApiOperation(value = "Lấy danh sách tất cả các mặt sàn")
    public ResponseEntity<ApiResult> getAll() {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getAll());
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @PostMapping(value = "/insert-update")
    @ApiOperation(value = "Thêm hoặc cập nhật mặt sàn")
    public ResponseEntity<ApiResult> insertOrUpdate(@RequestBody @Valid DanhMucMatSanDtoIn dto) {
        ApiResult res = new ApiResult();
        try {
            res = this.service.insertOrUpdate(dto);
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @PostMapping(value = "/delete/{id}")
    @ApiOperation(value = "Xoá mặt sàn")
    public ResponseEntity<ApiResult> delete(@PathVariable("id") Long id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.delete(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @GetMapping(value = "/idc-toanha")
    @ApiOperation(value = "Lấy danh sách toà nhà IDC")
    public ResponseEntity<ApiResult> getToaNha() {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getIDCToaNha());
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @GetMapping(value = "/check-ten-mat-san")
    @ApiOperation(value = "Kiểm tra tên mặt sàn đã tồn tại hay chưa")
    public ResponseEntity<ApiResult> checkTenMatSan(@RequestParam("ten") String ten) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.existsByName(ten));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
    @GetMapping(value = "/check-ten-tat-mat-san")
    @ApiOperation(value = "Kiểm tra tên tắt mặt sàn đã tồn tại hay chưa")
    public ResponseEntity<ApiResult> checkTenTatMatSan(@RequestParam("tenTat") String tenTat) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.existsByTenTat(tenTat));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @PostMapping(value = "/validate-list-mat-san")
    @ApiOperation(value = "Kiểm tra danh sách mặt sàn")
    public ResponseEntity<ApiResult> validateListMatSan(@RequestBody @Valid java.util.List<DanhMucMatSanDtoIn> list) {
        ApiResult res = new ApiResult();
        try {
            res = this.service.checkListMatSan(list);
        } catch (AppSqlException | com.fasterxml.jackson.core.JsonProcessingException e) {
            res.setException((AppSqlException) e);
        }
        return res.getResponseEntity();
    }

    @PostMapping(value = "/get-by-id")
    @ApiOperation(value = "Lấy mặt sàn theo ID")
    public ResponseEntity<ApiResult> getById(@RequestBody DataKeyInput item) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getById(item.getId()));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ======= không gian
    @PostMapping(value = "/get-by-toanha")
    @ApiOperation(value = "Lấy danh sách tất cả các mặt sàn theo tòa nhà")
    public ResponseEntity<ApiResult> getByToaNha(@RequestParam Long id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getByToaNha(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @PostMapping(value = "/get-by-idc")
    @ApiOperation(value = "Lấy danh sách tất cả các mặt sàn theo tòa nhà")
    public ResponseEntity<ApiResult> getByIdc(@RequestParam Long id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getByIdc(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
}