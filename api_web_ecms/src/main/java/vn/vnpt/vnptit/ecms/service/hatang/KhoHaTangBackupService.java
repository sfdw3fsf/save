package vn.vnpt.vnptit.ecms.service.hatang;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.KhoHaTangBackupDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class KhoHaTangBackupService {
    @Autowired
    private DbContext dbContext;

    public Object createOrUpdate(KhoHaTangBackupDTO dto) throws AppSqlException {
        byte[] decodedBytes = null;
        try {
            decodedBytes = Base64.getDecoder().decode(dto.getAvatar());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_mahatang_infra", dto.getMaHaTangInfra(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hinhthucdautu", dto.getHinhThucDauTu(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaihatang_id", dto.getLoaiHaTangId(), Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", dto.getIdcId(), Types.NUMERIC));

        Gson gson = new Gson();
        params.add(new SqlParameter("p_hatangmang_id", gson.toJson(dto.getHaTangMangId()), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_cungcap_dichvu", gson.toJson(dto.getIpCungCapDichVu()), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_replica_data", gson.toJson(dto.getIpReplicaData()), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_quantri", gson.toJson(dto.getIpQuanTri()), Types.NVARCHAR));

        params.add(new SqlParameter("p_admin_link", dto.getAdminLink(), Types.NVARCHAR));
        params.add(new SqlParameter("p_avatar", decodedBytes, Types.BLOB));
        params.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", dto.getHieuLuc(), Types.NUMERIC));
        params.add(new SqlParameter("p_donviqly_id", dto.getDonViQLyId(), Types.NUMERIC));
        params.add(new SqlParameter("p_phongbanqly_id", dto.getPhongBanQLyId(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoiqly_id", dto.getNguoiQLyId(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", dto.getNhanVienId(), Types.NUMERIC));
        params.add(new SqlParameter("p_may_cn", dto.getMayCapNhap(), Types.NVARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", dto.getNguoiCapNhap(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_cn", dto.getIpCapNhap(), Types.NVARCHAR));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_KHO_HATANG_BACKUP_INSERT_OR_UPDATE", params);

        long resultValue = Long.parseLong(result.getOutValue().toString());
        if (resultValue > 0) {
            List<SqlParameter> deleteBackupThietBiParams = new ArrayList<>();
            deleteBackupThietBiParams.add(new SqlParameter("p_hatang", "BACKUP_THIETBI", Types.NVARCHAR));
            deleteBackupThietBiParams.add(new SqlParameter("p_hatang_id", resultValue, Types.NUMERIC));
            deleteBackupThietBiParams.add(new SqlParameter("p_result",  Types.NUMERIC));
            dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_THIETBI_HINHTHANH_HATANG_DELETEALL", deleteBackupThietBiParams);

            for (KhoHaTangBackupDTO.ThietBiHinhThanh tb : dto.getThietBiHinhThanhList()) {
                List<SqlParameter> insertBackupThietBiParams = new ArrayList<>();
                insertBackupThietBiParams.add(new SqlParameter("p_hatang_id", resultValue, Types.NUMERIC));
                insertBackupThietBiParams.add(new SqlParameter("p_thietbi_id", tb.getThietbi_id(), Types.NUMERIC));
                insertBackupThietBiParams.add(new SqlParameter("p_nhanvien_id", dto.getNhanVienId(), Types.NUMERIC));
                insertBackupThietBiParams.add(new SqlParameter("p_may_cn", dto.getMayCapNhap(), Types.NVARCHAR));
                insertBackupThietBiParams.add(new SqlParameter("p_nguoi_cn", dto.getNguoiCapNhap(), Types.NVARCHAR));
                insertBackupThietBiParams.add(new SqlParameter("p_ip_cn", dto.getIpCapNhap(), Types.NVARCHAR));
                insertBackupThietBiParams.add(new SqlParameter("p_ngay_cn", tb.getNgay_cn(), Types.DATE));
                insertBackupThietBiParams.add(new SqlParameter("p_result",  Types.NUMERIC));
                dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_THIETBI_HINHTHANH_HATANG_BACKUP_THIETBI_INSERT", insertBackupThietBiParams);
            }

            List<SqlParameter> deleteBackupTuDiaParams = new ArrayList<>();
            deleteBackupTuDiaParams.add(new SqlParameter("p_hatang", "BACKUP_TUDIA", Types.NVARCHAR));
            deleteBackupTuDiaParams.add(new SqlParameter("p_hatang_id", resultValue, Types.NUMERIC));
            deleteBackupTuDiaParams.add(new SqlParameter("p_result",  Types.NUMERIC));
            dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_THIETBI_HINHTHANH_HATANG_DELETEALL", deleteBackupTuDiaParams);
            for (KhoHaTangBackupDTO.ThietBiTuDiaDauNoi tudia : dto.getThietBiTuDiaDauNoiList()) {
                List<SqlParameter> insertBackupTuDiaParams = new ArrayList<>();
                insertBackupTuDiaParams.add(new SqlParameter("p_hatang_id", resultValue, Types.NUMERIC));
                insertBackupTuDiaParams.add(new SqlParameter("p_thietbi_id", tudia.getThietbi_id(), Types.NUMERIC));
                insertBackupTuDiaParams.add(new SqlParameter("p_sothutu", tudia.getSothutu(), Types.NUMERIC));
                insertBackupTuDiaParams.add(new SqlParameter("p_result",  Types.NUMERIC));
                dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_THIETBI_HINHTHANH_HATANG_BACKUP_TUDIA_INSERT", insertBackupTuDiaParams);

            }
        }

        return resultValue;
    }

    public Object getAll() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_KHO_HATANG_BACKUP_GETALL", params);
    }

    public Object deleteById(Number id) {
        try {
            List<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id", id, Types.NUMERIC));
            params.add(new SqlParameter("p_table_name", "HATANGBACKUP", Types.NVARCHAR));
            params.add(new SqlParameter("p_result", Types.NUMERIC));
            dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_DELETE_BY_ID", params);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Object getKhoHaTangBackupRelationship(Number id, String tabName) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_table_name", tabName, Types.NVARCHAR));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_KHO_HATANG_BACKUP_RELATIONSHIP", params);
    }

    public Object getThietBiHinhThanhKhoHaTang(String hatang, int idcId, List<Integer> oldThietBiIdList) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatang", hatang, Types.NVARCHAR));
        params.add(new SqlParameter("p_idc_id", idcId, Types.NUMERIC));
        Gson gson = new Gson();
        params.add(new SqlParameter("p_old_thietbi_ids", gson.toJson(oldThietBiIdList), Types.NVARCHAR));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_THIETBI_HINHTHANH_HATANG_GET", params);
        return dbResult;
    }

}
