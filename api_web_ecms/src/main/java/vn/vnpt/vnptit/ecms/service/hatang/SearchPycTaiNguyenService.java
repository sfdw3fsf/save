package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.*;
import vn.vnpt.vnptit.ecms.service.idc.HaTangCNTTService;

import java.sql.Types;
import java.util.*;

@Service
public class SearchPycTaiNguyenService {
    
    private static final String PKG_PREFIX = "ECMS.PKG_TEST_DUONG.";
    @Autowired
    private HaTangCNTTService haTangCNTTService;
    @Autowired
    private DbContext dbContext;

    public ArrayList<Map<String, Object>> timKiemThanhPhan(SearchPycTaiNguyenDto dto) throws AppSqlException {
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

        // Type 4: Thiết bị vật lý
        parameters.add(new SqlParameter("p_khong_gian", dto.getKhongGian(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_rack", dto.getRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_so_huu_id", dto.getSoHuuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loai_thiet_bi_id", dto.getLoaiThietBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hang_sx_id", dto.getHangSxId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_serial_number", dto.getSerialNumber(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_thiet_bi", dto.getTenThietBi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_id_chi_tiet", dto.getIdChiTiet(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_the_tai_san", dto.getTheTaiSan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        // Type 3: Dịch vụ IDG / Dịch vụ hạ tầng khác
        parameters.add(new SqlParameter("p_ma_dich_vu", dto.getMaDichVu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_dich_vu", dto.getTenDichVu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_dat_tai_idc_id", dto.getDatTaiIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ngay_cap_tu", dto.getNgayCapTu(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_ngay_cap_den", dto.getNgayCapDen(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        parameters.add(new SqlParameter("p_loai_luu_tru_id", dto.getLoaiLuuTruId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loai_dichvu_id", dto.getLoaiDichVuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        // Type 1: VPC
        parameters.add(new SqlParameter("p_ma_vpc", dto.getMaVpc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ten_vpc", dto.getTenVpc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_loai_vpc_id", dto.getLoaiVpcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hieu_luc_id", dto.getHieuLucId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_don_vi_yeu_cau_id", dto.getDonViYeuCauId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phieu_yeu_cau", dto.getPhieuYeuCau(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cumspdv_id", dto.getCumSpdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_spdv_id", dto.getSpdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        parameters.add(new SqlParameter("p_user_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_is_to_truong", haTangCNTTService.checkIsToTruong() ? 1 : 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(PKG_PREFIX + "SP_TIMKIEM_THANHPHAN", parameters);
    }


}
