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
import vn.vnpt.vnptit.ecms.dto.mau.MauPortDto;

@Service
public class MauPortService {

    private final DbContext dbContext;
    private final ObjectMapper objectMapper;

    public MauPortService(DbContext dbContext) {
        this.dbContext = dbContext;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Lấy danh sách port mẫu theo mẫu thiết bị ID
     */
    public ArrayList<Map<String, Object>> gePorttByMauThietBi(long mautbiId, int phanLoai, int kheCam) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_THIETBI_ID", mautbiId, Types.NUMERIC));
        parameters.add(new SqlParameter("P_PHANLOAI", phanLoai, Types.NUMERIC));
        parameters.add(new SqlParameter("P_KHECAM", kheCam, Types.NUMERIC));
        parameters.add(new SqlParameter("P_CUR", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_THIETBI_MAU.SP_DS_PORT_CHUNGLOAI_TBI",
                parameters);
    }
    /**
     * Tạo mới hoặc cập nhật port mẫu
     */
    public ArrayList<Map<String, Object>> insertPort(MauPortDto dto) throws AppSqlException {
        try {
            ArrayList<Map<String, Object>> combinedResult = new ArrayList<>();

            // Xử lý portsDto
            if (dto.getPortsDto() != null && dto.getPortsDto().getPorts() != null) {
                ArrayList<SqlParameter> portParameters = new ArrayList<>();
                String portsJson = objectMapper.writeValueAsString(dto.getPortsDto().getPorts());

                portParameters.add(new SqlParameter("p_thietbi_id", dto.getMautbiId(),
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
                        "ECMS.PKG_IDC_THIETBI_MAU.SP_INSERT_PORT_CHUNGLOAI_TB",
                        portParameters);
                combinedResult.addAll(portResult);
            }

            // Xử lý lineCardsDto (chỉ khi không null)
            if (dto.getLineCardsDto() != null && dto.getLineCardsDto().getPorts() != null) {
                ArrayList<SqlParameter> cardParameters = new ArrayList<>();
                String lineCardsJson = objectMapper
                        .writeValueAsString(dto.getLineCardsDto().getPorts());

                cardParameters.add(new SqlParameter("p_thietbi_id", dto.getMautbiId(),
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
                        "ECMS.PKG_IDC_THIETBI_MAU.SP_INSERT_PORT_CHUNGLOAI_TB",
                        cardParameters);
                combinedResult.addAll(cardResult);
            }

            return combinedResult;
        } catch (Exception e) {
            throw new AppSqlException(e);
        }
    }
}

