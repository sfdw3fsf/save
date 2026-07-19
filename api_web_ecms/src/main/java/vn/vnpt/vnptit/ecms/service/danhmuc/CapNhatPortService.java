package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.CapNhatPortDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ChangePortMegaVNNDtoIn;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class CapNhatPortService {
    private DbContext _dDbContext;
    public  CapNhatPortService(DbContext dbContext){
        this._dDbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> lietKe_donvi_qli(@NotNull Long p_donvi_id, Long p_loaidv_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", p_donvi_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIDV_ID", p_loaidv_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_LOAD_DONVIQL", params);
    }

    public ArrayList<Map<String, Object>> lietKe_tramtbi(@NotNull Long p_donvi_id,Long p_loaidv_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", p_donvi_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIDV_ID", p_loaidv_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_LOAD_TRAMTBI", params);
    }

    public ArrayList<Map<String, Object>> lietKe_dslam_tinh(@NotNull Long p_donvi_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", p_donvi_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_LOAD_DSLAM_TINH", params);
    }

    public ArrayList<Map<String, Object>> lietKe_dscard_theo_dslam(@NotNull Long p_dslam_id, Long p_loaitbi_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", p_dslam_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAITBI_ID", p_loaitbi_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_LOAD_DSCARD_THEODSLAM", params);
    }

    public ArrayList<Map<String, Object>> lietKe_bras() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_LOAD_BRAS", params);
    }

    public ArrayList<Map<String, Object>> lietKe_dslam(@NotNull Long p_bras_id, Long p_kieu, Long p_thamso ) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_BRAS_ID", p_bras_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KIEU", p_kieu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_THAMSO", p_thamso, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_LOAD_DSLAM", params);
    }
    public ArrayList<Map<String, Object>> lay_ds_port_logic_v3(@NotNull Long p_donvi_id, String p_tt_port_id, long p_dslam_id,long p_card_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", p_donvi_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TT_PORT_ID", p_tt_port_id, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_DSLAM_ID", p_dslam_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CARD_ID", p_card_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.LAY_DS_PORT_LOGIC_V3", params);
    }
    public ArrayList<Map<String, Object>> lay_ds_thuebao_theo_port(@NotNull Long p_dslam_id, String p_ds_port_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", p_dslam_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TT_PORT_ID", p_ds_port_id, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.LAY_DS_THUEBAO_THEO_PORT", params);
    }

    public ArrayList<Map<String, Object>> log_ins(CapNhatPortDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_ID", obj.getPort_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", obj.getDslam_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_VDC_ID_MOI", obj.getDslam_vdc_id_moi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_VDC_ID_CU", obj.getDslam_vdc_id_cu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_THUEBAO_ID", obj.getThuebao_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HDTB_ID", obj.getHdtb_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MA_TB", obj.getMa_tb(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getUserName()!= null ? AppRequestContext.getCurrent().getUserName() : "LECAOCUONG", SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_NGAY_CN", AppRequestContext.getCurrent().getClientName() != null ? AppRequestContext.getCurrent().getClientName() :"0:0:0:0:0:0:0:1", SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN",AppRequestContext.getCurrent().getClientIpAddress() != null ? AppRequestContext.getCurrent().getClientIpAddress() :"ONEBSS", SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_LOG_INS", params);
    }
    public ArrayList<Map<String, Object>> port_upd(@NotNull Long p_dslam_vdc_id, Long p_port_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_VDC_ID", p_dslam_vdc_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PORT_ID", p_port_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_PORT_UPD", params);
    }

    public Map<String, Object> get_bras_vdc(@NotNull Long bras_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_BRAS_ID", bras_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_BRAS_VDC_SEL", params);
        return rs!=null &&rs.size()>0?rs.get(0):null;
    }

    public Map<String, Object> get_dslam_vdc(@NotNull Long dslam_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", dslam_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_DSLAM_VDC_SEL", params);
        return rs!=null &&rs.size()>0?rs.get(0):null;
    }

    public Map<String, Object> map_bras_vdc(@NotNull Long bras_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_BRAS_ID", bras_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_MAP_BRAS_VDC_ID", params);
        return rs!=null &&rs.size()>0?rs.get(0):null;
    }

    public Map<String, Object> map_dslam_vdc(@NotNull Long dslam_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", dslam_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CAPNHAT_PORT_DSLAMVDC.SP_MAP_DSLAM_VDC", params);
        return rs!=null &&rs.size()>0?rs.get(0):null;
    }

    public String changePortMegaVNN(ChangePortMegaVNNDtoIn obj) throws AppSqlException {
        return "OK";
    }


}
