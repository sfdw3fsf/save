package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.HuongKetNoiBrasInput;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HuongKetNoiService {
    private final DbContext dbContext;
    public HuongKetNoiService(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> lietKeHuongKetNoi(HuongKetNoiBrasInput.HuongKetNoiBrasInputLayDsHuongKetNoi input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("p_kieu", input.getvKieu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vung_id", input.getvVungId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_HUONG_KET_NOI.SP_HUONG_KET_NOI_SEL", params);

    }

    public Map<String, Object> layHuongKetNoiTheoId(String id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_ID", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> list = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_HUONG_KET_NOI.SP_HUONG_KET_NOI_SEL_BY_ID", params);
        return list.size() > 0 ?list.get(0) : null;
    }

    public Map<String, Object> themHuongKetNoi(HuongKetNoiBrasInput huongKetNoi) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id",AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaikn_id", huongKetNoi.getLoaiknId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten_huong", huongKetNoi.getTenHuong(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_thiet_bi", huongKetNoi.getThietBi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_kieu", huongKetNoi.getKieu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", huongKetNoi.getDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> res = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_HUONG_KET_NOI.SP_HUONG_KET_NOI_INS", params);
        return (res != null && res.size() != 0) ? res.get(0) : null;
    }

    public Map<String, Object> suaHuongKetNoi(HuongKetNoiBrasInput.HuongKetNoiBrasInputLayDsHuongKetNoiUpd huongKetNoi) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id",AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_huongkn_id", huongKetNoi.getHuongknId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaikn_id", huongKetNoi.getLoaiknId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten_huong", huongKetNoi.getTenHuong(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_thiet_bi", huongKetNoi.getThietBi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_kieu", huongKetNoi.getKieu(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", huongKetNoi.getDslamId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_HUONG_KET_NOI.SP_HUONG_KET_NOI_UPD", params);
        Map<String, Object> res = new HashMap<>();
        res.put("result", "1".equals(list.get(0).get("KETQUA").toString()));
        return res;
    }

    public Map<String, Object> xoaHuongKetNoi(String someId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id",AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id", someId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_HUONG_KET_NOI.SP_HUONG_KET_NOI_DEL", params);
        Map<String, Object> res = new HashMap<>();
        res.put("result", "1".equals(list.get(0).get("KETQUA").toString()));
        return res;
    }

    public Object layDMHuongKetNoi() throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("vvung_kn", java.sql.Types.REF_CURSOR, "VUNG_KN"));
        params.add(new SqlParameter("vloai_kn", java.sql.Types.REF_CURSOR, "LOAI_KN"));
        params.add(new SqlParameter("vkieu_kn", java.sql.Types.REF_CURSOR, "KIEU_KN"));
        params.add(new SqlParameter("vthietbi", java.sql.Types.REF_CURSOR, "THIETBI_KN"));
        return dbContext.executeSpWithCursorsToListMap("ECMS.PKG_HUONG_KET_NOI.SP_LAY_DM_HUONG_KN", params);
    }
}
