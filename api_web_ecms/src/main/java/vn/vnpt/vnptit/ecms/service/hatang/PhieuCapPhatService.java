package vn.vnpt.vnptit.ecms.service.hatang;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.hatang.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhieuCapPhatService {

    private final DbContext dbContext;

    public PhieuCapPhatService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    /**
     * Gọi SP_PHIEU_DANHSACH trong PKG_PHIEU_CAPPHAT
     */
    public List<Map<String,Object>> spPhieuDanhSach(Long trangThai, Long donviId, Long spdvId) throws AppSqlException {
        // Tạo list param
        ArrayList<SqlParameter> params = new ArrayList<>();

        // p_trang_thai IN  NUMBER DEFAULT -1
        params.add(new SqlParameter("p_trang_thai", 
            (trangThai == null) ? -1 : trangThai,  // Nếu null => -1
            ParameterDirection.INPUT, 
            Types.NUMERIC)
        );
        // p_donvi_id IN  NUMBER DEFAULT -1
        params.add(new SqlParameter("p_donvi_id", 
            (donviId == null) ? -1 : donviId, 
            ParameterDirection.INPUT, 
            Types.NUMERIC)
        );
        // p_spdv_id IN  NUMBER DEFAULT -1
        params.add(new SqlParameter("p_spdv_id", 
            (spdvId == null) ? -1 : spdvId, 
            ParameterDirection.INPUT, 
            Types.NUMERIC)
        );
        // p_cur OUT SYS_REFCURSOR
        params.add(new SqlParameter("p_cur", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        // Gọi stored procedure
        // Tên: PKG_PHIEU_CAPPHAT.SP_PHIEU_DANHSACH
        // => format: {call PKG_PHIEU_CAPPHAT.SP_PHIEU_DANHSACH(?,?,?,?)}
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PHIEU_CAPPHAT.SP_PHIEU_DANHSACH", params);
    }

    /**
     * Gọi SP_PHIEU_TAOMOI => Tạo phiếu, trả về ID phiếu mới + result
     */
    public Map<String, Object> spPhieuTaoMoi(Long spdvId, String tenSpdvTuNhap, Long hatangmangId, 
                                            String mucDichSudung, String ghiChu) throws AppSqlException 
    {
        ArrayList<SqlParameter> params = new ArrayList<>();
        // p_spdv_id, p_ten_spdv_tu_nhap, p_hatangmang_id, p_mucdich_sudung, p_ghi_chu
        params.add(new SqlParameter("p_spdv_id", spdvId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten_spdv_tu_nhap", tenSpdvTuNhap, ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hatangmang_id", hatangmangId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mucdich_sudung", mucDichSudung, ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_ghi_chu", ghiChu, ParameterDirection.INPUT, Types.NVARCHAR));

        // p_new_id OUT NUMBER
        params.add(new SqlParameter("p_new_id", ParameterDirection.OUTPUT, Types.NUMERIC));
        // p_result OUT PLS_INTEGER
        params.add(new SqlParameter("p_result", ParameterDirection.OUTPUT, Types.NUMERIC));

        // Gọi SP
        // Sau khi gọi xong, dbContext sẽ trả ra "list" 
        // Các param OUTPUT ta lấy qua getOutValue
        dbContext.executeNonQuery("ECMS.PKG_PHIEU_CAPPHAT.SP_PHIEU_TAOMOI", params);

        // Lấy output
        Long newId = null;
        Long result = null;
        for(SqlParameter p : params) {
            if("p_new_id".equalsIgnoreCase(p.getName())) {
                newId = (p.getOutValue() != null) ? ((Number)p.getOutValue()).longValue() : null;
            } else if("p_result".equalsIgnoreCase(p.getName())) {
                result = (p.getOutValue() != null) ? ((Number)p.getOutValue()).longValue() : null;
            }
        }
        // Trả ra 1 map (hoặc object tuỳ ý)
         Map<String, Object> output = new HashMap<>();
         output.put("new_id", newId);
         output.put("result", result);
        return output;
    }

    /**
     * Gọi SP_PHIEU_CHITIET => Lấy chi tiết phiếu
     * SP này trả ra 2 CURSOR: 1 cho header, 1 cho detail
     * => ta có thể tách 2 call, hoặc fetch 2 cursor => code phức tạp
     * 
     * Ở đây, demo approach: package cũ return 2 cursor => 
     *   ta phải modify DB code => or ta tách 2 call
     *   (TUỲ logic). 
     * 
     * Tạm minh hoạ: ta "chia" ra 2 procedure con => 
     *   - SP_PHIEU_HEADER => 1 out cursor 
     *   - SP_PHIEU_CHITIET => 1 out cursor
     */
    public List<Map<String,Object>> spPhieuHeader(Long phieuId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieu_id", phieuId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_out", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PHIEU_CAPPHAT.SP_PHIEU_HEADER_ONLY", params);
        // => Bạn tuỳ biến store procedure, 
        //    hoặc tách logic như code cũ => 
        //    hiển thị 2 refcursor => code phức tạp.
    }

    public List<Map<String,Object>> spPhieuChiTiet(Long phieuId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieu_id", phieuId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur_ct", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PHIEU_CAPPHAT.SP_PHIEU_CHITIET_ONLY", params);
    }

    /**
     * Gọi SP_PHIEU_CAPNHAT => update phiếu
     */
    public Long spPhieuCapNhat(Long phieuId, Long spdvId, String tenSpdvTuNhap, 
                               Long hatangmangId, String mucDichSudung, String ghiChu) 
            throws AppSqlException 
    {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieu_id", phieuId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_spdv_id", spdvId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten_spdv_tu_nhap", tenSpdvTuNhap, ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hatangmang_id", hatangmangId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mucdich_sudung", mucDichSudung, ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_ghi_chu", ghiChu, ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_result", ParameterDirection.OUTPUT, Types.NUMERIC));

        dbContext.executeNonQuery("ECMS.PKG_PHIEU_CAPPHAT.SP_PHIEU_CAPNHAT", params);

        // Lấy result
        Long result = 0L;
        for(SqlParameter p : params) {
            if("p_result".equalsIgnoreCase(p.getName())) {
                result = (p.getOutValue() != null) ? ((Number)p.getOutValue()).longValue() : null;
            }
        }
        return result;
    }

    /**
     * Gọi SP_PHIEU_GUIYEUCAU => Chuyển trạng thái phiếu sang 1
     */
    public Long spPhieuGuiYeuCau(Long phieuId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieu_id", phieuId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", ParameterDirection.OUTPUT, Types.NUMERIC));

        dbContext.executeNonQuery("ECMS.PKG_PHIEU_CAPPHAT.SP_PHIEU_GUIYEUCAU", params);

        Long result = 0L;
        for(SqlParameter p : params) {
            if("p_result".equalsIgnoreCase(p.getName())) {
                result = (p.getOutValue() != null) ? ((Number)p.getOutValue()).longValue() : null;
            }
        }
        return result;
    }

    /**
     * Gọi SP_PHIEU_HOANTHANH => Chuyển phiếu sang trạng thái 2, set ngay_hoan_thanh
     */
    public Long spPhieuHoanThanh(Long phieuId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieu_id", phieuId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", ParameterDirection.OUTPUT, Types.NUMERIC));
        dbContext.executeNonQuery("ECMS.PKG_PHIEU_CAPPHAT.SP_PHIEU_HOANTHANH", params);

        Long result = 0L;
        for(SqlParameter p : params) {
            if("p_result".equalsIgnoreCase(p.getName())) {
                result = (p.getOutValue() != null) ? ((Number)p.getOutValue()).longValue() : null;
            }
        }
        return result;
    }

    /**
     * Gọi SP_PHIEU_DANHSACH_CAPPHAT => Lấy danh sách tài nguyên đã cấp
     */
    public List<Map<String, Object>> spPhieuDsCapPhat(Long phieuId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieu_id", phieuId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_PHIEU_CAPPHAT.SP_PHIEU_DANHSACH_CAPPHAT", params);
    }

    /////////////////////////// Yeu cau cap phat //////////////////////////
    ///

    private List<Map<String, Object>> nomalizeResponse (ArrayList<Map<String, Object>> dbResult) {
        return dbResult.stream().map(map -> map.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(),
                        entry -> entry.getValue() == null ? "" : entry.getValue())))
                .collect(Collectors.toList());
    }

    public Object dsYeuCauCapPhat(Number type) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        params.add(new SqlParameter("p_type", type, Types.NUMERIC));

        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_GET_CAP_PHAT_YC", params);

        List<Map<String, Object>> result = dbResult.stream().map(map -> map.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toLowerCase(),
                entry -> entry.getValue() == null ? "" : entry.getValue())))
                .collect(Collectors.toList());
        
        return result;
    }

    public Object dsNhuCauAoHoa(Integer ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_GET_NC_AOHOA", params);

        List<Map<String, Object>> result = nomalizeResponse(dbResult);

        return result;
    }

    public Object dsNhuCauK8S(Integer ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_GET_NC_K8S", params);

        return nomalizeResponse(dbResult);
    }

    public Object dsNhuCauIDG(Integer ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_GET_NC_IDG", params);

        return nomalizeResponse(dbResult);
    }

    public Object dsNhuCauBackup(Integer ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_GET_NC_BACKUP", params);

        return nomalizeResponse(dbResult);
    }

    public Object dsThongTinCapPhatAoHoa(Integer ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_GET_TTCP_AO_HOA", params);

        return nomalizeResponse(dbResult);
    }

    public Object dsThongTinCapPhatK8S(Integer ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_GET_TTCP_CONTAINER", params);

        return nomalizeResponse(dbResult);
    }

    public Object dsThongTinCapPhatIDG(Integer ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_GET_TTCP_IDG", params);

        return nomalizeResponse(dbResult);
    }

    public Object dsThongTinCapPhatBackup(Integer ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_GET_TTCP_BACKUP", params);

        return nomalizeResponse(dbResult);
    }

    public Object createUpdateCapPhat(YeuCauCapPhatDTO dto) throws AppSqlException {
        Object result = createAndUpdateYeuCau(dto);

        //Tạo nhu cầu ảo hóa
        for (CapPhatYCAoHoaDTO yeucau : dto.getAohoa_list()) {
            createUpdateYCAoHoa(yeucau, (Number) result);
        }

        //Tạo nhu cầu k8s
        for (CapPhatYCK8sDTO yeucau : dto.getContainer_list()) {
            createUpdateYCK8s(yeucau, (Number) result);
        }

        //Tạo nhu cầu IDG
        for (CapPhatYCIDGDTO yeucau : dto.getIdg_list()) {
            createUpdateYCIDG(yeucau, (Number) result);
        }

        //Tạo nhu cầu ảo hóa
        for (CapPhatYCBackupDTO yeucau : dto.getBackup_list()) {
            createUpdateYCBackup(yeucau, (Number) result);
        }

        return result;
    }

    public Object createAndUpdateYeuCau (YeuCauCapPhatDTO dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", dto.getDonvi_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_yc_id", dto.getNguoi_yc_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_yc", dto.getNgay_yc(), Types.DATE));
        params.add(new SqlParameter("p_ngay_ht", dto.getNgay_ht(), Types.DATE));
        params.add(new SqlParameter("p_mucdich_yc", dto.getMucdich_yc(), Types.NVARCHAR));
        params.add(new SqlParameter("p_spdv_id", dto.getSpdv_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_spdv_ten", dto.getSpdv_ten(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hatangmang_id", dto.getHatangmang_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_file_yc", dto.getFile_yc(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", dto.getGhi_chu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_trangthai_phieu", dto.getTrangthai_phieu(), Types.NVARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ecms.PKG_PHIEU_CAPPHAT.SP_CREATE_UPDATE_YEU_CAU", params);
        return result.getOutValue();
    }

    public Object createUpdateYCAoHoa(CapPhatYCAoHoaDTO dto, Number ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_capphat_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_thanhphan", dto.getThanhphan(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngaytao", dto.getNgaytao(), Types.DATE));
        params.add(new SqlParameter("p_batdau", dto.getBatdau(), Types.DATE));
        params.add(new SqlParameter("p_ketthuc", dto.getKetthuc(), Types.DATE));
        params.add(new SqlParameter("p_vcpu", dto.getVcpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_vram", dto.getVram(), Types.NUMERIC));
        params.add(new SqlParameter("p_vgpu", dto.getVgpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_ssd", dto.getSsd(), Types.NUMERIC));
        params.add(new SqlParameter("p_hdd", dto.getHdd(), Types.NUMERIC));
        params.add(new SqlParameter("p_iops", dto.getIops(), Types.NUMERIC));
        params.add(new SqlParameter("p_throughtput", dto.getThroughput(), Types.NUMERIC));
        params.add(new SqlParameter("p_network_card", dto.getNetwork_card(), Types.NUMERIC));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ecms.PKG_PHIEU_CAPPHAT.SP_TT_NC_CREATE_UPDATE_AOHOA", params);
        return result.getOutValue();
    }

    public Object createUpdateYCK8s(CapPhatYCK8sDTO dto, Number ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_capphat_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_thanhphan", dto.getThanhphan(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngaytao", dto.getNgaytao(), Types.DATE));
        params.add(new SqlParameter("p_batdau", dto.getBatdau(), Types.DATE));
        params.add(new SqlParameter("p_ketthuc", dto.getKetthuc(), Types.DATE));
        params.add(new SqlParameter("p_cpu", dto.getCpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_ram", dto.getRam(), Types.NUMERIC));
        params.add(new SqlParameter("p_storage", dto.getStorage(), Types.NUMERIC));
        params.add(new SqlParameter("p_pod", dto.getPod(), Types.NUMERIC));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ecms.PKG_PHIEU_CAPPHAT.SP_TT_NC_CREATE_UPDATE_K8S", params);
        return result.getOutValue();
    }

    public Object createUpdateYCIDG(CapPhatYCIDGDTO dto, Number ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_capphat_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_thanhphan", dto.getThanhphan(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngaytao", dto.getNgaytao(), Types.DATE));
        params.add(new SqlParameter("p_batdau", dto.getBatdau(), Types.DATE));
        params.add(new SqlParameter("p_ketthuc", dto.getKetthuc(), Types.DATE));
        params.add(new SqlParameter("p_ma_rabbitmq", dto.getMa_rabbitmq(), Types.VARCHAR));
        params.add(new SqlParameter("p_idg_object", dto.getIdg_object(), Types.NUMERIC));
        params.add(new SqlParameter("p_ma_kafka", dto.getMa_kafka(), Types.VARCHAR));
        params.add(new SqlParameter("p_ma_api_gateway", dto.getMa_api_gateway(), Types.VARCHAR));
        params.add(new SqlParameter("p_ma_mongo", dto.getMa_mongo(), Types.VARCHAR));
        params.add(new SqlParameter("p_ma_mysql", dto.getMa_mysql(), Types.VARCHAR));
        params.add(new SqlParameter("p_ma_search_engine", dto.getMa_search_engine(), Types.VARCHAR));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ecms.PKG_PHIEU_CAPPHAT.SP_TT_NC_CREATE_UPDATE_IDG", params);
        return result.getOutValue();
    }

    public Object createUpdateYCBackup(CapPhatYCBackupDTO dto, Number ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_capphat_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_thanhphan", dto.getThanhphan(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngaytao", dto.getNgaytao(), Types.DATE));
        params.add(new SqlParameter("p_batdau", dto.getBatdau(), Types.DATE));
        params.add(new SqlParameter("p_ketthuc", dto.getKetthuc(), Types.DATE));
        params.add(new SqlParameter("p_tap_trung", dto.getTap_trung(), Types.NUMERIC));
        params.add(new SqlParameter("p_manual", dto.getManual(), Types.NUMERIC));
        params.add(new SqlParameter("p_dungluong", dto.getDungluong(), Types.NUMERIC));
        params.add(new SqlParameter("p_dungluong_tangtruong", dto.getDungluong_tangtruong(), Types.NUMERIC));
        params.add(new SqlParameter("p_vitri_backup", dto.getVitri_backup(), Types.VARCHAR));
