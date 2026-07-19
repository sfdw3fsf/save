package vn.vnpt.vnptit.ecms.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.UpsertLoaiVaiTroDto;
import vn.vnpt.vnptit.ecms.service.DanhMucLoaiVaiTroService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("danh-muc-loai-vai-tro")
@Api(tags = "Danh mục loại vai trò")
public class DanhMucLoaiVaiTroController {

    private final DanhMucLoaiVaiTroService service;

    public DanhMucLoaiVaiTroController(DanhMucLoaiVaiTroService service) {
        this.service = service;
    }

    // ===========================================
    // GET LIST
    // ===========================================
    @PostMapping("/get-all")
    @ApiOperation(value = "Lấy danh sách loại vai trò")
    public ResponseEntity<ApiResult> getAll() {

        ApiResult res = new ApiResult();
        try {
            res.setData(service.getAll());
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ===========================================
    // GET DETAIL
    // ===========================================
    @GetMapping("/{id}/detail")
    @ApiOperation(value = "Chi tiết loại vai trò")
    public ResponseEntity<ApiResult> getDetail(@PathVariable Long id) {

        ApiResult res = new ApiResult();
        try {
            res.setData(service.getDetail(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ===========================================
    // INSERT / UPDATE
    // ===========================================
    @PostMapping("/insert-update")
    @ApiOperation(value = "Thêm hoặc cập nhật loại vai trò")
    public ResponseEntity<ApiResult> insertOrUpdate(
            @RequestBody @Valid UpsertLoaiVaiTroDto dto) {

        ApiResult res = new ApiResult();
        try {
            res = service.insertOrUpdate(dto);
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ===========================================
    // DELETE SINGLE
    // ===========================================
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Xoá loại vai trò")
    public ResponseEntity<ApiResult> delete(@PathVariable Long id) {

        ApiResult res = new ApiResult();
        try {
            res = service.delete(id);
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ===========================================
    // DELETE MULTI
    // ===========================================
    @PostMapping("/delete-multi")
    @ApiOperation(value = "Xoá nhiều loại vai trò")
    public ResponseEntity<ApiResult> deleteMulti(@RequestBody Map<String, Object> payload) {

        ApiResult res;
        try {
            List<?> ids = (List<?>) payload.get("ids");

            String listId = ids.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));

            res = service.deleteMulti(listId);

        } catch (AppSqlException e) {
            ApiResult err = new ApiResult();
            err.setException(e);
            return err.getResponseEntity();
        }

        return res.getResponseEntity();
    }

    // ===========================================
    // CHECK TRÙNG TÊN
    // ===========================================
    @GetMapping("/check-ten")
    @ApiOperation(value = "Check trùng tên loại vai trò")
    public ResponseEntity<ApiResult> checkTen(
            @RequestParam(required = false) Long id,
            @RequestParam String ten) {

        ApiResult res = new ApiResult();
        try {
            boolean exist = service.checkTenTonTai(id, ten);
            res.setData(exist);
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    // ===========================================
    // CHECK TRÙNG TÊN TẮT
    // ===========================================
    @GetMapping("/check-ten-tat")
    @ApiOperation(value = "Check trùng tên tắt loại vai trò")
    public ResponseEntity<ApiResult> checkTenTat(
            @RequestParam(required = false) Long id,
            @RequestParam String tenTat) {

        ApiResult res = new ApiResult();
        try {
            boolean exist = service.checkTenTatTonTai(id, tenTat);
            res.setData(exist);
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }
}