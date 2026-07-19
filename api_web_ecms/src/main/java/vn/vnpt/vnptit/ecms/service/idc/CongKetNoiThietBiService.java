package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.CongKetNoiThietBiDto;
import vn.vnpt.vnptit.ecms.dto.idc.CongKetNoiThietBiDtoV2;

@Service
public class CongKetNoiThietBiService {
        private final DbContext dbContext;
        private final ObjectMapper objectMapper;

        public CongKetNoiThietBiService(DbContext dbContext) {
                this.dbContext = dbContext;
                this.objectMapper = new ObjectMapper();
        }

        public ArrayList<Map<String, Object>> insertPort(CongKetNoiThietBiDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_thietbi_id", dto.getThietBiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_phanloai", dto.getPhanLoai(), SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_soluong", dto.getSoLuong(), SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_ten", dto.getTen(), SqlParameter.ParameterDirection.INPUT,
                                                java.sql.Types.VARCHAR));
                parameters.add(new SqlParameter("p_batdautu", dto.getBatDauTu(), SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_INSERT_CONG_KETNOI_THIETBI",
                                parameters);
        }

        public ArrayList<Map<String, Object>> insertPortV2(CongKetNoiThietBiDtoV2 dto) throws AppSqlException {
                try {
                        ArrayList<Map<String, Object>> combinedResult = new ArrayList<>();

                        // Xử lý portsDto
                        if (dto.getPortsDto() != null && dto.getPortsDto().getPorts() != null) {
                                ArrayList<SqlParameter> portParameters = new ArrayList<>();
                                String portsJson = objectMapper.writeValueAsString(dto.getPortsDto().getPorts());

                                portParameters.add(new SqlParameter("p_thietbi_id", dto.getThietBiId(),
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                                portParameters.add(new SqlParameter("p_khecam", 0,
                                        SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                                portParameters.add(new SqlParameter("p_phanloai", dto.getPortsDto().getPhanLoai(),
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                                portParameters.add(new SqlParameter("p_soluong", dto.getPortsDto().getSoCong(),
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                                portParameters.add(new SqlParameter("p_ten", dto.getPortsDto().getDinhDangTen(),
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
                                portParameters.add(new SqlParameter("p_batdautu", dto.getPortsDto().getBatDauTu(),
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                                portParameters.add(new SqlParameter("p_ports", portsJson,
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.CLOB));
                                portParameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                                                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                                portParameters.add(new SqlParameter("p_cursor", null,
                                                SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));

                                ArrayList<Map<String, Object>> portResult = dbContext.executeSpWithCursorToListMap(
                                                "ECMS.PKG_IDC_THIETBI_VATLY.SP_INSERT_CONG_KETNOI_THIETBI_V2",
                                                portParameters);
                                combinedResult.addAll(portResult);
                        }

                        // Xử lý lineCardsDto (chỉ khi không null)
                        if (dto.getLineCardsDto() != null && dto.getLineCardsDto().getPorts() != null) {
                                ArrayList<SqlParameter> cardParameters = new ArrayList<>();
                                String lineCardsJson = objectMapper
                                                .writeValueAsString(dto.getLineCardsDto().getPorts());

                                cardParameters.add(new SqlParameter("p_thietbi_id", dto.getThietBiId(),
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                                cardParameters.add(new SqlParameter("p_khecam", 1,
                                        SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                                cardParameters.add(new SqlParameter("p_phanloai", dto.getLineCardsDto().getPhanLoai(),
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                                cardParameters.add(new SqlParameter("p_soluong", dto.getLineCardsDto().getSoCong(),
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                                cardParameters.add(new SqlParameter("p_ten", dto.getLineCardsDto().getDinhDangTen(),
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.VARCHAR));
                                cardParameters.add(new SqlParameter("p_batdautu", dto.getLineCardsDto().getBatDauTu(),
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                                cardParameters.add(new SqlParameter("p_ports", lineCardsJson,
                                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.CLOB));
                                cardParameters.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(),
                                        SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
                                cardParameters.add(new SqlParameter("p_cursor", null,
                                                SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));

                                ArrayList<Map<String, Object>> cardResult = dbContext.executeSpWithCursorToListMap(
                                                "ECMS.PKG_IDC_THIETBI_VATLY.SP_INSERT_CONG_KETNOI_THIETBI_V2",
                                                cardParameters);
                                combinedResult.addAll(cardResult);
                        }

                        return combinedResult;
                } catch (Exception e) {
                        throw new AppSqlException(e);
                }
        }

        public ArrayList<Map<String, Object>> danhSachPortThietBi(long thietBiId, int phanLoai, int kheCam) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("P_THIETBI_ID", thietBiId, Types.NUMERIC));
                parameters.add(new SqlParameter("P_PHANLOAI", phanLoai, Types.NUMERIC));
                parameters.add(new SqlParameter("P_KHECAM", kheCam, Types.NUMERIC));
                parameters.add(new SqlParameter("P_CUR", java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_DS_PORT_THIETBI",
                                parameters);
        }

        public ArrayList<Map<String, Object>> updatePort(CongKetNoiThietBiDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_port_id", dto.getPortId(), SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_dongdinhdanh", dto.getDongDinhDanh(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_dienapdinhdanh", dto.getDienApDinhDanh(),
                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_mucdichsudung", dto.getMucDichSuDung(),
                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_trangthaiketnoi", dto.getTrangThaiKetNoi(),
                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_thietbiketnoi_id", dto.getThietBiKetNoiId(),
                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_ip", dto.getIp(), SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.VARCHAR));
                parameters.add(new SqlParameter("p_bangthong", dto.getBangThong(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.VARCHAR));
                parameters.add(new SqlParameter("p_module", dto.getModule(), SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_chuandaunoi", dto.getChuanDauNoi(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_loai_cong", dto.getLoaiCong(), SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_trangthai_port", dto.getTrangThaiPort(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_loai_nguondien", dto.getLoaiNguonDien(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_UPDATE_CONG_KETNOI_THIETBI",
                                parameters);
        }

        public ArrayList<Map<String, Object>> connectDeviceToPort(CongKetNoiThietBiDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_port_id", dto.getPortId(), SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_thietbi_ketnoi_id", dto.getThietBiKetNoiId(),
                                SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_KET_NOI_THIETBI_VAO_PORT",
                                parameters);
        }

        public ArrayList<Map<String, Object>> disconnectDeviceFromPort(CongKetNoiThietBiDto dto)
                        throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_port_id", dto.getPortId(), SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_GO_KET_NOI_THIETBI_TU_PORT",
                                parameters);
        }

        public ArrayList<Map<String, Object>> listDevicesNotConnected(CongKetNoiThietBiDto dto) throws AppSqlException {
                ArrayList<SqlParameter> parameters = new ArrayList<>();
                parameters.add(new SqlParameter("p_thietbi_id", dto.getThietBiId(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(new SqlParameter("p_phanloai", dto.getPhanLoai(),
                                SqlParameter.ParameterDirection.INPUT,
                                java.sql.Types.NUMERIC));
                parameters.add(
                                new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT,
                                                java.sql.Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_THIETBI_VATLY.SP_DS_THIETBI_CARD_CHUA_KETNOI",
                                parameters);
        }
}
