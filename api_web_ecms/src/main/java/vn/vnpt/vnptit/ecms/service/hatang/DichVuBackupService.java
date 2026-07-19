package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.*;
import vn.vnpt.vnptit.ecms.dto.idc.IdcRackBatchValidateResponseDTO;
import vn.vnpt.vnptit.ecms.service.idc.HaTangCNTTService;

import java.io.IOException;
import java.sql.Types;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DichVuBackupService {

    @Autowired
    private DbContext dbContext;
    @Autowired
    private HaTangCNTTService haTangCNTTService;

    public ArrayList<Map<String, Object>> luuDichVuBackup(DichVuBackupDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        // Cập nhật tên param và thứ tự khớp với Procedure DB
        params.add(new SqlParameter("p_dichvubk_id", dto.getIdDichVu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_dichvu", dto.getMaDichVu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_kieu_cp", dto.getKieuCapphatId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dungluong", dto.getDungLuong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_hh", dto.getNgayHetHan(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_tinhphi", dto.getTinhPhi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_trangthaiccdv_id", dto.getTrangthaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_cq_id", dto.getChuquanDvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_ql_id", dto.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phong_ql_id", dto.getPhongQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_ql_id", dto.getCanhanQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sudung", dto.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DICHVU_BACKUP_HAILN.UPSET_DICHVU_BACKUP", params);
    }

    public ArrayList<Map<String, Object>> xoaDichVuBackup(DichVuBackupDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_dichvubk_id", dto.getIdDichVu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DICHVU_BACKUP_HAILN.XOA_DICHVU_BACKUP", params);
    }

    public ArrayList<Map<String, Object>> getChiTietDichVuBackup(DichVuBackupDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_dichvubk_id", dto.getIdDichVu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DICHVU_BACKUP_HAILN.GET_DICHVU_BACKUP_CHI_TIET", params);
    }

    public ArrayList<Map<String, Object>> getCauHinhDichVuBackup(DichVuBackupDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_dichvubk_id", dto.getIdDichVu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DICHVU_BACKUP_HAILN.GET_DICHVU_BACKUP_CAU_HINH", params);
    }

    public ArrayList<Map<String, Object>> layDsNguonBackup(DichVuBackupDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_dichvubk_id", dto.getIdDichVu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DICHVU_BACKUP_HAILN.GET_NGUON_BACKUP", params);
    }

    public ArrayList<Map<String, Object>> luuCauHinhDichVuBackup(DichVuBackupDto dto) throws AppSqlException {
        ArrayList<Map<String, Object>> result = new ArrayList<>();

        ArrayList<Map<String, Object>> dsNguonHienTai = layDsNguonBackup(dto);

        Set<Long> listIdMoi = new HashSet<>();
        if (dto.getNguonBackup() != null) {
            for (NguonBackupDto nguon : dto.getNguonBackup()) {
                if (nguon.getId() != null) {
                    listIdMoi.add(nguon.getId().longValue());
                }
            }
        }

        if (dsNguonHienTai != null && !dsNguonHienTai.isEmpty()) {
            for (Map<String, Object> nguonCu : dsNguonHienTai) {
                Long idCu = null;
                if (nguonCu.get("ID") != null) {
                    idCu = Long.valueOf(nguonCu.get("ID").toString());
                }
                if (idCu != null && !listIdMoi.contains(idCu)) {
                    xoaNguonBackup(idCu);
                }
            }
        }

        if (dto.getNguonBackup() != null && !dto.getNguonBackup().isEmpty()) {
            for (NguonBackupDto nguon : dto.getNguonBackup()) {
                result = saveNguonBackup(nguon, dto);
            }
        }

        return result;
    }

    private void xoaNguonBackup(Number nguonId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_nguon_id", nguonId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DICHVU_BACKUP_HAILN.XOA_NGUON_BACKUP", params);
    }

    private ArrayList<Map<String, Object>> saveNguonBackup(NguonBackupDto nguon, DichVuBackupDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", nguon.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id_dich_vu", dto.getIdDichVu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        
        params.add(new SqlParameter("p_ten_doi_tuong", nguon.getTenDoiTuong(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_loai_doi_tuong_id", nguon.getLoaiDoiTuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cum_hatang_id", nguon.getCumHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguon_bk_khac", nguon.getNguonBkKhac(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_idc_id", nguon.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_nguon_id", nguon.getIpNguonId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_nguon_text", nguon.getIpNguonText(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_loai_dulieu_id", nguon.getLoaiDuLieuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hostname", nguon.getHostname(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_duong_dan_nguon", nguon.getDuongDanNguon(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tan_suat_id", nguon.getTanSuatId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_chu_ky", nguon.getChuKy(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_lich_backup_id", nguon.getLichBackupId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_bat_dau", nguon.getNgayBatDau(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_thoi_gian", nguon.getThoiGian(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mayao_id", nguon.getMayaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dichvu_hatang_id", nguon.getDichvuHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", nguon.getThietbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_cum_ht_dich_id", dto.getCumHtDichId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_dich_id", dto.getIpDich(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_dich_text", dto.getIpDichText(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_duong_dan_dich", dto.getDuongDanDich(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));

        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DICHVU_BACKUP_HAILN.LUU_NGUON_BACKUP", params);
    }


    public ArrayList<Map<String, Object>> timKiemNguon(TimKiemThanhPhanDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_type", dto.getType(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        // Type 1: Thiết bị vật lý
        parameters.add(new SqlParameter("p_khong_gian", dto.getKhongGian(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_rack", dto.getRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_so_huu_id", dto.getSoHuuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loai_thiet_bi_id", dto.getLoaiThietBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hang_sx_id", dto.getHangSxId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_serial_number", dto.getSerialNumber(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_thiet_bi", dto.getTenThietBi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_id_chi_tiet", dto.getIdChiTiet(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_the_tai_san", dto.getTheTaiSan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        // Type 2: Máy ảo
        parameters.add(new SqlParameter("p_ip", dto.getIp(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_may_ao", dto.getTenMayAo(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_loai_may_ao_id", dto.getLoaiMayAoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_tinh_phi", dto.getTinhPhi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_doi_tuong_id", dto.getDoiTuongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cum_ha_tang_id", dto.getCumHaTangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_trang_thai_id", dto.getTrangThaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ngay_tao_tu", dto.getNgayTaoTu(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ngay_tao_den", dto.getNgayTaoDen(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_thuoc_vpc", dto.getThuocVpc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_don_vi_ql_id", dto.getDonViQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ngay_het_han_tu", dto.getNgayHetHanTu(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ngay_het_han_den", dto.getNgayHetHanDen(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_don_vi_chu_quan_id", dto.getDonViChuQuanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ca_nhan_quan_ly", dto.getCaNhanQuanLy(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        // Type 3 Dịch vụ hạ tầng
        parameters.add(new SqlParameter("p_ma_dich_vu", dto.getMaDichVu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_dich_vu", dto.getTenDichVu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_dat_tai_idc_id", dto.getDatTaiIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ngay_cap_tu", dto.getNgayCapTu(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ngay_cap_den", dto.getNgayCapDen(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_loai_luu_tru_id", dto.getLoaiLuuTruId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        parameters.add(new SqlParameter("p_user_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_is_to_truong", haTangCNTTService.checkIsToTruong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DICHVU_BACKUP_HAILN.SP_TIMKIEM_NGUON", parameters);
    }

    public ArrayList<Map<String, Object>> logLichSuCapPhat(Number id) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_NHDU.SP_GET_LICHSU_CAPPHAT", params);
    }

    public ArrayList<Map<String, Object>> getDungLuongThucTe(Number id) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_NHDU.SP_GET_DUNGLUONG_THUCTE", params);
    }

    public Object logLichSuThayDoi(Number id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_GET_LICHSU_THAYDOI", params);
    }

    public Object layDanhSach(DichVuBackupSearchDTO dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_kieu_cp", dto.getKieu_cap_phat(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cum_hatang_backup", dto.getCum_hatang_backup(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_doi_tuong", dto.getLoai_doi_tuong_nguon(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguon_backup", dto.getNguon_backup(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_loai_dl", dto.getLoai_du_lieu_backup(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cum_hatang_nguon", dto.getCum_hatang_nguon(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_nguon", dto.getIp_nguon(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dich_backup", dto.getDich_backup(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tan_suat", dto.getTan_suat_backup(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_trang_thai", dto.getTrang_thai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sudung", dto.getSudung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tinh_phi", dto.getTinh_phi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_cq", dto.getChu_quan(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_ql", dto.getDon_vi_quan_ly(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_ql", dto.getCa_nhan_quan_ly(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_V2.SP_SEARCH_DICHVU_BACKUP", params);
    }

    public IdcRackBatchValidateResponseDTO validateBatchDichVuBackup(List<DichVuBackupImportDTO> dtos) throws AppSqlException {
        Map<String, String> ERROR_MESSAGE_MAP = new HashMap<>();
        ERROR_MESSAGE_MAP.put("DUPLICATE_MA_DICHVU", "Mã Dịch vụ đã tồn tại trong hệ thống");

        try {
            int errorCount = 0;
            StringBuilder errorMessage = new StringBuilder();
            ArrayList<Map<String, Object>> response = new ArrayList<>();

            Map<String, Number> maMap = new HashMap<>();
            boolean hasValid1 = false;

            for (DichVuBackupImportDTO dto : dtos) {
                int errorCountForDto = errorCount;

                String ma = dto.getMa_dichvu() == null ? "" : dto.getMa_dichvu().trim().toLowerCase();

                if (!ma.isEmpty()) {
                    if (maMap.containsKey(ma)) {
                        errorCount++;
                        if (errorMessage.length() > 0) errorMessage.append("\n");

                        errorMessage.append("Dòng ")
                                .append(dto.getStt())
                                .append(": Mã dịch vụ bị trùng trong danh sách.");
                    } else {
                        maMap.put(ma, dto.getStt());
                    }
                }

                hasValid1 = errorCount == errorCountForDto;
            }

            boolean hasValid2 = false;

            for (DichVuBackupImportDTO dto : dtos) {

                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_ma_dichvu", dto.getMa_dichvu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("p_error_codes", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR));

                dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_V2.SP_VALIDATE_ITEM_DICHVU_BK", params);

                String errorCodes = null;

                for (SqlParameter param : params) {
                    if ("p_error_codes".equals(param.getName()) && param.getOutValue() != null) {
                        errorCodes = String.valueOf(param.getOutValue());
                    }
                }

                if (errorCodes != null && !errorCodes.isEmpty()) {
                    String[] codes = errorCodes.split(",");

                    for (String code : codes) {
                        String msg = ERROR_MESSAGE_MAP.getOrDefault(code, "Dữ liệu không hợp lệ");

                        errorCount++;
                        if (errorMessage.length() > 0) errorMessage.append("\n");

                        errorMessage.append("Dòng ")
                                .append(dto.getStt())
                                .append(": ")
                                .append(msg);
                    }
                } else {
                    hasValid2 = true;
                }
            }

            return new IdcRackBatchValidateResponseDTO(
                    errorCount,
                    errorMessage.toString(),
                    response,
                    hasValid1 && hasValid2
            );

        } catch (IOException e) {
            throw new AppSqlException(e);
        }
    }

    public Map<String, Object> batchInsertDichVuBackup(List<DichVuBackupImportDTO> dtos) throws AppSqlException {

        IdcRackBatchValidateResponseDTO validateResult = validateBatchDichVuBackup(dtos);

        Set<Number> errorSttSet = extractErrorStt(validateResult.getErrorMessage());
        List<Number> successStt = new ArrayList<>();

        for (DichVuBackupImportDTO dto : dtos) {

            if (errorSttSet.contains(dto.getStt())) {
                continue;
            }

            String nguoiCn = AppRequestContext.getCurrent().getUserName();

            ArrayList<SqlParameter> params = new ArrayList<>();

            params.add(new SqlParameter("p_ma_dichvu", dto.getMa_dichvu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_kieu_cp", dto.getKieu_cp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_dungluong", dto.getDungluong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_trangthaiccdv_id", dto.getTrangthaiccdv_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ngay_hh",dto.getNgay_hh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));

            params.add(new SqlParameter("p_sudung", dto.getSudung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_tinhphi", dto.getTinhphi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));

            params.add(new SqlParameter("p_donvi_cq_id", dto.getDonvi_cq_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_donvi_ql_id", dto.getDonvi_ql_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nhanvien_ql_id", dto.getNhanvien_ql_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_phong_ql_id", dto.getPhong_ql_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

            params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_res", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

            Map<String, Object> result = dbContext.executeSpWithCursorToMap(
                    "ECMS.PKG_IDC_TAINGUYEN_V2.SP_IMPORT_ITEM_DICHVU_BK",
                    params
            );

            if (result != null && result.containsKey("DICHVUBK_ID") && result.get("DICHVUBK_ID") != null) {
                successStt.add(dto.getStt());
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("SUCCESS_ITEMS_STT", successStt);
        response.put("VALIDATION_ERRORS", validateResult);

        return response;
    }

    private Set<Number> extractErrorStt(String errorMessage) {

        Set<Number> result = new HashSet<>();

        if (errorMessage == null || errorMessage.isEmpty()) {
            return result;
        }

        Pattern pattern = Pattern.compile("Dòng\\s+(\\d+)");
        Matcher matcher = pattern.matcher(errorMessage);

        while (matcher.find()) {
            result.add(Integer.parseInt(matcher.group(1)));
        }

        return result;
    }
}
