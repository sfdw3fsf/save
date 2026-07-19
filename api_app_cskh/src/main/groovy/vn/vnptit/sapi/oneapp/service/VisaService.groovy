package vn.vnptit.sapi.oneapp.service

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.model.request.*
import vn.vnptit.sapi.oneapp.util.Constant

import java.text.SimpleDateFormat

@Component
public class VisaService {

    @Autowired
    private DiscoveryClient discoveryClient

    public static final String PATH_SPACE = "/visa-app/"
    public static final String APP_PATH_SPACE = "/visa-app/"
    public static final String COM_PATH_SPACE = "/visa-com/"

    private String token = ""
    private String bare_url = ""
    private String app_bare_url = ""
    private String com_bare_url = ""

    def init(String token) {
        this.token = token
        List<ServiceInstance> nodes = discoveryClient.getInstances(Constant.SERVICE)
        if (nodes == null || nodes.size() == 0){
            app_bare_url = "https://api-onebss-rb-dev.vnptit3.vn/tichhop/visa-app/"
            com_bare_url = "https://api-onebss-rb-dev.vnptit3.vn/tichhop/visa-com/"
        }else{
            ServiceInstance node = nodes[0]
            app_bare_url = "http${node.secure ? 's' : ''}://${node.host}:${node.port}" + APP_PATH_SPACE
            com_bare_url = "http${node.secure ? 's' : ''}://${node.host}:${node.port}" + COM_PATH_SPACE
        }
    }

    public String lookupRASAccount(LookUpRASAccountRequest request) {
        String url = app_bare_url + "lookupRASAccountJson"
        String body = """{"accountName": "$request.accountName", "login": {"userId": "$request.userId", "password": "$request.password"}}"""
        String resp = HttpManaged.post(["url": url],
                ["Content-Type" : "application/json",
                 "Authorization": token], body)
        return resp
    }

    public String modifyPasswordVNN(ModifyPasswordVNNRequest request) {
        String url = app_bare_url + "modifyPasswordVNN"
        String body = """{"accountModel": {"accountName": "$request.accountName", "accountPassword": "$request.accountPassword"}, "login": {"userId": "$request.userId", "password": "$request.password"}}"""
        String resp = HttpManaged.post(["url": url],
                ["Content-Type" : "application/json",
                 "Authorization": token], body)
        def res = new JsonSlurper().parseText(resp)
        if (res["errorCode"] == 0) {
            return "OK"
        } else {
            return res["faultString"]
        }
    }

