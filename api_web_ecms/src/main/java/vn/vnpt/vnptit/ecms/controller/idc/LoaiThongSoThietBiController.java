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
import vn.vnpt.common.exception.BadRequestExceptionMessage;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiThongSoThietBiDto;
import vn.vnpt.vnptit.ecms.service.idc.LoaiThongSoThietBiService;

@RestController
@RequestMapping("/danhmuc/loai-thongso-thietbi")
public class LoaiThongSoThietBiController {

    @Autowired
    private LoaiThongSoThietBiService loaiThongSoThietBiService;

    /**
     * Lấy danh sách loại thiết bị cho dropdown
     */
    @GetMapping(value = "/get-loai-thiet-bi")
    public ResponseEntity<ApiResult> getListLoaiThietBi() {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(loaiThongSoThietBiService.getListLoaiThietBi());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Lấy danh sách trạng thái vận hành cho dropdown
     */
    @GetMapping(value = "/get-trangthai-vanhanh")
    public ResponseEntity<ApiResult> getListTrangThaiVanHanh() {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(loaiThongSoThietBiService.getListTrangThaiVanHanh());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Lấy danh sách tất cả loại thông số thiết bị
     */
    @GetMapping(value = "/get")
    public ResponseEntity<ApiResult> get() {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(loaiThongSoThietBiService.getListLoaiThongSoThietBi());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Tạo mới hoặc cập nhật loại thông số thiết bị
     */
    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody LoaiThongSoThietBiDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            dto.isValid();
            apiResult.setData(loaiThongSoThietBiService.saveLoaiThongSoThietBi(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Xóa loại thông số thiết bị
     */
    @PostMapping(value = "/delete")
    public ResponseEntity<ApiResult> delete(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(loaiThongSoThietBiService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    /**
     * Validate dữ liệu cho việc upload file
     */
    public void validate(Number idLoaiTB, String tenLoaiThongSoTB, Number giaTriDinhDanh, Number trangThaiVanHanhId) {
        boolean isValid = (idLoaiTB != null &&
                tenLoaiThongSoTB != null && !tenLoaiThongSoTB.trim().isEmpty() &&
                giaTriDinhDanh != null &&
                trangThaiVanHanhId != null);

        if (!isValid) {
            throw new BadRequestExceptionMessage(
                    "Không được để trống các trường bắt buộc: ID Loại TB, Tên Loại Thông Số TB, Giá Trị Định Danh, Trạng Thái Vận Hành. Vui lòng kiểm tra lại file!");
        }

        // Validate trangThaiVanHanhId
        if (trangThaiVanHanhId != null) {
            int trangThai = trangThaiVanHanhId.intValue();
            if (trangThai < 0 || trangThai > 2) {
                throw new BadRequestExceptionMessage(
                        "Trạng thái vận hành chỉ được nhận giá trị 0, 1, 2. Vui lòng kiểm tra lại file!");
            }
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

                validate(
                        (Number) item.get("LOAITBI_ID"),
                        (String) item.get("TEN"),
                        (Number) item.get("GIATRI"),
                        (Number) item.get("TRANGTHAI_VH_ID"));

                jsonDataList.add(item);
            }

            apiResult.setData(loaiThongSoThietBiService.saveByFile(jsonDataList));

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

        List<String> errors = new ArrayList<>();
        int rowIndex = 1;

        // convert userInput to json data
        List<Map<String, Object>> jsonDataList = new ArrayList<>();

        for (Map<String, Object> item : userInput) {
            try {
                item.put("DONG", rowIndex);
                item.put("SUDUNG", formatHieuLuc(item.get("SUDUNG")));
                item.put("NGUOI_CN", AppRequestContext.getCurrent().getUserName());

                validate(
                        (Number) item.get("LOAITBI_ID"),
                        (String) item.get("TEN"),
                        (Number) item.get("GIATRI"),
                        (Number) item.get("TRANGTHAI_VH_ID"));

                jsonDataList.add(item);

            } catch (Exception e) {
                errors.add("Dòng " + rowIndex + ": " + e.getMessage());
            }
            rowIndex++;
        }

        try {
            Map<String, Object> result = loaiThongSoThietBiService.validateByFile(jsonDataList);
            result.put("dataTypeError", errors);
            apiResult.setData(result);

        } catch (AppSqlException e) {
            apiResult.setException(e);
        }

        return apiResult.getResponseEntity();
    }
}
