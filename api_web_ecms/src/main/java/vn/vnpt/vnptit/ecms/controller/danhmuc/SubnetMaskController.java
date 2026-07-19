package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.SubnetMaskDto;
import vn.vnpt.vnptit.ecms.service.danhmuc.SubnetMaskService;

@RestController
@RequestMapping("danhmuc/subnetmask")
public class SubnetMaskController {
    private final SubnetMaskService subnetMaskService;
    public SubnetMaskController(SubnetMaskService service) {
        this.subnetMaskService = service;
    }

    @Operation(summary = "Liệt kê danh sách SubnetMask")
    @RequestMapping(value = "/danhsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> danhSachSubnetMask() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(subnetMaskService.getDSSubnetMask());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới thay đổi SubnetMask")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatSubnetMask(@RequestBody SubnetMaskDto input) {
        ApiResult rs = new ApiResult();
        try {
            subnetMaskService.thayDoiThongTinSubnetMask(input);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới thay đổi SubnetMask")
    @RequestMapping(value = "/xoa/{id}", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaSubnetMask(@PathVariable("id") Integer id) {
        ApiResult rs = new ApiResult();
        try {
            subnetMaskService.xoaSubnetMask(id);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Phân loại IP")
    @RequestMapping(value = "/phanloai-ip", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDSPhanLoaiIP() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(subnetMaskService.getDSPhanLoaiIP());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
