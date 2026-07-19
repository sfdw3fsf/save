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
import vn.vnpt.vnptit.ecms.dto.idc.CapDoHaTangDto;
import vn.vnpt.vnptit.ecms.service.idc.CapDoHaTangService;

@RestController
@RequestMapping("/danhmuc/capdo-hatang")
public class CapDoHaTangController {

    @Autowired
    private CapDoHaTangService capDoHaTangService;

    /**
     * Lấy danh sách tất cả cấp độ hạ tầng
     */
    @PostMapping(value = "/get")
    public ResponseEntity<ApiResult> get() {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(capDoHaTangService.getListCapDoHaTangV2());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Tạo mới hoặc cập nhật cấp độ hạ tầng
     */
    @PostMapping(value = "/upsert")
    public ResponseEntity<ApiResult> upsert(@RequestBody CapDoHaTangDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            dto.isValid();
            apiResult.setData(capDoHaTangService.saveCapDoHaTang(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Xóa cấp độ hạ tầng
     */
    @PostMapping(value = "/delete")
    public ResponseEntity<ApiResult> delete(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(capDoHaTangService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Validate dữ liệu cho việc upload file
     */
    public void validate(String ten) {
        boolean isValid = (ten != null && !ten.trim().isEmpty());

        if (!isValid) {
            throw new BadRequestExceptionMessage(
                    "Không được để trống các trường bắt buộc: Tên. Vui lòng kiểm tra lại file!");
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
     * Validate dữ liệu từ file trước khi upload
     */

    @PostMapping(value = "/validate-file")
    public ResponseEntity<ApiResult> validateFile(@RequestBody List<Map<String, Object>> userInput) {
        ApiResult apiResult = new ApiResult();

        // check if userInput is empty
        if (userInput.isEmpty()) {
            apiResult.setException(new AppSqlException("Bạn chưa chọn file để upload"));
            return apiResult.getResponseEntity();
        }

        try {
            Map<String, Object> result = capDoHaTangService.validateFile(userInput);
            apiResult.setData(result);

        } catch (AppSqlException e) {
            apiResult.setException(e);
        }

        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/upload-file")
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

                validate((String) item.get("ten"));
                item.put("sudung", formatHieuLuc(item.get("sudung")));
                item.put("nguoicn", AppRequestContext.getCurrent().getUserName());

                jsonDataList.add(item);
            }

            apiResult.setData(capDoHaTangService.saveFile(jsonDataList));

        } catch (Exception e) {
            apiResult.setException(new AppSqlException(e));
        }

        return apiResult.getResponseEntity();
    }
}
