package vn.vnptit.sapi.oneapp.models

class TimKiem_KHDN_REQ {
    String customerType
    String phoneNumber
    String idService
    String customerId
    String taxId
    String name
    String campaignName
    String planCode
    List<String> classification
    List<String> rank
    List<String> potential
    List<String> risk
    List<String> segment
    String maTinh
    String maQuocGia
    int priority
    List<String> am
    int page
    int size
    List<String> status
}

class TimKiem_KHTN_REQ {
    String customerType
    String phoneNumber
    String idService
    String customerId
    String taxId
    String customerName
    String classification
    String rank
    String potential
    String risk
    String maTinh
}

class Xem_ChiTiet_KhachHang_TiemNang_REQ {
    String customerType
    String customerId
    String email
    String idService
    String ma_so_thue
    String ma_tinh
    String khachhang_id
    String service
    String phoneNumber
}

class AddressInfo {
    int cityId
    String cityName
    int districtId
    String districtName
    int wardId
    String wardName
    String homeNumber
    int areaId
    String areaName
    int streetId
    String streetName
    int hamletId
    String hamletName
    String characteristic
    String additionalAddress
    String acreage
    String infrastructure
}

class EnterpriseInfo {
    String customerName
    String taxIdentification
    String customerDepartment
    int fieldOfEnterprise
    int job
    int enterpriseSize
    int revenue
    int workingArea
    String corporateDate
    String phoneNumber
    String socialNetworkInfo
}

class OfficerDecideInfo {
    String officerName
    String dateOfBirth
    String gender
    String phoneNumber
    String email
}

class RelevantInfo {
    String relevantName
    String department
    String dateOfBirth
    String gender
    String phoneNumber
    String email
}

class KHDN_ThuCong_REQ {
    EnterpriseInfo enterpriseInfo
    AddressInfo addressInfo
    ArrayList<RelevantInfo> relevantInfo
    ArrayList<OfficerDecideInfo> officerDecideInfo
    String note
}

class Address {
    String street
    String city
    String state
    String country
    String zipcode
    String cityId
}

class AddressInfoNK {
    String street
    String city
    String state
    String country
    String zipcode
}

class BehaviorOnesme {
    String saleAssociate
    String department
    String leaderOfSaleAssociate
    String leaderOfDepartment
    String sale
    String carer
}

class BranchAddress {
    String street
    String city
    String state
    String country
    String zipcode
}

class CommitSLA {
}

class CustomerComplain {
    int numberOfProblem
    String serviceName
    Address address
    String reason
    String duration
    String result
}

class DeciderInfo {
    String name
    String dateOfBirth
    String gender
    String phoneNumber
    String email
    String hobb0y
    String maritalStatus
    FamilyInfo familyInfo
    String position
}

class EnterpriseInfoNK {
    String customerName
    String customerId
    String parentId
    String childId
    String sector
    String career
    String activity
    AddressInfoNK addressInfo
    BranchAddress branchAddress
    String taxId
    String bankAccountId
    String bankName
    String scale
    String workingArea
    String corporateDate
    String phoneNumber
    String fax
    String website
    double totalRevenue
    double revenuePerYear
    double revenuePerMonth
    String useServiceDate
    String serviceDuration
    CommitSLA commitSLA
    String paymentCycle
    String revenueMonthlyFluctuations
    String revenueAccrued
    String revenueDecline
    String classification
    String rank
    String potential
    String risk
}

class FamilyInfo {
}

class LevelOfProviderService {
    String serviceName
    String surveyDate
    double avgPoint
    int progressPoint
    int qualityPointCC
    int qualityPointNV
}

class ManagementInfoVnpt {
    String saleAssociate
    String department
    String leaderOfSaleAssociate
    String leaderOfDepartment
    String sale
    String carer
    String productSpecialist
    String updateTime
}

class PolicyCare {
    String name
    String duration
    String object
    int numberOfObject
    String careMethod
    String careItem
    double careCost
}

class RelevantInfoNK {
    String name
    String dateOfBirth
    String gender
    String phoneNumber
    String email
    String hobb0y
    String maritalStatus
    String position
    String deparment
}

class Reporter {
    int numberOfProblem
    String serviceName
    Address address
    String reason
    String duration
    String result
}

class Representative {
    String name
    String dateOfBirth
    String gender
    String position
    String phoneNumber
    String email
}

