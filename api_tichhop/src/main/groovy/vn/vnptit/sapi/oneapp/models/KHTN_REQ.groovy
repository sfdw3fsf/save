package vn.vnptit.sapi.oneapp.models

import com.fasterxml.jackson.annotation.JsonProperty

public class AddressInfo_V3 {
    public int cityId
    public String cityName
    public int districtId
    public String districtName
    public int wardId
    public String wardName
    public int streetId
    public String streetName
    public int hamletId
    public String hamletName
    public int areaId
    public String areaName
    public String characteristic
    public String additionalAddress
    public int acreage
    public String infrastructure
    public int numberOfFloors
    public int numberOfRooms
    public int homeNumber
    public String typeOfHouse
    public String zoneId
    public String zoneName
    public String infrastructureId
    public String infrastructureName
}

public class CustomerCommonInfo {
    public String customerName
    public String taxId
    public String customerId
    public String customerDepartment
    public String activity
    public int careerId
    public String careerName
    public String career
    public String scale
    public int revenue
    public String workingArea
    public String corporateDate
    public String phoneNumber
    public String socialNetworkInfo
    public String birthday
    public String gender
    public String idType
    public String idNumber
    public String idIssueDate
    public String idIssuePlace
    public String maritalStatus
    public String hobbies
    public String income
    public int totalMembers
    public ArrayList<FamilyMember> familyMembers
    public int customerTypeId
    public String customerTypeName
    public int contactId
    public String contactName
}

public class FamilyMember {
    public String phoneNumber
    public String memberName
    public String relationship
    public String birthday
    public String averageMobileRatePerMonth
}

public class InternetService {
    public boolean isUsingIntegratedPackage
    public String provider
    public String packageName
    public String packagePrice
    public String deposit
    public String paymentMethod
    public String serviceStartDate
    public String prepaidMonths
    public String expireDate
    public String ontType
    public String serviceQualityRating
    public boolean isPrePayment
    public int numberOfWifi
}

public class MobileService {
    public boolean isUsingData
    public String provider
    public String averageUsageCost
    public boolean usesData
    public String rechargeHabit
    public String subscriptionType
    public String packageName
    public int packagePrice
    public int deposit
    public String paymentMethod
    public String serviceStartDate
    public String prepaidMonths
    public String expireDate
    public SmartphoneInfo smartphoneInfo
    public boolean isPrePayment
    public String serviceQualityRating
}

public class OfficeInfo {
    public String officerName
    public String dateOfBirth
    public String gender
    public String phoneNumber
    public String email
}

public class RelevantInfo_V3 {
    public String relevantName
    public String department
    public String dateOfBirth
    public String gender
    public String phoneNumber
    public String email
}

public class SmartphoneInfo {
    public String os
    public String numberOfSimSlots
    public ArrayList<String> vnptInternalApps
    public ArrayList<String> frequentlyUsedApps
    public Map<String, Object> serviceOfInterest
}

public class TvService {
    public boolean isUsingIntegratedPackage
    public String provider
    public String packageName
    public int packagePrice
    public int deposit
    public String paymentMethod
    public String serviceStartDate
    public String prepaidMonths
    public String expireDate
    public int smartTV
    public int TV
    public String serviceQualityRating
    public boolean isPrePayment
}

public class WifiMeshService {
    public String wifiMeshService
    public int wifiMesh
    public int wifi
    public String packageName
    public int packagePrice
    public int deposit
    public String paymentMethod
    public String serviceStartDate
    public String prepaidMonths
    public String expireDate
    public String ontType
    public String serviceQualityRating
    public boolean isPrePayment
}

public class ManagementInfo {
    public Integer zoneId
    public String zoneName
}

public class ServicesOfInterest {
    public String service
    public String typeOfService
}

public class KhachHang_TiemNang_REQ {
    public String customerType
    public Integer statusId
    public String status
    public CustomerCommonInfo customerCommonInfo
    public AddressInfo_V3 addressInfo
    public ArrayList<RelevantInfo_V3> relevantInfo
    public ArrayList<OfficeInfo> officeInfo
    public InternetService internetService
    public TvService tvService
    public WifiMeshService wifiMeshService
    public MobileService mobileService
    public ManagementInfo managementInfo
    public ArrayList<ServicesOfInterest> servicesOfInterest
    public String totalPackageInterest
    public String createBy
    public String source
    public String note
    public String maTinh
    public String number_branchs
    public ArrayList<CameraIndoors> cameraIndoors
    public ArrayList<CameraOutdoors> cameraOutdoors
}

public class CameraIndoors {
    public int storageTypeId
    public String storageTypeName
    public int quantity
    public int price
    public String startMonth
    public String endMonth
}

public class CameraOutdoors {
    public int storageTypeId
    public String storageTypeName
    public int quantity
    public int price
    public String startMonth
    public String endMonth
}

public class CapNhat_KhachHang_REQ {
    public String customerType
    public Integer statusId
    public String status
    public CustomerCommonInfo customerCommonInfo
    public AddressInfo_V3 addressInfo
    public ArrayList<RelevantInfo_V3> relevantInfo
    public ArrayList<OfficeInfo> officeInfo
    public InternetService internetService
    public TvService tvService
    public WifiMeshService wifiMeshService
    public MobileService mobileService
    public ManagementInfo managementInfo
    public ArrayList<ServicesOfInterest> servicesOfInterest
    public String totalPackageInterest
    public String createBy
    public String source
    public String note
    public String maTinh
    public String number_branchs
    public ArrayList<CameraIndoors> cameraIndoors
    public ArrayList<CameraOutdoors> cameraOutdoors
}

public class Xoa_KhachHang_REQ {
    public String ma_so_thue
    public String ma_tinh
    public String so_dien_thoai
    public String loai_khach_hang
}