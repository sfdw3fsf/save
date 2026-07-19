package vn.vnpt.vnptit.ecms.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.message.ResultOutput;
import vn.vnpt.vnptit.ecms.dto.HknSwitchDeleteDtoInput;
import vn.vnpt.vnptit.ecms.dto.HknSwitchUpdateDtoInput;
import vn.vnpt.vnptit.ecms.dto.ThongTinKetNoiIn;
import vn.vnpt.vnptit.ecms.service.HknSwitchService;

import javax.validation.Valid;

@RestController
@RequestMapping("ketnoiswitch")
public class HknSwitchController {
    private final HknSwitchService hknSwitchService;

    public HknSwitchController(HknSwitchService hknSwitchService) {
        this.hknSwitchService = hknSwitchService;
    }


    @Operation(summary = "Kết nối hướng kết nối phân loại Switch", description = "Kết nối hướng kết nối phân loại Switch")
    @RequestMapping(value = "/ketnoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> connectSwitch(@Valid @RequestBody HknSwitchUpdateDtoInput input) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hknSwitchService.ketNoiSwitch(input));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Gỡ kết nối hướng kết nối phân loại Switch", description = "Gỡ kết nối hướng kết nối phân loại Switch")
    @RequestMapping(value = "/goketnoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> xoaDslShelf(@Valid @RequestBody HknSwitchDeleteDtoInput input) {

        ApiResult rs = new ApiResult();
        try {
            rs.setData(new ResultOutput(hknSwitchService.goKetNoiSwitch(input)));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy thiết bị theo trạm", description = "Lấy thiết bị theo trạm")
    @RequestMapping(value = "/laytbitheotram/{loaitbi_id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> lietKeDanhSachDslam(@PathVariable("loaitbi_id") int loaiTbiId) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hknSwitchService.layTbiTheoTram(loaiTbiId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }


    @Operation(summary = "Lấy card mane theo dslamId", description = "Lấy card mane theo dslamId")
    @RequestMapping(value = "/layporttheodslam/{dslam_id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layCardManeTheoDsLam(@PathVariable("dslam_id") int dslamIdd) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(hknSwitchService.layCardManeTheoDsLam(dslamIdd));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Load thông tin kết nối switch")
    @RequestMapping(value = "/loadttketnoi/{madslam_port}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> loadthongtinketnoi(@Valid @PathVariable Integer madslam_port) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(hknSwitchService.loadthongtinketnoi(phanvung_id,madslam_port));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy mã kết nối tới Dslam")
    @RequestMapping(value = "/loadttketnoi/laymakn", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layMaKetNoi(@Valid @RequestBody ThongTinKetNoiIn.MaKetNoi item) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id= AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(hknSwitchService.layMaKetNoi(phanvung_id,item));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
