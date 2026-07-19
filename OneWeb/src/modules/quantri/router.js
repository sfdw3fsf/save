import UploadFileHosoDienTu from './components/UploadFileHoSoDienTu'
import LayoutMain from '@/layouts/LayoutMain'

export default [
  {
    path: '/quantri',
    name: 'quantri',
    component: LayoutMain,
    redirect: '/quantri/home',
    children: [
      {
        path: 'home',
        name: 'QUANTRI home page',
        component: () => import('./Home'),
        meta: {
          title: 'Trang chủ'
        }
      },
      {
        path: 'UploadFileHosoDienTu',
        name: 'UploadFileHosoDienTu',
        component: UploadFileHosoDienTu
      }
    ]
  }
]
