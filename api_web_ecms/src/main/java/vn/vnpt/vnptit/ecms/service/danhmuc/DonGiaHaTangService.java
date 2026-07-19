package vn.vnpt.vnptit.ecms.service.danhmuc;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.vnpt.common.Utils;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.danhmuc.DonGiaHaTangDtoIn;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;
@Service
public class DonGiaHaTangService {
    private final DbContext dbContext;

    public DonGiaHaTangService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>> getData() throws AppSqlException {
        SqlParameter param = new SqlParameter("p_cur", null, SqlParameter.ParameterDirection.OUTPUT, java.sql.Types.REF_CURSOR);
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ecms.pkg_hatang_danhmuc.sp_dongia_hatang_danhsach", param);
    }

    public ResponseEntity<ApiResult>  capNhatDonGiaHaTang(DonGiaHaTangDtoIn input)  {
        ApiResult rs = new ApiResult();
        String msg="";
        if (input.getBatDau()!=null && !Utils.isDate(input.getBatDau(), "dd/MM/yyyy")){
            msg+=", Thời gian áp dụng từ ngày không đúng";
        }
        if (input.getKetThuc()!=null && !Utils.isDate(input.getKetThuc(), "dd/MM/yyyy")){
            msg+=", Thời gian áp dụng đến ngày không đúng";
        }
        if (input.getNgayVanBan()!=null && !Utils.isDate(input.getNgayVanBan(), "dd/MM/yyyy")){
            msg+=", Ngày văn bản không đúng";
        }

        if (!msg.isEmpty()) {
            rs.setErrorCode("BSS-00009490");
            rs.setMessage(msg.substring(2));
            return rs.getResponseEntity();
        }

        try {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_id", input.getId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_congnghe_id", input.getCongNgheId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_hatang_phanloai_id", input.getHaTangPhanLoaiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_hatang_phanloaitt_id", input.getHaTangPhanLoaiTtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_batdau", input.getBatDau(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ketthuc", input.getKetThuc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_sovanban", input.getSoVanBan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ngayvanban", input.getNgayVanBan(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_dongia_cpu", input.getDonGiaCPU(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_dongia_ram", input.getDonGiaRAM(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_dongia_disk", input.getDonGiaDISK(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_tyle_giam", input.getTyLeGiam(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ghi_chu", input.getGhiChu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_hieu_luc", input.getHieuLuc(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nvcn", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ma_nd", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ipcn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            SqlParameter  result=new SqlParameter("p_result", 0, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
            params.add(result);
            dbContext.setConnection(ConnectionManager.Connections.CSS);
            dbContext.executeStoredProcedure("ecms.pkg_hatang_danhmuc.sp_dongia_hatang_capnhat", params);
            int kq=Integer.parseInt(result.getOutValue().toString());
            if (kq==1) {
                rs.setMessage("Cập nhật thành công!");
            }else {
                rs.setMessage("Cập nhật không thành công");
                rs.setErrorCode("BSS-00009490");
            }
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();

    }

    public ResponseEntity<ApiResult>  xoaDonGiaHaTang(DonGiaHaTangDtoIn input)  {
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
            dbContext.executeStoredProcedure("ecms.pkg_hatang_danhmuc.sp_dongia_hatang_delete", params);
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
