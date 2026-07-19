package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PrefixIpBrasService {

    private final DbContext dbContext;

    public PrefixIpBrasService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Map<String, Object> themMoiPrefixIp(ThemMoiPrefixIpDtoIn dtoIn) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_BRAS_ID", dtoIn.getBrasId(), Types.VARCHAR));
        params.add(new SqlParameter("P_PREFIX", dtoIn.getPrefix(), Types.VARCHAR));
        params.add(new SqlParameter("P_TUSO", dtoIn.getTuso(), Types.NUMERIC));
        params.add(new SqlParameter("P_DENSO", dtoIn.getDenso(), Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_ID", dtoIn.getLoaiId(), Types.NUMERIC));
        params.add(new SqlParameter("P_DS_LOAITB_ID", dtoIn.getDsLoaitbId(), Types.VARCHAR));
        params.add(new SqlParameter("P_KETQUA", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_PREFIX_IP_BRAS.SP_CAPNHAT_PREFIX_IP_BRAS_INS", params);
        return list.get(0);
    }

    public boolean capNhatPrefixIp(CapNhatPrefixIpDtoIn dtoIn) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_BRAS_ID", dtoIn.getBrasId(), Types.VARCHAR));
        params.add(new SqlParameter("P_PREFIX", dtoIn.getPrefix(), Types.VARCHAR));
        params.add(new SqlParameter("P_TUSO", dtoIn.getTuso(), Types.NUMERIC));
        params.add(new SqlParameter("P_DENSO", dtoIn.getDenso(), Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_ID", dtoIn.getLoaiId(), Types.NUMERIC));
        params.add(new SqlParameter("P_DS_LOAITB_ID", dtoIn.getDsLoaitbId(), Types.VARCHAR));
        params.add(new SqlParameter("P_KETQUA", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_PREFIX_IP_BRAS.SP_CAPNHAT_PREFIX_IP_BRAS_UPD", params);
        Map<String, Object> map = list.get(0);
        return "1".equals(map.get("KQ").toString());
    }

    public boolean xoaPrefixIp(XoaPrefixIpDtoIn dtoIn) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PREFIX", dtoIn.getPrefix(), Types.VARCHAR));
        params.add(new SqlParameter("P_KETQUA", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_PREFIX_IP_BRAS.SP_CAPNHAT_PREFIX_IP_BRAS_DEL", params);
        Map<String, Object> map = list.get(0);
        return "1".equals(map.get("KQ").toString());
    }

    public List<Map<String, Object>> layDsPrefixIp() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_DS_PREFIX_IP", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PREFIX_IP_BRAS.SP_LAY_DS_PREFIX_IP_BRAS_SEL", params);
    }

    public Map<String, Object> kiemTraPrefixIp(String prefix) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PREFIX", prefix, Types.VARCHAR));
        params.add(new SqlParameter("P_KETQUA", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_PREFIX_IP_BRAS.SP_KT_PREFIX_CHK", params);
        return list.get(0);
    }

    public boolean sinhIp(SinhIpDtoIn dtoIn) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PREFIX", dtoIn.getPrefix(), Types.VARCHAR));
        params.add(new SqlParameter("P_KETQUA", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_PREFIX_IP_BRAS.SP_SINH_IP_INS", params);
        Map<String, Object> map = list.get(0);
        return "1".equals(map.get("KQ").toString());
    }

    public List<Map<String, Object>> layDsIp(String prefix) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PREFIX", prefix, Types.VARCHAR));
        params.add(new SqlParameter("P_DS_IP", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PREFIX_IP_BRAS.SP_LAY_DS_IP_SEL", params);
    }

    public List<Map<String, Object>> layDsLoaiTbDaGan(String prefix) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PREFIX", prefix, Types.VARCHAR));
        params.add(new SqlParameter("P_DS_LOAITB", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PREFIX_IP_BRAS.SP_LAY_DS_LOAITB_DAGAN_SEL", params);
    }

    public List<Map<String, Object>> layDsLoaiTbChuaGan(LayDsLoaiTbChuaGanDtoIn dtoIn) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PREFIX", dtoIn.getPrefix(), Types.VARCHAR));
        params.add(new SqlParameter("P_DICHVUVT_ID", dtoIn.getDichvuvtId(), Types.VARCHAR));
        params.add(new SqlParameter("P_DS_LOAITB", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PREFIX_IP_BRAS.SP_LAY_DS_LOAITB_CHUAGAN_SEL", params);
    }

    public List<Map<String, Object>> layDmDichVuVt() throws AppSqlException {
        SqlParameter param = new SqlParameter("P_DS_DICHVU_VT", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PREFIX_IP_BRAS.SP_LAY_DM_DICHVU_VT", param);
    }

    public List<Map<String, Object>> layDmLoaiIp() throws AppSqlException {
        SqlParameter param = new SqlParameter("P_DS_LOAI_IP", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PREFIX_IP_BRAS.SP_LAY_DM_LOAI_IP", param);
    }
}
