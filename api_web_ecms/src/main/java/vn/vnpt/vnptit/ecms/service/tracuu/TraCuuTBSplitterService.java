package vn.vnpt.vnptit.ecms.service.tracuu;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.TraCuuDsSplitterTheoPortOltDtoIn;
import vn.vnpt.vnptit.ecms.dto.TtThueBaoIn;

import java.sql.Types;
import java.util.*;

@Service
public class TraCuuTBSplitterService {

    private final DbContext dbContext;

    public TraCuuTBSplitterService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> layDlFileMau(long phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_TRA_CUU_TB_SPLITTER.LAY_DL_FILE_MAU", params);
    }

    public List<Object> layTtThueBao  (TtThueBaoIn ttThueBaoIn) throws AppSqlException {
        List<Map<String, Object>> res = new ArrayList<>();
        int phanVungId = AppRequestContext.getCurrent().getPhanVungID();
        Arrays.asList(ttThueBaoIn.getKyHieuKetCuoi().split(",")).stream().distinct().parallel().forEach(kyHieu -> {
            List<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_phanvung_id",  phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ky_hieu_ket_cuoi", kyHieu, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
            try {
                res.addAll(dbContext.executeSpWithCursorToListMap("ecms.PKG_TRA_CUU_TB_SPLITTER.LAY_TT_THUEBAO", params));
            } catch (AppSqlException e) {
                e.printStackTrace();
            }
        });
        Set<Map<String, Object>> s = new TreeSet<Map<String, Object>>(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                if (o1.get("ACCOUNT").equals(o2.get("ACCOUNT"))) return 0;
                return 1;
            }
        });
        s.addAll(res);
        return Arrays.asList(s.toArray());
    }
}
