// Khai bao store của module qlvt
import { qlvtCommon } from "./commons";
import { hoantrathietbibaohanh } from "./hoantrathietbibaohanh";
import { tiepnhanthietbibaohanh } from "./tiepnhanthietbibaohanh";
import { giaophieubaohanh } from "./giaophieubaohanh/index";
import { mohinhbaohanhthietbi } from "./mohinhbaohanhthietbi";
import { xulythietbibaohanh } from "./xulythietbibaohanh";
import { tracuuvattutrongkho } from "./tracuuvattutrongkho";
import { tracuuccdc } from "./tracuuccdc";
import { tracuuvattuthuebao } from "./tracuuvattuthuebao";
import { tracuuchungtu } from "./tracuuchungtu";
import { capphatccdc } from "./capphatccdc";
import { chonnhanvienccdc } from "./chonnhanvienccdc";
import { chonchungtuccdc } from "./chonchungtuccdc";
import { capnhatserial } from "./capnhatserial";
import { tracuuhopdongvadonhang } from "./tracuuhopdongvadonhang";
import { duyetnhucaudangkyvattu } from "./duyetnhucaudangkyvattu";
import { tracuutonkhoTTCUVT } from "./tracuutonkhoTTCUVT";
import { quyettoanvattucongtrinh } from "./quyettoanvattucongtrinh";
import { tonghopnhucaudangkymuasam } from "./tonghopnhucaudangkymuasam";
import { thanhtoandonhang } from "./thanhtoandonhang";
import { xetduyetcongcubaoho } from "./xetduyetcongcubaoho";
import { trucuutonkhotheoUser } from "./trucuutonkhotheoUser";
import { nhapthietbibaohanh } from "./nhapthietbibaohanh";
import { duyetnhucaucapphatdoivt } from "./duyetnhucaucapphatdoivt";
import { dsvattu } from "./dsvattu";
import { dottonghop } from "./dottonghop";
import { tracuuserialthietbi } from "./tracuuserialthietbi";
import { donhang } from "./donhang";
import { dangkynhucauVT } from "./dangkynhucauVT";
import { nhapxuatvt_v2 } from "./nhapxuatvt_v2";
import { nghiemthudonhang } from "./nghiemthudonhang";
import { tracuudonhangconhan } from "./tracuudonhangconhan";
import { tamungvattu } from "./tamungvattu";
import { giaohoantravattu } from "./giaohoantravattu";
import { thuhoivattuthuebao } from "./thuhoivattuthuebao";
import { quyettoanvattu } from "./quyettoanvattu";
import { tonghopdanhsachthuhoi } from "./tonghopdanhsachthuhoi";
import { nhapxuatvt } from "./nhapxuatvt";
import { dieuchuyensuachuathuhoiccdc } from "./dieuchuyensuachuathuhoiccdc";
import { danhmucthietbi } from "./danhmucthietbi";
import { loaithietbi } from "./loaithietbi";
import { quanlynhatram } from "./quanlynhatram";
import { frmganseri } from "./frmganseri";
import { ganthuoctinh } from "./ganthuoctinh";
import { capvattuthuebao } from "./capvattuthuebao"
import { frmvattutrongkho } from "./frmvattutrongkho"
import { frmnhapkhoth } from "./frmnhapkhoth"
import { danhsachquyettoanvattu } from "./danhsachquyettoanvattu"
import { frmchiacuoncap } from "./frmchiacuoncap"
import { frmnhapcap } from "./frmnhapcap"
import { capvattuct } from "./capvattuct"
import { chonvattuccdc } from "./chonvattuccdc"
import { tracuudenghi } from "./tracuudenghi"
import { denghiquyettoanvattu } from "./denghiquyettoanvattu"
import { nhapdenghivattu } from "./nhapdenghivattu"
import { chonvattuctct } from "./chonvattuctct"
import { doisoatserial } from "./doisoatserial"
import { xacnhandenghi } from "./xacnhandenghi"
import { suaserialgp } from "./suaserialgp"
import { kiemkeccdc } from "./kiemkeccdc"
import { loaivt } from "./loaivt"
import { qlts } from "./qlts"
import { loaikhomakho } from "./loaikhomakho"
import { frmgiahanhd } from "./frmgiahanhd"
import { tracuuttdongbo } from "./tracuuttdongbo"
import { chonchungtuqt } from "./chonchungtuqt"
import { bosungvttheofile } from "./bosungvttheofile"
import { dongboccdc } from "./dongboccdc"
export const qlvt = {
  modules: {
    qlvtCommon,
    hoantrathietbibaohanh,
    tiepnhanthietbibaohanh,
    giaophieubaohanh,
    mohinhbaohanhthietbi,
    xulythietbibaohanh,
    tracuuvattutrongkho,
    tracuuccdc,
    tracuuchungtu,
    capphatccdc,
    chonnhanvienccdc,
    chonchungtuccdc,
    capnhatserial,
    tracuuhopdongvadonhang,
    duyetnhucaudangkyvattu,
    tracuutonkhoTTCUVT,
    quyettoanvattucongtrinh,
    tonghopnhucaudangkymuasam,
    thanhtoandonhang,
    xetduyetcongcubaoho,
    trucuutonkhotheoUser,
    nhapthietbibaohanh,
    duyetnhucaucapphatdoivt,
    dsvattu,
    dottonghop,
    tracuuserialthietbi,
    donhang,
    dangkynhucauVT,
    nhapxuatvt_v2,
    nghiemthudonhang,
    tracuudonhangconhan,
    tamungvattu,
    giaohoantravattu,
    thuhoivattuthuebao,
    quyettoanvattu,
    tonghopdanhsachthuhoi,
    nhapxuatvt,
    dieuchuyensuachuathuhoiccdc,
    danhmucthietbi,
    loaithietbi,
    quanlynhatram,
    frmganseri,
    ganthuoctinh,
    capvattuthuebao,
    frmvattutrongkho,
    frmnhapkhoth,
    danhsachquyettoanvattu,
    frmchiacuoncap,
    frmnhapcap,
    tracuuvattuthuebao,
    capvattuct,
    chonvattuccdc,
    tracuudenghi,
    denghiquyettoanvattu,
    nhapdenghivattu,
    chonvattuctct,
    doisoatserial,
    xacnhandenghi,
    suaserialgp,
    kiemkeccdc,
    loaivt,
    qlts,
    loaikhomakho,
    frmgiahanhd,
    tracuuttdongbo,
    chonchungtuqt,
    bosungvttheofile,
    dongboccdc
  }
};
