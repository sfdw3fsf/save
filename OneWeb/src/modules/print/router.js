import LayoutMain from '@/layouts/LayoutMain'
export default [
  {
    path: '/print',
    name: 'print',
    component: LayoutMain,
    children: [
      {
        path: 'inphieuchi',
        name: 'inphieuchi',
        meta: {
          title: '[UR2.2.005] - In phiếu chi'
        },
        component: () => import('./InPhieuChi/index.js')
      },
      {
        path: 'inphieuthu',
        name: 'inphieuthu',
        meta: {
          title: '[UR2.2.005] - In phiếu thu'
        },
        component: () => import('./InPhieuThu/index.js')
      },
      {
        path: 'inbienban',
        name: 'inbienban',
        meta: {
          title: '[UR2.2.005] - In biên bản'
        },
        component: () => import('./InBienBan/index.js')
      },
      {
        path: 'CheckTichHopBaoCao',
        name: 'CheckTichHopBaoCao',
        meta: {
          title: '[UR2.2.005] - In biên bản'
        },
        component: () => import('./index')
      },
      {
        path: 'CauHinhBaoCao',
        name: 'ConfigReport',
        meta: {
          title: 'Cấu hình báo cáo'
        },
        component: () => import('./ConfigReport/index')
      },
      {
        path: 'VattuTBTest',
        name: 'VattuTBTest',
        meta: {
          title: 'Test vật tư'
        },
        component: () => import('../contract/complete_profile/CompleteProfileChangeTSMyTV/popups/VatTuTBTest')
      },
      {
        path: 'ProfileChangeService',
        name: 'profileChangeService',
        meta: {
          title: '[UR2.4.018] - Hoàn thiện hồ sơ Thay đổi dịch vụ'
        },
        component: () => import('../contract/setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'ChangServiceByHost',
        name: 'ChangServiceByHost',
        meta: {
          title: '[UR2.4.018] - HOST - THAY ĐỔI DỊCH VỤ CỐ ĐỊNH'
        },
        component: () => import('../contract/setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'profileChangeService',
        name: 'profileChangeService',
        meta: {
          title: '[UR2.4.018] - HOST - THAY ĐỔI DỊCH VỤ CỐ ĐỊNH'
        },
        component: () => import('../contract/setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'ChangServiceByHost',
        name: 'ChangServiceByHost',
        meta: {
          title: '[UR2.4.018] - HOST - THAY ĐỔI DỊCH VỤ CỐ ĐỊNH'
        },
        component: () => import('../contract/setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'profileChangeService',
        name: 'profileChangeService',
        meta: {
          title: '[UR2.4.018] - HOST - THAY ĐỔI DỊCH VỤ CỐ ĐỊNH'
        },
        component: () => import('../contract/setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'ChangeFiberLandlineServiceByTeleStation',
        name: 'ChangeFiberLandlineServiceByTeleStation',
        meta: {
          title: '[UR2.4.018] - Hoàn thiện hồ sơ Thay đổi dịch vụ'
        },
        component: () => import('../contract/setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'ChangeMyTVParameter',
        name: 'ChangeMyTVParameter',
        meta: {
          title: '[UR2.4.018] - Thay đổi thông số MyTV'
        },
        component: () => import('../contract/setup/ChangeMyTVParameter/index.js')
      },
      {
        path: 'ChangeServiceAddCrossSale',
        name: 'ChangeServiceAddCrossSale',
        meta: {
          title: '[BSS-52080] - [Bán chéo]: Thay đổi dịch vụ gia tăng bán chéo'
        },
        component: () => import('../contract/setup/ChangeServiceAddCrossSale/index.js')
      },
      {
        path: 'ChangeMyTVParameter',
        name: 'changeMyTVParameter',
        meta: {
          title: '[UR2.1.036] - Thay đổi thông số MyTV'
        },
        component: () => import('../contract/setup/ChangeMyTVParameter/index.js')
      }
    ]
  }
]
