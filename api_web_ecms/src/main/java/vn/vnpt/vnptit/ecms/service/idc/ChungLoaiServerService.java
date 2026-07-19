package vn.vnpt.vnptit.ecms.service.idc;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.idc.ChungLoaiServerDto;

@Service
public class ChungLoaiServerService {

    @Autowired
    private DbContext dbContext;

    /**
     * Tìm kiếm hoặc lấy danh sách tất cả chủng loại server
     * Nếu không có điều kiện search thì lấy toàn bộ dữ liệu
     */
    public ArrayList<Map<String, Object>> searchChungLoaiServer(JsonNode searchJsonData) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String jsonString = searchJsonData != null ? searchJsonData.toString() : null;
        params.add(new SqlParameter("p_search_json", jsonString, ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_rs", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap(
                "ECMS.PKG_TAINGUYEN_THIETBI.SP_SEARCH_CHUNGLOAI_SERVER", params);
    }

    /**
     * Lưu (tạo mới hoặc cập nhật) chủng loại server
     */
    public Object saveChungLoaiServer(ChungLoaiServerDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        SqlParameter p_id = new SqlParameter("P_ID", dto.getChungloaisvrId(), Types.NUMERIC);
        params.add(p_id);
        params.add(new SqlParameter("P_MA_VT", dto.getMaVt(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MODEL_THIETBI", dto.getModelThietBi(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_HANGSX_ID", dto.getHangsxId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CONGSUAT", dto.getCongSuat(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_FULL_DEPTH", dto.getFullDepth(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CPU_SOKHE", dto.getCpuSoKhe(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CPU_SOSOCKET", dto.getCpuSoSocket(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CPU_MODEL", dto.getCpuModel(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SOKHE_RAM", dto.getSokheRam(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SOKHE_SSD", dto.getSokheSsd(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SOKHE_HDD", dto.getSokheHdd(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SOKHE_NVME", dto.getSokheNvme(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GPU_MODEL", dto.getGpuModel(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_GPU_SOLUONG", dto.getGpuSoLuong(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SOSLOT_FAN", dto.getSoslotFan(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SOSLOT_PCI", dto.getSoslotPci(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DP_SOCONG", dto.getDpSoCong(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DP_TENCONG", dto.getDpTenCong(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DP_LOAICONG", dto.getDpLoaiCong(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_DP_BANGTHONG", dto.getDpBangThong(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("P_DP_TTMODULE_ID", dto.getDpTtmoduleId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("P_DP_MUCDICHSD_ID", dto.getDpMucdichsdId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PP_SOCONG", dto.getPpSoCong(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PP_TENCONG", dto.getPpTenCong(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_PP_DONGDIEN", dto.getPpDongDien(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PP_DIENAP", dto.getPpDienAp(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PP_LOAIND", dto.getPpLoaiNd(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PP_CHUANDN_ID", dto.getPpChuandnId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(
                new SqlParameter("P_CP_MUCDICHSD_ID", dto.getCpMucdichsdId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CP_LOAICONG", dto.getCpLoaiCong(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_CP_SL", dto.getCpSl(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CP_TENCONG", dto.getCpTenCong(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MOTA", dto.getMoTa(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SUDUNG", dto.getSuDung(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", dto.getGhiChu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                ParameterDirection.INPUT, Types.VARCHAR));

        dbContext.executeStoredProcedure("ECMS.PKG_TAINGUYEN_THIETBI.SP_SAVE_CHUNGLOAI_SERVER", params);
        return p_id.getOutValue();
    }

    /**
     * Xóa chủng loại server
     */
    public Boolean delete(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_TAINGUYEN_THIETBI.SP_DELETE_CHUNGLOAI_SERVER", params);

        BigDecimal rs = (BigDecimal) result.getOutValue();
        return rs != null && rs.intValue() == 1;
    }
}
