package vn.vnpt.vnptit.ecms.controller.hatang;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import vn.vnpt.common.converter.ExcelHelper;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.service.hatang.ToiUuTaiNguyenService;

@RestController
@RequestMapping("/toiuu-tainguyen")
public class ToiUuTaiNguyenController {
    @Autowired
    ToiUuTaiNguyenService toiUuTaiNguyenService;

    @Operation(summary = "Lấy danh sách yêu cầu tối ưu")
    @RequestMapping(value = "/lay-ds-yeucau-toiuu", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> layDanhSachYeuCauThuHoi(@RequestBody Map<String, Object> params) {
        String p_thang = (String) params.get("p_thang");
        String p_spdv_ids = (String) params.get("p_spdv_ids");
        ApiResult rs = new ApiResult();
        try {
            rs.setData(toiUuTaiNguyenService.layDanhSachYeuCauThuHoi(p_thang, p_spdv_ids));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<ApiResult> uploadExcelFile(@RequestParam("file") MultipartFile file,
            @RequestParam("monthYear") String monthYear) {
        ApiResult rs = new ApiResult();

        if (file.isEmpty()) {
            rs.setError("400");
            rs.setMessage("p_spdv_id không được null");
            return ResponseEntity.badRequest().body(rs);
        }

        try (InputStream is = file.getInputStream()) {
            // 1. Parse Excel
            List<Object> dataFromExcel = ExcelHelper.parseExcel(is);

            // 2. Save to DB
            rs.setData(toiUuTaiNguyenService.saveViaStoredProcedure(dataFromExcel, monthYear));

        } catch (IOException e) {
            rs.setError("500");
            rs.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(rs);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }
        return rs.getResponseEntity();
    }
}
