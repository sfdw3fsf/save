package vn.vnpt.vnptit.ecms.dto.hatang;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class CapPhatYCAoHoaDTO {
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
    private Number vcpu;
    private Number vram;
    private Number vgpu;
    private Number ssd;
    private Number hdd;
    private Number iops;
    private Number throughput;
    private Number network_card;

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

    public Number getVcpu() {
        return vcpu;
    }

    public Number getVram() {
        return vram;
    }

    public Number getVgpu() {
        return vgpu;
    }

    public Number getSsd() {
        return ssd;
    }

    public Number getHdd() {
        return hdd;
    }

    public Number getIops() {
        return iops;
    }

    public Number getThroughput() {
        return throughput;
    }

    public Number getNetwork_card() {
        return network_card;
    }
}
