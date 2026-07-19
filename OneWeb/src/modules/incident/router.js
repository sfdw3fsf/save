import LayoutMain from '@/layouts/LayoutMain'
import frmCapNhatNoiDungTruyenThong from '@/modules/incident/subscriber/ReceiveIncident/popups/frmCapNhatNoiDungTruyenThong/index.vue';
export default [
  {
    path: '/incident',
    name: 'incident',
    component: LayoutMain,
    children: [
      {
        path: 'ReceiveIncident',
        name: 'ReceiveIncident',
        component: () => import('./subscriber/ReceiveIncident'),
        meta: {
          title: '[UR4.1.001] - Tiếp nhận báo hỏng'
        }
      },
      {
        path: 'ReceiveIncident/:tag',
        name: 'ReceiveIncident',
        component: () => import('./subscriber/ReceiveIncident'),
        meta: {
          title: '[UR4.1.001] - Tiếp nhận báo hỏng'
        }
      },
      {
        path: 'HandoverIncident',
        name: 'handover-incident',
        component: () => import('./subscriber/HandoverIncident/index.js'),
        meta: {
          title: '[UR4.1.002] - Giao phiếu báo hỏng'
        }
      },
      {
        path: 'NotifTeleCenterIncident',
        name: 'NotifTeleCenterIncident',
        component: () => import('./subscriber/NotifTeleCenterIncident/index.js'),
        meta: { title: '[UR4.1.021] - Thông báo sự cố TTVT' }
      },
      {
        path: 'SearchIncidentUpdateAppointment',
        name: 'SearchIncidentUpdateAppointment',
        component: () => import('./subscriber/SearchIncidentUpdateAppointment/index.js'),
        meta: {
          title: '[UR4.1.027] - Tra cứu báo hỏng - cập nhật giờ hẹn '
        }
      },
      {
        path: 'SearchIncidentTicket',
        name: 'SearchIncidentTicket',
        component: () => import('./subscriber/SearchIncidentTicket/index.js'),
        meta: {
          title: '[UR4.1.030] - Tra cứu phiếu giao đến báo hỏng '
        }
      },
      {
        path: 'HandoverIncidentIntermediate',
        name: 'handover-incident-intermediate',
        component: () => import('./subscriber/HandoverIncidentIntermediate/index.vue'),
        meta: {
          title: '[UR4.1.018] - Giao phiếu điểm chia trung gian'
        }
      },
      {
        path: 'ProcessAcceptanceIncident',
        name: 'ProcessAcceptanceIncident',
        meta: {
          title: '[UR4.1.003] - [119->Điểm chia] - Xử lý/nghiệm thu báo hỏng'
        },
        component: () => import('./subscriber/ProcessAcceptanceIncident')
      },
      {
        path: 'CheckResult',
        name: 'CheckResult',
        meta: {
          title: '[UR4.1.007] - [VNPT-NET->OMC] - Kiểm tra kết quả'
        },
        component: () => import('./subscriber/ProcessAcceptanceIncident')
      },
      {
        path: 'HandleTransferIncidentReport',
        name: 'HandleTransferIncidentReport',
        meta: {
          title: '[UR4.1.004] - [Điểm chia->OMC] - Xử lý/chuyển phiếu báo hỏng'
        },
        component: () => import('./subscriber/ProcessAcceptanceIncident')
      },
      {
        path: 'ProcessIncident',
        name: 'ProcessIncident',
        meta: {
          title: '[UR4.1.011] - [119->Điểm chia] - Xử lý báo hỏng'
        },
        component: () => import('./subscriber/ProcessAcceptanceIncident')
      },
      {
        path: 'AcceptanceIncident',
        name: 'AcceptanceIncident',
        meta: {
          title: '[Điểm chia -> 119] - Nghiệm thu báo hỏng'
        },
        component: () => import('./subscriber/ProcessAcceptanceIncident')
      },
      {
        path: 'HandleIncedentReportsOMCDC',
        name: 'HandleIncedentReportsOMCDC',
        meta: {
          title: '[OMC -> Điểm chia] - Xử lý báo hỏng'
        },
        component: () => import('./subscriber/ProcessAcceptanceIncident')
      },
      {
        path: 'HandleIncedentReportsOMCNet',
        name: 'HandleIncedentReportsOMCNet',
        meta: {
          title: '[UR4.1.013] - [OMC->VNPT - NET] - Xử lý báo hỏng'
        },
        component: () => import('./subscriber/ProcessAcceptanceIncident')
      },
      {
        path: 'HandleIncedentReports119OMC',
        name: 'HandleIncedentReports119OMC',
        meta: {
          title: '[UR4.1.017] - [119 -> OMC] - Xử lý báo hỏng'
        },
        component: () => import('./subscriber/ProcessAcceptanceIncident')
      },
      {
        path: 'AcceptanceIncedentReportsDTDC',
        name: 'AcceptanceIncedentReportsDTDC',
        meta: {
          title: '[Đối tác->Điểm chia] - Nghiệm thu báo hỏng'
        },
        component: () => import('./subscriber/ProcessAcceptanceIncident')
      },
      {
        path: 'ProcessAcceptanceIncidentByTag',
        name: 'ProcessAcceptanceIncidentByTag',
        meta: {
          title: 'Xử lý báo hỏng'
        },
        component: () => import('./subscriber/ProcessAcceptanceIncident')
      },
      {
        path: 'HandleAcceptanceIncidentReports',
        name: 'HandleAcceptanceIncidentReports',
        component: () => import('./subscriber/HandleAcceptanceIncidentReports/HandleAcceptanceIncidentReports.vue'),
        meta: {
          title: '[UR4.1.009] - [OMC/Đối tác -> Điểm chia] - Xử lý/nghiệm thu báo hỏng'
        }
      },
      {
        path: 'UpdateProcessIncident',
        name: 'UpdateProcessIncident',
        component: () => import('./subscriber/UpdateProcessIncident/UpdateProcessIncident.vue'),
        meta: {
          title: '[UR4.1.005] - Cập nhật tiến trình báo hỏng'
        }
      },
      {
        path: 'ListSubcribersNotReceiveDamage',
        meta: {
          title: '[UR4.1.019] - Danh sách thuê bao không nhận hỏng'
        },
        component: () => import('./subscriber/ListSubcribersNotReceiveDamage/index')
      },
      {
        path: 'HandoverIncident',
        meta: {
          title: '[UR4.1.002] - Giao phiếu báo hỏng'
        },
        component: () => import('./subscriber/HandoverIncident/index')
      },
      {
        path: 'giao-phieu-bao-hong',
        meta: {
          title: '[DATNT] - Giao phiếu báo hỏng NEW'
        },
        component: () => import('./subscriber/GiaoPhieuBaoHong/index')
      },
      {
        path: 'VerifyAppointmentEntry',
        name: 'VerifyAppointmentEntry',
        component: () => import('./subscriber/VerifyAppointmentEntry/VerifyAppointmentEntry.vue'),
        meta: {
          title: 'Xác minh thông tin hẹn'
        }
      },
      {
        path: 'frmTienTrinhBH_DaiHTKH',
        name: 'frmTienTrinhBH_DaiHTKH',
        meta: {
          title: '[UR4.1.024] - Đài Quản lý tiến tình báo hỏng'
        },
        component: () => import('./subscriber/frmTienTrinhBH_DaiHTKH')
      },
      {
        path: 'ReceiveMisdirectionReport',
        name: 'ReceiveMisdirectionReport',
        component: () => import('./subscriber/ReceiveMisdirectionReport/ReceiveMisdirectionReport.vue'),
        meta: {
          title: 'Nhận phiếu báo hỏng lạc hướng'
        }
      },
      {
        path: 'WorkAssignment',
        name: 'WorkAssignment',
        component: () => import('./subscriber/WorkAssignment/WorkAssignment.vue'),
        meta: {
          title: 'BSS-38457 Quản lý phân công công việc'
        }
      },
      {
        path: 'SearchIncident',
        name: 'SearchIncident',
        component: () => import('./subscriber/SearchIncidentUpdateAppointment/index.js'),
        meta: {
          title: '[UR4.1.027] - Tra cứu báo hỏng '
        }
      },
      {
        path: 'IncidentMessageTTVTNet',
        name: 'IncidentMessageTTVTNet',
        meta: {
          title: '[UR4.1.023] - Thông báo sự cố TTVT - VNPT NET'
        },
        component: () => import('./subscriber/NotifTeleCenterIncident/index')
      },
      {
        path: 'IncidentMessageTTVTMedia',
        name: 'IncidentMessageTTVTMedia',
        meta: {
          title: '[UR4.1.025] - Thông báo sự cố TTVT - VNPT MEDIA'
        },
        component: () => import('./subscriber/NotifTeleCenterIncident/index')
      },
      {
        path: 'AcceptanceIncedentReportsOMCNet119',
        name: 'AcceptanceIncedentReportsOMCNet119',
        meta: {
          title: '[OMC, VNPT-NET -> 119] - Nghiệm thu báo hỏng'
        },
        component: () => import('./subscriber/AcceptanceIncedentReportsOMCNet119/AcceptanceIncedentReportsOMCNet119.vue')
      },
      {
        path: 'ReceiveIncidentCrossSale',
        name: 'ReceiveIncidentCrossSale',
        component: () => import('./subscriber/ReceiveIncidentCrossSale'),
        meta: {
          title: 'Tiếp nhận báo hỏng bán chéo'
        }
      },
      {
        path: 'ReceiveIncidentCrossSale/:tag',
        name: 'ReceiveIncidentCrossSale',
        component: () => import('./subscriber/ReceiveIncidentCrossSale'),
        meta: {
          title: 'Tiếp nhận báo hỏng bán chéo'
        }
      },
      {
        path: 'HandoverTicketIncidentCrossSale',
        name: 'HandoverTicketIncidentCrossSale',
        component: () => import('./subscriber/HandoverTicketIncidentCrossSale/index.js'),
        meta: {
          title: '[Bán chéo]: Giao phiếu phối hợp xử lý báo hỏng bán chéo'
        }
      },
      {
        path: 'HandleIncidentNetReports',
        name: 'HandleIncidentNetReports',
        component: () => import('./subscriber/HandleIncidentNetReports/HandleIncIdentNetReports.vue'),
        meta: {
          title: 'BSS-76289 -  - Xử báo hỏng'
        }
      },
      {
        path: 'MaloiDonvinhan',
        name: 'MaloiDonvinhan',
        component: () => import('./subscriber/MaloiDonvinhan/index.vue'),
        meta: {
          title: 'BSS-93702: Mã lỗi đơn vị nhận'
        }
      },
      {
        path: 'SearchIncidentBC',
        name: 'SearchIncidentBC',
        component: () => import('./subscriber/SearchIncidentUpdateAppointmentBC/index.js'),
        meta: {
          title: 'Tra cứu báo hỏng bán chéo '
        }
      },
	  {
        path: 'frmCapNhatNoiDungTruyenThong',
        name: 'frmCapNhatNoiDungTruyenThong',
        component: frmCapNhatNoiDungTruyenThong,
        meta: {
          title: 'Cập nhật nội dung Truyền thông',
          dialog: true,
          dialogfull: true
        }
      },
    ]
  }
]