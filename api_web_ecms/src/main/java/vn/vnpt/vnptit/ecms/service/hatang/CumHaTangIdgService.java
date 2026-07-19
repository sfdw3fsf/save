package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.CumHaTangIdgDto;

@Service
public class CumHaTangIdgService {
    private final DbContext dbContext;

    public CumHaTangIdgService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    /**
     * Lưu hoặc cập nhật thông tin cụm hạ tầng IDG
     */
    public Object upsertCumHaTang(CumHaTangIdgDto dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        
        // Output parameter for result
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        // Basic info
        params.add(new SqlParameter("p_ma_cum", dto.getMaCum(), Types.VARCHAR));
        params.add(new SqlParameter("p_ten_cum", dto.getTenCum(), Types.NVARCHAR));
        params.add(new SqlParameter("p_idc_id", dto.getIdcId(), Types.NUMERIC));
        params.add(new SqlParameter("p_cong_nghe_id", dto.getCongNgheId(), Types.NUMERIC));
        params.add(new SqlParameter("p_phan_loai_id", dto.getPhanLoaiId(), Types.NUMERIC));
        params.add(new SqlParameter("p_cap_do_id", dto.getCapDoId(), Types.NUMERIC));
        params.add(new SqlParameter("p_loai_ht_id", dto.getLoaiHtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_trang_thai_ccdv_id", dto.getTrangThaiCcdvId(), Types.NUMERIC));
        
        // Management info
        params.add(new SqlParameter("p_donvi_ql_id", dto.getDonviQlId(), Types.NUMERIC));
        params.add(new SqlParameter("p_phong_ql_id", dto.getPhongQlId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ca_nhan_ql_id", dto.getCaNhanQlId(), Types.NUMERIC));
        params.add(new SqlParameter("p_hieu_luc", dto.getHieuLuc(), Types.NUMERIC));
        
        // Sync / Extra info
        params.add(new SqlParameter("p_rancher_code", dto.getRancherCode(), Types.VARCHAR));
        params.add(new SqlParameter("p_region_id", dto.getRegionId(), Types.VARCHAR));
        params.add(new SqlParameter("p_phien_ban", dto.getPhienBan(), Types.VARCHAR));
        params.add(new SqlParameter("p_ip_quan_tri", dto.getIpQuanTri(), Types.VARCHAR));
        params.add(new SqlParameter("p_link_quan_tri", dto.getLinkQuanTri(), Types.VARCHAR));
        params.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_avatar_url", dto.getAvatarUrl(), Types.VARCHAR));
        
        // Capacity info
        params.add(new SqlParameter("p_total_vcpu", dto.getTotalVcpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_total_vram", dto.getTotalVram(), Types.NUMERIC));
        params.add(new SqlParameter("p_total_vstorage", dto.getTotalVstorage(), Types.NUMERIC));
        params.add(new SqlParameter("p_avail_vcpu", dto.getAvailVcpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_avail_vram", dto.getAvailVram(), Types.NUMERIC));
        params.add(new SqlParameter("p_avail_vstorage", dto.getAvailVstorage(), Types.NUMERIC));
        params.add(new SqlParameter("p_used_vcpu", dto.getUsedVcpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_used_vram", dto.getUsedVram(), Types.NUMERIC));
        params.add(new SqlParameter("p_used_vstorage", dto.getUsedVstorage(), Types.NUMERIC));

        params.add(new SqlParameter("p_nguoidung_id", AppRequestContext.getCurrent().getNguoiDungID() , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        // Note: The SP implementation on DB side should handle MERGE or check if p_ma_cum exists
        Object result = dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_IDG_THUANLN.sp_cum_hatang_idg_upsert", params);
        
        // Gọi thêm stored procedure để lưu thông tin Dịch Vụ Cụm Hạ Tầng
        if (dto.getLoaiDichVuIds() != null && result instanceof Map) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> resultMap = (Map<String, Object>) result;
                
                Number cumhtId = null;
                if (resultMap.containsKey("CUMHT_ID")) {
                    cumhtId = (Number) resultMap.get("CUMHT_ID");
                } else if (resultMap.containsKey("cumht_id")) {
                    cumhtId = (Number) resultMap.get("cumht_id");
                }
                
                if (cumhtId != null) {
                    List<SqlParameter> dvParams = new ArrayList<>();
                    dvParams.add(new SqlParameter("p_cumht_id", cumhtId.longValue(), Types.NUMERIC));
                    dvParams.add(new SqlParameter("p_dich_vu_ids", dto.getLoaiDichVuIds(), Types.VARCHAR));
                    dvParams.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                    
                    dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_IDG_THUANLN.SP_SAVE_DICHVU_CUMHT_BY_MACUM", dvParams);
                } else {
                    System.err.println("Không tìm thấy CUMHT_ID trả về từ sp_cum_hatang_idg_upsert");
                }
            } catch (Exception e) {
                System.err.println("Lỗi lưu Loại tài nguyên cung cấp mạng: " + e.getMessage());
            }
        }
        
        return result;
    }

    /**
     * Lấy thông tin cụm hạ tầng IDG theo mã cụm
     */
    public Object getCluster(String clusterId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        
        // Input parameter comes first based on the user's provided SP structure
        params.add(new SqlParameter("p_cluster_id", clusterId, Types.VARCHAR));
        
        // Output parameter for cursor
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_IDG_THUANLN.SP_LAY_THONG_TIN_TUNG_CUM", params);
    }

    /**
     * Lấy danh sách Loại dịch vụ IDG (có checkbox, CPU, RAM, STORAGE...)
     */
    public Object getListLoaiDichVuIdg(Integer loaiHtId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        // Truyền 4 mặc định theo yêu cầu nếu null
        params.add(new SqlParameter("p_loai_ht_id", loaiHtId != null ? loaiHtId : 4, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_IDG_THUANLN.SP_LAY_DS_LOAI_DICH_VU_IDG", params);
    }

    /**
     * Lấy danh sách Cụm SPDV theo Cụm hạ tầng
     */
    public List<Map<String, Object>> getDsCumSPDV(Number cumIdgId) throws AppSqlException{
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", cumIdgId, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));
        List<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_NHDU.SP_IDG_CUMSPDV_GET_DSACH", params);
        return dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
    }

    /**
     * Lấy chi tiết cụm hạ tầng IDG
     */
    public Object getDetailCluster(Long cumhtId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cumht_id", cumhtId, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_IDG_THUANLN.SP_LAY_CHITIET_CUMHT_BY_ID", params);
    }
}
