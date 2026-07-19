package vn.vnpt.vnptit.ecms.controller.tracuu;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.CardTDDtoInput;
import vn.vnpt.vnptit.ecms.service.CardTDService;

import javax.validation.Valid;

@RestController
@RequestMapping("carttd")
public class CardTDController {
    @Autowired
    private CardTDService cardTDService;

    @Operation(summary = "Lấy thông tin frame_card", description = "Thông tin frame_card")
    @RequestMapping(value = "/thongtin_framecard", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layThongTinFrameCard(@RequestParam("cardtd_id") long cardtd_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardTDService.layThongTinFrameCard(cardtd_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin port sử dụng", description = "Thông tin port sử dụng")
    @RequestMapping(value = "/port_sudung", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layThongTinPortSuDung(@RequestParam("cardtd_id") long cardtd_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardTDService.layThongTinPortSuDung(cardtd_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin port theo cardtd", description = "Thông tin port theo cardtd")
    @RequestMapping(value = "/ds_port_theo_cardtd", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsPortTheoCardTD(@RequestParam("cardtd_id") long cardtd_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardTDService.layDsPortTheoCardTD(cardtd_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thông tin loại card", description = "Lấy thông tin loại card")
    @RequestMapping(value = "/thongtin_loaicard", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layThongTinLoaiCard(@RequestParam("loaicard_id") long loaicard_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardTDService.layThongTinLoaiCard(loaicard_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm card truyền dẫn", description = "Thêm card truyền dẫn")
    @RequestMapping(value = "/them", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themCardTD(@RequestBody @Valid CardTDDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardTDService.themCardTD(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo port card truyền dẫn", description = "Tạo port card truyền dẫn")
    @RequestMapping(value = "/taoport_cardtd", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> taoPortCardTD(@RequestParam("cardtd_id") long cardtd_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardTDService.taoPortCardTD(cardtd_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật card truyền dẫn", description = "Cập nhật card truyền dẫn")
    @RequestMapping(value = "/capnhat", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatCardTD(@RequestBody @Valid CardTDDtoInput input, @RequestParam("cardtd_id") long cardtd_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardTDService.capNhatCardTD(input, cardtd_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa card truyền dẫn", description = "Xóa card truyền dẫn")
    @RequestMapping(value = "/xoa", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaCardTD(@RequestParam("cardtd_id") long cardtd_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardTDService.xoaCardTD(cardtd_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
