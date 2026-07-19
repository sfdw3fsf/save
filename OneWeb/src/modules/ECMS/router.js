import MainLayout from "@/layouts/MainLayout";
import Home from "./Home";
import QuanLyPrefixIPCuaBras from "./QuanLyPrefixIPCuaBras";
import CatChuyenSanTaiDslam from "./catchuyen-santai-dslam";
import QuyHoachVLAN from "./QuyHoachVLAN";
import PortChanel from "./port-chanel";
import QuyHoachVlanUpe from "./QuyHoachVlanUpe";
import LoaiThietBi from "./loai-thiet-bi";
import LoaiCard from "./LoaiCard";
import TraCuuThueBaoTheoCapKetCuoi from "./tracuu-thuebao-theo-capketcuoi";
import TraCuuChiTietThueBaoTheoFile from "./tracuu-chitiet-thuebao-theofile";
import TraCuuThongTinTrenMDF from "./tracuu-thongtin-trenmdf";
import QuanLyModule from "./components/QuanLyModule";
import TraCuuDanDoCapTheoThueBao from "./tracuu-dandocap-theothuebao";
import TraCuuDanDoCapTheoKetCuoi from "./tracuu-dandocap_theoketcuoi";
import KieuDsLam from "./KieuDsLam";
import Rack from "./Rack";
import TraCuuThueBaoTheoDSSplitter from "./TraCuuThueBaoTheoDSSplitter";
import SCVLanTheoCong from "./SCVLanTheoCong";
import QuyHoachVCID from "./QuyHoachVCID";
import HuongKetNoiNoiTinh from "./components/HuongKetNoiNoiTinh";
import TraCuuLyLichDayMay from "./tracuu-lylich-daymay";
import DoiCongTheoDanhSach from "./doicong-theodanhsach";
import DoiCongTheoDanhSachTTVT from "./doicong-theodanhsach-TTVT";
import TraCuuBanDoMangCap from "./tracuu-bando-mangcap";
import TraCuuSplitterTheoPortOLT from "./TraCuuSplitterTheoPortOLT";
import DongBoPortThueBaoTheoDslam from "./DongBoPortThueBaoTheoDslam";
import NhomDslam from "./NhomDslam";
import TraCuuChiTietCap from "./TraCuuChiTietCap";
import VciVpi from "./vci-vpi";
import HangSanXuat from "./hang-san-xuat";
import LoaiTu from "./loai-tu";
import DslamRack from "./dslam-rack";
// import testPDF from './test-pdf'
import CapNhatPort from "./capnhat-port";
import ThongSoPort from "./thongsoport/thongsoport";
import LoaiEwsd from "./loai-ewsd";
import LoaiDslam from "./loai-dslam";
import QuyHoachSCVLAN from "./QuyHoachSCVLAN";
import QuanLyBras from "./components/QuanLyBras";
import HuongKetNoiBras from "./components/HuongKetNoiBras";
import HuongKetNoiInternetTT from "./components/HuongKetNoiInternetTT";
import QuanLyThietBiBangRongTruyenDan from "./QLTB-BangRong-TruyenDan";
import QuanLyThietBiChuyenMachShelf from "./quanly-thietbi-chuyenmach-shelf";
import QuanLyThietBiChuyenMachTuRack from "./quanly-thietbi-chuyenmach-turack";
import QuanLyThietBiChuyenMachEWSD from "./quanly-thietbi-chuyenmach-ewsd";
import QuanLyThietBiChuyenMachCard from "./quanly-thietbi-chuyenmach-card";
import QuanLyThietBiChuyenMach from "./quanly-thietbi-chuyenmach";
import ChuyenTram from "./quanly-thietbi-chuyenmach/chuyentram";
import HuongKetNoiLienTinh from "./components/HuongKetNoiLienTinh";
import QuanLyThietBiBTS from "./QuanLyThietBiBTS";
import QuanLyThietBiPhuTroBTS from "./quanly-thietbi-phutro-bts";
import QuanLyThietBiPhuTroDiDong from "./quanly-thietbi-phutro-didong";
import TraCuuKetCuoiChuaPhanNhanVien from "./TraCuuKetCuoiChuaPhanNhanVien";

