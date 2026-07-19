package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.HanhLangDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ToaDoHanhLangDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriHanhLangBatchDto;
import vn.vnpt.vnptit.ecms.dto.idc.khonggian.ViTriHanhLangDto;

@Service
public class HanhLangService {
	private final DbContext dbContext;

	public HanhLangService(DbContext dbContext) {
		this.dbContext = dbContext;
	}

	public Object getDsHanhLangChuaCoViTri(Number phongID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HANHLANG.SP_GET_HANHLANG_CHUACO_VITRI", params);
	}

	public Object getDsHanhLangDaCoViTri(Number phongID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HANHLANG.SP_GET_HANHLANG_DACO_VITRI", params);
	}

	public Object getToaDoHanhLang(Number hanhLangID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_hanhlang_id", hanhLangID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HANHLANG.SP_TOADO_HANHLANG_GET", params);
	}

	public Object insertToaDoHanhLang(ToaDoHanhLangDto toaDoHanhLang) throws AppSqlException {
		return this.insertToaDoHanhLang(toaDoHanhLang.getHanhLangID(), toaDoHanhLang.getDsToaDo());
	}

	public Object insertToaDoHanhLang(Number hanhLangID, ToaDoDto[] dsToaDo) throws AppSqlException {
		ArrayList<Object> result = new ArrayList<Object>();
		for (int i = 0; i < dsToaDo.length; i++) {
			result.add(
					this.insertToaDoHanhLang(hanhLangID, dsToaDo[i].getToaDoX(), dsToaDo[i].getToaDoY(), i + 1, null));
		}
		return result;
	}

	public Object insertToaDoHanhLang(Number hanhLangID, ToaDoDto toaDo) throws AppSqlException {
		return this.insertToaDoHanhLang(hanhLangID, toaDo, null);
	}

	public Object insertToaDoHanhLang(Number hanhLangID, ToaDoDto toaDo, Number thuTu) throws AppSqlException {
		return this.insertToaDoHanhLang(hanhLangID, toaDo.getToaDoX(), toaDo.getToaDoY(), thuTu, null);
	}

	public Object insertToaDoHanhLang(Number hanhLangID, Number toaDoX, Number toaDoY, Number thuTu, String moTa)
			throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_hanhlang_id", hanhLangID, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_x", toaDoX, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_y", toaDoY, Types.NUMERIC));
		params.add(new SqlParameter("p_thutu", thuTu, Types.NUMERIC));
		params.add(new SqlParameter("p_mota", moTa, Types.NVARCHAR));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_HANHLANG.SP_TOADO_HANHLANG_INSERT", params);
		return result.getOutValue();
	}

	public Object deleteToaDoHanhLang(Number hanhLangID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_hanhlang_id", hanhLangID, Types.NUMERIC));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_HANHLANG.SP_TOADO_HANHLANG_DELETE", params);
		return result.getOutValue();
	}

	public Object updateToaDoHanhLang(ToaDoHanhLangDto toaDoHanhLang) throws AppSqlException {
		this.deleteToaDoHanhLang(toaDoHanhLang.getHanhLangID());
		return this.insertToaDoHanhLang(toaDoHanhLang.getHanhLangID(), toaDoHanhLang.getDsToaDo());
	}

	public Object updateViTriHanhLang(Number hanhLangID, Number toaDoX, Number toaDoY) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_hanhlang_id", hanhLangID, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_x", toaDoX, Types.NUMERIC));
		params.add(new SqlParameter("p_toado_y", toaDoY, Types.NUMERIC));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_HANHLANG.SP_VITRI_HANHLANG_UPDATE", params);
		return result.getOutValue();
	}

	public Object deleteViTriHanhLang(Number hanhLangID) throws AppSqlException {
		return this.updateViTriHanhLang(hanhLangID, null, null);
	}

	public Object updateViTriHanhLangBatch(ViTriHanhLangBatchDto batchDto) throws AppSqlException {
		ViTriHanhLangDto[] data = batchDto.getData();
		int successCount = 0;
		int failCount = 0;
		StringBuilder errors = new StringBuilder();

		for (ViTriHanhLangDto item : data) {
			try {
				this.updateViTriHanhLang(
						item.getHanhLangID(),
						item.getToaDoX(),
						item.getToaDoY());
				successCount++;
			} catch (AppSqlException e) {
				failCount++;
				errors.append("HanhLangID ").append(item.getHanhLangID()).append(": ").append(e.getMessage())
						.append("; ");
			}
		}

		if (failCount > 0) {
			throw new AppSqlException("Cập nhật hoàn thành " + successCount + " hành lang, thất bại " + failCount
					+ " hành lang. Lỗi: " + errors.toString());
		}

		return successCount;
	}

	public Object getHanhLangById(Number hanhLangID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_hanhlang_id", hanhLangID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HANHLANG.SP_HANHLANG_GET_BYID", params);
	}

	public Object getAllHanhLangByPhongId(Number phongID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_phong_id", phongID, Types.NUMERIC));
		params.add(new SqlParameter("p_result", Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HANHLANG.SP_HANHLANG_GET_ALLBYPHONGID", params);
	}

	public Object deleteHanhLang(Number hanhLangID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_hanhlang_id", hanhLangID, Types.NUMERIC));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_HANHLANG.SP_HANHLANG_DELETE", params);
		return result.getOutValue();
	}

	public Object upsertHanhLang(HanhLangDto hanhLang) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<>();
		params.add(new SqlParameter("p_hanhlang_id", hanhLang.getHanhLangID(), Types.NUMERIC));
		params.add(new SqlParameter("p_phong_id", hanhLang.getPhongID(), Types.NUMERIC));
		params.add(new SqlParameter("p_ten", hanhLang.getTen(), Types.NVARCHAR));
		params.add(new SqlParameter("p_tentat", hanhLang.getTenTat(), Types.NVARCHAR));
		params.add(new SqlParameter("p_nong", hanhLang.getNong(), Types.NUMERIC));
		params.add(new SqlParameter("p_ghichu", hanhLang.getGhiChu(), Types.NVARCHAR));
		params.add(new SqlParameter("p_mota", hanhLang.getMoTa(), Types.NVARCHAR));
		params.add(new SqlParameter("p_sudung", hanhLang.getSuDung(), Types.NVARCHAR));
		params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
		SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
		params.add(result);
		dbContext.executeStoredProcedure("ECMS.PKG_HANHLANG.SP_HANHLANG_UPSERT", params);
		return result.getOutValue();
	}
}
