import CompleteSurvey from './CompleteSurvey'
import ProcessOverdueNumber from './ProcessOverdueNumber'
import ChangeInstallUnit from './ChangeInstallUnit'
import CollectGeneratedAferInstall from './CollectGeneratedAferInstall'
import UpdateSurveyResult from './UpdateSurveyResult'
import DeclareServiceSpeedITSecure from '../contract/setup/DeclareServiceSpeedITSecure'

import LayoutMain from '@/layouts/LayoutMain'

export default [
  {
    path: '/install',
    name: 'install',
    component: LayoutMain,
    redirect: '/install/home',
    children: [
      {
        path: 'home',
        name: 'INSTALL home page',
        component: () => import('./Home'),
        meta: {
          title: 'Trang chủ'
        }
      },
      {
        path: 'CompleteSurvey',
        name: 'CompleteSurvey',
        component: CompleteSurvey,
        meta: {
          title: '[UR2.4.010] - Hoàn thành khảo sát'
        }
      },
      {
        path: 'ProcessOverdueNumber',
        name: 'ProcessOverdueNumber',
        component: ProcessOverdueNumber,
        meta: {
          title: '[UR2.4.047] - Xử lý thuê số theo hạn'
        }
      },
      {
        path: 'CollectGeneratedAferInstall',
        name: 'CollectGeneratedAferInstall',
        component: () => import('./CollectGeneratedAferInstall'),
        meta: {
          title: '[UR2.4.054] - Hoàn công thu tiền phát sinh'
        }
      },
      {
        path: 'ChangeGPhoneType',
        name: 'change-gphone-type',
        component: () => import('./ChangeGPhoneType/index.js')
      },
      {
        path: 'RecallUnusedPort',
        name: 'RecallUnusedPort',
        meta: { title: '[UR2.4.013] - Host - Thu hồi cổng không sử dụng' },
        component: () => import('./RecallUnusedPort/index.js')
      },
      {
        path: 'RecallUnusedPort/:tag',
        name: 'RecallUnusedPort',
        meta: { title: '[UR2.4.013] - Host - Thu hồi cổng không sử dụng' },
        component: () => import('./RecallUnusedPort/index.js')
      },
      {
        path: 'UpdateContractSignedResult',
        name: 'update-contract-signed-result',
        meta: { title: '[UR2.4.050] - Cập nhật kết quả ký HĐ tại địa chỉ KH' },
        component: () => import('./UpdateContractSignedResult/index.js')
      },
      {
        path: 'ChangeInstallUnit',
        name: 'changeinstallunit',
        component: () => import('./ChangeInstallUnit/index.js'),
        meta: {
          title: '[UR2.4.048]  - Thay đổi đơn vị thi công '
        }
      },
      {
        path: 'ChangeTSLSpeed',
        name: 'ChangeTSLSpeed',
        meta : {
          title: '[UR2.4.039] - Tổ KTVT - Thay đổi tốc độ TSL'
        },
        component: () => import('./ChangeTSLSpeed/index.js')
      },
      {
        path: 'BNMTemplate',
        name: 'BNMTemplate',
        meta: {
          title: '[UR2.4.121] - Khai báo template BNM'
        },
        component: () => import('./BNMTemplate/index.vue')
      },
      {
        path: 'InterDividePointInstallTSL',
        name: 'interDividePointInstallTSL',
        meta: {
          title: '[UR2.4.112] - Điểm chia trung gian - Thi công TSL'
        },
        component: () => import('./InterDividePointInstallTSL/index.js')
      },
      {
        path: 'DividePointChangeMyTVConfig',
        name: 'DividePointChangeMyTVConfig',
        meta: { title: '[UR2.4.090] - Điểm chia - Thay đổi thông số Mytv' },
        component: () => import('./DividePointChangeMyTVConfig/index')
      },
      {
        path: 'UpdateSurveyResult',
        name: 'UpdateSurveyResult',
        component: UpdateSurveyResult,
        meta: {
          title:'[UR2.4.049] - Cập nhật kết quả khảo sát'
        }
      },
      {
        path: 'HostProcessDebtSubs',
        name: 'hostProcessDebtSubs',
        meta: {
          title: '[UR2.4.023] Host - Khóa mở thuê bao nợ cước'
        },
        component: () => import('./HostProcessDebtSubs/index')
      },
      {
        path: 'MultipleChoiceQuestionsATVSLD',
        name: 'MultipleChoiceQuestionsATVSLD',
        meta:{
          title: '[UR2.3.015] - Câu hỏi trắc nghiệm ATVSLĐ'
        },
        component: () => import('./MultipleChoiceQuestionsATVSLD/MultipleChoiceQuestionsATVSLD.vue')
      },
      {
        path: 'DeliveryIntermediate',
        name: 'DeliveryIntermediate',
        meta:{
          title: '[UR2.3.017] - Giao phiếu điểm chia trung gian cấu hình TSL'
        },
        component: () => import('./setup/DeliveryIntermediate/DeliveryIntermediate.vue')
      },
      {
        path: 'DeliveryInstallationReceipt',
        name: 'DeliveryInstallationReceipt',
        meta : {
          title: '[UR2.3.008] - Giao phiếu tiêp nhận lắp đặt'
        },
        component: () => import('./DeliveryInstallationReceipt/index.vue')
      },
      {
        path: 'PotentialCustomers/:tag',
        name: 'PotentialCustomers',
        meta : {
          title: '[UR2.3.011] - Khách hàng tiềm năng'
        },
        component: () => import('./PotentialCustomers/index.vue')
      },
      {
        path: 'PotentialCustomers',
        name: 'PotentialCustomers',
        meta : {
          title: '[UR2.3.011] - Khách hàng tiềm năng'
        },
        component: () => import('./PotentialCustomers/index.vue')
      },
      {
        path: 'ChangeADSLSpeed/:tag',
        name: 'ChangeADSLSpeed',
        meta : {
          title: '[UR2.12.001] - Thay đổi tốc độ ADSL'
        },
        component: () => import('./ChangeADSLSpeed/index.vue')
      },
      {
        path: 'UpdateOrdersMyVNPTFreedoo',
        name: 'UpdateOrdersMyVNPTFreedoo',
        meta: { title: '[UR2.3.012] - Cập nhật đơn hàng MyVNPT, Freedoo - NV mặc định' },
        component: () => import('./UpdateOrdersMyVNPTFreedoo/index.vue')
      },
      {
        path: 'UpdateOrdersMyVNPTFreedooNVTC',
        name: 'UpdateOrdersMyVNPTFreedooNVTC',
        meta: { title: '[UR2.3.013] - Cập nhật đơn hàng MyVNPT, Freedoo - NVTC' },
        component: () => import('./UpdateOrdersMyVNPTFreedoo/index.vue')
      },
     
      {
        path: 'UpdateInstallProgress',
        name: 'UpdateInstallProgress',
        meta: { title: '[UR2.3.009] - Cập nhật tiến trình thi công' },
        component: () => import('./UpdateInstallProgress/index.vue')
      },
      {
        path: 'HandingOverConsultationContractSigning',
        name: 'HandingOverConsultationContractSigning',
        meta: { title: '[UR2.3.010] - Giao phiếu tư vấn / ký hợp đồng' },
        component: () => import('./HandingOverConsultationContractSigning/index.vue')
      },
      {
        path: 'ChangeADSLSpeed',
        name: 'ChangeADSLSpeed',
        meta : {
          title: '[UR2.12.001] - Thay đổi tốc độ ADSL'
        },
        component: () => import('./ChangeADSLSpeed/index.vue')
      },
      {
        path:'ChangeInternetSpeed_DBR',
        name: 'DeclareServiceSpeedITSecure',
        component: DeclareServiceSpeedITSecure,
        meta: {
          title:'[UR2.4.040] - Đài BR - Thay đổi tốc độ Internet'
        }
      },
      {
        path: 'AutoConfigurationDelivery',
        name: 'AutoConfigurationDelivery',
        component: () => import('./AutoConfigurationDelivery/index.vue')
      },
      {
        path: 'CompleteSurveyBS',
        name: 'CompleteSurveyBS',
        meta:{
          title: '[UR2.4.010] - Cập nhật hoàn thành khảo sát'
        },
        component: () => import('./CompleteSurveyBS/CompleteSurveyBS.vue')
      },
      {
        path: 'GetConstructionTicket',
        name: 'GetConstructionTicket',
        component: () => import('../contract/setup/ReturnInstallTicket/index.vue'),
        meta:{
          title: '[UR2.3.002] - Trả phiếu thi công'
        }
      },
      {
        path: 'ChangeParameterMegawanMetronet',
        name: 'ChangeParameterMegawanMetronet',
        component: () => import('@/modules/contract/setup/ChangeParameterMegawan'),
        meta: {
          title: '[UR2.5.031] - OMC - Thay đổi thông số Megawan/Metronet'
        }
      }, 
      //UR2.4.036
      {
        path: 'ChangeParameterMegawan_KT1',
        name: 'ChangeParameterMegawan_KT1',
        component: () => import('@/modules/contract/setup/ChangeParameterMegawan'),
        meta: {
          title: '[UR2.4.036] - Tổ KT1 - Thay đổi thông số Megawan'
        }
      },    
      {
        path: 'InstallTicketStatistics',
        name: 'InstallTicketStatistics',
        meta: {
          title: '[UR2.3.007] - Thống kê phiếu thi công'
        },
        component: () => import('@/modules/search/subscriber/SearchIncomeTicket/index.js')
      },  
    ]
  }
]