class SatisfyLevel {
    ArrayList<LevelOfProviderService> levelOfProviderService
    ArrayList<Object> levelOfCareCustomer
    ArrayList<Object> levelOfGQKN
    ArrayList<Object> levelOfFailure
    ArrayList<Object> levelOfSupport
    ArrayList<Object> levelOfEmployee
}

class ServiceOther {
    String serviceName
    String servicePrice
    String providerName
    String policy
    String feedback
}

class ServiceVnpt {
    int totalSubscriber
    double revenueService
    double revenueServicePerYear
    double revenueServicePerMonth
    double avgRevenueServicePerMonth
    double revenue
    double revenueDecline
    ArrayList<String> serviceNotUses
    ArrayList<String> intendedServices
    double revenueIntendedNewService
}

class SupportRequest {
    String serviceName
    String duration
    String classification
    String content
    String status
}

class KHDN_NguonKhac_REQ {
    EnterpriseInfoNK enterpriseInfo
    Representative representative
    ArrayList<DeciderInfo> deciderInfos
    ArrayList<RelevantInfoNK> relevantInfos
    ServiceVnpt serviceVnpt
    ServiceOther serviceOther
    ArrayList<PolicyCare> policyCares
    ArrayList<CustomerComplain> customerComplain
    ArrayList<Reporter> reporters
    ArrayList<SupportRequest> supportRequests
    SatisfyLevel satisfyLevel
    BehaviorOnesme behaviorOnesme
    ManagementInfoVnpt managementInfoVnpt
}

class TimKiem_B2A_REQ {
    List<String> am
    String maTinh
    int page
    int size
    List<String> mucDoUuTien
    String planCode
}

class Export_KHDN_REQ {
    String customerType
    String phoneNumber
    String idService
    String customerId
    String taxId
    String name
    String campaignName
    String planCode
    int priority
    List<String> classification
    List<String> rank
    List<String> potential
    List<String> risk
    String maTinh
    List<String> am
    int page
    int size
    List<String> status
}

class Export_B2A_REQ {
    List<String> am
    String maTinh
    String planCode
    List<String> mucDoUuTien
}

class TongHop_DuLieu_KhachHang_REQ {
    String am
    String ma_tinh
    String ngay_bat_dau
    String ngay_ket_thuc
}

class BaoCao_TheoPhongKHDN_REQ {
    String donvi_id
    String ma_tinh
    String ngay_bat_dau
    String ngay_ket_thuc
}

class BaoCao_Theo_TongCongTy_REQ {
    String ngay_bat_dau
    String ngay_ket_thuc
}

class BaoCao_Theo_GiamDocTTKD_REQ {
    String ma_tinh
    String ngay_bat_dau
    String ngay_ket_thuc
}

class ThongTin {
    String hoTen
    String ngaySinh
    String soDienThoai
    String gioiTinh
    String email
    String queQuan
    String cuTru
    int donviCongTacId
    String donviCongTacName
}

class HocVan {
    String trinhDo
    String cosoDaoTao
    String chuyenMon
}

class SoThich {
    String tinhCach
    String stAmThuc
    String stTheThao
    String stKhac
}

class CongTac {
    String chucVu
    String qtCtTu
    String qtCtDen
    String viTriQuyhoach
    String mstDn
    String tenDn
    String maTinhDn
}

class HoSo {
    String caNhanId
    String moiQuanHe
    String moTa
}

class MoiQuanHe {
    String mstDn
    String maTinhDn
    String tenDoanhNghiep
    String chucVu
    String moTa
}

class Them_ThongTin_CN_REQ {
    String maTinh
    String mst
    String nguoiTao
    String nguoiCn
    ThongTin thongTin
    List<HocVan> hocVans
    SoThich soThich
    List<CongTac> congTacs
    List<HoSo> hoSos
    List<MoiQuanHe> moiQuanHes
}

class CapNhat_ThongTin_CN_REQ {
    String id
    String maTinh
    String mst
    String nguoiTao
    String nguoiCn
    ThongTin thongTin
    List<HocVan> hocVans
    SoThich soThich
    List<CongTac> congTacs
    List<HoSo> hoSos
    List<MoiQuanHe> moiQuanHes
}

class Xoa_ThongTin_CN_REQ {
    String id
    String maTinh
    String mst
}

class Xem_ThongTin_CN_REQ {
    String id
    String maTinh
    String mst
}

class Lay_DanhSach_ThongTin_CN_REQ {
    String hoTen
    String ngaySinh
    String soDienThoai
    String gioiTinh
    String email
    String mst
    String maTinh
    int page
    int size
}

