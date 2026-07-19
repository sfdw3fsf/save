package vn.vnpt.vnptit.ecms.dto.hatang;


public class tamthonghoiDTO {

    private String tenphong;
    private Integer PAGE;
    private Integer PAGESIZE;
    private Long tamthId;
    private String ten;
    private String tenTat;
    private Long phongId;
    private Integer chieudai;
    private Integer chieurong;
    private Double toadoX;
    private Double toadoY;
    private String ghichu;
    private String mota;

    public tamthonghoiDTO() {
    }

    public tamthonghoiDTO(String tenphong, Integer PAGE, Integer PAGESIZE, Long tamthId, String ten, String tenTat, Long phongId, Integer chieudai, Integer chieurong, Double toadoX, Double toadoY, String ghichu, String mota) {
        this.tenphong = tenphong;
        this.PAGE = PAGE;
        this.PAGESIZE = PAGESIZE;
        this.tamthId = tamthId;
        this.ten = ten;
        this.tenTat = tenTat;
        this.phongId = phongId;
        this.chieudai = chieudai;
        this.chieurong = chieurong;
        this.toadoX = toadoX;
        this.toadoY = toadoY;
        this.ghichu = ghichu;
        this.mota = mota;
    }

    public String getTenphong() {
        return tenphong;
    }

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }

    public Integer getPAGE() {
        return PAGE;
    }

    public void setPAGE(Integer PAGE) {
        this.PAGE = PAGE;
    }

    public Integer getPAGESIZE() {
        return PAGESIZE;
    }

    public void setPAGESIZE(Integer PAGESIZE) {
        this.PAGESIZE = PAGESIZE;
    }

    public Long getTamthId() {
        return tamthId;
    }

    public void setTamthId(Long tamthId) {
        this.tamthId = tamthId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTenTat() {
        return tenTat;
    }

    public void setTenTat(String tenTat) {
        this.tenTat = tenTat;
    }

    public Long getPhongId() {
        return phongId;
    }

    public void setPhongId(Long phongId) {
        this.phongId = phongId;
    }

    public Integer getChieudai() {
        return chieudai;
    }

    public void setChieudai(Integer chieudai) {
        this.chieudai = chieudai;
    }

    public Integer getChieurong() {
        return chieurong;
    }

    public void setChieurong(Integer chieurong) {
        this.chieurong = chieurong;
    }

    public Double getToadoX() {
        return toadoX;
    }

    public void setToadoX(Double toadoX) {
        this.toadoX = toadoX;
    }

    public Double getToadoY() {
        return toadoY;
    }

    public void setToadoY(Double toadoY) {
        this.toadoY = toadoY;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

}
