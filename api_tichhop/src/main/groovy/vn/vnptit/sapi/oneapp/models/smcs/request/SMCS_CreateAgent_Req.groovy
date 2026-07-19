package vn.vnptit.sapi.oneapp.models.smcs.request

class SMCS_CreateAgent_Req {
    String agentCode
    String agentName
    String contactName
    String hrmCode
    String contactPhone
    String tin
    String identificationType
    String identificationNo
    String type
    String agentType
    String partner
    String cityId
    String stateId
    String countyId
    String address
    String careType
    String longitude
    String latitude
    String imgPath
    String vasSales
    String isRegInfo
    String maDvqhns
    String soCccd
    String province2025
    String county2025
    String idCreateReq
    String birthday
    String email
}

class SMCS_UpdateAgent_Req {
    String agentId
    String agentName
    String contactName
    String hrmCode
    String contactPhone
    String tin
    String identificationType
    String identificationNo
    String cityId
    String stateId
    String countyId
    String address
    String careType
    String longitude
    String latitude
    String imgPath
    String vasSales
    String isRegInfo
    String province2025
    String county2025
    String idCreateReq
    String birthday
    String email
}

class SMCS_GetListAgentCareRequest_Req {
    String service
    String staffCode
    String status
    String fromDate
    String toDate
}

class SMCS_UpdateAgentCareRequest_Req {
    String service
    String requestId
    String timeCSDB
    String latitude
    String longitude
    String careTypeDone
    String image
    String anhBienVay6
    String anhBienDoc6
    String anhBienTreo6
    String anhBienDung6
    String anhBienNgang6
    String anhBienVay9
    String anhBienDoc9
    String anhBienTreo9
    String anhBienDung9
    String anhBienNgang9

    String anh_bien_vay_6 = ""
    String anh_bien_ngang_6 = ""
    String anh_bien_doc_6 = ""
    String anh_bien_treo_6 = ""
    String anh_bien_dung_6 = ""
    String anh_bien_vay_9 = ""
    String anh_bien_ngang_9 = ""
    String anh_bien_doc_9 = ""
    String anh_bien_treo_9 = ""
    String anh_bien_dung_9 = ""
    String nam_bien_vay_9 = ""
    String nam_bien_ngang_9 = ""
    String nam_bien_doc_9 = ""
    String nam_bien_treo_9 = ""
    String nam_bien_dung_9 = ""
}