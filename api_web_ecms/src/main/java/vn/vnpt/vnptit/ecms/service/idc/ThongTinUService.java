package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.*;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ThongTinUService {

  private final DbContext dbContext;

  public ThongTinUService(DbContext dbContext) {
    this.dbContext = dbContext;
  }

  public Object getListRackUnitByRackId(int rackUnitId) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<>();

    params.add(new SqlParameter("p_rack_id", rackUnitId, SqlParameter.ParameterDirection.INPUT, Types.INTEGER));
    params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLX.get_rackunit_list", params);

    return result;
  }

  public void updateRackUnit(RackUnitDTO rackUnit) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_rackunit_id", rackUnit.getRackUnitId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_trangthai_id", rackUnit.getTrangthaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    SqlParameter result = new SqlParameter("p_result", 0, Types.NUMERIC);
    params.add(result);
    dbContext.setConnection(ConnectionManager.Connections.CSS);
    dbContext.executeStoredProcedure("ECMS.PKG_TEST_THUANLX.sp_update_rackunit", params);
  }

  public Object idcList(int donviId) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_donvi_id", donviId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLX.SP_GET_IDC_BY_DONVI", params);
    return result;
  }

  public Object getThietbiFromU(int uId) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_donvi_id", uId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLX.SP_GET_THIETBI_FROM_U", params);
    return result;
  }

  public Object getThietbi(int phieutc_id) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_phieutc_id", phieutc_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLX.SP_GET_THIETBI_BY_PHIEUTC", params);
    return result;
  }

  public Object getThietbiPort(int thietbi_id) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_thietbi_id", thietbi_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLX.SP_GET_PORT_BY_THIETBI", params);
    return result;
  }

  public Object getIdc() throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLX.SP_GET_IDC_LIST", params);
    return result;
  }

  public Object getMatSan() throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLX.SP_GET_MATSAN_LIST", params);
    return result;
  }

  public Object getPhieutc(int phieutc_id) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_phieutc_id", phieutc_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToEntity("ECMS.PKG_TEST_THUANLX.SP_GET_PHIEUTC_BY_ID", params);
    return result;
  }

  public Object getPhieutcKenhban(int phieutc_id) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("phieutc_id", phieutc_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToEntity("ECMS.PKG_TEST_THUANLX.SP_GET_PHIEUTC_KENHBAN_BY_PHIEUTC", params);
    return result;
  }

  public Object getDsKetquaThicongBangthong(String loaiKq) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_loai_kq", loaiKq, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLX.SP_GET_DS_KETQUA_THICONG_BANGTHONG", params);
    return result;
  }


  public Object getDsThicongBangthongThuhoi(int trangthai) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_trangthai_id", trangthai, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLX.SP_GET_DS_THICONG_BANGTHONG_THUHOI", params);
    return result;
  }

  public Object getDsNhanvienDonvi(int donvi_id) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_donvi_id", donvi_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLX.SP_GET_DS_NHANVIEN_DONVI", params);
    return result;
  }

  public Object getDsThicongBangthong(int trangthai_id, int phieutcId) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

    params.add(new SqlParameter("p_trangthai_id", trangthai_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_phieutc_id", phieutcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    Object result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_TEST_THUANLX.SP_GET_DS_THICONG_BANGTHONG", params);
    return result;
  }
  public List<Object> createThiCongBangThong(List<ThiCongBangThongDto> dtoList, String loaiAction, int phieutc) throws AppSqlException {
    List<Object> results = new ArrayList<>();
    if (loaiAction.equals("CP")) {
      if (dtoList == null || dtoList.isEmpty()) {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_thicong_id",phieutc , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thicong_bt_id", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        Object result = dbContext.executeSpWithCursorToEntity(
                "ECMS.PKG_TEST_THUANLX.SP_DELETE_THICONG_BT",
                params
        );
      } else {
        ArrayList<LinkedHashMap> dsBtm = (ArrayList<LinkedHashMap>) getDsThicongBangthong(0, dtoList.get(0).getPhieutcId());

        // Lấy danh sách ID từ API (dtoList)
        Set<Long> dtoIds = dtoList.stream()
                .filter(dto -> !"Tự sinh".equals(dto.getThicongBtId())) // bỏ qua "Tự sinh"
                .map(dto -> Long.valueOf(dto.getThicongBtId()))
                .collect(Collectors.toSet());

        // Lặp qua danh sách DB (dsBtm)
        for (LinkedHashMap entity : dsBtm) {
          Long id = ((BigDecimal) entity.get("THICONGBT_ID")).longValue();
          if (!dtoIds.contains(id)) {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_thicong_id",phieutc , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_thicong_bt_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

            Object result = dbContext.executeSpWithCursorToEntity(
                    "ECMS.PKG_TEST_THUANLX.SP_DELETE_THICONG_BT",
                    params
            );
          }
        }
      }

    } else if (loaiAction.equals("TH")) {
      if (dtoList == null || dtoList.isEmpty()) {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_thicong_id",phieutc , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thicong_bt_id", 0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        Object result = dbContext.executeSpWithCursorToEntity(
                "ECMS.PKG_TEST_THUANLX.SP_DELETE_THICONG_BT",
                params
        );
      } else {
        ArrayList<LinkedHashMap> dsBtm = (ArrayList<LinkedHashMap>) getDsThicongBangthong(0, dtoList.get(0).getPhieutcId());

        // Lấy danh sách ID từ API (dtoList)
        Set<Long> dtoIds = dtoList.stream()
                .filter(dto -> !"Tự sinh".equals(dto.getPhieutcBtId())) // bỏ qua "Tự sinh"
                .map(dto -> Long.valueOf(dto.getPhieutcBtId()))
                .collect(Collectors.toSet());

        // Lặp qua danh sách DB (dsBtm)
        for (LinkedHashMap entity : dsBtm) {
          Long id = ((BigDecimal) entity.get("THICONGBT_ID")).longValue();
          if (!dtoIds.contains(id)) {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_thicong_id", phieutc, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_thicong_bt_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

            Object result = dbContext.executeSpWithCursorToEntity(
                    "ECMS.PKG_TEST_THUANLX.SP_DELETE_THICONG_BT",
                    params
            );
          }
        }
      }
    }

    for (ThiCongBangThongDto dto : dtoList) {
      ArrayList<SqlParameter> params = new ArrayList<>();

      params.add(new SqlParameter("p_phieutc_bt_id", dto.getPhieutcBtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_phieutc_id", dto.getPhieutcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_idc_id", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_matsan_id", dto.getMatSanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_goicuoc_id", dto.getGoiCuocId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_quocte", dto.getQuocTe(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
      params.add(new SqlParameter("p_trongnuoc", dto.getTrongNuoc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
      params.add(new SqlParameter("p_ghichu", dto.getTrongNuoc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
      params.add(new SqlParameter("p_thietbimang_id", dto.getThietbimangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_port_id", dto.getThietbiportId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_nhanvien_id", 24, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_nguoi_cn", dto.getNguoiCn(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

      params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

      Object result = dbContext.executeSpWithCursorToEntity(
              "ECMS.PKG_TEST_THUANLX.SP_SAVE_THICONG_BANGTHONG",
              params
      );
      results.add(result);
    }
    return results;
  }

  public Object updateNguoiXuLy(int phieutcId, String nguoiXl, String nguoiCn) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<>();
    params.add(new SqlParameter("p_phieutc_id", phieutcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_nguoi_xl", nguoiXl, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

    return dbContext.executeSpWithCursorToEntity("ECMS.PKG_TEST_THUANLX.SP_UPDATE_NGUOIXL_PHIEUTC", params);
  }

  public Object capNhatTrangThaiThiCongBt(List<CapNhatTrangThaiDto> items) throws AppSqlException {
    List<Object> results = new ArrayList<>();

    for (CapNhatTrangThaiDto dto : items) {
      ArrayList<SqlParameter> params = new ArrayList<>();
      params.add(new SqlParameter("p_thicongbt_id", dto.getThicongbtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_trangthai", dto.getTrangThai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_phieutc_id", dto.getPhieutcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
      params.add(new SqlParameter("p_nguoi_cn", dto.getNguoiCn(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

      params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

      Object result = dbContext.executeSpWithCursorToEntity(
              "ECMS.PKG_TEST_THUANLX.SP_UPDATE_TRANGTHAI_THICONG_BT",
              params
      );
      results.add(result);
    }

    return results; // trả về danh sách kết quả của tất cả items
  }

  public Object capNhatTrangThaiPhieuTc(int phieutcId, int trangThaiId, String nguoiCn) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<>();
    params.add(new SqlParameter("p_phieutc_id", phieutcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_trangthai_id", trangThaiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
    params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
    params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

    return dbContext.executeSpWithCursorToEntity("ECMS.PKG_TEST_THUANLX.SP_CAPNHAT_TRANGTHAI_PHIEUTC", params);
  }
  public Object timKiemCongViec(DanhSachCongViecDto dto) throws AppSqlException {
    ArrayList<SqlParameter> params = new ArrayList<>();

    params.add(new SqlParameter("P_LOAICV_ID", dto.getLoaicvId(), Types.NUMERIC));
    params.add(new SqlParameter("P_DONVI_ID", dto.getDonviId(), Types.NUMERIC));
    params.add(new SqlParameter("P_IDC_ID", dto.getIdcId(), Types.NUMERIC));
    params.add(new SqlParameter("P_NHANVIEN_ID", dto.getNhanvienId(), Types.NUMERIC));
    params.add(new SqlParameter("P_PHANLOAI", dto.getPhanloai(), Types.NUMERIC));
    params.add(new SqlParameter("P_YC_BATDAU", dto.getYcBatdau(), Types.DATE));
    params.add(new SqlParameter("P_YC_KETTHUC", dto.getYcKetthuc(), Types.DATE));
    params.add(new SqlParameter("P_YCHT_BATDAU", dto.getYchtBatdau(), Types.DATE));
    params.add(new SqlParameter("P_YCHT_KETTHUC", dto.getYchtKetthuc(), Types.DATE));
    params.add(new SqlParameter("P_KHACHHANG", dto.getKhachhang(), Types.VARCHAR));
    params.add(new SqlParameter("P_CONGVIEC", dto.getCongviec(), Types.VARCHAR));
    params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangthaiId(), Types.NUMERIC));
    params.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

    // gọi stored procedure
    return dbContext.executeSpWithCursorToListMap(
            "ECMS.PKG_TEST_THUANLX.SP_TIMKIEM_CONGVIEC_BTM",
            params
    );
  }


}
