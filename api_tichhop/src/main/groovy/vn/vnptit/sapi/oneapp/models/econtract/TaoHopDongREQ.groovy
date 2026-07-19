package vn.vnptit.sapi.oneapp.models.econtract

public class BusinessCustomerInformation {
    public DaiDien daiDien;
    public ToChuc toChuc;
}

public class CustomerInformation {
    public String customerCode;
    public String customerName;
    public String birthDay;
    public String gender;
    public String nationality;
    public String citizenIdentificationNumber;
    public String dateOfIssuanceOfCid;
    public String placeOfIssuanceOfCid;
    public String address;
    public String cluster;
    public String wardId;
    public String districtId;
    public String provinceId;
    public String phoneNumber;
    public String email;
    public String otherContactInformation;
    public String visa;
}

public class DanhSach {
    public String tt;
    public String dichvu;
    public String dvt;
    public String soluong;
    public String dongia;
    public String thanhtien;
    public String tienthue;
    public String cong;
}

public class DaiDien {
    public String customerCode;
    public String ten;
    public String chucVu;
    public String ngaySinh;
    public String cmnd;
    public String ngayCap;
    public String noiCap;
    public String tinhId;
    public String xaId;
    public String soNha;
    public String duong;
    public String sdt;
    public String email;
    public String giayUyQuyenSo;
    public String ngayCapUyQuyen;
}

public class DataRender {
    public String soHopDong;
    public String tenKhachHang;
    public String diaChiKhachHang;
    public String sdtKhachHang;
    public String faxKhachHang;
    public String emailGiaoDichKH;
    public String mstToChucKH;
    public String taiKhoanSoKH;
    public String taiNganHangKH;
    public String quocTichKH;
    public String soGiayToKH;
    public String noiCapGiayToKH;
    public String ngayCapGiayToKH;
    public String nguoiDaiDienKH;
    public String chucVuNguoiDaiDien;
    public String giayUyQuyenSoKH;
    public String ngayCapGiayUyQuyenKH;
    public String cmndNguoiDaiDienKH;
    public String noiCapCmndKH;
    public String ngayCapCmndKH;
    public String thongTinKhacKH;
    public String sdtSuDungCTS;
    public String diaChiEmailCTS;
    public String thsdThang;
    public String tongTienBangChu;
    public String tenCTS;
    public String chucVuCTS;
    public String cmndCTS;
    public String ngayCapCmndCTS;
    public String noiCapCmndCTS;
    public String diaDanh;
    public String ngay;
    public String thang;
    public String dsTong1;
    public String dsTong2;
    public String dsTong3;
    public String noiKy;
    public ArrayList<DanhSach> ds;
    public ArrayList<Integer> htccdv;
    public ArrayList<Integer> dtkh;
    public ArrayList<Integer> thietbisddv;
    public ArrayList<Integer> smartCA;
    public ArrayList<Integer> thsd;
    public ArrayList<Integer> goiDVTC;
    public ArrayList<Integer> goiDVCN;
    public ArrayList<Integer> goiDV1Lan;
    public ArrayList<Object> benTaoKhoa;
    public ArrayList<Object> vnptCA;
}

public class TaoHopDongREQ {
    public CustomerInformation customerInformation;
    public BusinessCustomerInformation businessCustomerInformation;
    public Map<String, Object> dataRender;
    public String orderCode;
    public String areaCode;
    public String transactionCode;
    public String subscriptionCode;
    public String unitCode;
    public String hrmCodeOfCreator;
    public String signForm;
    public String contractTemplateId;
    public String contractNumber;
    public String tinhId;
    public String loaiHinhDichVu;
}

public class ToChuc {
    public String tenToChuc;
    public String sdt;
    public String email;
    public String soDkdn;
    public String ngayCapSoDkdn;
    public String noiCapSoDkdn;
    public String diaChiTruSoTinhId;
    public String diaChiTruSoHuyenId;
    public String diaChiTruSoXaId;
    public String maSoThue
}

