import Vue from 'vue'
import Router from 'vue-router'
import menu from '../utils/menu'
// layouts
import BlankLayout from '@/layouts/BlankLayout'
import MainLayout from '@/layouts/MainLayout'
// public page
import Login from '@/modules/AUTH/Login'

import hatang from '@/modules/hatang/router.js'
// import tainguyenidc from '@/modules/tainguyen-idc/router.js'
// import KHIEUNAI from '@/modules/EXTS/KHIEUNAI/router.js'

// module ECMS routes
//import ECMS from '@/modules/ECMS/router.js'

// module routes
// import QLTN from '@/modules/QLTN/router.js'
// // // module OB routes

// // // module QLVT routes
// import QLVT from '@/modules/QLVT/router.js'
// // // module account
// import account from '@/modules/account/router.js'
// // module admin
// import admin from '@/modules/admin/router.js'
// import vnp08 from '@/modules/vnp08/router.js'
// // // module install
// import install from '@/modules/install/router.js'
// // // module contract
// import contract from '@/modules/contract/router.js'
// // // module quantri
// import quantri from '@/modules/quantri/router.js'
// // // module search
// import search from '@/modules/search/router.js'

// // // module common
// import common from '@/modules/common/router.js'
// import complaint from '@/modules/complaint/router.js'
// import prepaid from '@/modules/prepaid/router.js'

// // module CABMAN routes
// import CABMAN from '@/modules/CABMAN/router.js'
// // module QLSC routes
// import QLSC from '@/modules/QLSC/router.js'

// import incident from '@/modules/incident/router.js'
// import report from '@/modules/report/router.js'
// // module HOPDONG routes
// import HOPDONG from '@/modules/EXTS/HOPDONG/router.js'
// // module KHIEUNAI routes
// import KHIEUNAI from '@/modules/EXTS/KHIEUNAI/router.js'
// // module QUANTRI routes
// import QUANTRI from '@/modules/EXTS/QUANTRI/router.js'
// // module TOANHA routes
// import TOANHA from '@/modules/EXTS/TOANHA/router.js'
// // module Payment routes
// import Payment from '@/modules/Payment/router.js'
// import print from '@/modules/print/router.js'
// // module HTKH
// import htkh from '@/modules/htkh/router.js'
// //module channel
// import GHTT from '@/modules/GHTT/Router.js'
// import channel from '@/modules/channel/router.js'
// import customer from '@/modules/customer/router.js'
// import ipcc from '@/modules/ipcc/router.js'
// import OBNC from '@/modules/OBNC/Router.js'

Vue.use(Router)
const router = new Router({
  routes: [
    {
      path: '',
      component: MainLayout,
      children: [
        {
          path: '',
          name: 'Home',
          component: () => import('@/modules/index') //Home
        },
        {
          path: 'home1',
          name: 'Home1',
          component: () => import('@/modules/index1') //Home
        }
      ]
    },
    {
      path: '/auth',
      name: 'auth',
      component: BlankLayout,
      meta: {
        requiresAuth: false
      },
      children: [
        {
          path: 'login',
          name: 'Login',
          component: Login,
          meta: {
            requiresAuth: false
          }
        }
      ]
    },
    ...hatang
    // ...tainguyenidc
    //...ECMS

    // ...CSKH,
    // ...QLTN,
    // ...QLVT,
    // ...ECMS,
    // ...CABMAN,
    // ...QLSC,
    // ...account,
    // ...admin,
    // ...search,
    // ...install,
    // ...contract,
    // ...admin,
    // ...quantri,
    // ...incident,
    // ...common,
    // ...complaint,
    // ...prepaid,
    // ...report,
    // ...HOPDONG,
    // ...KHIEUNAI
    // ...QUANTRI,
    // ...TOANHA,
    // ...Payment,
    // ...print,
    // ...vnp08,
    // ...htkh,
    // ...channel,
    // ...ipcc,
    // ...GHTT,
    // ...customer,
    // ...OBNC,

    /*
    {
      path: '/qltn',
      name: 'qltn',
      component: LayoutMain
    },
    */
  ]
})
// Vue Router navigation guards
router.beforeEach((to, from, next) => {
  console.log('Routing from:', from.fullPath, 'to:', to.fullPath)
  let path = to.fullPath
  if (path != '/auth/login') sessionStorage.setItem('currentPath', path)

  if (!menu.checkExists(to.fullPath)) {
    if (menu.checkExists(to.path)) {
      path = to.path
    }
  }

  if (to.meta.requiresAuth !== false) {
    var menuitems = Vue.auth.getMenuFromStorage()

    if (
      menuitems &&
      menuitems.length > 0 &&
      to.fullPath &&
      !['/', '/#', '/#/'].includes(to.fullPath) &&
      !menuitems.find(
        (x) =>
          x.url &&
          [to.fullPath.toLowerCase(), '/#' + to.fullPath.toLowerCase(), '/#/' + to.fullPath.toLowerCase()].includes(
            decodeURIComponent(x.url.toLowerCase())
          )
      )
    ) {
      console.info('Bạn không có quyền truy cập chức năng này! Vui lòng liên hệ admin.', to.fullPath)
    }

    var expirationDate = new Date(0)
    expirationDate.setUTCSeconds(Vue.auth.getProperty('exp'))
    var currentTime = new Date().getTime() / 1000

    if (expirationDate && expirationDate > 0 && expirationDate < currentTime) {
      menu.setCurrentMenuItem(0)
      menu.setCurrentPath('')
      Vue.$toast.error('Phiên đăng nhập hết hạn! Vui lòng đăng nhập lại.')
      next({ name: 'Login' })
    } else if (Vue.auth.isAuthenticated()) {
      menu.setCurrentMenuItemFromRouter(path)
      next()
    } else {
      menu.setCurrentMenuItem(0)
      menu.setCurrentPath('')
      next({ name: 'Login' })
    }
  } else {
    menu.setCurrentMenuItemFromRouter(path)
    next()
  }
})

export default router
