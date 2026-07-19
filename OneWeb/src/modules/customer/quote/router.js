import LayoutMain from '@/layouts/LayoutMain'

export default [
  {
    path: 'CustomerQuotation',
    name: 'CustomerQuotation',
    component: () => import('./CustomerQuotation'),
    meta: {
      title: 'Báo giá khách hàng'
    }
  },
]