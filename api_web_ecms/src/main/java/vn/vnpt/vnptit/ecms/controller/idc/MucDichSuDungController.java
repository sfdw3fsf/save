package vn.vnpt.vnptit.ecms.controller.idc;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteMucDichSuDungDto;
import vn.vnpt.vnptit.ecms.dto.idc.MucDichSuDungDto;
import vn.vnpt.vnptit.ecms.service.idc.MucDichSuDungService;

@RestController
@RequestMapping("idc/mucdich-sd")
public class MucDichSuDungController {
    private final MucDichSuDungService service;

    public MucDichSuDungController(MucDichSuDungService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách hoặc tìm kiếm mục đích sử dụng (truyền param để search, truyền {} để lấy tất cả)")
    @PostMapping("")
    public ResponseEntity<ApiResult> listMucDichSuDung(@RequestBody(required = false) MucDichSuDungDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getListMucDichSuDung(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết mục đích sử dụng theo ID")
    @PostMapping("/detail")
    public ResponseEntity<ApiResult> getMucDichSuDungById(@RequestBody DataKeyInput item) {
        ApiResult res = new ApiResult();
        try {
            res.setData(this.service.getMucDichSuDungById(item.getId()));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @Operation(summary = "Thêm mới hoặc cập nhật mục đích sử dụng (upsert)")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsertMucDichSuDung(@RequestBody MucDichSuDungDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.upsertMucDichSuDung(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa nhiều mục đích sử dụng")
    @PostMapping("/delete-multi")
    public ResponseEntity<ApiResult> deleteMucDichSuDungMulti(@RequestBody DeleteMucDichSuDungDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.deleteMucDichSuDungMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate một mục đích sử dụng")
    @PostMapping("/validate")
    public ResponseEntity<ApiResult> validateMucDichSuDung(@RequestBody MucDichSuDungDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateMucDichSuDung(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Validate nhiều mục đích sử dụng")
    @PostMapping("/validate-multi")
    public ResponseEntity<ApiResult> validateMucDichSuDungMulti(@RequestBody List<MucDichSuDungDto> dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.validateMucDichSuDungMulti(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}

