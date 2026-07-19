package vn.vnpt.vnptit.ecms.service.hatang;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhMucHanhLangDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class DanhMucHanhLangService {
    private final DbContext dbcontext;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DanhMucHanhLangService(DbContext dbContext, JdbcTemplate jdbcTemplate) {
        this.dbcontext = dbContext;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Object getAll() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbcontext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_HANHLANG.SP_GET_DS_HANHLANG_ALL", params);
    }

    public Object getDsPhongAll() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbcontext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_HANHLANG.SP_GET_DS_PHONG", params);
    }

    public Number update(DanhMucHanhLangDTO dmHanhLangDto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_HANHLANG_ID",dmHanhLangDto.getHanhLangId() , Types.NUMERIC));
        params.add(new SqlParameter("P_PHONG_ID",dmHanhLangDto.getPhongId(), Types.NUMERIC ));
        params.add(new SqlParameter("P_TEN", dmHanhLangDto.getTen() , Types.VARCHAR ));
        params.add(new SqlParameter("P_TENTAT",dmHanhLangDto.getTenTat() , Types.VARCHAR ));
        params.add(new SqlParameter("P_NONG",dmHanhLangDto.getNong(),  Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", dmHanhLangDto.getGhiChu() , Types.VARCHAR ));
        params.add(new SqlParameter("P_MOTA", dmHanhLangDto.getMoTa(),Types.VARCHAR ));
        params.add(new SqlParameter("P_SUDUNG",dmHanhLangDto.getSuDung() , Types.VARCHAR ));
        params.add(new SqlParameter("P_DIENTICH",dmHanhLangDto.getDientich(), Types.NUMERIC ));
        params.add(new SqlParameter("P_TOADO_X",dmHanhLangDto.getToado_x() , Types.NUMERIC ));
        params.add(new SqlParameter("P_TOADO_Y",dmHanhLangDto.getToado_y() , Types.NUMERIC ));
        params.add(new SqlParameter("P_NGUOI_CN",AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbcontext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC_HANHLANG.SP_DM_HANHLANG_UPDATE", params);
        return (Number) result.getOutValue();
    }

    public boolean deleteById(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_HANHLANG_ID", id, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbcontext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC_HANHLANG.SP_HANHLANG_DELETE", params);
        return result.getOutValue().toString().equals("1");
    }

}
