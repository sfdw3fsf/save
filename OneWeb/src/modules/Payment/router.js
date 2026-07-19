import LayoutMain from '@/layouts/LayoutMain'
import ReceiveMoneyPostPaid from './ReceiveMoneyPostPaid/ReceiveMoneyPostPaid'

export default [
  {
    path: '/Payment',
    name: 'Payment',
    component: LayoutMain,
    redirect: '/Payment/home',
    children: [
      {
        path: 'home',
        name: 'PAYMENT home page',
        component: () => import('./Home'),
        meta: {
          title: 'Trang chủ'
        }
      },
      {
        path: 'ExportInvoice',
        name: 'ExportInvoice',
        meta:{
          title: 'Xuất hoá đơn điện tử'
        },
        component: () => import('./ExportInvoice')
      },
      {
        path: 'eInvoiceAdmin',
        name: 'eInvoiceAdmin',
        component: () => import('./eInvoice'),
        meta: {
          title: '[UR2.2.011] - Hóa đơn điện tử - admin'
        }
      },
      {
        path: 'ProactiveEinvoiceIssuance',
        name: 'ProactiveEinvoiceIssuance',
        component: () => import('./ProactiveEinvoiceIssuance'),
        meta: {
          title: '[UR2.2.014] - Phát hành hóa đơn điện tử chủ động - VTT'
        }
      },
      {
        path: 'ReceiveList',
        name: 'ReceiveList',
        meta: {
          title: '[UR2.2.007] - Nhận bảng kê'
        },
        component: () => import('./ReceiveList')
      },
      {
        path: 'IssuanceElectronicInvoices',
        name: 'IssuanceElectronicInvoices',
        component: () => import('./IssuanceElectronicInvoices'),
        meta: {
          title: '[UR2.2.013] - Phát hành hóa đơn điện tử - file'
        }
      },
      {
        path: 'ReceiveMoneyPostPaid',
        name: 'ReceiveMoneyPostPaid',
        component: ReceiveMoneyPostPaid,
        meta: {
          title: '[UR2.2.009] - Nhận tiền trả sau'
        }
      },
      {
        path: 'CancellationContract',
        name: 'CancellationContract',
        meta: {
          title: '[UR2.2.003] - Thoái trả hợp đồng'
        },
        component: () => import('./CancellationContract')
      },
      {
        path: 'ListOfPaymentList',
        name: 'ListOfPaymentList',
        meta: {
          title: '[UR2.2.002] - Danh sách phiếu thanh toán'
        },
        component: () => import('./ListOfPaymentList')
      },
      {
        path: 'SelfManageEinvoice',
        name: 'self-manage-einvoice',
        component: () => import('./SelfManageEinvoice/index.vue')
      },
      {
        path: 'CreateList',
        name: 'CreateList',
        meta: {
          title: '[UR2.2.006] - Tạo bảng kê'
        },
        component: () =>
          import(
            "./CreateList/CreateList.vue"
          )
      },
      {
        path: 'PopupHDDT',
        name: 'popup',
        props: route => ({ query: route.query.tag }),
        meta:{
          title: '[UR2.2.010] - HDDT'
        },
        component: () => import('./eInvoice/PopupHDDT.vue')
      },
      {
        path: 'eInvoice',
        name: 'eInvoice',
        component: () => import('./eInvoice'),
        meta: {
          title: 'Trang chủ'
        }
      },
      {
          path: 'EditPaymentInvoice',
          name: 'EditPaymentInvoice',
          meta: {
              title: '[UR2.2.008] - EditPaymentInvoice'
          },
          component: () =>
              import ('./EditPaymentInvoice/index.js')
      },
      {
        path: 'UpdateReceiptOfReceipts/:tag',
        name: 'UpdateReceiptOfReceipts',
        meta:{
          title: '[UR2.2.016] - Cập nhật biên lai thu tiền'
        },
        component: () => import('./UpdateReceiptOfReceipts/index.vue')
      },
      {
        path: 'UpdateReceiptOfReceipts',
        name: 'UpdateReceiptOfReceipts',
        meta:{
          title: '[UR2.2.016] - Cập nhật biên lai thu tiền'
        },
        component: () => import('./UpdateReceiptOfReceipts/index.vue')
      },
      {
        path: 'IssuingReceiptsCollection',
        name: 'IssuingReceiptsCollection',
        meta:{
          title: '[UR2.2.015] - Phát hành biên lai thu tiền'
        },
        component: () => import('./IssuingReceiptsCollection/index.vue')
      },
      {
        path: 'IssuingReceiptsCollection/:tag',
        name: 'IssuingReceiptsCollection',
        meta:{
          title: '[UR2.2.015] - Phát hành biên lai thu tiền'
        },
        component: () => import('./IssuingReceiptsCollection/index.vue')
      },
      {
        path: 'AddInvoiceInformationVNPT',
        name: 'AddInvoiceInformationVNPT',
        meta:{
          title: '[frmThongTinHD_BS_VNPTI] - Bổ sung thông tin hóa đơn'
        },
        component: () => import('./AddInvoiceInformationVNPT/index.js')
      },
      {
        path: 'DocumentManagement',
        name: 'DocumentManagement',
        meta:{
          title: 'Quản lý chứng từ'
        },
        component: () => import('./DocumentManagement/index.js')
      },
      // {
      //   path: 'thuanTest',
      //   name: 'thuanTest',
      //   meta:{
      //     title: 'Test'
      //   },
      //   component: () => import('./thuanTest/thongtinHDon.vue')
      // },
      // {
      //   path: '/products',
      //   name: 'product-list',
      //   component: () => import('./thuanTest/listProduct.vue')
      // },
      // {
      //   path: '/products/create',
      //   name: 'product-form',
      //   component: () => import('./thuanTest/index.vue')
      // },
      // {
      //   path: '/products/edit/:id',
      //   name: 'product-edit',
      //   component: () => import('./thuanTest/index.vue')
      // }
      {
        path: 'phe-duyet-hoa-don-dat-coc',
        name: 'PheDuyHoaDonDatCoc',
        meta:{
          title: 'Phê duyệt hóa đơn đặt cọc'
        },
        component: () => import('./PheDuyHoaDonDatCoc')
      },
    ]
  }
]
