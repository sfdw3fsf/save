package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.YKienThuHoiBackupDTO;


import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ThuHoiBackUpService {
    private final DbContext dbContext;

    public ThuHoiBackUpService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getDSThuHoiBackUp() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.sp_ds_thuhoi_backup", param);
    }

    public ArrayList<Map<String, Object>> getDSYeuCauThuHoi(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.sp_lay_thongtin_yeucau_thuhoi_backup", params);
    }

    public Boolean hoanThanhThuHoiBackup(Long id, YKienThuHoiBackupDTO yKien) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_y_kien", yKien.getYkien(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.sp_hoanthanh_thuhoi_backup", params);
        if (rs != null && !rs.isEmpty()){
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}