import MainLayout from '@/layouts/MainLayout'
import Home from './Home'
import DongBoVisaVasc from './DongBoVisaVasc'
import DongBoVisaVascUpdate from './DongBoVisaVascUpdate'
import InHopDong from './InHopDong/InHopDong.vue'
import QuanLyIssue from './QuanLyIssue'
import ThayDoiThongTinHopDong from './ThayDoiThongTinHopDong'
import ChuanHoaDLKhachHang from './ChuanHoaDLKhachHang'
import HTHoSoChuyenQuyen from './HoanThienHoSoChuyenQuyen'
import TraCuuHTHoSoChuyenQuyen from './HoanThienHoSoChuyenQuyen'
import DichChuyen from './DichChuyenNew'
import ThayDoiThongTinKhuyenMai from './ThayDoiThongTinKhuyenMai'
import ThayDoiThongTinKhachHang from './ThayDoiThongTinKhachHang/ThayDoiThongTinKhachHang.vue'
import ThayDoiKhuyenMaiDatCoc from './ThayDoiKhuyenMaiDatCoc'
import HoanThienHoSoTachNhapThueBao from './HoanThienHoSoTachNhapThueBao'
import DoiSim from './DoiSim'
import ThayDoiDatCoc from './ThayDoiDatCoc'
import ThayDoiDatCocHuy from './frmHuyDatCoc'
import CapNhatThueBaoDatCoc from './CapNhatThueBaoDatCoc'
import HoanThienHoSoDoiSim from './HoanThienHoSoDoiSim'
import ChuyenDichBC from './ChuyenDichBC'
export default [
  {
    path: '/ext-hopdong',
    name: 'ext-hopdong',
    component: MainLayout,
    redirect: '/ext-hopdong/home',
    children: [
      {
        path: 'home',
        name: 'Hợp đồng',
        component: Home,
        meta: {
          title: 'Trang chủ: Quản lý hợp đồng'
        }
      },
      {
        path: 'dongbo-visa-vasc',
        name: 'Đồng bộ dữ liệu thuê bao internet trên visa/vasc',
        component: DongBoVisaVasc,
        meta: {
          title: 'Đồng bộ dữ liệu thuê bao internet trên visa/vasc'
        }
      },
      {
        path: 'dongbo-visa-vasc-update',
        name: 'Đồng bộ dữ liệu thuê bao internet trên visa/vasc update',
        component: DongBoVisaVascUpdate,
        meta: {
          title: 'Đồng bộ dữ liệu thuê bao internet trên visa/vasc update'
        }
      },
      {
        path: 'inhopdong',
        name: 'InHopDong',
        component: InHopDong,
        meta: {
          title: 'IN HỢP ĐỒNG/PHỤ LỤC'
        }
      },
      {
        path: 'quan-ly-issue',
        name: 'QuanLyIssue',
        component: QuanLyIssue,
        meta: {
          title: 'Quản lý issue'
        }
      },
      {
        path: 'ht-hoso-chuyenquyen',
        name: 'HTHoSoChuyenQuyen',
        component: HTHoSoChuyenQuyen,
        meta: {
          title: 'Hoàn thiện hồ sơ chuyển quyền'
        }
      },
      {
        path: 'tracuu-hoso-chuyenquyen',
        name: 'TraCuuHTHoSoChuyenQuyen',
        component: TraCuuHTHoSoChuyenQuyen,
        meta: {
          title: 'TRA CỨU THÔNG TIN HỢP ĐỒNG CHUYỂN QUYỀN'
        }
      },
      {
        path: 'dichchuyen',
        name: 'DichChuyen',
        component: DichChuyen,
        meta: {
          title: 'Dịch chuyển'
        }
      },
      {
        path: 'thay-doi-thong-tin-hop-dong',
        name: 'ThayDoiThongTinHopDong',
        component: ThayDoiThongTinHopDong,
        meta: {
          title: 'Thay đổi thông tin hợp đồng'
        }
      },
      {
        path: 'chuan-hoa-du-lieu-khach-hang',
        name: 'ChuanHoaDLKhachHang',
        component: ChuanHoaDLKhachHang,
        meta: {
          title: 'Chuẩn hóa dữ liệu khách hàng'
        }
      },
      {
        path: 'thaydoi-thongtin-khuyenmai',
        name: 'ThayDoiThongTinKhuyenMai',
        component: ThayDoiThongTinKhuyenMai,
        meta: {
          title: 'Thay đổi thông tin khuyến mãi'
        }
      },
      {
        path: 'thaydoi-thongtin-khachhang',
        name: 'ThayDoiThongTinKhachHang',
        component: ThayDoiThongTinKhachHang,
        meta: {
          title: 'Thay đổi thông tin khách hàng'
        }
      },
      {
        path: 'thaydoi-thongtin-khuyenmai-datcoc',
        name: 'ThayDoiKhuyenMaiDatCoc',
        component: ThayDoiKhuyenMaiDatCoc,
        meta: {
          title: 'Thay đổi thông tin khuyến mãi đặt cọc'
        }
      },
      {
        path: 'doi-sim',
        name: 'DoiSim',
        component: DoiSim,
        meta: {
          title: 'Thay đổi số sim'
        }
      },
      {
        path: 'hoanthien-hoso-tachnhap-tbao',
        name: 'HoanThienHoSoTachNhapThueBao',
        component: HoanThienHoSoTachNhapThueBao,
        meta: {
          title: 'Hoàn thiện hồ sơ Tách, nhập thuê bao'
        }
      },
      {
        path: 'thaydoi-datcoc',
        name: 'ThayDoiDatCoc',
        component: ThayDoiDatCoc,
        meta: {
          title: 'Thay đổi đặt cọc, trả trước'
        }
      },
{
        path: 'thaydoi-datcoc-v2',
        name: 'ThayDoiDatCoc',
        component: () => import('./ThayDoiDatCocV2'),
        meta: {
          title: 'Thay đổi đặt cọc, trả trước'
        }
      },
      {
        path: 'Cap-Nhat-Thue-Bao-Dat-Coc',
        name: 'Cap-Nhat-Thue-Bao-Dat-Coc',
        component: CapNhatThueBaoDatCoc,
        meta: {
          title: 'Đặt cọc trả trước'
        }
      },
      {
        path: 'hoanthien-hoso-doisim',
        name: 'HoanThienHoSoDoiSim',
        component: HoanThienHoSoDoiSim,
        meta: {
          title: 'Hoàn thiện hồ sơ đổi sim'
        }
      },
      {
        path: 'huy-dat-coc',
        name: 'ThayDoiDatCocHuy',
        component: ThayDoiDatCocHuy,
        meta: {
          title: 'Thay đổi đặt cọc, trả trước'
        }
      },
      {
        path: 'chuyen-dich-bancheo',
        name: 'ChuyenDichBC',
        component: ChuyenDichBC,
        meta: {
          title: 'Chuyển dịch thuê bao (Bán chéo)'
        }
      },
	{
        path: 'dichchuyenv2',
        name: 'DichChuyenV2',
        component: () => import('./DichChuyenV2'),
        meta: {
          title: 'Dịch chuyển'
        }
      },
	{
        path: 'huy-dat-coc-v2',
        name: 'ThayDoiDatCocHuyNew',
        component: () => import('./HuyDatCoc2'),
        meta: {
          title: 'Thay đổi đặt cọc, trả trước'
        }
      },
	{
        path: 'Cap-Nhat-Thue-Bao-Dat-Coc-v2',
        name: 'CapNhatThueBaoDatCoc2',
        component: () => import('./CapNhatThueBaoDatCoc2'),
        meta: {
          title: 'Đặt cọc trả trước'
        }
      },
    ]
  }
]
