package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.GiaoTiep.*;
import vn.vnpt.vnptit.ecms.dto.GiaoTiepCommmandDtoIn;
import vn.vnpt.vnptit.ecms.service.GiaoTiepThietBiService;

@RestController
@RequestMapping("giaotiep-thietbi")
public class GiaoTiepThietBiController {
    @Autowired
    private GiaoTiepThietBiService giaoTiepThietBiService;

    @Operation(summary = "Lấy danh sách thiết bị OLT thực hiện")
    @GetMapping("lay-danhsach-thietbi-olt-giaotiep")
    public ResponseEntity<ApiResult> getListDeviceOLT() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(giaoTiepThietBiService.getListDeviceOLT());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách thiết bị OLT thực hiện cấu hình")
    @GetMapping("lay-danhsach-thietbi-olt-dslam")
    public ResponseEntity<ApiResult> getListDeviceOLTDslam() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(giaoTiepThietBiService.getListDeviceOLTDslam());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh mục các nghiệp vụ")
    @GetMapping("lay-danhmuc-nghiepvu")
    public ResponseEntity<ApiResult> getListService() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(giaoTiepThietBiService.getListService());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách yêu cầu giao tiếp thiết bị OLT")
    @GetMapping("lay-danhsach-yeucau-giaotiep/{idApp}")
    public ResponseEntity<ApiResult> getRequestList(@PathVariable String idApp ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(giaoTiepThietBiService.getRequestList(idApp));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách lệnh của 1 yêu cầu theo nghiệp vụ")
    @PostMapping("lay-danhsach-lenh-yeucau")
    public ResponseEntity<ApiResult> getCommmandList(@RequestBody GiaoTiepCommmandDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(giaoTiepThietBiService.getCommmandList(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách lệnh của 1 nghiệp vụ")
    @PostMapping("lay-danhsach-lenh-theo-nghiepvu")
    public ResponseEntity<ApiResult> getServiceCommmandList(@RequestBody DanhSachServiceDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(giaoTiepThietBiService.getServiceCommmandList(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Cập nhật cấu hình OLT")
    @PostMapping("capnhat-cauhinh-olt")
    public ResponseEntity<ApiResult> postConfigOlt(@RequestBody CapNhatOLTDTO input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(giaoTiepThietBiService.postConfigOLT(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật danh sách lệnh nghiệp vụ")
    @PostMapping("capnhat-danhsach-lenh")
    public ResponseEntity<ApiResult> postCommandSerivice(@RequestBody DanhSachServiceDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(giaoTiepThietBiService.postCommandSerivice(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật Hủy yêu cầu")
    @PostMapping("huy-yeucau/{pId}")
     public ResponseEntity<ApiResult> postCancelSerivice(@PathVariable Long pId ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(giaoTiepThietBiService.postCancelSerivice(pId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật Hủy yêu cầu")
    @PostMapping("reset-yeucau/{pId}")
    public ResponseEntity<ApiResult> postResetSerivice(@PathVariable Long pId ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(giaoTiepThietBiService.postResetSerivice(pId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lay thong tin da thuc hien cho 1 ID")
    @PostMapping("get-Log/{pId}")
    public ResponseEntity<ApiResult> getLogService(@PathVariable Long pId ) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(giaoTiepThietBiService.getLogService(pId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
        @Operation(summary = "Cập nhật kết quả thực hiện")
        @PostMapping("capnhat-ketqua-thuchien")
        public ResponseEntity<ApiResult> postResultProcess(@RequestBody CapNhatKetQuaDTO input) {
            ApiResult rs = new ApiResult();
            try {
                rs.setData(new ResultOutput(giaoTiepThietBiService.postResultProcess(input)));
            } catch (AppSqlException e) {
                rs.setException(e);
            }
            return rs.getResponseEntity();
        }

    @Operation(summary = "Cập nhật thêm sửa 1 nghiệp vụ ")
    @PostMapping("capnhat-nghiepvu")
    public ResponseEntity<ApiResult> postSerivice(@RequestBody DanhSachServiceDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(giaoTiepThietBiService.postSerivice(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh mục tien ich")
    @GetMapping("lay-danhmuc-tienich")
    public ResponseEntity<ApiResult> getUtilitiest() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(giaoTiepThietBiService.getUltilitíe());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Gui yêu cầu nghiẹp vụ")
    @PostMapping("gui-yeucau")
    public ResponseEntity<ApiResult> postSendSerivice(@RequestBody YeuCauDTO input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(giaoTiepThietBiService.postSendSerivice(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    
    @Operation(summary = "Danh sach thuc hien")
    @PostMapping("lay-danhsach-ketqua-da-thuchien")
    public ResponseEntity<ApiResult> postListSeriviceReturn(@RequestBody ThongTinTraCuu input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(giaoTiepThietBiService.postListSeriviceReturn(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
