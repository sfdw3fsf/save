package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.CapNhatModuleDtoInput;
import vn.vnpt.vnptit.ecms.dto.ModuleDtoInput;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ModuleService {
    private final DbContext dbContext;

    public ModuleService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> layDanhSachModule() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("phanVungId", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_MODULE.SP_ALL_MODULE_LIST", params);
    }

    public ArrayList<Map<String, Object>> layModuleTheoId(Integer id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MODULE.SP_ALL_MODULE_LIST_BY_ID", params);
    }

    public Map<String, Object> taoModule(ModuleDtoInput moduleDtoInput) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_module", moduleDtoInput.getLoai_module(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_port", moduleDtoInput.getSo_port(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_bd", moduleDtoInput.getPort_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> list = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MODULE.SP_MODULE_INS", params);
        return list.get(0);
    }


    public boolean capNhatModule(CapNhatModuleDtoInput moduleDtoInput) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_loai_module_id", moduleDtoInput.getLoai_module_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_module", moduleDtoInput.getLoai_module(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_so_port", moduleDtoInput.getSo_port(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_port_bd", moduleDtoInput.getPort_bd(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> kq =  dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MODULE.SP_MODULE_UPD", params);
        return "1".equals(kq.get(0).get("KETQUA").toString());
    }

    public boolean xoaModule(int loaiModuleId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id",AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_module_id", String.valueOf(loaiModuleId), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> kq =  dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MODULE.SP_MODDULE_DEL", params);
        return "1".equals(kq.get(0).get("KETQUA").toString());
    }

}
