package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.NangLucLuuTruDTO;

@Service
public class NangLucLuuTruService {

    private final DbContext dbContext;

    public NangLucLuuTruService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public List<Map<String, Object>> getNangLucByThietBiId(Number thietbiId) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", thietbiId, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));

        ArrayList<Map<String, Object>> dbResult =
                dbContext.executeSpWithCursorToListMap(
                        "ECMS.PKG_IDC_THIETBI_VATLY.SP_GET_NANGLUCLT_BY_ID",
                        params
                );

        return dbResult.stream()
                .map(row -> row.entrySet().stream()
                        .filter(e -> e.getValue() != null)
                        .collect(Collectors.toMap(
                                e -> e.getKey().toLowerCase(),
                                Map.Entry::getValue,
                                (v1, v2) -> v1,
                                LinkedHashMap::new
                        ))
                ).collect(Collectors.toList());
    }

    public Map<String, Object> upsertNangLuc(NangLucLuuTruDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_TBI_TUDIA_ID", dto.getThietbitudId(), Types.NUMERIC));

        params.add(new SqlParameter("P_THIETBI_ID", dto.getThietbiId(), Types.NUMERIC));
        params.add(new SqlParameter("P_DL_MEMCACHE", dto.getDlMemcache(), Types.NUMERIC));
        params.add(new SqlParameter("P_DL_FLASHCACHE", dto.getDlFlashcache(), Types.NUMERIC));
        params.add(new SqlParameter("P_RAIDLEVEL_ID", dto.getRaidlevelId(), Types.NUMERIC));

        params.add(new SqlParameter("P_DLTR_HDD", dto.getDltrHdd(), Types.NUMERIC));
        params.add(new SqlParameter("P_DLTR_SSD", dto.getDltrSsd(), Types.NUMERIC));
        params.add(new SqlParameter("P_DLTR_NVME", dto.getDltrNvme(), Types.NUMERIC));

        params.add(new SqlParameter("P_DLSR_HDD", dto.getDlsrHdd(), Types.NUMERIC));
        params.add(new SqlParameter("P_DLSR_SSD", dto.getDlsrSsd(), Types.NUMERIC));
        params.add(new SqlParameter("P_DLSR_NVME", dto.getDlsrNvme(), Types.NUMERIC));

        params.add(new SqlParameter("P_DL_HDD", dto.getDlHdd(), Types.NUMERIC));
        params.add(new SqlParameter("P_DLNC_HDD", dto.getDlncHdd(), Types.NUMERIC));

        params.add(new SqlParameter("P_DL_SSD", dto.getDlSsd(), Types.NUMERIC));
        params.add(new SqlParameter("P_DLNC_SSD", dto.getDlncSsd(), Types.NUMERIC));

        params.add(new SqlParameter("P_DL_NVME", dto.getDlNvme(), Types.NUMERIC));
        params.add(new SqlParameter("P_DLNC_NVME", dto.getDlncNvme(), Types.NUMERIC));

        params.add(new SqlParameter("P_DL_IOPS", dto.getDlIops(), Types.NUMERIC));
        params.add(new SqlParameter("P_DLNC_IOPS", dto.getDlncIops(), Types.NUMERIC));

        params.add(new SqlParameter("P_MOTA", dto.getMota(), Types.VARCHAR));
        params.add(new SqlParameter("P_GHICHU", dto.getGhichu(), Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("P_RESULT", Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToMap(
                "ECMS.PKG_IDC_THIETBI_VATLY.SP_SAVE_NANGLUC",
                params
        );
    }
}
