package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
// import vn.vnpt.vnptit.ecms.dto.SubnetRequestDto;
// import vn.vnpt.vnptit.ecms.dto.hatang.RequestVlanDto;

import vn.vnpt.vnptit.ecms.dto.idc.DaiIPDto;

@Service
public class DaiIPService {
    @Autowired
    DbContext dbContext;

    public ArrayList<Map<String, Object>> layDsTrangThai()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SP_GET_DS_TRANGTHAI_DAIIP", parameters);
    }
    
    public ArrayList<Map<String, Object>> layDsSubnetMark()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SP_GET_DS_SUBNETMASK", parameters);
    }
    
    public ArrayList<Map<String, Object>> lay_dsLoaiIP()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SP_GET_DS_LOAIIP", parameters);
    }
    
    public ArrayList<Map<String, Object>> lay_dsIdc_DonviId(Integer input)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        parameters.add(new SqlParameter("P_DONVI_ID", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SP_GET_DS_IDC", parameters);
    }
    
    public ArrayList<Map<String, Object>> lay_dsdonviIdc()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SP_GET_DS_IDC_DONVI", parameters);
    }

    public ArrayList<Map<String, Object>> laydsDaiIP_DonviId(Integer input)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        parameters.add(new SqlParameter("P_DONVI_ID", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SP_GET_DS_DAIIP_DONVIID", parameters);
    }
    
    public ArrayList<Map<String, Object>> laydsDaiIPCha()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SP_GET_DS_DAIIPCHA", parameters);
    }

    public ArrayList<Map<String, Object>> layDsDaiIP()  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SP_GET_DS_DAIIP", parameters);
    }

    public ArrayList<Map<String, Object>> layDsDaiIP_con(Integer input)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_ID", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SP_GET_DS_DAIIP_CON", parameters);
    }

    public ArrayList<Map<String, Object>> lay_chitiet_DaiIP(Integer input)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_ID", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.CHITIET_DAIIP", parameters);
    }
    
    public Object updateDaiIP(DaiIPDto daiIPDto ) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        // params.add(new SqlParameter("P_ID", daiIPDto.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        Integer id = daiIPDto.getId();
        params.add(new SqlParameter("P_ID", (id == null || id == 0) ? null : id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CHA_ID", daiIPDto.getIdCha(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_IP", daiIPDto.getIp_loai_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHAN_LOAI_ID", daiIPDto.getIp_phanloai_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IPSTORE_TEN", daiIPDto.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IPSTORE_KYHIEU", daiIPDto.getKyhieu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IP_DAU", daiIPDto.getIpDau(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IP_CUOI", daiIPDto.getIpCuoi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SUBNETMASK_ID", daiIPDto.getSubnet_mask_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GATEWAY", daiIPDto.getGateway(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_IDC_ID", daiIPDto.getIdc_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CANCU_THUCHIEN", daiIPDto.getCancuTH(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TRANGTHAI_ID", daiIPDto.getTrangthai_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        // System.out.print(daiIPDto.getTrangthai_id());
        java.sql.Date sqlDate = (daiIPDto.getThoidiemKhaibao() != null) ? new java.sql.Date(daiIPDto.getThoidiemKhaibao().getTime()) : null;
        params.add(new SqlParameter("P_THOIDIEM_KHAIBAO", sqlDate, SqlParameter.ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("P_DONVI_TH_ID", daiIPDto.getDonvi_th_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", daiIPDto.getNguoiCapnhat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_GHI_CHU", daiIPDto.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        // params.add(new SqlParameter("p_identifycode",  AppRequestContext.getCurrent().getIdentityCode(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToEntity("ECMS.PKG_IDC_DAIIP.SP_DAIIP_UPDATE_NEW", params);
    }

    public Boolean deleteDaiIP(Integer input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
        parameters.add(new SqlParameter("P_ID", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SP_DAIIP_XOA", parameters);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    public ArrayList<Map<String, Object>> searchDaiIP(DaiIPDto daiIPDto)  throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_IDC_ID", daiIPDto.getIdc_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", daiIPDto.getDonvi_th_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIIP_ID", daiIPDto.getIp_loai_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SEARCH_DAIIP", params);
    }

    
    public ArrayList<Map<String, Object>> laylichsu_tacdong(Integer input)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_ID", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));        
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_DAIIP.SP_GET_LS_TACDONG_DAIIP", parameters);
    }
}
