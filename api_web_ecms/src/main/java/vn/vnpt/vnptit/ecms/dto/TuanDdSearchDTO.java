package vn.vnpt.vnptit.ecms.dto;

import java.util.Date;

public class TuanDdSearchDTO {
        private String hoten;
        private String email;
        private String sodienthoai;
        private String sothich;
        private int chieucaofrom;
        private int chieucaoto;
        private Date ngaysinhfrom;
        private Date ngaysinhto;
        private int xaphuongid;
        private  int quanhuyenid;
        private  int tinhid;

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getSothich() {
        return sothich;
    }

    public void setSothich(String sothich) {
        this.sothich = sothich;
    }

    public int getChieucaofrom() {
        return chieucaofrom;
    }

    public void setChieucaofrom(int chieucaofrom) {
        this.chieucaofrom = chieucaofrom;
    }

    public int getChieucaoto() {
        return chieucaoto;
    }

    public void setChieucaoto(int chieucaoto) {
        this.chieucaoto = chieucaoto;
    }

    public Date getNgaysinhfrom() {
        return ngaysinhfrom;
    }

    public void setNgaysinhfrom(Date ngaysinhfrom) {
        this.ngaysinhfrom = ngaysinhfrom;
    }

    public Date getNgaysinhto() {
        return ngaysinhto;
    }

    public void setNgaysinhto(Date ngaysinhto) {
        this.ngaysinhto = ngaysinhto;
    }

    public int getXaphuongid() {
        return xaphuongid;
    }

    public void setXaphuongid(int xaphuongid) {
        this.xaphuongid = xaphuongid;
    }

    public int getQuanhuyenid() {
        return quanhuyenid;
    }

    public void setQuanhuyenid(int quanhuyenid) {
        this.quanhuyenid = quanhuyenid;
    }

    public int getTinhid() {
        return tinhid;
    }

    public void setTinhid(int tinhid) {
        this.tinhid = tinhid;
    }
}
