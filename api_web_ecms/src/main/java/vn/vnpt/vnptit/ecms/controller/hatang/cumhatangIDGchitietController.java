package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.cumhatangIDGchitietDTO;
import vn.vnpt.vnptit.ecms.service.hatang.cumhatangIDGchitietService;

@RestController
@RequestMapping("/cum-hatang-idg-chitiet")
public class cumhatangIDGchitietController {

    private final cumhatangIDGchitietService service;

    public cumhatangIDGchitietController(cumhatangIDGchitietService service) {
        this.service = service;
    }

    // ===== LICH SU =====
    //@CrossOrigin(origins = "*")
    @PostMapping("/lich-su")
    public ResponseEntity<ApiResult> lichSu(@RequestBody cumhatangIDGchitietDTO dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.lichSuThayDoi(dto.getId())); // ✅ fix dto
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    // ===== HA TANG MANG =====
    //@CrossOrigin(origins = "*")
    @PostMapping("/hatang-mang")
    public ResponseEntity<ApiResult> hatangMang(@RequestBody cumhatangIDGchitietDTO dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.hatangMang(dto.getId())); // ✅ fix dto
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    // ===== CHI TIET =====
    //@CrossOrigin(origins = "*")
    @PostMapping("/hatang-mang-chitiet")
    public ResponseEntity<ApiResult> hatangMangChitiet(@RequestBody cumhatangIDGchitietDTO dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.hatangMangChitiet(dto.getCumhtId())); // ✅ đúng field
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}