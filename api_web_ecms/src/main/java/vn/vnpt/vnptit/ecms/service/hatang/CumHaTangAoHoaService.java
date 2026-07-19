package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.service.idc.VCenterService;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchHistorySyncMayAoDto;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchThietBiV2Dto;
import vn.vnpt.vnptit.ecms.dto.idc.UpsertMayAoDtoV2;
import vn.vnpt.vnptit.ecms.service.idc.MayAoService;
import vn.vnpt.vnptit.ecms.service.client.FeignClientAPIService;
import vn.vnpt.vnptit.ecms.dto.sync.smartCloud.SMCapacityExtDto;
import java.util.HashMap;

@Service
public class CumHaTangAoHoaService {

    @Autowired
    DbContext dbContext;

    @Autowired
    VCenterService vCenterService;

    @Autowired
    MayAoService mayAoService;

    @Autowired
    FeignClientAPIService feignClientAPIService;

    // ======================== TRƯỜNG HỢP CỤM HT VMWARE & SMARTCLOUD =============================
    // ============================================================================================

    /**
     * Lấy danh sách thiết bị thuộc một cụm hạ tầng.
     */

    public ArrayList<Map<String, Object>> getDanhSachThietBiCum(Long cumId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cumht_id", cumId, Types.NUMERIC));
        parameters.add(
                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_THIETBI_CUM", parameters);
    }

    public Object getDanhSachThietBiV2(SearchThietBiV2Dto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.INTEGER));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));

        params.add(new SqlParameter("p_khongGian", dto != null ? dto.getKhongGian() : null, Types.VARCHAR));
        params.add(new SqlParameter("p_rackId", dto != null ? dto.getRackId() : null, Types.NUMERIC));
        params.add(new SqlParameter("p_soHuuId", dto != null ? dto.getSoHuuId() : null, Types.NUMERIC));
        params.add(new SqlParameter("p_loaiThietBi", dto != null ? dto.getLoaiThietBi() : null, Types.NUMERIC));
        params.add(new SqlParameter("p_tenThietBi", dto != null ? dto.getTenThietBi() : null, Types.VARCHAR));
        params.add(new SqlParameter("p_hangSxId", dto != null ? dto.getHangSxId() : null, Types.NUMERIC));
        params.add(new SqlParameter("p_serial", dto != null ? dto.getSerial() : null, Types.VARCHAR));
        params.add(new SqlParameter("p_taisanId", dto != null ? dto.getTaisanId() : null, Types.VARCHAR));
        params.add(new SqlParameter("p_soTheTs", dto != null ? dto.getSoTheTs() : null, Types.VARCHAR));
        params.add(new SqlParameter("p_donViQlId", dto != null ? dto.getDonViQlId() : null, Types.NUMERIC));
        params.add(new SqlParameter("p_caNhanQl", dto != null ? dto.getCaNhanQl() : null, Types.VARCHAR));
        params.add(new SqlParameter("p_pageIndex", dto != null ? dto.getPageIndex() : null, Types.NUMERIC));
        params.add(new SqlParameter("p_pageSize", dto != null ? dto.getPageSize() : null, Types.NUMERIC));
        params.add(new SqlParameter("p_ht_id", dto != null ? dto.getCumhtId() : null, Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", dto != null ? dto.getIdcId() : null, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        ArrayList<Map<String, Object>> dbResult = dbContext
                .executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_TBI_V2", params);

        List<Map<String, Object>> result = dbResult.stream().map(map -> {
            Map<String, Object> transformed = map.entrySet().stream()
                    .filter(entry -> entry.getValue() != null)
                    .collect(Collectors.toMap(
                            entry -> entry.getKey().toLowerCase(),
                            Map.Entry::getValue,
                            (v1, v2) -> v1,
                            LinkedHashMap::new));
            return transformed;
        }).collect(Collectors.toList());

        return result;
    }

    /**
     * Lấy danh sách vùng dữ liệu (DATASTORE) thuộc một thiết bị.
     */
    public ArrayList<Map<String, Object>> getDanhSachVungDuLieuByThietBi(Long thietBiId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_thietbi_id", thietBiId, Types.NUMERIC));
        parameters.add(
                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_VUNGDL_BY_THIETBI",
                parameters);
    }

    public Object insertBatchThietBiCum(Long cumId, String idsStr) throws AppSqlException {
        if (idsStr == null || idsStr.isEmpty()) {
            throw new IllegalArgumentException("Danh sách thiết bị không được để trống");
        }

        // Cập nhật người dùng hiện tại
        int nhanVienId = AppRequestContext.getCurrent().getNhanVienID();

        List<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cumht_id", cumId, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ids", idsStr, Types.VARCHAR));
        parameters.add(new SqlParameter("p_nhanvien_id", nhanVienId, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", Types.NUMERIC));

        dbContext.executeStoredProcedure("ECMS.PKG_THI_AOHOA_TEST.SP_INSERT_THIETBI_CUM", parameters);

        return getDanhSachThietBiCum(cumId);
    }

    public Object deleteThietBiCum(Long cumId, String idString) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cumht_id", cumId, Types.NUMERIC));
        params.add(new SqlParameter("p_ids", idString, Types.VARCHAR));
        params.add(new SqlParameter("p_result", Types.NUMERIC));
        dbContext.executeStoredProcedure("ECMS.PKG_THI_AOHOA_TEST.SP_DELETE_THIETBI_CUM", params);
        return Boolean.TRUE;
    }

    /**
     * Lấy danh sách máy ảo thuộc một cụm hạ tầng.
     * 
     * @param vpcType null/-1 = tất cả, 1 = chỉ VPC, 0 = chỉ máy ảo thường
     */
    public ArrayList<Map<String, Object>> getDanhSachMayAoCum(Long cumId, Integer vpcType) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cumht_id", cumId, Types.NUMERIC));
        parameters.add(new SqlParameter("p_vpc_type", vpcType, Types.NUMERIC));
        parameters.add(
                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_MAYAO_CUM", parameters);
    }

    /**
     * Lấy danh sách tài nguyên khác (DICHVU_HT) thuộc một cụm hạ tầng.
     * 
     * @param type null = tất cả, 1 = chỉ IDG, 0 = DVHT khác IDG
     */
    public ArrayList<Map<String, Object>> getTaiNguyenKhacCum(Long cumId, Integer type) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cumht_id", cumId, Types.NUMERIC));
        parameters.add(new SqlParameter("p_type", type, Types.NUMERIC));
        parameters.add(
                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_TAINGUYEN_KHAC_CUM",
                parameters);
    }

    // =================================== TRƯỜNG HỢP CỤM HT IDG =================================
    // ============================================================================================
    /**
     * Lấy danh sách máy ảo (MAYAO) thuộc CỤM hạ tầng IDG
     */
    public ArrayList<Map<String, Object>> getDanhSachMayAoIDG(Long cumId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cumht_id", cumId, Types.NUMERIC));
        parameters.add(
                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_MAYAO_BY_CUM_IDG", parameters);
    }

    /**
     * Lấy danh sách thiết bị + máy ảo (MAYAO) thuộc một cụm hạ tầng.
     */
    public ArrayList<Map<String, Object>> getDanhSachThietBiMayAo(Long cumId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cumht_id", cumId, Types.NUMERIC));
        parameters.add(
                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_THIETBI_MAYAO_BY_CUM_IDG", parameters);
    }

    /*
     * Lấy danh sách tài nguyên IDG thuộc cụm hạ tầng IDG
     */
    public ArrayList<Map<String, Object>> getDanhSachTaiNguyenIDG(Long cumId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cumht_id", cumId, Types.NUMERIC));
        parameters.add(
                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_DICHVU_HT_BY_CUM_IDG", parameters);
    }

    // =================== ĐỒNG BỘ TỪ VCENTER =====================
    /**
     * Đồng bộ danh sách máy ảo từ vCenter
     * 
     * @param clusterCode
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Map<String, Object>> syncVMs(String clusterCode) throws Exception {
        Object rawVmInfo = vCenterService.getClusterVms(clusterCode);
        // Map to array list
        ArrayList<Map<String, Object>> vmList = new ArrayList<>();
        if (rawVmInfo instanceof List) {
            // Map object sang Array
            vmList = (ArrayList<Map<String, Object>>) rawVmInfo;
            // duyệt qua từng máy ảo và check xem đã tồn tại trên DB chưa
            for (Map<String, Object> vm : vmList) {
                String maMayAo = (String) vm.get("vm");
                // check đã tồn tại chưa. Nếu chưa insert, nếu có update (nếu có thay đổi)
                UpsertMayAoDtoV2 detailMayAo = syncVmDetail(maMayAo, clusterCode);
                System.out.println("detailMayAo: " + detailMayAo.toString());
            }
        }
        return vmList;
    }

    /**
     * Đồng bộ thông tin chi tiết máy ảo
     * 
     * @param vmCode
     * @param clusterCode
     * @return
     */
    public UpsertMayAoDtoV2 syncVmDetail(String vmCode, String clusterCode) {
        Object rawData = vCenterService.getVmDetail(vmCode);
        if (!(rawData instanceof Map)) {
            throw new RuntimeException("Dữ liệu trả về từ gateway không hợp lệ");
        }
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) rawData;
        UpsertMayAoDtoV2 mayAoDto = mayAoService.mapVCenterToUpsertDto(data, clusterCode);

        try {
            mayAoService.syncMayAoByCumHT(mayAoDto);
        } catch (AppSqlException e) {
            e.printStackTrace();
        }

        return mayAoDto;
    }

    public ArrayList<Map<String, Object>> syncVMHistory(SearchHistorySyncMayAoDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cumht_id", dto.getCumId(), Types.NUMERIC));
        parameters.add(new SqlParameter("p_loai_doituong", dto.getLoaiDoiTuong(), Types.VARCHAR));
        parameters.add(new SqlParameter("p_ma_mayao", dto.getMaMayAo(), Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_mayao", dto.getTenMayAo(), Types.VARCHAR));
        parameters.add(new SqlParameter("p_ngay_cp_from", dto.getNgayCpFrom(), Types.DATE));
        parameters.add(new SqlParameter("p_ngay_cp_to", dto.getNgayCpTo(), Types.DATE));
        parameters.add(
                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.pkg_idc_tainguyen_aohoa.SP_GET_LICHSU_SYNC_MAYAO_BY_CUMHT",
                parameters);
    }

    public Map<String, List<Map<String, Object>>> syncNangLucCumSmartCloud(String regionCode) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        List<Map<String, Object>> successList = new ArrayList<>();
        List<Map<String, Object>> failureList = new ArrayList<>();

        Long cumHtId = null;

        // Tra cứu cumHtId từ DB thông qua regionCode
        try {
            ArrayList<SqlParameter> pFind = new ArrayList<>();
            pFind.add(new SqlParameter("p_region_code", regionCode, Types.VARCHAR));
            pFind.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
            List<Map<String, Object>> dsCum = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_FIND_CUMHT_BY_REGIONCODE", pFind);

            if (dsCum != null && !dsCum.isEmpty() && dsCum.get(0).get("CUMHT_ID") != null) {
                cumHtId = ((Number) dsCum.get(0).get("CUMHT_ID")).longValue();
            } else {
                Map<String, Object> err = new HashMap<>();
                err.put("type", "ALL");
                err.put("message", "Không tìm thấy Cụm Hạ Tầng theo mã Region " + regionCode);
                failureList.add(err);
                result.put("success", successList);
                result.put("failure", failureList);
                return result;
            }
        } catch (Exception e) {
            Map<String, Object> err = new HashMap<>();
            err.put("type", "DB_QUERY");
            err.put("message", "Lỗi CSDL khi lấy CUMHT_ID: " + e.getMessage());
            failureList.add(err);
            result.put("success", successList);
            result.put("failure", failureList);
            return result;
        }

        try {
            SMCapacityExtDto rs = feignClientAPIService.smCloudCapacityExt(regionCode);
            if (rs == null || rs.getTotals() == null) {
                Map<String, Object> err = new HashMap<>();
                err.put("type", "ALL");
                err.put("message", "Dữ liệu trả về null hoặc không hợp lệ");
                failureList.add(err);
                result.put("success", successList);
                result.put("failure", failureList);
                return result;
            }

            Double cpuTot = 0D, cpuKha = 0D, cpuThuc = 0D;
            Double ramTot = 0D, ramKha = 0D, ramThuc = 0D;
            Double bkTot = 0D, bkKha = 0D, bkThuc = 0D;
            Double hddTot = 0D, hddKha = 0D, hddThuc = 0D;
            Double ssdTot = 0D, ssdKha = 0D, ssdThuc = 0D;

            for (SMCapacityExtDto.CapacityNodeDto item : rs.getTotals()) {
                String type = item.getResourceType();
                if (type == null) continue;

                Double rVal = item.getRealValue() != null ? item.getRealValue() : 0;
                Double val = item.getValue() != null ? item.getValue() : 0; // Kha nang
                Double usedReal = item.getUsageRealValue() != null ? item.getUsageRealValue() : 0;

                if ("CPU".equalsIgnoreCase(type)) {
                    cpuTot = rVal; cpuKha = val; cpuThuc = usedReal;
                    Map<String, Object> sc = new HashMap<>();
                    sc.put("type", "CPU"); sc.put("message", "Thành công");
                    successList.add(sc);
                } else if ("RAM".equalsIgnoreCase(type)) {
                    ramTot = rVal; ramKha = val; ramThuc = usedReal;
                    Map<String, Object> sc = new HashMap<>();
                    sc.put("type", "RAM"); sc.put("message", "Thành công");
                    successList.add(sc);
                } else if ("Backup".equalsIgnoreCase(type)) {
                    bkTot = rVal; bkKha = val; bkThuc = usedReal;
                    Map<String, Object> sc = new HashMap<>();
                    sc.put("type", "Backup"); sc.put("message", "Thành công");
                    successList.add(sc);
                } else if ("volume_hdd".equalsIgnoreCase(type)) {
                    hddTot = rVal; hddKha = val; hddThuc = usedReal;
                    Map<String, Object> sc = new HashMap<>();
                    sc.put("type", "volume_hdd"); sc.put("message", "Thành công");
                    successList.add(sc);
                } else if ("volume_ssd".equalsIgnoreCase(type)) {
                    ssdTot = rVal; ssdKha = val; ssdThuc = usedReal;
                    Map<String, Object> sc = new HashMap<>();
                    sc.put("type", "volume_ssd"); sc.put("message", "Thành công");
                    successList.add(sc);
                } else if ("IPPublic".equalsIgnoreCase(type)) {
                    Map<String, Object> fail = new HashMap<>();
                    fail.put("type", "IPPublic");
                    fail.put("message", "Chưa triển khai");
                    failureList.add(fail);
                }
            }

            // Gọi store procedure lưu năng lực cụm
            String userCn = "KIETVH_SYNC";
            try {
                if (AppRequestContext.getCurrent() != null && AppRequestContext.getCurrent().getUserName() != null) {
                    userCn = AppRequestContext.getCurrent().getUserName();
                }
            } catch (Exception e) {}

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_cumht_id", cumHtId, Types.NUMERIC));
            params.add(new SqlParameter("p_cpu_total", cpuTot, Types.NUMERIC));
            params.add(new SqlParameter("p_cpu_khanang", cpuKha, Types.NUMERIC));
            params.add(new SqlParameter("p_cpu_thucte", cpuThuc, Types.NUMERIC));
            params.add(new SqlParameter("p_ram_total", ramTot, Types.NUMERIC));
            params.add(new SqlParameter("p_ram_khanang", ramKha, Types.NUMERIC));
            params.add(new SqlParameter("p_ram_thucte", ramThuc, Types.NUMERIC));
            params.add(new SqlParameter("p_backup_total", bkTot, Types.NUMERIC));
            params.add(new SqlParameter("p_backup_khanang", bkKha, Types.NUMERIC));
            params.add(new SqlParameter("p_backup_thucte", bkThuc, Types.NUMERIC));
            params.add(new SqlParameter("p_hdd_total", hddTot, Types.NUMERIC));
            params.add(new SqlParameter("p_hdd_khanang", hddKha, Types.NUMERIC));
            params.add(new SqlParameter("p_hdd_thucte", hddThuc, Types.NUMERIC));
            params.add(new SqlParameter("p_ssd_total", ssdTot, Types.NUMERIC));
            params.add(new SqlParameter("p_ssd_khanang", ssdKha, Types.NUMERIC));
            params.add(new SqlParameter("p_ssd_thucte", ssdThuc, Types.NUMERIC));
            params.add(new SqlParameter("p_nguoi_cn", userCn, Types.VARCHAR));
            params.add(new SqlParameter("p_result", Types.NUMERIC));

            try {
                dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_SYNC_NANGLUC_CUMHT_SMARTCLOUD", params);
            } catch (Exception e) {
                Map<String, Object> err = new HashMap<>();
                err.put("type", "DB_UPDATE");
                err.put("message", "Lỗi lưu DB: " + e.getMessage());
                failureList.add(err);
            }

        } catch (Exception e) {
            Map<String, Object> err = new HashMap<>();
            err.put("type", "CALL_API");
            err.put("message", "Lỗi kết nối SmartCloud API: " + e.getMessage());
            failureList.add(err);
        }

        result.put("success", successList);
        result.put("failure", failureList);
        return result;
    }

}
