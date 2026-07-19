package vn.vnpt.vnptit.ecms.service.idc;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KhoHaTangMangService {

    private final DbContext dbContext;

    public KhoHaTangMangService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    // -----------------------------
    // GET DETAIL HTM
    // -----------------------------
    public Object getDetail(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        SqlParameter cursor = new SqlParameter("p_cursor", Types.REF_CURSOR);
        params.add(cursor);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_HATANGMANG.SP_GET_HATANG_BY_ID",
                params
        );
        return mapFirstRow(cursor.getOutValue());
    }

    // -----------------------------
    // GET LIST HTM
    // -----------------------------
    public Object getAll(SearchHaTangMangDto dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.VARCHAR));
        params.add(new SqlParameter("p_idc_id", dto.getIdcId(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_ccdv_id", dto.getTrangThaiCCDVId(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_quanly_id", dto.getDonViQuanLyId(), Types.NUMERIC));
        params.add(new SqlParameter("p_phong_quanly_id", dto.getPhongQuanLyId(), Types.NUMERIC));

        params.add(new SqlParameter(
                "p_result",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANGMANG.SP_DANHSACH_HATANG",
                params
        );
    }


    // -----------------------------
    // INSERT / UPDATE HTM
    // -----------------------------
    public ApiResult insertOrUpdate(UpsertHaTangMangDto dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_mahatang", dto.getMaHaTangMang(), Types.VARCHAR));
        params.add(new SqlParameter("p_idc_id", dto.getIdcId(), Types.NUMERIC));
        params.add(new SqlParameter("p_congnghe_id", dto.getCongNgheId(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_ccdv_id", dto.getTrangThaiCCDVId(), Types.NUMERIC));
        params.add(new SqlParameter("p_hieuluc", dto.getHieuLuc(), Types.NUMERIC));
        params.add(new SqlParameter("p_donviql_id", dto.getDonViQuanLyId(), Types.NUMERIC));
        params.add(new SqlParameter("p_phongbanql_id", dto.getPhongQuanLyId(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoicn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), Types.VARCHAR));
        params.add(new SqlParameter("p_hinhanh", dto.getHinhAnh(), Types.VARCHAR));

        // OUT params
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_HATANGMANG.SP_HATANG_UPDATE_INSERT",
                params
        );

        return getApiResult(result, errorCode, errorMessage);
    }


    // -----------------------------
    // DELETE ONE
    // -----------------------------
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
                "ECMS.PKG_IDC_HATANGMANG.SP_HATANG_DELETE",
                params
        );

        return getApiResult(result, errorCode, errorMessage);
    }


    // -----------------------------
    // DELETE MULTI
    // -----------------------------
    public ApiResult deleteMulti(String listId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_list_id", listId, Types.VARCHAR));

        SqlParameter success = new SqlParameter("p_success", Types.NUMERIC);
        SqlParameter failed = new SqlParameter("p_failed", Types.NUMERIC);
        SqlParameter errList = new SqlParameter("p_err_list", Types.VARCHAR);

        params.add(success);
        params.add(failed);
        params.add(errList);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_HATANGMANG.SP_HATANG_DELETE_MULTI",
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


    // -----------------------------
    // GET SUBNET BY HTM
    // -----------------------------
    public Object getSubnetByHaTangMangId(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangmang_id", id, Types.NUMERIC));

        params.add(new SqlParameter(
                "p_cursor",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR
        ));


        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANGMANG.SP_GET_SUBNET_BY_HATANGMANG_ID",
                params
        );
    }

    //LẤY DANH SÁCH NHÂN VIÊN HTM
    public Object getNhanVienByHtmId(Long htmId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_htm_id", htmId, Types.NUMERIC));

        SqlParameter cursor = new SqlParameter("p_cursor", Types.REF_CURSOR);
        params.add(cursor);
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANGMANG.SP_GET_HTM_NHANVIEN",
                params
        );
    }

    //  LƯU DANH SÁCH NHÂN VIÊN THEO HTM
    public ApiResult saveNhanVienForHtm(Long htmId, List<NhanVienHTMItem> employees) throws AppSqlException {

        String json = new Gson().toJson(employees);

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_htm_id", htmId, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_json", json, Types.CLOB));
        params.add(new SqlParameter("p_nguoicn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        SqlParameter errCode = new SqlParameter("p_err_code", Types.VARCHAR);
        SqlParameter errMsg = new SqlParameter("p_err_msg", Types.VARCHAR);

        params.add(result);
        params.add(errCode);
        params.add(errMsg);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_HATANGMANG.SP_SAVE_HTM_NHANVIEN", params);
        return getApiResult(result, errCode, errMsg);
    }

    // -----------------------------
    // CHECK TÊN HẠ TẦNG MẠNG TỒN TẠI
    // -----------------------------
    public ApiResult checkTenHaTangTonTai(Long id, String ten) throws AppSqlException {

        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", ten, Types.NVARCHAR));

        SqlParameter exists = new SqlParameter("p_exists", Types.NUMERIC);
        SqlParameter errCode = new SqlParameter("p_err_code", Types.VARCHAR);
        SqlParameter errMsg  = new SqlParameter("p_err_msg", Types.VARCHAR);

        params.add(exists);
        params.add(errCode);
        params.add(errMsg);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_HATANGMANG.SP_CHECK_TEN_HATANG_TONTAI",
                params
        );

        return getApiResult(exists, errCode, errMsg);
    }

    // -----------------------------
    // CHECK ma ha tang da ton tai
    // -----------------------------
    public ApiResult checkMaHaTangTonTai(Long id, String maHaTang) throws AppSqlException {

        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_mahatang", maHaTang, Types.VARCHAR));

        SqlParameter exists = new SqlParameter("p_exists", Types.NUMERIC);
        SqlParameter errCode = new SqlParameter("p_err_code", Types.VARCHAR);
        SqlParameter errMsg  = new SqlParameter("p_err_msg", Types.VARCHAR);

        params.add(exists);
        params.add(errCode);
        params.add(errMsg);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_HATANGMANG.SP_CHECK_MA_HATANG_TONTAI",
                params
        );

        return getApiResult(exists, errCode, errMsg);
    }
    //get ip address by htm id
    public Object getIpAddressByHtmId(Long htmId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_htm_id", htmId, Types.NUMERIC));

        SqlParameter cursor = new SqlParameter("p_cursor", Types.REF_CURSOR);
        params.add(cursor);
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANGMANG.SP_GET_IPADDRESS_BY_HTM_ID",
                params
        );
    }
    public Object getThietBiMangByIdcHtm(Long htmId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_htm_id",htmId, Types.NUMERIC));

        params.add(new SqlParameter(
                "p_cursor",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANGMANG.SP_GET_THIETBIMANG_BY_IDC_HTM",
                params
        );
    }

    public Object getThietBiMangByHaTangMang(Long htmId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_htm_id", htmId, Types.NUMERIC));

        params.add(new SqlParameter(
                "p_cursor",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANGMANG.SP_GET_THIETBIMANG_BY_HATANGMANG",
                params
        );
    }
    public ApiResult updateThietBiHaTangMang(UpdateThietBiHaTangMangDto dto) throws AppSqlException {

        // Convert List<Long> -> JSON "[1,2,3]"
        String json = new Gson().toJson(dto.getThietBiIds());

        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_thietbi_json", json, Types.CLOB));
        params.add(new SqlParameter("p_htm_id", dto.getHaTangMangId(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoicn",AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));

        // OUT params
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_HATANGMANG.SP_UPDATE_THIETBI_HATANGMANG_MULTI",
                params
        );

        return getApiResult(result, errorCode, errorMessage);
    }

    public Object getLichSuThietBiHaTangMang(Long htmId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_htm_id", htmId, Types.NUMERIC));

        SqlParameter cursor = new SqlParameter("p_cursor", Types.REF_CURSOR);
        params.add(cursor);
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANGMANG.SP_GET_LOG_THIETBI_HATANGMANG",
                params
        );
    }

    // -----------------------------
    // Helpers
    // -----------------------------
    private Object mapFirstRow(Object cursorValue) {
        if (cursorValue instanceof List) {
            List<?> list = (List<?>) cursorValue;
            if (!list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }

    private int toInt(Object obj) {
        return obj == null ? 0 : ((Number) obj).intValue();
    }

    public static ApiResult getApiResult(SqlParameter result, SqlParameter errorCode, SqlParameter errorMessage) {
        ApiResult apiResult = new ApiResult();
        apiResult.setErrorCode(errorCode.getOutValue() != null ? errorCode.getOutValue().toString() : "BSS-00000000");
        apiResult.setMessage(errorMessage.getOutValue() != null ? errorMessage.getOutValue().toString() : "");
        apiResult.setData(result.getOutValue());
        return apiResult;
    }

}