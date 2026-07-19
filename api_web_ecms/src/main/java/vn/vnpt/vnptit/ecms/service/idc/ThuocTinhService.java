package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.ThuocTinhDTO;

@Service
public class ThuocTinhService {
    @Autowired
    private DbContext dbContext;

    // ==== thuộc tính

    public Object getThuocTinh(String kieuTT, Long duLieuId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_kieu_tt", kieuTT, Types.VARCHAR));
        params.add(new SqlParameter("p_dulieu_id", duLieuId, Types.NUMERIC));
        SqlParameter out = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(out);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THUOCTINH.SP_GET_DS_THUOCTINH", params);
        return out.getOutValue();
    }

    public Object insertThuocTinh(ThuocTinhDTO dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_kieu_tt", dto.getKieuTT(), Types.VARCHAR));
        params.add(new SqlParameter("p_dulieu_id", dto.getDuLieuId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.VARCHAR));
        params.add(new SqlParameter("p_giatri", dto.getGiaTri(), Types.VARCHAR));
        params.add(new SqlParameter("p_thutu", dto.getThuTu(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));

        SqlParameter pResult = new SqlParameter("p_result", Types.NUMERIC);
        params.add(pResult);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THUOCTINH.SP_THUOCTINH_INSERT", params);

        return pResult.getOutValue();
    }

    public Object updateThuocTinh(Long thuocTinhId, ThuocTinhDTO dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thuoctinh_id", thuocTinhId, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.VARCHAR));
        params.add(new SqlParameter("p_giatri", dto.getGiaTri(), Types.VARCHAR));
        params.add(new SqlParameter("p_thutu", dto.getThuTu(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));

        SqlParameter pResult = new SqlParameter("p_result", Types.NUMERIC);
        params.add(pResult);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THUOCTINH.SP_THUOCTINH_UPDATE", params);

        return pResult.getOutValue();
    }

    public Object deleteThuocTinh(Long thuocTinhId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thuoctinh_id", thuocTinhId, Types.NUMERIC));

        SqlParameter pResult = new SqlParameter("p_result", Types.NUMERIC);
        params.add(pResult);

        dbContext.executeStoredProcedure("ECMS.PKG_IDC_THUOCTINH.SP_THUOCTINH_DELETE", params);

        return pResult.getOutValue();
    }
}
