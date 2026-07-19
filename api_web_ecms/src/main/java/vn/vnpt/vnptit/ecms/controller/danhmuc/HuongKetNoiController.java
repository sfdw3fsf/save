package vn.vnpt.vnptit.ecms.controller.danhmuc;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.HuongKetNoiBrasInput;
import vn.vnpt.vnptit.ecms.service.danhmuc.HuongKetNoiService;

import javax.validation.Valid;

@RestController
@RequestMapping("/danhmuc/huongketnoi")
public class HuongKetNoiController {

    private final HuongKetNoiService huongKetNoiService;

    public HuongKetNoiController(HuongKetNoiService huongKetNoiService)
    {
        this.huongKetNoiService = huongKetNoiService;
    }

    @Operation(summary = "Liệt kê danh sách hướng kết nối")
    @RequestMapping(value = "/danhsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lietKeHuongKetNoi(@Valid @RequestBody  HuongKetNoiBrasInput.HuongKetNoiBrasInputLayDsHuongKetNoi huongKetNoiBrasInputLayDsHuongKetNoi) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(huongKetNoiService.lietKeHuongKetNoi(huongKetNoiBrasInputLayDsHuongKetNoi));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy một hướng kết nối", description = "Lấy một hướng kết nối")
    @RequestMapping(value = "/chitiet/{id}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layHuongKetNoiTheoId(@PathVariable(value = "id") String id) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(huongKetNoiService.layHuongKetNoiTheoId(id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm một hướng kết nối", description = "Thêm một hướng kết nối")
    @RequestMapping(value = "/them", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themHuongKetNoi(@Valid @RequestBody HuongKetNoiBrasInput huongKetNoi) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(huongKetNoiService.themHuongKetNoi(huongKetNoi));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Sửa một hướng kết nối", description = "Sửa một hướng kết nối")
    @RequestMapping(value = "/capnhat", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> suaHuongKetNoi(@Valid @RequestBody HuongKetNoiBrasInput.HuongKetNoiBrasInputLayDsHuongKetNoiUpd huongKetNoi) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(huongKetNoiService.suaHuongKetNoi(huongKetNoi));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa một hướng kết nối", description = "Xóa một hướng kết nối")
    @RequestMapping(value = "/xoa/{id}", produces = {"application/json"}, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaHuongKetNoi(@PathVariable(value = "id") String id) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(huongKetNoiService.xoaHuongKetNoi(id));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục hướng kết nối", description = "Lấy danh mục hướng kết nối")
    @RequestMapping(value = "/laydanhmuc", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDMHuongKetNoi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(huongKetNoiService.layDMHuongKetNoi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
