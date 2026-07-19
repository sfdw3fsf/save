package vn.vnpt.vnptit.ecms.service.idc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.VpcDto;
import vn.vnpt.vnptit.ecms.dto.sync.smartCloud.SMCloudVPCDetailDTO;
import vn.vnpt.vnptit.ecms.dto.sync.smartCloud.SMCloudVpcItemDTO;
import vn.vnpt.vnptit.ecms.service.client.FeignClientAPIService;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service("idcVpcService")
public class VpcService {

        private static final Logger log = LoggerFactory.getLogger(VpcService.class);

        // Mapping SmartCloud serviceCode -> LOAI_DVHT.MA_SPDV
        private static final Map<String, String> SERVICE_CODE_TO_MA_SPDV;
        static {
                SERVICE_CODE_TO_MA_SPDV = new HashMap<>();
                SERVICE_CODE_TO_MA_SPDV.put("instance", "vm");
                SERVICE_CODE_TO_MA_SPDV.put("volume", "volume");
                SERVICE_CODE_TO_MA_SPDV.put("ip", "ippublic");
                SERVICE_CODE_TO_MA_SPDV.put("snapshot", "snapshot_package");
                SERVICE_CODE_TO_MA_SPDV.put("backup", "backup_package");
                SERVICE_CODE_TO_MA_SPDV.put("file storage", "sharefile_storage");
                SERVICE_CODE_TO_MA_SPDV.put("object_storage", "object_storage");
                SERVICE_CODE_TO_MA_SPDV.put("loadbalancer", "loadbalancer_sdn");
                SERVICE_CODE_TO_MA_SPDV.put("vpn", "vpn_site_to_site");
        }
        private final DbContext dbContext;
        private final FeignClientAPIService feignClientAPIService;

        public VpcService(DbContext dbContext, FeignClientAPIService feignClientAPIService) {
                this.dbContext = dbContext;
                this.feignClientAPIService = feignClientAPIService;
        }