    String removeVNN(String account, String lydohuy, String chuthich, String user, String pass){
        String url = app_bare_url + "removeVNN"
        String body = """{
                          "accountModel": {
                            "accountName": "$account"
                          },
                          "note": "$chuthich",
                          "reason": "$lydohuy"
                        }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String updateCustomer(String account, String activeDate, String firstName, String address, String phone, String email, String contract, String user, String pass, String matinh){
        String url = app_bare_url + "updateCustomer"
        String body = """{
                            "accountModel": {
                                "accountName": "$account"
                            },
                            "accountServiceModel": {
                                "firstName": "$firstName",
                                "lastName": "",
                                "address": "$address",
                                "phone": "$phone",
                                "city": ""
                            },
                            "contract": "$contract"
                        }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String doiCongMegavnn4(String account, String PORT, String VCI, String VPI, String SLOT, String BrasId, String DslamId, String user, String pass, String matinh){
        String url = app_bare_url + "changePortMegaVNN"
        String body = """{
                              "accountModel": {
                                "accountName": "$account"
                              },
                             "brasId": "$BrasId",
                              "dslamId": "$DslamId",
                              "port": "$PORT",
                              "rackId": "",
                              "selfId": "",
                              "slot": "$SLOT",
                              "systemId": "",
                              "vci": "$VCI",
                              "vpi": "$VPI"
                            }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String doiCongMegavnn7(String account, String PORT, String VCI, String VPI, String SLOT, String BrasId, String DslamId, String SYSTEMID, String RACKID, String SHELFID, String user, String pass, String matinh){
        String url = app_bare_url + "changePortMegaVNN"
        String body = """{
                              "accountModel": {
                                "accountName": "$account"
                              },
                             "brasId": "$BrasId",
                              "dslamId": "$DslamId",
                              "port": "$PORT",
                              "rackId": "$RACKID",
                              "selfId": "$SHELFID",
                              "slot": "$SLOT",
                              "systemId": "$SYSTEMID",
                              "vci": "$VCI",
                              "vpi": "$VPI"
                            }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String doiCongMegavnn9_ADSL(String account, String PORT, String VCI, String VPI, String SLOT, String BrasId, String DslamId, String SYSTEMID, String SELFID01, String SELFID02, String SELFID03, String SELFID04, String user, String pass, String matinh){
        String url = app_bare_url + "changePortMegaVNN"
        String body = """{
                              "accountModel": {
                                "accountName": "$account"
                              },
                             "brasId": "$BrasId",
                              "dslamId": "$DslamId",
                              "port": "$PORT",
                              "rackId": "",
                              "selfId": "$SELFID01",
                              "slot": "$SLOT",
                              "systemId": "$SYSTEMID",
                              "vci": "$VCI",
                              "vpi": "$VPI"
                            }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String huyDatChoMegaVNN(String account, String user, String pass, String matinh){
        String url = app_bare_url + "unReservedAccount"
        String body = """{
                          "accountName": "$account"
                        }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String registerMegaVnn4(String account, String password, String status, String CusName, String CusAdd, String phone, String PORT,
                            String VCI, String VPI, String SLOT, String SoHd, String BrasId, String DslamId, long dichvuId, int LoaiCuoc, String Ip,
                            int loaikh, String planId, String strBilling, String partnerCode, String user, String pass, String matinh){
        String url = com_bare_url + "registerMegaVNN"
        String body = """{
                              "accountModel": {
                                "accountName": "$account",
                                "activeDate": {
                                  "time": ""
                                },
                                "areaCode": "",
                                "partnerCode": "$partnerCode",
                                "status": $status,
                                "statusPre": 0,
                                "type": 0,
                                "useType": 0
                              },
                              "customerModel": {
                                "address": "$CusAdd",
                                "city": "",
                                "customerType": $loaikh,
                                "district": "",
                                "firstName": "$CusName",
                                "lastName": "",
                                "phone": "$phone",
                                "ward": ""
                              },
                              "accountServiceModel": {
                                "serviceId": $dichvuId,
                                "serviceName": "",
                                "serviceValue": "",
                                "tariffType": $LoaiCuoc
                              },
                              "brasId": "$BrasId",
                              "dslamId": "$DslamId",
                              "IPV4-WAN": "",
                              "IPV4": "",
                              "IPV4-AUTO": "",
                              "IP-NUMBER": "$Ip",
                              "GREEN-NET": "",
                              "VRF": "",
                              "CGNAT": "",
                              "port": "$PORT",
                              "SLOT": "$SLOT",
                              "vci": "$VCI",
                              "vpi": "$VPI"
                            }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String registerMegaVnn7(String account, String password, String status, String CusName, String CusAdd, String phone, String PORT,
                            String VCI, String VPI, String SLOT, String SoHd, String BrasId, String DslamId, String SYSTEMID, String RACKID, String SHELFID,
                            long dichvuId, int LoaiCuoc, String IP, int loaikh, String planId, String strBilling, String partnerCode, String user, String pass, String matinh){
        String url = com_bare_url + "registerMegaVNN"
        String body = """{
                              "accountModel": {
                                "accountName": "$account",
                                "activeDate": {
                                  "time": ""
                                },
                                "areaCode": "",
                                "partnerCode": "$partnerCode",
                                "status": $status,
                                "statusPre": 0,
                                "type": 0,
                                "useType": 0
                              },
                              "customerModel": {
                                "address": "$CusAdd",
                                "city": "",
                                "customerType": $loaikh,
                                "district": "",
                                "firstName": "$CusName",
                                "lastName": "",
                                "phone": "$phone",
                                "ward": ""
                              },
                              "accountServiceModel": {
                                "serviceId": $dichvuId,
                                "serviceName": "",
                                "serviceValue": "",
                                "tariffType": $LoaiCuoc
                              },
                              "brasId": "$BrasId",
                              "dslamId": "$DslamId",
                              "IPV4-WAN": "",
                              "IPV4": "",
                              "IPV4-AUTO": "",
                              "IP-NUMBER": "$IP",
                              "GREEN-NET": "",
                              "VRF": "",
                              "CGNAT": "",
                              "port": "$PORT",
                              "SLOT": "$SLOT",
                              "vci": "$VCI",
                              "vpi": "$VPI"
                            }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String registerMegaVnn9_Mega(String account, String password, String status, String CusName, String CusAdd, String phone,
                                 String PORT, String VCI, String VPI, String SLOT, String SoHd, String BrasId, String DslamId, String SYSTEMID,
                                 String SELFID01, String SELFID02, String SELFID03, String SELFID04, long dichvuId, int LoaiCuoc, String IP,
                                 int loaikh, String planId, String strBilling, String partnerCode, String user, String pass, String matinh){
        String url = com_bare_url + "registerMegaVNN"
        String body = """{
                              "accountModel": {
                                "accountName": "$account",
                                "activeDate": {
                                  "time": ""
                                },
                                "areaCode": "",
                                "partnerCode": "$partnerCode",
                                "status": $status,
                                "statusPre": 0,
                                "type": 0,
                                "useType": 0
                              },
                              "customerModel": {
                                "address": "$CusAdd",
                                "city": "",
                                "customerType": $loaikh,
                                "district": "",
                                "firstName": "$CusName",
                                "lastName": "",
                                "phone": "$phone",
                                "ward": ""
                              },
                              "accountServiceModel": {
                                "serviceId": $dichvuId,
                                "serviceName": "",
                                "serviceValue": "",
                                "tariffType": $LoaiCuoc
                              },
                              "brasId": "$BrasId",
                              "dslamId": "$DslamId",
                              "IPV4-WAN": "",
                              "IPV4": "",
                              "IPV4-AUTO": "",
                              "IP-NUMBER": "$IP",
                              "GREEN-NET": "",
                              "VRF": "",
                              "CGNAT": "",
                              "port": "$PORT",
                              "SLOT": "$SLOT",
                              "vci": "$VCI",
                              "vpi": "$VPI"
                            }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String registerMegaVnn9_FE(String account, String password, String status, String CusName, String CusAdd,
                               String phone, String PORT, String VLAND_ID, String AXCI, String SLOT, String SoHd, String BrasId,
                               String DslamId, String SYSTEMID, String SELFID01, String SELFID02, String SELFID03, String SELFID04,
                               long dichvuId, int LoaiCuoc, String IP, int loaikh, String planId, String strBilling, String partnerCode,
                               String user, String pass, String matinh) {
        String url = com_bare_url + "registerMegaVNN"
        String body = """{
                              "accountModel": {
                                "accountName": "$account",
                                "activeDate": {
                                  "time": ""
                                },
                                "areaCode": "",
                                "partnerCode": "$partnerCode",
                                "status": $status,
                                "statusPre": 0,
                                "type": 0,
                                "useType": 0
                              },
                              "customerModel": {
                                "address": "$CusAdd",
                                "city": "",
                                "customerType": $loaikh,
                                "district": "",
                                "firstName": "$CusName",
                                "lastName": "",
                                "phone": "$phone",
                                "ward": ""
                              },
                              "accountServiceModel": {
                                "serviceId": $dichvuId,
                                "serviceName": "",
                                "serviceValue": "",
                                "tariffType": $LoaiCuoc
                              },
                              "brasId": "$BrasId",
                              "dslamId": "$DslamId",
                              "IPV4-WAN": "",
                              "IPV4": "",
                              "IPV4-AUTO": "",
                              "IP-NUMBER": "$IP",
                              "GREEN-NET": "",
                              "VRF": "",
                              "CGNAT": "",
                              "port": "$PORT",
                              "SLOT": "$SLOT",
                              "vci": "",
                              "vpi": ""
                            }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String registerMegaVnn9_ADSL(String account, String password, String status, String CusName, String CusAdd, String phone, String PORT,
                                 String VCI, String VPI, String SLOT, String SoHd, String BrasId, String DslamId, String SYSTEMID,
                                 String SELFID01, String SELFID02, String SELFID03, String SELFID04, long dichvuId, int LoaiCuoc,
                                 String IP, int loaikh, String planId, String strBilling, String partnerCode, String user, String pass, String matinh) {
        String url = com_bare_url + "registerMegaVNN"
        String body = """{
                              "accountModel": {
                                "accountName": "$account",
                                "activeDate": {
                                  "time": ""
                                },
                                "areaCode": "",
                                "partnerCode": "$partnerCode",
                                "status": $status,
                                "statusPre": 0,
                                "type": 0,
                                "useType": 0
                              },
                              "customerModel": {
                                "address": "$CusAdd",
                                "city": "",
                                "customerType": $loaikh,
                                "district": "",
                                "firstName": "$CusName",
                                "lastName": "",
                                "phone": "$phone",
                                "ward": ""
                              },
                              "accountServiceModel": {
                                "serviceId": $dichvuId,
                                "serviceName": "",
                                "serviceValue": "",
                                "tariffType": $LoaiCuoc
                              },
                              "brasId": "$BrasId",
                              "dslamId": "$DslamId",
                              "IPV4-WAN": "",
                              "IPV4": "",
                              "IPV4-AUTO": "",
                              "IP-NUMBER": "$IP",
                              "GREEN-NET": "",
                              "VRF": "",
                              "CGNAT": "",
                              "port": "$PORT",
                              "SLOT": "$SLOT",
                              "vci": "$VCI",
                              "vpi": "$VPI"
                            }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String changeAcceptanceTime(String account, Date activeDate, String note, String user, String pass, String matinh){
        //Code thực thi nếu có phải comment khi upcode cho đến khi được chạy thật
        return "OK"
    }

    String getFrameIP2(String account, String user, String pass, String matinh){
        String url = app_bare_url + "searchVNNAccount"
        String body = """{
                            "accountModel": {
                                "accountName": "$account"
                            }
                        }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String changePartner(String account, String partnerCode, String note, String user, String pass, String matinh){
        String url = com_bare_url + "changePartner"
        String body = """{
                            "accountName": "$account",
                            "partnerCode": "$partnerCode",
                            "note": "$note"
                        }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String changeCustomerType(String account, Date activeDate, int cusType, String user, String pass, String matinh){
        String date = new SimpleDateFormat("dd/MM/yyyy").format(activeDate)
        String url = app_bare_url + "changeCustomerType"
        String body = """{
                            "customerModel": {
                            "accountName": "$account",
                            "customerType": $cusType
                            },
                                "note": "Thay doi loai khach hang qua WS ngay : $date"
                            }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String thaydoiGoiCuoc(String account, long tocdo_id, String ip, int tucthi, boolean autoIP, boolean giuIP, String user, String pass, String matinh){
        String url = com_bare_url + "modifyServiceTypeMegaVNN1"
        String body = """{
                            "accountModel": {
                                "accountName": "$account"
                            },
                            "accountServiceModel": {
                                "serviceId": "$tocdo_id"        
                            },
                            "ip": "$ip",
                            "autoIpFlag": $autoIP,
                            "oldIp": $giuIP,
                            "ipv6": "",
                            "autoIpV6Flag": true
                        }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String modifyPasswordVNN(String account, String passNew, String user, String pass, String matinh){
        String url = app_bare_url + "modifyPasswordVNN"
        String body = """{
                              "accountModel": {
                                "accountName": "$account",
                                    "accountPassword": "$passNew"
                            }
                        }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String changeBillingInfo(String account, String billingInfo, String note, String user, String pass, String matinh){
        // billingInfo có cấu trúc: cuoctb:value ;cuockh:value ;cuoctg:value ;tlgiam:value ;ngiam:value ;thgiam:value ;mienip:value ;
        String url = app_bare_url + "changeBillingInfo"
        String body = """{
                              "accountModel": {
                                "accountName": "$account"
                              },
                              "billingInfo": "$billingInfo",
                              "note": "$note"
                            }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String thayDoiHTTT(String account, int loaicuoc_id, String tucthi, String user, String pass, String matinh){
        String url = app_bare_url + "modifyTariffType"
        String body = """{
                            "accountModel": {
                                "accountName": "$account"
                            },
                            "accountServiceModel": {
                                "tariffType": "0"
                            },
                            "effectType": "$tucthi"
                        }
                        """
        try {
            String resp = HttpManaged.post(["url": url], ["Content-Type": "application/json", "Authorization": token], body)
            def result = new JsonSlurper().parseText(resp)
            if (result.errorCode == 0) {
                return "OK"
            } else {
                return result.faultString == null ? result.message : result.faultString
            }
        } catch(Exception ex) {
            return ex.message
        }
    }

    String updateStatusVNN(UpdateStatusVNNRequest request) {
        if (request == null) {
            return null
        }
        String url = app_bare_url + "updateStatusVNN"
        String body= new JsonBuilder(request)
        String resp = HttpManaged.post(["url": url],
                ["Content-Type": "application/json",
                 "Authorization": token], body)
        def res = new JsonSlurper().parseText(resp)
        if (res["errorCode"] == 0) {
            return "OK"
        } else {
            return res["faultString"]
        }
    }

    String removeVNN(RemoveVNNRequest request) {
        if (request == null) {
            return null
        }
        String url = app_bare_url + "removeVNN"
        String body= new JsonBuilder(request)
        String resp = HttpManaged.post(["url": url],
                ["Content-Type": "application/json",
                 "Authorization": token], body)
        def res = new JsonSlurper().parseText(resp)
        if (res["errorCode"] == 0) {
            return "OK"
        } else {
            return res["faultString"]
        }
    }
}
