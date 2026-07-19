package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.ImportrPDUDto;
import vn.vnpt.database.AppSqlException;

@Service
public class ImportrPDUService {

    private final DbContext dbContext;
    private static final Pattern ORA_APP_CODE_PATTERN = Pattern.compile("ORA-(20\\d{3})");

    public ImportrPDUService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Map<String, Object> importPDU(List<ImportrPDUDto> inputs) {
        int ok = 0, fail = 0;
        List<Map<String, Object>> errors = new ArrayList<>();

        if (inputs == null || inputs.isEmpty()) {
            Map<String, Object> summary = new HashMap<>();
            summary.put("total", 0);
            summary.put("success", 0);
            summary.put("failed", 0);
            summary.put("errors", errors);
            return summary;
        }

        final String userName = AppRequestContext.getCurrent().getUserName();

        for (ImportrPDUDto input : inputs) {
            try {
                List<SqlParameter> params = buildImportrPDUParams(input, userName);
                params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                // dbContext.executeStoredProcedure("ECMS.PKG_IDC_IMPORT_TAINGUYEN_TBI.SP_RPDU_IMPORT",
                // params);
                dbContext.executeStoredProcedure("ECMS.PKG_IDC_IMPORT.SP_RPDU_IMPORT", params);

                ok++;
            } catch (AppSqlException ex) {
                String raw = ex.getMessage();

                Integer code = extractOracleAppCodeFromMessage(raw);
                String custom = mapOracleAppCode(code);

                if (custom == null)
                    custom = "Lỗi hệ thống. Vui lòng liên hệ quản trị.";

                fail++;
                Map<String, Object> err = new HashMap<>();
                err.put("input", input);
                // err.put("errorMessage", custom);
                err.put("errorMessage", raw);
                errors.add(err);
            }
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("total", inputs.size());
        summary.put("success", ok);
        summary.put("failed", fail);
        summary.put("errors", errors);
        return summary;
    }

    private List<SqlParameter> buildImportrPDUParams(ImportrPDUDto input, String userName) {
        List<SqlParameter> params = new ArrayList<>(25);

        params.add(new SqlParameter("P_RPDU_TEN", input.getRpduTen(), Types.VARCHAR));
        params.add(new SqlParameter("P_CONG_SUAT_W", input.getCongSuatDinhDanhW(), Types.NUMERIC));
        params.add(new SqlParameter("P_DIEN_AP_V", input.getDienApDinhDanhV(), Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_NGUON_DIEN", input.getLoaiNguonDien(), Types.VARCHAR));
        params.add(new SqlParameter("P_HANG_SX", input.getHangSx(), Types.VARCHAR));
        params.add(new SqlParameter("P_LOAI_RPDU", input.getLoaiRpdu(), Types.VARCHAR));
        params.add(new SqlParameter("P_KHONG_GIAN_LAP_DAT_U", input.getKhongGianLapDatU(), Types.NUMERIC));
        params.add(new SqlParameter("P_KIEU_LAP_DAT", input.getKieuLapDat(), Types.VARCHAR));
        params.add(new SqlParameter("P_SO_CONG_RA", input.getSoCongRa(), Types.NUMERIC));
        params.add(new SqlParameter("P_SO_CONG_VAO", input.getSoCongVao() != null ? input.getSoCongVao() : 1,
                Types.NUMERIC));

        params.add(new SqlParameter("P_IDC_CODE", input.getIdcCode(), Types.VARCHAR));
        params.add(new SqlParameter("P_IDC_TEN", input.getIdcTen(), Types.VARCHAR));

        params.add(new SqlParameter("P_MAT_SAN_TANG", input.getMatSanTang(), Types.VARCHAR));
        params.add(new SqlParameter("P_TOA_NHA", input.getToaNha(), Types.VARCHAR));
        params.add(new SqlParameter("P_MAT_SAN", input.getMatSan(), Types.VARCHAR));
        params.add(new SqlParameter("P_ZONE", input.getZone(), Types.VARCHAR));
        params.add(new SqlParameter("P_RACK_CHUA_RPDU", input.getRackChuaRpdu(), Types.VARCHAR));

        params.add(new SqlParameter("P_VI_TRI_TREN_RACK", input.getViTriTrenRack(), Types.VARCHAR));

        params.add(new SqlParameter("P_VI_TRI_LAP_DAT", input.getViTriLapDat(), Types.VARCHAR));
        params.add(new SqlParameter("DON_VI_QUAN_LY", input.getDonViQuanLy(), Types.VARCHAR));
        params.add(new SqlParameter("P_MOTA", input.getMoTa(), Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", userName, Types.VARCHAR));

        return params;
    }

    public Map<String, Object> dataValidate(List<ImportrPDUDto> inputs) {
        Map<String, Object> summary = new HashMap<>();
        if (inputs == null || inputs.isEmpty()) {
            summary.put("total", 0);
            summary.put("success", 0);
            summary.put("failed", 0);
            summary.put("validRows", new ArrayList<>());
            summary.put("invalidRows", new ArrayList<>());
            return summary;
        }

        final String userName = AppRequestContext.getCurrent().getUserName();

        int ok = 0, fail = 0;

        List<Map<String, Object>> validRows = new ArrayList<>();
        List<Map<String, Object>> invalidRows = new ArrayList<>();

        int rowNo = 0;
        for (ImportrPDUDto input : inputs) {
            rowNo++;

            Map<String, Object> row = new HashMap<>();
            row.put("ROW_NO", rowNo);
            row.put("RPDU_TEN", input.getRpduTen());
            row.put("CONG_SUAT_DINH_DANH_W", input.getCongSuatDinhDanhW());
            row.put("DIEN_AP_DINH_DANH_V", input.getDienApDinhDanhV());
            row.put("LOAI_NGUON_DIEN", input.getLoaiNguonDien());
            row.put("HANG_SX", input.getHangSx());
            row.put("LOAI_RPDU", input.getLoaiRpdu());
            row.put("KHONG_GIAN_LAP_DAT_U", input.getKhongGianLapDatU());
            row.put("KIEU_LAP_DAT", input.getKieuLapDat());
            row.put("SO_CONG_RA", input.getSoCongRa());
            row.put("SO_CONG_VAO", input.getSoCongVao() != null ? input.getSoCongVao() : 1);

            row.put("IDC_CODE", input.getIdcCode());
            row.put("MAT_SAN_TANG", input.getMatSanTang());
            row.put("IDC_TEN", input.getIdcTen());
            row.put("TOA_NHA", input.getToaNha());
            row.put("MAT_SAN", input.getMatSan());
            row.put("ZONE", input.getZone());

            row.put("RACK_CHUA_RPDU", input.getRackChuaRpdu());
            row.put("VI_TRI_TREN_RACK", input.getViTriTrenRack());
            row.put("VI_TRI_LAP_DAT", input.getViTriLapDat());
            row.put("DON_VI_QUAN_LY", input.getDonViQuanLy());

            try {
                List<SqlParameter> params = buildImportrPDUParams(input, userName);
                params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

                Map<String, Object> spRow = dbContext
                        .executeSpWithCursorToMap("ECMS.PKG_IDC_IMPORT.SP_RPDU_DATA_VALID", params);

                System.out.println("DEBUG SP Result: " + spRow);
                String status = "ERROR";
                // status = spRow != null && spRow.get("STATUS") != null
                // ? String.valueOf(spRow.get("STATUS"))
                // : "ERROR";

                String message = spRow != null && spRow.get("MESSAGE") != null
                        ? String.valueOf(spRow.get("MESSAGE"))
                        : null;

                if (spRow != null) {
                    Object statusObj = spRow.get("STATUS") != null ? spRow.get("STATUS") : spRow.get("status");
                    if (statusObj != null) {
                        status = String.valueOf(statusObj).trim().toUpperCase();
                    }
                }
                if ("SUCCESS".equals(status)) {
                    ok++;
                    validRows.add(row);
                } else {
                    fail++;
                    row.put("LY_DO_KHONG_HOP_LE",
                            message != null ? message : "Dữ liệu không hợp lệ");
                    invalidRows.add(row);
                }
            } catch (AppSqlException ex) {
                String raw = ex.getMessage();

                Integer code = extractOracleAppCodeFromMessage(raw);
                String custom = mapOracleAppCode(code);

                if (custom == null)
                    custom = "Lỗi hệ thống. Vui lòng liên hệ quản trị.";

                fail++;
                row.put("LY_DO_KHONG_HOP_LE", custom);
                invalidRows.add(row);
            }
        }

        summary.put("total", inputs.size());
        summary.put("success", ok);
        summary.put("failed", fail);
        summary.put("validRows", validRows);
        summary.put("invalidRows", invalidRows);
        return summary;
    }

    private Integer extractOracleAppCodeFromMessage(String rawMessage) {
        if (rawMessage == null)
            return null;
        Matcher m = ORA_APP_CODE_PATTERN.matcher(rawMessage);
        if (m.find()) {
            return Integer.parseInt(m.group(1)); // 20001..20999
        }
        return null;
    }

    private String mapOracleAppCode(Integer code) {
        if (code == null)
            return null;
        switch (code) {
            case 20001:
                return "RPDU_TEN là bắt buộc.";
            case 20002:
                return "Công suất định danh là bắt buộc.";
            case 20003:
                return "Điện áp định danh là bắt buộc.";
            case 20004:
                return "Loại nguồn điện là bắt buộc.";
            case 20005:
                return "Không tìm thấy định nghĩa loại thiết bị RPDU.";
            case 20006:
                return "IDC không tồn tại.";
            case 20007:
                return "Kiểu lắp đặt chỉ nhận Ngang/Dọc.";
            case 20008:
                return "Lắp Ngang: Yêu cầu nhập vị trí U.";
            case 20009:
                return "Lỗi format vị trí U.";
            case 20010:
                return "Kiểu lắp đặt không hợp lệ (Chỉ nhận Dọc/Ngang).";
            case 20011:
                return "Tên RPDU hoặc vị trí mặt RackUnit đã tồn tại.";
            case 20012:
                return "Không tìm thấy Rack.";
            case 20013:
                return "Không tìm thấy vị trí Unit.";
            default:
                return "Dữ liệu không hợp lệ. Mã lỗi: " + code;
        }
    }

}