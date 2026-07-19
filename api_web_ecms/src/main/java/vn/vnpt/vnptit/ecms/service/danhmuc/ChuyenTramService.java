package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.ChuyenTramDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.KenhPort;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ChuyenTramService {
    private DbContext _dDbContext;
    public  ChuyenTramService(DbContext dbContext){
        this._dDbContext = dbContext;
    }
    //Cuong add Hình 15: Chuyển trạm cho thiết bị dslam, switch, gpon
    public ArrayList<Map<String, Object>> chuyenTramThietbi(ChuyenTramDtoIn chuyenTramDtoIn) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_type", chuyenTramDtoIn.getType(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_turack_id", chuyenTramDtoIn.getTurack_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tramvt_id", chuyenTramDtoIn.getTramvt_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", chuyenTramDtoIn.getDslam_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tu_list", StringUtils.join(chuyenTramDtoIn.getTuId(), ','), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CHUYENTRAM.SP_CHUYENTRAM", params);
    }

    public boolean addTuDslam(String tuId, ChuyenTramDtoIn chuyenTramDtoIn) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        for (String item : Arrays.asList(tuId.split("\\s*,\\s*"))) {
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_turack_id", Integer.parseInt(item), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dslam_id", chuyenTramDtoIn.getDslam_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        _dDbContext.executeSpWithCursorToListMap("ECMS.PKG_CHUYENTRAM.SP_TU_DSLAM_INS", params);
        }
        return true;
    }
}
