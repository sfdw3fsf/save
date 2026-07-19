package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.*;
import vn.vnpt.context.AppRequestContext;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class CapPhatIPIDCService {
    
    private static final String PKG_IDC_PREFIX = "ECMS.PKG_IDC_CAPPHATTHUHOI_IP.";
    
    @Autowired
    DbContext dbContext;

    public ArrayList<Map<String, Object>> getChiTietCongViecCapPhatThuHoiIP(Long phieutcId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phieutc_id", phieutcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_GET_CHITIET_CONGVIEC_CAPPHAT_THUHOI_IP", parameters);
    }

    public ArrayList<Map<String, Object>> getDanhSachIPCapPhat(DanhSachIPCapPhatDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_ip_address", dto.getIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_subnet", dto.getTenSubnet(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_trangthai_id", dto.gettrangThaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phieutc_id", dto.getPhieutcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_GET_DANHSACH_IP_CAPPHAT", parameters);
    }

    public ArrayList<Map<String, Object>> getDanhSachDaiIPCapPhat(DanhSachDaiIPCapPhatDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_ten_dai_ip", dto.getTenDaiIp(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_subnet", dto.getTenSubnet(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_donvi_ql", dto.getTenDonviQl(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_trangthai_id", dto.getTrangThaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phieutc_id", dto.getPhieutcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_GET_DANHSACH_DAIIP_CAPPHAT", parameters);
    }

    public ArrayList<Map<String, Object>> getDanhSachIPStoreThiCong(DanhSachIPStoreThiCongDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phieutc_id", dto.getPhieutcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_GET_DANHSACH_IP_STORE_THICONG", parameters);
    }

    public ArrayList<Map<String, Object>> getChiTietIPTheoThicongipstore(ChiTietIPTheoThicongipstoreDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_thicongipstore_id", dto.getThicongipstoreId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_GET_CHITIET_IP_THEO_THICONGIPSTORE", parameters);
    }

    public ArrayList<Map<String, Object>> capPhatIP(CapPhatIPDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_thicongipstore_id", dto.getThicongipstoreId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phieutc_id", dto.getPhieutcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ipstore_id", dto.getIpstoreId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nhanvien_id", dto.getNhanvienId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_noidung", dto.getNoiDung(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_trangthai_id", dto.getTrangThaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ghichu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_mota", dto.getMoTa(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ip_id", dto.getIpID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_thietbi_mang_id", dto.getThietbiMangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loai_cv", dto.getLoaiCv(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_CAPPHAT_IP", parameters);
    }

    public ArrayList<Map<String, Object>> capPhatIPThietBiPort(CapPhatIPThietBiPortDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_ip_id", dto.getIpId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_thietbi_id", dto.getThietbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_port_id", dto.getPortId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_trangthaicapphat", dto.getTrangThaiCapPhat(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phieutc_id", dto.getPhieutcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_thicongipstore_id", dto.getThicongipstoreId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_CAPPHAT_IP_THIETBI_PORT", parameters);
    }

    public ArrayList<Map<String, Object>> deleteCapPhatIPStoreIP(DeleteCapPhatIPStoreIPDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_thicongipstore_id", dto.getThicongipstoreId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_thicongip_id", dto.getThicongipId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_type", dto.getType(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_DELETE_CAPPHAT_IPSTORE_IP", parameters);
    }

    public ArrayList<Map<String, Object>> getDanhSachThietBiCapPhat(DanhSachThietBiCapPhatDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_ten_thietbi", dto.getTenThietBi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_thietbi_id", dto.getThietbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_type", dto.getType(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_GET_DANHSACH_THIETBI_CAPPHAT", parameters);
    }

    public ArrayList<Map<String, Object>> getDanhSachPortTheoThietBi(DanhSachPortTheoThietBiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_thietbi_id", dto.getThietBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ten_port", dto.getTenPort(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cong_id", dto.getCongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_type", dto.getType(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_thuoc_nhom", dto.getThuocNhom(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_GET_DANHSACH_PORT_THEO_THIETBI", parameters);
    }

    public ArrayList<Map<String, Object>> getKetQuaCapPhatThuHoiIP(KetQuaDanhSachTacNghiepDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_loaicv_id", dto.getLoaiCvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_donvi_id", dto.getDonviId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_trangthai_id", dto.getTrangthaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nhanvien_th_id", dto.getNhanvienThId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ngay_yc_start", dto.getNgayYcStart(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ngay_yc_end", dto.getNgayYcEnd(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ma_thuebao", dto.getMaThuebao(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_kh", dto.getTenKh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_phanloai", dto.getPhanloai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_khachhang", dto.getKhachhang(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_congviec", dto.getCongviec(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_idc_id", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ip_store_name", dto.getIpStoreName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ngay_ht_start", dto.getNgayHTStart(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ngay_ht_end", dto.getNgayHTEnd(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_GET_KETQUA_CAPPHAT_THUHOI_IP", parameters);
    }

    public ArrayList<Map<String, Object>> deleteThuHoiIPStoreIP(DeleteThuHoiIPStoreIPDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_thicongipstore_id", dto.getThicongipstoreId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_thicongip_id", dto.getThicongipId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_type", dto.getType(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_DELETE_THUHOI_IPSTORE_IP", parameters);
    }

    public ArrayList<Map<String, Object>> thuHoiIPThietBiPort(ThuHoiIPThietBiPortDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_ip_id", dto.getIpId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_thicongipstore_id", dto.getThicongipstoreId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phieutc_id", dto.getPhieutcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_THUHOI_IP_THIETBI_PORT", parameters);
    }

    public ArrayList<Map<String, Object>> capNhatThongTinPhieuTC(CapNhatThongTinPhieuTCDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phieutc_id", dto.getPhieutcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoi_chuyen_xl", dto.getNguoiChuyenXl(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_trangthai", dto.gettrangthai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_lydo_traphieu", dto.getLydoTraphieu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_isHoanThanh", dto.getIsHoanThanh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_IDC_PREFIX + "SP_CAPNHAT_THONGTIN_PHIEUTC", parameters);
    }
}
