import SomeModule from '@/modules/SomeModule/SomeModule'
import User from './User'

export default [
  {
    path: '/main',
    name: 'Main',
    component: SomeModule,
    children: [
      {
        path: 'user',
        name: 'User',
        component: User
      }
    ]
  }
]
