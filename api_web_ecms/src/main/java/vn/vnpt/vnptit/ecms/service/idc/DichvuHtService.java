package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.DichvuHtDto;
import vn.vnpt.vnptit.ecms.dto.idc.DongBoDichvuHtDto;
import vn.vnpt.vnptit.ecms.dto.idc.NangLucDvhtDto;
import vn.vnpt.vnptit.ecms.service.dongbo.DongBoIDGService;

import java.sql.Types;
import java.util.ArrayList;

import java.util.Map;

@Service("idcDichvuHtService")
public class DichvuHtService {
    private final DbContext dbContext;
    private final DongBoIDGService dongBoIDGService;

    public DichvuHtService(DbContext dbContext, DongBoIDGService dongBoIDGService) {
        this.dbContext = dbContext;
        this.dongBoIDGService = dongBoIDGService;
    }

    // ==================== Đồng bộ Dịch vụ hạ tầng ====================

    /**
     * Đồng bộ dịch vụ hạ tầng dựa trên tham số nội bộ.
     * noibo = 1 => Gọi API nội bộ (dongBoChiTietDichVu)
     * noibo = 0 => Gọi API public (dongBoChiTietDichVuPublic)
     * Sau đồng bộ, tra cứu DB theo mã cụm HT + serviceName để trả về dichvuhtId.
     */
    public Number dongBoDichvuHt(DongBoDichvuHtDto dto) throws AppSqlException {
        // 1. Validate đầu vào
        if (dto.getNoibo() == null) {
            throw new AppSqlException("Trường nội bộ (noibo) không được để trống!");
        }
        if (dto.getServiceName() == null || dto.getServiceName().trim().isEmpty()) {
            throw new AppSqlException("Mã dịch vụ (serviceName) không được để trống!");
        }

        int noibo = dto.getNoibo().intValue();

        // Default values cho maCumHaTang và rancherCode
        String maCumHaTang = (dto.getMaCumHaTang() != null && !dto.getMaCumHaTang().trim().isEmpty())
                ? dto.getMaCumHaTang() : "local";
        String rancherCode = (dto.getRancherCode() != null && !dto.getRancherCode().trim().isEmpty())
                ? dto.getRancherCode() : "xxx";

        // 2. Xác định dịch vụ đã tồn tại chưa để quyết định quyền xóa nếu đồng bộ thất bại
        boolean isNew = !kiemTraTonTai(maCumHaTang, dto.getServiceName());
        Number dichvuhtId = timDichvuHtTheoMa(maCumHaTang, dto.getServiceName());

        // 3. Phân luồng gọi API và tự bắt lỗi 
        try {
            if (noibo == 1) {
                dongBoIDGService.dongBoChiTietDichVu(maCumHaTang, rancherCode, dto.getServiceName());
            } else {
                if (dto.getVpcId() == null || dto.getVpcId().trim().isEmpty()) {
                    throw new AppSqlException("Đồng bộ public yêu cầu vpc_id!");
                }
                dongBoIDGService.dongBoChiTietDichVuPublic(maCumHaTang, dto.getVpcId(), dto.getServiceName());
            }

            // Gọi kiểm tra lại ngay lập tức nếu dữ liệu DICHVUHT_ID chưa cập nhật năng lực/trạng thái (Optional workaround cho void).
            // Tạm thời nếu phương thức đã được quy định là throw từ service khi ko thấy thì xuống khối catch.
        } catch (Exception e) {
            // Trường hợp lỗi API, lỗi kết nối hoặc exception văng ra
            if (isNew && dichvuhtId != null) {
                deleteDichvuHtTheoLoi(dichvuhtId);
            }
            if (e instanceof AppSqlException) {
                throw (AppSqlException) e;
            }
            throw new AppSqlException("Đồng bộ thất bại do không có mã hoặc lỗi IDG: " + e.getMessage());
        }

        return dichvuhtId;
    }

