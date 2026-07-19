package vn.vnpt.vnptit.ecms.controller.danhmuc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.web.bind.annotation.RequestBody;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.QuanLyKetNoiDanhMucDto;
import vn.vnpt.vnptit.ecms.service.danhmuc.QuanLyKetNoiDanhMucService;

@RestController
@RequestMapping("/dien")
public class QuanLyKetNoiDanhMucController {
    @Autowired
    private QuanLyKetNoiDanhMucService service;

    @PostMapping("/danhmuc")
    public ResponseEntity<ApiResult> getDanhMuc(@RequestBody QuanLyKetNoiDanhMucDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(service.thiCongKetNoiDanhMuc(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping("/rack/search")
    public ResponseEntity<ApiResult> searchRack(@RequestBody JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(service.searchRack(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping("/tbi/search")
    public ResponseEntity<ApiResult> searchThietBi(@RequestBody JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(service.searchThietBi(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
}
