// middlewares
import auth from "@/middlewares/auth";
// import component
//import Group1Layout from "@/layouts/Group1Layout";
import MainLayout from '@/layouts/MainLayout'
//import Home from "./Home";
// import GachNo from "./GachNo";
// import InThuXacNhanCongNo from './components/InThuXacNhanCongNo'
// import XacDinhCongNoTheoDanhSach from './components/XacDinhCongNoTheoDanhSach'
// import TraCuuCuocCuocGoi from './components/TraCuuCuocCuocGoi'
import TraCuuPhieuDaGiao from "./TraCuuPhieuDaGiao";
import TraCuuTongNoKhachHang from "./TraCuuTongNoKhachHang";
import TraCuuCuocPhatSinh from "./TraCuuCuocPhatSinh";
import TraCuuKhoaMoMay from "./TraCuuKhoaMoMay";
import TraCuuPhieuTraTheoNgay from "./TraCuuPhieuTraTheoNgay";
import TraCuuHoaDonChuyenKhoan from "./TraCuuHoaDonChuyenKhoan";
import TraCuuPhieuTraTheoUser from "./TraCuuPhieuTraTheoUser";
import TraCuuXuLyNo from "./TraCuuXuLyNo";
import TraCuuNopTienCuaKhachHang from "./TraCuuNopTienCuaKhachHang";
import TraCuuNoKhachHang from "./TraCuuNoKhachHang";
import TraCuuPhieuThuTrongNgayCuaNVTC from "./TraCuuPhieuThuTrongNgayCuaNVTC";
import TraCuuDoiChieuTraCuocVinaphoneVTT from "./TraCuuDoiChieuTraCuocVinaphoneVTT";
// import TraCuuTinhHinhThuNo from '../QLTN/components/TraCuuTinhHinhThuNo/TraCuuTinhHinhThuNo.vue'
import TCTinhHinhThuNo from "./TCTinhHinhThuNo";
import TraCuuNoTongHop from "./TraCuuNoTongHop";
import PhanChiaTuyenThu from "./PhanChiaTuyenThu";
import TraCuuLichSuNhacNo from "./TraCuuLichSuNhacNo";
import InThuXacNhanCongNo from "./InThuXacNhanCongNo";
import TraCuuTongHopPhieuGiao from "./TraCuuTongHopPhieuGiao";
import ChuyenPhieuXLNLacHuong from "./ChuyenPhieuXLNLacHuong";
import TraCuuTheoLichSuThanhToan from "./TraCuuTheoLichSuThanhToan";
import GiaoHoaDonChuyenKhoan from "./GiaoHoaDonChuyenKhoan";
import InLeXuLyNo from "./InLeXuLyNo";
import ChuyenPhieuLacHuong from "./ChuyenPhieuLacHuong";
import TraCuuChuyenPhieuLacHuongPhanChiaKH from "./TraCuuChuyenPhieuLacHuongPhanChiaKH";
import LayDanhSachNo from "./LayDanhSachNo";
import TiepNhanThongTinKhoaMoNoCuoc from "./TiepNhanThongTinKhoaMoNoCuoc";
import TraCuuDichVu from "./TraCuuDichVu";
import KhatNo from "./KhatNo";
import KhoaMayThueBaoNoCuoc from "./KhoaMayThueBaoNoCuoc";
import QuanLyNhanVienNopTien from "./QuanLyNhanVienNopTien";
import TiepNhanYeuCauKhoaMo from "./TiepNhanYeuCauKhoaMo";
import TaoDanhSachNangGoi from "./TaoDanhSachNangGoi";
import HoanThienThayDoiTocDo from "./HoanThienThayDoiTocDo";
import TaoDanhSachHaGoi from "./TaoDanhSachHaGoi";
import MoMayTheoTungSoMayAcc from "./MoMayTheoTungSoMayAcc";
import TreoNo from "./TreoNo";
import NhacNoThanhToan from "./NhacNoThanhToan";
import TraCuuKhachHangLon from "./TraCuuKhachHangLon";
import KhachHangDacBietThang from "./KhachHangDacBietThang";
import TaoDanhSachThanhLyThueBaoTamDung from "./TaoDanhSachThanhLyThueBaoTamDung";
import GiaoPhieuTon from "./GiaoPhieuTon";
import KhoaMayNoCuocTheoTT from "./KhoaMayNoCuocTheoTT";
import KhoaMayTheoSoMayAcc from "./KhoaMayTheoSoMayAcc";
import KhoaMayTheoSoMayAccV2 from "./KhoaMayTheoSoMayAccV2";
import NhanPhieu from "./NhanPhieu";
import XacNhanYeuCauMoKhoa from "./XacNhanYeuCauMoKhoa";
import TaoDuLieuXuLyNo from "./TaoDuLieuXuLyNo";
import MoMayTheoDanhSach from "./MoMayTheoDanhSach";
import InHoaDonChuyenKhoan from "./InHoaDonChuyenKhoan";
import TaoDanhSachNhacNo from "./TaoDanhSachNhacNo";
import NhacNoThueBao from "./NhacNoThueBao";
import DanhSachThueBaoKhongKhoaMo from "./DanhSachThueBaoKhongKhoaMo";
import XacNhanChuyenTuyen from "./XacNhanChuyenTuyen";
import ThayDoiTTKhachHang from "./ThayDoiTTKhachHang";
import CapNhatHoSoCongNo from "./CapNhatHoSoCongNo";
import ThayDoiTuyenThuNVTCFile2 from "./ThayDoiTuyenThuNVTCFile2";
import XacMinhDiaChiThanhToanDiDong from "./XacMinhDiaChiThanhToanDiDong";
import KetXuatChiTietCongNo from "./KetXuatChiTietCongNo";
import XacNhanCongNoTheoMaVach from "./XacNhanCongNoTheoMaVach";
import GiaoPhieu from "./XLTBGiaoPhieu";
import TaoDuLieuXacNhanCongNo from "./TaoDuLieuXacNhanCongNo";
import GanNhomCongNo from "./GanNhomCongNo";
import HanNopTien from "./HanNopTien";
import XacNhanNNNoCuoc from "./XacNhanNNNoCuoc";
import CapNhatKetQuaKhoaMayNoCuoc from "./CapNhatKetQuaKhoaMayNoCuoc";
import XacNhanCongNoTheoDanhSach from "./XacNhanCongNoTheoDanhSach";
import XacNhanCongNoTheoMaThanhToan from "./XacNhanCongNoTheoMaThanhToan";
import DaiLyNopNganHang from "./DaiLyNopNganHang";
import XacNhanHoaDonChuyenKhoan from "./XacNhanHoaDonChuyenKhoan";
import GiaoNhanVienXacNhan from "./GiaoNhanVienXacNhan";
import CapNhatTienKyQuy from "./CapNhatTienKyQuy";
import TraCuuHoaDonDienTuKH from "./TraCuuHoaDonDienTuKH";
import KhoaMoNoCuoc from "./KhoaMoNoCuoc";
import DieuChinhNoKhieuNai from "./DieuChinhNoKhieuNai";
import GachNoFileChuyenKhoan from "./GachNoFileChuyenKhoan";
import ThayDoiTuyenThuNVTCFile from "./ThayDoiTuyenThuNVTCFile";
import QLDLGiaoPhieu from "./QLDLGiaoPhieu";
import XuatDuLieuChiTietCuoc from "./XuatDuLieuChiTietCuoc";
import GanDinhDangChoBanKe from "./GanDinhDangChoBanKe";
import GiaoPhieuXuLyNo from "./GiaoPhieuXuLyNo";
import InThongBaoCuoc from "./InThongBaoCuoc";
import InHoaDonDienTu from "./InHoaDonDienTu";
import HuyHoaDon from "./HuyHoaDon";
import ThayTheHoaDon from "./ThayTheHoaDon";
import TraCuuLichSuDieuChinhHoaDon from "./TraCuuLichSuDieuChinhHoaDon";
import DieuChinhHoaDon from "./DieuChinhHoaDon";
import XuatFileGachNoVNP from "./XuatFileGachNoVNP";
import TCCuocGoi from "./TCCuocGoi";
import DongBoThanhLyInternet from "./DongBoThanhLyInternet";
import DoiSoatTienTraNganHang from "./DoiSoatTienTraNganHang";
import GachNoKhachHangThanhLy from "./GachNoKhachHangThanhLy";
import ThayDoiKenhThu from "./ThayDoiKenhThu";
import TraCuuKhieuNaiDaiLy from "./TraCuuKhieuNaiChung";
import GiaoSanLuongNVTC from "./GiaoSanLuongNVTC/route.vue";
 import GachNoDiDongTienMat from './GachNoDiDong_PhieuThuTienMat'
