package vn.vnptit.sapi.oneapp.services

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import groovy.sql.OutParameter
import groovy.sql.Sql
import oracle.jdbc.OracleTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.converter.ResultSetToObjectv
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.model.output.lay_port_theo_tbid_res
import vn.vnptit.sapi.oneapp.util.Constant

import java.sql.ResultSet

@Component
public class BNMServicev {

    static OutParameter CURSOR_PARAMETER = { OracleTypes.CURSOR }
    @Autowired
    OracleManaged oracleManaged

    @Autowired
    private DiscoveryClient discoveryClient

    public static final String PATH_SPACE = "/bnm/"
    private String token = ""
    private String base_url = ""

    def init(String token) {
        this.token = token
        base_url = "http://tichhop-ex/bnm/"
    }


    public String ResetPort(long vphanvung_id, long p_hdtb_id, long p_thuebao_id) {

        try {
            String vuser_alt = ""
            String vpass_alt = ""


            ResultSetToObjectv resultSetToObject = new ResultSetToObjectv()
            List<lay_port_theo_tbid_res> data = []
            Sql.newInstance(oracleManaged.getDataSource("css")).withCloseable { sql ->
                sql.call("{? = call css.map_id(?,?,?)}", [Sql.VARCHAR, "USER_ALT", "css.tinh", "where TINH_ID = " + vphanvung_id]) { String us ->

                    vuser_alt = us
                }
                sql.call("{? = call css.map_id(?,?,?)}", [Sql.VARCHAR, "PASS_ALT", "css.tinh", "where TINH_ID = " + vphanvung_id]) { String ps ->

                    vpass_alt = ps
                }
                sql.call("{ call CSS.LAY_PORT_THEO_TBID(?,?,?,?) }", [vphanvung_id, p_hdtb_id, p_thuebao_id, CURSOR_PARAMETER]) { ResultSet rs ->
                    data = resultSetToObject.getDanhMuc(rs, lay_port_theo_tbid_res.class)
                }
            }
            if (data.size() > 0) {
                data.each {
                    String vip = it.ip
                    String vport_adsl = it.port_adsl
                    String vport_visa = it.port_visa
                    String vonu_id = it.onu_id
                    String vInput = vip + ":" + vport_visa
                    if (vonu_id != "")
                        vInput += "/" + vonu_id;
                    if (vip == "")
                    {
                        return  "Reset port thiết bị không thành công, chi tiết lỗi: Thuê bao chưa được cấp port"
                    }
                    return Tool_ResetOnePortVisa(vuser_alt,vpass_alt,vip,vport_visa,vonu_id)
                }
            } else {
                return "Reset port thiết bị không thành công, chi tiết lỗi: Thuê bao chưa được cấp port"
            }
            return "OK"

        } catch (Exception ex) {

            return "Reset port thiết bị không thành công!r\\n" + ex.getMessage()

        }

    }

    Object testPort(String linetestUser, String linetestPassword, String input, String mac) {
        try {
            String body = """{
                          "linetestUser": "$linetestUser",
                          "linetestPassword": "$linetestPassword",
                          "input": "$input",
                          "mac": "$mac"
                        }"""
            String url = base_url + "testPort"
            String response = HttpManaged.post(["url": url],
                    ["Content-Type" : "application/json",
                     "Authorization": token], body)
            def res = new JsonSlurper().parseText(response)
            return res
        } catch (Exception ex) {
            return ex.getMessage()
        }
    }

    Object testPortV2(String linetestUser, String linetestPassword, String input, String mac) {
        try {
            String body = """{
                          "linetestUser": "$linetestUser",
                          "linetestPassword": "$linetestPassword",
                          "input": "$input",
                          "mac": "$mac"
                        }"""
            String url = base_url + "testPortV2"
            String response = HttpManaged.post(["url": url],
                    ["Content-Type" : "application/json",
                     "Authorization": token], body)
            def res = new JsonSlurper().parseText(response)
            return res
        } catch (Exception ex) {
            return ex.getMessage()
        }
    }

