package vn.vnpt.vnptit.ecms.service.idc;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.LoaiMayAoDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DanhMucLoaiMayAoService {
    private final DbContext dbContext;
    DanhMucLoaiMayAoService(DbContext dbContext){this.dbContext = dbContext;}

    public List<Map<String, Object>> getDanhsach() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        List<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_LOAIMAYAO.SP_LOAI_MAYAO_GET_DANHSACH", params);
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

    public Boolean upsert(LoaiMayAoDTO input) throws AppSqlException{
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", input.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", input.getTen(),  Types.NVARCHAR));
        params.add(new SqlParameter("p_tentat", input.getTenTat(),  Types.NVARCHAR));
        params.add(new SqlParameter("p_vpc", input.getVpc(),  Types.NUMERIC));
        params.add(new SqlParameter("p_mota", input.getMoTa(),  Types.NVARCHAR));
        params.add(new SqlParameter("p_sudung", input.getSuDung(),  Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", input.getGhiChu(),  Types.NVARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));

        SqlParameter rs = new SqlParameter("p_result", Types.NUMERIC);
        params.add(rs);
        System.out.println("check params" + params);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_LOAIMAYAO.SP_LOAI_MAYAO_UPSERT", params);
        return rs.getOutValue().toString().equals("1");
    }

    public Boolean delete(Number id) throws AppSqlException{
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        SqlParameter rs = new SqlParameter("p_result", Types.NUMERIC);
        params.add(rs);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_LOAIMAYAO.SP_LOAI_MAYAO_DELETE", params);
        return rs.getOutValue().toString().equals("1");
    }

    public Boolean deleteItems(Number[] ids) throws AppSqlException{
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", StringUtils.join(ids, ";"), Types.NVARCHAR));
        SqlParameter rs = new SqlParameter("p_result", Types.NUMERIC);
        params.add(rs);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_LOAIMAYAO.SP_LOAI_MAYAO_DELETE_ITEMS",params);
        return rs.getOutValue().toString().equals("1");
    }
}
