package vn.vnpt.vnptit.ecms.service.idc;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.PhieuThiCongHeThongKhDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThiCongHeThongDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThiCongThietBiDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThietBiKhachHangDto;
import vn.vnpt.vnptit.ecms.dto.idc.ThietBiPortDto;
import vn.vnpt.vnptit.ecms.dto.idc.UpsertListThietBiPortKhachHangDto;
import vn.vnpt.vnptit.ecms.dto.idc.searchRackDto;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.database.SqlParameter.ParameterDirection;

@Service
public class ThietBiKhachHangService {

        private final DbContext dbContext;

        public ThietBiKhachHangService(DbContext dbContext) {
                this.dbContext = dbContext;
        }

        public Object getDanhSachHeThongThietBiKhachHang(
                        String tenHeThong,
                        String trangThaiVanHanh,
                        Long idc,
                        Long mangTbiId,
                        Long donViQLId,
                        Long nguoiQLId,
                        Long hdtbId,
                        Long tinhId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_TEN_HT", tenHeThong,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_TRANGTHAI_VANHANH", trangThaiVanHanh,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_IDC", idc,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_TINH_ID", tinhId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_DONVI_QL", donViQLId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NGUOI_QL", nguoiQLId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_HDTB_ID", hdtbId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_MANG_TBI_ID", mangTbiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_GET_DEVICE_SYSTEM_CUSTOMER",
                                params);
        }

        public Object upserHeThongThietBiKhachHang(
                        Long heThongTbiId,
                        Long mangTbiId,
                        Long loaiKHId,
                        String tenHeThong,
                        String ghiChu,
                        Long donViQLId,
                        Long nguoiQLId,
                        Long idcId,
                        Date ngayLD,
                        String canCuYC,
                        String trangThaiTSId,
                        String trangThaiVHId,
                        String suDung,
                        String moTa,
                        Long hdtbId,
                        Long tinhId,
                        Date ngayThaoGo) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_HE_THONG_TBI_ID", heThongTbiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_MANG_TBI_ID", mangTbiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_LOAI_KH_ID", loaiKHId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_TEN_HE_THONG", tenHeThong,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_GHI_CHU", ghiChu,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_DONVI_QL_ID", donViQLId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NGUOI_QL_ID", nguoiQLId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_IDC_ID", idcId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NGAY_LD", ngayLD,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_CAN_CU_YC", canCuYC,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_TRANGTHAI_TS_ID", trangThaiTSId,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_TRANGTHAI_VH_ID", trangThaiVHId,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_SU_DUNG", suDung,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_MO_TA", moTa,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_HDTB_ID", hdtbId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_TINH_ID", tinhId,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_NGAY_THAOGO", ngayThaoGo,
                                SqlParameter.ParameterDirection.INPUT, Types.DATE));
                params.add(new SqlParameter("P_OUT_ID", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_UPSER_DEVICE_SYSTEM_CUSTOMER",
                                params);
        }

        public Object deleteHeThongThietBiKhachHang(Long heThongTbiId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_HETHONGTBI_ID", heThongTbiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_DELETE_DEVICE_SYSTEM_CUSTOMER",
                                params);
        }

        public Object getThietBiKhachHang(
                        Long thietBiKHId,
                        String tenThietBi,
                        Long loaiTbiId,
                        String loaiKhachHang,
                        String serialNumber,
                        Long toaNhaId,
                        String trangThaiVanHanhId,
                        Long hdtbId,
                        Long tinhId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THIETBIKH_ID", thietBiKHId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_TEN_TBI", tenThietBi,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_LOAITBI_ID", loaiTbiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_LOAIKHACHHANG", loaiKhachHang,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_SERIAL", serialNumber,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_TOANHA_ID", toaNhaId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_TRANGTHAI_VH_ID", trangThaiVanHanhId,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_HDTB_ID", hdtbId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_TINH_ID", tinhId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_GET_DEVICE_CUSTOMER",
                                params);
        }

        public Object upserThietBiKhachHang(
                        Long thietBiKHId,
                        Long heThongTbiId,
                        Long mangTbiId,
                        Long loaiThietBiId,
                        List<Long> thanhPhanThietBiIds,
                        String ten,
                        String model,
                        String cauHinh,
                        String serialNumber,
                        String partNumber,
                        String csDinhDanh,
                        String soPortConsole,
                        String soPortInterface,
                        Long hangSxId,
                        Long noiSanXuatId,
                        String namSx,
                        String iconPath,
                        String loaiKhachHang,
                        Long donViQLId,
                        Long nguoiQLId,
                        Long phamViIDCId,
                        Long phongId,
                        Long idcId,
                        Long idcRackId,
                        Long upUnitId,
                        Long downUnitId,
                        Date ngayLapDat,
                        String canCuLD,
                        String trangThaiSD,
                        String trangThaiTsId,
                        String maTs,
                        Long hieuLuc,
                        String tsNoiBo,
                        String moTa,
                        Date ngayCn,
                        Long hdtbId,
                        Long tinhId) throws AppSqlException {

                String p_nguoi_cn = AppRequestContext.getCurrent().getUserName();
                String thanhPhanThietBiIdsJson;

                if (thanhPhanThietBiIds == null || thanhPhanThietBiIds.isEmpty()) {
                        thanhPhanThietBiIdsJson = null;
                } else {
                        try {
                                thanhPhanThietBiIdsJson = JsonConvert.serialize(thanhPhanThietBiIds);
                        } catch (IOException e) {
                                throw new RuntimeException("Error converting thanhPhanThietBiIds to JSON", e);
                        }
                }

                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THIETBIKH_ID", thietBiKHId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_HETHONGTBI_ID", heThongTbiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_MANGTBI_ID", mangTbiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_LOAITHIETBI_ID", loaiThietBiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_THANHPHAN_THIETBI_ID", thanhPhanThietBiIdsJson,
                                SqlParameter.ParameterDirection.INPUT, Types.CLOB));
                params.add(new SqlParameter("P_TEN", ten,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_MODEL", model,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_CAU_HINH", cauHinh,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_SERIAL_NUMBER", serialNumber,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_PART_NUMBER", partNumber,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_CS_DINHDANH", csDinhDanh,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_SO_PORT_CONSOLE", soPortConsole,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_SO_PORT_INTERFACE", soPortInterface,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_HANGSX_ID", hangSxId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NAM_SX", namSx,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_NOISANXUAT_ID", noiSanXuatId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_ICON_PATH", iconPath,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_LOAIKHACHHANG", loaiKhachHang,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_DONVIQL_ID", donViQLId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_PHAMVI_IDC_ID", phamViIDCId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NGUOIQL_ID", nguoiQLId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_PHONG_ID", phongId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_IDC_ID", idcId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_IDC_RACK_ID", idcRackId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_UP_UNIT_ID", upUnitId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_DOWN_UNIT_ID", downUnitId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NGAY_LAPDAT", ngayLapDat,
                                SqlParameter.ParameterDirection.INPUT, Types.DATE));
                params.add(new SqlParameter("P_CANCU_LD", canCuLD,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_TRANGTHAI_SD", trangThaiSD,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_TRANGTHAI_TS_ID", trangThaiTsId,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_MA_TS", maTs,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_HIEU_LUC", hieuLuc,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_TS_NOIBO", tsNoiBo,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_MOTA", moTa,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_NGUOI_CN", p_nguoi_cn,
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_NGAY_CN", ngayCn,
                                SqlParameter.ParameterDirection.INPUT, Types.DATE));
                params.add(new SqlParameter("P_HDTB_ID", hdtbId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_TINH_ID", tinhId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_ID_OUT", null,
                                SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));

                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_UPSER_DEVICE_CUSTOMER",
                                params);
        }

        public Object deleteThietBiKhachHang(Long thietBiId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THIETBI_ID", thietBiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_DELETE_DEVICE_CUSTOMER",
                                params);
        }

        public Object getLichSuThietBiKhachHang(Long thietBiId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THIETBI_ID", thietBiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_GET_HISTORY_DEVICE_CUSTOMER",
                                params);
        }

        public Object getPortThietBiKhachHang(
                        Long thietBiId,
                        Long loaiCongId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THIETBI_ID", thietBiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_LOAICONG_ID", loaiCongId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_GET_PORT_DEVICE_CUSTOMER",
                                params);
        }

        public Object deletePortThietBiKhachHang(Long portId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_PORT_ID", portId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_DELETE_PORT_DEVICE_CUSTOMER",
                                params);
        }

        public Object searchRackIdc(searchRackDto searchDto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_IDC_ID", searchDto.getIdcId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_TOANHA_ID", searchDto.getToaNhaId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_MATSAN_ID", searchDto.getMatSanId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_PHONG_ID", searchDto.getPhongId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_TEN", searchDto.getTen(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_SEARCH_RACK",
                                params);
        }

        public Object getDevicesBySystemCustomer(Integer heThongTbiId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_HETHONGTBI_ID", heThongTbiId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_GET_DEVICES_BY_SYSTEM_CUSTOMER",
                                params);
        }

        public Object themThiCongHeThongKhachHang(ThiCongHeThongDto dto) throws AppSqlException {
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

        public Object themThiCongThietBiKhachHang(ThiCongThietBiDto dto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_PHIEUTC_ID", dto.getPhieuThiCongId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_THICONGHT_ID", dto.getThiCongHeThongId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_ID_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_INSERT_CONSTRUCTION_FORM_DEVICE",
                                params);
        }

        public Object layDanhSachThiCongHeThong(ThiCongHeThongDto dto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_PHIEUTC_ID", dto.getPhieuThiCongId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_GET_THICONG_SYSTEM_CUSTOMER",
                                params);
        }

        public Object layDanhSachThiCongThietBi(ThiCongThietBiDto dto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THICONGHT_ID", dto.getThiCongHeThongId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_GET_THICONG_DEVICE_CUSTOMER",
                                params);
        }

        public Object chuyenPhieuThiCong(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
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

        public Object traPhieuThiCong(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_PHIEU_THICONG_ID", dto.getPhieuThiCongId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangThaiId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_LYDO_TRA", dto.getLyDoTra(),
                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_RETURN_PROCESSING",
                                params);
        }

        public Object hoanThanhPhieuThiCong(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_PHIEU_THICONG_ID", dto.getPhieuThiCongId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_TRANGTHAI_ID", dto.getTrangThaiId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_COMPLETE_PROCESSING",
                                params);
        }

        public Object kiemTraThietBiDaLapDat(ThietBiKhachHangDto dto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiKHId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_CHECK_INSTALL_DEVICE",
                                params);
        }

        public Object kiemTraUnitKhaDung(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
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

        public Object goThietBiKhachHang(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_PHIEUTC_ID", dto.getPhieuThiCongId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_REMOVE_DEVICE_FROM_RACK",
                                params);
        }

        public Object thayDoiUnitThietBiKhachHang(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_IDC_RACK_ID", dto.getRackId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_UNIT_UP_POS", dto.getUpUnitPos(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_UNIT_DOWN_POS", dto.getDownUnitPos(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_PHIEUTC_ID", dto.getPhieuThiCongId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_CHANGE_DEVICE_RACK_POSITION",
                                params);
        }

        public Object lapDatThietBiKhachHang(PhieuThiCongHeThongKhDto dto) throws AppSqlException {
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
                params.add(new SqlParameter("P_PHIEUTC_ID", dto.getPhieuThiCongId(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_INSTALL_DEVICE_TO_RACK",
                                params);
        }

        public Object getDanhSachPhieuThiCongHeThong() throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_GET_LIST_CONSTRUCTION_SYSTEM",
                                params);
        }

        public Object getThiCongById(Long phieuThiCongId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THICONGHT_ID", phieuThiCongId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToMap(
                                "ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_GET_DETAIL_FROM_CONSTRUCTION_SYSTEM",
                                params);
        }

        public Object upsertListThietBiPortKhachHang(UpsertListThietBiPortKhachHangDto dto) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                String portsJson;
                if (dto.getPortList() == null || dto.getPortList().isEmpty()) {
                        return null;
                }
                try {
                        portsJson = JsonConvert.serialize(dto.getPortList());
                } catch (IOException e) {
                        throw new RuntimeException("Error converting ports to JSON", e);
                }

                params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiId(), ParameterDirection.INPUT,
                                Types.NUMERIC));
                params.add(new SqlParameter("P_PORTS_JSON", portsJson, ParameterDirection.INPUT, Types.CLOB));
                params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                                ParameterDirection.INPUT, Types.VARCHAR));

                SqlParameter outResult = new SqlParameter("P_RESULT", null, ParameterDirection.OUTPUT, Types.NUMERIC);
                params.add(outResult);

                dbContext.executeStoredProcedure("ECMS.PKG_IDC_THIETBI_KHACHHANG.SP_UPSERT_LIST_THIETBI_PORT_JSON",
                                params);
                return outResult.getOutValue();
        }

        public Object getDanhSachThietBiKhachHang(int loaiTbId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THICONGHT_ID", loaiTbId,SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                        "ECMS.PKG_IDC_THIETBI_VATLY.SP_DANHSACH_KHACHHANG_THIETBI",
                        params);
        }
        public Object getHdtbDetail(int hdtbId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_HDTB_ID", hdtbId,SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToMap(
                        "ECMS.PKG_IDC_THIETBI_VATLY.SP_HOPDONG_DETAIL",
                        params);
        }

        public Object getDanhSachNghiepvuIDC(int thietBiId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_THIETBI_ID", thietBiId,SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                        "ECMS.PKG_IDC_THIETBI_VATLY.SP_DANHSACH_NGHIEPVU_IDC",
                        params);
        }
}
