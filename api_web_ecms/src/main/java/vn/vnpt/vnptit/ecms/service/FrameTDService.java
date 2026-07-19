package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.FrameTDDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FrameTDService {
    private DbContext dbContext;

    public  FrameTDService(DbContext dbContext){
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getFrameTD() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_FRAME_TD.SP_DS_FRAME_TD_SEL", params);
    }

    public Map<String, Object> addFrameTD(FrameTDDtoIn frameTDDtoIn) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten_frame", frameTDDtoIn.getTen_frame(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_turack_id", frameTDDtoIn.getTurack_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_so_slot", frameTDDtoIn.getSo_slot(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_serial", frameTDDtoIn.getSerial(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_loai_dslam_id", frameTDDtoIn.getLoai_dslam_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaidb_vao_id", frameTDDtoIn.getLoaidb_vao_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaidb_ra_id", frameTDDtoIn.getLoaidb_ra_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_so_soiquang", frameTDDtoIn.getSo_soiquang(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ring_id", frameTDDtoIn.getRing_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dongbo_vao_id", frameTDDtoIn.getDongbo_vao_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hangsx_id", frameTDDtoIn.getHangsx_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_giaodien_id", frameTDDtoIn.getGiaodien_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_slot_bd", frameTDDtoIn.getSlot_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rack", frameTDDtoIn.getRack(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_FRAME_TD.SP_FRAME_TD_INS", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public Map<String, Object> editFrameTD(FrameTDDtoIn frameTDDtoIn) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_frametd_id", frameTDDtoIn.getFrametd_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten_frame", frameTDDtoIn.getTen_frame(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_turack_id", frameTDDtoIn.getTurack_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_so_slot", frameTDDtoIn.getSo_slot(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_serial", frameTDDtoIn.getSerial(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_loai_dslam_id", frameTDDtoIn.getLoai_dslam_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaidb_vao_id", frameTDDtoIn.getLoaidb_vao_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaidb_ra_id", frameTDDtoIn.getLoaidb_ra_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_so_soiquang", frameTDDtoIn.getSo_soiquang(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ring_id", frameTDDtoIn.getRing_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dongbo_vao_id", frameTDDtoIn.getDongbo_vao_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hangsx_id", frameTDDtoIn.getHangsx_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_giaodien_id", frameTDDtoIn.getGiaodien_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_slot_bd", frameTDDtoIn.getSlot_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rack", frameTDDtoIn.getRack(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_FRAME_TD.SP_FRAME_TD_UPD", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public boolean deleteFrameTD(Long p_frametd_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_frametd_id", p_frametd_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_FRAME_TD.SP_FRAME_TD_DEL", params);
        if (rs != null && rs.size() > 0){
            if ("1".equals(rs.get(0).get("KQ").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Map<String, Object> getFrameTDId(Long p_frametd_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_frametd_id", p_frametd_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_FRAME_TD.SP_FRAME_TD_SEL_ID", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }
}
