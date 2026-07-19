package vn.vnpt.vnptit.ecms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.DbHelper;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.QuyHoachVCIDInput;
import vn.vnpt.vnptit.ecms.dto.QuyHoachVlanUpeInput;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuyHoachVlanUpeService {

    private final DbContext dbContext;

    @Autowired
    DbHelper dbHelper;

    public QuyHoachVlanUpeService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> lay(Long vlan) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vlan", vlan, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN_UPE.SP_LAY_DS_QUYHOACH_VLAN_UPE", params);
    }

    public ArrayList<Map<String, Object>> lay_all() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN_UPE.SP_LAY_FULL_DS_QUYHOACH_VLAN_UPE", params);
    }

    public Map<String, Object> them(QuyHoachVlanUpeInput input) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", input.getDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vlan_tu", input.getVlanTu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vlan_den", input.getVlanDen(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> list = dbContext
                .executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN_UPE.SP_THEM_QUYHOACH_VLAN_UPE", params);
        if (list.size() > 0) {
            return list.get(0);
        } else
            return null;
    }

    public Map<String, Object> capnhat(Long quyhoachId, QuyHoachVlanUpeInput input) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_quyhoach_id", quyhoachId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", input.getDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vlan_tu", input.getVlanTu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vlan_den", input.getVlanDen(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> list = dbContext
                .executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN_UPE.SP_SUA_QUYHOACH_VLAN_UPE", params);
        if (list.size() > 0) {
            return list.get(0);
        } else
            return null;
    }

    public boolean xoa(Long quyhoachId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_quyhoach_id", quyhoachId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> list = dbContext
                .executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN_UPE.SP_XOA_QUYHOACH_VLAN_UPE", params);
        Map<String, Object> map = list.get(0);
        return "1".equals(map.get("RESULT").toString());
    }

    public List<Map<String, Object>> layUpe() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN_UPE.SP_LAY_DS_UPE", params);
    }
}
