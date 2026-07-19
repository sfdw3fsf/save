package vn.vnpt.vnptit.ecms.dto.hatang;

public class SearchIDCRackDto {
    private Long slu;
    private String loaikb;
    private Long idc;
    private String khonggian;
    private String rack;
    private Long hangsx;
    private String serial;
    private String chitiet;
    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getSlu() {
        return slu;
    }

    public void setSlu(Long slu) {
        this.slu = slu;
    }

    public String getLoaikb() {
        return loaikb;
    }

    public void setLoaikb(String loaikb) {
        this.loaikb = loaikb;
    }

    public Long getIdc() {
        return idc;
    }

    public void setIdc(Long idc) {
        this.idc = idc;
    }

    public String getKhonggian() {
        return khonggian;
    }

    public void setKhonggian(String khonggian) {
        this.khonggian = khonggian;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public Long getHangsx() {
        return hangsx;
    }

    public void setHangsx(Long hangsx) {
        this.hangsx = hangsx;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getChitiet() {
        return chitiet;
    }

    public void setChitiet(String chitiet) {
        this.chitiet = chitiet;
    }
}
