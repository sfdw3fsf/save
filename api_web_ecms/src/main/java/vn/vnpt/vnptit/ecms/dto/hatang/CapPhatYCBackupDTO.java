package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class CapPhatYCBackupDTO {
    private Number id;
    private Number capphat_yc_id;
    private Number sothutu;
    private Number thanhphan;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate ngaytao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate batdau;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate ketthuc;
    private Number tap_trung;
    private Number manual;
    private Number dungluong;
    private Number dungluong_tangtruong;
    private String vitri_backup;
    private Number tansuat_id;
    private String tansuat;
    private Number sl_backup;
    private Number external_backup;

    public Number getId() {
        return id;
    }

    public Number getCapphat_yc_id() {
        return capphat_yc_id;
    }

    public Number getSothutu() {
        return sothutu;
    }

    public Number getThanhphan() {
        return thanhphan;
    }

    public LocalDate getNgaytao() {
        return ngaytao;
    }

    public LocalDate getBatdau() {
        return batdau;
    }

    public LocalDate getKetthuc() {
        return ketthuc;
    }

    public Number getTap_trung() {
        return tap_trung;
    }

    public Number getManual() {
        return manual;
    }

    public Number getDungluong() {
        return dungluong;
    }

    public Number getDungluong_tangtruong() {
        return dungluong_tangtruong;
    }

    public String getVitri_backup() {
        return vitri_backup;
    }

    public Number getTansuat_id() {
        return tansuat_id;
    }

    public String getTansuat() {
        return tansuat;
    }

    public Number getSl_backup() {
        return sl_backup;
    }

    public Number getExternal_backup() {
        return external_backup;
    }
}
