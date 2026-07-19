package vn.vnpt.vnptit.qlsc.controller.cntt;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.qlsc.dto.cntt.ChiTietThuTucXuLyInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.DmLyDoHaTangInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.DmThuTucHaTangInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.DongThuTucInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.LuuBuocThuTucInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.TaoGiaoPhieuThuTucInput;
import vn.vnpt.vnptit.qlsc.service.cntt.ChiTietThuTucXLQT45Service;

import javax.validation.Valid;

@RestController
@RequestMapping("/thutuc-xuly-cntt")
public class ChiTietThuTucXLQT45Controller {

    @Autowired
    ChiTietThuTucXLQT45Service service;

    @Operation(summary = "Chi tiết thủ tục xử lý")
    @RequestMapping(value = "/chitiet-thutuc-xuly", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> chiTietThuTucXuLy(@RequestBody @Valid ChiTietThuTucXuLyInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.chiTietThuTucXuLy(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Chi tiết ảnh của thủ tục xử lý")
    @RequestMapping(value = "/anh-thutuc-xuly", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> chiTietThuTucAnhXuLy(@RequestBody @Valid ChiTietThuTucXuLyInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.chiTietThuTucAnhXuLy(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lưu bước xử lý thủ tục")
    @RequestMapping(value = "/luu-buoc-thutuc", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> luuBuocThuTuc(@RequestBody @Valid LuuBuocThuTucInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.luuBuocThuTuc(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đóng thủ tục xử lý")
    @RequestMapping(value = "/dong-thutuc-xuly", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dongThuTuc(@RequestBody @Valid DongThuTucInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dongThuTuc(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh mục thủ tục hạ tầng")
    @RequestMapping(value = "/dm-thutuc-hatang", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dmThuTucHaTang(@RequestBody @Valid DmThuTucHaTangInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dmThuTucHaTang(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh mục lý do hạ tầng")
    @RequestMapping(value = "/dm-lydo-hatang", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dmLyDoHaTang(@RequestBody @Valid DmLyDoHaTangInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dmLyDoHaTang(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo giao phiếu thủ tục")
    @RequestMapping(value = "/tao-giaophieu-thutuc", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> taoGiaoPhieuThuTuc(@RequestBody @Valid TaoGiaoPhieuThuTucInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.taoGiaoPhieuThuTuc(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
