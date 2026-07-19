package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiRackDTO;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiRackBatchDTO;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiRackBatchResponseDTO;
import vn.vnpt.vnptit.ecms.service.idc.LoaiRackService;

import javax.validation.Valid;

@RestController
@RequestMapping("/idc/loai-rack")
public class LoaiRackController {

    @Autowired
    LoaiRackService loaiRackService;

    @Operation(summary = "Lấy danh sách loại rack")
    @RequestMapping(value = "/lay-danhsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_danhsach() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiRackService.layDsLoaiRack());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "insert and update loại rack")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tao(@Valid @RequestBody LoaiRackDTO input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(loaiRackService.updateInsertLoaiRack(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xóa loại rack")
    @RequestMapping(value = "/xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(loaiRackService.xoaLoaiRack(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới nhiều loại rack (batch insert)")
    @RequestMapping(value = "/bulk-create", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themNhieu(@Valid @RequestBody LoaiRackBatchDTO input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiRackService.batchInsertLoaiRack(input.getDanhSachLoaiRack()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate dữ liệu trước khi thêm mới nhiều loại rack")
    @RequestMapping(value = "/validate-batch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> validateBatch(@Valid @RequestBody LoaiRackBatchDTO input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiRackService.validateBatchLoaiRack(input.getDanhSachLoaiRack()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
