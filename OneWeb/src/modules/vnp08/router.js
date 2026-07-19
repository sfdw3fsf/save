import LayoutMain from '@/layouts/LayoutMain'
export default [
  {
    path: '/vnp08',
    name: 'vnp08',
    component: LayoutMain,
    children: [
      {
        path: 'ChannelManagement',
        name: 'ChannelManagement',
        meta: {
          title: 'Quản lý thông tin kênh bán'
        },
        component: () => import('./ChannelManagement/index.vue')
      },
      {
        path: 'DirectoryPermissionGroups',
        name: 'DirectoryPermissionGroups',
        meta: {
          title: 'Danh mục nhóm quyền'
        },
        component: () => import('./DirectoryPermissionGroups/index.vue')
      },
      {
        path: 'DirectoryRights',
        name: 'DirectoryRights',
        meta: {
          title: 'Danh mục quyền'
        },
        component: () => import('./DirectoryRights/index.vue')
      },
      {
        path: 'QuestionList',
        name: 'QuestionList',
        meta: {
          title: 'Danh mục câu hỏi'
        },
        component: () => import('./QuestionList/index.vue')
      },
      // Mẫu route con
      // {
      //   path: 'inphieuchi',
      //   name: 'inphieuchi',
      //   meta: {
      //     title: '[UR2.2.005] - In phiếu chi'
      //   },
      //   component: () => import('./InPhieuChi/index.js')
      // },
      {
        path: 'ChannelCategoryList',
        name: 'ChannelCategoryList',
        component: () => import('./ChannelCategoryList/index.js'),
        meta: {
          title: 'Danh mục hạng kênh'
        }
      },
      {
        path: 'ListChannelMembers',
        name: 'ListChannelMembers',
        component: () => import('./ListChannelMembers/index.js'),
        meta: {
          title: 'Danh mục thành viên kênh'
        }
      },
      {
        path: 'ListContractTypes',
        name: 'ListContractTypes',
        meta: {
          title: '[UR2.1.6] - Danh Mục loai Hop Dong'
        },
        component: () => import('./ListContractTypes/index.js')
      },
      {
        path: 'ProductCategoryList',
        name: 'ProductCategoryList',
        meta: {
          title: '[UR2.1.7] - Danh Mục loai Sản Phẩm'
        },
        component: () => import('./ProductCategoryList/index.js')
      },
      {
        path: 'ProductPortfolio',
        name: 'ProductPortfolio',
        meta: {
          title: '[UR2.1.8] -  Danh Mục Sản Phẩm'
        },
        component: () => import('./ProductPortfolio/index.js')
      },
      {
        path: 'SalesChannel',
        name: 'SalesChannel',
        component: () => import('./SalesChannel/SalesChannel.vue'),
        meta: {
            title: 'Nghiệp vụ quản lý kênh bán'
        }
      },
      {
        path: 'ProposedSalesChannel',
        name: 'ProposedSalesChannel',
        component: () => import('./ProposedSalesChannel/ProposedSalesChannel.vue'),
        meta: {
            title: 'Đề xuất ký hợp đồng kênh bán'
        }
      },
      {
        path: 'ApproveSalesChannel',
        name: 'ApproveSalesChannel',
        component: () => import('./ApproveSalesChannel/ApproveSalesChannel.vue'),
        meta: {
            title: 'Lãnh đạo đơn vị phê duyệt ký hợp đồng kênh bán'
        }
      },
      {
        path: 'SalesChannelContract',
        name: 'SalesChannelContract',
        component: () => import('./SalesChannelContract/index.js'),
        meta: {
          title: 'Hợp đồng ký với kênh bán'
        }
      },
      {
        path: 'ApproveSalesChannel_LDB',
        name: 'ApproveSalesChannel_LDB',
        component: () => import('./ApproveSalesChannel_LDB/ApproveSalesChannel_LDB.vue'),
        meta: {
            title: 'Lãnh đạo Ban KHD/LĐ TTKD phê duyệt đề xuất ký hợp đồng kênh bán'
        }
      },
      {
        path: 'SalesChannelContractDetail',
        name: 'SalesChannelContractDetail',
        component: () => import('./SalesChannelContract/popups/popup_TTHopDongKenh.vue'),
        meta: {
          title: 'Thông tin Hợp đồng kênh bán'
        }
      },
      {
        path: 'SalesChannelMechanism',
        name: 'SalesChannelMechanism',
        component: () => import('./SalesChannelContract/popups/popup_TTCoChe.vue'),
        meta: {
          title: 'Cơ chế của kênh bán'
        }
      },
	    {
        path: 'AssignContract',
        name: 'AssignContract',
        meta: {
          title: 'Gán công đoạn trên hợp đồng của kênh bán'
        },
        component: () => import('./AssignContract/index.vue')
      },
      {
        path: 'SearchChannels',
        name: 'SearchChannels',
        component: () => import('./DeliveryHistory/DeliveryHistory.vue'),
        meta: {
            title: 'Tra cứu đề xuất kênh bán'
        }
      },
    ]
  }
]