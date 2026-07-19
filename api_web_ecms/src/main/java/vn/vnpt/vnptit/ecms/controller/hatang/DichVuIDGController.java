package vn.vnpt.vnptit.ecms.controller.hatang;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchDichVuIDGDto;
import vn.vnpt.vnptit.ecms.dto.hatang.SaveIdgGiaHanDto;
import vn.vnpt.vnptit.ecms.dto.hatang.SaveVpcGiaHanDto;
import vn.vnpt.vnptit.ecms.service.hatang.DichVuIDGService;

@RestController
@RequestMapping("/hatang/dichvu-idg")
public class DichVuIDGController {
    private final DichVuIDGService dichVuIDGService;

    public DichVuIDGController(DichVuIDGService dichVuIDGService) {
        this.dichVuIDGService = dichVuIDGService;
    }

    @Operation(summary = "Lấy danh mục đối tượng IDG")
    @RequestMapping(value = "/get-all-doituong-idg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhMucDoiTuong() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.getDanhMucDoiTuong());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục trạng thái IDG")
    @RequestMapping(value = "/get-all-trangthai-idg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhMucTrangThai() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.getDanhMucTrangThai());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục loại dịch vụ IDG")
    @RequestMapping(value = "/get-all-loaidv-idg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhMucLoaiDichVu() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.getDanhMucLoaiDichVu());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục tên SPDV IDG")
    @RequestMapping(value = "/get-all-ten-spdv-idg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhMucTenSPDV() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.getDanhMucTenSPDV());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tìm kiếm dịch vụ IDG")
    @RequestMapping(value = "/search-dichvu-idg", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> searchDichVuIDG(@RequestBody SearchDichVuIDGDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.searchDichVuIDG(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng IDG")
    @RequestMapping(value = "/get-hatang-idg-vmware", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachHaTangIDGVmware() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.getDanhSachHaTangIDGVmware());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách hạ tầng IDG")
    @RequestMapping(value = "/get-hatang-idg-smart-cloud", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> getDanhSachHaTangIDGSmartCloud() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.getDanhSachHaTangIDGSmartCloud());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin phiếu hủy/gia hạn Smart Cloud")
    @RequestMapping(value = "/get-phieu-huy-giahan-smart-cloud/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getPhieuHuyGiaHanSmartCloud(@PathVariable(value = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.getPhieuHuyGiaHanSmartCloud(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin phiếu hủy/gia hạn VmWare")
    @RequestMapping(value = "/get-phieu-huy-giahan-vmware/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getPhieuHuyGiaHanVmWare(@PathVariable(value = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.getPhieuHuyGiaHanVmWare(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin phiếu hủy/gia hạn IDG")
    @RequestMapping(value = "/get-phieu-huy-giahan-idg/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> getPhieuHuyGiaHanIDG(@PathVariable(value = "id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.getPhieuHuyGiaHanIDG(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lưu thông tin gia hạn VPC")
    @RequestMapping(value = "/save-vpc-giahan", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveVpcGiaHan(@RequestBody SaveVpcGiaHanDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.saveVpcGiaHan(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lưu thông tin gia hạn IDG")
    @RequestMapping(value = "/save-idg-giahan", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> saveIdgGiaHan(@RequestBody SaveIdgGiaHanDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(this.dichVuIDGService.saveIdgGiaHan(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}

