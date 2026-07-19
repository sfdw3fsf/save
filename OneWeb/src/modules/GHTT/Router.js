// import LayoutMain from '@/layouts/LayoutMain'
// export default [
//   {
//     path: '/GHTT',
//     name: 'GHTT',
//     component: () => import('./phangiao/thongtinHDon.vue')
//   },
//   {
//     path: '/myGHTT',
//     name: 'myGHTT',
//     component: LayoutMain,
//     component: () => import('./phangiao/myFormGHTT.vue')
//   },
// ]


import LayoutMain from '@/layouts/LayoutMain'
export default [
  {
    path: '/GHTT',
    name: 'GHTT',
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
        path:'phangiao',
        name: 'phangiao',
        //component: () => import('./Phangiao/frmPhanGiao.vue'),
        component: () => import('./Phangiao/phangiao.vue'),
        //component: () => import('./Phangiao/Luu.vue'),
        //component: () => import('./Phangiao/index.js'),
        meta: {
          title:'Gia Han Tra Truoc'
        }
      }
    ]
  }
]