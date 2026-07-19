import CorrectLocation from './CorrectLocation'


import LayoutMain from '@/layouts/LayoutMain'

export default [
  {
    path: '/common',
    name: 'common',
    component: LayoutMain,
    redirect: '/common/home',
    children: [
      {
        path: 'home',
        name: 'COMMON home page',
        component: () => import('./Home'),
        meta: {
          title: 'Trang chủ'
        }
      },
      {
        path: 'CorrectLocation',
        name: 'CorrectLocation',
        component: CorrectLocation,
        meta: {
          title: '[UR2.13.005] - Chuẩn hóa địa danh'
        }
      },
      {
        path: 'popupGiaoPhieu',
        name: 'popupGiaoPhieu',
        component: () => import('./popup/popupGiaoPhieu/index.js'),
        meta: {
          title: '[UR2.13.005] - Chuẩn hóa địa danh'
        }
      }
    ]
  }
]
