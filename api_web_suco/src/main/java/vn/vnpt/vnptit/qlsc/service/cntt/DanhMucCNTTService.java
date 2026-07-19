package vn.vnpt.vnptit.qlsc.service.cntt;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.LayDsTinhTrangXuLyDto;
import vn.vnpt.vnptit.qlsc.dto.cntt.LayDsNvTheoDvDto;
import vn.vnpt.vnptit.qlsc.dto.cntt.QuyMoSuCoCNTTDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class DanhMucCNTTService {
    private final DbContext dbContext;

    public DanhMucCNTTService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object dsTrangThaiSuCo() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_LAY_DS_TRANGTHAI_SC", params);
    }

    public Object dsDichVuCntt() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.SP_LAY_DS_DICHVU_CNTT", params);
    }

    public Object dsLoaiHinhTheoDichVu(Integer dichVuId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DICHVUVT_ID", dichVuId,
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.SP_LAY_DS_LOAIHINH_THEO_DICHVU",
                params);
    }

    public Object dsNguonSuCo() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_LAY_DS_NGUON_SC", params);
    }

    public Object dsMucDoKhanCap() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_LAY_DS_MUCDO_KC", params);
    }

    public Object dsMucDoSuCo() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_LAY_DS_MUCDO_SC", params);
    }

    public Object layDsQuyTrinh() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_DS_QUYTRINH_SEL", params);
    }

    public Object layDsThaoTacGiaoPhieu() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_DS_THAOTAC_SEL", params);
    }

    public Object layDsHuongGiao(Integer quyTrinhId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_QUYTRINH_ID", quyTrinhId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_DS_HUONGGIAO_SEL", params);
    }

    public Object layDsDonViGiao(Integer huongGiaoId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("P_HUONGGIAO_ID", huongGiaoId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_DS_DONVI_GIAO", params);
    }

    public Object layDsTinhTrangXuLy(LayDsTinhTrangXuLyDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_SUCO_ID", input.getSuco_id(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI", input.getTrangthai_id(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("CUR_RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT,
                java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_LAY_DS_TINHTRANG_XULY", params);
    }

    public Object layDsNvXuLyTheoDv(LayDsNvTheoDvDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEU_ID", input.getPhieu_id(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT,
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("CUR_RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT,
                java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.LAY_DS_NHANVIEN_XL_THEO_DV", params);
    }

    public Object getDsNhomSuCo(int nhanVienID) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_NHANVIEN_ID", nhanVienID, java.sql.Types.INTEGER));
        params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.SP_LAY_DS_NHOM_SUCO_THEO_NHANVIEN", params);
    }

    public List<QuyMoSuCoCNTTDTO> dsQuyMoSuCoCNTT(){
        List<QuyMoSuCoCNTTDTO> result = new ArrayList<>();
        result.add(new QuyMoSuCoCNTTDTO(1, "Thấp"));
        result.add(new QuyMoSuCoCNTTDTO(2, "Trung bình"));
        result.add(new QuyMoSuCoCNTTDTO(3, "Cao"));
        return result;
    }

    public Object getTiepNhanDonViGiaoViec(long pPhanVungId, long pSuCoId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", pPhanVungId, Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", pSuCoId, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.sp_ds_donvi_giaoviec", params);
    }

    public Object dsNhanVienTheoDSDonVi(String pDSDonVi, int pPhanVungId, long pSuCoId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", pPhanVungId, Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", pSuCoId, Types.NUMERIC));
        params.add(new SqlParameter("p_ds", pDSDonVi, Types.NVARCHAR));
        params.add(new SqlParameter("p_rs", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC_CNTT.sp_ds_nhanvien_theo_ds_donvi", params);
    }



}
