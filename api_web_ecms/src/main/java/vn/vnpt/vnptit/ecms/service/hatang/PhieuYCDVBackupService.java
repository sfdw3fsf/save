package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.PhieuYCDVBackupDto;
import vn.vnpt.vnptit.ecms.dto.hatang.NguonBackupDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class PhieuYCDVBackupService {

    @Autowired
    private DbContext dbContext;

    /**
     * Lưu phiếu yêu cầu dịch vụ Backup
     */
    public ArrayList<Map<String, Object>> savePhieuYCDVBackup1(PhieuYCDVBackupDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_PHIEUYC_ID", dto.getPhieuycId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_CQ_ID", dto.getChuquanDvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_QL_ID", dto.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_QL_ID", dto.getCanhanQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGAY_HH", dto.getNgayHetHan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_KIEU_BK", dto.getKieuBk(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUMHT_BK_ID", dto.getCumhtBkId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IP_D", dto.getIpD(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IP_D_ID", dto.getIpDId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DUONGDAN_D", dto.getDuongdanD(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DUNGLUONG_HT", dto.getDungluongHt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DUNGLUONG_YC", dto.getDungluongYc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TINHPHI", dto.getTinhPhi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangthaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        
        String userCn = dto.getNguoiCn() != null ? dto.getNguoiCn() : AppRequestContext.getCurrent().getUserName();
        params.add(new SqlParameter("P_NGUOI_CN", userCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        
        params.add(new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHAILH.SP_SAVE_PHIEUYC_BK", params);
    }

    public ArrayList<Map<String, Object>> savePhieuYCDVBackup(PhieuYCDVBackupDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_PHIEUYC_ID", dto.getPhieuycId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_CQ_ID", dto.getChuquanDvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_QL_ID", dto.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_QL_ID", dto.getCanhanQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGAY_HH", dto.getNgayHetHan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_KIEU_BK", dto.getKieuBk(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUMHT_BK_ID", dto.getCumhtBkId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IP_D", dto.getIpD(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IP_D_ID", dto.getIpDId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DUONGDAN_D", dto.getDuongdanD(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DUNGLUONG_HT", dto.getDungluongHt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DUNGLUONG_YC", dto.getDungluongYc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TINHPHI", dto.getTinhPhi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangthaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        
        params.add(new SqlParameter("P_LOAI_DOITUONG", null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUMHT_N_ID", null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IP_N", null, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IP_N_ID", null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HOSTNAME", null, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_LOAIDL_ID", null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DUONGDAN_N", null, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TEN_DOITUONG", null, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TANSUAT", null, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_THOIGIAN_BK", null, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        
        String userCn = dto.getNguoiCn() != null ? dto.getNguoiCn() : AppRequestContext.getCurrent().getUserName();
        params.add(new SqlParameter("P_NGUOI_CN", userCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        ArrayList<Map<String, Object>> resultMaster = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHAILH.SP_SAVE_PHIEUYC_BK", params);

        if (resultMaster != null && !resultMaster.isEmpty() && "1".equals(String.valueOf(resultMaster.get(0).get("STATUS")))) {
            
            Long phieuycBkId = Long.valueOf(resultMaster.get(0).get("PHIEUYCBK_ID").toString());

            if (dto.getDsNguonBackup() != null && !dto.getDsNguonBackup().isEmpty()) {
                
                for (NguonBackupDto nguon : dto.getDsNguonBackup()) {
                    
                    ArrayList<SqlParameter> detailParams = new ArrayList<>();
                    detailParams.add(new SqlParameter("P_PHIEUYCBK_ID", phieuycBkId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                    
                    // Map đúng theo các hàm GET của NguonBackupDto
                    detailParams.add(new SqlParameter("P_LOAI_DOITUONG", nguon.getLoaiDoiTuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                    detailParams.add(new SqlParameter("P_CUMHT_N_ID", nguon.getCumHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                    detailParams.add(new SqlParameter("P_IP_N", nguon.getIpNguonText(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                    detailParams.add(new SqlParameter("P_HOSTNAME", nguon.getHostname(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                    detailParams.add(new SqlParameter("P_LOAIDL_ID", nguon.getLoaiDuLieuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                    detailParams.add(new SqlParameter("P_DUONGDAN_N", nguon.getDuongDanNguon(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                    detailParams.add(new SqlParameter("P_TEN_DOITUONG", nguon.getTenDoiTuong(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                    
                    // Ép kiểu tanSuatId từ Long sang String (vì SP đang nhận VARCHAR)
                    String tanSuatStr = nguon.getTanSuatId() != null ? String.valueOf(nguon.getTanSuatId()) : null;
                    detailParams.add(new SqlParameter("P_TANSUAT", tanSuatStr, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                    
                    detailParams.add(new SqlParameter("P_THOIGIAN_BK", nguon.getThoiGian(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                    detailParams.add(new SqlParameter("P_NGUOI_CN", userCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                    
                    detailParams.add(new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

                    // Gọi SP Chi tiết (SP tạo ở bước trước)
                    dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHAILH.SP_SAVE_CHITIET_NGUONBK", detailParams);
                }
            }
        }

        return resultMaster;
    }

    public ArrayList<Map<String, Object>> dsDichvuBackup(PhieuYCDVBackupDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        
        params.add(new SqlParameter("P_DICHVUBK_ID", dto.getIdDichVu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KIEU_CP_ID", dto.getKieuCapphatId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUM_HT_BACKUP_ID", dto.getCumHatangBackupId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_DT_NGUON_ID", dto.getLoaiDoiTuongNguon(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUON_BACKUP", dto.getNguonBackupSearch(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DICH_BACKUP", dto.getDichBackup(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IP_NGUON", dto.getIpNguon(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_LOAI_DL_BACKUP_ID", dto.getLoaiDuLieuBackup(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangthaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUM_HT_NGUON_ID", dto.getCumHatangNguon(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TAN_SUAT", dto.getTanSuatBackup(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TINHPHI", dto.getTinhPhi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CHUQUAN_DV_ID", dto.getChuquanDvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_QL_ID", dto.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CANHAN_QL_ID", dto.getCanhanQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        
        params.add(new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_KHAILH.GET_DS_DV_BACKUP", params);
    }
}