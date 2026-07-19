package vn.vnpt.vnptit.ecms.controller.dongbo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.service.dongbo.DongBoIDGService;

@RestController
@RequestMapping("DongBoIDG")
public class DongBoIDGController {

    private final DongBoIDGService dongBoIDGService;

    public DongBoIDGController(DongBoIDGService dongBoIDGService) {
        this.dongBoIDGService = dongBoIDGService;
    }

    @Operation(summary = "Đồng bộ thông tin toàn bộ cụm hạ tầng IDG")
    @PostMapping(value = "/dong-bo-cum-hatang-idg")
    public ResponseEntity<ApiResult> dongBoToanBo() {
        System.out.println("Vao controller dong bo");
        ApiResult rs = new ApiResult();
        try {
            dongBoIDGService.dongBoToanBoCumHaTang();
            rs.setData(new ResultOutput(true));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ năng lực hạ tầng IDG")
    @PostMapping(value = "/dong-bo-nang-luc")
    public ResponseEntity<ApiResult> dongBoNangLuc() {
        ApiResult rs = new ApiResult();
        try {
            dongBoIDGService.dongBoNangLucHaTang();
            rs.setData(new ResultOutput(true));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ thông tin của 1 cụm hạ tầng IDG")
    @PostMapping(value = "/dong-bo-theo-cum")
    public ResponseEntity<ApiResult> dongBoTheoCum(
            @RequestParam("cluster_id") String clusterId,
            @RequestParam("rancher_code") String rancherCode) {
        ApiResult rs = new ApiResult();

        dongBoIDGService.dongBoChiTietCumHaTang(clusterId, rancherCode);
        rs.setData(new ResultOutput(true));
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin chi tiết cấu hình của 1 cụm IDG")
    @PostMapping(value = "/lay-thong-tin-tung-cum")
    public ResponseEntity<ApiResult> layThongTinTungCum(
            @RequestParam("cluster_id") String clusterId) {
        ApiResult rs = new ApiResult();
        try {
            java.util.Map<String, Object> data = dongBoIDGService.layThongTinTungCum(clusterId);
            rs.setData(data);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ chi tiết dịch vụ IDG (Cụm nội bộ)")
    @PostMapping(value = "/dong-bo-chi-tiet-dich-vu-idg-noibo")
    public ResponseEntity<ApiResult> dongBoChiTietDichVu(
            @RequestParam("cluster_id") String clusterId,
            @RequestParam("rancher_code") String rancherCode,
            @RequestParam("service_name") String serviceName) {
        ApiResult rs = new ApiResult();


        dongBoIDGService.dongBoChiTietDichVu(clusterId, rancherCode, serviceName);
        rs.setData(new ResultOutput(true));

        return rs.getResponseEntity();
    }

    @Operation(summary = "Đồng bộ chi tiết tài nguyên IDG public (VPC Cloud)")
    @PostMapping(value = "/dong-bo-chi-tiet-dich-vu-idg-public")
    public ResponseEntity<ApiResult> dongBoChiTietDichVuPublic(
            @RequestParam("cluster_id") String clusterId,
            @RequestParam("vpc_id") String vpcId,
            @RequestParam("service_name") String serviceName) {
        ApiResult rs = new ApiResult();

        dongBoIDGService.dongBoChiTietDichVuPublic(clusterId, vpcId, serviceName);
        rs.setData(new ResultOutput(true));

        return rs.getResponseEntity();
    }
}
