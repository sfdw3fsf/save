package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.IPAddressDTO;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucLongQuayDtoIn;

import java.sql.Types;
import java.util.*;

@Service
public class QuanLyDiaChiIPService {
    @Autowired
    private DbContext dbContext;

    public Object createOrUpdate(IPAddressDTO dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        // param update
        if (dto.getId() != null && !dto.getId().equals(0)) {
            params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        }
        params.add(new SqlParameter("p_ip", dto.getDiaChiIp(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_store_id", dto.getIpStoreId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ip_loai_id", dto.getIpLoaiId(), Types.NUMERIC));
        params.add(new SqlParameter("p_subnet_id", dto.getIpSubnetId(), Types.NUMERIC));
        params.add(new SqlParameter("p_hatangmang_id", dto.getHaTangMangId(), Types.NUMERIC));
        params.add(new SqlParameter("p_subnet_quyhoach_id", dto.getSubnetQuyHoachId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ip_doituongcaphat_id", dto.getDoiTuongCapPhatId(), Types.NUMERIC));
        params.add(new SqlParameter("p_thietbimang_id", dto.getThietBiMangId(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthaicapphat", dto.getTrangThaiCapPhat(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cap", dto.getNguoiCapId(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_cap", dto.getDonViCapId(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_sd", dto.getNguoiSuDungId(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_sd", dto.getDonViSuDungId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_cap", dto.getNgayCap(), Types.DATE));
        params.add(new SqlParameter("p_ngay_thuhoi", dto.getNgayThuHoi(), Types.DATE));
        params.add(new SqlParameter("p_ngay_hethan", dto.getNgayHetHan(), Types.DATE));
        params.add(new SqlParameter("p_thamchieu", dto.getThamChieu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", dto.getHieuLuc(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", dto.getNhanVienId(), Types.NUMERIC));
        params.add(new SqlParameter("p_may_cn", dto.getMayCapNhap(), Types.NVARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", dto.getNguoiCapNhap(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_cn", dto.getIpCapNhap(), Types.NVARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        // update
        if (dto.getId() != null && !dto.getId().equals(0)) {
            dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_IP_ADDRESS_UPDATE", params);
            return result.getOutValue();
        }
        // create
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_IP_ADDRESS_INSERT", params);
        return result.getOutValue();
    }

    public Object selectionItems() throws AppSqlException {
        List<SqlParameter> paramCursor = Arrays.asList(new SqlParameter("p_cur", Types.REF_CURSOR));
        // item ip store
        Object ipStoreItems = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_IP_STORE_GET_ALL", paramCursor);

        // item ip doi tuong cap phat
        Object ipDoiTuongCapPhatItems = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_ds_doituong_capphat_ip", paramCursor);

        // item subnet quy hoach(muc dich su dung)
        Object subnetItems = dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC.sp_subnet_quyhoach_danhsach", paramCursor);

        Map<String, Object> result = new HashMap<>();
        result.put("ipStoreList", ipStoreItems);
        result.put("nguoiDungSDList", Collections.emptyList());
        result.put("nguoiDungCapList", Collections.emptyList());
        result.put("donViSDList", Collections.emptyList());
        result.put("donViCapList", Collections.emptyList());
        result.put("subnetQuyHoachList", subnetItems);
        result.put("ipDoiTuongCapPhatList", ipDoiTuongCapPhatItems);
        result.put("thietBiMangList", Collections.emptyList());
        return result;
    }

    public Object getAll() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_IP_ADDRESS_GET_ALL", params);
    }

    public Object getChangeHistoryById(Number ipAddressId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ip_address_id", ipAddressId, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_IP_ADDRESS_LICHSU_BY_IPADDRESS", params);
    }

    public Object deleteIPAddress(Number id) {
        try {
            List<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id", id, Types.NUMERIC));
            params.add(new SqlParameter("p_result", Types.NUMERIC));
            dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_IP_ADDRESS_DELETE", params);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Object getListIPAddress(IPAddressDTO input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_LOAI_IP", input.getLoaiIpId(),SqlParameter.ParameterDirection.INPUT,Types.NUMERIC));
        params.add(new SqlParameter("P_PHANLOAI_IP",input.getPhanLoaiIpId(), SqlParameter.ParameterDirection.INPUT,Types.NUMERIC));
        params.add(new SqlParameter("P_IDC_ID",input.getIdcId(),SqlParameter.ParameterDirection.INPUT,Types.NUMERIC));
        params.add(new SqlParameter("P_SUBNET_ID", input.getSubnetId(),SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI_CCDV", input.getTrangThaiCcdvId(),SqlParameter.ParameterDirection.INPUT,Types.NUMERIC));
        params.add(new SqlParameter("P_HTM_ID", input.getHaTangMangId(),SqlParameter.ParameterDirection.INPUT,Types.NUMERIC));
        params.add(new SqlParameter("P_DOITUONG_ID", input.getDoiTuongSDId(),SqlParameter.ParameterDirection.INPUT,Types.NUMERIC));
        params.add(new SqlParameter("P_DONVIQL_ID", input.getDonViQuanLyId(),SqlParameter.ParameterDirection.INPUT,Types.NUMERIC));

        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_DS_IP_ADDRESS",params);
    }

    public ArrayList<Map<String, Object>> getChiTietIP(IPAddressDTO input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_IP_ID", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_GET_DETAIL_IP", params);
    }

    public Object saveIpAddress(IPAddressDTO input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_IP_ID", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DIACHI_IP", input.getDiaChiIp(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SUBNET_ID", input.getSubnetId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IDC_ID", input.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_QUANGBA_IP", input.getQuangBaIp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TT_CCDV_ID", input.getTrangThaiCcdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MUCDICHSD_ID", input.getMucDichSdId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NOIBO", input.getNoiBoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIIP_ID", input.getLoaiIpId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANLOAIIP_ID", input.getPhanLoaiIpId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVIQL_ID", input.getDonViQuanLyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DOITUONG_ID", input.getDoiTuongSDId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_GHICHU", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_SUDUNG", input.getSuDung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SOCU", input.getSoCu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        SqlParameter result = new SqlParameter("P_ID_OUT", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_SAVE_IP_ADDRESS", params);
        return result.getOutValue();
    }

    public Object getListSubnet(IPAddressDTO input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_HTM_ID", input.getHaTangMangId(),SqlParameter.ParameterDirection.INPUT,Types.NUMERIC));
        params.add(new SqlParameter("P_LOAIIP_ID",input.getLoaiIpId(), SqlParameter.ParameterDirection.INPUT,Types.NUMERIC));
        params.add(new SqlParameter("P_PHANLOAIIP_ID",input.getPhanLoaiIpId(),SqlParameter.ParameterDirection.INPUT,Types.NUMERIC));

        params.add(new SqlParameter("P_CUR", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_DS_SUBNET_BY_HTM",params);
    }
}
