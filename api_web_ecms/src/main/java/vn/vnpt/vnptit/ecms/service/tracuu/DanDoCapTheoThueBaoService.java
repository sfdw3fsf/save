package vn.vnpt.vnptit.ecms.service.tracuu;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.TraCuuDoDanCapTBTheoSoMay;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DanDoCapTheoThueBaoService {
    private final DbContext dbContext;

    public DanDoCapTheoThueBaoService(DbContext dbContext){
        this.dbContext = dbContext;
    }

    public List<Map<String, Object>> traCuuDoDanCap(TraCuuDoDanCapTBTheoSoMay obj) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_MA_TB", obj.getSo_may(), Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String,Object>> rsTC_TheoMaTB = new ArrayList<>();
        if(obj.getLoai_tra_cuu() == 0) {
            rsTC_TheoMaTB = dbContext.executeSpIdxWithCursorToListMap("CABMAN.PKG_TRA_CUU_THUE_BAO.SP_TRA_CUU_TB_DANHBA", params);
        }
        else if(obj.getLoai_tra_cuu() == 1) {
            rsTC_TheoMaTB = dbContext.executeSpIdxWithCursorToListMap("CABMAN.PKG_TRA_CUU_THUE_BAO.SP_TRA_CUU_TB_HOPDONG", params);
        }
        else {
            return new ArrayList<>();
        }
        return rsTC_TheoMaTB;
    }

    public List<Map<String, Object>> traCuuLichSuCap(int thuebao_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_THUEBAO_ID", thuebao_id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_TRACUUDODANCAPTHEOTB.SP_TT_LICH_SU_CAP", params);
    }

    public Map<String, Object> thongTinCap(TraCuuDoDanCapTBTheoSoMay obj) throws AppSqlException {
        List<Map<String,Object>> rsTC_TT_Cap = new ArrayList<>();
        Map<String, ArrayList<Map<String, Object>>> rsTC_Theo_DODANCAP = null;
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        String procedure = "";
        if(obj.getLoai_tra_cuu() == 0) {
            params.add(new SqlParameter("P_THUEBAO_ID", obj.getId(), Types.NUMERIC));
            procedure = "ECMS.PKG_TRACUUDODANCAPTHEOTB.SP_TT_CAP_THEO_DB";
        }
        else {
            params.add(new SqlParameter("P_HOPDONG_ID",  obj.getId(), Types.NUMERIC));
            procedure = "ECMS.PKG_TRACUUDODANCAPTHEOTB.SP_TT_CAP_THEO_HD";
        }
        params.add(new SqlParameter("P_DAUCUOI_ID", obj.getDau_cuoi_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        rsTC_TT_Cap = dbContext.executeSpIdxWithCursorToListMap(procedure, params);

        if(rsTC_TT_Cap.size() > 0) {
            obj.setKy_hieu(rsTC_TT_Cap.get(0).get("KYHIEU_KC").toString());
            obj.setVitri(Integer.parseInt(rsTC_TT_Cap.get(0).get("VITRI").toString()));

            params = new ArrayList<>();
            params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
            params.add(new SqlParameter("P_KYHIEU", obj.getKy_hieu(), Types.VARCHAR));
            params.add(new SqlParameter("P_MAT", 2, Types.NUMERIC));
            params.add(new SqlParameter("P_VITRI", obj.getVitri(), Types.NUMERIC));
            params.add(new SqlParameter("P_KIEU", 1, Types.NUMERIC));
            params.add(new SqlParameter("P_DANDO_CHITIET", Types.REF_CURSOR, "chitiet"));
            params.add(new SqlParameter("P_DANDO_RUTGON", Types.REF_CURSOR, "rutgon"));
            rsTC_Theo_DODANCAP = dbContext.executeSpWithCursorsToListMap("CABMAN.CABMANV2.LAY_DANDO_KC", params);
            ArrayList<Map<String, Object>> rutgon = rsTC_Theo_DODANCAP.get("rutgon");
            int length = rutgon.size();
            for(int i = length - 1; i >= 0; i--) {
                if(Integer.parseInt(rutgon.get(i).get("KIEU").toString()) == 0) {
                    rsTC_TT_Cap.get(0).put("KYHIEU_CAP", rutgon.get(i).get("KYHIEU"));
                    rsTC_TT_Cap.get(0).put("SOI", rutgon.get(i).get("VITRI"));
                    break;
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("TTCAP", rsTC_TT_Cap);
        map.put("TTSDCAP", rsTC_Theo_DODANCAP == null ? new ArrayList<>() : rsTC_Theo_DODANCAP.get("rutgon"));
        return map;
    }

    public List<Map<String, Object>> capNhatGhiChuCap(TraCuuDoDanCapTBTheoSoMay obj) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_KIEU", obj.getLoai_tra_cuu(), Types.NUMERIC));
        params.add(new SqlParameter("P_DB_ID", obj.getId(), Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", obj.getGhichu(), Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), Types.VARCHAR));
        params.add(new SqlParameter("P_DAUCUOI_ID", obj.getDau_cuoi_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_TRACUUDODANCAPTHEOTB.SP_CAPNHAT_GHICHU_CAP", params);
        if(Integer.parseInt(rs.get(0).get("KETQUA").toString()) == 1) {
            rs.get(0).put("KETQUA", true);
        }
        else {
            rs.get(0).put("KETQUA", false);
        }
        return rs;
    }
}
