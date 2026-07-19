package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.KiemTraTrungViTriModuleManeDtoInput;
import vn.vnpt.vnptit.ecms.dto.ManeModuleDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ManeModuleService {
    private final DbContext dbContext;

    public ManeModuleService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getManeModule(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_MODULEMANE_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MANE_MODULE.SP_LAY_THONG_TIN_MANE_MODULE", params);
        return rs.size() > 0 ? rs.get(0) : null;
    }

    public List<Map<String, Object>> getDsLoaiModule() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MANE_MODULE.SP_LAY_DS_LOAI_MODULE", params);
    }

    public Object insert(ManeModuleDtoIn data) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_CARDMANE_ID", data.getCardmane_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI", data.getVitri(), Types.NUMERIC));
        params.add(new SqlParameter("P_SERIAL", data.getSerial(), Types.VARCHAR));
        params.add(new SqlParameter("P_PARTNUMBER", data.getPartnumber(), Types.VARCHAR));
        params.add(new SqlParameter("P_LOAIMODULE_ID", data.getLoaimodule_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MANE_MODULE.SP_MODULE_MANE_INS", params);
    }

    public Object update(ManeModuleDtoIn.ManeModuleDtoInUpd data) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_MODULEMANE_ID", data.getModulemane_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_CARDMANE_ID", data.getCardmane_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI", data.getVitri(), Types.NUMERIC));
        params.add(new SqlParameter("P_SERIAL", data.getSerial(), Types.VARCHAR));
        params.add(new SqlParameter("P_PARTNUMBER", data.getPartnumber(), Types.VARCHAR));
        params.add(new SqlParameter("P_LOAIMODULE_ID", data.getLoaimodule_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> kq = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MANE_MODULE.SP_MODULE_MANE_UPD", params);
        return "1".equals(kq.get(0).get("KETQUA").toString());
    }

    public Object delete(Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_MODULEMANE_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MANE_MODULE.SP_MODULE_MANE_DEL", params);
        return "1".equals(rs.get(0).get("KETQUA").toString());
    }

    public List<Map<String, Object>> create_port(Integer id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_MODULEMANE_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MANE_MODULE.TAO_PORT_DSL_MODULE_MANE", params);
    }

    public Boolean kiemTraTrungVitri(KiemTraTrungViTriModuleManeDtoInput input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_CARDMANE_ID", input.getCardmane_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI", input.getVitri(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MANE_MODULE.SP_KIEMTRA_TRUNG_VITRI_MODULE", params);
        return "1".equals(rs.get(0).get("KQ").toString());
    }

    public Boolean kiemTraTrungSerial(String serial) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_SERIAL", serial, Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_MANE_MODULE.SP_KIEMTRA_SERIAL", params);
        return "1".equals(rs.get(0).get("KQ").toString());
    }
}
