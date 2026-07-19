package vn.vnpt.vnptit.qlsc.service.cntt;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import vn.vnpt.common.Converter;
import vn.vnpt.common.converter.JsonConvert;
import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;
import vn.vnpt.vnptit.qlsc.dto.SendSMSDtoIn;
import vn.vnpt.vnptit.qlsc.dto.cntt.ChiaSeSuCoCNTTInput;
import vn.vnpt.vnptit.qlsc.dto.cntt.TiepNhanSuCoCnttInput;

@Service
public class TiepNhanSuCoCNTTService {
        private final DbContext dbContext;
        private final GiaoPhieuSuCoCNTTService giaoPhieuSuCoCNTTService;

        public TiepNhanSuCoCNTTService(DbContext dbContext, GiaoPhieuSuCoCNTTService giaoPhieuSuCoCNTTService) {
                this.dbContext = dbContext;
                this.giaoPhieuSuCoCNTTService = giaoPhieuSuCoCNTTService;
        }

        public Object dsSuCoCntt(Integer nguonScId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NGUONSC_ID", nguonScId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_NHANVIEN_ID", AppRequestContext.getCurrent().getNhanVienID(),
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("CUR_RETURNDS", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.SP_LAY_DS_SUCO", params);
        }

        public Object tiepNhanSuCoCntt(TiepNhanSuCoCnttInput item) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                                java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_NGUONSC_ID", item.getNguonSuCoID(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_NHOMSC_ID", item.getNhomSuCoID(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_NGAY_BD", Converter.toDateTime(item.getNgayBD()), java.sql.Types.DATE));
                params.add(new SqlParameter("P_GHICHU_HONG", item.getGhiChuHong(), java.sql.Types.NVARCHAR));
                params.add(new SqlParameter("P_NGAY_YC", Converter.toDateTime(item.getNgayYeuCau()),
                                java.sql.Types.DATE));

                params.add(new SqlParameter("P_DONVI_YC_ID", item.getDonViYeuCauID(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_NHANVIEN_YC_ID", item.getNhanVienYeuCauID(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_DIENTHOAI_LH", item.getDienThoaiLH(), java.sql.Types.NVARCHAR));
                params.add(new SqlParameter("P_DONVI_ID", item.getDonViID(), java.sql.Types.INTEGER));

                params.add(new SqlParameter("P_NHANVIEN_ID", item.getNhanVienID(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_HENSUATU", Converter.toDateTime(item.getHenSuaTu()),
                                java.sql.Types.DATE));
                params.add(new SqlParameter("P_HENSUADEN", Converter.toDateTime(item.getHenSuaDen()),
                                java.sql.Types.DATE));
                params.add(new SqlParameter("P_NGAY_CN", new Date(), java.sql.Types.DATE));
                params.add(
                                new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                                                java.sql.Types.NVARCHAR));
                params.add(
                                new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(),
                                                java.sql.Types.NVARCHAR));
                params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(),
                                java.sql.Types.NVARCHAR));
                params.add(new SqlParameter("P_TINHTRANG", item.getTinhTrang(), java.sql.Types.CLOB));
                params.add(new SqlParameter("P_ANHCHUP", item.getAnhChup(), java.sql.Types.CLOB));
                params.add(new SqlParameter("P_MUCDO_SC_ID", item.getMucDoSuCoId(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
                 ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.SP_TIEPNHAN_SUCO_CNTT", params);
                for (Map<String, Object> giaoviec : result) {
                    this.giaoPhieuSuCoCNTTService.sendSMS(
                            String.valueOf(giaoviec.get("SO_DT")),
                            String.valueOf(giaoviec.get("NOIDUNG"))
                    );
                }
//                result.forEach(giaoviec -> {
//                    SendSMSDtoIn sendDTO = new SendSMSDtoIn();
//                    sendDTO.setMsisdn(String.valueOf(giaoviec.get("SO_DT")));
//                    sendDTO.setMsisdn(String.valueOf(giaoviec.get("NOIDUNG")));
//                    sendDTO.setMt_type("reply");
//                    try {
//                        this.giaoPhieuSuCoCNTTService.jobGiaoPhieuSendSMSNhanVien(sendDTO);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
                return true;
        }

        public Object getNguoiPhuTrachByLoaiTbId(Integer loaitbId) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_LOAITB_ID", loaitbId,
                                SqlParameter.ParameterDirection.INPUT, Types.NUMERIC));
                params.add(new SqlParameter("P_RS", null, SqlParameter.ParameterDirection.OUTPUT,
                                Types.REF_CURSOR));
                return dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.SP_GET_NTP_BY_LOAITB_ID",
                                params);
        }

        public Object tiepNhanSuCoCnttVer2(TiepNhanSuCoCnttInput item) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<SqlParameter>();
                params.add(new SqlParameter("P_PHANVUNG_ID", AppRequestContext.getCurrent().getPhanVungID(),
                        java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_NGUONSC_ID", item.getNguonSuCoID(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_NHOMSC_ID", item.getNhomSuCoID(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_NGAY_BD", Converter.toDateTime(item.getNgayBD()), java.sql.Types.DATE));
                params.add(new SqlParameter("P_GHICHU_HONG", item.getGhiChuHong(), java.sql.Types.NVARCHAR));
                params.add(new SqlParameter("P_NGAY_YC", Converter.toDateTime(item.getNgayYeuCau()),
                        java.sql.Types.DATE));

                params.add(new SqlParameter("P_DONVI_YC_ID", item.getDonViYeuCauID(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_NHANVIEN_YC_ID", item.getNhanVienYeuCauID(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_DIENTHOAI_LH", item.getDienThoaiLH(), java.sql.Types.NVARCHAR));
                params.add(new SqlParameter("P_DONVI_ID", item.getDonViID(), java.sql.Types.INTEGER));

                params.add(new SqlParameter("P_NHANVIEN_ID", item.getNhanVienID(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_HENSUATU", Converter.toDateTime(item.getHenSuaTu()),
                        java.sql.Types.DATE));
                params.add(new SqlParameter("P_HENSUADEN", Converter.toDateTime(item.getHenSuaDen()),
                        java.sql.Types.DATE));
                params.add(new SqlParameter("P_NGAY_CN", new Date(), java.sql.Types.DATE));
                params.add(
                        new SqlParameter("P_NGUOI_CN", AppRequestContext.getCurrent().getUserName(),
                                java.sql.Types.NVARCHAR));
                params.add(
                        new SqlParameter("P_MAY_CN", AppRequestContext.getCurrent().getClientName(),
                                java.sql.Types.NVARCHAR));
                params.add(new SqlParameter("P_IP_CN", AppRequestContext.getCurrent().getClientIpAddress(),
                        java.sql.Types.NVARCHAR));
                params.add(new SqlParameter("P_TINHTRANG", item.getTinhTrang(), java.sql.Types.CLOB));
                params.add(new SqlParameter("P_ANHCHUP", item.getAnhChup(), java.sql.Types.CLOB));
                params.add(new SqlParameter("P_MUCDO_SC_ID", item.getMucDoSuCoId(), java.sql.Types.INTEGER));
                params.add(new SqlParameter("P_CURSOR", java.sql.Types.REF_CURSOR));
                params.add(new SqlParameter("P_QUYMO_SUCO", item.getQuyMoSuCoId(), java.sql.Types.INTEGER));
                ArrayList<Map<String, Object>> result = dbContext.executeSpWithCursorToListMap("QLSC.PKG_SUCO_CNTT.SP_TIEPNHAN_SUCO_CNTT_V2", params);
                try {
                        for (Map<String, Object> giaoviec : result) {
                                this.giaoPhieuSuCoCNTTService.sendSMS(
                                        String.valueOf(giaoviec.get("SO_DT")),
                                        String.valueOf(giaoviec.get("NOIDUNG"))
                                );
                        }
                } catch (Exception ignored) {}

                return true;
        }

        public Object chiaSe(ChiaSeSuCoCNTTInput input) throws AppSqlException, IOException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_phanvung_id", input.getPhanVungId(), java.sql.Types.NUMERIC));
                params.add(new SqlParameter("p_nguoidung_id", AppRequestContext.getCurrent().getNguoiDungID(), java.sql.Types.NUMERIC));
                params.add(new SqlParameter("p_suco_id", input.getSuCoId(),  java.sql.Types.NUMERIC));
                params.add(new SqlParameter("p_ds_chiase", JsonConvert.serialize(input.getDsChiaSe()),  java.sql.Types.VARCHAR));
                params.add(new SqlParameter("p_noidung_giao", input.getNoiDungGiao(),  java.sql.Types.VARCHAR));
                dbContext.executeStoredProcedure("QLSC.PKG_SUCO_CNTT.SP_CHIASE_SUCO_CNTT", params);
                if (input.getSms() == null || input.getSms().equals(0)) return true;
                CompletableFuture.runAsync(() -> input.getDsChiaSe().forEach(item -> {
                        SendSMSDtoIn sendDTO = new SendSMSDtoIn();
                        sendDTO.setMsisdn(item.getSoDT());
                        sendDTO.setContent(input.getNoiDungGiao());
                        sendDTO.setMt_type("reply");
                        try {
                                this.giaoPhieuSuCoCNTTService.giaoPhieuSendSMSNhanVien(sendDTO);
                        } catch (Exception ignored) { }
                }));
                return true;
        }

        public void tiepNhanGiaoViec(long pPhanVungId, long pSuCoId,
                                     int pDonViTHienId, int pNhanVienTHienId,
                                     String pNoiDung, String pSDT,
                                     int pNguoiDungId, int isSMS) throws AppSqlException {
                ArrayList<SqlParameter> params = new ArrayList<>();
                params.add(new SqlParameter("p_phanvung_id", pPhanVungId, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                params.add(new SqlParameter("p_suco_id", pSuCoId, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                params.add(new SqlParameter("p_donvi_th_id", pDonViTHienId, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                params.add(new SqlParameter("p_nhanvien_th_id", pNhanVienTHienId, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                params.add(new SqlParameter("p_noidung", pNoiDung, SqlParameter.ParameterDirection.INPUT, Types.NVARCHAR));
                params.add(new SqlParameter("p_nguoidung_id", pNguoiDungId, SqlParameter.ParameterDirection.INPUT, java.sql.Types.NUMERIC));
                dbContext.executeStoredProcedure("QLSC.PKG_SUCO_CNTT.sp_tiepnhan_giaoviec_nhanvien", params);

                if (isSMS == 0) return;

                SendSMSDtoIn sendDTO = new SendSMSDtoIn();
                sendDTO.setMsisdn(pSDT);
                sendDTO.setContent(pNoiDung);
                sendDTO.setMt_type("reply");
                try {
                        this.giaoPhieuSuCoCNTTService.giaoPhieuSendSMSNhanVien(sendDTO);
                } catch (Exception ignored) {}

        }
}
