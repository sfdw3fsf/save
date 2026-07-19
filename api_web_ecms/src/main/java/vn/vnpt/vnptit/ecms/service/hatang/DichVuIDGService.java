package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import vn.vnpt.vnptit.ecms.dto.hatang.SearchDichVuIDGDto;
import vn.vnpt.vnptit.ecms.dto.hatang.PhieuHuyGiaHanSmartCloudDto;
import vn.vnpt.vnptit.ecms.dto.hatang.SaveIdgGiaHanDto;
import vn.vnpt.vnptit.ecms.dto.hatang.SaveVpcGiaHanDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class DichVuIDGService {
    private final DbContext dbContext;

    public DichVuIDGService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getDanhMucDoiTuong() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_GET_DOITUONG_IDG", params);
    }

    public ArrayList<Map<String, Object>> getDanhMucTrangThai() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_GET_TRANGTHAI_IDG", params);
    }

    public ArrayList<Map<String, Object>> getDanhMucLoaiDichVu() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_GET_LOAIDV_IDG", params);
    }

    public ArrayList<Map<String, Object>> getDanhMucTenSPDV() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_GET_TEN_SPDV_IDG", params);
    }

    public ArrayList<Map<String, Object>> searchDichVuIDG(SearchDichVuIDGDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_madv", input.getMadv(), Types.VARCHAR));
        params.add(new SqlParameter("p_tendv", input.getTendv(), Types.VARCHAR));
        params.add(new SqlParameter("p_doituong_id", input.getDoituong_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_id", input.getTrangthai_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_ql_id", input.getDonvi_ql_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_idc_id", input.getIdc_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_cumhatang_id", input.getCumhatang_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaidv_id", input.getLoaidv_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_cq_id", input.getDonvi_cq_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_canhan_ql_id", input.getCanhan_ql_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_tinhphi", input.getTinhphi(), Types.NUMERIC));
        params.add(new SqlParameter("p_hieuluc", input.getHieuluc(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngaycaptu", input.getNgaycaptu(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngaycapden", input.getNgaycapden(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngayhethantu", input.getNgayhethantu(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngayhethanden", input.getNgayhethanden(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten_spds_id", input.getTen_spds_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_cum_spdv_id", input.getCum_spdv_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_SEARCH_DICHVU_IDG", params);
    }

    public ArrayList<Map<String, Object>> getDanhSachHaTangIDGVmware() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_HATANG_IDG_VMWARE", params);
    }

    public ArrayList<Map<String, Object>> getDanhSachHaTangIDGSmartCloud() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_HATANG_IDG_SMART_CLOUD",
                params);
    }

    public Object getPhieuHuyGiaHanSmartCloud(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_PHIEU_HUY_GIAHAN_SMART_CLOUD",
                params);
    }

    public Object getPhieuHuyGiaHanVmWare(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_PHIEU_HUY_GIAHAN_VMWARE", params);
    }

    public Object getPhieuHuyGiaHanIDG(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_PHIEU_HUY_GIAHAN_IDG", params);
    }

    public Map<String, Object> saveVpcGiaHan(SaveVpcGiaHanDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieu_yc_id", input.getPhieu_yc_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaiht_id", input.getLoaiht_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ma_dich_vu", input.getMa_dich_vu(), Types.VARCHAR));
        params.add(new SqlParameter("p_trangthai_id", input.getTrangthai_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_langui", input.getSo_langui(), Types.NUMERIC));
        params.add(new SqlParameter("p_cumht_id", input.getCumht_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_hh", input.getNgay_hh(), Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_gh", input.getNgay_gh(), Types.VARCHAR));
        params.add(new SqlParameter("p_donvi_cq_id", input.getDonvi_cq_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_ql_id", input.getDonvi_ql_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_ql_id", input.getNhanvien_ql_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", input.getNguoi_cn(), Types.VARCHAR));
        params.add(new SqlParameter("p_mota", input.getMota(), Types.VARCHAR));
        params.add(new SqlParameter("p_ghichu", input.getGhichu(), Types.VARCHAR));
        params.add(new SqlParameter("p_ten_dangnhap", input.getTen_dangnhap(), Types.VARCHAR));
        params.add(new SqlParameter("p_slht_cpu", input.getSlht_cpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlht_ram", input.getDlht_ram(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlht_gpu", input.getDlht_gpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlht_ssd", input.getDlht_ssd(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlht_hdd", input.getDlht_hdd(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlht_nvme", input.getDlht_nvme(), Types.NUMERIC));
        params.add(new SqlParameter("p_slyc_cpu", input.getSlyc_cpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlyc_ram", input.getDlyc_ram(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlyc_gpu", input.getDlyc_gpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlyc_ssd", input.getDlyc_ssd(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlyc_hdd", input.getDlyc_hdd(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlyc_nvme", input.getDlyc_nvme(), Types.NUMERIC));
        params.add(new SqlParameter("p_hedieuhanh_id", input.getHedieuhanh_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_kientruc", input.getKientruc(), Types.VARCHAR));
        params.add(new SqlParameter("p_mayao_id", input.getMayao_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_is_vpc", input.getIs_vpc(), Types.NUMERIC));

        SqlParameter p_error_code = new SqlParameter("p_error_code", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        SqlParameter p_error_msg = new SqlParameter("p_error_msg", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR);
        params.add(p_error_code);
        params.add(p_error_msg);

        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_SAVE_VPC_GIAHAN", params);

        Map<String, Object> result = new HashMap<>();
        result.put("p_error_code", p_error_code.getOutValue());
        result.put("p_error_msg", p_error_msg.getOutValue());
        return result;
    }

    public Map<String, Object> saveIdgGiaHan(SaveIdgGiaHanDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phieu_yc_id", input.getPhieu_yc_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaiht_id", input.getLoaiht_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ma_dich_vu", input.getMa_dich_vu(), Types.VARCHAR));
        params.add(new SqlParameter("p_trangthai_id", input.getTrangthai_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_so_langui", input.getSo_langui(), Types.NUMERIC));
        params.add(new SqlParameter("p_cumht_id", input.getCumht_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_ngay_hh", input.getNgay_hh(), Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_gh", input.getNgay_gh(), Types.VARCHAR));
        params.add(new SqlParameter("p_donvi_cq_id", input.getDonvi_cq_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_ql_id", input.getDonvi_ql_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_ql_id", input.getNhanvien_ql_id(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", input.getNguoi_cn(), Types.VARCHAR));
        params.add(new SqlParameter("p_mota", input.getMota(), Types.VARCHAR));
        params.add(new SqlParameter("p_ghichu", input.getGhichu(), Types.VARCHAR));
        params.add(new SqlParameter("p_ten_dangnhap", input.getTen_dangnhap(), Types.VARCHAR));
        params.add(new SqlParameter("p_slht_corecpu", input.getSlht_corecpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlht_ram", input.getDlht_ram(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlht_storage", input.getDlht_storage(), Types.NUMERIC));
        params.add(new SqlParameter("p_slyc_corecpu", input.getSlyc_corecpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlyc_ram", input.getDlyc_ram(), Types.NUMERIC));
        params.add(new SqlParameter("p_dlyc_storage", input.getDlyc_storage(), Types.NUMERIC));
        params.add(new SqlParameter("p_dichvuht_id", input.getDichvuht_id(), Types.NUMERIC));

        SqlParameter p_error_code = new SqlParameter("p_error_code", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
        SqlParameter p_error_msg = new SqlParameter("p_error_msg", null, SqlParameter.ParameterDirection.OUTPUT, Types.VARCHAR);
        params.add(p_error_code);
        params.add(p_error_msg);

        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_SAVE_IDG_GIAHAN", params);

        Map<String, Object> result = new HashMap<>();
        result.put("p_error_code", p_error_code.getOutValue());
        result.put("p_error_msg", p_error_msg.getOutValue());
        return result;
    }

}
