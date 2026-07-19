package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.NhaCungCapV2RequestDTO;
import vn.vnpt.vnptit.ecms.dto.idc.IdcRackBatchValidateResponseDTO;

import java.io.IOException;
import java.sql.Types;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NhaCungCapV2Service {
    private final DbContext dbContext;
    private static final Map<String, String> ERROR_MESSAGE_MAP = new HashMap<>();

    public NhaCungCapV2Service(DbContext context) {
        this.dbContext = context;
        ERROR_MESSAGE_MAP.put("DUPLICATE_TEN_NHA_CC", "Tên nhà cung cấp đã tồn tại trong hệ thống");
        ERROR_MESSAGE_MAP.put("DUPLICATE_TENTAT_NHA_CC", "Tên tắt đã tồn tại trong hệ thống");
    }

    public Object getDanhMuc(String loaiDanhmuc, Number thamSo1) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_loai_danhmuc", loaiDanhmuc, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_thamso_1", thamSo1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_COMMON.SP_GET_DANH_MUC", params);
    }

    public Object upsertNhaCungCap(NhaCungCapV2RequestDTO dto) throws AppSqlException {
        String nguoiCn = AppRequestContext.getCurrent().getUserName();

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_tentat", dto.gettentat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_diachi", dto.getDiachi(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_dienthoai", dto.getDienthoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_email", dto.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_website", dto.getWebsite(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_tk", dto.getSo_tk(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_dd", dto.getNguoi_dd(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSudung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_COMMON.SP_UPSERT_NHA_CC", params);
    }

    public Boolean deleteNhaCungCap(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_COMMON.SP_DELETE_NHA_CC", params);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public IdcRackBatchValidateResponseDTO validateBatchNhaCC(List<NhaCungCapV2RequestDTO> dtos) throws AppSqlException {
        try {
            int errorCount = 0;
            StringBuilder errorMessage = new StringBuilder();
            ArrayList<Map<String, Object>> response = new ArrayList<>();

            //Bước 1: check trùng lặp tên trong dto trước
            Map<String, Number> nhaccNameMap = new HashMap<>();
            Map<String, Number> nhaccShortNameMap = new HashMap<>();
            Boolean hasValid1 = false;

            for (NhaCungCapV2RequestDTO dto : dtos) {
                int errorCountForDto = errorCount;
                String name = dto.getTen() == null ? "" : dto.getTen().trim().toLowerCase();
                String shortName = dto.gettentat() == null ? "" : dto.gettentat().trim().toLowerCase();

                if (!name.isEmpty()) {
                    if (nhaccNameMap.containsKey(name)) {
                        errorCount++;

                        if (errorMessage.length() > 0) errorMessage.append("\n");
                        errorMessage
                                .append("Dòng ")
                                .append(dto.getStt())
                                .append(": Tên nhà cung cấp đã tồn tại trong danh sách.");
                    } else {
                        nhaccNameMap.put(name, dto.getStt());
                    }
                }
                if (!shortName.isEmpty()) {
                    if (nhaccShortNameMap.containsKey(shortName)) {
                        errorCount++;

                        if (errorMessage.length() > 0) errorMessage.append("\n");
                        errorMessage
                                .append("Dòng ")
                                .append(dto.getStt())
                                .append(": Tên tắt đã tồn tại trong danh sách.");
                    } else {
                        nhaccShortNameMap.put(shortName, dto.getStt());
                    }
                }
                hasValid1 = errorCount == errorCountForDto;
            }

            Boolean hasValid2 = false;
            //Bước 2: tiếp tục kiểm tra với db
            for (NhaCungCapV2RequestDTO dto : dtos) {

                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
                params.add(new SqlParameter("p_tentat", dto.gettentat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_error_codes", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR));
                dbContext.executeStoredProcedure("ECMS.PKG_HATANG_COMMON.SP_VALIDATE_ITEM_NHA_CC", params);

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

    public Map<String, Object> batchInsertNhaCC(List<NhaCungCapV2RequestDTO> dtos) throws AppSqlException {
        IdcRackBatchValidateResponseDTO validateResult = validateBatchNhaCC(dtos);

        Set<Number> errorSttSet = extractErrorStt(validateResult.getErrorMessage());
        List<Number> successStt = new ArrayList<>();

        for (NhaCungCapV2RequestDTO dto : dtos) {

            if (errorSttSet.contains(dto.getStt())) {
                continue;
            }

            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            params.add(new SqlParameter("p_tentat", dto.gettentat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_diachi", dto.getDiachi(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            params.add(new SqlParameter("p_dienthoai", dto.getDienthoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_email", dto.getEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_website", dto.getWebsite(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_so_tk", dto.getSo_tk(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_nguoi_dd", dto.getNguoi_dd(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            params.add(new SqlParameter("p_sudung", dto.getSudung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

            Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_COMMON.SP_IMPORT_ITEM_NHA_CC", params);

            if (result != null && result.containsKey("NHACC_ID") && result.get("NHACC_ID") != null) {
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
