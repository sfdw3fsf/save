package vn.vnpt.vnptit.ecms.service.test;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;

@Service
public class TraCuuHDDTService {

	@Autowired
	DbContext dbContext;

	public ArrayList<Map<String, Object>> lay_ds_tieuchi_timkiem() throws AppSqlException {
		ArrayList<SqlParameter> parameters = new ArrayList<>();
		parameters.add(new SqlParameter("p_rsout", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("DEV_IT3.PKG_TU_TEST.SP_DM_TIEUCHI_TRACUU_HOADON_DIENTU",
				parameters);
	}

	public Map<String, Object> lay_ds_hoadon_paging(Integer tieuchi, String noidung, Integer pageIndex,
			Integer pageSize) throws AppSqlException {
		ArrayList<SqlParameter> parameters = new ArrayList<>();
		parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
				ParameterDirection.INPUT, Types.NUMERIC));
		parameters.add(new SqlParameter("p_tieuchi", tieuchi, ParameterDirection.INPUT, Types.NUMERIC));
		parameters.add(new SqlParameter("p_noidung", noidung, ParameterDirection.INPUT, Types.VARCHAR));
		parameters.add(new SqlParameter("p_page_index", pageIndex, ParameterDirection.INPUT, Types.NUMERIC));
		parameters.add(new SqlParameter("p_page_size", pageSize, ParameterDirection.INPUT, Types.NUMERIC));

		SqlParameter parameterCursor = new SqlParameter("p_rsout", null, ParameterDirection.OUTPUT,
				java.sql.Types.REF_CURSOR);
		parameters.add(parameterCursor);
		SqlParameter parameterTotalRow = new SqlParameter("p_total_row", null, ParameterDirection.OUTPUT,
				java.sql.Types.NUMERIC);
		parameters.add(parameterTotalRow);
		dbContext.executeStoredProcedure("DEV_IT3.PKG_TU_TEST.SP_TRACUU_HOADON_DIENTU", parameters);

		Map<String, Object> result = new HashMap<>();
		result.put("pageItems", parameterCursor.getOutValue());
		result.put("allItems", parameterTotalRow.getOutValue());
		return result;
	}
}
