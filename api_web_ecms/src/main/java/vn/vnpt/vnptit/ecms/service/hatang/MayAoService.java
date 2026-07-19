package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.AddDiskRequestDTO;
import vn.vnpt.vnptit.ecms.dto.hatang.MayAoDto;

import java.sql.Types;
import java.util.ArrayList;

@Service
public class MayAoService {
    private final DbContext dbContext;

    public MayAoService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getListVirtualMachine() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_list_virtual_machine", params);
    }

    public Object getDetailVirtualMachineById(int dtId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToEntity("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_list_virtual_machine", params);
    }

    public Object createVirtualMachine(MayAoDto data) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("p_mamayao_infra", data.getMaMayAoCDSDHIT(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten", data.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hatangaohoa_id", data.getHaTangAoHoaId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hatangidg_id", data.getHaTangIdgId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaivaitro_id", data.getLoaiVaiTroId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguon_yeucau", data.getNguonYeuCau(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_yeucau", data.getNguoiYeuCau(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hedieuhanh_id", data.getHeDieuHanhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hatang_phanloai_id", data.getHaTangPhanLoaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_mayao_id", data.getTrangThaiMayAoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_tao", data.getNgayTao(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_hethan", data.getNgayHetHan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_capnhat", data.getNguoiCapNhat(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_capnhat", data.getNgayCapNhat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tinhgia", data.getTinhGia(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", data.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hieu_luc", data.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donviqly_id", data.getDonViQlyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phongbanqly_id", data.getPhongBanQlyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoiqly_id", data.getNguoiQlyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cumdichvu", data.getCumDichVuId(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_spdv_id", data.getSpdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_chuquan_id", data.getDonViChuQuanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vcpu", data.getvCPU(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vram", data.getvRAM(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vgpu", data.getGpu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dichvu_iops_id", data.getDichVuIopsId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dichvu_throughtput_id", data.getDichVuThroughtputId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ecms.PKG_HATANG_TAINGUYEN_AOHOA.insert_virtual_machine", params);
        return result.getOutValue();
    }

    public Object updateVirtualMachineById(int id, MayAoDto data) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mamayao_infra", data.getMaMayAoCDSDHIT(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten", data.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hatangaohoa_id", data.getHaTangAoHoaId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hatangidg_id", data.getHaTangIdgId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaivaitro_id", data.getLoaiVaiTroId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguon_yeucau", data.getNguonYeuCau(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_yeucau", data.getNguoiYeuCau(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hedieuhanh_id", data.getHeDieuHanhId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hatang_phanloai_id", data.getHaTangPhanLoaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_mayao_id", data.getTrangThaiMayAoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_tao", data.getNgayTao(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_hethan", data.getNgayHetHan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_capnhat", data.getNguoiCapNhat(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_capnhat", data.getNgayCapNhat(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_tinhgia", data.getTinhGia(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ghi_chu", data.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hieu_luc", data.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donviqly_id", data.getDonViQlyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phongbanqly_id", data.getPhongBanQlyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoiqly_id", data.getNguoiQlyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cumdichvu", data.getCumDichVuId(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_spdv_id", data.getSpdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_chuquan_id", data.getDonViChuQuanId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vcpu", data.getvCPU(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vram", data.getvRAM(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_vgpu", data.getGpu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dichvu_iops_id", data.getDichVuIopsId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dichvu_throughtput_id", data.getDichVuThroughtputId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ecms.PKG_HATANG_TAINGUYEN_AOHOA.update_virtual_machine", params);
        return result.getOutValue();
    }

    public Object deleteVirtualMachineById(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();

        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ecms.PKG_HATANG_TAINGUYEN_AOHOA.delete_virtual_machine", params);
        return result.getOutValue();
    }

    public Object getListAllocationHistoryVirtualMachine(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_allocation_history_virtual_machine", params);
    }

    public Object getListDiskVmVirtualMachine(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_list_disk_vm_virtual_machine", params);
    }

    public Object getListBackupVirtualMachine(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_list_backup_virtual_machine", params);
    }

    public Object getListNFSVirtualMachine(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_list_nfs_virtual_machine", params);
    }

    public Object getListIOPS() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_list_iops", params);
    }

    public Object getListThroughtput() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_list_throughtput", params);
    }

    public Object getListKHVirtualMachine(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_list_kh_virtual_machine", params);
    }

    public Object getListNetworkVirtualMachine(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_list_network_virtual_machine", params);
    }

    public Object getListHistoryVirtualMachine(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_list_history_virtual_machine", params);
    }

    public Object getDetailHTAoHoa(int id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToEntity("ecms.PKG_HATANG_TAINGUYEN_AOHOA.get_detail_ht_ao_hoa", params);
    }

    public Object getListOfVmsWithIP() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_TAINGUYEN_AOHOA.sp_get_vms_with_ip", params);
    }

    public Object addNfs(int vmId, int nfsId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_mayao_id", vmId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nfs_id", nfsId,  SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ecms.PKG_TEST_THUANLX.sp_add_nfs_to_mayao", params);
    }

    public Object addBackup(int vmId, int backupId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_mayao_id", vmId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_backup_id", backupId,  SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ecms.PKG_TEST_THUANLX.sp_add_backup_to_mayao", params);
    }

    public Object addDisk(int vmId, AddDiskRequestDTO request) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_mayao_id", vmId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", request.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_loai", request.getLoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dungluong", request.getDungluong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hieu_luc", request.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ecms.PKG_TEST_THUANLX.sp_add_disk_to_mayao", params);
    }

    public Object getNetworkIP(int vmId) throws AppSqlException  {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_mayao_id", vmId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ecms.PKG_IDC_HATANG_MAYAO_HAILN.SP_GET_NETWORK_IP_INFO", params);
    }
}
