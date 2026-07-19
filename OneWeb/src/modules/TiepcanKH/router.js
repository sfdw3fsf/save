import LayoutMain from '@/layouts/LayoutMain'


export default [
  {
    path: '/TiepcanKH',
    name: 'TiepcanKH',
    component: LayoutMain,
    redirect: '/TiepcanKH/home',
    children: [
      {
        path: 'home',
        name: 'TCKH home page',
        component: () => import('./Home'),
        meta: {
          title: 'Trang chủ'
        }
      },

      {
        path: 'PheduyetKehoachTCKH',
        name: 'PheduyetKehoachTCKH',
        meta: {
          title: '[URx.x.xxx] - Phê Duyệt Kế Hoạch Tiếp Cận Khách Hàng '
        },
        component: () => import('./PheduyetKehoachTCKH/PheduyetKehoachTCKH.vue')
      },

    ]
  }
]
