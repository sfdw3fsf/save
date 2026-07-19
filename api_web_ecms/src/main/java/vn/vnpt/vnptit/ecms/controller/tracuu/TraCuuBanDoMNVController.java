package vn.vnpt.vnptit.ecms.controller.tracuu;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.LayDungLuongRoiDtoIn;
import vn.vnpt.vnptit.ecms.dto.TraCuuBanDoMNVDtoIn;
import vn.vnpt.vnptit.ecms.dto.TracuuTBTheoFileIn;
import vn.vnpt.vnptit.ecms.service.tracuu.TraCuuBanDoMNVService;

import javax.validation.Valid;

@RestController
@RequestMapping("tracuubando")
public class TraCuuBanDoMNVController {
    private TraCuuBanDoMNVService traCuuBanDoMNVService;
    public  TraCuuBanDoMNVController(TraCuuBanDoMNVService traCuuBanDoMNVService){
        this.traCuuBanDoMNVService = traCuuBanDoMNVService;
    }

    @Operation(summary = "Tra cứu bản đồ mạng ngoại vi")
    @RequestMapping(value = "/timketcuoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> timketcuoi(@RequestBody TraCuuBanDoMNVDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(traCuuBanDoMNVService.LAY_DS_KETCUOI_TRONG_BANKINH_V3(obj));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Danh sách thuộc tính")
    @RequestMapping(value = "/lay-ds-thuoctinh", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> timketcuoi() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(traCuuBanDoMNVService.lay_ds_thuoctinh());
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy dung lượng rỗi theo kết cuối")
    @RequestMapping(value = "/lay-dungluong-theo-ketcuoi", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> get_dungluong_roi_theo_kc(@RequestBody LayDungLuongRoiDtoIn obj) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(traCuuBanDoMNVService.get_dungluong_roi_theo_kc(obj));
        }catch(AppSqlException e){
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
