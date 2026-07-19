package vn.vnpt.vnptit.qlsc.service.mnv;

import org.springframework.stereotype.Service;
import vn.vnpt.common.Utils;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.storage.MinioStorage;
import vn.vnpt.vnptit.qlsc.dto.mnv.FilterYeuCauHoTroInput;
import vn.vnpt.vnptit.qlsc.dto.mnv.PhanHoiYeuCauDTO;

import java.io.IOException;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class YeuCauHoTroTroService {
    private final DbContext dbContext;
    private final MinioStorage minioStorage;

    private static final String PKG_NAME = "qlsc.pkg_xuly_suco_mnv";

    public YeuCauHoTroTroService(DbContext dbContext, MinioStorage minioStorage) {
        this.dbContext = dbContext;
        this.minioStorage = minioStorage;
    }

    public Object yeuCauHoTroDanhSach(FilterYeuCauHoTroInput filter, int phanVungId, long nguoiDungId)
            throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoidung_id", nguoiDungId, Types.NUMERIC));
        params.add(new SqlParameter("p_trangthai", filter.getTrangThai(), Types.NUMERIC));
        params.add(new SqlParameter("p_ma_sc", filter.getMaSC(), Types.NVARCHAR));
        params.add(new SqlParameter("p_noidung", filter.getNoiDungSC(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ngaytn_tu", filter.getNgayTNTu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ngaytn_den", filter.getNgayTNDen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ngayyc_tu", filter.getNgayYCTu(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ngayyc_den", filter.getNgayYCDen(), Types.NVARCHAR));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap(PKG_NAME + ".sp_sucomnv_ychotro_danhsach", params);
    }

    @SuppressWarnings("unchecked")
    public Object yeuCauHoTroChiTiet(int phanVungId, long suCoId, long xuLyId, long nguoiDungId)
            throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", suCoId, Types.NUMERIC));
        params.add(new SqlParameter("p_xuly_id", xuLyId, Types.NUMERIC));
        params.add(new SqlParameter("p_nguoidung_id", nguoiDungId, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        List<Map<String, Object>> resultList = dbContext
                .executeSpWithCursorToListMap(PKG_NAME + ".sp_sucomnv_ychotro_chitiet", params);
        if (!resultList.isEmpty()) {
            Map<String, Object> map = resultList.get(0);
            ArrayList<SqlParameter> paramsTinhTrang = new ArrayList<>();
            paramsTinhTrang.add(new SqlParameter("p_phanvung_id", phanVungId, Types.NUMERIC));
            paramsTinhTrang.add(new SqlParameter("p_suco_id", suCoId, Types.NUMERIC));
            paramsTinhTrang.add(new SqlParameter("p_xuly_id", xuLyId, Types.NUMERIC));
            paramsTinhTrang.add(new SqlParameter("p_rs", Types.REF_CURSOR));
            List<Map<String, Object>> dsTinhTrang = dbContext
                    .executeSpWithCursorToListMap(PKG_NAME + ".sp_sucomnv_ychotro_ds_trinhtrang", paramsTinhTrang);

            map.put("DS_THIETBI_HOTRO", dsTinhTrang);
            if (!Utils.isEmpty(resultList.get(0).get("DS_FILE_DINHKEM"))) {
                String dsFileDinhKem = map.get("DS_FILE_DINHKEM").toString();
                try {
                    List<Map<String, String>> fileList = JsonConvert.deserialize(dsFileDinhKem, List.class);
                    for (Map<String, String> file : fileList) {
                        String preURL = this.minioStorage.getPresignedUrl(String.valueOf(file.get("PATH")));
                        file.put("URL", preURL);
                    }
                    map.put("DS_FILE_DINHKEM", fileList);
                } catch (Exception ignored) {
                }
            }
            resultList = Collections.singletonList(map);
        }
        return resultList;
    }

    public Object layBuocXuLyYCHTMNV(Long suCoId, int phanVungId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("P_SUCO_ID", suCoId, Types.NUMERIC));
        params.add(new SqlParameter("P_THUTU", 4, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, Types.NUMERIC));
        params.add(new SqlParameter("CUR_RETURNDS", java.sql.Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_DANHMUC.SP_LAY_BUOC_XULY_YCHOTRO_MNV", params);
    }

    public Object yeuCauHoTroKetQuaDoKiem(int phanVungId, long xuLyId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", phanVungId, Types.NUMERIC));
        params.add(new SqlParameter("p_xuly_id", xuLyId, Types.NUMERIC));
        params.add(new SqlParameter("p_rs", Types.REF_CURSOR));
        List<Map<String, Object>> result = dbContext
                .executeSpWithCursorToListMap(PKG_NAME + ".sp_sucomnv_ychotro_ketqua_dokiem", params);
        if (!result.isEmpty()) {
            Map<String, List<Map<String, Object>>> grouped = result.stream()
                    .collect(Collectors
                            .groupingBy(m -> (String) Optional.ofNullable(m.get("LOAI_KIEM_TRA")).orElse("UNKNOWN")));

            return grouped;
        }
        return Collections.emptyMap();
    }

    public Object yeuCauHoTroPhanHoi(PhanHoiYeuCauDTO dto) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("p_phanvung_id", dto.getPhanVungId(), Types.NUMERIC));
        params.add(new SqlParameter("p_suco_id", dto.getSuCoId(), Types.NUMERIC));
        params.add(new SqlParameter("p_xuly_id", dto.getXuLyId(), Types.NUMERIC));
        params.add(new SqlParameter("p_noidung", dto.getNoiDung(), Types.NVARCHAR));
        params.add(new SqlParameter("p_is_tiepnhan", dto.getIsTiepNhan(), Types.NUMERIC));
        params.add(new SqlParameter("p_is_dokiem", dto.getIsDoKiem(), Types.NUMERIC));
        try {
            params.add(new SqlParameter("p_ds_file",
                    dto.getDsFileDinhKem() == null ? null : JsonConvert.serialize(dto.getDsFileDinhKem()), Types.CLOB));
            params.add(new SqlParameter("p_ds_dokiem",
                    dto.getDsThongTinDoKiem() == null ? null : JsonConvert.serialize(dto.getDsThongTinDoKiem()),
                    Types.CLOB));
        } catch (IOException e) {
            throw new AppSqlException(e.getMessage());
        }
        params.add(new SqlParameter("p_nguoidung_id", AppRequestContext.getCurrent().getNguoiDungID(), Types.NUMERIC));
        params.add(new SqlParameter("p_nguoi_cn", AppRequestContext.getCurrent().getUserName(), Types.NVARCHAR));
        params.add(new SqlParameter("p_may_cn", AppRequestContext.getCurrent().getClientName(), Types.NVARCHAR));
        params.add(new SqlParameter("p_ip", AppRequestContext.getCurrent().getClientIpAddress(), Types.NVARCHAR));
        dbContext.executeStoredProcedure(PKG_NAME + ".sp_sucomnv_ychotro_phanhoi", params);
        return true;
    }

}
