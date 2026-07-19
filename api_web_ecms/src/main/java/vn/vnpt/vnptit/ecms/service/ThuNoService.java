package vn.vnpt.vnptit.ecms.service;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.ChuyenDSKhoaMoMaySangHopDongKhoaMoInput;

@Service
public class ThuNoService {
	private final DbContext dbContext;

	public ThuNoService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public Object chuyenDSKhoaMoMaySangHopDongKhoaMo(ChuyenDSKhoaMoMaySangHopDongKhoaMoInput input)
			throws AppSqlException {
		Object rs = null;
		if (!(input.getChieuKhoa() != 1 && input.getChieuKhoa() != 2)) {
			List<Object> result = new ArrayList<>();
			List<String> tempArr = this.convertListObjectToListJsonByLength(input.getDs(), 100);
			for (String item : tempArr) {
				result.add(this.chuyenDSKhoaMoMaySangHopDongKhoaMo(item, input.getKieuYC(), input.getChieuKhoa(),
						this.formatList(input.getLoaiTBId()), input.getDichVuVTId(), input.getDonViId(),
						input.getGhiChu(), input.getLoaiChem(), input.getQuyTrinhID()));
			}
			rs = result.get(0);
		}
		return rs;
	}

	private Object chuyenDSKhoaMoMaySangHopDongKhoaMo(String p_ds, Number p_kieu_yc, Number p_chieukhoa,
			String p_loaitb_tk_id, Number p_dichvuvt_id, Number p_donvi_id, String p_ghichu, Number p_loaichem,
			Number p_quytrinh_id) throws AppSqlException {
		AppRequestContext context = AppRequestContext.getCurrent();
		List<SqlParameter> params = new ArrayList<>();
		Date current = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		params.add(new SqlParameter("p_ds", p_ds, Types.CLOB));
		params.add(new SqlParameter("p_kieu_yc", p_kieu_yc, Types.NUMERIC));
		params.add(new SqlParameter("p_chieukhoa", p_chieukhoa, Types.NUMERIC));
		params.add(new SqlParameter("p_loaitb_tk_id", p_loaitb_tk_id, Types.NVARCHAR));
		params.add(new SqlParameter("p_dichvuvt_id", p_dichvuvt_id, Types.NUMERIC));
		params.add(new SqlParameter("p_donvi_id", p_donvi_id, Types.NUMERIC));
		params.add(new SqlParameter("p_nhanvien_id", context.getNhanVienID(), Types.NUMERIC));
		params.add(new SqlParameter("p_ghichu", p_ghichu, Types.NVARCHAR));
		params.add(new SqlParameter("p_loaichem", p_loaichem, Types.NUMERIC));
		params.add(new SqlParameter("p_ngay_cn", dateFormat.format(current), Types.NVARCHAR));
		params.add(new SqlParameter("p_nguoi_cn", context.getUserName(), Types.NVARCHAR));
		params.add(new SqlParameter("p_may_cn", context.getClientName(), Types.NVARCHAR));
		params.add(new SqlParameter("p_quytrinh_id", p_quytrinh_id, Types.NUMERIC));
		params.add(new SqlParameter("p_phanvung_id", context.getPhanVungID(), Types.NUMERIC));
		params.add(new SqlParameter("cur_returnds", Types.REF_CURSOR));
		ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap(
				"QLTN.PKG_XULY_THUEBAO6.SP_CHUYEN_DANHSACH_KHOAMOMAY_SANG_HOPDONG_KHOAMO_v3", params);
		return result.get(0).get("CODE_MESSAGE");
	}

	private <T> List<String> convertListObjectToListJsonByLength(List<T> list, final int length) {
		List<List<T>> parts = new ArrayList<List<T>>();
		List<String> datas = new ArrayList<String>();
		final int N = list.size();
		for (int i = 0; i < N; i += length) {
			parts.add(new ArrayList<T>(list.subList(i, Math.min(N, i + length))));
		}
		ObjectMapper obj = new ObjectMapper();
		for (List<T> item : parts) {
			try {
				datas.add(obj.writeValueAsString(item));
			} catch (Exception e) {
			}
		}
		return datas;
	}

	private String formatList(List<?> data) {
		if (data != null && data.size() > 0) {
			return data.toString().substring(1, data.toString().length() - 1);
		}
		return null;
	}

}
