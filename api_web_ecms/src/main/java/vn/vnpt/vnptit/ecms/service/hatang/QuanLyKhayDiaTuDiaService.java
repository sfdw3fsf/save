package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.KhayDiaTrongTuDiaDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuanLyKhayDiaTuDiaService {
    @Autowired
    private DbContext dbContext;

    public Object createOrUpdate(KhayDiaTrongTuDiaDTO dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", dto.getThietbi_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_mathietbi", dto.getMathietbi(), Types.NVARCHAR));
        params.add(new SqlParameter("p_sothutu", dto.getSothutu(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_kyhieu", dto.getKyhieu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_serial_number", dto.getSerial_number(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_mgmt_id", dto.getIp_mgmt_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ip_controller_id", dto.getIp_controller_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", dto.getGhi_chu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", dto.getHieu_luc(), Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_tong", dto.getHdd_tong(), Types.NUMERIC));
        params.add(new SqlParameter("p_hdd_capphat", dto.getHdd_capphat(), Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_tong", dto.getSsd_tong(), Types.NUMERIC));
        params.add(new SqlParameter("p_ssd_capphat", dto.getSsd_capphat(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthaisd", dto.getTrangthaisd(), Types.NUMERIC));
        params.add(new SqlParameter("p_maduan", dto.getMaduan(), Types.NVARCHAR));
        params.add(new SqlParameter("p_tenduan", dto.getTenduan(), Types.NVARCHAR));
        params.add(new SqlParameter("p_sothe", dto.getSothe(), Types.NVARCHAR));
        params.add(new SqlParameter("p_donviqly_id", dto.getDonviqly_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_phongbanqly_id", dto.getPhongbanqly_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoiqly_id", dto.getNguoiqly_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", dto.getNhanvien_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_may_capnhap", dto.getMay_capnhap(), Types.NVARCHAR));
        params.add(new SqlParameter("p_nguoi_capnhap", dto.getNguoi_capnhap(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_capnhap", dto.getIp_capnhap(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_KHAY_DIA_INSERT_UPDATE", params);
        return result.getOutValue();
    }

    public Object getDetailById(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_KHAY_DIA_GET_DETAIL_BY_ID", params);
        List<Map<String, Object>> result = dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
        return result;
    }

    public Object getListByThietBiId(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_KHAY_DIA_GETALL_BY_THIETBI_ID", params);
        List<Map<String, Object>> result = dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
        return result;
    }

    public Object deleteById(int id) {
        try {
            List<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id", id, Types.NUMERIC));
            params.add(new SqlParameter("p_table_name", "KHAYDIA", Types.NVARCHAR));
            params.add(new SqlParameter("p_result", Types.NUMERIC));
            dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_DELETE_BY_ID", params);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Object getLichSuById(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> dbResult = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_KHAY_DIA_GET_LICHSU_BY_ID", params);
        List<Map<String, Object>> result = dbResult.stream()
                .map(map -> map.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> entry.getKey().toLowerCase(),
                                Map.Entry::getValue
                        )))
                .collect(Collectors.toList());
        return result;
    }

}
