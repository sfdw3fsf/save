package vn.vnpt.vnptit.ecms.service.tracuu;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.LayDungLuongRoiDtoIn;
import vn.vnpt.vnptit.ecms.dto.TraCuuBanDoMNVDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class TraCuuBanDoMNVService {
    private DbContext dbContext;

    public TraCuuBanDoMNVService(DbContext dbContext){
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> LAY_DS_KETCUOI_TRONG_BANKINH_V3(TraCuuBanDoMNVDtoIn traCuuBanDoMNV) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvungid", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_Lat", traCuuBanDoMNV.getLat(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_Lng", traCuuBanDoMNV.getLng(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_BanKinh", traCuuBanDoMNV.getBankinh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_PhanLoaiKC_ID", traCuuBanDoMNV.getPhanloaikc_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_IsLayKCCon", traCuuBanDoMNV.getLaykccon(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUUBANDO_MNV.SP_LAY_DS_KETCUOI_TRONG_BANKINH_V3", parameters);
    }

    public ArrayList<Map<String, Object>> lay_ds_thuoctinh() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TRACUUBANDO_MNV.LAY_DS_THUOCTINH", parameters);
    }

    public Object get_dungluong_roi_theo_kc(LayDungLuongRoiDtoIn obj) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("VKETCUOI_ID", obj.getKetcuoi_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        return dbContext.executeFunction("ECMS.PKG_TRACUUBANDO_MNV.LAY_DUNGLUONG_ROI_THEO_KETCUOI", 12, params);
    }
}
