import LayoutMain from '@/layouts/LayoutMain'
import outreach from './outreach/router.js'
import spdv from './spdv/router.js'
import khdn from './khdn/router.js'
import quote from './quote/router.js'

export default [
  {
    path: '/customer',
    name: 'customer',
    component: LayoutMain,
    children: [
      ...outreach,
      ...spdv,
      ...khdn,
      ...quote
      // {
      //   path: 'UpdateOutReachPlan',
      //   name: 'UpdateOutReachPlan',
      //   component: () => import('./outreach/UpdateOutReachPlan'),
      //   meta: {
      //     title: 'Báo cáo kế hoạch tiếp cận khách hàng'
      //   }
      // },
    ]
  }
]
