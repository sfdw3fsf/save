package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.CumSmartCloudDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service("idcSmartCloudService")
public class CumSmartCloudService {
    private final DbContext dbContext;

    public CumSmartCloudService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getDanhSachCumSmartCloud(CumSmartCloudDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        
        params.add(new SqlParameter("P_MA_HATANG", dto.getMaHatang(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TEN_HATANG", dto.getTenHatang(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IDC_ID", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HIEULUC_ID", dto.getHieulucId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HATANG_MANG_ID", dto.getHatangMangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HINHTHUC_DAUTU_ID", dto.getHinhthucDauTuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHOM_HATANG_ID", dto.getNhomHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_HATANG_ID", dto.getLoaiHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GOI_HATANG_ID", dto.getGoiHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CONGNGHE_TRIENKHAI_ID", dto.getCongngheTrienkhaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANLOAI_HATANG_ID", dto.getPhanloaiHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CAPDO_HATANG_ID", dto.getCapdoHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_CCDV_ID", dto.getTrangthaiCcdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_QL_ID", dto.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CANHAN_QL", dto.getCanhanQl(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_DS_CUMHT", params);
    }

    public Map<String, Object> deleteCumSmartCloud(Number cumSmartCloudId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cumSmartCloud_id", cumSmartCloudId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_DELETE_CUMHT", params);
    }

    public Map<String, Object> syncAndSaveFromGateway(CumSmartCloudDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        
        params.add(new SqlParameter("P_MA_HATANG", dto.getMaHatang(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TEN_HATANG", dto.getTenHatang(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IDC_ID", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HIEULUC_ID", dto.getHieulucId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HATANG_MANG_ID", dto.getHatangMangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HINHTHUC_DAUTU_ID", dto.getHinhthucDauTuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHOM_HATANG_ID", dto.getNhomHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_HATANG_ID", dto.getLoaiHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GOI_HATANG_ID", dto.getGoiHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CONGNGHE_TRIENKHAI_ID", dto.getCongngheTrienkhaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANLOAI_HATANG_ID", dto.getPhanloaiHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CAPDO_HATANG_ID", dto.getCapdoHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_CCDV_ID", dto.getTrangthaiCcdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_QL_ID", dto.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CANHAN_QL", dto.getCanhanQl(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_UPSERT_CUMSMARTCLOUD", params);
    }

    public ArrayList<Map<String, Object>> getChiTietCumHT(Number cumHtId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_CUMHT_ID", cumHtId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_CHITIET_CUMHT", params);
    }

    public ArrayList<Map<String, Object>> getNangLucCumHT(Number cumHtId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_CUMHT_ID", cumHtId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_NANGLUC_CUMHT", params);
    }

    public ArrayList<Map<String, Object>> getNangLucLuuTruCumHT(CumSmartCloudDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_CUMHT_ID", dto.getCumHTId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_HATANG_ID", dto.getLoaiHatangId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_GET_NANGLUC_LUUTRU_CUMHT", params);
    }
}
