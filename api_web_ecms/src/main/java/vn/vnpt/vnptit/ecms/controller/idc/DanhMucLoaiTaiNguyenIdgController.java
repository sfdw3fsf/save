package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLoaiTaiNguyenIdgDto;
import vn.vnpt.vnptit.ecms.service.idc.DanhMucLoaiTaiNguyenIdgService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/danhmuc/loai-tainguyen-idg")
public class DanhMucLoaiTaiNguyenIdgController {


    @Autowired
    private DanhMucLoaiTaiNguyenIdgService danhMucLoaiTaiNguyenIdgService;

    @Operation(summary = "Lấy danh sách đơn vị")
    @PostMapping("/get-danhsach-idg")
    public ResponseEntity<ApiResult> getDanhSachIDG() {
        ApiResult rs = new ApiResult();

        try{
            rs.setData(danhMucLoaiTaiNguyenIdgService.getDanhSachIdg());
        }

        catch(AppSqlException e) {
            rs.setException(e);
        }
        return  rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhóm tài nguyên")
    @PostMapping("/get-danhsach-nhom-tainguyen")
    public ResponseEntity<ApiResult> getDanhSachNhomTaiNguyen() {
        ApiResult rs = new ApiResult();

        try{
            rs.setData(danhMucLoaiTaiNguyenIdgService.getDanhSachNhomTaiNguyen());
        }

        catch(AppSqlException e) {
            rs.setException(e);
        }
        return  rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhập danh mục idg")
    @PostMapping(value = "/upsert")
    public ResponseEntity<ApiResult> upsert(@Valid @RequestBody DanhMucLoaiTaiNguyenIdgDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(danhMucLoaiTaiNguyenIdgService.upsertLoaiTaiNguyenIdg(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Validate danh sách danh mục IDG (batch)")
    @PostMapping("/validate-batch")
    public ResponseEntity<ApiResult> validateBatchDanhMucIdg(@RequestBody List<Map<String, Object>> payload) {

        ApiResult apiResult = new ApiResult();

        try {
            apiResult.setData(danhMucLoaiTaiNguyenIdgService.validateBatchDanhMucIdg(payload));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }

        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Thêm mới danh mục IDG theo danh sách (batch)")
    @PostMapping("/batch-insert")
    public ResponseEntity<ApiResult> batchInsertDanhMucIdg(@RequestBody List<Map<String, Object>> payload) {

        ApiResult apiResult = new ApiResult();

        try {
            apiResult.setData(danhMucLoaiTaiNguyenIdgService.batchInsertDanhMucIdg(payload));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }

        return apiResult.getResponseEntity();
    }

    @Operation(summary = "Xóa Danh muc Idg theo ID")
    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResult> deleteDonVi(@PathVariable("id") Number id) {
        ApiResult rs = new ApiResult();

        try {
            rs.setData(danhMucLoaiTaiNguyenIdgService.deleteLoaiTaiNguyenIdg(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}


