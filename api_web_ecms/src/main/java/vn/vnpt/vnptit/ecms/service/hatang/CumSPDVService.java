package vn.vnpt.vnptit.ecms.service.hatang;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSPDVDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSpdvChiTietDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSpdvDanhSachThanhPhanDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSpdvLuuDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSpdvLuuThanhPhanDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CumSpdvTheoDoiTuongDto;
import vn.vnpt.vnptit.ecms.dto.hatang.TimKiemThanhPhanDto;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.vnptit.ecms.service.idc.HaTangCNTTService;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CumSPDVService {
    
    private static final String PKG_PREFIX = "ECMS.PKG_HATANG_CUMSPDV.";
    
    @Autowired
    private DbContext dbContext;

    @Autowired
    private HaTangCNTTService haTangCNTTService;

    public ArrayList<Map<String, Object>> getDsCumSPDV(CumSPDVDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_ten_cum_spdv", dto.getTenCumSpdv(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_hinhthuc_cungcap_id", dto.getHinhthucCungcapId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_trangthaihd_id", dto.getTrangthaihdId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hieuluc_id", dto.getHieulucId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_tenspdv_id", dto.getTenspdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hatang_trienkhai_id", dto.getHatangTrienkhaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_donvi_ql_id", dto.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_chuquan_dv_id", dto.getChuquanDvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ngaytao_tu", dto.getNgaytaoTu(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ngaytao_den", dto.getNgaytaoDen(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_canhan_ql", dto.getCanhanQl(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_user_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_is_to_truong", haTangCNTTService.checkIsToTruong() ? 1 : 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_check_quyen", dto.getCheckQuyen(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(PKG_PREFIX + "SP_GET_DS_CUM_SPDV", parameters);
    }

    public ArrayList<Map<String, Object>> getDsCumSPDVTheoDoiTuong(CumSpdvTheoDoiTuongDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", dto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_type", dto.getType(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_PREFIX + "SP_GET_DS_CUM_SPDV_THEO_DOI_TUONG", parameters);
    }

    public ArrayList<Map<String, Object>> xoaCumSPDV(CumSPDVDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cumspdv_id", dto.getCumspdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_PREFIX + "SP_XOA_CUM_SPDV", parameters);
    }

    public ArrayList<Map<String, Object>> getDsSPDV(CumSPDVDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id_1", dto.getId1(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_id_2", dto.getId2(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap(PKG_PREFIX + "SP_GET_DS_SPDV", parameters);
    }

    public ArrayList<Map<String, Object>> timKiemThanhPhan(TimKiemThanhPhanDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_type", dto.getType(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

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

        // Type 3: Thiết bị vật lý
        parameters.add(new SqlParameter("p_khong_gian", dto.getKhongGian(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_rack", dto.getRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_so_huu_id", dto.getSoHuuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loai_thiet_bi_id", dto.getLoaiThietBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hang_sx_id", dto.getHangSxId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_serial_number", dto.getSerialNumber(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_thiet_bi", dto.getTenThietBi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_id_chi_tiet", dto.getIdChiTiet(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_the_tai_san", dto.getTheTaiSan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        // Type 4 & 5: Dịch vụ IDG / Dịch vụ hạ tầng khác
        parameters.add(new SqlParameter("p_ma_dich_vu", dto.getMaDichVu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_dich_vu", dto.getTenDichVu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_dat_tai_idc_id", dto.getDatTaiIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ngay_cap_tu", dto.getNgayCapTu(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ngay_cap_den", dto.getNgayCapDen(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_loai_luu_tru_id", dto.getLoaiLuuTruId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        // Type 6: VPC
        parameters.add(new SqlParameter("p_ma_vpc", dto.getMaVpc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_vpc", dto.getTenVpc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_loai_vpc_id", dto.getLoaiVpcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hieu_luc_id", dto.getHieuLucId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_don_vi_yeu_cau_id", dto.getDonViYeuCauId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phieu_yeu_cau", dto.getPhieuYeuCau(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_tinh_phi_id", dto.getTinhPhiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_user_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_is_to_truong", haTangCNTTService.checkIsToTruong() ? 1 : 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(PKG_PREFIX + "SP_TIMKIEM_THANHPHAN", parameters);
    }

    public ArrayList<Map<String, Object>> chiTietCumSpdv(CumSpdvChiTietDto dto) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cumspdv_id", dto.getCumspdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_PREFIX + "SP_GET_CHI_TIET_CUM_SPDV", parameters);
    }

    public ArrayList<Map<String, Object>> luuCumSpdv(CumSpdvLuuDto dto) throws AppSqlException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<SqlParameter> parameters = new ArrayList<>();

        parameters.add(new SqlParameter("p_cumspdv_id", dto.getCumspdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ten_cum_spdv", dto.getTenCumSpdv(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_hieu_luc", dto.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_spdv_id", dto.getSpdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_trang_thai_id", dto.getTrangThaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_chuquan_dv_id", dto.getChuquanDvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ngay_khoi_tao", dto.getNgayKhoiTao(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_phien_ban", dto.getPhienBan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_hinhthuc_cungcap_id", dto.getHinhthucCungcapId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_donvi_khoi_tao_id", dto.getDonviKhoiTaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hatang_trienkhai_id", dto.getHatangTrienkhaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoi_khoi_tao_id", dto.getNguoiKhoiTaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_donvi_ql_id", dto.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phong_ql_id", dto.getPhongQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        String caNhanQlJson;
        try {
            caNhanQlJson = dto.getCaNhanQlList() != null ? mapper.writeValueAsString(dto.getCaNhanQlList()) : "[]";
        } catch (Exception e) {
            caNhanQlJson = "[]";
        }
        parameters.add(new SqlParameter("p_ca_nhan_ql_list", caNhanQlJson, SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_ca_nhan_ql_id", dto.getCaNhanQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_PREFIX + "SP_UPSERT_CUM_SPDV", parameters);
    }

    public ArrayList<Map<String, Object>> danhSachThanhPhanCumSpdv(CumSpdvDanhSachThanhPhanDto dto) throws AppSqlException {
        ArrayList<Map<String, Object>> result = new ArrayList<>();

        Map<String, Object> mergedData = new HashMap<>();
        String[] typeKeys = {"", "ds_tat_ca", "ds_may_ao", "ds_thiet_bi_vat_ly", "ds_dich_vu_idg", "ds_dich_vu_ha_tang_khac", "ds_vpc"};

        for (int type = 1; type <= 6; type++) {
            ArrayList<SqlParameter> parameters = new ArrayList<>();
            parameters.add(new SqlParameter("p_cumspdv_id", dto.getCumspdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_type", type, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

            ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap(PKG_PREFIX + "SP_GET_DS_THANH_PHAN_CUM_SPDV", parameters);
            mergedData.put(typeKeys[type], rs != null ? rs : new ArrayList<>());
        }

        result.add(mergedData);
        return result;
    }

    public ArrayList<Map<String, Object>> luuThanhPhanCumSpdv(CumSpdvLuuThanhPhanDto dto) throws AppSqlException {
        ArrayList<Map<String, Object>> result = new ArrayList<>();

        // Xóa hết tất cả thành phần thuộc cụm SPDV trước khi lưu mới
        ArrayList<SqlParameter> xoaParameters = new ArrayList<>();
        xoaParameters.add(new SqlParameter("p_cumspdv_id", dto.getCumspdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        xoaParameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        dbContext.executeSpWithCursorToListMap(PKG_PREFIX + "SP_XOA_DS_THANH_PHAN_CUM_SPDV", xoaParameters);

        Map<String, List<CumSpdvLuuThanhPhanDto.ThanhPhanItem>> typeMap = new LinkedHashMap<>();
        typeMap.put("DS_MAY_AO", dto.getDsMayAo());
        typeMap.put("DS_THIET_BI_VAT_LY", dto.getDsThietBiVatLy());
        typeMap.put("DS_DICH_VU_IDG", dto.getDsDichVuIdg());
        typeMap.put("DS_DICH_VU_HA_TANG_KHAC", dto.getDsDichVuHaTangKhac());
        typeMap.put("DS_VPC", dto.getDsVpc());

        for (Map.Entry<String, List<CumSpdvLuuThanhPhanDto.ThanhPhanItem>> entry : typeMap.entrySet()) {
            String type = entry.getKey();
            List<CumSpdvLuuThanhPhanDto.ThanhPhanItem> items = entry.getValue();
            if (items == null || items.isEmpty()) continue;

            for (CumSpdvLuuThanhPhanDto.ThanhPhanItem item : items) {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_cumspdv_id", dto.getCumspdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                parameters.add(new SqlParameter("p_type", type, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                parameters.add(new SqlParameter("p_id", item.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                parameters.add(new SqlParameter("p_ngay_bo_sung", item.getNgayBoSung(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
                parameters.add(new SqlParameter("p_nguoi_bo_sung_id", item.getNguoiBoSungId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                parameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
                parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

                ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap(PKG_PREFIX + "SP_LUU_DS_THANH_PHAN_CUM_SPDV", parameters);
                if (rs != null) result.addAll(rs);
            }
        }

        return result;
    }
}
