package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.LuuTruTuDungNFSDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HaTangLuuTruTuDungNFSService {
    @Autowired
    DbContext dbContext;

    public Object createOrUpdate(LuuTruTuDungNFSDTO dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_mahatang_infra", dto.getMahatang_infra(), Types.NVARCHAR));
        params.add(new SqlParameter("p_thanhphan", dto.getThanhphan(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hatang_congnghe_id", dto.getHatang_congnghe_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ip_cungcapdv_id", dto.getIp_cungcapdv_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ip_quantri_id", dto.getIp_quantri_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", dto.getGhi_chu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", dto.getHieu_luc(), Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_tong", dto.getHdd_tong(), Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_nangluc", dto.getHdd_nangluc(), Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_capphat", dto.getHdd_capphat(), Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_tong", dto.getSsd_tong(), Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_nangluc", dto.getSsd_nangluc(), Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_capphat", dto.getSsd_capphat(), Types.NUMERIC));
        params.add(new SqlParameter("p_iops_tong", dto.getIops_tong(), Types.NUMERIC));
        params.add(new SqlParameter("p_iops_nangluc", dto.getIops_nangluc(), Types.NUMERIC));
        params.add(new SqlParameter("p_iops_capphat", dto.getIops_capphat(), Types.NUMERIC));
        params.add(new SqlParameter("p_donviqly_id", dto.getDonviqly_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_phongbanqly_id", dto.getPhongbanqly_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoiqly_id", dto.getNguoiqly_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", dto.getNhanvien_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_may_cn", dto.getMay_capnhap(), Types.NVARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", dto.getNguoi_capnhap(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_cn", dto.getIp_capnhap(), Types.NVARCHAR));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_HATANG_LUUTRU_TUDUNG_INSERT_UPDATE", params);
        return result.getOutValue();
    }

    public Object getAll() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_HATANG_LUUTRU_TUDUNG_GETALL", params);
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
            params.add(new SqlParameter("p_table_name", "HATANGLUUTRU", Types.NVARCHAR));
            params.add(new SqlParameter("p_result", Types.NUMERIC));
            dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_DELETE_BY_ID", params);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Object haTangLuuTruTuDungNFSRelationship(Number id, String tabName) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_object_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_table_name", tabName, Types.NVARCHAR));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_HATANG_LUUTRU_TUDUNG_RELATIONSHIP", params);
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

    public Object haTangLuuTruTuDungNFSRelationshipV2(Number id, String tabName) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_object_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_table_name", tabName, Types.NVARCHAR));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_HATANG_LUUTRU_TUDUNG_RELATIONSHIP", params);
    }

    public Object addVms(Long id, List<Long> nfsIds) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangluutru_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_link_ids",
                nfsIds.stream().map(String::valueOf).collect(Collectors.joining(",")),
                SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_AOHOA.sp_them_vms_vao_hatangluutru", params);
        return null;
    }

    public Object addVirtualInfraAndSmartCloud(Long id, List<Long> nfsIds) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangluutru_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_link_ids",
                nfsIds.stream().map(String::valueOf).collect(Collectors.joining(",")),
                SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_AOHOA.sp_them_hatangaohoa_smartcloud_vao_hatangluutru", params);
        return null;
    }

    public Object addNFS(Long id, List<Long> nfsIds) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangluutru_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_link_ids",
                nfsIds.stream().map(String::valueOf).collect(Collectors.joining(",")),
                SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_AOHOA.sp_them_nfs_vao_hatangluutru", params);
        return null;
    }

    public Object addDevices(Long id, List<Long> nfsIds) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangluutru_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_link_ids",
                nfsIds.stream().map(String::valueOf).collect(Collectors.joining(",")),
                SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_AOHOA.sp_them_thietbi_vao_hatangluutru", params);
        return null;
    }

    public Object getSourceVM(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangluutru_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_AOHOA.sp_source_vms", params);
    }

    public Object getSourceVSmartCloud(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangluutru_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_AOHOA.sp_source_v_smart_cloud", params);
    }

    public Object getSourceNFS(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangluutru_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_AOHOA.sp_source_nfs", params);
    }

    public Object getSourceDevices(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_hatangluutru_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_AOHOA.sp_source_devices", params);
    }
}
