package vn.vnpt.vnptit.ecms.service.idc;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.SoDoKetNoiDienDto;

@Service
public class SoDoKetNoiDienService {
        private final DbContext dbContext;

        public SoDoKetNoiDienService(DbContext dbContext) {
                this.dbContext = dbContext;
        }

        public ArrayList<Map<String, Object>> listDevicesNotConnected(SoDoKetNoiDienDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_idc_id", dto.getIdcId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_toa_nha_id", dto.getToaNhaId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_mat_san_id", dto.getMatSanId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_phong_id", dto.getPhongId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_rack_id", dto.getRackId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAUNOI_DIEN.SP_DS_THIETBI_DIEN_CHUA_KETNOI",
                                parameters);
        }

        public ArrayList<Map<String, Object>> listDevicesNotConnectedByObject(SoDoKetNoiDienDto dto)
                        throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_idc_id", dto.getIdcId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_toa_nha_id", dto.getToaNhaId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_mat_san_id", dto.getMatSanId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_phong_id", dto.getPhongId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_rack_id", dto.getRackId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_ten_thiet_bi", dto.getTenThietBi(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.VARCHAR));
                parameters.add(new SqlParameter("p_type", dto.getType(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.VARCHAR));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_DAUNOI_DIEN.SP_DS_THIETBI_DIEN_CHUA_KETNOI_THEO_DOITUONG",
                                parameters);
        }

        public ArrayList<Map<String, Object>> listPortsNotConnected(SoDoKetNoiDienDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_thietbi_id", dto.getThietBiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAUNOI_DIEN.SP_DS_PORT_THIETBI_CHUA_KETNOI",
                                parameters);
        }

        public ArrayList<Map<String, Object>> soDoKetNoiDienIdc(SoDoKetNoiDienDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_idc_id", dto.getIdcId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAUNOI_DIEN.SP_SODO_KETNOI_DIEN_IDC",
                                parameters);
        }

        public ArrayList<Map<String, Object>> goKetNoiDienThietBi(SoDoKetNoiDienDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_thietbi_nguon_id", dto.getThietBiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_port_nguon_id", dto.getPortThietbiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_thietbi_dich_id", dto.getThietbiKetnoiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_port_dich_id", dto.getPortThietbiKetnoiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAUNOI_DIEN.SP_GO_KETNOI_DIEN_THIETBI",
                                parameters);
        }

        public ArrayList<Map<String, Object>> ketNoiDienThietBi(SoDoKetNoiDienDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_thietbi_id", dto.getThietBiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_thietbi_ketnoi_id", dto.getThietbiKetnoiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_port_thietbi_id", dto.getPortThietbiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_port_thietbi_ketnoi_id", dto.getPortThietbiKetnoiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_khoang_cach_ketnoi", dto.getKhoangCachKetNoi(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.FLOAT));
                parameters.add(new SqlParameter("p_loai_ketnoi_id", dto.getLoaiKetNoiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_trang_thai_id", dto.getTrangThaiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAUNOI_DIEN.SP_KETNOI_DIEN_THIETBI",
                                parameters);
        }

        public ArrayList<Map<String, Object>> danhSachKetNoiDienThietBiTheoPhong(SoDoKetNoiDienDto dto)
                        throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_phong_id", dto.getPhongId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_DAUNOI_DIEN.SP_DS_KETNOI_DIEN_THIETBI_THEO_PHONG",
                                parameters);
        }

        public ArrayList<Map<String, Object>> getScopeDiagram(SoDoKetNoiDienDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("P_SCOPE_TYPE", dto.getType(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.VARCHAR));
                parameters.add(new SqlParameter("P_SCOPE_ID", dto.getScopeId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("P_INCLUDE_EXTERNAL", dto.getExternal(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("P_DEPTH", dto.getDepth(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAUNOI_DIEN.SP_GET_SCOPE_DIAGRAM",
                                parameters);
        }

        /**
         * Lấy sơ đồ đấu nối của TẤT CẢ thiết bị điện trong một IDC
         * Trả về danh sách thiết bị, danh sách kết nối và thống kê tổng quan
         */
        public ArrayList<Map<String, Object>> getAllDevicesDiagramByIdc(SoDoKetNoiDienDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("P_IDC_ID", dto.getIdcId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap(
                                "ECMS.PKG_IDC_DAUNOI_DIEN.SP_SODO_DAUNOI_TATCA_THIETBI_DIEN_IDC",
                                parameters);
        }
}
