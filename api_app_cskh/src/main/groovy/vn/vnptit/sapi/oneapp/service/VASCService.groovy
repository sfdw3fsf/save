package vn.vnptit.sapi.oneapp.service

import groovy.json.JsonSlurper
import groovy.sql.OutParameter
import groovy.sql.Sql
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.util.Constant

import java.sql.ResultSet

@Component
public class VASCService {

    @Autowired
    private DiscoveryClient discoveryClient

    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    @Autowired
    OracleManaged oracleManaged

    public static final String PATH_SPACE = "/vasc/"
    private String token = ""
    private String base_url = ""

    String user_vasc, pass_vasc
    String diemgiaodich_vasc
    String phanvung_id
    String nhanvien_id
    String AreaCode

    def init(String token, String dataSrc, String phanVungId, String nhanVienId) {
        init(token)
        init(dataSrc, phanVungId, nhanVienId)
    }

    def init(String token) {
        this.token = token
        List<ServiceInstance> nodes = discoveryClient.getInstances(Constant.SERVICE)
        if (nodes == null || nodes.size() == 0) {
            base_url = "http://tichhop/vasc/"
        } else {
            ServiceInstance node = nodes[0]
            base_url = "http${node.secure ? 's' : ''}://${node.host}:${node.port}" + PATH_SPACE
        }
    }

    def init(String dataSrc, String phanVungId, String nhanVienId) {
        // Lấy thông tin đăng nhập Vasc
        if (phanVungId == phanvung_id && nhanVienId == nhanvien_id && user_vasc != null && pass_vasc != null) {
            return null
        }
        String sqlString = "{ call admin.sp_lay_thongtin_nguoidung(?,?,?) }"
        Sql.newInstance(oracleManaged.getDataSource(dataSrc)).withCloseable { sql ->
            sql.call(sqlString, [Long.parseLong(phanVungId), Long.parseLong(nhanVienId), CURSOR_PARAMETER]) { ResultSet rs ->
                if (rs != null && rs.next()) {
                    user_vasc = rs.getString("USER_VASC")
                    pass_vasc = rs.getString("PASS_VASC")
                    diemgiaodich_vasc = rs.getString("DIEMGIAODICH_VASC")
                    AreaCode = rs.getString("TENTAT")
                }
            }
        }
        if (AreaCode.toUpperCase() == "DNO")
            AreaCode = "DKG";
        if (AreaCode.toUpperCase() == "BKN")
            AreaCode = "BCN";
        if (AreaCode.toUpperCase() == "DLC")
            AreaCode = "DLK";
        phanvung_id = phanVungId
        nhanvien_id = nhanVienId
    }

    public String getSubscriberInfo(String iptvaccount) {
        String url = base_url + "getSubscriberInfo"
        String body = """{"iptvaccount": "$iptvaccount"}"""
        String resp = HttpManaged.post(["url": url],
                ["Content-Type" : "application/json",
                 "Authorization": token], body)
        return resp
    }

    public Object changeSystem(String reason, String iptvaccount, String sysFlg) {
        String url = base_url + "changeSystem"
        String body = """{"objRequest": {"sysFlg": "$sysFlg", "reason": "$reason", "iptvaccount": "$iptvaccount"}}"""
        String resp = HttpManaged.post(["url": url],
                ["Content-Type" : "application/json",
                 "Authorization": token], body)
        def res = new JsonSlurper().parseText(resp)
        return res
    }

    String changeSubscriber(String name, String birthday, String sex, String firstAdd,
                            String secondAdd, String idNo, String telNo, String cellNo, String email, String ym,
                            String website, String faxNo, String IPTVAccount) {
        String url = base_url + "changeSubscriber"
        String body = """{
                          "areaCode": "$AreaCode",
                          "birthday": "$birthday",
                          "cellNo": "$cellNo",
                          "email": "$email",
                          "faxNo": "$faxNo",
                          "firstAdd": "$firstAdd",
                          "idno": "$idNo",
                          "iptvaccount": "$IPTVAccount",
                          "name": "$name",
                          "secondAdd": "$secondAdd",
                          "sex": "$sex",
                          "telNo": "$telNo",
                          "website": "$website",
                          "ym": "$ym"
                        }
                        """

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

    String changeSubscriber_v2(String name, String birthday, String sex, String firstAdd,
                               String secondAdd, String idNo, String telNo, String cellNo, String email, String ym,
                               String website, String faxNo, String IPTVAccount, String tinh_id, String phuong_id, String pho_id){
        String url = base_url + "changeSubscriber"
        String body = """{
                          "areaCode": "$AreaCode",
                          "birthday": "$birthday",
                          "cellNo": "$cellNo",
                          "email": "$email",
                          "faxNo": "$faxNo",
                          "firstAdd": "$firstAdd",
                          "idno": "$idNo",
                          "iptvaccount": "$IPTVAccount",
                          "name": "$name",
                          "secondAdd": "$secondAdd",
                          "sex": "$sex",
                          "telNo": "$telNo",
                          "website": "$website",
                          "ym": "$ym",
                          "tinh_id": "$tinh_id",
                          "phuong_id": "$phuong_id",
                          "pho_id": "$pho_id"
                        }
                        """

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

    public String suspendSubscriberV2(String iptvaccount, int reasonID) {
        String url = base_url + "suspendSubscriberV2"
        String body = """
                    {
                      "iptvaccount": "$iptvaccount",
                      "reasonID": $reasonID
                    }
                    """
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

    String cust_Trial_UpdateInfo(String username, String method_FLG, String nomonth, String trial_CODE, String trial_GROUP, String trial_NOMONTH) {
        String url = base_url + "cust_Trial_UpdateInfo"
        String body = """{
                          "custDTO": {
                            "method_FLG": "$method_FLG",
                            "nomonth": "$nomonth",
                            "trial_CODE": "$trial_CODE",
                            "trial_GROUP": "$trial_GROUP",
                            "trial_NOMONTH": $trial_NOMONTH,
                            "username": "$username"
                          }
                        }
                        """
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
}
