package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.model.PortSwitchModel;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PortSwitchService {
    private final DbContext dbContext;

    public PortSwitchService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public List<Map<String, Object>> getListPortSwitch(int turackid, int dslamid) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_TURACK_ID", turackid, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", dslamid, Types.NUMERIC));
//        params.add(new SqlParameter("P_PAGING", paging, Types.NUMERIC));
//        params.add(new SqlParameter("P_OFFSET", offset, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_SWITCH.SP_GET_LIST_PORT_SWITCH", params);
    }

    public List<Map<String, Object>> addPortSwitch(PortSwitchModel newPortSwitch) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_SLOT", newPortSwitch.getSlot(), Types.VARCHAR));
        params.add(new SqlParameter("P_PORT", newPortSwitch.getPort(), Types.VARCHAR));
        params.add(new SqlParameter("P_TT_PORT_ID", newPortSwitch.getTt_port_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_CARD_ID", newPortSwitch.getCard_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", newPortSwitch.getDslam_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_SELFID01", newPortSwitch.getSelfid01(), Types.VARCHAR));
        params.add(new SqlParameter("P_SELFID02", newPortSwitch.getSelfid02(), Types.VARCHAR));
        params.add(new SqlParameter("P_SELFID03", newPortSwitch.getSelfid03(), Types.VARCHAR));
        params.add(new SqlParameter("P_SELFID04", newPortSwitch.getSelfid04(), Types.VARCHAR));
        params.add(new SqlParameter("P_RACK_ID", newPortSwitch.getRack_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), Types.NUMERIC));
        params.add(new SqlParameter("P_NGAY_CN", new Date(), Types.DATE));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), Types.NUMERIC));
        params.add(new SqlParameter("P_PORTVL_ID", newPortSwitch.getPortvl_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_VCD", newPortSwitch.getVcd(), Types.VARCHAR));
        params.add(new SqlParameter("P_DSLAM_VDC_ID", newPortSwitch.getDslam_vdc_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_ONT_ID", newPortSwitch.getOnt_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_SLOT_VL", newPortSwitch.getSlot_vl(), Types.VARCHAR));
        params.add(new SqlParameter("P_PORT_VL", newPortSwitch.getPort_vl(), Types.VARCHAR));
        params.add(new SqlParameter("P_VLAN_INT", newPortSwitch.getVlan_int(), Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", newPortSwitch.getGhichu(), Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_SWITCH.SP_INSERT_PORT", params);
    }

    public List<Map<String, Object>> updatePortSwitch(PortSwitchModel newPortSwitch, int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_SLOT", newPortSwitch.getSlot(), Types.VARCHAR));
        params.add(new SqlParameter("P_PORT", newPortSwitch.getPort(), Types.VARCHAR));
        params.add(new SqlParameter("P_TT_PORT_ID", newPortSwitch.getTt_port_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_CARD_ID", newPortSwitch.getCard_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", newPortSwitch.getDslam_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_SELFID01", newPortSwitch.getSelfid01(), Types.VARCHAR));
        params.add(new SqlParameter("P_SELFID02", newPortSwitch.getSelfid02(), Types.VARCHAR));
        params.add(new SqlParameter("P_SELFID03", newPortSwitch.getSelfid03(), Types.VARCHAR));
        params.add(new SqlParameter("P_SELFID04", newPortSwitch.getSelfid04(), Types.VARCHAR));
        params.add(new SqlParameter("P_RACK_ID", newPortSwitch.getRack_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), Types.NUMERIC));
        params.add(new SqlParameter("P_NGAY_CN", new Date(), Types.DATE));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), Types.NUMERIC));
        params.add(new SqlParameter("P_PORTVL_ID", newPortSwitch.getPortvl_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_VCD", newPortSwitch.getVcd(), Types.VARCHAR));
        params.add(new SqlParameter("P_DSLAM_VDC_ID", newPortSwitch.getDslam_vdc_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_ONT_ID", newPortSwitch.getOnt_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_SLOT_VL", newPortSwitch.getSlot_vl(), Types.VARCHAR));
        params.add(new SqlParameter("P_PORT_VL", newPortSwitch.getPort_vl(), Types.VARCHAR));
        params.add(new SqlParameter("P_VLAN_INT", newPortSwitch.getVlan_int(), Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", newPortSwitch.getGhichu(), Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_SWITCH.SP_UPDATE_PORT", params);
        if(Integer.parseInt(rs.get(0).get("KETQUA").toString()) == 1) {
            rs.get(0).put("KETQUA", true);
        }
        else {
            rs.get(0).put("KETQUA", false);
        }
        return  rs;
    }

    public List<Map<String, Object>> deletePortSwtich(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_SWITCH.SP_DELETE_PORT", params);
        if(Integer.parseInt(rs.get(0).get("KETQUA").toString()) == 1) {
            rs.get(0).put("KETQUA", true);
        }
        else {
            rs.get(0).put("KETQUA", false);
        }
        return  rs;
    }
    public List<Map<String, Object>> getdsThueBaoTheoPortID(int port_switch_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_SWITCH_ID", port_switch_id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_PORT_SWITCH.SP_THUE_BAO_THEO_PORTSWITCH_SEL", params);
    }
}
