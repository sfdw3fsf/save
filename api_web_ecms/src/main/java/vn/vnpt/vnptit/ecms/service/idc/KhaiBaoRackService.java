package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.DsKhaiBaoRackThiCongRequestDto;

import java.sql.Types;
import java.util.ArrayList;

@Service
public class KhaiBaoRackService {
    private final DbContext dbContext;

    public KhaiBaoRackService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getDmLoaiKhaiBao() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_PHUCLH.SP_GET_DM_LOAI_KHAIBAO", params);
    }

    public Object getDmDonViQl() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_PHUCLH.SP_GET_DM_DONVI_QL", params);
    }

    public Object getDmSoHuu() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_PHUCLH.SP_GET_DM_SOHUU", params);
    }

    public Object getDmToaNha(Long idcId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_idc_id", idcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_PHUCLH.SP_GET_DM_TOANHA", params);
    }

    public Object getDmMatSan(Long idcId, Long toanhaId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_idc_id", idcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_toanha_id", toanhaId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_PHUCLH.SP_GET_DM_MATSAN", params);
    }

    public Object getDmPhong(Long matsanId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_matsan_id", matsanId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_PHUCLH.SP_GET_DM_PHONG", params);
    }

    public Object getChiTietRack(Long idChitiet) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id_chitiet", idChitiet, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_PHUCLH.SP_GET_CHITIET_RACK", params);
    }

    public Object getDsRackThayThe(Long idcId, Long rackId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_idc_id", idcId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rack_id", rackId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_PHUCLH.SP_GET_DS_RACK_THAYTHE", params);
    }

    public Object getDsKhaiBaoRackThiCong(DsKhaiBaoRackThiCongRequestDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieu_id", dto.getPhieuId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_khaibao", dto.getLoaiKhaibao(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma_rack", dto.getMaRack(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_sohuu_id", dto.getSohuuId(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_idc_id", dto.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_serial", dto.getSerial(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dvql_id", dto.getDvqlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_khonggian", dto.getKhonggian(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_id_chitiet", dto.getIdChitiet(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_canhan_ql", dto.getCanhanQl(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_PHUCLH.SP_GET_DS_KHAIBAO_RACK_THICONG", params);
    }
}
