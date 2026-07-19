import UpdateBirthdayByFile from './UpdateBirthdayByFile'
import CancelBookingVISAAccount from './CancelBookingVISAAccount'
import LayoutMain from '@/layouts/LayoutMain'

export default [
  {
    path: '/account',
    name: 'account',
    component: LayoutMain,
    redirect: '/account/home',
    children: [
      {
        path: 'home',
        name: 'ACCOUNT home page',
        component: () => import('./Home'),
        meta: {
          title: 'Trang chủ'
        }
      },
      {
        path: 'UpdateBirthdayByFile',
        name: 'UpdateBirthdayByFile',
        component: UpdateBirthdayByFile,
        meta: {
          title: '[UR1.5.031] - Cập nhật ngày sinh thuê bao di động theo file'
        }
      },
      {
        path: 'CancelBookingVISAAccount',
        name: 'CancelBookingVISAAccount',
        component: CancelBookingVISAAccount
      },
      {
        path: 'ChangeMacFiber',
        name: 'ChangeMacFiber',
        component: () => import('./ChangeMacFiber/indexModal.vue'),
        meta: {
        title: '[UR1.5.044] - Đổi địa chỉ MAC Fiber',
        dialog: true
        }
      },
      {
        path: 'CancelBookingVISAAccount',
        name: 'CancelBookingVISAAccount',
        component: () => import('./CancelBookingVISAAccount/index.js'),
        meta: {
          title: '[UR2.1.026] - Hủy đặt chỗ account visa'
        }
      }
    ]
  }
]
