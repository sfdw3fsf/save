package vn.vnpt.vnptit.qlsc.dto.DanhMucToChucQLDA;

import vn.vnpt.message.ModelValidation;
import javax.validation.constraints.*;
import java.awt.geom.FlatteningPathIterator;

public class CapNhatDiaChiDto {
    private Integer kieu;
    private Integer diaChiId;
    private Integer tinhId;
    private Integer quanId;
    private Integer phuongId;
    private Integer phoId;
    private Integer apId;
    private Integer khuId;
    private String soNha;
    private String diaChi;
    private Integer dacDiemId;
    private Float viDo;
    private Float kinhDo;

    public Integer getKieu() { return this.kieu; }
    public Integer getDiaChiId() { return this.diaChiId; }
    public Integer getTinhId() { return this.tinhId; }
    public Integer getQuanId() { return this.quanId; }
    public Integer getPhuongId() { return this.phuongId; }
    public Integer getPhoId() { return this.phoId; }
    public Integer getApId() { return this.apId; }
    public Integer getKhuId() { return this.khuId; }
    public String getSoNha() { return this.soNha; }
    public String getDiaChi() { return this.diaChi; }
    public Integer getDacDiemId() { return this.dacDiemId; }
    public Float getViDo() { return this.viDo; }
    public Float getKinhDo() { return this.kinhDo; }
}
