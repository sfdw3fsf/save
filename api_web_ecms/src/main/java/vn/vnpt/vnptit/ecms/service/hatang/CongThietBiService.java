package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.HangSanXuatDto;
import vn.vnpt.vnptit.ecms.dto.hatang.CongThietBiDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CongThietBiService {

    private final DbContext dbContext;

    public CongThietBiService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Map<String, Object> dsThongTinCong(String tenCong) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten", tenCong, Types.NVARCHAR));
        params.add(new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_COMMON.SP_LAY_THONGTIN_CONG", params).get(0);
    }
    public Map<String, Object> dsThongTinCongId(Long Id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", Id, Types.NUMERIC));
        params.add(new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_COMMON.SP_LAY_THONGTIN_CONG_THEO_ID", params).get(0);
    }
    public ArrayList<Map<String, Object>> dsThongTinKetNoiCong(String tenCong) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten", tenCong, Types.NVARCHAR));
        params.add(new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_COMMON.SP_LAY_DANHSACH_THONGTIN_DAUNOI_THIETBI", params);
    }

    public void setHangSanXuat(HangSanXuatDto input) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id", input.getpId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", input.getpTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_mahsx", input.getpMaHSX(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ghichu", input.getpGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_hieuluc", input.getpHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_COMMON.sp_set_danhmuc_hang_sanxuat", params);
    }

    // Get chi tiet cong thiet bi
    public Object getCongThietBiByID(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_GET_CONG_THIET_BI_BY_ID", params);
    }

    // get thiet bi noi cong
    public Object getThietBiNoiCongByIDCong(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_GET_THIET_BI_BY_ID_CONG", params);
    }

    // get lich su thay doi
    public Object getLichSuByIDCong(Number id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_GET_LICH_SU_THAY_DOI_BY_ID_CONG", params);
    }

    // update thong tin cong
    public boolean updateCongThietBi(CongThietBiDTO dto) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_sothutu", dto.getSothutu(), Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_hieuluc", dto.getHieuLuc(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaicong", dto.getLoaiCongID(), Types.NUMERIC));
        params.add(new SqlParameter("p_mucdichsudungcong", dto.getMucDichSuDungCongID(), Types.NUMERIC));
        params.add(new SqlParameter("p_bangthongcong", dto.getBangThongCongID(), Types.NUMERIC));
        params.add(new SqlParameter("p_thongtinmodule", dto.getThongTinModuleID(), Types.NUMERIC));
        params.add(new SqlParameter("p_ketnoi", dto.getKetnoi(), Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_sd", dto.getTrangthai_sd(), Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), Types.VARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_CONG_THIET_BI_UPDATE", params);
        return result.getOutValue().toString().equals("1");
    }
}
