import LayoutMain from '@/layouts/LayoutMain'
export default [
  {
    path: '/OBNC',
    name: 'OBNC',
    component: LayoutMain,
    children: [
      //Mẫu route con
      // {
      //   path: 'inphieuchi',
      //   name: 'inphieuchi',
      //   meta: {
      //     title: '[UR2.2.005] - In phiếu chi'
      //   },
      //   component: () => import('./InPhieuChi/index.js')
      // },
      {
        path:'khachhangkhoiphuc',
        name: 'khachhangkhoiphuc',
        component: () => import('./Khachhangkhoiphuc/ObNhancong.vue'),
        meta: {
          title:'Gia Han Tra Truoc'
        }
      }
    ]
  }
]
