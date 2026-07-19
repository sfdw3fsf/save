package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhSachThietBiDauNoiDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.DauNoiPortDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.DanhSachPortChuaDauNoiDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Service
public class DauNoiThietBiService {
    private final DbContext dbContext;

    public DauNoiThietBiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getDeviceTypes() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THIETBI_DAUNOI.SP_LOAITHIETBI_DAUNOI", params);
    }

    public Object getDevices(DanhSachThietBiDauNoiDTO item) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_nguon_id", item.getThietBiNguonId(), Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", item.getIdcId(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaithietbi_id", item.getLoaiThietBiId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THIETBI_DAUNOI.SP_THIETBI_DAUNOI_DANHSACH", params);
    }

    public Object getPortFree(DanhSachPortChuaDauNoiDTO item) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", item.getThietBiId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THIETBI_DAUNOI.SP_PORT_CHUA_DAUNOI_DANHSACH", params);
    }

    public Object addLink(DauNoiPortDTO item) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id_nguon", item.getThietBiNguonId(), Types.NUMERIC));
        params.add(new SqlParameter("p_thietbi_id_dich", item.getThietBiDichId(), Types.NUMERIC));
        params.add(new SqlParameter("p_tbiport_id_nguon", item.getPortNguonId(), Types.NUMERIC));
        params.add(new SqlParameter("p_tbiport_id_dich", item.getPortDichId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", item.getGhiChu(), Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), Types.VARCHAR));
        params.add(new SqlParameter("p_result", SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THIETBI_DAUNOI.SP_THIETBI_PORT_DAUNOI_CREATE_UPDATE", params);
    }
  
    public Object deleteLink(DataKeyInput item) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_port_ketnoi_id", item.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), Types.VARCHAR));
        params.add(new SqlParameter("p_result", SqlParameter.ParameterDirection.OUTPUT,Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THIETBI_DAUNOI.SP_THIETBI_DAUNOI_DELETE", params);
    }

    public Object getLinkInfo(DataKeyInput item) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_thietbi_id", item.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_THIETBI_DAUNOI.SP_THONGTIN_DAUNOI_THIETBI", params);
    }
}
