package vn.vnpt.vnptit.qlsc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.CapNhatChuKyNhanTinDtoInp;
import vn.vnpt.vnptit.qlsc.dto.NhanVienNhanSMSDtoInp;
import vn.vnpt.vnptit.qlsc.dto.QuanLyDieuHanhTuDongInputDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QuanLyDieuHanhTuDongService {
    private final DbContext dbContext;

    public QuanLyDieuHanhTuDongService(DbContext dbContext){
        this.dbContext = dbContext;
    }

    public Object Get_DS_Loai_TinNhan() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_GET_DS_LOAI_TINNHAN", params);
    }

    public Object Get_DS_Cap_TinNhan(int loaitn_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_LOAITN_ID", loaitn_id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_GET_DS_CAP_TINNHAN", params);
    }

    public Object Get_DS_Donvi() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_GET_DS_DONVI_DULIEU", params);
    }

    public Object Get_DS_NV_DaGan_Paging(NhanVienNhanSMSDtoInp input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_TINNHAN_ID", input.getTinnhan_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_PAGE_NUM", input.getPage_num(), Types.NUMERIC));
        params.add(new SqlParameter("P_PAGE_SIZE", input.getPage_size(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR, "dsnv_dagan"));
        params.add(new SqlParameter("P_PAGINFO", Types.REF_CURSOR, "paging"));
        return dbContext.executeSpIdxWithCursorsToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_GET_DS_NHANVIEN_DAGAN_PAGING", params);
    }

    public Object Get_DS_NV_ChuaGan_Paging(NhanVienNhanSMSDtoInp input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_TINNHAN_ID", input.getTinnhan_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_PAGE_NUM", input.getPage_num(), Types.NUMERIC));
        params.add(new SqlParameter("P_PAGE_SIZE", input.getPage_size(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR, "dsnv_chuagan"));
        params.add(new SqlParameter("P_PAGINFO", Types.REF_CURSOR, "paging"));
        return dbContext.executeSpIdxWithCursorsToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_GET_DS_NHANVIEN_CHUAGAN_PAGING", params);
    }

    public Object Update_NV_SMS(int tinnhan_id, String dsnv_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_TINNHAN_ID", tinnhan_id, Types.NUMERIC));
        params.add(new SqlParameter("P_DSNV_ID", dsnv_id, Types.VARCHAR));
        params.add(new SqlParameter("P_NGAY_CN", new Date(), Types.DATE));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getMaNhanVien(), Types.VARCHAR));
        params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_UPDATE_NHANVIEN_NHANSMS", params);
        if(rs.size() > 0) {
            if(Integer.parseInt(rs.get(0).get("KETQUA").toString()) == 1) {
                rs.get(0).put("KETQUA", true);
            }
            else {
                rs.get(0).put("KETQUA", false);
            }
        }
        return rs.get(0);
    }

    public Object Update_DonVi_SMS(int tinnhan_id, int nhanvien_id, String donvi_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),  Types.NUMERIC));
        params.add(new SqlParameter("P_TINNHAN_ID", tinnhan_id, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", nhanvien_id, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", donvi_id, Types.VARCHAR));
        params.add(new SqlParameter("P_NGAY_CN", new Date(), Types.DATE));
        params.add(new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(), Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getMaNhanVien(), Types.VARCHAR));
        params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(), Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_UPDATE_DONVI_NHAN_SMS", params);
        if(rs.size() > 0) {
            if(Integer.parseInt(rs.get(0).get("KETQUA").toString()) == 1) {
                rs.get(0).put("KETQUA", true);
            }
            else {
                rs.get(0).put("KETQUA", false);
            }
        }
        return rs.get(0);
    }

    public Object Get_DS_MucDoSC() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_GET_DS_MUCDO_SUCO", params);
    }

    public Object Get_DS_MucDoSC_ById(int mucdo_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_MUDCO_ID", mucdo_id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_GET_MUCDO_SUCO_BY_ID", params);
    }

    public Object Insert_MucDo_SC(QuanLyDieuHanhTuDongInputDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_MUCDO_SC", input.getMucdo_sc(), Types.VARCHAR));
        params.add(new SqlParameter("P_CANHBAO_DT", input.getCanhbao_dt(), Types.NUMERIC));
        params.add(new SqlParameter("P_MOTA", input.getMota(), Types.VARCHAR));
        params.add(new SqlParameter("P_MAUNEN", input.getMaunen(), Types.VARCHAR));
        params.add(new SqlParameter("P_MAUCHU", input.getMauchu(), Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_INSERT_MUCDO_SUCO", params);
    }

    public Object Update_MucDo_SC(QuanLyDieuHanhTuDongInputDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_MUDO_ID", input.getMucdo_id(), Types.VARCHAR));
        params.add(new SqlParameter("P_MUCDO_SC", input.getMucdo_sc(), Types.VARCHAR));
        params.add(new SqlParameter("P_CANHBAO_DT", input.getCanhbao_dt(), Types.NUMERIC));
        params.add(new SqlParameter("P_MOTA", input.getMota(), Types.VARCHAR));
        params.add(new SqlParameter("P_MAUNEN", input.getMaunen(), Types.VARCHAR));
        params.add(new SqlParameter("P_MAUCHU", input.getMauchu(), Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_UPDATE_MUCDO_SUCO", params);
        if(rs.size() > 0) {
            if(Integer.parseInt(rs.get(0).get("KETQUA").toString()) == 1) {
                rs.get(0).put("KETQUA", true);
            }
            else {
                rs.get(0).put("KETQUA", false);
            }
        }
        return rs.get(0);
    }

    public Object Delete_MucDo_SC(int mucdo_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_MUDO_ID", mucdo_id, Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_DELETE_MUCDO_SUCO", params);
        if(rs.size() > 0) {
            if(Integer.parseInt(rs.get(0).get("KETQUA").toString()) == 1) {
                rs.get(0).put("KETQUA", true);
            }
            else {
                rs.get(0).put("KETQUA", false);
            }
        }
        return rs.get(0);
    }

    public Object Get_DS_CHUKY() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_GET_DS_CHUKY_DH", params);
    }

    public Object Insert_DS_CHUKY(QuanLyDieuHanhTuDongInputDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_CAPDODH_ID", input.getCapdodh_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_MUCDO_ID", input.getMucdo_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_CHUKY_DH", input.getChuky_dh(), Types.VARCHAR));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_INSERT_CHUKY_DH", params);
    }

    public Boolean Update_DS_CHUKY(List<CapNhatChuKyNhanTinDtoInp> input) throws AppSqlException, JsonProcessingException {
        List<SqlParameter> params = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_LIST", input != null ? objectMapper.writeValueAsString(input) : "", Types.CLOB));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return "1".equals(dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_UPDATE_CHUKY_DH", params).get(0).get("KETQUA").toString());
    }

    public Object Delete_DS_CHUKY(int id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_CHUKY_ID", id, Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        List<Map<String, Object>> rs = dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_DELETE_CHUKY_DH", params);
        if(rs.size() > 0) {
            if(Integer.parseInt(rs.get(0).get("KETQUA").toString()) == 1) {
                rs.get(0).put("KETQUA", true);
            }
            else {
                rs.get(0).put("KETQUA", false);
            }
        }
        return rs.get(0);
    }

    public Object Get_DS_NV_DaGan(QuanLyDieuHanhTuDongInputDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_TINNHAN_ID", input.getTinnhan_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_GET_DS_NHANVIEN_DAGAN", params);
    }

    public Object Get_DS_NV_ChuaGan(QuanLyDieuHanhTuDongInputDto input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("P_TINNHAN_ID", input.getTinnhan_id(), Types.NUMERIC));
        params.add(new SqlParameter("P_REF_CURSOR", Types.REF_CURSOR));
        return dbContext.executeSpIdxWithCursorToListMap("QLSC.PKG_QUANLY_DIEUHANH_TUDONG.SP_GET_DS_NHANVIEN_CHUAGAN", params);
    }
}