//        params.add(new SqlParameter("p_tansuat_id", dto.getTansuat_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_tansuat", dto.getTansuat(), Types.VARCHAR));
        params.add(new SqlParameter("p_sl_backup", dto.getSl_backup(), Types.NUMERIC));
        params.add(new SqlParameter("p_external_backup", dto.getExternal_backup(), Types.NUMERIC));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ecms.PKG_PHIEU_CAPPHAT.SP_TT_NC_CREATE_UPDATE_BACKUP", params);
        return result.getOutValue();
    }

    public Object getFileYeuCau (Integer ycID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yc_id", ycID, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_GET_CAP_PHAT_YC_FILE", params);
    }

    public boolean deleteNCAoHoa (Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_TT_NC_DELETE_AOHOA", params);
        return result.getOutValue().toString().equals("1");
    }

    public boolean deleteNCK8s (Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_TT_NC_DELETE_K8S", params);
        return result.getOutValue().toString().equals("1");
    }

    public boolean deleteNCIDG (Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_TT_NC_DELETE_IDG", params);
        return result.getOutValue().toString().equals("1");
    }

    public boolean deleteNCBackup (Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_TT_NC_DELETE_BACKUP", params);
        return result.getOutValue().toString().equals("1");
    }

    public boolean deleteYeuCauCapPhat (Number[] ids) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", StringUtils.join(ids, ";"), Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_DELETE_YC_CP", params);
        return result.getOutValue().toString().equals("1");
    }

    public Object createPhieuCapPhat (Number yc_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_yc_id", yc_id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ecms.PKG_PHIEU_CAPPHAT.CREATE_PHIEU_CAPPHAT", params);
        return result.getOutValue();
    }

    public Object tuChoiCapPhatTaiNguyen (Number capphat_id, String lydo_tuchoi) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_identity_code", AppRequestContext.getCurrent().getIdentityCode(), Types.VARCHAR));
        params.add(new SqlParameter("p_capphat_id", capphat_id, Types.NUMERIC));
        params.add(new SqlParameter("p_lydotuchoi", lydo_tuchoi, Types.VARCHAR));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_TUCHOI_CAPPHAT_TAINGUYEN", params);
    }

    public Object xacNhanCapPhatTaiNguyen (Number capphat_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_identity_code", AppRequestContext.getCurrent().getIdentityCode(), Types.VARCHAR));
        params.add(new SqlParameter("p_capphat_id", capphat_id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_XACNHAN_CAPPHAT_TAINGUYEN", params);
    }

    public Object thucHienCapPhatTaiNguyen (Number capphat_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_identity_code", AppRequestContext.getCurrent().getIdentityCode(), Types.VARCHAR));
        params.add(new SqlParameter("p_capphat_id", capphat_id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_PHIEU_CAPPHAT.SP_THUCHIEN_CAPPHAT_TAINGUYEN", params);
    }
}