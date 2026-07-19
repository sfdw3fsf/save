package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.tainguyenTANGGIAMtheoYCDTO;

@Service
public class tainguyenTANGGIAMtheoYCService {

    @Autowired
    private final DbContext dbContext;

    public tainguyenTANGGIAMtheoYCService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object chitietVPC(tainguyenTANGGIAMtheoYCDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter(
                "p_id_VPC",
                dto.getIdXemChiTiet(),
                SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));

        params.add(new SqlParameter(
                "p_RESULT",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.sp_VPC_chtiet",
                params);
    }

    public Object chitietMaYao(tainguyenTANGGIAMtheoYCDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter(
                "p_ma_MayAo",
                dto.getMaMayAo(),
                SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));

        params.add(new SqlParameter(
                "p_RESULT",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.sp_MayAo_chtiet",
                params);
    }

    public Map<String, Object> insertVPC(tainguyenTANGGIAMtheoYCDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_DONVI_CQ_ID", dto.getDonViId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_SPDV_ID", dto.getSpdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_LOAIHT_ID", dto.getLoaiHtId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_CUMSPDV_ID", dto.getCumSpdvId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_NOIDUNG", dto.getNoiDung(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_LOAIYC_ID", dto.getLoaiYcId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_TRANGTHAIPH_ID", dto.getTrangThaiPhId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_NHANVIEN_YC_ID", dto.getNhanVienId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DONVI_YC_ID", dto.getDonViId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_NGAY_YC", parseDate(dto.getNgayYc()), SqlParameter.ParameterDirection.INPUT,
                Types.DATE));
        params.add(new SqlParameter("p_NGAY_HT", parseDate(dto.getNgayHt()), SqlParameter.ParameterDirection.INPUT,
                Types.DATE));
        params.add(new SqlParameter("p_FILE_DINHKEM", dto.getFileDinhKem(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_MOTA", dto.getMoTa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_SUDUNG", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_GHICHU", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_NGAY_CN", new Date(), SqlParameter.ParameterDirection.INPUT, Types.DATE));

        SqlParameter statusParam = new SqlParameter("p_STATUS", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.VARCHAR);
        SqlParameter messageParam = new SqlParameter("p_MESSAGE", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.VARCHAR);

        params.add(statusParam);
        params.add(messageParam);

        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.sp_VPC_INSERT_PHIEU_YC", params);

        Map<String, Object> result = new HashMap<>();
        result.put("status", statusParam.getOutValue() != null ? statusParam.getOutValue().toString() : "ERROR");
        result.put("message", messageParam.getOutValue() != null ? messageParam.getOutValue().toString()
                : "NO RESPONSE FROM SYSTEM");

        return result;
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        String[] formats = { "dd/MM/yyyy", "yyyy-MM-dd", "dd-MM-yyyy", "yyyy/MM/dd" };
        for (String fmt : formats) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(fmt);
                sdf.setLenient(false);
                return sdf.parse(dateStr.trim());
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public Map<String, Object> insertPhieuYCDV(tainguyenTANGGIAMtheoYCDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_PHIEUYC_ID", dto.getPhieuYcId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_LOAIHT_ID", dto.getLoaiHtId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_MA_DICHVU", dto.getMaDichVu(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_TRANGTHAICP_ID", dto.getTrangThaiCpId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_SO_LANGUI", dto.getSoLanGui(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_CUMHT_ID", dto.getCumHtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_NGAY_HH", dto.getNgayHh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(
                new SqlParameter("p_NGAY_GH", dto.getNgayGh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_DONVI_CQ_ID", dto.getDonViId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DONVI_QL_ID", dto.getDonViId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_NHANVIEN_QL_ID", dto.getNhanVienId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_SUDUNG", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("p_TINHPHI", dto.getTinhPhi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_MOTA", dto.getMoTa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_GHICHU", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("p_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_NGAY_CN", new Date(), SqlParameter.ParameterDirection.INPUT, Types.DATE));

        SqlParameter statusParam = new SqlParameter("p_RESULT", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.VARCHAR);
        SqlParameter messageParam = new SqlParameter("p_MESSAGE", null, SqlParameter.ParameterDirection.OUTPUT,
                Types.VARCHAR);

        params.add(statusParam);
        params.add(messageParam);

        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.sp_INS_PHIEUYC_DV", params);

        Map<String, Object> result = new HashMap<>();
        result.put("status", statusParam.getOutValue() != null ? statusParam.getOutValue().toString() : "ERROR");
        result.put("message", messageParam.getOutValue() != null ? messageParam.getOutValue().toString()
                : "NO RESPONSE FROM SYSTEM");

        return result;
    }

    public Map<String, Object> insertPhieuYcMayAo(tainguyenTANGGIAMtheoYCDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        String tenDangNhap = dto.getTenDangNhap();
        if (tenDangNhap == null || tenDangNhap.trim().isEmpty()) {
            tenDangNhap = AppRequestContext.getCurrent().getUserName();
        }

        params.add(new SqlParameter("p_PHIEUYCDV_ID", dto.getPhieuYcDvId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_TEN_DANGNHAP", tenDangNhap, SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_SLHT_CORECPU", dto.getSlhtCoreCpu(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLHT_RAM", dto.getDlhtRam(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLHT_GPU", dto.getDlhtGpu(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLHT_SSD", dto.getDlhtSsd(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLHT_HDD", dto.getDlhtHdd(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLHT_NVME", dto.getDlhtNvme(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_SLYC_CORECPU", dto.getSlycCoreCpu(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLYC_RAM", dto.getDlycRam(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLYC_GPU", dto.getDlycGpu(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLYC_SSD", dto.getDlycSsd(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLYC_HDD", dto.getDlycHdd(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLYC_NVME", dto.getDlycNvme(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_SLCP_CORECPU", dto.getSlcpCoreCpu(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLCP_RAM", dto.getDlcpRam(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLCP_GPU", dto.getDlcpGpu(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLCP_SSD", dto.getDlcpSsd(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLCP_HDD", dto.getDlcpHdd(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_DLCP_NVME", dto.getDlcpNvme(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_HEDIEUHANH_ID", dto.getHeDieuHanhId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_KIENTRUC", dto.getKienTruc(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_VPC", dto.getVpc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_MAYAO_ID", dto.getMayAoId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_MOTA", dto.getMoTa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_GHICHU", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_NGAY_CN", new Date(), SqlParameter.ParameterDirection.INPUT, Types.DATE));

        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.SP_INSERT_PHIEUYC_MAYAO", params);

        Map<String, Object> result = new HashMap<>();
        result.put("status", "SUCCESS");
        result.put("message", "Insert phieu yeu cau may ao thanh cong");

        return result;
    }

    public Object lstTTPhieu() throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter(
                "p_RESULT",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.sp_tt_PHIEUYC_DV",
                params);
    }

    public Object ttDonViQL(tainguyenTANGGIAMtheoYCDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_DONVI_ID", dto.getDonViId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter(
                "p_RESULT",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.sp_tt_DONVI",
                params);
    }

    public Object ttDonViYC(tainguyenTANGGIAMtheoYCDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_DONVI_ID", dto.getDonViId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter(
                "p_RESULT",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.sp_tt_DONVI",
                params);
    }

    public Object ttNhanVienYC(tainguyenTANGGIAMtheoYCDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_NHANVIEN_ID", dto.getNhanVienId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter(
                "p_RESULT",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.sp_tt_NHANVIEN",
                params);
    }

    public Object ttPhieuYC(tainguyenTANGGIAMtheoYCDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_PHIEUYC_ID", dto.getPhieuYcId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter(
                "p_RESULT",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.sp_tt_PHIEUYC",
                params);
    }

    public Object dsDonVi(tainguyenTANGGIAMtheoYCDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_DONVI_ID", dto.getDonViId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter(
                "p_RESULT",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.sp_tt_DONVI",
                params);
    }

    public Object dsNhanVien(tainguyenTANGGIAMtheoYCDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_NHANVIEN_ID", dto.getNhanVienId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter(
                "p_RESULT",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_HATANG_TANGGIAM_VPCMAYAOIDG.sp_tt_NHANVIEN",
                params);
    }
}
