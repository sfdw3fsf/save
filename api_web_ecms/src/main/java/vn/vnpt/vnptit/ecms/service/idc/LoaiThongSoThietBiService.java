package vn.vnpt.vnptit.ecms.service.idc;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiThongSoThietBiDto;

@Service
public class LoaiThongSoThietBiService {

    @Autowired
    private DbContext dbContext;

    /**
     * Lấy danh sách tất cả loại thông số thiết bị
     */
    public ArrayList<Map<String, Object>> getListLoaiThongSoThietBi() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_LOAI_THONGSO_TB", params);
    }

    /**
     * Lấy danh sách loại thiết bị cho dropdown
     */
    public ArrayList<Map<String, Object>> getListLoaiThietBi() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_LOAI_TBI_SUDUNG", params);
    }

    /**
     * Lấy danh sách trạng thái vận hành
     */
    public ArrayList<Map<String, Object>> getListTrangThaiVanHanh() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_TRANGTHAI_VH", params);
    }

    /**
     * Lưu (tạo mới hoặc cập nhật) loại thông số thiết bị
     */
    public Object saveLoaiThongSoThietBi(LoaiThongSoThietBiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        SqlParameter p_id = new SqlParameter("P_ID", dto.getIdLoaiThongSoTB(), Types.NUMERIC);
        params.add(p_id);
        params.add(new SqlParameter("P_LOAITBI_ID", dto.getIdLoaiTB(), Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", dto.getTenLoaiThongSoTB(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_GIATRI", dto.getGiaTriDinhDanh(), Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_VH_ID", dto.getTrangThaiVanHanhId(), Types.NUMERIC));
        params.add(new SqlParameter("P_GHI_CHU", dto.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SUDUNG", dto.getHieuLuc(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MOTA", dto.getMoTa(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TENTAT", dto.getTenTat(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_SAVE_LOAI_THONGSO_TB", params);
        return p_id.getOutValue();
    }

    /**
     * Lưu nhiều bản ghi từ file upload
     */
    public Object saveByFile(List<Map<String, Object>> jsonDataList) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            params.add(new SqlParameter("P_JSON", JsonConvert.serialize(jsonDataList), ParameterDirection.INPUT,
                    Types.VARCHAR));
        } catch (Exception e) {
            throw new AppSqlException(e);
        }

        SqlParameter valid_rows = new SqlParameter("P_VALID_ROWS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        SqlParameter invalid_rows = new SqlParameter("P_INVALID_ROWS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        params.add(valid_rows);
        params.add(invalid_rows);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_SAVE_MULTI_LOAI_THONGSO_TB", params);

        return new HashMap<String, Object>() {
            {
                put("validRows", valid_rows.getOutValue());
                put("invalidRows", invalid_rows.getOutValue());
            }
        };
    }

    /**
     * Validate file trước khi upload
     */
    public Map<String, Object> validateByFile(List<Map<String, Object>> jsonDataList) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        try {
            params.add(new SqlParameter("P_JSON", JsonConvert.serialize(jsonDataList), ParameterDirection.INPUT,
                    Types.VARCHAR));
        } catch (Exception e) {
            throw new AppSqlException(e);
        }

        SqlParameter invalidRows = new SqlParameter("P_INVALID_ROWS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.NUMERIC);
        SqlParameter errors = new SqlParameter("P_ERROR_MESSAGE", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.VARCHAR);

        params.add(invalidRows);
        params.add(errors);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_CHECK_SAVE_MULTI_LOAI_THONGSO_TB", params);

        return new HashMap<String, Object>() {
            {
                put("invalidRows", invalidRows.getOutValue());
                put("businessLogicError", errors.getOutValue());
            }
        };
    }

    /**
     * Xóa loại thông số thiết bị
     */
    public Boolean delete(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_DELETE_LOAI_THONGSO_TB", params);

        BigDecimal rs = (BigDecimal) result.getOutValue();
        return rs != null && rs.intValue() == 1;
    }
}
