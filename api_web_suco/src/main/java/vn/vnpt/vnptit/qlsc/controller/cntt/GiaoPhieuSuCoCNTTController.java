package vn.vnpt.vnptit.qlsc.controller.cntt;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.qlsc.dto.CapNhatPhieuXlSuCoDto;
import vn.vnpt.vnptit.qlsc.dto.LayDsPhieuXuLyDto;
import vn.vnpt.vnptit.qlsc.dto.cntt.GiaoPhieuXlSuCoCnttDto;
import vn.vnpt.vnptit.qlsc.dto.cntt.KhoaPhieuCnttDto;
import vn.vnpt.vnptit.qlsc.service.cntt.GiaoPhieuSuCoCNTTService;

import javax.validation.Valid;

@RestController
@RequestMapping("/giao-phieu-cntt")
public class GiaoPhieuSuCoCNTTController {
    private final GiaoPhieuSuCoCNTTService service;

    public GiaoPhieuSuCoCNTTController(GiaoPhieuSuCoCNTTService service) {
        this.service = service;
    }

    @Operation(summary = "Danh sách phiếu xử lý")
    @RequestMapping(value = "/ds-phieu-xuly", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsPhieuXuLy(@RequestBody LayDsPhieuXuLyDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.layDsPhieuXuLy(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Giao phiếu xử lý sự cố")
    @RequestMapping(value = "/giaophieu-xuly", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> giaoPhieuXlSuCo(@RequestBody GiaoPhieuXlSuCoCnttDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.giaoPhieuXlSuCo(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật phiếu xử lý sự cố")
    @RequestMapping(value = "/capnhat-phieu-xuly", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatPhieuXlSuCo(@RequestBody CapNhatPhieuXlSuCoDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.capNhatPhieuXlSuCo(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Hủy phiếu xử lý sự cố")
    @RequestMapping(value = "/huy-phieu-xuly", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> huyPhieuXlSuCo(@RequestBody GiaoPhieuXlSuCoCnttDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.huyPhieuXlSuCo(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Danh sách giao phiếu xử lý sự cố")
    @RequestMapping(value = "/ds-giaophieu", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> dsGiaoPhieu(@RequestBody GiaoPhieuXlSuCoCnttDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(service.dsGiaoPhieuXl(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Khóa phiếu")
    @RequestMapping(value = "/khoa-phieu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> khoaPhieu(@Valid @RequestBody KhoaPhieuCnttDto input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(service.khoaPhieu(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
