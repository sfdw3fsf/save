package vn.vnpt.vnptit.ecms.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.CardManeDto;
import vn.vnpt.vnptit.ecms.dto.CardSwitchDto;
import vn.vnpt.vnptit.ecms.dto.EwsdCardDto;
import vn.vnpt.vnptit.ecms.dto.EwsdInsDto;
import vn.vnpt.vnptit.ecms.dto.EwsdShelfIns;
import vn.vnpt.vnptit.ecms.dto.ModuleManeDto;
import vn.vnpt.vnptit.ecms.dto.TuLamDto;
import vn.vnpt.vnptit.ecms.service.ThietBiChuyenMachService;

@RestController
@RequestMapping("/thietbichuyenmach")
public class ThietBiChuyenMachController {

    private final ThietBiChuyenMachService thietBiChuyenMachService;

    public ThietBiChuyenMachController(ThietBiChuyenMachService thietBiChuyenMachService) {
        this.thietBiChuyenMachService = thietBiChuyenMachService;
    }

    @Operation(summary = "Lấy danh sách trung tâm viễn thông", description = "Lấy danh sách trung tâm viễn thông")
    @RequestMapping(value = "ds-tt-vienthong", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsTrungTamVienThong(@NotNull @Valid @RequestParam("donviDlId") Long donviDlId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsTrungTamVienThong(donviDlId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách tổ viễn thông", description = "Lấy danh sách tổ viễn thông")
    @RequestMapping(value = "ds-to-vienthong", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsToVienThong(@NotNull @Valid @RequestParam("donviDlId") Long donviDlId, @NotNull @Valid @RequestParam("loaiDvId") Long loaiDvId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsToVienThong(donviDlId,loaiDvId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách trạm viễn thông", description = "Lấy danh sách trạm viễn thông")
    @RequestMapping(value = "ds-tram-vienthong", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsTramVienThong(@NotNull @Valid @RequestParam("loaiDvId") Long loaiDvId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsTramVienThong(loaiDvId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách EWSD", description = "Lấy danh sách EWSD")
    @RequestMapping(value = "dsEWSD", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsEWSD() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsEWSD());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Ewsd Shelf", description = "Lấy danh sách Ewsd Shelf")
    @RequestMapping(value = "dsEwsdShelf", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsEwsdShelf() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsEwsdShelf());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Card Ewsd", description = "Lấy danh sách Card Ewsd")
    @RequestMapping(value = "dsCardEwsd", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsCardEwsd() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsCardEwsd());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách BrasCard", description = "Lấy danh sách BrasCard")
    @RequestMapping(value = "dsBrasCard", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsBrasCard() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsBrasCard());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách BrasPort", description = "Lấy danh sách BrasPort")
    @RequestMapping(value = "dsBrasPort", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsBrasPort() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsBrasPort());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách DslamGponSwitch", description = "Lấy danh sách DslamGponSwitch")
    @RequestMapping(value = "dsDslamGponSwitch", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsDslamGponSwitch(@NotNull @Valid @RequestParam("loaiTBiId") Long loaiTBiId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsDslamGponSwitch(loaiTBiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Lấy danh sách GponCard", description = "Lấy danh sách GponCard")
    @RequestMapping(value = "dsGponCard", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsGponCard() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsGponCard());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách GponPort", description = "Lấy danh sách GponPort")
    @RequestMapping(value = "dsGponPort", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsGponPort() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsGponPort());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dCardMane", description = "Lấy danh sách CardMane")
    @RequestMapping(value = "dsCardMane", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsCardMane() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsCardMane());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách PortMane", description = "Lấy danh sách PortMane")
    @RequestMapping(value = "dsPortMane", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsPortMane() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsPortMane());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách DSlamMane", description = "Lấy danh sách DSlamMane")
    @RequestMapping(value = "DslamMane", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsDSlamMane() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsDSlamMane());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Port Module Mane", description = "Lấy danh sách Port Module Mane")
    @RequestMapping(value = "PortModuleMane", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsPortModuleMane() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsPortModuleMane());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Card Switch", description = "Lấy danh sách Card Switch")
    @RequestMapping(value = "dsCardSwitch", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsCardSwitch() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsCardSwitch());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Switch port", description = "Lấy danh sách Switch port")
    @RequestMapping(value = "dsSwitchPort", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsSwitchPort() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsSwitchPort());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Switch card port", description = "Lấy danh sách Switch card port")
    @RequestMapping(value = "dsSwitchCardPort", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsSwitchCardPort() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsSwitchCardPort());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách port dslam", description = "Lấy danh sách port dslam")
    @RequestMapping(value = "dsPortDslam", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsPortDslam() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsPortDslam());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách dslam module", description = "Lấy danh sách dslam module")
    @RequestMapping(value = "dsDslamModule", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsDslamModule() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsDslamModule());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách port module", description = "Lấy danh sách port module")
    @RequestMapping(value = "dsPortModule", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsPortModule() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsPortModule());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách frame card", description = "Lấy danh sách frame card")
    @RequestMapping(value = "dsFrameCard", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsFrameCard() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsFrameCard());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới ewsd", description = "Thêm mới ewsd")
    @RequestMapping(value = "/themewsd", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themEWSD(@Valid @RequestBody EwsdInsDto ewsdInsDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.themEWSD(ewsdInsDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật ewsd", description = "Cập nhật ewsd")
    @RequestMapping(value = "/capnhatewsd", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatEWSD(@Valid @RequestBody EwsdInsDto.EwsdUpdateDto ewsdInsDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.capNhatEWSD(ewsdInsDto)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Xóa ewsd", description = "Xóa ewsd")
    @RequestMapping(value = "/xoaewsd", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaEWSD(@Valid  @RequestParam("ewsd_id") Long ewsdId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.xoaEWSD(ewsdId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Check ewsd da lap Shelf", description = "Check ewsd da lap Shelf")
    @RequestMapping(value = "/checkewsdhasshelf", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> checkEwsdShelf(@Valid  @RequestParam("ewsd_id") Long ewsdId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.checkEwsdShelf(ewsdId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới ewsd SHELF", description = "Thêm mới ewsd SHELF")
    @RequestMapping(value = "/themEWSDSHELF", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themEWSDSHELF(@Valid @RequestBody EwsdShelfIns ewsdShelfIns) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.themEWSDSHELF(ewsdShelfIns));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật shelf ewsd", description = "Cập nhật shelf ewsd")
    @RequestMapping(value = "/capNhatEWSDSHELF", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatEWSDSHELF(@Valid @RequestBody EwsdShelfIns.EwsdShelfUpd ewsdInsDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.capNhatEWSDSHELF(ewsdInsDto)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Xóa shelf ewsd", description = "Xóa shelf ewsd")
    @RequestMapping(value = "/XoaEwsdShelf", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaEwsdShelf(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.xoaEWSDSHELF(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa all shelf ewsd", description = "Xóa all shelf ewsd")
    @RequestMapping(value = "/XoaAllEwsdShelf", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaAllEwsdShelf(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.xoaAllCardSHELF(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa shelf ewsd", description = "Xóa shelf ewsd")
    @RequestMapping(value = "/XoaShelfEwsd", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> XoaShelfEwsd(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.XoaShelfEwsd(item.getId())));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Thêm mới ewsd card", description = "Thêm mới ewsd card")
    @RequestMapping(value = "/themewsdcard", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themEwsdCard(@Valid @RequestBody EwsdCardDto ewsdCardDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.themEwsdCard(ewsdCardDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
 
    @Operation(summary = "Cập nhật ewsd card", description = "Cập nhật ewsd card")
    @RequestMapping(value = "/capnhatewsdcard", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatEwsdCard(@Valid @RequestBody EwsdCardDto.EwsdCardUpd ewsdInsDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.capNhatEwsdCard(ewsdInsDto)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Xóa ewsd card", description = "Xóa ewsd")
    @RequestMapping(value = "/xoaewsdcard", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaEwsdCard(@Valid @RequestParam("cardEwsdId") Long cardEwsdId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.xoaEwsdCard(cardEwsdId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Thêm tủ dslam", description = "Thêm tủ dslam")
    @RequestMapping(value = "/themtudslam", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themTuDSLam(@Valid @RequestBody TuLamDto tuLamDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.themTuDSLam(tuLamDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo Port SW", description = "Tạo Port SW")
    @RequestMapping(value = "/taoportsw", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> taoPortSW(@Valid  @RequestParam("switchId") Long switchId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.taoPortSW(switchId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Thêm mới module mane", description = "Thêm mới module mane")
    @RequestMapping(value = "/themmodulemane", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themModuleMane(@Valid @RequestBody ModuleManeDto moduleManeDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.themModuleMane(moduleManeDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật module mane", description = "Cập nhật module mane")
    @RequestMapping(value = "/capnhatmodulemane", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatModuleMane(@Valid @RequestBody ModuleManeDto.ModuleManeUpdDto moduleManeDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.capNhatModuleMane(moduleManeDto)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Xóa module mane", description = "Xóa module mane")
    @RequestMapping(value = "/xoamodulemane", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaModuleMane(@Valid  @RequestParam("moduleDslId") Long moduleDslId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.xoaModuleMane(moduleDslId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo Port Dslam Module", description = "Tạo Port Dslam Module")
    @RequestMapping(value = "/taoportdslammodule", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> taoPortDslamModule(@Valid @RequestParam("moduleManeId") Long moduleManeId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.taoPortDslamModule(moduleManeId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Thêm mới card switch", description = "Thêm mới card switch")
    @RequestMapping(value = "/themcardswitch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themCardSwitch(@Valid @RequestBody CardSwitchDto cardSwitchDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.themCardSwitch(cardSwitchDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật card switch", description = "Cập nhật card switch")
    @RequestMapping(value = "/capnhatCardSwitch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatCardSwitch(@Valid @RequestBody CardSwitchDto.CardSwitchUpDto cardSwitchDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.capNhatCardSwitch(cardSwitchDto)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa card switch", description = "Xóa card switch")
    @RequestMapping(value = "/xoaCardSwitch", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaCardSwitch(@Valid  @RequestParam("cardswId") Long cardswId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.xoaCardSwitch(cardswId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Thêm mới card mane", description = "Thêm mới card mane")
    @RequestMapping(value = "/themCardMane", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> themCardMane(@Valid @RequestBody CardManeDto cardManeDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.themCardMane(cardManeDto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật card mane", description = "Cập nhật card mane")
    @RequestMapping(value = "/capnhatCardMane", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatCardSwitch(@Valid @RequestBody CardManeDto.CardManeUpdDto cardManeDto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.capNhatCardMane(cardManeDto)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa card mane", description = "Xóa card mane")
    @RequestMapping(value = "/xoaCardMane", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaCardMane(@Valid  @RequestParam("cardManeId") Long cardManeId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.xoaCardMane(cardManeId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tạo Port Dslam Mane", description = "Tạo Port Dslam Mane")
    @RequestMapping(value = "/taoportdslamane", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> taoPortDslamMane(@Valid @RequestParam("cardManeId") Long cardManeId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(thietBiChuyenMachService.taoPortDslamMane(cardManeId)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Card theo shelf", description = "Lấy danh sách Card theo shelf")
    @RequestMapping(value = "layCardEwsdTheoShelf", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layCardEwsdTheoShelf(@Valid @RequestParam("shelfEwsdId") Long shelfEwsdId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.layCardEwsdTheoShelf(shelfEwsdId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách port theo Card", description = "Lấy danh sách port theo Card")
    @RequestMapping(value = "layPortTheoCard", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layPortTheoCard(@Valid @RequestParam("cardEwsdId") Long cardEwsdId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.layPortTheoCard(cardEwsdId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách trạng thai port", description = "Lấy danh sách trạng thai port")
    @RequestMapping(value = "lay-ds-trangthai-port", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layTrangThai() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.layTrangThai());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách loai card", description = "Lấy danh sách loai card")
    @RequestMapping(value = "lay-ds-loai-card", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layLoaiCard(@Valid @RequestParam("nhomcard_id") Integer nhomcard_id, @RequestParam("loaitbi_id") Integer loaitbi_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.layLoaiCard(nhomcard_id,loaitbi_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách nhóm port", description = "Lấy danh sách nhóm port")
        @RequestMapping(value = "lay-ds-nhom-card", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layNhomCard(@Valid @RequestParam("loaitbi_id") Integer loaitbi_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.layNhomCard(loaitbi_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    /**
	 * @author LOCNV
	 * @date 25/08/2021
	 */
    @Operation(summary = "Lấy danh sách shelf ewsd full")
    @RequestMapping(value = "/layDsShelfEwsdFull", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDsShelfEwsdFull() {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(thietBiChuyenMachService.layDsShelfEwsdFull());
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy thông tin shelf ewsd")
    @RequestMapping(value = "/layShelfEwsd", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layShelfEwsd(@Valid @RequestBody DataKeyInput item) {
        ApiResult rs = new ApiResult();
        try {
			rs.setData(thietBiChuyenMachService.layShelfEwsd(item.getId()));
		} catch (AppSqlException e) {
			rs.setException(e);
		}
        return rs.getResponseEntity();
    }

    /**
	 * END LOCNV
	 */

    /**
     * @author CUONGLC
     * @date 26/08/2021
     */
    @Operation(summary = "Lấy EWSD theo id")
    @RequestMapping(value = "/layEwsdTheoId", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layEwsdTheoId(@Valid @RequestParam("ewsdId") Integer ewsdId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.layEwsdTheoID(ewsdId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách tủ rack theo nhà trạm")
    @RequestMapping(value = "/lay-ds-rack-theo-tram", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDSTuRackeTheoNhaTram(@Valid @RequestParam("donvi_id") Integer donvi_id, @RequestParam("kieu") Integer kieu) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.layDSTuRackeTheoNhaTram(donvi_id, kieu));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách loai ewsd")
    @RequestMapping(value = "/lay-ds-loai-ewsd", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDSLoaiEWSD() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.layDSLoaiEWSD());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách host")
    @RequestMapping(value = "/lay-ds-host", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDSLoaiEWSD(@Valid @RequestParam("loaidv_id") String loaidv_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.layDSHost(loaidv_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Card Ewsd theo Shelf ID", description = "Lấy danh sách Card Ewsd theo Shelf ID")
    @RequestMapping(value = "ds-card-theo-shelf-id", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsCardTheoShelfEwsdId(@Valid @RequestParam("shelf_id") Integer shelf_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsCardTheoShelfEwsdId(shelf_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy số lượng port theo Id card", description = "Lấy số lượng port theo Id card")
    @RequestMapping(value = "lay-soluong-port-card-id", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layPortInCard(@Valid @RequestParam("card_id") Integer card_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.layPortInCard(card_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy số lượng port theo Id card", description = "Lấy số lượng port theo Id card")
    @RequestMapping(value = "check-port-logic-delete-card", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> checkDeleteCard(@Valid @RequestParam("card_ewsd_id") Integer card_ewsd_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.checkDeleteCard(card_ewsd_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Cập nhật trangk thái port", description = "Cập nhật trangk thái port")
    @RequestMapping(value = "capnhat-trangthai-port", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> updateTTPort(@Valid @RequestParam("nevl_id") Integer nevl_id, @RequestParam("tt_port_id") Integer tt_port_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.updateTTPort(nevl_id, tt_port_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    /**
     * END CUONGLC
     */

     /** HUU MINH ADD
      **/
     @Operation(summary = "Lấy danh sách Bras", description = "Lấy danh sách Bras")
     @RequestMapping(value = "dsBras", method = RequestMethod.GET)
     public ResponseEntity<ApiResult> dsBras() {
         ApiResult rs = new ApiResult();
         try {
             rs.setData(thietBiChuyenMachService.dsBras());
         } catch (AppSqlException e) {
             rs.setException(e);
         }
         return rs.getResponseEntity();
     }

    @Operation(summary = "Lấy danh sách DslShelf", description = "Lấy danh sách DslShelf")
    @RequestMapping(value = "dsDslShelf", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsDslShelf() {
        ApiResult rs = new ApiResult();
        try {

            rs.setData(thietBiChuyenMachService.dsDslShelf());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Odf", description = "Lấy danh sách Odf")
    @RequestMapping(value = "dsOdf", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> dsOdf() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.dsOdf());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "LayThongTin_Loai_Card", description = "LayThongTin_Loai_Card")
    @RequestMapping(value = "LayThongTin_Loai_Card", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> LayThongTin_Loai_Card(@Valid @RequestParam("loaicard_id") Integer loaicard_id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.LayThongTin_Loai_Card(loaicard_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    /**
     * END HUU MINH
     */

    /** HONG QUAN ADD
     **/
    @Operation(summary = "Tao port ewsd", description = "Tao port ewsd")
    @RequestMapping(value = "tao-port-ewsd", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> TaoPortEWSD(@Valid @RequestParam("card_ewsd_id") Integer card_ewsd_id, @Valid @RequestParam("so_port") Integer so_port) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(thietBiChuyenMachService.TaoPortEWSD(card_ewsd_id, so_port));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    /**
     * END HONG QUAN
     */
}
