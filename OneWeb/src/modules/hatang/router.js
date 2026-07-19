import Vue from 'vue'
import Router from 'vue-router'
import menu from '../../utils/menu'

// layouts
import BlankLayout from '@/layouts/BlankLayout'
import MainLayout from '@/layouts/MainLayout'
// public page
import Login from '@/modules/AUTH/Login'

const checkAuth = (to, from, next) => {
  console.log('Hatang route guard - Routing from:', from.fullPath, 'to:', to.fullPath)
  let path = to.fullPath
  if (path != '/auth/login') sessionStorage.setItem('currentPath', path)

  if (!menu.checkExists(to.fullPath)) {
    if (menu.checkExists(to.path)) {
      path = to.path
    }
  }

  if (to.meta.requiresAuth !== false) {
    var menuitems = Vue.auth.getMenuFromStorage()

    // Check both fullPath and matched route path for parameterized routes
    const pathsToCheck = [
      to.fullPath.toLowerCase(),
      '/#' + to.fullPath.toLowerCase(),
      '/#/' + to.fullPath.toLowerCase()
    ]

    // Add the route path pattern for parameterized routes (e.g., /ha-tang/danhmuc-controller-tudia/:id)
    if (to.matched && to.matched.length > 0) {
      const routePath = to.matched[to.matched.length - 1].path
      if (routePath && routePath.includes(':')) {
        // This is a parameterized route, construct the full pattern path
        let parentPath = ''
        if (to.matched.length > 1) {
          parentPath = to.matched[to.matched.length - 2].path
        }

        const fullRoutePattern = parentPath + '/' + routePath
        const cleanPattern = fullRoutePattern.replace(/^\/+/, '/').replace(/\/+/g, '/')

        pathsToCheck.push(cleanPattern.toLowerCase())
        pathsToCheck.push('/#' + cleanPattern.toLowerCase())
        pathsToCheck.push('/#/' + cleanPattern.toLowerCase())

        // Also add without leading slash for compatibility
        const noSlashPattern = cleanPattern.replace(/^\//, '')
        pathsToCheck.push(noSlashPattern.toLowerCase())
        pathsToCheck.push('/#' + noSlashPattern.toLowerCase())
        pathsToCheck.push('/#/' + noSlashPattern.toLowerCase())

        // For debugging: also try with just the route pattern without parent path
        pathsToCheck.push(routePath.toLowerCase())
        pathsToCheck.push('/#' + routePath.toLowerCase())
        pathsToCheck.push('/#/' + routePath.toLowerCase())
      }
    }

    if (menuitems && menuitems.length > 0 && to.fullPath && !['/', '/#', '/#/'].includes(to.fullPath)) {
      // // Debug: Log all the paths we're checking and menu URLs
      // console.info('=== DEBUG ROUTE ACCESS ===');
      // console.info('Current route:', to.fullPath);
      // console.info('Paths being checked:', pathsToCheck);
      // console.info('Menu items URLs:', menuitems.map(x => x.url).filter(url => url));
      // // Try different matching strategies
      // const foundMatch = menuitems.find(x => {
      //   if (!x.url) return false;
      //   const menuUrl = decodeURIComponent(x.url.toLowerCase());
      //   return pathsToCheck.includes(menuUrl);
      // });
      // if (!foundMatch) {
      //   console.info('Bạn không có quyền truy cập chức năng này! Vui lòng liên hệ admin.', to.fullPath);
      //   // Show toast message
      //   Vue.$toast.error('Bạn không có quyền truy cập chức năng này! Vui lòng liên hệ admin.', to.fullPath);
      //   // Redirect to login or home page
      //   menu.setCurrentMenuItem(0);
      //   menu.setCurrentPath('');
      //   sessionStorage.removeItem('currentPath');
      //   next({ name: 'Login' });
      //   return;
      // }
    }

    var expirationDate = new Date(0)
    expirationDate.setUTCSeconds(Vue.auth.getProperty('exp'))
    var currentTime = new Date().getTime() / 1000

    if (expirationDate && expirationDate > 0 && expirationDate < currentTime) {
      menu.setCurrentMenuItem(0)
      menu.setCurrentPath('')
      Vue.$toast.error('Phiên đăng nhập hết hạn! Vui lòng đăng nhập lại.')
      next({ name: 'Login' })
    } else if (Vue.auth.isAuthenticated()) {
      menu.setCurrentMenuItemFromRouter(path)
      next()
    } else {
      menu.setCurrentMenuItem(0)
      menu.setCurrentPath('')
      next({ name: 'Login' })
    }
  } else {
    menu.setCurrentMenuItemFromRouter(path)
    next()
  }
}

const addAuthGuard = (routes) => {
  return routes.map((route) => {
    // Thêm beforeEnter nếu chưa có
    if (!route.beforeEnter) {
      route.beforeEnter = checkAuth
    }

    // Nếu có children, đệ quy thêm guard
    if (route.children && route.children.length > 0) {
      route.children = addAuthGuard(route.children)
    }

    return route
  })
}

const baseRoutes = [
  {
    path: '/ha-tang',
    name: 'hatang',
    component: MainLayout,
    redirect: '/ha-tang/home',
    children: [
      {
        path: 'home',
        name: 'Quản lý hạ tầng',
        component: () => import('./Home/index.vue'),
        meta: {
          title: 'Trang chủ'
        }
      },
      {
        path: 'danhmuc-tinhtrang-ip',
        component: () => import('./DanhMucTinhTrangIP')
      },
      {
        path: 'hang-sanxuat',
        component: () => import('./HangSanXuat')
      },
      {
        path: 'nha-cungcap',
        component: () => import('./NhaCungCap')
      },
      {
        path: 'thong-tin-module',
        component: () => import('./ThongTinModule'),
        meta: {
          title: 'Thông tin module'
        }
      },
      {
        name: 'LoaiIP',
        path: 'loai-ip',
        component: () => import('./LoaiIP'),
        meta: {
          title: 'Danh mục loại IP'
        }
      },
      {
        name: 'PhanLoaiIP',
        path: 'phanloai-ip',
        component: () => import('./PhanLoaiIP'),
        meta: {
          title: 'Danh mục phân loại IP'
        }
      },
      {
        path: 'loai-tu-dia',
        component: () => import('./LoaiTuDia')
      },
      {
        path: 'dichvu-iops',
        component: () => import('./dichvu-iops/index.vue'),
        meta: {
          title: 'Danh mục gói dịch vụ IOPS'
        }
      },
      {
        path: 'loai-cong',
        component: () => import('./loai-cong/index.vue'),
        meta: {
          title: 'Danh mục Loại cổng'
        }
      },
      {
        path: 'danhmuc-goidichvu-throughput',
        component: () => import('./DanhMucGoiDichVuThroughPut')
      },
      {
        path: 'danhmuc-phanloai-hatang',
        component: () => import('./DanhMucPhanLoaiHaTang')
      },
      {
        path: 'danhmuc-trangthai-mayao',
        component: () => import('./DanhMucTrangThaiMayAo'),
        meta: {
          title: 'Danh mục trạng thái máy ảo'
        }
      },
      {
        path: 'danhmuc-capdo-hatang',
        component: () => import('./DanhMucCapDoHaTang'),
        meta: {
          title: 'Danh mục cấp độ hạ tầng'
        }
      },
      {
        path: 'danhmuc-congnghe-hatang',
        component: () => import('./DanhMucCongNgheHaTang')
      },
      {
        path: 'trang-thai-tai-nguyen',
        component: () => import('./TrangThaiTaiNguyen')
      },
      {
        path: 'phan-loai-ha-tang-tinh-toan',
        component: () => import('./PhanLoaiHaTang')
      },
      {
        path: 'quy-hoach-subnet',
        component: () => import('./QuyHoach-Subnet'),
        meta: {
          title: 'Quy hoạch subnet'
        }
      },
      {
        path: 'giai-phap-backup',
        component: () => import('./GiaiPhap-Backup')
      },
      {
        path: 'don-gia-ha-tang',
        component: () => import('./dongia-hatang')
      },
      {
        path: 'quan-ly-zone',
        component: () => import('./quanly-zone')
      },
      {
        path: 'kho-ha-tang-mang',
        component: () => import('./kho-hatang-mang')
      },

      {
        path: 'danhmuc-bangthong-cong',
        component: () => import('./DanhMucBangThongCong/index.vue'),
        meta: {
          title: 'Danh mục băng thông cổng'
        }
      },
      {
        path: 'danhmuc-mucdich-sudung-cong',
        component: () => import('./DanhMucMucDichSuDungCong/index.vue'),
        meta: {
          title: 'Danh mục mục đích sử dụng cổng'
        }
      },
      {
        path: 'danhmuc-loai-backup',
        component: () => import('./DanhMucLoaiBackup'),
        meta: {
          title: 'Danh mục loại backup'
        }
      },
      {
        path: 'danhmuc-tinhtrang-hoatdong',
        component: () => import('./DanhMucTinhTrangHoatDong'),
        meta: {
          title: 'Danh mục tình trạng hoạt động'
        }
      },
      {
        path: 'danhmuc-he-dieuhanh',
        component: () => import('./DanhMucHeDieuHanh'),
        meta: {
          title: 'Danh mục hệ điều hành'
        }
      },
      {
        path: 'doituong-capphat-ip',
        component: () => import('./DoiTuongCapPhatIP/index.vue'),
        meta: {
          title: 'Danh mục đối tượng cấp phát IP'
        }
      },
      {
        path: 'ip-address',
        component: () => import('./DanhMucDiaChiIPAdress/index.vue'),
        meta: {
          title: 'Quản lý IP address'
        }
      },
      {
        path: 'subnet-mask',
        component: () => import('./SubnetMask'),
        meta: {
          title: 'Danh mục Subnet Mask'
        }
      },
      {
        path: 'danhmuc-loai-vaitro',
        component: () => import('./DanhMucLoaiVaiTro'),
        meta: {
          title: 'Danh mục loại vai trò'
        }
      },
      {
        path: 'khohatang-idg',
        component: () => import('./IDG')
      },
      {
        path: 'quanly-vrf',
        component: () => import('./VRF/index.vue'),
        meta: {
          title: 'Quản lý VRF'
        }
      },
      {
        path: 'danh-muc-loai-thiet-bi',
        component: () => import('./DanhMucLoaiThietBi'),
        meta: {
          title: 'Danh mục loại thiết bị'
        }
      },
      {
        path: 'kho-hatang-backup',
        component: () => import('./KhoHaTangBackup/index.vue'),
        meta: {
          title: 'Kho hạ tầng Backup'
        }
      },
      {
        path: 'demo-chon-dv-nv',
        component: () => import('./ChonDonViNhanVien/demoChonDonViNhanVien.vue'),
        meta: {
          title: 'Chọn đơn vị và nhân viên'
        }
      },
      {
        path: 'kho-hatang-smart-cloud',
        component: () => import('./KhoHaTangSmartCloud/index.vue'),
        meta: {
          title: 'Kho hạ tầng SmartCloud'
        }
      },
      {
        path: 'vlan',
        component: () => import('./Vlan/index.vue'),
        meta: {
          title: 'Quản lý VLAN'
        }
      },
      {
        path: 'loaivatlieu-ketnoimang',
        component: () => import('./LoaiVatLieuKetNoiMang')
      },
      {
        path: 'tu-dung-nfs',
        component: () => import('./LuuTruTuDungNFS/index.vue'),
        meta: {
          title: 'Hạ tầng lưu trữ tự dựng NFS'
        }
      },
      {
        path: 'tai-nguyen-backup',
        component: () => import('./TaiNguyenBackup/index.vue'),
        meta: {
          title: 'Tài nguyên Backup'
        }
      },
      {
        path: 'sodo-kho-tainguyen',
        component: () => import('./sodo-kho-tainguyen/index.vue'),
        meta: {
          title: 'Sơ đồ logic kho tài nguyên ảo'
        }
      },
      {
        path: 'baocao-hieunang-thang',
        component: () => import('./baocao-hieunang-thang/index.vue'),
        meta: {
          title: 'Báo cáo tài nguyên tháng'
        }
      },
      {
        path: 'insert-hieunang-thang',
        component: () => import('./insert-hieunang-thang/index.vue'),
        meta: {
          title: 'Nhập dữ liệu tài nguyên tháng'
        }
      },
      {
        path: 'ip-store',
        component: () => import('./ipStoreV2/index.vue'),
        meta: {
          title: 'Quản lý IP Store'
        }
      },
      {
        path: 'khay-dia-trong-tu-dia/:id',
        component: () => import('./KhayDiaTrongTuDia/index.vue'),
        meta: {
          title: 'Thông tin khay đĩa trong tủ đĩa'
        }
      },
      {
        path: 'thu-hoi-ip',
        component: () => import('./ThuHoiIP'),
        meta: {
          title: 'Thực hiện thu hồi IP'
        }
      },
      {
        path: 'thu-hoi-backup',
        component: () => import('./ThuHoiBackup'),
        meta: {
          title: 'Thực hiện thu hồi backup'
        }
      },
      {
        path: 'cap-phat-backup',
        component: () => import('./CapPhatBackup'),
        meta: {
          title: 'Cấp phát hạ tầng backup'
        }
      },
      {
        path: 'danhmuc-chungloai-thietbi',
        component: () => import('./DanhMucChungLoaiThietBi/index.vue'),
        meta: {
          title: 'Chủng loại thiết bị'
        }
      },
      {
        path: 'tai-nguyen-nfs',
        component: () => import('./tainguyen-nfs/index.vue'),
        meta: {
          title: 'Tài nguyên NFS'
        }
      },
      {
        path: 'so-do-muc-thiet-bi',
        component: () => import('./SoDoMucThietBi'),
        meta: {
          title: 'Sơ đồ logic mức thiết bị'
        }
      },

      {
        path: 'chi-tiet-cong-thiet-bi/:id',
        component: () => import('./ChiTietCongThietBiTheoChungLoai/index.vue'),
        meta: {
          title: 'Chi tiết cổng thiết bị - sinh ra từ khai báo chủng loại'
        }
      },
      {
        path: 'kho-hatang-aohoa',
        component: () => import('./KhoHaTangAoHoa/index.vue'),
        meta: {
          title: 'Kho hạ tầng Ảo Hoá'
        }
      },
      {
        path: 'yeu-cau-thu-hoi-tai-nguyen',
        component: () => import('./yeucau-thuhoi-tainguyen/index.vue'),
        meta: {
          title: 'Yêu cầu thu hồi tài nguyên'
        }
      },
      {
        path: 'chi-tiet-cong-thiet-bi-thiet-bi/:id/:deviceid',
        component: () => import('./ChiTietCongThietBi_ThietBi/index.vue'),
        meta: {
          title: 'Chi tiết cổng thiết bị - Cổng sinh ra từ khai báo thiết bị'
        }
      },
      // {
      //   path: 'danhmuc-chungloai-thietbi',
      //   name: 'DanhMucChungLoaiThietBi',
      //   component: () => import('./DanhMucChungLoaiThietBi/index.vue'),
      //   props: true,
      //   meta: {
      //     title: 'Chủng loại thiết bị'
      //   }
      // },
      {
        path: 'may-ao',
        component: () => import('./may-ao'),
        meta: {
          title: 'Máy ảo'
        }
      },
      {
        path: 'thuchien-toiuu-tainguyen',
        component: () => import('./ThucHienToiUuTaiNguyen/index.vue'),
        meta: {
          title: 'Thực hiện tối ưu tài nguyên tăng'
        }
      },
      {
        path: 'thuchien-toiuu-tainguyen-giam',
        component: () => import('./ThucHienToiUuTaiNguyenGiam/index.vue'),
        meta: {
          title: 'Thực hiện tối ưu tài nguyên giảm'
        }
      },
      {
        path: 'xacnhan-toiuu-tainguyen-tang',
        component: () => import('./xacnhan-toiuu-tainguyen/tang/index.vue'),
        meta: {
          title: 'Xác nhận tối ưu tài nguyên tăng'
        }
      },
      {
        path: 'xacnhan-toiuu-tainguyen-giam',
        component: () => import('./xacnhan-toiuu-tainguyen/giam/index.vue'),
        meta: {
          title: 'Xác nhận tối ưu tài nguyên giảm'
        }
      },
      {
        path: 'capphat-ip',
        component: () => import('./CapPhatIP'),
        meta: {
          title: 'Cấp phát IP Address'
        }
      },
      {
        path: 'xacnhan-thuhoi-tainguyen',
        component: () => import('./xacnhan-thuhoi-tainguyen'),
        meta: {
          title: 'Xác nhận thu hồi'
        }
      },
      {
        path: 'cap-phat-tai-nguyen',
        component: () => import('./CapPhatTaiNguyen'),
        meta: {
          title: 'Cấp phát tài nguyên'
        }
      },
      {
        path: 'ds-thietbi-server',
        component: () => import('./ThongTinThietBiV3/wrappers/ServerDevice.vue'),
        meta: {
          title: 'Danh sách thiết bị - Server'
        }
      },
      {
        path: 'thietbi-server/:id?',
        component: () => import('./ThongTinThietBiV3/wrappers/ServerDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Server'
        }
      },
      {
        path: 'ds-thietbi-network',
        component: () => import('./ThongTinThietBiV3/wrappers/NetworkDevice.vue'),
        meta: {
          title: 'Danh sách thiết bị - Network'
        }
      },
      {
        path: 'thietbi-network/:id?',
        component: () => import('./ThongTinThietBiV3/wrappers/NetworkDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Network'
        }
      },
      {
        path: 'ds-thietbi-blade',
        component: () => import('./ThongTinThietBiV3/wrappers/BladeDevice.vue'),
        meta: {
          title: 'Danh sách thiết bị - Blade'
        }
      },
      {
        path: 'thietbi-blade/:id?',
        component: () => import('./ThongTinThietBiV3/wrappers/BladeDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Blade'
        }
      },
      {
        path: 'ds-thietbi-server-blade',
        component: () => import('./ThongTinThietBiV3/wrappers/ServerBladeDevice.vue'),
        meta: {
          title: 'Danh sách thiết bị - Server Blade'
        }
      },
      {
        path: 'thietbi-server-blade/:id?',
        component: () => import('./ThongTinThietBiV3/wrappers/ServerBladeDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Server Blade'
        }
      },
      {
        path: 'ds-thietbi-tudia',
        component: () => import('./ThongTinThietBiV3/wrappers/TuDiaDevice.vue'),
        meta: {
          title: 'Danh sách thiết bị - Tủ đĩa'
        }
      },
      {
        path: 'thietbi-tudia/:id?',
        component: () => import('./ThongTinThietBiV3/wrappers/TuDiaDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Tủ đĩa'
        }
      },
      {
        path: 'ds-thietbi-bo-dk',
        component: () => import('./ThongTinThietBiV3/wrappers/BoDieuKhienDevice.vue'),
        meta: {
          title: 'Danh sách thiết bị - Bộ điều khiển'
        }
      },
      {
        path: 'thietbi-bo-dk/:id?',
        component: () => import('./ThongTinThietBiV3/wrappers/BoDieuKhienDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Bộ điều khiển'
        }
      },
      {
        path: 'ds-thietbi-khay-dia',
        component: () => import('./ThongTinThietBiV3/wrappers/KhayDiaDevice.vue'),
        meta: {
          title: 'Danh sách thiết bị - Khay đĩa'
        }
      },
      {
        path: 'thietbi-khay-dia/:id?',
        component: () => import('./ThongTinThietBiV3/wrappers/KhayDiaDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Khay đĩa'
        }
      },
      // {
      //   path: 'ds-thietbi-linecard',
      //   component: () => import('./ThongTinThietBiV3/wrappers/LinecardDevice.vue'),
      //   meta: {
      //     title: 'Danh sách thiết bị - Linecard'
      //   }
      // },
      // {
      //   path: 'thietbi-linecard/:id?',
      //   component: () => import('./ThongTinThietBiV3/wrappers/LinecardDevice.vue'),
      //   meta: {
      //     title: 'Thông tin thiết bị - Linecard'
      //   }
      // },
      // {
      //   path: 'ds-thietbi-powercard',
      //   component: () => import('./ThongTinThietBiV3/wrappers/PowercardDevice.vue'),
      //   meta: {
      //     title: 'Danh sách thiết bị - Powercard'
      //   }
      // },
      // {
      //   path: 'thietbi-powercard/:id?',
      //   component: () => import('./ThongTinThietBiV3/wrappers/PowercardDevice.vue'),
      //   meta: {
      //     title: 'Thông tin thiết bị - Powercard'
      //   }
      // },
      // {
      //   path: 'ds-thietbi-consolecard',
      //   component: () => import('./ThongTinThietBiV3/wrappers/ConsolecardDevice.vue'),
      //   meta: {
      //     title: 'Danh sách thiết bị - Consolecard'
      //   }
      // },
      // {
      //   path: 'thietbi-consolecard/:id?',
      //   component: () => import('./ThongTinThietBiV3/wrappers/ConsolecardDevice.vue'),
      //   meta: {
      //     title: 'Thông tin thiết bị - Consolecard'
      //   }
      // },
      {
        path: 'ds-thietbi-card',
        component: () => import('./ThongTinThietBiV3/wrappers/CardDevice.vue'),
        meta: {
          title: 'Danh sách thiết bị - Card'
        }
      },
      {
        path: 'thietbi-card/:id?',
        component: () => import('./ThongTinThietBiV3/wrappers/CardDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Card'
        }
      },
      {
        path: 'ds-thietbi-tu-phan-phoi',
        component: () => import('./Physicalequpment/index.vue'),
        meta: {
          title: 'Quản lý thiết bị - Tủ phân phối'
        }
      },
      {
        path: 'thietbi-tu-phan-phoi/:id?',
        component: () => import('./Physicalequpment/wrappers/TuPhanPhoiDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Tủ phân phối'
        }
      },
      {
        path: 'ds-thietbi-rpdu',
        component: () => import('./Physicalequpment/index.vue'),
        meta: {
          title: 'Quản lý thiết bị - rPDU'
        }
      },
      {
        path: 'thietbi-rpdu/:id?',
        component: () => import('./Physicalequpment/wrappers/RPDUDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - rPDU'
        }
      },
      {
        path: 'ds-thietbi-may-phat-dien',
        component: () => import('./Physicalequpment/index.vue'),
        meta: {
          title: 'Quản lý thiết bị - Máy phát điện'
        }
      },
      {
        path: 'thietbi-may-phat-dien/:id?',
        component: () => import('./Physicalequpment/wrappers/MayPhatDienDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Máy phát điện'
        }
      },
      {
        path: 'ds-thietbi-tu-trung-the',
        component: () => import('./Physicalequpment/index.vue'),
        meta: {
          title: 'Quản lý thiết bị - Tủ trung thế'
        }
      },
      {
        path: 'thietbi-tu-trung-the/:id?',
        component: () => import('./Physicalequpment/wrappers/TuTrungTheDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Tủ trung thế'
        }
      },
      {
        path: 'ds-thietbi-may-bien-ap',
        component: () => import('./Physicalequpment/index.vue'),
        meta: {
          title: 'Quản lý thiết bị - Máy biến áp'
        }
      },
      {
        path: 'thietbi-may-bien-ap/:id?',
        component: () => import('./Physicalequpment/wrappers/MayBienApDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Máy biến áp'
        }
      },
      {
        path: 'ds-thietbi-tu-dien-ha-the',
        component: () => import('./Physicalequpment/index.vue'),
        meta: {
          title: 'Quản lý thiết bị - Tủ điện hạ thế'
        }
      },
      {
        path: 'thietbi-tu-dien-ha-the/:id?',
        component: () => import('./Physicalequpment/wrappers/TuDienHaTheDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Tủ điện hạ thế'
        }
      },
      {
        path: 'ds-thietbi-tu-dien-ats',
        component: () => import('./Physicalequpment/index.vue'),
        meta: {
          title: 'Quản lý thiết bị - Tủ điện ATS'
        }
      },
      {
        path: 'thietbi-tu-dien-ats/:id?',
        component: () => import('./Physicalequpment/wrappers/TuDienATSDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Tủ điện ATS'
        }
      },
      {
        path: 'ds-thietbi-ups',
        component: () => import('./Physicalequpment/index.vue'),
        meta: {
          title: 'Quản lý thiết bị - UPS'
        }
      },
      {
        path: 'thietbi-ups/:id?',
        component: () => import('./Physicalequpment/wrappers/UPSDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - UPS'
        }
      },
      {
        path: 'ds-thietbi-dieu-hoa',
        component: () => import('./Physicalequpment/index.vue'),
        meta: {
          title: 'Quản lý thiết bị - Điều hòa'
        }
      },
      {
        path: 'thietbi-dieu-hoa/:id?',
        component: () => import('./Physicalequpment/wrappers/DieuHoaDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Điều hòa'
        }
      },
      {
        path: 'ds-thietbi-thap-gian-nhiet',
        component: () => import('./Physicalequpment/index.vue'),
        meta: {
          title: 'Quản lý thiết bị - Tháp giản nhiệt'
        }
      },
      {
        path: 'thietbi-thap-gian-nhiet/:id?',
        component: () => import('./Physicalequpment/wrappers/ThapGianNhietDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Tháp giản nhiệt'
        }
      },
      {
        path: 'ds-thietbi-may-bom',
        component: () => import('./Physicalequpment/index.vue'),
        meta: {
          title: 'Quản lý thiết bị - Máy Bơm'
        }
      },
      {
        path: 'thietbi-may-bom/:id?',
        component: () => import('./Physicalequpment/wrappers/MayBomDevice.vue'),
        meta: {
          title: 'Thông tin thiết bị - Máy Bơm'
        }
      },
      {
        path: 'ds-thietbi-mau',
        component: () => import('./QuanLyThietBiMau/index.vue'),
        meta: {
          title: 'Danh sách thiết bị mẫu'
        }
      },
      {
        path: 'thietbi-mau-server/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/ServerThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Server'
        }
      },
      {
        path: 'thietbi-mau-server-blade/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/ServerBladeThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Server Blade'
        }
      },
      {
        path: 'thietbi-mau-khay-dia/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/KhayDiaThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Khay đĩa'
        }
      },
      {
        path: 'thietbi-mau-network/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/NetworkThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Network'
        }
      },
      {
        path: 'thietbi-mau-blade/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/BladeThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Blade'
        }
      },
      {
        path: 'thietbi-mau-controller/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/ControllerThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Controller'
        }
      },
      {
        path: 'thietbi-mau-may-phat-dien/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/MayPhatDienThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Máy phát điện'
        }
      },
      {
        path: 'thietbi-mau-tu-trung-the/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/TuTrungTheThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Tủ trung thế'
        }
      },
      {
        path: 'thietbi-mau-may-bien-ap/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/MayBienApThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Máy biến áp'
        }
      },
      {
        path: 'thietbi-mau-tu-ha-the/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/TuHaTheThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Tủ hạ thế'
        }
      },
      {
        path: 'thietbi-mau-tu-phan-phoi/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/TuPhanPhoiThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Tủ phân phối'
        }
      },
      // Chi tiết thiết bị mẫu - Server
      {
        path: 'thietbi-mau-server/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/ServerThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Server'
        }
      },
      // Chi tiết thiết bị mẫu - Server Blade
      {
        path: 'thietbi-mau-server-blade/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/ServerBladeThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Server Blade'
        }
      },
      // Chi tiết thiết bị mẫu - Controller
      {
        path: 'thietbi-mau-controller/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/ControllerThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Controller'
        }
      },
      // Chi tiết thiết bị mẫu - Khay đĩa
      {
        path: 'thietbi-mau-khay-dia/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/KhayDiaThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Khay đĩa'
        }
      },
      // Chi tiết thiết bị mẫu - Network
      {
        path: 'thietbi-mau-network/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/NetworkThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Network'
        }
      },
      // Chi tiết thiết bị mẫu - Máy phát điện
      {
        path: 'thietbi-mau-generator/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/GeneratorThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Máy phát điện'
        }
      },
      // Chi tiết thiết bị mẫu - Tủ trung thế
      {
        path: 'thietbi-mau-tu-trung-the/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/TuTrungTheThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Tủ trung thế'
        }
      },
      // Chi tiết thiết bị mẫu - Máy biến áp
      {
        path: 'thietbi-mau-may-bien-ap/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/MayBienApThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Máy biến áp'
        }
      },
      // Chi tiết thiết bị mẫu - Tủ hạ thế
      {
        path: 'thietbi-mau-tu-ha-the/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/TuHaTheThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Tủ hạ thế'
        }
      },
      // Chi tiết thiết bị mẫu - Tủ ATS
      {
        path: 'thietbi-mau-tu-ats/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/TuAtsThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Tủ ATS'
        }
      },
      // Chi tiết thiết bị mẫu - Tủ phân phối
      {
        path: 'thietbi-mau-tu-phan-phoi/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/TuPhanPhoiThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - Tủ phân phối'
        }
      },
      // Chi tiết thiết bị mẫu - UPS
      {
        path: 'thietbi-mau-ups/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/UpsThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - UPS'
        }
      },
      // Chi tiết thiết bị mẫu - rPDU
      {
        path: 'thietbi-mau-rpdu/:id?',
        component: () => import('./QuanLyThietBiMau/wrappers/RpduThietBiMau.vue'),
        meta: {
          title: 'Chi tiết thiết bị mẫu - rPDU'
        }
      },
      {
        path: 'cap-mayao',
        component: () => import('./CapMayAo'),
        meta: {
          title: 'Cấp máy ảo'
        }
      },
      {
        path: 'thietbi-trunggian',
        component: () => import('./ThietBiTrungGian'),
        meta: {
          title: 'Danh mục thiết bị trung gian'
        }
      },
      {
        path: 'quanly-subnet',
        component: () => import('./QuanLySubnet/index.vue'),
        meta: {
          title: 'Quản lý subnet '
        }
      },
      {
        path: 'thu-hoi-mayao',
        component: () => import('./ThuHoiMayAo'),
        meta: {
          title: 'Thực hiện thu hồi máy ảo'
        }
      },
      {
        path: 'yeu-cau-cap-phat',
        component: () => import('./YeuCauCapPhat/index.vue'),
        meta: {
          title: 'Yêu cầu cấp phát'
        }
      },
      {
        path: 'danhmuc-controller-tudia/:id',
        name: 'ControllerTuDia',
        component: () => import('./ControllerTuDia'),
        meta: {
          title: 'Danh mục Controller trong tủ đĩa'
        }
      },
      {
        path: 'quanly-kho-tainguyen',
        name: 'QuanLyKhoTaiNguyen',
        component: () => import('./QuanLyKhoTaiNguyen'),
        meta: {
          title: 'Quản lý kho tài nguyên'
        }
      },
      {
        path: 'quanly-ip-address',
        component: () => import('./quanly-ip-address/index.vue'),
        meta: {
          title: 'Quản lý IP address'
        }
      },
      {
        path: 'quanly-vrf',
        component: () => import('./quanly-vrf/index.vue'),
        meta: {
          title: 'Quản lý VRF'
        }
      },
      {
        path: 'quanly-subnet-v2',
        name: 'QUẢN LÝ SUBNET CLOUD - DẢI IP BÊN IDC',
        component: () => import('./quanly-subnet'),
        meta: {
          title: "QUẢN LÝ SUBNET CLOUD - DẢI IP BÊN IDC"
        }
      },
    ]
  }
]

const routes = addAuthGuard(baseRoutes)

export default routes
