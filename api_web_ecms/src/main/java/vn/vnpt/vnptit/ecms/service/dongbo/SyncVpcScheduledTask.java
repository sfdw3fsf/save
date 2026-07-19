package vn.vnpt.vnptit.ecms.service.dongbo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.service.idc.VpcService;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SyncVpcScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(SyncVpcScheduledTask.class);

    private final VpcService vpcService;
    private final DbContext dbContext;

    @Autowired
    public SyncVpcScheduledTask(VpcService vpcService, DbContext dbContext) {
        this.vpcService = vpcService;
        this.dbContext = dbContext;
    }

    /**
     * Cronjob chạy mỗi 15 phút một lần để đồng bộ danh sách VPC từ SmartCloud
     */
    @Scheduled(cron = "0 0/15 * * * ?")
    public void syncVpcPeriodicJob() {
        log.info("Bắt đầu cronjob đồng bộ VPC...");
        try {
            List<Map<String, Object>> dsCum = getDanhMucSmartCloud();
            log.debug("Danh sách Cụm SmartCloud: {}", dsCum);

            if (dsCum == null || dsCum.isEmpty()) {
                log.warn("Không tìm thấy danh sách Cụm SmartCloud nào để đồng bộ.");
                return;
            }

            int count = 0;
            for (Map<String, Object> map : dsCum) {
                if (map.get("TEN") != null) {
                    String regionCode = map.get("TEN").toString();
                    log.info("Đang xử lý đồng bộ VPC cho regionCode={}", regionCode);
                    try {
                        vpcService.syncAndSaveVpcList(regionCode);
                        count++;
                    } catch (AppSqlException e) {
                        log.error("Lỗi đồng bộ tại regionCode={}: {}", regionCode, e.getAppMessage());
                        logSyncVpcFailureToDb(regionCode, buildErrorDetail(e));
                    } catch (Exception e) {
                        log.error("Lỗi ngoài ý muốn tại regionCode={}: {}", regionCode, e.getMessage(), e);
                        logSyncVpcFailureToDb(regionCode, buildErrorDetail(e));
                    }
                }
            }
            log.info("Hoàn tất cronjob đồng bộ VPC. Số cluster được đồng bộ: {}", count);
        } catch (Exception e) {
            log.error("Lỗi tổng thể không lường trước khi chạy cronjob đồng bộ VPC", e);
            logSyncVpcFailureToDb(null, buildErrorDetail(e));
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

    private void logSyncVpcFailureToDb(String regionCode, String errorMessage) {
        try {
            Number cumHtId = findCumHtIdByRegionCode(regionCode);
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_cumht_id", cumHtId != null ? cumHtId : 0,
                    SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_vpc_id", null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ma_vpc", regionCode, SqlParameter.ParameterDirection.INPUT, Types.CHAR));
            params.add(new SqlParameter("p_sl_corecpu", null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_dl_ram", null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_dl_hdd", null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_dl_ssd", null, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nguoi_cn", "SYNC_VPC_CRONJOB", SqlParameter.ParameterDirection.INPUT,
                    Types.CHAR));
            params.add(new SqlParameter("p_hanh_dong", "SYNC_LIST", SqlParameter.ParameterDirection.INPUT, Types.CHAR));
            params.add(new SqlParameter("p_ket_qua", "FAIL", SqlParameter.ParameterDirection.INPUT, Types.CHAR));
            params.add(new SqlParameter("p_thong_diep", truncate(errorMessage, 190), SqlParameter.ParameterDirection.INPUT, Types.CHAR));
            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_AOHOA.PR_LOG_LICHSU_SYNC_VPC", params);
        } catch (Exception dbLogEx) {
            log.error("Không ghi được log lỗi VPC vào DB cho regionCode={}: {}", regionCode, dbLogEx.getMessage(), dbLogEx);
        }
    }

    private String buildErrorDetail(Throwable throwable) {
        if (throwable == null) {
            return "Unknown error";
        }
        String message = throwable.getMessage() != null ? throwable.getMessage() : "No message";
        StringBuilder detail = new StringBuilder(256)
                .append(throwable.getClass().getSimpleName())
                .append(": ")
                .append(message.replace('\n', ' ').replace('\r', ' '));
        StackTraceElement[] stack = throwable.getStackTrace();
        if (stack != null && stack.length > 0) {
            detail.append(" @ ").append(stack[0].getClassName()).append(":").append(stack[0].getLineNumber());
        }
        return detail.toString();
    }

    private String truncate(String value, int maxLength) {
        if (value == null) {
            return null;
        }
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength);
    }

    private Number findCumHtIdByRegionCode(String regionCode) throws AppSqlException {
        if (regionCode == null || regionCode.trim().isEmpty()) {
            return null;
        }
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_region_code", regionCode, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        List<Map<String, Object>> list = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_FIND_CUMHT_BY_REGIONCODE", params);
        if (list != null && !list.isEmpty()) {
            return (Number) list.get(0).get("CUMHT_ID");
        }
        return null;
    }
}
