package vn.vnpt.vnptit.ecms.service.hatang;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.hatang.TaiSanThietBiRequestDTO;

@Service
public class TaiSanThietBiService {
    @Autowired
    private DbContext dbContext;

    public boolean saveThietBiNhanVienBatch(Long thietBiId, List<Map<String, Object>> thietBiNhanVienList)
            throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            params.add(new SqlParameter("P_JSON", JsonConvert.serialize(thietBiNhanVienList), ParameterDirection.INPUT,
                    Types.CLOB));
        } catch (Exception e) {
            throw new AppSqlException(e);
        }
        params.add(new SqlParameter("P_THIETBI_ID", thietBiId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));

        SqlParameter result = new SqlParameter("P_RES", null, ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_VATLY.SP_MULTI_BATCH_THBI_NV", params);

        BigDecimal rs = (BigDecimal) result.getOutValue();
        return rs != null && rs.intValue() == 1;
    }

    public List<Map<String, Object>> getThietBiNhanVien(Long thietBiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_THIETBI_ID", thietBiId, ParameterDirection.INPUT, Types.NUMERIC));

        SqlParameter cursor = new SqlParameter("P_CURSOR", null, ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(cursor);

        List<Map<String, Object>> dbResult = dbContext
                .executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_GET_THIETBI_NHANVIEN", params);

        return dbResult;
    }

    public Object saveTaiSanThietBi(TaiSanThietBiRequestDTO dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_THIET_BI_ID", dto.getThietBiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MA_DA", dto.getMaDa(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TEN_DA", dto.getTenDa(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SO_THE_TS", dto.getSoTheTs(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TAI_SAN_ID", dto.getTaiSanId(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MA_VTU", dto.getMaVtu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGAY_KH", dto.getNgayKh(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_NGAY_BAO_HANH", dto.getNgayBaoHanh(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_NGAY_LAP_DAT", dto.getNgayLapDat(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_NGAY_SU_DUNG", dto.getNgaySuDung(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_DON_VI_QLY_ID", dto.getDonViQlyId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("P_PHONG_BAN_QLY_ID", dto.getPhongBanQlyId(), ParameterDirection.INPUT,
                        Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RES", null, ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap(
                "ECMS.PKG_IDC_THIETBI_VATLY.SP_SAVE_THIETBI_TAISAN",
                params);
        return result.get("item");
    }

    public Map<String, Object> getThietBiById(Long thietBiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_THIETBI_ID", thietBiId, ParameterDirection.INPUT, Types.NUMERIC));

        SqlParameter cursor = new SqlParameter("P_CURSOR", null, ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(cursor);

        Map<String, Object> dbResult = dbContext
                .executeSpWithCursorToMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_GET_THIETBI_TAISAN", params);

        return normalizeKeys(dbResult);
    }

    public Map<String, Object> getIDCLapDat(Long thietBiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_THIETBI_ID", thietBiId, ParameterDirection.INPUT, Types.NUMERIC));
        SqlParameter cursor = new SqlParameter("P_RESULT", null, ParameterDirection.OUTPUT, Types.REF_CURSOR);
        params.add(cursor);

        Map<String, Object> dbResult = dbContext
                .executeSpWithCursorToMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_GET_IDC_LAPDAT_THIETBI", params);
        return normalizeKeys(dbResult);
    }

    private Map<String, Object> normalizeKeys(Map<String, Object> dbResult) {
        if (dbResult == null) {
            return new LinkedHashMap<>();
        }

        return dbResult.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(
                        e -> e.getKey().toLowerCase(),
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new));
    }

}
