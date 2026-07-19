import MainLayout from '@/layouts/MainLayout'
import Home from './Home'
import TiepNhanKN from './TiepNhanKN/TiepNhanKN'
import TraPhieuKN from './TraPhieuKhieuNai'
import TraPhieuKNKHDN from './TraPhieuKhieuNaiKHDN'
import GiaoPhieuKN from './GiaoPhieuKhieuNai'
import GiaoPhieuKNKHDN from './GiaoPhieuKhieuNaiKHDN'
export default [
  {
    path: '/ext-khieunai',
    name: 'ext-khieunai',
    component: MainLayout,
    redirect: '/ext-khieunai/home',
    children: [
      {
        path: 'home',
        name: 'Khiếu nại',
        component: Home,
        meta: {
          title: 'Trang chủ: Quản lý khiếu nại'
        }
      },
      {
        path: 'tiep-nhan-khieu-nai',
        name: 'Tiếp nhận khiếu nại',
        component: TiepNhanKN,
        meta: {
          title: 'Tiếp nhận khiếu nại'
        }
      },
      {
        path: 'tiepnhan-khieunai',
        name: 'Tiếp nhận khiếu nại',
        component: () => import('./TiepNhanKhieuNai'),
        meta: {
          title: 'Tiếp nhận khiếu nại'
        }
      },
      {
        path: 'traphieu-khieunai',
        name: 'TraPhieuKN',
        component: () => import('./TraPhieuKhieuNai'),
        meta: {
          title: 'Trả phiếu khiếu nại'
        }
      },
      {
        path: 'traphieu-khieunai-khdn',
        name: 'TraPhieuKNKHDN',
        component: TraPhieuKNKHDN,
        meta: {
          title: 'Trả phiếu khiếu nại KHDN'
        }
      },
      {
        path: 'giaophieu-khieunai',
        name: 'GiaoPhieuKN',
        component: GiaoPhieuKN,
        meta: {
          title: 'Giao phiếu xử lý khiếu nại'
        }
      },
      {
        path: 'giaophieu-khieunai-khdn',
        name: 'GiaoPhieuKNKHDN',
        component: GiaoPhieuKNKHDN,
        meta: {
          title: 'Giao phiếu xử lý khiếu nại KHDN'
        }
      },

      {
        path: 'xu-ly-khieu-nai',
        name: 'xu-ly-khieu-nai',
        component: () => import('./xu-ly-khieu-nai'),
        meta: {
          title: 'Xử lý khiếu nại'
        }
      },
      {
        path: 'xuly-khieunai',
        name: 'Xử lý khiếu nại',
        component: () => import('./XuLyKhieuNai'),
        meta: {
          title: 'Xử lý khiếu nại'
        }
      },
      {
        path: 'xu-ly-khieu-nai-new',
        name: 'xu-ly-khieu-nai-new',
        component: () => import('./xu-ly-khieu-nai-new'),
        meta: {
          title: 'Xử lý khiếu nại mới'
        }
      },
      {
        path: 'nghiem-thu-khieu-nai',
        name: 'nghiem-thu-khieu-nai',
        component: () => import('./nghiem-thu-khieu-nai'),
        meta: {
          title: 'Nghiệm thu khiếu nại'
        }
      },
      {
        path: 'nghiemthu-khieunai',
        name: 'nghiemthu-khieunai',
        component: () => import('./NghiemThuKhieuNai'),
        meta: {
          title: 'Nghiệm thu khiếu nại'
        }
      },
      {
        path: 'phong-ban-xac-minh-khieu-nai',
        name: 'phong-ban-xac-minh-khieu-nai',
        component: () => import('./phong-ban-xac-minh-khieu-nai'),
        meta: {
          title: 'Phòng ban - Xác minh khiếu nại'
        }
      },
      {
        path: 'xacminh-khieunai',
        name: 'phong-ban-xac-minh-khieu-nai',
        component: () => import('./XacMinhKhieuNai'),
        meta: {
          title: 'Phòng ban - Xác minh khiếu nại'
        }
      },
      {
        path: 'tiepnhan-xm-khdn',
        name: 'tiepnhan-xm-khdn',
        component: () => import('./tiepnhan-xm-khdn'),
        meta: {
          title: 'Tiếp nhận - Xác minh khiếu nại KHDN'
        }
      },
      {
        path: 'xu-ly-khieu-nai-khdn',
        name: 'xu-ly-khieu-nai-khdn',
        component: () => import('./xu-ly-khieu-nai-khdn'),
        meta: {
          title: 'Xử lý khiếu nại KHDN'
        }
      },
      {
        path: 'xacminh-khdn',
        name: 'xacminh-khdn',
        component: () => import('./xacminh-khdn'),
        meta: {
          title: 'Xác minh khiếu nại KHDN'
        }
      },
      {
        path: 'nghiemthu-khdn',
        name: 'nghiemthu-khdn',
        component: () => import('./nghiemthu-khdn'),
        meta: {
          title: 'Nghiệm thu khiếu nại KHDN'
        }
      },
      {
        path: 'xu-ly-khieu-nai-net',
        name: 'xu-ly-khieu-nai-net',
        component: () => import('./xu-ly-khieu-nai-net'),
        meta: {
          title: 'Xử lý khiếu nại NET'
        }
      },
      {
        path: 'cauhinh-chude-ketqua',
        name: 'cauhinh-chude-ketqua',
        component: () => import('./CauHinhChuDe'),
        meta: {
          title: 'Cấu hình chủ đề nguyên nhân và phương án xử lý'
        }
      },
      {
        path: 'cauhinh-chude-donvi',
        name: 'cauhinh-chude-donvi',
        component: () => import('./CauHinhDonVi'),
        meta: {
          title: 'Cấu hình chủ đề theo đơn vị'
        }
      },
      {
        path: 'cauhinh-chude-nhanvien',
        name: 'cauhinh-chude-nhanvien',
        component: () => import('./CauHinhNhanVien'),
        meta: {
          title: 'Cấu hình chủ đề loại hình thuê bao theo nhân viên'
        }
      },
      {
        path: 'xu-ly-khieu-nai-media',
        name: 'xu-ly-khieu-nai-media',
        component: () => import('./xu-ly-khieu-nai-media'),
        meta: {
          title: 'Xử lý khiếu nại MEDIA'
        }
      },
      {
        path: 'pheduyet-giamtru-cuoc',
        name: 'PheDuyetGiamTru',
        component: () => import('./pheduyet-giamtru-cuoc'),
        meta: {
          title: 'Phê duyệt giảm trừ cước'
        }
      },
      {
        path: 'pheduyet-ketqua-thamdinh',
        name: 'PheDuyetKetQuaThamDinh',
        component: () => import('./pheduyet-ketqua-thamdinh-v2'),
        meta: {
          title: 'Phê duyệt kết quả thẩm định'
        }
      },
      {
        path: 'thamdinh-giamtru-cuoc',
        name: 'ThamDinhGiamTruCuoc',
        component: () => import('./thamdinh-giamtru-cuoc'),
        meta: {
          title: 'Thẩm định giảm trừ cước'
        }
      },
      {
        path: 'dexuat-giamtru-cuoc',
        name: 'DeXuatGiamTruCuoc',
        component: () => import('./dexuat-giamtru-cuoc'),
        meta: {
          title: 'Đề xuất giảm trừ cước'
        }
      },
      {
        path: 'xacminh-khdn-v2',
        name: 'xacminh-khdn-v2',
        component: () => import('./KHDN/xacminh-khdn'),
        meta: {
          title: 'Xác minh khiếu nại KHDN V2'
        }
      },
      {
        path: 'pheduyet-dexuat-giamtru-cuoc',
        name: 'PheDuyetDeXuatGiamTruCuoc',
        component: () => import('./pheduyet-dexuat-giamtrucuoc-v2'),
        meta: {
          title: 'Phê duyệt đề xuất giảm trừ cước'
        }
      },
      {
        path: 'danhsach-khieunai',
        name: 'danhsach-khieunai',
        component: () => import('./danhsach-khieunai'),
        meta: {
          title: 'Danh sách khiếu nại'
        }
      }
    ]
  }
]
