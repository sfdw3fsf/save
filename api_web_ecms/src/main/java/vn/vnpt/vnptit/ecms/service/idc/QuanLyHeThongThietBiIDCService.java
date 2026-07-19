package vn.vnpt.vnptit.ecms.service.idc;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.database.SqlParameter.ParameterDirection;
import vn.vnpt.vnptit.ecms.dto.idc.HeThongThietBiDto;
import vn.vnpt.vnptit.ecms.dto.idc.DeleteMultiDto;

@Service
public class QuanLyHeThongThietBiIDCService {  
    
    @Autowired
    private DbContext dbContext;

    public ArrayList<Map<String, Object>> getDanhSachHeThongThietBi() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", AppRequestContext.getCurrent().getPhanVungID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_id", AppRequestContext.getCurrent().getNhanVienID(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_GET_DS_HETHONG_THIETBI", params);
    }

    public Object upsertHeThongThietBi(HeThongThietBiDto dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        String p_nguoi_cn = AppRequestContext.getCurrent().getUserName();
        params.add(new SqlParameter("p_id", dto.getId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", dto.getTen(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_idc_id", dto.getIdcId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_ql_id", dto.getDonviQlId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_ql_id", dto.getNhanvienQlId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_vh_id", dto.getTrangthaiVhId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mota", dto.getMota(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ghichu", dto.getGhichu(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_ngay_ld", dto.getNgayLd(), ParameterDirection.INPUT, Types.DATE));
        params.add(new SqlParameter("p_sudung", dto.getSudung(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_trang_thai_tai_san_id", dto.getTrangThaiTaiSanId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mang_tbi_id", dto.getMangThietBiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", p_nguoi_cn, ParameterDirection.INPUT, Types.VARCHAR));
        
        SqlParameter result = new SqlParameter("p_result", null, ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);
        
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_UPSERT_HETHONG_THIETBI", params);
        return result.getOutValue();
    }

    public Object searchHeThongThietBi(HeThongThietBiDto searchDto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", searchDto.getId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", searchDto.getTen(), ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("p_idc_id", searchDto.getIdcId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_donvi_ql_id", searchDto.getDonviQlId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_nhanvien_ql_id", searchDto.getNhanvienQlId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai_vh_id", searchDto.getTrangthaiVhId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_mang_tbi_id", searchDto.getMangThietBiId(), ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_cursor", null, ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpIdxWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN.SP_SEARCH_HETHONG_THIETBI", params);
    }

    public Object deleteMultiHeThongThietBi(DeleteMultiDto dto) throws AppSqlException {
        String idsString = "";
        if (dto.getIds() != null && dto.getIds().length > 0) {
            // xóa ký tự [ ] và chuyển array thành string phân cách bằng dấu phẩy
            idsString = java.util.Arrays.toString(dto.getIds()).replaceAll("[\\[\\]]", "");
        }

        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", idsString, ParameterDirection.INPUT, Types.VARCHAR));
        
        SqlParameter result = new SqlParameter("p_result", null, ParameterDirection.OUTPUT, Types.NUMERIC);
        params.add(result);
        
        dbContext.executeStoredProcedure("ECMS.PKG_IDC_TAINGUYEN.SP_DELETE_HETHONG_THIETBI_MULTI", params);
        return result.getOutValue();
    }
}
