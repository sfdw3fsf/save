import LayoutMain from '@/layouts/LayoutMain'
export default [
  {
    path: '/prepaid',
    name: 'prepaid',
    component: LayoutMain,
    children: [
      {
        path: 'ChoosePreapaidNumber',
        name: 'choosepreapaidnumber',
        meta: {
          title: '[UR2.11.001] - Chọn số trả trước'
        },
        component: () => import('./ChoosePreapaidNumber/index.js')
      },
      {
        path: 'UpdateWrongIDNumber',
        name: 'UpdateWrongIDNumber',
        meta: {
          title: '[UR2.11.006] - Cập nhật số giấy tờ sai',
          dialog: true,
        },
        component: () => import('./UpdateWrongIDNumber/UpdateWrongIDNumber.vue')
      },
      {
        path: 'ChoseNumberInProvinceWarehouse',
        name: 'chosenumberinprovincewarehouse',
        meta: {
        title: '[UR2.11.014] - Chọn số cho khách hàng kho tỉnh'
        },
        component: () => import('./ChoosePreapaidNumber/index.js')
      },

    ]
  }
]
