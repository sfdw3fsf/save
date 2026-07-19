package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

@Service
public class ToiUuTaiNguyenService {
    @Autowired
    DbContext dbContext;

    public ArrayList<Map<String, Object>> layDanhSachYeuCauThuHoi(String pThang, String pSpdvIds)
            throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_thang", pThang, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters
                .add(new SqlParameter("p_dichvu_ids", pSpdvIds, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TOIUU_TAINGUYEN.sp_ds_toiuu_hieunangthang", parameters);
    }

    public List<Map<String, Object>> saveViaStoredProcedure(List<Object> rawData, String monthYear)
            throws AppSqlException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth yearMonth = YearMonth.parse(monthYear, formatter);

        int month = yearMonth.getMonthValue();
        int year = yearMonth.getYear();

        List<Map<String, Object>> processedData = new ArrayList<>();

        for (Object obj : rawData) {
            Map<String, Object> row = (Map<String, Object>) obj;

            String tenvm = (String) row.get("Tên máy chủ");
            String ip = row.get("IP") != null ? String.valueOf(row.get("IP")).trim() : null;
            Integer vcpu = convertToNumber(row.get("vCPU"));
            Integer vram = convertToNumber(row.get("RAM"));
            Integer vcpuMax = convertToNumber(row.get("CPU USE MAX (%)"));
            Integer vramMax = convertToNumber(row.get("RAM USE MAX (%)"));
            String spdvName = row.get("Sản phẩm dịch vụ") != null ? String.valueOf(row.get("Sản phẩm dịch vụ")).trim()
                    : null;
            Long donviqlyId = mockIdFromName(row.get("Đơn vị chủ quản"));
            Long qtriId = mockIdFromName(row.get("Quản trị viên"));

            Map<String, Object> processedRow = new HashMap<>();
            processedRow.put("TEN_MAYCHU", tenvm);
            processedRow.put("IP", ip);
            processedRow.put("DONVI_CHUQUAN", donviqlyId);
            processedRow.put("DONVI_VANHANH", donviqlyId);
            processedRow.put("TEN_QUANTRIVIEN", qtriId);
            processedRow.put("VCPU", vcpu);
            processedRow.put("VRAM", vram);
            processedRow.put("VCPU_USE_MAX", vcpuMax);
            processedRow.put("VRAM_USE_MAX", vramMax);
            processedRow.put("MONTH", month);
            processedRow.put("YEAR", year);

            try {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_tenvm", tenvm, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_ip_address_name", ip, SqlParameter.ParameterDirection.INPUT,
                        Types.VARCHAR)); // sử dụng tên IP
                params.add(new SqlParameter("p_spdv_name", spdvName, SqlParameter.ParameterDirection.INPUT,
                        Types.VARCHAR)); // sử dụng tên SPDV
                params.add(new SqlParameter("p_donviqly_id", donviqlyId, SqlParameter.ParameterDirection.INPUT,
                        Types.NUMERIC));
                params.add(new SqlParameter("p_qtri_id", qtriId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_vcpu", vcpu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_vram", vram, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(
                        new SqlParameter("p_vcpu_max", vcpuMax, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(
                        new SqlParameter("p_vram_max", vramMax, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_ngay", new Date(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
                params.add(new SqlParameter("p_thang", month, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_nam", year, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

                dbContext.executeSpWithCursorToListMap("ECMS.PKG_TOIUU_TAINGUYEN.sp_insert_tt_toi_uu_hieu_nang",
                        params);

                processedRow.put("ERROR", "Thành công"); // không lỗi
            } catch (Exception ex) {
                String errorMessage = ex.getMessage();
                String customError;

                if (errorMessage != null) {
                    if (errorMessage.contains("ORA-01422")) {
                        customError = "Truy vấn trả về nhiều hơn 1 dòng";
                    } else if (errorMessage.contains("ORA-20001")) {
                        customError = "Không tìm thấy IP hoặc SPDV tương ứng với tên được cung cấp";
                    } else if (errorMessage.contains("ORA-00001")) {
                        customError = "Dữ liệu đã tồn tại";
                    } else if (errorMessage.contains("ORA-06502")) {
                        customError = "Lỗi kiểu dữ liệu hoặc tràn bộ đệm";
                    } else if (errorMessage.contains("ORA-")) {
                        customError = "Lỗi Oracle: " + errorMessage;
                    } else {
                        customError = "Lỗi không xác định: " + errorMessage;
                    }
                } else {
                    customError = "Lỗi không xác định";
                }

                processedRow.put("ERROR", "Thất bại - Lỗi: " + customError);
            }

            processedData.add(processedRow);
        }

        return processedData;
    }

    private Integer convertToNumber(Object value) {
        if (value == null)
            return null;
        return (value instanceof Number) ? ((Number) value).intValue() : null;
    }

    private Long mockIdFromName(Object val) {
        return (val == null) ? null : (long) Math.abs(val.toString().hashCode());
    }
}
