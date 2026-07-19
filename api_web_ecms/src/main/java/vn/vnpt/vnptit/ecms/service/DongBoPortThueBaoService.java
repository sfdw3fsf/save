package vn.vnpt.vnptit.ecms.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao.DongBoPort;
import vn.vnpt.vnptit.ecms.dto.DongBoPortThueBao.DongBoPortDto;
import vn.vnpt.vnptit.ecms.model.ThongTinNguoiDungModel;

@Service
public class DongBoPortThueBaoService {
	private final DbContext dbContext;

	public DongBoPortThueBaoService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public ArrayList<Map<String, Object>> DsBras(Integer phanVungId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		// dbContext.setConnection(ConnectionManager.Connections.CSS);
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_LAY_DS_BRAS", params);
	}

	public ArrayList<Map<String, Object>> DsDslamTheoBras(Integer phanVungId, Long brasId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_BRAS_ID", brasId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		// dbContext.setConnection(ConnectionManager.Connections.CSS);
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_LAY_DS_DSLAM_THEO_BRAS", params);
	}

	public Map<String, Object> DsThueBaoTrongDanhBa(Integer phanVungId, Long dslamId, Integer pageSize, Integer pageNum)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_DSLAM_ID", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_PAGE_SIZE", pageSize, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_PAGE_NUM", pageNum, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> ds = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_LAY_DS_THUEBAO_TRONG_DB", params);

		// Lấy tổng số rows
		params = new ArrayList<>();
		params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_DSLAM_ID", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> tongSoRows = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_LAY_SO_THUEBAO_TRONG_DB", params);

		Map<String, Object> rs = new HashMap<>();
		rs.put("ds", ds);
		rs.put("tongSoRows", tongSoRows.get(0).get("TONG_SO"));
		return rs;
	}

	public Map<String, Object> DsThueBaoDangDongBo(Integer phanVungId, Long dslamId, Integer pageSize, Integer pageNum)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_DSLAM_ID", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_PAGE_SIZE", pageSize, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_PAGE_NUM", pageNum, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> ds = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_LAY_DS_THUEBAO_DANGDONGBO", params);

		// Lấy tổng số rows
		params = new ArrayList<>();
		params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_DSLAM_ID", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
		ArrayList<Map<String, Object>> tongSoRows = dbContext
				.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_LAY_SO_THUEBAO_DANGDONGBO", params);

		Map<String, Object> rs = new HashMap<>();
		rs.put("ds", ds);
		rs.put("tongSoRows", tongSoRows.get(0).get("TONG_SO"));
		return rs;
	}

	public Boolean ThemDongBoPort(DongBoPort input, Long dslamId, ThongTinNguoiDungModel ttnd) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", ttnd.getPhanVungId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("P_MA_TB", input.getMaTB(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_DICHVUVT_ID", input.getDichVuVTId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("P_LOAITB_ID", input.getLoaiTBId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("P_BRAS_ID_VDC", input.getBrasIdVDC(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("P_DSLAM_ID_VDC", input.getDslamIdVDC(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("P_DSLAM_ID", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(
				new SqlParameter("P_THAMSO", input.getThamSo(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(
				new SqlParameter("P_PORT_ID", input.getPortId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_VCI_VPI_ID", input.getVciVpiId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(
				new SqlParameter("P_SYSTEM", input.getSystem(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RACK", input.getRack(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_SHELF", input.getShelf(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_SLOT", input.getSlot(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_PORT", input.getPort(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_VCI", input.getVci(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_VPI", input.getVpi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_TRANGTHAI", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_NHANVIEN_ID", ttnd.getNhanVienId(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("P_NGUOI_CN", ttnd.getUserName(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("P_MAY_CN", ttnd.getMayCN(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_IP", input.getIp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_SELFID01", input.getSelfId01(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("P_SELFID02", input.getSelfId02(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("P_SELFID03", input.getSelfId03(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		params.add(new SqlParameter("P_SELFID04", input.getSelfId04(), SqlParameter.ParameterDirection.INPUT,
				Types.NUMERIC));
		// dbContext.setConnection(ConnectionManager.Connections.CSS);
		dbContext.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_DONGBO_PORT_INS", params);
		return true;
	}

	public Boolean CapNhatDongBoPort(Integer phanVungId, Long dongBoId, Integer kieu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_DONGBO_ID", dongBoId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_KIEU", kieu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		dbContext.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_DONGBO_PORT_UPD", params);
		return true;
	}

	public Boolean XoaDongBoPort(Integer phanVungId, Long dongBoId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_DONGBO_ID", dongBoId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		dbContext.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_DONGBO_PORT_DEL", params);
		return true;
	}

	/**
	 * Update 202505
	 */
	public Object GetDsThueBaoTrongDanhBa(Number dslamId, String maThueBao, String[] excludeTB, Number pageSize,
			Number pageIndex) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_DSLAM_ID", dslamId, Types.NUMERIC));
		params.add(new SqlParameter("P_MA_TB", (maThueBao == null || maThueBao.trim() == "") ? null : maThueBao,
				Types.NVARCHAR));
		params.add(new SqlParameter("P_NOTIN_MA_TB",
				(excludeTB == null || excludeTB.length == 0) ? null : String.join(",", excludeTB), Types.NVARCHAR));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
		params.add(new SqlParameter("P_PAGE_SIZE", pageSize, Types.NUMERIC));
		params.add(new SqlParameter("P_PAGE_INDEX", pageIndex, Types.NUMERIC));
		params.add(new SqlParameter("P_RESULT", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_GET_DS_THUEBAO_TRONG_DB",
				params);
	}

	public Object CountThueBaoTrongDanhBa(Number dslamId, String maThueBao, String[] excludeTB) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		SqlParameter result = new SqlParameter("P_RESULT", java.sql.Types.NUMERIC);
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_DSLAM_ID", dslamId, Types.NUMERIC));
		params.add(new SqlParameter("P_MA_TB", (maThueBao == null || maThueBao.trim() == "") ? null : maThueBao,
				Types.NVARCHAR));
		params.add(new SqlParameter("P_NOTIN_MA_TB",
				(excludeTB == null || excludeTB.length == 0) ? null : String.join(",", excludeTB), Types.NVARCHAR));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_COUNT_DS_THUEBAO_TRONG_DB", params);
		return result.getOutValue();
	}

	public Object GetDsThueBaoDongBo(Number brasId, Number dslamId, String maThueBao, Number trangThaiId,
			Number pageSize, Number pageIndex) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_BRAS_ID", brasId.equals(0) ? null : brasId, Types.NUMERIC));
		params.add(new SqlParameter("P_DSLAM_ID", dslamId.equals(0) ? null : dslamId, Types.NUMERIC));
		params.add(new SqlParameter("P_MA_TB", (maThueBao == null || maThueBao.trim() == "") ? null : maThueBao,
				Types.NVARCHAR));
		params.add(new SqlParameter("P_TRANGTHAI_ID", trangThaiId, Types.NUMERIC));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
		params.add(new SqlParameter("P_PAGE_SIZE", pageSize, Types.NUMERIC));
		params.add(new SqlParameter("P_PAGE_INDEX", pageIndex, Types.NUMERIC));
		params.add(new SqlParameter("P_RESULT", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_GET_DS_THUEBAO_DONGBO", params);
	}

	public Object CountThueBaoDongBo(Number brasId, Number dslamId, String maThueBao, Number trangThaiId)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		SqlParameter result = new SqlParameter("P_RESULT", java.sql.Types.NUMERIC);
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_BRAS_ID", brasId.equals(0) ? null : brasId, Types.NUMERIC));
		params.add(new SqlParameter("P_DSLAM_ID", dslamId.equals(0) ? null : dslamId, Types.NUMERIC));
		params.add(new SqlParameter("P_MA_TB", (maThueBao == null || maThueBao.trim() == "") ? null : maThueBao,
				Types.NVARCHAR));
		params.add(new SqlParameter("P_TRANGTHAI_ID", trangThaiId, Types.NUMERIC));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_COUNT_DS_THUEBAO_DONGBO", params);
		return result.getOutValue();
	}

	public Boolean ThemDongBoPortTB(DongBoPortDto input) throws AppSqlException {
		Boolean result = false;
		try {
			AppRequestContext context = AppRequestContext.getCurrent();
			ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
			params.add(new SqlParameter("P_PHANVUNG_ID", context.getPhanVungID(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(
					new SqlParameter("P_MA_TB", input.getMaTB(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
			params.add(new SqlParameter("P_DICHVUVT_ID", input.getDichVuVTId(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_LOAITB_ID", input.getLoaiTBId(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_BRAS_ID_VDC", input.getBrasIdVDC(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_DSLAM_ID_VDC", input.getDslamIdVDC(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_DSLAM_ID", input.getDslamId(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_THAMSO", input.getThamSo(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_PORT_ID", input.getPortId(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_VCI_VPI_ID", input.getVciVpiId(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_SYSTEM", input.getSystem(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(
					new SqlParameter("P_RACK", input.getRack(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("P_SHELF", input.getShelf(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(
					new SqlParameter("P_SLOT", input.getSlot(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(
					new SqlParameter("P_PORT", input.getPort(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("P_VCI", input.getVci(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("P_VPI", input.getVpi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("P_TRANGTHAI", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
			params.add(new SqlParameter("P_NHANVIEN_ID", context.getNhanVienID(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_NGUOI_CN", context.getUserName(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_MAY_CN", context.getClientName(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_IP", context.getClientIpAddress(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_SELFID01", input.getSelfId01(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_SELFID02", input.getSelfId02(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_SELFID03", input.getSelfId03(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			params.add(new SqlParameter("P_SELFID04", input.getSelfId04(), SqlParameter.ParameterDirection.INPUT,
					Types.NUMERIC));
			dbContext.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_DONGBO_PORT_INS", params);
			result = true;
		} catch (Exception e) {

		}
		return result;
	}

	public Boolean XoaDongBoPortTB(Number dongBoID)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		SqlParameter result = new SqlParameter("P_RESULT", java.sql.Types.NUMERIC);
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_DONGBO_ID", dongBoID, Types.NUMERIC));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_DONGBO_PORT_DELETE", params);
		return result.getOutValue().toString().equals("1");
	}

	public Boolean XoaDsDongBoPortTB(Number[] ids)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		SqlParameter result = new SqlParameter("P_RESULT", java.sql.Types.NUMERIC);
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_DS_DONGBO_ID", StringUtils.join(ids, ";"), Types.VARCHAR));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_DONGBO_PORT_DELETE_ITEMS", params);
		return result.getOutValue().toString().equals("1");
	}

	public Boolean HoanThienDongBoPortTB(Number dongBoID)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		SqlParameter result = new SqlParameter("P_RESULT", java.sql.Types.NUMERIC);
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_DONGBO_ID", dongBoID, Types.NUMERIC));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_DONGBO_PORT_HOANTHIEN", params);
		return result.getOutValue().toString().equals("1");
	}

	public Boolean HoanThienDsDongBoPortTB(Number[] ids)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		SqlParameter result = new SqlParameter("P_RESULT", java.sql.Types.NUMERIC);
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_DS_DONGBO_ID", StringUtils.join(ids, ";"), Types.VARCHAR));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_DONGBO_PORT_HOANTHIEN_ITEMS", params);
		return result.getOutValue().toString().equals("1");
	}

	public Boolean KichHoatDongBoPortTB(Number dongBoID)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		SqlParameter result = new SqlParameter("P_RESULT", java.sql.Types.NUMERIC);
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_DONGBO_ID", dongBoID, Types.NUMERIC));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_DONGBO_PORT_KICHHOAT", params);
		return result.getOutValue().toString().equals("1");
	}
	
	public Boolean KichHoatDsDongBoPortTB(Number[] ids)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		SqlParameter result = new SqlParameter("P_RESULT", java.sql.Types.NUMERIC);
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_DS_DONGBO_ID", StringUtils.join(ids, ";"), Types.VARCHAR));
		params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_DONGBO_PORT_KICHHOAT_ITEMS", params);
		return result.getOutValue().toString().equals("1");
	}

	public Object GetDsBras() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_RESULT", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_GET_DS_BRAS", params);
	}
	
	public Object GetDsDslam(Number brasID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("P_BRAS_ID", brasID, Types.NUMERIC));
		params.add(new SqlParameter("P_RESULT", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DONGBO_PORT_THUEBAO.SP_GET_DS_DSLAM_THEO_BRAS", params);
	}
	
}
