import LayoutMain from '@/layouts/LayoutMain'
export default [
  {
    path: '/htkh',
    name: 'htkh',
    component: LayoutMain,
    children: [
      //Mẫu route con
      // {
      //   path: 'inphieuchi',
      //   name: 'inphieuchi',
      //   meta: {
      //     title: '[UR2.2.005] - In phiếu chi'
      //   },
      //   component: () => import('./InPhieuChi/index.js')
      // },
      {
        path: 'SurveyConcentrateCustomer',
        name: 'SurveyConcentrateCustomer',
        component: () => import('./SurveyConcentrateCustomer'),
        meta: {
          title: 'BSS-79914 - Khảo sát khách hàng tập trung'
        }
      },
      {
        path: 'ReceivingCallsSwitchboard',
        name: 'ReceivingCallsSwitchboard',
        meta: {
          title: '[UR2.1.006] - Tiếp nhận yêu cầu tập trung'
        },
        component: () => import('./ReceivingCallsSwitchboard/index.js')
      },
      {
        path: 'ManagementDamageInventory',
        name: 'ManagementDamageInventory',
        component: () => import('./ManagementDamageInventory'),
        meta: {
          title: 'Phiếu tồn báo hỏng'
        }
      },
      {
        path: 'Order',
        name: 'Order',
        component: () => import('./Order/Order.vue'),
        meta: {
          title: 'BSS-80627 - Đơn hàng'
        }
      },
      {
        path: 'LockIncidentReportProactive',
        name: 'LockIncidentReportProactive',
        component: () => import('./LockIncidentReportProactive/LockIncidentReportProactive.vue'),
        meta: {
          title: 'BSS-- Khoá phiếu chủ động'
        }
      },
      {
        path: 'ListConcentrateSurveyQuestionnaire',
        name: 'ListConcentrateSurveyQuestionnaire',
        component: () => import('./ListConcentrateSurveyQuestionnaire'),
        meta: {
          title: 'Danh sách phiếu khảo sát tập trung'
        }
      },
      {
        path: 'TiepNhanDonHang',
        name: 'TiepNhanDonHang',
        component: () => import('./TiepNhanDonHang'),
        meta: {
          title: 'Tiếp nhận đơn hàng'
        }
      },
      {
        path: 'DivideOrdersRequest',
        name: 'DivideOrdersRequest',
        meta: { title: '[UR2.3.013] - Phân chia đơn  hàng' },
        component: () => import('./DivideOrdersRequest/index.js')
      },
      {
        path: 'ManagementOrderInventory',
        name: 'ManagementOrderInventory',
        meta: { title: 'Quản lý đơn  hàng' },
        component: () => import('./ManagementOrderInventory/index.js')
      },
      {
        path: 'ManagementIncidentInventory',
        name: 'ManagementIncidentInventory',
        meta: { title: 'Quản lý tồn báo hỏng' },
        component: () => import('./ManagementIncidentInventory/index.js')
      },
      {
        path: 'KiemSoatTonKhieuNai',
        name: 'KiemSoatTonKhieuNai',
        component: () => import('./KiemSoatTonKhieuNai'),
        meta: {
          title: 'Kiểm soát tồn khiếu nại'
        }
      },
      {
        path: 'OBGiaHanTraTruoc',
        name: 'OBGiaHanTraTruoc',
        meta: { title: 'OB Gia hạn trả trước' },
        component: () => import('./OBGiaHanTraTruoc/index.js')
      },
      {
        path: 'OBGiaHanTraTruocV2',
        name: 'OBGiaHanTraTruocV2',
        meta: { title: 'OB Gia hạn trả trước' },
        component: () => import('./OBGiaHanTraTruocV2/index.js')
      },
      {
        path: 'DeclareChannelInfo',
        name: 'DeclareChannelInfo',
        component: () => import('./DeclareChannelInfo/DeclareChannelInfo.vue'),
        meta: {
          title: 'Khai báo thông tin chung kênh luồng'
        }
      },
      {
        path:'ExportInvoice',
        name: 'ExportInvoice',
        component: () => import('./ExportInvoice/index.js'),
        meta: {
          title:'Xuất Dữ Liệu hóa đơn chi tiết'
        }
      },
      {
        path: 'TaoNhanVien',
        name: 'TaoNhanVien',
        meta: { title: 'Tạo nhân viên' },
        component: () => import('./TaoNhanVien')
      },
      {
        path: 'CustomerEngagement',
        name: 'CustomerEngagement',
        component: () => import('./CustomerEngagement/CustomerEngagement.vue'),
        meta: {
          title: 'Giao nhiệm vụ tiếp cận khách hàng',
          // tag: '9+1;3;6+7;8'
        }
      },
      {
        path: 'SearchDeliveryTicket',
        name: 'SearchDeliveryTicket',
        component: () => import('./SearchDeliveryTicket/SearchDeliveryTicket.vue'),
        meta: {
          title: 'Tra cứu danh sách xử lý phiếu',
          // tag: '9+1;3;6+7;8'
        }
      },
      {
        path: 'BlacklistAutocall',
        name: 'BlacklistAutocall',
        component: () => import('./BlacklistAutocall/index.js'),
        meta: {
          title: 'Import Blacklist Autocall',
        }
      },
	  {
        path: 'TraCuuTienDoHoTro',
        name: 'TraCuuTienDoHoTro',
        component: () => import('./TraCuuTienDoHoTro'),
        meta: {
          title: 'Tra cứu tiến độ hỗ trợ',
        }
      },
      {
        path: 'DangKyChiTieuDonHang',
        name: 'DangKyChiTieuDonHang',
        component: () => import('./DangKyChiTieu/index.vue'),
        meta: {
          title: 'Đăng ký chỉ tiêu đơn hàng',
        }
      },
      {
        path: 'LaborCostChannelMgmt',
        name: 'LaborCostChannelMgmt',
        component: () => import('./LaborCostChannelMgmt/LaborCostChannelMgmt.vue'),
        meta: {
          title: 'Import file tính cước nhân công',
          // tag: '9+1;3;6+7;8'
        }
      },
      {
        path: 'LaborCostChannelSubApproval',
        name: 'LaborCostChannelSubApproval',
        component: () => import('./LaborCostChannelSubApproval/LaborCostChannelSubApproval.vue'),
        meta: {
          title: 'Phê duyệt dữ liệu thuê bao kênh truyền tính cước nhân công',
          // tag: '9+1;3;6+7;8'
        }
      },
    ]
  }
]