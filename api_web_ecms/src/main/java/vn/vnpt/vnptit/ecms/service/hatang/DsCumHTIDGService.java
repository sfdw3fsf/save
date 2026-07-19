package vn.vnpt.vnptit.ecms.service.hatang;

import org.apache.commons.lang.StringUtils;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchCumHTIDGDTO;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DsCumHTIDGService {
    private final DbContext dbContext;
    private final vn.vnpt.vnptit.ecms.service.dongbo.DongBoIDGService dongBoIDGService;

    DsCumHTIDGService(DbContext dbContext, vn.vnpt.vnptit.ecms.service.dongbo.DongBoIDGService dongBoIDGService){
        this.dbContext = dbContext;
        this.dongBoIDGService = dongBoIDGService;
    }
    public List<Map<String, Object>> getDsach(SearchCumHTIDGDTO input) throws AppSqlException{
        List<SqlParameter> params = new ArrayList<>();
        Number[] ids = input.getLoaiDichVuIds();
        String formatIds = "";
        if(ids == null || ids.length == 0){
            formatIds = "";
        }else{
            formatIds = StringUtils.join(ids, ";");
        }
        params.add(new SqlParameter("p_macum_ht", input.getMaCumht(), Types.NVARCHAR));
        params.add(new SqlParameter("p_tencum_ht", input.getTenCumht(), Types.NVARCHAR));
        params.add(new SqlParameter("p_link_quantri", input.getLinkQuantri(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_id", input.getIpId(), Types.NUMERIC));
        params.add(new SqlParameter("p_congnghe_id", input.getCongngheId(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_ql_id", input.getDonviQlId(), Types.NUMERIC));
        params.add(new SqlParameter("p_region_id", input.getRegionId(), Types.NVARCHAR));
        params.add(new SqlParameter("p_trangthaivh_id", input.getTrangthaivhId(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthaiccdv_id", input.getTrangthaiccdvId(), Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", input.getIdcId(), Types.NUMERIC));
        params.add(new SqlParameter("p_phanloaiht_id", input.getPhanloaihtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_canhan_ql", input.getCanhanQl(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieuluc_id", input.getHieuLucId(), Types.NUMERIC));
        params.add(new SqlParameter("p_rancher_code", input.getRancherCode(), Types.NVARCHAR));
        params.add(new SqlParameter("p_capdoht_id", input.getCapdohtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaiht_id", input.getLoaihtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_loai_dichvu_ids", formatIds, Types.NVARCHAR));
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));
        List<Map<String, Object>> dbResult =  dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_NHDU.SP_CUMHATANG_IDG_GET_DSACH", params);
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

    public Boolean delete(Number idgId) throws AppSqlException{
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", idgId, Types.NUMERIC));
        SqlParameter rs = new SqlParameter("p_result", Types.NUMERIC);
        params.add(rs);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_NHDU.SP_CUMHATANG_IDG_DELETE", params);
        return rs.getOutValue().toString().equals("1");
    }

    public Boolean syncCumIDG(String region_id) throws AppSqlException{
        dongBoIDGService.dongBoCumHaTangIDG(region_id);
        return Boolean.TRUE;
    }
}
