package vn.vnpt.vnptit.ecms.service.idc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLoaiThietBiHeThongDtoIn;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLoaiThietBiHeThongDtoOut;
import vn.vnpt.vnptit.ecms.dto.idc.MangThietBiHeThongDtoOut;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DanhMucLoaiThietBiHeThongService {
    private final DbContext dbContext;

    public DanhMucLoaiThietBiHeThongService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public List<DanhMucLoaiThietBiHeThongDtoOut> getAll() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));

        List<Map<String, Object>> rows = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN.SP_DS_LOAI_TBI_HTHONG", params
        );

        List<DanhMucLoaiThietBiHeThongDtoOut> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            DanhMucLoaiThietBiHeThongDtoOut dto = new DanhMucLoaiThietBiHeThongDtoOut();
            dto.setId(((BigDecimal) row.get("LOAITBI_ID")).longValue());  // DB NUMBER -> Long
            dto.setTen((String) row.get("LOAI_TBI"));
            dto.setMangThietBiId(((BigDecimal) row.get("NHOMTBI_ID")).longValue());
            dto.setHieuLuc(((BigDecimal) row.get("SUDUNG")).intValue());
            dto.setGhiChu((String) row.get("GHICHU"));
            result.add(dto);
        }

        return result;
    }

    public List<MangThietBiHeThongDtoOut> getMangThietBiHeThong() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));

        List<Map<String, Object>> rows = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN.SP_DS_NHOMTHIETBI", params
        );

        List<MangThietBiHeThongDtoOut> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            MangThietBiHeThongDtoOut dto = new MangThietBiHeThongDtoOut();
            dto.setMangThietBiId(((BigDecimal) row.get("NHOMTBI_ID")).longValue());
            dto.setManThietBiTen((String) row.get("NHOM_TBI"));
            result.add(dto);
        }
        return result;
    }

    public ApiResult insertOrUpdate(DanhMucLoaiThietBiHeThongDtoIn input) throws AppSqlException {
         List<SqlParameter> params = new ArrayList<>();
         params.add(new SqlParameter("p_id", input.getId(), Types.NUMERIC));
         params.add(new SqlParameter("p_loai_tbi", input.getTen(), Types.VARCHAR));
         params.add(new SqlParameter("p_ghi_chu", input.getGhiChu(), Types.NVARCHAR));
         params.add(new SqlParameter("p_nhomtbi", input.getMangThietBiId(), Types.NUMERIC));
         params.add(new SqlParameter("p_sudung", input.getHieuLuc(), Types.NUMERIC));   //trong dbase la s dung mp ra hieu luc cho giong ui
         SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
         SqlParameter errorCode = new SqlParameter("p_err_code", Types.VARCHAR);
         SqlParameter errorMessage = new SqlParameter("p_err_msg", Types.VARCHAR);

         params.add(result);
         params.add(errorCode);
         params.add(errorMessage);

         dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_INSERT_UPDATE_LOAI_TBI_HTHONG", params);

         ApiResult apiResult = new ApiResult();
         String errCodeVal = errorCode.getOutValue() != null ? (String) errorCode.getOutValue() : "BSS_00000000";
         String errMsgVal = errorMessage.getOutValue() != null ? errorMessage.getOutValue().toString() : "Unknown error";
         Number resultVal = result.getOutValue() != null ? (Number) result.getOutValue() : -1;

         apiResult.setErrorCode(errCodeVal);
         apiResult.setMessage(errMsgVal);
         apiResult.setData(resultVal);
         return apiResult;
    }
    public ApiResult delete(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure(
                "ECMS.PKG_IDC_TAINGUYEN.SP_LOAI_TBI_DELETE", params
        );

        int code = Integer.parseInt(result.getOutValue().toString());
        ApiResult res = new ApiResult();

        switch (code) {
            case -1:
                res.setError("409");
                res.setErrorCode("BSS-REF-EXISTS");
                res.setMessage("Không thể xoá: loại thiết bị đã được tham chiếu");
                res.setData(null);
                break;
            case 0:
                res.setError("404");
                res.setErrorCode("BSS-NOT-FOUND");
                res.setMessage("Không tìm thấy loại thiết bị cần xoá");
                res.setData(null);
                break;
            case 1:
                res.setError("200");
                res.setErrorCode("BSS-00000000");
                res.setMessage("Xoá thành công");
                res.setData(id);
                break;
            case -2:
            default:
                res.setError("500");
                res.setErrorCode("BSS-UNKNOWN-ERR");
                res.setMessage("Có lỗi xảy ra trong quá trình xoá");
                res.setData(null);
                break;
        }

        return res;
    }

    public boolean existsByName(String tenLoaiThietBiHeThong) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten", tenLoaiThietBiHeThong, Types.VARCHAR));
        params.add(new SqlParameter("p_result", Types.NUMERIC));

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_CHECK_LOAITHIETBI", params);

        Number result = (Number) params.get(1).getOutValue();
        return result != null && result.intValue() > 0;
    }
    public ApiResult checkListThietBi(List<DanhMucLoaiThietBiHeThongDtoIn> list) throws JsonProcessingException, AppSqlException {
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
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_VALIDATE_LIST_LOAI_THIETBI", params);

        ApiResult apiResult = new ApiResult();
        apiResult.setErrorCode((String) errorCode.getOutValue());
        apiResult.setMessage((String) errorMsg.getOutValue());
        apiResult.setMessageDetail((String) errorMsg.getOutValue());
        apiResult.setData(result.getOutValue());
        return apiResult;
    }
}