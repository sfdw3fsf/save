package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.IdcRackBatchValidateResponseDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.LoaiDVHTRequestDTO;

import java.io.IOException;
import java.sql.Types;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DanhMucLoaiDVHTService {
    private final DbContext dbContext;
    private static final Map<String, String> ERROR_MESSAGE_MAP = new HashMap<>();

    public DanhMucLoaiDVHTService(DbContext context) {
        this.dbContext = context;
        ERROR_MESSAGE_MAP.put("DUPLICATE_MA_SPDV", "Mã SPDV đã tồn tại trong hệ thống");
    }

    public Object getDanhMuc(String loaiDanhmuc, String thamSo1) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_loai_danhmuc", loaiDanhmuc, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_thamso_1", thamSo1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_DANH_MUC_LOAI_DVHT", params);
    }

    public Object upsertLoaiDVHT(LoaiDVHTRequestDTO dto) throws AppSqlException {
        String nguoiCn = AppRequestContext.getCurrent().getUserName();

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getLoaidvhtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaiht_id", dto.getLoaihtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaitn_id", dto.getLoaitnId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_spdv", dto.getMaSpdv(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ip", dto.getIp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cpu", dto.getCpu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ram", dto.getRam(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_storage", dto.getStorage(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_gpu", dto.getGpu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_bangthong", dto.getBangthong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_iops", dto.getIops(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_throughput", dto.getThroughput(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_log", dto.getGhiLog(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mota", dto.getMota(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_UPSERT_LOAI_DVHT", params);
    }

    public Boolean deleteLoaiDVHT(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DELETE_LOAI_DVHT", params);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public IdcRackBatchValidateResponseDTO validateBatchLoaiDVHT(List<LoaiDVHTRequestDTO> dtos) throws AppSqlException {
        try {
            int errorCount = 0;
            StringBuilder errorMessage = new StringBuilder();
            ArrayList<Map<String, Object>> response = new ArrayList<>();

            //Bước 1: check trùng lặp tên trong dto trước
            Map<String, Number> itemNameMap = new HashMap<>();
            Map<String, Number> itemShortNameMap = new HashMap<>();
            Boolean hasValid1 = false;

            for (LoaiDVHTRequestDTO dto : dtos) {
                int errorCountForDto = errorCount;
                String maSpdv = dto.getMaSpdv() == null ? "" : dto.getMaSpdv().trim().toLowerCase();

                if (!maSpdv.isEmpty()) {
                    if (itemNameMap.containsKey(maSpdv)) {
                        errorCount++;

                        if (errorMessage.length() > 0) errorMessage.append("\n");
                        errorMessage
                                .append("Dòng ")
                                .append(dto.getStt())
                                .append(": Mã SPDV đã tồn tại trong danh sách.");
                    } else {
                        itemNameMap.put(maSpdv, dto.getStt());
                    }
                }
                hasValid1 = errorCount == errorCountForDto;
            }

            Boolean hasValid2 = false;
            //Bước 2: tiếp tục kiểm tra với db
            for (LoaiDVHTRequestDTO dto : dtos) {

                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_ma_spdv", dto.getMaSpdv(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_error_codes", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR));
                dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_VALIDATE_ITEM_LOAI_DVHT", params);

                String errorCodes = null;

                for (SqlParameter param : params) {
                    if ("p_error_codes".equals(param.getName()) && param.getOutValue() != null) {
                        errorCodes = String.valueOf(param.getOutValue());
                    }
                }

                if (errorCodes != null && !errorCodes.isEmpty()) {
                    String[] codes = errorCodes.split(",");

                    for (String code : codes) {

                        String msg = ERROR_MESSAGE_MAP.getOrDefault(code, "Dữ liệu không hợp lệ");

                        errorCount++;
                        if (errorMessage.length() > 0) errorMessage.append("\n");
                        errorMessage.append("Dòng ")
                                .append(dto.getStt())
                                .append(": ")
                                .append(msg);
                    }
                } else {
                    hasValid2 = true;
                }
            }
            return new IdcRackBatchValidateResponseDTO(errorCount, errorMessage.toString(), response, hasValid1 && hasValid2);
        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

    public Map<String, Object> batchInsertLoaiDVHT(List<LoaiDVHTRequestDTO> dtos) throws AppSqlException {
        IdcRackBatchValidateResponseDTO validateResult = validateBatchLoaiDVHT(dtos);

        Set<Number> errorSttSet = extractErrorStt(validateResult.getErrorMessage());
        List<Number> successStt = new ArrayList<>();

        for (LoaiDVHTRequestDTO dto : dtos) {

            if (errorSttSet.contains(dto.getStt())) {
                continue;
            }

            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_loaiht_id", dto.getLoaihtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_loaitn_id", dto.getLoaitnId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ma_spdv", dto.getMaSpdv(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ip", dto.getIp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_cpu", dto.getCpu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ram", dto.getRam(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_storage", dto.getStorage(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_gpu", dto.getGpu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_bangthong", dto.getBangthong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_iops", dto.getIops(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_throughput", dto.getThroughput(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ghi_log", dto.getGhiLog(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_mota", dto.getMota(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

            Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_IMPORT_ITEM_LOAI_DVHT", params);

            if (result != null && result.containsKey("LOAIDVHT_ID") && result.get("LOAIDVHT_ID") != null) {
                successStt.add(dto.getStt());
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("SUCCESS_ITEMS_STT", successStt);
        response.put("VALIDATION_ERRORS", validateResult);

        return response;
    }

    private Set<Number> extractErrorStt(String errorMessage) {

        Set<Number> result = new HashSet<>();

        if (errorMessage == null || errorMessage.isEmpty()) {
            return result;
        }

        Pattern pattern = Pattern.compile("Dòng\\s+(\\d+)");
        Matcher matcher = pattern.matcher(errorMessage);

        while (matcher.find()) {
            result.add(Integer.parseInt(matcher.group(1)));
        }

        return result;
    }
}
