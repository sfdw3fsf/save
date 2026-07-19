package vn.vnpt.vnptit.qlsc.dto;


public class GiaoPhieuXuLyNetV3Dto {

    private Integer type;
    private Long phieu_cha_id;
    private String nd_giao;
    private Integer donvi_nhan_id;
    private String ma_quytrinh;
    private String ma_buoc;
    private String json_input; // JSON string từ FE chứa mảng phương án đề xuất, ví dụ: [{"phieu_id":null,...}]
    private Object file_dinhkem;
    private Integer phanloai_padx_id;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getPhieu_cha_id() {
        return phieu_cha_id;
    }

    public void setPhieu_cha_id(Long phieu_cha_id) {
        this.phieu_cha_id = phieu_cha_id;
    }

    public String getNd_giao() {
        return nd_giao;
    }

    public void setNd_giao(String nd_giao) {
        this.nd_giao = nd_giao != null ? nd_giao.replaceAll("[\\x00-\\x1F]", "") : nd_giao;
    }

    public Integer getDonvi_nhan_id() {
        return donvi_nhan_id;
    }

    public void setDonvi_nhan_id(Integer donvi_nhan_id) {
        this.donvi_nhan_id = donvi_nhan_id;
    }

    public String getMa_quytrinh() {
        return ma_quytrinh;
    }

    public void setMa_quytrinh(String ma_quytrinh) {
        this.ma_quytrinh = ma_quytrinh;
    }

    public String getMa_buoc() {
        return ma_buoc;
    }

    public void setMa_buoc(String ma_buoc) {
        this.ma_buoc = ma_buoc;
    }

    public String getJson_input() {
        return json_input;
    }

    public void setJson_input(String json_input) {
        this.json_input = json_input;
    }

    public Object getFile_dinhkem() {
        return file_dinhkem;
    }

    public void setFile_dinhkem(Object file_dinhkem) {
        this.file_dinhkem = file_dinhkem;
    }

    public Integer getPhanloai_padx_id() {
        return phanloai_padx_id;
    }

    public void setPhanloai_padx_id(Integer phanloai_padx_id) {
        this.phanloai_padx_id = phanloai_padx_id;
    }
}