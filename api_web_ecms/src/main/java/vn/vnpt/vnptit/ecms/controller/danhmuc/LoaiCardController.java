package vn.vnpt.vnptit.ecms.controller.danhmuc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.PaginationInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CapNhatLoaiCardDtnIn;
import vn.vnpt.vnptit.ecms.dto.ThemLoaiCardDtnIn;
import vn.vnpt.vnptit.ecms.dto.ThemNhomCardTbiDtnIn;
import vn.vnpt.vnptit.ecms.service.danhmuc.LoaiCardService;

import javax.validation.Valid;

@RestController
@RequestMapping("/danhmuc/loaicard/")
public class LoaiCardController {
    @Autowired
    LoaiCardService loaiCardService;
    @Operation(summary = "LAY_DS_LOAICARD")
    @RequestMapping(value = "/lay-ds-loaicard", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_ds_loaicard(){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiCardService.lay_ds_loaicard());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "LAY_DS_LOAICARD_PAGING")
    @RequestMapping(value = "/lay-ds-loaicard-paging", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> lay_ds_loaicard_paging(@Valid @RequestBody PaginationInput item){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiCardService.lay_ds_loaicard_paging(item.getPageIndex(), item.getPageSize()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "LAY_LOAICARD_THEO_LOAICARD_ID")
    @RequestMapping(value = "/lay-loaicard-theo-loaicard-id", produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<ApiResult> lay_loaicard_theo_loaicard_id(Long loaicardId){
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiCardService.lay_loaicard_theo_loaicard_id(loaicardId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "TAO_LOAICARD")
    @RequestMapping(value = "/tao-loaicard", produces = { "application/json" }, method = RequestMethod.POST)
    ResponseEntity<ApiResult> tao_loaicard(@Valid @RequestBody ThemLoaiCardDtnIn loaiCard) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiCardService.tao_loaicard(loaiCard));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "TAO_NHOM_CARD_TBI")
    @RequestMapping(value = "/tao-nhomcardtbi", produces = { "application/json" }, method = RequestMethod.POST)
    ResponseEntity<ApiResult> tao_nhom_card_tbi(@Valid @RequestBody ThemNhomCardTbiDtnIn nhomCardTbiDtnIn) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiCardService.tao_nhom_card_tbi(nhomCardTbiDtnIn));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "CAPNHAT_LOAICARD")
    @RequestMapping(value = "/capnhat-loaicard", produces = { "application/json" }, method = RequestMethod.POST)
    ResponseEntity<ApiResult> capnhat_loaicard(@Valid @RequestBody CapNhatLoaiCardDtnIn loaiCard) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiCardService.capnhat_loaicard(loaiCard));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "XOA_LOAICARD")
    @RequestMapping(value = "/xoa-loaicard", produces = { "application/json" }, method = RequestMethod.POST)
    ResponseEntity<ApiResult> xoa_loaicard(Long loaicardId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData( new ResultOutput(loaiCardService.xoa_loaicard(loaicardId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Liệt kê danh sách nhóm card")
    @RequestMapping(value = "/lay-ds-nhomcard", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lay_danhsach_nhomcard() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(loaiCardService.lay_danhsach_nhomcard());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
