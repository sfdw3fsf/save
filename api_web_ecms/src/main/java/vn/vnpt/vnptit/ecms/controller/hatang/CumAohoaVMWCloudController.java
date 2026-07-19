package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.CumAohoaUpdateDtoIn;
import vn.vnpt.vnptit.ecms.service.hatang.CumAohoaVMWCloudService;

@RestController
//@CrossOrigin("*")
@RequestMapping("/hatang/cumaohoavmw")
public class CumAohoaVMWCloudController {

    @Autowired
    private CumAohoaVMWCloudService cumAohoaService;

    @PostMapping(value = "/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody CumAohoaUpdateDtoIn dto) {
        return cumAohoaService.capNhatCumAoHoa(dto);
    }

    @GetMapping(value = "/get_chitiet_cumhatang/{id}")
    public ResponseEntity<ApiResult> getDetailCluster(@PathVariable("id") Long cumhtId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumAohoaService.getDetailCluster(cumhtId));
        } catch (vn.vnpt.database.AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /**
     * Lấy danh sách Loại tài nguyên cung cấp theo Cum HT ID (SmartCloud)
     * Frontend gọi: GET /hatang/cumaohoavmw/loai-tai-nguyen/{cumId}
     */
    @GetMapping(value = "/loai-tai-nguyen/{cumId}")
    public ResponseEntity<ApiResult> getLoaiTaiNguyen(@PathVariable("cumId") Long cumId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumAohoaService.getLoaiTaiNguyenByCumId(cumId));
        } catch (vn.vnpt.database.AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /**
     * Lưu danh sách Loại tài nguyên cung cấp sau khi cập nhật cụm hạ tầng SmartCloud
     * Frontend gọi: POST /hatang/cumaohoavmw/save-loai-tai-nguyen
     * Request body: { "cumhtId": 123, "loaiTaiNguyenIds": "id1,id2,id3" }
     */
    @PostMapping(value = "/save-loai-tai-nguyen")
    public ResponseEntity<ApiResult> saveLoaiTaiNguyen(
            @RequestParam("cumhtId") Long cumhtId,
            @RequestParam("loaiTaiNguyenIds") String loaiTaiNguyenIds) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumAohoaService.saveTaiNguyenForCluster(cumhtId, loaiTaiNguyenIds));
        } catch (vn.vnpt.database.AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /**
    * Insert Năng lực Cụm Hạ tầng
     */
    @PostMapping("/insertnangluccumht")
    public ResponseEntity<ApiResult> insertNangLucCumHT(@RequestBody CumAohoaUpdateDtoIn dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cumAohoaService.insertNangLucCumHT(dto));
        } catch (vn.vnpt.database.AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
