package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.QuyHoachScvLanDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class QuyHoachScvLanService {
    private final DbContext dbContext;
    public QuyHoachScvLanService(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }
    public ArrayList<Map<String, Object>> dsQuyHoachscvLan(int phanvung_id,QuyHoachScvLanDtoIn.QuyHoachScvLanTraCuu input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_KIEU", input.getKieu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_SVLAN_TU", input.getSvlan_tu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_SVLAN_DEN", input.getSvlan_den(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_SCVLAN.SP_LAY_DS_QUYHOACH_V2", params);
    }
    public ArrayList<Map<String, Object>> dsTramThietBi(int phanvung_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_SCVLAN.SP_LAY_DS_TRAM_TBI", params);
    }
    public Map<String, ArrayList<Map<String, Object>>> dmHuongKetNoi(int phanvung_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_VUNG_KN", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR,"vung_kn"));
        params.add(new SqlParameter("P_LOAI_KN", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR,"loai_kn"));
        params.add(new SqlParameter("P_KIEU_KN", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR,"kieu_kn"));
        params.add(new SqlParameter("P_THIETBI", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR,"thietbi"));
        return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_QUYHOACH_SCVLAN.SP_LAY_DM_HUONG_KN", params);
    }
    public ArrayList<Map<String, Object>> dmThietBi(int phanvung_id,QuyHoachScvLanDtoIn.danhMucThietBiDto input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_KIEU", input.getKieu(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_DONVI_ID", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_SCVLAN.SP_LAY_DM_THIETBI", params);
    }
    public ArrayList<Map<String, Object>> dsHuongKetnoi(int phanvung_id,QuyHoachScvLanDtoIn.danhMucHuongKNDto input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_VUNG_ID", input.getVung_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_KIEU", input.getKieu(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_SCVLAN.SP_LAY_DS_HUONG_KN", params);
    }
    public Map<String, Object> quyhoachScvLanIns(int phanvung_id, QuyHoachScvLanDtoIn.QuyHoachScvLanIns input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_DONVI_ID", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_DSLAM_ID", input.getDslam_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_SVLAN", input.getSvlan(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CVLAN_TU", input.getCvlan_tu(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CVLAN_DEN", input.getCvlan_den(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_HUONGKN_ID", input.getHuongkn_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_METHOD", input.getMethod(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_SCVLAN.SP_QUYHOACH_SCVLAN_INS", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }
    public Map<String, Object> quyhoachScvLanUpd(int phanvung_id,QuyHoachScvLanDtoIn.QuyHoachScvLanUpd input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_QUYHOACH_ID", input.getQuyhoach_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_DONVI_ID", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_DSLAM_ID", input.getDslam_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_SVLAN", input.getSvlan(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CVLAN_TU", input.getCvlan_tu(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CVLAN_DEN", input.getCvlan_den(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_HUONGKN_ID", input.getHuongkn_id(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_METHOD", input.getMethod(), SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_SCVLAN.SP_QUYHOACH_SCVLAN_UPD", params);
        return result != null && result.size() > 0 ? result.get(0) : null;
    }
    public boolean quyhoachScvLanDel(int phanvung_id,int quyhoach_scvlan_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_QUYHOACH_ID", quyhoach_scvlan_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>>rs=  dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_SCVLAN.SP_QUYHOACH_SCVLAN_DEL", params);
        if(rs!=null && rs.size()>0)
            return  true;
        return false;
    }
    public Map<String, Object> quyhoachScvLanDetail(int phanvung_id,int quyhoach_scvlan_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_QUYHOACH_ID", quyhoach_scvlan_id, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_SCVLAN.SP_QUYHOACH_SCVLAN_CHI_TIET", params);
        return result != null && result.size() > 0 ? result.get(0) : null;

    }
    public ArrayList<Map<String, Object>> dsMethod() throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_SCVLAN.SP_LAY_DS_METHOD", params);
    }
}
