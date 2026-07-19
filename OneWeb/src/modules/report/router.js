import LayoutMain from '@/layouts/LayoutMain'

export default [
  {
    path: '/report',
    name: 'report',
    component: LayoutMain,
    children: [
      {
        path: 'bi',
        name: 'bi',
        component: () => import('./BI/BI.vue')
      }
    ]
  }
]
