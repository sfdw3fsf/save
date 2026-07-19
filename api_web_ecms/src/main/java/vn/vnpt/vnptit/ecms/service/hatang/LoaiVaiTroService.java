package vn.vnpt.vnptit.ecms.service.hatang;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.DataKeyInput;
import vn.vnpt.vnptit.ecms.dto.hatang.DoiTuongDto;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoaiVaiTroService {
    private final DbContext dbContext;

    public LoaiVaiTroService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object getItems() throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.pkg_hatang_danhmuc.sp_loai_vai_tro_get_all", params);
    }

    public Object getItemsV2(Integer validity, Integer doi_tuong) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_validity", validity, validity !=null ? Types.NUMERIC : Types.NULL));
        params.add(new SqlParameter("p_role", doi_tuong, doi_tuong !=null ? Types.NUMERIC : Types.NULL));
        params.add(new SqlParameter("p_result", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_DANHMUC_THIETBI.SP_LOAI_VAI_TRO_GET", params);
    }

    public Object insert(MultipartFile file, String ten,
                         Number doiTuong, String ghiChu,
                         Integer hieuLuc) throws AppSqlException, IOException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ten", ten, Types.NVARCHAR));
        params.add(new SqlParameter("p_doi_tuong", doiTuong, Types.NUMERIC));
        params.add(new SqlParameter("p_anh_dai_dien", (file == null) ? null : file.getBytes(), Types.BLOB));
        params.add(new SqlParameter("p_ghi_chu", ghiChu, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", hieuLuc, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.pkg_hatang_danhmuc.sp_loai_vai_tro_insert", params);
        return result.getOutValue();
    }

    public Object update(MultipartFile file, Number id, String ten,
                         Number doiTuong, String ghiChu,
                         Integer hieuLuc) throws AppSqlException, IOException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", id, Types.NUMERIC));
        params.add(new SqlParameter("p_ten", ten, Types.NVARCHAR));
        params.add(new SqlParameter("p_doi_tuong", doiTuong, Types.NUMERIC));
        params.add(new SqlParameter("p_anh_dai_dien", (file == null) ? null : file.getBytes(), Types.BLOB));
        params.add(new SqlParameter("p_ghi_chu", ghiChu, Types.NVARCHAR));
        params.add(new SqlParameter("p_hieu_luc", hieuLuc, Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.pkg_hatang_danhmuc.sp_loai_vai_tro_update", params);
        return result.getOutValue();
    }

    public Object delete(String ids) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_ids", ids, Types.NVARCHAR));
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        params.add(result);
        dbContext.executeStoredProcedure("ECMS.pkg_hatang_danhmuc.sp_loai_vai_tro_delete", params);
        return result.getOutValue();
    }

    public Object getDoiTuongs() throws AppSqlException {
        List<DoiTuongDto> results = new ArrayList<>();
        results.add(new DoiTuongDto(1, "Máy ảo"));
        results.add(new DoiTuongDto(2, "Thiết bị mạng"));
        results.add(new DoiTuongDto(3, "Thiết bị máy chủ"));
        return results;
    }

    public Object getAnhDaiDien(DataKeyInput input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_id", input.getId(), Types.NUMERIC));
        SqlParameter result = new SqlParameter("p_result", Types.REF_CURSOR);
        params.add(result);
        dbContext.executeSpWithCursorToMap("ECMS.pkg_hatang_danhmuc.sp_get_anh_dai_dien_loai_vai_tro", params);
        return result.getOutValue();
    }
}
