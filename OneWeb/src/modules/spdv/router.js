import LayoutMain from '@/layouts/LayoutMain'
export default [
  {
    path: '/spdv',
    name: 'spdv',
    component: LayoutMain,
    redirect: '/spdv/home',
    children: [
      {
        path: 'DeNghiCapMaSanPham/:tag',
        name: 'DeNghiCapMaSanPham',
        meta: {
          title: 'Đề nghị cấp mã sản phẩm'
        },
        component: () => import('./DeNghiCapMaSanPham/DeNghiCapMaSanPham.vue')
      },
    ]
  }
]
