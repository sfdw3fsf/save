package vn.vnpt.vnptit.ecms.service.hatang;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.ConnectionManager;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.message.ApiResult;
import vn.vnpt.vnptit.ecms.dto.hatang.CumAohoaUpdateDtoIn;

@Service
public class CumAohoaVMWCloudService {
    private final DbContext dbContext;

    public CumAohoaVMWCloudService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public ResponseEntity<ApiResult> capNhatCumAoHoa(CumAohoaUpdateDtoIn input) {
        ApiResult rs = new ApiResult();
        String msg = "";
        
        if (input.getMaCumht() == null || input.getMaCumht().isEmpty()) {
            msg += ", Mã cụm chưa nhập";
        }
        if (input.getTenCumht() == null || input.getTenCumht().isEmpty()) {
            msg += ", Tên cụm chưa nhập";
        }

        if (!msg.isEmpty()) {
            rs.setErrorCode("BSS-00009490");
            rs.setMessage(msg.substring(2));
            return rs.getResponseEntity();
        }

        try {
            ArrayList<SqlParameter> params = new ArrayList<>();
            params.add(new SqlParameter("p_loaicum", input.getLoaicum(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_cumht_id", input.getCumhtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_cumht_aohoa_id", input.getCumhtAohoaId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ma_cumht", input.getMaCumht(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ten_cumht", input.getTenCumht(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_ten_hienthi", input.getTenHienthi(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_phanloaiht_id", input.getPhanloaihtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_loaiht_id", input.getLoaihtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_avatar", input.getAvatar(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_sudung", input.getSudung(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_hinhthucdt_id", input.getHinhthucdtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_idc_id", input.getIdcId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_datacenter", input.getDatacenter(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_nhomht_id", input.getNhomhtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_htm_id", input.getHtmId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_capdoht_id", input.getCapdohtId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_congnghe_id", input.getCongngheId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_link_quantri", input.getLinkQuantri(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_trangthaiccdv_id", input.getTrangthaiccdvId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_ghichu", input.getGhichu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
            params.add(new SqlParameter("p_donvi_ql_id", input.getDonviQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_phong_ql_id", input.getPhongQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nhanvien_ql_id", input.getNhanvienQlId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
            params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));

            SqlParameter result = new SqlParameter("p_result", 0, SqlParameter.ParameterDirection.OUTPUT, Types.NUMERIC);
            params.add(result);

            dbContext.setConnection(ConnectionManager.Connections.CSS);
            dbContext.executeStoredProcedure("ecms.pkg_hatang_cumaohoa_cntt.SP_cumaohoa_UPDATE", params);

            int kq = Integer.parseInt(result.getOutValue().toString());
            rs.setData(kq);
            
            if (kq > 1) {
                rs.setMessage("Cập nhật thành công!");
                rs.setErrorCode("BSS-00000000");
            } else if (kq == -3) {
                rs.setErrorCode("BSS-00009490");
                rs.setMessage("Mã và tên cụm hạ tầng đã tồn tại!");
            } else if (kq == -2) {
                rs.setErrorCode("BSS-00009490");
                rs.setMessage(String.format("Mã cụm hạ tầng [%s] đã tồn tại!", input.getMaCumht()));
            } else if (kq == -1) {
                rs.setErrorCode("BSS-00009490");
                rs.setMessage(String.format("Tên cụm hạ tầng [%s] đã tồn tại!", input.getTenCumht()));
            } else {
                rs.setMessage("Cập nhật không thành công");
                rs.setErrorCode("BSS-00009490");
            }
        } catch (AppSqlException e) {
            rs.setException(e);
        }
        return rs.getResponseEntity();
    }

    public Object getDetailCluster(Long cumhtId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cumht_id", cumhtId, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        
        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ecms.pkg_hatang_cumaohoa_cntt.SP_LAY_CHITIET_CUMHT_BY_ID", params);
    }

    /**
     * Lấy danh sách Loại tài nguyên cung cấp theo cumht_id (dùng cho SmartCloud)
     * Endpoint: GET /hatang/cumaohoavmw/loai-tai-nguyen/{cumId}
     */
    public Object getLoaiTaiNguyenByCumId(Long cumhtId) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_cumht_id", cumhtId, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ecms.pkg_hatang_cumaohoa_cntt.SP_LAY_LOAI_TN_BY_CUMHTID", params);
    }

    /**
     * Lưu danh sách Loại tài nguyên cung cấp cho Cụm hạ tầng SmartCloud
     * Được gọi từ frontend sau khi tạo/cập nhật cụm chính
     * 
     * @param cumhtId: ID của cụm hạ tầng
     * @param loaiTaiNguyenIds: Chuỗi các ID tài nguyên cách nhau bởi dấu phẩy, VD: "1,2,5"
     * @return Cursor chứa STATUS (1: thành công, 0: không tìm thấy cụm, -1: lỗi) và MSG
     */
    public Object saveTaiNguyenForCluster(Long cumhtId, String loaiTaiNguyenIds) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_CUMHT_ID", cumhtId, Types.NUMERIC));
        params.add(new SqlParameter("P_DICH_VU_IDS", loaiTaiNguyenIds, Types.VARCHAR));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_HATANG_CUMAOHOA_CNTT.SP_SAVE_DICHVU_CUMHT_BY_MACUM", params);
    }

    public Object insertNangLucCumHT(CumAohoaUpdateDtoIn input) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_CUMHT_ID", input.getCumhtId(), Types.NUMERIC));
        params.add(new SqlParameter("P_VCPU_THUCTE", input.getvcpuThucte(), Types.NUMERIC));
        params.add(new SqlParameter("P_VCPU_KHANANG", input.getvcpuKhanang(), Types.NUMERIC));
        params.add(new SqlParameter("P_RAM_THUCTE", input.getramThucte(), Types.NUMERIC));
        params.add(new SqlParameter("P_RAM_KHANANG", input.getramKhanang(), Types.NUMERIC));
        params.add(new SqlParameter("P_GPU_THUCTE", input.getgpuThucte(), Types.NUMERIC));
        params.add(new SqlParameter("P_GPU_KHANANG", input.getgpuKhanang(), Types.NUMERIC));
        params.add(new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(), Types.VARCHAR));
        params.add(new SqlParameter("P_RESULT", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));

        dbContext.setConnection(ConnectionManager.Connections.CSS);
        return dbContext.executeSpWithCursorToListMap("ECMS.PKG_IDC_TAINGUYEN_AOHOA.SP_INSERT_NANGLUC_CUMHT", params);
    }
}
