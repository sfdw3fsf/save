package vn.vnpt.vnptit.ecms.service.idc;


import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.ecms.dto.idc.ThongTinPortDto;
import vn.vnpt.vnptit.ecms.dto.idc.ViTriThietBiRequest;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MayPhatDienService {

    private final DbContext dbContext;

    public MayPhatDienService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object addOrUpdateThietBiPort( ThongTinPortDto dto ) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();
        if(dto.getPortId() != null ) {
            params.add(new SqlParameter("P_PORT_ID", dto.getPortId(), Types.NUMERIC));
        }
        params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiId(),
                java.sql.Types.NUMERIC));
        params.add(new SqlParameter("P_TEN", dto.getTen(),
                java.sql.Types.NVARCHAR));

        params.add(new SqlParameter("P_LOAI_NDIEN_ID", dto.getLoaiNguonDien(),
                java.sql.Types.NUMERIC));

        params.add(new SqlParameter("P_KETNOI", dto.getTrangThaiKetNoi(),
                java.sql.Types.NUMERIC));

        params.add(new SqlParameter("P_TRANGTHAI_SD", dto.getTrangThaiSD(),
                java.sql.Types.NUMERIC));

        params.add(new SqlParameter("P_LOAICONG_ID", dto.getLoaiCongId(),
                java.sql.Types.NUMERIC));

        params.add(new SqlParameter("P_DD_DINHDANH", dto.getDdDinhDanh(),
                java.sql.Types.NUMERIC));

        params.add(new SqlParameter("P_DA_DINHDANH", dto.getDienApDinhDanh(),
                java.sql.Types.NUMERIC));

        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                java.sql.Types.NUMERIC));

        if(dto.getPortId() != null ){
            SqlParameter result = new SqlParameter("P_RETURN_ID", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
            params.add(result);
            return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.SP_UPDATE_THIETBI_PORT", params);
        } else {

            return dbContext.executeSpWithCursorToListMap(
                    "ECMS.PKG_IDC_HATANG_CNTT.SP_ADD_THIETBI_PORT",
                    params
            );
        }
    }

    public List<Map<String, Object>> getThietBiPort(BigDecimal portId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_PORT_ID", portId,
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RESULT", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        // Gọi procedure và nhận kết quả từ cursor
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANG_CNTT.SP_GET_THIETBI_PORT",
                params
        );
    }

    public List<Map<String, Object>> getThietBiPortByLoaiCong(BigDecimal thietBiId, Long loaiCongId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_THIETBI_ID", thietBiId,
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAICONG_ID", loaiCongId,
                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RESULT", null,
                SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANG_CNTT.SP_GET_THIETBI_PORT_BY_THIETBI",
                params
        );
    }

    public Object deleteThietBiPort(BigDecimal thietBiPortId) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter(
                "P_TB_PORT_ID",
                thietBiPortId,
                SqlParameter.ParameterDirection.INPUT,
                Types.NUMERIC
        ));
        // Gọi procedure
        dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANG_CNTT.SP_DELETE_THIETBI_PORT",
                params
        );
        return "xóa thành công!";
    }

    public Map<String, Object> getThietBiById(Long thietBiId) throws AppSqlException {
        String sql = "SELECT * FROM ECMS.THIETBI WHERE ID = " + thietBiId;

        List<Map<String, Object>> resultList = dbContext.executeQueryToListMap(sql);

        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0); // lấy bản ghi đầu tiên
        } else {
            return null; // không tìm thấy
        }
    }

    public List<Map<String, Object>> findIdcRack(Long phongId) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter( "P_PHONG_ID", phongId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC ));
        params.add(new SqlParameter( "P_RESULT", java.sql.Types.REF_CURSOR ));

        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANG_CNTT.SP_FIND_IDC_RACK",
                params
        );
    }

    public List<Map<String, Object>> getViTriRackU(Long idcRackId, Long min, Long max) throws AppSqlException {
        try{
            String sql = null;
            if (min == max) {
                sql = "SELECT ir.RACKUNIT_ID,ir.RACK_ID,ir.VITRI,ir.THIETBI_ID FROM ECMS.IDC_RACKUNIT ir WHERE RACK_ID = " + idcRackId +
                        " AND VITRI = " + min;
            } else {
                sql = "SELECT ir.RACKUNIT_ID,ir.RACK_ID,ir.VITRI,ir.THIETBI_ID FROM ECMS.IDC_RACKUNIT ir WHERE RACK_ID = " + idcRackId +
                        " AND VITRI >= " + min + " AND VITRI < " + max;
            }

            List<Map<String, Object>> resultList = dbContext.executeQueryToListMap(sql);
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Map<String, Object>>  insertViTriThietBi(ViTriThietBiRequest req, boolean isDelete) throws AppSqlException {
            if(!isDelete && req.getIdcRackId() != null){
                ArrayList<Map<String, Object>> data= new ArrayList<>();
                //validate RACKUNIT còn chỗ để đặt thiết bị không.
                List<Map<String, Object>> resultList = getViTriRackU( req.getIdcRackId(), req.getUpUnitId(),req.getDownUnitId());
                if(resultList == null ||  resultList.isEmpty()){
                    Map<String, Object> errorMap = new HashMap<>();
                    errorMap.put("error", "Không tìm thấy vị trí rack!");
                    data.add(errorMap);
                    return data;
                }
                if(resultList.size() < req.getDownUnitId() - req.getUpUnitId()){
                    Map<String, Object> errorMap = new HashMap<>();
                    errorMap.put("error", "Không đủ chỗ trống trên rack để lắp đặt!");
                    data.add(errorMap);
                    return data;
                }
            }

            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("P_ID",        req.getThietBiId(),       SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_IDC_ID",        req.getIdcId(),       SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_DONVIQLY_ID",   req.getDonViQLyId(),  SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_MATSAN_ID",     req.getMatSanId(),    SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_TOANHA_ID",     req.getToaNhaId(),    SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_PHONG_ID",      req.getPhongId(),     SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_IDC_RACK_ID",   req.getIdcRackId(),   SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_SL_RACKUNIT",   req.getSlRackUnit(),  SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_KIEU_LD",       req.getKieuLapDat(),  SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_UP_UNIT_ID",    req.getUpUnitId(),    SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_DOWN_UNIT_ID",  req.getDownUnitId(),  SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_MAT_RACKUNIT",  req.getMatRackUnit(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            params.add(new SqlParameter("P_CANH_RACKUNIT", req.getCanhRackUnit(),SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
            params.add(new SqlParameter("P_NGUOI_CN",  AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));

            dbContext.executeSpWithCursorToListMap(
                    "ECMS.PKG_IDC_HATANG_CNTT.SP_UPDATE_VITRI_THIETBI",
                    params
            );

            //thêm dữ liệu vào Rackunit
            updateRackunitByRange(req);


        return null;
    }

    public ArrayList<Map<String, Object>> updateRackunitByRange(ViTriThietBiRequest dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RACK_ID", dto.getIdcRackId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_MIN", dto.getUpUnitId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_VITRI_MAX", dto.getDownUnitId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_UPDATED_ROWS", Types.REF_CURSOR));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.SP_UPDATE_IDC_RACKUNIT_BY_RANGE", params );
        // thêm dữ liệu vao RackunitchiTiet
        for (Map item: result){
            dto.setRackUnitId((BigDecimal) item.get("RACKUNIT_ID"));
            insertRackunitChiTiet(dto);
        }
        // Trả về số dòng đã update
        return result;
    }

    public  ArrayList<Map<String, Object>> insertRackunitChiTiet(ViTriThietBiRequest req) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("P_RACKUNIT_ID", req.getRackUnitId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MAT_RACKUNIT", req.getMatRackUnit(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_THIETBI_ID", req.getThietBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MOTA", req.getMota(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_NGUOI_CN",  AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
        params.add(new SqlParameter("P_CANH_RACKUNIT", req.getCanhRackUnit(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

        params.add(new SqlParameter("P_NEW_ID", Types.NUMERIC)); // OUT parameter

        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANG_CNTT.SP_INSERT_RACKUNIT_CHITIET",
                params
        );

        return result;
    }

    public Map<String, String> validate(ViTriThietBiRequest viTriDto) {
        Map<String, String> errors = new HashMap<>();

        if (viTriDto.getThietBiId() == null) {
            errors.put("thietBiId", "thietBiId không được để trống");
        }
        if (viTriDto.getIdcId() == null) {
            errors.put("idcId", "idc không được để trống");
        }
        if (viTriDto.getDonViQLyId() == null) {
            errors.put("donViQLyId", "donViQLyId không được để trống");
        }
        if (viTriDto.getMatSanId() == null) {
            errors.put("matSanId", "matSanId không được để trống");
        }
        if (viTriDto.getToaNhaId() == null) {
            errors.put("toaNhaId", "toaNhaId không được để trống");
        }
        if( viTriDto.getDownUnitId() != null && viTriDto.getUpUnitId() != null && (   viTriDto.getUpUnitId() > viTriDto.getDownUnitId()
                        || (viTriDto.getDownUnitId() - viTriDto.getUpUnitId())+1 != viTriDto.getSlRackUnit()
                )){
            errors.put("downUnitId", "lỗi DownUnitId không hợp lệ");
        }
        return errors;
    }


    public Map<String, Object> getLichSuTacDongPage(
            BigDecimal thietBiId,
            String tenBang,
            String khoaDuLieuList,
            String tenCotList,                  // ⭐ NEW: thêm tham số
            int pageNumber,
            int pageSize
    ) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();

        // 1) P_TENBANG_STR
        tenBang = tenBang + ",THIETBI,THIETBI_PORT";
        params.add(new SqlParameter("P_TENBANG_STR", tenBang,
                SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

        // 2) P_KHOA_DULIEU_STR
        List<Map<String, Object>> port = getThietBiPortByLoaiCong(thietBiId, null);
        String id = thietBiId + "," + port.stream()
                .map(item -> String.valueOf(item.get("ID")))
                .collect(Collectors.joining(","));

        if (khoaDuLieuList != null && !khoaDuLieuList.isEmpty()) {
            khoaDuLieuList = khoaDuLieuList + "," + id;
            params.add(new SqlParameter("P_KHOA_DULIEU_STR", khoaDuLieuList,
                    SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        } else {
            params.add(new SqlParameter("P_KHOA_DULIEU_STR", id,
                    SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        }

        // ⭐ 3) P_TENCOT_STR (THÊM MỚI)
        if (tenCotList != null && !tenCotList.isEmpty()) {
            params.add(new SqlParameter("P_TENCOT_STR", tenCotList,
                    SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        } else {
            params.add(new SqlParameter("P_TENCOT_STR", null,
                    SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        }

            params.add(new SqlParameter("P_PAGE_NUMBER", pageNumber, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("P_PAGE_SIZE", pageSize, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));

            // OUT param tổng số bản ghi
        params.add(new SqlParameter("P_TOTAL", null, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC, "P_TOTAL"));

            // OUT param cursor
            params.add(new SqlParameter("P_CURSOR", Types.REF_CURSOR));

            // Gọi SP
            List<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap(
                    "ECMS.PKG_IDC_HATANG_CNTT.SP_GET_LICHSU_TACDONG_PAGE",
                    params
            );
        Map<String, Object> mapdata  = new HashMap<>();
        mapdata.put("total",params.get(4).getOutValue());
        mapdata.put("list", result);
        return mapdata;
    }

    public ArrayList<Map<String, Object>> resetRackunitByThietBi(ViTriThietBiRequest dto) throws AppSqlException {

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_THIETBI_ID", dto.getThietBiId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_HATANG_CNTT.SP_RESET_IDC_RACKUNIT_BY_THIETBI_ID", params );
        return result;
    }

    public List<Map<String, Object>> getPhieuTcByThietBiId(Long thietBiId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        // Tham số input
        params.add(new SqlParameter( "P_THIETBI_ID", thietBiId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC ));
        params.add(new SqlParameter( "P_CUR", java.sql.Types.REF_CURSOR ));
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_IDC_HATANG_CNTT.SP_GET_PHIEUTC_BY_THIETBI_ID",
                params
        );
    }

    public List<Map<String, Object>> getDonViIDC() throws AppSqlException {
        String sql = "select dv.DONVI_ID AS ID, dv.TEN_DV AS TEN\n" +
                "          from ADMIN.DONVI dv\n" +
                "          left join ADMIN.DONVI_LDV ldv\n" +
                "            on dv.DONVI_ID = ldv.DONVI_ID\n" +
                "         where ldv.LOAIDV_ID = 895\n" +
                "           and dv.PHANVUNG_ID = 68\n" +
                "           and dv.SUDUNG = 1";

        return dbContext.executeQueryToListMap(sql);
    }

}
