package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchPhieuYeuCauTnDto;
import vn.vnpt.vnptit.ecms.dto.hatang.UpsertNoidungTraodoiDto;
import vn.vnpt.vnptit.ecms.dto.idc.PhieuYcTaiNguyenDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Types;

@Service
public class PhieuYcTaiNguyenService {
    private final DbContext dbContext;

    public PhieuYcTaiNguyenService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getThongTinChung(Long phieuycId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieuyc_id", phieuycId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_PHIEUYC_TAINGUYEN_AOHOA.SP_GET_DETAIL_PHIEU_YC", params);
    }

    public Object upsertThongTinChung(PhieuYcTaiNguyenDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieuyc_id", dto.getPhieuycId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_cq_id", dto.getDonviCqId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_spdv_id", dto.getSpdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaiht_id", dto.getLoaihtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cumspdv_id", dto.getCumspdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_noidung", dto.getNoidung(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_loaiyc_id", dto.getLoaiycId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_trangthaiph_id", dto.getTrangthaiphId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_yc_id", dto.getNhanvienYcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_yc_id", dto.getDonviYcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_yc", dto.getNgayYc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_ht", dto.getNgayHt(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_file_dinhkem", dto.getFileDinhkem(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mota", dto.getMota(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSudung() != null ? dto.getSudung() : 1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        Map<String, Object> result = dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_PHIEUYC_TAINGUYEN_AOHOA.SP_UPSERT_PHIEU_YC", params);
        return result.get("item");
    }

    public Object getDsTaiNguyen(Long phieuycId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieuyc_id", phieuycId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_PHIEUYC_TAINGUYEN_AOHOA.SP_GET_DS_TAINGUYEN", params);
    }

    /**
     * Tìm kiếm danh sách phiếu yêu cầu tài nguyên.
     * Gọi stored procedure: SP_GET_DS_PHIEU_YEU_CAU_TN
     *
     * @param dto Điều kiện tìm kiếm
     * @return Danh sách phiếu yêu cầu tài nguyên
     */
    public Object search(SearchPhieuYeuCauTnDto dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_MA_PHIEU", dto.getMaPhieu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_NGUOI_YC", dto.getNguoiYc(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_DONVI_CQ_ID", dto.getDonViCqId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_SPDV_ID", dto.getSpdvId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_CUM_SPDV_ID", dto.getCumSpdvId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_LOAI_HT_ID", dto.getLoaiHtId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_NOI_DUNG", dto.getNoiDung(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_DONVI_YC_ID", dto.getDonViYcId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_LOAI_YC_ID", dto.getLoaiYcId() != null ? dto.getLoaiYcId() : 0L,
                ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_TRANG_THAI", dto.getTrangThai() != null ? dto.getTrangThai() : 0L,
                ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_HIEU_LUC", dto.getHieuLuc() != null ? dto.getHieuLuc() : 1L,
                ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_TINH_PHI", dto.getTinhPhi() != null ? dto.getTinhPhi() : 0L,
                ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_TU_NGAY_YC", dto.getTuNgayYc(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_DEN_NGAY_YC", dto.getDenNgayYc(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_TU_NGAY_HT", dto.getTuNgayHt(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_DEN_NGAY_HT", dto.getDenNgayHt(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_CURSOR", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_DS_PHIEU_YEU_CAU_TN", params);
    }

    /**
     * Lấy danh sách nội dung trao đổi theo phiếu yêu cầu (có phân trang).
     * Gọi stored procedure: SP_GET_NOIDUNG_TRAODOI_BY_PHIEU
     *
     * @param phieuYcId ID của phiếu yêu cầu
     * @param pageIndex Số trang (mặc định: 1)
     * @param pageSize  Số bản ghi mỗi trang (mặc định: 20)
     * @return Danh sách nội dung trao đổi
     */
    public Object getNoidungTraodoi(String phieuYcId, Long pageIndex, Long pageSize) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_PHIEUYC_ID", phieuYcId, ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_PAGE_INDEX", pageIndex != null ? pageIndex : 1L, ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_PAGE_SIZE", pageSize != null ? pageSize : 20L, ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("p_CURSOR", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_THI_AOHOA_TEST.SP_GET_NOIDUNG_TRAODOI_BY_PHIEU", params);
    }

    /**
     * Thêm mới hoặc cập nhật nội dung trao đổi theo phiếu yêu cầu.
     * Gọi stored procedure: SP_UPSERT_NOIDUNG_TRAODOI
     *
     * @param dto Dữ liệu upsert
     * @return Map chứa ndTraodoiId sau khi insert/update
     */
    public Map<String, Object> upsertNoidungTraodoi(UpsertNoidungTraodoiDto dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_ND_TRAODOI_ID", dto.getNdTraodoiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_PHIEUYC_ID", dto.getPhieuycId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_NOIDUNG", dto.getNoiDung(), ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_NHANVIEN_ID", dto.getNhanvienId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter outParam = new SqlParameter("p_OUT_ID", Types.NUMERIC);
        params.add(outParam);

        dbContext.executeStoredProcedure("ECMS.PKG_THI_AOHOA_TEST.SP_UPSERT_NOIDUNG_TRAODOI", params);

        Map<String, Object> result = new HashMap<>();
        result.put("ndTraodoiId", outParam.getOutValue());
        return result;
    }
}
