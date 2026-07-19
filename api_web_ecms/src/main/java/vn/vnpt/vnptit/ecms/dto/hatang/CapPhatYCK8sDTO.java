package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class CapPhatYCK8sDTO {
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
    private Number cpu;
    private Number ram;
    private Number storage;
    private Number pod;

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

    public Number getCpu() {
        return cpu;
    }

    public Number getRam() {
        return ram;
    }

    public Number getStorage() {
        return storage;
    }

    public Number getPod() {
        return pod;
    }
}
