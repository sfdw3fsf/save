package vn.vnpt.vnptit.ecms.service;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.GiaoTiep.*;
import vn.vnpt.vnptit.ecms.dto.GiaoTiepCommmandDtoIn;

import java.sql.Clob;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GiaoTiepThietBiService {
    private DbContext dbContext;
    public GiaoTiepThietBiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public List<Map<String, Object>> getListDeviceOLT() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("GIAOTIEP_TBI.GPON.GPON_DANHSACH_THIETBI_OLT_MAIN", params);
    }

    public List<Map<String, Object>> getListDeviceOLTDslam() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("GIAOTIEP_TBI.GPON.GPON_DANHSACH_THIETBI_OLT_CAUHINH", params);
    }
    public List<Map<String, Object>> getListService() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        //params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("GIAOTIEP_TBI.GPON.DANHSACH_NGHIEPVU", params);
    }

    public List<Map<String, Object>> postListSeriviceReturn(ThongTinTraCuu input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_TU_NGAY", input.getTuNgay(), Types.VARCHAR));
        params.add(new SqlParameter("P_DEN_NGAY", input.getDenNgay(), Types.VARCHAR));
        params.add(new SqlParameter("P_TRANG_THAI", input.getLoai(), Types.VARCHAR));
        params.add(new SqlParameter("P_NGAY", "0", Types.VARCHAR));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("GIAOTIEP_TBI.GPON.DANHSACH_KETQUA_THUCHIEN", params);
    }

    public List<Map<String, Object>> getRequestList(String idApp) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_ID_APP", idApp, Types.VARCHAR));
        params.add(new SqlParameter("P_TRANG_THAI", 0,Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("GIAOTIEP_TBI.GPON.DANHSACH_YEUCAU_XULY_2", params);
    }
    public List<Map<String, Object>> getUltilitíe() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("GIAOTIEP_TBI.GPON.DS_DANHMUC_TIENICH", params);
    }
    public List<Map<String, Object>> getCommmandList(GiaoTiepCommmandDtoIn input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID_OLT", input.getP_loai_olt(), Types.NUMERIC));
        params.add(new SqlParameter("P_ID_ONT", input.getP_loai_ont(),Types.NUMERIC));
        params.add(new SqlParameter("P_ID", input.getP_id().toString(), Types.VARCHAR));
        params.add(new SqlParameter("P_LOAI_YC", input.getP_loai_yeucau(), Types.NUMERIC));
        params.add(new SqlParameter("P_MYTV", input.getP_mytv(), Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("GIAOTIEP_TBI.GPON.DANHSACH_COMMAND", params);
    }

    public Boolean postConfigOLT(CapNhatOLTDTO input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id_dslam",input.getpIDDslam(),Types.NUMERIC));
        params.add(new SqlParameter("p_ip",input.getpIP(),Types.VARCHAR));
        params.add(new SqlParameter("p_user",input.getpUser(),Types.VARCHAR));
        params.add(new SqlParameter( "p_pass",input.getpPass(),Types.VARCHAR));
        params.add(new SqlParameter( "p_loai_gpon",input.getpLoaiGpon(),Types.NUMERIC));
        params.add(new SqlParameter( "p_active",input.getpActive(),Types.NUMERIC));
        params.add(new SqlParameter("p_vlan_internet",input.getpVlanInternet(),Types.VARCHAR));
        params.add(new SqlParameter("p_vlan_mytv",input.getpVlanMyTV(),Types.VARCHAR));
        params.add(new SqlParameter("p_vlan_voip",input.getpVlanVoip(),Types.VARCHAR));
        params.add(new SqlParameter("p_vlan_gnms",input.getpVlanGNMS(),Types.VARCHAR));
        params.add(new SqlParameter("p_queue_internet_shaper",input.getpQueueInternetShaper(),Types.VARCHAR));
        params.add(new SqlParameter("p_queue_internet_bandwidth",input.getpQueueInternetBandwidth(),Types.VARCHAR));
        params.add(new SqlParameter("p_shaper_profile_mytv",input.getpShaperProfileMyTV(),Types.VARCHAR));
        params.add(new SqlParameter("p_bandwidth_profile_mytv",input.getpBandwidthProfileMyTV(),Types.VARCHAR));
        params.add(new SqlParameter("p_queue_mytv_shaper",input.getpQueueMyTVShaper(),Types.VARCHAR));
        params.add(new SqlParameter("p_queue_mytv_bandwidth",input.getpQueueMyTVBandwidth(),Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        //params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        dbContext.executeStoredProcedure("GIAOTIEP_TBI.GPON.CAPNHAT_THONGTIN_CAUHINH_OLT", params);
        return true;

    }
    public Boolean postSerivice(DanhSachServiceDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id_loaithietbi",input.getTypeOLT(),Types.NUMERIC));
        params.add(new SqlParameter("p_id_nghiepvu",input.getServiceId(),Types.NUMERIC));
        params.add(new SqlParameter("p_ten_nghiepvu",input.getServiceName(),Types.VARCHAR));
        params.add(new SqlParameter("p_kiemtra_ont",input.getCheckONT(), Types.NUMERIC));
        //params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        dbContext.executeStoredProcedure("GIAOTIEP_TBI.GPON.CAPNHAT_NGHIEPVU", params);
        return true;

    }

    public List<Map<String,Object>> postSendSerivice(YeuCauDTO input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_sysname",input.getSysName(),Types.VARCHAR));
        params.add(new SqlParameter("p_slot",input.getSlot(),Types.VARCHAR));
        params.add(new SqlParameter("p_port",input.getPort(),Types.VARCHAR));
        params.add(new SqlParameter("p_onu",input.getOnu(),Types.VARCHAR));
        params.add(new SqlParameter("p_dichvu",input.getService(),Types.VARCHAR));
        params.add(new SqlParameter("p_value",input.getValue(),Types.VARCHAR));
        params.add(new SqlParameter("p_phanvung_id",AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("GIAOTIEP_TBI.GPON.GUI_YEUCAU", params);

    }
    public Boolean postCommandSerivice(DanhSachServiceDto input) throws AppSqlException {

            insertTMPClob("", input.getServiceCommand());
            List<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id_loaithietbi", input.getTypeOLT(), Types.NUMERIC));
            params.add(new SqlParameter("p_id_nghiepvu", input.getServiceId(), Types.NUMERIC));
            params.add(new SqlParameter("p_table", "", Types.VARCHAR));
            params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
            //params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
            dbContext.executeStoredProcedure("GIAOTIEP_TBI.GPON.CAPNHAT_NGHIEPVU_LENH_2", params);
            return true;
    }
    public Boolean postCancelSerivice(Long pId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("vid",pId, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        //params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        dbContext.executeStoredProcedure("GIAOTIEP_TBI.GPON.HUY_YEUCAU", params);
        return true;
    }

    public Boolean postResetSerivice(Long pId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("vid",pId, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        //params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        dbContext.executeStoredProcedure("GIAOTIEP_TBI.GPON.RESET_YEUCAU", params);
        return true;
    }
    public Boolean postResultProcess(CapNhatKetQuaDTO input) throws AppSqlException {
        try {
            insertTMPClob(input.getpKetQua(), input.getpLenh());
            List<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id", input.getpID(), Types.NUMERIC));
            params.add(new SqlParameter("p_trang_thai", input.getpTrangThaiId(), Types.NUMERIC));
            params.add(new SqlParameter("p_ketquaerror", input.getpKetQuaError(), Types.VARCHAR));
            //params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
            //params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
            dbContext.executeStoredProcedure("GIAOTIEP_TBI.GPON.CAPNHAT_KETQUA_THUCHIEN_2", params);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
    public List<Map<String, Object>> getServiceCommmandList(DanhSachServiceDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID_LOAITHIETBI", input.getTypeOLT(), Types.NUMERIC));
        params.add(new SqlParameter("P_ID_NGHIEPVU", input.getServiceId(),Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("GIAOTIEP_TBI.GPON.DANHSACH_NGHIEPVU_LENH", params);
    }
    public List<Map<String, Object>> getLogService(Long pID) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_ID",pID, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("RSOUT", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("GIAOTIEP_TBI.GPON.GET_LOG2", params);
    }

    public void insertTMPClob (String pKetQua,String pLenh) throws AppSqlException {
        String sql =
                "insert into giaotiep_tbi.tmp_ketqua(ketqua, lenh) values (?, ?)";

        dbContext.executeNonQuery(
                sql,
                new Object[]{pKetQua, pLenh}
        );
    }

}
