// middlewares
import auth from '@/middlewares/auth'
// import component
//import Group1Layout from '@/layouts/Group1Layout'
import MainLayout from '@/layouts/MainLayout'
//import Home from './Home'

// import DanhSachPhieuKhaoSat from './DanhSachPhieuKhaoSat'
import TraCuuLichSuPhanAnh from './TraCuuLichSuPhanAnh'
// import QuanLyNhanVienKhaoSat from './QuanLyNhanVienKhaoSat'
// import TraCuuThueBaoKhaoSat from './TraCuuThueBaoKhaoSat'
// import TraCuuKhachHangLon from './TraCuuKhachHangLon'
import DanhSachThueBaoKhongKhaoSat from './DanhSachThueBaoKhongKhaoSat'
import LayoutMain from '@/layouts/LayoutMain'
import GiamThinhPhieuDaKhaoSat from './GiamThinhPhieuDaKhaoSat'
import DanhSachPhieuKhaoSatOutbound from './DanhSachPhieuKhaoSatOutbound'
import QuanLyNhomInTongHop from './QuanLyNhomInTongHop'
import MoPhieuKhaoSat from './MoPhieuKhaoSat'
import PhanQuyenNhanVienNhanKS from './PhanQuyenNhanVienNhanKS'
import TraCuuKhachHangLon from './TraCuuKhachHangLon'
import ChuongTrinhCSKH from './ChuongTrinhCSKH'
import ChamSocThueBaoRoiMang from './ChamSocThueBaoRoiMang'
import DanhSachKSTB5NgayKLL from './DanhSachKSTB5NgayKLL'
import TiepNhanPhanAnhKhachHang from './TiepNhanPhanAnhKhachHang'
import TiepNhanHoTroKhachHang from './TiepNhanHoTroKhachHang'
import DanhSachKhachHangLon from './DanhSachKhachHangLon'
import ThietLapDoiTuongKhaoSat from './ThietLapDoiTuongKhaoSat'
import DanhSachKSTBMyTVKoDangNhap from './DanhSachKSTBMyTVKoDangNhap'
import DanhSachKSTBGPONSuyHaoCao from './DanhSachKSTBGPONSuyHaoCao'
import TaoCongViecCSKH from './TaoCongViecCSKH'
import GanNVChamSocKHL from './GanNVChamSocKHL'
import GanDVChamSocKHL from './GanDVChamSocKHL'
import XacNhanPhanHoiNVKT from './XacNhanPhanHoiNVKT'
import GiaoPhieuKhaoSatKhachHang from './GiaoPhieuKhaoSatKhachHang'
import TraCuuThueBaoKhaoSat from './TraCuuThueBaoKhaoSat'
import DuyetCongViecCSKH from './DuyetCongViecCSKH'
import GiaoNVCSKH from './GiaoNVCSKH'
import GiaoDVCSKH from './GiaoDVCSKH'
import HomeTraCuu from './HomeTraCuu'
import HomeQuanLyKHL from './HomeQuanLyKHL'
import HomeQuanLyChinhSachCSKH from './HomeQuanLyChinhSachCSKH'
import HomeXuLyThongTinPhanAnh from './HomeXuLyThongTinPhanAnh'
import TaoDsPhanHangKH from './TaoDsPhanHangKH'
import NhanPhieuKhaoSat from './NhanPhieuKhaoSat'
import InTongHopKHL from './InTongHopKHL'
import InTongHopKHLInTBC from './InTongHopKHLInTBC'
import ThucHienCSKH from './ThucHienCSKH'
import DSKhachHangKS from './DSKhachHangKS'
import QuanLyNhanVienKhaoSat from './QuanLyNhanVienKhaoSat'
import GiaoPhieuKhaoSatTB from "./GiaoPhieuKhaoSatTB/GiaoPhieuKhaoSatTB.vue";
import NhanPhieuKhaoSatTB from "./NhanPhieuKhaoSatTB/NhanKhaoSatTB.vue";
import ThietLapThamSoLayPhieuKhaoSat from "./ThietLapThamSoLayPhieuKhaoSat/Menu/index.vue";
import CapNhapThongTinCSKH from "./CapNhapThongTinCSKH/Menu/index.vue";
import CauHinhSMTP from "./InTongHopKHL/routeSMTP.vue";
import XuLyPhieuHoTro from './XuLyPhieuHoTro'
import GiaoPhieuHoTro from './GiaoPhieuHoTro/index'
import NghiemThuHoTro from './NghiemThuHoTro'
import XacMinhHoTro from './XacMinhHoTro'