    Object getPort_QuanlityV2(String linetestUser, String linetestPassword, String input, String att_gpon,
                              String att_adsl, String snr_adsl, String ip, String rack, String shelf, String slot, String port,
                              String onu_id, String server_dk) {
        try {
            String body = """{
                          "linetestUser": "$linetestUser",
                          "linetestPassword": "$linetestPassword",
                          "input": "$input",
                          "att_gpon": "$att_gpon",
                          "att_adsl": "$att_adsl",
                          "snr_adsl": "$snr_adsl",
                          "ip": "$ip",
                          "rack": "$rack",
                          "shelf": "$shelf",
                          "slot": "$slot",
                          "port": "$port",
                          "onu_id": "$onu_id",
                          "server_dk": "$server_dk"
                        }"""
            String url = base_url + "getPort_QuanlityV2"
            String response = HttpManaged.post(["url": url],
                    ["Content-Type" : "application/json",
                     "Authorization": token], body)
            def res = new JsonSlurper().parseText(response)
            return res
        } catch (Exception ex) {
            return ex.getMessage()
        }
    }

    Object toolResetOnePort(String linetestUser, String linetestPassword, String input) {
        try {
            String body = """{
                          "input": "$input",
                          "linetestPassword": "$linetestPassword",
                          "linetestUser": "$linetestUser"
                        }"""
            String url = base_url + "toolResetOnePort"
            String response = HttpManaged.post(["url": url],
                    ["Content-Type" : "application/json",
                     "Authorization": token], body)
            def res = new JsonSlurper().parseText(response)
            return res
        } catch (Exception ex) {
            return ex.getMessage()
        }
    } 

    Object toolSetOnePortProfile(String linetestUser, String linetestPassword, String input, String profile) {
        try {
            String body = """{
                          "input": "$input",
                          "linetestPassword": "$linetestPassword",
                          "linetestUser": "$linetestUser",
                          "profile": "$profile"
                        }"""
            String url = base_url + "toolSetOnePortProfile"
            String response = HttpManaged.post(["url": url],
                    ["Content-Type" : "application/json",
                     "Authorization": token], body)
            def res = new JsonSlurper().parseText(response)
            return res
        } catch (Exception ex) {
            return ex.getMessage()
        }
    }

    Object checkAccountOnline(String ipBras, String username) {
        try {
            String body = """{
                          "ipBras": "$ipBras",
                          "username": "$username"
                        }"""
            String url = base_url + "checkAccountOnline"
            String response = HttpManaged.post(["url": url],
                    ["Content-Type" : "application/json",
                     "Authorization": token], body)
            def res = new JsonSlurper().parseText(response)
            return res
        } catch (Exception ex) {
            return ex.getMessage()
        }
    }

    Object resetPassOnt(String userOnt, String passOnt, String vslid) {
        try {
            String body = """{
                          "passOnt": "$userOnt",
                          "userOnt": "$passOnt",
                          "vslid": "$vslid"
                        }"""
            String url = base_url + "resetPassOnt"
            String response = HttpManaged.post(["url": url],
                    ["Content-Type" : "application/json",
                     "Authorization": token], body)
            def res = new JsonSlurper().parseText(response)
            return res
        } catch (Exception ex) {
            return ex.getMessage()
        }
    }

    Object resetPassOntIgate040H(String userOnt, String passOnt, String vslid) {
        try {
            String body = """{
                          "passOnt": "$passOnt",
                          "userOnt": "$userOnt",
                          "vslid": "$vslid"
                        }"""
            String url = base_url + "resetPassOntIgate040H"
            String response = HttpManaged.post(["url": url],
                    ["Content-Type" : "application/json",
                     "Authorization": token], body)
            def res = new JsonSlurper().parseText(response)
            return res
        } catch (Exception ex) {
            return ex.getMessage()
        }
    }

    private static String Tool_ResetOnePortVisa(String linetestUser, String linetestPassword, String vIP, String vPort_Visa, String vOnu_ID) {
        try {
            String url = "http://tichhop-ex/bnm/toolResetPort"
            def body = [:]
            body["linetestUser"] = linetestUser
            body["linetestPassword"] = linetestPassword
            body["ip"] = vIP
            body["port"] = vPort_Visa
            body["onuID"] = vOnu_ID
            String result = HttpManaged.post(["url": url],
                    ["Content-Type": "application/json;charset=UTF-8"], new ObjectMapper().writeValueAsString(body))
            def res = new ObjectMapper().readValue(result, Map.class)
            if (res["errorCode"].toString() == "0") {
                return "Reset port thiết bị thành công"
            } else {
                return "Reset port thiết bị không thành công, chi tiết lỗi: " + res["faultString"]
            }
        } catch (Exception ex) {
            return ex.getMessage()
        }
    }

}
