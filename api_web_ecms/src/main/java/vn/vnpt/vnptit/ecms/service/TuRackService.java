package vn.vnpt.vnptit.ecms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.DbHelper;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.ThemTuRackInput;
import vn.vnpt.vnptit.ecms.dto.TuRackInput;

@Service
public class TuRackService {
	private final DbContext dbContext;

	@Autowired
	DbHelper dbHelper;

	public TuRackService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public ArrayList<Map<String, Object>> danhsach_turack(int kieutu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_kieutu", kieutu, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TURACK.SP_TURACK_GET_ALL_BY_KIEUTU", params);
	}

	public Map<String, Object> thongtin_turack_id(int p_turack_id)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_turack_id", p_turack_id, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> list= dbContext.executeSpWithCursorToListMap("ECMS.PKG_TURACK.SP_TURACK_GET_BY_ID", params);
		if (list.size()>0)
		{	return list.get(0);
		}			
		else return null;
	}

	public Map<String, Object> them_turack(ThemTuRackInput turack) throws AppSqlException {

		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_ten_tu", turack.getten_tu(), ParameterDirection.INPUT, java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_vitri", turack.getvitri(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(
				new SqlParameter("p_donvi_id", turack.getdonvi_id(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_loaitu_id", turack.getloaitu_id(), ParameterDirection.INPUT,
				java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_kieutu", turack.getkieutu(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> list=  dbContext.executeSpWithCursorToListMap("ECMS.PKG_TURACK.SP_TURACK_INS", params);
		if (list.size()>0)
		{	return list.get(0);
		}			
		else return null;
	}

	public boolean sua_turack(TuRackInput turack) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_turack_id", turack.getturack_id().toString(), ParameterDirection.INPUT,
				java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_ten_tu", turack.getten_tu().toString(), ParameterDirection.INPUT,
				java.sql.Types.VARCHAR));
		params.add(new SqlParameter("p_vitri", turack.getvitri().toString(), ParameterDirection.INPUT,
				java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_donvi_id", turack.getdonvi_id().toString(), ParameterDirection.INPUT,
				java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_loaitu_id", turack.getloaitu_id().toString(), ParameterDirection.INPUT,
				java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_kieutu", turack.getkieutu().toString(), ParameterDirection.INPUT,
				java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list =  dbContext.executeSpWithCursorToListMap("ECMS.PKG_TURACK.SP_TURACK_UPD", params);
		Map<String, Object> map = list.get(0);
		return "1".equals(map.get("RESULT").toString());
	}

	public boolean xoa_turack(int p_turack_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_turack_id", p_turack_id, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list =   dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_TURACK.SP_TURACK_DEL_BY_ID", params);
		Map<String, Object> map = list.get(0);
		return "1".equals(map.get("RESULT").toString());
	}

	public ArrayList<Map<String, Object>> lay_ds_turack_theo_donvi_va_kieutu(TuRackInput obj) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_donvi_id", obj.getdonvi_id(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_kieutu", obj.getkieutu(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TURACK.SP_TURACK_GET_BY_DONVI_AND_KIEUTU", params);
	}

	public ArrayList<Map<String, Object>> ds_turack() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TURACK.SP_TURACK_GET_ALL", params);
	}

	public ArrayList<Map<String, Object>> lay_ds_turack_theo_donvi(TuRackInput obj) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_donvi_id", obj.getdonvi_id(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TURACK.SP_TURACK_GET_BY_DONVI", params);
	}
}
