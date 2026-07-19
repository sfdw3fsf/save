package vn.vnpt.vnptit.ecms.controller.idc;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.ThroughputDto;
import vn.vnpt.vnptit.ecms.service.idc.ThroughputService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vn.vnpt.common.exception.BadRequestExceptionMessage;
import vn.vnpt.context.AppRequestContext;

@RestController
@RequestMapping("/throughput/v2")
public class ThroughputController {
    private final ThroughputService throughputService;

    public ThroughputController(ThroughputService throughputService) {
        this.throughputService = throughputService;
    }

    @Operation(summary = "Thêm hoặc cập nhật Throughput")
    @PostMapping("/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody ThroughputDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(throughputService.upsertThroughput(dto));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Lấy danh sách Throughput")
    @PostMapping("/get-danh-sach")
    public ResponseEntity<ApiResult> getDanhSach() {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(throughputService.getDanhSachThroughput());
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    @Operation(summary = "Xóa Throughput")
    @PostMapping("/delete")
    public ResponseEntity<ApiResult> delete(@RequestBody ThroughputDto dto) {
        ApiResult rs = new ApiResult();
        try {
            rs.setData(throughputService.deleteThroughput(dto.getThroughputId()));
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    /**
     * Validate trường bắt buộc cho import
     */
    public void validate(String ten) {
        boolean isValid = (ten != null && !ten.trim().isEmpty());
        if (!isValid) {
            throw new BadRequestExceptionMessage(
                    "Không được để trống các trường bắt buộc: Tên. Vui lòng kiểm tra lại file!");
        }
    }

    /**
     * Format trường hiệu lực cho import
     */
    public Integer formatHieuLuc(Object hieuLuc) {
        try {
            if (hieuLuc instanceof Number) {
                return ((Number) hieuLuc).intValue();
            }
            if (hieuLuc instanceof String) {
                return Integer.parseInt((String) hieuLuc);
            }
            if (hieuLuc instanceof Boolean) {
                return (Boolean) hieuLuc ? 1 : 0;
            }
        } catch (Exception ignored) {
        }
        throw new BadRequestExceptionMessage("Trường Hiệu lực không hợp lệ. Vui lòng kiểm tra lại file!");
    }

    /**
     * Validate dữ liệu từ file trước khi upload
     */
    @Operation(summary = "Validate file import Throughput")
    @PostMapping(value = "/validate")
    public ResponseEntity<ApiResult> validateFile(@RequestBody List<Map<String, Object>> userInput) {
        ApiResult apiResult = new ApiResult();
        if (userInput.isEmpty()) {
            apiResult.setException(new AppSqlException("Bạn chưa chọn file để upload"));
            return apiResult.getResponseEntity();
        }

        List<String> errors = new ArrayList<>();
        List<Map<String, Object>> jsonDataList = new ArrayList<>();
        int rowIndex = 1;

        for (Map<String, Object> item : userInput) {
            try {
                validate((String) item.get("ten"));
                item.put("sudung", formatHieuLuc(item.get("sudung")));
                item.put("nguoicn", AppRequestContext.getCurrent().getUserName());

                jsonDataList.add(item);
            } catch (Exception e) {
                errors.add("Dòng " + rowIndex + ": " + e.getMessage());
            }
            rowIndex++;
        }

        try {
            Map<String, Object> dbResult = throughputService.validateFile(jsonDataList);
            String dbErrors = (String) dbResult.get("businessLogicError");
            if (dbErrors != null && !dbErrors.isEmpty()) {
                for (String msg : dbErrors.split("\n")) {
                    if (!msg.trim().isEmpty()) {
                        errors.add(msg.trim());
                    }
                }
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("totalCount", userInput.size());
            result.put("errorCount", errors.size());
            result.put("errorMessages", errors);
            
            apiResult.setData(result);
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Thực hiện import dữ liệu từ file
     */
    @Operation(summary = "Import dữ liệu Throughput từ file")
    @PostMapping(value = "/upload")
    public ResponseEntity<ApiResult> uploadFile(@RequestBody List<Map<String, Object>> userInput) {
        ApiResult apiResult = new ApiResult();
        if (userInput.isEmpty()) {
            apiResult.setException(new AppSqlException("Bạn chưa chọn file để upload"));
            return apiResult.getResponseEntity();
        }

        List<Map<String, Object>> jsonDataList = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        int rowIndex = 1;
        
        for (Map<String, Object> item : userInput) {
            try {
                validate((String) item.get("ten"));
                item.put("sudung", formatHieuLuc(item.get("sudung")));
                item.put("nguoicn", AppRequestContext.getCurrent().getUserName());
                jsonDataList.add(item);
            } catch (Exception e) {
                errors.add("Dòng " + rowIndex + ": " + e.getMessage());
            }
            rowIndex++;
        }

        try {
            Map<String, Object> dbResult = throughputService.saveFile(jsonDataList);

            String dbErrors = (String) dbResult.get("errorMessage");
            if (dbErrors != null && !dbErrors.isEmpty()) {
                for (String msg : dbErrors.split("\n")) {
                    if (!msg.trim().isEmpty()) {
                        errors.add(msg.trim());
                    }
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("totalCount", userInput.size());
            result.put("successCount", dbResult.get("successCount"));
            result.put("errorCount", errors.size());
            result.put("errorMessages", errors);

            apiResult.setData(result);
        } catch (Exception e) {
            apiResult.setException(new AppSqlException(e));
        }
        return apiResult.getResponseEntity();
    }
}
