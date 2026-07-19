package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;

import vn.vnpt.vnptit.ecms.dto.hatang.tamthonghoiDTO;
import vn.vnpt.vnptit.ecms.service.hatang.tamthonghoiService;

@RestController
@RequestMapping("/tam-thong-hoi")

public class tamthonghoiController {

    private final tamthonghoiService tamthonghoiService;

    public tamthonghoiController(tamthonghoiService tamthonghoiService) {
        this.tamthonghoiService = tamthonghoiService;
    }

    @PostMapping("/phong/list")
    public ResponseEntity<ApiResult> loadPhong(@RequestBody tamthonghoiDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(tamthonghoiService.loadPhong(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/list")
    public ResponseEntity<ApiResult> loadGrid(
            @RequestBody tamthonghoiDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(tamthonghoiService.loadGrid(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(
            @RequestBody tamthonghoiDTO dto) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(tamthonghoiService.upsert(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResult> delete(@PathVariable Long id) {

        ApiResult rs = new ApiResult();

        try {
            rs.setData(tamthonghoiService.delete(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }

        return rs.getResponseEntity();
    }

}
