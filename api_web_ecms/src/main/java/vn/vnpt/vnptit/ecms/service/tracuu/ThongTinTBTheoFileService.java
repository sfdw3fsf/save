package vn.vnpt.vnptit.ecms.service.tracuu;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.TracuuTBTheoFileIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ThongTinTBTheoFileService {
    private final DbContext dbContext;
    public ThongTinTBTheoFileService(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }
    public ArrayList<Map<String, Object>> thuebao_theofile(int phanvung_id,TracuuTBTheoFileIn obj)throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_MA_TBAO", obj.getMa_tbao(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("V_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUUMNV.SP_TRACUU_THUEBAO_THEO_FILE", params);
    }
    public ArrayList<Map<String, Object>> thuebao_theofile_v2(int phanvung_id,TracuuTBTheoFileIn obj)throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_MA_TBAO", obj.getMa_tbao(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("V_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUUMNV.SP_TRACUU_THUEBAO_THEO_FILE_V2", params);
    }
    public Map<String, Object> laydsfilemau(int phanvung_id)throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        ArrayList<Map<String, Object>> rs = new ArrayList<>();
        params.add(new SqlParameter("V_PHANVUNG_ID", phanvung_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        rs= dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUUMNV.SP_LAY_DU_LIEU_MA_TBAO_MAU", params);
        return rs!=null &&rs.size()>0?rs.get(0):null;
    }
}
