package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.*;
import vn.vnpt.vnptit.ecms.service.PrefixIpBrasService;

import javax.validation.Valid;


@RestController
@RequestMapping(value="/prefix-ip-bras")
public class PrefixIpBrasController {

    private final PrefixIpBrasService prefixIpBrasService;

    public PrefixIpBrasController(PrefixIpBrasService prefixIpBrasService) {
        this.prefixIpBrasService = prefixIpBrasService;
    }

    @Operation(summary = "Thêm mới prefix ip cho bras", description = "Thêm mới prefix ip cho bras")
    @RequestMapping(value = "/themmoi", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themMoiPrefixIp(@Valid @RequestBody ThemMoiPrefixIpDtoIn dtoIn) {
        ApiResult result = new ApiResult();
        try {
            result.setData(prefixIpBrasService.themMoiPrefixIp(dtoIn));
        } catch (AppSqlException e) {
            result.setException(e);
        }
        return result.getResponseEntity();
    }

    @Operation(summary = "Cập nhật prefix ip của bras", description = "Cập nhật prefix ip của bras")
    @RequestMapping(value = "/capnhat", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatPrefixIp(@Valid @RequestBody CapNhatPrefixIpDtoIn dtoIn) {
        ApiResult result = new ApiResult();
        try {
            result.setData(new ResultOutput(prefixIpBrasService.capNhatPrefixIp(dtoIn)));
        } catch (AppSqlException e) {
            result.setException(e);
        }
        return result.getResponseEntity();
    }

    @Operation(summary = "Xóa prefix ip của bras", description = "Xóa prefix ip của bras")
    @RequestMapping(value = "/xoa", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaPrefixIp(XoaPrefixIpDtoIn dtoIn) {
        ApiResult result = new ApiResult();
        try {
            result.setData(new ResultOutput(prefixIpBrasService.xoaPrefixIp(dtoIn)));
        } catch (AppSqlException e) {
            result.setException(e);
        }
        return result.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách prefix ip", description = "Lấy danh sách prefix ip")
    @RequestMapping(value = "/lay-ds-prefix-ip", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsPrefixIp() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(prefixIpBrasService.layDsPrefixIp());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra prefix ip trước khi xóa", description = "Kiểm tra prefix ip trước khi xóa")
    @RequestMapping(value = "/kiemtra-prefix-ip", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemTraPrefixIp(@RequestParam("prefix") String prefix) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(prefixIpBrasService.kiemTraPrefixIp(prefix));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Sinh Ip", description = "Sinh Ip")
    @RequestMapping(value = "/sinh-ip", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> sinhIp(@Valid @RequestBody SinhIpDtoIn dtoIn) {
        ApiResult result = new ApiResult();
        try {
            result.setData(new ResultOutput(prefixIpBrasService.sinhIp(dtoIn)));
        } catch (AppSqlException e) {
            result.setException(e);
        }
        return result.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách IP", description = "Lấy danh sách IP")
    @RequestMapping(value = "/lay-ds-ip", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsIp(@RequestParam("prefix") String prefix) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(prefixIpBrasService.layDsIp(prefix));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách loại hình thuê bao đã gán", description = "Lấy danh sách loại hình thuê bao đã gán")
    @RequestMapping(value = "/lay-ds-loaitb-dagan", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsLoaiTbDaGan(@RequestParam("prefix") String prefix) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(prefixIpBrasService.layDsLoaiTbDaGan(prefix));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách loại hình thuê bao chưa gán", description = "Lấy danh sách loại hình thuê bao chưa gán")
    @RequestMapping(value = "/lay-ds-loaitb-chuagan", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsLoaiTbDaGan(@RequestBody LayDsLoaiTbChuaGanDtoIn dtoIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(prefixIpBrasService.layDsLoaiTbChuaGan(dtoIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục dịch vụ viễn thông", description = "Lấy danh mục dịch vụ viễn thông")
    @RequestMapping(value = "/dm-dichvu-vt", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDmDichVuVt() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(prefixIpBrasService.layDmDichVuVt());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục loại IP", description = "Lấy danh mục loại IP")
    @RequestMapping(value = "/dm-loai-ip", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDmLoaiIp() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(prefixIpBrasService.layDmLoaiIp());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
