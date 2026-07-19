package vn.vnpt.vnptit.ecms.dto.idc;

/**
 * DTO cho import từng dòng Rack từ file Excel.
 * Các trường có tiền tố "ten" sẽ được SP mapping sang ID tương ứng.
 */
public class RackImportRowDto {
    private Integer stt;                  // Số thứ tự dòng trong file Excel
    private String tenRack;               // Tên Rack (bắt buộc)
    private String tenTat;
    private String tenLoaiRack;           // Tên Loại Rack → mapping sang LOAIRACK_ID
    private String maTaiSan;              // Mã tài sản
    private String tenVanHanh;            // Tên trạng thái vận hành → mapping sang VANHANH ID
    private String tenQuyHoach;           // Tên trạng thái quy hoạch → mapping sang QUYHOACH ID
    private String tenCcdv;               // Tên trạng thái CCDV → mapping sang CCDV ID
    private String tenChuQuan;            // Tên đơn vị sở hữu → mapping sang CHUQUAN_ID
    private String tenHangSX;             // Tên hãng SX → mapping sang HANGSX_ID
    private String tenDonViQl;
    private String tenPhongQl;
    private String taiSanId;
    private Number camera;                // 1: Có, 0: Không
    private Number huong;
    private String ngaySD;                // Ngày sử dụng (dd/mm/yyyy)
    private Number suDung;                // Hiệu lực: 1 hoặc 0
    private String ghiChu;                // Ghi chú
    private String maDuAn;                // Mã dự án
    private String tenDuAn;               // Tên dự án
    private String theTs;                 // Thẻ tài sản

    // Default constructor
    public RackImportRowDto() {
    }

    // Getters and Setters
    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public String getTenRack() {
        return tenRack;
    }

    public void setTenRack(String tenRack) {
        this.tenRack = tenRack;
    }

    public String getTenTat() {return tenTat;}

    public void setTenTat(String tenTat) {this.tenTat = tenTat;}

    public String getTenLoaiRack() {
        return tenLoaiRack;
    }

    public void setTenLoaiRack(String tenLoaiRack) {
        this.tenLoaiRack = tenLoaiRack;
    }

    public String getMaTaiSan() {
        return maTaiSan;
    }

    public void setMaTaiSan(String maTaiSan) {
        this.maTaiSan = maTaiSan;
    }

    public String getTenVanHanh() {
        return tenVanHanh;
    }

    public void setTenVanHanh(String tenVanHanh) {
        this.tenVanHanh = tenVanHanh;
    }

    public String getTenQuyHoach() {
        return tenQuyHoach;
    }

    public void setTenQuyHoach(String tenQuyHoach) {
        this.tenQuyHoach = tenQuyHoach;
    }

    public String getTenCcdv() {
        return tenCcdv;
    }

    public void setTenCcdv(String tenCcdv) {
        this.tenCcdv = tenCcdv;
    }

    public String getTenChuQuan() {
        return tenChuQuan;
    }

    public void setTenChuQuan(String tenChuQuan) {
        this.tenChuQuan = tenChuQuan;
    }

    public String getTenHangSX() {
        return tenHangSX;
    }

    public void setTenHangSX(String tenHangSX) {
        this.tenHangSX = tenHangSX;
    }

    public String getTaiSanId() {
        return taiSanId;
    }

    public void setTaiSanId(String taiSanId) {
        this.taiSanId = taiSanId;
    }

    public String getTenDonViQl() {
        return tenDonViQl;
    }

    public void setTenDonViQl(String tenDonViQl) {
        this.tenDonViQl = tenDonViQl;
    }

    public String getTenPhongQl() {
        return tenPhongQl;
    }

    public void setTenPhongQl(String tenPhongQl) {
        this.tenPhongQl = tenPhongQl;
    }

    public Number getCamera() {
        return camera;
    }

    public void setCamera(Number camera) {
        this.camera = camera;
    }

    public Number getHuong() {return huong;}

    public void setHuong(Number huong) {this.huong = huong;}

    public String getNgaySD() {
        return ngaySD;
    }

    public void setNgaySD(String ngaySD) {
        this.ngaySD = ngaySD;
    }

    public Number getSuDung() {
        return suDung;
    }

    public void setSuDung(Number suDung) {
        this.suDung = suDung;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getMaDuAn() {
        return maDuAn;
    }

    public void setMaDuAn(String maDuAn) {
        this.maDuAn = maDuAn;
    }

    public String getTenDuAn() {
        return tenDuAn;
    }

    public void setTenDuAn(String tenDuAn) {
        this.tenDuAn = tenDuAn;
    }

    public String getTheTs() {
        return theTs;
    }

    public void setTheTs(String theTs) {
        this.theTs = theTs;
    }
}
