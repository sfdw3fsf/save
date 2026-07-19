import LayoutMain from '@/layouts/LayoutMain'

export default [
  {
    path: 'UpdateOutReachPlan',
    name: 'UpdateOutReachPlan',
    component: () => import('./UpdateOutReachPlan'),
    meta: {
      title: 'Báo cáo kế hoạch tiếp cận khách hàng'
    }
  },
]
