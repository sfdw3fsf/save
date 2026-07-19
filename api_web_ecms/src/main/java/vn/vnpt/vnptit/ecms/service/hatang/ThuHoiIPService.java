package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ThuHoiIPService {
    @Autowired
    private DbContext dbContext;

    public Object getDSThuHoiIP() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_THUHOI_IP_GETALL", params);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        List<Map<String, Object>> result = dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
        return result;
    }

    public Object getDSYeuCauThuHoi(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_THUHOI_IP_TT_YEUCAU", params);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        List<Map<String, Object>> result = dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
        return result;
    }

    public Object hoanThanhThuHoiIP(Number id, String trangthai_thuhoi) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai", trangthai_thuhoi, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_THUHOI_IP_HOANTHANH", params);
        return result.getOutValue();
    }
}
