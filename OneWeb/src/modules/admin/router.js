import UpdateCancelDemoReason from './category/UpdateCancelDemoReason'
import ManagementUnit from './category/ManagementUnit'
import InvoiceSerial from './category/InvoiceSerial'
import StaffStock from './category/StaffStock'
import OverdueDebtPercentage from './category/OverdueDebtPercentage'
import Units from './category/Units'
import Purpose from './category/Purpose'
import AssignSuppliesToTitleDepartment from './category/AssignSuppliesToTitleDepartment'
import UpdateAttributeType from './category/UpdateAttributeType'
import AssignSuppliseAttribute from './category/AssignSuppliseAttribute'
import MailNoticeUnitPrice from './category/MailNoticeUnitPrice'
import Project from './category/Project'
import ProjectList from './category/ProjectList'
import ConfirmRenewPrepaid from './subscriber/ConfirmRenewPrepaid/index.vue'
import UpdateUnitPrice3883 from './UpdateUnitPrice3883'
import ListBuilding from './category/ListBuilding'
import StaffMapCountry from './category/StaffMapCountry'
import LayoutMain from '@/layouts/LayoutMain'
import PrefixOfCounty from './category/PrefixOfCounty'
import DepartmentArea from './category/DepartmentArea'
import GrantUserPermission from './category/GrantUserPermission'
import AssignAttTypeToAttribute from './category/AssignAttTypeToAttribute'
import AssignTechStaffLocation from './category/AssignTechStaffLocation'
import MapSetupTypeToSubsType from './category/MapSetupTypeToSubsType'
import UpdateField from './category/UpdateField'
import AssignAMStaffToPaymentByFile from './category/AssignAMStaffToPaymentByFile'
import LocationSpec from './category/LocationSpec'
import IntersectionDirection from './category/IntersectionDirection'
import UpdateCustomerLocation from './category/UpdateCustomerLocation'
import ChannelRentalPriceList from './category/ChannelRentalPriceList'
import CreateDebtTable from './category/CreateDebtTable'
import DebtTableSQLForInvoiceCycle from './category/DebtTableSQLForInvoiceCycle'
import ChargeNoticeParameter from './category/ChargeNoticeParameter'
import CorrectOwner from './subscriber/CorrectOwner'
import PermissionGroupUser from './category/PermissionGroupUser'
import StaffLocation from './category/StaffLocation'
import UpdateAttributeValues from './category/UpdateAttributeValues'
import VASInfo from './category/VASInfo'
import SubscriberChargePrice from './subscriber/SubscriberChargePrice'
import AssignAttributeTypeToSuppliesType from './category/AssignAttributeTypeToSuppliesType'
import Attribute from './category/Attribute'
import ManageReceiptStore from './category/ManageReceiptStore'
import ManagePermission from './category/ManagePermission'
import Supplies from './category/Supplies'
import AssignCollectDebtStaff from './category/AssignCollectDebtStaff'
import MultiServicePackage from './category/MultiServicePackage'
import Users from './category/Users'
import DefaultParameter from './category/DefaultParameter'
import Prefix from './category/Prefix'
import AssignStaffLocation from './category/AssignStaffLocation'
import AssignSuppliesToSubsType from './category/AssignSuppliesToSubsType'
import UpdateInstallGroupByFile from './subscriber/UpdateInstallGroupByFile'
import Menu from './category/Menu'
import UpdateAnswers from './category/UpdateAnswers'
import UpdateQuestions from './category/UpdateQuestions'
import AssignLocationEmployee from './category/AssignLocationEmployee'
import UpdateEmployeePosition from './category/UpdateEmployeePosition'
import UpdateItem from './category/UpdateItem'
import CancelReason from './category/CancelReason'
import FreightRateTSL from './category/FreightRateTSL'
import AssignLocationEmployeeHPG from './category/AssignLocationEmployeeHPG'
import PartnerList from './category/PartnerList'
import DebtCycle from './category/DebtCycle'
import RemunerateDistributeInvoice from './category/RemunerateDistributeInvoice'
import EmployeeAssignmentWard from './category/EmployeeAssignmentWard'
import ChangeFreeIPInfo from './subscriber/ChangeFreeIPInfo'
import ContractorRole from './category/ContractorRole'
import ChangeNEParameter from './subscriber/ChangeNEParameter'
import AssignUnitToCustomerType from './category/AssignUnitToCustomerType'
import ProgramOperation from './category/ProgramOperation'
import SetStaffToReceiveMessages from './category/SetStaffToReceiveMessages'
import { ThongBaoCuocTheoFile, TraCuuThongTinNhanThongBaoCuoc, TraCuuLogCapNhatThongTinTBC, TraCuuDoiSoatSoHoaThuCuoc, ThietLapGuiTBC, ThueBaoKhongNhanThongBaoNhacNoCuoc } from './category/QuanLyDaiLy'
import DoiSoatDuLieuIT from './category/DoiSoatDuLieuIT'
export default [
  {
    path: '/admin',
    name: 'admin',
    component: LayoutMain,
    redirect: '/admin/home',
    children: [
      {
        path: 'home',
        name: 'ADMIN home page',
        component: () => import('./Home'),
        meta: {
          title: 'Trang chủ'
        }
      },
      {
        path: 'UpdateProductMO',
        name: 'UpdateProductMO',
        component: () => import('./category/UpdateProductMO'),
        meta: {
          title: '[UR1.1.033] - Cập nhật sản phẩm quay vòng MO'
        }
      },
      {
        path: 'UpdatePriorityMultiServicePackage',
        name: 'UpdatePriorityMultiServicePackage',
        component: () => import('./category/UpdatePriorityMultiServicePackage'),
        meta: {
          title: '[UR1.1.026] - Cập nhật danh sách ưu tiên đăng ký gói đa dịch vụ'
        }
      },
      {
        path: 'TransferIncurredDebt',
        name: 'TransferIncurredDebt',
        component: () => import('./debt/TransferIncurredDebt'),
        meta: {
          title: '[UR1.1.042] - Chuyển nợ phát sinh'
        }
      },
      {
        path: 'ChangeSubsManageArea',
        name: 'ChangeSubsManageArea',
        component: () => import('./subscriber/ChangeSubsManageArea/index.js'),
        meta: {
          title: 'UR1.5.024- Thay đổi khu vực quản lý thuê bao '
        }
      },
      {
        path: 'ChangeCableInfo',
        name: 'ChangeCableInfo',
        component: () => import('./subscriber/ChangeCableInfo/index.js'),
        meta: {
          title: 'UR1.5.006- Thay đổi thông tin cáp '
        }
      },
      {
        path: 'StaffArea',
        name: 'StaffArea',
        component: () => import('./category/StaffArea/index.js'),
        meta: {
          title: 'UR1.2.020 - Gán nhân viên khu vực '
        }
      },
      {
        path: 'UpdateCancelDemoReason',
        name: 'UpdateCancelDemoReason',
        component: UpdateCancelDemoReason,
        meta: { title: '[UR1.5.036] - Cập nhật lý do hủy dùng thử' }
      },
      {
        path: 'ManagementUnit',
        name: 'ManagementUnit',
        component: ManagementUnit,
        meta: { title: '[UR1.1.015] - Đơn vị quản lý' }
      },
      {
        path: 'Permission',
        name: 'Permission',
        component: () => import('./category/Permission'),
        meta: {
          title: '[UR1.2.026] - Quản lý quyền'
        }
      },
      {
        path: 'ClassifyService',
        name: 'classifyService',
        meta: {
          title: '[UR1.3.015] - Phân loại dịch vụ'
        },
        component: () => import('./category/ClassifyService/index.js')
      },
      {
        path: 'StaffCareer',
        name: 'StaffCareer',
        meta: {
          title: '[UR1.2.027] - Gán nhân viên với ngành nghề'
        },
        component: () => import('./category/StaffCareer')
      },
      {
        path: 'AssignUnitToCustomerType',
        name: 'mapUnitAndTypeOfCustomer',
        meta: {
          title: '[UR1.2.024] - Gán đơn vị - loại khách hàng',
          dialog: true
        },
        component: AssignUnitToCustomerType
      },
      {
        path: 'InvoiceType',
        name: 'InvoiceType',
        meta: {
          title: '[UR1.1.027] - Loại hóa đơn'
        },
        component: () => import('./category/InvoiceType/index.js')
      },
      {
        path: 'UpdateField',
        name: 'updatefield',
        component: UpdateField,
        meta: {
          title: '[UR1.3.014] - Cập nhật lĩnh vực',
          dialog: true,
          dialogfull: true
        }
      },
      {
        path: 'MapPaymentAccountToSubsType',
        name: 'MapPaymentAccountToSubsType',
        component: () => import('./category/MapPaymentAccountToSubsType'),
        meta: {
          title: '[UR1.1.038] - Khoản mục TT cho loại thuê bao'
        }
      },
      {
        path: 'PrefixOfCounty',
        name: 'PrefixOfCounty',
        component: PrefixOfCounty,
        meta: {
          title: '[UR2.8.015] - Tra cứu tài khoản dịch vụ'
        }
      },
      {
        path: 'DepartmentArea',
        name: 'DepartmentArea',
        component: DepartmentArea,
        meta: {
          title: '[UR1.1.016] - Đơn vị - Địa danh (T2)'
        }
      },
      {
        path: 'GrantUserPermission',
        name: 'GrantUserPermission',
        component: GrantUserPermission,
        meta: {
          title: '[UR1.2.016] - Phân quyền NND'
        }
      },
      {
        path: 'AssignTechStaffLocation',
        name: 'AssignTechStaffLocation',
        component: AssignTechStaffLocation,
        meta: {
          title: '[UR2.13.008] - Gán địa bàn - NVKT'
        }
      },
      {
        path: 'MapSetupTypeToSubsType',
        name: 'MapSetupTypeToSubsType',
        component: MapSetupTypeToSubsType,
        meta: {
          title: '[UR1.4.004] - Kiểu lắp đặt - loại hình',
          dialog: true
        }
      },
      {
        path: 'AssignAttTypeToAttribute',
        name: 'AssignAttTypeToAttribute',
        component: AssignAttTypeToAttribute,
        meta: {
          title: '[UR1.1.087] - Gán kiểu thuộc tính vào thuộc tính',
          dialog: true
        }
      },
      {
        path: 'AssignCustomerListForB2A',
        name: 'AssignCustomerListForB2A',
        component: () => import('./subscriber/AssignCustomerListForB2A'),
        meta: {
          title: '[UR1.5.046] - Phân tập khách hàng B2A'
        }
      },
      {
        path: 'Promotion',
        name: 'promotion',
        meta: {
          title: '[UR1.3.003] - Khuyến mại'
        },
        component: () => import('./category/Promotion/index.js')
      },
      {
        path: 'Terminals',
        name: 'Terminals',
        meta: {
          title: '[UR1.1.008] - Thiết bị đầu cuối'
        },
        component: () => import('./category/Terminals/index.js')
      },
      {
        path: 'DeviceRecallCommission',
        name: 'deviceRecallCommission',
        meta: {
          title: '[UR1.1.053] - Thù lao thu hồi thiết bị'
        },
        component: () => import('./category/DeviceRecallCommission/index.js')
      },
      {
        path: 'CommissionRate',
        name: 'commissionRate',
        meta: {
          title: ' [UR1.1.047] - Tỷ lệ thù lao được hưởng'
        },
        component: () => import('./category/CommissionRate/index.js')
      },
      {
        path: 'AssignAMStaffToPaymentByFile',
        name: 'AssignAMStaffToPaymentByFile',
        component: AssignAMStaffToPaymentByFile,
        meta: {
          title: '[UR1.5.026] - Gán nhân viên AM quản lý thanh toán theo file'
        }
      },
      {
        path: 'LocationSpec',
        name: 'LocationSpec',
        component: LocationSpec,
        meta: {
          title: '[UR1.1.004] - Đặc điểm'
        }
      },
      {
        path: 'IntersectionDirection',
        name: 'IntersectionDirection',
        component: IntersectionDirection,
        meta: {
          title: '[UR1.4.002] - Hướng giao'
        }
      },
      {
        path: 'UpdateCustomerLocation',
        name: 'UpdateCustomerLocation',
        component: UpdateCustomerLocation,
        meta: {
          title: '[UR1.5.035] - Cập nhật địa chỉ, dịch vụ hộ dân'
        }
      },
      {
        path: 'ChannelRentalPriceList',
        name: 'ChannelRentalPriceList',
        component: ChannelRentalPriceList,
        meta: {
          title: '[UR1.3.013] - Bảng giá cước thuê kênh, thuê cổng'
        }
      },
      {
        path: 'CreateDebtTable',
        name: 'CreateDebtTable',
        component: CreateDebtTable,
        meta: {
          title: '[UR1.1.040] - Tạo bảng nợ'
        }
      },
      {
        path: 'DebtTableSQLForInvoiceCycle',
        name: 'DebtTableSQLForInvoiceCycle',
        component: DebtTableSQLForInvoiceCycle,
        meta: {
          title: '[UR1.1.037] - Câu lệnh tạo bảng nợ cho kỳ hóa đơn'
        }
      },
      {
        path: 'ChargeNoticeParameter',
        name: 'ChargeNoticeParameter',
        component: ChargeNoticeParameter,
        meta: {
          title: '[UR1.2.006] - Tham số thông báo cước',
          dialog: true
        }
      },
      {
        path: 'CorrectOwner',
        name: 'CorrectOwner',
        component: CorrectOwner,
        meta: {
          title: '[UR1.5.045] - Chuẩn hóa chủ quản',
          dialog: true
        }
      },
      {
        path: 'PermissionGroupUser',
        name: 'PermissionGroupUser',
        component: PermissionGroupUser,
        meta: {
          title: '[UR1.2.015] - Phân quyền ND'
        }
      },
      {
        path: 'Physicalequpment',
        name: 'ThongTinThietBiIDC',
        component: ThongTinThietBiIDC,
        meta: {
          title: 'Thông tin thiết bị IDC'
        }
      },
      {
        path: 'StaffLocation',
        name: 'StaffLocation',
        component: StaffLocation,
        meta: {
          title: '[UR2.13.007] - Gán địa bàn - NVKD'
        }
      },
      {
        path: 'UpdateAttributeValues',
        name: 'UpdateAttributeValues',
        component: UpdateAttributeValues,
        meta: {
          title: '[UR1.1.089] - Cập nhập giá trị thuộc tính',
          dialog: true
        }
      },
      {
        path: 'VASInfo',
        name: 'VASInfo',
        component: VASInfo,
        meta: {
          title: '[UR1.3.005] - Dịch vụ gia tăng'
        }
      },
      {
        path: 'InvoiceSerial',
        name: 'InvoiceSerial',
        component: InvoiceSerial,
        meta: { title: '[UR1.1.028] - Seri hóa đơn' }
      },
      {
        path: 'StaffStock',
        name: 'StaffStock',
        component: StaffStock,
        meta: { title: '[UR1.1.078] - Gán kho nhân viên' }
      },
      {
        path: 'AssignCommitNumberManual',
        name: 'AssignCommitNumberManual',
        component: () => import('./category/AssignCommitNumberManual'),
        meta: { title: 'AssignCommitNumberManual' }
      },
      {
        path: 'StaffPlace',
        name: 'StaffPlace',
        component: () => import('./category/StaffPlace/index.js'),
        meta: {
          title: 'UR2.13.006 - Gán địa danh - NVKD '
        }
      },
      {
        path: 'AssignPermissionUnit',
        name: 'AssignPermissionUnit',
        component: () => import('./category/AssignPermissionUnit/index.js'),
        meta: {
          title: '[UR1.2.022] - Phân quyền tham số mặc định - Đơn vị '
        }
      },
      {
        path: 'SubscriberChargePrice',
        name: 'SubscriberChargePrice',
        component: SubscriberChargePrice,
        meta: {
          title: '[UR1.3.006] - Mức cước thuê bao',
          tag: '9+11'
        }
      },
      {
        path: 'NoDocument',
        name: 'NoDocument',
        component: () => import('./category/NoDocument'),
        meta: {
          title: '[UR1.3.011] - Số công văn'
        }
      },
      {
        path: 'AssignAttributeTypeToSuppliesType',
        name: 'AssignAttributeTypeToSuppliesType',
        component: AssignAttributeTypeToSuppliesType,
        meta: {
          title: '[UR1.1.088] - Gắn thuộc tính, kiểu thuộc tính vào loại vật tư'
        }
      },
      {
        path: 'Attribute',
        name: 'Attribute',
        component: Attribute,
        meta: {
          title: '[UR1.1.083] - Danh mục thuộc tính'
        }
      },
      {
        path: 'ManageReceiptStore',
        name: 'ManageReceiptStore',
        component: ManageReceiptStore,
        meta: {
          title: '[UR1.1.098] - Quản lý kho biên lai'
        }
      },
      {
        path: 'AssignStaffLocation',
        name: 'AssignStaffLocation',
        component: AssignStaffLocation,
        meta: {
          title: 'UR1.2.021 - Gán vị trí nhân viên - AssignStaffLocation'
        }
      },
      {
        path: 'AssignSuppliesToSubsType',
        name: 'AssignSuppliesToSubsType',
        component: AssignSuppliesToSubsType,
        meta: {
          title: 'UR1.1.076 - Gán vật tư - loại hình thuê bao  - AssignSuppliesToSubsType'
        }
      },
      {
        path: 'UpdateInstallGroupByFile',
        name: 'UpdateInstallGroupByFile',
        component: UpdateInstallGroupByFile,
        meta: {
          title: 'UR1.5.042 - Cập nhật tổ thi công theo File  - UpdateInstallGroupByFile'
        }
      },
      {
        path: 'OverdueDebtPercentage',
        name: 'OverdueDebtPercentage',
        component: OverdueDebtPercentage,
        meta: {
          title: '[UR1.1.050] - Tỷ lệ thù lao thu nợ quá hạn'
        }
      },
      {
        path: 'Units',
        name: 'Units',
        component: Units,
        meta: {
          title: '[UR1.1.072] - Đơn vị tính',
          dialog: true,
          dialogfull: true
        }
      },
      {
        path: 'AssignSuppliesToTitleDepartment',
        name: 'AssignSuppliesToTitleDepartment',
        component: AssignSuppliesToTitleDepartment,
        meta: {
          title: '[UR1.1.082] - Gán loại vật tư - Chức danh, Đơn vị'
        }
      },
      {
        path: 'Purpose',
        name: 'Purpose',
        component: Purpose,
        meta: {
          title: '[UR1.1.073] - Mục đích',
          dialog: true,
          dialogfull: true
        }
      },
      {
        path: 'AssignSingleStaffSMSAlert',
        name: 'AssignSingleStaffSMSAlert',
        meta: {
          title: '[UR1.1.071] - Quản lý nhân viên nhận tin nhắn cảnh báo dự báo (Gán từng nhân viên)'
        },
        component: () => import('./category/AssignSingleStaffSMSAlert/index.vue')
      },
      {
        path: 'AssignStaffSMSAlert',
        name: 'AssignStaffSMSAlert',
        meta: {
          title: '[UR1.1.070] - Quản lý nhân viên nhận tin nhắn cảnh báo dự báo (Gán hàng loạt nhân viên)'
        },
        component: () => import('./category/AssignStaffSMSAlert/index.vue')
      },
      {
        path: 'AssignStaffSubs',
        name: 'assign-staff-subs',
        meta: {
          title: '[UR1.5.048] - Gán nhân viên quản lý thuê bao'
        },
        component: () => import('./category/AssignStaffSubs/index.vue')
      },
      {
        path: 'AssignCustormerCareStaff',
        name: 'AssignCustormerCareStaff',
        meta: {
          title: '[UR1.5.050] - Gán lại nhân viên chăm sóc KHDN'
        },
        component: () => import('./category/AssignCustormerCareStaff/index.vue')
      },
      {
        path: 'DebtLine',
        name: 'DebtLine',
        meta: {
          title: '[UR1.1.013] - Tuyến thu'
        },
        component: () => import('./category/DebtLine/index.vue')
      },
      {
        path: 'ManagePermission',
        name: 'ManagePermission',
        component: ManagePermission,
        meta: {
          title: '[UR1.1.107] - Quản lý quyền'
        }
      },
      {
        path: 'Supplies',
        name: 'Supplies',
        component: Supplies,
        meta: {
          title: '[UR1.1.075] - Vật tư'
        }
      },
      {
        path: 'UpdateAttributeType',
        name: 'UpdateAttributeType',
        component: UpdateAttributeType,
        meta: {
          title: '[UR1.1.086] - Cập nhật kiểu thuộc tính'
        }
      },
      {
        path: 'AssignSuppliseAttribute',
        name: 'AssignSuppliseAttribute',
        component: AssignSuppliseAttribute,
        meta: {
          title: '[UR1.1.084] - Gán thuộc tính vật tư'
        }
      },
      {
        path: 'OverdueDebtUnitPrice',
        name: 'OverdueDebtUnitPrice',
        meta: {
          title: '[UR1.1.049] - Đơn giá thù lao thu nợ quá hạn'
        },
        component: () => import('./category/OverdueDebtUnitPrice/index.vue')
      },
      {
        path: 'UpdateQuestionsForStaffType',
        name: 'UpdateQuestionsForStaffType',
        meta: {
          title: '[UR1.1.007] - Cập nhật câu hỏi cho loại nhân viên'
        },
        component: () => import('./category/UpdateQuestionsForStaffType/index.vue')
      },
      {
        path: 'PaymentItem',
        name: 'PaymentItem',
        meta: {
          title: '[UR1.3.002] - Khoản mục thanh toán'
        },
        component: () => import('./category/PaymentItem/index.vue')
      },
      {
        path: 'ManageCustormerNoMyTV',
        name: 'ManageCustormerNoMyTV',
        meta: {
          title: '[UR1.5.039] - Quản lý KH sử dụng SmartTV chưa dùng MyTV'
        },
        component: () => import('./category/ManageCustormerNoMyTV/index.vue')
      },
      {
        path: 'MailNoticeUnitPrice',
        name: 'MailNoticeUnitPrice',
        component: MailNoticeUnitPrice,
        meta: {
          title: '[UR1.1.051] - Đơn giá phát thư thông báo'
        }
      },
      {
        path: 'AgentCommissionParameter',
        name: 'agentCommissionParameter',
        meta: {
          title: '[UR1.1.045] - Bảng tham số tính lương đại lý'
        },
        component: () => import('./category/AgentCommissionParameter/index.js')
      },
      {
        path: 'AssignCollectDebtStaff',
        name: 'AssignCollectDebtStaff',
        component: AssignCollectDebtStaff,
        meta: {
          title: '[UR1.2.019] - Gán nhân viên thu cước',
          dialog: true
        }
      },
      {
        path: 'MultiServicePackage',
        name: 'MultiServicePackage',
        component: MultiServicePackage,
        meta: {
          title: '[UR1.3.010] - Gói đa dịch vụ'
        }
      },
      {
        path: 'Users',
        name: 'Users',
        component: Users,
        meta: {
          title: '[UR1.2.012] - Người dùng'
        }
      },
      {
        path: 'PriceList',
        name: 'PriceList',
        component: () => import('./category/PriceList/index.js'),
        meta: {
          title: '[UR1.3.001] - Bảng giá'
        }
      },
      {
        path: 'DefaultParameter',
        name: 'DefaultParameter',
        component: DefaultParameter,
        meta: {
          title: '[UR1.2.002] - Tham số mặc định',
          dialog: true
        }
      },
      {
        path: 'DSLAMTransfer',
        name: 'dslamTransfer',
        meta: {
          title: ' [UR1.5.018] - Cắt chuyển DSLam'
        },
        component: () => import('./subscriber/DSLAMTransfer/index')
      },
      {
        path: 'UpdateSubsListWithCable',
        name: 'UpdateSubsListWithCable',
        meta: {
          title: ' [UR1.5.009] - Cập nhật DS thuê bao cùng đôi cáp'
        },
        component: () => import('./subscriber/UpdateSubsListWithCable/index')
      },
      {
        path: 'ChangeFreeIPInfo',
        name: 'changeFreeIpInfor',
        meta: {
          title: '[UR1.5.023] - Thay đổi thông tin miễn phí IP',
          dialog: true
        },
        component: ChangeFreeIPInfo
      },
      {
        path: 'ChangeNEParameter',
        name: 'ChangeNEParameter',
        meta: {
          title: '[UR1.5.013] - (*)Lập trình - Thay đổi thông số Ne'
        },
        component: ChangeNEParameter
      },
      {
        path: 'Store',
        name: 'store',
        meta: {
          title: '[UR1.1.077] - Kho'
        },
        component: () => import('./category/Store/index.js')
      },
      {
        path: 'PreferentialSubscriber',
        name: 'preferentialSubscription',
        meta: {
          title: '[UR1.3.009] - Bổ sung thuê bao ưu đãi phục vụ tính cước'
        },
        component: () => import('./category/PreferentialSubscriber/index.js')
      },
      {
        path: 'ManageReceiptCreatedTimes',
        name: 'ManageReceiptCreatedTimes_',
        component: () => import('./category/ManageReceiptCreatedTimes_/index.vue'),
        meta: {
          title: '[UR1.1.096] - Quản lý lượt tạo biên lai'
        }
      },
      {
        path: 'PurposeContractType',
        name: 'PurposeContractType',
        component: () => import('./category/PurposeContractType/index.vue'),
        meta: {
          title: '[UR1.1.081] - Loại HĐ - Mục đích'
        }
      },
      {
        path: 'County',
        name: 'County',
        component: () => import('./category/County/index.vue'),
        meta: {
          title: '[UR1.1.001] - Phường/Xã'
        }
      },
      {
        path: 'Prefix',
        name: 'Prefix',
        component: Prefix,
        meta: {
          title: '[UR1.1.010] - Prefix'
        }
      },
      {
        path: 'Department',
        name: 'Department',
        component: () => import('./category/Department/index.js'),
        meta: {
          title: 'UR1.2.013 - Đơn vị '
        }
      },
      {
        path: 'Staff',
        name: 'Staff',
        meta: {
          title: '[UR1.2.014] - Nhân viên'
        },
        component: () => import('./category/Staff/index.js')
      },
      {
        path: 'Street',
        name: 'street',
        meta: {
          title: '[UR1.1.003] - Phố/Ấp/Khu'
        },
        component: () => import('./category/Street/index.js')
      },
      {
        path: 'State',
        name: 'state',
        meta: {
          title: '[UR1.1.002] - Quận/Huyện'
        },
        component: () => import('./category/State/index.js')
      },
      {
        path: 'ContractorRole',
        name: 'contractorRole',
        meta: {
          title: '[UR1.1.074] - Vai trò tổ thầu',
          dialog: true
        },
        component: () => import('./category/ContractorRole')
      },
      {
        path: 'ComplaintTopic',
        name: 'complaintTopic',
        meta: {
          title: '[UR1.1.039] - Chủ đề khiếu nại',
          dialog: true
        },
        component: () => import('./category/ComplaintTopic')
      },
      {
        path: 'Project',
        name: 'Project',
        component: Project,
        meta: {
          title: '[UR1.1.080] - Dự án'
        }
      },
      {
        path: 'ConfirmRenewPrepaid',
        name: 'ConfirmRenewPrepaid',
        component: ConfirmRenewPrepaid,
        meta: {
          title: '[UR1.5.038] - Xác nhận gia hạn đặt cọc trả trước'
        }
      },
      {
        path: 'UpdateUnitPrice3883',
        name: 'UpdateUnitPrice3883',
        component: UpdateUnitPrice3883,
        meta: {
          title: '[UR1.1.029] - Cập nhật đơn giá 3883 (Trưởng đơn vị)'
        }
      },
      {
        path: 'ListBuilding',
        name: 'ListBuilding',
        component: ListBuilding,
        meta: {
          title: '[UR1.1.125] - Danh mục tòa nhà'
        }
      },
      {
        path: 'ExchangeRate',
        name: 'ExchangeRate',
        component: () => import('./category/ExchangeRate/index.js'),
        meta: {
          title: 'UR1.1.035 - Tỷ giá quy đổi '
        }
      },
      {
        path: 'UpdatePromote',
        name: 'UpdatePromote',
        component: () => import('./category/UpdatePromote/UpdatePromote.vue'),
        meta: {
          title: 'UR1.1.030 - Cập nhật khuyến mại STB (2731) '
        }
      },
      {
        path: 'Menu',
        name: 'Menu',
        component: Menu,
        meta: {
          title: '[UR1.1.105] - Menu'
        }
      },
      {
        path: 'NiceNumberStyle',
        name: 'NiceNumberStyle',
        meta: {
          title: '[UR1.1.011] - Kiểu số đẹp'
        },
        component: () => import('./category/NiceNumberStyle/index.vue')
      },
      {
        path: 'AssignPropertyMaterial',
        name: 'AssignPropertyMaterial',
        component: () => import('./category/AssignPropertyMaterial/AssignPropertyMaterial.vue'),
        meta: {
          title: '[UR1.1.085] - Gán thuộc tính - Loại vật tư'
        }
      },
      {
        path: 'UserStatus',
        name: 'UserStatus',
        component: () => import('./category/UserStatus/UserStatus.vue'),
        meta: {
          title: '[UR1.2.017] - Trạng thái người dùng'
        }
      },
      {
        path: 'EmployeeAssignmentCriteria',
        name: 'EmployeeAssignmentCriteria',
        component: () => import('./category/EmployeeAssignmentCriteria/EmployeeAssignmentCriteria.vue'),
        meta: {
          title: '[UR1.2.031] - Gán nhân viên - tiêu chí'
        }
      },
      {
        path: 'RemunerateDistributeFreight',
        name: 'RemunerateDistributeFreight',
        meta: {
          title: '[UR1.1.052] - Đơn giá thù lao phát giấy báo cước'
        },
        component: () => import('./category/RemunerateDistributeFreight/index.vue')
      },
      {
        path: 'FeeCollectionFactor',
        name: 'FeeCollectionFactor',
        meta: {
          title: '[UR1.1.048] - Hệ số thu cước róc'
        },
        component: () => import('./category/FeeCollectionFactor')
      },
      {
        path: 'UpdateAnswers',
        name: 'UpdateAnswers',
        component: UpdateAnswers,
        meta: {
          title: '[UR1.1.006] - Cập nhật câu trả lời'
        }
      },
      {
        path: 'UpdateQuestions',
        name: 'UpdateQuestions',
        component: UpdateQuestions,
        meta: {
          title: '[UR1.1.005] - Cập nhật câu hỏi'
        }
      },
      {
        path: 'SitesArea',
        name: 'SitesArea',
        meta: {
          title: '[UR1.3.004] - Khu vực địa danh'
        },
        component: () => import('./category/SitesArea/SitesArea.vue')
      },
      {
        path: 'SitesArea2',
        name: 'SitesArea2',
        meta: {
          title: '[UR1.3.004] - Khu vực địa danh'
        },
        component: () => import('./category/SiteArea2/SitesArea2.vue')
      },
      {
        path: 'DeliveryReceiveReceipt',
        name: 'DeliveryReceiveReceipt',
        meta: {
          title: '[UR1.1.128] - Giao, nhận, trả biên lai thu tiền'
        },
        component: () => import('./category/DeliveryReceiveReceipt/DeliveryReceiveReceipt.vue')
      },
      {
        path: 'AssignLocationEmployee',
        name: 'AssignLocationEmployee',
        component: AssignLocationEmployee,
        meta: {
          title: '[UR1.2.023] - Gán địa danh - nhân viên'
        }
      },
      ,
      {
        path: 'UpdateEmployeePosition',
        name: 'UpdateEmployeePosition',
        component: UpdateEmployeePosition,
        meta: {
          title: '[UR1.2.025] - Cập nhật vị trí nhân viên'
        }
      },
      {
        path: 'UpdateItem',
        name: 'UpdateItem',
        component: UpdateItem,
        meta: {
          title: '[UR1.1.091] - Cập nhập khoản mục',
          dialog: true
        }
      },
      {
        path: 'CancelReason',
        name: 'CancelReason',
        component: CancelReason,
        meta: {
          title: '[UR1.1.094] - Lý do hủy',
          dialog: true
        }
      },
      {
        path: 'FreightRateTSL',
        name: 'FreightRateTSL',
        component: FreightRateTSL,
        meta: {
          title: '[UR1.1.116] - Mức cước TSL'
        }
      },
      {
        path: 'AssignLocationEmployeeHPG',
        name: 'AssignLocationEmployeeHPG',
        component: AssignLocationEmployeeHPG,
        meta: {
          title: '[UR1.2.028] - Gán địa danh- nhân viên-HPG'
        }
      },
      {
        path: 'PartnerList',
        name: 'PartnerList',
        component: PartnerList,
        meta: {
          title: '[UR1.1.122] - Danh mục đối tác '
        }
      },
      {
        path: 'ChangeTicket',
        name: 'ChangeTicket',
        meta: {
          title: '[UR1.1.121] - Phiếu thay đổi'
        },
        component: () => import('./category/ChangeTicket/index.vue')
      },
      {
        path: 'CompensationUtility',
        name: 'CompensationUtility',
        meta: {
          title: '[UR1.1.056] - Tiện ích thù lao'
        },
        component: () => import('./category/CompensationUtility/index.vue')
      },
      {
        path: 'RemunerationOrder',
        name: 'RemunerationOrder',
        component: () => import('./category/RemunerationOrder/RemunerationOrder.vue'),
        meta: {
          title: '[UR1.1.100] - Lập lệnh chi trả thù lao'
        }
      },
      {
        path: 'MenuGroup',
        name: 'MenuGroup',
        component: () => import('./category/MenuGroup/index.js'),
        meta: {
          title: 'UR1.1.104 - Nhóm menu'
        }
      },
      {
        path: 'LockUnlockUsers',
        name: 'LockUnlockUsers',
        component: () => import('./LockUnlockUsers/index.vue'),
      },
      {
        path: 'ProgramOperation',
        name: 'ProgramOperation',
        meta: {
          title: '[UR1.4.003] - Thao tác chương trình',
          dialog: true
        },
        component: ProgramOperation
      },
      {
        path: 'DebtCycle',
        name: 'DebtCycle',
        component: DebtCycle,
        meta: {
          title: '[UR1.1.036] - Chu kỳ nợ',
          dialog: true
        }
      },
      {
        path: 'RemunerateDistributeInvoice',
        name: 'RemunerateDistributeInvoice',
        component: RemunerateDistributeInvoice,
        meta: {
          title: '[UR1.1.054] - Thù lao phát hoá đơn chuyển khoản'
        }
      },
      {
        path: 'OverdueDebtCollectionFee',
        name: 'OverdueDebtCollectionFee',
        meta: {
          title: '[UR1.1.055] - Thù lao thu nợ quá hạn'
        },
        component: () => import('./category/OverdueDebtCollectionFee/index.vue')
      },
      {
        path: 'UpdateReceipt',
        name: 'UpdateReceipt',
        meta: {
          title: '[UR1.1.095] - Cập nhật loại biên lai'
        },
        component: () => import('./category/UpdateReceipt/index.vue')
      },
      {
        path: 'DeadlineData',
        name: 'DeadlineData',
        meta: {
          title: '[UR1.1.092] - Hạn số liệu'
        },
        component: () => import('./category/DeadlineData/index.vue')
      },

      {
        path: 'InternetSpeed',
        name: 'InternetSpeed',
        meta: {
          title: '[UR1.3.012] - Tốc độ Internet'
        },
        component: () => import('./category/InternetSpeed/index.vue')
      },
      {
        path: 'UpdateSMSBrandnameMaintenanceFee',
        name: 'UpdateSMSBrandnameMaintenanceFee',
        meta: {
          title: '[UR1.3.016] - Cập nhật phí duy trì SMSBrandname'
        },
        component: () => import('./category/UpdateSMSBrandnameMaintenanceFee/index.vue')
      },
      {
        path: 'MailServer',
        name: 'MailServer',
        meta: {
          title: '[UR1.4.008] - Mail server'
        },
        component: () => import('./category/MailServer/index.vue')
      },
      {
        path: 'ChangePassword',
        name: 'ChangePassword',
        meta: {
          title: '[UR1.2.001] - Đổi mật khẩu'
        },
        component: () => import('./category/ChangePassword/index.vue')
      },
      {
        path: 'CustomerCareInformation',
        name: 'CustomerCareInformation',
        component: () => import('./category/CustomerCareInformation/index.js'),
        meta: {
          title: '[UR1.3.007] - Thông tin CSKH'
        }
      },
      {
        path: 'NoticeFromAdmin',
        name: 'NoticeFromAdmin',
        meta: {
          title: '[UR1.1.057] - Thông báo từ quản trị'
        },
        component: () => import('./category/NoticeFromAdmin/index.vue')
      },
      {
        path: 'ListInvestmentContracts',
        name: 'ListInvestmentContracts',
        meta: {
          title: '[UR1.1.126] - Danh mục hợp đồng đầu tư'
        },
        component: () => import('./category/ListInvestmentContracts')
      },
      {
        path: 'UpdateDeviceType',
        name: 'UpdateDeviceType',
        meta: {
          title: '[UR14.1.006] - Cập nhật loại thiết bị'
        },
        component: () => import('./category/UpdateDeviceType')
      },
      {
        path: 'SyncVinaphoneContacts',
        name: 'SyncVinaphoneContacts',
        meta: {
          title: '[UR1.5.019] - Đồng bộ danh bạ vinaphone'
        },
        component: () => import('./category/SyncVinaphoneContacts')
      },
      {
        path: 'EmployeeAssignmentWard',
        name: 'EmployeeAssignmentWard',
        component: EmployeeAssignmentWard,
        meta: {
          title: '[UR1.2.030] - Gán nhân viên phường xã '
        }
      },
      {
        path: 'Storetest',
        name: 'storetest',
        meta: {
          title: '[UR1.1.077] - Kho Test'
        },
        component: () => import('./category/StoreTest/index.js')
      },
      {
        path: 'ProjectList',
        name: 'ProjectList',
        component: ProjectList,
        meta: {
          title: '[UR1.1.124] - Danh mục dự án'
        }
      },
      {
        path: 'RevocationCACerCreatesError',
        name: 'RevocationCACerCreatesError',
        meta: {
          title: 'Thu hồi chứng thư CA tạo lỗ'
        },
        component: () => import('./category/RevocationCACerCreatesError')
      },
      {
        path: 'Message',
        name: 'Message',
        meta: {
          title: '[UR1.4.009] - Message'
        },
        component: () => import('./category/Message/index.vue')
      },
      {
        path: 'UnitPriceFeeCollecByLevelRevenue',
        name: 'UnitPriceFeeCollecByLevelRevenue',
        meta: {
          title: '[UR1.1.046] - Đơn giá thù lao thu cước theo mức doanh thu'
        },
        component: () => import('./category/UnitPriceFeeCollecByLevelRevenue/index.vue')
      },
      {
        path: 'DictionaryConfig',
        name: 'DictionaryConfig',
        meta: {
          title: '[UR1.4.005] - Cấu hình bảng từ điển'
        },
        component: () => import('./category/DictionaryConfig/index.vue')
      },
      {
        path: 'ApprovePayCompensation',
        name: 'ApprovePayCompensation',
        meta: {
          title: '[UR1.1.101] - Duyệt lệnh chi trả thù lao'
        },
        component: () => import('./category/ApprovePayCompensation/index.vue')
      },
      {
        path: 'Undertaking',
        name: 'Undertaking',
        meta: {
          title: '[UR1.1.079] - Undertaking'
        },
        component: () => import('./category/Undertaking/index.vue')
      },
      {
        path: 'ListInvestor',
        name: 'Listinvestor',
        meta: {
          title: '[UR1.1.123] - Listinvestor'
        },
        component: () => import('./category/ListInvestor/index.vue')
      },
      {
        path: 'UpdateHouseholdInformation',
        name: 'UpdateHouseholdInformation',
        meta: {
          title: '[UR1.5.032] - Cập nhật thông tin hộ dân'
        },
        component: () => import('./category/UpdateHouseholdInformation/UpdateHouseholdInformation.vue')
      },
      {
        path: 'CreateNESubs',
        name: 'CreateNESubs',
        component: () => import('./category/CreateNESubs'),
        meta: {
          title: 'Tạo số NE'
        }
      },
      {
        path: 'Process',
        name: 'Process',
        meta: {
          title: '[UR1.4.001] - Quy trình'
        },
        component: () => import('./category/Process')
      },
      {
        path: 'Bank',
        name: 'Bank',
        component: () => import('./category/Bank/index.js'),
        meta: {
          title: 'UR1.1.014 - Ngân hàng '
        }
      },
      {
        path: 'ChangePasswordADSLFTTHMyTV',
        name: 'ChangePasswordADSLFTTHMyTV',
        meta: {
          title: '[UR1.5.014] - Đổi mật khẩu ADSL, FTTH, MyTV'
        },
        component: () => import('./category/ChangePasswordADSLFTTHMyTV/index.vue')
      },
      {
        path: 'FunctionGroup',
        name: 'FunctionGroup',
        component: () => import('./category/MenuGroup/index.js'),
        meta: {
          title: 'UR1.1.108 - Nhóm hàm'
        }
      },
      {
        path: 'AssignRightToCollectDebt',
        name: 'AssignRightToCollectDebt',
        meta: {
          title: 'BSS-66793 Gán quyền gạch nợ'
        },
        component: () => import('./category/AssignRightToCollectDebt/index.vue')
      },
      {
        path: 'SetStaffToReceiveMessages',
        name: 'SetStaffToReceiveMessages',
        component: SetStaffToReceiveMessages,
        meta: {
          title: 'UR2.17.003 Thiết lập nhân viên nhận tin nhắn'
        }
      },
      {
        path: 'DeleteCache',
        name: 'DeleteCache',
        component: () => import('./category/DeleteCache/DeleteCache.vue'),
        meta: {
          title: 'UR1.1.1111 - Xóa cache'
        }
      },
      {
        path: 'ThongBaoCuocTheoFile',
        name: 'ThongBaoCuocTheoFile',
        component: ThongBaoCuocTheoFile,
        meta: {
          title: 'BSS-73300_01 Thông báo cước theo file'
        }
      },
      {
        path: 'TraCuuThongTinNhanThongBaoCuoc',
        name: 'TraCuuThongTinNhanThongBaoCuoc',
        component: TraCuuThongTinNhanThongBaoCuoc,
        meta: {
          title: 'BSS-73300_02 Tra cứu thông tin nhận thông báo cước'
        }
      },
      {
        path: 'TraCuuLogCapNhatThongTinTBC',
        name: 'TraCuuLogCapNhatThongTinTBC',
        component: TraCuuLogCapNhatThongTinTBC,
        meta: {
          title: 'BSS-73300_03 Tra cứu log cập nhật thông tin thông báo cước'
        }
      },
      {
        path: 'TraCuuDoiSoatSoHoaThuCuoc',
        name: 'TraCuuDoiSoatSoHoaThuCuoc',
        component: TraCuuDoiSoatSoHoaThuCuoc,
        meta: {
          title: 'BSS-73300_04 Tra cứu đối soát số hoá thu cước'
        }
      },
      {
        path: 'ThietLapGuiTBC',
        name: 'ThietLapGuiTBC',
        component: ThietLapGuiTBC,
        meta: {
          title: 'BSS-73300_05 Thiết lập gửi thông báo cước'
        }
      },
      {
        path: 'ThueBaoKhongNhanThongBaoNhacNoCuoc',
        name: 'ThueBaoKhongNhanThongBaoNhacNoCuoc',
        component: ThueBaoKhongNhanThongBaoNhacNoCuoc,
        meta: {
          title: 'BSS-73300_06 Thuê bao không nhận thông báo/ nhắc nợ cước'
        }
      },
      {
        path: 'DoiSoatDuLieuIT',
        name: 'DoiSoatDuLieuIT',
        component: DoiSoatDuLieuIT,
        meta: {
          title: 'BSS-75403 Bổ sung chức năng đối soát dịch vụ CNTT'
        }
      },
      {
        path: 'frmHoanCongIMS_Net',
        name: 'frmHoanCongIMS_Net',
        meta: {
          title: '[BSS-76287] - NET khai báo dịch vụ SipTrunking'
        },
        component: () => import('./category/frmHoanCongIMS_Net')
      },
      {
        path: 'frmHoanCongMegaWan_Net',
        name: 'frmHoanCongMegaWan_Net',
        meta: {
          title: '[BSS-76286] - NET khai báo dịch vụ Megawan'
        },
        component: () => import('./category/frmHoanCongMegaWan_Net')
      },
      {
        path: 'frmGiaoViecVIP',
        name: 'frmGiaoViecVIP',
        meta: {
          title: '[BSS-76285] - Giao việc Vip'
        },
        component: () => import('./category/frmGiaoViecVIP')
      },
      {
        path: 'AssignConfiguration',
        name: 'AssignConfiguration',
        meta: {
          title: '[KHDN-14157] - Cấu hình phân giao đơn hàng'
        },
        component: () => import('./category/AssignConfiguration')
      },
      {
        path: 'SetupFavoriteFunctional',
        name: 'SetupFavoriteFunctional',
        meta: {
          title: 'Cài đặt chức năng yêu thích'
        },
        component: () => import('./category/SetupFavoriteFunctional/index.vue')
      },
      {
        path: 'ReviewFailTest',
        name: 'ReviewFailTest',
        meta: {
          title: 'Xét duyệt bài thi trắc nghiệm không đạt'
        },
        component: () => import('./category/ReviewFailTest/index.vue')
      },
      { path: 'B2APLAN', name: 'B2APLAN', component: () => import('./subscriber/B2APLAN/index.js'), meta: { title: '[CDS2023-344] - Quản lý Chiến dịch B2A' } },
      {
        path: 'b2aimportexportlist',
        name: 'b2aimportexportlist',
        meta: {
          title: '[CDS2023-162] - Quản trị chiến dịch - Tra cứu, Import/Export danh sách'
        },
        component: () => import('./subscriber/b2aimportexportlist/index.vue')
      },
      {
        path: 'ChangeSubsManageAreaBTS',
        name: 'ChangeSubsManageAreaBTS',
        component: () => import('./subscriber/ChangeSubsManageAreaBTS/index.js'),
        meta: {
          title: 'Phân thuê bao theo địa bàn - nhân công'
        }
      },
      {
        path: 'ManageBTS',
        name: 'ManageBTS',
        meta: {
          title: 'Khai báo BTS'
        },
        component: () => import('./category/ManageBTS/QuanLyThietBiBTS.vue')
      },
      {
        path: 'KhaiBaoTyGia',
        name: 'KhaiBaoTyGia',
        meta: {
          title: 'KHDN-19937 Khai báo tỷ giá'
        },
        component: () => import('./category/KhaiBaoTyGia/index.vue')
      },
      {
        path: 'AssignTrustPoint',
        name: 'AssignTrustPoint',
        meta: {
          title: 'GÁN ĐIỂM TÍN NHIỆM KHÓA MÁY'
        },
        component: () => import('./category/AssignTrustPoint/index.vue')
      },
      {
        path: 'NhapXuatVT_v2',
        name: 'NhapXuatVT_v2',
        component: () => import('./subscriber/NhapXuatVT_v2'),
        meta: {
          title: '[]'
        }
      },
      {
        path: 'flexing_page',
        name: 'TestPage',
        component: () => import('./subscriber/flexing_page'),
        meta: {
          title: '[]'
        }
      },
      {
        path: 'SearchBuildingByList',
        name: 'SearchBuildingByList',
        meta: {
        title: 'Tra cứu thuê bao tòa nhà theo danh sách'
        },
        component: () => import('./category/SearchBuildingByList')
      },
      {
        path: 'CapNhatTTToaNhaFile',
        name: 'CapNhatTTToaNhaFile',
        meta: {
        title: 'BSS-97325 - Bổ sung chức năng Cập nhật thông tin dự án - Tòa nhà'
        },
        component: () => import('./category/CapNhatTTToaNhaFile/index.js')
      },
      {
        path: 'frmChonQuay',
        name: 'frmChonQuay',
        meta: {
          title: '[BSS-98538] - Chọn quầy hợp đồng'
        },
        component: () => import('./category/frmChonQuay')
      },
      {
        path: 'StaffMapCountry',
        name: 'StaffMapCountry',
        meta: {
          title: '[BSS-105334] - Xây dựng chức năng Map nhân viên thao tác nhiều tỉnh'
        },
        component: StaffMapCountry
      },
      {
        path: 'capNhatTT_VNPTI',
        name: 'capNhatTT_VNPTI',
        meta: {
          title: 'Quản lý kênh toàn trình'
        },
        component: () => import('./category/frmCapNhatTT_VNPTI')
      },
      {
        path: 'SearchProcess',
        name: 'SearchProcess',
        meta: {
          title: 'Tra cứu quy trình'
        },
        component: () => import('./category/SearchProcess')
      },
      {
        path: 'cauhinhthamso',
        name: 'cauhinhthamso',
        meta: {
          title: '[BSS-107257] - Bổ sung chức năng cấu hình tham số mặc định'
        },
        component: () => import('./category/ParameterConfiguration')
      },	  
      {
        path: 'HouseType',
        name: 'HouseType',
        meta: {
          title: 'Danh mục loại nhà'
        },
        component: () => import('./category/HouseType/index.vue')
      },
      {
        path: 'BNMScripts',
        name: 'BNMScripts',
        meta: {
          title: 'Danh sách kịch bản lệnh BNM'
        },
        component: () => import('./bnm/BNMScripts/index.js')
      },
      {
        path: 'BNMScriptRequests',
        name: 'BNMScriptRequests',
        meta: {
          title: 'Tạo mới kịch bản BNM và khai báo Script'
        },
        component: () => import('./bnm/BNMScriptRequests/index.js')
      },
      {
        path: 'BNMLogScripts',
        name: 'BNMLogScripts',
        meta: {
          title: 'Danh sách lịch sử thay đổi Script'
        },
        component: () => import('./bnm/BNMLogScripts/index.js')
      },
      {
        path: 'BNMLogScripts',
        name: 'BNMLogScripts',
        meta: {
          title: 'Danh sách lịch sử thay đổi Script'
        },
        component: () => import('./bnm/BNMLogScripts/index.js')
      },
      {
        path: 'BNMDeviceParams',
        name: 'BNMDeviceParams',
        meta: {
          title: 'Khai báo profile thiết bị'
        },
        component: () => import('./bnm/BNMDeviceParams/index.js')
      },

      {
        path: 'BangCuocBrandname',
        name: 'BangCuocBrandname',
        meta: {
          title: 'Bảng cước dịch vụ SMS Brandname'
        },
        component: () => import('./category/BangCuocBrandname/index.vue')
      },
      {
        path: 'GoiThoaiNoiNgoaiMang',
        name: 'GoiThoaiNoiNgoaiMang',
        meta: {
          title: 'Gói cước thoại nội ngoại mạng'
        },
        component: () => import('./category/GoiThoaiNoiNgoaiMang/index.vue')
      },
      {
        path: 'KhoSoCNTT',
        name: 'KhoSoCNTT',
        meta: {
          title: 'Kho số CNTT'
        },
        component: () => import('./category/KhoSoCNTT/index.vue')
      },
      {
        path: 'BNMRequestOneBss',
        name: 'BNMRequestOneBss',
        meta: {
          title: 'DANH SÁCH YÊU CẦU TẠO SCRIPT TỪ BSS'
        },
        component: () => import('./bnm/BNMRequestOneBss/index.js')
      },
      {
        path: 'frmGanVitri_cd',
        name: 'frmGanVitri_cd',
        meta: {
          title: 'Gán vị trí - chức danh'
        },
        component: () => import('./category/frmGanVitri_cd')
      },
	  {
        path: 'DanhMucDiemCham',
        name: 'DanhMucDiemCham',
        meta: {
          title: 'Danh mục điểm chạm'
        },
        component: () => import('./category/DanhMucDiemCham/index.vue')
      },
      {
        path: 'ApproveProductCodeByCorp',
        name: 'ApproveProductCodeByCorp',
        meta: {
          title: 'TCT PHÊ DUYỆT CẤP MÃ SẢN PHẨM'
        },
        component: () => import('./category/ApproveProductCodeByCorp/index.js')

      },
	  {
        path: 'CongDoanDvBanCheo',
        name: 'CongDoanDvBanCheo',
        meta: {
          title: 'Danh mục công đoạn đơn vị tham gia bán chéo'
        },
        component: () => import('./category/CongDoanDvBanCheo/index.js')
      },
      {

        path: 'ApproveProductCodeByTD',
        name: 'ApproveProductCodeByTD',
        meta: {
          title: 'TĐ Phê duyệt cấp mã Sản phẩm'
        },
        component: () => import('./category/ApproveProductCodeByTD/index.js')
      },
      {

        path: 'AreaTaskManagement',
        name: 'AreaTaskManagement',
        meta: {
          title: 'Dashboard Điều hành ô địa bàn'
        },
        component: () => import('./AreaTaskManagement/index.vue')
      },
    ]
  },
  
]
