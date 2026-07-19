package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

@Service
public class DanhMucThietBiService {
    private final DbContext dbContext;

    public DanhMucThietBiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getDanhMucVLan(String loaiDanhMuc, String ThamSo1, String ThamSo2)
            throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(
                new SqlParameter("p_phan_vung_id", AppRequestContext.getCurrent().getPhanVungID(),
                        SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_loai_danh_muc", loaiDanhMuc, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tham_so_1", ThamSo1, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tham_so_2", ThamSo2, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_DANHMUC_VLAN", params);
    }
}
