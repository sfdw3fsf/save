package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.VRFDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuanLyVRF2Service {
    @Autowired
    private DbContext dbContext;

    public Object createOrUpdate(VRFDto dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_idc_id", dto.getIdcID(), Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id", dto.getThietBiMangId(), Types.NUMERIC));
        params.add(new SqlParameter("p_asn", dto.getAsn(), Types.NVARCHAR));
        params.add(new SqlParameter("p_rd", dto.getRd(), Types.NVARCHAR));
        params.add(new SqlParameter("p_rt_export", dto.getRtExport(), Types.NVARCHAR));
        params.add(new SqlParameter("p_rt_import", dto.getRtImport(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ghi_chu", dto.getGhiChu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", dto.getHieuLuc(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", dto.getNhanVienId(), Types.NUMERIC));
        params.add(new SqlParameter("p_may_cn", dto.getMayCapNhap(), Types.NVARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", dto.getNguoiCapNhap(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_cn", dto.getIpCapNhap(), Types.NVARCHAR));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_QUANLY_VRF_INSERT_OR_UPDATE", params);
        return result.getOutValue();
    }

    public Object getAll() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_QUANLY_VRF_GET_ALL", params);
    }

    public Object getVRFDanhMucSuDung(Number vrfID, String tabName) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_object_id", vrfID, Types.NUMERIC));
        params.add(new SqlParameter("p_table_name", tabName, Types.NVARCHAR));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_DANHMUC_QUANLY_VRF_GET", params);
    }

    public Object deleteById(Number id) {
        try {
            List<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id", id, Types.NUMERIC));
            params.add(new SqlParameter("p_table_name", "vrf", Types.NVARCHAR));
            params.add(new SqlParameter("p_result", Types.NUMERIC));
            dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_DELETE_BY_ID", params);
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Object getThietBiMangByIDCID(Number IDCID, String loaiDanhMuc) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loai_danhmuc", loaiDanhMuc, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_tham_so1", IDCID.toString(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tham_so2", null, SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.pkg_hatang_danhmuc.sp_danhmuc_danhsach", params);
    }

    public Object getHaTangMangById(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_TAINGUYEN_QUANLY.SP_HATANGMANG_BY_ID", params);
    }
}
