package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.LichSuTacDongDto;

@Service
public class LichSuTacDongService {
    @Autowired
    private DbContext dbContext;

    public ArrayList<Map<String, Object>> getListSuTacDong(LichSuTacDongDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_tenbang", dto.getTenBang(), Types.VARCHAR));
        params.add(new SqlParameter("p_tencot", dto.getTenCot(), Types.VARCHAR));
        params.add(new SqlParameter("p_khoa_dulieu", dto.getKhoaDuLieu(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_LICHSU_TACDONG.SP_GET_LS_TACDONG", params);
    }

    public ArrayList<Map<String, Object>> getListSuCongSuat(Long thietBiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id",thietBiId, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_LICHSU_TACDONG.SP_GET_LS_CONGSUAT", params);
    }

    public ArrayList<Map<String, Object>> getListSuCapPhat(Long thietBiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id",thietBiId, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_LICHSU_TACDONG.SP_GET_LS_CAPPHAT", params);
    }
}
