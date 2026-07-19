package vn.vnpt.vnptit.ecms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.CapNhatFixScvlanDtoIn;
import vn.vnpt.vnptit.ecms.dto.ThemFixScvlanDtoIn;
import vn.vnpt.vnptit.ecms.dto.XoaFixScvlanDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FixScvlanService {
//    @Autowired
//    DbContext dbContext;
    private final DbContext dbContext;

    public FixScvlanService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> lay_ds_port_quyhoach(Long dslamId)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_dslam_id", dslamId != null ? dslamId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PORT_QUYHOACH.SP_PORT_FIX_SCVLAN_SEL", parameters);
    }
    public Boolean capnhat_ds_port_quyhoach(List<CapNhatFixScvlanDtoIn> portQuyhoachList)  throws AppSqlException, JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_quyhoach", portQuyhoachList != null ? mapper.writeValueAsString(portQuyhoachList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_PORT_QUYHOACH.SP_PORT_FIX_SCVLAN_UPD", parameters);
        if (rs != null && rs.size() > 0)
            if ("1".equals(rs.get(0).get("RESULT").toString()))
                return Boolean.TRUE;
        return Boolean.FALSE;
    }
    public Boolean them_ds_port_quyhoach(List<ThemFixScvlanDtoIn> portQuyhoachList)  throws AppSqlException, JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_quyhoach", portQuyhoachList != null ? mapper.writeValueAsString(portQuyhoachList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_PORT_QUYHOACH.SP_PORT_FIX_SCVLAN_INS", parameters);
        if (rs != null && rs.size() > 0)
            if ("1".equals(rs.get(0).get("RESULT").toString()))
                return Boolean.TRUE;
        return Boolean.FALSE;
    }

    public Boolean xoa_ds_port_quyhoach(List<XoaFixScvlanDtoIn> portQuyhoachList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_list", portQuyhoachList != null ? mapper.writeValueAsString(portQuyhoachList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_PORT_QUYHOACH.SP_PORT_FIX_SCVLAN_DEL", parameters);
        if (rs != null && rs.size() > 0)
            if ("1".equals(rs.get(0).get("RESULT").toString()))
                return Boolean.TRUE;
        return Boolean.FALSE;
    }
    public ArrayList<Map<String, Object>> dm_quyhoach(Long dslamId)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_dslam_id", dslamId != null ? dslamId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PORT_QUYHOACH.SP_DM_QUYHOACH", parameters);
    }
}
