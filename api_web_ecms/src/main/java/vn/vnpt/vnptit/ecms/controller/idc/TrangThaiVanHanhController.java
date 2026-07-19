package vn.vnpt.vnptit.ecms.controller.idc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.vnpt.common.exception.BadRequestExceptionMessage;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.TrangThaiVanHanhDto;
import vn.vnpt.vnptit.ecms.service.idc.TrangThaiVanHanhService;

@RestController
@RequestMapping("/danhmuc/trangthai-vanhanh")
public class TrangThaiVanHanhController {

    @Autowired
    private TrangThaiVanHanhService trangThaiVanHanhService;

    /**
     * Lấy danh sách tất cả trạng thái vận hành
     */
    @PostMapping(value = "/get")
    public ResponseEntity<ApiResult> get() {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(trangThaiVanHanhService.getListTrangThaiVanHanh());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Tạo mới hoặc cập nhật trạng thái vận hành
     */
    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody TrangThaiVanHanhDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            dto.isValid();
            apiResult.setData(trangThaiVanHanhService.saveTrangThaiVanHanh(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Xóa trạng thái vận hành
     */
    @PostMapping(value = "/delete")
    public ResponseEntity<ApiResult> delete(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(trangThaiVanHanhService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Validate dữ liệu cho việc upload file
     */
    public void validate(String trangThaiVh) {
        boolean isValid = (trangThaiVh != null && !trangThaiVh.trim().isEmpty());

        if (!isValid) {
            throw new BadRequestExceptionMessage(
                    "Không được để trống các trường bắt buộc: Trạng Thái Vận Hành. Vui lòng kiểm tra lại file!");
        }
    }

    /**
     * Format trường hiệu lực
     */
    public boolean formatHieuLuc(Object hieuLuc) {
        try {
            Integer res = (Integer) hieuLuc;
            if (res == 0 || res == 1) {
                return res == 1;
            }
        } catch (Exception ignored) {
        }
        throw new BadRequestExceptionMessage("Trường Hiệu lực chỉ được nhập 1 hoặc 0. Vui lòng kiểm tra lại file!");
    }

    /**
     * Upload dữ liệu từ file
     */
    @PostMapping(value = "/upload-by-file")
    public ResponseEntity<ApiResult> uploadByFile(@RequestBody List<Map<String, Object>> userInput) {
        ApiResult apiResult = new ApiResult();

        // check if userInput is empty
        if (userInput.isEmpty()) {
            apiResult.setException(new AppSqlException("Bạn chưa chọn file để upload"));
            return apiResult.getResponseEntity();
        }

        // convert userInput to json data
        List<Map<String, Object>> jsonDataList = new ArrayList<>();
        try {
            for (Map<String, Object> item : userInput) {

                item.put("SUDUNG", formatHieuLuc(item.get("SUDUNG")));
                item.put("NGUOI_CN", AppRequestContext.getCurrent().getUserName());

                validate((String) item.get("TRANGTHAI_VH"));

                jsonDataList.add(item);
            }

            apiResult.setData(trangThaiVanHanhService.saveByFile(jsonDataList));

        } catch (Exception e) {
            apiResult.setException(new AppSqlException(e));
        }

        return apiResult.getResponseEntity();
    }

    /**
     * Validate dữ liệu từ file trước khi upload
     */
    @PostMapping(value = "/validate-by-file")
    public ResponseEntity<ApiResult> validateByFile(@RequestBody List<Map<String, Object>> userInput) {
        ApiResult apiResult = new ApiResult();

        // check if userInput is empty
        if (userInput.isEmpty()) {
            apiResult.setException(new AppSqlException("Bạn chưa chọn file để upload"));
            return apiResult.getResponseEntity();
        }

        // List<String> errors = new ArrayList<>();
        // int rowIndex = 1;

        // convert userInput to json data
        // List<Map<String, Object>> jsonDataList = new ArrayList<>();

        // for (Map<String, Object> item : userInput) {
        // try {
        // item.put("DONG", rowIndex);
        // item.put("SUDUNG", formatHieuLuc(item.get("SUDUNG")));
        // item.put("NGUOI_CN", AppRequestContext.getCurrent().getUserName());
        // item.put("TRANGTHAI_VH", item.get("TRANGTHAI_VH").toString().trim());

        // validate((String) item.get("TRANGTHAI_VH"));

        // jsonDataList.add(item);

        // } catch (Exception e) {
        // errors.add("Dòng " + rowIndex + ": " + e.getMessage());
        // }
        // rowIndex++;
        // }

        try {
            Map<String, Object> result = trangThaiVanHanhService.validateByFile(userInput);
            // result.put("dataTypeError", errors);
            apiResult.setData(result);

        } catch (AppSqlException e) {
            apiResult.setException(e);
        }

        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/get-v2")
    public ResponseEntity<ApiResult> getV2() {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(trangThaiVanHanhService.getListTrangThaiVanHanhV2());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/validate-file-v2")
    public ResponseEntity<ApiResult> validateFile(@RequestBody List<Map<String, Object>> userInput) {
        ApiResult apiResult = new ApiResult();

        // check if userInput is empty
        if (userInput.isEmpty()) {
            apiResult.setException(new AppSqlException("Bạn chưa chọn file để upload"));
            return apiResult.getResponseEntity();
        }

        try {
            Map<String, Object> result = trangThaiVanHanhService.validateFileV2(userInput);
            apiResult.setData(result);

        } catch (AppSqlException e) {
            apiResult.setException(e);
        }

        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/upload-file-v2")
    public ResponseEntity<ApiResult> uploadFile(@RequestBody List<Map<String, Object>> userInput) {
        ApiResult apiResult = new ApiResult();

        // check if userInput is empty
        if (userInput.isEmpty()) {
            apiResult.setException(new AppSqlException("Bạn chưa chọn file để upload"));
            return apiResult.getResponseEntity();
        }

        // convert userInput to json data
        List<Map<String, Object>> jsonDataList = new ArrayList<>();
        try {
            for (Map<String, Object> item : userInput) {

                item.put("suDung", formatHieuLuc(item.get("suDung")));
                item.put("nguoiCn", AppRequestContext.getCurrent().getUserName());

                validate((String) item.get("trangThaiVh"));

                jsonDataList.add(item);
            }

            apiResult.setData(trangThaiVanHanhService.saveFileV2(jsonDataList));

        } catch (Exception e) {
            apiResult.setException(new AppSqlException(e));
        }

        return apiResult.getResponseEntity();
    }
}
