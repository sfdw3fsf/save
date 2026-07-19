package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.*;

import java.sql.Types;
import java.util.ArrayList;

@Service
public class CongSuatDienService {
    private final DbContext dbContext;

    public CongSuatDienService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getCongViecCongSuatDien(Long phieutc_id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", phieutc_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_IDC_CONGSUATDIEN.SP_GET_CONGVIEC_CONGSUATDIEN", params);
    }
    public Object getDsThietBiCapPhatDien(Long phieutc_id){
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", phieutc_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        try {
            return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_CONGSUATDIEN.SP_GET_THIETBI_BY_PHIEUTC", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }
    }
    public Object getDsThanhNguonOCam(Long rack_id){
        ArrayList <SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rack_id", rack_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        try {
            return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_CONGSUATDIEN.SP_GET_OCAM_THANHNGUON_BY_RACKID", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }
    }
    public ApiResult capphatCongSuatDien(CapPhatCongSuatDienDto dto)throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", dto.getPhieuTcId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ocam_id", dto.getOcamId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", dto.getNhanvienId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", dto.getThietbiId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_thanhnguon_id", dto.getThanhnguonId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_noidung", dto.getNoiDung(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_trangthai_id", dto.getTrangthaiId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        try {
            dbContext.executeStoredProcedure("ECMS.PKG_IDC_CONGSUATDIEN.SP_CAPPHAT_CONGSUATDIEN", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }

        ApiResult apiResult = new ApiResult();
        String errCodeVal = errorCode.getOutValue() != null ? (String) errorCode.getOutValue() : "BSS_00000000";
        String errMsgVal = errorMessage.getOutValue() != null ? errorMessage.getOutValue().toString() : "Unknown error";
        Number resultVal = result.getOutValue() != null ? (Number) result.getOutValue() : -1;

        apiResult.setErrorCode(errCodeVal);
        apiResult.setMessage(errMsgVal);
        apiResult.setData(resultVal);
        return apiResult;
    }
    public Object getKetQuaDsThietBiCapPhatDien(Long phieutc_id) throws AppSqlException{
        ArrayList <SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", phieutc_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        try {
            return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_CONGSUATDIEN.SP_GET_KQ_DSTB_CAPPHATDIEN", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }
    }
    public ApiResult updateTrangThaiPhieuTC(UpdatePhieuCongSuatDienDto dto) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", dto.getPhieuTcId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_id", dto.getTrangthaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_th_id", dto.getNguoiThucHienId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_lydotraphieu", dto.getLyDoTraPhieu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        try {
            dbContext.executeStoredProcedure("ECMS.PKG_IDC_CONGSUATDIEN.SP_UPDATE_PHIEUTC_CSD", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }

        ApiResult apiResult = new ApiResult();
        String errCodeVal = errorCode.getOutValue() != null ? (String) errorCode.getOutValue() : "BSS_00000000";
        String errMsgVal = errorMessage.getOutValue() != null ? errorMessage.getOutValue().toString() : "Unknown error";
        Number resultVal = result.getOutValue() != null ? (Number) result.getOutValue() : -1;

        apiResult.setErrorCode(errCodeVal);
        apiResult.setMessage(errMsgVal);
        apiResult.setData(resultVal);
        return apiResult;
    }
    //THU HOI DIEN
    public Object getDsThietBiThuHoiDien(Long phieutc_id) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", phieutc_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        try {
            return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_CONGSUATDIEN.SP_GET_DSTB_THUHOIDIEN", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }
    }
    public ApiResult thuHoiCongSuatDien(ThuHoiCongSuatDienDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", dto.getPhieuTcId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", dto.getThietbiId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ocam_id", dto.getOcamId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id",AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_noidung", dto.getNoiDung(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_trangthai_id", dto.getTrangthaiId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));

        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        try {
            dbContext.executeStoredProcedure("ECMS.PKG_IDC_CONGSUATDIEN.SP_THUHOI_CONGSUATDIEN", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }

        ApiResult apiResult = new ApiResult();
        String errCodeVal = errorCode.getOutValue() != null ? (String) errorCode.getOutValue() : "BSS_00000000";
        String errMsgVal = errorMessage.getOutValue() != null ? errorMessage.getOutValue().toString() : "Unknown error";
        Number resultVal = result.getOutValue() != null ? (Number) result.getOutValue() : -1;

        apiResult.setErrorCode(errCodeVal);
        apiResult.setMessage(errMsgVal);
        apiResult.setData(resultVal);
        return apiResult;
    }
    public Object getKetQuaDsThietBiThuHoiDien(Long phieutc_id) throws AppSqlException{
        ArrayList <SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", phieutc_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        try {
            return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_CONGSUATDIEN.SP_GET_KQ_DSTB_THUHOIDIEN", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }
    }
    //Chuyen doi dien
    public Object getDsThietBiChuyenDoiDien(Long phieutc_id) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", phieutc_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        try {
            return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_CONGSUATDIEN.SP_GET_DSTB_CHUYENDOIDIEN", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }
    }
    public ApiResult chuyenDoiCongSuatDien(ChuyenDoiCongSuatDienDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", dto.getPhieuTcId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", dto.getThietbiId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ocamcu_id", dto.getOcamCuId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ocammoi_id", dto.getOcamMoiId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_thanhnguon_id", dto.getThanhnguonId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_noidung", dto.getNoiDung(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_trangthai_id", dto.getTrangthaiId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));

        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        try {
            dbContext.executeStoredProcedure("ECMS.PKG_IDC_CONGSUATDIEN.SP_CHUYENDOI_CONGSUATDIEN", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }

        ApiResult apiResult = new ApiResult();
        String errCodeVal = errorCode.getOutValue() != null ? (String) errorCode.getOutValue() : "BSS_00000000";
        String errMsgVal = errorMessage.getOutValue() != null ? errorMessage.getOutValue().toString() : "Unknown error";
        Number resultVal = result.getOutValue() != null ? (Number) result.getOutValue() : -1;
        apiResult.setErrorCode(errCodeVal);
        apiResult.setMessage(errMsgVal);
        apiResult.setData(resultVal);
        return apiResult;
    }
    public Object getKetQuaDsThietBiChuyenDoiDien(Long phieutc_id) throws AppSqlException{
        ArrayList <SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", phieutc_id, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        try {
            return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_CONGSUATDIEN.SP_GET_KQ_DSTB_CHUYENDOIDIEN", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }
    }
    public Object getKetQuaCongSuatDienFiltered(SearchKetQuaCongSuatDienDto dto) throws AppSqlException{
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_loai_cv_id", dto.getLoaiCvId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_id", dto.getTrangThaiId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_th_id", dto.getNhanVienThucHienId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_khachhang", dto.getKhachHang(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_ngayyc_start", dto.getNgayYcStart(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.DATE));
        params.add(new SqlParameter("p_ngayyc_end", dto.getNgayYcEnd(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.DATE));
        params.add(new SqlParameter("p_ngayht_start", dto.getNgayHoanThanhStart(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.DATE));
        params.add(new SqlParameter("p_ngayht_end", dto.getNgayHoanThanhEnd(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.DATE));
        params.add(new SqlParameter("p_congviec", dto.getCongViec(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_idc", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi", dto.getThietBi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ocam_id", dto.getoCamId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        try {
            return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_CONGSUATDIEN.SP_GET_KETQUA_CSD_FILTERED", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }
    }
    public ApiResult deleteKetQuaCongViec(DeleteKetQuaCongSuatDien dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thicong_dien_id", dto.getThiCongDienId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_loai", dto.getLoai(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_thietbi_id", dto.getThietBiId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ocam_id", dto.getoCamId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ocam_cu_id", dto.getoCamIdCu(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_ocam_moi_id", dto.getoCamIdMoi(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));

        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        try {
            dbContext.executeStoredProcedure("ECMS.PKG_IDC_CONGSUATDIEN.SP_DELETE_THICONG_CONGSUATDIEN", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }

        ApiResult apiResult = new ApiResult();
        String errCodeVal = errorCode.getOutValue() != null ? (String) errorCode.getOutValue() : "BSS_00000000";
        String errMsgVal = errorMessage.getOutValue() != null ? errorMessage.getOutValue().toString() : "Unknown error";
        Number resultVal = result.getOutValue() != null ? (Number) result.getOutValue() : -1;
        apiResult.setErrorCode(errCodeVal);
        apiResult.setMessage(errMsgVal);
        apiResult.setData(resultVal);
        return apiResult;
    }
    public ApiResult selectThietBiCongSuatDien(SelectThietBiCongSuatDienDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieutc_id", dto.getPhieuTcId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", dto.getThietBiId(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvientao_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.NUMERIC);
        SqlParameter errorCode = new SqlParameter("p_err_code", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);
        SqlParameter errorMessage = new SqlParameter("p_err_msg", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.VARCHAR);

        params.add(result);
        params.add(errorCode);
        params.add(errorMessage);

        try {
            dbContext.executeStoredProcedure("ECMS.PKG_IDC_CONGSUATDIEN.SP_SELECT_THIETBI_CSD", params);
        } catch (AppSqlException e) {
            throw new RuntimeException(e);
        }

        ApiResult apiResult = new ApiResult();
        String errCodeVal = errorCode.getOutValue() != null ? (String) errorCode.getOutValue() : "BSS_00000000";
        String errMsgVal = errorMessage.getOutValue() != null ? errorMessage.getOutValue().toString() : "Unknown error";
        Number resultVal = result.getOutValue() != null ? (Number) result.getOutValue() : -1;
        apiResult.setErrorCode(errCodeVal);
        apiResult.setMessage(errMsgVal);
        apiResult.setData(resultVal);
        return apiResult;
    }
}