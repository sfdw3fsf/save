package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoLongQuayDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoPhongDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriLongQuayBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriLongQuayDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriPhongBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriPhongDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriRackBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriRackDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriThietBiBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriThietBiDto;

@Service
public class KhongGianService {
	private final DbContext dbContext;

	public KhongGianService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public Object getIDCTheoNhanVien() throws AppSqlException {
		return this.getIDCTheoNhanVien(AppRequestContext.getCurrent().getNhanVienID());
	}

	public Object getIDCTheoNhanVien(Number nhanVienID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_nhanvien_id", nhanVienID, Types.NUMERIC));
		return dbContext.executeFunction("ECMS.PKG_IDC_KHONGGIAN.FN_GET_IDC_THEO_NHANVIEN", java.sql.Types.INTEGER,
				params);
	}

	public Object getDsRackChuaLapDat(Number phongID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_RACK_CHUA_LAPDAT", params);
	}

	public Object getDsThietBiChuaCoViTri(Number phongID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_THIETBI_CHUACO_VITRI", params);
	}

	public Object getDsPhongChuaCoViTri(Number matSanID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_matsan_id", matSanID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_PHONG_CHUACO_VITRI", params);
	}

	public Object getDsLongQuayChuaCoViTri(Number phongID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_LONGQUAY_CHUACO_VITRI", params);
	}

	public Object getDsRackDaLapDat(Number phongID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_RACK_DA_LAPDAT", params);
	}

	public Object getDsThietBiDaCoViTri(Number phongID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_THIETBI_DACO_VITRI", params);
	}

	public Object getDsPhongDaCoViTri(Number matSanID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_matsan_id", matSanID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_PHONG_DACO_VITRI", params);
	}

	public Object getDsLongQuayDaCoViTri(Number phongID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_LONGQUAY_DACO_VITRI", params);
	}

	public Object capNhatLongQuayChoRack(Number rackID, Number longQuayID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_rack_id", rackID, Types.NUMERIC));
		params.add(new SqlParameter("p_longquay_id", longQuayID, Types.NUMERIC));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_UPDATE_RACK_LONGQUAY", params);
		return result.getOutValue();
	}

	public Object xoaLongQuayChoRack(Number rackID) throws AppSqlException {
		return this.capNhatLongQuayChoRack(rackID, null);
	}

	public Object capNhatLongQuayChoThietBi(Number thietBiID, Number longQuayID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_thietbi_id", thietBiID, Types.NUMERIC));
		params.add(new SqlParameter("p_longquay_id", longQuayID, Types.NUMERIC));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_UPDATE_THIETBI_LONGQUAY", params);
		return result.getOutValue();
	}

	public Object xoaLongQuayChoThietBi(Number thietBiID) throws AppSqlException {
		return this.capNhatLongQuayChoThietBi(thietBiID, null);
	}

	/**
	 * Lấy cây sơ đồ hạ tầng
	 */
	public Object getCaySoDoIDC(Number nhanVienID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_nhanvien_id", nhanVienID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_GET_CAY_SODO_IDC", params);
	}

	/**
	 * #region Vị trí tọa độ phòng
	 */

	public Object getToaDoPhong(Number phongID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_TOADO_PHONG_GET", params);
	}

	public Object insertToaDoPhong(ToaDoPhongDto toaDoPhong) throws AppSqlException {
		return this.insertToaDoPhong(toaDoPhong.getPhongID(), toaDoPhong.getDsToaDo());
	}

	public Object insertToaDoPhong(Number phongID, ToaDoDto[] dsToaDo) throws AppSqlException {
		ArrayList<Object> result = new ArrayList<Object>();
		for (int i = 0; i < dsToaDo.length; i++) {
			result.add(this.insertToaDoPhong(phongID, dsToaDo[i].getToaDoX(), dsToaDo[i].getToaDoY(), i + 1, null));
		}
		return result;
	}

	public Object insertToaDoPhong(Number phongID, ToaDoDto toaDo) throws AppSqlException {
		return this.insertToaDoPhong(phongID, toaDo, null);
	}

	public Object insertToaDoPhong(Number phongID, ToaDoDto toaDo, Number thuTu) throws AppSqlException {
		return this.insertToaDoPhong(phongID, toaDo.getToaDoX(), toaDo.getToaDoY(), thuTu, null);
	}

	public Object insertToaDoPhong(Number phongID, Number toaDoX, Number toaDoY, Number thuTu, String moTa)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_x", toaDoX, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_y", toaDoY, Types.NUMERIC));
		params.add(new SqlParameter("p_thutu", thuTu, Types.NUMERIC));
		params.add(new SqlParameter("p_mota", moTa, Types.NVARCHAR));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_TOADO_PHONG_INSERT", params);
		return result.getOutValue();
	}

	public Object deleteToaDoPhong(Number phongID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_TOADO_PHONG_DELETE", params);
		return result.getOutValue();
	}

	public Object updateToaDoPhong(ToaDoPhongDto toaDoPhong) throws AppSqlException {
		this.deleteToaDoPhong(toaDoPhong.getPhongID());
		return this.insertToaDoPhong(toaDoPhong.getPhongID(), toaDoPhong.getDsToaDo());
	}

	public Object updateViTriPhong(Number phongID, Number toaDoX, Number toaDoY) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_x", toaDoX, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_y", toaDoY, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_VITRI_PHONG_UPDATE", params);
		return result.getOutValue();
	}

	public Object deleteViTriPhong(Number phongID) throws AppSqlException {
		return this.updateViTriPhong(phongID, null, null);
	}

	public Object updateViTriPhongBatch(ViTriPhongBatchDto batchDto) throws AppSqlException {
		ViTriPhongDto[] data = batchDto.getData();
		int successCount = 0;
		int failCount = 0;
		StringBuilder errors = new StringBuilder();

		for (ViTriPhongDto item : data) {
			try {
				this.updateViTriPhong(
						item.getPhongID(),
						item.getToaDoX(),
						item.getToaDoY());
				successCount++;
			} catch (AppSqlException e) {
				failCount++;
				errors.append("PhongID ").append(item.getPhongID()).append(": ").append(e.getMessage()).append("; ");
			}
		}

		if (failCount > 0) {
			throw new AppSqlException("Cập nhật hoàn thành " + successCount + " phòng, thất bại " + failCount
					+ " phòng. Lỗi: " + errors.toString());
		}

		return successCount;
	}

	/**
	 * #endregion Vị trí phòng
	 */

	/**
	 * #region Vị trí tọa độ lồng quay
	 */

	public Object getToaDoLongQuay(Number longQuayID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_longquay_id", longQuayID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHONGGIAN.SP_TOADO_LONGQUAY_GET", params);
	}

	public Object insertToaDoLongQuay(ToaDoLongQuayDto toaDoLongQuay) throws AppSqlException {
		return this.insertToaDoLongQuay(toaDoLongQuay.getLongQuayID(), toaDoLongQuay.getDsToaDo());
	}

	public Object insertToaDoLongQuay(Number longQuayID, ToaDoDto[] dsToaDo) throws AppSqlException {
		ArrayList<Object> result = new ArrayList<Object>();
		for (int i = 0; i < dsToaDo.length; i++) {
			result.add(
					this.insertToaDoLongQuay(longQuayID, dsToaDo[i].getToaDoX(), dsToaDo[i].getToaDoY(), i + 1, null));
		}
		return result;
	}

	public Object insertToaDoLongQuay(Number longQuayID, ToaDoDto toaDo) throws AppSqlException {
		return this.insertToaDoLongQuay(longQuayID, toaDo, null);
	}

	public Object insertToaDoLongQuay(Number longQuayID, ToaDoDto toaDo, Number thuTu) throws AppSqlException {
		return this.insertToaDoLongQuay(longQuayID, toaDo.getToaDoX(), toaDo.getToaDoY(), thuTu, null);
	}

	public Object insertToaDoLongQuay(Number longQuayID, Number toaDoX, Number toaDoY, Number thuTu, String moTa)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_longquay_id", longQuayID, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_x", toaDoX, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_y", toaDoY, Types.NUMERIC));
		params.add(new SqlParameter("p_thutu", thuTu, Types.NUMERIC));
		params.add(new SqlParameter("p_mota", moTa, Types.NVARCHAR));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_TOADO_LONGQUAY_INSERT", params);
		return result.getOutValue();
	}

	public Object deleteToaDoLongQuay(Number longQuayID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_longquay_id", longQuayID, Types.NUMERIC));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_TOADO_LONGQUAY_DELETE", params);
		return result.getOutValue();
	}

	public Object updateToaDoLongQuay(ToaDoLongQuayDto toaDoLongQuay) throws AppSqlException {
		this.deleteToaDoLongQuay(toaDoLongQuay.getLongQuayID());
		return this.insertToaDoLongQuay(toaDoLongQuay.getLongQuayID(), toaDoLongQuay.getDsToaDo());
	}

	public Object updateViTriLongQuay(Number longQuayID, Number toaDoX, Number toaDoY) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_longquay_id", longQuayID, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_x", toaDoX, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_y", toaDoY, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_VITRI_LONGQUAY_UPDATE", params);
		return result.getOutValue();
	}

	public Object deleteViTriLongQuay(Number longQuayID) throws AppSqlException {
		return this.updateViTriLongQuay(longQuayID, null, null);
	}

	public Object updateViTriLongQuayBatch(ViTriLongQuayBatchDto batchDto) throws AppSqlException {
		ViTriLongQuayDto[] data = batchDto.getData();
		int successCount = 0;
		int failCount = 0;
		StringBuilder errors = new StringBuilder();

		for (ViTriLongQuayDto item : data) {
			try {
				this.updateViTriLongQuay(
						item.getLongQuayID(),
						item.getToaDoX(),
						item.getToaDoY());
				successCount++;
			} catch (AppSqlException e) {
				failCount++;
				errors.append("LongQuayID ").append(item.getLongQuayID()).append(": ").append(e.getMessage())
						.append("; ");
			}
		}

		if (failCount > 0) {
			throw new AppSqlException("Cập nhật hoàn thành " + successCount + " lồng quay, thất bại " + failCount
					+ " lồng quay. Lỗi: " + errors.toString());
		}

		return successCount;
	}

	/**
	 * #endregion Vị trí lồng quay
	 */

	/**
	 * #region Vị trí rack và thiết bị
	 */

	public Object updateViTriRack(Number rackID, Number toaDoX, Number toaDoY, Number huong) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_rack_id", rackID, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_x", toaDoX, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_y", toaDoY, Types.NUMERIC));
		params.add(new SqlParameter("p_huong", huong, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_VITRI_RACK_UPDATE", params);
		return result.getOutValue();
	}

	public Object deleteViTriRack(Number rackID) throws AppSqlException {
		return this.updateViTriRack(rackID, null, null, null);
	}

	public Object updateViTriRackBatch(ViTriRackBatchDto batchDto) throws AppSqlException {
		ViTriRackDto[] data = batchDto.getData();
		int successCount = 0;
		int failCount = 0;
		StringBuilder errors = new StringBuilder();

		for (ViTriRackDto item : data) {
			try {
				this.updateViTriRack(
						item.getRackID(),
						item.getToaDoX(),
						item.getToaDoY(),
						item.getHuongNgang() ? 1 : 0);
				successCount++;
			} catch (AppSqlException e) {
				failCount++;
				errors.append("RackID ").append(item.getRackID()).append(": ").append(e.getMessage()).append("; ");
			}
		}

		if (failCount > 0) {
			throw new AppSqlException("Cập nhật hoàn thành " + successCount + " rack, thất bại " + failCount
					+ " rack. Lỗi: " + errors.toString());
		}

		return successCount;
	}

	public Object updateViTriThietBi(Number thietBiID, Number toaDoX, Number toaDoY) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_thietbi_id", thietBiID, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_x", toaDoX, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_y", toaDoY, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_IDC_KHONGGIAN.SP_VITRI_THIETBI_UPDATE", params);
		return result.getOutValue();
	}

	public Object deleteViTriThietBi(Number thietBiID) throws AppSqlException {
		return this.updateViTriThietBi(thietBiID, null, null);
	}

	public Object updateViTriThietBiBatch(ViTriThietBiBatchDto batchDto) throws AppSqlException {
		ViTriThietBiDto[] data = batchDto.getData();
		int successCount = 0;
		int failCount = 0;
		StringBuilder errors = new StringBuilder();

		for (ViTriThietBiDto item : data) {
			try {
				this.updateViTriThietBi(
						item.getThietBiID(),
						item.getToaDoX(),
						item.getToaDoY());
				successCount++;
			} catch (AppSqlException e) {
				failCount++;
				errors.append("ThietBiID ").append(item.getThietBiID()).append(": ").append(e.getMessage())
						.append("; ");
			}
		}

		if (failCount > 0) {
			throw new AppSqlException("Cập nhật hoàn thành " + successCount + " thiết bị, thất bại " + failCount
					+ " thiết bị. Lỗi: " + errors.toString());
		}

		return successCount;
	}

	/**
	 * #endregion Vị trí rack và thiết bị
	 */

}
