package vn.vnpt.vnptit.ecms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class DoiCongTheoDanhSachTTVTService {
    @Autowired
    DbContext dbContext;
    public ArrayList<Map<String, Object>> them_hoso_catchuyen(ThemHoSoCatChuyenDtoIn hoSoCatChuyen) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TENHOSO", hoSoCatChuyen.getTenHoso() != null ? hoSoCatChuyen.getTenHoso() :"", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
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
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_HOSO_CATCHUYEN_INS", parameters);
    }
    public ArrayList<Map<String, Object>> capnhat_hoso_catchuyen(CapNhatHoSoCatChuyenDtoIn hoSoCatChuyen) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hoSoCatChuyen.getHsccId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TENHOSO", hoSoCatChuyen.getTenHoso() != null ? hoSoCatChuyen.getTenHoso() :"", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGAYBD", hoSoCatChuyen.getNgayBd() != null ? hoSoCatChuyen.getNgayBd() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGAYKT", hoSoCatChuyen.getNgayKt() != null ? hoSoCatChuyen.getNgayKt(): "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_GHICHU", hoSoCatChuyen.getGhiChu() != null ? hoSoCatChuyen.getGhiChu() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_HOSO_CATCHUYEN_UPD", parameters);
    }
    public ArrayList<Map<String, Object>> nap_ds_hoso(NapDanhSachHoSoDtoIn napDanhSachHoSo) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_THUEBAO", napDanhSachHoSo.getMaThueBao() != null ? napDanhSachHoSo.getMaThueBao() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CBO_TTHS", napDanhSachHoSo.getCboTths() != null ? napDanhSachHoSo.getCboTths() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TUNGAY", napDanhSachHoSo.getTuNgay() != null ? napDanhSachHoSo.getTuNgay() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DENNGAY", napDanhSachHoSo.getDenNgay() != null ? napDanhSachHoSo.getDenNgay(): "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DONVI_ID",  AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TAG", napDanhSachHoSo.getTag() != null ? napDanhSachHoSo.getTag() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_NAP_DS_HOSO", parameters);
    }
    public ArrayList<Map<String, Object>> load_hoso_catchuyen(NapDanhSachHoSoDtoIn napDanhSachHoSo) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MA_THUEBAO", napDanhSachHoSo.getMaThueBao() != null ? napDanhSachHoSo.getMaThueBao() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CBO_TTHS", napDanhSachHoSo.getCboTths() != null ? napDanhSachHoSo.getCboTths() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TUNGAY", napDanhSachHoSo.getTuNgay() != null ? napDanhSachHoSo.getTuNgay() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DENNGAY", napDanhSachHoSo.getDenNgay() != null ? napDanhSachHoSo.getDenNgay() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DONVI_ID",  AppRequestContext.getCurrent().getDonViID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TAG", napDanhSachHoSo.getTag() != null ? napDanhSachHoSo.getTag() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_LOAD_HOSO_CATCHUYEN", parameters);
    }
    public ArrayList<Map<String, Object>> xoa_ds_catchuyen(List<XoaDanhSachCatChuyenDtoIn> catChuyenDtoInList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", catChuyenDtoInList != null ? catChuyenDtoInList.get(0).getHSCC_ID() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_JSON_CHON", catChuyenDtoInList != null ? objectMapper.writeValueAsString(catChuyenDtoInList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_DSCATCHUYEN_DEL", parameters);
    }
    public Boolean xoa_phieu_tab1(Long hsccId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_hscc_id", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_XOA_PHIEU_TAB1", parameters);
        if (rs != null && rs.size() > 0){
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
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_NAP_DS_THUEBAO_CATCHUYEN_TAB2", parameters);
    }
    public ArrayList<Map<String, Object>> kiemtra_matb1_madoicap_ins_tab2(List<MaTBDtoIn> maTBList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_matb", maTBList != null ? objectMapper.writeValueAsString(maTBList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_KIEMTRA_MATB1_MAPDOICAP_INS_TAB2", parameters);
    }
    public ArrayList<Map<String, Object>> kiemtra_malt1_madoicap_ins_tab2(List<MaLTDtoIn> maLTList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_malt", maLTList != null ? objectMapper.writeValueAsString(maLTList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_KIEMTRA_MALT1_MAPDOICAP_INS_TAB2", parameters);
    }
    public ArrayList<Map<String, Object>> kiemtra_matb2_madoicap_ins_tab2(List<MaTBDtoIn> maTBList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_matb", maTBList != null ? objectMapper.writeValueAsString(maTBList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_KIEMTRA_MATB2_MAPDOICAP_INS_TAB2", parameters);
    }
    public ArrayList<Map<String, Object>> kiemtra_malt2_madoicap_ins_tab2(List<MaLTDtoIn> maLTList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_malt", maLTList != null ? objectMapper.writeValueAsString(maLTList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_KIEMTRA_MALT2_MAPDOICAP_INS_TAB2", parameters);
    }
    public ArrayList<Map<String, Object>> kiemtra_matb3_madoicap_ins_tab2(List<MaTBDtoIn> maTBList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_matb", maTBList != null ? objectMapper.writeValueAsString(maTBList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_KIEMTRA_MATB3_MAPDOICAP_INS_TAB2", parameters);
    }
    public ArrayList<Map<String, Object>> kiemtra_malt3_madoicap_ins_tab2(List<MaLTDtoIn> maLTList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("p_ds_malt", maLTList != null ? objectMapper.writeValueAsString(maLTList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_KIEMTRA_MALT3_MAPDOICAP_INS_TAB2", parameters);
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
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_GET_INFOR_MALT_MAPDOICAP_INS_TAB2", parameters);
    }
    public Boolean them_ds_madoicap(Long hsccId, Integer cungDoiCap, List<ThemDanhSachMaDoiCapDtoIn> danhSachMaDoiCapDtoInList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        System.out.println("Start api");
        System.out.println("P_PHANVUNG_ID;" + AppRequestContext.getCurrent().getPhanVungID());
        System.out.println("P_MAY_CN;" + AppRequestContext.getCurrent().getClientName());
        System.out.println("P_MA_ND;" + AppRequestContext.getCurrent().getMaNhanVien());
        System.out.println("P_IP_CN;" + AppRequestContext.getCurrent().getClientIpAddress());
        System.out.println("End api");
        for (ThemDanhSachMaDoiCapDtoIn e:danhSachMaDoiCapDtoInList) {
            parameters = new ArrayList<>();
            parameters.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("KEYNAME", "DKCC_ID", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            parameters.add(new SqlParameter("NUMBLOCKSIZE", 1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("NUMRETRY", 10, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
            ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_GET_KEY", parameters);
            e.setDKCC_ID(Long.parseLong(rs.get(0).get("ID").toString()));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DS_DKCC_ID", danhSachMaDoiCapDtoInList != null ? objectMapper.writeValueAsString(danhSachMaDoiCapDtoInList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_HSCC_ID", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_MA_ND", AppRequestContext.getCurrent().getMaNhanVien(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_CUNGDOICAP", cungDoiCap != null ? cungDoiCap : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_DS_MADOICAP_INS", parameters);
        if (rs != null && rs.size() > 0){
            if ("1".equals(rs.get(0).get("RESULT").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    public ArrayList<Map<String, Object>> giu_vci_cpi(List<GiuVciCpiDtoIn> giuVciCpiDtoInList, Long hsccId) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DS_DKCC_ID", giuVciCpiDtoInList != null ? objectMapper.writeValueAsString(giuVciCpiDtoInList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_HSCC_ID", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_GIU_VCI_CPI", parameters);
    }
    public ArrayList<Map<String, Object>> nap_ds_thuebao_catchuyen_tab3(Long hsccId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_NAP_DS_THUEBAO_CATCHUYEN_TAB3", parameters);
    }
    public ArrayList<Map<String, Object>> nap_ds_tramtbi() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_NAP_DS_TRAMTBI", parameters);
    }
    public ArrayList<Map<String, Object>> nap_ds_dslam(Long tramtbId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TRAMTB_ID", tramtbId != null ? tramtbId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_NAP_DS_DSLAM", parameters);
    }
    public ArrayList<Map<String, Object>> nap_ds_port_fix(Long dslamId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DSLAM_ID", dslamId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_SLOT", "-1", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_PORT", "-1", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_SYSTEM", "0", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RACK", "-1", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_SHELF","-1", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_VCI", -1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_VPI", -1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_THAMSO", -1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_TRANGTHAI", 1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_LOAITB_ID", 58, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_NAP_DS_PORT_FIX", parameters);
    }
    public ArrayList<Map<String, Object>> nap_ds_luoi_port(Long dslamId, Long loaitbId) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DSLAM_ID", dslamId != null ? dslamId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_LOAITB_ID", loaitbId != null ? loaitbId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_NAP_DS_LUOI_PORT", parameters);
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
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_NAP_DS_LUOI_VCIVPI", parameters);
    }
    public ArrayList<Map<String, Object>> nap_cbo_trangthai_cc() throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_NAP_CBO_TRANGTHAI_CC", parameters);
    }
    public Boolean capnhat_gan_cong_catchuyen(Integer kieu, Long hsccId, List<CapNhatCongCatChuyen> congCatChuyenList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        for (CapNhatCongCatChuyen e:congCatChuyenList) {
            parameters = new ArrayList<>();
            parameters.add(new SqlParameter("VPHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("KEYNAME", "RP_ID", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            parameters.add(new SqlParameter("NUMBLOCKSIZE", 1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("NUMRETRY", 10, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            parameters.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
            ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_GET_KEY", parameters);
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
        Map<String, Object> rs =  dbContext.executeSpWithCursorToMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_CAPNHAT_CONG_CATCHUYEN", parameters);
        if (rs != null){
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
        Map<String, Object> rs =   dbContext.executeSpWithCursorToMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_CAPNHAT_CONG_CATCHUYEN", parameters);
        if (rs != null){
            if ("1".equals(rs.get("KETQUA").toString()))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    public ArrayList<Map<String, Object>> hoanthien_catchuyen(Long hsccId, List<HoanThienCatChuyenDtoIn> hoanThienCatChuyenDtoInList) throws AppSqlException, JsonProcessingException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hsccId != null ? hsccId : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_DS_CC", hoanThienCatChuyenDtoInList != null ? objectMapper.writeValueAsString(hoanThienCatChuyenDtoInList) : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_THUCTHI", parameters);
    }
    public Map<String, Object> lay_ttcc_id(String hscc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hscc_id, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_LAY_TTCC_ID", parameters);
        return rs.size() != 0 ? rs.get(0) : null;
    }

    public Map<String, Object> capnhat9_catchuyen_hoanthien_tab3(Long dkcc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DKCC_ID", dkcc_id != null ? dkcc_id :"", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs =  dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_CAPNHAT9_DK_CATCHUYEN_HOANTHIEN_TAB3", parameters);
        return rs.size() != 0 ? rs.get(0) : null;
    }

    public Map<String, Object> cauhinh_bnm(CauHinhBNMDtoIn cauhinhBNM) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_THUEBAO_ID",cauhinhBNM.getThuebao_id() != null ? cauhinhBNM.getThuebao_id() : "", SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_PORT_ID", cauhinhBNM.getPort_id() != null ? cauhinhBNM.getPort_id() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_VCI_VPI_ID", cauhinhBNM.getVci_vpi_id() != null ? cauhinhBNM.getVci_vpi_id() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_LOAI_TD", cauhinhBNM.getLoai_td() != null ? cauhinhBNM.getLoai_td() : "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_NGUOI_KH", cauhinhBNM.getNguoi_kh() != null ? cauhinhBNM.getNguoi_kh() : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_CHUYEN_ALL", cauhinhBNM.getChuyen_all() != null ? cauhinhBNM.getChuyen_all() : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_DS_TB", cauhinhBNM.getDs_tb() != null ? cauhinhBNM.getDs_tb() : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_TRANGTHAI", cauhinhBNM.getTrangthai() != null ? cauhinhBNM.getTrangthai() : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_DKCC_ID", cauhinhBNM.getDkcc_id() != null ? cauhinhBNM.getDkcc_id() : "" ,SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_PASS_FTTH", cauhinhBNM.getPass_ftth() != null ? cauhinhBNM.getPass_ftth() : "", SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs = dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_CAUHINH_BNM", parameters);
        return rs.size() != 0 ? rs.get(0) : null;
    }

    public String getPassVisa(String vma_tb_ftth) throws AppSqlException {
        return "123456";
    }
    public Integer UpdateDeviceInfo(String IPTVAcString, String strDeviceName, String strPortID) throws AppSqlException{
        return 0;
    }

    public Map<String, Object> capnhat10_catchuyen_hoanthien_tab3(Long dkcc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DKCC_ID", dkcc_id != null ? dkcc_id :"", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs =  dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_CAPNHAT10_DK_CATCHUYEN_HOANTHIEN_TAB3", parameters);
        return rs.size() != 0 ? rs.get(0) : null;
    }

    public Map<String, Object> capnhat11_catchuyen_huycauhinh_tab3(Long dkcc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_DKCC_ID", dkcc_id != null ? dkcc_id :"", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        ArrayList<Map<String, Object>> rs =  dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_CAPNHAT11_DK_CATCHUYEN_HUYCAUHINH_TAB3", parameters);
        return rs.size() != 0 ? rs.get(0) : null;
    }

    public ArrayList<Map<String, Object>> check_ttcc_donghoso_tab3(Long hscc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hscc_id != null ? hscc_id :"", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_LAY_SOLUONG_TTCC_DONGHOSO_TAB3", parameters);
    }

    public ArrayList<Map<String, Object>> donghoso_tab3(Long hscc_id) throws AppSqlException {
        ArrayList<SqlParameter> parameters = new ArrayList<>();
        parameters.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        parameters.add(new SqlParameter("P_HSCC_ID", hscc_id != null ? hscc_id :"", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        parameters.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_DOICONG_THEO_DS_TTVT.SP_DONGHOSO_TAB3", parameters);
    }
}
