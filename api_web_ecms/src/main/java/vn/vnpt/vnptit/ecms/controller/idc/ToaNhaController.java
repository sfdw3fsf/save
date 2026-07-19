package vn.vnpt.vnptit.ecms.controller.idc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vnpt.common.exception.BadRequestExceptionMessage;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ToaNhaDto;
import vn.vnpt.vnptit.ecms.service.idc.ToaNhaService;

@RestController
@RequestMapping("/danhmuc/toanha")
public class ToaNhaController {
    @Autowired
    private ToaNhaService toaNhaService;


    @GetMapping(value = "/get")
    public ResponseEntity<ApiResult> get() {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(toaNhaService.getListToaNha());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody ToaNhaDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            dto.isValid();
            apiResult.setData(toaNhaService.saveToaNha(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    public void validate(Integer idIDC, String ten) {
        boolean isvalid = (idIDC != null && idIDC > 0) && (ten != null && !ten.isEmpty());
        if (!isvalid)
            throw new BadRequestExceptionMessage(
                    "Không được để trống các trường bắt buộc. Vui lòng kiểm tra lại file!");
    }

    public boolean formatSuDung(Object suDung) {
        try {
            Integer res = (Integer) suDung;
            if (res == 0 || res == 1) {
                return res == 1;
            }
        } catch (Exception ignored) {
        }
        throw new BadRequestExceptionMessage("Trường Hiệu lực chỉ được nhập 1 hoặc 0. Vui lòng kiểm tra lại file!");
    }

    @PostMapping(value = "/upload-by-file")
    public ResponseEntity<ApiResult> uploadByFile(@RequestBody List<Map<String, Object>> userInput) {

        ApiResult apiResult = new ApiResult();

        // check if userInput is empty
        if (userInput.isEmpty()) {
            apiResult.setException(new AppSqlException("Bạn chưa chọn file để upload"));
        }

        // convert userInput to json data of ToaNha
        List<Map<String, Object>> jsonDataList = new ArrayList<>();
        try {
            for (Map<String, Object> toaNha : userInput) {

                toaNha.put("SUDUNG", formatSuDung(toaNha.get("SUDUNG")));
                toaNha.put("NGUOI_CN", AppRequestContext.getCurrent().getUserName());
                validate(
                        (Integer) toaNha.get("IDC_ID"),
                        (String) toaNha.get("TEN"));

                jsonDataList.add(toaNha);
            }

            apiResult.setData(toaNhaService.saveByFile(jsonDataList));

        } catch (Exception e) {
            apiResult.setException(new AppSqlException(e));
        }

        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/validate-by-file")
    public ResponseEntity<ApiResult> validateByFile(@RequestBody List<Map<String, Object>> userInput) {

        ApiResult apiResult = new ApiResult();

        // check if userInput is empty
        if (userInput.isEmpty()) {
            apiResult.setException(new AppSqlException("Bạn chưa chọn file để upload"));
        }

        List<String> errors = new ArrayList<>();
        int rowIndex = 1;

        // convert userInput to json data of ToaNha
        List<Map<String, Object>> jsonDataList = new ArrayList<>();

        for (Map<String, Object> toaNha : userInput) {
            try {
                toaNha.put("DONG", rowIndex);
                toaNha.put("SUDUNG", formatSuDung(toaNha.get("SUDUNG")));
                toaNha.put("NGUOI_CN", AppRequestContext.getCurrent().getUserName());

                validate(
                        (Integer) toaNha.get("IDC_ID"),
                        (String) toaNha.get("TEN"));

                jsonDataList.add(toaNha);

            } catch (Exception e) {
                errors.add("Dòng " + rowIndex + ": " + e.getMessage());
            }
            rowIndex++;
        }

        try {
            Map<String, Object> result = toaNhaService.validateByFile(jsonDataList);
            result.put("dataTypeError", errors);
            apiResult.setData(result);

        } catch (AppSqlException e) {
            apiResult.setException(e);
        }

        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<ApiResult> delete(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(toaNhaService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    // ====== không gian

    @PostMapping(value = "/get-by-id")
    public ResponseEntity<ApiResult> getById(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(toaNhaService.getToaNhaById(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/get-mat-san")
    public ResponseEntity<ApiResult> getMatSan(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(toaNhaService.getMatSan(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }


    @PostMapping(value = "/search")
    public ResponseEntity<ApiResult> getByIdc(@RequestBody JsonNode searchJsonData) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(toaNhaService.searchToaNha(searchJsonData));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

}
