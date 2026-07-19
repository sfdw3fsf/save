package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.FrameTDDtoIn;
import vn.vnpt.vnptit.ecms.service.FrameTDService;

import javax.validation.Valid;

@RestController
@RequestMapping("frame-truyendan")
public class FrameTDController {
    private FrameTDService _frameTDService;

    public FrameTDController(FrameTDService frameTDService){
        this._frameTDService = frameTDService;
    }

    @Operation(summary = "Liệt kê danh sách Frame TD theo phân vùng id", description = "Liệt kê danh sách Frame TD theo phân vùng id")
    @RequestMapping(value = "/get-frametd", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDanhSachFrametd() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_frameTDService.getFrameTD());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Liệt kê danh sách Frame TD theo p_frametd_id", description = "Liệt kê danh sách Frame TD theo p_frametd_id")
    @RequestMapping(value = "/get-frametd-id", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDanhSachFrametdId(@RequestParam("frametd_id") Long p_frametd_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_frameTDService.getFrameTDId(p_frametd_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm một frametd", description = "Thêm một frametd")
    @RequestMapping(value = "/them-frametd", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> taoFrametd(@Valid @RequestBody FrameTDDtoIn frameTDDtoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_frameTDService.addFrameTD(frameTDDtoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Cập nhật Frame TD", description = "Cập nhật Frame TD")
    @RequestMapping(value = "/capnhat-frametd", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatFrametd(@Valid @RequestBody FrameTDDtoIn frameTDDtoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(_frameTDService.editFrameTD(frameTDDtoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Xóa Frame TD", description = "Xóa Frame TD")
    @RequestMapping(value = "/xoa-frametd", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaFrametd(@RequestParam("frametd_id") Long p_frametd_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(_frameTDService.deleteFrameTD(p_frametd_id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
