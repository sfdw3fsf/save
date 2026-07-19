import LayoutMain from '@/layouts/LayoutMain'

export default [
      {
        path: 'TaoMaDichVu',
        name: 'TaoMaDichVu',
        component: () => import('./taomadv/index.js'),
        meta: {
          title: 'Tạo mã dịch vụ'
        }
      },
      {
        path: 'PheDuyetMaDichVu',
        name: 'PheDuyetMaDichVu',
        component: () => import('./pheduyetmadv/index.js'),
        meta: {
          title: 'Phê duyệt mã dịch vụ'
        }
      },
]
