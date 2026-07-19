package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.ThuocTinhDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SoDoMatSanService {
    private final DbContext dbContext;

    public SoDoMatSanService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getMatSanDetail(Long matSanId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_matsan_id", matSanId, Types.NUMERIC));
        SqlParameter out = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(out);

        dbContext.executeStoredProcedure("ECMS.PKG_TAINGUYEN_KHONGGIAN.SP_GET_MATSAN_DETAIL", params);
        return mapFirstRow(out.getOutValue());
    }
    public Object getListMatSanByToaNha(Long toaNhaId) throws AppSqlException{
        List<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_toanha_id", toaNhaId, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_TAINGUYEN_KHONGGIAN.SP_GET_MATSAN_BY_TOANHA", params);
        return result.getOutValue();
    }
    public Object getNangLucKhongGian(Long matSanId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_matsan_id", matSanId, Types.NUMERIC));
        SqlParameter out = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(out);

        dbContext.executeStoredProcedure("ECMS.PKG_TAINGUYEN_KHONGGIAN.SP_GET_NANGLUC_KHONGGIAN", params);
        return mapFirstRow(out.getOutValue());
    }
    public Object getListPhongByMatSan(Long matSanId) throws AppSqlException{
        List<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_matsan_id", matSanId, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_TAINGUYEN_KHONGGIAN.SP_GET_DS_PHONG_BY_MATSAN", params);
        return result.getOutValue();
    }
    public Object getNangLucDien(Long matSanId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_matsan_id", matSanId, Types.NUMERIC));
        SqlParameter out = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(out);

        dbContext.executeStoredProcedure("ECMS.PKG_TAINGUYEN_KHONGGIAN.SP_GET_NANGLUC_DIEN_MATSAN", params);
        return mapFirstRow(out.getOutValue());
    }
    private Object mapFirstRow(Object outVal) {
        if (outVal instanceof List<?>) {
            List<?> rows = (List<?>) outVal;
            if (!rows.isEmpty() && rows.get(0) instanceof Map<?, ?>) {
                return rows.get(0);
            }
        }
        return null;
    }
    public Object getPhongZoneDetail(Long phongId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phong_id", phongId, Types.NUMERIC));
        SqlParameter out = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(out);

        dbContext.executeStoredProcedure("ECMS.PKG_TAINGUYEN_KHONGGIAN.SP_GET_PHONGZONE_DETAIL_FR_SDMS", params);
        return mapFirstRow(out.getOutValue());
    }
    //THUOC TINH
    public Object getThuocTinhMatSan(Long matSanId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_kieu_tt", "MATSAN", Types.VARCHAR));
        params.add(new SqlParameter("p_dulieu_id", matSanId, Types.NUMERIC));
        SqlParameter out = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(out);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THUOCTINH.SP_GET_DS_THUOCTINH", params);
        return out.getOutValue();
    }

    public ApiResult insertThuocTinh(Long matSanId, ThuocTinhDTO dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_kieu_tt", "MATSAN", Types.VARCHAR));
        params.add(new SqlParameter("p_dulieu_id", matSanId, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.VARCHAR));
        params.add(new SqlParameter("p_giatri", dto.getGiaTri(), Types.VARCHAR));
        params.add(new SqlParameter("p_thutu", dto.getThuTu(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));

        SqlParameter pResult = new SqlParameter("p_result", Types.NUMERIC);
        params.add(pResult);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THUOCTINH.SP_THUOCTINH_INSERT", params);

        return buildResult(pResult.getOutValue(), "Thêm", null);
    }

    public ApiResult updateThuocTinh(Long thuocTinhId, ThuocTinhDTO dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thuoctinh_id", thuocTinhId, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.VARCHAR));
        params.add(new SqlParameter("p_giatri", dto.getGiaTri(), Types.VARCHAR));
        params.add(new SqlParameter("p_thutu", dto.getThuTu(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));

        SqlParameter pResult = new SqlParameter("p_result", Types.NUMERIC);
        params.add(pResult);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THUOCTINH.SP_THUOCTINH_UPDATE", params);

        return buildResult(pResult.getOutValue(), "Cập nhật", dto);
    }
    public ApiResult deleteThuocTinh(Long thuocTinhId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thuoctinh_id", thuocTinhId, Types.NUMERIC));

        SqlParameter pResult = new SqlParameter("p_result", Types.NUMERIC);
        params.add(pResult);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THUOCTINH.SP_THUOCTINH_DELETE", params);

        return buildResult(pResult.getOutValue(), "Xoá", thuocTinhId);
    }

    private ApiResult buildResult(Object outVal, String actionName, Object dataOnSuccess) {
        int code = -1;
        if (outVal != null) {
            try {
                code = Integer.parseInt(outVal.toString());
            } catch (NumberFormatException ignored) {}
        }

        ApiResult res = new ApiResult();

        if (code > 0 || code == 1) {
            // Thành công
            res.setError("200");
            res.setErrorCode("BSS-00000000");
            res.setMessage(actionName + " thuộc tính thành công");
            res.setData(code > 1 ? code : dataOnSuccess);
        } else {
            // Thất bại
            res.setError("500");
            res.setErrorCode("BSS-ERROR");
            res.setMessage("Có lỗi xảy ra trong quá trình " + actionName.toLowerCase() + " thuộc tính");
            res.setData(null);
        }

        return res;
    }



}