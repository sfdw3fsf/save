package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.DslamCardDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DsLamCardService {
	private final DbContext dbContext;

	public DsLamCardService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public ArrayList<Map<String, Object>> lietKe(long phanVungId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", phanVungId, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_CARD_DSL_GET_ALL", params);
	}

	public Map<String, Object> chiTiet(long phanVungId, long id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", phanVungId, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_carddsl_id", id, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_CARD_DSL_GET_BY_ID", params);
		return list.size() > 0 ? list.get(0) : null;
	}

	public Map<String, Object> them(DslamCardDtoIn dslamCardDtoIn) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_shelfdsl_id", dslamCardDtoIn.getShelfDslId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vitri", dslamCardDtoIn.getViTri(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loai_card_id", dslamCardDtoIn.getLoaiCardId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ten_card", dslamCardDtoIn.getTenCard(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_serial", dslamCardDtoIn.getSerial(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_phien_td", dslamCardDtoIn.getPhienTd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_phien_tb", dslamCardDtoIn.getPhienTb(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_doidaytb_tu", dslamCardDtoIn.getDoiDayTbTu(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_doidaytd_tu", dslamCardDtoIn.getDoiDayTdTu(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_partnumber", dslamCardDtoIn.getPartNumber(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_CARD_DSL_INS", params).get(0);
	}

	public Map<String, Object> capNhat(DslamCardDtoIn.DslamCardDtoUpd dslamCardDtoIn) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_carddsl_id", dslamCardDtoIn.getCardDslId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_shelfdsl_id", dslamCardDtoIn.getShelfDslId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_vitri", dslamCardDtoIn.getViTri(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_loai_card_id", dslamCardDtoIn.getLoaiCardId(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_ten_card", dslamCardDtoIn.getTenCard(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_serial", dslamCardDtoIn.getSerial(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_phien_td", dslamCardDtoIn.getPhienTd(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_phien_tb", dslamCardDtoIn.getPhienTb(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_doidaytb_tu", dslamCardDtoIn.getDoiDayTbTu(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_doidaytd_tu", dslamCardDtoIn.getDoiDayTdTu(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_partnumber", dslamCardDtoIn.getPartNumber(), ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_CARD_DSL_UPD", params);
		Map<String, Object> res = new HashMap<>();
		if (list.size() != 0)
			res.put("result", true);
		else
			res.put("result", false);
		return res;
	}

	public Map<String, Object> xoa(long phanVungId, long id) throws AppSqlException
	{
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", phanVungId, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_carddsl_id", id, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_CARD_DSL_DEL_BY_ID", params);
		//if (list == null) throw new AppSqlException("Xóa thất bại");
		Map<String, Object> res = new HashMap<>();
		res.put("result", true);
		return res;
	}

	public List<Map<String, Object>> getPort(int phanVungID, Long cardDslId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phan_vung_id", phanVungID, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_carddsl_id", cardDslId, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_CARD_DSL_GET_PORT", params);
	}

	public List<Map<String, Object>> getNhomCardByLoaitbId(int phanVungID, int id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHAN_VUNG_ID", phanVungID, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_LOAITB_ID", id, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_DS_NHOM_CARD_BY_LOAITBI", params);
	}

	public Map<String, Object> getLoaiCardById(int phanVungID, int id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHAN_VUNG_ID", phanVungID, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_LOAI_CARD_ID", id, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		List<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_LOAICARD_BY_ID", params);
		if(rs.size() > 0) {
			return rs.get(0);
		}
		return new HashMap<>();
	}

	public List<Map<String, Object>> getLoaiCardByNhomCardId(int phanVungID, int nhomcard_id, int loaitb_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHAN_VUNG_ID", phanVungID, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_NHOM_CARD_ID", nhomcard_id, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_LOAITB_ID", loaitb_id, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_DS_LOAICARD_BY_NHOMCARD_ID", params);
	}

	public Map<String, Object> getPortCardDslamSuDung(int phanVungID, int carddsl_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHAN_VUNG_ID", phanVungID, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CARDDSL_ID", carddsl_id, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_PORT_DSL_CARD_SUDUNG", params).get(0);
	}

	public List<Map<String, Object>> layPortTheoCard(Long cardDslId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHAN_VUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CARDDSL_ID", cardDslId, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_LAY_PORT_THEO_CARD", params);
	}

	public List<Map<String, Object>> taoDuLieuVisa(Long cardDslId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHAN_VUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CARDDSL_ID", cardDslId, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_TAO_DULIEU_VISA_THEO_CARDDSL_ID", params);
	}

	public List<Map<String, Object>> taoPortDslCard(Long cardDslId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHAN_VUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CARDDSL_ID", cardDslId, ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_CARD_DSL.SP_TAO_PORT_DSL_CARD", params);
	}
}
