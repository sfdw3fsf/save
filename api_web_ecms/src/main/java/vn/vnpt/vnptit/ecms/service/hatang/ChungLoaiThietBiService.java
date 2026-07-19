package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.ChungLoaiThietBiDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.LoaiCongInterfaceDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.LoaiCongInterfaceFilterDtoIn;
import vn.vnpt.vnptit.ecms.dto.danhmuc.VlanDtoIn;
import vn.vnpt.vnptit.ecms.dto.hatang.DMLienQuanVlanDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ChungLoaiThietBiService {
    @Autowired
    DbContext dbContext;

    public ArrayList<Map<String, Object>> layDsChungLoaiThietBi() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_ds_chungloai_thietbi", parameters);
    }
    public ArrayList<Map<String, Object>> layDsChungLoaiThietBiId(Long id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_ds_chungloai_thietbi_id", parameters);
    }
    public ArrayList<Map<String, Object>> layLichSuThayDoiChungLoaiThietBi(Integer pId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", pId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_lichsu_thaydoi_dmchungloaitb", parameters);
    }

    public ArrayList<Map<String, Object>> layDsPortTheoLineCardThietBi(Long cltbId, Long phanLoai) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", cltbId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phan_loai", phanLoai, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_ds_port_theo_linecard", parameters);
    }

    public ArrayList<Map<String, Object>> updateChungLoaiThietBi(ChungLoaiThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input.getId() != null ? input.getId().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_model", input.getModel(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_nhasx_id", input.getNhasx_id() != null ? input.getNhasx_id().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_chungloai_id", input.getChungloai_id() != null ? input.getChungloai_id().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_chieucao", input.getChieucao() != null ? input.getChieucao().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cs_dinhdanh", input.getCs_dinhdanh() != null ? input.getCs_dinhdanh().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ghi_chu", input.getGhi_chu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_hieu_luc", input.getHieu_luc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_fulldepth", input.getFulldepth(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phan_loai", input.getPhanLoai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_so_line_card", input.getSo_line_card(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_so_power", input.getSo_power(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_so_control_card", input.getSo_control_card(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_update_chungloai_thietbi", parameters);
//        if (rs != null && !rs.isEmpty()) {
//            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
//                return Boolean.TRUE;
//        }
        return rs;
    }

    public Boolean xoaChungLoaiThietBi(Integer input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_delete_chungloai_thietbi", parameters);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean khaibaoLoaiCong(LoaiCongInterfaceDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input.getId() != null ? input.getId().toString() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phanloai", input.getPhanloai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_chungloaithietbi_id", input.getChungloaiThietBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_loaicong_id", input.getLoaicongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_so_cong", input.getSoCong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_bat_dau", input.getBatdauTu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_dinhdang_ten", input.getDinhdangTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_mucdichsudung_cong_id", input.getMucdichsudungCongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_bangthongcong_id", input.getBangthongcongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_thongtinmodule_id", input.getThongtinModule(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("p_ketnoi", input.getKetnoi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_update_loaicong", parameters);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    public ArrayList<Map<String, Object>> danhsachLoaiCong(LoaiCongInterfaceFilterDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_chungloaithietbi_id", input.getChungloaiThietBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phanloai", input.getPhanloai(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> listOfDeclaredPorts = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_ds_cong", parameters);
        for (Map<String, Object> listOfDeclaredPort : listOfDeclaredPorts) {
            ArrayList<SqlParameter> paramsForPorts = new ArrayList<>();
            paramsForPorts.add(new SqlParameter("p_khai_bao_id", listOfDeclaredPort.get("ID"), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            paramsForPorts.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
            ArrayList<Map<String, Object>> listOfPorts = dbContext.executeSpWithCursorToListMap(
                    "ECMS.PKG_HATANG_DANHMUC.sp_ds_port_theo_khai_bao", paramsForPorts);
            listOfDeclaredPort.put("DS_CONG", listOfPorts);
        }
        return listOfDeclaredPorts;
    }

    public Boolean xoaCong(Integer input) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_id", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_delete_cong", parameters);
        if (rs != null && !rs.isEmpty()) {
            if ("1".equals(String.valueOf(rs.get(0).get("item"))))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public ArrayList<Map<String, Object>> getListOfCards(Long chungLoaiThietBiId, Long phanLoai) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_chungloaithietbi_id", chungLoaiThietBiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phanloai", phanLoai, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_ds_card", parameters);
    }

    public ArrayList<Map<String, Object>> getSumOfPorts(Long chungLoaiThietBiId, Long phanLoai) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_chungloaithietbi_id", chungLoaiThietBiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_phanloai", phanLoai, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_sum_ports", parameters);
    }

    public ArrayList<Map<String, Object>> getHistoryChanges(Long chungLoaiThietBiId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_chungloaithietbi_id", chungLoaiThietBiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_chungloaithietbi_history", parameters);
    }
}
