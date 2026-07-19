package vn.vnpt.vnptit.ecms.dto.hatang;

public class DanhMucQuatDTO {
    private int p_id;
    private int p_phongid;
    private String p_tenquat;
    private String p_tentat;
    private int p_chieudai;
    private int p_chieurong;
    private int p_toadox;
    private int p_todoy;
    private String p_ghichu;
    private String p_mota;

    public DanhMucQuatDTO(int p_id, int p_phongid, String p_tenquat, String p_tentat, int p_chieudai, int p_chieurong, int p_toadox, int p_todoy, String p_ghichu, String p_mota) {
        this.p_id = p_id;
        this.p_phongid = p_phongid;
        this.p_tenquat = p_tenquat;
        this.p_tentat = p_tentat;
        this.p_chieudai = p_chieudai;
        this.p_chieurong = p_chieurong;
        this.p_toadox = p_toadox;
        this.p_todoy = p_todoy;
        this.p_ghichu = p_ghichu;
        this.p_mota = p_mota;
    }

    public DanhMucQuatDTO() {
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getP_phongid() {
        return p_phongid;
    }

    public void setP_phongid(int p_phongid) {
        this.p_phongid = p_phongid;
    }

    public String getP_tenquat() {
        return p_tenquat;
    }

    public void setP_tenquat(String p_tenquat) {
        this.p_tenquat = p_tenquat;
    }

    public String getP_tentat() {
        return p_tentat;
    }

    public void setP_tentat(String p_tentat) {
        this.p_tentat = p_tentat;
    }

    public int getP_chieudai() {
        return p_chieudai;
    }

    public void setP_chieudai(int p_chieudai) {
        this.p_chieudai = p_chieudai;
    }

    public int getP_chieurong() {
        return p_chieurong;
    }

    public void setP_chieurong(int p_chieurong) {
        this.p_chieurong = p_chieurong;
    }

    public int getP_toadox() {
        return p_toadox;
    }

    public void setP_toadox(int p_toadox) {
        this.p_toadox = p_toadox;
    }

    public int getP_todoy() {
        return p_todoy;
    }

    public void setP_todoy(int p_todoy) {
        this.p_todoy = p_todoy;
    }

    public String getP_ghichu() {
        return p_ghichu;
    }

    public void setP_ghichu(String p_ghichu) {
        this.p_ghichu = p_ghichu;
    }

    public String getP_mota() {
        return p_mota;
    }

    public void setP_mota(String p_mota) {
        this.p_mota = p_mota;
    }
}

