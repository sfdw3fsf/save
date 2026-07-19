package vn.vnpt.vnptit.ecms.service.idc;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.*;

@Service
public class ThongTinRackService {
    private final DbContext dbContext;

    public ThongTinRackService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Map<String, Object> upsertRack(ThongTinRackDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_rack_id", dto.getRackId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_rack_id", dto.getLoaiRackId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phong_id", dto.getPhongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_longquay_id", dto.getLongquayId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vitri_rack", dto.getVitriRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_rack", dto.getTenRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tentat", dto.getTenTat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma_ts", dto.getMaTaiSan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_quyhoach", dto.getQuyHoach(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vanhanh", dto.getVanHanh(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ccdv", dto.getCcdv(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sudung", dto.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_chuquan_id", dto.getChuQuanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hangsx_id", dto.getHangSXId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mucdichsd_id", dto.getMucDichSuDungId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_camera", dto.getCamera(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_huong", dto.getHuong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_sd", dto.getNgaySD(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hinh_anh", dto.getHinhAnh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_donviql", dto.getDonviQl(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phongql", dto.getPhongQl(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoiql_ids", dto.getNguoiQlIds(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma_duan", dto.getMaDuAn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_duan", dto.getTenDuAn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_qlts_id", dto.getQltsId(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_the_ts", dto.getTheTaisan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TNKG_RACK.SP_UPSERT_RACK", params);
    }

    public Object getDanhmucRack(String loaiDanhmuc, Number thamSo1, Number thamSo2) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(
                new SqlParameter("p_loai_danhmuc", loaiDanhmuc, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tham_so1", thamSo1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tham_so2", thamSo2, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TNKG_RACK.SP_GET_DANHMUC_RACK", params);
    }

    public ArrayList<Map<String, Object>> getThongTinRack() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TNKG_RACK.SP_GET_TT_RACK", params);
    }

    public Map<String, Object> deleteRack(Number id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TNKG_RACK.SP_DELETE_RACK", params);
    }

    public ArrayList<Map<String, Object>> getAvailableRacks() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TUANLN.GET_AVAILABLE_RACKS", params);
    }

    public Map<String, Object> getRackById(Number rackId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rack_id", rackId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_RACK_BY_ID", params);
    }

    public ArrayList<Map<String, Object>> getRacksByRoomId(Number roomId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_room_id", roomId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_RACKS_BY_ROOM_ID", params);
    }

    public ArrayList<Map<String, Object>> getUByRoomId(Number roomId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_room_id", roomId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_U_BY_ROOM_ID", params);
    }

    public ArrayList<Map<String, Object>> getUByRackId(Number rackId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rack_id", rackId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_U_BY_RACK_ID", params);
    }

