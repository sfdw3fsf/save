package vn.vnpt.vnptit.ecms.controller.idc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import io.swagger.annotations.ApiOperation;
import vn.vnpt.common.exception.BadRequestExceptionMessage;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.IDCDto;
import vn.vnpt.vnptit.ecms.dto.idc.IDCStatisticRequestDto;
import vn.vnpt.vnptit.ecms.service.idc.IDCService;

@RestController
@RequestMapping("/danhmuc/idc")
public class IDCController {
    @Autowired
    private IDCService idcService;

    @GetMapping(value = "/get")
    public ResponseEntity<ApiResult> get() {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(idcService.getListIDC());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/get-by-id")
    public ResponseEntity<ApiResult> getById(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(idcService.getById(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @GetMapping(value = "/statistic")
    public ResponseEntity<ApiResult> statistic() {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(idcService.getListIDCStatistic());
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/statistic-detail")
    public ResponseEntity<ApiResult> statisticDetail(@RequestBody IDCStatisticRequestDto body) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(idcService.getIDCStatisticDetail(body.getIdcId()));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        } catch (Exception e) {
            apiResult.setException(new AppSqlException(e));
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/rack-statistic")
    public ResponseEntity<ApiResult> statisticRacks(@RequestBody IDCStatisticRequestDto body) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(idcService.getIDCRackStatistic(body.getIdcId()));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        } catch (Exception e) {
            apiResult.setException(new AppSqlException(e));
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/create-or-update")
    public ResponseEntity<ApiResult> createOrUpdate(@RequestBody IDCDto dto) {
        ApiResult apiResult = new ApiResult();
        try {
            dto.isValid();
            apiResult.setData(idcService.saveIDC(dto));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    public void validate(Integer idDonVi, String ten, String diaChi, String thoiDiem) {
        boolean isvalid = (idDonVi != null && ten != null && diaChi != null && !ten.isEmpty() && !diaChi.isEmpty());
        if (!isvalid)
            throw new BadRequestExceptionMessage(
                    "Không được để trống các trường bắt buộc. Vui lòng kiểm tra lại file!");

    }

    public String formatDate(Object thoiDiem) {
        try {
            String thoiDiemStr = (String) thoiDiem;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(thoiDiemStr, dateFormatter);

            return localDate.format(dateFormatter);
        } catch (Exception e) {
            throw new BadRequestExceptionMessage("Vui lòng nhập đúng định dạng ngày tháng (dd/MM/yyyy)");
        }
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

        if (userInput.isEmpty()) {
            apiResult.setException(new AppSqlException("Bạn chưa chọn file để upload"));
            return apiResult.getResponseEntity();
        }

        List<Map<String, Object>> jsonDataList = new ArrayList<>();

        try {
            for (Object obj : userInput) {
                Map<String, Object> idc = (Map<String, Object>) obj;

                // Chuẩn hóa field theo procedure SP_SAVE_MULTI_IDC
                idc.put("THOIDIEM_VANHANH", idc.get("THOIDIEM_VANHANH"));
                idc.put("SUDUNG", formatSuDung(idc.get("SUDUNG")));
                idc.put("NGUOI_CN", AppRequestContext.getCurrent().getUserName());

                validate(
                        (Integer) idc.get("ID_DONVI"),
                        (String) idc.get("TEN"),
                        (String) idc.get("DIA_CHI"),
                        (String) idc.get("THOIDIEM_VANHANH"));
                idc.putIfAbsent("TONG_DIENTICH", 0);
                idc.putIfAbsent("ANHDAIDIEN", null);
                idc.putIfAbsent("ID_TRANGTHAI_VH", 1);
                idc.putIfAbsent("TOADO_X", null);
                idc.putIfAbsent("TOADO_Y", null);
                idc.putIfAbsent("TENTAT", null);
                idc.putIfAbsent("MOTA", null);

                jsonDataList.add(idc);
            }

            apiResult.setData(idcService.saveByFile(jsonDataList));

        } catch (Exception e) {
            apiResult.setException(new AppSqlException(e));
        }

        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/validate-by-file")
    public ResponseEntity<ApiResult> validateByFile(@RequestBody List<Map<String, Object>> userInput) {
        ApiResult apiResult = new ApiResult();

        if (userInput.isEmpty()) {
            apiResult.setException(new AppSqlException("Bạn chưa chọn file để upload"));
            return apiResult.getResponseEntity();
        }

        List<String> errors = new ArrayList<>();
        List<Map<String, Object>> jsonDataList = new ArrayList<>();
        int rowIndex = 1;

        for (Map<String, Object> idc : userInput) {
            try {
                idc.put("DONG", rowIndex);
                idc.put("THOIDIEM_VANHANH", idc.get("THOIDIEM_VANHANH"));
                idc.put("SUDUNG", formatSuDung(idc.get("SUDUNG")));
                idc.put("NGUOI_CN", AppRequestContext.getCurrent().getUserName());

                validate(
                        (Integer) idc.get("ID_DONVI"),
                        (String) idc.get("TEN"),
                        (String) idc.get("DIA_CHI"),
                        (String) idc.get("THOIDIEM_VANHANH"));

                // Gán giá trị mặc định nếu thiếu
                idc.putIfAbsent("TONG_DIENTICH", 0);
                idc.putIfAbsent("ANHDAIDIEN", null);
                idc.putIfAbsent("ID_TRANGTHAI_VH", 1);
                idc.putIfAbsent("TOADO_X", null);
                idc.putIfAbsent("TOADO_Y", null);
                idc.putIfAbsent("TENTAT", null);
                idc.putIfAbsent("MOTA", null);

                jsonDataList.add(idc);
            } catch (Exception e) {
                errors.add("Dòng " + rowIndex + ": " + e.getMessage());
            }
            rowIndex++;
        }

        try {
            Map<String, Object> result = idcService.validateByFile(jsonDataList);
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
            apiResult.setData(idcService.delete(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    // ====== Không gian
    @PostMapping(value = "/matsan-statistic")
    @ApiOperation(value = "Lấy danh sách tất cả các mặt sàn theo idc")
    public ResponseEntity<ApiResult> getMatSan(@RequestParam Long id) {
        ApiResult res = new ApiResult();
        try {
            res.setData(idcService.getMatSan(id));
        } catch (AppSqlException e) {
            res.setException(e);
        }
        return res.getResponseEntity();
    }

    @PostMapping(value = "/toanha-statistic")
    public ResponseEntity<ApiResult> getToaNha(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(idcService.getToaNha(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }

    @PostMapping(value = "/khachhang-statistic")
    public ResponseEntity<ApiResult> getKhachHang(@RequestParam Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            apiResult.setData(idcService.getKhachHang(id));
        } catch (AppSqlException e) {
            apiResult.setException(e);
        }
        return apiResult.getResponseEntity();
    }
}
