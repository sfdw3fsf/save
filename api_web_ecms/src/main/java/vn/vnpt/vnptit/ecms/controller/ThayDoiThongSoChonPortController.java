package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.PortQuyHoachDto;
import vn.vnpt.vnptit.ecms.service.ThayDoiThongSoChonPortService;

@RestController
@RequestMapping("/port-quy-hoach")
public class ThayDoiThongSoChonPortController {

    private ThayDoiThongSoChonPortService chonPortService;

    public ThayDoiThongSoChonPortController(ThayDoiThongSoChonPortService chonPortService) {
        this.chonPortService = chonPortService;
    }

    @Operation(summary = "Get ds port quy hoach", description = "Lấy danh sách port quy hoạch")
    @RequestMapping(value = "/lay-ds-port-quy-hoach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_port_quy_hoach(@RequestBody PortQuyHoachDto data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chonPortService.lay_ds_port_quy_hoach(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get ds port quy hoach", description = "Lấy danh sách port quy hoạch")
    @RequestMapping(value = "/lay-ds-port-quy-hoach-v2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_port_quy_hoachV2(@RequestBody PortQuyHoachDto data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chonPortService.lay_ds_port_quy_hoachV2(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Count port quy hoach", description = "Đếm số lượng port quy hoạch")
    @RequestMapping(value = "/lay-count-port-quy-hoach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_count_port_quy_hoach(@Valid @RequestBody PortQuyHoachDto data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chonPortService.lay_count_port_quy_hoach(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Count port quy hoach v2", description = "Đếm số lượng port quy hoạch")
    @RequestMapping(value = "/lay-count-port-quy-hoach-v2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_count_port_quy_hoach_v2(@Valid @RequestBody PortQuyHoachDto data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chonPortService.lay_count_port_quy_hoach_v2(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Count port quy hoach v3", description = "Đếm số lượng port quy hoạch")
    @RequestMapping(value = "/lay-count-port-quy-hoach-v3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_count_port_quy_hoach_v3(@Valid @RequestBody PortQuyHoachDto data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chonPortService.lay_count_port_quy_hoach_v3(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Get ds port quy hoach", description = "Lấy danh sách port quy hoạch")
    @RequestMapping(value = "/lay-ds-port-quy-hoach-v3", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_port_quy_hoachV3(@RequestBody PortQuyHoachDto data) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chonPortService.lay_ds_port_quy_hoachV3(data));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
