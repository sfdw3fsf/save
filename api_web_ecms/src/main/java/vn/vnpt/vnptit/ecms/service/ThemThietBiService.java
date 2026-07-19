package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ThemThietBiService {

    private final DbContext dbContext;

    public ThemThietBiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object lay_ds_thietbi_loaitbi(lay_ds_thietbi_loaitbi_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_MA_VT", input.getMa_vt(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.LAY_DS_THIETBI_LOAITBI", params);
    }

    public Object lay_ds_donvi_cua_loai_dvi(lay_ds_donvi_cua_loai_dvi_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIDV_ID", input.getLoaidv_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.LAY_DS_DONVI_CUA_LOAI_DVI", params);
    }

    public Object lay_ds_donvi_con_loaidv(lay_ds_donvi_cua_loai_dvi_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIDV_ID", input.getLoaidv_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.LAY_DS_DONVI_CON_LOAIDV", params);
    }

    public Object lay_ds_donvi_loai_dvi_v2(lay_ds_donvi_cua_loai_dvi_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIDV_ID", input.getLoaidv_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.LAY_DS_DONVI_LOAI_DVI_V2", params);
    }

    public Object lay_ds_thamso_md() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.LAY_DS_THAMSO_MD", params);
    }

    public Object kiemtra_csht_tram_moi(kiemtra_csht_tram_moi_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.KIEMTRA_CSHT_TRAM_MOI", params);
    }

    public Object phutro_them_tbi_v3(phutro_them_tbi_v3_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VDONVI_ID", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VVATTU_ID", input.getVattu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VMA_TBI", input.getMa_tbi(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VSERIAL", input.getSerial(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VGHICHU", input.getGhichu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VNGUOI_CN", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VMAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VIP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VNHOMTBI_ID", input.getNhomtbi_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VTHIETBI_CHA_ID", input.getThietbi_cha_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.PHUTRO_THEM_TBI_V3", params);
    }

    public Object update_vattu_thuoctinh(update_vattu_thuoctinh_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VTHIETBI_ID", input.getThietbi_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VTTVT_ID", input.getTtvt_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VGTTT_ID", input.getGttt_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VKIEUTT_ID", input.getKieutt_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VGIATRI", input.getGiatri(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VNGUOI_CN", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VMAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VIP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VRETURN", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.UPDATE_VATTU_THUOCTINH", params);
    }

    public boolean themmoi_diachi_thietbi(themmoi_diachi_thietbi_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VTHIETBI_ID", input.getThietbi_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VTINH_ID", input.getTinh_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VQUAN_ID", input.getQuan_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VPHUONG_ID", input.getPhuong_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VPHO_ID", input.getPho_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VKHU_ID", input.getKhu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VAP_ID", input.getAp_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VDACDIEM_ID", input.getDacdiem_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VSONHA", input.getSonha(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VDIACHI", input.getDiachi(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("VKINHDO", input.getKinhdo(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VVIDO", input.getVido(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.THEMMOI_DIACHI_THIETBI", params);
        return true;
    }

    public Object lay_ds_thietbi_loaithietbi(Long vattu_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_VATTU_ID", vattu_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.LAY_DS_THIETBI_LOAITHIETBI", params);
    }

    public Object lay_thongtin_thuoctinh_theo_nhom_thietbi(lay_thongtin_thuoctinh_theo_nhom_thietbi_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_NHOMTBI_ID", input.getNhomtbi_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_VATTU_ID", input.getVattu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.LAY_THONGTIN_THUOCTINH_THEO_NHOM_THIETBI", params);
    }

    public Object lay_ds_tbi_cha(lay_ds_tbi_cha_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VVATTU_ID", input.getVattu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VDONVI_ID", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VRETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.LAY_DS_TBI_CHA", params);
    }

    public Object phutro_lay_doituong_nhap_sl(phutro_lay_doituong_nhap_sl_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("VVATTU_ID", input.getVattu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.PHUTRO_LAY_DOITUONG_NHAP_SL", params);
    }

    public Object lay_ds_giatri_thuoctinh(lay_ds_giatri_thuoctinh_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_VATTU_ID", input.getVattu_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_THUOCTINH_ID", input.getThuoctinh_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THEM_THIETBI.LAY_DS_GIATRI_THUOCTINH", params);
    }

    public Object lay_ds_thuoctinh_theo_ttid(lay_ds_thuoctinh_theo_ttid_dto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("vtype", input.getType(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("vthuoctinh_id", input.getThuoctinh_id(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.LAY_DS_THUOCTINH_THEO_TTID", params);
    }
}
