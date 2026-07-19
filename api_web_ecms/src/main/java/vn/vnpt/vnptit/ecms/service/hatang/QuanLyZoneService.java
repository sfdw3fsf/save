package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.QuanLyZoneDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class QuanLyZoneService {
    private final DbContext dbContext;

    public QuanLyZoneService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Map<String, Object> upsertZone(QuanLyZoneDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getZoneId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTenZone(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_idc_id", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hieuluc", dto.getHieuluc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_UPSERT_ZONE", params);
    }

    public ArrayList<Map<String, Object>> searchZoneList(QuanLyZoneDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten", dto.getTenZone(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_idc_id", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_SEARCH_DS_ZONE", params);
    }

    public Object getDanhmucZone(String loaiDanhmuc, Number thamSo1, Number thamSo2) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(
                new SqlParameter("p_loai_danhmuc", loaiDanhmuc, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tham_so1", thamSo1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tham_so2", thamSo2, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_GET_DANHMUC_ZONE", params);
    }

    public Map<String, Object> deleteZone(Number id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_IP.SP_DELETE_ZONE", params);
    }
 }
