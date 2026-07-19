import ResovleComplaintAfterSales from './ResovleComplaintAfterSales/ResovleComplaintAfterSales'
import SearchComplaintHistory from './SearchComplaintHistory'
import LayoutMain from '@/layouts/LayoutMain'
import SearchComplainForAgent from './SearchComplainForAgent'
export default [
  {
    path: '/complaint',
    name: 'complaint',
    component: LayoutMain,
    redirect: '/complaint/home',
    children: [
      {
        path: 'home',
        name: 'COMPLAINT home page',
        component: () => import('./Home'),
        meta: {
          title: 'Trang chủ'
        }
      },
      {
        path: 'HandoverComplaint',
        name: 'handover-complaint',
        component: () => import('./HandoverComplaint/index'),
        meta: {
          title: '[UR5.1.003] - Giao phiếu Xử lý khiếu nại'
      }
      },
      {
        path: 'SearchComplainDetail',
        name: 'SearchComplainDetail',
        component: () => import('./SearchComplainDetail/index'),
        meta: {
          title: '[UR5.1.013] - Tra cứu chi tiết xử lý khiếu nại'
        }
      },
      {
        path: 'SearchComplainForAgent',
        name: 'SearchComplainForAgent',
        component: SearchComplainForAgent,
        meta: {
            title: '[UR5.1.015] - Tra cứu khiếu nại - Đại lý_SearchComplainForAgent'
        }
      },
      {
        path: 'SearchComplaintHistory',
        name: 'SearchComplaintHistory',
        component: ()=>import('./SearchComplaintHistory'),
        meta: {
          title: '[UR5.1.014] - Tra cứu lịch sử khiếu nại'
        }
      },
      {
        path: 'ReadUpdateFileComplaint',
        name: 'ReadUpdateFileComplaint',
        component: () => import('./ReadUpdateFileComplaint'),
        meta: {
          title: '[UR5.1.012] - Đọc file cập nhật chờ xử lý khiếu nại'
        }
      },
      {
        path: 'ReadReturnComplaintForm',
        name: 'ReadReturnComplaintForm',
        component: () => import('./ReadReturnComplaintForm'),
        meta: {
          title: '[UR5.1.011] - Đọc file trả phiếu khiếu nại'
        }
      },
      { 
        path: 'ResovleComplaintAfterSales', 
        name: 'ResovleComplaintAfterSales', 
        meta: { title: '[UR5.1.007] - Đại lý - Xử lý khiếu nại', tag: '2+2257' }, 
        component: () => import('./ResovleComplaintAfterSales/index.js') 
      }, 
      { 
        path: 'SolveComplain_NVC', 
        name: 'SolveComplain_NVC', 
        meta: { title: '[UR5.1.008] -Phòng nghiệp vụ - Xử lý khiếu nại', tag: '2+2260' }, 
        component: () => import('./ResovleComplaintAfterSales/index.js') 
      }, 
      { 
        path: 'SolveComplain_TNH', 
        name: 'SolveComplain_TNH', 
        meta: { title: '[UR5.1.009] -Tổ thu ngân hàng - Xử lý khiếu nại', tag: '2+2264' }, 
        component: () => import('./ResovleComplaintAfterSales/index.js') 
      }, 
      { 
        path: 'SolveComplain_KD', 
        name: 'SolveComplain_KD', 
        meta: { title: '[UR5.1.010] -Tổ Kinh Doanh - Xử lý khiếu nại', tag: '2+2268' }, 
        component: () => import('./ResovleComplaintAfterSales/index.js') 
      },
      {
        path: 'ReceiveCustomerFeedback',
        name: 'ReceiveCustomerFeedback',
        meta: { title: 'Tiếp nhận xử lý phản hồi khách hàng' },
        component: () => import('./ReceiveCustomerFeedback/ReceiveCustomerFeedback.js')
      },
      {
        path: 'ReceiveCustomerFeedback/:tag',
        name: 'ReceiveIncident',
        meta: { title: 'Tiếp nhận xử lý phản hồi khách hàng' },
        component: () => import('./ReceiveCustomerFeedback/ReceiveCustomerFeedback.js')
      },
    ]
  }
]
