package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLongQuayDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class LongQuayService {
	private final DbContext dbContext;

	public LongQuayService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	// Lấy danh sách Lồng quây
	public Object getListLongQuay(DanhMucLongQuayDtoIn input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_TEN", input.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_TRANGTHAI",  input.getTrangThaiVh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_SOHUU_ID",  input.getDonviSHId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_KHOA_ID", input.getKhoaId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

		return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_LIST_LONGQUAY", params);
	}

	// Thêm  mới/ Cập nhật Lồng Quầy
	public  Object saveLongQuay(DanhMucLongQuayDtoIn input) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_LONGQUAY_ID", input.getLongQuayId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_LOAILQ_ID", input.getLoaiLqId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_PHONG_ID", input.getPhongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_TEN", input.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_CHIEUDAI", input.getChieuDai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CHIEURONG", input.getChieuRong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CHIEUCAO", input.getChieuCao(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CAMERA", input.getCamera(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_KHOA_ID", input.getKhoaId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_TRANGTHAI_VH", input.getTrangThaiVh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_GHICHU", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_TENTAT", input.getTenTat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_MOTA", input.getMoTa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_SUDUNG", input.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_TOADO_X", input.getToaDoX(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_TOADO_Y", input.getToaDoY(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_NGUOI_CN", input.getNguoiCn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		params.add(new SqlParameter("P_SOHUU_ID", input.getDonviSHId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_DS_RACK", input.getDsRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		SqlParameter result = new SqlParameter("P_ID_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
		params.add(result);

		dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_SAVE_LONGQUAY", params);
		return result.getOutValue();

	}

	//Chi tiết Lồng quây
	public ArrayList<Map<String, Object>> getChiTietLongQuay(Number longQuayId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_LONGQUAY_ID", longQuayId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

		return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DETAIL_LONGQUAY", params);
	}

	// Xóa lồng quây
	public Object deleteLongQuay(Number longQuayId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_LONGQUAY_ID", longQuayId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		//params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
		params.add(result);

		dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_DELETE_LONGQUAY", params);
		return result.getOutValue();
	}

	// Danh sách Rack chưa lắp đặt vào lồng quây
	public ArrayList<Map<String, Object>> getListRackChuaSD(Number idcId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_PHONG_ID", idcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_RACK_CHUA_LAPDAT", params);
	}

	// Danh sách Phòng/Zone theo Mặt sàn
	public ArrayList<Map<String, Object>> getListPhongByMatSan(Number idcId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_MATSAN_ID", idcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_PHONG_BY_MATSAN", params);
	}

	// Tháo gỡ Rack ra khỏi lồng quây
	public Object thaoGoRack(Number rackId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_RACK_ID", rackId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
		SqlParameter result = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
		params.add(result);

		dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_THAO_GO_RACK_KHOI_LONGQUAY", params);
		return result.getOutValue();
	}

	// Danh sách Rack trong lồng quây
	public ArrayList<Map<String, Object>> getListRackInLongQuay(Number longQuayId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_LONGQUAY_ID", longQuayId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_RACKS_IN_LONGQUAY", params);
	}

	// Danh sách sở hữu Rack
	public ArrayList<Map<String, Object>> getListSoHuuRack() throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_LIST_SOHUU", params);
	}

	// Danh sách thiết bị trong lồng quây
	public ArrayList<Map<String, Object>> getListTBiInLongQuay(Number longQuayId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_LONGQUAY_ID", longQuayId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_THIETBI_TRONG_LONGQUAY", params);
	}

	// Danh sách thanh nguồn trong lồng quây
	public ArrayList<Map<String, Object>> getListThanhNguonInLongQuay(Number longQuayId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_LONGQUAY_ID", longQuayId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_THANHNGUON_TRONG_LONGQUAY", params);
	}
	//Lịch sử Lồng quây
	public Object getLichSuLongQuay(Long longQuayId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_LONGQUAY_ID", longQuayId,SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_HISTORY_LONGQUAY",
				params);
	}
	// Danh sách khách hàng liên quan lồng quây
	public ArrayList<Map<String, Object>> getListKhachHang(Number longQuayId) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("P_LONGQUAY_ID", longQuayId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
		params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_KHACHHANG_LONGQUAY", params);
	}
}
