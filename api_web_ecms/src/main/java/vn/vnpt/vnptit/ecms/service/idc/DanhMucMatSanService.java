package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucMatSanDtoIn;

@Service
public class DanhMucMatSanService {
    private final DbContext dbContext;

    public DanhMucMatSanService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getMatSanDetail(Long matSanId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_matsan_id", matSanId, Types.NUMERIC));
        SqlParameter out = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(out);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_GET_MAT_SAN_BY_ID", params);
        return mapFirstRow(out.getOutValue());
    }
    public Object getAll() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        // params.add(new SqlParameter("p_validity", validity != null ? validity : null,
        // validity !=null ? Types.NUMERIC : Types.NULL));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_DANHSACH_MAT_SAN", params);
    }

    public Object getByToaNha(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_MAT_SAN_BY_TOANHA", params);
    }

    public ApiResult insertOrUpdate(DanhMucMatSanDtoIn dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_id_toanha", dto.getIdToaNha(), Types.NUMERIC));
        params.add(new SqlParameter("p_dientich", dto.getDienTich(), Types.NUMERIC));
        params.add(new SqlParameter("p_chieudai", dto.getChieuDai(), Types.NUMERIC));
        params.add(new SqlParameter("p_chieurong", dto.getChieuRong(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.VARCHAR));
        params.add(new SqlParameter("p_ten_tat", dto.getTenTat(), Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMoTa(), Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSuDung(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
        params.add(new SqlParameter("p_hinhanh", dto.getHinhAnh(), Types.VARCHAR));
        // NEW: trạng thái vận hành
        params.add(new SqlParameter("p_trangthai_vh", dto.getTrangThaiVH(), Types.NUMERIC));

        // NEW: ngày sử dụng (DATE). Nếu dto.getNgaySd() là java.util.Date:
        java.sql.Date sqlNgaySd = null;
        if (dto.getNgaySd() != null) {
            sqlNgaySd = new java.sql.Date(dto.getNgaySd().getTime());
        }
        params.add(new SqlParameter("p_ngay_sd", sqlNgaySd, Types.DATE));
        params.add(new SqlParameter("p_vi_tri", dto.getViTri(), Types.VARCHAR));
        params.add(new SqlParameter("p_tam_san_rong", dto.getTamSanRong(), Types.NUMERIC));
        params.add(new SqlParameter("p_tam_san_dai", dto.getTamSanDai(), Types.NUMERIC));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_MAT_SAN_UPDATE_INSERT", params);

        ApiResult apiResult = new ApiResult();
        String errCodeVal = errorCode.getOutValue() != null ? (String) errorCode.getOutValue() : "BSS_00000000";
        String errMsgVal = errorMessage.getOutValue() != null ? errorMessage.getOutValue().toString() : "Unknown error";
        Number resultVal = result.getOutValue() != null ? (Number) result.getOutValue() : -1;

        apiResult.setErrorCode(errCodeVal.toString());
        apiResult.setMessage(errMsgVal);
        apiResult.setData(resultVal);
        return apiResult;
    }

    public Object delete(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_MAT_SAN_DELETE", params);
        return result.getOutValue();
    }

    public Object getIDCToaNha() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_DANHSACH_IDC_TOANHA", params);
    }

    public boolean existsByName(String tenMatSan) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten", tenMatSan, Types.VARCHAR));
        params.add(new SqlParameter("p_result", Types.NUMERIC));

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_CHECK_TEN_MAT_SAN", params);

        Number result = (Number) params.get(1).getOutValue();
        return result != null && result.intValue() > 0;
    }
    public boolean existsByTenTat(String tenTat) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten_tat", tenTat, Types.VARCHAR));
        params.add(new SqlParameter("p_result", Types.NUMERIC));

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_CHECK_TEN_TAT_MAT_SAN", params);

        Number result = (Number) params.get(1).getOutValue();
        return result != null && result.intValue() > 0;
    }
    public ApiResult checkListMatSan(List<DanhMucMatSanDtoIn> list) throws AppSqlException, JsonProcessingException {
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

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_VALIDATE_LIST_MATSAN", params);

        ApiResult apiResult = new ApiResult();
        apiResult.setErrorCode((String) errorCode.getOutValue());
        apiResult.setMessage((String) errorMsg.getOutValue());
        apiResult.setMessageDetail((String) errorMsg.getOutValue());
        apiResult.setData(result.getOutValue());
        return apiResult;
    }

    public Object getById(Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_MAT_SAN_BY_ID", params);
    }

    // ======= Không gian

    public Object getByIdc(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_STATISTIC.SP_MAT_SAN_BY_IDC", params);
    }
    private Object mapFirstRow(Object cursorOutValue) {
        if (cursorOutValue instanceof List) {
            List<?> list = (List<?>) cursorOutValue;
            if (!list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }
}