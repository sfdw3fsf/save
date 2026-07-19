package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.BrasDtoInput;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class QuanLyBrasService {

    private final DbContext dbContext;
    public QuanLyBrasService(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    public Map<String, Object> layBrasTheoKey(BigDecimal id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_bras_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> map = dbContext.executeSpIdxWithCursorToListMap("CSS.PKG_QUANLY_BRAS.SP_BRAS_LIST_BY_KEY", params);
        return map != null && map.size() > 0 ? map.get(0) : null;
    }

    public ArrayList<Map<String, Object>> layBras() throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("CSS.PKG_QUANLY_BRAS.SP_BRAS_LIST", params);
    }

    public Map<String, Object> taoBras(BrasDtoInput brasDtoInput) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id",AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_bras_id", brasDtoInput.getBrasId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_bras_vdc_id", brasDtoInput.getBrasVdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip", brasDtoInput.getIp(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_bras", brasDtoInput.getTenBras(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_slot", brasDtoInput.getSoSlot(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_port", brasDtoInput.getSoPort(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_used", brasDtoInput.getUsed(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> list = dbContext.executeSpIdxWithCursorToListMap("CSS.PKG_QUANLY_BRAS.SP_BRAS_INS", params);
        return list.get(0);
    }

    public boolean capNhatBras(BrasDtoInput brasDtoInput) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id",AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_bras_id", brasDtoInput.getBrasId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_bras_vdc_id", brasDtoInput.getBrasVdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip", brasDtoInput.getIp(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_bras", brasDtoInput.getTenBras(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_slot", brasDtoInput.getSoSlot(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_port", brasDtoInput.getSoPort(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_used", brasDtoInput.getUsed(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> list = dbContext.executeSpIdxWithCursorToListMap("CSS.PKG_QUANLY_BRAS.SP_BRAS_UPD", params);
        return "1".equals(list.get(0).get("KETQUA").toString());

    }

    public boolean xoaBras(long brasId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_bras_id", String.valueOf(brasId), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_curs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> list = dbContext.executeSpIdxWithCursorToListMap("CSS.PKG_QUANLY_BRAS.SP_BRAS_DEL", params);
        return "1".equals(list.get(0).get("KETQUA").toString());
    }

    public ArrayList<Map<String, Object>> layBrasTheoTuRack(int turack_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TURACK_ID", turack_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_curs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("CSS.PKG_QUANLY_BRAS.SP_BRAS_LIST_BY_TURACK_ID", params);
    }
}
