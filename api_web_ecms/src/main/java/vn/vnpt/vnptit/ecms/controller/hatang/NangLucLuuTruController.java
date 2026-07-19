package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.NangLucLuuTruDTO;
import vn.vnpt.vnptit.ecms.service.hatang.NangLucLuuTruService;

import java.util.Map;

@RestController
@RequestMapping("nang-luc-luu-tru")
public class NangLucLuuTruController {

    @Autowired
    NangLucLuuTruService service;

    @Operation(summary = "Lấy dữ liệu thiết bị tủ đĩa theo id của thiết bị")
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResult> getNLLTByThietBiId(@PathVariable("id") Number id) {
        ApiResult res = new ApiResult();

        try {
            res.setData(service.getNangLucByThietBiId(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }

        return res.getResponseEntity();
    }

    @Operation(summary = "Cập nhập hoặc Thêm mới thiết bị tủ đĩa")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> save(@RequestBody NangLucLuuTruDTO dto) {
        ApiResult res = new ApiResult();

        try {
            res.setData(service.upsertNangLuc(dto));
        } catch (AppSqlException e) {
            res.setException(e);
        }

        return res.getResponseEntity();
    }

}
