package vn.vnpt.vnptit.ecms.controller.danhmuc;
    import io.swagger.v3.oas.annotations.Operation;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import vn.vnpt.database.AppSqlException;
    import vn.vnpt.message.ApiResult;
    import vn.vnpt.vnptit.ecms.dto.danhmuc.DanhMucDtoIn;
    import vn.vnpt.vnptit.ecms.service.danhmuc.PhanLoaiTTService;
@RestController
@RequestMapping("danhmuc/phan-loai-tt")
public class PhanLoaiTTController {
    private final PhanLoaiTTService phanLoaiTTervice;

    public PhanLoaiTTController(PhanLoaiTTService phanLoaiTTervice) {
        this.phanLoaiTTervice = phanLoaiTTervice;
    }


    @Operation(summary = "Danh sách phân loại tt")
    @GetMapping("/danh-sach")
    public ResponseEntity<ApiResult> danhSachPhanLoai() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(phanLoaiTTervice.getPhanLoaiTT());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật Danh mục phân loại TT")
    @PostMapping("/cap-nhat")
    public ResponseEntity<ApiResult> capNhatPhanLoaiTT(@RequestBody DanhMucDtoIn input) throws AppSqlException {
        return phanLoaiTTervice.capNhatPhanLoaiTT(input);
    }
    @Operation(summary = "Xóa Danh mục phân loại TT")
    @PostMapping("/xoa")
    public ResponseEntity<ApiResult> xoaPhanLoaiTT(@RequestBody DanhMucDtoIn input) throws AppSqlException {
        return phanLoaiTTervice.xoaPhanLoaiTT(input);
    }

}