import ThayDoiTrangThaiHDDT from "./ThayDoiTrangThaiHDDT";
import GachNoKhachHangViPhamCamKet from './GachNoKhachHangViPhamCamKet'
import DanhSachBanKe from "./DanhSachBanKe";
import InPhieuTTHopDong from "./InPhieuTTHopDong";
import InUyNhiem from "./InUyNhiem";
import GachNoDiDong from './GachNoDiDong'
import ThietKeDinhDang from "./ThietKeDinhDang"
import GachNoMaVach from './GachNoMaVach'
import GachNoTuyenThu from './GachNoTuyenThu'
import GachNoThueBaoDiDongCCBS from './GachNoThueBaoDiDongCCBS'
import DanhSachPhieuDaGachNo from './GachNoTuyenThu/components/DanhSachPhieuDaGachNo'
import XacMinhNNNoCuoc from './XacMinhNNNoCuoc'
import GiaoViecPSNVTC from './GiaoViecPSNVTC'
import KhoaMayTheoSoMayAcc_Ver3 from "./KhoaMayTheoSoMayAcc_Ver3";
import QuanLyQuayVong from './QuanLyQuayVong'
import KhoaMayTheoDanhSach from "./KhoaMayTheoDanhSach";
import BanGoiCuoc_DD from "./BanGoiCuoc_DD";
import GiaoKHChamSocKH from './GiaoKHChamSocKH'
import QuanLyNhomCK from './QuanLyNhomCK'
import DuyetNoChuyenTTVT from './DuyetNoChuyenTTVT'
import TraCuuDBKH from './TraCuu_DBKH'
import KhoaMayTheoDanhSach_ver3 from "./KhoaMayTheoDanhSach_ver3";
import CapNhatTTZalo from "./CapNhatTTZalo";
import NhanVienNopTienTraTruocV2 from "./NhanVienNopTienTraTruocV2";
import TraCuuPhieuTraVinaPhone from './TraCuuPhieuTraVinaPhone'
import NhanPhieu3 from './NhanPhieu3'
import TraCuuNoThang from './TraCuuNoThang'
import PhanQuyen_CQCD from "./PhanQuyen_CQCD";
import PhanTapKhachHangDonVi from "./PhanTapKhachHangDonVi"
import PhanTapKhachHangNhanVien from "./PhanTapKhachHangNhanVien"
import PhanTapKhachHangTuyenThu from "./PhanTapKhachHangTuyenThu"
import XuLyTBKhoaNoCuoc from "./XuLyTBKhoaNoCuoc";
import InThuXacNhanCongNoMoi from "./InThuXacNhanCongNoMoi";
import QuanLyThuTuIn from "./QuanLyThuTuIn/route.vue";
import TaoDuLieuInGBC from "./TaoDuLieuInGBC";
import XacNhanNguyenNhanNoCuoc2 from './XacNhanNguyenNhanNoCuoc2'
import GDVNhapLyDo from './GDVNhapLyDo'
import TCThongTinNo from './TCThongTinNo'
import InChiTiet from './InChiTiet'
import XacNhanNNKhongGiaHan from './XacNhanNNKhongGiaHan'
import ThietLapTBC from './ThietLapTBC'
import GiaoKeHoachCSKHFile from './GiaoKeHoachCSKHFile'
import DanhSachMoMayTuDongLoi from './DanhSachMoMayTuDongLoi'
import ImportDongTienVnptPay from './ImportDongTienVnptPay'
import TraCuuHddtDHSX from './TraCuuHddtDHSX'
import TraCuuNoVinaphone from './TraCuuNoVinaphone'
import TraCuuPhieuTra from './TraCuuPhieuTra'
import TaoPhieuThanhLy from './TaoPhieuThanhLy'
import InHoaDon from './InHoaDon'
import CapNhatNhomCongNo from './CapNhatNhomCongNo';
import GanNhomCongNoNhanVien from './GanNhomCongNoNhanVien';
import TraCuuPhieuYeuCauMoKhoa from './TraCuuPhieuYeuCauMoKhoa';
import InThongBaoNo from "./InThongBaoNo/InThongBao";
import InHoaDonChuyenDoi2 from "./InHoaDonChuyenDoi2"
import TraCuuSeriHoaDonThanhToan from './TraCuuSeriHoaDonThanhToan/index.vue'
import KBTraCuuKH from './KBTraCuuKH'
import KBTTBangKe from './KBTTBangKe'
import DocFileNhanTinTBC from './DocFileNhanTinTBC'
import TraCuuTongHopKhoaMoMay from './TraCuuTongHopKhoaMoMay'
import TraCuuGachNoTheoDanhSach from './TraCuuGachNoTheoDanhSach'
export default [
    {
        path: "/qltn",
        name: "qltn",
        component: MainLayout,
        meta: {
            middleware: [auth]
        },
        children: [
            {
                path: "DoiSoatDuLieuNo",
                name: "DoiSoatDuLieuNo",
                component: () => import('./DoiSoatDuLieuNo'),
                meta: {
                    title: 'Đối soát dữ liệu nợ CCBS/OneBSS'
                }
            },
            {
                path: "TraCuuPhieuDaGiao",
                name: "TraCuuPhieuDaGiao",
                component: TraCuuPhieuDaGiao
            },
            {
                path: "TraCuuPhieuTraTheoUser",
                name: "TraCuuPhieuTraTheoUser",
                component: TraCuuPhieuTraTheoUser
            },
            {
                path: "TraCuuTongNoKhachHang",
                name: "TraCuuTongNoKhachHang",
                component: TraCuuTongNoKhachHang
            },
            {
                path: "TraCuuHoaDonChuyenKhoan",
                name: "TraCuuHoaDonChuyenKhoan",
                component: TraCuuHoaDonChuyenKhoan
            },
            {
                path: "TraCuuPhieuTraTheoNgay",
                name: "TraCuuPhieuTraTheoNgay",
                component: TraCuuPhieuTraTheoNgay,
                meta: {
                    title: 'Tra cứu phiếu trả theo ngày'
                }
            },
            {
                path: "TraCuuCuocPhatSinh",
                name: "TraCuuCuocPhatSinh",
                component: TraCuuCuocPhatSinh
            },
            {
              path: "TCTinhHinhThuNo",
              name: "TCTinhHinhThuNo",
              component: TCTinhHinhThuNo
            },
            {
                path: "khat-no",
                component: KhatNo
            },
            {
                path: "nhan-phieu",
                component: NhanPhieu
            },
            {
                path: "in-hoa-don-chuyen-khoan",
                component: InHoaDonChuyenKhoan
            },
            {
                path: "thay-doi-tt-khachhang",
                component: ThayDoiTTKhachHang
            },
            {
                path: "xltb-giao-phieu",
                name: "GiaoPhieuDieuHanhThiCong",
                component: GiaoPhieu
            },
            {
                path: "han-nop-tien",
                component: HanNopTien
            },
            {
                path: "xac-nhan-nn-no-cuoc",
                component: XacNhanNNNoCuoc
            },
            {
                path: "khoa-mo-no-cuoc",
                component: KhoaMoNoCuoc
            },
            {
                path: "qldl-giao-phieu",
                component: QLDLGiaoPhieu
            },
            {
              path: 'in-phieu-tt-hop-dong',
              redirect: to => {
                return '/contract/Payment';
              }
            },
            {
              path: 'ds-ban-ke',
              component: DanhSachBanKe
            },
            {
                path: "MoMayTheoDanhSach",
                name: "MoMayTheoDanhSach",
                component: MoMayTheoDanhSach
            },
            {
                path: "TaoDanhSachNhacNo",
                name: "TaoDanhSachNhacNo",
                component: TaoDanhSachNhacNo
            },
            {
                path: "NhacNoThueBao",
                name: "NhacNoThueBao",
                component: NhacNoThueBao
            },
            {
                path: "XacNhanChuyenTuyen",
                name: "XacNhanChuyenTuyen",
                component: XacNhanChuyenTuyen
            },
            {
                path: "ThayDoiTuyenThuNVTCFile2",
                name: "ThayDoiTuyenThuNVTCFile2",
                component: ThayDoiTuyenThuNVTCFile2
            },
            {
                path: "XacMinhDiaChiThanhToanDiDong",
                name: "XacMinhDiaChiThanhToanDiDong",
                component: XacMinhDiaChiThanhToanDiDong
            },
            {
                path: "KetXuatChiTietCongNo",
                component: KetXuatChiTietCongNo
            },
            {
                path: "XacNhanCongNoTheoMaVach",
                component: XacNhanCongNoTheoMaVach
            },
            {
                path: "TaoDuLieuXacNhanCongNo",
                name: "TaoDuLieuXacNhanCongNo",
                component: TaoDuLieuXacNhanCongNo
            },
            {
                path: "GanNhomCongNo",
                name: "GanNhomCongNo",
                component: GanNhomCongNo,
            },
            {
                path: "CapNhatKetQuaKhoaMayNoCuoc",
                name: "CapNhatKetQuaKhoaMayNoCuoc",
                component: CapNhatKetQuaKhoaMayNoCuoc
            },
            {
                path: "TrungTamVienThongXacMinhNoCuoc",
                name: "TrungTamVienThongXacMinhNoCuoc",
                component: CapNhatKetQuaKhoaMayNoCuoc
            },
            {
                path: "XacNhanCongNoTheoDanhSach",
                name: "XacNhanCongNoTheoDanhSach",
                component: XacNhanCongNoTheoDanhSach
            },
            {
                path: "XacNhanCongNoTheoMaThanhToan",
                component: XacNhanCongNoTheoMaThanhToan
            },
            {
                path: "DaiLyNopNganHang",
                name: "DaiLyNopNganHang",
                component: DaiLyNopNganHang
            },
            {
                path: "XacNhanHoaDonChuyenKhoan",
                name: "XacNhanHoaDonChuyenKhoan",
                component: XacNhanHoaDonChuyenKhoan
            },
            {
                path: "GiaoNhanVienXacNhan",
                component: GiaoNhanVienXacNhan
            },
            {
                path: "CapNhatTienKyQuy",
                name: "CapNhatTienKyQuy",
                component: CapNhatTienKyQuy
            },
            {
                path: "TraCuuHoaDonDienTuKH",
                name: "TraCuuHoaDonDienTuKH",
                component: TraCuuHoaDonDienTuKH
            },
            {
                path: "tra-cuu-dich-vu",
                component: TraCuuDichVu
            },
            {
                path: "TaoDuLieuXuLyNo",
                name: "TaoDuLieuXuLyNo",
                component: TaoDuLieuXuLyNo
            },
            {
                path: "DanhSachThueBaoKhongKhoaMo",
                name: "DanhSachThueBaoKhongKhoaMo",
                component: DanhSachThueBaoKhongKhoaMo
            },
            {
                path: "CapNhatHoSoCongNo",
                name: "CapNhatHoSoCongNo",
                component: CapNhatHoSoCongNo
            },
            {
                path: "GachNo/:nhom",
                name: "GachNo",
                // component: GachNo
                component: () => import('./GachNo')
            },
            {
                path: "DuyetKetQuaXuLyNoCuoc",
                name: "DuyetKetQuaXuLyNoCuoc",
                component: CapNhatKetQuaKhoaMayNoCuoc
            },
            {
                path: "thay-doi-tt-khachhang-am",
                name: "thay-doi-tt-khachhang-am",
                component: ThayDoiTTKhachHang
            },
            {
                path: "DieuChinhNoKhieuNai",
                name: "DieuChinhNoKhieuNai",
                component: DieuChinhNoKhieuNai
            },
            {
                path: "GachNoFileChuyenKhoan",
                name: "GachNoFileChuyenKhoan",
                component: GachNoFileChuyenKhoan
            },
            {
                path: "ThayDoiTuyenThuNVTCFile",
                name: "ThayDoiTuyenThuNVTCFile",
                component: ThayDoiTuyenThuNVTCFile
            },
            {
                path: "XuatDuLieuChiTietCuoc",
                name: "XuatDuLieuChiTietCuoc",
                component: XuatDuLieuChiTietCuoc
            },
            {
                path: "GanDinhDangChoBanKe",
                name: "GanDinhDangChoBanKe",
                component: GanDinhDangChoBanKe
            },
            {
                path: "HuyHoaDon",
                name: "HuyHoaDon",
                component: HuyHoaDon
            },
            {
                path: "ThayTheHoaDon",
                name: "ThayTheHoaDon",
                component: ThayTheHoaDon
            },
            {
                path: "DieuChinhHoaDon",
                name: "DieuChinhHoaDon",
                component: DieuChinhHoaDon
            },
            {
                path: "XuatFileGachNoVNP",
                name: "XuatFileGachNoVNP",
                component: XuatFileGachNoVNP
            },
            {
                path: "TCCuocGoi",
                name: "TCCuocGoi",
                component: TCCuocGoi
            },
            {
                path: "InHoaDonDienTu",
                name: "InHoaDonDienTu",
                component: InHoaDonDienTu
            },
            {
                path: "DoiSoatTienTraNganHang",
                name: "DoiSoatTienTraNganHang",
                component: DoiSoatTienTraNganHang
            },
            {
                path: "GachNoKhachHangThanhLy",
                name: "GachNoKhachHangThanhLy",
                component: GachNoKhachHangThanhLy
            },
            {
                path: "ThayDoiKenhThu",
                name: "ThayDoiKenhThu",
                component: ThayDoiKenhThu
            },
            {
                path: "DongBoThanhLyInternet",
                name: "DongBoThanhLyInternet",
                component: DongBoThanhLyInternet
            },
            {
                path: "TraCuuKhieuNaiDaiLy",
                name: "TraCuuKhieuNaiDaiLy",
                component: TraCuuKhieuNaiDaiLy
            },
            {
                path: "GachNoDiDongTienMat",
                name: "GachNoDiDongTienMat",
                component: GachNoDiDongTienMat
            },
            {
                path: "TraCuuXuLyNo",
                name: "TraCuuXuLyNo",
                component: TraCuuXuLyNo
            },
            {
                path: "TraCuuNopTienCuaKhachHang",
                name: "TraCuuNopTienCuaKhachHang",
                component: TraCuuNopTienCuaKhachHang
            },
            {
                path: "TraCuuNoKhachHang",
                name: "TraCuuNoKhachHang",
                component: TraCuuNoKhachHang
            },
            {
                path: "TraCuuPhieuThuTrongNgayCuaNVTC",
                name: "TraCuuPhieuThuTrongNgayCuaNVTC",
                component: TraCuuPhieuThuTrongNgayCuaNVTC
            },
            {
                path: "TraCuuNoTongHop",
                name: "TraCuuNoTongHop",
                component: TraCuuNoTongHop
            },
            {
                path: "PhanChiaTuyenThu",
                name: "PhanChiaTuyenThu",
                component: PhanChiaTuyenThu
            },
            {
                path: "TraCuuLichSuNhacNo",
                name: "TraCuuLichSuNhacNo",
                component: TraCuuLichSuNhacNo
            },
            {
                path: "TraCuuKhoaMoMay",
                name: "TraCuuKhoaMoMay",
                component: TraCuuKhoaMoMay
            },
            {
                path: "TraCuuDoiChieuTraCuocVinaphoneVTT",
                name: "TraCuuDoiChieuTraCuocVinaphoneVTT",
                component: TraCuuDoiChieuTraCuocVinaphoneVTT
            },
            {
                path: "InThuXacNhanCongNo",
                name: "InThuXacNhanCongNo",
                component: InThuXacNhanCongNo
            },
            {
                path: "TraCuuTongHopPhieuGiao",
                name: "TraCuuTongHopPhieuGiao",
                component: TraCuuTongHopPhieuGiao
            },
            {
                path: "ChuyenPhieuXLNLacHuong",
                name: "ChuyenPhieuXLNLacHuong",
                component: ChuyenPhieuXLNLacHuong
            },
            {
                path: "TraCuuTheoLichSuThanhToan",
                name: "TraCuuTheoLichSuThanhToan",
                component: TraCuuTheoLichSuThanhToan
            },
            {
                path: "GiaoHoaDonChuyenKhoan",
                name: "GiaoHoaDonChuyenKhoan",
                component: GiaoHoaDonChuyenKhoan
            },
            {
                path: "InLeXuLyNo",
                name: "InLeXuLyNo",
                component: InLeXuLyNo
            },
            {
                path: "ChuyenPhieuLacHuong",
                name: "ChuyenPhieuLacHuong",
                component: ChuyenPhieuLacHuong
            },
            {
                path: "TraCuuChuyenPhieuLacHuongPhanChiaKH",
                name: "TraCuuChuyenPhieuLacHuongPhanChiaKH",
                component: TraCuuChuyenPhieuLacHuongPhanChiaKH
            },
            {
                path: "LayDanhSachNo",
                name: "LayDanhSachNo",
                component: LayDanhSachNo
            },
            {
                path: "TiepNhanThongTinKhoaMoNoCuoc",
                name: "TiepNhanThongTinKhoaMoNoCuoc",
                component: TiepNhanThongTinKhoaMoNoCuoc
            },
            {
                path: "KhoaMayThueBaoNoCuoc",
                name: "KhoaMayThueBaoNoCuoc",
                component: KhoaMayThueBaoNoCuoc
            },
            {
                path: "QuanLyNhanVienNopTien",
                name: "QuanLyNhanVienNopTien",
                component: QuanLyNhanVienNopTien
            },
            {
                path: "TiepNhanYeuCauKhoaMo",
                name: "TiepNhanYeuCauKhoaMo",
                component: TiepNhanYeuCauKhoaMo
            },
            {
                path: "TaoDanhSachNangGoi",
                name: "TaoDanhSachNangGoi",
                component: TaoDanhSachNangGoi
            },
            {
                path: "HoanThienThayDoiTocDo",
                name: "HoanThienThayDoiTocDo",
                component: HoanThienThayDoiTocDo
            },
            {
                path: "TaoDanhSachHaGoi",
                name: "TaoDanhSachHaGoi",
                component: TaoDanhSachHaGoi
            },
            {
                path: "MoMayTheoTungSoMayAcc",
                name: "MoMayTheoTungSoMayAcc",
                component: MoMayTheoTungSoMayAcc
            },
            {
                path: "TreoNo",
                name: "TreoNo",
                component: TreoNo
            },
            {
                path: "NhacNoThanhToan",
                name: "NhacNoThanhToan",
                component: NhacNoThanhToan
            },
            {
                path: "GiaoPhieuTon",
                name: "GiaoPhieuTon",
                component: GiaoPhieuTon
            },
            {
                path: "TraCuuKhachHangLon",
                name: "TraCuuKhachHangLon",
                component: TraCuuKhachHangLon
            },
            {
                path: "KhoaMayNoCuocTheoTT",
                name: "KhoaMayNoCuocTheoTT",
                component: KhoaMayNoCuocTheoTT
            },
            {
                path: "KhachHangDacBietThang",
                name: "KhachHangDacBietThang",
                component: KhachHangDacBietThang
            },
            {
                path: "TaoDanhSachThanhLyThueBaoTamDung",
                name: "TaoDanhSachThanhLyThueBaoTamDung",
                component: TaoDanhSachThanhLyThueBaoTamDung
            },
            {
                path: "KhoaMayTheoSoMayAcc",
                name: "KhoaMayTheoSoMayAcc",
                component: KhoaMayTheoSoMayAcc
            },
            {
                path: "KhoaMayTheoSoMayAccV2",
                name: "KhoaMayTheoSoMayAccV2",
                component: KhoaMayTheoSoMayAccV2
            },
            {
                path: "XacNhanYeuCauMoKhoa",
                name: "XacNhanYeuCauMoKhoa",
                component: XacNhanYeuCauMoKhoa
            },
            {
                path: "GiaoPhieuXuLyNo",
                name: "GiaoPhieuXuLyNo",
                component: GiaoPhieuXuLyNo
            },
            {
                path: "InThongBaoCuoc",
                name: "InThongBaoCuoc",
                component: InThongBaoCuoc
            },
            {
                path: "GiaoSanLuongNVTC",
                name: "GiaoSanLuongNVTC",
                component: GiaoSanLuongNVTC,
                meta: {
                    title: 'Giao sản lượng NVTC',
                    dialog: true
                }
            },
            {
              path: "ThayDoiTrangThaiHDDT",
              name: "ThayDoiTrangThaiHDDT",
              component: ThayDoiTrangThaiHDDT
            },
            {
                path: "GachNoKhachHangViPhamCamKet",
                name: "GachNoKhachHangViPhamCamKet",
                component: GachNoKhachHangViPhamCamKet
            },
            {
                path: "InUyNhiem",
                name: "InUyNhiem",
                component: InUyNhiem
            },
            {
                path: "TraCuuLichSuDieuChinhHoaDon",
                name: "TraCuuLichSuDieuChinhHoaDon",
                component: TraCuuLichSuDieuChinhHoaDon
            },
            {
                path: "GachNoDiDong",
                name: "GachNoDiDong",
                component: GachNoDiDong
            },
            {
                path: "ThietKeDinhDang",
                name: "ThietKeDinhDang",
                component: ThietKeDinhDang
            },
            {
                path: 'GachNoMaVach',
                name: 'GachNoMaVach',
                component: GachNoMaVach
            },
            {
                path: 'GachNoTuyenThu',
                name: 'GachNoTuyenThu',
                component: GachNoTuyenThu
            },
            {
                path: 'GachNoThueBaoDiDongCCBS',
                name: 'GachNoThueBaoDiDongCCBS',
                component: GachNoThueBaoDiDongCCBS
            },
{
            path: 'DanhSachPhieuDaGachNo',
            name: 'DanhSachPhieuDaGachNo',
            component: DanhSachPhieuDaGachNo
          },
          {
            path: 'XacMinhNNNoCuoc',
            name: 'XacMinhNNNoCuoc',
            component: XacMinhNNNoCuoc
          },
          {
              path: "GiaoViecPSNVTC",
              name: "GiaoViecPSNVTC",
              component: GiaoViecPSNVTC
          },
          {
            path: "KhoaMayTheoSoMayAcc-ver3",
            name: "KhoaMayTheoSoMayAcc-ver3",
            component: KhoaMayTheoSoMayAcc_Ver3
          },
          {
            path: "QuanLyQuayVong",
            name: "QuanLyQuayVong",
            component: QuanLyQuayVong
          },
          {
            path: "KhoaMayTheoDanhSach",
            name: "KhoaMayTheoDanhSach",
            component: KhoaMayTheoDanhSach
          },
          {
            path: "BanGoiCuoc_DD",
            name: "BanGoiCuoc_DD",
            component: BanGoiCuoc_DD
          },
          {
            path: "GiaoKHChamSocKH",
            name: "GiaoKHChamSocKH",
            component: GiaoKHChamSocKH
        },
        {
            path: 'QuanLyNhomCK',
            name: 'QuanLyNhomCK',
            component: QuanLyNhomCK,
            meta: {
            title: 'Quản lý nhóm chuyển khoản'
            }
        },
        {
            path: 'DuyetNoChuyenTTVT',
            name: 'DuyetNoChuyenTTVT',
            component: DuyetNoChuyenTTVT
        },
          {
            path: 'TraCuuDBKH',
            name: 'TraCuuDBKH',
            component: TraCuuDBKH
          },
          {
            path: 'KhoaMayTheoDanhSach_ver3',
            name: 'KhoaMayTheoDanhSach_ver3',
            component: KhoaMayTheoDanhSach_ver3
          },
          {
            path: 'CapNhatTTZalo',
            name: 'CapNhatTTZalo',
            component: CapNhatTTZalo
          },
          {
            path: 'NhanVienNopTienTraTruocV2',
            name: 'NhanVienNopTienTraTruocV2',
            component: NhanVienNopTienTraTruocV2
          },
          {
            path: 'TraCuuPhieuTraVinaPhone',
            name: 'TraCuuPhieuTraVinaPhone',
            component: TraCuuPhieuTraVinaPhone
          },
          {
            path: 'NhanPhieu3',
            name: 'NhanPhieu3',
            component: NhanPhieu3
          },
          {
            path: 'TraCuuNoThang',
            name: 'TraCuuNoThang',
            component: TraCuuNoThang
          },
          {
            path: 'PhanQuyen_CQCD',
            name: 'PhanQuyen_CQCD',
            component: PhanQuyen_CQCD
          },
          {
            path: 'PhanTapKhachHangDonVi',
            name: 'PhanTapKhachHangDonVi',
            component: PhanTapKhachHangDonVi
          },
          {
            path: 'PhanTapKhachHangNhanVien',
            name: 'PhanTapKhachHangNhanVien',
            component: PhanTapKhachHangNhanVien
          },
          {
            path: 'PhanTapKhachHangTuyenThu',
            name: 'PhanTapKhachHangTuyenThu',
            component: PhanTapKhachHangTuyenThu
          },

          {
            path: 'XuLyTBKhoaNoCuoc',
            name: 'XuLyTBKhoaNoCuoc',
            component: XuLyTBKhoaNoCuoc
          },
          {
            path: 'InThuXacNhanCongNoMoi',
            name: 'InThuXacNhanCongNoMoi',
            component: InThuXacNhanCongNoMoi
          },
          {
            path: 'TaoDuLieuInGBC',
            name: 'TaoDuLieuInGBC',
            component: TaoDuLieuInGBC,
            meta: {
                title: 'Tạo dữ liệu in Giấy Báo Cước',
                dialog: true
              }

          },
          {
            path: 'QuanLyThuTuIn',
            name: 'QuanLyThuTuIn',
            component: QuanLyThuTuIn,
            meta: {
                title: 'Quản lý thứ tự in',
                dialog: true
              }
          },
          {
            path: 'XacNhanNguyenNhanNoCuoc2',
            name: 'XacNhanNguyenNhanNoCuoc2',
            component: XacNhanNguyenNhanNoCuoc2,
            meta: {
              title: 'Xác nhận nguyên nhân nợ cước (theo Nhật ký thu nợ)'
            }
          },
          {
            path: 'GDVNhapLyDo',
            name: 'GDVNhapLyDo',
            component: GDVNhapLyDo,
            meta: {
              title: 'Giao dịch viên cập nhật thông tin nợ'
            }
          },
          {
            path: 'TCThongTinNo',
            name: 'TCThongTinNo',
            component: TCThongTinNo,
            meta: {
              title: 'Tra cứu thông tin nợ'
            }
          },
          {
            path: 'InChiTiet',
            name: 'InChiTiet',
            component: InChiTiet,
            meta: {
              title: 'Tra cứu cước cuộc gọi'
            }
          },
          {
            path: 'XacNhanNNKhongGiaHan',
            name: 'XacNhanNNKhongGiaHan',
            component: XacNhanNNKhongGiaHan,
            meta: {
              title: 'Xác nhận nguyên nhân không gia hạn trước'
            }
          },
          {
            path: 'ThietLapTBC',
            name: 'ThietLapTBC',
            component: ThietLapTBC,
            meta: {
              title: 'Thiết lập gửi thông báo cước'
            }
          },
          {
            path: 'GiaoKeHoachCSKHFile',
            name: 'GiaoKeHoachCSKHFile',
            component: GiaoKeHoachCSKHFile
          },
	      {
            path: 'DanhSachMoMayTuDongLoi',
            name: 'DanhSachMoMayTuDongLoi',
            component: DanhSachMoMayTuDongLoi
          },
          {
            path: 'ImportDongTienVnptPay',
            name: 'ImportDongTienVnptPay',
            component: ImportDongTienVnptPay
          },
          {
            path: 'TraCuuHddtDHSX',
            name: 'TraCuuHddtDHSX',
            component: TraCuuHddtDHSX
          },
          {
            path: 'TraCuuNoVinaphone',
            name: 'TraCuuNoVinaphone',
            component: TraCuuNoVinaphone,
            meta: {
              title: 'Tra cứu nợ thuê bao di động'
            }
          },
            {
                path: 'TraCuuPhieuTra',
                name: 'TraCuuPhieuTra',
                component: TraCuuPhieuTra
            },
            {
                path: 'TaoPhieuThanhLy',
                name: 'TaoPhieuThanhLy',
                component: TaoPhieuThanhLy
            },
            {
                path: 'InHoaDon',
                name: 'InHoaDon',
                component: InHoaDon,
                meta: {
                    title: 'In hóa đơn',
                    dialog: true
                  }
            },
            {
                path: 'CapNhatNhomCongNo',
                name: 'CapNhatNhomCongNo',
                component: CapNhatNhomCongNo,
                meta: {
                    title: 'Cập nhật nhóm công nợ',
                    dialog: true
                  }
            },
            {
                path: 'GanNhomCongNoNhanVien',
                name: 'GanNhomCongNoNhanVien',
                component: GanNhomCongNoNhanVien,
                meta: {
                    title: 'Gán nhóm công nợ - nhân viên',
                    dialog: true
                  }
            },
            {
                path: 'TraCuuPhieuYeuCauMoKhoa',
                name: 'TraCuuPhieuYeuCauMoKhoa',
                component: TraCuuPhieuYeuCauMoKhoa,
            },
            {
                path: 'InThongBaoNo',
                name: 'InThongBaoNo',
                component: InThongBaoNo,
                meta: {
                    title: 'In thông báo nợ',
                    dialog: true
                  }
            },
            {
                path: 'InHoaDonChuyenDoi',
                name: 'InHoaDonChuyenDoi',
                component: InHoaDonChuyenDoi2,
                meta: {
                    title: 'In hóa đươn chuyển đổi',
                    dialog: true
                  }
            },
            {
                path: 'TraCuuSeriHoaDon',
                name: 'TraCuuSeriHoaDon',
                component: TraCuuSeriHoaDonThanhToan,
                meta: {
                    title: 'Tra cứu Seri hóa đơn thanh toán',
                    dialog: true
                  }
            },
            {
                path: 'KBTraCuuKH',
                name: 'KBTraCuuKH',
                component: KBTraCuuKH,
            },
            {
                path: 'KBTTBangKe',
                name: 'KBTTBangKe',
                component: KBTTBangKe,
            },
            {
                path: 'DocFileNhanTinTBC',
                name: 'DocFileNhanTinTBC',
                component: DocFileNhanTinTBC,
            },
            {
                path: 'TraCuuGachNoTheoDanhSach',
                name: 'TraCuuGachNoTheoDanhSach',
                component: TraCuuGachNoTheoDanhSach,
            },

            {
                path: 'TraCuuTongHopKhoaMoMay',
                name: 'TraCuuTongHopKhoaMoMay',
                component: TraCuuTongHopKhoaMoMay,
                meta: {
                    title: 'Tra cứu khóa mở máy theo ngày',
                    dialog: true
                  }
            },
            
        ]
    }];
