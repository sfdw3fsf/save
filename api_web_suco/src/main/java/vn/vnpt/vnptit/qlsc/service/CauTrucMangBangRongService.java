package vn.vnpt.vnptit.qlsc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.*;

import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CauTrucMangBangRongService {
    private final DbContext dbContext;

    public CauTrucMangBangRongService(DbContext dbContext) {
        this.dbContext = dbContext;
    }
    public Object NapGianDo(Integer phanVungId, CauTrucMangBangRongDtoIn.GianDo input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYENTD_ID", input.getTuyen_td_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_DOI_TUONG_ID", input.getLoai_doituong_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_CAUTRUC_MANG_BANGRONG.SP_LAY_DL_GIANDO_TD", params);
    }
    public Object TaoDoiTuong(Integer phanVungId, CauTrucMangBangRongDtoIn.DoiTuongIns input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN_HT", input.getTen_ht(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_LOAIDT_ID", input.getLoaidt_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DOITUONG_MAP_ID", input.getDoituong_map_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYENTD_ID", input.getTuyentd_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_X", input.getVitri_x(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_Y", input.getVitri_y(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_TD_X", input.getVitri_td_x(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_TD_Y", input.getVitri_td_y(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.PKG_NGHIEPVU_TD_V2.SP_TAO_DOITUONG", params);
    }
    public Object CapNhatDoiTuong(Integer phanVungId, CauTrucMangBangRongDtoIn.DoiTuongUpd input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DOITUONG_ID", input.getDoituong_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYENTD_ID", input.getTuyentd_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN_HT", input.getTen_ht(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_VITRI_X", input.getVitri_x(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_Y", input.getVitri_y(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_TD_X", input.getVitri_td_x(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_TD_Y", input.getVitri_td_y(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.PKG_NGHIEPVU_TD_V2.SP_CAPNHAT_DOITUONG", params);
    }
    public Object XoaDoiTuong(Integer phanVungId,CauTrucMangBangRongDtoIn.DoiTuongUpd input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DOITUONG_ID", input.getDoituong_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYENTD_ID", input.getTuyentd_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.PKG_NGHIEPVU_TD_V2.SP_XOA_DOITUONG", params);
    }

    public Object TaoLienKet(Integer phanVungId, CauTrucMangBangRongDtoIn.LienketIns input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN_LK", input.getTen_lk(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TEN_HT", input.getTen_ht(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_LOAILK_ID", input.getLoailk_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DOITUONG_N_ID", input.getDoituong_n_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DOITUONG_D_ID", input.getDoituong_d_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYENTD_ID", input.getTuyentd_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DIEMUON_X1", input.getDiemuon_x1(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DIEMUON_Y1", input.getDiemuon_y1(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DIEMUON_X2", input.getDiemuon_x2(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DIEMUON_Y2", input.getDiemuon_y2(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_TD_X", input.getVitri_td_x(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_TD_Y", input.getVitri_td_y(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.PKG_NGHIEPVU_TD_V2.SP_TAO_LIENKET", params);
    }
    public Object CapNhatLienKet(Integer phanVungId, CauTrucMangBangRongDtoIn.LienketUpd input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LIENKET_ID", input.getLienket_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYENTD_ID", input.getTuyentd_id(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_LOAILK_ID", input.getLoailk_id(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_TEN_LK", input.getTen_lk(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN_HT", input.getTen_ht(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DIEMUON_X1", input.getDiemuon_x1(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DIEMUON_Y1", input.getDiemuon_y1(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DIEMUON_X2", input.getDiemuon_x2(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DIEMUON_Y2", input.getDiemuon_y2(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_TD_X", input.getVitri_td_x(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_TD_Y", input.getVitri_td_y(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.PKG_NGHIEPVU_TD_V2.SP_CAPNHAT_LIENKET", params);
    }
    public Object XoaLienKet(Integer phanVungId, CauTrucMangBangRongDtoIn.LienketUpd input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LIENKET_ID", input.getLienket_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYENTD_ID", input.getTuyentd_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.PKG_NGHIEPVU_TD_V2.SP_XOA_LIENKET", params);
    }

    public Object TaoVong(Integer phanVungId, CauTrucMangBangRongDtoIn.VongIns input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN_RING", input.getTen_ring(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYENTD_ID", input.getTuyentd_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DS_LIENKET", input.getDs_lienket(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_X", input.getVitri_x(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_Y", input.getVitri_y(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MAUNEN", input.getMaunen(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.PKG_NGHIEPVU_TD_V2.SP_TAO_VONG", params);
    }
    public Object CapNhatVong(Integer phanVungId, CauTrucMangBangRongDtoIn.VongUpd input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RING_ID", input.getRing_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN_RING", input.getTen_ring(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_X", input.getVitri_x(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_Y", input.getVitri_y(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MAUNEN", input.getMaunen(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.PKG_NGHIEPVU_TD_V2.SP_CAPNHAT_VONG", params);
    }
    public Object XoaVong(Integer phanVungId, int input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RING_ID", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.PKG_NGHIEPVU_TD_V2.SP_XOA_VONG", params);
    }
    public String []  TyLeIn() throws AppSqlException {
        String [] result= {"10%",
                "20%",
                "30%",
                "40%",
                "50%",
                "60%",
                "70%",
                "80%",
                "90%",
                "100%",
                "200%",
                "300%",
                "400%",
                "Vừa màn hình"};
        return result;
    }

    public Object dsTuyenTruyenDan() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.DANHMUC_TD_V2.SP_LAY_DS_TUYEN_TD", params);
    }

    public Object themTuyenTruyenDan(ThemTuyenTruyenDan tuyenTruyenDan) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYEN_TD", tuyenTruyenDan.getTUYEN_TD(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAITTD_ID", tuyenTruyenDan.getLOAITTD_ID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("CABMAN.DANHMUC_TD_V2.SP_TUYEN_TD_INS", params);
        return rs.size() > 0 ? rs.get(0) : null;
    }
    public Object capnhatTuyenTruyenDan(CapNhatTuyenTruyenDan tuyenTruyenDan) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYENTD_ID", tuyenTruyenDan.getTUYENTD_ID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYEN_TD", tuyenTruyenDan.getTUYEN_TD(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAITTD_ID", tuyenTruyenDan.getLOAITTD_ID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.DANHMUC_TD_V2.SP_TUYEN_TD_UPD", params);
    }

    public Boolean xoaTuyenTruyenDan(XoaTuyenTruyenDan tuyenTruyenDan) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TUYENTD_ID", tuyenTruyenDan.getTUYENTD_ID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("CABMAN.DANHMUC_TD_V2.SP_TUYEN_TD_DEL", params);
        if (rs != null && rs.size() > 0){
            if ("1".equals(rs.get(0).get("RESULT").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    public Object layLoaiLk() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.DANHMUC_TD_V2.SP_LAY_LOAI_LK", params);
    }

    public Object CapNhatListDoiTuong(List<DoiTuongUpdate> input) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LIST", input != null ? objectMapper.writeValueAsString(input) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
       return  dbContext.executeSpWithCursorToListMap("CABMAN.PKG_NGHIEPVU_TD_V2.SP_CAPNHAT_LIST_DOITUONG", params);
    }

    public Object dsLoaiTuyenTruyenDan() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CABMAN.DANHMUC_TD_V2.SP_LAY_DS_LOAI_TUYENTD1", params);
    }
}
