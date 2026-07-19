package vn.vnpt.vnptit.ecms.service.hatang;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucQuatDTO;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class DanhMucQuatService {
    private final DbContext dbContext;

    public DanhMucQuatService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String,Object>> getDanhMucPhong() throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result",null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_GET_ECMS_PHONG",params);
    }
    public ArrayList<Map<String,Object>> getDanhMucQuat() throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result",null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_GET_ECMS_QUAT",params);
    }

    public ArrayList<Map<String, Object>> insertQuat (DanhMucQuatDTO input) throws AppSqlException, IOException {
        String jsonData = JsonConvert.serialize(input);
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input.getP_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phongid", input.getP_phongid(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_tenquat", input.getP_tenquat(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_tentat", input.getP_tentat(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_chieudai", input.getP_chieudai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_chieurong", input.getP_chieurong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_toadox", input.getP_toadox(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_todoy", input.getP_todoy(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ghichu", input.getP_ghichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_mota", input.getP_mota(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_QUAT_INSERT", parameters);
    }
    public Map<String, Object> deleteQuat(Number id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_QUAT_DELETE", parameters);
    }

}
