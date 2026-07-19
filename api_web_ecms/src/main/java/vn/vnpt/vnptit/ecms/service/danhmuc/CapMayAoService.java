package vn.vnpt.vnptit.ecms.service.danhmuc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.danhmuc.MayAoDTO;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class CapMayAoService {
    private final DbContext dbContext;
    public  CapMayAoService(DbContext dbContext){
        this.dbContext = dbContext;
    }

    public void capNhatMayAo(MayAoDTO mayAoDTO) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_fields", formatJsonObject(mayAoDTO), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nguoidung_id", AppRequestContext.getCurrent().getNguoiDungID() , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID() , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_QUANLY.THEM_MAY_AO",params);
    }
    public void xacNhanCapMayAo(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_id",id, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        dbContext.executeStoredProcedure("ECMS.PKG_HATANG_QUANLY.sp_xacnhan_capphat_mayao",params);
    }
    public ArrayList<Map<String, Object>>  getDanhSach() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        //params.add(new SqlParameter("p_nguoidung_id", AppRequestContext.getCurrent().getNguoiDungID() , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID() , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("cur_returnds",null,SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_QUANLY.get_danhsach_mayao",params);
    }
    public ArrayList<Map<String, Object>>  getDanhSachDonViCap2(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        //params.add(new SqlParameter("p_nguoidung_id", AppRequestContext.getCurrent().getNguoiDungID() , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_cha",id , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("cur_returnds",null,SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_QUANLY.sp_danhsach_donvi_con",params);
    }
    public ArrayList<Map<String, Object>>  getDanhSachNhanVienDonVi(Long id) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        //params.add(new SqlParameter("p_nguoidung_id", AppRequestContext.getCurrent().getNguoiDungID() , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id",id , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("cur_returnds",null,SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_QUANLY.sp_danhsach_nhanvien_quanly_theo_donvi",params);
    }
    public ArrayList<Map<String, Object>>  getHaTangAoHoaIDG(Long pId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        //params.add(new SqlParameter("p_nguoidung_id", AppRequestContext.getCurrent().getNguoiDungID() , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_hatang_aohoa_id", pId , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("cur_returnds",null,SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_QUANLY.sp_danhmuc_hangtang_aohoa_idg",params);
    }

    public static <T> String formatJsonObject(T list){
        try {
            return new ObjectMapper().writeValueAsString(list);
        }
        catch(Exception ex)
        {
            return "";
        }
    }

    public ArrayList<Map<String, Object>>  capNhatIPMayAo(Number pId, String p_ip_address_id_list) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID() , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mayao_id", pId , SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ip_address_id_list", p_ip_address_id_list , SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("cur_returnds",null,SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_QUANLY.capnhat_ip_mayao",params);
    }
}
