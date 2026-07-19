package vn.vnpt.vnptit.ecms.service;


import java.sql.Types;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

@Service
public class WebRegistryService {

    private final DbContext dbContext;

    public WebRegistryService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getValue(String keyName) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_keyname", keyName, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        SqlParameter resultSqlParameter = new SqlParameter("p_result", Types.NVARCHAR);
        params.add(resultSqlParameter);
        dbContext.executeStoredProcedure("COMMON.PKG_WEB_REGISTRY_CONFIGURATION.sp_get_value", params);
        return resultSqlParameter.getOutValue();
    }
    
    public Boolean setValue(String keyName, String value) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_keyname", keyName, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("p_value", value, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        SqlParameter resultSqlParameter = new SqlParameter("p_result", Types.INTEGER);
        params.add(resultSqlParameter);
        dbContext.executeStoredProcedure("COMMON.PKG_WEB_REGISTRY_CONFIGURATION.sp_set_value", params);
        return Integer.parseInt(resultSqlParameter.getOutValue().toString()) == 1;
    }

}
