package vn.vnpt.vnptit.qlsc.service.vattu;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.vattu.VatTuCongTrinhV2DtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CapPhatVatTuService {
  private final DbContext dbContext;
  private static final String PKG_NAME = "QLSC.PKG_SUCO_VATTU";

  public CapPhatVatTuService(DbContext dbContext) {
    this.dbContext = dbContext;
  }

  public Object dsKhoNet(Long is_net) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
    params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
            SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),
            SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_is_net", is_net,
            SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    return dbContext.executeSpWithCursorToListMap("QLSC.PKG_CAP_VATTU_SC.sp_lay_ds_kho_net", params);
  }

  public String ghiLaiCapPhatVatTu(VatTuCongTrinhV2DtoIn vatTuCongTrinhV2DtoIn) throws AppSqlException, JsonProcessingException {

    ArrayList<SqlParameter> params = new ArrayList<>();
    params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_suco_id", vatTuCongTrinhV2DtoIn.getSuCoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_phieu_id", vatTuCongTrinhV2DtoIn.getPhieuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_congtrinh_id", vatTuCongTrinhV2DtoIn.getCongTrinhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_mucdich_id", vatTuCongTrinhV2DtoIn.getMucDichId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_lydocap_id", vatTuCongTrinhV2DtoIn.getLyDoCapId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_tinhtrang_id", vatTuCongTrinhV2DtoIn.getTinhTrangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_trangbi_id", vatTuCongTrinhV2DtoIn.getTrangBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_nhanvien_id", vatTuCongTrinhV2DtoIn.getNhanVienId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_donvi_id", vatTuCongTrinhV2DtoIn.getDonViId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_nguoi_cn", vatTuCongTrinhV2DtoIn.getNguoiCn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_ip_cn", vatTuCongTrinhV2DtoIn.getIpCn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_may_cn", vatTuCongTrinhV2DtoIn.getMayCn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_ngay_cap", vatTuCongTrinhV2DtoIn.getNgayCap(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_ghichu", vatTuCongTrinhV2DtoIn.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_ds_congtrinh", new ObjectMapper().writeValueAsString(
            vatTuCongTrinhV2DtoIn.getDsCongTrinh()), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_ds_update", new ObjectMapper().writeValueAsString(
            vatTuCongTrinhV2DtoIn.getDsUpdateVatTu()), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

    List<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap(
            "QLSC.PKG_CAP_VATTU_SC.sp_ghi_lai_cap_phat_vattu_v2", params);

    if (result != null && !result.isEmpty()) {
      return String.valueOf(result.get(0).values().iterator().next());
    }

    return null;
  }

  public Object xoaVattuDaCap(Long phieuvtId, Long tuvtId) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
    params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
            SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(),
            SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_phieuvt_id", phieuvtId,
            SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_tuvt_id", tuvtId,
            SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    return dbContext.executeSpWithCursorToListMap("QLSC.PKG_CAP_VATTU_SC.sp_xoa_vattu_dacap", params);
  }
}
