package vn.vnpt.vnptit.ecms.controller.hatang;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.hatang.GetCumDichVuDto;
import vn.vnpt.vnptit.ecms.dto.hatang.PhieuYeuCauThuHoiInputDto;
import vn.vnpt.vnptit.ecms.dto.hatang.ThongTinYeuCauDto;
import vn.vnpt.vnptit.ecms.dto.hatang.XacNhanYeuCauThuHoiInputDto;
import vn.vnpt.vnptit.ecms.service.hatang.HaTangThuHoiTaiNguyenService;

@Api(value = "HaTangThuHoiThietBi Controller", description = "Thu hồi tài nguyên")
@RestController
@RequestMapping("hatang/thu-hoi-tai-nguyen")
public class HaTangThuHoiTaiNguyenController {
    private final HaTangThuHoiTaiNguyenService service;

    public HaTangThuHoiTaiNguyenController(HaTangThuHoiTaiNguyenService service) {
        this.service = service;
    }

    @Operation(summary = "Lấy danh sách sản phẩm dịch vụ")
    @RequestMapping(value = "/get-spdv", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getSPDVByUserId() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getSPDVByUserId());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách cụm dịch vụ theo SPDV")
    @RequestMapping(value = "/get-ds-cumdv-by-spdv", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsCudvBySpdv(@RequestBody GetCumDichVuDto getCumDichVuDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDsCudvBySpdv(getCumDichVuDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách yêu cầu thu hồi ảo hoá theo yêu cầu id")
    @RequestMapping(value = "/get-aohoa-yeucau", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getAoHoaYeuCau(@RequestBody ThongTinYeuCauDto yeuCauId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getAoHoaYeuCau(yeuCauId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách yêu cầu thu hồi ảo hoá theo yêu cầu id")
    @RequestMapping(value = "/get-k8s-yeucau", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getK8SYeuCau(@RequestBody ThongTinYeuCauDto yeuCauId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getK8SYeuCau(yeuCauId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách yêu cầu thu hồi ảo hoá theo yêu cầu id")
    @RequestMapping(value = "/get-idg-yeucau", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getIDGYeuCau(@RequestBody ThongTinYeuCauDto yeuCauId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getIDGYeuCau(yeuCauId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách yêu cầu thu hồi ảo hoá theo yêu cầu id")
    @RequestMapping(value = "/get-backup-yeucau", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getBackupYeuCau(@RequestBody ThongTinYeuCauDto yeuCauId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getBackupYeuCau(yeuCauId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách yêu cầu thu hồi ảo hoá theo yêu cầu id")
    @RequestMapping(value = "/get-phieu-yeucau-by-id", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getPhieuYeuCauById(@RequestBody ThongTinYeuCauDto yeuCauId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getPhieuYeuCauById(yeuCauId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo phiếu yêu cầu thu hồi") 
    @RequestMapping(value = "/create-phieu-yeucau-thuhoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> createPhieuYeuCauThuHoi(@RequestBody PhieuYeuCauThuHoiInputDto yeuCau) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.createPhieuYeuCauThuHoi(yeuCau));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật phiếu yêu cầu thu hồi")
    @RequestMapping(value = "/update-phieu-yeucau-thuhoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updatePhieuYeuCauThuHoi(@RequestBody PhieuYeuCauThuHoiInputDto yeuCau) {
        ApiResult rs = new ApiResult();
        try {
            yeuCau.validUpdate();
            rs.setData(this.service.updatePhieuYeuCauThuHoi(yeuCau));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /* Xác nhận yêu cầu thu hồi - START - HoangMN */
    @Operation(summary = "Xác nhận phiếu yêu cầu thu hồi")
    @RequestMapping(value = "/xacnhan-phieu-yeucau-thuhoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xacNhanYeuCauThuHoi(@RequestBody XacNhanYeuCauThuHoiInputDto yeuCau) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.xacNhanYeuCauThuHoi(yeuCau));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();

    }
        /* Xác nhận yêu cầu thu hồi - END - HoangMN */


    @Operation(summary = "Lấy danh sách phiếu yêu cầu thu hồi")
    @RequestMapping(value = "/get-ds-phieu-yeucau-thuhoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsPhieuYeuCauThuHoi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDsPhieuYeuCauThuHoi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    

    @Operation(summary = "Xóa phiếu yêu cầu thu hồi")
    @RequestMapping(value = "/delete-phieu-yeucau-thuhoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> deletePhieuYeuCauThuHoi(@RequestBody ThongTinYeuCauDto yeuCauId) {
        ApiResult rs = new ApiResult();
        try { 
            rs.setData(this.service.deletePhieuYeuCauThuHoi(yeuCauId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng ảo hoá")
    @RequestMapping(value = "/get-ds-aohoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsAoHoa() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachHaTangAoHoa());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng idg") 
    @RequestMapping(value = "/get-ds-idg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsIdg() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachHaTangIDG());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng backup")
    @RequestMapping(value = "/get-ds-backup", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDsBackup() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.getDanhSachHaTangBackup());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Gửi yêu cầu thu hồi")
    @RequestMapping(value = "/send-yeucau-thuhoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> sendYeuCauThuHoi(@RequestBody ThongTinYeuCauDto yeuCauId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.service.guiYeuCauThuHoi(yeuCauId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
