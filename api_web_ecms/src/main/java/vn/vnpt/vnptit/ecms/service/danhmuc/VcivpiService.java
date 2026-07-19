package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.VcivpiDto;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class VcivpiService {
    private final DbContext dbContext;

    public VcivpiService(DbContext dbContext)
    {
        this.dbContext = dbContext;
    }
    public ArrayList<Map<String, Object>> laydsDonViQL(int phanVungId,int loaidv_id) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIDV_ID", loaidv_id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_VCI_VPI.SP_DS_DONVI_QL", params);

    }

    public ArrayList<Map<String, Object>> laydsTramThiCong(int phanVungId, VcivpiDto.TramThiCong input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_CHA", input.getDonvi_cha_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIDV_ID", input.getLoaidv_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_VCI_VPI.SP_DS_TO_THICONG", params);

    }
    public ArrayList<Map<String, Object>> laydsTramTBi(int phanVungId, VcivpiDto.TramThiCong input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_CHA", input.getDonvi_cha_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIDV_ID", input.getLoaidv_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_VCI_VPI.SP_DS_TRAM_TBI", params);
    }

    public ArrayList<Map<String, Object>> laydsDsLam(int phanVungId, int donvi_cha) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_CHA", donvi_cha, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_VCI_VPI.SP_DS_DSLAM", params);
    }

    public ArrayList<Map<String, Object>> laydsVciVpi(int phanVungId, int dslamid) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", dslamid, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_VCI_VPI.SP_DS_VCI_VPI_SEL", params);
    }

    public Map<String, Object> themVciVpi(int phanVungId, VcivpiDto.VciVpiIns input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VCI", input.getVci(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VPI", input.getVpi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAMID", input.getDslamid(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MAYCN", (AppRequestContext.getCurrent().getClientName()==null?"":AppRequestContext.getCurrent().getClientName()), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGAYCN", LocalDate.now().format( DateTimeFormatter.ofPattern("dd/MM/YYYY")), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOICN", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return layDoiTuong(dbContext.executeSpWithCursorToListMap("ECMS.PKG_VCI_VPI.SP_VCI_VPI_INS", params));
    }

    public boolean capNhatVciVpi(int phanVungId, VcivpiDto.VciVpiUpd input) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VCI_VPI_ID", input.getVcivpi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VCI", input.getVci(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VPI", input.getVpi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAMID", input.getDslamid(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MAYCN", (AppRequestContext.getCurrent().getClientName()==null?"":AppRequestContext.getCurrent().getClientName()), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGAYCN", LocalDate.now().format( DateTimeFormatter.ofPattern("dd/MM/YYYY")), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOICN", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return layKetQua(dbContext.executeSpWithCursorToListMap("ECMS.PKG_VCI_VPI.SP_VCI_VPI_UPD", params));
    }
    public Map<String, Object>  layVciVpiById(int phanVungId, int vcivpiId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VCI_VPI_ID", vcivpiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return layDoiTuong(dbContext.executeSpWithCursorToListMap("ECMS.PKG_VCI_VPI.SP_VCI_VPI_DETAIL", params));
    }

    public boolean  xoaVciVpiBy(int phanVungId, int vcivpiId) throws AppSqlException
    {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VCI_VPI_ID", vcivpiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return layKetQua(dbContext.executeSpWithCursorToListMap("ECMS.PKG_VCI_VPI.SP_VCI_VPI_DEL", params));
    }

    public Map<String, Object> layDoiTuong(ArrayList<Map<String, Object>> object){
        return  object!= null && object.size()>0 ?object.get(0):null;
    }
    public boolean layKetQua(ArrayList<Map<String, Object>> object){
        return  object!= null && object.size()>0 ?true:false;
    }
}
