package vn.vnpt.vnptit.ecms.controller.hatang;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ChungLoaiThietBiDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.LoaiCongInterfaceDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.LoaiCongInterfaceFilterDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.VlanDtoIn;
import vn.vnpt.vnptit.ecms.dto.hatang.DMLienQuanVlanDtoIn;
import vn.vnpt.vnptit.ecms.service.hatang.ChungLoaiThietBiService;

import javax.validation.Valid;

@RestController
@RequestMapping("/chungloai-thietbi")
public class ChungLoaiThietBiController {
    @Autowired
    ChungLoaiThietBiService chungLoaiThietBiService;

    @Operation(summary = "Lấy danh sách Chủng loại thiết bị")
    @RequestMapping(value = "/lay-ds-chungloai-thietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsChungLoaiThietBi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chungLoaiThietBiService.layDsChungLoaiThietBi());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy Chủng loại thiết bị theo ID")
    @RequestMapping(value = "/lay-chungloai-thietbi-theo-id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsChungLoaiThietBiId(@PathVariable Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chungLoaiThietBiService.layDsChungLoaiThietBiId(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy Lịch sử thay đổi Chủng loại thiết bị")
    @RequestMapping(value = "/lay-lichsu-thaydoi-chungloai-thietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layLichSuThayDoiChungLoaiThietBi(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chungLoaiThietBiService.layLichSuThayDoiChungLoaiThietBi(item.getId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Danh sách Port theo LineCard của thiết bị")
    @GetMapping(value = "/lay-ds-port-theo-linecard")
    public ResponseEntity<ApiResult> layDsPortTheoLineCardThietBi(
            @RequestParam(name = "chungLoaiThietBiId") Long chungLoaiThietBiId,
                @RequestParam(name = "phanLoai") Long phanLoai) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chungLoaiThietBiService.layDsPortTheoLineCardThietBi(chungLoaiThietBiId, phanLoai));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "insert and update chủng loại thiết bị")
    @RequestMapping(value = "/capnhat-chungloai-thietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> tao(@Valid @RequestBody ChungLoaiThietBiDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chungLoaiThietBiService.updateChungLoaiThietBi(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "xóa chủng loại thiết bị")
    @RequestMapping(value = "/xoa-chungloai-thietbi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoa(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(chungLoaiThietBiService.xoaChungLoaiThietBi(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "khai báo cổng")
    @RequestMapping(value = "/khaibao-loaicong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> khaibaoLoaiCong(@Valid @RequestBody LoaiCongInterfaceDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(chungLoaiThietBiService.khaibaoLoaiCong(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "danh sách cổng")
    @RequestMapping(value = "/danhsach-loaicong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> danhsachLoaiCong(@Valid @RequestBody LoaiCongInterfaceFilterDtoIn input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(chungLoaiThietBiService.danhsachLoaiCong(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "xóa cổng")
    @RequestMapping(value = "/xoa-cong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaCong(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(chungLoaiThietBiService.xoaCong(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Card")
    @GetMapping(value = "/ds-card")
    public ResponseEntity<ApiResult> getListOfCards(@RequestParam(name = "chungLoaiThietBiId") Long chungLoaiThietBiId,
                                                    @RequestParam(name = "phanLoai") Long phanLoai) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chungLoaiThietBiService.getListOfCards(chungLoaiThietBiId, phanLoai));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy tổng số port của thiết bị")
    @GetMapping(value = "/sum-ports")
    public ResponseEntity<ApiResult> getSumOfPorts(@RequestParam(name = "chungLoaiThietBiId") Long chungLoaiThietBiId,
                                                   @RequestParam(name = "phanLoai") Long phanLoai) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chungLoaiThietBiService.getSumOfPorts(chungLoaiThietBiId, phanLoai));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy lịch sử thay đổi của Chủng loại thiết bị")
    @GetMapping(value = "/history-changes")
    public ResponseEntity<ApiResult> getHistoryChanges(@RequestParam(name = "chungLoaiThietBiId") Long chungLoaiThietBiId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chungLoaiThietBiService.getHistoryChanges(chungLoaiThietBiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
