package vn.vnpt.vnptit.ecms.service;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.*;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BTSService {
    private DbContext dbContext;

    public BTSService(DbContext dbContext){
        this.dbContext = dbContext;
    }

    public ArrayList<Map<String, Object>>lay_ds_bts_nodeb(DsBTSNodebDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_upe_id", input.getP_upe_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_swl2_id", input.getP_swl2_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phan_loai", input.getP_phan_loai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma_node", input.getP_ma_node(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ten_thiet_bi", input.getP_ten_thiet_bi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_diachi", input.getP_diachi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        // bosung 07/07/2023
        params.add(new SqlParameter("p_khuvuc_id", input.getP_khuvuc_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_khuvuc_xuly_id", input.getP_khuvuc_xuly_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_ttdh_id", input.getP_nhanvien_ttdh_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_so_dt_ttdh", input.getP_so_dt_ttdh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        // bosung 17/07/2023
        params.add(new SqlParameter("p_donvi_xlsc_id", input.getP_donvi_xlsc_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_quanly_id", input.getP_donvi_quanly_id(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));


        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.SP_LAY_DS_BTS_NODEB", params);
    }

//    @Transactional
    public Object lay_tt_tbi_bts(DsBTSNodebDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phan_loai", input.getP_phan_loai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma_node", input.getP_ma_node(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        return dbContext.executeFunction("CSS.FN_LAY_THONGTIN_THIETBI_BTS", 12, params);
//        return dbContext.executeFunction("CSS.FN_LAY_THONGTIN_THIETBI_BTS", 12,params);
//        String SQL_FIND_THONG_TIN_THIET_BI = "select to_char(CSS.FN_LAY_THONGTIN_THIETBI_BTS(?,?,?)) from dual";
//        List<Map<String,Object >> query = entityManager.createNativeQuery(SQL_FIND_THONG_TIN_THIET_BI)
//                .setParameter(1, AppRequestContext.getCurrent().getPhanVungID())
//                .setParameter(2, input.getP_phan_loai())
//                .setParameter(3, input.getP_ma_node())
//                .getResultList();
//
//        return query;
    }

    public Object lay_danhmuc_bts_nodeb() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("returnds_phanloai", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        params.add(new SqlParameter("returnds_upe", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        params.add(new SqlParameter("returnds_swl2", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        params.add(new SqlParameter("returnds_trangthaihd", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        params.add(new SqlParameter("returnds_quanhuyen", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorsToListMap("CSS.SP_LAY_DANHMUC_BTS_ENODEB", params);
    }

    public Object capnhat_thongtin_bts(CapNhatThongTinBTSDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_action_type", input.getP_action_type(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_json_input", input.getP_json_input(), SqlParameter.ParameterDirection.INPUT, Types.CLOB));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ip_cn", AppRequestContext.getCurrent().getClientIpAddress(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        return dbContext.executeFunction("CSS.FN_CAPNHAT_THONGTIN_BTS", 12, params);
    }

    public Object lay_chitiet_bts(DsBTSNodebDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_phan_loai", input.getP_phan_loai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ma_node", input.getP_ma_node(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "returnds"));
        params.add(new SqlParameter("p_returnds_kv", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "returnds_kv"));
        return dbContext.executeSpWithCursorsToListMap("CSS.SP_LAY_CHITIET_BTS_ENODEB", params);
    }

    public Object lay_ds_khuvuc_khoandiaban(DsKhuVucKhoanDiaBanDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaikv_id", input.getP_loaikv_id(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ref_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "returnds"));
        return dbContext.executeSpWithCursorsToListMap("CSS.sp_lay_ds_khuvuc_khoandiaban", params);
    }

    public Object sp_lay_ds_khuvuc_khoandiaban_xlsc(DsKhuVucKhoanDiaBanXlscDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_loaikv_id", input.getP_loaikv_id(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ref_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "returnds"));
        return dbContext.executeSpWithCursorsToListMap("CSS.sp_lay_ds_khuvuc_khoandiaban_xlsc", params);
    }

    public Object lay_ds_nhanvien_theo_donvi(DsNhanVienTheoDonViDtoIn input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_id", input.getDonvi_id(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("returnds", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "returnds"));
        return dbContext.executeSpWithCursorsToListMap("CSS.SP_LAY_DS_NHANVIEN_THEO_DONVI", params);
    }

    public Object layds_donvi_xlsc() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ref_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "returnds"));
        return dbContext.executeSpWithCursorsToListMap("QLSC.sp_layds_donvi_xlsc", params);
    }

    public Object layds_donvi_quanly_bts() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ref_cursor", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR, "returnds"));
        return dbContext.executeSpWithCursorsToListMap("QLSC.sp_layds_donvi_quanly_bts", params);
    }
}
