package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchCHTAoHoaDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DanhSachCumBackupService {
    private final DbContext dbContext;

    public DanhSachCumBackupService(DbContext dbContext ){
        this.dbContext = dbContext;
    }

    public List<Map<String, Object>> getDsach(SearchCHTAoHoaDTO input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_macum_ht", input.getMaCumht(), Types.NVARCHAR));
        params.add(new SqlParameter("p_tencum_ht", input.getTenCumht(), Types.NVARCHAR));
        params.add(new SqlParameter("p_nhomht_id", input.getNhomhtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_phanloaiht_id", input.getPhanloaihtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_donviql_id", input.getDonviQlId(), Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", input.getIdcId(), Types.NUMERIC));
        params.add(new SqlParameter("p_htm_id", input.getHtmId(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaiht_id", input.getLoaihtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_capdoht_id", input.getCapdohtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_hieuluc_id", input.getHieuLucId(), Types.NUMERIC));
        params.add(new SqlParameter("p_hinhthucdt_id", input.getHinhthucdtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_congnghe_id", input.getCongngheId(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthaiccdv_id", input.getTrangthaiccdvId(), Types.NUMERIC));
        params.add(new SqlParameter("p_canhan_ql", input.getCanhanQl(), Types.NVARCHAR));
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));
        List<Map<String,Object>> dbResult =  dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_NHDU.SP_CUMHATANG_BACKUP_GET_DSACH", params);
        return dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
    }

    public Boolean delete(Number cumhtId) throws AppSqlException{
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", cumhtId, Types.NUMERIC));
        SqlParameter rs = new SqlParameter("p_result", Types.NUMERIC);
        params.add(rs);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_NHDU.SP_CUMHATANG_BACKUP_DELETE", params);
        return rs.getOutValue().toString().equals("1");
    }
}
