package vn.vnpt.vnptit.ecms.controller.danhmuc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.Message;
import vn.vnpt.vnptit.ecms.model.LoaiDsLamModel;
import vn.vnpt.vnptit.ecms.service.danhmuc.LoaiDSLamService;

import java.util.ArrayList;
import java.util.List;

@RestController()
@Configuration
@RequestMapping("danhmuc")
public class LoaiDSLamController {

    @Autowired
    private LoaiDSLamService loaiDSLamService;

    @GetMapping("ds-loaidslam")
    public ResponseEntity<ApiResult> getListLoaiDsLam() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiDSLamService.getLoaiDsLam());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return  rs.getResponseEntity();
    }

    @PostMapping("loaidslam")
    public ResponseEntity<ApiResult> addLoaiDsLam(@RequestBody LoaiDsLamModel loaiDsLam) {
        ApiResult rs = new ApiResult();
        try {
            if(loaiDSLamService.addLoaiDsLam(loaiDsLam) > 0) {
                return rs.getResponseEntity(Message.Codes.BSS_00000204);
            }
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity(Message.Codes.BSS_00000500);
    }

    @PutMapping("loaidslam/{id}")
    public ResponseEntity<ApiResult> updateLoaiDsLam(@RequestBody LoaiDsLamModel loaiDsLam, @PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiDSLamService.editLoaiDsLamById(loaiDsLam, id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return  rs.getResponseEntity();
    }

    @DeleteMapping("loaidslam/{id}")
    public  ResponseEntity<ApiResult> deleteLoaiDsLam(@PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            loaiDSLamService.deleteLoaiDsLamById(id);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return  rs.getResponseEntity(Message.Codes.BSS_00000204);
    }

    @GetMapping("loaidslam/{id}")
    public ResponseEntity<ApiResult> getLoaiDsLam(@PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiDSLamService.getLoaiDsLamById(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
