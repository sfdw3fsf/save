package vn.vnpt.vnptit.ecms.dto.hatang;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CapPhatBackupDto {
    @NotNull(message = ModelValidation.NOT_NULL)
    private Long id;
    private String ghiChu;
    private String ngayHethan;
    private String ngayCapnhat;
    private long dvVanhanhId;
    private long nguoiCapnhatId;
    private long nguoiSudungId;
    private long fileId;
    private long khoBackupId;
    private long phongbanId;
    private long nguoiQuanlyId;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getGhiChu() {
        return ghiChu;
    }
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getNgayHethan() {
        return ngayHethan;
    }
    public void setNgayHethan(String ngayHethan) {
        this.ngayHethan = ngayHethan;
    }

    public long getDvVanhanhId() {
        return dvVanhanhId;
    }
    public void setDvVanhanhId(long dvVanhanhId) {
        this.dvVanhanhId = dvVanhanhId;
    }

    public long getNguoiCapnhatId() {
        return nguoiCapnhatId;
    }
    public void setNguoiCapnhatId(long nguoiCapnhatId) {
        this.nguoiCapnhatId = nguoiCapnhatId;
    }

    public long getNguoiSudungId() {
        return nguoiSudungId;
    }
    public void setNguoiSudungId(long nguoiSudungId) {
        this.nguoiSudungId = nguoiSudungId;
    }

    public long getFileId() {
        return fileId;
    }
    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getNgayCapnhat() { return ngayCapnhat; }
    public void setNgayCapnhat(String ngayCapnhat) { this.ngayCapnhat = ngayCapnhat; }

    public long getKhoBackupId() { return khoBackupId; }
    public void setKhoBackupId(long khoBackupId) { this.khoBackupId = khoBackupId; }

    public long getPhongbanId() { return phongbanId; }
    public void  setPhongbanId(long phongbanId) { this.phongbanId = phongbanId; }

    public long getNguoiQuanlyId() {
        return nguoiQuanlyId;
    }

    public void setNguoiQuanlyId(long nguoiQuanlyId) {
        this.nguoiQuanlyId = nguoiQuanlyId;
    }
}
