import LayoutMain from '@/layouts/LayoutMain'
// import XuLyPhieuHoTro from './XuLyPhieuHoTro'

export default [
  {
    path: '/ipcc',
    name: 'ipcc',
    component: LayoutMain,
    children: [
      {
        path: 'new',
        name: 'new',
        meta: {
          title: 'Tiếp nhận cuộc gọi tổng đài'
        },
        component: () => import('./Home2/index.js')
      },
      {
        path: 'index1',
        name: 'index1',
        meta: {
          title: 'Tiếp nhận cuộc gọi tổng đài'
        },
        component: () => import('./Home/index.js')
      },
      {
        path: 'index',
        name: 'index',
        meta: {
          title: 'Tiếp nhận cuộc gọi tổng đài'
        },
        component: () => import('./Home3/index.js')
      },
      // chức năng xử lý phiếu hỗ trợ
      // {
      //   path: 'xu-ly-phieu-ho-tro',
      //   name: 'XuLyPhieuHoTro',
      //   meta: {
      //     title: 'Xử lý phiếu hỗ trợ'
      //   },
      //   component: XuLyPhieuHoTro
      // }
    ]
  }
]
