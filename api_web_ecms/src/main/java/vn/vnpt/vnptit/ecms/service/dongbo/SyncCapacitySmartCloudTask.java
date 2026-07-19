package vn.vnpt.vnptit.ecms.service.dongbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.service.hatang.CumHaTangAoHoaService;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SyncCapacitySmartCloudTask {

    private static final Logger log = LoggerFactory.getLogger(SyncCapacitySmartCloudTask.class);

    private final CumHaTangAoHoaService cumHaTangAoHoaService;
    private final DbContext dbContext;

    @Autowired
    public SyncCapacitySmartCloudTask(CumHaTangAoHoaService cumHaTangAoHoaService, DbContext dbContext) {
        this.cumHaTangAoHoaService = cumHaTangAoHoaService;
        this.dbContext = dbContext;
    }

    /**
     * Đồng bộ Năng lực Cụm Hạ tầng SmartCloud định kỳ (mỗi 15 phút)
     */
    @Scheduled(cron = "0 */15 * * * ?")
    public void syncCapacityPeriodicJob() {
        log.info("Bắt đầu cronjob đồng bộ năng lực Cụm Hạ tầng SmartCloud...");
        try {
            List<Map<String, Object>> dsCum = getDanhMucSmartCloud();
            log.debug("Danh sách Cụm SmartCloud: {}", dsCum);
            
            if (dsCum == null || dsCum.isEmpty()) {
                log.warn("Không tìm thấy mã region Cụm SmartCloud nào để đồng bộ.");
                return;
            }

            int count = 0;
            for (Map<String, Object> map : dsCum) {
                if (map.get("TEN") != null && map.get("CUMHT_ID") != null) {
                    String regionCode = map.get("TEN").toString();
                    Long cumHtId = ((Number) map.get("CUMHT_ID")).longValue();
                    
                    log.info("Đang xử lý đồng bộ năng lực Cụm SmartCloud cho regionCode={}, CUMHT_ID={}", regionCode, cumHtId);
                    
                    try {
                        Map<String, List<Map<String, Object>>> result = cumHaTangAoHoaService.syncNangLucCumSmartCloud(regionCode);
                        
                        log.info("Kết quả đồng bộ regionCode={}: Thành công {} nodes, thất bại {} nodes.", 
                            regionCode, 
                            result.get("success") != null ? result.get("success").size() : 0,
                            result.get("failure") != null ? result.get("failure").size() : 0);
                            
                        count++;
                    } catch (Exception e) {
                        log.error("Lỗi ngoài ý muốn tại regionCode={}: {}", regionCode, e.getMessage(), e);
                    }
                }
            }
            log.info("Hoàn tất cronjob đồng bộ năng lực Cụm SmartCloud. Số cluster được đồng bộ: {}", count);
        } catch (Exception e) {
            log.error("Lỗi tổng thể không lường trước khi chạy cronjob đồng bộ Năng lực", e);
        }
    }

    private List<Map<String, Object>> getDanhMucSmartCloud() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_danhmuc", "IDC-MACUMHT-SMARTCLOUD", SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_tham_so1", null, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tham_so2", null, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DANHMUC.SP_DANHMUC_DANHSACH", params);
    }
}
