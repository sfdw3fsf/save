package vn.vnpt.vnptit.ecms.service.hatang;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.KhoHaTangSmartCloudDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.KhoHaTangThietBiHinhThanhDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KhoHaTangSmartCloudService {
    @Autowired
    private DbContext dbContext;


    public Object createOrUpdate(KhoHaTangSmartCloudDTO dto) throws AppSqlException {
        byte[] decodedBytes = null;
        try {
            decodedBytes = Base64.getDecoder().decode(dto.getAvatar());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_mahatang_infra", dto.getMahatang_infra(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.VARCHAR));
        params.add(new SqlParameter("p_hinhthucdautu", dto.getHinhthucdautu(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaihatang_id", dto.getLoaihatang_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_hatang_phanloai_id", dto.getHatang_phanloai_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_hatang_congnghe_id", dto.getHatang_congnghe_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", dto.getIdc_id(), Types.NUMERIC));

        Gson gson = new Gson();
        params.add(new SqlParameter("p_hatangmang_id", gson.toJson(dto.getHatangmang_id()), Types.NVARCHAR));
        params.add(new SqlParameter("p_thietbi_tudia_id", gson.toJson(dto.getThietbi_tudia_id()), Types.NVARCHAR));

        params.add(new SqlParameter("p_admin_link", dto.getAdmin_link(), Types.NVARCHAR));
        params.add(new SqlParameter("p_avatar", decodedBytes, Types.BLOB));
        params.add(new SqlParameter("p_ghi_chu", dto.getGhi_chu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", dto.getHieu_luc(), Types.NUMERIC));
        params.add(new SqlParameter("p_donviqly_id", dto.getDonviqly_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_phongbanqly_id", dto.getPhongbanqly_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoiqly_id", dto.getNguoiqly_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_hatang_capdo_id", dto.getHatang_capdo_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_vcpu_nangluc", dto.getVcpu_nangluc(), Types.NUMERIC));
        params.add(new SqlParameter("p_vcpu_cap", dto.getVcpu_cap(), Types.NUMERIC));
        params.add(new SqlParameter("p_vcpu_total", dto.getVcpu_total(), Types.NUMERIC));
        params.add(new SqlParameter("p_vram_nangluc", dto.getVram_nangluc(), Types.NUMERIC));
        params.add(new SqlParameter("p_vram_cap", dto.getVram_cap(), Types.NUMERIC));
        params.add(new SqlParameter("p_vram_total", dto.getVram_total(), Types.NUMERIC));
        params.add(new SqlParameter("p_gpu_nangluc", dto.getGpu_nangluc(), Types.NUMERIC));
        params.add(new SqlParameter("p_gpu_cap", dto.getGpu_cap(), Types.NUMERIC));
        params.add(new SqlParameter("p_gpu_total", dto.getGpu_total(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        params.add(new SqlParameter("p_may_capnhap", AppRequestContext.getCurrent().getClientIpAddress(), Types.NVARCHAR));
        params.add(new SqlParameter("p_nguoi_capnhap", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_capnhap", AppRequestContext.getCurrent().getClientIpAddress(), Types.NVARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_KHO_HATANG_SMARTCLOUD_INSERT_UPDATE", params);
        long resultValue = Long.parseLong(result.getOutValue().toString());
        if (resultValue > 0) {
            List<SqlParameter> deleteBackupThietBiParams = new ArrayList<>();
            deleteBackupThietBiParams.add(new SqlParameter("p_hatang", "SMARTCOUD_THIETBI", Types.NVARCHAR));
            deleteBackupThietBiParams.add(new SqlParameter("p_hatang_id", resultValue, Types.NUMERIC));
            deleteBackupThietBiParams.add(new SqlParameter("p_result",  Types.NUMERIC));
            dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_THIETBI_HINHTHANH_HATANG_DELETEALL", deleteBackupThietBiParams);

            for (KhoHaTangThietBiHinhThanhDTO tb : dto.getThietBiHinhThanhList()) {
                List<SqlParameter> insertSmartThietBiParams = new ArrayList<>();
                insertSmartThietBiParams.add(new SqlParameter("p_hatang_id", resultValue, Types.NUMERIC));
                insertSmartThietBiParams.add(new SqlParameter("p_thietbi_id", tb.getThietbi_id(), Types.NUMERIC));
                insertSmartThietBiParams.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
                insertSmartThietBiParams.add(new SqlParameter("p_ngay_cn", tb.getNgay_cn(), Types.DATE));
                insertSmartThietBiParams.add(new SqlParameter("p_result",  Types.NUMERIC));
                dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_THIETBI_HINHTHANH_HATANG_SMARTCLOUD_THIETBI_INSERT", insertSmartThietBiParams);
            }
        }
        return resultValue;
    }

    public Object getAll() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_KHO_HATANG_SMARTCLOUD_GETALL", params);
        List<Map<String, Object>> result = dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
        return result;
    }

    public Object getItem(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_KHO_HATANG_SMARTCLOUD_GETITEM", params);
        List<Map<String, Object>> result = dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
        return result;
    }

    public Object deleteById(Number id) {
        try {
            List<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id", id, Types.NUMERIC));
            params.add(new SqlParameter("p_table_name", "HATANGSMARTCLOUD", Types.NVARCHAR));
            params.add(new SqlParameter("p_result", Types.NUMERIC));
            dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_DELETE_BY_ID", params);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Object smartCloudRelationship(Number id, String tabName) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_object_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_table_name", tabName, Types.NVARCHAR));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_KHO_HATANG_SMARTCLOUD_RELATIONSHIP", params);
        List<Map<String, Object>> result = dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
        return result;
    }
}
