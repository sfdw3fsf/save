package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.common.exception.BadRequestExceptionMessage;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.DanhSachPhieuTCDTO;
import vn.vnpt.vnptit.ecms.dto.idc.IdcCongViecDto;
import vn.vnpt.vnptit.ecms.dto.idc.PhieuThiCongDTO;

@Component
public class TiepNhanThiCongService {
        @Autowired
        private DbContext dbContext;

        public Object dsPhieuThiCong(DanhSachPhieuTCDTO input) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_identity_code", AppRequestContext.getCurrent().getIdentityCode(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_loaicv_id", input.getpLoaicvId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_ma_cv", input.getpMaCv(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_donvi_id", input.getpDonViId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_nhanvien_th_id", input.getpNhanVienThId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_nhanvien_ph_id", input.getpNhanVienPhId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_thoigian_giao_tu", input.getpThoiGianGiaoTu(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_thoigian_giao_den", input.getpThoiGianGiaoDen(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_thoigian_ht_tu", input.getpThoiGianHtTu(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_thoigian_ht_den", input.getpThoiGianHtDen(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_ma_gd", input.getpMaGd(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(
                                new SqlParameter("p_ten_kh", input.getpTenKh(), SqlParameter.ParameterDirection.INPUT,
                                                Types.VARCHAR));
                params.add(new SqlParameter("p_so_hd", input.getpSoHd(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ma_tb", input.getpMaTb(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_DS_PHIEU_THICONG",
                                params);
        }

        public Object dsCongViecKenhBan(DanhSachPhieuTCDTO input) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_thoigian_tu", input.getpThoiGianTu(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_thoigian_den", input.getpThoiGianDen(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ma_gd", input.getpMaGd(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(
                                new SqlParameter("p_ten_kh", input.getpTenKh(), SqlParameter.ParameterDirection.INPUT,
                                                Types.VARCHAR));
                params.add(new SqlParameter("p_so_hd", input.getpSoHd(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ma_tb", input.getpMaTb(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THICONG_CONGVIEC.SP_LAY_DS_CONGVIEC_KENHBAN",
                                params);
        }

        public Object dsCongViecKenhBanV2(DanhSachPhieuTCDTO input) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_thoigian_tu", input.getpThoiGianTu(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_thoigian_den", input.getpThoiGianDen(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ma_gd", input.getpMaGd(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(
                                new SqlParameter("p_ten_kh", input.getpTenKh(), SqlParameter.ParameterDirection.INPUT,
                                                Types.VARCHAR));
                params.add(new SqlParameter("p_so_hd", input.getpSoHd(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ma_tb", input.getpMaTb(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THICONG_CONGVIEC.SP_LAY_DS_CONGVIEC_KENHBAN_V2",
                                params);
        }

        public Object createOrUpdatePhieuTC(PhieuThiCongDTO dto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_phieutc_id", dto.getpPhieuTCId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_phieutc_cha_id", dto.getpPhieuTCChaId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_loaicv_id", dto.getpLoaiCVId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_donvi_id", dto.getpDonViId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_matsan_id", dto.getpMatSanId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_ten_cv", dto.getpTenCV(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_cancu_yc", dto.getpCanCuYC(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(
                                new SqlParameter("p_noidung", dto.getpNoiDung(), SqlParameter.ParameterDirection.INPUT,
                                                Types.VARCHAR));
                params.add(new SqlParameter("p_giatri_khaibao", dto.getpGiaTriKhaiBao(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_nhanvien_th_id", dto.getpNhanVienTHId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_ngay_ycht", dto.getpNgayYCHT(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_mota", dto.getpMoTa(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_loaitn_id", dto.getpLoaiTNId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(
                                new SqlParameter("p_chitiet", dto.getpChiTiet(), SqlParameter.ParameterDirection.INPUT,
                                                Types.VARCHAR));
                try {
                        if (!dto.getpDSFile().isEmpty()) {
                                params.add(new SqlParameter("p_ds_file", JsonConvert.serialize(dto.getpDSFile()),
                                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                        } else {
                                params.add(new SqlParameter("p_ds_file", null, SqlParameter.ParameterDirection.INPUT,
                                                Types.VARCHAR));
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new BadRequestExceptionMessage(e.getMessage());
                }
                params.add(new SqlParameter("p_nhanvien_cn_id", AppRequestContext.getCurrent().getNhanVienID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_congviec_id", dto.getpCongViecId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_loaiph_id", dto.getpLoaiPhId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(
                                new SqlParameter("p_tinh_id", dto.getpTinhId(), SqlParameter.ParameterDirection.INPUT,
                                                Types.NUMERIC));
                params.add(
                                new SqlParameter("p_hdtb_id", dto.getpHdtbId(), SqlParameter.ParameterDirection.INPUT,
                                                Types.NUMERIC));
                params.add(new SqlParameter("p_thuebao_id", dto.getpThueBaoId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_matb", dto.getpMaTb(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ma_gd", dto.getpMaGd(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ten_kh", dto.getpTenKh(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_so_hd", dto.getpSoHd(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_goicuoc_id", dto.getpGoiCuocId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_ds_nv_phoihop", dto.getpDsNvPhoiHop(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ds_phieu_tc_con", dto.getpDsPhieuTCCon(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.CLOB));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                Object result = dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THICONG_CONGVIEC.SP_CAPNHAT_PHIEU_THICONG",
                                params);
                return result;
        }

        public Object ctPhieuCv(PhieuThiCongDTO input) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_phieutc_id", input.getpPhieuTCId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THICONG_CONGVIEC.SP_CHITIET_PHIEU_THICONG_IDC",
                                params);
        }

        public Object chuyenThucHienPhieuCv(PhieuThiCongDTO input) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_identity_code", AppRequestContext.getCurrent().getIdentityCode(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_phieutc_id", input.getpPhieuTCId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THICONG_CONGVIEC.SP_CHUYEN_THUCHIEN_PHIEU_THICONG",
                                params);
        }

        public Object dsCongViecKenhBanV3(DanhSachPhieuTCDTO input) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_thoigian_tu", input.getpThoiGianTu(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_thoigian_den", input.getpThoiGianDen(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ma_gd", input.getpMaGd(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(
                                new SqlParameter("p_ten_kh", input.getpTenKh(), SqlParameter.ParameterDirection.INPUT,
                                                Types.VARCHAR));
                params.add(new SqlParameter("p_so_hd", input.getpSoHd(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ma_tb", input.getpMaTb(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THICONG_CONGVIEC.SP_LAY_DS_CONGVIEC_KENHBAN_V3",
                                params);
        }

        public Object dsCongViecCon(DanhSachPhieuTCDTO input) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_phanvung_id", input.getpPhanvungId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_hdtb_id", input.getpHdtbId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_LAY_DS_CONGVIEC_CON",
                                params);
        }

        public Object danhSachCongViecKenhBan(DanhSachPhieuTCDTO input) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_loaicv_id", input.getpLoaicvId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_khachhang", input.getpTenKh(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_congviec", input.getpMaCv(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_idc_id", input.getpIdcId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THICONG_CONGVIEC.SP_GET_DS_CONGVIEC_KENHBAN",
                                params);
        }

        public Object insertCongViecKenhBan(IdcCongViecDto input) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_congviec_id", input.getCongViecId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_phanloai", input.getPhanLoai(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.NUMERIC);
                params.add(result);
                dbContext.executeStoredProcedure(
                                "ECMS.PKG_IDC_THICONG_CONGVIEC.SP_INSERT_CONGVIEC_KENHBAN",
                                params);
                return result.getOutValue();
        }

        public Object deleteCongViec(IdcCongViecDto input) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_congviec_id", input.getCongViecId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.NUMERIC);
                params.add(result);
                dbContext.executeStoredProcedure(
                                "ECMS.PKG_IDC_THICONG_CONGVIEC.SP_DELETE_CONGVIEC",
                                params);
                return result.getOutValue();
        }
}
