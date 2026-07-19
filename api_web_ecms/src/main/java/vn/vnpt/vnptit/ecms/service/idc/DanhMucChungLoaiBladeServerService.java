package vn.vnpt.vnptit.ecms.service.idc;

import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.idc.DanhMucBladeServerDto;
import vn.vnpt.vnptit.ecms.dto.idc.SearchDanhMucBladeServerDto;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class DanhMucChungLoaiBladeServerService {
    private final DbContext dbContext;


    public DanhMucChungLoaiBladeServerService(DbContext dbContext) {
        this.dbContext = dbContext;
    }


    /**
     * Lấy danh sách hoặc tìm kiếm chủng loại Blade Server.
     * Nếu các trường trong DTO = null hoặc rỗng => search tất cả.
     */
    public Object getAll(SearchDanhMucBladeServerDto dto) throws AppSqlException {

        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_ma_vt", dto.getMaVt(), Types.VARCHAR));
        params.add(new SqlParameter("p_model_thietbi", dto.getModelThietBi(), Types.VARCHAR));
        params.add(new SqlParameter("p_hangsx_id", dto.getHangSxId(), Types.NUMERIC));
        params.add(new SqlParameter("p_blade_sokhe", dto.getBladeSoKhe(), Types.NUMERIC));
        params.add(new SqlParameter("p_dp_loaicong", dto.getDpLoaiCong(), Types.VARCHAR));
        params.add(new SqlParameter("p_dp_ttmodule_id", dto.getDpTtModuleId(), Types.NUMERIC));
        params.add(new SqlParameter("p_dp_mucdichsd_id", dto.getDpMucDichSdId(), Types.NUMERIC));
        params.add(new SqlParameter("p_pp_loaind", dto.getPpLoaiNd(), Types.NUMERIC));
        params.add(new SqlParameter("p_pp_chuandn_id", dto.getPpChuanDnId(), Types.NUMERIC));
        params.add(new SqlParameter("p_cp_loaicong", dto.getCpLoaiCong(), Types.VARCHAR));

        // REF_CURSOR output
        params.add(new SqlParameter("p_result", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        // Gọi stored procedure
        return dbContext.executeSpWithCursorToListMap(
                "ECMS.PKG_TEST_CHITHANH.SP_SEARCH_CHUNGLOAI_BLADESERVER",
                params
        );
    }
    /**
     * Thêm mới hoặc cập nhật chủng loại Blade Server.
     * Nếu id = null hoặc 0 → INSERT, ngược lại → UPDATE.
     */
    public ApiResult insertOrUpdate(DanhMucBladeServerDto dto) throws AppSqlException {

        List<SqlParameter> params = new ArrayList<>();

        params.add(new SqlParameter("p_id", dto.getId(), Types.NUMERIC));
        params.add(new SqlParameter("p_ma_vt", dto.getMaVt(), Types.VARCHAR));
        params.add(new SqlParameter("p_model_thietbi", dto.getModelThietBi(), Types.VARCHAR));
        params.add(new SqlParameter("p_hangsx_id", dto.getHangSxId(), Types.NUMERIC));
        params.add(new SqlParameter("p_congsuat", dto.getCongSuat(), Types.NUMERIC));
        params.add(new SqlParameter("p_full_depth", dto.getFullDepth(), Types.NUMERIC));

        params.add(new SqlParameter("p_blade_sokhe", dto.getBladeSoKhe(), Types.NUMERIC));
        params.add(new SqlParameter("p_blade_model_gpu", dto.getBladeModelGpu(), Types.VARCHAR));
        params.add(new SqlParameter("p_blade_sl_gpu", dto.getBladeSoLuongGpu(), Types.NUMERIC));
        params.add(new SqlParameter("p_blade_soslot_fan", dto.getBladeSoSlotFan(), Types.NUMERIC));
        params.add(new SqlParameter("p_blade_soslot_pci", dto.getBladeSoSlotPci(), Types.NUMERIC));

        params.add(new SqlParameter("p_dp_socong", dto.getDpSoCong(), Types.NUMERIC));
        params.add(new SqlParameter("p_dp_tencong", dto.getDpTenCong(), Types.VARCHAR));
        params.add(new SqlParameter("p_dp_loaicong", dto.getDpLoaiCong(), Types.VARCHAR));
        params.add(new SqlParameter("p_dp_bangthong", dto.getDpBangThong(), Types.NUMERIC));
        params.add(new SqlParameter("p_dp_ttmodule_id", dto.getDpTtModuleId(), Types.NUMERIC));
        params.add(new SqlParameter("p_dp_mucdichsd_id", dto.getDpMucDichSdId(), Types.NUMERIC));

        params.add(new SqlParameter("p_pp_socong", dto.getPpSoCong(), Types.NUMERIC));
        params.add(new SqlParameter("p_pp_tencong", dto.getPpTenCong(), Types.VARCHAR));
        params.add(new SqlParameter("p_pp_dongdien", dto.getPpDongDien(), Types.NUMERIC));
        params.add(new SqlParameter("p_pp_dienap", dto.getPpDienAp(), Types.NUMERIC));
        params.add(new SqlParameter("p_pp_loaind", dto.getPpLoaiNd(), Types.NUMERIC));
        params.add(new SqlParameter("p_pp_chuandn_id", dto.getPpChuanDnId(), Types.NUMERIC));

        params.add(new SqlParameter("p_cp_mucdichsd_id", dto.getCpMucDichSdId(), Types.NUMERIC));
        params.add(new SqlParameter("p_cp_loaicong", dto.getCpLoaiCong(), Types.VARCHAR));
        params.add(new SqlParameter("p_cp_sl", dto.getCpSoLuong(), Types.NUMERIC));
        params.add(new SqlParameter("p_cp_tencong", dto.getCpTenCong(), Types.VARCHAR));

        params.add(new SqlParameter("p_soluong_u_ld", dto.getSoLuongULD(), Types.NUMERIC));
        params.add(new SqlParameter("p_mota", dto.getMoTa(), Types.VARCHAR));
        params.add(new SqlParameter("p_sudung", dto.getSuDung(), Types.NUMERIC));
        params.add(new SqlParameter("p_ghichu", dto.getGhiChu(), Types.VARCHAR));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));

        // Output parameters
        SqlParameter result = new SqlParameter("p_result", Types.NUMERIC);
        SqlParameter errCode = new SqlParameter("p_err_code", Types.VARCHAR);
        SqlParameter errMsg = new SqlParameter("p_err_msg", Types.VARCHAR);

        params.add(result);
        params.add(errCode);
        params.add(errMsg);

        dbContext.executeStoredProcedure("ECMS.PKG_TEST_CHITHANH.SP_CHUNGLOAI_BLADESERVER_UPSERT", params);

        ApiResult apiResult = new ApiResult();
        String errCodeVal = errCode.getOutValue() != null ? (String) errCode.getOutValue() : "BSS_00000000";
        String errMsgVal = errMsg.getOutValue() != null ? errMsg.getOutValue().toString() : "Unknown error";
        Number resultVal = result.getOutValue() != null ? (Number) result.getOutValue() : -1;
        apiResult.setErrorCode(errCodeVal);
        apiResult.setMessage(errMsgVal);
        apiResult.setData(resultVal);
        return apiResult;
    }
}