package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

@Service
public class cumhatangIDGchitietService {

    private final DbContext dbContext;

    public cumhatangIDGchitietService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    // ===== COMMON CALL =====
    private Object callSP(String spName, String paramName, Long value) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter(
                paramName,
                value,
                SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC
        ));

        params.add(new SqlParameter(
                "P_RESULT",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(spName, params);
    }

    // ===== BUSINESS =====

    public Object lichSuThayDoi(Long id) throws AppSqlException {
        return callSP(
                "ECMS.PKG_HATANG_IDG_ext.sp_cumhatangchitietIDG_lichsuthaydoi",
                "P_ID",
                id
        );
    }

    public Object hatangMang(Long id) throws AppSqlException {
        return callSP(
                "ECMS.PKG_HATANG_IDG_ext.sp_cumhatangchitietIDG_hatangmang",
                "P_ID",
                id
        );
    }

    public Object hatangMangChitiet(Long cumhtId) throws AppSqlException {
        return callSP(
                "ECMS.PKG_HATANG_IDG_ext.SP_GET_CUMHT_IDG_CHITIET",
                "P_CUMHT_ID",
                cumhtId
        );
    }
}