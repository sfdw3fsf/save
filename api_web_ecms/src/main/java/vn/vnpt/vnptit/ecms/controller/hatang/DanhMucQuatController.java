package vn.vnpt.vnptit.ecms.controller.hatang;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucLoaiThietBiDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucQuatDTO;
import vn.vnpt.vnptit.ecms.service.hatang.DanhMucQuatService;

import javax.validation.Valid;
import java.io.IOException;
@RestController
@RequestMapping("hatang/danhmuc-quat")
public class DanhMucQuatController {
    private final DanhMucQuatService quatService;

    public DanhMucQuatController(DanhMucQuatService quatService) {
        this.quatService = quatService;
    }

    @Operation(summary = "Lấy tất cả danh mục phòng")
    @RequestMapping(value = "/get-all-phong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAllPhong() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.quatService.getDanhMucPhong());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy tất cả danh mục quạt")
    @RequestMapping(value = "/get-all-quat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAllQuat() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.quatService.getDanhMucQuat());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật danh mục quạt")
    @RequestMapping(value = "/insert-quat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> update(@Valid @RequestBody DanhMucQuatDTO input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(this.quatService.insertQuat(input)));
        } catch (AppSqlException | IOException e) {
            throw new RuntimeException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa danh mục quạt theo ID")
    //@DeleteMapping("/delete/{id}")
    @RequestMapping(value = "/delete/{p_id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deleteDonVi(@PathVariable("p_id") Number id) {
        ApiResult rs = new ApiResult();

        try {
            rs.setData(quatService.deleteQuat(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