export default [
  {
    path: "/ecms",
    name: "ecms",
    component: MainLayout,
    redirect: "/ecms/home",
    children: [
      {
        path: "home",
        name: "ECMS home page",
        component: Home,
        meta: {
          title: "Trang chủ"
        }
      },
      // {
      //   path: 'test-pdf',
      //   name: 'testPDF',
      //   component: testPDF,
      //   meta: {
      //     title: 'testPDF'
      //   }
      // },
      {
        path: "quanly-prefix-ip-cua-bras",
        name: "QuanLyPrefixIPCuaBras",
        component: QuanLyPrefixIPCuaBras,
        meta: {
          title: "Quản lý Prefix IP của Bras"
        }
      },
      {
        path: "catchuyen-santai-dslam",
        name: "CatChuyenSanTaiDslam",
        component: CatChuyenSanTaiDslam,
        meta: {
          title: "Cắt chuyển, san tải DSLAM"
        }
      },
      {
        path: "quy-hoach-vlan",
        name: "QuyHoachVLAN",
        component: QuyHoachVLAN,
        meta: {
          title: "Quy hoạch VLAN"
        }
      },
      {
        path: "port-chanel",
        name: "PortChanel",
        component: PortChanel,
        meta: {
          title: "Danh sách port channel"
        }
      },
      {
        path: "loai-thiet-bi",
        name: "LoaiThietBi",
        component: LoaiThietBi,
        meta: {
          title: "Loại thiết bị",
          dialog: true
        }
      },
      {
        path: "quyhoach-vlan-upe",
        name: "QuyHoachVlanUpe",
        component: QuyHoachVlanUpe,
        meta: {
          title: "Quy hoạch Vlan Upe"
        }
      },
      {
        path: "loai-card",
        name: "LoaiCard",
        component: LoaiCard,
        meta: {
          title: "Loại Card"
        }
      },
      {
        path: "tracuu-thuebao-theo-capketcuoi",
        name: "TraCuuThueBaoTheoCapKetCuoi",
        component: TraCuuThueBaoTheoCapKetCuoi,
        meta: {
          title: "Tra cứu thuê bao theo cáp/kết cuối"
        }
      },
      {
        path: "tracuu-chitiet-thuebao-theofile",
        name: "TraCuuChiTietThueBaoTheoFile",
        component: TraCuuChiTietThueBaoTheoFile,
        meta: {
          title: "Tra cứu chi tiết thuê bao theo file"
        }
      },
      {
        path: "tracuu-thongtin-trenmdf",
        name: "TraCuuThongTinTrenMDF",
        component: TraCuuThongTinTrenMDF,
        meta: {
          title: "Tra cứu thuê bao - phiến dọc"
        }
      },
      {
        path: "kieu-dslam",
        name: "KieuDsLam",
        component: KieuDsLam,
        meta: {
          title: "Danh mục kiểu DSLAM",
          dialog: true
        }
      },
      {
        path: "quanly-module",
        name: "QuanLyModule",
        component: QuanLyModule,
        meta: {
          title: "Danh mục loại module",
          dialog: true
        }
      },
      {
        path: "tracuu-dandocap-theothuebao",
        name: "TraCuuDanDoCapTheoThueBao",
        component: TraCuuDanDoCapTheoThueBao,
        meta: {
          title: "Tra cứu dẫn độ cáp theo thuê bao"
        }
      },
      {
        path: "tracuu-dandocap-theoketcuoi",
        name: "TraCuuDanDoCapTheoKetCuoi",
        component: TraCuuDanDoCapTheoKetCuoi,
        meta: {
          title: "Tra cứu dẫn độ cáp theo kết cuối"
        }
      },
      {
        path: "rack",
        name: "Rack",
        component: Rack,
        meta: {
          title: "Rack"
        }
      },
      {
        path: "tracuu-thuebao-theodssplitter",
        name: "TraCuuThueBaoTheoDSSplitter",
        component: TraCuuThueBaoTheoDSSplitter,
        meta: {
          title: "Tra cứu thuê bao theo danh sách Splitter"
        }
      },
      {
        path: "scvlan-theocong",
        name: "SCVLanTheoCong",
        component: SCVLanTheoCong,
        meta: {
          title: "Fix scvlan theo cổng vật lý"
        }
      },
      {
        path: "quy-hoach-vcid",
        name: "QuyHoachVCID",
        component: QuyHoachVCID,
        meta: {
          title: "Quy hoạch vcid"
        }
      },
      {
        path: "hkn-noi-tinh",
        name: "HknNoiTinhHknNoiTinh",
        component: HuongKetNoiNoiTinh,
        meta: {
          title: "Quy Hoạch Hướng kết nối nội tỉnh"
        }
      },
      {
        path: "huong-ketnoi-lientinh",
        name: "HuongKetNoiLienTinh",
        component: HuongKetNoiLienTinh,
        meta: {
          title: "Quy Hoạch Hướng kết nối liên tỉnh"
        }
      },
      {
        path: "tracuu-lylich-daymay",
        name: "TraCuuLyLichDayMay",
        component: TraCuuLyLichDayMay,
        meta: {
          title: "Tra cứu lý lịch dây máy"
        }
      },
      {
        path: "tracuu-bando-mangcap",
        name: "TraCuuBanDoMangCap",
        component: TraCuuBanDoMangCap,
        meta: {
          title: "Tra cứu bản đồ mạng cáp",
          dialog: true
        }
      },
      {
        path: "doicong-theodanhsach",
        name: "DoiCongTheoDanhSach",
        component: DoiCongTheoDanhSach,
        meta: {
          title: "Đổi cổng theo danh sách"
        }
      },
      {
        path: "doicong-theodanhsach-ttvt",
        name: "DoiCongTheoDanhSachTTVT",
        component: DoiCongTheoDanhSachTTVT,
        meta: {
          title: "Đổi cổng theo danh sách (TTVT)"
        }
      },
      {
        path: "tracuu-splitter-theo-port-olt",
        name: "TraCuuSplitterTheoPortOLT",
        component: TraCuuSplitterTheoPortOLT,
        meta: {
          title: "Tra cứu Splitter theo port OLT"
        }
      },
      {
        path: "dong-bo-port-thue-bao-theo-dslam",
        name: "DongBoPortThueBaoTheoDslam",
        component: DongBoPortThueBaoTheoDslam,
        meta: {
          title: "Đồng bộ port thuê bao theo dslam"
        }
      },
      {
        path: "nhom-dslam",
        name: "NhomDslam",
        component: NhomDslam,
        meta: {
          title: "Nhóm dslam",
          dialog: true
        }
      },
      {
        path: "tracuu-chitiet-cap",
        name: "TraCuuChiTietCap",
        component: TraCuuChiTietCap,
        meta: {
          title: "Tra cứu chi tiết cáp"
        }
      },
      {
        path: "vci-vpi",
        name: "VciVpi",
        component: VciVpi,
        meta: {
          title: "Cập nhật Vci - Vpi",
          dialog: true
        }
      },
      {
        path: "hangsanxuat",
        name: "HangSanXuat",
        component: HangSanXuat,
        meta: {
          title: "Tra cứu danh sách thanh toán",
          dialog: true
        }
      },
      {
        path: "loaitu",
        name: "LoaiTu",
        component: LoaiTu,
        meta: {
          title: "Tra cứu danh sách thanh toán",
          dialog: true
        }
      },
      {
        path: "dslam-rack",
        name: "DslamRack",
        component: DslamRack,
        meta: {
          title: "Tra cứu danh sách thanh toán",
          dialog: true
        }
      },
      {
        path: "capnhat-port",
        name: "CapNhatPort",
        component: CapNhatPort,
        meta: {
          title: "Cập nhật DSLAM VDC CHO PORT"
        }
      },
      {
        path: "thong-so-port",
        name: "ThongSoPort",
        component: ThongSoPort,
        meta: {
          title: "THÔNG SỐ PORT"
        }
      },
      {
        path: "loai-ewsd",
        name: "LoaiEwsd",
        component: LoaiEwsd,
        meta: {
          title: "Loại thiết bị EWSD",
          dialog: true
        }
      },
      {
        path: "loai-dslam",
        name: "LoaiDslam",
        component: LoaiDslam,
        meta: {
          title: "Danh mục loại dslam",
          dialog: true
        }
      },
      {
        path: "quy-hoach-qinq-vpn-noitinh",
        name: "Quy hoạch QinQ VPN Nội tỉnh",
        component: QuyHoachSCVLAN
        // meta: {
        //   title: 'Quy hoạch SCVLAN Liên Tỉnh'
        // }
      },
      {
        path: "quy-hoach-qinq-vpn-lientinh",
        name: "Quy hoạch QinQ VPN Liên tỉnh",
        component: QuyHoachSCVLAN
        // meta: {
        //   title: 'Quy hoạch SCVLAN Liên Tỉnh'
        // }
      },
      {
        path: "quy-hoach-qinq-internet-tructiep",
        name: "Quy hoạch QinQ Internet Trực tiếp",
        component: QuyHoachSCVLAN
        // meta: {
        //   title: 'Quy hoạch SCVLAN Trực tiếp'
        // }
      },
      {
        path: "quy-hoach-scvlan-fiber",
        name: "QuyHoachSCVLANFiber",
        component: QuyHoachSCVLAN,
        meta: {
          title: "Quy hoạch SCVLAN Fiber"
        }
      },
      {
        path: 'quan-ly-bras',
        name: 'QuanLyBras',
        component: QuanLyBras,
        meta: {
          title: 'Cập nhật Bras',
          dialog: true
        }
      },
      {
        path: "hkn-bras",
        name: "HuongKetNoiBras",
        component: HuongKetNoiBras,
        meta: {
          title: "Quy hoạch hướng kết nối bras"
        }
      },
      {
        path: "hkn-internet-tt",
        name: "HuongKetNoiInternetTT",
        component: HuongKetNoiInternetTT,
        meta: {
          title: "Quy hoạch hướng kết nối internet trực tiếp"
        }
      },
      {
        path: "quanly-thietbi-bangrong-truyendan",
        name: "QuanLyThietBiBangRongTruyenDan",
        component: QuanLyThietBiBangRongTruyenDan,
        meta: {
          title: "QUẢN LÝ THIẾT BỊ BĂNG RỘNG - TRUYỀN DẪN"
        }
      },
      {
        path: "chuyentram-dslam-swtich-gpon",
        name: "ChuyenTram",
        component: ChuyenTram,
        meta: {
          title: "Chuyển trạm cho thiết bị dslam, switch, gpon"
        }
      },
      {
        path: "quanly-thietbi-chuyenmach-turack",
        name: "QuanLyThietBiChuyenMachTuRack",
        component: QuanLyThietBiChuyenMachTuRack,
        meta: {
          title: "Quản lý thiết bị chuyển mạch - tủ rack"
        }
      },
      {
        path: "quanly-thietbi-chuyenmach-shelf",
        name: "QuanLyThietBiChuyenMachShelf",
        component: QuanLyThietBiChuyenMachShelf,
        meta: {
          title: "Quản lý thiết bị chuyển mạch - shelf"
        }
      },
      {
        path: "quanly-thietbi-chuyenmach-card",
        name: "QuanLyThietBiChuyenMachCard",
        component: QuanLyThietBiChuyenMachCard,
        meta: {
          title: "Quản lý thiết bị chuyển mạch - card"
        }
      },
      {
        path: "quanly-thietbi-chuyenmach-ewsd",
        name: "QuanLyThietBiChuyenMachEWSD",
        component: QuanLyThietBiChuyenMachEWSD,
        meta: {
          title: "Quản lý thiết bị chuyển mạch - EWSD"
        }
      },
      {
        path: "quanly-thietbi-chuyenmach",
        name: "QuanLyThietBiChuyenMach",
        component: QuanLyThietBiChuyenMach,
        meta: {
          title: "Quản lý thiết bị chuyển mạch"
        }
      },
      {
        path: "quanly-thietbi-bts",
        name: "QuanLyThietBiBTS",
        component: QuanLyThietBiBTS,
        meta: {
          title: "Quản lý thiết bị BTS, NODEB, ENODEB"
        }
      },
      {
        path: "quanly-thietbi-phutro-bts",
        name: "QuanLyThietBiPhuTroBTS",
        component: QuanLyThietBiPhuTroBTS,
        meta: {
          title: "Quản lý thiết bị phụ trợ và BTS - TTĐH"
        }
      },
      {
        path: "quanly-thietbi-phutro-didong",
        name: "QuanLyThietBiPhuTroDiDong",
        component: QuanLyThietBiPhuTroDiDong,
        meta: {
          title: "Quản lý thiết bị phụ trợ và di động"
        }
      },
      {
        path: "tracuu-ketcuoi-chuaphan-nhanvien",
        name: "TraCuuKetCuoiChuaPhanNhanVien",
        component: TraCuuKetCuoiChuaPhanNhanVien,
        meta: {
          title: "Tra cứu kết cuối chưa phân nhân viên"
        }
      },
    ]
  }
];
