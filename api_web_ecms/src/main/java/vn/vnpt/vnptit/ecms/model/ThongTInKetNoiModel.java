package vn.vnpt.vnptit.ecms.model;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.ThongTinKetNoiIn;

import java.util.ArrayList;
import java.util.Map;

public class ThongTInKetNoiModel {
 /*   private long phanvung_id =0 ;
    private long madslam_port = 0 ;
    private long loaithietbi_id_n =0;
    private long loaithietbi_id_d =0;
    private long thietbi_id_n =0;
    private long thietbi_id_d =0;
    private long link_code =0;
    private long port_dich =0;
    private long chk_port =1;
    public long getPhanvung_id() {
        return phanvung_id;
    }

    public void setPhanvung_id(long phanvung_id) {
        this.phanvung_id = phanvung_id;
    }
    public long getChk_port() {
        return chk_port;
    }

    public void setChk_port(long chk_port) {
        this.chk_port = chk_port;
    }

    public long getLink_code() {
        return link_code;
    }

    public long getLoaithietbi_id_d() {
        return loaithietbi_id_d;
    }

    public long getLoaithietbi_id_n() {
        return loaithietbi_id_n;
    }

    public long getMadslam_port() {
        return madslam_port;
    }

    public long getPort_dich() {
        return port_dich;
    }

    public long getThietbi_id_d() {
        return thietbi_id_d;
    }

    public long getThietbi_id_n() {
        return thietbi_id_n;
    }

    public void setLink_code(long link_code) {
        this.link_code = link_code;
    }

    public void setLoaithietbi_id_d(long loaithietbi_id_d) {
        this.loaithietbi_id_d = loaithietbi_id_d;
    }

    public void setLoaithietbi_id_n(long loaithietbi_id_n) {
        this.loaithietbi_id_n = loaithietbi_id_n;
    }

    public void setMadslam_port(long madslam_port) {
        this.madslam_port = madslam_port;
    }

    public void setPort_dich(long port_dich) {
        this.port_dich = port_dich;
    }

    public void setThietbi_id_d(long thietbi_id_d) {
        this.thietbi_id_d = thietbi_id_d;
    }

    public void setThietbi_id_n(long thietbi_id_n) {
        this.thietbi_id_n = thietbi_id_n;
    }
    public boolean Load(ThongTinKetNoiIn item)
    {
        if(item != null)
        {
            this.setPhanvung_id(item.getPhanvung_id());
            this.setLink_code(item.getLink_code());
            this.setLoaithietbi_id_d(item.getLoaithietbi_id_d());
            this.setLoaithietbi_id_n(item.getLoaithietbi_id_n());
            this.setMadslam_port(item.getMadslam_port());
            this.setPort_dich(item.getPort_dich());
            this.setThietbi_id_d(item.getThietbi_id_d());
            this.setThietbi_id_n(item.getThietbi_id_n());
            this.setChk_port(item.getChk_port());
            return true;
        }
        return false;
    }
    public ArrayList<Map<String, Object>> ketnoiDslam(DbContext dbContext) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", this.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_ma_dslam_port", this.getMadslam_port(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loaitbi_id_n", this.getLoaithietbi_id_n(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loaitbi_id_d", this.getLoaithietbi_id_d(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_thietbi_id_n", this.getThietbi_id_n(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_thietbi_id_d", this.getThietbi_id_d(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_link_code", this.getLink_code(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_port_dich", this.getPort_dich(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_chk_port", this.getChk_port(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_KETNOI_DSLAM", params);
        if(!(result == null || result.size() == 0))
        {
            return result;
        }
        return null;
    }
    public ArrayList<Map<String, Object>> goKetNoiDsLam(DbContext dbContext) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", this.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_ma_dslam_port", this.getMadslam_port(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_port_dich", this.getPort_dich(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loaitbi_id_n", this.getLoaithietbi_id_n(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_loaitbi_id_d", this.getLoaithietbi_id_d(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_GO_KETNOI", params);
        if(!(result == null || result.size() == 0))
        {
            return result;
        }
        return null;
    }
    public ArrayList<Map<String, Object>> loadThongtinKetNoi(DbContext dbContext) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", this.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_ma_dslam_port", this.getMadslam_port(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_LOAD_TTKETNOI", params);
        if(result==null ||result.size()==0)
             result = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.SP_LOAD_TTKETNOI2", params);
        if(!(result == null || result.size() == 0))
        {
            return result;
        }
        return null;
    }
    public ArrayList<Map<String, Object>> layMaKetNoi(DbContext dbContext) throws AppSqlException
    {

        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", this.getPhanvung_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_ma_dslam_port", this.getMadslam_port(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_port_dich", this.getPort_dich(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_THONGTIN_KETNOI.LAY_MA_KETNOI", params);
        if(!(result == null || result.size() == 0))
        {
            return result;
        }
        return null;
    }

  */
}
