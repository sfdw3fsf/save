package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.MayAoSearchDto;
import vn.vnpt.vnptit.ecms.dto.idc.MayAoDto;
import vn.vnpt.vnptit.ecms.dto.idc.NangLucMayAoDto;
import vn.vnpt.vnptit.ecms.dto.idc.OcungMayAoDto;
import vn.vnpt.vnptit.ecms.dto.idc.UpsertMayAoDtoV2;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("idcMayAoService")
public class MayAoService {
    private final DbContext dbContext;
    private final VCenterService vCenterService;
    private final HaTangCNTTService haTangCNTTService;

    public MayAoService(DbContext dbContext, VCenterService vCenterService, HaTangCNTTService haTangCNTTService) {
        this.dbContext = dbContext;
        this.vCenterService = vCenterService;
        this.haTangCNTTService = haTangCNTTService;
    }

    public Object getDanhSachMayAo(MayAoSearchDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_IP", dto.getIp(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_ID_MAY_AO", dto.getIdMayAo(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_LOAI_MAY_AO_ID", dto.getLoaiMayAoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TINH_PHI", dto.getTinhPhi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HIEU_LUC", dto.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN_MAY_AO", dto.getTenMayAo(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_CUM_HA_TANG_ID", dto.getCumHaTangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DOI_TUONG_ID", dto.getDoiTuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGAY_TAO_BD", dto.getNgayTaoBd(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_NGAY_TAO_KT", dto.getNgayTaoKt(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_THUOC_VPC_ID", dto.getThuocVpcId(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_HE_DIEU_HANH_ID", dto.getHeDieuHanhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGAY_HET_HAN_BD", dto.getNgayHetHanBd(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_NGAY_HET_HAN_KT", dto.getNgayHetHanKt(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_DON_VI_QUAN_LY_ID", dto.getDonViQuanLyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRANG_THAI_ID", dto.getTrangThaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DON_VI_CHU_QUAN_ID", dto.getDonViChuQuanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CA_NHAN_QUAN_LY_ID", dto.getCaNhanQuanLyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HA_TANG_IDG_ID", dto.getHaTangIdgId(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_USER_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IS_TO_TRUONG", haTangCNTTService.checkIsToTruong() ? 1 : 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_DS_MAYAO", params);
    }

    public ArrayList<Map<String, Object>> getDetailMayAo(Number mayaoId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mayao_id", mayaoId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_pks", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_DETAIL_MAYAO", params);
    }

    public Map<String, Object> upsertMayAo(MayAoDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = getMayAoSqlParameters(dto);
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_UPSERT_MAYAO", params);
    }

    public Map<String, Object> deleteMayAo(Number mayaoId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mayao_id", mayaoId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_DELETE_MAYAO", params);
    }

    private ArrayList<SqlParameter> getMayAoSqlParameters(MayAoDto dto) {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mayao_id", dto.getMayaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cumht_id", dto.getCumhtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cumht_idg_id", dto.getCumhtIdgId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_mayao", dto.getMaMayao(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_mayao", dto.getTenMayao(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_vpc", dto.getVpc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tinh_id", dto.getTinhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hdtb_id", dto.getHdtbId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thuebao_id", dto.getThuebaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_tb", dto.getMaTb(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hedieuhanh_id", dto.getHedieuhanhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mayao_vpc_id", dto.getMayaoVpcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaimayao_id", dto.getLoaimayaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sudung", dto.getSudung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tinhphi", dto.getTinhphi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaivt_id", dto.getLoaivtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_noibo", dto.getNoibo(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_trangthaisd_id", dto.getTrangthaisdId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phieuyc_id", dto.getPhieuycId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_yc", dto.getNguoiYc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_kt", dto.getNgayKt(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_ngay_hh", dto.getNgayHh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_donvi_ql_id", dto.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phong_ql_id", dto.getPhongQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_ql_id", dto.getNhanvienQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_cq_id", dto.getDonviCqId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mota", dto.getMota(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_cn", dto.getNgayCn(), SqlParameter.ParameterDirection.INPUT, Types.DATE));

        return params;
    }

    // ==================== Năng lực Máy ảo ====================

    public ArrayList<Map<String, Object>> getDetailNangLucMayAo(Number mayaoId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mayao_id", mayaoId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_pks", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_DETAIL_NANGLUC_MAYAO", params);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getDetailNangLucWithStorage(Number mayaoId) throws AppSqlException {
        Map<String, Object> result = new HashMap<>();
        ArrayList<Map<String, Object>> nangLucList = getDetailNangLucMayAo(mayaoId);
        if (nangLucList != null && !nangLucList.isEmpty()) {
            result.putAll(nangLucList.get(0));
        }

        Map<String, Object> storageDetail = getStorageDetail(mayaoId);
        result.put("storageDetail", storageDetail);

        // Tự tính tổng disk theo loại từ blockStorage -> ghi đè vào DL_HDD, DL_SDD, DL_NVME
        // LOAIOCUNG_ID: 122 = SSD, 123 = HDD, 124 = NVMe
        long dlHdd = 0, dlSdd = 0, dlNvme = 0;
        Object blockStorageObj = storageDetail.get("blockStorage");
        if (blockStorageObj instanceof List) {
            for (Object item : (List<?>) blockStorageObj) {
                if (item instanceof Map) {
                    Map<String, Object> disk = (Map<String, Object>) item;
                    Number loaiId = disk.get("LOAIOCUNG_ID") instanceof Number ? (Number) disk.get("LOAIOCUNG_ID") : null;
                    Number dungLuong = disk.get("DUNGLUONG") instanceof Number ? (Number) disk.get("DUNGLUONG") : null;
                    if (loaiId != null && dungLuong != null) {
                        long dl = dungLuong.longValue();
                        switch (loaiId.intValue()) {
                            case 123: dlHdd += dl; break;
                            case 122: dlSdd += dl; break;
                            case 124: dlNvme += dl; break;
                        }
                    }
                }
            }
        }
        result.put("DL_HDD", dlHdd);
        result.put("DL_SDD", dlSdd);
        result.put("DL_NVME", dlNvme);

        return result;
    }

    public Map<String, Object> upsertNangLucMayAo(NangLucMayAoDto dto) throws AppSqlException {
        // Tự tính tổng disk theo loại từ OCUNG_MAYAO trước khi lưu
        // LOAIOCUNG_ID: 122 = SSD, 123 = HDD, 124 = NVMe
        long dlHdd = 0, dlSdd = 0, dlNvme = 0;
        ArrayList<Map<String, Object>> ocungList = getOcungMayAo(dto.getMayaoId());
        if (ocungList != null) {
            for (Map<String, Object> disk : ocungList) {
                Number loaiId = disk.get("LOAIOCUNG_ID") instanceof Number ? (Number) disk.get("LOAIOCUNG_ID") : null;
                Number dungLuong = disk.get("DUNGLUONG") instanceof Number ? (Number) disk.get("DUNGLUONG") : null;
                if (loaiId != null && dungLuong != null) {
                    long dl = dungLuong.longValue();
                    switch (loaiId.intValue()) {
                        case 123: dlHdd += dl; break;
                        case 122: dlSdd += dl; break;
                        case 124: dlNvme += dl; break;
                    }
                }
            }
        }
        dto.setDlHdd(dlHdd);
        dto.setDlSdd(dlSdd);
        dto.setDlNvme(dlNvme);

        ArrayList<SqlParameter> params = getNangLucMayAoSqlParameters(dto);
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_UPSERT_NANGLUC_MAYAO", params);
    }

    private ArrayList<SqlParameter> getNangLucMayAoSqlParameters(NangLucMayAoDto dto) {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_nangluc_mayao_id", dto.getNanglucMayaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mayao_id", dto.getMayaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_corecpu", dto.getSlCorecpu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_ram", dto.getDlRam(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_gpu", dto.getDlGpu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_storage", dto.getDlStorage(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_hdd", dto.getDlHdd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_sdd", dto.getDlSdd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_nvme", dto.getDlNvme(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_iops_id", dto.getIopsId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_throughput_id", dto.getThroughputId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMota(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        return params;
    }

    // ==================== OCUNG_MAYAO (Block Storage) ====================

    public ArrayList<Map<String, Object>> getOcungMayAo(Number mayaoId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_mayao_id", mayaoId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_pks", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_OCUNG_MAYAO", params);
    }

    public Map<String, Object> getStorageDetail(Number mayaoId) throws AppSqlException {
        Map<String, Object> storageDetail = new HashMap<>();
        storageDetail.put("blockStorage", getOcungMayAo(mayaoId));
        storageDetail.put("fileStorage", new ArrayList<>());
        storageDetail.put("objectStorage", new ArrayList<>());
        return storageDetail;
    }

    public Map<String, Object> upsertOcungMayAo(OcungMayAoDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = getOcungMayAoSqlParameters(dto);
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_UPSERT_OCUNG_MAYAO", params);
    }

    public Map<String, Object> deleteOcungMayAo(Number ocungMayaoId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ocung_mayao_id", ocungMayaoId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_DELETE_OCUNG_MAYAO", params);
    }

    private ArrayList<SqlParameter> getOcungMayAoSqlParameters(OcungMayAoDto dto) {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ocung_mayao_id", dto.getOcungMayaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mayao_id", dto.getMayaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vungdl_id", dto.getVungdlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_ocung", dto.getMaOcung(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_ocung", dto.getTenOcung(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_loaiocung_id", dto.getLoaiocungId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dungluong", dto.getDungluong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sudung", dto.getSudung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hatanglttd_id", dto.getHatanglttdId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", dto.getThietbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ocung_hethong", dto.getOcungHethong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMota(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        return params;
    }

    // ==================== Đồng bộ từ Gateway ====================

    /**
     * Đồng bộ dữ liệu máy ảo từ VCenter Gateway.
     * Gọi API vm-detail, mapping response sang DTO ECMS (không lưu DB).
     * @param vmCode mã máy ảo trên vCenter (ví dụ: vm-1013)
     * @return Map chứa mayAo (MayAoDto fields), nangLuc (NangLucMayAoDto fields), diskList (OcungMayAoDto list)
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> syncVmFromGateway(String vmCode) {
        Object rawData = vCenterService.getVmDetail(vmCode);
        if (!(rawData instanceof Map)) {
            throw new RuntimeException("Dữ liệu trả về từ gateway không hợp lệ");
        }
        Map<String, Object> data = (Map<String, Object>) rawData;

        Map<String, Object> result = new HashMap<>();

        // === Mapping MayAoDto ===
        Map<String, Object> mayAo = new HashMap<>();
        mayAo.put("maMayao", data.get("vmCode"));       // vmCode -> MA_MAYAO
        mayAo.put("tenMayao", data.get("vmName"));       // vmName -> TEN_MAYAO
        // Các trường còn lại cần user nhập thủ công, set null
        mayAo.put("mayaoId", null);
        mayAo.put("cumhtId", null);
        mayAo.put("cumhtIdgId", null);
        mayAo.put("vpc", null);
        mayAo.put("tinhId", null);
        mayAo.put("hdtbId", null);
        mayAo.put("thuebaoId", null);
        mayAo.put("maTb", null);
        mayAo.put("hedieuhanhId", null);
        mayAo.put("mayaoVpcId", null);
        mayAo.put("loaimayaoId", null);
        mayAo.put("sudung", null);
        mayAo.put("tinhphi", null);
        mayAo.put("loaivtId", null);
        mayAo.put("noibo", null);
        mayAo.put("trangthaisdId", null);
        mayAo.put("phieuycId", null);
        mayAo.put("nguoiYc", null);
        mayAo.put("ngayKt", null);
        mayAo.put("ngayHh", null);
        mayAo.put("ghichu", null);
        mayAo.put("donviQlId", null);
        mayAo.put("phongQlId", null);
        mayAo.put("nhanvienQlId", null);
        mayAo.put("donviCqId", null);
        mayAo.put("mota", null);
        result.put("mayAo", mayAo);

        // === Mapping NangLucMayAoDto ===
        Map<String, Object> nangLuc = new HashMap<>();
        nangLuc.put("slCorecpu", data.get("slCoreCpu"));  // slCoreCpu -> SL_CORECPU
        nangLuc.put("dlRam", data.get("dlRam"));           // dlRam -> DL_RAM
        nangLuc.put("dlGpu", null);
        nangLuc.put("dlStorage", null);
        nangLuc.put("dlHdd", null);
        nangLuc.put("dlSdd", null);
        nangLuc.put("dlNvme", null);
        nangLuc.put("iopsId", null);
        nangLuc.put("throughputId", null);
        result.put("nangLuc", nangLuc);

        // === Mapping diskList -> OcungMayAoDto list ===
        List<Map<String, Object>> diskList = new ArrayList<>();
        Object rawDiskList = data.get("diskList");
        if (rawDiskList instanceof List) {
            for (Object diskItem : (List<?>) rawDiskList) {
                if (diskItem instanceof Map) {
                    Map<String, Object> disk = (Map<String, Object>) diskItem;
                    Map<String, Object> ocung = new HashMap<>();
                    ocung.put("maOcung", disk.get("diskCode"));     // diskCode -> MA_OCUNG
                    ocung.put("tenOcung", disk.get("diskName"));    // diskName -> TEN_OCUNG
                    ocung.put("dungluong", disk.get("dungLuong"));  // dungLuong -> DUNGLUONG
                    ocung.put("ocungMayaoId", null);
                    ocung.put("mayaoId", null);
                    ocung.put("vungdlId", null);
                    ocung.put("loaiocungId", null);
                    // Lấy trạng thái sử dụng từ Gateway trả về (nếu có trường sudung hoặc status)
                    Object state = disk.get("sudung");
                    if (state == null) state = disk.get("status");
                    ocung.put("sudung", state);
                    ocung.put("hatanglttdId", null);
                    ocung.put("thietbiId", null);
                    ocung.put("ocungHethong", null);
                    diskList.add(ocung);
                }
            }
        }
        result.put("diskList", diskList);

        // networkIpList - TODO: xử lý sau
        result.put("networkIpList", new ArrayList<>());

        return result;
    }


    // ==================== Cập nhật trực tiếp Đồng Bộ VM (Backend) ====================

    private Number toNumber(Object obj) {
        if (obj instanceof Number) return (Number) obj;
        if (obj instanceof String) {
            try { return new java.math.BigDecimal((String) obj); } catch (Exception e) { return null; }
        }
        return null;
    }

    private String toStringValue(Object obj) {
        return obj != null ? obj.toString() : null;
    }









    /**
     * Tìm MAYAO_ID theo mã máy ảo (MA_MAYAO) trong DB.
     * Sử dụng SP danh sách với filter P_ID_MAY_AO.
     * @return MAYAO_ID nếu tìm thấy, null nếu không tồn tại
     */
    @SuppressWarnings("unchecked")
    private Number findMayaoIdByMa(String maMayao) throws AppSqlException {
        MayAoSearchDto searchDto = new MayAoSearchDto();
        searchDto.setIdMayAo(maMayao);
        Object result = getDanhSachMayAo(searchDto);
        if (result instanceof List) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) result;
            if (!list.isEmpty()) {
                // Tìm chính xác theo mã (tránh trường hợp LIKE)
                for (Map<String, Object> row : list) {
                    String dbMa = toStringValue(row.get("MA_MAYAO"));
                    if (maMayao.equalsIgnoreCase(dbMa)) {
                        Number id = toNumber(row.get("MAYAO_ID"));
                        System.out.println("[syncSaveVm] Tìm thấy máy ảo trong DB: MA_MAYAO=" + dbMa + ", MAYAO_ID=" + id);
                        return id;
                    }
                }
            }
        }
        System.out.println("[syncSaveVm] Không tìm thấy máy ảo trong DB với mã: " + maMayao);
        return null;
    }

    @SuppressWarnings("unchecked")
    public Object syncAndSaveSingleVm(String vmCode, Number vmId) throws AppSqlException {
        // === Bước 1: Nếu client không truyền vmId, tìm trong DB theo mã ===
        if (vmId == null || vmId.longValue() == 0) {
            Number existingId = findMayaoIdByMa(vmCode);
            if (existingId != null && existingId.longValue() > 0) {
                vmId = existingId;
                System.out.println("[syncSaveVm] Mã " + vmCode + " đã tồn tại trong DB với ID=" + vmId + " -> chuyển sang CẬP NHẬT");
            }
        }

        // === Bước 2: Lấy dữ liệu từ VCenter Gateway ===
        Map<String, Object> syncDataWrapper = syncVmFromGateway(vmCode);
        if (syncDataWrapper == null) {
            throw new RuntimeException("Không tìm thấy thông tin đồng bộ từ VCenter cho mã: " + vmCode);
        }

        Map<String, Object> syncMayAo = (Map<String, Object>) syncDataWrapper.get("mayAo");
        Map<String, Object> syncNangLuc = (Map<String, Object>) syncDataWrapper.get("nangLuc");
        List<Map<String, Object>> syncDiskList = (List<Map<String, Object>>) syncDataWrapper.get("diskList");

        String jsonDisk = null;
        if (syncDiskList != null && !syncDiskList.isEmpty()) {
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                jsonDisk = mapper.writeValueAsString(syncDiskList);
            } catch (Exception e) {
                System.out.println("[syncSaveVm] Lỗi parse disk list sang json: " + e.getMessage());
            }
        }

        System.out.println("[syncSaveVm] Bắt đầu gọi SP_SYNC_MAYAO_FROM_VCENTER (" + vmCode + ")");

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_mayao", syncMayAo.get("maMayao"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_mayao", syncMayAo.get("tenMayao"), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sl_corecpu", toNumber(syncNangLuc.get("slCorecpu")), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_ram", toNumber(syncNangLuc.get("dlRam")), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_disk_json", jsonDisk, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> resMap;
        try {
            resMap = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_SYNC_MAYAO_FROM_VCENTER", params);
        } catch (AppSqlException e) {
            System.out.println("[syncSaveVm] ❌ Lỗi DB khi chạy SP_SYNC: " + e.getMessage());
            throw new RuntimeException(e);
        }

        Number mayaoId = extractResultId(resMap);
        if (mayaoId == null || mayaoId.longValue() <= 0) {
            System.out.println("[syncSaveVm] ❌ SP_SYNC_MAYAO_FROM_VCENTER trả về null hoặc <=0. resMap=" + resMap);
            throw new RuntimeException("Lỗi đồng bộ máy ảo, SP trả về: " + resMap);
        }

        System.out.println("[syncSaveVm] ✅ XONG! ID máy ảo=" + mayaoId);
        return mayaoId;
    }

    /**
     * Trích xuất resultId từ kết quả SP.
     * SP_UPSERT_MAYAO trả về Map có key "item" chứa MAYAO_ID.
     */
    private Number extractResultId(Map<String, Object> resMap) {
        if (resMap == null) return null;
        Object val = resMap.get("item");
        return val instanceof Number ? (Number) val : null;
    }

    // ============ Mapping VCenter response → UpsertMayAoDtoV2 ============
    @SuppressWarnings("unchecked")
    public UpsertMayAoDtoV2 mapVCenterToUpsertDto(Map<String, Object> data, String clusterCode) {
        UpsertMayAoDtoV2 result = new UpsertMayAoDtoV2();
        result.setClusterCode(clusterCode);

        // === Mapping MayAoDto ===
        MayAoDto mayAo = new MayAoDto();
        mayAo.setMaMayao(toStringValue(data.get("vmCode")));   // vmCode -> MA_MAYAO
        mayAo.setTenMayao(toStringValue(data.get("vmName"))); // vmName -> TEN_MAYAO
        result.setMayAo(mayAo);

        // === Mapping NangLucMayAoDto ===
        NangLucMayAoDto nangLuc = new NangLucMayAoDto();
        nangLuc.setSlCorecpu(toNumber(data.get("slCoreCpu"))); // slCoreCpu -> SL_CORECPU
        nangLuc.setDlRam(toNumber(data.get("dlRam")));         // dlRam -> DL_RAM
        result.setNangLuc(nangLuc);

        // === Mapping diskList -> List<OcungMayAoDto> ===
        List<OcungMayAoDto> diskList = new ArrayList<>();
        Object rawDiskList = data.get("diskList");
        if (rawDiskList instanceof List) {
            for (Object diskItem : (List<?>) rawDiskList) {
                if (diskItem instanceof Map) {
                    Map<String, Object> disk = (Map<String, Object>) diskItem;
                    OcungMayAoDto ocung = new OcungMayAoDto();
                    ocung.setMaOcung(toStringValue(disk.get("diskCode")));   // diskCode -> MA_OCUNG
                    ocung.setTenOcung(toStringValue(disk.get("diskName"))); // diskName -> TEN_OCUNG
                    ocung.setDungluong(toNumber(disk.get("dungLuong")));    // dungLuong -> DUNGLUONG
                    ocung.setOcungMayaoId(null);
                    diskList.add(ocung);
                }
            }
        }
        result.setDisks(diskList);

        // networkIpList - TODO: xử lý sau
        return result;
    }

    // ============  Sync MayAo By CumHT ==============
    public Map<String, Object> syncMayAoByCumHT(UpsertMayAoDtoV2 dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String diskJson = "";
        try {
            diskJson = JsonConvert.serialize(dto.getDisks());
        } catch (Exception e) {
            throw new AppSqlException("Lỗi convert danh sách ổ cứng sang JSON");
        }

        MayAoDto mayAo = dto.getMayAo() != null ? dto.getMayAo() : new MayAoDto();
        NangLucMayAoDto nangLuc = dto.getNangLuc() != null ? dto.getNangLuc() : new NangLucMayAoDto();

        params.add(new SqlParameter("p_cluster_code", dto.getClusterCode(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_ma_mayao", mayAo.getMaMayao(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_ten_mayao", mayAo.getTenMayao(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        params.add(new SqlParameter("p_sl_corecpu", nangLuc.getSlCorecpu(), SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(
                new SqlParameter("p_dl_ram", nangLuc.getDlRam(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_disk_json", diskJson, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_SYNC_MAYAO_BY_CUMHT", params);
    }
}
