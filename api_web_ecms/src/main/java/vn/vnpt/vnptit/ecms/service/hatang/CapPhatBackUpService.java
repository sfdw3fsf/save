package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.hatang.CapPhatBackupDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CapPhatBackUpService {
    private final DbContext dbContext;

    public CapPhatBackUpService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> layDSPhieuCapPhatBackup() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_CAPPHAT_TN_BACKUP.sp_lay_ds_phieu_capphat_backup", param);
    }

    public Object capNhatCapPhatBackup(CapPhatBackupDto data) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add( new SqlParameter("p_id", data.getId(), Types.NUMERIC));
        params.add( new SqlParameter("p_kho_backup_id", data.getKhoBackupId(), Types.NUMERIC));
        params.add( new SqlParameter("p_ngay_hethan", data.getNgayHethan(), Types.VARCHAR));
        params.add( new SqlParameter("p_ngay_capnhat", data.getNgayCapnhat(), Types.VARCHAR));
        params.add( new SqlParameter("p_dv_vanhanh_id", data.getDvVanhanhId(), Types.NUMERIC));
        params.add( new SqlParameter("p_phongban_id", data.getPhongbanId(), Types.NUMERIC));
        params.add( new SqlParameter("p_nguoi_quanly_id", data.getNguoiQuanlyId(), Types.NUMERIC));
        params.add( new SqlParameter("p_nguoi_capnhat_id", data.getNguoiCapnhatId(), Types.NUMERIC));
        params.add( new SqlParameter("p_ghichu", data.getGhiChu(), Types.NVARCHAR));
        params.add( new SqlParameter("p_nguoi_sudung_id", data.getNguoiSudungId(), Types.NUMERIC));
        params.add( new SqlParameter("p_file_id", data.getFileId(), Types.NUMERIC));

        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);

        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_CAPPHAT_TN_BACKUP.sp_capnhat_capphat_backup", params);
        return result.getOutValue().toString().equals("1");
    }

    public Object xacNhanCapPhatBackup(Long id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add( new SqlParameter("p_id", id, Types.NUMERIC));
        params.add( new SqlParameter("p_nguoi_capnhat_id", AppRequestContext.getCurrent().getNhanVienID(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_CAPPHAT_TN_BACKUP.sp_xacnhan_capphat_backup", params);
        return result.getOutValue().toString().equals("1");
    }

    public ArrayList<Map<String, Object>> layDSNguoiSuDung(Number phanvungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", phanvungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_CAPPHAT_TN_BACKUP.sp_danhsach_nguoisudung", params);
    }

    public ArrayList<Map<String, Object>> layDSKhoHaTangBackup() throws AppSqlException {
        SqlParameter param = new SqlParameter("cur_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_CAPPHAT_TN_BACKUP.sp_ds_kho_backup", param);
    }
}