    public Map<String, Object> getFrontBackInfo(Number rackId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rack_id", rackId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_FRONT_BACK_INFO", params);
    }

    public ArrayList<Map<String, Object>> getU2MatTheoRack(Number rackId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rack_id", rackId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_U_2MAT_THEO_RACK", params);
    }

    /**
     * Import nhiều Rack từ file Excel (không dùng JSON).
     * Loop qua từng dòng và gọi SP để validate + insert.
     * 
     * @param danhSachRack Danh sách các dòng cần import
     * @return RackImportResponseDto chứa kết quả import
     */
    public RackImportResponseDto importRackRows(java.util.List<RackImportRowDto> danhSachRack) throws AppSqlException {
        RackImportResponseDto response = new RackImportResponseDto();
        String nguoiCn = AppRequestContext.getCurrent().getUserName();

        for (RackImportRowDto row : danhSachRack) {
            try {
                RackImportResponseDto.RackImportRowResult result = importSingleRackRow(row, nguoiCn);
                response.addResult(result);
                
                if (result.isSuccess()) {
                    response.addSuccess();
                } else {
                    response.addFailed();
                }
            } catch (Exception e) {
                // Nếu có exception, vẫn tiếp tục với dòng tiếp theo
                RackImportResponseDto.RackImportRowResult errorResult = 
                    new RackImportResponseDto.RackImportRowResult(
                        row.getStt(), 
                        "ERROR", 
                        "Lỗi hệ thống: " + e.getMessage(), 
                        null
                    );
                response.addResult(errorResult);
                response.addFailed();
            }
        }

        return response;
    }

    /**
     * Import một dòng Rack bằng cách gọi SP_IMPORT_RACK_ROW.
     * SP sẽ thực hiện mapping tên → ID và validate dữ liệu.
     */
    private RackImportResponseDto.RackImportRowResult importSingleRackRow(RackImportRowDto row, String nguoiCn) 
            throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        // Input parameters
        params.add(new SqlParameter("p_stt", row.getStt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten_rack", row.getTenRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tentat", row.getTenTat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_loai_rack", row.getTenLoaiRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_vanhanh", row.getTenVanHanh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_quyhoach", row.getTenQuyHoach(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_ccdv", row.getTenCcdv(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_chuquan", row.getTenChuQuan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_hangsx", row.getTenHangSX(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_donvi_ql", row.getTenDonViQl(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_phong_ql", row.getTenPhongQl(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_taisan_id", row.getTaiSanId(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_camera", row.getCamera(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_huong", row.getHuong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_sd", row.getNgaySD(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", row.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", row.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma_duan", row.getMaDuAn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_duan", row.getTenDuAn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_the_ts", row.getTheTs(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", nguoiCn, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        // Output parameters
        params.add(new SqlParameter("p_status", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_message", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rack_id", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        // Execute SP
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TNKG_RACK.SP_IMPORT_RACK_ROW", params);

        // Get output values
        String status = null;
        String message = null;
        Number rackId = null;

        for (SqlParameter param : params) {
            if ("p_status".equals(param.getName()) && param.getOutValue() != null) {
                status = String.valueOf(param.getOutValue());
            }
            if ("p_message".equals(param.getName()) && param.getOutValue() != null) {
                message = String.valueOf(param.getOutValue());
            }
            if ("p_rack_id".equals(param.getName()) && param.getOutValue() != null) {
                rackId = (Number) param.getOutValue();
            }
        }

        return new RackImportResponseDto.RackImportRowResult(row.getStt(), status, message, rackId);
    }

    /**
     * Validate nhiều Rack từ file Excel (không insert, chỉ kiểm tra).
     * Sử dụng cùng logic mapping tên → ID như khi import.
     * 
     * @param danhSachRack Danh sách các dòng cần validate
     * @return RackImportResponseDto chứa kết quả validate
     */
    public RackImportResponseDto validateImportRackRows(java.util.List<RackImportRowDto> danhSachRack) throws AppSqlException {
        RackImportResponseDto response = new RackImportResponseDto();

        for (RackImportRowDto row : danhSachRack) {
            try {
                RackImportResponseDto.RackImportRowResult result = validateSingleRackRow(row);
                response.addResult(result);
                
                if (result.isSuccess()) {
                    response.addSuccess();
                } else {
                    response.addFailed();
                }
            } catch (Exception e) {
                RackImportResponseDto.RackImportRowResult errorResult = 
                    new RackImportResponseDto.RackImportRowResult(
                        row.getStt(), 
                        "ERROR", 
                        "Lỗi hệ thống: " + e.getMessage(), 
                        null
                    );
                response.addResult(errorResult);
                response.addFailed();
            }
        }

        return response;
    }

    /**
     * Validate một dòng Rack bằng cách gọi SP_VALIDATE_IMPORT_RACK_ROW.
     * SP chỉ validate (mapping tên → ID), không insert vào DB.
     */
    private RackImportResponseDto.RackImportRowResult validateSingleRackRow(RackImportRowDto row) 
            throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        // Input parameters - giống hệt import nhưng gọi SP validate
        params.add(new SqlParameter("p_stt", row.getStt(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten_rack", row.getTenRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tentat", row.getTenTat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_loai_rack", row.getTenLoaiRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_vanhanh", row.getTenVanHanh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_quyhoach", row.getTenQuyHoach(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_ccdv", row.getTenCcdv(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_chuquan", row.getTenChuQuan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_hangsx", row.getTenHangSX(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_donvi_ql", row.getTenDonViQl(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_phong_ql", row.getTenPhongQl(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_taisan_id", row.getTaiSanId(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_camera", row.getCamera(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_huong", row.getHuong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_sd", row.getNgaySD(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", row.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", row.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma_duan", row.getMaDuAn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_duan", row.getTenDuAn(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_the_ts", row.getTheTs(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));


        // Output parameters
        params.add(new SqlParameter("p_status", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_message", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_rack_id", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

        // Execute SP - gọi SP validate thay vì import
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TNKG_RACK.SP_VALIDATE_IMPORT_RACK_ROW", params);

        // Get output values
        String status = null;
        String message = null;
        Number rackId = null;

        for (SqlParameter param : params) {
            if ("p_status".equals(param.getName()) && param.getOutValue() != null) {
                status = String.valueOf(param.getOutValue());
            }
            if ("p_message".equals(param.getName()) && param.getOutValue() != null) {
                message = String.valueOf(param.getOutValue());
            }
            if ("p_rack_id".equals(param.getName()) && param.getOutValue() != null) {
                rackId = (Number) param.getOutValue();
            }
        }

        return new RackImportResponseDto.RackImportRowResult(row.getStt(), status, message, rackId);
    }
}

