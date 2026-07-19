package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.LoaiTuDiaIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThongTinModule;
import vn.vnpt.vnptit.ecms.service.danhmuc.LoaiTuDiaService;


@RestController
@RequestMapping("danhmuc/loai-tu-dia")
public class LoaiTuDiaController {
    private final LoaiTuDiaService loaiTuDiaService;
    public LoaiTuDiaController(LoaiTuDiaService service) {
        this.loaiTuDiaService = service;
    }


    @Operation(summary = "Liệt kê danh sách hãng sản xuất")
    @RequestMapping(value = "/danhsach", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> danhSachModule() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiTuDiaService.getDSLoaiTuDia());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Thêm mới sửa hãng sản xuất")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatModule(@RequestBody LoaiTuDiaIn input) {
        ApiResult rs = new ApiResult();
        try {
            loaiTuDiaService.thayDoiThongTinLoaiTuDia(input);
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
