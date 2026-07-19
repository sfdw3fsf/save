package vn.vnpt.vnptit.ecms.service.idc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.UpsertModuleDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DanhMucModuleService {

    private final DbContext dbContext;

    public DanhMucModuleService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    // ===========================================
    // GET LIST
    // ===========================================
    public Object getAll() throws AppSqlException {

        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter(
                "p_cursor",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_MODULE_LIST",
                params
        );
    }

    // ===========================================
    // GET DETAIL
    // ===========================================
    public Object getDetail(Long id) throws AppSqlException {

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));

        SqlParameter cursor = new SqlParameter("p_cursor", Types.REF_CURSOR);
        params.add(cursor);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_MODULE_BY_ID",
                params
        );

        return mapFirstRow(cursor.getOutValue());
    }

    // ===========================================
    // INSERT / UPDATE
    // ===========================================
    public ApiResult insertOrUpdate(UpsertModuleDto dto) throws AppSqlException {

        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_tentat", dto.getTenTat(), Types.VARCHAR));
        params.add(new SqlParameter("p_bangthong",dto.getBangThong(), Types.NUMERIC));
        params.add(new SqlParameter("p_mota", dto.getMoTa(), Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getHieuLuc(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), Types.VARCHAR));
        params.add(new SqlParameter(
                "p_nguoicn",
                AppRequestContext.getCurrent().getUserName(),
                Types.VARCHAR
        ));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_TAINGUYEN_V2.SP_INSERT_UPDATE_MODULE",
                params
        );

        return KhoHaTangMangService.getApiResult(result, errorCode, errorMessage);
    }

    // ===========================================
    // DELETE SINGLE
    // ===========================================
    public ApiResult delete(Long id) throws AppSqlException {

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_TAINGUYEN_V2.SP_DELETE_MODULE",
                params
        );

        return KhoHaTangMangService.getApiResult(result, errorCode, errorMessage);
    }

    // ===========================================
    // DELETE MULTI
    // ===========================================
    public ApiResult deleteMulti(String listId) throws AppSqlException {

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_list_id", listId, Types.VARCHAR));

        SqlParameter success = new SqlParameter("p_success", Types.NUMERIC);
        SqlParameter failed  = new SqlParameter("p_failed", Types.NUMERIC);
        SqlParameter errList = new SqlParameter("p_err_list", Types.VARCHAR);

        params.add(success);
        params.add(failed);
        params.add(errList);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_TAINGUYEN_V2.SP_DELETE_MULTI_MODULE",
                params
        );

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", success.getOutValue());
        resultMap.put("failed", failed.getOutValue());
        resultMap.put("errorIds", errList.getOutValue());

        ApiResult apiResult = new ApiResult();
        apiResult.setData(resultMap);

        int sVal = toInt(success.getOutValue());
        int fVal = toInt(failed.getOutValue());

        if (sVal == 0) {
            apiResult.setErrorCode("BSS-DELETE_ERR_MULTI");
            apiResult.setMessage("Không có bản ghi nào được xoá.");
        } else if (fVal > 0) {
            apiResult.setErrorCode("BSS-PARTIAL_SUCCESS");
            apiResult.setMessage("Xóa thành công một phần.");
        } else {
            apiResult.setErrorCode("BSS-00000000");
            apiResult.setMessage("Xóa thành công toàn bộ.");
        }

        return apiResult;
    }

    // ===========================================
    // CHECK TRÙNG TÊN
    // ===========================================
    public boolean checkTenTonTai(Long id, String ten) throws AppSqlException {

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", ten, Types.VARCHAR));

        SqlParameter exist = new SqlParameter("p_exist", Types.NUMERIC);
        params.add(exist);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_TAINGUYEN_V2.SP_CHECK_MODULE_TEN",
                params
        );

        return toInt(exist.getOutValue()) > 0;
    }

    // ===========================================
    // CHECK TRÙNG TÊN TẮT
    // ===========================================
    public boolean checkTenTatTonTai(Long id, String tenTat) throws AppSqlException {

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_tentat", tenTat, Types.VARCHAR));

        SqlParameter exist = new SqlParameter("p_exist", Types.NUMERIC);
        params.add(exist);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_TAINGUYEN_V2.SP_CHECK_MODULE_TENTAT",
                params
        );

        return toInt(exist.getOutValue()) > 0;
    }

    // ===========================================
    // VALIDATE LIST
    // ===========================================
    public ApiResult checkListModule(List<UpsertModuleDto> list)
            throws AppSqlException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(list);

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_json", json, Types.CLOB));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", Types.VARCHAR);
        SqlParameter errorMsg = new SqlParameter("p_err_msg", Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMsg);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_TAINGUYEN_V2.SP_VALIDATE_LIST_MODULE",
                params
        );

        ApiResult apiResult = new ApiResult();
        apiResult.setErrorCode((String) errorCode.getOutValue());
        apiResult.setMessage((String) errorMsg.getOutValue());
        apiResult.setMessageDetail((String) errorMsg.getOutValue());
        apiResult.setData(result.getOutValue());

        return apiResult;
    }

    // ===========================================
    // Helpers
    // ===========================================
    private Object mapFirstRow(Object cursorValue) {
        if (cursorValue instanceof List) {
            List<?> list = (List<?>) cursorValue;
            if (!list.isEmpty()) return list.get(0);
        }
        return null;
    }

    private int toInt(Object obj) {
        return obj == null ? 0 : ((Number) obj).intValue();
    }
}