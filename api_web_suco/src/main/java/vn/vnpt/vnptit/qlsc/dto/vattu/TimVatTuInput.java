package vn.vnpt.vnptit.qlsc.dto.vattu;

public class TimVatTuInput {
    private String tenVattu;
    private String partNumber;
    private Long hangSXId;
    private Long hethongId;
    private Long linhvucId;
    private Long soluongDenghi;
    private Integer loaiTimkiem; // 1: tìm theo TTHT, 2: tìm VTT, 3: tìm kho đài thuộc tỉnh, 4:  tìm trong net, 5: tìm ngoài net

    public String getTenVattu() {
        return tenVattu;
    }

    public void setTenVattu(String tenVattu) {
        this.tenVattu = tenVattu;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Long getHangSXId() {
        return hangSXId;
    }

    public void setHangSXId(Long hangSXId) {
        this.hangSXId = hangSXId;
    }

    public Long getHethongId() {
        return hethongId;
    }

    public void setHethongId(Long hethongId) {
        this.hethongId = hethongId;
    }

    public Long getLinhvucId() {
        return linhvucId;
    }

    public void setLinhvucId(Long linhvucId) {
        this.linhvucId = linhvucId;
    }

    public Long getSoluongDenghi() {
        return soluongDenghi;
    }

    public void setSoluongDenghi(Long soluongDenghi) {
        this.soluongDenghi = soluongDenghi;
    }

    public Integer getLoaiTimkiem() {
        return loaiTimkiem;
    }

    public void setLoaiTimkiem(Integer loaiTimkiem) {
        this.loaiTimkiem = loaiTimkiem;
    }
}
