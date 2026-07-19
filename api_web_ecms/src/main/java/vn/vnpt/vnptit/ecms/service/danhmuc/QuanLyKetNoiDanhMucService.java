package vn.vnpt.vnptit.ecms.service.danhmuc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.danhmuc.QuanLyKetNoiDanhMucDto;

@Service
public class QuanLyKetNoiDanhMucService {
    @Autowired
    private DbContext dbContext;

    public ArrayList<Map<String, Object>> thiCongKetNoiDanhMuc(QuanLyKetNoiDanhMucDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_danhmuc", dto.getLoaiDanhMuc(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tham_so1", dto.getThamSo1(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tham_so2", dto.getThamSo2(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ecms.pkg_idc_thicong_congviec.sp_get_danhmuc_rack", params);
    }

    public ArrayList<Map<String, Object>> searchRack(JsonNode searchJsonData) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String jsonString = searchJsonData != null ? searchJsonData.toString() : null;
        params.add(new SqlParameter("p_search_json", jsonString, ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ecms.pkg_idc_thicong_congviec.sp_search_danhmuc_rack", params);
    }

    public ArrayList<Map<String, Object>> searchThietBi(JsonNode searchJsonData) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String jsonString = searchJsonData != null ? searchJsonData.toString() : null;
        params.add(new SqlParameter("p_search_json", jsonString, ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_donvi_qly_id", AppRequestContext.getCurrent().getDonViID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ecms.pkg_idc_thicong_congviec.sp_search_thietbi", params);
    }
}
