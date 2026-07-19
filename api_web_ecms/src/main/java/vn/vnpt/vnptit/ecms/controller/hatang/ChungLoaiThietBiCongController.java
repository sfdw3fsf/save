package vn.vnpt.vnptit.ecms.controller.hatang;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.ChungLoaiThietBiCongService;
import vn.vnpt.vnptit.ecms.service.hatang.ChungLoaiThietBiService;

import java.util.Map;

@RestController
@RequestMapping("/cong-chungloai-thietbi")
public class ChungLoaiThietBiCongController {
    @Autowired
    ChungLoaiThietBiCongService chungLoaiThietBiCongService;

    @Operation(summary = "Lấy danh sách thiết bị đấu nối Cổng Chủng loại thiết bị")
    @RequestMapping(value = "/lay-ds-thietbi-daunoi-chungloai-thietbi-cong/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDSThietBiDauNoiCong(@PathVariable (value="id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chungLoaiThietBiCongService.layDSThietBiDauNoiCong(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách thiết bị đấu nối Cổng Chủng loại thiết bị")
    @RequestMapping(value = "/lay-ds-chungloai-thietbi-cong/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layDsChungLoaiThietBiCong(@PathVariable (value="id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chungLoaiThietBiCongService.layDSChungLoaiThietBiCong(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Lấy danh sách lich su thay dổi Cổng Chủng loại thiết bị")
    @RequestMapping(value = "/lay-ds-lichsu-thaydoi-chungloai-thietbi-cong/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> layLichSuDsChungLoaiThietBiCong(@PathVariable (value="id") Long id) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(chungLoaiThietBiCongService.layDSLichSuThayDoiChungLoaiThietBiCong(id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
    @Operation(summary = "Cập nhật thông tin Cổng Chủng loại thiết bị")
    @RequestMapping(value = "/capnhat-chungloai-thietbi-cong", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> capNhatChungLoaiThietBiCong(@RequestBody Map<String, Object> input) {
        ApiResult rs = new ApiResult();
        String pDs = (String) input.get("pDs");
        Long pId = Long.valueOf(input.get("pId").toString());
        JsonObject jsonObject = JsonParser.parseString(pDs).getAsJsonObject();
        try {
            rs.setData(chungLoaiThietBiCongService.capNhatChungLoaiThietBiCong(jsonObject.toString(),pId));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }
}
