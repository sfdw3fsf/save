package vn.vnpt.vnptit.ecms.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

@Service
public class ThongSoInternetService {
	private final DbContext dbContext;

	public ThongSoInternetService(DbContext dbContext) {
		this.dbContext = dbContext;
	}
	
	public Object kiemTraChonPort(Number hdtb_id, Number thuebao_id, Number port_id, Number vci_vpi_id) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("vphanvung_id", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("vhdtb_id", hdtb_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("vthuebao_id", thuebao_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("vport_id", port_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("vvci_vpi_id", vci_vpi_id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("vreturnds", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("CSS.KIEMTRA_CHON_PORT", params);
	}
	
	public Object getThongTinTocDoADSL(Number id, Number kieu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_id", id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_kieu", kieu, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("CSS.pkg_thaydoi_thongso_internet.sp_lay_tt_tocdo_adsl", params);
	}
	
	public Object getThongTinTocDoADSLTheoDslam(Number id, Number kieu, Number dslamID) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_id", id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_kieu", kieu, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_dslam_id", kieu, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToListMap("CSS.pkg_thaydoi_thongso_internet.sp_lay_tt_tocdo_adsl_theo_dslam", params);
	}
	
	public Object getDoiTuongID(Number id, Number kieu) throws AppSqlException {
		ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_id", id, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_kieu", kieu, java.sql.Types.INTEGER));
		params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
		return dbContext.executeSpWithCursorToMap("CSS.pkg_thaydoi_thongso_internet.sp_lay_doituong_id", params);
	}
	
}