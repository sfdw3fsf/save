package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.hatang.ControllerTuDiaDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import vn.vnpt.common.Utils;
import vn.vnpt.vnptit.ecms.dto.hatang.PortThietBiInputDto;

@Service
public class ControllerTuDiaService {
    private final DbContext dbContext;

    public ControllerTuDiaService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getThongTinController(DataKeyInput input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", input.getId(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(result);
        dbContext.executeSpWithCursorToMap("ECMS.pkg_thongtin_thietbi.sp_get_thongtin_controller", params);
        return result.getOutValue();
    }

    public Object getDsPortTheoPhanLoai(ControllerTuDiaDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_bodieukhien_id", input.getBodieukhien_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_phanloai", input.getPhanloai(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(result);
        dbContext.executeSpWithCursorToListMap("ECMS.pkg_thongtin_thietbi.sp_get_danhsach_port_theo_phanloai", params);
        return result.getOutValue();
    }

    public Object capnhatController(ControllerTuDiaDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_bodieukhien_id", input.getBodieukhien_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", input.getTen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_kyhieu", input.getKyhieu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_serial_number", input.getSerial_number(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ghi_chu", input.getGhi_chu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", input.getHieu_luc(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_sd", input.getTrangthai_sd(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_linecard", input.getSo_linecard(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_interface", input.getSo_interface(), Types.NUMERIC));
        params.add(new SqlParameter("p_interface_start", input.getInterface_start(), Types.NUMERIC));
        params.add(new SqlParameter("p_interface_fname", input.getInterface_fname(), Types.NVARCHAR));
        params.add(new SqlParameter("p_so_port_interface", input.getSo_port_interface(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_power", input.getSo_power(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_powerslot", input.getSo_powerslot(), Types.NUMERIC));
        params.add(new SqlParameter("p_power_start", input.getPower_start(), Types.NUMERIC));
        params.add(new SqlParameter("p_power_fname", input.getPower_fname(), Types.NVARCHAR));
        params.add(new SqlParameter("p_so_port_power", input.getSo_port_power(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_controlcard", input.getSo_controlcard(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_console", input.getSo_console(), Types.NUMERIC));
        params.add(new SqlParameter("p_console_start", input.getConsole_start(), Types.NUMERIC));
        params.add(new SqlParameter("p_console_fname", input.getConsole_fname(), Types.NVARCHAR));
        params.add(new SqlParameter("p_so_port_console", input.getSo_port_console(), Types.NUMERIC));
        params.add(new SqlParameter("p_ds_port", input.getDs_port(), Types.VARCHAR));
        params.add(new SqlParameter("p_ip_mgmt", input.getIp_mgmt(), Types.VARCHAR));
        params.add(new SqlParameter("p_ip_controller", input.getIp_controller(), Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        params.add(new SqlParameter("p_may_cn", Utils.getNodeName(), Types.NVARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getMaNhanVien(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip_cn", Utils.getNodeIp(), Types.NVARCHAR));

        SqlParameter result = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(result);
        dbContext.executeSpWithCursorToMap("ECMS.pkg_thongtin_thietbi.sp_capnhat_controller", params);
        return result.getOutValue();
    }

    public Object getThongTinLichSuController(DataKeyInput input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", input.getId(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(result);
        dbContext.executeSpWithCursorToListMap("ECMS.pkg_thongtin_thietbi.sp_get_log_controller", params);
        return result.getOutValue();
    }

    public Object getDsPortThietBiTheoPhanLoai(PortThietBiInputDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", input.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_phanloai", input.getPhanloai(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(result);
        dbContext.executeSpWithCursorToListMap("ECMS.pkg_thongtin_thietbi.sp_get_danhsach_port_thietbi_theo_phanloai", params);
        return result.getOutValue();
    }

    public Object getDsPortBoDieuKhienChungLoaiTheoPhanLoai(PortThietBiInputDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", input.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_phanloai", input.getPhanloai(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(result);
        dbContext.executeSpWithCursorToListMap("ECMS.pkg_thongtin_thietbi.sp_get_ds_thietbi_con_theo_phanloai", params);
        return result.getOutValue();
    }

    public Object getTongPortChungLoaiKhaiBaoTheoPhanLoai(PortThietBiInputDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", input.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_phanloai", input.getPhanloai(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(result);
        dbContext.executeSpWithCursorToMap("ECMS.pkg_thongtin_thietbi.sp_tongso_port_chungloai_tb_khaibao_theo_phanloai", params);
        return result.getOutValue();
    }

}
