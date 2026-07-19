package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CardSwitchDtoInp;
import vn.vnpt.vnptit.ecms.dto.TaoPortLGCardSwitch;
import vn.vnpt.vnptit.ecms.service.CardSwitchService;

@RestController
@RequestMapping("/card-switch")
public class CardSwitchController {
    @Autowired
    public CardSwitchService cardSwitchService;

    @Operation(summary = "Lấy thông tin thiết bị theo switch card")
    @GetMapping(value = "ds-thietbi-theo-switch-card/{id}")
    public ResponseEntity<ApiResult> layPortTheoSwitchCard(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardSwitchService.layPortTheoSwitchCard(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy port switch card sử dụng")
    @GetMapping(value = "port-switch-card-sd/{id}")
    public ResponseEntity<ApiResult> layPortSwitchCardSD(@PathVariable int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardSwitchService.layPortSwitchCardSD(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm switch card")
    @PostMapping(value = "them")
    public ResponseEntity<ApiResult> themCardSwitch(@RequestBody CardSwitchDtoInp input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardSwitchService.themCardSwitch(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật switch card")
    @PostMapping(value = "capnhat")
    public ResponseEntity<ApiResult> themCardSwitch(@RequestBody CardSwitchDtoInp.CardSwitchDtoInpUpd input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(cardSwitchService.capNhatCardSwitch(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa switch card")
    @PostMapping(value = "xoa/{id}")
    public ResponseEntity<ApiResult> themCardSwitch(@PathVariable("id") int id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(cardSwitchService.xoatCardSwitch(id)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo port card switch")
    @PostMapping(value = "tao-port-card-switch")
    public ResponseEntity<ApiResult> taoPortCardSwitch(@RequestBody TaoPortLGCardSwitch input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(cardSwitchService.taoPortCardSwitch(input.getCardsw_id(), input.getIstaoportlg() ? 1 : 0));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
