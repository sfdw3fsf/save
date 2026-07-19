package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class YeuCauCapPhatDTO {
    private Number id;
    private String sophieu;
    private Number donvi_id;
    private Number nguoi_yc_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate ngay_yc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate ngay_ht;
    private String mucdich_yc;
    private Number spdv_id;
    private String spdv_ten;
    private Number hatangmang_id;
    private Number file_yc;
    private String ghi_chu;
    private String trangthai_phieu;
    private CapPhatYCAoHoaDTO[] aohoa_list;
    private CapPhatYCK8sDTO[] container_list;
    private CapPhatYCIDGDTO[] idg_list;
    private CapPhatYCBackupDTO[] backup_list;

    public Number getId() {
        return id;
    }

    public String getSophieu() {
        return sophieu;
    }

    public Number getDonvi_id() {
        return donvi_id;
    }

    public Number getNguoi_yc_id() {
        return nguoi_yc_id;
    }

    public LocalDate getNgay_yc() {
        return ngay_yc;
    }

    public LocalDate getNgay_ht() {
        return ngay_ht;
    }

    public String getMucdich_yc() {
        return mucdich_yc;
    }

    public Number getSpdv_id() {
        return spdv_id;
    }

    public String getSpdv_ten() {
        return spdv_ten;
    }

    public Number getHatangmang_id() {
        return hatangmang_id;
    }

    public Number getFile_yc() {
        return file_yc;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public String getTrangthai_phieu() {
        return trangthai_phieu;
    }

    public CapPhatYCAoHoaDTO[] getAohoa_list() {
        return aohoa_list;
    }

    public CapPhatYCK8sDTO[] getContainer_list() {
        return container_list;
    }

    public CapPhatYCIDGDTO[] getIdg_list() {
        return idg_list;
    }

    public CapPhatYCBackupDTO[] getBackup_list() {
        return backup_list;
    }
}

