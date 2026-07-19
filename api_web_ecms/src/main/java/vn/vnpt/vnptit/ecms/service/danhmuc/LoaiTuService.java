package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.CapNhatLoaiTuDtoIn;
import vn.vnpt.vnptit.ecms.dto.ThemLoaiTuDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LoaiTuService {

	private final DbContext dbContext;

	public LoaiTuService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public ArrayList<Map<String, Object>> lietKeDanhSachLoaiTu() throws AppSqlException
	{
		SqlParameter param = new SqlParameter("p_loai_tu", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
		return dbContext.executeSpWithCursorToListMap("ecms.pkg_loai_tu.sp_loai_tu_list", param);
	}

	public Map<String, Object> themLoaiTu(ThemLoaiTuDtoIn themLoaiTuDtoIn) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_ten_loai_tu", themLoaiTuDtoIn.getTenLoaiTu(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_loai_tu", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ecms.pkg_loai_tu.sp_loai_tu_ins", params);
		return list.get(0);
	}

	public boolean capNhatLoaiTu(CapNhatLoaiTuDtoIn capNhatLoaiTuDtoIn) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_loaitu_id", capNhatLoaiTuDtoIn.getLoaiTuId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ten_loai_tu", capNhatLoaiTuDtoIn.getTenLoaiTu(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_ketqua", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ecms.pkg_loai_tu.sp_loai_tu_upd", params);
		return "1".equals(list.get(0).get("KQ").toString());
	}

	public boolean xoaLoaiTu(int loaiTuId) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_loaitu_id", String.valueOf(loaiTuId), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ketqua", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ecms.pkg_loai_tu.sp_loai_tu_del", params);
		return "1".equals(list.get(0).get("KQ").toString());
	}
}
