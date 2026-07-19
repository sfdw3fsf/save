package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.TaiNguyenNFSDtoIn;

@Service
public class TaiNguyenNFSService {
    private final DbContext dbContext;

    public TaiNguyenNFSService(DbContext dbContext) {
        this.dbContext = dbContext;
    }


    public ArrayList<Map<String, Object>> danhSachTaiNguyenNFS() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ecms.pkg_hatang_tainguyen_aohoa.sp_nfs_danhsach", param);
    }

    public ResponseEntity<ApiResult> capNhatTaiNguyenNFS(TaiNguyenNFSDtoIn input)  {
        ApiResult rs = new ApiResult();
        String msg="";
        if (input.getTen().isEmpty()){
            msg+=", Tên chưa nhập";
        }

        if (!msg.isEmpty()) {
            rs.setErrorCode("BSS-00009490");
            rs.setMessage(msg.substring(2));
            return rs.getResponseEntity();
        }

        try {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ten", input.getTen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_link_backup", input.getLink_backup(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_phieuyeucau_id", input.getPhieuyeucau_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ghi_chu", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_hieu_luc", input.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.TINYINT));
            params.add(new SqlParameter("p_loaihatang_luutru", input.getLoaihatang_luutru(), SqlParameter.ParameterDirection.INPUT, Types.TINYINT));
            params.add(new SqlParameter("p_hatang_luutru_id", input.getHatang_luutru_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nangluc", input.getNangluc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_loailuutru", input.getLoailuutru(), SqlParameter.ParameterDirection.INPUT, Types.TINYINT));
            params.add(new SqlParameter("p_dungluong", input.getDungLuong(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_donviqly_id", input.getDonViQlyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_phongbanqly_id", input.getPhongBanQlyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nguoiqly_id", input.getNguoiQlyId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nvcn", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ma_nd", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ipcn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            SqlParameter  result=new SqlParameter("p_result", 0, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
            params.add(result);
            dbContext.setConnection(ConnectionManager.Connections.CSS);
            dbContext.executeStoredProcedure("ecms.pkg_hatang_tainguyen_aohoa.sp_nfs_capnhat", params);
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

    public ResponseEntity<ApiResult>  xoaTaiNguyenNFS(TaiNguyenNFSDtoIn input)  {
        ApiResult rs = new ApiResult();
        if (input.getId()==null || input.getId()<1){
            rs.setErrorCode("BSS-00009490");
            rs.setMessage("Chưa nhập số id để xóa dữ liệu !");
            return rs.getResponseEntity();
        }

        try {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nvcn", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ma_nd", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ipcn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            SqlParameter  result=new SqlParameter("p_result", 0, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
            params.add(result);
            dbContext.setConnection(ConnectionManager.Connections.CSS);
            dbContext.executeStoredProcedure("ecms.pkg_hatang_tainguyen_aohoa.sp_nfs_delete", params);
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

    public Object getNFSSuDung(String loaiDanhMuc, Long id) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_loai_danhmuc", loaiDanhMuc, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_id", id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ecms.pkg_hatang_tainguyen_aohoa.sp_nfs_sudung", params);
    }
}
