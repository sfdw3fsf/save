package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.CapNhatLoaiSfpDtoInput;
import vn.vnpt.vnptit.ecms.dto.CapNhatSerialPortGponDtoInput;
import vn.vnpt.vnptit.ecms.dto.PortGponDtoInput;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PortGponService {
    private final DbContext dbContext;

    public PortGponService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getPortGpon(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_GPON.SP_GET_PORT_GPON_BY_ID", params);
        return rs.size() > 0 ? rs.get(0) : null;
    }

    public Object getSLThuebaoDoiTT(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_GPON.SP_GET_SL_TB_DOI_TRANG_THAI", params);
        return rs.get(0).get("RS");
    }

    public List<Map<String, Object>> getDsThueBaoTheoPort(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_GPON.SP_LAY_DS_THUEBAO_THEO_PORTVL_ID", params);
    }

    public Boolean chuyenTrangThaiPort(PortGponDtoInput input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_ID", input.getPort_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_SERIAL", input.getSerial(), Types.NUMERIC));
        params.add(new SqlParameter("P_CODE", input.getCode(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_GPON.SP_CHUYEN_TRANGTHAI_PORT", params);
        return "1".equals(rs.get(0).get("KQ").toString());
    }

    public Boolean kiemTraTrungSerial(String serial) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_SERIAL", serial, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_GPON.SP_KIEMTRA_SERIAL", params);
        return "1".equals(rs.get(0).get("KQ").toString());
    }

    public Boolean capNhatSerial(CapNhatSerialPortGponDtoInput input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_ID", input.getPort_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_SERIAL", input.getSerial(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_GPON.SP_UPDATE_SERIAL", params);
        return "1".equals(rs.get(0).get("KQ").toString());
    }

    public List<Map<String, Object>> taoDuLieuVisa(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PORTVL_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_GPON.SP_TAO_DULIEU_VISA_THEO_PORTVL", params);
    }
    
    public Boolean capNhatLoaiSFP(CapNhatLoaiSfpDtoInput input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("p_port_id", input.getPort_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_loai_sfp_id", input.getLoai_sfp_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ref_cursor", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_GPON.sp_update_loai_sfp", params);
        return "1".equals(rs.get(0).get("KQ").toString());
    }
   
}
