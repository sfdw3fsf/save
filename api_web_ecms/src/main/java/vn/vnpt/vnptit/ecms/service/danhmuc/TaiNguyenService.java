package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DanhMucDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
@Service
public class TaiNguyenService {
    private final DbContext dbContext;

    public TaiNguyenService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getTrangThaiTaiNguyen() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ecms.PKG_HATANG_DANHMUC.SP_TRANGTHAI_TAINGUYEN_DANHSACH", param);
    }

    public ResponseEntity<ApiResult>  capNhatTrangThaiTaiNguyen(DanhMucDtoIn input) throws AppSqlException {
        ApiResult rs = new ApiResult();
        if (input.getTen().isEmpty()){
            rs.setErrorCode("BSS-00009490");
            rs.setMessage(String.format("Chưa nhập nội dung tên !", input.getTen()));
            return rs.getResponseEntity();
        }
        try {
            ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
            params.add(new SqlParameter("p_id", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ten", input.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ghi_chu", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_hieu_luc", input.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            SqlParameter  result=new SqlParameter("p_result", 0, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
            params.add(result);
            dbContext.setConnection(ConnectionManager.Connections.CSS);
            dbContext.executeStoredProcedure("ECMS.PKG_HATANG_DANHMUC.SP_TRANGTHAI_TAINGUYEN_CAPNHAT", params);
            int kq=Integer.parseInt(result.getOutValue().toString());
            if (kq==1) {
                rs.setMessage("Cập nhật thành công!");
            }else if (kq==2) {
                rs.setErrorCode("BSS-00009490");
                rs.setMessage(String.format("Tên [%s] đã tồn tại vì vậy bạn không thể cập nhật!", input.getTen()));
            }else {
                rs.setMessage("Cập nhật không thành công");
                rs.setErrorCode("BSS-00009490");
            }
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();

    }

    public ResponseEntity<ApiResult>  xoaTrangThaiTaiNguyen(DanhMucDtoIn input) throws AppSqlException {
        ApiResult rs = new ApiResult();
        if (input.getId()==null || input.getId()<1){
            rs.setErrorCode("BSS-00009490");
            rs.setMessage(String.format("Chưa nhập số id để xóa dữ liệu !", input.getTen()));
            return rs.getResponseEntity();
        }
        try {
            ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
            params.add(new SqlParameter("p_id", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            SqlParameter  result=new SqlParameter("p_result", 0, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
            params.add(result);
            dbContext.setConnection(ConnectionManager.Connections.CSS);
            dbContext.executeStoredProcedure("ecms.PKG_HATANG_DANHMUC.SP_TRANGTHAI_TAINGUYEN_DELETE", params);
            int kq=Integer.parseInt(result.getOutValue().toString());
            if (kq==1) {
                rs.setMessage("Xóa thành công!");
            }else {
                rs.setMessage("Xóa không thành công");
                rs.setErrorCode("BSS_00004002");
            }
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

}
