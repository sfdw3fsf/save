package vn.vnpt.vnptit.ecms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.DbHelper;
import vn.vnpt.database.OracleParamEx;
import vn.vnpt.database.OracleParamEx.OracleDirect;
import vn.vnpt.database.OracleParamEx.OracleType;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.message.Response;
import vn.vnpt.vnptit.ecms.dto.LoaiThietBiInput;
import vn.vnpt.vnptit.ecms.dto.QuyHoachVCIDInput;
import vn.vnpt.vnptit.ecms.dto.ThemTuRackInput;
import vn.vnpt.vnptit.ecms.dto.TuRackInput;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuyHoachVCIDService {
	private final DbContext dbContext;

	@Autowired
	DbHelper dbHelper;

	public QuyHoachVCIDService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public ArrayList<Map<String, Object>> danhsach_tram_tbi() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_VCID.SP_LOAD_TRAM_TBI", params);
	}

	public ArrayList<Map<String, Object>> danhsach_tbi_theo_tram(int donvi_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_donvi_id", donvi_id, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_VCID.SP_LOAD_TBI_THEO_TRAM", params);
	}

	public ArrayList<Map<String, Object>> danhsach_quyhoach(int vlan) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_vlan", vlan, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_VCID.SP_DS_QUYHOACH_VCID", params);
	}

	public Map<String, Object> them_quyhoach_vcid(QuyHoachVCIDInput input) throws AppSqlException {

		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(
				new SqlParameter("P_DONVI_ID", input.getDONVI_ID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(
				new SqlParameter("P_DSLAM_ID", input.getDSLAM_ID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));

		params.add(new SqlParameter("P_VLAN_TU", input.getVLAN_TU(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(
				new SqlParameter("P_VLAN_DEN", input.getVLAN_DEN(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));

		params.add(new SqlParameter("P_VCID_TU", input.getVCID_TU(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(
				new SqlParameter("P_VCID_DEN", input.getVCID_DEN(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));

		params.add(new SqlParameter("P_SI_TU", input.getSI_TU(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_SI_DEN", input.getSI_DEN(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));

		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_VCID.SP_THEM_QUYHOACH_VCID", params);
		if (list.size() > 0) {
			return list.get(0);
		} else
			return null;
	}

	public Map<String, Object> capnhat_quyhoach_vcid(int quyhoach_id, QuyHoachVCIDInput input) throws AppSqlException {

		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				ParameterDirection.INPUT, java.sql.Types.NUMERIC));

		params.add(new SqlParameter("P_QUYHOACH_ID", quyhoach_id, ParameterDirection.INPUT, java.sql.Types.NUMERIC));

		params.add(
				new SqlParameter("P_DONVI_ID", input.getDONVI_ID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(
				new SqlParameter("P_DSLAM_ID", input.getDSLAM_ID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));

		params.add(new SqlParameter("P_VLAN_TU", input.getVLAN_TU(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(
				new SqlParameter("P_VLAN_DEN", input.getVLAN_DEN(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));

		params.add(new SqlParameter("P_VCID_TU", input.getVCID_TU(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(
				new SqlParameter("P_VCID_DEN", input.getVCID_DEN(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));

		params.add(new SqlParameter("P_SI_TU", input.getSI_TU(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_SI_DEN", input.getSI_DEN(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));

		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> list = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_QUYHOACH_VCID.SP_CAPNHAT_QUYHOACH_VCID", params);
		if (list.size() > 0) {
			return list.get(0);
		} else
			return null;
	}

	public boolean xoa_quyhoach_vcid(int quyhoach_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
				ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_QUYHOACH_ID", quyhoach_id, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext
				.executeSpIdxWithCursorToListMap("ECMS.PKG_QUYHOACH_VCID.SP_XOA_QUYHOACH_VCID", params);
		Map<String, Object> map = list.get(0);
		return "1".equals(map.get("RESULT").toString());
	}
}
