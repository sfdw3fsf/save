package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.model.QuyHoachVLANModel;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuyHoachVLANService {
    private final DbContext dbContext;

    public QuyHoachVLANService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public List<Map<String, Object>> LayDSLoaiQuyHoach() throws AppSqlException {
        SqlParameter params = new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR);
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN.SP_LAY_VUNG_QUYHOACH", params);
    }

    public List<Map<String, Object>> LayDSVungQuyHoach() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN.SP_LAY_DM_VUNG", params);
    }

    public List<Map<String, Object>> LayDSDichVu() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOIDUNG_ID", AppRequestContext.getCurrent().getNguoiDungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN.SP_LAY_DS_DICH_VU", params);
    }

    public List<Map<String, Object>> LayLoaiDSDichVu(int dichvu_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_DICHVU_ID", dichvu_id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN.SP_LAY_DS_LOAI_DICH_VU", params);
    }

    public List<Map<String, Object>> LayDSTrangThai() throws AppSqlException {
        SqlParameter params = new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR);
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN.SP_LAY_TRANGTHAI", params);
    }

    public List<Map<String, Object>> LayDSQuyHoachVLAN(int kieu, int vlan) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_KIEU", kieu, Types.NUMERIC));
        params.add(new SqlParameter("P_VLAN", vlan, Types.NUMERIC));
//        params.add(new SqlParameter("P_PAGE", page, Types.NUMERIC));
//        params.add(new SqlParameter("P_OFFSET", offset, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN.SP_LAY_DS_QUYHOACH_VLAN_V2", params);
    }

    public List<Map<String, Object>> ThemQuyHoachVLAN(QuyHoachVLANModel quyHoachVLANModel) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", quyHoachVLANModel.getDonvi_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", quyHoachVLANModel.getDslam_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_VLAN_TU", quyHoachVLANModel.getVlan_tu(), Types.NUMERIC));
        params.add(new SqlParameter("P_VLAN_DEN", quyHoachVLANModel.getVlan_den(), Types.NUMERIC));
        params.add(new SqlParameter("P_KIEU", quyHoachVLANModel.getKieu(), Types.NUMERIC));
        params.add(new SqlParameter("P_LOAITB_ID", quyHoachVLANModel.getLoaitb_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI", quyHoachVLANModel.getTrangthai(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN.SP_THEM_QUYHOACH_VLAN", params);
    }

    public List<Map<String, Object>> CapNhatQuyHoachVLAN(QuyHoachVLANModel quyHoachVLANModel) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_QUYHOACH_ID", quyHoachVLANModel.getQuyhoach_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", quyHoachVLANModel.getDonvi_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_DSLAM_ID", quyHoachVLANModel.getDslam_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_VLAN_TU", quyHoachVLANModel.getVlan_tu(), Types.NUMERIC));
        params.add(new SqlParameter("P_VLAN_DEN", quyHoachVLANModel.getVlan_den(), Types.NUMERIC));
        params.add(new SqlParameter("P_KIEU", quyHoachVLANModel.getKieu(), Types.NUMERIC));
        params.add(new SqlParameter("P_LOAITB_ID", quyHoachVLANModel.getLoaitb_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_TRANGTHAI", quyHoachVLANModel.getTrangthai(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN.SP_CAPNHAT_QUYHOACH_VLAN", params);
        if(Integer.parseInt(rs.get(0).get("KETQUA").toString()) == 1) {
            rs.get(0).put("KETQUA", true);
        }
        else {
            rs.get(0).put("KETQUA", false);
        }
        return  rs;
    }

    public List<Map<String, Object>> XoaQuyHoachVLAN(int quyhoach_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_QUYHOACH_ID", quyhoach_id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_QUYHOACH_VLAN.SP_XOA_QUYHOACH_VLAN", params);
        if(Integer.parseInt(rs.get(0).get("KETQUA").toString()) == 1) {
            rs.get(0).put("KETQUA", true);
        }
        else {
            rs.get(0).put("KETQUA", false);
        }
        return  rs;
    }
}
