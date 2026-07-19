package vn.vnpt.vnptit.ecms.dto.hatang;


import java.io.Serializable;

public class YKienThuHoiBackupDTO implements Serializable {
    private String ykien;

    public YKienThuHoiBackupDTO(String ykien) {
        this.ykien = ykien;
    }

    public YKienThuHoiBackupDTO() {
    }

    public String getYkien() {
        return ykien;
    }

    public void setYkien(String ykien) {
        this.ykien = ykien;
    }
}
