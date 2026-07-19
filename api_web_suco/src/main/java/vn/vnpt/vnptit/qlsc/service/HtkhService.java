package vn.vnpt.vnptit.qlsc.service;

import java.sql.Types;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

@Service
public class HtkhService {

    private final DbContext dbContext;

    public HtkhService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object GetThongTinHTKH(Number htkhID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HTKH_ID", htkhID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("QLTN.PKG_XULY_HOTRO.SP_GET_THONGTIN_HTKH", params);
    }

    public Object GetThongTinHTKHTheoPhieu(Number phieuhtID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUHT_ID", phieuhtID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("QLTN.PKG_XULY_HOTRO.SP_GET_THONGTIN_HTKH_THEO_PHIEU", params);
    }

    public Object GetDsHuongGiao(Number quyTrinhID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_QUYTRINH_ID", quyTrinhID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLTN.PKG_XULY_HOTRO.SP_GET_DS_HUONGGIAO", params);
    }

    public Object GetDsDonViTheoHuongGiao(Number huongGiaoID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HUONGGIAO_ID", huongGiaoID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLTN.PKG_XULY_HOTRO.SP_GET_DS_DONVI_THEO_HUONGGIAO_ID", params);
    }

    public Object GetDsDonViChuaGiaoHuongGiao(Number htkhID, Number huongGiaoID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HTKH_ID", htkhID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HUONGGIAO_ID", huongGiaoID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLTN.PKG_XULY_HOTRO.SP_GET_DS_DONVI_THEO_HUONGGIAO_HTKH_ID", params);
    }

    public Object GetDsDonViChuaGiaoHuongGiaoPhoiHop() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("qltn.htkh_xuly_thuebao.sp_donvi_phoihop", params);
    }

    public Object GetDsPhieuDaGiao(Number htkhID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HTKH_ID", htkhID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLTN.PKG_XULY_HOTRO.SP_GET_DS_PHIEU_DAGIAO_THEO_HTKH_ID", params);
    }

    public Object GetTrangThaiPHCuaPhieu(Number phieuhtID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUHT_ID", phieuhtID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("QLTN.PKG_XULY_HOTRO.SP_GET_TTPH_PHIEU", params);
    }

    public Object KiemTraGiaoDonVi(Number htkhID, Number donViID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HTKH_ID", htkhID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", donViID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        SqlParameter paramResult = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(paramResult);
        dbContext.executeStoredProcedure("QLTN.PKG_XULY_HOTRO.SP_KIEMTRA_GIAO_DONVI", params);
        return !("0".equals(paramResult.getOutValue().toString()));
    }

    public Object GiaoPhieu(Number htkhID, String noiDung, Number phieuChaID, Number huongGiaoID, String soCongVan, Number[] dsDonViNhanID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        AppRequestContext context = AppRequestContext.getCurrent();
        params.add(new SqlParameter("P_PHANVUNG_ID", context.getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HTKH_ID", htkhID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_ND_GIAO", noiDung, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_DONVI_GIAO_ID", context.getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_GIAO_ID", context.getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEU_CHA_ID", phieuChaID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HUONGGIAO_ID", huongGiaoID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SOCONGVAN", soCongVan, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_MAY_CN", htkhID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", htkhID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DS_DONVI_NHAN_ID", StringUtils.join(dsDonViNhanID, ","), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DS_PHIEUHT_ID", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLTN.PKG_XULY_HOTRO.SP_GIAOPHIEU_HOTRO", params);
    }

    public Object XoaPhieu(Number phieuhtID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUHT_ID", phieuhtID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        SqlParameter paramResult = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(paramResult);
        dbContext.executeStoredProcedure("QLTN.PKG_XULY_HOTRO.SP_XOAPHIEU_HOTRO", params);
        return "1".equals(paramResult.getOutValue().toString());
    }

    public Object CapNhatDonVi(Number htkhID, Number huongGiaoID, Number donViID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HTKH_ID", htkhID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HUONGGIAO_ID", huongGiaoID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", donViID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        SqlParameter paramResult = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(paramResult);
        dbContext.executeStoredProcedure("QLTN.PKG_XULY_HOTRO.SP_CAPNHAT_DONVINHAN", params);
        return "1".equals(paramResult.getOutValue().toString());
    }

    public Object HuyGiaoPhieu(Number phieuhtID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUHT_ID", phieuhtID, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        SqlParameter paramResult = new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NVARCHAR);
        params.add(paramResult);
        dbContext.executeStoredProcedure("QLTN.PKG_XULY_HOTRO.SP_HUYGIAO_PHIEU", params);
        return paramResult.getOutValue();
    }
}
