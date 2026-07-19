package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.hatang.CumHaTangIdgDto;
import vn.vnpt.vnptit.ecms.service.hatang.CumHaTangIdgService;

@RestController
//@CrossOrigin("*")
@RequestMapping("hatang/cum-hatang-idg")
public class CumHaTangIdgController {

    private final CumHaTangIdgService service;

    public CumHaTangIdgController(CumHaTangIdgService service) {
        this.service = service;
    }

    @Operation(summary = "Lưu hoặc cập nhật thông tin Cụm hạ tầng IDG")
    @PostMapping(value = "/save-cum-hatang-idg")
    public ResponseEntity<ApiResult> save(@RequestBody CumHaTangIdgDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.upsertCumHaTang(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin Cụm hạ tầng IDG")
    @PostMapping(value = "/get-cluster")
    public ResponseEntity<ApiResult> getCluster(@RequestBody Map<String, String> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getCluster(input.get("p_cluster_id")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách loại dịch vụ IDG")
    @PostMapping(value = "/list-loai-dich-vu")
    public ResponseEntity<ApiResult> getListLoaiDichVuIdg(@RequestBody Map<String, Integer> input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getListLoaiDichVuIdg(input.get("loai_ht_id")));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách cụm SPDV")
    @PostMapping(value = "/ds-cum-spdv")
    public ResponseEntity<ApiResult> getListCumSPDV(@RequestBody DataKeyInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.getDsCumSPDV(input.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy chi tiết cụm hạ tầng IDG")
    @GetMapping(value = "/get_chitiet_cumhatang_idg/{id}")
    public ResponseEntity<ApiResult> getDetailCluster(@PathVariable("id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDetailCluster(id));
        } catch (AppSqlException e) {
            rs.setException(e);

        }
        return rs.getResponseEntity();
    }
}
