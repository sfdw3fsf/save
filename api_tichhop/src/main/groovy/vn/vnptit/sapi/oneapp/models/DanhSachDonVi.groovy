package vn.vnptit.sapi.oneapp.models

import org.springframework.stereotype.Component


class DanhSachDonVi {
    String ten_tat
    String ma_vung

    DanhSachDonVi(String ten_tat, String ma_vung) {
        this.ten_tat = ten_tat
        this.ma_vung = ma_vung
    }

}

@Component
class DonVi{
    String get_dv(String ten_tat){
        List<DanhSachDonVi> dvi = new ArrayList<>()
        dvi.add(new DanhSachDonVi("AGG","094"))
        dvi.add(new DanhSachDonVi("VTU","074"))
        dvi.add(new DanhSachDonVi("BGG","021"))
        dvi.add(new DanhSachDonVi("BKN","017"))
        dvi.add(new DanhSachDonVi("BNH","016"))
        dvi.add(new DanhSachDonVi("BDH","053"))
        dvi.add(new DanhSachDonVi("BTE","083"))
        dvi.add(new DanhSachDonVi("BDG","072"))
        dvi.add(new DanhSachDonVi("BPC","077"))
        dvi.add(new DanhSachDonVi("BTN","062"))
        dvi.add(new DanhSachDonVi("BLU","099"))
        dvi.add(new DanhSachDonVi("CBG","022"))
        dvi.add(new DanhSachDonVi("CTO","088"))
        dvi.add(new DanhSachDonVi("CMU","096"))
        dvi.add(new DanhSachDonVi("DNG","059"))
        dvi.add(new DanhSachDonVi("DLC","065"))
        dvi.add(new DanhSachDonVi("DNI","071"))
        dvi.add(new DanhSachDonVi("DTP","093"))
        dvi.add(new DanhSachDonVi("GLI","054"))
        dvi.add(new DanhSachDonVi("HGG","029"))
        dvi.add(new DanhSachDonVi("HNI","010"))
        dvi.add(new DanhSachDonVi("HTH","043"))
        dvi.add(new DanhSachDonVi("HYN","039"))
        dvi.add(new DanhSachDonVi("HNM","030"))
        dvi.add(new DanhSachDonVi("HPG","035"))
        dvi.add(new DanhSachDonVi("HDG","034"))
        dvi.add(new DanhSachDonVi("HCM","070"))
        dvi.add(new DanhSachDonVi("KHA","057"))
        dvi.add(new DanhSachDonVi("KGG","095"))
        dvi.add(new DanhSachDonVi("KTM","058"))
        dvi.add(new DanhSachDonVi("LCU","012"))
        dvi.add(new DanhSachDonVi("LSN","020"))
        dvi.add(new DanhSachDonVi("LCI","019"))
        dvi.add(new DanhSachDonVi("LDG","061"))
        dvi.add(new DanhSachDonVi("LAN","081"))
        dvi.add(new DanhSachDonVi("NDH","032"))
        dvi.add(new DanhSachDonVi("NAN","042"))
        dvi.add(new DanhSachDonVi("NBH","040"))
        dvi.add(new DanhSachDonVi("NTN","063"))
        dvi.add(new DanhSachDonVi("PYN","056"))
        dvi.add(new DanhSachDonVi("QBH","045"))
        dvi.add(new DanhSachDonVi("QNM","051"))
        dvi.add(new DanhSachDonVi("QNI","052"))
        dvi.add(new DanhSachDonVi("QNH","036"))
        dvi.add(new DanhSachDonVi("QTI","046"))
        dvi.add(new DanhSachDonVi("STG","097"))
        dvi.add(new DanhSachDonVi("SLA","027"))
        dvi.add(new DanhSachDonVi("TNH","073"))
        dvi.add(new DanhSachDonVi("TBH","033"))
        dvi.add(new DanhSachDonVi("THA","041"))
        dvi.add(new DanhSachDonVi("HUE","047"))
        dvi.add(new DanhSachDonVi("TGG","082"))
        dvi.add(new DanhSachDonVi("TVH","090"))
        dvi.add(new DanhSachDonVi("TQG","025"))
        dvi.add(new DanhSachDonVi("VLG","091"))
        dvi.add(new DanhSachDonVi("VPC","011"))
        dvi.add(new DanhSachDonVi("PTO","024"))
        dvi.add(new DanhSachDonVi("YBI","026"))
        dvi.add(new DanhSachDonVi("TNN","023"))
        dvi.add(new DanhSachDonVi("DAN","066"))
        dvi.add(new DanhSachDonVi("HBH","013"))
        dvi.add(new DanhSachDonVi("HGI","087"))
        dvi.add(new DanhSachDonVi("NET","168"))
        dvi.add(new DanhSachDonVi("DBN","015"))

        return dvi.find { it.ten_tat == ten_tat}.ma_vung
    }
}
