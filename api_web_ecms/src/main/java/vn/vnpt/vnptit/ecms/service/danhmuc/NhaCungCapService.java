package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.NhaCungCapDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
@Service
public class NhaCungCapService {
    private final DbContext dbContext;

    public NhaCungCapService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> dsNhaCungCap() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_COMMON.sp_get_danhmuc_nha_cungcap", param);
    }
    public void setNhaCungCap(NhaCungCapDTO input) throws AppSqlException {

            ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
            params.add(new SqlParameter("p_id", input.getpId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ten", input.getpTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_diachi", input.getpDiaChi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_dienthoai", input.getpDienThoai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_email", input.getpEmail(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_website", input.getpWebsite(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_taikhoan", input.getpSoTaiKhoan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_nguoi_daidien", input.getpNguoiDaiDien(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ghichu", input.getpGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ma_ncc", input.getpMaNCC(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_hieuluc", input.getpHieuLuc()?1:0, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            dbContext.setConnection(ConnectionManager.Connections.CSS);
            dbContext.executeStoredProcedure("ECMS.PKG_HATANG_COMMON.sp_set_danhmuc_nha_cungcap", params);
    }
    public void deleteNhaCungCap(Long input) throws AppSqlException {
        SqlParameter param = new SqlParameter("p_id", input, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_COMMON.sp_delete_danhmuc_nha_cungcap", param);
    }
}
