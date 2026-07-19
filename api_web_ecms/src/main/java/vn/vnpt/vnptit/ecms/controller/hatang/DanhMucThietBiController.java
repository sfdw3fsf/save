package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DanhMucParam;
import vn.vnpt.vnptit.ecms.service.hatang.DanhMucThietBiService;

@RestController
@RequestMapping("/danhmuc-thietbi")
public class DanhMucThietBiController {
    private final DanhMucThietBiService service;

    public DanhMucThietBiController(DanhMucThietBiService service) {
        this.service = service;
    }

    @PostMapping("")
    public ResponseEntity<ApiResult> danhMuc(@RequestBody DanhMucParam request) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(
                    this.service.getDanhMucVLan(request.getLoaiDanhMuc(), request.getThamSo1(), request.getThamSo2()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
