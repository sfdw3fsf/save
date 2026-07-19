package vn.vnpt.vnptit.qlsc.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.CapNhatHeThongDichVuDto;
import vn.vnpt.vnptit.qlsc.dto.CapNhatHeThongDichVuInput;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DanhMucHeThongThietBiService {
	private final DbContext dbContext;

    public DanhMucHeThongThietBiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object DsHeThongThietBi(String dviChuQuan) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_dvi_chuquan", dviChuQuan, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.SP_LAY_DS_THIETBI_HETHONG", params);
    }

    public Object ChiTietHeThongDichVu(Integer heThongId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_hethong_id", heThongId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.SP_LAY_CHITIET_HETHONG_DICHVU", params);
    }

    public Object CapNhapHeThongDichVu(CapNhatHeThongDichVuInput input) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        ObjectMapper mapper = new ObjectMapper();
        params.add(new SqlParameter("p_action_type", input.getActionType(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_json_input", mapper.writeValueAsString(input.getCapNhatDto()), SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), Types.VARCHAR));
//        params.add(new SqlParameter("p_ketqua", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        Object rs = dbContext.executeFunction("QLSC.FN_CAPNHAT_HETHONG_DICHVU", Types.VARCHAR, params);
//        Map<String, Object> map = rs.get(0);
//        return Integer.parseInt(map.get("item").toString());
        return rs;
    }

    public Object XoaHeThongDichVu(Integer heThongId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_hethong_id", heThongId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("qlsc.sp_xoa_hethong_dichvu", params);
    }
}