export default [
  {
    path: '/cskh',
    name: 'cskh',
    component: MainLayout,
    meta: {
      middleware: [auth]
    },
    children: [
      /*      {
              path: 'HomeTraCuu',
              name: 'HomeTraCuu',
              component: HomeTraCuu
            },
            {
              path: 'HomeQuanLyKHL',
              name: 'HomeQuanLyKHL',
              component: HomeQuanLyKHL
            },
            {
              path: 'HomeQuanLyChinhSachCSKH',
              name: 'HomeQuanLyChinhSachCSKH',
              component: HomeQuanLyChinhSachCSKH
            },
            {
              path: 'HomeXuLyThongTinPhanAnh',
              name: 'HomeXuLyThongTinPhanAnh',
              component: HomeXuLyThongTinPhanAnh
            },
      */
      {
        path: 'TraCuuLichSuPhanAnh',
        name: 'TraCuuLichSuPhanAnh',
        component: TraCuuLichSuPhanAnh
      },
      {
        path: 'DanhSachThueBaoKhongKhaoSat',
        name: 'DanhSachThueBaoKhongKhaoSat',
        component: DanhSachThueBaoKhongKhaoSat
      },
      {
        path: 'GiamThinhPhieuDaKhaoSat',
        name: 'GiamThinhPhieuDaKhaoSat',
        component: GiamThinhPhieuDaKhaoSat
      },
      {
        path: 'DanhSachPhieuKhaoSatOutbound',
        name: 'DanhSachPhieuKhaoSatOutbound',
        component: DanhSachPhieuKhaoSatOutbound
      },
      {
        path: 'QuanLyNhomInTongHop',
        name: 'QuanLyNhomInTongHop',
        component: QuanLyNhomInTongHop
      },
      {
        path: 'MoPhieuKhaoSat',
        name: 'MoPhieuKhaoSat',
        component: MoPhieuKhaoSat
      },
      {
        path: 'PhanQuyenNhanVienNhanKS',
        name: 'PhanQuyenNhanVienNhanKS',
        component: PhanQuyenNhanVienNhanKS
      },
      {
        path: 'TraCuuKhachHangLon',
        name: 'TraCuuKhachHangLon',
        component: TraCuuKhachHangLon
      },
      {
        path: 'ChuongTrinhCSKH',
        name: 'ChuongTrinhCSKH',
        component: ChuongTrinhCSKH
      },
      {
        path: 'ChamSocThueBaoRoiMang',
        name: 'ChamSocThueBaoRoiMang',
        component: ChamSocThueBaoRoiMang
      },
      {
        path: 'DanhSachKSTB5NgayKLL',
        name: 'DanhSachKSTB5NgayKLL',
        component: DanhSachKSTB5NgayKLL
      },
      {
        path: 'TiepNhanPhanAnhKhachHang',
        name: 'TiepNhanPhanAnhKhachHang',
        component: TiepNhanPhanAnhKhachHang
      },
      {
        path: 'TiepNhanHoTroKhachHang',
        name: 'TiepNhanHoTroKhachHang',
        component: TiepNhanHoTroKhachHang
      },
      {
        path: 'DanhSachKhachHangLon',
        name: 'DanhSachKhachHangLon',
        component: DanhSachKhachHangLon
      },
      {
        path: 'ThietLapDoiTuongKhaoSat',
        name: 'ThietLapDoiTuongKhaoSat',
        component: ThietLapDoiTuongKhaoSat
      },
      {
        path: 'DanhSachKSTBMyTVKoDangNhap',
        name: 'DanhSachKSTBMyTVKoDangNhap',
        component: DanhSachKSTBMyTVKoDangNhap
      },
      {
        path: 'DanhSachKSTBGPONSuyHaoCao',
        name: 'DanhSachKSTBGPONSuyHaoCao',
        component: DanhSachKSTBGPONSuyHaoCao
      },
      {
        path: 'GanNVChamSocKHL',
        name: 'GanNVChamSocKHL',
        component: GanNVChamSocKHL
      },
      {
        path: 'GanDVChamSocKHL',
        name: 'GanDVChamSocKHL',
        component: GanDVChamSocKHL
      },
      {
        path: 'XacNhanPhanHoiNVKT',
        name: 'XacNhanPhanHoiNVKT',
        component: XacNhanPhanHoiNVKT
      },
      {
        path: 'GiaoPhieuKhaoSatKhachHang',
        name: 'GiaoPhieuKhaoSatKhachHang',
        component: GiaoPhieuKhaoSatKhachHang
      },
      {
        path: 'TraCuuThueBaoKhaoSat',
        name: 'TraCuuThueBaoKhaoSat',
        component: TraCuuThueBaoKhaoSat
      },
      {
        path: 'TaoDsPhanHangKH',
        name: 'TaoDsPhanHangKH',
        component: TaoDsPhanHangKH
      },
      /*
      {
        path: 'DanhSachPhieuKhaoSat',
        name: 'DanhSachPhieuKhaoSat',
        component: DanhSachPhieuKhaoSat
      },

      {
        path: 'QuanLyNhanVienKhaoSat',
        name: 'QuanLyNhanVienKhaoSat',
        component: QuanLyNhanVienKhaoSat
      },
      {
        path: 'TraCuuThueBaoKhaoSat',
        name: 'TraCuuThueBaoKhaoSat',
        component: TraCuuThueBaoKhaoSat
      },
      {
        path: 'TraCuuKhachHangLon',
        name: 'TraCuuKhachHangLon',
        component: TraCuuKhachHangLon
      }
      */
      {
        path: 'TaoCongViecCSKH',
        name: 'TaoCongViecCSKH',
        component: TaoCongViecCSKH
      },
      {
        path: 'DuyetCongViecCSKH',
        name: 'DuyetCongViecCSKH',
        component: DuyetCongViecCSKH
      },
      {
        path: 'GiaoNVCSKH',
        name: 'GiaoNVCSKH',
        component: GiaoNVCSKH
      },
      {
        path: 'GiaoDVCSKH',
        name: 'GiaoDVCSKH',
        component: GiaoDVCSKH
      },
      {
        path: 'NhanPhieuKhaoSat',
        name: 'NhanPhieuKhaoSat',
        component: NhanPhieuKhaoSat
      },
      {
        path: 'InTongHopKHL',
        name: 'InTongHopKHL',
        component: InTongHopKHL
      },
      {
        path: 'ThucHienCSKH',
        name: 'ThucHienCSKH',
        component: ThucHienCSKH
      },
      {
        path: 'DSKhachHangKS',
        name: 'DSKhachHangKS',
        component: DSKhachHangKS
      },
      {
        path: 'QuanLyNhanVienKhaoSat',
        name: 'QuanLyNhanVienKhaoSat',
        component: QuanLyNhanVienKhaoSat
      },
      {
        path: 'GiaoPhieuKhaoSatTB',
        name: 'GiaoPhieuKhaoSatTB',
        component: GiaoPhieuKhaoSatTB
      },
      {
        path: 'NhanPhieuKhaoSatTB',
        name: 'NhanPhieuKhaoSatTB',
        component: NhanPhieuKhaoSatTB
      },
      {
        path: 'ThietLapThamSoLayPhieuKhaoSat',
        name: 'ThietLapThamSoLayPhieuKhaoSat',
        component: ThietLapThamSoLayPhieuKhaoSat
      },
      {
        path: 'CapNhapThongTinCSKH',
        name: 'CapNhapThongTinCSKH',
        component: CapNhapThongTinCSKH
      },
      {
        path: 'CauHinhSMTP',
        name: 'CauHinhSMTP',
        component: CauHinhSMTP
      },
      {
        path: 'InTongHopKHLInTBC',
        name: 'InTongHopKHLInTBC',
        component: InTongHopKHLInTBC
      },
      {
        path: 'frmImportYCKhaoSatBC',
        name: 'frmImportYCKhaoSatBC',
        component: () => import('./frmImportYCKhaoSatBC')
      },
      {
        path: 'GiaoPhieuHoTro',
        name: 'GiaoPhieuHoTro',
        component: GiaoPhieuHoTro
      },
      {
        path: 'XuLyPhieuHoTro',
        name: 'XuLyPhieuHoTro',
        component: XuLyPhieuHoTro
      },
      {
        path: 'NghiemThuHoTro',
        name: 'NghiemThuHoTro',
        component: NghiemThuHoTro
      },
      {
        path: 'xacminh-hotro',
        name: 'XacMinhHoTro',
        component: XacMinhHoTro
      }
    ]
  }
]
