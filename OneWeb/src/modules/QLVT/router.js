// middlewares
import auth from "@/middlewares/auth";

// import Group1Layout from "@/layouts/Group1Layout";
import MainLayout from "@/layouts/MainLayout";
//import Home from "./Home";
import HoanTraThietBiBaoHanh from "./HoanTraThietBiBaoHanh";
import TiepNhanThietBiBaoHanh from "./TiepNhanThietBiBaoHanh";
import MoHinhBaoHanhThietBi from "./MoHinhBaoHanhThietBi";
import XuLyThietBiBaoHanh from "./XuLyThietBiBaoHanh";
import TraCuuVatTuTrongKho from "./TraCuuVatTuTrongKho";
import TraCuuCCDC from "./TraCuuCCDC";

import TraCuuChungTu from "./TraCuuChungTu";
import GiaoPhieuBaoHanh from "./GiaoPhieuBaoHanh";
import CapPhatCCDC from "./CapPhatCCDC";
import CapNhatSerial from "./CapNhatSerial";
import TraCuuHopDongVaDonHang from "./TraCuuHopDongVaDonHang";
import DuyetNhuCauDangKyVatTu from "./DuyetNhuCauDangKyVatTu";
import TraCuuTonKhoTTCUVT from "./TraCuuTonKhoTTCUVT";
import QuyetToanVatTuCongTrinh from "./QuyetToanVatTuCongTrinh";
import TongHopNhuCauDangKyMuaSam from "./TongHopNhuCauDangKyMuaSam";
import ThanhToanDonHang from "./ThanhToanDonHang";
import XetDuyetCongCuBaoHo from "./XetDuyetCongCuBaoHo";
import NhapThietBiBaoHanh from "./NhapThietBiBaoHanh";
import DuyetNhuCauCapPhatDoiVT from "./DuyetNhuCauCapPhatDoiVT";
import TraCuuSerialThietBi from "./TraCuuSerialThietBi";
import DonHang from "./DonHang";
import DangKyNhuCauVT from "./DangKyNhuCauVT";
import NhapXuatVT_v2 from "./NhapXuatVT_v2";
import TraCuuDonHangConHan from "./TraCuuDonHangConHan";
import TamUngVatTu from "./TamUngVatTu";
import NghiemThuDonHang from "./NghiemThuDonHang";
import GiaoHoanTraVatTu from "./GiaoHoanTraVatTu";
import ThuHoiVatTuThueBao from "./ThuHoiVatTuThueBao";
import QuyetToanVatTu from "./QuyetToanVatTu";
import DotTongHop from "./DotTongHop";
import TongHopDanhSachThuHoi from "./TongHopDanhSachThuHoi";
import TongHopDanhSachThuHoi_New from "./frmTongHopThuHoi_New";
import NhapXuatVT from "./NhapXuatVT";
import DieuChuyenSuaChuaThuHoiCCDC from "./DieuChuyenSuaChuaThuHoiCCDC";
import QuanLyNhaTram from "./QuanLyNhaTram";
import HopDongMuaSam from "./HopDongMuaSam";
import CapVatTuThueBao from "./CapVatTuThueBao";
import DanhSachQuyetToanVatTu from "./DanhSachQuyetToanVatTu";
import CapVatTuCT from "./CapVatTuCT";

