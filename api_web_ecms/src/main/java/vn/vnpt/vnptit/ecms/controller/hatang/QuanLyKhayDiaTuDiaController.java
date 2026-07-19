package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ModelValidation;
import vn.vnpt.vnptit.ecms.dto.hatang.KhayDiaTrongTuDiaDTO;
import vn.vnpt.vnptit.ecms.service.hatang.QuanLyKhayDiaTuDiaService;


@RestController
@RequestMapping(value = "khaydia-tudia")
public class QuanLyKhayDiaTuDiaController {
    @Autowired
    private QuanLyKhayDiaTuDiaService service;

    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody KhayDiaTrongTuDiaDTO dto) {
        ApiResult rest = new ApiResult();
        boolean valid = dto.valid();
        if (!valid) {
            rest.setData(ModelValidation.INVALID);
            return rest.getResponseEntity();
        }
        try {
            rest.setData(service.createOrUpdate(dto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rest.getResponseEntity();
    }

    @GetMapping(value = "/get-by-thietbi-id")
    public ResponseEntity<ApiResult> getListByThietBiId(@RequestParam(value = "id") int id) {
        ApiResult rest = new ApiResult();
        try {
            rest.setData(service.getListByThietBiId(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rest.getResponseEntity();
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<ApiResult> delete(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        rs.setData(this.service.deleteById(id));
        return rs.getResponseEntity();
    }

    @GetMapping(value = "/detail/{id}")
    public ResponseEntity<ApiResult> getDetail(@PathVariable(value = "id") int id) {
        ApiResult rest = new ApiResult();
        try {
            rest.setData(service.getDetailById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rest.getResponseEntity();
    }

    @GetMapping(value = "/change-history")
    public ResponseEntity<ApiResult> getChangeHistoryList(@RequestParam(value = "id")int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getLichSuById(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
