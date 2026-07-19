package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.HaTangMangDtoIn;
import vn.vnpt.vnptit.ecms.dto.hatang.KhoHaTangIDGThietBiDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.KhoHaTangMangThietBiDTO;

@Service
public class HaTangMangService {
    private final DbContext dbContext;

    public HaTangMangService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getData() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT,
                java.sql.Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ecms.pkg_hatang_tainguyen_aohoa.sp_hatangmang_danhsach", param);
    }

    public ResponseEntity<ApiResult> capNhatHaTangMang(HaTangMangDtoIn input) {
        ApiResult rs = new ApiResult();
        String msg = "";
        if (input.getTen().isEmpty()) {
            msg += ", Tên chưa nhập";
        }

        if (!msg.isEmpty()) {
            rs.setErrorCode("BSS-00009490");
            rs.setMessage(msg.substring(2));
            return rs.getResponseEntity();
        }

        byte[] fileAvatar = null;
        try {
            fileAvatar = Base64.getDecoder().decode(input.getAvatar());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ten", input.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_mahatang_infra", input.getMaHaTang(), SqlParameter.ParameterDirection.INPUT,
                    Types.VARCHAR));
            params.add(new SqlParameter("p_idc_id", input.getIdcId(), SqlParameter.ParameterDirection.INPUT,
                    Types.NUMERIC));
            params.add(new SqlParameter("p_hatang_congnghe_id", input.getCongNgheId(),
                    SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_avatar", fileAvatar, SqlParameter.ParameterDirection.INPUT, Types.BLOB));
            params.add(new SqlParameter("p_ghi_chu", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT,
                    Types.VARCHAR));
            params.add(new SqlParameter("p_hieu_luc", input.getHieuLuc(), SqlParameter.ParameterDirection.INPUT,
                    Types.NUMERIC));
            params.add(new SqlParameter("p_nguoiqly_id", input.getNguoiQlyId(), SqlParameter.ParameterDirection.INPUT,
                    Types.NUMERIC));
            params.add(new SqlParameter("p_donviqly_id", input.getDonViQlyId(), SqlParameter.ParameterDirection.INPUT,
                    Types.NUMERIC));
            params.add(new SqlParameter("p_phongbanqly_id", input.getPhongBanQlyId(),
                    SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

            params.add(new SqlParameter("p_nvcn", AppRequestContext.getCurrent().getNhanVienID(),
                    SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ma_nd", AppRequestContext.getCurrent().getUserName(),
                    SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ipcn", AppRequestContext.getCurrent().getClientName(),
                    SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            SqlParameter result = new SqlParameter("p_result", 0, SqlParameter.ParameterDirection.OUTPUT,
                    Types.NUMERIC);
            params.add(result);
            dbContext.setConnection(ConnectionManager.Connections.CSS);
            dbContext.executeStoredProcedure("ecms.pkg_hatang_tainguyen_aohoa.sp_hatangmang_capnhat", params);
            int kq = Integer.parseInt(result.getOutValue().toString());
            if (kq == 1) {
                rs.setMessage("Cập nhật thành công!");
            } else if (kq == 2) {
                rs.setErrorCode("BSS-00009490");
                rs.setMessage(String.format("Tên [%s] đã tồn tại vì vậy bạn không thể cập nhật!", input.getTen()));
            } else {
                rs.setMessage("Cập nhật không thành công");
                rs.setErrorCode("BSS-00009490");
            }
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();

    }

    public ResponseEntity<ApiResult> xoaHaTangMang(HaTangMangDtoIn input) {
        ApiResult rs = new ApiResult();
        if (input.getId() == null || input.getId() < 1) {
            rs.setErrorCode("BSS-00009490");
            rs.setMessage("Chưa nhập số id để xóa dữ liệu !");
            return rs.getResponseEntity();
        }

        try {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nvcn", AppRequestContext.getCurrent().getNhanVienID(),
                    SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ma_nd", AppRequestContext.getCurrent().getUserName(),
                    SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ipcn", AppRequestContext.getCurrent().getClientName(),
                    SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            SqlParameter result = new SqlParameter("p_result", 0, SqlParameter.ParameterDirection.OUTPUT,
                    Types.NUMERIC);
            params.add(result);
            dbContext.setConnection(ConnectionManager.Connections.CSS);
            dbContext.executeStoredProcedure("ecms.pkg_hatang_tainguyen_aohoa.sp_hatangmang_delete", params);
            int kq = Integer.parseInt(result.getOutValue().toString());
            if (kq == 1) {
                rs.setMessage("Xóa thành công!");
            } else {
                rs.setMessage("Xóa không thành công");
                rs.setErrorCode("BSS_00004002");
            }
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    public Object getHaTangMangSuDung(String loaiDanhMuc, Long id) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(
                new SqlParameter("p_loai_danhmuc", loaiDanhMuc, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.pkg_hatang_tainguyen_aohoa.sp_hatangmang_sudung", params);
    }

    public Object getHaTangMangById(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ecms.pkg_hatang_tainguyen_aohoa.sp_hatangmang_theo_id", params);
    }

    public Object getHaTangMang(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ecms.pkg_hatang_tainguyen_aohoa.sp_get_hatangmang", params);
    }

    public ArrayList<Map<String, Object>> haTangMangThietBi(String input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_hatang_id", input, Types.NUMERIC));
        parameters.add(
                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.pkg_hatang_tainguyen_aohoa.SP_GET_THIETBI_HATANGMANG",
                parameters);
    }

    public ArrayList<Map<String, Object>> haTangMangThietBiThem(KhoHaTangMangThietBiDTO input) throws AppSqlException {
        // Validate input
        if (input == null || input.getThietBiHinhThanhList() == null || input.getThietBiHinhThanhList().isEmpty()) {
            throw new IllegalArgumentException("ThietBiHinhThanhList cannot be null or empty");
        }

        input.getThietBiHinhThanhList().forEach(item -> {
            List<SqlParameter> parameters = new ArrayList<>();
            parameters.add(new SqlParameter("p_hatang_id", item.getId(), Types.NUMERIC));
            parameters.add(new SqlParameter("p_thietbi_id", item.getThietbi_id(), Types.NUMERIC));
            parameters.add(
                    new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
            parameters.add(new SqlParameter("p_ngay_cn", item.getNgay_cn(), Types.NUMERIC));

            parameters.add(new SqlParameter("p_result", Types.NUMERIC));
            try {
                dbContext.executeStoredProcedure(
                        "ECMS.pkg_hatang_tainguyen_aohoa.SP_THIETBI_HINHTHANH_HATANGMANG_THIETBI_INSERT",
                        parameters);
            } catch (AppSqlException e) {
                throw new RuntimeException(e);
            }
        });
        return haTangMangThietBi(String.valueOf(input.getThietBiHinhThanhList().get(0).getId()));
    }

    public Object deleteThietBiHaTangMang(String id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.NUMERIC));
        dbContext.executeSpWithCursorToListMap("ECMS.pkg_hatang_tainguyen_aohoa.SP_HATANGMANG_THIETBI_DELETE", params);
        return Boolean.TRUE;
    }
}
