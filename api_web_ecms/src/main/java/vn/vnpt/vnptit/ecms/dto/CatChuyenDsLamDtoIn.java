package vn.vnpt.vnptit.ecms.dto;

public class CatChuyenDsLamDtoIn {
    private Long dslam_id;
    private Long bras_id;
    private Long dslam_cha_id;
    private Long bras_moi_id;
    private Long dslam_moi_id;
    private Long slotbras_id;
    private Long portbras_id;
    private Long nguoidung_id;

    public int getBras_ts() {
        return bras_ts;
    }

    public void setBras_ts(int bras_ts) {
        this.bras_ts = bras_ts;
    }

    private int bras_ts;

    public Long getDslam_id() {
        return dslam_id;
    }

    public void setDslam_id(Long dslam_id) {
        this.dslam_id = dslam_id;
    }

    public Long getBras_id() {
        return bras_id;
    }

    public void setBras_id(Long bras_id) {
        this.bras_id = bras_id;
    }

    public Long getDslam_cha_id() {
        return dslam_cha_id;
    }

    public void setDslam_cha_id(Long dslam_cha_id) {
        this.dslam_cha_id = dslam_cha_id;
    }

    public Long getBras_moi_id() {
        return bras_moi_id;
    }

    public void setBras_moi_id(Long bras_moi_id) {
        this.bras_moi_id = bras_moi_id;
    }

    public Long getDslam_moi_id() {
        return dslam_moi_id;
    }

    public void setDslam_moi_id(Long dslam_moi_id) {
        this.dslam_moi_id = dslam_moi_id;
    }

    public Long getSlotbras_id() {
        return slotbras_id;
    }

    public void setSlotbras_id(Long slotbras_id) {
        this.slotbras_id = slotbras_id;
    }

    public Long getPortbras_id() {
        return portbras_id;
    }

    public void setPortbras_id(Long portbras_id) {
        this.portbras_id = portbras_id;
    }

    public Long getNguoidung_id() {
        return nguoidung_id;
    }

    public void setNguoidung_id(Long nguoidung_id) {
        this.nguoidung_id = nguoidung_id;
    }
}
