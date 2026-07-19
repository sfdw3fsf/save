package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.tamthonghoiDTO;

@Service
public class tamthonghoiService {
    @Autowired
    private final DbContext dbContext;

    public tamthonghoiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object loadPhong(tamthonghoiDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_TEN", dto.getTenphong(),
                SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_PAGE", dto.getPAGE(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PAGESIZE", dto.getPAGESIZE(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CURSOR", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_HATANG_DM_TAMTHONGHOI.SP_GET_DM_PHONG",
                params
        );
    }

    public Object loadGrid(tamthonghoiDTO dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter(
                "P_TEN",
                dto.getTen(),
                SqlParameter.ParameterDirection.INPUT,
                Types.NVARCHAR
        ));

        params.add(new SqlParameter(
                "P_PAGE",
                dto.getPAGE(),
                SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC
        ));

        params.add(new SqlParameter(
                "P_PAGESIZE",
                dto.getPAGESIZE(),
                SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC
        ));

        params.add(new SqlParameter(
                "P_CURSOR",
                null,
                SqlParameter.ParameterDirection.OUTPUT,
                Types.REF_CURSOR
        ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_HATANG_DM_TAMTHONGHOI.SP_GET_DANH_MUC",
                params
        );
    }

    public Boolean upsert(tamthonghoiDTO dto) throws AppSqlException {

        String nguoiCn = AppRequestContext.getCurrent().getUserName();

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter(
                "P_TAMTH_ID",
                dto.getTamthId(),
                SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC
        ));

        params.add(new SqlParameter("P_TEN", dto.getTen(),
                SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_TENTAT", dto.getTenTat(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_PHONG_ID", dto.getPhongId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CHIEUDAI", dto.getChieudai(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CHIEURONG", dto.getChieurong(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TOADO_X", dto.getToadoX(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TOADO_Y", dto.getToadoY(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", dto.getGhichu(),
                SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_MOTA", dto.getMota(),
                SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", nguoiCn,
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGAY_CN", Timestamp.valueOf(LocalDateTime.now()),
                SqlParameter.ParameterDirection.INPUT, Types.TIMESTAMP));

        dbContext.executeStoredProcedure(
                "ECMS.PKG_HATANG_DM_TAMTHONGHOI.SP_UPSERT_TAM_THONGHOI",
                params
        );

        return Boolean.TRUE;
    }

    public Boolean delete(Long id) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter(
                "P_IDS",
                String.valueOf(id),   // convert Long → String
                SqlParameter.ParameterDirection.INPUT,
                Types.VARCHAR
        ));

        dbContext.executeStoredProcedure(
                "ECMS.PKG_HATANG_DM_TAMTHONGHOI.SP_DELETE_BULK",
                params
        );

        return Boolean.TRUE;
    }


}

