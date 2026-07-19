package vn.vnpt.vnptit.qlsc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.*;
import vn.vnpt.vnptit.qlsc.dto.vattu.TimVatTuInput;
import vn.vnpt.vnptit.qlsc.dto.vattu.XacNhanVatTuSuCoInput;

import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CSKHService {
    private final DbContext dbContext;

    public CSKHService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object dsPhieuXuLy(Integer phanVungId, Integer nhanVienId, PhieuXuLyRequest input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("VPHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("VNGUONSC_ID", input.getNguonScId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("VMA_SC", input.getMaSc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("VNET_MA_QT", input.getMaQuytrinh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("VMA_BUOC", "", SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("VNGAYGIAO", input.getNgayGiao(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("VTRANGTHAI", input.getTrangThai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("VMUCDOSC_ID", input.getMucDoScId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("VKEODAI", input.getKeoDai(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("VHUONGGIAO_ID", input.getHuongGiaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("VNHANVIEN_ID", nhanVienId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.SP_LAY_DS_PHIEU_XULY_NET_V4", params);

    }

    public Object dsPhieuNghiemThuXuLy(Integer phanVungId, Integer nhanVienId, LayDSPhieuNghiemThuXLRequest input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_NHOMSC_ID", input.getNhomScId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_MA_SC", input.getMaSc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGUOIBAO_ID", input.getNguoiBaoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NGUONSC_ID", input.getNguonScId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TINHTRANG", input.getTinhTrang(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MA_QUYTRINH", input.getMaQuytrinh(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NOIDUNG_SC", input.getNoiDungSc(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGAYSC_TU", input.getNgayScTu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGAYSC_DEN", input.getNgayScDen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGAYBAO_TU", input.getNgayBaoTu(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_NGAYBAO_DEN", input.getNgayBaoDen(), SqlParameter.ParameterDirection.INPUT, Types.VARCHAR));
        params.add(new SqlParameter("P_MUCDOSC_ID", input.getMucDoScId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KEODAI_TU_OP", input.getKeoDaiTuOp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KEODAI_TU", input.getKeoDaiTu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KEODAI_DEN_OP", input.getKeoDaiDenOp(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_KEODAI_DEN", input.getKeoDaiDen(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DONVI_ID", input.getDonViId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", nhanVienId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_page_num", input.getPageNum(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_page_rec", input.getPageRec(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("p_total_row", 1, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
//        params.add(new SqlParameter("p_paginfo", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        params.add(new SqlParameter("p_rsout", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));


        return dbContext.executeSpWithCursorToListMap("QLSC.pkg_danhmuc.SP_LAY_DS_PHIEU_NGHIEMTHU_XL_PT_V2", params);
    }

    public Object dsThuTucXuLy(Integer phanVungId, Integer nhanVienId, Long phieuId, Long sucoId) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PHIEU_ID", phieuId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SUCO_ID", sucoId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", nhanVienId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.SP_LAY_DS_THUTUC_XULYPHIEU_V2", params);

    }

    public Object dsVattu(Integer phanVungId, Integer nhanVienId, TimVatTuInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", nhanVienId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_TEN_VATTU", input.getTenVattu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_PART_NO", input.getPartNumber(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HANG_SX_ID", input.getHangSXId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_HETHONG_ID", input.getHethongId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LINHVUC_ID", input.getLinhvucId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SOLUONG_DENGHI", input.getSoluongDenghi(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_LOAI_TRACUU", input.getLoaiTimkiem(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_VATTU.TRACUU_VATTU", params);

    }

    public Object xacnhanVattuSuco(Integer phanVungId, Integer nhanVienId, XacNhanVatTuSuCoInput input) throws AppSqlException {
        ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
        params.add(new SqlParameter("P_PHANVUNG_ID", phanVungId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NHANVIEN_ID", nhanVienId, SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_SUCO_ID", input.getSucoId(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_IS_NET", input.getIsNet(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_NOIDUNG", input.getNoidungNVKT(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_DS_VATTU", input.getDsVattu(), SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
        params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT, Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_VATTU.XACNHAN_VATTU_SUCO", params);

    }
}
