package vn.vnpt.vnptit.ecms.dto.hatang;

public class DoiTuongDto {
    private Number id;
    private String ten;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public DoiTuongDto(Number id, String ten) {
        this.id = id;
        this.ten = ten;
    }
}
