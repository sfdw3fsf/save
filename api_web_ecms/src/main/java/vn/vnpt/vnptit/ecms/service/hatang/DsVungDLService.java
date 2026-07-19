package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import vn.vnpt.vnptit.ecms.dto.hatang.DongBoInputDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.SearchVungDLDTO;
import vn.vnpt.vnptit.ecms.dto.sync.vcenter.SyncDatastoreInfoDTO;
import vn.vnpt.vnptit.ecms.service.idc.VCenterService;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DsVungDLService {
    private final DbContext dbContext;
    private final VCenterService vCenterService;

    DsVungDLService(DbContext dbContext, VCenterService vCenterService) {
        this.dbContext = dbContext;
        this.vCenterService = vCenterService;
    }

    public List<Map<String, Object>> getDsach(SearchVungDLDTO input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_vung", input.getMaVung(), Types.NVARCHAR));
        params.add(new SqlParameter("p_loaivung_id", input.getLoaiVungId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten_vung", input.getTenVung(), Types.NVARCHAR));
        params.add(new SqlParameter("p_loaiht_id", input.getLoaiHtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_hatanglt_id", input.getHatangLtId(), Types.NUMERIC));
        params.add(new SqlParameter("p_vung_tudia_id", input.getVungTuDiaId(), Types.NUMERIC));
        params.add(new SqlParameter("p_loai_ocung_id", input.getLoaiOCungId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ip_host", input.getIpHost(), Types.NVARCHAR));
        params.add(new SqlParameter("p_trangthai_id", input.getTrangthaiId(), Types.NUMERIC));
        params.add(new SqlParameter("p_hieuluc_id", input.getHieuLucId(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_ql_id", input.getDonViQlId(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_ql", input.getNguoiQl(), Types.NVARCHAR));
        params.add(new SqlParameter("p_cursor", Types.REF_CURSOR));
        List<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_NHDU.SP_VUNG_DL_GET_DSACH", params);
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

    public Boolean delete(Number id) throws AppSqlException{
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        SqlParameter rs = new SqlParameter("p_result", Types.NUMERIC);
        params.add(rs);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_NHDU.SP_VUNG_DL_DELELTE",params);
        return rs.getOutValue().toString().equals("1");
    }

    public Boolean syncVungDl(DongBoInputDTO input) throws AppSqlException{
        String datastoreCode = input.getCode();
        Long vungDlId = input.getId();
        String nguoiCn = AppRequestContext.getCurrent().getUserName();

        Object rawData;
        try {
            rawData = vCenterService.getDatastoreDetail(datastoreCode);
        } catch (RuntimeException e) {
            String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
            if (msg.contains("404") || msg.contains("not found")) {
                this.delete(vungDlId);
                throw new RuntimeException("Dữ liệu không tồn tại để đồng bộ. Bản ghi đã được hệ thống tự động xóa khỏi hệ thống!!");
            }
            throw e;
        }
        // mapper dữ liệu
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SyncDatastoreInfoDTO datastore;
        try {
            datastore = mapper.convertValue(rawData, SyncDatastoreInfoDTO.class);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Dữ liệu trả về từ gateway không hợp lệ", e);
        }

        String maVungDl = datastore.getCode();
        String tenVungDl = datastore.getName();
        // Không cần chia gbFactor vì dữ liệu trả về từ Gateway đã ở dạng GB (VD: 59615.75)
        double dlThucte = datastore.getCapacity() != null ? datastore.getCapacity() : 0;
        double dlTrong = datastore.getFreeSpace() != null ? datastore.getFreeSpace() : 0;

        String jsonData;
        try {
            if (datastore.getHostList() == null || datastore.getHostList().isEmpty()) {
                jsonData = "[]";
            } else {
                jsonData = JsonConvert.serialize(datastore.getHostList());
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi serialize danh sách thiết bị", e);
        }
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ma_vungdl", maVungDl, Types.CHAR));
        params.add(new SqlParameter("p_ten_vungdl", tenVungDl, Types.CHAR));
        params.add(new SqlParameter("p_dl_thucte", dlThucte, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_trong", dlTrong, Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_list", jsonData, Types.CHAR));
        params.add(new SqlParameter("p_nguoi_cn", jsonData, Types.NVARCHAR));
        SqlParameter rs = new SqlParameter("p_result",nguoiCn, Types.NUMERIC);
        params.add(rs);
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_NHDU.SP_VUNG_DL_SYNC" ,params);
        return rs.getOutValue().toString().equals("1");
    }
}
