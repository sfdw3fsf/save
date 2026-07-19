package vn.vnpt.vnptit.ecms.service.hatang;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.DongBoInputDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchCHTAoHoaDTO;
import vn.vnpt.vnptit.ecms.dto.sync.vcenter.SyncClusterInfoDTO;
import vn.vnpt.vnptit.ecms.service.idc.VCenterService;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DsCumHTAoHoaVMWareService {
    private final DbContext dbContext;
    private final VCenterService vCenterService;

    public DsCumHTAoHoaVMWareService(DbContext dbContext, VCenterService vCenterService){
        this.dbContext = dbContext;
        this.vCenterService = vCenterService;
    }

    public List<Map<String, Object>> getDsach(SearchCHTAoHoaDTO input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_macum_ht", input.getMaCumht(), Types.NVARCHAR));
        params.add(new SqlParameter("p_tencum_ht", input.getTenCumht(), Types.NVARCHAR));
        params.add(new SqlParameter("p_nhomht_id", input.getNhomhtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_phanloaiht_id", input.getPhanloaihtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_donviql_id", input.getDonviQlId(), Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", input.getIdcId(), Types.NUMERIC));
        params.add(new SqlParameter("p_htm_id", input.getHtmId(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaiht_id", input.getLoaihtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_capdoht_id", input.getCapdohtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_hieuluc_id", input.getHieuLucId(), Types.NUMERIC));
        params.add(new SqlParameter("p_hinhthucdt_id", input.getHinhthucdtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_congnghe_id", input.getCongngheId(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthaiccdv_id", input.getTrangthaiccdvId(), Types.NUMERIC));
        params.add(new SqlParameter("p_canhan_ql", input.getCanhanQl(), Types.NVARCHAR));
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));
        List<Map<String,Object>> dbResult =  dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_NHDU.SP_CUMHATANG_VMWARE_GET_DSACH", params);
        return dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
    }

    public Boolean delete(Number vmwareId) throws AppSqlException{
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", vmwareId, Types.NUMERIC));
        SqlParameter rs = new SqlParameter("p_result", Types.NUMERIC);
        params.add(rs);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_NHDU.SP_CUMHATANG_VMWARE_DELETE", params);
        return rs.getOutValue().toString().equals("1");
    }

    public Boolean sync(DongBoInputDTO input) throws AppSqlException{
        String clusterCode = input.getCode();
        Long cumVMId = input.getId();
        String jsonData = AppRequestContext.getCurrent().getUserName();
        Object rawData;
        try {
            rawData = vCenterService.getDatastoreDetail(clusterCode);
        } catch (RuntimeException e) {
            String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
            if (msg.contains("404") || msg.contains("not found")) {
                this.delete(cumVMId);
                throw new RuntimeException("Cụm hạ tầng VMWare " + clusterCode + " không tồn tại.");
            }
            throw e;
        }
        //mapper dữ liệu
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SyncClusterInfoDTO cluster;
        try{
            cluster = mapper.convertValue(rawData, SyncClusterInfoDTO.class);
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Dữ liệu trả về từ gateway không hợp lệ ");
        }

        String maCumHt = cluster.getCode();
        String tenCumht = cluster.getName();
        Object thietBiList = cluster.getHostList();
        Object vungDlList = cluster.getDatastoreList();
        Object mayAoList = cluster.getVmwareList();

        String jsonThietBi;
        String jsonVungDl;
        String jsonMayAo;
        try {
            jsonThietBi = JsonConvert.serialize(thietBiList);
            jsonVungDl = JsonConvert.serialize(vungDlList);
            jsonMayAo = JsonConvert.serialize(mayAoList);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi serialize danh sách các dữ liệu", e);
        }

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_cumht", maCumHt, Types.CHAR));
        params.add(new SqlParameter("p_ten_cumht", tenCumht, Types.CHAR));
        params.add(new SqlParameter("p_thietbi_list", jsonThietBi, Types.CHAR));
        params.add(new SqlParameter("p_vungdl_list", jsonVungDl, Types.CHAR));
        params.add(new SqlParameter("p_mayao_list", jsonMayAo, Types.CHAR));
        params.add(new SqlParameter("p_nguoi_cn", jsonData, Types.NVARCHAR));
        SqlParameter rs = new SqlParameter("p_result", Types.NUMERIC);
        params.add(rs);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_NHDU.SP_CUMHT_AOHOA_VMWARE_SYNC" ,params);
        return rs.getOutValue().toString().equals("1");
    }
}
