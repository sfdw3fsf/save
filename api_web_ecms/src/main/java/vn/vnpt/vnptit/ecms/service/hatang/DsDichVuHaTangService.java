package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchDvhtDTO;
import vn.vnpt.vnptit.ecms.dto.idc.DongBoDichvuHtDto;
import vn.vnpt.vnptit.ecms.service.idc.DichvuHtService;
import vn.vnpt.vnptit.ecms.service.idc.HaTangCNTTService;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DsDichVuHaTangService {
    private final DbContext dbContext;
    private final DichvuHtService dichvuHtService;
    private final HaTangCNTTService haTangCNTTService;

    public DsDichVuHaTangService(DbContext dbContext, DichvuHtService dichvuHtService, HaTangCNTTService haTangCNTTService) {
        this.dbContext = dbContext;
        this.dichvuHtService = dichvuHtService;
        this.haTangCNTTService = haTangCNTTService;
    }

    public List<Map<String, Object>> getDsach(SearchDvhtDTO input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_dich_vu", input.getMaDichVu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ten_dich_vu", input.getTenDichVu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", input.getHieuLuc(), Types.NUMERIC));
        params.add(new SqlParameter("p_tinh_phi", input.getTinhPhi(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_id", input.getTrangThaiId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_cap_tu", input.getNgayCapTu(), Types.DATE));
        params.add(new SqlParameter("p_ngay_cap_den", input.getNgayCapDen(), Types.DATE));
        params.add(new SqlParameter("p_ngay_hh_tu", input.getNgayHetHanTu(), Types.DATE));
        params.add(new SqlParameter("p_ngay_hh_den", input.getNgayHetHanDen(), Types.DATE));
        params.add(new SqlParameter("p_idc_id", input.getIdcId(), Types.NUMERIC));
        params.add(new SqlParameter("p_cum_ht_id", input.getCumHtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_loai_dv_id", input.getLoaiDvId(), Types.NUMERIC));
        params.add(new SqlParameter("p_doi_tuong_id", input.getDoiTuongId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten_spdv_id", input.getTenSpdvId(), Types.NUMERIC));
        params.add(new SqlParameter("p_cum_spdv_id", input.getCumSpdvId(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_cq_id", input.getDonViChuQuanId(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_ql_id", input.getDonViQuanLyId(), Types.NUMERIC));
        params.add(new SqlParameter("p_canhan_ql", input.getCaNhanQuanLy(), Types.NVARCHAR));
        params.add(new SqlParameter("p_loaiht_id", input.getLoaiHtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_user_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        params.add(new SqlParameter("p_is_to_truong", haTangCNTTService.checkIsToTruong() ? 1 : 0,  Types.NUMERIC));
        params.add(new SqlParameter("p_check_quyen", input.getCheckQuyen(), Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));
        List<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_NHDU.SP_DS_DICHVU_HATANG_GET_DSACH", params);
        return dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
    }

    public Boolean delete(Number dichVuId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dichVuId, Types.NUMERIC));
        SqlParameter rs = new SqlParameter("p_result", Types.NUMERIC);
        params.add(rs);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_NHDU.SP_DS_DICHVU_HATANG_DELETE", params);
        return rs.getOutValue().toString().equals("1");
    }

    public Number sync(DongBoDichvuHtDto dto) throws AppSqlException {
        return dichvuHtService.dongBoDichvuHt(dto);
    }
}
