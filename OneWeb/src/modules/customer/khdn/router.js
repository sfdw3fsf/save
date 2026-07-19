import LayoutMain from '@/layouts/LayoutMain'

export default [
      {
        path: 'ChiTiet',
        name: 'ChiTiet',
        component: () => import('./chitiet/index.js'),
        meta: {
          title: 'Tạo mã dịch vụ'
        }
      },
         // Thu thập thông tin khách hàng doang nghiệp tiềm năng DN
         {
          path: 'thu-thap-thong-tin-kh-tiem-nang-dn',
          name: 'ThuThapThongTinKHTiemNangDN',
          meta: {
            title: 'Thu thập thông tin khách hàng tiềm năng doanh nghiệp'
          },
          component: ()=> import('./ThuThapThongTinKHTiemNangDN/index.vue')
        },
      {
        path: 'DeNghiCapMaSanPham/:tag',
        name: 'DeNghiCapMaSanPham',
        meta: {
          title: 'Đề nghị cấp mã sản phẩm'
        },
        component: () => import('./DeNghiCapMaSanPham/DeNghiCapMaSanPham.vue')
      },
]
