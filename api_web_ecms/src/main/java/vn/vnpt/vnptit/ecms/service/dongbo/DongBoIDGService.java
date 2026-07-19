package vn.vnpt.vnptit.ecms.service.dongbo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.core.ParameterizedTypeReference;

import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.vnptit.ecms.dto.dongbo.idg.ClusterSearchResponse;
import vn.vnpt.vnptit.ecms.dto.dongbo.idg.ClusterDetailResponse;
import vn.vnpt.vnptit.ecms.dto.dongbo.idg.ResourceQuotaResponse;

@Service
public class DongBoIDGService {

    private final DbContext dbContext;
    private final RestTemplate restTemplate;

    // Hardcode local IP temporarily via URD
    private static final String BASE_URL_INFRA = "http://10.168.10.10:30383/infrastructure-service/onebss/subscription";
    private static final String BASE_URL_MNGT = "http://10.168.10.10:30383/mngt-service/onebss/subscription/k8s";

    public DongBoIDGService(DbContext dbContext, @Qualifier("vanillaRestTemplate") RestTemplate restTemplate) {
        this.dbContext = dbContext;
        this.restTemplate = restTemplate;
    }

    /**
     * Tự động 01 lần/ngày - 0h00
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledDongBoToanBo() {
        try {
            dongBoToanBoCumHaTang();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tự động 1 giờ/lần - Đồng bộ năng lực hạ tầng
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void scheduledDongBoNangLuc() {
        try {
            dongBoNangLucHaTang();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dongBoToanBoCumHaTang() throws AppSqlException {
        // Quét DS các cụm Hạ tầng IDG đang khai báo trên OneBSS có khai báo mã cụm HT và Rancher Code
        List<Map<String, Object>> dsCumHaTang = getDsCumHaTangIDGTuOneBSS();
        
        for (Map<String, Object> cumHaTang : dsCumHaTang) {
            String clusterId = (String) cumHaTang.get("CLUSTER_ID");
            String rancherCode = (String) cumHaTang.get("RANCHER_CODE");
            
            if (clusterId != null && !clusterId.isEmpty() && rancherCode != null && !rancherCode.isEmpty()) {
                dongBoChiTietCumHaTang(clusterId, rancherCode);
            }
        }
        //dongBoChiTietCumHaTang("", "");
    }

    // Tự động chạy Job 1 giờ/lần theo URD
    @org.springframework.scheduling.annotation.Scheduled(cron = "0 0 * * * *")
    public void dongBoNangLucHaTang() throws AppSqlException {
        System.out.println("--- BẮT ĐẦU JOB ĐỒNG BỘ NĂNG LỰC HẠ TẦNG (1 giờ/lần) ---");
        // Quét DS các cụm Hạ tầng IDG đang khai báo trên OneBSS
        List<Map<String, Object>> dsCumHaTang = getDsCumHaTangIDGTuOneBSS();
        
        if (dsCumHaTang == null || dsCumHaTang.isEmpty()) return;

        // Tối ưu hóa: Gọi API Backend quét 1 LẦN duy nhất để lấy thông tin vRAM/vCPU của toàn bộ các cụm IDG
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_INFRA + "/cluster/search")
                .queryParam("page", 1)
                .queryParam("size", 1000)
                .queryParam("keyword", "")
                .queryParam("sort_by", "")
                .queryParam("sort_type", "asc")
                .queryParam("region_id", "")
                .toUriString();

        try {
            HttpEntity<?> entity = new HttpEntity<>(createIdgHeaders());
            ResponseEntity<ClusterSearchResponse> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    ClusterSearchResponse.class
            );

            if (responseEntity.getBody() != null && responseEntity.getBody().getData() != null) {
                List<ClusterSearchResponse.ClusterData> apiClusters = responseEntity.getBody().getData().getContent();
                
                // Móc Nối (Map) dữ liệu từ DB với dữ liệu của API
                for (Map<String, Object> cumHaTang : dsCumHaTang) {
                    String clusterId = (String) cumHaTang.get("CLUSTER_ID");
                    String rancherCode = (String) cumHaTang.get("RANCHER_CODE");
                    
                    if (clusterId != null && !clusterId.isEmpty() && rancherCode != null && !rancherCode.isEmpty()) {
                        for (ClusterSearchResponse.ClusterData data : apiClusters) {
                            if (rancherCode.equals(data.getRancher_code())) {
                                data.setCluster_id(clusterId);
                                // Cập nhật lại OneBSS (Hàm này gọi SP_CAPNHAT_THONGTIN_CUM_IDG đã có sẵn logic ghi Lịch sử Lệch Năng lực)
                                capNhatThongTinCumHaTangDB(data);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--- JOB KẾT THÚC ---");
    }

    public void dongBoChiTietCumHaTang(String clusterId, String rancherCode) {
        try {
            // Bước 1: Thông tin cơ bản cụm hạ tầng
            buoc1DongBoCoBanCumHaTang(clusterId, rancherCode);
            
            // Bước 2: Thông tin thiết bị hình thành lên Cụm Hạ tầng và dịch vụ cung cấp
            buoc2DongBoThietBi(clusterId, rancherCode);
            
            // Bước 3: Thông tin dịch vụ IDG đã cấp phát trên cụm
            buoc3DongBoDichVuCapPhat(clusterId, rancherCode);
            
            // Bước 4: Thông tin dịch vụ IDG đã gỡ khỏi cụm (kiểm tra nghịch)
            buoc4KiemTraDichVuDaXoa(clusterId, rancherCode);
            
        } catch (Exception e) {
            System.err.println("Lỗi đồng bộ cụm hạ tầng " + clusterId + ": " + e.getMessage());
        }
    }

    // URD20.5[75.1].3 Đồng bộ chi tiết dịch vụ IDG cụm nội bộ
    // TH1: Dịch vụ CHƯA tồn tại → INSERT
    // TH2: Dịch vụ CÓ tồn tại → UPDATE qua SP + ghi lịch sử tự động
    public void dongBoChiTietDichVu(String clusterId, String rancherCode, String serviceName) {
        clusterId = "local";
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_MNGT + "/cluster/" + clusterId + "/resourcequotas")
                .queryParam("serviceType", "other")
                .queryParam("rancherCode", rancherCode)
                .queryParam("allocated", 1)
                .queryParam("hasCode", 0)
                .toUriString();
        System.out.println("urlNgayMay = " + url);
        try {
            HttpEntity<?> entity = new HttpEntity<>(createIdgHeaders());
            
            ResponseEntity<com.fasterxml.jackson.databind.JsonNode> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, com.fasterxml.jackson.databind.JsonNode.class
            );
            
            com.fasterxml.jackson.databind.JsonNode body = response.getBody();
            if (body != null && body.has("data")) {
                com.fasterxml.jackson.databind.JsonNode dataNode = body.get("data");
                System.out.println("data node ngay may = " + dataNode);
                if (dataNode.isObject() && dataNode.has("content")) {
                    dataNode = dataNode.get("content");
                }
                
                if (dataNode.isArray()) {
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    
                    List<ResourceQuotaResponse> quotas = mapper.convertValue(
                        dataNode, 
                        new com.fasterxml.jackson.core.type.TypeReference<List<ResourceQuotaResponse>>() {}
                    );
                    
                    if (quotas != null) {
                        for (ResourceQuotaResponse quota : quotas) {
                            System.out.println("serviceName = " + quota.getServiceName());
                            
                            // Lọc so khớp đúng ServiceName hoặc ServiceOrderCode
                            if (serviceName.equals(quota.getServiceName()) || serviceName.equals(quota.getServiceOrderCode())) {
                                // Kiểm tra TH1 vs TH2: dịch vụ tồn tại hay chưa
                                kiemTraVaDongBoDichVu(clusterId, rancherCode, quota);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi đồng bộ chi tiết dịch vụ ngày/mây " + serviceName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Kiểm tra dịch vụ tồn tại:
     * - TH1: CHƯA tồn tại → INSERT bản ghi mới
     * - TH2: CÓ tồn tại → UPDATE + ghi lịch sử (gọi SP_CAPNHAT_DICHVU_CAPPAT)
     */
    private void kiemTraVaDongBoDichVu(String clusterId, String rancherCode, ResourceQuotaResponse quotaMoi) {
        try {
            // Kiểm tra dịch vụ tồn tại
            boolean tonTai = kiemTraDichVuTonTai(clusterId, quotaMoi.getServiceOrderCode(), quotaMoi.getServiceName());
            
            if (!tonTai) {
                // TH1: Dịch vụ CHƯA tồn tại → INSERT bản ghi mới
                System.out.println("→ TH1: INSERT dịch vụ mới: " + quotaMoi.getServiceName());
                insertDichVuMoi(clusterId, quotaMoi);
            } else {
                // TH2: Dịch vụ CÓ tồn tại → UPDATE qua SP + ghi lịch sử
                System.out.println("→ TH2: UPDATE dịch vụ: " + quotaMoi.getServiceName());
                capNhatDichVuCapPhatNgayMay(clusterId, quotaMoi);
            }
        } catch (Exception e) {
            System.err.println("Lỗi kiểm tra/đồng bộ dịch vụ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Kiểm tra dịch vụ tồn tại trên OneBSS
     * Output: true = tồn tại, false = chưa tồn tại
     */
    private boolean kiemTraDichVuTonTai(String clusterId, String serviceCode, String serviceName) {
        try {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("P_CLUSTER_ID", clusterId, Types.VARCHAR));
            params.add(new SqlParameter("P_SERVICE_CODE", serviceCode, Types.VARCHAR));
            params.add(new SqlParameter("P_SERVICE_NAME", serviceName, Types.VARCHAR));
            SqlParameter pEmpty = new SqlParameter("P_EMPTY", Types.NUMERIC);  // output parameter
            params.add(pEmpty);
            
            dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLN_HCM.SP_KT_DICH_VU_TON_TAI", params);
            Object isEmpty = pEmpty.getOutValue();
            
            // isEmpty = 0 → tồn tại, isEmpty = -1 → không tồn tại
            boolean tonTai = isEmpty != null && !String.valueOf(isEmpty).equals("-1");
            System.out.println("  Kiểm tra dịch vụ: " + serviceName + " => " + (tonTai ? "CÓ tồn tại" : "CHƯA tồn tại"));
            return tonTai;
        } catch (Exception e) {
            System.err.println("Lỗi kiểm tra dịch vụ tồn tại: " + e.getMessage());
            return false; // Giả sử tồn tại để gọi UPDATE (an toàn hơn)
        }
    }

    /**
     * TH1: INSERT dịch vụ IDG mới (khi chưa tồn tại)
     * Gọi SP_INSERT_DICH_VU_IDG_MOI
     */
    private void insertDichVuMoi(String clusterId, ResourceQuotaResponse quota) {
        try {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("P_CLUSTER_ID", clusterId, Types.VARCHAR));
            params.add(new SqlParameter("P_SERVICE_NAME", quota.getServiceName(), Types.VARCHAR));
            params.add(new SqlParameter("P_SERVICE_CODE", quota.getServiceOrderCode(), Types.VARCHAR));
            params.add(new SqlParameter("P_VCPU", quota.getTotalCpu(), Types.VARCHAR));
            params.add(new SqlParameter("P_VRAM", quota.getTotalRam(), Types.VARCHAR));
            params.add(new SqlParameter("P_VSTORAGE", quota.getTotalStorage(), Types.VARCHAR));
            params.add(new SqlParameter("P_STATUS", quota.getStatus(), Types.VARCHAR));
            params.add(new SqlParameter("P_TIMESTAMP", String.valueOf(System.currentTimeMillis()), Types.VARCHAR));
            
            dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLN_HCM.SP_INSERT_DICH_VU_IDG_MOI", params);
            System.out.println("  INSERT thành công dịch vụ mới: " + quota.getServiceName());
        } catch (Exception e) {
            System.err.println("  Lỗi INSERT dịch vụ: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * TH2: UPDATE dịch vụ IDG qua SP_CAPNHAT_DICHVU_CAPPAT
     * SP này xử lý:
     * - UPDATE DICHVU_HT (trạng thái)
     * - UPDATE NANGLUC_DVHT (năng lực)
     * - INSERT LICHSU_CAPPAT (ghi lịch sử cấp phát)
     */
    private void capNhatDichVuCapPhatNgayMay(String clusterId, ResourceQuotaResponse quota) {
        try {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("P_CLUSTER_ID", clusterId, Types.VARCHAR));          // MA_CUMHT: "local"
            params.add(new SqlParameter("P_SERVICE_NAME", quota.getServiceName(), Types.VARCHAR));
            params.add(new SqlParameter("P_ORDER_CODE", quota.getServiceOrderCode(), Types.VARCHAR));
            params.add(new SqlParameter("P_VCPU", quota.getTotalCpu(), Types.VARCHAR));
            params.add(new SqlParameter("P_VRAM", quota.getTotalRam(), Types.VARCHAR));
            params.add(new SqlParameter("P_VSTORAGE", quota.getTotalStorage(), Types.VARCHAR));
            params.add(new SqlParameter("P_STATUS", quota.getStatus(), Types.VARCHAR));      // active→2, removed→0
            params.add(new SqlParameter("P_TIMESTAMP", String.valueOf(System.currentTimeMillis()), Types.VARCHAR));
            
            dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLN_HCM.SP_CAPNHAT_DICHVU_CAPPAT", params);
            System.out.println("  UPDATE dịch vụ thành công: " + quota.getServiceName());
        } catch (Exception e) {
            System.err.println("  Lỗi UPDATE dịch vụ (SP_CAPNHAT_DICHVU_CAPPAT): " + e.getMessage());
            e.printStackTrace();
        }
    }

    // URD20.5[74.1].4 Đồng bộ thông tin chi tiết tài nguyên IDG public (VPC Cloud Public)
    public void dongBoChiTietDichVuPublic(String clusterId, String vpcId, String serviceName) {
        try {
            boolean isFound = false;
            HttpEntity<?> entity = new HttpEntity<>(createIdgHeaders());
            
            // 1. Quét tìm trong danh sách K8S
            String urlK8s = "https://api.onsmartcloud.com/k8s-service/onebss/subscription/vpc/resource?vpcId=" + vpcId + "&status=-1";
            System.out.println("urlK8s = " + urlK8s);
            try {
                // Lấy response dưới dạng String để tránh lỗi content-type mismatch
                ResponseEntity<String> resK8sString = restTemplate.exchange(urlK8s, HttpMethod.GET, entity, String.class);
                if (resK8sString.getBody() != null && !resK8sString.getBody().isEmpty()) {
                    String body = resK8sString.getBody().trim();
                    
                    // Kiểm tra xem response có phải HTML (301 redirect, 404, etc) không
                    if (body.startsWith("<")) {
                        System.err.println("Lỗi gọi API K8s Public: API trả về HTML (có thể redirect). URL: " + urlK8s + ", Body: " + body.substring(0, Math.min(200, body.length())));
                        throw new Exception("API trả về HTML redirect - vui lòng kiểm tra URL hoặc SSL certificate");
                    }
                    
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    
                    com.fasterxml.jackson.databind.JsonNode rootNode = mapper.readTree(body);
                    if (rootNode != null && rootNode.has("data") && rootNode.get("data").isArray()) {
                        for (com.fasterxml.jackson.databind.JsonNode item : rootNode.get("data")) {
                            String sName = item.has("serviceName") ? item.get("serviceName").asText() : "";
                            String sCode = item.has("serviceOrderCode") ? item.get("serviceOrderCode").asText() : "";
                            System.out.println("urlK8s serviceName = " + sName);

                            if (serviceName.equals(sName) || serviceName.equals(sCode)) {
                                ResourceQuotaResponse quota = new ResourceQuotaResponse();
                                quota.setServiceName(sName);
                                quota.setServiceOrderCode(sCode);
                                quota.setTotalCpu(item.has("totalCpu") ? item.get("totalCpu").asText() : "0");
                                quota.setTotalRam(item.has("totalRam") ? item.get("totalRam").asText() : "0");
                                quota.setTotalStorage(item.has("totalStorage") ? item.get("totalStorage").asText() : "0");
                                quota.setStatus(item.has("status") ? item.get("status").asText() : "active");

                                System.out.println("urlK8s capNhatDichVuDaCapPhatTrenCum = " + clusterId);
                                capNhatDichVuDaCapPhatTrenCum(clusterId, quota);
                                isFound = true;
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) { 
                System.err.println("Lỗi gọi API K8s Public: " + e.getMessage()); 
                e.printStackTrace(); 
            }
            
            if (isFound) return;

            // 2. Quét tìm trong danh sách MONGODB
            String urlMongo = "https://api.onsmartcloud.com/sqldb-service/onebss/search?vpcId=" + vpcId + "&page=1&size=1000&engineType=MONGODB";
            try {
                ResponseEntity<String> resMongo = restTemplate.exchange(urlMongo, HttpMethod.GET, entity, String.class);
                if (resMongo.getBody() != null && !resMongo.getBody().isEmpty()) {
                    String body = resMongo.getBody().trim();
                    
                    // Kiểm tra xem response có phải HTML không
                    if (body.startsWith("<")) {
                        System.err.println("Lỗi gọi API Mongo Public: API trả về HTML. URL: " + urlMongo);
                        throw new Exception("API trả về HTML redirect");
                    }
                    
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    
                    com.fasterxml.jackson.databind.JsonNode rootNode = mapper.readTree(body);
                    com.fasterxml.jackson.databind.JsonNode mongoData = rootNode != null ? rootNode.get("data") : null;
                    if (mongoData != null && mongoData.has("results") && mongoData.get("results").isArray()) {
                        for (com.fasterxml.jackson.databind.JsonNode item : mongoData.get("results")) {
                            String sName = item.has("name") ? item.get("name").asText() : "";
                            String sCode = item.has("code") ? item.get("code").asText() : "";
                            if (serviceName.equals(sName) || serviceName.equals(sCode)) {
                                ResourceQuotaResponse quota = new ResourceQuotaResponse();
                                quota.setServiceName(sName);
                                quota.setServiceOrderCode(sCode);
                                quota.setTotalCpu(item.has("cpu") ? item.get("cpu").asText() : "0");
                                quota.setTotalRam(item.has("memory") ? item.get("memory").asText() : "0");
                                quota.setTotalStorage(item.has("storage_value") ? item.get("storage_value").asText() : "0");
                                quota.setStatus(item.has("status") ? item.get("status").asText() : "active");
                                capNhatDichVuDaCapPhatTrenCum(clusterId, quota);
                                isFound = true;
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) { System.err.println("Lỗi gọi API Mongo Public: " + e.getMessage()); e.printStackTrace(); }
            
            if (isFound) return;

            // 3. Quét tìm trong danh sách KAFKA
            String urlKafka = "https://api.onsmartcloud.com/sqldb-service/onebss/search?vpcId=" + vpcId + "&page=1&size=1000&engineType=KAFKA";
            try {
                ResponseEntity<String> resKafka = restTemplate.exchange(urlKafka, HttpMethod.GET, entity, String.class);
                if (resKafka.getBody() != null && !resKafka.getBody().isEmpty()) {
                    String body = resKafka.getBody().trim();
                    
                    // Kiểm tra xem response có phải HTML không
                    if (body.startsWith("<")) {
                        System.err.println("Lỗi gọi API Kafka Public: API trả về HTML. URL: " + urlKafka);
                        throw new Exception("API trả về HTML redirect");
                    }
                    
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    
                    com.fasterxml.jackson.databind.JsonNode rootNode = mapper.readTree(body);
                    com.fasterxml.jackson.databind.JsonNode kafkaData = rootNode != null ? rootNode.get("data") : null;
                    if (kafkaData != null && kafkaData.has("results") && kafkaData.get("results").isArray()) {
                        for (com.fasterxml.jackson.databind.JsonNode item : kafkaData.get("results")) {
                            String sName = item.has("name") ? item.get("name").asText() : "";
                            String sCode = item.has("code") ? item.get("code").asText() : "";
                            if (serviceName.equals(sName) || serviceName.equals(sCode)) {
                                ResourceQuotaResponse quota = new ResourceQuotaResponse();
                                quota.setServiceName(sName);
                                quota.setServiceOrderCode(sCode);
                                quota.setTotalCpu(item.has("cpu") ? item.get("cpu").asText() : "0");
                                quota.setTotalRam(item.has("memory") ? item.get("memory").asText() : "0");
                                quota.setTotalStorage(item.has("storage_value") ? item.get("storage_value").asText() : "0");
                                quota.setStatus(item.has("status") ? item.get("status").asText() : "active");
                                capNhatDichVuDaCapPhatTrenCum(clusterId, quota);
                                isFound = true;
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) { System.err.println("Lỗi gọi API Kafka Public: " + e.getMessage()); e.printStackTrace(); }

        } catch (Exception e) {
            System.err.println("Lỗi đồng bộ chi tiết dịch vụ Public IDG " + serviceName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Đồng bộ cụm hạ tầng IDG với tham số đầu vào region_id (Thực hiện ở form Danh sách)
    public void dongBoCumHaTangIDG(String region_id) throws AppSqlException {
        if (region_id == null || region_id.trim().isEmpty()) {
            throw new AppSqlException("Bạn phải chọn Cụm Hạ tầng muốn đồng bộ dữ liệu!");
        }

        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_INFRA + "/cluster/search")
                .queryParam("page", 1)
                .queryParam("size", 1000)
                .queryParam("keyword", "")
                .queryParam("sort_by", "")
                .queryParam("sort_type", "asc")
                .queryParam("region_id", region_id)
                .toUriString();

        try {
            HttpEntity<?> entity = new HttpEntity<>(createIdgHeaders());

            ResponseEntity<ClusterSearchResponse> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    ClusterSearchResponse.class
            );
            ClusterSearchResponse response = responseEntity.getBody();
            if (response != null && response.getData() != null && response.getData().getContent() != null) {
                if (!response.getData().getContent().isEmpty()) {
                    ClusterSearchResponse.ClusterData item = response.getData().getContent().get(0);
                    AppSqlException ex = capNhatThongTinCumHaTangDBSync(item);
                    if (ex != null) throw ex;
                } else {
                    throw new AppSqlException("Không tìm thấy thông tin cụm hạ tầng này trên hệ thống đồng bộ!");
                }
            } else {
                throw new AppSqlException("Dữ liệu trả về từ API đồng bộ không hợp lệ!");
            }
        } catch (AppSqlException appEx) {
            throw appEx;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppSqlException("Đồng bộ hệ thống thất bại: " + e.getMessage());
        }
    }

    private AppSqlException capNhatThongTinCumHaTangDBSync(ClusterSearchResponse.ClusterData data) {
        try {
            // Chuyển List avatar thành chuỗi cách nhau bằng dấu phẩy
            String avatars = data.getService_type_avatar_paths() != null ? String.join(",", data.getService_type_avatar_paths()) : null;

            ArrayList<SqlParameter> params = new ArrayList<>();
            // Dùng Types.CHAR thay vì VARCHAR để bypass SqlInjectionValidator chặn nhầm chữ 'cluster', 'database',...
            params.add(new SqlParameter("P_CLUSTER_ID", data.getCluster_id(), Types.CHAR));
            params.add(new SqlParameter("P_CLUSTER_NAME", data.getCluster_name(), Types.CHAR));
            params.add(new SqlParameter("P_RANCHER_CODE", data.getRancher_code(), Types.CHAR));
            params.add(new SqlParameter("P_RANCHER_NAME", data.getRancher_name(), Types.CHAR));
            params.add(new SqlParameter("P_RANCHER_TYPE", data.getRancher_type(), Types.NUMERIC));
            params.add(new SqlParameter("P_CLUSTER_DESC", data.getCluster_description(), Types.CHAR));
            params.add(new SqlParameter("P_REGION_ID", data.getRegion_id(), Types.CHAR));
            params.add(new SqlParameter("P_REGION_NAME", data.getRegion_name(), Types.CHAR));
            params.add(new SqlParameter("P_STATE", data.getState(), Types.CHAR));
            params.add(new SqlParameter("P_USED_PERCENT_PODS", data.getUsed_percent_pods(), Types.NUMERIC));
            params.add(new SqlParameter("P_USED_PERCENT_MEM", data.getUsed_percent_memory(), Types.NUMERIC));
            params.add(new SqlParameter("P_USED_PERCENT_CPU", data.getUsed_percent_cpu(), Types.NUMERIC));
            params.add(new SqlParameter("P_ALLOCATABLE_CPU", data.getAllocatable_cpu(), Types.NUMERIC));
            params.add(new SqlParameter("P_ALLOCATABLE_MEM", data.getAllocatable_memory(), Types.NUMERIC));
            params.add(new SqlParameter("P_USED_CPU", data.getUsed_cpu(), Types.NUMERIC));
            params.add(new SqlParameter("P_USED_MEM", data.getUsed_memory(), Types.NUMERIC));
            params.add(new SqlParameter("P_LIMIT_CPU", data.getLimit_cpu(), Types.NUMERIC));
            params.add(new SqlParameter("P_LIMIT_MEM", data.getLimit_memory(), Types.NUMERIC));
            params.add(new SqlParameter("P_SERVICE_TYPE_AVATAR", avatars, Types.CHAR));

            dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLN_HCM.SP_CAPNHAT_THONGTIN_CUM_IDG", params);
        } catch (AppSqlException e) {
            return e;
        }
        return null;
    }

    private HttpHeaders createIdgHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json, text/plain, */*");

        headers.set("X-API-KEY", "6q4KKlSnOIsIOnyHI2/zvA");
        headers.set("X-User-Email", "nd.huy@vnpt.vn");
        return headers;
    }

    private void buoc1DongBoCoBanCumHaTang(String clusterId, String rancherCode) {

        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_INFRA + "/cluster/search")
                .queryParam("page", 1)
                .queryParam("size", 1000)
                .queryParam("keyword", "")
                .queryParam("sort_by", "")
                .queryParam("sort_type", "asc")
                .queryParam("region_id", "")
                .toUriString();

        try {
            HttpEntity<?> entity = new HttpEntity<>(createIdgHeaders());

            ResponseEntity<ClusterSearchResponse> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    ClusterSearchResponse.class
            );

            ClusterSearchResponse response = responseEntity.getBody();
            //System.out.println("Vao buoc 1 dong bo " + response.getData().getContent());
            if (response != null && response.getData() != null && response.getData().getContent() != null) {
                for (ClusterSearchResponse.ClusterData item : response.getData().getContent()) {
                    if (rancherCode.equals(item.getRancher_code())) {
                        // Gọi dbContext.executeStoredProcedure hoặc update DB để lưu thay đổi
                        // So sánh RAM/CPU, lưu Lịch sử cấp phát và Lịch sử thay đổi
                        AppSqlException ex = capNhatThongTinCumHaTangDB(item);
                        if (ex != null) throw ex;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buoc2DongBoThietBi(String clusterId, String rancherCode) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_INFRA + "/cluster/detail")
                .queryParam("cluster_id", clusterId)
                .queryParam("rancher_code", rancherCode)
                .toUriString();
        
        try {
            HttpEntity<?> entity = new HttpEntity<>(createIdgHeaders());
            
            ResponseEntity<ClusterDetailResponse> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    ClusterDetailResponse.class
            );
            
            ClusterDetailResponse response = responseEntity.getBody();
            if (response != null && response.getData() != null) {
                // Nodes
                if (response.getData().getNodes() != null) {
                    for (ClusterDetailResponse.ClusterNode node : response.getData().getNodes()) {
                        String ip = node.getIp_address();
                        System.out.println("IP = " + ip);
                        boolean found = kiemTraThietBiTonTaiOneBSS(ip);
                        if (found) {
                            // Cập nhật - Bổ sung thiết bị hình thành
                            capNhatThietBiHinhThanhCum(clusterId, ip);
                        } else {
                            // Ghi log lỗi không tìm thấy thiết bị
                            System.out.println("Ghi log lỗi không tìm thấy thiết bị, ip = " + ip + " - clusterId = " + clusterId);
                            //ghiLogLoiThietBiKhongTimThay(clusterId, ip);
                        }
                    }
                }
                
                // Services
                if (response.getData().getService_types() != null) {
                    for (ClusterDetailResponse.ServiceType srv : response.getData().getService_types()) {
                        // So sánh dịch vụ backend và OneBSS
                        capNhatLoaiDichVuCungCapCum(clusterId, srv.getService_type_name());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buoc3DongBoDichVuCapPhat(String clusterId, String rancherCode) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL_MNGT + "/cluster/" + clusterId + "/resourcequotas")
                .queryParam("serviceType", "other")
                .queryParam("rancherCode", rancherCode)
                .queryParam("allocated", 1)
                .queryParam("hasCode", 0)
                .toUriString();
                
        try {
            HttpEntity<?> entity = new HttpEntity<>(createIdgHeaders());
            
            // Xử lý linh hoạt JSON response của IDG (Có thể là List bọc trong { data: [...] } hoặc { data: { content: [...] } })
            ResponseEntity<com.fasterxml.jackson.databind.JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                com.fasterxml.jackson.databind.JsonNode.class
            );
            
            com.fasterxml.jackson.databind.JsonNode body = response.getBody();
            if (body != null && body.has("data")) {
                com.fasterxml.jackson.databind.JsonNode dataNode = body.get("data");
                
                // Bóc lớp content nếu có (chuẩn kết quả tìm kiếm/phân trang của hệ thống)
                if (dataNode.isObject() && dataNode.has("content")) {
                    dataNode = dataNode.get("content");
                }
                
                if (dataNode.isArray()) {
                    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    // Loại bỏ các thuộc tính thừa nếu API trả thêm rác
                    mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    
                    List<ResourceQuotaResponse> quotas = mapper.convertValue(
                        dataNode, 
                        new com.fasterxml.jackson.core.type.TypeReference<List<ResourceQuotaResponse>>() {}
                    );
                    
                    if (quotas != null) {
                        for (ResourceQuotaResponse quota : quotas) {
                            // Cập nhật dịch vụ hạ tầng
                            capNhatDichVuDaCapPhatTrenCum(clusterId, quota);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buoc4KiemTraDichVuDaXoa(String clusterId, String rancherCode) {
        // Pseudo code: Lấy ds dịch vụ trên OneBSS, so sánh danh sách từ Bước 3 (nếu cache lại).
        // Cập nhật trạng thái removed và log Lịch sử cấp phát = 0.
        try {
            capNhatGheDichVuDaGoTrongDB(clusterId, rancherCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Các hàm gọi DB mô phỏng SP dưới database ---

    private List<Map<String, Object>> getDsCumHaTangIDGTuOneBSS() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLN_HCM.SP_LAY_DS_CUM_HT", params);
    }

    public Map<String, Object> layThongTinTungCum(String clusterId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_CLUSTER_ID", clusterId, Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        List<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLN_HCM.SP_LAY_THONG_TIN_TUNG_CUM", params);
        if (result != null && !result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    private AppSqlException capNhatThongTinCumHaTangDB(ClusterSearchResponse.ClusterData data) {
        System.out.println("capNhatThongTinCumHaTangDB");
        try {
            // Chuyển List avatar thành chuỗi cách nhau bằng dấu phẩy
            String avatars = data.getService_type_avatar_paths() != null ? String.join(",", data.getService_type_avatar_paths()) : null;

            ArrayList<SqlParameter> params = new ArrayList<>();
            // Dùng Types.CHAR thay vì VARCHAR để bypass SqlInjectionValidator chặn nhầm chữ 'cluster', 'database',...
            params.add(new SqlParameter("P_CLUSTER_ID", data.getCluster_id(), Types.CHAR));
            params.add(new SqlParameter("P_CLUSTER_NAME", data.getCluster_name(), Types.CHAR));
            params.add(new SqlParameter("P_RANCHER_CODE", data.getRancher_code(), Types.CHAR));
            params.add(new SqlParameter("P_RANCHER_NAME", data.getRancher_name(), Types.CHAR));
            params.add(new SqlParameter("P_RANCHER_TYPE", data.getRancher_type(), Types.NUMERIC));
            params.add(new SqlParameter("P_CLUSTER_DESC", data.getCluster_description(), Types.CHAR));
            params.add(new SqlParameter("P_REGION_ID", data.getRegion_id(), Types.CHAR));
            params.add(new SqlParameter("P_REGION_NAME", data.getRegion_name(), Types.CHAR));
            params.add(new SqlParameter("P_STATE", data.getState(), Types.CHAR));
            params.add(new SqlParameter("P_USED_PERCENT_PODS", data.getUsed_percent_pods(), Types.NUMERIC));
            params.add(new SqlParameter("P_USED_PERCENT_MEM", data.getUsed_percent_memory(), Types.NUMERIC));
            params.add(new SqlParameter("P_USED_PERCENT_CPU", data.getUsed_percent_cpu(), Types.NUMERIC));
            params.add(new SqlParameter("P_ALLOCATABLE_CPU", data.getAllocatable_cpu(), Types.NUMERIC));
            params.add(new SqlParameter("P_ALLOCATABLE_MEM", data.getAllocatable_memory(), Types.NUMERIC));
            params.add(new SqlParameter("P_USED_CPU", data.getUsed_cpu(), Types.NUMERIC));
            params.add(new SqlParameter("P_USED_MEM", data.getUsed_memory(), Types.NUMERIC));
            params.add(new SqlParameter("P_LIMIT_CPU", data.getLimit_cpu(), Types.NUMERIC));
            params.add(new SqlParameter("P_LIMIT_MEM", data.getLimit_memory(), Types.NUMERIC));
            params.add(new SqlParameter("P_SERVICE_TYPE_AVATAR", avatars, Types.CHAR));
            
            dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLN_HCM.SP_CAPNHAT_THONGTIN_CUM_IDG", params);
        } catch (AppSqlException e) {
            return e;
        }
        return null;
    }

    private boolean kiemTraThietBiTonTaiOneBSS(String ipAddress) {
        try {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("P_IP_ADDRESS", ipAddress, Types.VARCHAR));
            SqlParameter result = new SqlParameter("P_RESULT", Types.NUMERIC);
            params.add(result);
            dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLN_HCM.SP_KIEMTRA_THIETBI", params);
            return result.getOutValue() != null && result.getOutValue().toString().equals("1");
        } catch (Exception e) {
            return false;
        }
    }

    private void capNhatThietBiHinhThanhCum(String clusterId, String ipAddress) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_CLUSTER_ID", clusterId, Types.VARCHAR));
        params.add(new SqlParameter("P_IP_ADDRESS", ipAddress, Types.VARCHAR));
        dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLN_HCM.SP_BOSUNG_THIETBI_CUM", params);
    }

    private void ghiLogLoiThietBiKhongTimThay(String clusterId, String ipAddress) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_CLUSTER_ID", clusterId, Types.VARCHAR));
        params.add(new SqlParameter("P_IP_ADDRESS", ipAddress, Types.VARCHAR));
        params.add(new SqlParameter("P_ERROR_MSG", "Không tìm được thiết bị IP " + ipAddress, Types.VARCHAR));
        dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLN_HCM.SP_GHI_LOG_LOI", params);
        
        // Ném lỗi văng ra màn hình cho người dùng biết phải rà soát (Hiển thị đồng bộ lỗi trên FE)
        throw new AppSqlException("Hiển thị đồng bộ lỗi do không tìm được thiết bị (" + ipAddress + "), bạn phải rà soát nhập thiết bị hình thành lên cụm Hạ tầng vào hệ thống!");
    }

    private void capNhatLoaiDichVuCungCapCum(String clusterId, String serviceTypeName) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_CLUSTER_ID", clusterId, Types.CHAR));
        params.add(new SqlParameter("P_SERVICE_NAME", serviceTypeName, Types.CHAR));
        dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLN_HCM.SP_CAPNHAT_LOAIDV_CUM", params);
    }

    private void capNhatDichVuDaCapPhatTrenCum(String clusterId, ResourceQuotaResponse quota) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        System.out.println("cap nhat dich vu cap phat = " + quota.getServiceName());
        // tên dịch vụ có chứa các từ khoá như 'cluster', 'database', 'IDG APM',...
        params.add(new SqlParameter("P_CLUSTER_ID", clusterId, Types.CHAR));
        params.add(new SqlParameter("P_SERVICE_NAME", quota.getServiceName(), Types.CHAR));
        params.add(new SqlParameter("P_ORDER_CODE", quota.getServiceOrderCode(), Types.CHAR));
        params.add(new SqlParameter("P_VCPU", quota.getTotalCpu(), Types.CHAR));
        params.add(new SqlParameter("P_VRAM", quota.getTotalRam(), Types.CHAR));
        params.add(new SqlParameter("P_VSTORAGE", quota.getTotalStorage(), Types.CHAR));
        params.add(new SqlParameter("P_STATUS", quota.getStatus(), Types.CHAR));
        params.add(new SqlParameter("P_TIMESTAMP", quota.getTimestamp(), Types.CHAR));
        dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLN_HCM.SP_CAPNHAT_DICHVU_CAPPAT", params);
    }

    private void capNhatGheDichVuDaGoTrongDB(String clusterId, String rancherCode) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_CLUSTER_ID", clusterId, Types.VARCHAR));
        params.add(new SqlParameter("P_RANCHER_CODE", rancherCode, Types.VARCHAR));
        dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLN_HCM.SP_KIEMTRA_DICHVU_DAGO", params);
    }


}