    private boolean kiemTraTonTai(String maCumHaTang, String tenDichvu) {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_cumht", maCumHaTang, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_dichvu", tenDichvu, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter resultParam = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(resultParam);

        try {
            dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_KIEMTRA_TONTAI_DICHVU_HT", params);
            if (resultParam.getOutValue() != null) {
                return ((Number) resultParam.getOutValue()).intValue() == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void deleteDichvuHtTheoLoi(Number dichvuhtId) {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_dichvuht_id", dichvuhtId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        try {
            // Gọi SP delete record dịch vụ rác nếu đã tạo mới mà call IDG fail
            dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_DELETE_DICHVU_HT", params);
        } catch (Exception ignore) {}
    }

    /**
     * Tra cứu DICHVU_HT theo mã cụm hạ tầng (MA_CUMHT) + tên dịch vụ (TEN_DICHVU hoặc MA_DICHVU).
     * Trả về DICHVUHT_ID nếu tìm thấy, null nếu không.
     */
    private Number timDichvuHtTheoMa(String maCumHaTang, String tenDichvu) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_cumht", maCumHaTang, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_dichvu", tenDichvu, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter outParam = new SqlParameter("p_dichvuht_id", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(outParam);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_TIM_DICHVU_HT_THEO_MA", params);

        if (outParam.getOutValue() != null) {
            return (Number) outParam.getOutValue();
        }
        return null;
    }

    public ArrayList<Map<String, Object>> getDetailDichvuHt(Number dichvuhtId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_dichvuht_id", dichvuhtId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_pks", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_DETAIL_DICHVU_HT", params);
    }

    public Map<String, Object> upsertDichvuHt(DichvuHtDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = getDichvuHtSqlParameters(dto);
        params.add(new SqlParameter("p_dichvuht_id", dto.getDichvuhtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_UPSERT_DICHVU_HT", params);
    }

    public Map<String, Object> deleteDichvuHt(Number dichvuhtId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_dichvuht_id", dichvuhtId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_DELETE_DICHVU_HT", params);
    }

    private ArrayList<SqlParameter> getDichvuHtSqlParameters(DichvuHtDto dto) {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten_dichvu", dto.getTenDichvu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma_dichvu", dto.getMaDichvu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cumht_id", dto.getCumhtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sudung", dto.getSudung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tinhphi", dto.getTinhphi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaidvht_id", dto.getLoaidvhtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mayao_vpc_id", dto.getMayaoVpcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_kt", dto.getNgayKt(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_ngay_hh", dto.getNgayHh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_trangthaicc_id", dto.getTrangthaiccId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tinh_id", dto.getTinhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hdtb_id", dto.getHdtbId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thuebao_id", dto.getThuebaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ma_tb", dto.getMaTb(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_noibo", dto.getNoibo(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_cp_id", dto.getDonviCpId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten_taikhoan", dto.getTenTaikhoan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_link_quantri", dto.getLinkQuantri(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_phanloai", dto.getPhanloai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_donvi_ql_id", dto.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phong_ql_id", dto.getPhongQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_ql_id", dto.getNhanvienQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_cq_id", dto.getDonviCqId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMota(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        return params;
    }

    // ==================== Năng lực Dịch vụ hạ tầng ====================

    public Map<String, Object> getDetailNangLucDvht(Number dichvuhtId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_dichvuht_id", dichvuhtId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_pks", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_DETAIL_NANGLUC_DVHT", params);
    }

    public Map<String, Object> upsertNangLucDvht(NangLucDvhtDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = getNangLucDvhtSqlParameters(dto);
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_UPSERT_NANGLUC_DVHT", params);
    }

    private ArrayList<SqlParameter> getNangLucDvhtSqlParameters(NangLucDvhtDto dto) {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_nangluc_dvht_id", dto.getNanglucDvhtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dichvuht_id", dto.getDichvuhtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_corecpu", dto.getSlCorecpu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_ram", dto.getDlRam(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaiht_id", dto.getLoaihtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hatanglttd_id", dto.getHatanglttdId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", dto.getThietbiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vungdl_id", dto.getVungdlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaiocung_id", dto.getLoaiocungId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_storage_cp", dto.getDlStorageCp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_storage", dto.getDlStorage(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_iops_id", dto.getIopsId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_throughput_id", dto.getThroughputId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mota", dto.getMota(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        return params;
    }
}