class Them_HoSo_CN_REQ {
    String canhan_id
    String moi_quan_he
    String mo_ta
    String mst
    String ma_tinh
    String nguoi_tao
}

class CapNhat_HoSo_CN_REQ {
    String id
    String canhan_id
    String moi_quan_he
    String mo_ta
    String mst
    String ma_tinh
    String nguoi_cn
}

class Xoa_HoSo_CN_REQ {
    String id
    String ma_tinh
    String mst
}

class Xem_HoSo_CN_REQ {
    String id
    String ma_tinh
    String mst
}

class Lay_DanhSach_HoSo_CN_REQ {
    String ho_ten
    String moi_quan_he
    String mo_ta
    String mst
    String ma_tinh
    int page
    int size
}

class Them_HocVan_REQ {
    String trinh_do
    String coso_daotao
    String chuyen_mon
    String mst
    String ma_tinh
    String nguoi_tao
}

class CapNhat_HocVan_REQ {
    String id
    String trinh_do
    String coso_daotao
    String chuyen_mon
    String mst
    String ma_tinh
    String nguoi_cn
}

class Xoa_HocVan_REQ {
    String id
    String ma_tinh
    String mst
}

class Xem_HocVan_REQ {
    String id
    String ma_tinh
    String mst
}

class Lay_DanhSach_HocVan_REQ {
    String mst
    String ma_tinh
    String co_so_dao_tao
    int page
    int size
}

class Them_QuaTrinh_CongTac_REQ {
    String mst
    String ma_tinh
    String chuc_vu
    String qt_ct_tu
    String qt_ct_den
    String vitri_quyhoach
    String nguoi_tao
    String mst_dn
    String ma_tinh_dn
}

class CapNhat_QuaTrinh_CongTac_REQ {
    String id
    String mst
    String ma_tinh
    String chuc_vu
    String qt_ct_tu
    String qt_ct_den
    String vitri_quyhoach
    String nguoi_cn
    String mst_dn
    String ma_tinh_dn
}

class Xoa_QuaTrinh_CongTac_REQ {
    String id
    String ma_tinh
    String mst
}

class Xem_QuaTrinh_CongTac_REQ {
    String id
    String ma_tinh
    String mst
}

class Lay_DanhSach_QuaTrinh_CongTac_REQ {
    String mst
    String ma_tinh
    String ten_to_chuc
    String chuc_vu
    String vitri_quyhoach
    String qt_ct_tu
    String qt_ct_den
    int page
    int size
}

class Them_ThongTin_BienDong_DN_REQ {
    String ma_tinh
    String ma_kh
    String mst
    String ten_kh
    String ngay
    String loai_thong_tin
    String noi_dung
    String nguoi_tao
    int loai_thong_tin_id
}

class CapNhat_ThongTin_BienDong_DN_REQ {
    String id
    String ma_tinh
    String ma_kh
    String mst
    String ten_kh
    String ngay
    String loai_thong_tin
    String noi_dung
    String nguoi_cn
    int loai_thong_tin_id
}

class Xoa_ThongTin_BienDong_DN_REQ {
    String id
    String maTinh
    String mst
}

class Xem_ThongTin_BienDong_DN_REQ {
    String id
    String maTinh
    String mst
}

class Lay_DanhSach_ThongTin_BienDong_DN_REQ {
    String ma_kh
    String mst
    String ma_tinh
    String ten_kh
    String ngay
    String loai_thong_tin
    String noi_dung
    int loai_thong_tin_id
    int page
    int size
}

class Them_ThongTin_QuanHe_DN_REQ {
    String mst
    String ma_tinh
    String mst_dn
    String ma_tinh_dn
    String chuc_vu
    String nguoi_tao
    String mo_ta
}

class CapNhat_ThongTin_QuanHe_DN_REQ {
    String id
    String mst
    String ma_tinh
    String mst_dn
    String ma_tinh_dn
    String chuc_vu
    String nguoi_cn
    String mo_ta
}

class Xoa_ThongTin_QuanHe_DN_REQ {
    String id
    String ma_tinh
    String mst
}

class Xem_ThongTin_QuanHe_DN_REQ {
    String id
    String ma_tinh
    String mst
}

class Lay_DanhSach_ThongTin_QuanHe_DN_REQ {
    String id
    String ma_tinh
    String mst
    String mo_ta
    String chuc_vu
    int page
    int size
}