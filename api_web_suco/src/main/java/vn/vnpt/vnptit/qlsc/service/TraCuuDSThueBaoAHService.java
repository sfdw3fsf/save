package vn.vnpt.vnptit.qlsc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import vn.vnpt.vnptit.qlsc.dto.DstbDto;
import vn.vnpt.vnptit.qlsc.dto.LayDsNhanVienXlTheoNvDto;
import vn.vnpt.vnptit.qlsc.dto.TraCuuDSTB;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TraCuuDSThueBaoAHService {
	private final DbContext dbContext;

	@Autowired
	DbHelper dbHelper;

	public TraCuuDSThueBaoAHService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

//	public Object thongtin_suco(int VSUCO_ID) throws AppSqlException {
//		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
//		params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("VSUCO_ID", VSUCO_ID, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("RETURNDS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
//		ArrayList<Map<String, Object>> list= (ArrayList<Map<String, Object>>)dbContext.executeSpWithCursorToListMap("QLSC.PKG_TRACUU_SUCO.SP_LAY_CHITIET_SC", params);
//		return list;
//	}
//	public Object ds_cong_bi_anhhuong(int VSUCO_ID) throws AppSqlException {
//		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
//		params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("VSUCO_ID", VSUCO_ID, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("RETURNDS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
//		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_TRACUU_SUCO.SP_LAY_DS_CONG_BI_AH", params);
//	}
//	public Object ds_nhanvien_xuly(int VPHIEU_ID,int VSUCO_PTM_ID) throws AppSqlException {
//		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
//		params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("VPHIEU_ID", VPHIEU_ID, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("VSUCO_PTM_ID", VSUCO_PTM_ID, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("RETURNDS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
//		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_TRACUU_SUCO.SP_LAY_DS_NHANVIEN_XL", params);
//	}
//	public Object ds_baoton(int VPHIEU_ID) throws AppSqlException {
//		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
//		params.add(new SqlParameter("P_PHIEU_ID", VPHIEU_ID, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("CUR_RETURNDS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
//		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_BAOTON", params);
//	}
//	public Object lay_ds_nhanvien_xl_theo_nv(LayDsNhanVienXlTheoNvDto input) throws AppSqlException {
//		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
//		params.add(new SqlParameter("P_PHIEU_ID", input.getPhieu_id(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("P_SUCO_PTM_ID", input.getSuco_ptm_id(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("CUR_RETURNDS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
//		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_NHANVIEN_XL_THEO_NV", params);
//	}
//	public Object lay_ds_nhanvien_xl_theo_dv(Integer donViId) throws AppSqlException {
//		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
//		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("P_DONVI_ID", donViId, ParameterDirection.INPUT, java.sql.Types.NUMERIC));
//		params.add(new SqlParameter("P_RETURNDS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
//		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_DS_NHANVIEN_XL_THEO_DV", params);
//	}
	
	public Object HT_DichVuVT_Combobox(String dichvuvt_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_DICHVUVT_ID", dichvuvt_id, ParameterDirection.INPUT, java.sql.Types.VARCHAR));		
		params.add(new SqlParameter("RETURNDS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_TRACUU_SUCO.HT_DICHVUVT_COMBOBOX", params);
	}
	
	public Object NAP_DS_TRANGTHAI_SC() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));			
		params.add(new SqlParameter("RETURNDS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_TRACUU_SUCO.NAP_DS_TRANGTHAI_SC", params);		
	}
	
	public Object SP_LAY_DSTB_THEO_JSON(List<DstbDto> input) throws AppSqlException,JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, java.sql.Types.NUMERIC));
		params.add(new SqlParameter("P_LIST_MA_TB", mapper.writeValueAsString(input), ParameterDirection.INPUT, java.sql.Types.VARCHAR));		
		params.add(new SqlParameter("RETURNDS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("QLSC.PKG_TRACUU_SUCO.SP_LAY_DSTB_THEO_JSON", params);
	}
	
	public Object Post_TraCuuDSTB_CNTT_AnhHuong(TraCuuDSTB input) throws AppSqlException {		
		return new Object[]{};
	}
}