import ChonVatTuCCDC from "./ChonVatTuCCDC";
import TraCuuDeNghi from "./TraCuuDeNghi";
import DeNghiQuyetToanVatTu from "./DeNghiQuyetToanVatTu";
import NhapDeNghiVatTu from "./NhapDeNghiVatTu";
import XacNhanDeNghi from "./XacNhanDeNghi";
import SuaSerialGP from "./SuaSerialGP";
import TraCuuVatTuThueBao from "./components/TraCuuVatTuThueBao/TraCuuVatTuThueBao";
import CapNhatTTThuHoi from "./CapNhatTTThuHoi/CapNhatTTThuHoi.vue";
import DSPhieuGiao from "./DSPhieuGiao/DSPhieuGiao.vue";
import KiemKeCCDC from "./KiemKeCCDC/KiemKeCCDC.vue";
import DinhMucVatTu from "./DinhMucVatTu/DinhMucVatTu.vue";
import ChuanHoaSerial from "./ChuanHoaSerial/ChuanHoaSerial.vue";
import QuanLyPhieuIn from "./QuanLyPhieuIn/QuanLyPhieuIn.vue";
import frmKhung_Gttt from "./frmKhung_Gttt/frmKhung_Gttt.vue";
import frmCapNhat_CKS_QLVT from "./frmCapNhat_CKS_QLVT/frmCapNhat_CKS_QLVT.vue";
import LoaiVT from "./LoaiVT";
import ChuyenVatTuThueBao from "./ChuyenVatTuThueBao/ChuyenVatTuThueBao.vue";
import LoaiKhoMaKho from "./LoaiKhoMaKho";
import XuLyYeuCauVatTu from "./XuLyYeuCauVatTu/XuLyYeuCauVatTu.vue";
import CongTrinh from "./CongTrinh/CongTrinh.vue";
import BoSungVatTu from "./frmBoSung_VT/frmBoSung_VT.vue";
import DeNghiVT from "./DeNghiVT/DeNghiVT.vue";
import showDanhMucThietBi from "./showDanhMucThietBi";
import popupTraCuuVatTuThueBao from "./popupTraCuuVatTuThueBao";
import popupTraCuuVatTuTrongKho from "./popupTraCuuVatTuTrongKho";
import TraCuuTonKhoTheoUser from "./TraCuuTonKhoTheoUser";
import BoSungVT_TheoFile from "./BoSungVT_TheoFile";
import LenhDieuChuyen from "./LenhDieuChuyen";
import DuyetNhuCauCapPhatKHDN from "./DuyetNhuCauCapPhatKHDN";
import NhanPhieuLuanChuyen from "./NhanPhieuLuanChuyen";
import BaoHanhTraCuuThongTinPhieu from "./BaoHanhTraCuuThongTinPhieu";
import TraCuuTheoSoLoPhieuPSIS from "./TraCuuTheoSoLoPhieuPSIS";
import PhieuBaoHanhPSIS from "./PhieuBaoHanhPSIS";
import popupTraCuuTTDongBo from "./popupTraCuuTTDongBo";
export default [
    {
        path: "/qlvt",
        name: "qlvt",
        component: MainLayout,
        meta: {
            middleware: [auth]
        },
        children: [
            {
                path: "HoanTraThietBiBaoHanh",
                name: "HoanTraThietBiBaoHanh",
                component: HoanTraThietBiBaoHanh,
                meta: {
                    title: ""
                }
            },
            {
                path: "TiepNhanThietBiBaoHanh",
                name: "TiepNhanThietBiBaoHanh",
                component: TiepNhanThietBiBaoHanh,
                meta: {
                    title: ""
                }
            },
            {
                path: "GiaoPhieuBaoHanh",
                name: "GiaoPhieuBaoHanh",
                component: GiaoPhieuBaoHanh,
                meta: {
                    title: ""
                }
            },
            {
                path: "MoHinhBaoHanhThietBi",
                name: "MoHinhBaoHanhThietBi",
                component: MoHinhBaoHanhThietBi,
                meta: {
                    title: ""
                }
            },
            {
                path: "XuLyThietBiBaoHanh",
                name: "XuLyThietBiBaoHanh",
                component: XuLyThietBiBaoHanh,
                meta: {
                    title: ""
                }
            },
            {
                path: "TraCuuVatTuTrongKho",
                name: "popupTraCuuVatTuTrongKho",
                component: popupTraCuuVatTuTrongKho,
                meta: {
                    title: ""
                }
            },
            {
                path: "TraCuuChungTu",
                name: "TraCuuChungTu",
                component: TraCuuChungTu,
                meta: {
                    title: ""
                }
            },
            {
                path: "TraCuuCCDC",
                name: "TraCuuCCDC",
                component: TraCuuCCDC,
                meta: {
                    title: ""
                }
            },
            {
                path: "CapPhatCCDC",
                name: "CapPhatCCDC",
                component: CapPhatCCDC,
                meta: {
                    title: ""
                }
            },
            {
                path: "CapNhatSerial",
                name: "CapNhatSerial",
                component: CapNhatSerial,
                meta: {
                    title: ""
                }
            },
            {
                path: "TraCuuHopDongVaDonHang",
                name: "TraCuuHopDongVaDonHang",
                component: TraCuuHopDongVaDonHang,
                meta: {
                    title: ""
                }
            },
            {
                path: "DuyetNhuCauDangKyVatTu",
                name: "DuyetNhuCauDangKyVatTu",
                component: DuyetNhuCauDangKyVatTu,
                meta: {
                    title: ""
                }
            },
            {
                path: "TraCuuTonKhoTTCUVT",
                name: "TraCuuTonKhoTTCUVT",
                component: TraCuuTonKhoTTCUVT,
                meta: {
                    title: ""
                }
            },
            {
                path: "QuyetToanVatTuCongTrinh",
                name: "QuyetToanVatTuCongTrinh",
                component: QuyetToanVatTuCongTrinh,
                meta: {
                    title: ""
                }
            },
            {
                path: "TongHopNhuCauDangKyMuaSam",
                name: "TongHopNhuCauDangKyMuaSam",
                component: TongHopNhuCauDangKyMuaSam,
                meta: {
                    title: ""
                }
            },
            {
                path: "ThanhToanDonHang",
                name: "ThanhToanDonHang",
                component: ThanhToanDonHang,
                meta: {
                    title: ""
                }
            },
            {
                path: "XetDuyetCongCuBaoHo",
                name: "XetDuyetCongCuBaoHo",
                component: XetDuyetCongCuBaoHo,
                meta: {
                    title: ""
                }
            },
            {
                path: "NhapThietBiBaoHanh",
                name: "NhapThietBiBaoHanh",
                component: NhapThietBiBaoHanh,
                meta: {
                    title: ""
                }
            },
            {
                path: "DuyetNhuCauCapPhatDoiVT",
                name: "DuyetNhuCauCapPhatDoiVT",
                component: DuyetNhuCauCapPhatDoiVT,
                meta: {
                    title: ""
                }
            },
            {
                path: "TraCuuSerialThietBi",
                name: "TraCuuSerialThietBi",
                component: TraCuuSerialThietBi,
                meta: {
                    title: ""
                }
            },
            {
                path: "DonHang",
                name: "DonHang",
                component: DonHang,
                meta: {
                    title: ""
                }
            },
            {
                path: "DangKyNhuCauVT",
                name: "DangKyNhuCauVT",
                component: DangKyNhuCauVT,
                meta: {
                    title: ""
                }
            },
            {
                path: "NhapXuatVT_v2",
                name: "NhapXuatVT_v2",
                component: NhapXuatVT_v2,
                meta: {
                    title: ""
                }
            },
            {
                path: "TraCuuDonHangConHan",
                name: "TraCuuDonHangConHan",
                component: TraCuuDonHangConHan,
                meta: {
                    title: ""
                }
            },
            {
                path: "TamUngVatTu",
                name: "TamUngVatTu",
                component: TamUngVatTu,
                meta: {
                    title: ""
                }
            },
            {
                path: "NghiemThuDonHang",
                name: "NghiemThuDonHang",
                component: NghiemThuDonHang,
                meta: {
                    title: ""
                }
            },
            {
                path: "GiaoHoanTraVatTu",
                name: "GiaoHoanTraVatTu",
                component: GiaoHoanTraVatTu,
                meta: {
                    title: ""
                }
            },
            {
                path: "ThuHoiVatTuThueBao",
                name: "ThuHoiVatTuThueBao",
                component: ThuHoiVatTuThueBao,
                meta: {
                    title: ""
                }
            },
            {
                path: "QuyetToanVatTu",
                name: "QuyetToanVatTu",
                component: QuyetToanVatTu,
                meta: {
                    title: ""
                }
            },
            {
                path: "DotTongHop",
                name: "DotTongHop",
                component: DotTongHop,
                meta: {
                    title: ""
                }
            },
            {
                path: "TongHopDanhSachThuHoi",
                name: "TongHopDanhSachThuHoi",
                component: TongHopDanhSachThuHoi,
                meta: {
                    title: ""
                }
            },
            {
                path: "TongHopDanhSachThuHoi_New",
                name: "TongHopDanhSachThuHoi_New",
                component: TongHopDanhSachThuHoi_New,
                meta: {
                    title: ""
                }
            },
            {
                path: "NhapXuatVT",
                name: "NhapXuatVT",
                component: NhapXuatVT,
                meta: {
                    title: ""
                }
            },
            {
                path: "DieuChuyenSuaChuaThuHoiCCDC",
                name: "DieuChuyenSuaChuaThuHoiCCDC",
                component: DieuChuyenSuaChuaThuHoiCCDC,
                meta: {
                    title: ""
                }
            },
            {
                path: "QuanLyNhaTram",
                name: "QuanLyNhaTram",
                component: QuanLyNhaTram,
                meta: {
                    title: ""
                }
            },
            {
                path: "HopDongMuaSam",
                name: "HopDongMuaSam",
                component: HopDongMuaSam,
                meta: {
                    title: ""
                }
            },
            {
                path: "CapVatTuThueBao",
                name: "CapVatTuThueBao",
                component: CapVatTuThueBao,
                meta: {
                    title: ""
                }
            },
            {
                path: "DanhSachQuyetToanVatTu",
                name: "DanhSachQuyetToanVatTu",
                component: DanhSachQuyetToanVatTu,
                meta: {
                    title: ""
                }
            },
            {
                path: "CapVatTuCT",
                name: "CapVatTuCT",
                component: CapVatTuCT,
                meta: {
                    title: ""
                }
            },
            {
                path: "ChonVatTuCCDC",
                name: "ChonVatTuCCDC",
                component: ChonVatTuCCDC,
                meta: {
                    title: ""
                }
            },
            {
                path: "TraCuuDeNghi",
                name: "TraCuuDeNghi",
                component: TraCuuDeNghi,
                meta: {
                    title: ""
                }
            },
            {
                path: "DeNghiQuyetToanVatTu",
                name: "DeNghiQuyetToanVatTu",
                component: DeNghiQuyetToanVatTu,
                meta: {
                    title: ""
                }
            },
            {
                path: "NhapDeNghiVatTu",
                name: "NhapDeNghiVatTu",
                component: NhapDeNghiVatTu,
                meta: {
                    title: ""
                }
            },
            {
                path: "XacNhanDeNghi",
                name: "XacNhanDeNghi",
                component: XacNhanDeNghi,
                meta: {
                    title: ""
                }
            },
            {
                path: "SuaSerialGP",
                name: "SuaSerialGP",
                component: SuaSerialGP,
                meta: {
                    title: ""
                }
            },
            {
                path: "TraCuuVatTuThueBao",
                name: "popupTraCuuVatTuThueBao",
                component: popupTraCuuVatTuThueBao,
                meta: {
                    title: "",
                    dialog: true
                }
            },
            {
                path: "TraCuuThongTinDongBo",
                name: "popupTraCuuTTDongBo",
                component: popupTraCuuTTDongBo,
                meta: {
                    title: "",
                    dialog: true
                }
            },
            {
                path: "CapNhatTTThuHoi",
                name: "CapNhatTTThuHoi",
                component: CapNhatTTThuHoi,
                meta: {
                    title: ""
                }
            },
            {
                path: "DSPhieuGiao",
                name: "DSPhieuGiao",
                component: DSPhieuGiao,
                meta: {
                    title: ""
                }
            },
            {
                path: "KiemKeCCDC",
                name: "KiemKeCCDC",
                component: KiemKeCCDC,
                meta: {
                    title: ""
                }
            },
            {
                path: "DinhMuc_VT",
                name: "DinhMucVatTu",
                component: DinhMucVatTu,
                meta: {
                    title: ""
                }
            },
            {
                path: "ChuanHoaSerial",
                name: "ChuanHoaSerial",
                component: ChuanHoaSerial,
                meta: {
                    title: ""
                }
            },
            {
                path: "LoaiVT",
                name: "LoaiVT",
                component: LoaiVT,
                meta: {
                    title: ""
                }
            },
            {
                path: "ChuyenVatTuThueBao",
                name: "ChuyenVatTuThueBao",
                component: ChuyenVatTuThueBao,
                meta: {
                    title: ""
                }
            },
            {
                path: "frmXuLyYeuCau_VT",
                name: "XuLyYeuCauVatTu",
                component: XuLyYeuCauVatTu,
                meta: {
                    title: ""
                }
            },
            {
                path: "QuanLyPhieuIn",
                name: "QuanLyPhieuIn",
                component: QuanLyPhieuIn,
                meta: {
                    title: ""
                }
            },
            {
                path: "frmKhung_Gttt",
                name: "frmKhung_Gttt",
                component: frmKhung_Gttt,
                meta: {
                    title: ""
                }
            },
            {
                path: "frmCapNhat_CKS_QLVT",
                name: "frmCapNhat_CKS_QLVT",
                component: frmCapNhat_CKS_QLVT,
                meta: {
                    title: ""
                }
            },
            {
                path: "frmLoaiKho_MaKho",
                name: "LoaiKhoMaKho",
                component: LoaiKhoMaKho,
                meta: {
                    title: ""
                }
            },
            {
                path: "frmCongTrinh",
                name: "CongTrinh",
                component: CongTrinh,
                meta: {
                    title: ""
                }
            },
            {
                path: "frmCongTrinhHeThong",
                name: "frmCongTrinhHeThong",
                component: CongTrinh,
                meta: {
                    title: ""
                }
            },
            {
                path: "BoSungVatTu",
                name: "BoSungVatTu",
                component: BoSungVatTu,
                meta: {
                    title: ""
                }
            },
            {
                path: "DeNghiVT",
                name: "DeNghiVT",
                component: DeNghiVT,
                meta: {
                    title: ""
                }
            },
            {
                path: "DanhMucThietBi",
                name: "showDanhMucThietBi",
                component: showDanhMucThietBi,
                meta: {
                    title: ""
                }
            },
            {
                path: "TraCuuTonKhoTheoUser",
                name: "TraCuuTonKhoTheoUser",
                component: TraCuuTonKhoTheoUser,
                meta: {
                    title: ""
                }
            },
            {
                path: "BoSungVT_TheoFile",
                name: "BoSungVT_TheoFile",
                component: BoSungVT_TheoFile,
                meta: {
                    title: ""
                }
            },
            {
                path: "LenhDieuChuyen",
                name: "LenhDieuChuyen",
                component: LenhDieuChuyen,
                meta: {
                    title: ""
                }
            },
            {
                path: "DuyetNhuCauCapPhatKHDN",
                name: "DuyetNhuCauCapPhatKHDN",
                component: DuyetNhuCauCapPhatKHDN,
                meta: {
                    title: ""
                }
            },
            {
                path: "NhanPhieuLuanChuyen",
                name: "NhanPhieuLuanChuyen",
                component: NhanPhieuLuanChuyen,
                meta: {
                    title: ""
                }
            },
            {
                path: "BaoHanhTraCuuThongTinPhieu",
                name: "BaoHanhTraCuuThongTinPhieu",
                component: BaoHanhTraCuuThongTinPhieu,
                meta: {
                    title: ""
                }
            },
            {
                path: "TraCuuTheoSoLoPhieuPSIS",
                name: "TraCuuTheoSoLoPhieuPSIS",
                component: TraCuuTheoSoLoPhieuPSIS,
                meta: {
                    title: ""
                }
            },
            {
                path: "PhieuBaoHanhPSIS",
                name: "PhieuBaoHanhPSIS",
                component: PhieuBaoHanhPSIS,
                meta: {
                    title: ""
                }
            }

        ]
    }
];