        public ArrayList<Map<String, Object>> getDanhSachVPC(VpcDto dto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_ma_vpc", dto.getMaVpc(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(
                                new SqlParameter("p_ten_vpc", dto.getTenVpc(), SqlParameter.ParameterDirection.INPUT,
                                                Types.VARCHAR));
                params.add(new SqlParameter("p_cumht_id", dto.getCumHaTangId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_donvi_cq_id", dto.getDonViCqId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_donvi_ql_id", dto.getDonViQlId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_loaivpc_id", dto.getLoaiVpcId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_doituong_id", dto.getDoiTuongId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_trangthai_id", dto.getTrangThaiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_tinhphi_id", dto.getTinhPhiId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_hieuluc_id", dto.getHieulucId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));

                params.add(new SqlParameter("p_ngaytao_tu", dto.getNgaytaoTu(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ngaytao_den", dto.getNgaytaoDen(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ngayhh_tu", dto.getNgayHethanTu(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_ngayhh_den", dto.getNgayHethanDen(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));

                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));

                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_DANHSACH_VPC",
                                params);
        }

        public ArrayList<Map<String, Object>> getDetailVpc(Number vpcId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vpc_id", vpcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_pks", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_DETAIL_VPC", params);
        }

        public Map<String, Object> upsertVpc(VpcDto dto) throws AppSqlException {
                ArrayList<SqlParameter> params = getVpcSqlParameters(dto);
                params.add(new SqlParameter("p_vpc_id", dto.getVpcId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_UPSERT_VPC", params);
        }

        public Map<String, Object> deleteVpc(Number vpcId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vpc_id", vpcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_DELETE_VPC", params);
        }

        @Transactional(rollbackFor = Exception.class)
        public Map<String, Object> deleteVpcBulk(List<Number> vpcIds) throws AppSqlException {
                if (vpcIds == null || vpcIds.isEmpty())
                        return new HashMap<>();

                String idsStr = vpcIds.stream().map(String::valueOf).collect(Collectors.joining(","));

                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vpc_ids", idsStr, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

                return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_DELETE_VPC_BULK", params);
        }

        public List<Map<String, Object>> getLichsuSyncVpc(Number vpcId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vpc_id", vpcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));

                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_LICHSU_SYNC_VPC",
                                params);
        }

        private ArrayList<SqlParameter> getVpcSqlParameters(VpcDto dto) {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_ma_vpc", dto.getMaVpc(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(
                                new SqlParameter("p_ten_vpc", dto.getTenVpc(), SqlParameter.ParameterDirection.INPUT,
                                                Types.VARCHAR));
                params.add(new SqlParameter("p_loai_vpc_id", dto.getLoaiVpcId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(
                                new SqlParameter("p_tinh_phi", dto.getTinhPhi(), SqlParameter.ParameterDirection.INPUT,
                                                Types.NUMERIC));
                params.add(
                                new SqlParameter("p_hieu_luc", dto.getHieuLuc(), SqlParameter.ParameterDirection.INPUT,
                                                Types.NUMERIC));
                params.add(new SqlParameter("p_ma_ha_tang", dto.getMaHaTang(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_trang_thai_id", dto.getTrangThaiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_nguoi_yc_id", dto.getNguoiYcId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_doi_tuong_id", dto.getDoiTuongId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_donvi_yc_id", dto.getDonViYcYId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_cum_ha_tang_id", dto.getCumHaTangId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_nguon_yc_id", dto.getNguonYcId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_ngay_tao", dto.getNgayTao(), SqlParameter.ParameterDirection.INPUT,
                                Types.DATE));
                params.add(new SqlParameter("p_ngay_het_han", dto.getNgayHetHan(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.DATE));
                params.add(
                                new SqlParameter("p_ghi_chu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT,
                                                Types.VARCHAR));
                params.add(new SqlParameter("p_donvi_ql_id", dto.getDonViQlId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_phong_ql_id", dto.getPhongQlId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_ca_nhan_ql_id", dto.getCaNhanQlId(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_donvi_cq_id", dto.getDonViCqId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_vcpu", dto.getVcpu(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_vram", dto.getVram(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_vgpu", dto.getVgpu(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_hdd", dto.getHdd(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_ssd", dto.getSsd(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_iops", dto.getIops(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_throughput", dto.getThroughput(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_so_luong_ip", dto.getSoLuongIp(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                return params;
        }

        public ArrayList<Map<String, Object>> getAddonVpc(Number vpcId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vpc_id", vpcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_DUONG.SP_GET_DS_ADDON", params);
        }

        public ArrayList<Map<String, Object>> getNetworkIpVpc(Number vpcId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vpc_id", vpcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_DUONG.SP_GET_DS_NETWORKIP", params);
        }

        public ArrayList<Map<String, Object>> getCumSPDVVpc(Number vpcId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vpc_id", vpcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_DUONG.SP_GET_DS_CUMSPDV", params);
        }

        // ==================== Đồng bộ VPC từ SmartCloud ====================

        /**
         * Đồng bộ và lưu VPC từ SmartCloud External API.
         * Gọi FeignClientAPIService.smCloudVPCDetailExt() lấy chi tiết VPC,
         * sau đó gọi SP_SYNC_VPC_FROM_SMARTCLOUD để upsert vào bảng MAYAO (VPC=1).
         * Chỉ cần truyền maVPC – dùng làm key tìm kiếm và gọi API.
         */
        @Transactional(rollbackFor = Exception.class)
        public Number syncAndSaveVpc(String maVPC, long smCloudId,
                        int serviceType, String maTB, Number vpcId, Number cumHtId) throws AppSqlException {

                // Bước 1: Gọi SmartCloud API
                SMCloudVPCDetailDTO detail;
                try {
                        detail = feignClientAPIService.smCloudVPCDetailExt(
                                        maVPC, smCloudId, serviceType, maTB != null ? maTB : maVPC);
                } catch (Exception e) {
                        String rootCause = e.getMessage();
                        if (e.getCause() != null) {
                                rootCause = e.getCause().getMessage();
                        }
                        log.error("[syncSaveVpc] Lỗi gọi SmartCloud API: {}", rootCause);
                        throw new AppSqlException("Lỗi kết nối SmartCloud API - " + rootCause);
                }

                if (detail == null) {
                        throw new AppSqlException("Không tìm thấy thông tin VPC từ SmartCloud (mã " + maVPC + ")");
                }

                // Bước 2: Tìm VPC_ID hiện tại trong DB theo MA_MAYAO nếu chưa có vpcId
                if (vpcId == null || vpcId.longValue() == 0) {
                        vpcId = findVpcIdByMaVpc(maVPC);
                        if (vpcId != null) {
                                log.info("[syncSaveVpc] Tìm thấy VPC trong DB: MAYAO_ID={}", vpcId);
                        }
                }

                // Bước 3: Serialize addons & serviceItems → JSON
                String addonsJson = serializeToJson(detail.getAddons());
                String serviceItemsJson = serializeToJson(detail.getServiceItems());

                // Bước 4: Gọi SP_SYNC_VPC_FROM_SMARTCLOUD
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vpc_id", vpcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_ma_vpc", maVPC, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_ma_tb", detail.getMaTB(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_total_cpu", detail.getTotalCpu(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_total_ram", detail.getTotalRam(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_total_hdd", detail.getTotalHdd(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_total_ssd", detail.getTotalSsd(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_total_ip", detail.getTotalIP(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_offer_cpu", detail.getOfferCpu(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_offer_ram", detail.getOfferRam(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_offer_hdd", detail.getOfferHdd(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_offer_ssd", detail.getOfferSsd(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_create_date", detail.getCreateDate(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_expire_date", detail.getExpireDate(),
                                SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(
                                new SqlParameter("p_status", detail.getStatus(), SqlParameter.ParameterDirection.INPUT,
                                                Types.VARCHAR));
                params.add(new SqlParameter("p_service_status", detail.getServiceStatus(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_addons_json", addonsJson, SqlParameter.ParameterDirection.INPUT,
                                Types.CLOB));
                params.add(new SqlParameter("p_svc_items_json", serviceItemsJson, SqlParameter.ParameterDirection.INPUT,
                                Types.CLOB));
                params.add(new SqlParameter("p_nguoi_cn", getCurrentUserName(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_cum_ha_tang_id", cumHtId, SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

                Map<String, Object> resMap = dbContext.executeSpWithCursorToMap(
                                "ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_SYNC_VPC_FROM_SMARTCLOUD", params);

                // Extract VPC_ID (p_result) trực tiếp
                Number resultId = resMap != null && resMap.get("item") != null
                                ? (Number) resMap.get("item")
                                : null;

                // Bước 5: Đồng bộ serviceItems
                if (resultId != null && detail.getServiceItems() != null && !detail.getServiceItems().isEmpty()) {
                        List<SMCloudVPCDetailDTO.ServiceItemsDTO> items = detail.getServiceItems();
                        log.info("[syncSaveVpc] Đồng bộ {} serviceItems...", items.size());

                        // 5a. Xử lý instance (VM) TRƯỚC — để có MAYAO_ID cho các dịch vụ attach sau
                        Map<String, Number> vmMap = new HashMap<>(); // smCloudServiceName → MAYAO_ID
                        for (SMCloudVPCDetailDTO.ServiceItemsDTO item : items) {
                                if ("instance".equalsIgnoreCase(item.getServiceCode())) {
                                        try {
                                                Number vmId = syncInstanceToMayao(resultId, item);
                                                if (vmId != null && item.getServiceName() != null) {
                                                        vmMap.put(item.getServiceName(), vmId);
                                                }
                                        } catch (Exception e) {
                                                log.warn("[syncSaveVpc] Lỗi sync instance [{}]: {}",
                                                                item.getServiceName(), e.getMessage());
                                        }
                                }
                        }

                        // 5b. Xử lý các dịch vụ khác → DICHVU_HT (skip instance)
                        for (SMCloudVPCDetailDTO.ServiceItemsDTO item : items) {
                                if ("instance".equalsIgnoreCase(item.getServiceCode()))
                                        continue;

                                // Tìm xem dịch vụ có attach vào VM nào không
                                Number targetVmId = null;
                                if (item.getDetails() != null) {
                                        for (SMCloudVPCDetailDTO.ServiceItemsDTO.DetailsDTO d : item.getDetails()) {
                                                if ("AttachToVM".equalsIgnoreCase(d.getUnit()) && d.getName() != null) {
                                                        targetVmId = vmMap.get(d.getName()); // Lấy MAYAO_ID từ map dựa
                                                                                             // trên tên VM
                                                        break;
                                                }
                                        }
                                }

                                try {
                                        syncServiceItemToDichvuHT(resultId, item, targetVmId);
                                } catch (Exception e) {
                                        log.warn("[syncSaveVpc] Lỗi sync serviceItem [{}/{}]: {}",
                                                        item.getServiceCode(), item.getServiceName(), e.getMessage());
                                }
                        }
                }

                return resultId;
        }

        /**
         * Đồng bộ và lưu danh sách VPC từ SmartCloud
         * Gọi API lấy mảng, sau đó dùng lại method đồng bộ chi tiết.
         */
        public Map<String, List<Map<String, Object>>> syncAndSaveVpcList(String regionCode) throws AppSqlException {
                log.info("[syncAndSaveVpcList] Tìm CUMHT_ID cho regionCode={}...", regionCode);

                Number cumHtId = findCumHtIdByRegionCode(regionCode);

                if (cumHtId == null) {
                        throw new AppSqlException(
                                        "mã cụm hạ tầng chưa tồn tại hoặc không có hiệu lực, cần đồng bộ chi tiết cụm hạ tầng trước");
                }

                log.info("[syncAndSaveVpcList] Gọi API SmartCloud lấy danh sách VPC, regionCode={}", regionCode);
                List<SMCloudVpcItemDTO> list = feignClientAPIService.smCloudVPCListExt(regionCode);

                List<Map<String, Object>> successList = new ArrayList<>();
                List<Map<String, Object>> failureList = new ArrayList<>();

                if (list != null && !list.isEmpty()) {
                        log.info("[syncAndSaveVpcList] Lấy được {} VPCs. Đang đồng bộ từng VPC...", list.size());
                        for (SMCloudVpcItemDTO item : list) {
                                try {
                                        if (item.getMaTB() == null || item.getMaTB().trim().isEmpty()) {
                                                throw new Exception("mã TB không hợp lệ (null/empty)");
                                        }

                                        syncAndSaveVpc(item.getMaTB(), item.getId() != null ? item.getId() : 0L,
                                                        item.getServiceType() != null ? item.getServiceType() : 0,
                                                        item.getMaTB(), null, cumHtId);

                                        Map<String, Object> successItem = new HashMap<>();
                                        successItem.put("maTB", item.getMaTB());
                                        successList.add(successItem);
                                } catch (Exception e) {
                                        log.warn("[syncAndSaveVpcList] Lỗi sync VPC {}: {}",
                                                        item.getMaTB(), e.getMessage());
                                        Map<String, Object> failureItem = new HashMap<>();
                                        failureItem.put("maTB", item.getMaTB());
                                        failureItem.put("Error", e.getMessage());
                                        failureList.add(failureItem);
                                }
                        }
                } else {
                        log.warn("[syncAndSaveVpcList] Danh sách rỗng hoặc null cho regionCode={}", regionCode);
                }

                Map<String, List<Map<String, Object>>> result = new HashMap<>();
                result.put("success", successList);
                result.put("failure", failureList);
                return result;
        }

        /**
         * Tìm MAYAO_ID (VPC) theo mã VPC (MA_MAYAO) trong bảng MAYAO (VPC=1).
         */
        private Number findVpcIdByMaVpc(String maVpc) throws AppSqlException {
                if (maVpc == null || maVpc.trim().isEmpty())
                        return null;

                VpcDto searchDto = new VpcDto();
                searchDto.setMaVpc(maVpc);
                ArrayList<Map<String, Object>> list = getDanhSachVPC(searchDto);
                if (list != null && !list.isEmpty()) {
                        for (Map<String, Object> row : list) {
                                String dbMaVpc = row.get("MA_MAYAO") != null ? row.get("MA_MAYAO").toString() : null;
                                if (maVpc.equalsIgnoreCase(dbMaVpc)) {
                                        Object id = row.get("MAYAO_ID");
                                        return id instanceof Number ? (Number) id : null;
                                }
                        }
                }
                return null;
        }

        /**
         * Đồng bộ instance (VM) từ SmartCloud → MAYAO (VPC=0) + NANGLUC_MAYAO.
         * Parse CPU/RAM/SSD/HDD từ details array.
         * Return MAYAO_ID của VM được tạo/cập nhật.
         */
        private Number syncInstanceToMayao(Number vpcId, SMCloudVPCDetailDTO.ServiceItemsDTO item)
                        throws AppSqlException {
                // Parse details → CPU, RAM, SSD, HDD
                int cpu = 0, ram = 0, ssd = 0, hdd = 0;
                if (item.getDetails() != null) {
                        for (SMCloudVPCDetailDTO.ServiceItemsDTO.DetailsDTO d : item.getDetails()) {
                                if (d.getUnit() == null)
                                        continue;
                                switch (d.getUnit().toLowerCase()) {
                                        case "vcpu":
                                                cpu = d.getQuantity() != null ? d.getQuantity() : 0;
                                                break;
                                        case "gb":
                                                if ("RAM".equalsIgnoreCase(d.getName())) {
                                                        ram = d.getQuantity() != null ? d.getQuantity() : 0;
                                                } else if ("SSD".equalsIgnoreCase(d.getName())) {
                                                        ssd = d.getQuantity() != null ? d.getQuantity() : 0;
                                                } else if ("HDD".equalsIgnoreCase(d.getName())) {
                                                        hdd = d.getQuantity() != null ? d.getQuantity() : 0;
                                                }
                                                break;
                                }
                        }
                }

                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vpc_id", vpcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_ma_mayao", item.getServiceName(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_cpu", cpu, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_ram", ram, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_ssd", ssd, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_hdd", hdd, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_status", item.getStatus(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_create_date", item.getCreateDate(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_expire_date", item.getExpiredDate(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_nguoi_cn", getCurrentUserName(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

                Map<String, Object> res = dbContext.executeSpWithCursorToMap(
                                "ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_SYNC_VPC_INSTANCE", params);
                Number vmId = res != null && res.get("item") != null ? (Number) res.get("item") : null;
                log.info("[syncSaveVpc] VM: {} (CPU={}, RAM={}, SSD={}) -> MAYAO_ID={}",
                                item.getServiceName(), cpu, ram, ssd, vmId);
                return vmId;
        }

        /**
         * Đồng bộ 1 serviceItem từ SmartCloud → DICHVU_HT.
         * serviceName dùng cho cả TEN_DICHVU và MA_DICHVU.
         * Lookup LOAIDVHT_ID qua LOAI_DVHT.MA_SPDV.
         * Có thể attach vào 1 VM con nếu targetVmId != null.
         */
        private void syncServiceItemToDichvuHT(Number vpcId, SMCloudVPCDetailDTO.ServiceItemsDTO item,
                        Number targetVmId)
                        throws AppSqlException {
                String loaidvhtMa = mapServiceCodeToMaSpdv(item.getServiceCode());
                String detailsJson = serializeToJson(item.getDetails());

                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vpc_id", vpcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_ma_dichvu", item.getServiceName(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_loaidvht_ma", loaidvhtMa, SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_mayao_id", targetVmId, SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_status", item.getStatus(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_create_date", item.getCreateDate(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_expire_date", item.getExpiredDate(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_details_json", detailsJson, SqlParameter.ParameterDirection.INPUT,
                                Types.CLOB));
                params.add(new SqlParameter("p_nguoi_cn", getCurrentUserName(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

                Map<String, Object> res = dbContext.executeSpWithCursorToMap(
                                "ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_SYNC_VPC_DICHVU_HT", params);
                Number dichvuhtId = res != null && res.get("item") != null ? (Number) res.get("item") : null;
                log.info("[syncSaveVpc] {}/{} -> LOAI={}, DICHVUHT_ID={}",
                                item.getServiceCode(), item.getServiceName(), loaidvhtMa, dichvuhtId);
        }

        /**
         * Map SmartCloud serviceCode → LOAI_DVHT.MA_SPDV.
         */
        private String mapServiceCodeToMaSpdv(String smCloudServiceCode) {
                if (smCloudServiceCode == null)
                        return null;
                return SERVICE_CODE_TO_MA_SPDV.getOrDefault(smCloudServiceCode.toLowerCase(), smCloudServiceCode);
        }

        /**
         * Serialize object → JSON string.
         */
        private String serializeToJson(Object obj) {
                if (obj == null)
                        return null;
                try {
                        ObjectMapper mapper = new ObjectMapper();
                        return mapper.writeValueAsString(obj);
                } catch (Exception e) {
                        log.error("[syncSaveVpc] Lỗi serialize JSON: {}", e.getMessage());
                        return null;
                }
        }

        /**
         * Lấy userName từ HTTP request context, fallback "KIETVH_SYNC" khi chạy từ cronjob (background thread).
         */
        private String getCurrentUserName() {
                try {
                        return AppRequestContext.getCurrent().getUserName();
                } catch (Exception e) {
                        return "KIETVH_SYNC";
                }
        }

        /**
         * Đồng bộ tất cả dịch vụ hạ tầng KHÔNG thuộc VPC (serviceCode != "vpc") từ SmartCloud.
         * Gọi API /subscription/service/all?regionCode=xxx, lọc serviceCode != "vpc",
         * sau đó gọi /subscription/service-detail cho từng dịch vụ để lấy chi tiết (details[]).
         * Phân loại và upsert vào bảng DICHVU_HT + NANGLUC_DVHT hoặc MAYAO + NANGLUC_MAYAO.
         */
        @Transactional(rollbackFor = Exception.class)
        public Map<String, List<Map<String, Object>>> syncNonVpcServices(String regionCode) throws AppSqlException {
                log.info("[syncNonVpcServices] Bắt đầu đồng bộ DVHT không thuộc VPC, regionCode={}", regionCode);

                // 1. Lookup CUMHT_ID từ regionCode
                Number cumHtId = findCumHtIdByRegionCode(regionCode);
                if (cumHtId == null) {
                        throw new AppSqlException(
                                        "Mã cụm hạ tầng chưa tồn tại hoặc không có hiệu lực cho regionCode=" + regionCode);
                }
                log.info("[syncNonVpcServices] CUMHT_ID={} cho regionCode={}", cumHtId, regionCode);

                // 2. Gọi API BCA lấy tất cả dịch vụ
                List<SMCloudVpcItemDTO> allServices = feignClientAPIService.smCloudServiceAllExt(regionCode);

                List<Map<String, Object>> successList = new ArrayList<>();
                List<Map<String, Object>> failureList = new ArrayList<>();

                if (allServices == null || allServices.isEmpty()) {
                        log.warn("[syncNonVpcServices] Danh sách dịch vụ rỗng hoặc null cho regionCode={}", regionCode);
                        Map<String, List<Map<String, Object>>> result = new HashMap<>();
                        result.put("success", successList);
                        result.put("failure", failureList);
                        return result;
                }

                // 3. Lọc dịch vụ có serviceCode != "vpc"
                List<SMCloudVpcItemDTO> nonVpcServices = allServices.stream()
                                .filter(item -> !"vpc".equalsIgnoreCase(item.getServiceCode()))
                                .collect(Collectors.toList());

                log.info("[syncNonVpcServices] Tổng dịch vụ: {}, Không thuộc VPC: {}",
                                allServices.size(), nonVpcServices.size());

                // 4a. Pass 1: Xử lý instance/vm TRƯỚC (để có MAYAO_ID cho dịch vụ attach sau)
                Map<String, Number> vmMap = new HashMap<>();
                for (SMCloudVpcItemDTO item : nonVpcServices) {
                        String serviceCode = item.getServiceCode();
                        if (!"instance".equalsIgnoreCase(serviceCode) && !"vm".equalsIgnoreCase(serviceCode)) {
                                continue;
                        }

                        try {
                                // Gọi API chi tiết
                                SMCloudVPCDetailDTO.ServiceItemsDTO detail = fetchServiceDetail(item);

                                if (detail != null) {
                                        Number vmId = syncInstanceToMayao(null, detail);
                                        if (vmId != null && detail.getServiceName() != null) {
                                                vmMap.put(detail.getServiceName(), vmId);
                                        }
                                        addSuccess(successList, serviceCode, item.getName(), item.getId());
                                        log.info("[syncNonVpcServices] VM sync OK: {}/{} (smCloudId={}) -> MAYAO_ID={}",
                                                        serviceCode, item.getName(), item.getId(), vmId);
                                } else {
                                        // Fallback: nếu service-detail trả null, sync cơ bản
                                        syncNonVpcInstanceBasic(item, cumHtId);
                                        addSuccess(successList, serviceCode, item.getName(), item.getId());
                                        log.info("[syncNonVpcServices] VM sync (basic): {}/{}", serviceCode, item.getName());
                                }
                        } catch (Exception e) {
                                log.warn("[syncNonVpcServices] Lỗi sync VM {}/{}: {}",
                                                serviceCode, item.getName(), e.getMessage());
                                addFailure(failureList, item, e);
                        }
                }

                // 4b. Pass 2: Xử lý các dịch vụ khác → DICHVU_HT + NANGLUC_DVHT
                for (SMCloudVpcItemDTO item : nonVpcServices) {
                        String serviceCode = item.getServiceCode();
                        if ("instance".equalsIgnoreCase(serviceCode) || "vm".equalsIgnoreCase(serviceCode)) {
                                continue;
                        }

                        try {
                                // Gọi API chi tiết
                                SMCloudVPCDetailDTO.ServiceItemsDTO detail = fetchServiceDetail(item);

                                if (detail != null) {
                                        // Tìm AttachToVM nếu có
                                        Number targetVmId = findAttachedVmId(detail, vmMap);

                                        // Sync chi tiết (có details_json, create/expire date)
                                        syncNonVpcServiceDetail(item, detail, cumHtId, targetVmId);
                                } else {
                                        // Fallback: sync cơ bản không có chi tiết
                                        String loaidvhtMa = mapServiceCodeToMaSpdv(serviceCode);
                                        syncNonVpcServiceBasic(item, loaidvhtMa, cumHtId);
                                }

                                addSuccess(successList, serviceCode, item.getName(), item.getId());
                                log.info("[syncNonVpcServices] Đồng bộ thành công: {}/{} (smCloudId={})",
                                                serviceCode, item.getName(), item.getId());
                        } catch (Exception e) {
                                log.warn("[syncNonVpcServices] Lỗi sync dịch vụ {}/{}: {}",
                                                serviceCode, item.getName(), e.getMessage());
                                addFailure(failureList, item, e);
                        }
                }

                log.info("[syncNonVpcServices] Hoàn tất. Thành công: {}, Thất bại: {}",
                                successList.size(), failureList.size());

                Map<String, List<Map<String, Object>>> result = new HashMap<>();
                result.put("success", successList);
                result.put("failure", failureList);
                return result;
        }

        /**
         * Gọi API service-detail để lấy chi tiết 1 dịch vụ.
         * Trả null nếu API lỗi (cho phép fallback sync cơ bản).
         */
        private SMCloudVPCDetailDTO.ServiceItemsDTO fetchServiceDetail(SMCloudVpcItemDTO item) {
                try {
                        long id = item.getId() != null ? item.getId() : 0L;
                        int serviceType = item.getServiceType() != null ? item.getServiceType() : 0;
                        SMCloudVPCDetailDTO.ServiceItemsDTO detail = feignClientAPIService
                                        .smCloudServiceDetailExt(id, serviceType);
                        if (detail != null) {
                                log.info("[fetchServiceDetail] Lấy chi tiết OK: id={}, serviceType={}, serviceName={}",
                                                id, serviceType, detail.getServiceName());
                        }
                        return detail;
                } catch (Exception e) {
                        log.warn("[fetchServiceDetail] Lỗi gọi service-detail cho id={}, serviceType={}: {}",
                                        item.getId(), item.getServiceType(), e.getMessage());
                        return null;
                }
        }

        /**
         * Tìm MAYAO_ID của VM mà dịch vụ này attach vào (qua details[].unit = AttachToVM).
         */
        private Number findAttachedVmId(SMCloudVPCDetailDTO.ServiceItemsDTO detail, Map<String, Number> vmMap) {
                if (detail.getDetails() == null || vmMap.isEmpty()) return null;
                for (SMCloudVPCDetailDTO.ServiceItemsDTO.DetailsDTO d : detail.getDetails()) {
                        if ("AttachToVM".equalsIgnoreCase(d.getUnit()) && d.getName() != null) {
                                return vmMap.get(d.getName());
                        }
                }
                return null;
        }

        /**
         * Đồng bộ chi tiết 1 dịch vụ non-VPC (không phải instance/vm) vào DICHVU_HT + NANGLUC_DVHT.
         * Sử dụng SP_SYNC_NON_VPC_DICHVU_HT với đầy đủ thêm create_date, expire_date, details_json.
         */
        private void syncNonVpcServiceDetail(SMCloudVpcItemDTO item,
                        SMCloudVPCDetailDTO.ServiceItemsDTO detail,
                        Number cumHtId, Number targetVmId) throws AppSqlException {

                String loaidvhtMa = mapServiceCodeToMaSpdv(item.getServiceCode());
                String detailsJson = serializeToJson(detail.getDetails());
                // Dùng serviceName từ detail nếu có, fallback về name từ item
                String serviceName = detail.getServiceName() != null ? detail.getServiceName() : item.getName();

                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_sm_cloud_id", item.getId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_service_code", item.getServiceCode(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_service_name", serviceName, SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_loaidvht_ma", loaidvhtMa, SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_status", detail.getStatus() != null ? detail.getStatus() : item.getStatus(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_email", item.getEmail(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_cumht_id", cumHtId, SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_create_date", detail.getCreateDate(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_expire_date", detail.getExpiredDate(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_details_json", detailsJson, SqlParameter.ParameterDirection.INPUT,
                                Types.CLOB));
                params.add(new SqlParameter("p_nguoi_cn", getCurrentUserName(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

                Map<String, Object> res = dbContext.executeSpWithCursorToMap(
                                "ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_SYNC_NON_VPC_DICHVU_HT", params);
                Number dichvuhtId = res != null && res.get("item") != null ? (Number) res.get("item") : null;
                log.info("[syncNonVpcServiceDetail] {}/{} -> LOAI={}, DICHVUHT_ID={}",
                                item.getServiceCode(), serviceName, loaidvhtMa, dichvuhtId);
        }

        /**
         * Fallback: Đồng bộ cơ bản 1 dịch vụ non-VPC (khi service-detail API không khả dụng).
         * Truyền null cho create_date, expire_date, details_json.
         */
        private void syncNonVpcServiceBasic(SMCloudVpcItemDTO item, String loaidvhtMa, Number cumHtId)
                        throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_sm_cloud_id", item.getId(), SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_service_code", item.getServiceCode(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_service_name", item.getName(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_loaidvht_ma", loaidvhtMa, SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_status", item.getStatus(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_email", item.getEmail(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_cumht_id", cumHtId, SqlParameter.ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("p_create_date", null, SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_expire_date", null, SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_details_json", null, SqlParameter.ParameterDirection.INPUT,
                                Types.CLOB));
                params.add(new SqlParameter("p_nguoi_cn", getCurrentUserName(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

                Map<String, Object> res = dbContext.executeSpWithCursorToMap(
                                "ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_SYNC_NON_VPC_DICHVU_HT", params);
                Number dichvuhtId = res != null && res.get("item") != null ? (Number) res.get("item") : null;
                log.info("[syncNonVpcServiceBasic] {}/{} -> LOAI={}, DICHVUHT_ID={}",
                                item.getServiceCode(), item.getName(), loaidvhtMa, dichvuhtId);
        }

        /**
         * Fallback: Đồng bộ cơ bản 1 instance/vm non-VPC (khi service-detail API không khả dụng).
         * Gọi SP_SYNC_VPC_INSTANCE với p_vpc_id = null, CPU/RAM/SSD/HDD = 0.
         */
        private void syncNonVpcInstanceBasic(SMCloudVpcItemDTO item, Number cumHtId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_vpc_id", null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_ma_mayao", item.getName(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_cpu", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_ram", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_ssd", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_hdd", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("p_status", item.getStatus(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_create_date", null, SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_expire_date", null, SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_nguoi_cn", getCurrentUserName(), SqlParameter.ParameterDirection.INPUT,
                                Types.VARCHAR));
                params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

                Map<String, Object> res = dbContext.executeSpWithCursorToMap(
                                "ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_SYNC_VPC_INSTANCE", params);
                Number vmId = res != null && res.get("item") != null ? (Number) res.get("item") : null;
                log.info("[syncNonVpcInstanceBasic] VM: {} -> MAYAO_ID={}", item.getName(), vmId);
        }

        // ==================== Helper methods ====================

        private void addSuccess(List<Map<String, Object>> list, String serviceCode, String name, Long id) {
                Map<String, Object> item = new HashMap<>();
                item.put("serviceCode", serviceCode);
                item.put("name", name);
                item.put("id", id);
                list.add(item);
        }

        private void addFailure(List<Map<String, Object>> list, SMCloudVpcItemDTO item, Exception e) {
                Map<String, Object> failureItem = new HashMap<>();
                failureItem.put("serviceCode", item.getServiceCode());
                failureItem.put("name", item.getName());
                failureItem.put("id", item.getId());
                failureItem.put("error", e.getMessage());
                list.add(failureItem);
        }

        /**
         * Tìm CUMHT_ID theo regionCode qua danh mục IDC.
         * Gọi trực tiếp SP với p_phanvung_id=null (không cần HTTP request context).
         */
        private Number findCumHtIdByRegionCode(String regionCode) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_region_code", regionCode, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                List<Map<String, Object>> list = dbContext
                                .executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_FIND_CUMHT_BY_REGIONCODE", params);
                if (list != null && !list.isEmpty()) {
                        return (Number) list.get(0).get("CUMHT_ID");
                }
                return null;
        }
}
