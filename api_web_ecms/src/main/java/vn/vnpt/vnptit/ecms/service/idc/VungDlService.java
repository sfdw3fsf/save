package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.VungDlDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service("idcVungDlService")
public class VungDlService {
    private final DbContext dbContext;
    private final VCenterService vCenterService;

    public VungDlService(DbContext dbContext, VCenterService vCenterService) {
        this.dbContext = dbContext;
        this.vCenterService = vCenterService;
    }

    public ArrayList<Map<String, Object>> getDetailVungDl(Number vungdlId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_vungdl_id", vungdlId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_pks", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_DETAIL_VUNG_DL", params);
    }

    public ArrayList<Map<String, Object>> getDsVungDlAoHoaByTuDia(Number vungdlTudId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_vungdl_tud_id", vungdlTudId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return toLowerCaseKeys(dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_DS_VUNGDL_AOHOA_BY_TUDIA", params));
    }

    public Map<String, Object> upsertVungDl(VungDlDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = getVungDlSqlParameters(dto);
        params.add(new SqlParameter("p_vungdl_id", dto.getVungdlId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_UPSERT_VUNG_DL", params);
    }

    public Map<String, Object> deleteVungDl(Number vungdlId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_vungdl_id", vungdlId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_DELETE_VUNG_DL", params);
    }

    /**
     * Đồng bộ dữ liệu vùng dữ liệu (Datastore) từ VCenter Gateway.
     * 
     * @param datastoreCode mã datastore trên vCenter (ví dụ: datastore-1009)
     * @return Map chứa thông tin VungDlDto được mapping từ gateway
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> syncVungDlFromGateway(String datastoreCode) {
        Object rawData = vCenterService.getDatastoreDetail(datastoreCode);
        if (!(rawData instanceof Map)) {
            throw new RuntimeException("Dữ liệu trả về từ gateway không hợp lệ");
        }
        Map<String, Object> data = (Map<String, Object>) rawData;

        Map<String, Object> result = new HashMap<>();
        result.put("maVungdl", data.get("code"));
        result.put("tenVungdl", data.get("name"));

        // Giá trị từ gateway đã ở đơn vị GB, không cần chuyển đổi
        Object capacity = data.get("capacity");
        result.put("dlThucte", ((Number) capacity).doubleValue());

        Object freeSpace = data.get("freeSpace");
        result.put("dlTrong", ((Number) freeSpace).doubleValue());

        // Các trường khác set null hoặc mặc định
        result.put("vungdlId", null);
        result.put("loaiVungdl", null);
        result.put("loaihtId", null);
        result.put("loaiocungId", null);
        result.put("hatanglttdId", null);
        result.put("thietbiId", null);
        result.put("vungdlTudId", null);
        result.put("trangthaiccdvId", null);
        result.put("sudung", 1); // Mặc định sử dụng

        return result;
    }

    /**
     * Đồng bộ và lưu trực tiếp vùng dữ liệu (Datastore) từ VCenter vào DB.
     * Gọi SP_SYNC_VUNGDL_FROM_VCENTER để upsert trong 1 lần gọi DB duy nhất.
     *
     * @param datastoreCode mã datastore trên vCenter (ví dụ: datastore-1009)
     * @param vungDlId      ID vùng dữ liệu (có thể null nếu tạo mới)
     * @return VUNGDL_ID sau khi đồng bộ
     */
    public Number syncAndSaveSingleVungDl(String datastoreCode, Number vungDlId) throws AppSqlException {
        // === Bước 1: Lấy dữ liệu từ VCenter Gateway ===
        Map<String, Object> syncData = syncVungDlFromGateway(datastoreCode);
        if (syncData == null) {
            throw new RuntimeException("Không tìm thấy thông tin đồng bộ từ VCenter cho mã: " + datastoreCode);
        }

        String maVungdl = syncData.get("maVungdl") != null ? syncData.get("maVungdl").toString() : null;
        String tenVungdl = syncData.get("tenVungdl") != null ? syncData.get("tenVungdl").toString() : null;
        Number dlThucte = syncData.get("dlThucte") instanceof Number ? (Number) syncData.get("dlThucte") : null;
        Number dlTrong = syncData.get("dlTrong") instanceof Number ? (Number) syncData.get("dlTrong") : null;

        // === Bước 2: Gọi SP đồng bộ ===
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_vungdl", maVungdl, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_vungdl", tenVungdl, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dl_thucte", dlThucte, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_trong", dlTrong, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> resMap;
        try {
            resMap = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_SYNC_VUNGDL_FROM_VCENTER", params);
        } catch (AppSqlException e) {
            System.out.println("[syncSaveVungDl] ❌ Lỗi DB khi chạy SP_SYNC: " + e.getMessage());
            throw e;
        }

        // === Bước 3: Trích xuất kết quả ===
        Number resultId = null;
        if (resMap != null) {
            for (Map.Entry<String, Object> entry : resMap.entrySet()) {
                if (entry.getValue() instanceof Number) {
                    resultId = (Number) entry.getValue();
                    break;
                }
            }
        }

        if (resultId == null || resultId.longValue() <= 0) {
            System.out.println("[syncSaveVungDl] ❌ SP trả về null hoặc <=0. resMap=" + resMap);
            throw new RuntimeException("Lỗi đồng bộ vùng dữ liệu, SP trả về: " + resMap);
        }
        return resultId;
    }

    private ArrayList<SqlParameter> getVungDlSqlParameters(VungDlDto dto) {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_vungdl", dto.getMaVungdl(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_ten_vungdl", dto.getTenVungdl(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_loai_vungdl", dto.getLoaiVungdl(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_loaiht_id", dto.getLoaihtId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_loaiocung_id", dto.getLoaiocungId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_hatanglttd_id", dto.getHatanglttdId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", dto.getThietbiId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_vungdl_tud_id", dto.getVungdlTudId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_bs", dto.getNgayBs(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_nhanvien_bs_id", dto.getNhanvienBsId(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_trangthaiccdv_id", dto.getTrangthaiccdvId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dl_thucte", dto.getDlThucte(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_dl_trong", dto.getDlTrong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hs_cp", dto.getHsCp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_cothe_cp", dto.getDlCotheCp(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_dl_da_cp", dto.getDlDaCp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_khanang_cp", dto.getDlKhanangCp(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_sudung", dto.getSudung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mota", dto.getMota(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        return params;
    }

    /**
     * Lấy danh sách máy ảo được cấp phát trên một vùng dữ liệu.
     */
    public ArrayList<Map<String, Object>> getDsMayAoByVungId(Number vungdlId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_vungdl_id", vungdlId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return toLowerCaseKeys(
                dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_MAYAO_BY_VUNGDL", params));
    }

    /**
     * Lấy danh sách máy ảo được cấp phát trên một vùng dữ liệu.
     */
    public ArrayList<Map<String, Object>> getDsFileStorageByVungId(Number vungdlId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_vungdl_id", vungdlId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return toLowerCaseKeys(
                dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_FILE_STORAGE_BY_VUNGDL",
                        params));
    }

    /**
     * Lấy danh sách thiết bị được cấp phát trên một vùng dữ liệu.
     */
    public ArrayList<Map<String, Object>> getDsThietBiByVungId(Number vungdlId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_vungdl_id", vungdlId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return toLowerCaseKeys(
                dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_THIETBI_BY_VUNGDL",
                        params));
    }

    private ArrayList<Map<String, Object>> toLowerCaseKeys(ArrayList<Map<String, Object>> list) {
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Map<String, Object> newMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                newMap.put(entry.getKey().toLowerCase(), entry.getValue());
            }
            result.add(newMap);
        }
        return result;
    }

    public ArrayList<Map<String, Object>> getDanhSachHaTangAoHoaTheoVungDuLieu(Number loaiVungDl, Number vungDLId)
            throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_vungdl", loaiVungDl, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vung_dl_id", vungDLId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANG_MAYAO_HAILN.SP_LAYDSCUMHATANG_THEOVUNGDULIEU", params);
    }
}
