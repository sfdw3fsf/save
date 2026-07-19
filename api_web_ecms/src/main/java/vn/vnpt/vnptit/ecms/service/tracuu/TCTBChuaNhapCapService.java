package vn.vnpt.vnptit.ecms.service.tracuu;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.CapGocDtoIn;
import vn.vnpt.vnptit.ecms.dto.LichSuTacDongCapTheoMaTB;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class TCTBChuaNhapCapService {
    private final DbContext dbContext;

    public TCTBChuaNhapCapService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> loadTreeView() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        return dbContext.executeSpWithCursorToListMap("ECMS.TCTB_CHUA_NHAP_CAP.GET_DS_DON_VI", param);
    }

    public ArrayList<Map<String, Object>> lay_lichsu_tacdong_cap_theo_ma_tb(LichSuTacDongCapTheoMaTB lichSuTacDongCapTheoMaTB) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("vma_tb", lichSuTacDongCapTheoMaTB.getVma_tb() != null ? lichSuTacDongCapTheoMaTB.getVma_tb() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("vtungay", lichSuTacDongCapTheoMaTB.getVtungay() != null ? lichSuTacDongCapTheoMaTB.getVtungay().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("vdenngay", lichSuTacDongCapTheoMaTB.getVdenngay() != null ? lichSuTacDongCapTheoMaTB.getVdenngay().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.TCTB_CHUA_NHAP_CAP.lay_lichsu_tacdong_cap_theo_ma_tb", parameters);
    }

    public ArrayList<Map<String, Object>> dsThueBaoChuaNhapCap() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("VDONVI_ID", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("VNHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.LAY_DS_THUEBAO_CHUA_NHAPCAP_V3", parameters);
    }

    public ArrayList<Map<String, Object>> layDsCapGoc(int capGocId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_capgoc_id", capGocId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.TCTB_CHUA_NHAP_CAP.LAY_DS_CAPGOC", parameters);
    }

    public Map<String, Object> themCapGoc(CapGocDtoIn capGocDtoIn) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ArrayList<Map<String, Object>> rs;
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_tu_doi", capGocDtoIn.getTuDoi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_den_doi", capGocDtoIn.getDenDoi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        rs= dbContext.executeSpWithCursorToListMap("ECMS.TCTB_CHUA_NHAP_CAP.THEM_CAP_GOC_INS", parameters);
        return rs!=null &&rs.size()>0?rs.get(0):null;
    }

    public Map<String, Object> capNhatCapGoc(CapGocDtoIn capGocDtoIn) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ArrayList<Map<String, Object>> rs;
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cap_id", capGocDtoIn.getCapGocId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_tu_doi", capGocDtoIn.getTuDoi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_den_doi", capGocDtoIn.getDenDoi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        rs= dbContext.executeSpWithCursorToListMap("ECMS.TCTB_CHUA_NHAP_CAP.CAPNHAT_CAP_GOC_UPD", parameters);
        return rs!=null &&rs.size()>0?rs.get(0):null;
    }

    public Map<String, Object> xoaCapGoc(int capGocId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ArrayList<Map<String, Object>> rs;
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cap_id", capGocId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        rs= dbContext.executeSpWithCursorToListMap("ECMS.TCTB_CHUA_NHAP_CAP.XOA_CAPGOC_DEL", parameters);
        return rs!=null &&rs.size()>0?rs.get(0):null;
    }
}
