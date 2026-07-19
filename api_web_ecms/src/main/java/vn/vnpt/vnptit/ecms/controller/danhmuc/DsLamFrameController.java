package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DsLamFrameDto;
import vn.vnpt.vnptit.ecms.service.danhmuc.DsLamFrameService;

import javax.validation.Valid;

@RestController
@RequestMapping("danhmuc/dslam-frame")
public class DsLamFrameController {
    private final DsLamFrameService dsLamFrameService;

    public DsLamFrameController(DsLamFrameService dsLamFrameService) {
        this.dsLamFrameService = dsLamFrameService;
    }

    @Operation(summary = "Liệt kê danh sách DsLam Frame")
    @RequestMapping(value = "/getDsDsLamFrame", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDs() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dsLamFrameService.getDsDsLamFrame());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy DsLam Frame theo id")
    @RequestMapping(value = "/getDsDsLamFrameId/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getDsDsLamFrameId(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dsLamFrameService.getDsDsLamFrameId(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới DSLAM frame")
    @RequestMapping(value = "/themDsLamFrame", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> add(@Valid @RequestBody DsLamFrameDto.DslamFrameIns dsLamFrameIns) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dsLamFrameService.themDsLamFrame(dsLamFrameIns));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật DSLAM frame")
    @RequestMapping(value = "/capNhatDsLamFrame", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capnhat(@Valid @RequestBody DsLamFrameDto.DslamFrameUpd dsLamFrameUpd) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(dsLamFrameService.capNhatDsLamFrame(dsLamFrameUpd));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa DSLAM frame")
    @RequestMapping(value = "/xoaDsLamFrame", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa(@RequestParam("frameId") long frameId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(dsLamFrameService.xoa(frameId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
