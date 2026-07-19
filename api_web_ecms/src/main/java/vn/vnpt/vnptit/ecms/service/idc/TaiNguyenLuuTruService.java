package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.TaiNguyenLuuTruDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service("idcTaiNguyenLuuTruService")
public class TaiNguyenLuuTruService {
    private final DbContext dbContext;

    public TaiNguyenLuuTruService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getDetailTaiNguyenLt(Number tainguyenltId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_tainguyenlt_id", tainguyenltId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_pks", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_DETAIL_TAINGUYEN_LT", params);
    }

    public Map<String, Object> upsertTaiNguyenLt(TaiNguyenLuuTruDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = getSqlParameters(dto);
        params.add(new SqlParameter("p_tainguyenlt_id", dto.getTainguyenltId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_UPSERT_TAINGUYEN_LT", params);
    }

    public Map<String, Object> deleteTaiNguyenLt(Number tainguyenltId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_tainguyenlt_id", tainguyenltId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_DELETE_TAINGUYEN_LT", params);
    }

    private ArrayList<SqlParameter> getSqlParameters(TaiNguyenLuuTruDto dto) {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_tainguyen", dto.getMaTainguyen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_tainguyen", dto.getTenTainguyen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSudung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tinhphi", dto.getTinhphi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phieu_yc", dto.getPhieuYc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cumht_id", dto.getCumhtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaiht_id", dto.getLoaihtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_htlt", dto.getLoaiHtlt(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_loaitn_id", dto.getLoaitnId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_duongdan_kn", dto.getDuongdanKn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_cap", dto.getNgayCap(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_ngay_hh", dto.getNgayHh(), SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_donvi_ql_id", dto.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phong_ql_id", dto.getPhongQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_ql_id", dto.getNhanvienQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_cq_id", dto.getDonviCqId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaiocung_id", dto.getLoaiocungId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mota", dto.getMota(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        return params;
    }
}
