package vn.vnpt.vnptit.ecms.dto.hatang;

import java.time.LocalDate;

public class ThuHoiMayAoDTO {
    private long id;
    private long phieu_id;
    private long sophieu;
    private String socu_thuhoi = "";
    private int trangthai_yeucau;
    private long spdv_id;
    private String spdv_name = "";
    private long cumdichvu_id;
    private int donvi_yeucau_id;
    private int nguoigui_id;
    private LocalDate ngaygui;
    private LocalDate ngayhoanthanh;
    private LocalDate ngaythuhoi;
    private String attachment;
    private String ghichu = "";
    private String ykien_xacnhan = "";
    private int ngattainguyen;
    private String ykien_thuchien_thuhoi;
    private int trangthai_thuhoi;

    public long getId() {
        return id;
    }

    public long getPhieu_id() {
        return phieu_id;
    }

    public long getSophieu() {
        return sophieu;
    }

    public String getSocu_thuhoi() {
        return socu_thuhoi;
    }

    public int getTrangthai_yeucau() {
        return trangthai_yeucau;
    }

    public long getSpdv_id() {
        return spdv_id;
    }

    public String getSpdv_name() {
        return spdv_name;
    }

    public long getCumdichvu_id() {
        return cumdichvu_id;
    }

    public int getDonvi_yeucau_id() {
        return donvi_yeucau_id;
    }

    public int getNguoigui_id() {
        return nguoigui_id;
    }

    public LocalDate getNgaygui() {
        return ngaygui;
    }

    public LocalDate getNgayhoanthanh() {
        return ngayhoanthanh;
    }

    public LocalDate getNgaythuhoi() {
        return ngaythuhoi;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getGhichu() {
        return ghichu;
    }

    public String getYkien_xacnhan() {
        return ykien_xacnhan;
    }

    public int getNgattainguyen() {
        return ngattainguyen;
    }

    public String getYkien_thuchien_thuhoi() {
        return ykien_thuchien_thuhoi;
    }

    public int getTrangthai_thuhoi() {
        return trangthai_thuhoi;
    }
}
