import LayoutMain from '@/layouts/LayoutMain'
import CreateProfile from './profile/CreateProfile/index.vue'
import ChangeInstallUnit from './setup/ChangeInstallUnit'
import ExceptionBusinessSubs from './setup/ExceptionBusinessSubs'
import AssignAgentHomeCombo from './setup/AssignAgentHomeCombo'
import CompleteChangeSpeedProfile from './setup/CompleteChangeSpeedProfile'
import ChangeIntroducePeople from './setup/ChangeIntroducePeople'
import MonitorTrialContract from './setup/MonitorTrialContract'
import ChangeSubsType from './setup/ChangeSubsType'
import DeclareMetronetByOMC from './setup/DeclareMetronetByOMC'
import DeclareMegawanByOMC from './setup/DeclareMegawanByOMC'
import SurveyRequest from './setup/SurveyRequest'
import DeclareServiceSpeedITSecure from './setup/DeclareServiceSpeedITSecure'
import ProcessPotentialCustomers from './setup/ProcessPotentialCustomers'
import DistributingDocumentsCustomers from './setup/DistributingDocumentsCustomers'
import ListOfSurveyRequest from './setup/ListOfSurveyRequest'
import ExportData from './profile/ExportData'
import ChangeDecoder from './setup/ChangeDecoder'
import DeleteUnpaidContract from './profile/DeleteUnpaidContract'
import DangKyGoiDaDichVu from './setup/ChangeSubsType/DangKyGoiDaDichVu'
import SurveyRequestCrossSale from './setup/SurveyRequestCrossSale'
import RegisterBusinessNumberRange from './RegisterBusinessNumberRange'
import MyaccountPasswordLookup from './MyaccountPasswordLookup'
import UpdateMobileSubsInformation from './setup/UpdateMobileSubsInformation'
import ChangeSubsTypeCrossSale from './setup/ChangeSubsTypeCrossSale'
import LockUnlockPayTV from './LockUnlockPayTV/LockUnlockPayTV.vue'
import SyncDataVTN from './SyncData/SyncDataVTN'
export default [
  {
    path: '/contract',
    name: 'contract',
    component: LayoutMain,
    redirect: '/contract/home',
    children: [
      {
        path: 'home',
        name: 'CONTRACT home page',
        component: () => import('./Home'),
        meta: {
          title: 'Trang chủ'
        }
      },
      {
        path: 'ChangeCustomerInfo',
        name: 'ChangeCustomerInfo',
        meta: {
          title: '[UR2.1.004] - THAY ĐỔI THÔNG TIN KHÁCH HÀNG'
        },
        component: () => import('./profile/ChangeCustomerInfo')
      },
      {
        path: 'CustomerCareOB',
        name: 'CustomerCareOB',
        meta: {title: '[KHDN-1xxxx] - OB chăm sóc khách hàng'},
        component: () => import('./setup/OB_SMARTCA_Function/index.js')
      },
      {
        path: 'PayLaterNoTempFee',
        name: 'PayLaterNoTempFee',
        meta: {title: '[BSS-117893] - Chuyển trả sau không tạm thu'},
        component: () => import('./setup/PayLaterNoTempFee/index.js')
      },
      {
        path: 'ApproveBlockPrepaidSub',
        name: 'ApproveBlockPrepaidSub',
        meta: {title: '[BSS-118977] - Phê duyệt yêu cầu khóa cưỡng bức TB trả trước'},
        component: () => import('./setup/ApproveBlockPrepaidSub/index.js')
      },
      {
        path: 'ChangeModem',
        name: 'ChangeModem',
        meta: {
          title: '[UR2.1.043] - Thay thế vật tư - Đổi modem Wifi'
        },
        component: () => import('./setup/ChangeModem/ChangeModem.vue')
      },
      // {
      //   path: 'print-payment-receipts',
      //   name: 'print-payment-receipts',
      //   component: () => import('./payment/PrintPaymentReceipts/index.vue')
      // },
      // {
      //   path: 'update-promotion-private',
      //   name: 'update-promotion-private',
      //   component: () => import('./setup/UpdatePromotionPrivate/index.vue')
      // },
      // {
      //   path: 'collect-incurred-debt',
      //   name: 'collect-incurred-debt',
      //   meta: {
      //     title: "[UR2.1.015] - Thu tiền phát sinh"
      //   },
      //   component: () => import('./setup/CollectIncurredDebt/index.vue')
      // },
      // {
      //   path: 'DeclareLandline',
      //   name: 'DeclareLandline',
      //   component: () => import('./setup/DeclareLandline/index.vue'),
      //   meta:{
      //     title: '[UR2.5.001] - Khai báo Cố định'
      //   }
      // },
      // {
      //   path: 'ReturnInstallTicket',
      //   name: 'ReturnInstallTicket',
      //   component: () => import('./setup/ReturnInstallTicket/index.vue'),
      //   meta:{
      //     title: '[UR2.3.002] - Trả phiếu thi công'
      //   }
      // },
      // {
      //   path: 'RegMultiServicePackage',
      //   name: 'RegMultiServicePackage',
      //   component: () => import('./setup/RegMultiServicePackage/index.vue'),
      //   meta:{
      //     title: '[UR2.1.017] - Đăng ký gói đa dịch vụ'
      //   }
      // },
      // // {
      // //   path: 'HandoverTicket_KSTH',
      // //   name: 'HandoverTicket_KSTH',
      // //   component: () => import('./profile/HandoverTicket_KSTH'),
      // //   meta: {
      // //     title: '[UR2.9.002] - Giao phiếu cho trạm KSTH'
      // //   }
      // // },
      // {
      //   path: 'HandoverTicket',
      //   name: 'HandoverTicket',
      //   component: () => import('./setup/HandoverTicket'),
      //   meta: {
      //     title: '[UR2.3.001] - Giao phiếu'
      //   }
      // },
      // {
      //   path: 'SeparationSubscriber',
      //   name: 'SeparationSubscriber',
      //   component: () => import('./setup/SeparationSubscriber'),
      //   meta: {
      //     title: '[UR2.1.031] - Tách nhập thuê bao'
      //   }
      // },
      // {
      //   path: 'InstallInternet/:tag',
      //   name: 'InstallInternet',
      //   component: () => import('./setup/InstallInternet'),
      //   meta: {
      //     title: '[UR2.4.007] - Tổ KTVT - Thi công Internet'
      //   }
      // },
      {
        path: 'SubcriberHistory',
        name: 'SubcriberHistory',
        component: () => import('./complete_profile/SubcriberHistory'),
        meta: {
          title: '[UR4.1.028] - Lý lịch thuê bao'
        }
      },
      {
        path: 'CompleteProfileChangeTSMyTV',
        name: 'CompleteProfileChangeTSMyTV',
        meta: {
          title: '[UR2.6.015] - Hoàn thiện hồ sơ thay đổi TS MyTV'
        },
        component: () => import('./complete_profile/CompleteProfileChangeTSMyTV/index.js')
      },
      {
        path: 'UpdateBusinessPrepaidInfor',
        name: 'update-business-prepaid-infor',
        meta: {
          title: '[UR2.11.009] - Cập nhật thông tin trả trước doanh nghiệp'
        },
        component: () => import('./setup/UpdateBusinessPrepaidInfor/index.vue')
      },
      {
        path: 'ChangeSubscriberPort',
        name: 'ChangeSubscriberPort',
        component: () => import('./profile/ChangeSubscriberPort'),
        meta: {
          title: '[UR1.5.020] - Đổi port thuê bao'
        }
      },
      {
        path: 'MarketCapacityUpdate',
        name: 'MarketCapacityUpdate',
        component: () => import('./profile/MarketCapacityUpdate'),
        meta: {
          title: '[UR1.5.025] - Cập nhật dung lượng thị trường'
        }
      },
      {
        path: 'InstallInternet',
        name: 'InstallInternet',
        component: () => import('./setup/InstallInternet'),
        meta: {
          title: '[UR2.4.007] - Tổ KTVT - Thi công Internet'
        }
      },
      {
        path: 'RegMultiServicePackage',
        name: 'RegMultiServicePackage',
        component: () => import('./setup/RegMultiServicePackage/index.js'),
        meta: {
          title: '[UR2.1.017] - Đăng ký gói đa dịch vụ'
        }
      },
      {
        path: 'TraCuuTBC',
        name: 'TraCuuTBC',
        component: () => import('./setup/TraCuuTBC/index.js'),
        meta: {
          title: 'Tra cứu thông tin nhận thông báo cước'
        }
      },
      {
        path: 'ReturnInstallTicket',
        name: 'ReturnInstallTicket',
        component: () => import('./setup/ReturnInstallTicket/index.vue'),
        meta: {
          title: '[UR2.3.002] - Trả phiếu thi công'
        }
      },
      {
        path: 'ReceiveConsultingContract',
        name: 'ReceiveConsultingContract',
        component: () => import('./setup/ReceiveConsultingContract'),
        meta: {
          title: 'Tiếp nhận hợp đồng tư vấn'
        }
      },
      {
        path: 'ChangeInstallUnit',
        name: 'ChangeInstallUnit',
        component: ChangeInstallUnit,
        meta: {
          title: '[UR2.7.001] - Sửa đơn vị thi công'
        }
      },
      {
        path: 'ExceptionBusinessSubs',
        name: 'ExceptionBusinessSubs',
        component: ExceptionBusinessSubs,
        meta: {
          title: '[UR2.1.079] - Quản lý thuê bao ngoại lệ KHDN'
        }
      },
      {
        path: 'AssignAgentHomeCombo',
        name: 'AssignAgentHomeCombo',
        component: AssignAgentHomeCombo,
        meta: {
          title: '[UR2.1.061] - Gán CTV phát triển gói GD/VP'
        }
      },
      {
        path: 'CompleteChangeSpeedProfile',
        name: 'CompleteChangeSpeedProfile',
        component: DeclareServiceSpeedITSecure, //CompleteChangeSpeedProfile,
        meta: {
          title: '[UR2.6.007] - Hoàn thiện hồ sơ thay đổi tốc độ Internet'
        }
      },
      {
        path: 'ChangeIntroducePeople',
        name: 'ChangeIntroducePeople',
        component: ChangeIntroducePeople,
        meta: {
          title: '[UR2.3.014] - Điều chỉnh thông tin người giới thiệu'
        }
      },
      {
        path: 'CreateProfile',
        name: 'CreateProfile',
        component: CreateProfile,
        meta: {
          title: '[UR2.9.001] - Tạo bộ hồ sơ'
        }
      },
      {
        path: 'HandoverTicket_KSTH',
        name: 'HandoverTicket_KSTH',
        component: () => import('./profile/HandoverTicket_KSTH/HandoverTicket_KSTH.vue'),
        meta: {
          title: '[UR2.9.002] - Giao phiếu cho trạm KSTH'
        }
      },
      {
        path: 'SwitchboardCommand',
        name: 'SwitchboardCommand',
        component: () => import('./setup/SwitchboardCommand'),
        meta: { title: '[UR2.5.017] - Khai báo lệnh tổng đài' }
      },
      {
        path: 'ManageProfileStore',
        name: 'ManageProfileStore',
        component: () => import('./profile/ManageProfileStore'),
        meta: {
          title: '[UR2.9.006] - Quản lý kho hồ sơ'
        }
      },
      {
        path: 'ManageSelfcareContract',
        name: 'manageselfcarecontract',
        meta: {
          title: '[UR2.1.069] - Quản lý HÐ Self Care'
        },
        component: () => import('./setup/ManageSelfcareContract/index.js')
      },
      {
        path: 'UpdatePromotionPrivate',
        name: 'update-promotion-private',
        component: () => import('../admin/category/PreferentialSubscriber/index.js')
      },
      {
        path: 'ReceiveProfile',
        name: 'receiveProfile',
        meta: {
          // title: '[UR2.4.112] - Điểm chia trung gian - Thi công TSL'
          title: '[UR2.9.008] - Tiếp nhận Hồ sơ và giao việc'
        },
        component: () => import('./profile/receiveProfile/index.js')
      },
      {
        path: 'Liquidate',
        name: 'liquidate',
        meta: {
          title: '[UR2.1.006] - Thanh lý, hủy dịch vụ'
        },
        component: () => import('./setup/Liquidate/index.js')
      },
      {
        path: 'RestoreLiquidationSubs',
        name: 'RestoreLiquidationSubs',
        meta: {
          title: '[UR2.1.007] - Khôi phục thuê bao thanh lý'
        },
        component: () => import('./setup/RestoreLiquidationSubs/index.js')
      },
      {
        path: 'DeclareMPLS',
        name: 'declareMPLS',
        meta: {
          title: '[UR2.5.010] - Khai báo Truyền số liệu'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'ChangeMyTVParameter',
        name: 'changeMyTVParameter',
        meta: {
          title: '[UR2.1.036] - Thay đổi thông số MyTV'
        },
        component: () => import('./setup/ChangeMyTVParameter/index.js')
      },
      {
        path: 'InstallNewSubs',
        name: 'InstallNewSubs',
        meta: {
          title: '[UR2.1.004] - [Lập hợp đồng]: Lắp đặt mới'
        },
        component: () => import('./setup/InstallNewSubs')
      },
      {
        path: 'UpdateNewPromotion',
        name: 'UpdateNewPromotion',
        meta: {
          title: '[UR2.7.002] - Sửa khuyến mại lắp mới'
        },
        component: () => import('./setup/InstallNewSubs')
      },
      {
        path: 'MonitorTrialContract',
        name: 'MonitorTrialContract',
        component: MonitorTrialContract,
        meta: {
          title: '[UR2.1.065] - Theo dõi HĐ dùng thử'
        }
      },
      {
        path: 'ChangeMegaWanConfig',
        name: 'ChangeMegaWanConfig',
        meta: {
          title: '[UR2.1.033] - Thay đổi thông số MegaWan'
        },
        component: () => import('./setup/ChangeMegaWanConfig/index.js')
      },
      {
        path: 'ChangeInternetSpeed',
        name: 'ChangeInternetSpeed',
        meta: {
          title: 'UR2.1.032 - Thay đổi tốc độ Internet'
        },
        component: () => import('./setup/ChangeInternetSpeed/index.js')
      },
      {
        path: 'UpdateLogThread',
        name: 'UpdateLogThread',
        component: () => import('./setup/UpdateLogThread/index.js'),
        meta: {
          title: 'UR2.3.016 - Cập nhật thông tin xử lý tiến trình TC/ BH'
        }
      },
      {
        path: 'DeclareLandline',
        name: 'DeclareLandline',
        component: () => import('./setup/DeclareLandline/index.vue'),
        meta: {
          title: '[UR2.5.001] - Khai báo Cố định'
        }
      },
      {
        path: 'Payment',
        name: 'Payment',
        meta: {
          title: '[UR2.2.001] - Tiền hợp đồng'
        },
        component: () => import('./setup/Payment/index.js')
      },
      {
        path: 'HandoverTicket',
        name: 'HandoverTicket',
        component: () => import('./setup/HandoverTicket'),
        meta: {
          title: '[UR2.3.001] - Giao phiếu'
        }
      },
      {
        path: 'ProfileChangeService',
        name: 'profileChangeService',
        meta: {
          title: '[UR2.4.018] - Hoàn thiện hồ sơ Thay đổi dịch vụ'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'CompleteProfileChangeService',
        name: 'completeProfileChangeService',
        meta: {
          title: '[UR2.6.022] - Hoàn thiện hồ sơ Thay đổi dịch vụ'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'CompleteProfileCrosssellChangeVASService',
        name: 'completeProfileCrosssellChangeVASService',
        meta: {
          title: '[UR2.6.064] - HTHS Thay đổi dịch vụ gia tăng tỉnh khác (Bán chéo)'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'ProcessServiceByTeleStation',
        name: 'ProcessServiceByTeleStation',
        meta: {
          title: '[UR2.4.081] - (*)Trạm VT - Khóa mở dịch vụ'
        },
        component: () => import('./setup/ProcessServiceByTeleStation/index.js')
      },
      {
        path: 'ChangeContractOwner',
        name: 'ChangeContractOwner',
        meta: {
          title: '[UR2.2.030] - [Lập hợp đồng]: Chuyển quyền'
        },
        component: () => import('./setup/ChangeContractOwner/index.js')
      },
      {
        path: 'CompleteProfileLiquidation',
        name: 'CompleteProfileLiquidation',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.6.004] - Hoàn thiện hồ sơ Thanh lý',
          tag: '1+4+10;1;13;14;15;16;50'
        }
      },
      {
        path: 'ChangeSubsType',
        name: 'ChangeSubsType',
        component: ChangeSubsType,
        meta: {
          title: 'UR2.1.035 - Thay đổi loại hình thuê bao'
        }
      },
      {
        path: 'DeclareIMS',
        name: 'declareIMS',
        meta: {
          title: '[UR2.5.015] - Khai báo Ims'
        },
        props: {
          tagForm: {
            tthd_id: 9, // TODO: fixed = 4 để test, thực tế form này = 9
            dsloaihd_id: '1,3,30',
            dsdichvuvt_id: '11',
            flagTSKT: '',
            flagTTTB_IMS: 0
          }
        },
        component: () => import('./setup/DeclareIMS/index.js')
      },
      {
        path: 'SeparationSubscriber',
        name: 'SeparationSubscriber',
        component: () => import('./setup/SeparationSubscriber/SeparationSubscriber.vue'),
        meta: {
          title: '[UR2.1.031] - Tách nhập thuê bao'
        }
      },
      {
        path: 'SurveyRequest',
        name: 'SurveyRequest',
        component: SurveyRequest,
        meta: {
          title: '[UR2.1.001] - Tiếp nhận yêu cầu khảo sát lắp đặt'
        }
      },
      {
        path: 'SetupChangeTSLSpeed',
        name: 'SetupChangeTSLSpeed',
        component: () => import('./setup/SetupChangeTSLSpeed/index.vue'),
        meta: {
          title: '[UR2.1.034] - Thay đổi tốc độ TSL'
        }
      },
      {
        path: 'CompleteProfileChangeAccount',
        name: 'CompleteProfileChangeAccount',
        meta: {
          title: '[UR2.6.019] - Hoàn thiện hồ sơ Đổi số / Account',
          tag: '5+14+1;4;11'
        },
        component: () => import('./profile/CompleteProfileChangeAccount/index.js')
      },
      {
        path: 'CompleteProfileByManager',
        name: 'completeProfileByManager',
        meta: {
          title: '[UR2.9.005] -(*) QLK hoàn thiện'
        },
        component: () => import('./profile/CompleteProfileByManager')
      },
      {
        path: 'ChangeModem',
        name: 'ChangeModem',
        meta: {
          title: '[UR2.1.043] - Thay thế vật tư - Đổi modem Wifi'
        },
        component: () => import('./setup/ChangeModem/ChangeModem.vue')
      },
      {
        path: 'ListUnfinishProfile',
        name: 'ListUnfinishProfile',
        component: () => import('./setup/ListUnfinishProfile/ListUnfinishProfile.vue'),
        meta: {
          title: '[UR2.1.058] - Danh sách hồ sơ chờ lập HĐ/PL'
        }
      },
      {
        path: '800126InquiryWatchList',
        name: '800126InquiryWatchList',
        component: SurveyRequest,
        meta: {
          title: '[UR2.1.057] - 80126- Tiếp nhận yêu cầu'
        }
      },
      {
        path: 'ReceiveSurveyRequestChangeType',
        name: 'ReceiveSurveyRequestChangeType',
        component: SurveyRequest,
        meta: {
          title: '[UR2.1.002] - Tiếp nhận yêu cầu khảo sát thay đổi loại hình'
        }
      },
      {
        path: 'ReceiveRequest',
        name: 'ReceiveRequest',
        component: SurveyRequest,
        meta: {
          title: '[UR2.1.055] - Tiếp nhận yêu cầu'
        }
      },
      {
        path: 'DeclareServiceSpeedITSecure',
        name: 'DeclareServiceSpeedITSecure',
        component: DeclareServiceSpeedITSecure,
        meta: {
          title: '[UR2.6.042] - Khai báo thay đổi tốc độ dịch vụ CNTT - ATBMTT-TTDL'
        }
      },
      {
        path: 'ChangeMultiServicePackage',
        name: 'ChangeMultiServicePackage',
        meta: {
          title: '[UR2.1.018] - Thay đổi gói da dịch vụ'
        },
        component: () => import('./setup/ChangeMultiServicePackage/index.js')
      },
      {
        path: 'ChangeSubsTypeByInstallOperation',
        name: 'changeSubsTypeByInstallOperation',
        meta: {
          title: '[UR2.4.021] - ĐHTC - Thay đổi loại hình thuê bao'
        },
        component: () => import('./setup/ChangeSubsTypeByInstallOperation')
      },
      {
        path: 'ChangeLandlineIMSType',
        name: 'ChangeLandlineIMSType',
        meta: {
          title: '[UR2.1.037] - Thay đổi loại hình thuê bao(cố định <-> IMS)'
        },
        component: () => import('./setup/ChangeLandlineIMSType')
      },
      {
        path: 'CompleteProfileChangeSubsType',
        name: 'completeProfileChangeSubsType',
        meta: {
          title: '[UR2.6.012] - Hoàn thiện hồ sơ thay đổi loại hình thuê bao'
        },
        component: () => import('./complete_profile/CompleteProfileChangeSubsType')
      },
      {
        path: 'AcceptContractModification',
        name: 'AcceptContractModification',
        meta: {
          title: '[UR2.7.006] - Tiếp nhận hiệu chỉnh hợp đồng'
        },
        component: () => import('./profile/AcceptContractModification/index.js')
      },
      {
        path: 'ConfirmContractModify_Account',
        name: 'ConfirmContractModify_Account',
        meta: {
          title: '[UR2.7.007] - Kế toán - Xác nhận hiệu chỉnh hợp đồng'
        },
        component: () => import('./profile/AcceptContractModification/index.js')
      },
      {
        path: 'CompleteProfileChangeNumber',
        name: 'CompleteProfileChangeNumber',
        meta: {
          title: '[UR2.6.019] - Hoàn thiện hồ sơ Đổi số / Account',
          tag: '5+14+1;4;11'
        },
        component: () => import('./profile/CompleteProfileChangeAccount/index.js')
      },
      {
        path: 'DoiAccountInternet',
        name: 'DoiAccountInternet',
        meta: {
          title: '[UR2.4.042] - Tổ KTVT - Đổi Account Internet',
          tag: '4+14+4'
        },
        component: () => import('./profile/CompleteProfileChangeAccount/index.js')
      },
      {
        path: 'ChangeNumberLandline_KT',
        name: 'ChangeNumberLandline_KT',
        meta: {
          title: '[UR2.4.043] - Tổ Khai thác - Đổi số cố định/ISDN/IMS',
          tag: '9+14+1;11'
        },
        component: () => import('./profile/CompleteProfileChangeAccount/index.js')
      },
      {
        path: 'ChangeNumberLandline_KTVT',
        name: 'ChangeNumberLandline_KTVT',
        meta: {
          title: '[UR2.4.044] - Tổ KTVT - Đổi số cố định',
          tag: '4+14+1'
        },
        component: () => import('./profile/CompleteProfileChangeAccount/index.js')
      },
      {
        path: 'HandoverCompleteProfile',
        name: 'HandoverCompleteProfile',
        component: () => import('./setup/HandoverCompleteProfile/HandoverCompleteProfile.vue'),
        meta: {
          title: '[UR2.9.003] - Giao cho HTHS'
        }
      },
      {
        path: 'OperateNewInstallITService',
        name: 'OperateNewInstallITService',
        meta: {
          title: '[UR2.6.048] - Điều hành lắp mới dịch vụ CNTT'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'InstallITService',
        name: 'InstallITService',
        meta: {
          title: '[UR2.4.063] - Trạm - Thi công DV CNTT',
          des: '/#/contract/InstallITService'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'VoiceBrandname',
        name: 'VoiceBrandname',
        meta: {
          title: 'VoiceBrandname'
        },
        component: () => import('./setup/VoiceBrandname/index.js')
      },
      {
        path: 'CompleteProfileITVAS',
        name: 'CompleteProfileITVAS',
        meta: {
          title: '[UR2.6.045] - HTHS dịch vụ CNTT/GTGT'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'VoiceBrandname/list',
        name: 'danhSachSoThanhVien',
        meta: {
          title: 'Danh sách số thành viên'
        },
        component: () => import('./setup/VoiceBrandname/part/DanhSachThanhVien.vue')
      },
      {
        path: 'HandoverInventoryManagement',
        name: 'HandoverInventoryManagement',
        component: () => import('./setup/HandoverInventoryManagement/HandoverInventoryManagement.vue'),
        meta: {
          title: '[UR2.9.004] - Giao cho QLK'
        }
      },
      {
        path: 'DeleteLiquidationSubscription',
        name: 'DeleteLiquidationSubscription',
        component: () => import('./profile/DeleteLiquidationSubscription/DeleteLiquidationSubscription.vue'),
        meta: {
          title: '[UR1.5.001] - Xóa thuê bao thanh lý'
        }
      },
      {
        path: 'SubscribePrepaidRenewals',
        name: 'SubscribePrepaidRenewals',
        meta: {
          title: '[UR1.5.037] - Theo dõi thuê bao gia hạn trả trước'
        },
        component: () => import('./profile/SubscribePrepaidRenewals/index.vue')
      },
      {
        path: 'AssignSubscriberManagementAreaByFile',
        name: 'AssignSubscriberManagementAreaByFile',
        meta: {
          title: '[UR1.5.037] - Gán khu vực quan lý thuê bao theo file'
        },
        component: () => import('./profile/AssignSubscriberManagementAreaByFile/index.vue')
      },
      {
        path: 'ProcessPotentialCustomers',
        name: 'ProcessPotentialCustomers',
        component: ProcessPotentialCustomers,
        meta: {
          title: 'UR2.4.113 - ProcessPotentialCustomers'
        }
      },
      // {
      //   path: "SearchAccountModal",
      //   name: "SearchAccountModal",
      //   meta: {
      //     title: "[UR2.6.012] - Hoàn thiện hồ sơ thay đổi loại hình thuê bao"
      //   },
      //   component: () =>
      //     import(
      //       "./setup/ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue"
      //     )
      // },
      {
        path: 'DistributingDocumentsCustomers',
        name: 'DistributingDocumentsCustomers',
        component: DistributingDocumentsCustomers,
        meta: {
          title: 'UR2.9.009 - Cấp phát hồ sơ cho khách hàng'
        }
      },
      {
        path: 'ListOfSurveyRequest',
        name: 'ListOfSurveyRequest',
        component: () => import('./setup/ListOfSurveyRequest'),
        meta: {
          title: 'UR2.1.003 - Danh sách yêu cầu khảo sát'
        }
      },
      {
        path: 'CompleteChangeSpeedProfile',
        name: 'CompleteChangeSpeedProfile',
        component: DeclareServiceSpeedITSecure,
        meta: {
          title: '[UR2.6.007] - Hoàn thiện hồ sơ thay đổi tốc độ Internet'
        }
      },
      {
        path: 'CompleteProfileChangeITPackageService',
        name: 'CompleteProfileChangeITPackageService',
        component: DeclareServiceSpeedITSecure,
        meta: {
          title: '[UR2.6.040] - Hoàn thiện hồ sơ thay đổi gói dịch vụ CNTT'
        }
      },
      {
        path: 'LiquidateChangeSpeedITService',
        name: 'LiquidateChangeSpeedITService',
        component: DeclareServiceSpeedITSecure,
        meta: {
          title: '[UR2.6.043] - HTHS- thay đổi tốc độ dịch vụ CNTT - ATBMTT-TTDL'
        }
      },
      {
        path: 'HTHSChangeInternetSpeedOtherProvinceCrossSalte',
        name: 'HTHSChangeInternetSpeedOtherProvinceCrossSalte',
        component: DeclareServiceSpeedITSecure,
        meta: {
          title: '[UR2.6.061] - HTHS Đổi tốc độ Internet tỉnh khác (Bán chéo)'
        }
      },
      {
        path: 'ChangeSpeedFiberKTVT',
        name: 'ChangeSpeedFiberKTVT',
        component: DeclareServiceSpeedITSecure,
        meta: {
          title: '[UR2.4.041] - Tổ KTVT- Thay đổi tốc độ Fiber'
        }
      },
      {
        path: 'ChangeInternetSpeed_OMC',
        name: 'ChangeInternetSpeed_OMC',
        component: DeclareServiceSpeedITSecure,
        meta: {
          title: '[UR2.5.032] - OMC - Thay đổi tốc độ Internet'
        }
      },
      {
        path: 'ConstructeChangeSpeedITService',
        name: 'ConstructeChangeSpeedITService',
        component: DeclareServiceSpeedITSecure,
        meta: {
          title: '[UR2.6.044] - Thi công thay đổi tốc độ dịch vụ CNTT- ATBMTT- TTDL'
        }
      },
      {
        path: 'PortSplitPointChangeTechAONGPON',
        name: 'PortSplitPointChangeTechAONGPON',
        component: DeclareServiceSpeedITSecure,
        meta: {
          title: '[UR2.4.088] - Điểm chia cấp port - Đổi công nghệ AON <-> GPON'
        }
      },
      {
        path: 'CancelFiguredOldPort_OMC',
        name: 'CancelFiguredOldPort_OMC',
        component: DeclareServiceSpeedITSecure,
        meta: {
          title: '[UR2.5.033] - OMC - Hủy cấu hình cổng cũ ( đổi công nghệ AON <->GPON)'
        }
      },
      {
        path: 'ExportData',
        name: 'ExportData',
        component: ExportData,
        meta: {
          title: '[UR2.17.001] - Xuất dữ liệu'
        }
      },
      {
        path: 'ChangeSubsType',
        name: 'ChangeSubsType',
        component: ChangeSubsType,
        meta: {
          title: '[UR2.1.035] - Thay đổi loại hình thuê bao'
        }
      },
      {
        path: 'UpdateCodeInformation',
        name: 'UpdateCodeInformation',
        meta: {
          title: '[UR2.1.044] - Cập nhật thông tin mã in ghép'
        },
        component: () => import('./setup/UpdateCodeInformation/index.js')
      },
      {
        path: 'ChangeServiceOMCVIPCM',
        name: 'ChangeServiceOMCVIPCM',
        meta: {
          title: '[UR2.5.029] - OMC/Vip/Tổ CM- Thay đổi dịch vụ'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'RegisterRemoveKPlus',
        name: 'RegisterRemoveKPlus',
        component: () => import('./profile/RegisterRemoveKPlus'),
        meta: {
          title: '[UR1.5.041] - Đăng ký / Hủy K+ addon'
        }
      },
      {
        path: 'ChangeSpeedFiberKTVT',
        name: 'ChangeSpeedFiberKTVT',
        meta: {
          title: '[UR2.4.041] - Tổ KTVT- Thay đổi tốc độ Fiber'
        },
        component: () => import('./setup/ChangeSpeedFiberKTVT/index.vue')
      },
      {
        path: 'UpdatePortParameters',
        name: 'updatePortParameters',
        meta: {
          title: '[UR1.5.040] - Cập nhật thông số cổng'
        },
        component: () => import('./profile/UpdatePortParameters')
      },
      {
        path: 'InstallMPLSByTeleTechTeam',
        name: 'installMPLSByTeleTechTeam',
        meta: {
          title: '[UR2.4.020] - Tổ KTVT - Thi công Truyền số liệu'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'DeclareMPLS',
        name: 'DeclareMPLS',
        meta: {
          title: '[UR2.5.010] - Khai báo Truyền số liệu'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'DeclareITService',
        name: 'InstallNewITService',
        meta: {
          title: '[UR2.6.032] - Khai báo Dịch vụ CNTT'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'ChangeDecoder',
        name: 'ChangeDecoder',
        component: ChangeDecoder,
        meta: {
          title: 'UR2.1.022 - Thay đổi bộ giải mã Account MyTV',
          dialog: true
        }
      },
      {
        path: 'ChangeService',
        name: 'changeService',
        meta: {
          title: '[UR2.1.008] - Thay đổi dịch vụ'
        },
        component: () => import('./setup/ChangeService/index.js')
      },
      {
        path: 'CompleteChangeMegaWanParameter',
        name: 'CompleteChangeMegaWanParameter',
        component: () => import('./setup/ChangeParameterMegawan'),
        meta: {
          title: '[UR2.6.010] - Hoàn thiện hồ sơ thay đổi thông số MegaWan'
        }
      },
      {
        path: 'SubscriberLiquidationHost',
        name: 'SubscriberLiquidationHost',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.4.028] - Host - Thanh lý thuê bao',
          tag: '9+4+1;11'
        }
      },
      {
        path: 'InitializeBusinessNumberRange',
        name: 'InitializeBusinessNumberRange',
        component: () => import('./InitializeBusinessNumberRange'),
        meta: {
          title: '[UR2.1.071] - Khởi tạo dải số doanh nghiệp'
        }
      },
      {
        path: 'CompleteProfileBuySamsungTV',
        name: 'CompleteProfileBuySamsungTV',
        meta: {
          title: '[UR2.2.001] - CompleteProfileBuySamsungTV'
        },
        component: () => import('./setup/CompleteProfileBuySamsungTV/index.js')
      },
      // {
      //   path: 'ChiTietLapMoi',
      //   name: 'ChiTietLapMoi',
      //   meta: {
      //     title: '[UR2.8.008] - Chi tiết lắp mới'
      //   },
      //   component: () => import('./setup/CompleteProfileBuySamsungTV/index.js')
      // },
      {
        path: 'MatchCustomerCode',
        name: 'MatchCustomerCode',
        meta: {
          title: '[UR1.5.011] - MatchCustomerCode'
        },
        component: () => import('./profile/MatchCustomerCode')
      },
      // {
      //   path: 'CancellationContract',
      //   name: 'CancellationContract',
      //   meta: {
      //     title: '[UR2.2.003] - Thoái trả hợp đồng'
      //   },
      //   component: () => import('./payment/CancellationContract')
      // },
      // {
      //   path: 'ListOfPaymentList',
      //   name: 'ListOfPaymentList',
      //   meta: {
      //     title: '[UR2.2.002] - Danh sách phiếu thanh toán'
      //   },
      //   component: () => import('./payment/ListOfPaymentList')
      // },
      {
        path: 'SelleEquipment',
        name: 'SelleEquipment',
        meta: {
          title: '[UR2.1.078] - Bán thiết bị'
        },
        component: () => import('./SelleEquipment')
      },
      {
        path: 'InquiryWatchList/:tag',
        name: 'InquiryWatchList',
        component: () => import('./setup/InquiryWatchList/index.vue'),
        meta: {
          title: '[UR2.1.056] - Danh sách theo dõi yêu cầu'
        }
      },
      {
        path: 'InquiryWatchList',
        name: 'InquiryWatchList',
        component: () => import('./setup/InquiryWatchList/index.vue'),
        meta: {
          title: '[UR2.1.056] - Danh sách theo dõi yêu cầu'
        }
      },
      {
        path: 'ChangeSecondarySim',
        name: 'ChangeSecondarySim',
        meta: {
          title: '[UR2.1.077] - Đổi sim phụ'
        },
        component: () => import('./ChangeSecondarySim/ChangeSecondarySim.vue')
      },
      {
        path: 'LookupDetailConstructionCrossSale',
        name: 'LookupDetailConstructionCrossSale',
        meta: {
          title: '[UR2.12.006] - Tra cứu chi tiết thi công [Bán chéo]'
        },
        component: () => import('./setup/LookupDetailConstructionCrossSale/LookupDetailConstructionCrossSale')
      },
      {
        path: 'UpdateNewChannel',
        name: 'UpdateNewChannel',
        meta: {
          title: '[UR2.12.017] - Đồng bộ kênh trắng'
        },
        component: () => import('./setup/UpdateNewChannel/UpdateNewChannel')
      },
      {
        path: 'L_AssignCrossSellSubscriberMechanism',
        name: 'L_AssignCrossSellSubscriberMechanism',
        meta: {
          title: '[URx.xx.xxx] - Gán cơ chế bán chéo cho thuê bao'
        },
        component: () => import('./setup/L_AssignCrossSellSubscriberMechanism/L_AssignCrossSellSubscriberMechanism')
      },
      {
        path: 'HTHSChangeProfileMegawanOtherProvince',
        name: 'HTHSChangeProfileMegawanOtherProvince',
        meta: {
          title: '[UR2.6.062] - HTHS Đổi thông số Megawan tỉnh khác (Bán chéo)'
        },
        component: () => import('./setup/HTHSChangeProfileMegawanOtherProvince/index.js')
      },
      {
        path: 'RestoreUnconfiguredPort',
        name: 'RestoreUnconfiguredPort',
        meta: {
          title: '[UR2.4.120] - Khôi phục cổng đã huỷ'
        },
        component: () => import('./setup/RestoreUnconfiguredPort/RestoreUnconfiguredPort.vue')
      },
      {
        path: 'CheckIMSServicePartner/:tag',
        name: 'CheckIMSServicePartner',
        component: () => import('./setup/CheckIMSServicePartner/index.vue'),
        meta: {
          title: '[UR2.1.056] - Danh sách theo dõi yêu cầu'
        }
      },
      {
        path: 'UpdateReceiptOfReceipts',
        name: 'UpdateReceiptOfReceipts',
        meta: {
          title: '[UR2.2.016] - Cập nhật biên lai thu tiền'
        },
        component: () => import('./payment/UpdateReceiptOfReceipts')
      },
      {
        path: 'IssuingReceiptsCollection',
        name: 'IssuingReceiptsCollection',
        meta: {
          title: '[UR2.2.015] - Phát hành biên lai thu tiền'
        },
        component: () => import('./payment/IssuingReceiptsCollection')
      },
      {
        path: 'UpdatePostPaid',
        name: 'UpdatePostPaid',
        meta: {
          title: '[UR2.11.002] - Cập nhật trả trước'
        },
        component: () => import('./setup/UpdatePostPaid/index.js')
      },
      {
        path: 'RegisterBusinessNumberRange',
        name: 'RegisterBusinessNumberRange',
        component: RegisterBusinessNumberRange,
        meta: {
          title: '[UR2.1.070] - Đăng ký dải số doanh nghiệp'
        }
      },
      {
        path: 'SetupNewITService_DHTT',
        name: 'SetupNewITService_DHTT',
        meta: {
          title: '[UR2.5.043] - ĐHTT- Lắp mới CNTT'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'NormalizeCAinformation',
        name: 'NormalizeCAinformation',
        meta: {
          title: '[UR2.1.040] - Chuẩn hóa thông tin dịch vụ CA'
        },
        component: () => import('./setup/NormalizeCAinformation/index.vue')
      },
      {
        path: 'UpdateSubscriberDeposit',
        name: 'UpdateSubscriberDeposit',
        meta: {
          title: '[UR2.1.050] - Cập nhật thuê bao đặt cọc - Thử nghiệm'
        },
        component: () => import('./setup/UpdateSubscriberDeposit')
      },
      {
        path: 'ExtenChangeInformationITVATServices',
        name: 'ExtenChangeInformationITVATServices',
        meta: {
          title: '[UR2.1.039] - Gia hạn, thay đổi thông tin DV CNTT/GTGT'
        },
        component: () => import('./setup/ExtenChangeInformationITVATServices')
      },
      {
        path: 'RegisterServiceVCC',
        name: 'RegisterServiceVCC',
        meta: {
          title: '[UR2.1.020] - Đăng ký dịch vụ Cloud Contact Center (VCC)'
        },
        component: () => import('./setup/RegisterServiceVCC')
      },
      {
        path: 'SyncDataVTN',
        name: 'SyncDataVTN',
        meta: {
          title: '[UR2.1.023] - Đồng bộ dữ liệu VTN'
        },
        component: () => import('./SyncData/SyncDataVTN')
      },
      {
        path: 'SyncVNPContacts',
        name: 'SyncVNPContacts',
        meta: {
          title: '[UR2.1.025] - Đồng bộ danh bạ VNP'
        },
        component: () => import('./SyncData/SyncVNPContacts')
      },
      {
        path: 'RegisUnionMembersPackage',
        name: 'RegisUnionMembersPackage',
        component: () => import('./setup/RegisUnionMembersPackage'),
        meta: {
          title: '[UR2.11.008] - Đăng ký gói đoàn viên'
        }
      },
      {
        path: 'CompleteIProfileVANService',
        name: 'CompleteIProfileVANService',
        meta: {
          title: '[UR2.6.031] - Hoàn thiện hồ sơ dịch vụ IVAN'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'SetupVideoConference',
        name: 'SetupVideoConference',
        meta: {
          title: '[UR2.6.051] - Khai báo dịch vụ Hội nghị truyền hình'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'DeleteUnpaidContract',
        name: 'DeleteUnpaidContract',
        component: DeleteUnpaidContract,
        meta: {
          title: '[UR1.5.003] - Xóa HĐ chưa thanh toán'
        }
      },
      {
        path: 'LookupNavigateContract',
        name: 'lookup-navigate-contract',
        meta: {
          title: '[UR2.12.003] - Tra cứu và điều hướng hợp đồng'
        },
        component: () => import('./setup/LookupNavigateContract/index.vue')
      },
      {
        path: 'LookupReturnConstructionContract',
        name: 'lookup-return-construction-contract',
        meta: {
          title: '[UR2.12.015] - Tra cứu và thoái trả hợp đồng thi công'
        },
        component: () => import('./setup/LookupReturnConstructionContract/index.vue')
      },
      {
        path: 'IssuingReceiptsCollection/:tag',
        name: 'IssuingReceiptsCollection',
        meta: {
          title: '[UR2.2.015] - Phát hành biên lai thu tiền'
        },
        component: () => import('./payment/IssuingReceiptsCollection')
      },
      {
        path: 'ChangeIPAccess',
        name: 'ChangeIPAccess',
        meta: {
          title: '[UR1.5.016] - Đổi IP truy nhập Adsl/Ftth'
        },
        component: () => import('./profile/ChangeIPAccess/index.vue')
      },
      {
        path: 'ActiveBNM',
        name: 'ActiveBNM',
        component: () => import('./setup/ActiveBNM'),
        meta: {
          title: 'BSS-1332 [UR2.4.001] - Kích hoạt BNM'
        }
      },
      {
        path: 'ResetcellProvince',
        name: 'ResetcellProvince',
        component: () => import('./setup/ResetcellProvince'),
        meta: {
          title: 'BSS-1390 [UR2.4.056] - Reset Cell của tỉnh'
        }
      },
      // {
      //   path: 'UpdateChangeSpeedPromotion',
      //   name: 'UpdateChangeSpeedPromotion',
      //   component: () => import('./setup/UpdateChangeSpeedPromotion'),
      //   meta: {
      //     title: 'BSS-1477 [UR2.7.004] - Sửa khuyến mại thay đổi tốc độ Internet'
      //   }
      // },        //Tạm bỏ do chưa thấy code.
      {
        path: 'ChangeParameterMegawan',
        name: 'ChangeParameterMegawan',
        component: () => import('./setup/ChangeParameterMegawan'),
        meta: {
          title: '[UR2.4.038] - Thay đổi thông số Megawan'
        }
      },
      {
        path: 'SearchInformationChanges',
        name: 'SearchInformationChanges',
        meta: {
          title: '[UR1.5.017] - SearchInformationChanges'
        },
        component: () => import('./profile/SearchInformationChanges/index.js')
      },
      {
        path: 'MyaccountPasswordLookup',
        name: 'MyaccountPasswordLookup',
        component: MyaccountPasswordLookup,
        meta: {
          title: '[UR2.1.059] - Tra cứu mật khẩu MyAccount'
        }
      },
      {
        path: 'CompleteProfileCrosssellTSL',
        name: 'CompleteProfileCrosssellTSL',
        meta: {
          title: '[UR2.6.060] - HTHS TSL tỉnh khác (Bán chéo)'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'TSLPartner',
        name: 'TSLPartner',
        meta: {
          title: '[UR2.4.072] - TSL đối tác'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'SetupTSL_CM',
        name: 'SetupTSL_CM',
        meta: {
          title: '[UR2.5.026] - Tổ CM - Khai báo TSL',
          des: '/#/contract/SetupTSL_CM?tag=20+1;3;30+9'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'ProcessTSL_DHTC',
        name: 'ProcessTSL_DHTC',
        meta: {
          title: '[UR2.5.026] - Tổ CM - Khai báo TSL',
          des: '/#/contract/ProcessTSL_DHTC?tag=3+33'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'DeclareMPLSByOMC',
        name: 'DeclareMPLSByOMC',
        meta: {
          title: '[UR2.5.022] - VIP TTĐH - Khai báo Truyền số liệu',
          des: '/#/contract/DeclareMPLSByOMC?tag=9+1;3;6;30+9'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'SyncFixSubscribersToSwitchboard',
        name: 'SyncFixSubscribersToSwitchboard',
        component: () => import('./SyncData/SyncFixSubscribersToSwitchboard'),
        meta: {
          title: '[UR2.1.024] - Đồng bộ thuê bao Cố định lên Tổng đài'
        }
      },
      {
        path: 'CompleteTransferFile',
        name: 'CompleteTransferFile',
        meta: {
          title: '[UR2.6.003] - Hoàn thiện hồ sơ dịch chuyển'
        },
        component: () => import('./complete_profile/CompleteTransferFile/index.js')
      },
      {
        path: 'ResetCellGphone',
        name: 'resetCellGphone',
        meta: {
          title: '[UR2.4.055] - Reset Cell GPhone'
        },
        component: () => import('./setup/ResetCellGphone/index.js')
      },
      {
        path: 'InstallLandline_KTVT',
        name: 'InstallLandline_KTVT',
        component: () => import('./setup/DeclareLandline/index.vue'),
        meta: {
          title: '[UR2.5.001] - Thi công Cố định'
        }
      },
      {
        path: 'FinishIMSSiptrunk',
        name: 'FinishIMSSiptrunk',
        meta: {
          title: '[UR2.4.057] - Hoàn công Ims Siptrunk'
        },
        props: {
          tagForm: {
            tthd_id: 10,
            dsloaihd_id: '1,3,6,30',
            dsdichvuvt_id: '11',
            flagTSKT: '',
            flagTTTB_IMS: 0
          }
        },
        component: () => import('./setup/DeclareIMS/index.js')
      },
      {
        path: 'OperateSetupNewMoveCDLHIMSSiptrunk',
        name: 'OperateSetupNewMoveCDLHIMSSiptrunk',
        meta: {
          title: '[UR2.4.115] - Điều hành lắp mới/dịch chuyển/CĐLH - IMS SipTrunking'
        },
        props: {
          tagForm: {
            tthd_id: 22,
            dsloaihd_id: '1,3,6,30',
            dsdichvuvt_id: '11',
            flagTSKT: '',
            flagTTTB_IMS: 0
          }
        },
        component: () => import('./setup/DeclareIMS/index.js')
      },
      {
        path: 'HTHSLandLineOtherProvinceCrossSale',
        name: 'HTHSLandLineOtherProvinceCrossSale',
        component: () => import('./setup/DeclareLandline/index.vue'),
        meta: {
          title: '[UR2.6.056] - HTHS Cố định tỉnh khác (Bán chéo)'
        }
      },
      {
        path: 'GrantPortIMSSubcriber_TeleStation',
        name: 'GrantPortIMSSubcriber_TeleStation',
        meta: {
          title: '[UR2.4.098] - Trạm VT - Cấp Port thuê bao IMS'
        },
        props: {
          tagForm: {
            tthd_id: 3,
            dsloaihd_id: '1,3,6,30',
            dsdichvuvt_id: '11',
            flagTSKT: '',
            flagTTTB_IMS: 0
          }
        },
        component: () => import('./setup/DeclareIMS/index.js')
      },
      {
        path: 'SetupIMS_OMC',
        name: 'SetupIMS_OMC',
        meta: {
          title: '[UR2.5.024] - OMC - Khai báo Ims'
        },
        props: {
          tagForm: {
            tthd_id: 9,
            dsloaihd_id: '1,3,6,30',
            dsdichvuvt_id: '11',
            flagTSKT: '',
            flagTTTB_IMS: 0
          }
        },
        component: () => import('./setup/DeclareIMS/index.js')
      },
      {
        path: 'SetupIMS_TTDH',
        name: 'SetupIMS_TTDH',
        meta: {
          title: '[UR2.5.014] - TTĐH - Khai báo IMS'
        },
        props: {
          tagForm: {
            tthd_id: 9,
            dsloaihd_id: '206',
            dsdichvuvt_id: '11',
            flagTSKT: '',
            flagTTTB_IMS: 0
          }
        },
        component: () => import('./setup/DeclareIMS/index.js')
      },
      {
        path: 'HTHSIMSOtherProvinceCrossSale',
        name: 'HTHSIMSOtherProvinceCrossSale',
        meta: {
          title: '[UR2.6.058] - HTHS IMS tỉnh khác (Bán chéo)'
        },
        props: {
          tagForm: {
            tthd_id: 26,
            dsloaihd_id: '1,3,6,30',
            dsdichvuvt_id: '11',
            flagTSKT: '',
            flagTTTB_IMS: 0
          }
        },
        component: () => import('./setup/DeclareIMS/index.js')
      },
      {
        path: 'InstallIMS_KTVT',
        name: 'InstallIMS_KTVT',
        meta: {
          title: '[UR2.4.046] - Tổ KTVT - Thi công IMS'
        },
        props: {
          tagForm: {
            tthd_id: 4,
            dsloaihd_id: '1,3,6,30',
            dsdichvuvt_id: '11,21',
            flagTSKT: '',
            flagTTTB_IMS: 0
          }
        },
        component: () => import('./setup/DeclareIMS/index.js')
      },
      {
        path: 'ConfigIMSSiptrunking_Net',
        name: 'ConfigIMSSiptrunking_Net',
        meta: {
          title: '[UR2.4.111] - VNPT Net - Cấu hình thuê bao IMS Siptrunking'
        },
        props: {
          tagForm: {
            tthd_id: 25,
            dsloaihd_id: '1,3,30',
            dsdichvuvt_id: '11',
            flagTSKT: '',
            flagTTTB_IMS: 0
          }
        },
        component: () => import('./setup/DeclareIMS/index.js')
      },
      // TODO: this route only for testing, plz remove on prod
      {
        path: 'DeclareIMSMenu',
        name: 'declareIMSMenu',
        meta: {
          title: '[UR2.5.015] - Khai báo Ims'
        },
        component: () => import('./setup/DeclareIMS/Menu.vue')
      },
      {
        path: 'CompleteDepositFile',
        name: 'CompleteDepositFile',
        component: () => import('./complete_profile/CompleteDepositFile'),
        meta: {
          title: '[UR2.6.026] - Hoàn thiện hồ sơ đặt cọc theo file'
        }
      },
      {
        path: 'DeclareIMS',
        name: 'DeclareIMS',
        meta: {
          title: '[UR1.5.016] - Đổi IP truy nhập Adsl/Ftth'
        },
        component: () => import('./setup/DeclareIMS/DeclareIMS.vue')
      },
      {
        path: 'ManageProfile',
        name: 'ManageProfile',
        component: () => import('./profile/ManageProfile'),
        meta: {
          title: '[UR1.5.012] - Quản lý hồ sơ điện tử'
        }
      },
      {
        path: 'SyncChangeEPGPackage',
        name: 'SyncChangeEPGPackage',
        component: () => import('./category/SyncChangeEPGPackage/index.js'),
        meta: {
          title: '[UR1.5.015] - Đồng bộ thay đổi gói cước EPG'
        }
      },
      {
        path: 'AssignManagement',
        name: 'AssignManagement',
        meta: {
          title: '[UR2.7.010] - Gán nhân viên quản lý'
        },
        component: () => import('./setup/AssignManagement/index.js')
      },
      {
        path: 'CompleteMobileInitProfile',
        name: 'CompleteMobileInitProfile',
        meta: {
          title: '[UR2.6.030] - Hoàn thiện hồ sơ hòa mạng di động',
          des: '/#/contract/CompleteMobileInitProfile'
        },
        component: () => import('./complete_profile/CompleteMobileInitProfile/index.js')
      },
      // {
      //   path: 'UpdateChangeSpeedPromotion',
      //   name: 'UpdateChangeSpeedPromotion',
      //   component: () => import('./setup/UpdateChangeSpeedPromotion/index'),
      //   meta: {
      //     title: 'BSS-1477 [UR2.7.004] - Sửa khuyến mại thay đổi tốc độ Internet'
      //   }
      // },
      {
        path: 'DeclareHDDT_ITCenter',
        name: 'DeclareHDDT_ITCenter',
        meta: {
          title: '[UR2.6.030] - Hoàn thiện hồ sơ hòa mạng di động',
          des: '/#/contract/DeclareHDDT_ITCenter?tag=3+1;30+15;16;14'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'UpdateChangeSpeedPromotion',
        name: 'UpdateChangeSpeedPromotion',
        component: () => import('./setup/UpdateChangeSpeedPromotion/index'),
        meta: {
          title: 'BSS-1477 [UR2.7.004] - Sửa khuyến mại thay đổi tốc độ Internet'
        }
      },
      {
        path: 'DetailNewAssembly',
        name: 'DetailNewAssembly',
        meta: {
          title: '[UR2.8.008] - Chi tiết lắp mới',
          tag: '0'
        },
        component: () => import('./setup/CompleteProfileBuySamsungTV/index.js')
      },
      {
        path: 'SetupNewITService',
        name: 'SetupNewITService',
        meta: {
          title: '[UR2.6.050] - Thi công đặt mới dịch vụ CNTT',
          des: '/#/contract/SetupNewITService?tag=4+1+15;14;16'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'SetHotChargeThreshold',
        name: 'SetHotChargeThreshold',
        meta: {
          title: '[UR1.5.034] - Gán ngưỡng cước nóng'
        },
        component: () => import('./profile/SetHotChargeThreshold/SetHotChargeThreshold.vue')
      },
      {
        path: 'RegisMobilePackage',
        name: 'RegisMobilePackage',
        meta: {
          title: '[UR2.14.002] - Đăng ký gói cước di động'
        },
        component: () => import('./setup/RegisMobilePackage/RegisMobilePackage')
      },
      {
        path: 'ManagementMobilePackage',
        name: 'ManagementMobilePackage',
        meta: {
          title: '[UR2.14.001] - Quản lý gói cước di động'
        },
        component: () => import('./setup/ManagementMobilePackage/ManagementMobilePackage')
      },
      {
        path: 'UploadEProfile',
        name: 'UploadEProfile',
        meta: {
          title: '[UR1.5.029] - Upload file hồ sơ điện tử'
        },
        component: () => import('./profile/UploadEProfile')
      },
      {
        path: 'SubscriptionLiquidationCrossSell',
        name: 'SubscriptionLiquidationCrossSell',
        meta: {
          title: '[UR2.12.012] - [Bán chéo]: Thanh lý thuê bao'
        },
        component: () => import('./setup/SubscriptionLiquidationCrossSell/SubscriptionLiquidationCrossSell.vue')
      },
      {
        path: 'LockUnlockPayTV',
        name: 'LockUnlockPayTV',
        component: LockUnlockPayTV,
        meta: {
          title: '[UR2.1.054] - Khóa mở PayTV',
          dialog: true
        }
      },
      {
        path: 'RegisStudentPackage',
        name: 'RegisStudentPackage',
        meta: {
          title: '[UR2.11.007] - DK goi cuoc sv'
        },
        component: () => import('./setup/RegisStudentPackage/RegisStudentPackage.vue')
      },
      {
        path: 'HandoverTicketOnOffSubcriber',
        name: 'HandoverTicketOnOffSubcriber',
        component: () => import('./setup/HandoverTicketOnOffSubcriber'),
        meta: {
          title: '[UR2.3.006] - Giao phiếu khóa mở máy'
        }
      },
      {
        path: 'InstallGphone_KTVT',
        name: 'InstallGphone_KTVT',
        meta: {
          title: '[UR2.4.011] - Tổ KTVT - Thi công Gphone'
        },
        component: () => import('./setup/InstallGphone_KTVT/InstallGphone_KTVT.vue')
      },
      {
        path: 'SetupInfrastructure_TeleStation',
        name: 'SetupInfrastructure_TeleStation',
        props: (route) => ({ query: route.query.tag }),
        meta: {
          title: '[UR2.4.106] - Trạm VT - Thi công dịch vụ Cơ sở hạ tầng'
        },
        component: () => import('./setup/SetupInfrastructure_TeleStation/index.vue')
      },
      {
        path: 'OperateLiquidationIMS',
        name: 'OperateLiquidationIMS',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.4.118] - ĐH PTTB TTKD thanh lý IMS',
          tag: '22+4+11'
        }
      },
      {
        path: 'PartnerFixedBroadband',
        name: 'PartnerFixedBroadband',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.4.069] - BRCĐ đối tác',
          tag: '9+4+22'
        }
      },
      {
        path: 'PartnerIMS',
        name: 'PartnerIMS',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.4.070] - IMS đối tác',
          tag: '9+4+21;22;23;24'
        }
      },
      {
        path: 'LiquidationSubscriberKT',
        name: 'LiquidationSubscriberKT',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.4.029] - Tổ KT1 - Thanh lý thuê bao',
          tag: '9+4+4;8;9;11'
        }
      },
      {
        path: 'LiquidationSubscriberNetCenter',
        name: 'LiquidationSubscriberNetCenter',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.4.030] - Đài NetCenter - Thanh lý thuê bao',
          tag: '9+4+12'
        }
      },
      {
        path: 'SubscriberWithdrawal_KTVT',
        name: 'SubscriberWithdrawal_KTVT',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.4.031] - Tổ KTVT - Thu hồi thuê bao',
          tag: '4+4+8;9;12'
        }
      },
      {
        path: 'LiquidationFTTHNoAccPartnerService_KTVT',
        name: 'LiquidationFTTHNoAccPartnerService_KTVT',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.4.032] - Trạm VT - Thanh lý FTTH không account - dịch vụ đối tác',
          tag: '4+4+4'
        }
      },
      {
        path: 'KTRSubscriberLiquidation_CMTD',
        name: 'KTRSubscriberLiquidation_CMTD',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.5.036] - CMTD - Thanh lý thuê bao KTR',
          tag: '20+4+9'
        }
      },
      {
        path: 'LiquidationSubscriberOMC',
        name: 'LiquidationSubscriberOMC',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.5.034] - (*)OMC - Thanh lý thuê bao',
          tag: '9+4+7;8;9'
        }
      },
      {
        path: 'LiquidationLanlineSubscriberOMC',
        name: 'LiquidationLanlineSubscriberOMC',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.5.035] - OMC - Thanh lý cố định/IMS/BRCĐ',
          tag: '9+4+1;11;4'
        }
      },
      {
        path: 'SubscriberLiquidationKT',
        name: 'SubscriberLiquidationKT',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.4.083] - Tổ KT1 - Thanh lý thuê bao',
          tag: '9+4+4;8;9;11'
        }
      },
      {
        path: 'LiquidateDataCenterService',
        name: 'LiquidateDataCenterService',
        component: () => import('./setup/DeclareLiquidateITServiceDHNV/DeclareLiquidateITServiceDHNV.vue'),
        meta: {
          title: '[UR2.6.041] - HTHS - Thanh lý DV Trung tâm dữ liệu',
          tag: '1+4+16'
        }
      },
      {
        path: 'DeclareLiquidationSubscriber',
        name: 'DeclareLiquidationSubscriber',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.6.034] - Khai báo thanh lý Dịch vụ CNTT',
          tag: '9+4+15;14;16'
        }
      },
      {
        path: 'DeclareLiquidateITServiceDHNV',
        name: 'DeclareLiquidateITServiceDHNV',
        component: () => import('./setup/DeclareLiquidateITServiceDHNV/DeclareLiquidateITServiceDHNV.vue'),
        meta: {
          title: '[UR2.6.038] - Phòng ĐHNV khai báo thanh lý dịch vụ CNTT',
          tag: '4+4+15;14;16'
        }
      },
      {
        path: 'HTHSLiquidationOtherProvinceCrossSale',
        name: 'HTHSLiquidationOtherProvinceCrossSale',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.6.065] - HTHS Thanh lý tỉnh khác (Bán chéo)',
          tag: '1+4+1;4;8;9;11'
        }
      },
      {
        path: 'CompleteLiquidateSMSBrandVietlotService',
        name: 'CompleteLiquidateSMSBrandVietlotService',
        component: () => import('./setup/DeclareLiquidateITServiceDHNV/DeclareLiquidateITServiceDHNV.vue'),
        meta: {
          title: '[UR2.6.039] - Hoàn thiện hồ sơ thanh lý Dịch vụ SMSBrandname+Vietlot',
          tag: '1+4+14;15;16'
        }
      },
      {
        path: 'CompleteProfileLiquidationCompleteSetup',
        name: 'CompleteProfileLiquidationCompleteSetup',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.6.066] - Hoàn thiện hồ sơ thanh lý với thuê bao đã thi công xong',
          tag: '5+4+10;1;13;14;15;16;50;11'
        }
      },
      {
        path: 'CompleteLiquidateMegaEyes',
        name: 'CompleteLiquidateMegaEyes',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.6.028] - Hoàn Thiện Thanh lý MegaEyes',
          tag: '1+4+12'
        }
      },
      {
        path: 'CompleteLiquidateITService',
        name: 'CompleteLiquidateITService',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.6.037] - Hoàn thiện hồ sơ thanh lý Dịch vụ CNTT',
          tag: '4+4+15;14'
        }
      },
      {
        path: 'CompleteEmailRecovery',
        name: 'CompleteEmailRecovery',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.6.023] - Hoàn thiện hồ sơ thanh lý Email',
          tag: '1+4+6'
        }
      },
      {
        path: 'CompleteMobileLiquidate',
        name: 'CompleteMobileLiquidate',
        component: () => import('./setup/SubscriberLiquidationHost/SubscriberLiquidationHost.vue'),
        meta: {
          title: '[UR2.6.014] - Hoàn thiện hồ sơ thanh lý di động',
          tag: '1+4+6'
        }
      },
      {
        path: 'AcceptanceTSLByTechTeam',
        name: 'AcceptanceTSLByTechTeam',
        meta: {
          title: '[UR2.4.052] - Tổ kỹ thuật - Nghiệm thu TSL',
          des: '/#/contract/AcceptanceTSLByTechTeam?tag=11%2B1%3B3%3B6%2B9%3B24'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'CompleteGphoneRecovery',
        name: 'CompleteGphoneRecovery',
        meta: {
          title: '[UR2.6.018] - HTHS Khôi phục Gphone'
        },
        component: () => import('./setup/InstallGphone_KTVT/InstallGphone_KTVT.vue')
      },
      {
        path: 'InstallReplaceRecallWifi/:tag',
        meta: {
          title: '[UR2.4.008] - Cài đặt Wifi - Thay thế - Thu hồi vật tư'
        },
        component: () => import('./setup/InstallReplaceRecallWifi')
      },
      {
        path: 'ChangeIMSLandlineServiceByHost',
        name: 'ChangeIMSLandlineServiceByHost',
        meta: {
          title: '[UR2.5.028] - Host - Thay đổi dịch vụ cố định/IMS'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'ChangServiceByHost',
        name: 'ChangServiceByHost',
        meta: {
          title: '[UR2.4.014] - Host - Thay đổi dịch vụ'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'ChangeFiberLandlineServiceByTeleStation',
        name: 'ChangeFiberLandlineServiceByTeleStation',
        meta: {
          title: '[UR2.4.076] - Trạm VT - Thay đổi dịch vụ cố định/ Băng rộng'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'ChangeServiceByTeleStation',
        name: 'ChangeServiceByTeleStation',
        meta: {
          title: '[UR2.4.076] - Trạm VT - Thay đổi dịch vụ cố định/ Băng rộng'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'CompleteProfileChangeMobileService',
        name: 'CompleteProfileChangeMobileService',
        meta: {
          title: '[UR2.6.013] - Hoàn thiện hồ sơ đổi dịch vụ di động'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'DeclareInformationSecurityService',
        name: 'DeclareInformationSecurityService',
        meta: {
          title: '[UR2.6.035] - Khai báo thay đổi dịch vụ CNTT- ATBMTT'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'CompleteProfileChangeInformationSecurityService',
        name: 'CompleteProfileChangeInformationSecurityService',
        meta: {
          title: '[UR2.6.036] - Hoàn thiện hồ sơ Thay đổi dịch vụ ATBMTT- dich vụ CNTT'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'CompleteProfileChangeITVASService',
        name: 'CompleteProfileChangeITVASService',
        meta: {
          title: '[UR2.6.046] - HTHS thay đổi dịch vụ CNTT&GTGT'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'PauseAndRestoreIMS',
        name: 'PauseAndRestoreIMS',
        meta: {
          title: '[UR2.6.021] - Tạm dừng/Khôi phục IMS'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'ChangeSubsTypeByTechTeam1',
        name: 'ChangeSubsTypeByTechTeam1',
        meta: {
          title: '[UR2.4.016] - Tổ KT1 - Thay đổi dịch vụ'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'PauseAndRestoreMegaEyesByNetCenter',
        name: 'PauseAndRestoreMegaEyesByNetCenter',
        meta: {
          title: '[UR2.4.017] - Đài NetCenter - Tạm dừng/ Khôi phục MegaEyes'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'ChangeTSLServiceByTeleStation',
        name: 'ChangeTSLServiceByTeleStation',
        meta: {
          title: '[UR2.4.019] - Trạm VT - Thay đổi dịch vụ TSL'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'ManagementAgent',
        name: 'ManagementAgent',
        meta: {
          title: '[UR2.11.011] - Quản lý đại lý'
        },
        component: () => import('./setup/ManagementAgent/index')
      },
      {
        path: 'DangKyGoiDaDichVu',
        name: 'DangKyGoiDaDichVu',
        component: DangKyGoiDaDichVu
      },
      {
        path: 'DeclareMegawan',
        name: 'DeclareMegawan',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.5.012] - Khai báo Megawan',
          tag: '9+1;3;6+7;8'
        }
      },
      {
        path: 'CompleteInstallTSLBTS',
        name: 'CompleteInstallTSLBTS',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.4.119] - Hoàn thiện lắp mới TSL - BTS',
          tag: '23+1+7'
        }
      },
      {
        path: 'InstallMetronetByTeleTechTeam',
        name: 'InstallMetronetByTeleTechTeam',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.4.104] - Tổ KTVT - Thi công Metronet',
          tag: '4+1;3;6;30+7'
        }
      },
      {
        path: 'DeliverMegawanPortByTeleStation',
        name: 'DeliverMegawanPortByTeleStation',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.4.093] - Trạm VT - Cấp port thuê bao Megawan',
          tag: '3+1;3;6;30+8'
        }
      },
      {
        path: 'AcceptanceMegawanByTechTeam1',
        name: 'AcceptanceMegawanByTechTeam1',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.4.091] - Tổ kỹ thuật 1 - Nghiệm thu Megawan',
          tag: '11+1;3;6+8'
        }
      },
      {
        path: 'DeliverMetronetPortByTeleStation',
        name: 'DeliverMetronetPortByTeleStation',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.4.096] - Trạm VT - Cấp port thuê bao Metronet',
          tag: '3+1;3;6;30+7'
        }
      },
      {
        path: 'InterconnectMegawanPartner',
        name: 'InterconnectMegawanPartner',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.4.066] - Đối soát dịch vụ Megawan đối tác',
          tag: '11+1;3+23'
        }
      },
      {
        path: 'MegawanPartner',
        name: 'MegawanPartner',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.4.071] - Megawan đối tác',
          tag: '9+4+23'
        }
      },
      {
        path: 'AcceptanceMegawanByTechTeam',
        name: 'AcceptanceMegawanByTechTeam',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.4.051] - Tổ kỹ thuật - Nghiệm thu Megawan',
          tag: '11+1;3;6;30+7;8'
        }
      },
      {
        path: 'CheckNetworkMTN_TTDH',
        name: 'CheckNetworkMTN_TTDH',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.5.039] - TTĐH - Kiểm tra đường truyền MTN - BTS',
          tag: '22+1;3;6;30+7'
        }
      },
      {
        path: 'DeclareMegawanByOMC',
        name: 'DeclareMegawanByOMC',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.5.023] - OMC - Khai báo Megawan',
          tag: '9+1;3;6;30+8'
        }
      },
      {
        path: 'DeclareMetronetByOMC',
        name: 'DeclareMetronetByOMC',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.5.025] - OMC - Khai báo Metronet',
          tag: '9+1;3;6;30+7'
        }
      },
      {
        path: 'SetupMGW_CM',
        name: 'SetupMGW_CM',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.5.027] - Tổ CM - Khai báo MGW',
          tag: '20+1;3+8'
        }
      },
      {
        path: 'ProcessMegaWan_DHTC',
        name: 'ProcessMegaWan_DHTC',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.5.013] - ĐHTC - Xử lý MegaWan',
          tag: '3+34'
        }
      },
      {
        path: 'CompleteProfileCrosssellMegawan',
        name: 'CompleteProfileCrosssellMegawan',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.6.059] - HTHS Megawan tỉnh khác (Bán chéo)',
          tag: '26+1;6;3+8;7'
        }
      },
      {
        path: 'RestoreUnconfiguredPort',
        name: 'RestoreUnconfiguredPort',
        meta: {
          title: '[UR2.4.120] - Khôi phục cổng đã huỷ'
        },
        component: () => import('./setup/RestoreUnconfiguredPort/RestoreUnconfiguredPort.vue')
      },
      {
        path: 'RegisStudentPackage',
        name: 'RegisStudentPackage',
        meta: {
          title: '[UR2.11.007] - DK goi cuoc sv'
        },
        component: () => import('./setup/RegisStudentPackage/RegisStudentPackage.vue')
      },
      {
        path: 'UpdateChangePromotion',
        name: 'UpdateChangePromotion',
        meta: {
          title: '[UR2.7.003] - Sửa khuyến mại thay đổi loại hình thuê bao'
        },
        component: () => import('./setup/UpdateChangePromotion/UpdateChangePromotion.vue')
      },
      {
        path: 'OperateOnOffIMS',
        name: 'OperateOnOffIMS',
        meta: {
          title: '[UR2.4.116] - ĐH PTTB TTKD Tạm dừng IMS/Mở tạm dừng IMS'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'CollectIncurredDebt',
        name: 'CollectIncurredDebt',
        meta: {
          title: '[UR2.1.015] - Thu tiền phát sinh'
        },
        component: () => import('./setup/CollectIncurredDebt/CollectIncurredDebt.vue')
      },
      {
        path: 'AcceptanceTSLByTechTeam1',
        name: 'AcceptanceTSLByTechTeam1',
        meta: {
          title: '[UR2.4.092] - Tổ kỹ thuật 1 - Nghiệm thu TSL',
          des: '/#/contract/AcceptanceTSLByTechTeam1?tag=11%2B1%3B3%3B6%2B9'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'DeliverTSLPortByTeleStation',
        name: 'DeliverTSLPortByTeleStation',
        meta: {
          title: '[UR2.4.094] - Trạm VT - Cấp port đặt mới thuê bao TSL',
          des: '/#/contract/DeliverTSLPortByTeleStation?tag=3%2B1%3B3%3B6%3B30%2B9'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'DeliverTSLPortByTeleStation',
        name: 'DeliverTSLPortByTeleStation',
        meta: {
          title: '[UR2.4.067] - Đối soát dịch vụ TSL đối tác',
          des: '/#/contract/DeliverTSLPortByTeleStation?tag=11%2B1%3B3%2B24'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'DeclareMegawanKTVT',
        name: 'DeclareMegawanKTVT',
        component: () => import('./setup/DeclareMegawan/DeclareMegawan.vue'),
        meta: {
          title: '[UR2.4.022] - Tổ KTVT - Thi công MegaWan',
          tag: '4+1;3;6;30+7;8;23'
        }
      },
      {
        path: 'SetupNewCSHT',
        name: 'SetupNewCSHT',
        component: () => import('./setup/SetupNewCSHT'),
        meta: {
          title: '[BSS-38483] - Lắp đặt mới CSHT'
        }
      },
      {
        path: 'OnOffCallOutCallInOnDemand',
        name: 'OnOffCallOutCallInOnDemand',
        component: () => import('./setup/OnOffCallOutCallInOnDemand'),
        meta: {
          title: '[UR2.11.003] - Cắt mở gọi đi, gọi đến theo yêu cầu'
        }
      },
      {
        path: 'UpdateMobileSubsInformation',
        name: 'UpdateMobileSubsInformation',
        component: UpdateMobileSubsInformation,
        meta: {
          title: '[UR2.1.062] - Cập nhật thông tin thuê bao di động chuyển mạng đến (PI)'
        }
      },
      {
        path: 'ChangeTSLInfor',
        name: 'ChangeTSLInfor',
        meta: {
          title: 'ChangeTSLInfor'
        },
        component: () => import('./setup/ChangeTSLInfor/FrmThayDoiTsKTR.vue')
      },
      {
        path: 'CompleteProfileByManager',
        name: 'CompleteProfileByManager',
        component: () => import('./profile/CompleteProfileByManager'),
        meta: {
          title: 'BSS-1536 [UR2.9.005] - QLK hoàn thiện'
        }
      },
      {
        path: 'TransferRightsCrossSell',
        name: 'TransferRightsCrossSell',
        component: () => import('./setup/TransferRightsCrossSell/TransferRightsCrossSell.vue'),
        meta: {
          title: '[UR2.12.011] - [Bán chéo]:Chuyển quyền'
        }
      },
      {
        path: 'HandOverTicketVerifyCombine',
        name: 'HandOverTicketVerifyCombine',
        meta: {
          title: '[BSS-50073] - Giao phiếu xác minh, phối hợp'
        },
        component: () => import('./setup/HandOverTicketVerifyCombine')
      },
      {
        path: 'CollectMoneyGenerated',
        name: 'CollectMoneyGenerated',
        component: () => import('./complete_profile/CollectMoneyGenerated'),
        meta: {
          title: '[UR2.6.027] - HTHS - Thu tiền phát sinh',
          des: '/#/contract/CollectMoneyGenerated?tag=2%2B17%2B2'
        }
      },
      {
        path: 'RegisterMNPPackage',
        name: 'RegisterMNPPackage',
        component: () => import('./RegisterMNPPackage/RegisterMNPPackage.vue'),
        meta: {
          title: '[UR2.1.073] - Đăng ký gói cước MNP'
        }
      },
      {
        path: 'StationSupportSetupITServiceATBMTTDLVideoConf',
        name: 'StationSupportSetupITServiceATBMTTDLVideoConf',
        meta: {
          title: '[UR2.6.049] - Đài hỗ trợ khai báo Dịch vụ CNTT- ATBMTT-TTDL-Hội nghị truyền hình',
          des: '/#/contract/StationSupportSetupITServiceATBMTTDLVideoConf?tag=9%2B1%3B3%3B4%3B7%3B30%3B41%3B8%2B14%3B15%3B16%3B13'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'ManagementArea',
        name: 'ManagementArea',
        meta: {
          title: '[UR2.13.001] - Quản lý địa bàn'
        },
        component: () => import('./setup/ManagementArea/index')
      },
      {
        path: 'ProcessDebtSubsByNetCenter',
        name: 'ProcessDebtSubsByNetCenter',
        meta: {
          title: '[UR2.4.024] - Đài NetCenter - Khóa mở thuê bao nợ cước'
        },
        component: () => import('./setup/ProcessDebtSubsByNetCenter/index')
      },
      {
        path: 'ProcessDebtSubsByTechTeam1',
        name: 'ProcessDebtSubsByTechTeam1',
        meta: {
          title: '[UR2.4.025] - Tổ KT1 - Khóa mở thuê bao nợ cước'
        },
        component: () => import('./setup/ProcessDebtSubsByTechTeam1/index')
      },
      {
        path: 'ProcessNoAccountFiberByTeleStation',
        name: 'ProcessNoAccountFiberByTeleStation',
        meta: {
          title: '[UR2.4.026] - Trạm VT - Khóa mở Fiber - FTTH không account'
        },
        component: () => import('./setup/ProcessNoAccountFiberByTeleStation/index')
      },
      {
        path: 'ProcessDebtSubsByFiberStation',
        name: 'ProcessDebtSubsByFiberStation',
        meta: {
          title: '[UR2.4.027] - Đài BR - Khóa mở thuê bao nợ cước'
        },
        component: () => import('./setup/ProcessDebtSubsByFiberStation/index')
      },
      {
        path: 'OperateLockIMS',
        name: 'OperateLockIMS',
        meta: {
          title: '[UR2.4.117] - ĐH PTTB TTKD khóa máy IMS'
        },
        component: () => import('./setup/OperateLockIMS/index')
      },
      {
        path: 'OnOffService_OMC',
        name: 'OnOffService_OMC',
        meta: {
          title: '[UR2.5.030] - OMC/Vip/Tổ CM - Khóa mở dịch vụ'
        },
        component: () => import('./setup/OnOffService_OMC/index')
      },
      {
        path: 'ProcessDebtSubsByOMC',
        name: 'ProcessDebtSubsByOMC',
        meta: {
          title: '[UR2.5.037] - OMG Khóa mở thuê bao nợ cước'
        },
        component: () => import('./setup/ProcessDebtSubsByOMC/index')
      },
      {
        path: 'SyncSubcriberDataToVNP',
        name: 'SyncSubcriberDataToVNP',
        meta: {
          title: '[UR2.1.028] - Đồng bộ dữ liệu thuê bao di động lên VNP',
          des: '/#/contract/SyncSubcriberDataToVNP'
        },
        component: () => import('./complete_profile/CompleteMobileInitProfile/index.js')
      },
      {
        path: 'ConstructeNewITServiceDataCenter',
        name: 'ConstructeNewITServiceDataCenter',
        meta: {
          title: '[UR2.6.033] - Thi công lắp mới Dịch vụ CNTT- TT dữ liệu',
          des: '/#/contract/ConstructeNewITServiceDataCenter'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'CheckIMSServicePartner',
        name: 'CheckIMSServicePartner',
        meta: {
          title: '[UR2.4.065] - Đối soát dịch vụ IMS đối tác'
        },
        props: {
          tagForm: {
            tthd_id: 11,
            dsloaihd_id: '1,3',
            dsdichvuvt_id: '21',
            flagTSKT: '',
            flagTTTB_IMS: 0
          }
        },
        component: () => import('./setup/DeclareIMS/index.js')
      },
      {
        path: 'ManageCorporateClientsMNP',
        name: 'ManageCorporateClientsMNP',
        meta: {
          title: 'UR2.1.072] - Quản lý KHDN MNP'
        },
        component: () => import('./ManageCorporateClientsMNP/index.vue')
      },
      {
        path: 'DeclareITService',
        name: 'DeclareITService',
        meta: {
          title: '[UR2.6.032] - Khai báo Dịch vụ CNTT',
          des: '/#/contract/DeclareITService'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'PrintPaymentReceipts',
        name: 'PrintPaymentReceipts',
        meta: {
          title: '[UR2.2.005] - In phiếu chi'
        },
        component: () => import('./payment/PrintPaymentReceipts/index.vue')
      },
      {
        path: 'SetupChangeTSLSpeedCrossSale',
        name: 'SetupChangeTSLSpeedCrossSale',
        component: () => import('./setup/SetupChangeTSLSpeedCrossSale/SetupChangeTSLSpeedCrossSale.vue'),
        meta: {
          title: '[Bán chéo]: Thay đổi tốc độ TSL bán chéo'
        }
      },
      {
        path: 'VattuTBTest',
        name: 'VattuTBTest',
        meta: {
          title: 'Test vật tư'
        },
        component: () => import('./complete_profile/CompleteProfileChangeTSMyTV/popups/index')
      },
      {
        path: 'ChangeMegaWanConfigCrossSell',
        name: 'ChangeMegaWanConfigCrossSell',
        meta: {
          title: 'Thay đổi thông số MegaWan bán chéo'
        },
        component: () => import('./setup/ChangeMegaWanConfigCrossSell/index.js')
      },
      {
        path: 'SurveyRequestCrossSale',
        name: 'SurveyRequestCrossSale',
        component: SurveyRequestCrossSale,
        meta: {
          title: 'BSS-52077 - [Bán chéo]: Tiếp nhận yêu cầu khảo sát lắp đặt bán chéo'
        }
      },
      {
        path: 'ChangeSubsTypeCrossSale',
        name: 'ChangeSubsTypeCrossSale',
        component: ChangeSubsTypeCrossSale,
        meta: {
          title: 'BSS-52075 - [Bán chéo]: Thay đổi loại hình thuê bao bán chéo'
        }
      },
      {
        path: 'SyncDataVTN',
        name: 'SyncDataVTN',
        component: SyncDataVTN,
        meta: {
          title: '[UR2.1.023] - Đồng bộ dữ liệu VTN'
        }
      },
      {
        path: 'InstallReplaceRecallWifi',
        name: 'InstallReplaceRecallWifi',
        meta: {
          title: '[UR2.4.008] - Cài đặt Wifi - Thay thế - Thu hồi vật tư'
        },
        component: () => import('./setup/InstallReplaceRecallWifi')
      },
      {
        path: 'CompleteProfileSubMegaEyes',
        name: 'CompleteProfileSubMegaEyes',
        component: () => import('./setup/InstallInternet/index.vue'),
        meta: {
          title: '[UR2.6.001] Hoàn thiện hồ sơ thuê bao MegaEyes'
        }
      },
      {
        path: 'PauseUse',
        name: 'PauseUse',
        meta: {
          title: '[UR2.6.006] - Tạm dừng sử dụng'
        },
        component: () => import('./setup/ChangeServiceOMCVIPCM/index.js')
      },
      {
        path: 'SetupLandline_OMC',
        name: 'SetupLandline_OMC',
        component: () => import('./setup/DeclareLandline/index.vue'),
        meta: {
          title: 'Khai báo Cố định'
        }
      },
      {
        path: 'CompleteProfileCrosssellChangeTLSSpeed',
        name: 'CompleteProfileCrosssellChangeTLSSpeed',
        meta: {
          title: 'Hoàn thiện hồ sơ bán chéo thay đổi tốc độ TSL'
        },
        component: () => import('../install/ChangeTSLSpeed/index.js')
      },
      {
        path: 'ChangeServiceAddCrossSale',
        name: 'ChangeServiceAddCrossSale',
        meta: {
          title: '[BSS-52080] - [Bán chéo]: Thay đổi dịch vụ gia tăng bán chéo'
        },
        component: () => import('./setup/ChangeServiceAddCrossSale/index.js')
      },
      {
        path: 'ChangeParameterMegawanMetronet',
        name: 'ChangeParameterMegawanMetronet',
        component: () => import('./setup/ChangeParameterMegawan'),
        meta: {
          title: '[UR2.5.031] - OMC - Thay đổi thông số Megawan/Metronet'
        }
      },
      {
        path: 'CompleteProfileChangeTSLSpeed',
        name: 'CompleteProfileChangeTSLSpeed',
        meta: {
          title: 'CompleteProfileChangeTSLSpeed'
        },
        component: () => import('../install/ChangeTSLSpeed/index.js')
      },
      {
        path: 'InstallInternetOMC',
        name: 'InstallInternetOMC',
        component: () => import('./setup/InstallInternet'),
        meta: {
          title: 'InstallInternetOMC'
        }
      },
      {
        path: 'InstallMegaEyesNetCenter',
        name: 'InstallMegaEyesNetCenter',
        component: () => import('./setup/InstallInternet/index.vue'),
        meta: {
          title: 'Đài NetCenter - Khai báo thuê bao MegaEyes'
        }
      },
      // {
      //   path: 'RestoreLiquidationSubsCrossSale',
      //   name: 'RestoreLiquidationSubsCrossSale',
      //   component: () => import('./setup/RestoreLiquidationSubsCrossSale'),
      //   meta: {
      //     title: '[BSS-52076] - Khôi phục thuê bao thanh lý bán chéo'
      //   }
      // },
      // {
      //   path: 'ChangeParameterMegawanMetronet',
      //   name: 'ChangeParameterMegawanMetronet',
      //   component: () => import('./setup/ChangeParameterMegawan'),
      //   meta: {
      //     title: '[UR2.5.031] - OMC - Thay đổi thông số Megawan/Metronet'
      //   }
      // },
      {
        path: 'RestoreLiquidationSubsCrossSale',
        name: 'RestoreLiquidationSubsCrossSale',
        component: () => import('./setup/RestoreLiquidationSubsCrossSale'),
        meta: {
          title: '[BSS-52076] - Khôi phục thuê bao thanh lý bán chéo'
        }
      },
      {
        path: 'ChangeParameterMegawan_KTVT',
        name: 'ChangeParameterMegawan_KTVT',
        component: () => import('./setup/ChangeParameterMegawan'),
        meta: {
          title: '[UR2.4.038] - Tổ KTVT - Thay đổi thông số Megawan'
        }
      },
      {
        path: 'HTHSSetupNewITService_TTDL',
        name: 'HTHSSetupNewITService_TTDL',
        meta: {
          title: '{BSS-1200}-[UR2.6.047] - Hoàn thiện hồ sơ đặt mới Dịch vụ CNTT - TTDL',
          des: '/#/contract/HTHSSetupNewITService_TTDL'
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/index.js')
      },
      {
        path: 'SplitPointSetupChangeParaMegawanMetro',
        name: 'SplitPointSetupChangeParaMegawanMetro',
        component: () => import('./setup/ChangeParameterMegawan'),
        meta: {
          title: '[UR2.4.087] - Điểm chia - Thi công thay đổi thông số Megawan/Metronet'
        }
      },
      {
        path: 'InstallNewSubsCrossSale',
        name: 'InstallNewSubsCrossSale',
        meta: {
          title: '{BSS-52084} - [Bán chéo]: Lắp đặt mới bán chéo'
        },
        component: () => import('./setup/InstallNewSubsCrossSale')
      },
      {
        path: 'InstallMegaCameraKTNV',
        name: 'InstallMegaCameraKTNV',
        component: () => import('./setup/InstallInternet/index.vue'),
        meta: {
          title: '[UR2.4.045] - Tổ KTVT - Thi công MegaCarmera'
        }
      },
      {
        path: 'CompleteVideoConference',
        name: 'CompleteVideoConference',
        component: () => import('./setup/InstallInternet/index.vue'),
        meta: {
          title: '[UR2.6.052] - Hoàn công dịch vụ Hội nghị truyền hình'
        }
      },
      {
        path: 'ChangeNumberAccount',
        name: 'ChangeNumberAccount',
        meta: {
          title: '[UR2.1.012] - Thay đổi số máy/Account'
        },
        component: () => import('./setup/ChangeNumberAccount/ChangeNumberAccount')
      },
      {
        path: 'InterconnectTSLPartner',
        name: 'InterconnectTSLPartner',
        meta: {
          title: '[UR2.4.067] - Đối soát dịch vụ TSL đối tác',
          des: '/#/contract/InterconnectTSLPartner?tag=11%2B1%3B3%2B24'
        },
        component: () => import('./setup/DeclareMPLS/index.js')
      },
      {
        path: 'SolveLandLine_DHTC',
        name: 'SolveLandLine_DHTC',
        component: () => import('./setup/DeclareLandline/index.vue'),
        meta: {
          title: 'ĐHTC - Xử lý cố định'
        }
      },
      {
        path: 'DeclareInternetSubscriber',
        name: 'DeclareInternetSubscriber',
        component: () => import('./setup/InstallInternet'),
        meta: {
          title: 'Khai báo Internet'
        }
      },
      {
        path: 'CheckFixedBroadbandServicePartner',
        name: 'CheckFixedBroadbandServicePartner',
        component: () => import('./setup/InstallInternet'),
        meta: {
          title: 'Đối soát dịch vụ BRCĐ đối tác'
        }
      },
      {
        path: 'CompleteProfileLandline',
        name: 'CompleteProfileLandline',
        component: () => import('./setup/DeclareLandline/index.vue'),
        meta: {
          title: '[UR2.6.016] - HTHS Khôi phục Cố định'
        }
      },
      {
        path: 'SplitPointProvidePortChangeParamMegaMetro',
        name: 'SplitPointProvidePortChangeParamMegaMetro',
        component: () => import('./setup/ChangeParameterMegawan'),
        meta: {
          title: '[UR2.4.089] - Điểm chia - Cấp port thay đổi thông số Megawan/Metronet'
        }
      },
      {
        path: 'DeclarePortInternet',
        name: 'DeclarePortInternet',
        component: () => import('./setup/InstallInternet/index.vue'),
        meta: {
          title: 'Trạm VT- Cấp port Internet'
        }
      },
      {
        path: 'StandardizeNumber',
        name: 'StandardizeNumber',
        component: () => import('./StandardizeNumber/StandardizeNumber.vue'),
        meta: {
          title: '[] - Chuẩn hóa số liên hệ'
        }
      },
      {
        path: 'ChoseNumberInProvinceWarehouse',
        name: 'chosenumberinprovincewarehouse',
        meta: {
          title: '[UR2.11.014] - Chọn số cho khách hàng kho tỉnh'
        },
        component: () => import('../prepaid/ChoosePreapaidNumber/index.js')
      },
      {
        path: 'PaymentV2',
        name: 'PaymentV2',
        meta: {
          title: '[UR3.5.016] - In phiếu TT hợp đồng'
        },
        component: () => import('./setup/PaymentV2/index.js')
      },
      {
        path: 'ChangePasswordIMS',
        name: 'ChangePasswordIMS',
        meta: {
          title: 'ChangePasswordIMS'
        },
        component: () => import('./setup/ChangePasswordIMS/index.js')
      },
      {
        path: 'ResetCellGphone',
        name: 'resetCellGphone',
        meta: {
          title: '[UR2.4.055] - Reset Cell GPhone'
        },
        component: () => import('./setup/ResetCellGphone/index.js')
      },
      {
        path: 'CompleteInternetRecovery',
        name: 'CompleteInternetRecovery',
        component: () => import('./setup/InstallInternet'),
        meta: {
          title: 'HTHS Khôi phục Internet'
        }
      },
      {
        path: 'HTHSRegisMultisim',
        name: 'HTHSRegisMultisim',
        meta: {
          title: '[UR2.6.055] - Hoàn thiện hồ sơ đăng ký Multisim'
        },
        component: () => import('./setup/HTHSRegisMultisim/HTHSRegisMultisim.vue')
      },
      {
        path: 'WebDebtPaymentHistory',
        name: 'WebDebtPaymentHistory',
        meta: {
          title: 'Lịch sử nợ thanh toán'
        },
        component: () => import('./setup/WebDebtPaymentHistory/index.vue')
      },
      {
        path: 'DeclareServiceRevenueCodeList',
        name: 'DeclareServiceRevenueCodeList',
        component: () => import('./setup/DeclareServiceRevenueCodeList'),
        meta: {
          title: 'Bổ sung chức năng khai báo danh mục mã doanh thu dịch vụ'
        }
      },
      {
        path: 'ConfServiceRevenueCodeSubTypeBill',
        name: 'ConfServiceRevenueCodeSubTypeBill',
        component: () => import('./setup/ConfServiceRevenueCodeSubTypeBill')
      },
      // {
      //   path: 'RegisSiptrunkingService_MobilePostPaid',
      //   name: 'RegisSiptrunkingService_MobilePostPaid',
      //   meta:{
      //     title: 'RegisSiptrunkingService_MobilePostPaid'
      //   },
      //   component: () => import('./setup/RegisSiptrunkingService_MobilePostPaid/index.vue')
      // },
      {
        path: 'RegisSiptrunkingService_MobilePostPaid',
        name: 'RegisSiptrunkingService_MobilePostPaid',
        meta: {
          title: 'RegisSiptrunkingService_MobilePostPaid'
        },
        component: () => import('./setup/RegisSiptrunkingService_MobilePostPaid/index.vue')
      },
      {
        path: 'RegisterServiceMEG',
        name: 'RegisterServiceMEG',
        meta: {
          title: 'Đăng ký dịch vụ MEG'
        },
        component: () => import('./setup/RegisterServiceMEG/index.vue')
      },
      {
        path: 'TraCuuHocBaDienTu',
        name: 'TraCuuHocBaDienTu',
        meta: {
          title: 'Tra cứu Học bạ điện tử'  
        },
        component: () => import('./complete_profile/CompleteProfileITVAS/popups/popupHocBaDienTu/TraCuuHocBaDienTu.vue')
      },
      {
        path: 'PrepaidDeposite',
        name: 'PrepaidDeposite',
        meta: {
        title: 'Đặt cọc trả trước'
        },
        component: () => import('./setup/PrepaidDeposite/index.js')
      },
      {
        path: 'HandleInstallRequest',
        name: 'HandleInstallRequest',
        meta: {
          title: '[UR2.1.051] - Xử lý yêu cầu lắp đặt'
        },
        component: () => import('./setup/HandleInstallRequest/index.js')
      },
      {
        path: 'ReceiveSaleCareContract',
        name: 'ReceiveSaleCareContract',
        meta: {
          title: 'Tiếp nhận hợp đồng bán chăm'
        },
        component: () => import('./setup/ReceiveSaleCareContract/index.js')
      },
      {
        path: 'RequestInstall',
        name: 'RequestInstall',
        meta: {
          title: '[UR2.1.041] - Tiếp nhận yêu cầu lắp đặt'
        },
        component: () => import('./setup/RequestInstall')
      },
      {
        path: 'ChangeCustomerInfor',
        name: 'ChangeCustomerInfor',
        meta: {
          title: 'ChangeCustomerInfor'
        },
        component: () => import('./setup/ChangeCustomerInfor/index.js')
      },
      {
        path: 'FinishTSL_Net',
          name: 'FinishTSL_Net',
          meta: {
            title: 'FinishTSL'
          },
          component: () => import('./setup/FinishTSL_Net')
      },      
      { path: 'SyncDataDVGT', name: 'SyncDataDVGT', meta: { title: 'Đồng bộ dịch vụ GTGT' }, component: () => import('./SyncData/SyncDataDVGT/index.vue') },
      {
        path: 'PrepaidDepositeV2',
        name: 'PrepaidDepositeV2',
        meta: {
          title: 'Đặt cọc trả trước V2'
        },
        component: () => import('./setup/PrepaidDepositeV2')
      },
      {
        path: 'DivisionRevenueCrossSell',
        name: 'DivisionRevenueCrossSell',
        meta: {
          title: 'CƠ CHẾ PHÂN CHIA DOANH THU BÁN CHÉO'
        },
        component: () => import('./setup/DivisionRevenueCrossSell/index.js')
      },
      {
        path: 'ProvideDevices4GforSubscribers',
        name: 'ProvideDevices4GforSubscribers',
        meta: {
          title: 'Cấp thiết bị 4G cho thuê bao'
        },
        component: () => import('./setup/ProvideDevices4GforSubscribers')
      },
      {
        path: 'LookUpCrossSellDivisionInfo',
        name: 'LookUpCrossSellDivisionInfo',
        meta: {
          title: 'Thông tin phân chia doanh thu bán chéo'
        },
        component: () => import('./LookUpCrossSellDivisionInfo')
      },
      {
        path: 'SearchSubscriberCrossSale',
        name: 'SearchSubscriberCrossSale',
        meta: {
          title: ' SearchSubscriberCrossSale'
        },
        component: () => import('./setup/SearchSubscriberCrossSale')
      },
      {
        path: 'MapSubscriberCrossSale',
        name: 'MapSubscriberCrossSale',
        meta: {
          title: ' MapSubscriberCrossSale'
        },
        component: () => import('./setup/MapSubscriberCrossSale')
      },
     // { path: 'test', name: 'test',  component: () => import('./complete_profile/CompleteProfileITVAS/SMS_A2P/frm_Create_Agent_SMPP.vue') }
    ]
  }
]
