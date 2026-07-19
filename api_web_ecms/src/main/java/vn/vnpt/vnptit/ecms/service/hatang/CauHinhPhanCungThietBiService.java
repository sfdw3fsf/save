package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.CauHinhPhanCungThietBiDtoIn;

import java.sql.Types;
import java.util.ArrayList;

@Service
public class CauHinhPhanCungThietBiService {
    private final DbContext dbContext;

    public CauHinhPhanCungThietBiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object thongTinThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.SP_THONGTIN_THIETBI",
                params);
    }

    public Object danhSachRamThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_danhsach_ram_thietbi",
                params);
    }

    public Object danhSachCpuThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_danhsach_cpu_thietbi",
                params);
    }

    public Object danhSachGpuThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_danhsach_gpu_thietbi",
                params);
    }

    public Object danhSachBladeThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_danhsach_blade_thietbi",
                params);
    }

    public Object danhSachOCungThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_danhsach_ocung_thietbi",
                params);
    }

    public Object danhSachOCungKhayDia(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_danhsach_ocung_khaydia",
                params);
    }

    public Object danhSachPhanCungThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_phancung_thietbi_mang",
                params);
    }

    public Object danhSachKhayDiaTuDia(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_ds_khaydia_tudia",
                params);
    }

    public Object danhSachBoDKTuDia(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_ds_bo_dieukhien_tudia",
                params);
    }

    public Object danhSachKhayDiaTuDiaIDC(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_idc_id", input.getIdc_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_ds_khaydia_tudia_idc",
                params);
    }

    public Object danhSachBoDKTuDiaIDC(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_idc_id", input.getIdc_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_ds_bo_dieukhien_idc",
                params);
    }

    public Object danhSachBladeIDC(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", input.getIdc_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_danhsach_blade_thietbi_idc",
                params);
    }

    public Object xoaRamThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", input.getId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_cn",
                AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn",
                AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_xoa_ram_thietbi",
                params);
    }

    public Object xoaCpuThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", input.getId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_cn",
                AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn",
                AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_xoa_cpu_thietbi",
                params);
    }

    public Object xoaGpuThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", input.getId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_cn",
                AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn",
                AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_xoa_gpu_thietbi",
                params);
    }

    public Object xoaBladeThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", input.getId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_cn",
                AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn",
                AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_xoa_blade_thietbi",
                params);
    }

    public Object xoaOCungThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", input.getId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_cn",
                AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn",
                AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_xoa_ocung_thietbi",
                params);
    }

    public Object xoaTuDiaPCThietBi(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", input.getId(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_cn",
                AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn",
                AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_xoa_tudia_phancung_thietbi",
                params);
    }

    public Object capNhatCauHinhPhanCung(CauHinhPhanCungThietBiDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_thietbi_id", input.getThietbi_id(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khecpu", input.getSl_khecpu(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_corecpu", input.getSl_corecpu(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_threadcpu", input.getSl_threadcpu(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_model_cpu", input.getModel_cpu(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_cpu", input.getSl_cpu(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_kheram", input.getSl_kheram(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_ram", input.getSl_ram(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_ram", input.getDl_ram(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khegpu", input.getSl_khegpu(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_gpu", input.getSl_gpu(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khefan", input.getSl_khefan(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_fan", input.getSl_fan(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khepci", input.getSl_khepci(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_pci", input.getSl_pci(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khessd", input.getSl_khessd(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_ssd", input.getSl_ssd(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khehdd", input.getSl_khehdd(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_hdd", input.getSl_hdd(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_khenvme", input.getSl_khenvme(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_nvme", input.getSl_nvme(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_kheblade", input.getSl_kheblade(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_ds_cpu", input.getDs_cpu(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ds_gpu", input.getDs_gpu(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ds_ocung", input.getDs_ocung(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ds_ram", input.getDs_ram(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ds_blade", input.getDs_blade(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("p_tocdo_cpu_mang", input.getTocdo_cpu_mang(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_ram_mang", input.getDl_ram_mang(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_hdd_mang", input.getDl_hdd_mang(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dl_ssd_mang", input.getDl_ssd_mang(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_sl_fan_mang", input.getSl_fan_mang(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("p_ds_dkh", input.getDs_dkh(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ds_khd", input.getDs_khd(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn",
                AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn",
                AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_type", input.getType(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        //xóa
        params.add(new SqlParameter("p_dsxoa_cpu", input.getDsxoa_cpu(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dsxoa_ram", input.getDsxoa_ram(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dsxoa_gpu", input.getDsxoa_gpu(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dsxoa_ocung", input.getDsxoa_ocung(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dsxoa_blade", input.getDsxoa_blade(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dsxoa_khd", input.getDsxoa_khd(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_dsxoa_dkh", input.getDsxoa_dkh(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        //isBoDieuKhien
        params.add(new SqlParameter("p_dkh_dl_ram", input.getDkh_dl_ram(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dkh_sl_corecpu", input.getDkh_sl_corecpu(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_dkh_dl_cache", input.getDkh_dl_cache(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        //isKhayDia
        params.add(new SqlParameter("p_khd_sl_khessd", input.getKhd_sl_khessd(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_khd_sl_ssd", input.getKhd_sl_ssd(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_khd_sl_khehdd", input.getKhd_sl_khehdd(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_khd_sl_hdd", input.getKhd_sl_hdd(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_khd_sl_khenvme", input.getKhd_sl_khenvme(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_khd_sl_nvme", input.getKhd_sl_nvme(),
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_khd_ds_ocung", input.getKhd_ds_ocung(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_khd_dsxoa_ocung", input.getKhd_dsxoa_ocung(),
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        params.add(new SqlParameter("p_result", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_THIETBI_PHANCUNG.sp_luu_cauhinh_phancung",
                params);
    }
}
