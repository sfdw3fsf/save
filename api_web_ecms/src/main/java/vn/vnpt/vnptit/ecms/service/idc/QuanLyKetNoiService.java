package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.idc.*;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class QuanLyKetNoiService {
    @Autowired
    private DbContext dbContext;

    public ArrayList<Map<String, Object>> getPhieuTCById(Integer idPhieuTc) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHIEUTC_ID", idPhieuTc, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_GET_PHIEUTC_BY_ID", params);
    }

    public ArrayList<Map<String, Object>> getKetNoiById(Integer idKetNoi, Integer idPhieuTc) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", idKetNoi, Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUTC_ID", idPhieuTc, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_GET_KETNOI_IDC_BY_ID", params);
    }

    // public ArrayList<Map<String, Object>> getKetNoiByPhieuTc(Integer idPhieuTc)
    // throws AppSqlException {
    // ArrayList<SqlParameter> params = new ArrayList<>();
    // params.add(new SqlParameter("P_PHIEUTC_ID", idPhieuTc, Types.NUMERIC));
    // params.add(new SqlParameter("P_RS", null,
    // SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
    //
    // return
    // dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_KETNOI_IDC_BY_PHIEUTC_ID",
    // params);
    // }

    public ArrayList<Map<String, Object>> getDanhSachKetNoi(Integer idLoaiCv) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_LOAICV", idLoaiCv, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_GET_KETNOI_IDC", params);
    }

    public Object deleteKetNoiIdc(DeleteKetNoiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHIEUTC_ID", dto.getIdPhieuTc(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DS_KETNOI_ID", dto.getDsIdKetNoi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter result = new SqlParameter("P_RESULT", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_DELETE_KETNOI_IDC", params);
        return result.getOutValue();
    }

    public Object saveKetNoiIdc(KetNoiDto ketNoiDto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        SqlParameter result  = new SqlParameter("P_RS", Types.NUMERIC);
        try {
            params.add(new SqlParameter("P_JSON", JsonConvert.serialize(ketNoiDto), ParameterDirection.INPUT,
                    Types.VARCHAR));
        } catch (IOException e) {
            throw new AppSqlException(e);
        }
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_SAVE_KETNOI_IDC", params);
        return result.getOutValue();
    }

    public ArrayList<Map<String, Object>> getKetNoiKenhBan(KetNoiKenhBanDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHIEUTC_ID", dto.getIdPhieuTc(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MA_TB", dto.getMaTb(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAI", dto.getTrangThaiPhieuYC(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAICV", dto.getLoaicv(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_GET_KETNOI_KENHBAN", params);
    }

    public ArrayList<Map<String, Object>> searchKetNoi(SearchKetNoiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_IS_KHACHHANG", dto.getIsKhachHang(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAICV", dto.getIdLoaiCv(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUTC_ID", dto.getIdPhieuTc(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", dto.getDonviId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IDC_ID", dto.getIdcId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("P_TRANGTHAI_VL_ID", dto.getTrangThaiVlId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("P_TRANGTHAI_VH_ID", dto.getTrangThaiVhId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOP_KN_ID", dto.getLopKnId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HINHTHUC_ID", dto.getHinhThucKn(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_THUCHIEN", dto.getNhanVienThucHien(), ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("P_HETHONG_NGUON", dto.getHeThongNguon(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HETHONG_DICH", dto.getHeThongDich(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGAYTAO_FROM", dto.getNgayTaoFrom(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_NGAYTAO_TO", dto.getNgayTaoTo(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_NGAY_HOANTHANH_FROM", dto.getNgayHoanThanhFrom(), ParameterDirection.INPUT,
                Types.DATE));
        params.add(new SqlParameter("P_NGAY_HOANTHANH_TO", dto.getNgayHoanThanhTo(), ParameterDirection.INPUT,
                Types.DATE));
        params.add(new SqlParameter("P_MA_TB", dto.getMaTb(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TEN_KHACHHANG", dto.getTenKhachHang(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAI_THICONG_KN", dto.getTrangThaiThiCongKn(), ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("P_PHUONG_THUC", dto.getPhuongThuc(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_SEARCH_KETNOI_IDC_V2", params);
    }

    public ArrayList<Map<String, Object>> searchKQPhieuTc(SearchKetNoiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_IS_KHACHHANG", dto.getIsKhachHang(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAICV", dto.getIdLoaiCv(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUTC_ID", dto.getIdPhieuTc(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", dto.getDonviId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IDC_ID", dto.getIdcId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("P_TRANGTHAI_VL_ID", dto.getTrangThaiVlId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("P_TRANGTHAI_VH_ID", dto.getTrangThaiVhId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOP_KN_ID", dto.getLopKnId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HINHTHUC_ID", dto.getHinhThucKn(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_THUCHIEN", dto.getNhanVienThucHien(), ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("P_HETHONG_NGUON", dto.getHeThongNguon(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HETHONG_DICH", dto.getHeThongDich(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGAYTAO_FROM", dto.getNgayTaoFrom(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_NGAYTAO_TO", dto.getNgayTaoTo(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_NGAY_HOANTHANH_FROM", dto.getNgayHoanThanhFrom(), ParameterDirection.INPUT,
                Types.DATE));
        params.add(new SqlParameter("P_NGAY_HOANTHANH_TO", dto.getNgayHoanThanhTo(), ParameterDirection.INPUT,
                Types.DATE));
        params.add(new SqlParameter("P_MA_TB", dto.getMaTb(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TEN_KHACHHANG", dto.getTenKhachHang(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAI_THICONG_KN", dto.getTrangThaiThiCongKn(), ParameterDirection.INPUT,
                Types.NUMERIC));
        params.add(new SqlParameter("P_PHUONG_THUC", dto.getPhuongThuc(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_SEARCH_KETQUA_PTC", params);
    }

    public Object updateTrangThaiKetNoi(TrangThaiKetNoiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHIEUTC_ID", dto.getPhieutc(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_TT", dto.getTrangThai(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LYDO_TRAPHIEU", dto.getLyDoTraPhieu(), SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR));
        SqlParameter result = new SqlParameter("P_RESULT", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_UPDATE_TT_THICONG_KN", params);
        return result.getOutValue();
    }

    public Object saveThiCongKetNoi(ThiCongKetNoiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_THICONG_KN_ID", dto.getThiCongKnId(), Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEUTC_ID", dto.getPhieuTcId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KETNOI_ID", dto.getKetNoiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", dto.getNhanVienId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangThaiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NOIDUNG", dto.getNoiDung(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));

        SqlParameter result = new SqlParameter("P_RS", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_SAVE_THICONG_KN", params);
        return result.getOutValue();
    }

    public Object saveThiCongKetNoiByPhieu(ThiCongKetNoiDtoByPhieu dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHIEUTC_ID", dto.getIdPhieuTc(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DS_KETNOI_ID", dto.getDsIdKetNoi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_TH", dto.getNguoiThucHien(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));

        SqlParameter result = new SqlParameter("P_RS", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THICONG_CONGVIEC.SP_THICONGKETNOI_BY_PHIEU", params);
        return result.getOutValue();
    }

}
