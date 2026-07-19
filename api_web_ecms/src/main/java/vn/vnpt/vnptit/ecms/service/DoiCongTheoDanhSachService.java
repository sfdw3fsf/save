package vn.vnpt.vnptit.ecms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DoiCongTheoDanhSachService {
    @Autowired
    DbContext dbContext;

    public ArrayList<Map<String, Object>> them_hoso_catchuyen(ThemHoSoCatChuyenDtoIn hoSoCatChuyen) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TENHOSO", hoSoCatChuyen.getTenHoso() != null ? hoSoCatChuyen.getTenHoso() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_LOAIHS_ID", hoSoCatChuyen.getLoaihsId() != null ? hoSoCatChuyen.getLoaihsId() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_KIEUHS_ID", hoSoCatChuyen.getKieuhsId() != null ? hoSoCatChuyen.getKieuhsId() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TTCC_ID", hoSoCatChuyen.getTtccId() != null ? hoSoCatChuyen.getTtccId() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_NGAYBD", hoSoCatChuyen.getNgayBd() != null ? hoSoCatChuyen.getNgayBd() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGAYKT", hoSoCatChuyen.getNgayKt() != null ? hoSoCatChuyen.getNgayKt() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGUOI_DK_ID", AppRequestContext.getCurrent().getNguoiDungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TTVT_ID", hoSoCatChuyen.getTtvtId() != null ? hoSoCatChuyen.getTtvtId() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_CHAN_BAOHONG", hoSoCatChuyen.getChanBaohong() != null ? hoSoCatChuyen.getChanBaohong() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_GHICHU", hoSoCatChuyen.getGhiChu() != null ? hoSoCatChuyen.getGhiChu() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_HOSO_CATCHUYEN_INS", parameters);
    }

    public ArrayList<Map<String, Object>> capnhat_hoso_catchuyen(CapNhatHoSoCatChuyenDtoIn hoSoCatChuyen) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hoSoCatChuyen.getHsccId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TENHOSO", hoSoCatChuyen.getTenHoso() != null ? hoSoCatChuyen.getTenHoso() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGAYBD", hoSoCatChuyen.getNgayBd() != null ? hoSoCatChuyen.getNgayBd() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGAYKT", hoSoCatChuyen.getNgayKt() != null ? hoSoCatChuyen.getNgayKt() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_GHICHU", hoSoCatChuyen.getGhiChu() != null ? hoSoCatChuyen.getGhiChu() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_HOSO_CATCHUYEN_UPD", parameters);
    }

    public ArrayList<Map<String, Object>> nap_ds_hoso(NapDanhSachHoSoCusDtoIn napDanhSachHoSo) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_THUEBAO", napDanhSachHoSo.getMaThueBao() != null ? napDanhSachHoSo.getMaThueBao() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CBO_TTHS", napDanhSachHoSo.getCboTths() != null ? napDanhSachHoSo.getCboTths() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TUNGAY", napDanhSachHoSo.getTuNgay() != null ? napDanhSachHoSo.getTuNgay() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DENNGAY", napDanhSachHoSo.getDenNgay() != null ? napDanhSachHoSo.getDenNgay() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_DS_HOSO", parameters);
    }

    // Bo sung thay doi tab = 3. 28/12/2022 - BSS-77693
    public ArrayList<Map<String, Object>> nap_ds_hoso1(NapDanhSachHoSoCusDtoIn napDanhSachHoSo) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_THUEBAO", napDanhSachHoSo.getMaThueBao() != null ? napDanhSachHoSo.getMaThueBao() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CBO_TTHS", napDanhSachHoSo.getCboTths() != null ? napDanhSachHoSo.getCboTths() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TUNGAY", napDanhSachHoSo.getTuNgay() != null ? napDanhSachHoSo.getTuNgay() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DENNGAY", napDanhSachHoSo.getDenNgay() != null ? napDanhSachHoSo.getDenNgay() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DONVI_ID", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TAG", napDanhSachHoSo.getTag(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_DS_HOSO1", parameters);
    }

    public ArrayList<Map<String, Object>> load_hoso_catchuyen(NapDanhSachHoSoCusDtoIn napDanhSachHoSo) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_THUEBAO", napDanhSachHoSo.getMaThueBao() != null ? napDanhSachHoSo.getMaThueBao() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CBO_TTHS", napDanhSachHoSo.getCboTths() != null ? napDanhSachHoSo.getCboTths() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TUNGAY", napDanhSachHoSo.getTuNgay() != null ? napDanhSachHoSo.getTuNgay() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DENNGAY", napDanhSachHoSo.getDenNgay() != null ? napDanhSachHoSo.getDenNgay() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_LOAD_HOSO_CATCHUYEN", parameters);
    }
    public ArrayList<Map<String, Object>> load_hoso_catchuyen1(NapDanhSachHoSoCusDtoIn napDanhSachHoSo) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_THUEBAO", napDanhSachHoSo.getMaThueBao() != null ? napDanhSachHoSo.getMaThueBao() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CBO_TTHS", napDanhSachHoSo.getCboTths() != null ? napDanhSachHoSo.getCboTths() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TUNGAY", napDanhSachHoSo.getTuNgay() != null ? napDanhSachHoSo.getTuNgay() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DENNGAY", napDanhSachHoSo.getDenNgay() != null ? napDanhSachHoSo.getDenNgay() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DONVI_ID", AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TAG", napDanhSachHoSo.getTag(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_LOAD_HOSO_CATCHUYEN1", parameters);
    }

    public Map<String, Object> check_infor_before_xoa_ds_tab2(List<CheckXoaDanhSachCatChuyenDtoIn> maLTList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds", maLTList != null ? objectMapper.writeValueAsString(maLTList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_XOA_DS_TAB2", parameters);
        return rs.size() > 0 ? rs.get(0) : null;
    }

    public ArrayList<Map<String, Object>> xoa_ds_catchuyen(List<XoaDanhSachCatChuyenDtoIn> catChuyenDtoInList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", catChuyenDtoInList != null ? catChuyenDtoInList.get(0).getHSCC_ID() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_JSON_CHON", catChuyenDtoInList != null ? objectMapper.writeValueAsString(catChuyenDtoInList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_DSCATCHUYEN_DEL", parameters);
    }

    public Boolean xoa_phieu_tab1(Long hsccId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hscc_id", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_XOA_PHIEU_TAB1", parameters);
        if (rs != null && rs.size() > 0) {
            if ("1".equals(rs.get(0).get("RESULT").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public ArrayList<Map<String, Object>> nap_ds_thuebao_catchuyen_tab2(Long hsccId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_DS_THUEBAO_CATCHUYEN_TAB2", parameters);
    }

    public ArrayList<Map<String, Object>> kiemtra_matb1_madoicap_ins_tab2(List<MaTBDtoIn> maTBList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_matb", maTBList != null ? objectMapper.writeValueAsString(maTBList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_MATB1_MAPDOICAP_INS_TAB2", parameters);
    }

    public ArrayList<Map<String, Object>> kiemtra_malt1_madoicap_ins_tab2(List<MaLTDtoIn> maLTList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_malt", maLTList != null ? objectMapper.writeValueAsString(maLTList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_MALT1_MAPDOICAP_INS_TAB2", parameters);
    }

    public ArrayList<Map<String, Object>> kiemtra_matb2_madoicap_ins_tab2(List<MaTBDtoIn> maTBList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_matb", maTBList != null ? objectMapper.writeValueAsString(maTBList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_MATB2_MAPDOICAP_INS_TAB2", parameters);
    }

    public ArrayList<Map<String, Object>> kiemtra_malt2_madoicap_ins_tab2(List<MaLTDtoIn> maLTList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_malt", maLTList != null ? objectMapper.writeValueAsString(maLTList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_MALT2_MAPDOICAP_INS_TAB2", parameters);
    }

    public ArrayList<Map<String, Object>> kiemtra_matb3_madoicap_ins_tab2(List<MaTBDtoIn> maTBList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_matb", maTBList != null ? objectMapper.writeValueAsString(maTBList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_MATB3_MAPDOICAP_INS_TAB2", parameters);
    }

    public ArrayList<Map<String, Object>> kiemtra_malt3_madoicap_ins_tab2(List<MaLTDtoIn> maLTList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_malt", maLTList != null ? objectMapper.writeValueAsString(maLTList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_MALT3_MAPDOICAP_INS_TAB2", parameters);
    }

    public ArrayList<Map<String, Object>> get_infor_matb_madoicap_ins_tab2(List<MaTBDtoIn> maTBList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_matb", maTBList != null ? objectMapper.writeValueAsString(maTBList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_GET_INFOR_MATB_MAPDOICAP_INS_TAB2", parameters);
    }

    public ArrayList<Map<String, Object>> get_infor_malt_madoicap_ins_tab2(List<MaLTDtoIn> maLTList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_malt", maLTList != null ? objectMapper.writeValueAsString(maLTList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_GET_INFOR_MALT_MAPDOICAP_INS_TAB2", parameters);
    }

    public ArrayList<Map<String, Object>> CAUHINH_BNM(List<MaTBDtoIn> maTBList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds", maTBList != null ? objectMapper.writeValueAsString(maTBList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_MATB_GO_CONG_TAB3", parameters);
    }

    public Boolean them_ds_madoicap(Long hsccId, Integer cungDoiCap, List<ThemDanhSachMaDoiCapDtoIn> danhSachMaDoiCapDtoInList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        for (ThemDanhSachMaDoiCapDtoIn e : danhSachMaDoiCapDtoInList) {
            parameters = new ArrayList<>();
            parameters.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("KEYNAME", "DKCC_ID", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            parameters.add(new SqlParameter("NUMBLOCKSIZE", 1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("NUMRETRY", 10, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
            ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_GET_KEY", parameters);
            e.setDKCC_ID(Long.parseLong(rs.get(0).get("ID").toString()));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DS_DKCC_ID", danhSachMaDoiCapDtoInList != null ? objectMapper.writeValueAsString(danhSachMaDoiCapDtoInList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_HSCC_ID", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_MA_ND", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CUNGDOICAP", cungDoiCap != null ? cungDoiCap : "0", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_DS_MADOICAP_INS", parameters);
        if (rs != null && rs.size() > 0) {
            if ("1".equals(rs.get(0).get("RESULT").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public ArrayList<Map<String, Object>> giu_vci_cpi(List<GiuVciCpiDtoIn> giuVciCpiDtoInList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DS_DKCC_ID", giuVciCpiDtoInList != null ? objectMapper.writeValueAsString(giuVciCpiDtoInList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_GIU_VCI_VPI", parameters);
    }

    public ArrayList<Map<String, Object>> nap_ds_thuebao_catchuyen_tab3(Long hsccId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_DS_THUEBAO_CATCHUYEN_TAB3", parameters);
    }

    public ArrayList<Map<String, Object>> nap_ds_tramtbi() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_DS_TRAMTBI", parameters);
    }

    public ArrayList<Map<String, Object>> nap_ds_trangthai_vcivpi() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_LAY_DS_TRANGTHAI_VCIVPI", parameters);
    }

    public ArrayList<Map<String, Object>> nap_ds_dslam(Long tramtbId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TRAMTB_ID", tramtbId != null ? tramtbId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_DS_DSLAM", parameters);
    }

    public ArrayList<Map<String, Object>> nap_ds_port_fix(Long dslamId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DSLAM_ID", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_SLOT", "-1", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_PORT", "-1", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_SYSTEM", "0", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RACK", "-1", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_SHELF", "-1", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_VCI", -1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_VPI", -1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_THAMSO", -1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TRANGTHAI", 1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_LOAITB_ID", 58, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_DS_PORT_FIX", parameters);
    }

    public ArrayList<Map<String, Object>> nap_ds_luoi_port(Long dslamId, Long loaitbId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DSLAM_ID", dslamId != null ? dslamId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_LOAITB_ID", loaitbId != null ? loaitbId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_DS_LUOI_PORT", parameters);
    }

    public ArrayList<Map<String, Object>> nap_ds_luoi_vcivpi(NapDanhSachLuoiVciVpiDtoIn danhSachLuoiVciVpiDtoIn) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DSLAM_ID", danhSachLuoiVciVpiDtoIn.getDslamId() != null ? danhSachLuoiVciVpiDtoIn.getDslamId() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_QUYHOACH_ID", danhSachLuoiVciVpiDtoIn.getQuyhoachId() != null ? danhSachLuoiVciVpiDtoIn.getQuyhoachId() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_KIEUKN", danhSachLuoiVciVpiDtoIn.getKieuKn() != null ? danhSachLuoiVciVpiDtoIn.getKieuKn() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TRANGTHAI_ID", danhSachLuoiVciVpiDtoIn.getTrangThaiId() != null ? danhSachLuoiVciVpiDtoIn.getTrangThaiId() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_VCI_VPI_ID", danhSachLuoiVciVpiDtoIn.getVciVpiId() != null ? danhSachLuoiVciVpiDtoIn.getVciVpiId() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_DS_LUOI_VCIVPI", parameters);
    }

    public ArrayList<Map<String, Object>> nap_cbo_trangthai_cc() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_CBO_TRANGTHAI_CC", parameters);
    }

    public ArrayList<Map<String, Object>> nap_cbo_hoso_cc() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_CBO_HOSO_CC", parameters);
    }

    public ArrayList<Map<String, Object>> nap_cbo_kieu_cc() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_NAP_CBO_KIEU_CC", parameters);
    }

    public ArrayList<Map<String, Object>> lay_dsacc_tbao(String matb) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_THUEBAO", matb, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_LAY_DS_ACC", parameters);
    }

    public Map<String, Object> lay_ttcc_id(String hscc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hscc_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_LAY_TTCC_ID", parameters);
        return rs.size() != 0 ? rs.get(0) : null;
    }

    public Boolean capnhat_gan_cong_catchuyen(Integer kieu, Long hsccId, List<CapNhatCongCatChuyen> congCatChuyenList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        for (CapNhatCongCatChuyen e : congCatChuyenList) {
            parameters = new ArrayList<>();
            parameters.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("KEYNAME", "RP_ID", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            parameters.add(new SqlParameter("NUMBLOCKSIZE", 1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("NUMRETRY", 10, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
            ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_GET_KEY", parameters);
            e.setRP_ID(Long.parseLong(rs.get(0).get("ID").toString()));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_KIEU", kieu != null ? kieu : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DS_CC", congCatChuyenList != null ? objectMapper.writeValueAsString(congCatChuyenList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        Map<String, Object> rs = dbContext.executeSpWithCursorToMap("ECMS.PKG_DOICONG_THEO_DS.SP_CAPNHAT_CONG_CATCHUYEN", parameters);
        if (rs != null) {
            if ("1".equals(rs.get("KETQUA").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean capnhat_go_cong_catchuyen(Integer kieu, Long hsccId, List<CapNhatCongCatChuyen> congCatChuyenList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_KIEU", kieu != null ? kieu : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DS_CC", congCatChuyenList != null ? objectMapper.writeValueAsString(congCatChuyenList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        Map<String, Object> rs = dbContext.executeSpWithCursorToMap("ECMS.PKG_DOICONG_THEO_DS.SP_CAPNHAT_CONG_CATCHUYEN", parameters);
        if (rs != null) {
            if ("1".equals(rs.get("KETQUA").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Map<String, Object> hoanthien_catchuyen(Long hsccId, List<HoanThienCatChuyenDtoIn> hoanThienCatChuyenDtoInList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DS_CC", hoanThienCatChuyenDtoInList != null ? objectMapper.writeValueAsString(hoanThienCatChuyenDtoInList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_THUCTHI", parameters);
        return rs.get(0);
    }

    public Boolean capnhat_dk_catchuyen(Long dkcc_id, Integer type) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DKCC_ID", dkcc_id != null ? dkcc_id : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TYPE", type != null ? type : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_CAPNHAT_DK_CATCHUYEN", parameters);
        if (rs != null && rs.size() > 0) {
            if ("1".equals(rs.get(0).get("RESULT").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public ArrayList<Map<String, Object>> them_log_catchuyen(LogCatChuyenDtoIn hoSoCatChuyen) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_TB", hoSoCatChuyen.getMa_tb() != null ? hoSoCatChuyen.getMa_tb() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DKCC_ID", hoSoCatChuyen.getDkcc_id() != null ? hoSoCatChuyen.getDkcc_id() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName() != null ? AppRequestContext.getCurrent().getClientName() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_MA_ND", AppRequestContext.getCurrent().getUserName() != null ? AppRequestContext.getCurrent().getUserName() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_PORT_ID", hoSoCatChuyen.getPort_id() != null ? hoSoCatChuyen.getPort_id() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_VCI_VPI_ID", hoSoCatChuyen.getVci_vpi_id() != null ? hoSoCatChuyen.getVci_vpi_id() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DSLAM_VDC_ID", hoSoCatChuyen.getDslam_vdc_id() != null ? hoSoCatChuyen.getDslam_vdc_id() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_FLAG", hoSoCatChuyen.getFlag() != null ? hoSoCatChuyen.getFlag() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_ND", hoSoCatChuyen.getNd() != null ? hoSoCatChuyen.getNd() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_IP", AppRequestContext.getCurrent().getClientIpAddress() != null ? AppRequestContext.getCurrent().getClientIpAddress() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_STATUS", hoSoCatChuyen.getStatus() != null ? hoSoCatChuyen.getStatus() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_LOG_INS", parameters);
    }

    public Map<String, Object> cauHinhBNM(CauHinhBNMDtoIn cauhinhBNM) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_THUEBAO_ID", cauhinhBNM.getThuebao_id() != null ? cauhinhBNM.getThuebao_id() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_PORT_ID", cauhinhBNM.getPort_id() != null ? cauhinhBNM.getPort_id() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_VCI_VPI_ID", cauhinhBNM.getVci_vpi_id() != null ? cauhinhBNM.getVci_vpi_id() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_LOAI_TD", cauhinhBNM.getLoai_td() != null ? cauhinhBNM.getLoai_td() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGUOI_KH", cauhinhBNM.getNguoi_kh() != null ? cauhinhBNM.getNguoi_kh() : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_CHUYEN_ALL", cauhinhBNM.getChuyen_all() != null ? cauhinhBNM.getChuyen_all() : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_DS_TB", cauhinhBNM.getDs_tb() != null ? cauhinhBNM.getDs_tb() : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_TRANGTHAI", cauhinhBNM.getTrangthai() != null ? cauhinhBNM.getTrangthai() : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_DKCC_ID", cauhinhBNM.getDkcc_id() != null ? cauhinhBNM.getDkcc_id() : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_PASS_FTTH", cauhinhBNM.getPass_ftth() != null ? cauhinhBNM.getPass_ftth() : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_CAUHINH_BNM", parameters);
        return rs.size() != 0 ? rs.get(0) : null;
    }

    public String getPassVisa(String vma_tb_ftth) throws AppSqlException {
        return "123456";
    }

    public Integer UpdateDeviceInfo(String IPTVAcString, String strDeviceName, String strPortID) throws AppSqlException {
        return 0;
    }

    public ArrayList<Map<String, Object>> check_ttcc_donghoso_tab3(Long hscc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hscc_id != null ? hscc_id : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_LAY_SOLUONG_TTCC_DONGHOSO_TAB3", parameters);
    }

    public ArrayList<Map<String, Object>> donghoso_tab3(Long hscc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hscc_id != null ? hscc_id : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_DONGHOSO_TAB3", parameters);
    }

    public Map<String, Object> capnhat9_catchuyen_hoanthien_tab3(Long dkcc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DKCC_ID", dkcc_id != null ? dkcc_id : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_CAPNHAT9_DK_CATCHUYEN_HOANTHIEN_TAB3", parameters);
        return rs.size() != 0 ? rs.get(0) : null;
    }

    public Map<String, Object> capnhat10_catchuyen_hoanthien_tab3(Long dkcc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DKCC_ID", dkcc_id != null ? dkcc_id : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_CAPNHAT10_DK_CATCHUYEN_HOANTHIEN_TAB3", parameters);
        return rs.size() != 0 ? rs.get(0) : null;
    }

    public Map<String, Object> capnhat11_catchuyen_huycauhinh_tab3(Long dkcc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DKCC_ID", dkcc_id != null ? dkcc_id : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_CAPNHAT11_DK_CATCHUYEN_HUYCAUHINH_TAB3", parameters);
        return rs.size() != 0 ? rs.get(0) : null;
    }

    public ArrayList<Map<String, Object>> tao_dulieu_visa(Long dkcc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DKCC_ID", dkcc_id != null ? dkcc_id : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_TAO_DULIEU_VISA", parameters);
    }

    public ArrayList<Map<String, Object>> tao_dulieu_visa_cu(List<DuLieuViSaDtoIn> dkccIDList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DKCC_ID_LIST", dkccIDList != null ? objectMapper.writeValueAsString(dkccIDList) : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_TAO_DULIEU_VISA_CU_LIST_JSON", parameters);
    }

    public ArrayList<Map<String, Object>> kiemtra_list_inp_gocong_tab3(List<DkccDtoIn> maLTList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds", maLTList != null ? objectMapper.writeValueAsString(maLTList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_MATB_GO_CONG_TAB3", parameters);
    }

    public ArrayList<Map<String, Object>> tao_dulieu_visa_cu1(MaLTDtoIn input) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DKCC_ID_LIST", input.getMA_LT(), SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_TAO_DULIEU_VISA_CU_LIST_JSON", parameters);
    }

    public Object get_key_rp() throws AppSqlException {
        SqlParameter parameters = new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC);
        return dbContext.executeFunction("ECMS.PKG_DOICONG_THEO_DS.FUNC_GET_KEY_RP_ID", Types.NUMERIC, parameters);
    }

    public Boolean capnhat_gan_cong_catchuyen_1(CapNhatGanCongCatChuyen input) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_KIEU", input.getKieu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", input.getHsccId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DS_CC", input.getJson_list(), SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        Map<String, Object> rs = dbContext.executeSpWithCursorToMap("ECMS.PKG_DOICONG_THEO_DS.SP_CAPNHAT_CONG_CATCHUYEN", parameters);
        if (rs != null) {
            if ("1".equals(rs.get("KETQUA").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    public ArrayList<Map<String, Object>> lay_ds_tb_catchuyen_thaydoi_internet(Long hsccId, String vma_tb) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("vphanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("vhscc_id", hsccId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("vma_tb", vma_tb, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("vnguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.LAY_DS_TB_CATCHUYEN_THAYDOI_INTERNET", parameters);
    }
    public ArrayList<Map<String, Object>> lay_ds_thuebao_capcong_thaydoi_ts_internet(Long hsccId, String vma_tb) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("vphanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("vhscc_id", hsccId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("vma_tb", vma_tb, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("vnguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.LAY_DS_THUEBAO_CAPCONG_THAYDOI_TS_INTERNET", parameters);
    }

    public ArrayList<Map<String, Object>> kiemtra_thongtin_matb_dichvu_danhba(String ma_tb, String dichvuvt_ids) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_TB", ma_tb, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DICHVU_FIX", dichvuvt_ids, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_THONGTIN_MATB_DICHVU_DANHBA", parameters);
    }

    public ArrayList<Map<String, Object>> kiemtra_hoso_theo_matb(String ma_tb) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_TB", ma_tb, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_HOSO_THEO_MATB", parameters);
    }

    public Object kiemtra_dieukien_doicong_theo_matb(String ma_tb) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_TB", ma_tb, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        Map<String, Object> rs = dbContext.executeSpWithCursorToMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_DIEUKIEN_DOICONG_THEO_MATB", parameters);
        return rs.get("KQ");
    }

    public ArrayList<Map<String, Object>> lay_ds_thuebao_doicong_internet(String ma_tb)  throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_TB", ma_tb, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CURSOR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS.SP_LAY_DS_THUEBAO_DOICONG_INTERNET", parameters);
    }

    public Boolean kiemtra_giu_vci_vpi() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RESULT", java.sql.Types.REF_CURSOR));
        Map<String, Object> rs = dbContext.executeSpWithCursorToMap("ECMS.PKG_DOICONG_THEO_DS.SP_KIEMTRA_GIUVCI_VPI", parameters);
        return Integer.parseInt(rs.get("TEN_TS").toString()) == 1;
    }
}
