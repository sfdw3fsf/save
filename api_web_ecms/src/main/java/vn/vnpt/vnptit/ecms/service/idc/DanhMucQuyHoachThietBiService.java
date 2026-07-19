package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucQuyHoachThietBiBatchResponseDto;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucQuyHoachThietBiDto;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucQuyHoachThietBiValidateResponseDto;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class DanhMucQuyHoachThietBiService {

    private final DbContext dbContext;

    public DanhMucQuyHoachThietBiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getAllQuyHoachThietBi() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_ALL_QUYHOACH_DEVICE", params);
    }

    public Object upserQuyHoachThietBi(DanhMucQuyHoachThietBiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangThaiId(), Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", dto.getTen(), Types.VARCHAR));
        params.add(new SqlParameter("P_TENTAT", dto.getTenTat(), Types.VARCHAR));
        params.add(new SqlParameter("P_MOTA", dto.getMoTa(), Types.VARCHAR));
        params.add(new SqlParameter("P_SUDUNG", dto.getSuDung(), Types.VARCHAR));
        params.add(new SqlParameter("P_GHICHU", dto.getGhiChu(), Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGAY_CN", new java.sql.Date(System.currentTimeMillis()),
                ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_ID_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_INSERT_QUYHOACH_DEVICE", params);
    }

    public Object deleteQuyHoachThietBi(DanhMucQuyHoachThietBiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangThaiId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_DELETE_QUYHOACH_DEVICE", params);
    }

    public DanhMucQuyHoachThietBiBatchResponseDto batchInsertQuyHoach(List<DanhMucQuyHoachThietBiDto> danhSachQuyHoach)
            throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSachQuyHoach);
            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(
                    new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(
                    new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(
                    new SqlParameter("p_success_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(
                    new SqlParameter("p_failed_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_QUYHOACH_TBI_BATCH_INSERT", parameters);

            // Lấy giá trị từ output parameters
            Integer successCount = null;
            Integer failedCount = null;
            Integer result = null;

            for (SqlParameter param : parameters) {
                if ("p_success_count".equals(param.getName()) && param.getOutValue() != null) {
                    successCount = Integer.valueOf(String.valueOf(param.getOutValue()));
                }
                if ("p_failed_count".equals(param.getName()) && param.getOutValue() != null) {
                    failedCount = Integer.valueOf(String.valueOf(param.getOutValue()));
                }
                if ("p_result".equals(param.getName()) && param.getOutValue() != null) {
                    result = Integer.valueOf(String.valueOf(param.getOutValue()));
                }
            }

            return new DanhMucQuyHoachThietBiBatchResponseDto(successCount, failedCount, result);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

    public DanhMucQuyHoachThietBiValidateResponseDto validateBatchQuyHoachThietBi(
            List<DanhMucQuyHoachThietBiDto> danhSachQuyHoachThietBi) throws AppSqlException {
        try {
            String jsonData = JsonConvert.serialize(danhSachQuyHoachThietBi);

            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(
                    new SqlParameter("p_json_data", jsonData, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            parameters.add(
                    new SqlParameter("p_error_count", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
            parameters.add(
                    new SqlParameter("p_error_message", null, SqlParameter.ParameterDirection.OUTPUT, Types.NVARCHAR));

            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_QUYHOACH_TBI_VALIDATE_BATCH", parameters);

            // Lấy giá trị từ output parameters
            Integer errorCount = null;
            String errorMessage = null;

            for (SqlParameter param : parameters) {
                if ("p_error_count".equals(param.getName()) && param.getOutValue() != null) {
                    errorCount = Integer.valueOf(String.valueOf(param.getOutValue()));
                }
                if ("p_error_message".equals(param.getName()) && param.getOutValue() != null) {
                    errorMessage = String.valueOf(param.getOutValue());
                }
            }

            return new DanhMucQuyHoachThietBiValidateResponseDto(errorCount, errorMessage);

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }
}
