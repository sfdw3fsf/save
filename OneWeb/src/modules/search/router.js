import LayoutMain from '@/layouts/LayoutMain'
import PrintHandoverDoc from './subscriber/PrintHandoverDoc/PrintHandoverDoc'
import SearchChangeContact from './subscriber/SearchChangeContact'
import PayBillByFile from './subscriber/PayBillByFile'
import SearchHistoryLockUnlockPayTV from './subscriber/SearchHistoryLockUnlockPayTV'
import SearchPromotionChange from './subscriber/SearchPromotionChange'
import SearchSubcriberByNoAppMyVNPT from './subscriber/SearchSubcriberByNoAppMyVNPT'
import ChangeSubsType from '../contract/setup/ChangeSubsType'
import SearchHistoryPayrollManagementStaff from './subscriber/SearchHistoryPayrollManagementStaff'
import CheckIT from './subscriber/CheckIT'
import ChangeMegawanInfor from './subscriber/ChangeMegawanInfor'
import SearchMobileSolutions from './subscriber/SearchMobileSolutions/SearchMobileSolutions.vue'
import SearchDepositCustomersChooseNumber from './subscriber/SearchDepositCustomersChooseNumber'
import SearchPersonalBusinessInfor from './subscriber/SearchPersonalBusinessInfor'
import SearchRegisterKMCBDATAPackage from './subscriber/SearchRegisterKMCBDATAPackage'
import ChiTietKhachHang from './subscriber/SearchMobileSolutions/components/ChiTietKhachHang.vue'
import LichSuTacDong from './subscriber/SearchMobileSolutions/components/LichSuTacDong.vue'
import DanhSachKhachhang from './subscriber/SearchMobileSolutions/components/DanhSachKhachhang.vue'
import todolist from './subscriber/todolist'
export default [
  {
    path: '/search',
    name: 'search',
    component: LayoutMain,
    redirect: '/search/home',
    children: [
      {
        path: 'home',
        name: 'SEARCH home page',
        component: () => import('./Home'),
        meta: {
          title: 'Trang chủ'
        }
      },
      {
        path: 'SearchSubscriber',
        name: 'SearchSubscriber',
        meta: {
          title: '[UR2.8.014] - Tra cứu phát triển'
        },
        component: () => import('./subscriber/SearchSubscriber/index.js')
      },
      {
        path: 'SearchSubsInfo',
        name: 'SearchSubsInfo',
        component: () => import('./subscriber/SearchSubsInfo/index.js'),
        meta: {
          title: '[UR2.8.016] - Tra cứu thông tin thuê bao, tác động'
        }
      },
      {
        path: 'SearchSubsAccount',
        name: 'SearchSubsAccount',
        component: () => import('./subscriber/SearchSubsAccount/index.js'),
        meta: {
          title: '[UR2.8.033] - Tra cứu danh bạ thuê bao'
        }
      },
      {
        path: 'SearchOrder',
        name: 'SearchOrder',
        component: () => import('./subscriber/SearchOrder/index.js'),
        meta: {
          title: '[KHDN-344] - Tra cứu đơn hàng'
        }
      },
      {
        path: 'SearchMaintenanceDetails',
        name: 'SearchMaintenanceDetails',
        component: () => import('./subscriber/SearchMaintenanceDetails/index.vue'),
        meta: {
          title: 'Tra cứu chi tiết phiếu cải tạo, bảo dưỡng'
        }
      },
      {
        path: 'SearchAccount',
        name: 'account',
        component: () => import('./subscriber/SearchAccount/index.js'),
        meta: { title: '[UR2.8.002] - Tra cứu danh bạ' }
      },
      // {
      //   path: 'CreateNESubs',
      //   name: 'CreateNESubs',
      //   component: () => import('./subscriber/CreateNESubs/index.js'),
      //   meta: { title:  'Tạo số NE' },
      // },
      // {
      //   path: 'ReceiveConsultingContract',
      //   name: 'ReceiveConsultingContract',
      //   component: () => import('./subscriber/ReceiveConsultingContract'),
      //   meta: { title: '[UR2.8.018] - Tiếp nhận hợp đồng tư vấn' },
      // },
      {
        path: 'SearchIncomeTicket',
        name: 'searchincometicket',
        component: () => import('./subscriber/SearchIncomeTicket/index.js'),
        meta: {
          title: '[UR2.8.029] - Tra cứu phiếu giao đến'
        }
      },
      {
        path: 'SearchOAZaloSubs',
        name: 'searchoazalosubs',
        meta: { title: '[UR2.8.046] - Tra cứu khách hàng sử dụng OA Zalo' },
        component: () => import('./subscriber/SearchOAZaloSubs/index.js')
      },
      {
        path: 'PrintHandoverDoc',
        name: 'PrintHandoverDoc',
        meta: { title: '[UR2.8.027] - In biên bản nghiệm thu' },
        component: PrintHandoverDoc
      },
      {
        path: 'SearchProfileDetail',
        name: 'SearchProfileDetail',
        meta: { title: '[UR2.9.007] - Tra cứu chi tiết hồ sơ' },
        component: () => import('./subscriber/SearchProfileDetail')
      },
      {
        path: 'SearchPortDetail',
        name: 'searchportdetail',
        component: () => import('./subscriber/SearchPortDetail/index.js'),
        meta: {
          title: '[UR2.8.026] - Tra cứu chi tiết Port'
        }
      },
      {
        path: 'SearchEProfile',
        name: 'SearchEProfile',
        component: () => import('./subscriber/SearchEProfile/index.js'),
        meta: {
          title: '[UR2.8.038] - Tra cứu hồ sơ điện tử'
        }
      },
      // {
      //     path: 'searchMSISDN',
      //     name: 'searchMSISDN',
      //     component: () =>
      //         import ('./subscriber/SearchMSISDN/index.js'),
      //     meta: {
      //         title: '[UR2.8.032] - Tra cứu kho số'
      //     }
      // },
      {
        path: 'SearchITServiceByTechnicalParam',
        name: 'SearchITServiceByTechnicalParam',
        component: () => import('./subscriber/SearchITServiceByTechnicalParam/index.js'),
        meta: {
          title: '[UR2.8.048] - Tra cứu dịch vụ CNTT theo TSKT'
        }
      },
      {
        path: 'SearchLandlineSubscriber',
        name: 'SearchLandlineSubscriber',
        component: () => import('./subscriber/SearchLandlineSubscriber/index.js'),
        meta: {
          title: '[UR2.8.031] - Tra cứu thuê bao cố định trên tổng đài'
        }
      },
      {
        path: 'SearchInstallDetail',
        name: 'SearchInstallDetail',
        component: () => import('./subscriber/SearchInstallDetail/index.js'),
        meta: {
          title: '[UR2.8.004] - Tra cứu chi tiết thi công'
        }
      },
      {
        path: 'SearchDeletedSubscribers',
        name: 'SearchDeletedSubscribers',
        component: () => import('./subscriber/SearchDeletedSubscribers/index.js'),
        meta: {
          title: '[BSS-38766] - Tra cứu thuê bao bị xóa'
        }
      },
      {
        path: 'SearchManual',
        name: 'SearchManual',
        component: () => import('./subscriber/SearchManual/index.js'),
        meta: {
          title: '[BSS-38758] - Tra cứu cẩm nang'
        }
      },
      {
        path: 'SearchService',
        name: 'SearchService',
        component: () => import('./subscriber/SearchService/index.js'),
        meta: {
          title: '[UR2.8.006] - Tra cứu dịch vụ'
        }
      },
      {
        path: 'SearchSubscriberInformation',
        name: 'SearchSubscriberInformation',
        component: () => import('./subscriber/SearchSubscriberInformation/index.js'),
        meta: {
          title: '[UR2.8.003] - Tra cứu biến động thông tin thuê bao'
        }
      },
      {
        path: 'SearchPort',
        name: 'subscriber-search-ports',
        meta: {
          title: '[UR2.8.007] - Tra cứu port'
        },
        component: () => import('./subscriber/SearchPort/index.js')
      },
      {
        path: 'SearchSubcriberMyTV',
        name: 'subscriberMyTV',
        meta: {
          title: '[UR2.8.017] - Tra cứu thuê bao MyTV'
        },
        component: () => import('./subscriber/SearchSubcriberMyTV/index.js')
      },
      {
        path: 'SearchConstructionManagement',
        name: 'SearchConstructionManagement',
        meta: {
          title: '[UR2.8.005] - Tra cứu điều hành TC'
        },
        component: () => import('./subscriber/SearchConstructionManagement/index.vue')
      },
      {
        path: 'SearchModemInformation',
        name: 'SearchModemInformation',
        meta: {
          title: '[UR2.8.043] - Tra cứu thông tin modem'
        },
        component: () => import('./subscriber/SearchModemInformation/index.vue')
      },
      {
        path: 'SearchBroadbandSubcriberExpired',
        name: 'SearchBroadbandSubcriberExpired',
        meta: {
          title: '[UR2.8.039] - TRA CỨU THUÊ BAO BRCĐ SẮP HẾT HẠN ĐẶT CỌC - KHUYẾN MẠI'
        },
        component: () => import('./subscriber/SearchBroadbandSubcriberExpired/index.vue')
      },
      {
        path: 'SearchChangeContact',
        name: 'SearchChangeContact',
        component: SearchChangeContact,
        meta: {
          title: '[UR2.8.025] - Tra cứu thay đổi thông tin danh bạ'
        }
      },
      {
        path: 'SearchConstructionNoConstruction',
        name: 'SearchConstructionNoConstruction',
        component: () => import('./subscriber/SearchConstructionNoConstruction/index.js'),
        meta: {
          title: '[UR2.8.024] - Tra cứu phiếu TC chưa thực hiện'
        }
      },
      {
        path: 'SearchSubcriberIMS',
        name: 'SearchSubcriberIMS',
        component: () => import('./subscriber/SearchSubcriberIMS/index.js'),
        meta: {
          title: '[UR2.8.019] - Tra cứu thuê bao IMS'
        }
      },
      {
        path: 'SearchSubcriberIMSNew',
        name: 'SearchSubcriberIMSNew',
        component: () => import('./subscriber/SearchSubcriberIMSNew/index.js'),
        meta: {
          title: '[UR2.8.020] - Tra cứu thuê bao IMS new'
        }
      },
      // {
      //   path: 'DetailChangeType',
      //   name: 'DetailChangeType',
      //   component: () => import('./subscriber/DetailChangeType/index.js'),
      //   meta: {
      //     title: '[UR2.8.011] - Chi tiết chuyển đổi loại hình'
      //   }
      // },
      {
        path: 'SearchVISAOtherProvince',
        name: 'SearchVISAOtherProvince',
        component: () => import('./subscriber/SearchVISAOtherProvince/index.js'),
        meta: {
          title: '[UR2.8.023] - Tra cứu Visa tỉnh khác'
        }
      },
      {
        path: 'ListSubcriberMyTVInactive',
        name: 'mytvinactive',
        meta: {
          title: '[UR2.8.021] - Danh sách thuê bao MyTV không hoạt động'
        },
        component: () => import('./subscriber/ListSubcriberMyTVInactive/index.vue')
      },
      {
        path: 'SearchEBillByCustomerAdmin',
        name: 'SearchEBillByCustomerAdmin',
        component: () => import('./subscriber/SearchEBillByCustomerAdmin/index.js'),
        meta: {
          title: '[UR2.8.040] - Tra cứu hóa đơn điện tử cho Khách hàng -Dùng cho Admin'
        }
      },
      {
        path: 'SearchEBillProvince',
        name: 'SearchEBillProvince',
        component: () => import('./subscriber/SearchEBillProvince/index.js'),
        meta: {
          title: '[UR2.8.042] - Tra cứu hóa đơn điện tử VTT'
        }
      },
      {
        path: 'SearchEBillByCustomer',
        name: 'SearchEBillByCustomer',
        component: () => import('./subscriber/SearchEBillByCustomer/index.js'),
        meta: {
          title: '[UR2.8.037] - Tra cứu hóa đơn điện tử theo khách hàng'
        }
      },
      {
        path: 'SearchHistoryVNP',
        name: 'SearchHistoryVNP',
        component: () => import('./subscriber/SearchHistoryVNP/index.js'),
        meta: {
          title: '[UR2.8.035] - Tra cứu lịch sử tác động gói trên VNP'
        }
      },
      {
        path: 'SearchOrderDetail',
        name: 'SearchOrderDetail',
        component: () => import('./subscriber/SearchOrderDetail/index.js'),
        meta: {
          title: '[UR2.8.044] - Tra cứu chi tiết đơn hàng'
        }
      },
      {
        path: 'SearchPaymentDebtHistory',
        name: 'SearchPaymentDebtHistory',
        meta: {
          title: 'Tra cứu lịch sử nợ thanh toán'
        },
        component: () => import('./subscriber/SearchPaymentDebtHistory/index.js')
      },
      {
        path: 'SearchCustomerDebt',
        name: 'SearchCustomerDebt',
        meta: {
          title: 'Tra cứu nợ của khách hàng'
        },
        component: () => import('./subscriber/SearchCustomerDebt/index.js')
      },
      {
        path: 'SearchCancellTicket',
        name: 'SearchCancellTicket',
        meta: {
          title: 'Tra cứu phiếu hủy'
        },
        component: () => import('./subscriber/SearchCancellTicket/index.js')
      },
      {
        path: 'SearchPaymentSlip',
        name: 'SearchPaymentSlip',
        meta: {
          title: 'Tra cứu phiếu thanh toán'
        },
        component: () => import('./subscriber/SearchPaymentSlip/index.js')
      },
      {
        path: 'SearchCustomerInforHistory',
        name: 'SearchCustomerInforHistory',
        meta: {
          title: 'Tra cứu lịch sử thông tin khách hàng'
        },
        component: () => import('./subscriber/SearchCustomerInforHistory/index.js')
      },
      {
        path: 'SearchContactDetails',
        name: 'SearchContactDetails',
        meta: {
          title: '[UR2.8.012] - Tra cứu chi tiết thông tin danh bạ'
        },
        component: () => import('./subscriber/SearchContactDetails/index.js')
      },
      {
        path: 'SearchFreightDebt',
        name: 'SearchFreightDebt',
        meta: {
          title: 'Tra cứu nợ cước'
        },
        component: () => import('./subscriber/SearchFreightDebt')
      },
      {
        path: 'ListSubscribersMalfunctionDevice',
        name: 'ListSubscribersMalfunctionDevice',
        meta: {
          title: 'Danh sách thuê bao, thiết bị sự cố'
        },
        component: () => import('./subscriber/ListSubscribersMalfunctionDevice')
      },
      {
        path: 'ComplaintProcess',
        name: 'ComplaintProcess',
        meta: {
          title: 'Tiến trình KN'
        },
        component: () => import('./subscriber/ComplaintProcess')
      },
      {
        path: 'Competitors',
        name: 'Competitors',
        meta: {
          title: 'Đối thủ cạnh tranh'
        },
        component: () => import('./subscriber/Competitors')
      },
      {
        path: 'SearchPackagesPromotions',
        name: 'SearchPackagesPromotions',
        component: () => import('./subscriber/SearchPackagesPromotions/index.js'),
        meta: {
          title: ' Tra cứu thông tin gói cước, khuyến mại'
        }
      },
      {
        path: 'SearchEsim',
        name: 'SearchEsim',
        component: () => import('./subscriber/SearchEsim/index.js'),
        meta: {
          title: ' Tra cứu esim'
        }
      },
      {
        path: 'SearchHighcostUploadData',
        name: 'SearchHighcostUploadData',
        component: () => import('./subscriber/SearchHighcostUploadData/index.js'),
        meta: {
          title: ' Tra cứu dữ liệu upload cước cao'
        }
      },
      {
        path: 'SearchSMS',
        name: 'SearchSMS',
        component: () => import('./subscriber/SearchSMS/index.js'),
        meta: {
          title: ' Tra cứu SMS'
        }
      },
      {
        path: 'MoveDetails',
        name: 'MoveDetails',
        meta: {
          title: '[UR2.8.010] - Chi tiết dịch chuyển'
        },
        component: () => import('./subscriber/MoveDetails/index.js')
      },
      // {
      //     path: 'SearchContract',
      //     name: 'SearchContract',
      //     meta: {
      //         title: '[UR2.8.001] - Tra cứu hợp đồng'
      //     },
      //     component: () =>
      //         import ('./subscriber/SearchContract/SearchContract.vue')
      // },
      {
        path: 'CompleteEquipmentSale',
        name: 'CompleteEquipmentSale',
        component: () => import('./subscriber/CompleteEquipmentSale/index.js'),
        meta: {
          title: 'Hoàn công bán thiết bị'
        }
      },
      {
        path: 'TransferTollCollection',
        name: 'TransferTollCollection',
        meta: {
          title: '[BSS-38467] - Chuyển tuyến thu'
        },
        component: () => import('./subscriber/TransferTollCollection')
      },
      {
        path: 'SearchGeneral',
        name: 'SearchGeneral',
        meta: {
          title: '[UR2.8.013] - Tra cứu tổng hợp'
        },
        component: () => import('./subscriber/SearchGeneral/index.js')
      },
      {
        // Đổi tên do trùng với module khác
        path: 'SearchGeneral_CCBS',
        name: 'SearchGeneral_CCBS',
        component: () => import('./subscriber/SearchGeneral_CCBS/index.js'),
        meta: {
          title: '[BSS-38519] - Tra cứu tổng hợp'
        }
      },
      {
        path: 'SearchSubcriberInfor',
        name: 'SearchSubcriberInfor',
        meta: {
          title: '[BSS38586] - Tra cứu thông tin thuê bao'
        },
        component: () => import('./ccbs/SearchSubcriberInfor/SearchSubcriberInfor.vue')
      },
      {
        path: 'SearchRegularPostpaidPackages',
        name: 'SearchRegularPostpaidPackages',
        component: () => import('./subscriber/SearchRegularPostpaidPackages/index.js'),
        meta: {
          title: '[BSS-38739] - Tra cứu gói cước thuê bao trả sau thường'
        }
      },
      {
        path: 'FreeEquipmentTBDC',
        name: 'FreeEquipmentTBDC',
        component: () => import('./subscriber/FreeEquipmentTBDC/index.js'),
        meta: {
          title: 'Trang bị miễn phí TBDC'
        }
      },
      {
        path: 'UpdateSplitPointsByList',
        name: 'UpdateSplitPointsByList',
        meta: {
          title: 'BSS-38775 - Cập nhật điểm chia theo danh sách'
        },
        component: () => import('./subscriber/UpdateSplitPointsByList/UpdateSplitPointsByList.vue')
      },
      {
        path: 'SearchMegawanConnectionInfor',
        name: 'SearchMegawanConnectionInfor',
        meta: {
          title: 'BSS-38514 - Tra cứu thông tin kết nối Megawan'
        },
        component: () => import('./subscriber/SearchMegawanConnectionInfor/SearchMegawanConnectionInfor.vue')
      },
      {
        path: 'InstallTicketStatistics',
        name: 'InstallTicketStatistics',
        meta: {
          title: '[UR2.3.007] - Thống kê phiếu thi công'
        },
        component: () => import('./subscriber/SearchIncomeTicket/index.js')
      },
      {
        path: 'PayBillByFile',
        name: 'PayBillByFile',
        component: PayBillByFile,
        meta: {
          title: 'Thanh toán hóa đơn theo file'
        }
      },
      {
        path: 'SearchHistoryLockUnlockPayTV',
        name: 'SearchHistoryLockUnlockPayTV',
        component: SearchHistoryLockUnlockPayTV,
        meta: {
          title: 'Tra cứu lịch sử khóa mở Pay TV'
        }
      },
      {
        path: 'SearchPromotionChange',
        name: 'SearchPromotionChange',
        component: SearchPromotionChange,
        meta: {
          title: 'Tra cứu biến động khuyến mại'
        }
      },
      {
        path: 'SearchSubcriberByNoAppMyVNPT',
        name: 'SearchSubcriberByNoAppMyVNPT',
        component: SearchSubcriberByNoAppMyVNPT,
        meta: {
          title: 'Tra cứu thuê bao theo số liên hệ App MyVNPT'
        }
      },
      {
        path: 'DetailChangeType',
        name: 'DetailChangeType',
        component: ChangeSubsType,
        meta: {
          title: '[UR2.8.011] - Chi tiết chuyển đổi loại hình'
        }
      },
      {
        path: 'SearchContactsCrossSellContracts',
        name: 'SearchContactsCrossSellContracts',
        meta: {
          title: '[BSS-38462] - Tra cứu Danh bạ, hợp đồng bán chéo'
        },
        component: () => import('./subscriber/SearchContactsCrossSellContracts')
      },
      // {
      //   path: 'SearchCrossSellAggregate',
      //   name: 'SearchCrossSellAggregate',
      //   meta:{
      //     title: '[BSS-38461] - Tra cứu tổng hợp bán chéo'
      //   },
      //   component: () => import('./subscriber/SearchCrossSellAggregate')
      // },
      {
        path: 'IPRentalManagement',
        name: 'IPRentalManagement',
        meta: {
          title: '[UR2.8.014] - Tra cứu phát triển'
        },
        component: () => import('./subscriber/IPRentalManagement')
      },
      {
        path: 'ChangeIMSInfor',
        name: 'ChangeIMSInfor',
        component: () => import('./subscriber/ChangeIMSInfor/index.vue'),
        meta: {
          title: 'BSS-38511 - Thay đổi thông số IMS'
        }
      },
      {
        path: 'SearchAreaByPlaceName',
        name: 'SearchAreaByPlaceName',
        component: () => import('./subscriber/SearchAreaByPlaceName/index.js'),
        meta: {
          title: 'BSS-38504 03. Code FE WEB - Tra cứu khu vực theo địa danh'
        }
      },
      {
        path: 'SearchHistoryPayrollManagementStaff',
        name: 'SearchHistoryPayrollManagementStaff',
        component: SearchHistoryPayrollManagementStaff,
        meta: {
          title: '- Tra cứu lịch sử điều chỉnh nhân viên quản lý tính lương KHDN'
        }
      },
      {
        path: 'SearchVNEduData',
        name: 'SearchVNEduData',
        component: () => import('./subscriber/SearchVNEduData/index.js'),
        meta: {
          title: '[BSS-38526] - Tra cứu dữ liệu VNEdu'
        }
      },
      {
        path: 'SynchronizeElectronicInvoices',
        name: 'SynchronizeElectronicInvoices',
        component: () => import('./subscriber/SynchronizeElectronicInvoices/index.js'),
        meta: {
          title: 'Đồng bộ hóa đơn điện tử'
        }
      },
      {
        path: 'CompleteEquipmentPurchase',
        name: 'CompleteEquipmentPurchase',
        component: () => import('./subscriber/CompleteEquipmentPurchase/index.js'),
        meta: {
          title: 'Hoàn công mua thiết bị'
        }
      },
      {
        path: 'SaleDevice_TTVT',
        name: 'SaleDevice_TTVT',
        meta: {
          title: 'BSS-38484 Bán thiết bị-TTVT'
        },
        component: () => import('./subscriber/SaleDevice_TTVT')
      },
      {
        path: 'SaleDevice_TTVT/Payment',
        name: 'SaleDevice_TTVT_Payment',
        meta: {
          title: 'BSS-38484 Bán thiết bị-TTVT'
        },
        component: () => import('../contract/setup/Payment')
      },
      {
        path: 'ReadFileContractTransfer',
        name: 'ReadFileContractTransfer',
        meta: {
          title: 'BSS-38485 Đọc file chuyển HĐ chính thức'
        },
        component: () => import('./subscriber/ReadFileContractTransfer')
      },
      {
        path: 'SearchDebtByMonth',
        name: 'SearchDebtByMonth',
        component: () => import('./ccbs/SearchDebtByMonth/SearchDebtByMonth.vue'),
        meta: {
          title: 'Tra cứu nợ theo tháng'
        }
      },
      {
        path: 'SearchGphoneInfor',
        name: 'SearchGphoneInfor',
        component: () => import('./ccbs/SearchGphoneInfor/SearchGphoneInfor.vue'),
        meta: {
          title: 'Tra cứu thông tin Gphone'
        }
      },
      {
        path: 'WaitingListPausedLiquidated',
        name: 'WaitingListPausedLiquidated',
        meta: {
          title: 'BSS-38482 Danh sách chờ thanh lý'
        },
        component: () => import('./subscriber/WaitingListPausedLiquidated/WaitingListPausedLiquidated.vue')
      },
      {
        path: 'SetTimeEditSurvey',
        name: 'SetTimeEditSurvey',
        meta: {
          title: 'BSS-38482 Thời gian chờ sửa phiếu khảo sát'
        },
        component: () => import('./subscriber/SetTimeEditSurvey/index.vue')
      },
      {
        path: 'UpdateADSLPort',
        name: 'UpdateADSLPort',
        meta: {
          title: '[BSS-38463] - Cập nhật adsl port'
        },
        component: () => import('./subscriber/UpdateADSLPort')
      },
      {
        path: 'PublicUtilityTelecomSubscriber',
        name: 'PublicUtilityTelecomSubscriber',
        meta: {
          title: 'Thuê bao viễn thông công ích'
        },
        component: () => import('./subscriber/PublicUtilityTelecomSubscriber/index.js')
      },
      {
        path: 'UpdateUserPasswordMYTV',
        name: 'UpdateUserPasswordMYTV',
        meta: {
          title: 'Cập nhật mật khẩu user'
        },
        component: () => import('./subscriber/UpdateUserPasswordMYTV/index.js')
      },
      {
        path: 'UpdateInvoice0VND',
        name: 'UpdateInvoice0VND',
        meta: {
          title: 'BSS-38501 Cập nhật hóa đơn 0 đồng'
        },
        component: () => import('./subscriber/UpdateInvoice0VND')
      },
      {
        path: 'UploadListProfileFiles',
        name: 'UploadListProfileFiles',
        meta: {
          title: 'BSS-38868 Upload danh sách file hồ sơ'
        },
        component: () => import('./subscriber/UploadListProfileFiles')
      },
      {
        path: 'SearchHotChargeAlert',
        name: 'SearchHotChargeAlert',
        meta: {
          title: '[UR....] - Tra cứu cảnh báo cước nóng'
        },
        component: () => import('./subscriber/SearchHotChargeAlert/index.vue')
      },
      {
        path: 'SearchFiveNumbers',
        name: 'SearchFiveNumbers',
        meta: {
          title: '[UR....] - Tra cứu 5 số'
        },
        component: () => import('./subscriber/SearchFiveNumbers/index.vue')
      },
      {
        path: 'SearchCallHistoryGphone',
        name: 'SearchCallHistoryGphone',
        component: () => import('./subscriber/SearchCallHistoryGphone/index.js'),
        meta: {
          title: '[BSS-38528] - Tra cứu lịch sử gọi GPhone'
        }
      },
      {
        path: 'SearchHistorySubcriberInforVNP',
        name: 'SearchHistorySubcriberInforVNP',
        component: () => import('./subscriber/SearchHistorySubcriberInforVNP/index.js'),
        meta: {
          title: 'BSS-38527 - Tra cứu lịch sử thông tin thuê bao VNP'
        }
      },
      {
        path: 'SyncSubscribersToSLASystem',
        name: 'SyncSubscribersToSLASystem',
        meta: {
          title: 'BSS-38493 - Đồng bộ thuê bao lên hệ thống SLA KHTCDN'
        },
        component: () => import('./subscriber/SyncSubscribersToSLASystem/SyncSubscribersToSLASystem.vue')
      },
      {
        path: 'UpdatePaymentContactByFile',
        name: 'UpdatePaymentContactByFile',
        component: () => import('./subscriber/UpdatePaymentContactByFile/index.vue'),
        meta: {
          title: 'BSS-38512 - Cập nhật liên hệ thanh toán theo File'
        }
      },
      {
        path: 'CreateTemplateBNM',
        name: 'CreateTemplateBNM',
        meta: {
          title: 'Khai báo template BNM'
        },
        component: () => import('./subscriber/CreateTemplateBNM')
      },
      {
        path: 'CompleteTransferRightsBC',
        name: 'CompleteTransferRightsBC',
        meta: {
          title: 'Hoàn thiện hồ sơ chuyển quyền BC'
        },
        component: () => import('./subscriber/CompleteTransferRightsBC')
      },
      {
        path: 'SearchWarrantyEquipment',
        name: 'SearchWarrantyEquipment',
        component: () => import('./subscriber/SearchWarrantyEquipment/index.js'),
        meta: {
          title: 'BSS-38523 - Tra cứu thiết bị bảo hành'
        }
      },
      {
        path: 'UpdateResultCoordinationTicket',
        name: 'UpdateResultCoordinationTicket',
        meta: {
          title: 'Cập nhật kết quả xử lý phiếu phối hợp'
        },
        component: () => import('./subscriber/UpdateResultCoordinationTicket')
      },
      {
        path: 'SearchPackagesMNP',
        name: 'SearchPackagesMNP',
        meta: {
          title: '[UR....] - Tra cứu gói cước MNP'
        },
        component: () => import('./subscriber/SearchPackagesMNP/index.vue')
      },
      {
        path: 'ModifyContractMoney',
        name: 'ModifyContractMoney',
        component: () => import('./subscriber/ModifyContractMoney/index.js'),
        meta: {
          title: '[UR1.1.025] - Sửa tiền hợp đồng'
        }
      },
      {
        path: 'ImportSubscriberCareWorkerFile',
        name: 'ImportSubscriberCareWorkerFile',
        component: () => import('./subscriber/ImportSubscriberCareWorkerFile/index.js'),
        meta: {
          title: 'Import file nhân viên chăm sóc thuê bao'
        }
      },
      {
        path: 'UpdateIntermediateSplitPoint',
        name: 'UpdateIntermediateSplitPoint',
        meta: {
          title: 'Cập nhật điểm chia trung gian'
        },
        component: () => import('./subscriber/UpdateIntermediateSplitPoint/index.js')
      },
      {
        path: 'SearchHotBill',
        name: 'SearchHotBill',
        component: () => import('./subscriber/SearchHotBill/index.js'),
        meta: {
          title: '[BSS-38524] - Tra cứu cước nóng'
        }
      },
      {
        path: 'SearchSubscriber',
        name: 'SearchSubscriber',
        meta: {
          title: '[UR2.8.014] - Tra cứu phát triển'
        },
        component: () => import('./subscriber/SearchSubscriber/index.js')
      },
      {
        path: 'SearchSubsInfo',
        name: 'SearchSubsInfo',
        component: () => import('./subscriber/SearchSubsInfo/index.js'),
        meta: {
          title: '[UR2.8.016] - Tra cứu thông tin thuê bao, tác động'
        }
      },
      {
        path: 'SearchSubsAccount',
        name: 'SearchSubsAccount',
        component: () => import('./subscriber/SearchSubsAccount/index.js'),
        meta: {
          title: '[UR2.8.033] - Tra cứu danh bạ thuê bao'
        }
      },
      {
        path: 'SearchAccount',
        name: 'account',
        component: () => import('./subscriber/SearchAccount/index.js'),
        meta: { title: '[UR2.8.002] - Tra cứu danh bạ' }
      },
      // {
      //   path: 'CreateNESubs',
      //   name: 'CreateNESubs',
      //   component: () => import('./subscriber/CreateNESubs/index.js'),
      //   meta: { title:  'Tạo số NE' },
      // },
      {
        path: 'CheckPort',
        name: 'checkport',
        component: () => import('./subscriber/CheckPort/index.vue'),
        meta: { title: '[UR2.8.018] - Test Port' }
      },
      // {
      //   path: 'ReceiveConsultingContract',
      //   name: 'ReceiveConsultingContract',
      //   component: () => import('./subscriber/ReceiveConsultingContract'),
      //   meta: { title: '[UR2.8.018] - Tiếp nhận hợp đồng tư vấn' },
      // },
      {
        path: 'SearchIncomeTicket',
        name: 'searchincometicket',
        component: () => import('./subscriber/SearchIncomeTicket/index.js'),
        meta: {
          title: '[UR2.8.029] - Tra cứu phiếu giao đến'
        }
      },
      {
        path: 'SearchOAZaloSubs',
        name: 'searchoazalosubs',
        meta: { title: '[UR2.8.046] - Tra cứu khách hàng sử dụng OA Zalo' },
        component: () => import('./subscriber/SearchOAZaloSubs/index.js')
      },
      {
        path: 'SearchIncomeTicket',
        name: 'incometicket',
        meta: { title: '[UR2.8.029] - Tra cứu phiếu giao đến' },
        component: () => import('./subscriber/SearchIncomeTicket/index.js')
      },
      {
        path: 'PrintHandoverDoc',
        name: 'PrintHandoverDoc',
        meta: { title: '[UR2.8.027] - In biên bản nghiệm thu' },
        component: PrintHandoverDoc
      },
      {
        path: 'SearchProfileDetail',
        name: 'SearchProfileDetail',
        meta: { title: '[UR2.9.007] - Tra cứu chi tiết hồ sơ' },
        component: () => import('./subscriber/SearchProfileDetail')
      },
      {
        path: 'SearchPortDetail',
        name: 'searchportdetail',
        component: () => import('./subscriber/SearchPortDetail/index.js'),
        meta: {
          title: '[UR2.8.026] - Tra cứu chi tiết Port'
        }
      },
      {
        path: 'SearchEProfile',
        name: 'SearchEProfile',
        component: () => import('./subscriber/SearchEProfile/index.js'),
        meta: {
          title: '[UR2.8.038] - Tra cứu hồ sơ điện tử'
        }
      },
      {
        path: 'searchMSISDN',
        name: 'searchMSISDN',
        component: () => import('./subscriber/SearchMSISDN/index.js'),
        meta: {
          title: '[UR2.8.032] - Tra cứu kho số'
        }
      },
      {
        path: 'SearchITServiceByTechnicalParam',
        name: 'SearchITServiceByTechnicalParam',
        component: () => import('./subscriber/SearchITServiceByTechnicalParam/index.js'),
        meta: {
          title: '[UR2.8.048] - Tra cứu dịch vụ CNTT theo TSKT'
        }
      },
      {
        path: 'SearchLandlineSubscriber',
        name: 'SearchLandlineSubscriber',
        component: () => import('./subscriber/SearchLandlineSubscriber/index.js'),
        meta: {
          title: '[UR2.8.031] - Tra cứu thuê bao cố định trên tổng đài'
        }
      },
      {
        path: 'SearchInstallDetail',
        name: 'SearchInstallDetail',
        component: () => import('./subscriber/SearchInstallDetail/index.js'),
        meta: {
          title: '[UR2.8.004] - Tra cứu chi tiết thi công'
        }
      },
      {
        path: 'SearchDeletedSubscribers',
        name: 'SearchDeletedSubscribers',
        component: () => import('./subscriber/SearchDeletedSubscribers/index.js'),
        meta: {
          title: '[BSS-38766] - Tra cứu thuê bao bị xóa'
        }
      },
      {
        path: 'SearchManual',
        name: 'SearchManual',
        component: () => import('./subscriber/SearchManual/index.js'),
        meta: {
          title: '[BSS-38758] - Tra cứu cẩm nang'
        }
      },
      {
        path: 'SearchService',
        name: 'SearchService',
        component: () => import('./subscriber/SearchService/index.js'),
        meta: {
          title: '[UR2.8.006] - Tra cứu dịch vụ'
        }
      },
      {
        path: 'SearchSubscriberInformation',
        name: 'SearchSubscriberInformation',
        component: () => import('./subscriber/SearchSubscriberInformation/index.js'),
        meta: {
          title: '[UR2.8.003] - Tra cứu biến động thông tin thuê bao'
        }
      },
      {
        path: 'SearchPort',
        name: 'subscriber-search-ports',
        meta: {
          title: '[UR2.8.007] - Tra cứu port'
        },
        component: () => import('./subscriber/SearchPort/index.js')
      },
      {
        path: 'SearchSubcriberMyTV',
        name: 'subscriberMyTV',
        meta: {
          title: '[UR2.8.017] - Tra cứu thuê bao MyTV'
        },
        component: () => import('./subscriber/SearchSubcriberMyTV/index.js')
      },
      {
        path: 'SearchConstructionManagement',
        name: 'SearchConstructionManagement',
        meta: {
          title: '[UR2.8.005] - Tra cứu điều hành TC'
        },
        component: () => import('./subscriber/SearchConstructionManagement/index.vue')
      },
      {
        path: 'SearchModemInformation',
        name: 'SearchModemInformation',
        meta: {
          title: '[UR2.8.043] - Tra cứu thông tin modem'
        },
        component: () => import('./subscriber/SearchModemInformation/index.vue')
      },
      {
        path: 'SearchBroadbandSubcriberExpired',
        name: 'SearchBroadbandSubcriberExpired',
        meta: {
          title: '[UR2.8.039] - TRA CỨU THUÊ BAO BRCĐ SẮP HẾT HẠN ĐẶT CỌC - KHUYẾN MẠI'
        },
        component: () => import('./subscriber/SearchBroadbandSubcriberExpired/index.vue')
      },
      {
        path: 'SearchChangeContact',
        name: 'SearchChangeContact',
        component: SearchChangeContact,
        meta: {
          title: '[UR2.8.025] - Tra cứu thay đổi thông tin danh bạ'
        }
      },
      {
        path: 'SearchConstructionNoConstruction',
        name: 'SearchConstructionNoConstruction',
        component: () => import('./subscriber/SearchConstructionNoConstruction/index.js'),
        meta: {
          title: '[UR2.8.024] - Tra cứu phiếu TC chưa thực hiện'
        }
      },
      {
        path: 'SearchSubcriberIMS',
        name: 'SearchSubcriberIMS',
        component: () => import('./subscriber/SearchSubcriberIMS/index.js'),
        meta: {
          title: '[UR2.8.019] - Tra cứu thuê bao IMS'
        }
      },
      {
        path: 'SearchSubcriberIMSNew',
        name: 'SearchSubcriberIMSNew',
        component: () => import('./subscriber/SearchSubcriberIMSNew/index.js'),
        meta: {
          title: '[UR2.8.020] - Tra cứu thuê bao IMS new'
        }
      },
      // {
      //   path: 'DetailChangeType',
      //   name: 'DetailChangeType',
      //   component: () => import('./subscriber/DetailChangeType/index.js'),
      //   meta: {
      //     title: '[UR2.8.011] - Chi tiết chuyển đổi loại hình'
      //   }
      // },
      {
        path: 'SearchVISAOtherProvince',
        name: 'SearchVISAOtherProvince',
        component: () => import('./subscriber/SearchVISAOtherProvince/index.js'),
        meta: {
          title: '[UR2.8.023] - Tra cứu Visa tỉnh khác'
        }
      },
      {
        path: 'ListSubcriberMyTVInactive',
        name: 'mytvinactive',
        meta: {
          title: '[UR2.8.021] - Danh sách thuê bao MyTV không hoạt động'
        },
        component: () => import('./subscriber/ListSubcriberMyTVInactive/index.vue')
      },
      {
        path: 'SearchEBillByCustomerAdmin',
        name: 'SearchEBillByCustomerAdmin',
        component: () => import('./subscriber/SearchEBillByCustomerAdmin/index.js'),
        meta: {
          title: '[UR2.8.040] - Tra cứu hóa đơn điện tử cho Khách hàng -Dùng cho Admin'
        }
      },
      {
        path: 'SearchEBillProvince',
        name: 'SearchEBillProvince',
        component: () => import('./subscriber/SearchEBillByCustomer/index.js'),
        meta: {
          title: '[UR2.8.042] - Tra cứu hóa đơn điện tử VTT'
        }
      },
      {
        path: 'SearchEBillByCustomer',
        name: 'SearchEBillByCustomer',
        component: () => import('./subscriber/SearchEBillByCustomer/index.js'),
        meta: {
          title: '[UR2.8.037] - Tra cứu hóa đơn điện tử theo khách hàng'
        }
      },
      {
        path: 'SearchHistoryVNP',
        name: 'SearchHistoryVNP',
        component: () => import('./subscriber/SearchHistoryVNP/index.js'),
        meta: {
          title: '[UR2.8.035] - Tra cứu lịch sử tác động gói trên VNP'
        }
      },
      {
        path: 'SearchOrderDetail',
        name: 'SearchOrderDetail',
        component: () => import('./subscriber/SearchOrderDetail/index.js'),
        meta: {
          title: '[UR2.8.044] - Tra cứu chi tiết đơn hàng'
        }
      },
      {
        path: 'SearchPaymentDebtHistory',
        name: 'SearchPaymentDebtHistory',
        meta: {
          title: 'Tra cứu lịch sử nợ thanh toán'
        },
        component: () => import('./subscriber/SearchPaymentDebtHistory/index.js')
      },
      {
        path: 'SearchCustomerDebt',
        name: 'SearchCustomerDebt',
        meta: {
          title: 'Tra cứu nợ của khách hàng'
        },
        component: () => import('./subscriber/SearchCustomerDebt/index.js')
      },
      {
        path: 'SearchCancellTicket',
        name: 'SearchCancellTicket',
        meta: {
          title: 'Tra cứu phiếu hủy'
        },
        component: () => import('./subscriber/SearchCancellTicket/index.js')
      },
      {
        path: 'SearchPaymentSlip',
        name: 'SearchPaymentSlip',
        meta: {
          title: 'Tra cứu phiếu thanh toán'
        },
        component: () => import('./subscriber/SearchPaymentSlip/index.js')
      },
      {
        path: 'SearchCustomerInforHistory',
        name: 'SearchCustomerInforHistory',
        meta: {
          title: 'Tra cứu lịch sử thông tin khách hàng'
        },
        component: () => import('./subscriber/SearchCustomerInforHistory/index.js')
      },
      {
        path: 'SearchContactDetails',
        name: 'SearchContactDetails',
        meta: {
          title: '[UR2.8.012] - Tra cứu chi tiết thông tin danh bạ'
        },
        component: () => import('./subscriber/SearchContactDetails/index.js')
      },
      {
        path: 'SearchFreightDebt',
        name: 'SearchFreightDebt',
        meta: {
          title: 'Tra cứu nợ cước'
        },
        component: () => import('./subscriber/SearchFreightDebt')
      },
      {
        path: 'ListSubscribersMalfunctionDevice',
        name: 'ListSubscribersMalfunctionDevice',
        meta: {
          title: 'Danh sách thuê bao, thiết bị sự cố'
        },
        component: () => import('./subscriber/ListSubscribersMalfunctionDevice')
      },
      {
        path: 'ComplaintProcess',
        name: 'ComplaintProcess',
        meta: {
          title: 'Tiến trình KN'
        },
        component: () => import('./subscriber/ComplaintProcess')
      },
      {
        path: 'Competitors',
        name: 'Competitors',
        meta: {
          title: 'Đối thủ cạnh tranh'
        },
        component: () => import('./subscriber/Competitors')
      },
      // {
      //   path: 'SearchPackagesPromotions',
      //   name: 'SearchPackagesPromotions',
      //   component: () => import('./subscriber/SearchPackagesPromotions/index.js'),
      //   meta: {
      //     title: ' Tra cứu thông tin gói cước, khuyến mại'
      //   }
      // },
      // {
      //   path: 'SearchEsim',
      //   name: 'SearchEsim',
      //   component: () => import('./subscriber/SearchEsim/index.js'),
      //   meta: {
      //     title: ' Tra cứu esim'
      //   }
      // },
      // {
      //   path: 'SearchHighcostUploadData',
      //   name: 'SearchHighcostUploadData',
      //   component: () => import('./subscriber/SearchHighcostUploadData/index.js'),
      //   meta: {
      //     title: ' Tra cứu dữ liệu upload cước cao'
      //   }
      // },
      {
        path: 'SearchSMS',
        name: 'SearchSMS',
        component: () => import('./subscriber/SearchSMS/index.js'),
        meta: {
          title: ' Tra cứu SMS'
        }
      },
      {
        path: 'MoveDetails',
        name: 'MoveDetails',
        meta: {
          title: '[UR2.8.010] - Chi tiết dịch chuyển'
        },
        component: () => import('./subscriber/MoveDetails/index.js')
      },
      {
        path: 'SearchContract',
        name: 'SearchContract',
        meta: {
          title: '[UR2.8.001] - Tra cứu hợp đồng'
        },
        component: () => import('./subscriber/SearchContract/index.vue')
      },
      {
        path: 'CompleteEquipmentSale',
        name: 'CompleteEquipmentSale',
        component: () => import('./subscriber/CompleteEquipmentSale/index.js'),
        meta: {
          title: 'Hoàn công bán thiết bị'
        }
      },
      {
        path: 'TransferTollCollection',
        name: 'TransferTollCollection',
        meta: {
          title: '[BSS-38467] - Chuyển tuyến thu'
        },
        component: () => import('./subscriber/TransferTollCollection')
      },
      {
        path: 'SearchGeneral',
        name: 'SearchGeneral',
        meta: {
          title: '[UR2.8.013] - Tra cứu tổng hợp'
        },
        component: () => import('./subscriber/SearchGeneral/index.js')
      },
      {
        // Đổi tên do trùng với module khác
        path: 'SearchGeneral_CCBS',
        name: 'SearchGeneral_CCBS',
        component: () => import('./subscriber/SearchGeneral_CCBS/index.js'),
        meta: {
          title: '[BSS-38519] - Tra cứu tổng hợp'
        }
      },
      {
        path: 'SearchSubcriberInfor',
        name: 'SearchSubcriberInfor',
        meta: {
          title: '[BSS38586] - Tra cứu thông tin thuê bao'
        },
        component: () => import('./ccbs/SearchSubcriberInfor/SearchSubcriberInfor.vue')
      },
      {
        path: 'SearchRegularPostpaidPackages',
        name: 'SearchRegularPostpaidPackages',
        component: () => import('./subscriber/SearchRegularPostpaidPackages/index.js'),
        meta: {
          title: '[BSS-38739] - Tra cứu gói cước thuê bao trả sau thường'
        }
      },
      {
        path: 'FreeEquipmentTBDC',
        name: 'FreeEquipmentTBDC',
        component: () => import('./subscriber/FreeEquipmentTBDC/index.js'),
        meta: {
          title: 'Trang bị miễn phí TBDC'
        }
      },
      {
        path: 'UpdateSplitPointsByList',
        name: 'UpdateSplitPointsByList',
        meta: {
          title: 'BSS-38775 - Cập nhật điểm chia theo danh sách'
        },
        component: () => import('./subscriber/UpdateSplitPointsByList/UpdateSplitPointsByList.vue')
      },
      {
        path: 'SearchMegawanConnectionInfor',
        name: 'SearchMegawanConnectionInfor',
        meta: {
          title: 'BSS-38514 - Tra cứu thông tin kết nối Megawan'
        },
        component: () => import('./subscriber/SearchMegawanConnectionInfor/SearchMegawanConnectionInfor.vue')
      },
      {
        path: 'InstallTicketStatistics',
        name: 'InstallTicketStatistics',
        meta: {
          title: '[UR2.3.007] - Thống kê phiếu thi công'
        },
        component: () => import('./subscriber/SearchIncomeTicket/index.js')
      },
      {
        path: 'PayBillByFile',
        name: 'PayBillByFile',
        component: PayBillByFile,
        meta: {
          title: 'Thanh toán hóa đơn theo file'
        }
      },
      {
        path: 'SearchHistoryLockUnlockPayTV',
        name: 'SearchHistoryLockUnlockPayTV',
        component: SearchHistoryLockUnlockPayTV,
        meta: {
          title: 'Tra cứu lịch sử khóa mở Pay TV'
        }
      },
      {
        path: 'SearchPromotionChange',
        name: 'SearchPromotionChange',
        component: SearchPromotionChange,
        meta: {
          title: 'Tra cứu biến động khuyến mại'
        }
      },
      {
        path: 'SearchSubcriberByNoAppMyVNPT',
        name: 'SearchSubcriberByNoAppMyVNPT',
        component: SearchSubcriberByNoAppMyVNPT,
        meta: {
          title: 'Tra cứu thuê bao theo số liên hệ App MyVNPT'
        }
      },
      {
        path: 'DetailChangeType',
        name: 'DetailChangeType',
        component: ChangeSubsType,
        meta: {
          title: '[UR2.8.011] - Chi tiết chuyển đổi loại hình'
        }
      },
      {
        path: 'SearchContactsCrossSellContracts',
        name: 'SearchContactsCrossSellContracts',
        meta: {
          title: '[BSS-38462] - Tra cứu Danh bạ, hợp đồng bán chéo'
        },
        component: () => import('./subscriber/SearchContactsCrossSellContracts')
      },
      // {
      //   path: 'SearchCrossSellAggregate',
      //   name: 'SearchCrossSellAggregate',
      //   meta:{
      //     title: '[BSS-38461] - Tra cứu tổng hợp bán chéo'
      //   },
      //   component: () => import('./subscriber/SearchCrossSellAggregate')
      // },
      {
        path: 'IPRentalManagement',
        name: 'IPRentalManagement',
        meta: {
          title: '[UR2.8.014] - Tra cứu phát triển'
        },
        component: () => import('./subscriber/IPRentalManagement')
      },
      {
        path: 'ChangeIMSInfor',
        name: 'ChangeIMSInfor',
        component: () => import('./subscriber/ChangeIMSInfor/index.vue'),
        meta: {
          title: 'BSS-38511 - Thay đổi thông số IMS'
        }
      },
      {
        path: 'SearchAreaByPlaceName',
        name: 'SearchAreaByPlaceName',
        component: () => import('./subscriber/SearchAreaByPlaceName/index.js'),
        meta: {
          title: 'BSS-38504 03. Code FE WEB - Tra cứu khu vực theo địa danh'
        }
      },
      {
        path: 'SearchHistoryPayrollManagementStaff',
        name: 'SearchHistoryPayrollManagementStaff',
        component: SearchHistoryPayrollManagementStaff,
        meta: {
          title: '- Tra cứu lịch sử điều chỉnh nhân viên quản lý tính lương KHDN'
        }
      },
      {
        path: 'SearchVNEduData',
        name: 'SearchVNEduData',
        component: () => import('./subscriber/SearchVNEduData/index.js'),
        meta: {
          title: '[BSS-38526] - Tra cứu dữ liệu VNEdu'
        }
      },
      {
        path: 'SynchronizeElectronicInvoices',
        name: 'SynchronizeElectronicInvoices',
        component: () => import('./subscriber/SynchronizeElectronicInvoices/index.js'),
        meta: {
          title: 'Đồng bộ hóa đơn điện tử'
        }
      },
      {
        path: 'CompleteEquipmentPurchase',
        name: 'CompleteEquipmentPurchase',
        component: () => import('./subscriber/CompleteEquipmentPurchase/index.js'),
        meta: {
          title: 'Hoàn công mua thiết bị'
        }
      },
      {
        path: 'SaleDevice_TTVT',
        name: 'SaleDevice_TTVT',
        meta: {
          title: 'BSS-38484 Bán thiết bị-TTVT'
        },
        component: () => import('./subscriber/SaleDevice_TTVT')
      },
      {
        path: 'SaleDevice_TTVT/Payment',
        name: 'Payment',
        meta: {
          title: 'BSS-38484 Bán thiết bị-TTVT'
        },
        component: () => import('../contract/setup/Payment')
      },
      {
        path: 'ReadFileContractTransfer',
        name: 'ReadFileContractTransfer',
        meta: {
          title: 'BSS-38485 Đọc file chuyển HĐ chính thức'
        },
        component: () => import('./subscriber/ReadFileContractTransfer')
      },
      {
        path: 'SearchDebtByMonth',
        name: 'SearchDebtByMonth',
        component: () => import('./ccbs/SearchDebtByMonth/SearchDebtByMonth.vue'),
        meta: {
          title: 'Tra cứu nợ theo tháng'
        }
      },
      {
        path: 'SearchGphoneInfor',
        name: 'SearchGphoneInfor',
        component: () => import('./ccbs/SearchGphoneInfor/SearchGphoneInfor.vue'),
        meta: {
          title: 'Tra cứu thông tin Gphone'
        }
      },
      {
        path: 'WaitingListPausedLiquidated',
        name: 'WaitingListPausedLiquidated',
        meta: {
          title: 'BSS-38482 Danh sách chờ thanh lý'
        },
        component: () => import('./subscriber/WaitingListPausedLiquidated/WaitingListPausedLiquidated.vue')
      },
      {
        path: 'SetTimeEditSurvey',
        name: 'SetTimeEditSurvey',
        meta: {
          title: 'BSS-38482 Thời gian chờ sửa phiếu khảo sát'
        },
        component: () => import('./subscriber/SetTimeEditSurvey/index.vue')
      },
      {
        path: 'UpdateADSLPort',
        name: 'UpdateADSLPort',
        meta: {
          title: '[BSS-38463] - Cập nhật adsl port'
        },
        component: () => import('./subscriber/UpdateADSLPort')
      },
      {
        path: 'PublicUtilityTelecomSubscriber',
        name: 'PublicUtilityTelecomSubscriber',
        meta: {
          title: 'Thuê bao viễn thông công ích'
        },
        component: () => import('./subscriber/PublicUtilityTelecomSubscriber/index.js')
      },
      {
        path: 'UpdateUserPasswordMYTV',
        name: 'UpdateUserPasswordMYTV',
        meta: {
          title: 'Cập nhật mật khẩu user'
        },
        component: () => import('./subscriber/UpdateUserPasswordMYTV/index.js')
      },
      {
        path: 'UpdateInvoice0VND',
        name: 'UpdateInvoice0VND',
        meta: {
          title: 'BSS-38501 Cập nhật hóa đơn 0 đồng'
        },
        component: () => import('./subscriber/UpdateInvoice0VND')
      },
      {
        path: 'UploadListProfileFiles',
        name: 'UploadListProfileFiles',
        meta: {
          title: 'BSS-38868 Upload danh sách file hồ sơ'
        },
        component: () => import('./subscriber/UploadListProfileFiles')
      },
      {
        path: 'SearchHotChargeAlert',
        name: 'SearchHotChargeAlert',
        meta: {
          title: '[UR....] - Tra cứu cảnh báo cước nóng'
        },
        component: () => import('./subscriber/SearchHotChargeAlert/index.vue')
      },
      {
        path: 'SearchFiveNumbers',
        name: 'SearchFiveNumbers',
        meta: {
          title: '[UR....] - Tra cứu 5 số'
        },
        component: () => import('./subscriber/SearchFiveNumbers/index.vue')
      },
      {
        path: 'SearchCallHistoryGphone',
        name: 'SearchCallHistoryGphone',
        component: () => import('./subscriber/SearchCallHistoryGphone/index.js'),
        meta: {
          title: '[BSS-38528] - Tra cứu lịch sử gọi GPhone'
        }
      },
      {
        path: 'SearchHistorySubcriberInforVNP',
        name: 'SearchHistorySubcriberInforVNP',
        component: () => import('./subscriber/SearchHistorySubcriberInforVNP/index.js'),
        meta: {
          title: 'BSS-38527 - Tra cứu lịch sử thông tin thuê bao VNP'
        }
      },
      {
        path: 'SyncSubscribersToSLASystem',
        name: 'SyncSubscribersToSLASystem',
        meta: {
          title: 'BSS-38493 - Đồng bộ thuê bao lên hệ thống SLA KHTCDN'
        },
        component: () => import('./subscriber/SyncSubscribersToSLASystem/SyncSubscribersToSLASystem.vue')
      },
      {
        path: 'UpdatePaymentContactByFile',
        name: 'UpdatePaymentContactByFile',
        component: () => import('./subscriber/UpdatePaymentContactByFile/index.vue'),
        meta: {
          title: 'BSS-38512 - Cập nhật liên hệ thanh toán theo File'
        }
      },
      {
        path: 'CreateTemplateBNM',
        name: 'CreateTemplateBNM',
        meta: {
          title: 'Khai báo template BNM'
        },
        component: () => import('./subscriber/CreateTemplateBNM')
      },
      {
        path: 'CompleteTransferRightsBC',
        name: 'CompleteTransferRightsBC',
        meta: {
          title: 'Hoàn thiện hồ sơ chuyển quyền BC'
        },
        component: () => import('./subscriber/CompleteTransferRightsBC')
      },
      {
        path: 'SearchWarrantyEquipment',
        name: 'SearchWarrantyEquipment',
        component: () => import('./subscriber/SearchWarrantyEquipment/index.js'),
        meta: {
          title: 'BSS-38523 - Tra cứu thiết bị bảo hành'
        }
      },
      {
        path: 'UpdateResultCoordinationTicket',
        name: 'UpdateResultCoordinationTicket',
        meta: {
          title: 'Cập nhật kết quả xử lý phiếu phối hợp'
        },
        component: () => import('./subscriber/UpdateResultCoordinationTicket')
      },
      // {
      //   path: 'SearchPackagesPromotions',
      //   name: 'SearchPackagesPromotions',
      //   meta: {
      //     title: '[UR....] - Tra cứu gói cước MNP'
      //   },
      //   component: () => import('./subscriber/SearchPackagesPromotions/index.vue')
      // },
      {
        path: 'ModifyContractMoney',
        name: 'ModifyContractMoney',
        component: () => import('./subscriber/ModifyContractMoney/index.js'),
        meta: {
          title: '[UR1.1.025] - Sửa tiền hợp đồng'
        }
      },
      {
        path: 'ImportSubscriberCareWorkerFile',
        name: 'ImportSubscriberCareWorkerFile',
        component: () => import('./subscriber/ImportSubscriberCareWorkerFile/index.js'),
        meta: {
          title: 'Import file nhân viên chăm sóc thuê bao'
        }
      },
      {
        path: 'UpdateIntermediateSplitPoint',
        name: 'UpdateIntermediateSplitPoint',
        meta: {
          title: 'Cập nhật điểm chia trung gian'
        },
        component: () => import('./subscriber/UpdateIntermediateSplitPoint/index.js')
      },
      {
        path: 'CheckIT',
        name: 'CheckIT',
        component: CheckIT,
        meta: {
          title: 'Đối soát IT'
        }
      },
      {
        path: 'SearchVNPPayment',
        name: 'SearchVNPPayment',
        component: () => import('./ccbs/SearchVNPPayment/SearchVNPPayment.vue'),
        meta: {
          title: 'Tra cứu phiếu trả Vinaphone'
        }
      },
      {
        path: 'ModifyContractMoney',
        name: 'ModifyContractMoney',
        meta: {
          title: 'BSS-38490 - Sửa tiền hợp đồng'
        },
        component: () => import('./subscriber/ModifyContractMoney/ModifyContractMoney.vue')
      },
      {
        path: 'SearchCustomersSameNumber',
        name: 'SearchCustomersSameNumber',
        component: () => import('./subscriber/SearchCustomersSameNumber/index.js'),
        meta: {
          title: ' Tra cứu khách hàng trùng giấy tờ'
        }
      },
      //Bỏ theo comment của a Hiếu
      // {
      //   path: 'UpdateCollectionDate',
      //   name: 'UpdateCollectionDate',
      //   component: () => import('./subscriber/UpdateCollectionDate/index.js'),
      //   meta: {
      //     title: '[BSS-38518] - Cập nhật ngày truy thu'
      //   }
      // },
      {
        path: 'SearchRenovationMaintenanceTicket',
        name: 'SearchRenovationMaintenanceTicket',
        meta: {
          title: 'Tra cứu phiếu cải tạo, bảo dưỡng'
        },
        component: () => import('./subscriber/SearchRenovationMaintenanceTicket')
      },
      {
        path: 'MeasureSubscriberPortParaByFile',
        name: 'MeasureSubscriberPortParaByFile',
        meta: {
          title: 'Đo thông số cổng thuê bao theo file'
        },
        component: () => import('./subscriber/MeasureSubscriberPortParaByFile/index.js')
      },
      {
        path: 'ChangeMegawanInfor',
        name: 'ChangeMegawanInfor',
        component: ChangeMegawanInfor,
        meta: {
          title: 'Thay đổi thông số kỹ thuật Megawan'
        }
      },
      {
        path: 'SearchAccountSLAKHDN',
        name: 'SearchAccountSLAKHDN',
        component: () => import('./subscriber/SearchAccountSLAKHDN/index.js'),
        meta: {
          title: 'BSS-38522 Tra cứu Account SLA KHDN'
        }
      },
      {
        path: 'ConvertFixedToCrossSellingIMS',
        name: 'ConvertFixedToCrossSellingIMS',
        meta: {
          title: '[BSS-38460] - Chuyển đổi từ cố định sang IMS bán chéo'
        },
        component: () => import('./subscriber/ConvertFixedToCrossSellingIMS')
      },
      {
        path: 'SearchCrossSellAggregate',
        name: 'SearchCrossSellAggregate',
        meta: {
          title: '[BSS-38461] - Tra cứu tổng hợp bán chéo'
        },
        component: () => import('./subscriber/SearchCrossSellAggregate')
      },
      {
        path: 'frmBNMTools',
        name: 'frmBNMTools',
        component: () => import('./subscriber/frmBNMTools/index'),
        meta: {
          title: 'frmBNMTools'
        }
      },
      {
        path: 'SearchMaintenanceSlipNotDone',
        name: 'SearchMaintenanceSlipNotDone',
        meta: {
          title: '[BSS-38520] Tra cứu phiếu bảo dưỡng chưa thực hiện'
        },
        component: () => import('./subscriber/SearchMaintenanceSlipNotDone/index.js')
      },
      {
        path: 'SearchDepositCustomersChooseNumber',
        name: 'SearchDepositCustomersChooseNumber',
        component: () => import('./subscriber/SearchDepositCustomersChooseNumber/index.js'),
        meta: {
          title: '[BSS-38543] -Tra cứu khách hàng đặt cọc chọn số/đặt cọc iPhone'
        }
      },
      {
        path: 'SearchPersonalBusinessInfor',
        name: 'SearchPersonalBusinessInfor',
        component: () => import('./subscriber/SearchPersonalBusinessInfor/index.js'),
        meta: {
          title: '[BSS-38544] -Tra cứu cập nhật thông tin cá nhân,doanh nghiệp'
        }
      },
      {
        path: 'SearchRegisterKMCBDATAPackage',
        name: 'SearchRegisterKMCBDATAPackage',
        component: () => import('./subscriber/SearchRegisterKMCBDATAPackage/index.js'),
        meta: {
          title: '[BSS-38546] -Tra cứu, đăng ký gói cước KMCB, DATA'
        }
      },
      {
        path: 'SearchLineCollection',
        name: 'SearchLineCollection',
        meta: {
          title: '[UR1.1.013] - Tra cứu Tuyến thu'
        },
        component: () => import('../admin/category/DebtLine/index.vue')
      },
      {
        path: 'SearchDigitizedDataCollection',
        name: 'SearchDigitizedDataCollection',
        meta: {
          title: '[BSS-72820] - Đối soát dữ liệu số hóa thu cước'
        },
        component: () => import('./subscriber/SearchDigitizedDataCollection')
      },
      {
        path: 'todolist',
        name: 'todolist',
        meta: {
          title: '[BSS-72820] - test chuc nang'
        },
        component: () => import('./subscriber/todolist')
      },
      {
        path: 'CheckData',
        name: 'CheckData',
        component: () =>
          import('./subscriber/CheckData/index.js'),
        meta: {
          title: 'Đối soát dữ liệu thuê bao'
        }
      },
      {
        path: 'SearchLogBill',
        name: 'SearchLogBill',
        component: () => import('./subscriber/SearchLogBill/index.js'),
        meta: {
          title: 'Tra cứu log cập nhật thông tin thông báo cước'
        }
      },
      {
        path: 'SearchDigitizationBill',
        name: 'SearchDigitizationBill',
        component: () => import('./subscriber/SearchDigitizationBill/index'),
        meta: {
          title: 'Tra cứu đối soát số hóa thu cước'
        }
      },
      {
        path: 'AssignChargeNoticeByFile',
        name: 'AssignChargeNoticeByFile',
        meta: {
          title: 'Thông báo cước theo file'
        },
        component: () => import('./subscriber/AssignChargeNoticeByFile')
      },
      {
        path: 'SearchIncidentAndChanges',
        name: 'SearchIncidentAndChanges',
        meta: {
          title: 'Tìm kiếm sự cố và điều chỉnh'
        },
        component: () => import('./subscriber/SearchIncidentAndChanges')
      },
      {
        path: "AccessPlanInformationLookup",
        name: "AccessPlanInformationLookup",
        meta: {
          title: "Tra cứu thông tin kế hoạch tiếp cận"
        },
        component: () => import("./khdn/AccessPlanInformationLookup/index.js")
      },
      {
        path: "SearchMobileSolutions",
        name: "SearchMobileSolutions",
        meta: {
          title: "Tra cứu thông tin Mobile Solution"
        },
        component: SearchMobileSolutions,
        children: [
          { path: "/", redirect: '/search/SearchMobileSolutions/khach_hang' },
          { path: "khach_hang", component: DanhSachKhachhang },
          { path: "khach_hang/:vthuebao_id", props: true, component: ChiTietKhachHang },
          { path: "lich_su_tac_dong", component: LichSuTacDong },
        ]
      },
      // {
      //   path: 'CustomerEngagementPlan',
      //   name: 'CustomerEngagementPlan',
      //   meta: {
      //   title: 'Kế hoạch tiếp cận khách hàng'
      //   },
      //   component: () => import('./khdn/CustomerEngagementPlan')
      // },
      {
        path: 'ThongTinQuyHoachMucCuoc',
        name: 'ThongTinQuyHoachMucCuoc',
        meta: {
          title: 'Tư vấn thay đổi tốc độ, gói cước đa dịch vụ, gia hạn trả trước'
        },
        component: () => import('./subscriber/ThongTinQuyHoachMucCuoc/index.vue')
      },
      {
        path: 'SearchCustomers',
        name: 'SearchCustomers',
        component: () => import('./subscriber/SearchCustomers/SearchCustomers.vue'),
        meta: {
          title: 'Tra cứu khách hàng'
        }
      },
      {
        path: 'SearchMeshDevice',
        name: 'SearchMeshDevice',
        meta: {
        title: 'Quản lý thiết bị Mesh'
        },
        component: () => import('./subscriber/SearchMeshDevice/index.vue')
      },
      {
        path: 'LockAndUnlockMeshDevice',
        name: 'LockAndUnlockMeshDevice',
        meta: {
        title: 'Quản lý thiết bị Mesh (Khoá/Mở)'
        },
        component: () => import('./subscriber/SearchMeshDevice/index.vue')
      },
      {
        path: 'SearchSMSs',
        name: 'SearchSMSs',
        component: () => import('./subscriber/SearchSMSs/SearchSMSs.vue'),
        meta: {
            title: 'Tra cứu SMS'
        }
      },
      {
        path: 'TraCuuHDDT',
        name: 'TraCuuHDDT',
        component: () => import('./subscriber/SearchDVCNTT/HeThong_HoaDonDienTu/TraCuuHDDT.vue'),
        meta: {
            title: 'Tra cứu thông tin từ hệ thống hóa đơn điện tử'
        }
      },
      {
        path: 'TraCuuLichSuThueBaoGHTT',
        name: 'TraCuuLichSuThueBaoGHTT',
        meta: {
          title: 'Tra cứu lịch sử thuê bap OB GHTT'
        },
        component: () => import('./subscriber/TraCuuLichSuThueBaoGHTT/index.js')
      },
      {
        path: 'TraCuuDanhSachQuaTang',
        name: 'TraCuuDanhSachQuaTang',
        meta: {
          title: '[UR2.8.014] - Tra cứu danh sách quà tặng'
        },
        component: () => import('./subscriber/TraCuuDSQuaTang/index.js')
      }
    ]
  }
]
