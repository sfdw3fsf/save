package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.CapNhatGiaoPhieuVanHanhDtoInput;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GiaoPhieuBaoHongThietBiService {
    private DbContext dbContext;

    public GiaoPhieuBaoHongThietBiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public List<Map<String, Object>> LayDSThietBiTheoVHanhId(int phieuvh_cha_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUVH_CHA_ID", phieuvh_cha_id, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_GIAOPHIEU_BAOHONG_THIETBI.SP_PHUTRO_LAY_DS_THEO_VHANH_ID", params);
    }

    public List<Map<String, Object>> LayDSTrangThaiVanHanh() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_GIAOPHIEU_BAOHONG_THIETBI.SP_PHUTRO_LAY_DS_TRANGTHAIVH", params);
    }

    public List<Map<String, Object>> LayDSDonVi(int phieuvh_id, int vanhanh_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUVH_ID", phieuvh_id, Types.NUMERIC));
        params.add(new SqlParameter("P_VANHANH_ID", vanhanh_id, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_GIAOPHIEU_BAOHONG_THIETBI.SP_LAY_DS_DONVI_NHAN_VH", params);
    }

    public Boolean CapNhatGiaoPhieuVanHanh(CapNhatGiaoPhieuVanHanhDtoInput input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_KIEU", input.getKieu(), Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUVH_ID", input.getPhieuvh_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_VANHANH_ID", input.getVanhanh_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_GIAO_ID", AppRequestContext.getCurrent().getDonViID(), Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_GIAO_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_NHAN_ID", input.getDonvi_nhan_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_NHAN_ID", input.getNhanvien_nhan_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_NGAYGIAO", input.getNgay_giao(), Types.DATE));
        params.add(new SqlParameter("P_ND_GIAO", input.getNoidung_giao(), Types.VARCHAR));
        params.add(new SqlParameter("P_PHIEUVH_CHA_ID", input.getPhieuvh_cha_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_NGAY_CN", new Date(), Types.DATE));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), Types.VARCHAR));
        params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), Types.VARCHAR));
        params.add(new SqlParameter("P_TTVH_ID", input.getTtvh_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", Types.REF_CURSOR));
        return "1".equals(dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_GIAOPHIEU_BAOHONG_THIETBI.SP_PHUTRO_CAPNHAP_GIAOPHIEU_VH", params).get(0).get("RESULT").toString());
    }

    public List<Map<String, Object>> LayDsPhieuVanHanhDaGiao(int vanhanh_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_VANHANH_ID", vanhanh_id, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_GIAOPHIEU_BAOHONG_THIETBI.SP_PHUTRO_LAY_DS_PHIEUVH_DAGIAO", params);
    }

    public Boolean HuyGiao(int vanhanh_id, int phieuvh_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_VANHANH_ID", vanhanh_id, Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUVH_ID", phieuvh_id, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", Types.REF_CURSOR));
        return "1".equals(dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_GIAOPHIEU_BAOHONG_THIETBI.SP_PHUTRO_HUYGIAO", params).get(0).get("RESULT").toString());
    }
}
