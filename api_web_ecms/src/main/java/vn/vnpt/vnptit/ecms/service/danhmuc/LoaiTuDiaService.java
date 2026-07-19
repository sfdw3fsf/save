package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.LoaiTuDiaIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ThongTinModule;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;



@Service
public class LoaiTuDiaService {
    private final DbContext dbContext;

    public LoaiTuDiaService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getDSLoaiTuDia() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.sp_lay_loai_tu_dia", param);
    }
    public void thayDoiThongTinLoaiTuDia(LoaiTuDiaIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", input.getTen().trim(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ghichu", input.getGhiChu().trim(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hieuluc", input.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_kieu", input.getKieu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ECMS.sp_capnhat_loai_tu_dia", params);
    }
}
