package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DichVuCnttDTO;

import java.sql.Types;
import java.util.*;

@Service
public class DichVuCnttService {

  private final DbContext dbContext;
  public  DichVuCnttService(DbContext dbContext){
    this.dbContext = dbContext;
  }
  /**
   * Thêm mới dịch vụ CNTT
   */
  public Object insert(DichVuCnttDTO dto) throws AppSqlException {
    List<SqlParameter> params = new ArrayList<>();
    params.add(new SqlParameter("P_TEN", dto.getTen(), Types.VARCHAR));
    params.add(new SqlParameter("P_TENTAT", dto.getTenTat(), Types.VARCHAR));
    params.add(new SqlParameter("P_MOTA", dto.getMoTa(), Types.VARCHAR));
    params.add(new SqlParameter("P_SUDUNG", dto.getSuDung(), Types.NUMERIC));
    params.add(new SqlParameter("P_GHICHU", dto.getGhiChu(), Types.VARCHAR));
    params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
    params.add(new SqlParameter("p_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

    return dbContext.executeSpWithCursorToMap(
            "ECMS.PKG_IDC_TAINGUYEN_V2.SP_DICHVU_CNTT_INSERT",
            params
    );
  }

  /**
   * Cập nhật dịch vụ CNTT
   */
  public Object update(DichVuCnttDTO dto) throws AppSqlException {
    List<SqlParameter> params = new ArrayList<>();
    params.add(new SqlParameter("p_dichvu_cntt_id", dto.getId(), Types.NUMERIC));
    params.add(new SqlParameter("P_TEN", dto.getTen(), Types.VARCHAR));
    params.add(new SqlParameter("P_TENTAT", dto.getTenTat(), Types.VARCHAR));
    params.add(new SqlParameter("P_MOTA", dto.getMoTa(), Types.VARCHAR));
    params.add(new SqlParameter("P_SUDUNG", dto.getSuDung(), Types.NUMERIC));
    params.add(new SqlParameter("P_GHICHU", dto.getGhiChu(), Types.VARCHAR));
    params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
    params.add(new SqlParameter("p_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

    return dbContext.executeSpWithCursorToListMap(
            "ECMS.PKG_IDC_TAINGUYEN_V2.SP_DICHVU_CNTT_UPDATE",
            params
    );
  }
  /**
   * Xóa dịch vụ CNTT (Hard Delete)
   */
  public Object delete(Integer id) throws AppSqlException {
    List<SqlParameter> params = new ArrayList<>();
    params.add(new SqlParameter("p_dichvu_cntt_id", id, Types.NUMERIC));
    params.add(new SqlParameter("P_RESULT", Types.NUMERIC));
    params.add(new SqlParameter("P_MESSAGE", Types.VARCHAR));

    return dbContext.executeSpWithCursorToListMap(
            "ECMS.PKG_IDC_TAINGUYEN_V2.SP_DICHVU_CNTT_DELETE",
            params
    );
  }

  /**
   * Lấy tất cả dịch vụ CNTT
   */
  public Object getAll() throws AppSqlException {
    List<SqlParameter> params = new ArrayList<>();
    params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));

    return dbContext.executeSpIdxWithCursorToListMap(
            "ECMS.PKG_IDC_TAINGUYEN_V2.SP_DICHVU_CNTT_GET_ALL",
            params
    );

  }
  public Map<String, Object> batchInsertDanhMucDichVuCungCap(List<Map<String, Object>> danhSachIdg) throws AppSqlException {
    try {
      String jsonData = JsonConvert.serialize(danhSachIdg);

      ArrayList<SqlParameter> params = new ArrayList<>();
      params.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
      params.add(new SqlParameter("p_nguoi_cn",
              AppRequestContext.getCurrent().getUserName(),
              SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

      params.add(new SqlParameter("p_success_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_failed_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

      dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DANHMUC_DICHVU_CUNGCAP_MULTIPLE_INSERT", params);

      Integer successCount = 0;
      Integer failedCount = 0;
      Integer result = 0;

      for (SqlParameter p : params) {
        if ("p_success_count".equalsIgnoreCase(p.getName()) && p.getOutValue() != null)
          successCount = Integer.valueOf(String.valueOf(p.getOutValue()));

        if ("p_failed_count".equalsIgnoreCase(p.getName()) && p.getOutValue() != null)
          failedCount = Integer.valueOf(String.valueOf(p.getOutValue()));

        if ("p_result".equalsIgnoreCase(p.getName()) && p.getOutValue() != null)
          result = Integer.valueOf(String.valueOf(p.getOutValue()));
      }

      Map<String, Object> response = new LinkedHashMap<>();
      response.put("successCount", successCount);
      response.put("failedCount", failedCount);
      response.put("result", result);

      return response;

    } catch (Exception e) {
      throw new AppSqlException(e);
    }
  }

  public Map<String, Object> validateBatchDanhMucDichVuDichVuCungCap(List<Map<String, Object>> danhSachIdg) throws AppSqlException {
    try {
      String jsonData = JsonConvert.serialize(danhSachIdg);

      ArrayList<SqlParameter> params = new ArrayList<>();
      params.add(new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
      params.add(new SqlParameter("p_error_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_error_message", null, SqlParameter.ParameterDirection.OUTPUT, Types.NVARCHAR));

      dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_DANHMUC_DICHVU_CUNGCAP_VALIDATE_BATCH", params);

      Integer errorCount = 0;
      String errorMessage = "";

      for (SqlParameter p : params) {
        if ("p_error_count".equalsIgnoreCase(p.getName()) && p.getOutValue() != null)
          errorCount = Integer.valueOf(String.valueOf(p.getOutValue()));

        if ("p_error_message".equalsIgnoreCase(p.getName()) && p.getOutValue() != null)
          errorMessage = String.valueOf(p.getOutValue());
      }

      Map<String, Object> response = new LinkedHashMap<>();
      response.put("errorCount", errorCount);
      response.put("errorMessage", errorMessage);

      return response;

    } catch (Exception e) {
      throw new AppSqlException(e);
    }
  }
}

