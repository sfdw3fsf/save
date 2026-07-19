package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

import vn.vnpt.vnptit.ecms.dto.hatang.SearchIDCRackDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Map;

@Service
public class DichVuIDCService {
    private final DbContext dbContext;

    public DichVuIDCService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    /**
     * Lấy danh mục loại khai báo IDC từ procedure trong PKG_HATANG_DANHMUC_QDUNG
     */
    public ArrayList<Map<String, Object>> getLoaiKhaiBao(Long ccdvid) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ccdvid", ccdvid, Types.NUMERIC));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_LOAI_KHAIBAO", params);
    }

    /**
     * Lấy danh sách IDC
     */
    public ArrayList<Map<String, Object>> getDanhSachIDC() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_GET_DANHSACH_IDC", params);
    }

    /**
     * Lấy danh sách hãng sản xuất
     */
    public ArrayList<Map<String, Object>> getDanhSachHangSX() throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_GET_DANHSACH_HANGSX", params);
    }

    /**
     * Tra cứu IDC Rack
     */
    public ArrayList<Map<String, Object>> searchIDCRack(SearchIDCRackDto input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_slu", input.getSlu(), Types.NUMERIC));
        params.add(new SqlParameter("p_loaikb", input.getLoaikb(), Types.VARCHAR));
        params.add(new SqlParameter("p_idc", input.getIdc(), Types.NUMERIC));
        params.add(new SqlParameter("p_khonggian", input.getKhonggian(), Types.VARCHAR));
        params.add(new SqlParameter("p_rack", input.getRack(), Types.VARCHAR));
        params.add(new SqlParameter("p_hangsx", input.getHangsx(), Types.NUMERIC));
        params.add(new SqlParameter("p_serial", input.getSerial(), Types.VARCHAR));
        params.add(new SqlParameter("p_chitiet", input.getChitiet(), Types.VARCHAR));
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_QDUNG.SP_TRACUU_IDC_RACK", params);
    }
}
