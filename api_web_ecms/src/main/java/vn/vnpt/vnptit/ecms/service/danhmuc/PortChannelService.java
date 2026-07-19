package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.*;
import vn.vnpt.vnptit.ecms.service.DanhMucService;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class PortChannelService {
    private final DbContext dbContext;

    public PortChannelService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

//    public ArrayList<Map<String, Object>> DsPortChannel() throws AppSqlException {
//        SqlParameter param = new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
//        dbContext.setConnection(ConnectionManager.Connections.CSS);
//        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_DS_PORT_CHANNEL", param);
//    }

    public ArrayList<Map<String, Object>> DsPortChannel(Long tramTbId, Long dslamId, Integer phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_tramtb_id", tramTbId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_dslam_id", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_DS_PORT_CHANNEL", params);
    }

    public Map<String, Object> ChiTietPortChannel(Integer phanVungId, Long kenhId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_kenh_id", kenhId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_GET_PORT_CHANNEL", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }

    public ArrayList<Map<String, Object>> DsCongPortChannel(Long kenhId, Long dslamId, Integer kieu, Integer phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KENH_ID", kenhId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KIEU", kieu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_LAY_DANHSACH_CONG_PORTCHANNEL", params);
    }

    public ArrayList<Map<String, Object>> ThemKenh(ThemPortChannel input, Integer phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_kenh_id", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", input.getTramTbId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", input.getDslamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_PORT_CHANNEL_CHECK_INPUT", params);

        for (KenhPort item : input.getKenhPorts()) {
            params = new ArrayList<SqlParameter>();
            params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_DSLAM_ID", input.getDslamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_PORTVL_ID", item.getPortVlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_PORT", item.getPort(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_CHECK_CONG_PORTCHANNEL", params);
        }

        params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_ten", input.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma", input.getMa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_donvi_id", input.getTramTbId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", input.getDslamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_KENH_INS", params);
    }

    public ArrayList<Map<String, Object>> ThemKenhPort(KenhPort input, Integer phanVungId, Long kenhId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_kenh_id", kenhId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_portvl_id", input.getPortVlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port", input.getPort(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_KENH_PORT_INS", params);
    }

    public ArrayList<Map<String, Object>> CapNhatKenh(CapNhatPortChannel input, Integer phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_kenh_id", input.getKenhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_PORT_CHANNEL_CHECK_INPUT", params);

        for (KenhPort item : input.getKenhPorts()) {
            params = new ArrayList<SqlParameter>();
            params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_DSLAM_ID", input.getDslamId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_PORTVL_ID", item.getPortVlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_PORT", item.getPort(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_CHECK_CONG_PORTCHANNEL", params);
        }

        params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_kenh_id", input.getKenhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", input.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma", input.getMa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_KENH_UPD", params);
    }

    public ArrayList<Map<String, Object>> XoaKenhPortChannel(Integer phanVungId, Long kenhId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", kenhId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_KENH_PORT_DEL", params);
    }

    public Boolean XoaPortChannel(Integer phanVungId, Long kenhId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_kenh_id", kenhId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_PORT_CHANNEL_DEL", params);
        return true;
    }
    public ArrayList<Map<String, Object>> lay_ds_vetinh1(Long ttvt) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ttvt", ttvt != null ? ttvt : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_LAY_DS_VETINH1", params);
    }

    public Object lay_ds_vetinh(int ttvt) throws AppSqlException {
    	DanhMucService danhMuc= new DanhMucService(dbContext);
    	return danhMuc.layDsVeTinh(ttvt);
    }

    public ArrayList<Map<String, Object>> lay_ds_thietbi(Long tramtbi_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tramtbi_id", tramtbi_id != null ? tramtbi_id : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DANHMUC.SP_LAY_DS_THIETBI", params);
    }
}
