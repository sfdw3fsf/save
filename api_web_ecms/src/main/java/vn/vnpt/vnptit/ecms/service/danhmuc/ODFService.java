package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.CapNhatODFDtoIn;
import vn.vnpt.vnptit.ecms.dto.DsOdfDtoInp;
import vn.vnpt.vnptit.ecms.dto.ThemODFDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

@Service
public class ODFService {
    @Autowired
    DbContext dbContext;
    public Map<String, Object> lay_odf_theo_odf_id(Long odfId)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_odf_id", odfId != null ? odfId.toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_ODF.SP_ODF_SEL_BY_ODF_ID", parameters);
        return rs != null && rs.size() > 0 ? rs.get(0) : null;
    }
    
    public Map<String, Object> tao_odf(ThemODFDtoIn odf)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_turack_id", odf.getTurackId() != null ? odf.getTurackId() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ten_odf", odf.getTenOdf() != null ? odf.getTenOdf() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_so_port", odf.getSoPort() != null ? odf.getSoPort() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_portbd", odf.getPortBd() != null ? odf.getPortBd() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ghichu", odf.getGhichu() != null ? odf.getGhichu() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_loai_cong", odf.getLoaicong() != null ? odf.getLoaicong() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_duan_id", odf.getDuanId() != null ? odf.getDuanId() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_taisan_id", odf.getTaisanId() != null ? odf.getTaisanId() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_ODF.SP_ODF_INS_V2", parameters);
        return rs != null && rs.size() > 0 ? rs.get(0) : null;
    }

    public Map<String, Object> capnhat_odf(CapNhatODFDtoIn odf)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_odf_id", odf.getOdfId() != null ? odf.getOdfId() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_turack_id", odf.getTurackId() != null ? odf.getTurackId() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ten_odf", odf.getTenOdf() != null ? odf.getTenOdf() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_so_port", odf.getSoPort() != null ? odf.getSoPort() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_portbd", odf.getPortBd() != null ? odf.getPortBd() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ghichu", odf.getGhichu() != null ? odf.getGhichu() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_loai_cong", odf.getLoaicong() != null ? odf.getLoaicong() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_duan_id", odf.getDuanId() != null ? odf.getDuanId() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_taisan_id", odf.getTaisanId() != null ? odf.getTaisanId() : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_ODF.SP_ODF_UPD_V2", parameters);
        return rs != null && rs.size() > 0 ? rs.get(0) : null;
    }

    public Boolean xoa_odf(Long odfId)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_odf_id", odfId != null ? odfId : null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_ODF.SP_ODF_DEL_V2", parameters);
        if (rs != null && rs.size() > 0){
            if ("1".equals(rs.get(0).get("RESULT").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    
    public ArrayList<Map<String, Object>> lay_ds_port_odf_theo_odf_id(Long odfId)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_odf_id", odfId != null ? odfId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_ODF.SP_PORT_ODF_SEL_BY_ODF_ID", parameters);
    }

    public Boolean kiemTraTrungTenOdf(String ten)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_TEN_ODF", ten, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return "1".equals(dbContext.executeSpWithCursorToListMap("ECMS.PKG_ODF.SP_KIEMTRA_TRUNG_TENODF", parameters).get(0).get("KQ").toString());
    }

    public List<Map<String, Object>> layDsOdfTheoTuRackVaTramTb(DsOdfDtoInp obj)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TRAMTB_ID", obj.getTramtb_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TURACK_ID", obj.getTurack_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_ODF.SP_LAY_DS_ODF_THEO_TURACK_VA_TRAMTB", parameters);
    }

    public Boolean taoPortOdf(Long odfId)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_ODF_ID", odfId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_ODF.SP_TAO_PORT_ODF", parameters);
        return "1".equals(rs.get(0).get("KQ").toString());
    }
}
