package vn.vnpt.vnptit.ecms.controller.tracuu;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.SqlInjectionValidator;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.Test_ArrayContentInvalidInput;
import vn.vnpt.vnptit.ecms.dto.TracuuTBTheoFileIn;
import vn.vnpt.vnptit.ecms.service.tracuu.ThongTinTBTheoFileService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@RestController
@RequestMapping("tracuu")
public class ThongTinTBTheoFileController {
    private final ThongTinTBTheoFileService thongtintbtheofileService;

    public ThongTinTBTheoFileController(ThongTinTBTheoFileService thongtintbtheofileService) {
        this.thongtintbtheofileService = thongtintbtheofileService;
    }

    @Operation(summary = "Tra cứu thông tin theo file")
    @RequestMapping(value = "/thuebaotheofile", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> thuebaotheofile(@RequestBody TracuuTBTheoFileIn obj) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id = AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongtintbtheofileService.thuebao_theofile(phanvung_id, obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Tra cứu thông tin theo file")
    @RequestMapping(value = "/thuebaotheofile_v2", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> thuebaotheofile_v2(@RequestBody TracuuTBTheoFileIn obj) {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id = AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongtintbtheofileService.thuebao_theofile_v2(phanvung_id, obj));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy dữ liệu file mẫu")
    @RequestMapping(value = "/thuebaotheofile/filemau", method = RequestMethod.GET)
    public ResponseEntity<ApiResult> laydsfilemau() {
        ApiResult rs = new ApiResult();
        try {
            int phanvung_id = AppRequestContext.getCurrent().getPhanVungID();
            rs.setData(thongtintbtheofileService.laydsfilemau(phanvung_id));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Kiểm tra mảng nội dung hợp lệ")
    @RequestMapping(value = "/kiemTraNoiDung", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<ApiResult> kiemTraNoiDung(@Valid @RequestBody Test_ArrayContentInvalidInput input) {
        ApiResult rs = new ApiResult();
        try {
            SqlInjectionValidator validator = new SqlInjectionValidator();
            String[] cons = input.getContent();
            ArrayList<Map<String, Object>> arrayResult = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < cons.length; i++) {
                if (!(cons[i] == null || cons[i].length() == 0)) {
                    Map<String, Object> result = validator.checkInvalid(cons[i]);
                    if (result != null) {
                        String tuKhoa = "";
                        List<String> keys = (List<String>) result.get("key-words");
                        if (!(keys == null || keys.size() == 0))
                            tuKhoa += String.join(",", keys);
                        List<String> regs = (List<String>) result.get("reg-exs");
                        if (!(regs == null || regs.size() == 0)) {
                            if (tuKhoa.equals(""))
                                tuKhoa += ",";
                            tuKhoa += String.join(",", regs);
                        }
                        Map<String, Object> item = new HashMap<>();
                        item.put("noiDung", cons[i]);
                        item.put("tuKhoa", tuKhoa);
                        arrayResult.add(item);
                    }
                }
            }
            rs.setData(arrayResult);
        } catch (Exception e) {
            rs.setMessageDetail(e.getMessage());
        }
        return rs.getResponseEntity();
    }
}
