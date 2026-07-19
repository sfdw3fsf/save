package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.transaction.annotation.Transactional;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.GetCumDichVuDto;
import vn.vnpt.vnptit.ecms.dto.hatang.PhieuYeuCauThuHoiInputDto;
import vn.vnpt.vnptit.ecms.dto.hatang.ThongTinYeuCauDto;
import vn.vnpt.vnptit.ecms.dto.hatang.XacNhanYeuCauThuHoiInputDto;

@Service
public class HaTangThuHoiTaiNguyenService {
    private final DbContext dbContext;

    public HaTangThuHoiTaiNguyenService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getSPDVByUserId() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_user_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.get_spdv_by_user", params);
    }

    public Object getDsCudvBySpdv(GetCumDichVuDto getCumDichVuDto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_spdv_id", getCumDichVuDto.getIdSpdv(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.get_ds_cudv_by_spdv", params);
    }

    public Object getAoHoaYeuCau(ThongTinYeuCauDto yeucau) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yeucau_id", yeucau.getYeuCauId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.get_thuhoi_aohoa_by_yeucau_id", params);
    }

    public Object getK8SYeuCau(ThongTinYeuCauDto yeucau) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yeucau_id", yeucau.getYeuCauId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.get_thuhoi_k8s_by_yeucau_id", params);
    }

    public Object getIDGYeuCau(ThongTinYeuCauDto yeucau) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yeucau_id", yeucau.getYeuCauId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.get_thuhoi_idg_by_yeucau_id", params);
    }

    public Object getBackupYeuCau(ThongTinYeuCauDto yeucau) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yeucau_id", yeucau.getYeuCauId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.get_thuhoi_backup_by_yeucau_id", params);
    }

    public Object getPhieuYeuCauById(ThongTinYeuCauDto yeucau) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yeucau_id", yeucau.getYeuCauId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.get_phieu_thu_hoi_by_id", params);
    }

    public Object createPhieuYeuCauThuHoi(PhieuYeuCauThuHoiInputDto yeucau) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        
        // Thêm các tham số từ DTO
        params.add(new SqlParameter("p_so_phieu", yeucau.getSoPhieu(), Types.VARCHAR));
        params.add(new SqlParameter("p_trangthai_phieu", yeucau.getTrangThaiPhieu(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", yeucau.getDonViId(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_yc_id", yeucau.getNguoiYcId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_yc", yeucau.getNgayYc(), Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_thuhoi", yeucau.getNgayThuHoi(), Types.VARCHAR));
        params.add(new SqlParameter("p_spdv_id", yeucau.getSpdvId(), Types.NUMERIC));
        params.add(new SqlParameter("p_spdv_ten", yeucau.getSpdvTen(), Types.VARCHAR));
        params.add(new SqlParameter("p_cumdichvu", yeucau.getCumDichVu(), Types.VARCHAR));
        params.add(new SqlParameter("p_socuthuhoi", yeucau.getSoCuThuHoi(), Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_ht", yeucau.getNgayHt(), Types.VARCHAR));
        params.add(new SqlParameter("p_xacnhan", yeucau.getXacNhan(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngat", yeucau.getNgat(), Types.NUMERIC));
        params.add(new SqlParameter("p_thuchien_mayao", yeucau.getThucHienMayAo(), Types.NUMERIC));
        params.add(new SqlParameter("p_thuchien_backup", yeucau.getThucHienBackup(), Types.NUMERIC));
        params.add(new SqlParameter("p_thuchien", yeucau.getThucHien(), Types.NUMERIC));
        params.add(new SqlParameter("p_thuhoi_ip", yeucau.getThuHoiIp(), Types.VARCHAR));
        params.add(new SqlParameter("p_socumolai", yeucau.getSoCuMoLai(), Types.VARCHAR));
        params.add(new SqlParameter("p_file_yc", yeucau.getFileYc(), Types.VARCHAR));
        params.add(new SqlParameter("p_ghi_chu", yeucau.getGhiChu(), Types.VARCHAR));
        
        // Chuyển đổi các danh sách con thành JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String aohoaJson = objectMapper.writeValueAsString(yeucau.getAoHoa());
            String k8sJson = objectMapper.writeValueAsString(yeucau.getK8s());
            String idgJson = objectMapper.writeValueAsString(yeucau.getIdg());
            String backupJson = objectMapper.writeValueAsString(yeucau.getBackup());
            
            params.add(new SqlParameter("p_aohoa_data", aohoaJson, Types.CLOB));
            params.add(new SqlParameter("p_k8s_data", k8sJson, Types.CLOB));
            params.add(new SqlParameter("p_idg_data", idgJson, Types.CLOB));
            params.add(new SqlParameter("p_backup_data", backupJson, Types.CLOB));
        } catch (JsonProcessingException e) {
        }
        params.add(new SqlParameter("p_result", Types.NUMERIC));
        params.add(new SqlParameter("p_error_msg", Types.VARCHAR));
        
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.create_yeucau_thuhoi", params);
    }

    @Transactional
    public Object updatePhieuYeuCauThuHoi(PhieuYeuCauThuHoiInputDto yeucau) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
         // Thêm các tham số từ DTO
         params.add(new SqlParameter("p_id", yeucau.getId(), Types.NUMERIC));
         params.add(new SqlParameter("p_so_phieu", yeucau.getSoPhieu(), Types.VARCHAR));
         params.add(new SqlParameter("p_trangthai_phieu", yeucau.getTrangThaiPhieu(), Types.NUMERIC));
         params.add(new SqlParameter("p_donvi_id", yeucau.getDonViId(), Types.NUMERIC));
         params.add(new SqlParameter("p_nguoi_yc_id", yeucau.getNguoiYcId(), Types.NUMERIC));
         params.add(new SqlParameter("p_ngay_yc", yeucau.getNgayYc(), Types.VARCHAR));
         params.add(new SqlParameter("p_ngay_thuhoi", yeucau.getNgayThuHoi(), Types.VARCHAR));
         params.add(new SqlParameter("p_spdv_id", yeucau.getSpdvId(), Types.NUMERIC));
         params.add(new SqlParameter("p_spdv_ten", yeucau.getSpdvTen(), Types.VARCHAR));
         params.add(new SqlParameter("p_cumdichvu", yeucau.getCumDichVu(), Types.VARCHAR));
         params.add(new SqlParameter("p_socuthuhoi", yeucau.getSoCuThuHoi(), Types.VARCHAR));
         params.add(new SqlParameter("p_ngay_ht", yeucau.getNgayHt(), Types.VARCHAR));
         params.add(new SqlParameter("p_xacnhan", yeucau.getXacNhan(), Types.NUMERIC));
         params.add(new SqlParameter("p_ngat", yeucau.getNgat(), Types.NUMERIC));
         params.add(new SqlParameter("p_thuchien_mayao", yeucau.getThucHienMayAo(), Types.NUMERIC));
         params.add(new SqlParameter("p_thuchien_backup", yeucau.getThucHienBackup(), Types.NUMERIC));
         params.add(new SqlParameter("p_thuchien", yeucau.getThucHien(), Types.NUMERIC));
         params.add(new SqlParameter("p_thuhoi_ip", yeucau.getThuHoiIp(), Types.VARCHAR));
         params.add(new SqlParameter("p_socumolai", yeucau.getSoCuMoLai(), Types.VARCHAR));
         params.add(new SqlParameter("p_file_yc", yeucau.getFileYc(), Types.VARCHAR));
         params.add(new SqlParameter("p_ghi_chu", yeucau.getGhiChu(), Types.VARCHAR));
         
         // Chuyển đổi các danh sách con thành JSON
         ObjectMapper objectMapper = new ObjectMapper();
         try {
             String aohoaJson = objectMapper.writeValueAsString(yeucau.getAoHoa());
             String k8sJson = objectMapper.writeValueAsString(yeucau.getK8s());
             String idgJson = objectMapper.writeValueAsString(yeucau.getIdg());
             String backupJson = objectMapper.writeValueAsString(yeucau.getBackup());
             
             params.add(new SqlParameter("p_aohoa_data", aohoaJson, Types.CLOB));
             params.add(new SqlParameter("p_k8s_data", k8sJson, Types.CLOB));
             params.add(new SqlParameter("p_idg_data", idgJson, Types.CLOB));
             params.add(new SqlParameter("p_backup_data", backupJson, Types.CLOB));
         } catch (JsonProcessingException e) {
             e.printStackTrace();
         }
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        Map<String, Object> result =  dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.update_yeucau_thuhoi", params);
        return result;
    }

    /*Xác nhận thu hồi tài nguyên - START - HoangMN */
    public Object xacNhanYeuCauThuHoi(XacNhanYeuCauThuHoiInputDto yeucau) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
         // Thêm các tham số từ DTO
         params.add(new SqlParameter("p_id", yeucau.getId(), Types.NUMERIC));
         params.add(new SqlParameter("p_trangthai_xacnhan", yeucau.getTrangThaiXacNhan(), Types.NUMERIC));
         params.add(new SqlParameter("p_xacnhan", yeucau.getXacNhan(), Types.NVARCHAR));
         params.add(new SqlParameter("p_ngat", yeucau.getNgat(), Types.NUMERIC));
         params.add(new SqlParameter("p_thuchien", yeucau.getThucHien(), Types.NVARCHAR));
         params.add(new SqlParameter("p_thuhoi_ip", yeucau.getThuHoiIp(), Types.NVARCHAR));
        params.add(new SqlParameter("p_so_cu_thu_hoi", yeucau.getSoCuThuHoi(), Types.NVARCHAR));
        params.add(new SqlParameter("p_so_cu_mo_lai", yeucau.getSoCuMoLai(), Types.NVARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.xacnhan_yeucau_thuhoi", params);
    }
    /*Xác nhận thu hồi tài nguyên - END - HoangMN */


    public Object getDsPhieuYeuCauThuHoi() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.get_ds_phieu_thuhoi", params);
    }

    public Object deletePhieuYeuCauThuHoi(ThongTinYeuCauDto yeucau) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", yeucau.getYeuCauId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.delete_yeucau_thuhoi", params);
    }

    public Object getDanhSachHaTangAoHoa() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.get_ds_aohoa", params);
    }

    public Object getDanhSachHaTangIDG() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.get_ds_idg", params);
    }

    public Object getDanhSachHaTangBackup() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.get_ds_backup", params);
    }

    public Object guiYeuCauThuHoi(ThongTinYeuCauDto yeucau) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", yeucau.getYeuCauId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THUHOI_TAINGUYEN.GUIYEUCAUTHUHOI", params);
    }
}
