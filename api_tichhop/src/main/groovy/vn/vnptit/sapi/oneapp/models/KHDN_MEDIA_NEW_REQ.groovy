package vn.vnptit.sapi.oneapp.models

public class NewAddressInfo {
    public int cityId;
    public String cityName;
    public int districtId;
    public String districtName;
    public int wardId;
    public String wardName;
    public String homeNumber;
    public int areaId;
    public String areaName;
    public int streetId;
    public String streetName;
    public int hamletId;
    public String hamletName;
    public String characteristic;
    public String additionalAddress;
    public String acreage;
    public String infrastructure;
}

public class NewEnterpriseInfo {
    public String customerName;
    public String customerId;
    public String taxId;
    public String customerDepartment;
    public int activity;
    public int career;
    public Long scale;
    public Long revenue;
    public String workingArea;
    public String corporateDate;
    public String phoneNumber;
    public String website;
    public String serviceDemand;
    public String activityName;
    public String careerName;
}

public class NewOfficerDecideInfo {
    public String officerName;
    public String dateOfBirth;
    public String gender;
    public String phoneNumber;
    public String email;
    public String position;
}

public class NewRelevantInfo {
    public String relevantName;
    public String department;
    public String dateOfBirth;
    public String gender;
    public String phoneNumber;
    public String email;
}

public class ProviderInfo {
    public String otherProviderService;
    public String otherProviderName;
    public Long otherProviderPrice;
    public String otherProviderExpDate;
}

public class KHDN_ThuCong_New_REQ {
    public NewEnterpriseInfo enterpriseInfo;
    public NewAddressInfo addressInfo;
    public ArrayList<NewRelevantInfo> relevantInfo;
    public ArrayList<NewOfficerDecideInfo> officerDecideInfo;
    public ArrayList<ProviderInfo> providerInfo;
    public String createTime;
    public String createBy;
    public String updateTime;
    public String source;
    public String note;
    public String maTinh;
    public String status;
}

public class Xem_ChiTiet_KhachHang_REQ {
    String ma_so_thue
    String ma_tinh
    String khachhang_id
}

public class Import_Gan_AM_KH_REQ {
    public String taxId;
    public String cityCode;
    public List<String> hrmIds;
}



public class DanhSach_KhachHang_TiepCan_B2A_REQ {
//    public String ma_tinh;
}

public class get_b2a_khdn_REQ {
    String planCode
    List<b2aList> b2aList
}

public class b2aList {
    String ma_tinh
    String mst
}

public class Them_ChuongTrinh_BanHang_REQ {
    String plan_code
    String plan_name
    int plan_type
    int campaign_type
    String pm_name
    String start_date
    String end_date
    String created_by
}

public class CapNhat_ChuongTrinh_BanHang_REQ {
    String plan_code
    String plan_name
    int plan_type
    int campaign_type
    String pm_name
    String start_date
    String end_date
    String updated_by
}

public class CTBHInfo {
    public String taxId
    public String cityCode
    public String am
    public String planCode
}

public class Gan_ChuongTrinh_BanHang_REQ {
    public ArrayList<CTBHInfo> assignCTBHs
    public String createdBy
}

public class CustomerCTBHInfo {
    public String plan_code
    public String customer_id
    public String customer_name
    public String additional_address
    public String ward_name
    public String representative_name
    public String representative_phone
    public String tax_id
    public String city_code
    public String am
    public String email
    public int priority
}

public class Import_KhachHang_ChuongTrinh_BanHang_REQ {
    public ArrayList<CustomerCTBHInfo> items
    public String create_by
}

public class CapNhat_MaSoThue_KhachHang_REQ {
    public String mst_cu
    public String ma_tinh
    public String mst_moi
    public String createdBy
}




