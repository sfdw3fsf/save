package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.ViTriLapDatDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ViTriLapDatTBService {
    private final DbContext dbContext;

    public ViTriLapDatTBService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    // get vi tri cac thiet bi cntt : network , server,...
    public Object getViTriLapDatTB(Long thietBiId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id",thietBiId, Types.NUMERIC));
        SqlParameter out = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(out);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_VATLY.SP_GET_VITRILAPDAT_THIETBI", params);
        return mapFirstRow(out.getOutValue());
    }
    //get vi tri cac thiet bi dien: tu trung the,ups,..
    public Object getViTriLapDatTBDien(Long thietBiId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", thietBiId, Types.NUMERIC));
        SqlParameter out = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(out);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_VATLY.SP_GET_VITRILAPDAT_THIETBI_DIEN", params);
        return mapFirstRow(out.getOutValue());
    }
    public Object getViTriLapDatTuDia(Long thietBiId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", thietBiId, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        SqlParameter out = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(out);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_VATLY.SP_GET_VITRILAPDAT_TBTUDIA", params);
        return out.getOutValue();
    }
    public ApiResult updateViTriLapDat(Long thietBiId,ViTriLapDatDto dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_thietbi_id", thietBiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_toanha_id", dto.getToaNhaId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_matsan_id", dto.getMatSanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phong_id", dto.getPhongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rack_id", dto.getRackId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_u_tren",dto.getuTren(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_u_duoi", dto.getuDuoi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_mattruoc", dto.isMatTruoc() ? 1 : 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_matsau", dto.isMatSau() ? 1 : 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_canhtrai", dto.isCanhTrai()? 1 : 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_canhphai", dto.isCanhPhai() ? 1 : 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_nguoicn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));


        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        try {
            dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_VATLY.SP_UPDATE_VITRILAPDAT_THIETBI", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }

        ApiResult apiResult = new ApiResult();
        String errCodeVal = errorCode.getOutValue() != null ? (String) errorCode.getOutValue() : "BSS_00000000";
        String errMsgVal = errorMessage.getOutValue() != null ? errorMessage.getOutValue().toString() : "Unknown error";
        Number resultVal = result.getOutValue() != null ? (Number) result.getOutValue() : -1;

        apiResult.setErrorCode(errCodeVal);
        apiResult.setMessage(errMsgVal);
        apiResult.setData(resultVal);

        return apiResult;
    }
    public ApiResult updateViTriLapDatThietBiDien(Long thietBiId,ViTriLapDatDto dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_thietbi_id", thietBiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_toanha_id", dto.getToaNhaId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_matsan_id", dto.getMatSanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phong_id", dto.getPhongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_toado_x",dto.getToaDoX(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_toado_y", dto.getToaDoY(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_nguoicn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));


        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        try {
            dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_VATLY.SP_UPDATE_VITRILAPDAT_THIETBI_DIEN", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }

        ApiResult apiResult = new ApiResult();
        String errCodeVal = errorCode.getOutValue() != null ? (String) errorCode.getOutValue() : "BSS_00000000";
        String errMsgVal = errorMessage.getOutValue() != null ? errorMessage.getOutValue().toString() : "Unknown error";
        Number resultVal = result.getOutValue() != null ? (Number) result.getOutValue() : -1;

        apiResult.setErrorCode(errCodeVal);
        apiResult.setMessage(errMsgVal);
        apiResult.setData(resultVal);

        return apiResult;
    }

    private Object mapFirstRow(Object outVal) {
        if (outVal instanceof List<?>) {
            List<?> rows = (List<?>) outVal;
            if (!rows.isEmpty() && rows.get(0) instanceof Map<?, ?>) {
                return rows.get(0);
            }
        }
        return null;
    }
}