package vn.vnpt.vnptit.ecms.service.idc;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.idc.*;
import vn.vnpt.common.converter.JsonConvert;

@Service
public class QuanLyThietBiIDCService {

    @Autowired
    private DbContext dbContext;

    public ArrayList<Map<String, Object>> getDanhSachThietBi() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_donvi_qly_id", AppRequestContext.getCurrent().getDonViID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_THIETBI", params);
    }

    public Object upsertThietBi(ThietBiUpsertDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String p_nguoi_cn = AppRequestContext.getCurrent().getUserName();
        String thanhPhanThietBiIdsJson;

        if (dto.getThanhPhanThietBiIds() == null || dto.getThanhPhanThietBiIds().isEmpty()) {
                thanhPhanThietBiIdsJson = null;
        }
        try {
                thanhPhanThietBiIdsJson = JsonConvert.serialize(dto.getThanhPhanThietBiIds());
        } catch (IOException e) {
                throw new RuntimeException("Error converting thanhPhanThietBiIds to JSON", e);
        }
        // ID - null for insert, provided for update
        params.add(new SqlParameter("p_id", dto.getId(), ParameterDirection.INPUT, Types.NUMERIC));

        // Basic information - mapping theo thứ tự trong SP
        params.add(new SqlParameter("p_ten_thietbi", dto.getTenThietBi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_donvi_ql_id", dto.getDonViQuanLyId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mang_thietbi_id", dto.getMangThietBiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_thietbi_id", dto.getLoaiThietBiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dong_sanpham", dto.getDongSanPham(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_serial_number", dto.getSerialNumber(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tag_number", dto.getTagNumber(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nam_sanxuat", dto.getNamSanXuat(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hang_sanxuat_id", dto.getHangSanXuatId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_noi_sanxuat_id", dto.getNoiSanXuatId(), ParameterDirection.INPUT, Types.NUMERIC));

        // Technical specifications
        params.add(new SqlParameter("p_congsuat_thietke", dto.getCongSuatThietKe(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_so_cong_nguon_dauvao", dto.getSoCongNguonDauVao(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_so_cong_nguon_daura", dto.getSoCongNguonDauRa(), ParameterDirection.INPUT, Types.NUMERIC));

        // Status and management
        params.add(new SqlParameter("p_trangthai_taisan_id", dto.getTrangThaiTaiSanId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_vanhanh_id", dto.getTrangThaiVanHanhId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_thanhphan_thietbi_id", thanhPhanThietBiIdsJson, ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_thietbi_cha_id", dto.getThietBiChaId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_quanly_id", dto.getNguoiQuanLyId(), ParameterDirection.INPUT, Types.NUMERIC));

        // Date fields
        params.add(new SqlParameter("p_thoidiem_lapdat", dto.getThoiDiemLapDat(), ParameterDirection.INPUT, Types.TIMESTAMP));
        params.add(new SqlParameter("p_ngay_duavao_sudung", dto.getNgayDuaVaoSuDung(), ParameterDirection.INPUT, Types.TIMESTAMP));

        // System and location
        params.add(new SqlParameter("p_hethong_id", dto.getHeThongId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_lapdat_tai_idc_id", dto.getLapDatTaiIdcId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_toanha_id", dto.getToaNhaId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_matsan_id", dto.getMatSanId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phong_zone_id", dto.getPhongZoneId(), ParameterDirection.INPUT, Types.NUMERIC));

        // Rack information
        params.add(new SqlParameter("p_rack_id", dto.getRackId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_u_tren_id", dto.getUTrenId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_u_duoi_id", dto.getUDuoiId(), ParameterDirection.INPUT, Types.NUMERIC));

        // Asset information
        params.add(new SqlParameter("p_ma_the_taisan", dto.getMaTheTaiSan(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_icons", dto.getIcons(), ParameterDirection.INPUT, Types.VARCHAR));

        // User information
        params.add(new SqlParameter("p_nguoi_cn", p_nguoi_cn, ParameterDirection.INPUT, Types.VARCHAR));

        // Ports JSON data
        params.add(new SqlParameter("p_ports_json", dto.getPortsJson(), ParameterDirection.INPUT, Types.CLOB));

        // Result parameter
        SqlParameter result = new SqlParameter("p_result", null, ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_UPSERT_THIETBI_IDC", params);
        return result.getOutValue();
    }

    public boolean deleteThietBi(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String p_nguoi_cn = AppRequestContext.getCurrent().getUserName();

        params.add(new SqlParameter("p_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", p_nguoi_cn, ParameterDirection.INPUT, Types.VARCHAR));

        SqlParameter result = new SqlParameter("p_result", null, ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_DELETE_THIETBI_IDC", params);
        return "1".equals(String.valueOf(result.getOutValue()));
    }

    public Map<String, Object> getChiTietThietBi(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        ArrayList<Map<String, Object>> list = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_CHITIET_THIETBI", params);
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    public ArrayList<Map<String, Object>> searchThietBi(JsonNode searchJsonData) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String jsonString = searchJsonData != null ? searchJsonData.toString() : null;
        params.add(new SqlParameter("p_search_json", jsonString, ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_SEARCH_THIETBI_JSON", params);
    }

    public ArrayList<Map<String, Object>> getDanhSachPortTheoThietBi(Long thietBiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", thietBiId, ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_PORT_BY_TB", params);
    }
    // ========== CÁC CHỨC NĂNG HỆ THỐNG THIẾT BỊ IDC ==========

    public ArrayList<Map<String, Object>> getDanhSachHeThongThietBi(HeThongThietBiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_TEN_HT", dto.getTen(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAI_VANHANH", dto.getTrangthaiVhId() != null ? dto.getTrangthaiVhId().toString() : null, ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IDC", dto.getIdcId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_QL", dto.getDonviQlId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_QL", dto.getNhanvienQlId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_THIETBI_IDC.SP_GET_DEVICE_SYSTEM_IDC", params);
    }

    // ========== CÁC CHỨC NĂNG TÁC NGHIỆP THIẾT BỊ IDC ==========

    public Object themThiCongHeThongIDC(ThiCongHeThongDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHIEUTC_ID", dto.getPhieuThiCongId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HETHONGTBI_ID", dto.getHeThongTbiId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_ID_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_INSERT_CONSTRUCTION_FORM_SYSTEM",
                params);
    }

    public Object themThiCongThietBiIDC(ThiCongThietBiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHIEUTC_ID", dto.getPhieuThiCongId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_THICONGHT_ID", dto.getThiCongHeThongId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_ID_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_IDC.SP_INSERT_CONSTRUCTION_FORM_DEVICE",
                params);
    }

    public Object layDanhSachThiCongHeThongIDC(ThiCongHeThongDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHIEUTC_ID", dto.getPhieuThiCongId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_IDC.SP_GET_THICONG_SYSTEM_IDC",
                params);
    }

    public Object layDanhSachThiCongThietBiIDC(ThiCongThietBiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_THICONGHT_ID", dto.getThiCongHeThongId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_IDC.SP_GET_THICONG_DEVICE_IDC",
                params);
    }

    public Object layDanhSachThietBiTheoHeThongIDC(Integer heThongTbiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_HETHONGTBI_ID", heThongTbiId,
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_IDC.SP_GET_DEVICES_BY_SYSTEM_IDC",
                params);
    }

    public Object chuyenPhieuThiCongIDC(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHIEU_THICONG_ID", dto.getPhieuThiCongId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangThaiId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", dto.getNhanVienId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_TRANSFER_PROCESSING",
                params);
    }

    public Object traPhieuThiCongIDC(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHIEU_THICONG_ID", dto.getPhieuThiCongId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangThaiId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getUserName(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_RETURN_PROCESSING",
                params);
    }

    public Object hoanThanhPhieuThiCongIDC(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHIEU_THICONG_ID", dto.getPhieuThiCongId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangThaiId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getUserName(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_COMPLETE_PROCESSING",
                params);
    }

    public Object kiemTraThietBiDaLapDatIDC(ThietBiKhachHangDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiKHId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_CHECK_INSTALL_DEVICE",
                params);
    }

    public Object kiemTraUnitKhaDungIDC(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_IDC_RACK_ID", dto.getRackId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_UNIT_UP_POS", dto.getUpUnitPos(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_UNIT_DOWN_POS", dto.getDownUnitPos(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_CHECK_UNIT_AVAILABLE",
                params);
    }

    public Object goThietBiIDC(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_REMOVE_DEVICE_FROM_RACK",
                params);
    }

    public Object thayDoiUnitThietBiIDC(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IDC_RACK_ID", dto.getRackId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_UNIT_UP_POS", dto.getUpUnitPos(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_UNIT_DOWN_POS", dto.getDownUnitPos(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_CHANGE_DEVICE_RACK_POSITION",
                params);
    }

    public Object lapDatThietBiIDC(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IDC_RACK_ID", dto.getRackId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_UNIT_UP_POS", dto.getUpUnitPos(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_UNIT_DOWN_POS", dto.getDownUnitPos(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        java.sql.Timestamp pThoiDiemLapDat = dto.getThoiDiemLapDat() != null
                ? new java.sql.Timestamp(dto.getThoiDiemLapDat().getTime())
                : null;
        params.add(new SqlParameter("P_THOIDIEM_LAPDAT", pThoiDiemLapDat,
                SqlParameter.ParameterDirection.INPUT, Types.TIMESTAMP));
        params.add(new SqlParameter("P_MATHE_TAISAN", dto.getMaTheTaiSan(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_INSTALL_DEVICE_TO_RACK",
                params);
    }

    /**
     * Import danh sách thiết bị từ file
     * @param thietBiList Danh sách thiết bị dạng Map<String, Object>
     * @return Kết quả import
     */
    public Object importThietBi(List<Map<String, Object>> thietBiList) throws AppSqlException {
        if (thietBiList == null || thietBiList.isEmpty()) {
            throw new AppSqlException("Danh sách thiết bị không được rỗng");
        }

        // Thêm thông tin người cập nhật vào mỗi record
        String nguoiCn = AppRequestContext.getCurrent().getUserName();
        for (Map<String, Object> thietBi : thietBiList) {
            thietBi.put("NGUOI_CN", nguoiCn);
            // Đảm bảo không có ID (chỉ insert)
            thietBi.remove("ID");
        }

        // Convert danh sách sang JSON
        String jsonData;
        try {
            jsonData = JsonConvert.serialize(thietBiList);
        } catch (IOException e) {
            throw new AppSqlException("Lỗi khi chuyển đổi dữ liệu sang JSON: " + e.getMessage());
        }

        // Gọi stored procedure
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_data_json", jsonData, ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_nguoi_cn", nguoiCn, ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_IMPORT_THIETBI", params);
    }
}
