package vn.vnptit.sapi.oneapp.util

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.springframework.web.context.request.NativeWebRequest

import javax.servlet.http.HttpServletRequest

@CompileStatic
class GenericApi {
    public static final String REQUEST_ID = "REQUEST_ID"
    public static final String CSS_SCH = "css_sch"
    public static final String MA_NV = "ma_nv"
    public static final String WALLET_TOKEN = "WalletToken"
    public static final String EMPLOYEE_ID = "id_nhanvien_dhsx"
    public static final String NGUOIDUNG_ID = "nguoidung_id"
    public static final String GROUP_ID = "groupId"
    public static final String SELLING_DEPARTMENT_ID = "sellingDepartmentId";
    public static final String USER_CCBS = "ma_nhanvien_ccbs"
    public static final String MA_TINH = "ma_tinh"
    public static final String ID_TINHTHANH = "id_tinhthanh"
    public static final String ID_DONVI = "id_donvi"
    public static final String IP = "device_ip"
    public static final String MACHINE = "device_name"
    public static final String DEVICE_NAME = "device_name"
    public static final String APP_NAME = "app_version"
    public static final String DATASRC_CCBS = "ccbs"
    public static final String DATASRC_VNP = "vnp"
    public static final String DATASRC_NUMSTORE = "numstore"
    public static final String DATASRC_CSS = "css"
    public static final String QLSP_SYSTEM_ID = "1";
    public static final String ID_NHANVIEN_DHSX = "id_nhanvien_dhsx"
    public static final String DEVICE_ID = "device_id"
    static final String ID_NGUOIDUNG = "id_nguoidung"
    static final String IDENTIFY_CODE = "identify"
    static final String USER_WALLET = "user_vi"
    static final String WALLET_TOKEN = "WalletToken"
    static final String EMAIL = "email"
    static final String TOKEN = "token"
    public static final String APP_ID = "app_id"
    //numstore
    static final String QLSP_SYSTEM_ID = "1";
    public static final String USERNAME = "user_name"
    public static final String Email = "email"

    static ThreadLocal<ObjectMapper> objectMappers = ThreadLocal.withInitial { new ObjectMapper() }
    private String token
    private String appSec

    GenericApi(String token, String appSec) {
        this.token = token
        this.appSec = appSec
    }

    GenericApi() {}

    Map<String, Object> getSessionData(NativeWebRequest request) {
        Map<String, Object> r = [:], r2 = [:]
        try {
            String secrect = request.getHeader("App-secret") ?: appSec

            String sessSecrect
            if (secrect != null && !secrect.isEmpty()) {
                sessSecrect = new String(secrect.replace('-', '+').replace('_', '/').split("\\.")[0].decodeBase64())
            }
            r2 = (Map<String, Object>) new JsonSlurper().parseText(sessSecrect)
            String app_id = ""
            if (r2.containsKey("app_id")) {
                app_id = r2.get('app_id').toString().trim()
            }
            r.put(APP_ID, app_id)
        } catch (e) {

        }
        try {
            String token = request.getHeader("Authorization") ?: token
            r.put(TOKEN, token)
            if (token.startsWith("Bearer ")) token = token.substring(7)
            String sessData
            if (token != null && token != null) {
                sessData = new String(token.replace('-', '+').replace('_', '/').split("\\.")[1].decodeBase64())
            }

            r = r2 + (Map<String, Object>) new JsonSlurper().parseText(sessData)
            r.put(IP, getIp(request))

            String ccsSch = null
            if (r.containsKey("css_sch"))
                ccsSch = r.get('css_sch').toString().toLowerCase().take(7)
            else
                ccsSch = r.get('CSS_SCH').toString().toLowerCase().take(7)

            r.put("CSS_SCH", ccsSch)
            r.put("css_sch", ccsSch)

            String maNv = null

            if (r.containsKey("ma_nv"))
                maNv = r.get('ma_nv').toString().trim()
            else
                maNv = r.get('MA_NV').toString().trim()

            r.put("ma_nv", maNv)
            r.put("MA_NV", maNv)

            String id_nhanvien_dhsx = null

            if (r.containsKey("id_nhanvien_dhsx"))
                id_nhanvien_dhsx = r.get('id_nhanvien_dhsx').toString().trim()
            else
                id_nhanvien_dhsx = r.get('ID_NHANVIEN_DHSX').toString().trim()

            r.put("id_nhanvien_dhsx", id_nhanvien_dhsx)
            r.put("ID_NHANVIEN_DHSX", id_nhanvien_dhsx)

            String groupId
            if (r.containsKey("groupId")) {
                groupId = r.get('groupId').toString().trim()
            } else {
                groupId = r.get('groupId'.toUpperCase()).toString().trim()
            }
            r.put("groupId", groupId)
            r.put("groupId".toUpperCase(), groupId)

            String sellingDepartmentId
            if (r.containsKey("sellingDepartmentId")) {
                sellingDepartmentId = r.get('sellingDepartmentId').toString().trim()
            } else {
                sellingDepartmentId = r.get('sellingDepartmentId'.toUpperCase()).toString().trim()
            }
            r.put("sellingDepartmentId", sellingDepartmentId)
            r.put("sellingDepartmentId".toUpperCase(), sellingDepartmentId)

            String user_ccbs = null

            if (r.containsKey("ma_nhanvien_ccbs"))
                user_ccbs = r.get('ma_nhanvien_ccbs').toString().trim()
            else
                user_ccbs = r.get('MA_NHANVIEN_CCBS').toString().trim()

            r.put("ma_nhanvien_ccbs", user_ccbs)
            r.put("ma_nhanvien_ccbs", user_ccbs)

            String ma_tinh = null

            if (r.containsKey("ma_tinh"))
                ma_tinh = r.get('ma_tinh').toString().trim().toUpperCase()
            else
                ma_tinh = r.get('MA_TINH').toString().trim().toUpperCase()

            r.put("ma_tinh", ma_tinh)
            r.put("MA_TINH", ma_tinh)

            String idTinhThanh
            if (r.containsKey(ID_TINHTHANH)) {
                idTinhThanh = r.get(ID_TINHTHANH).toString().trim()
            } else {
                idTinhThanh = r.get(ID_TINHTHANH.toUpperCase()).toString().trim()
            }
            r.put(ID_TINHTHANH, idTinhThanh)
            r.put(ID_TINHTHANH.toUpperCase(), idTinhThanh)

            String idDonVi
            if (r.containsKey(ID_DONVI)) {
                idDonVi = r.get(ID_DONVI).toString().trim()
            } else {
                idDonVi = r.get(ID_DONVI.toUpperCase()).toString().trim()
            }
            r.put(ID_DONVI, idDonVi)
            r.put(ID_DONVI.toUpperCase(), idDonVi)

            String username
            if (r.containsKey(USERNAME)) {
                username = r.get(USERNAME).toString().trim()
            } else {
                username = r.get(USERNAME.toUpperCase()).toString().trim()
            }
            r.put(USERNAME, username)
            r.put(USERNAME.toUpperCase(), username)

            String email
            if (r.containsKey(EMAIL)) {
                email = r.get(EMAIL).toString().trim()
            } else {
                email = r.get(EMAIL.toUpperCase()).toString().trim()
            }
            r.put(EMAIL, email)
            r.put(EMAIL.toUpperCase(), email)

        } catch (Exception) {
            throw new Exception("Token is not valid")
        }

        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        r.put(REQUEST_ID, uuidAsString)

        r
    }

    Map<String, Object> getSessionData(HttpServletRequest request) {
        Map<String, Object> r = [:], r2 = [:]

        try {
            String token = request.getHeader("Authorization") ?: token
            String fullToken = token
            if (token.startsWith("Bearer ")) token = token.substring(7)
            String sessData
            if (token != null && token != null) {
                sessData = new String(token.replace('-', '+').replace('_', '/').split("\\.")[1].decodeBase64())
            }

            r = r2 + (Map<String, Object>) new JsonSlurper().parseText(sessData)
            r.put(TOKEN, token)
            r.put(IP, getIp(request))

            String ccsSch = null
            if (r.containsKey("css_sch"))
                ccsSch = r.get('css_sch').toString().toLowerCase().take(7)
            else
                ccsSch = r.get('CSS_SCH').toString().toLowerCase().take(7)

            r.put("CSS_SCH", ccsSch)
            r.put("css_sch", ccsSch)

            String maNv = null

            if (r.containsKey("ma_nv"))
                maNv = r.get('ma_nv').toString().trim()
            else
                maNv = r.get('MA_NV').toString().trim()

            r.put("ma_nv", maNv)
            r.put("MA_NV", maNv)

            String id_nhanvien_dhsx = null

            if (r.containsKey("id_nhanvien_dhsx"))
                id_nhanvien_dhsx = r.get('id_nhanvien_dhsx').toString().trim()
            else
                id_nhanvien_dhsx = r.get('ID_NHANVIEN_DHSX').toString().trim()

            r.put("id_nhanvien_dhsx", id_nhanvien_dhsx)
            r.put("ID_NHANVIEN_DHSX", id_nhanvien_dhsx)

            String user_ccbs = null

            if (r.containsKey("ma_nhanvien_ccbs"))
                user_ccbs = r.get('ma_nhanvien_ccbs').toString().trim()
            else
                user_ccbs = r.get('MA_NHANVIEN_CCBS').toString().trim()

            r.put("ma_nhanvien_ccbs", user_ccbs)
            r.put("ma_nhanvien_ccbs", user_ccbs)

            String ma_tinh = null

            if (r.containsKey("ma_tinh"))
                ma_tinh = r.get('ma_tinh').toString().trim().toUpperCase()
            else
                ma_tinh = r.get('MA_TINH').toString().trim().toUpperCase()

            r.put("ma_tinh", ma_tinh)
            r.put("MA_TINH", ma_tinh)

            String idTinhThanh
            if (r.containsKey(ID_TINHTHANH)) {
                idTinhThanh = r.get(ID_TINHTHANH).toString().trim()
            } else {
                idTinhThanh = r.get(ID_TINHTHANH.toUpperCase()).toString().trim()
            }
            r.put(ID_TINHTHANH, idTinhThanh)
            r.put(ID_TINHTHANH.toUpperCase(), idTinhThanh)

            String idDonVi
            if (r.containsKey(ID_DONVI)) {
                idDonVi = r.get(ID_DONVI).toString().trim()
            } else {
                idDonVi = r.get(ID_DONVI.toUpperCase()).toString().trim()
            }
            r.put(ID_DONVI, idDonVi)
            r.put(ID_DONVI.toUpperCase(), idDonVi)

            String username
            if (r.containsKey(USERNAME)) {
                username = r.get(USERNAME).toString().trim()
            } else {
                username = r.get(USERNAME.toUpperCase()).toString().trim()
            }
            r.put(USERNAME, username)
            r.put(USERNAME.toUpperCase(), username)

            String idNguoiDung
            if (r.containsKey("id_nguoidung")) {
                idNguoiDung = r.get('id_nguoidung').toString().trim()
            } else {
                idNguoiDung = r.get('id_nguoidung'.toUpperCase()).toString().trim()
            }
            r.put("id_nguoidung", idNguoiDung)
            r.put("id_nguoidung".toUpperCase(), idNguoiDung)

            String identify
            if (r.containsKey("identify")) {
                identify = r.get('identify').toString().trim()
            } else {
                identify = r.get('identify'.toUpperCase()).toString().trim()
            }
            r.put("identify", identify)
            r.put("identify".toUpperCase(), identify)

            String groupId
            if (r.containsKey("groupId")) {
                groupId = r.get('groupId').toString().trim()
            } else {
                groupId = r.get('groupId'.toUpperCase()).toString().trim()
            }
            r.put("groupId", groupId)
            r.put("groupId".toUpperCase(), groupId)

            String sellingDepartmentId
            if (r.containsKey("sellingDepartmentId")) {
                sellingDepartmentId = r.get('sellingDepartmentId').toString().trim()
            } else {
                sellingDepartmentId = r.get('sellingDepartmentId'.toUpperCase()).toString().trim()
            }
            r.put("sellingDepartmentId", sellingDepartmentId)
            r.put("sellingDepartmentId".toUpperCase(), sellingDepartmentId)

            try {
                String walletToken = request.getHeader("WalletToken")
                r.put(WALLET_TOKEN, walletToken)
                r.put(WALLET_TOKEN.toUpperCase(), walletToken)

                String userVi
                if (r.containsKey(USER_WALLET)) {
                    userVi = r.get(USER_WALLET).toString().trim()
                } else {
                    userVi = r.get(USER_WALLET.toUpperCase()).toString().trim()
                }
                r.put(USER_WALLET, userVi)
                r.put(USER_WALLET.toUpperCase(), userVi)
            } catch (Exception ex) {
                ex.printStackTrace()
            }

        } catch (Exception) {
            throw new Exception("Token is not valid")
        }

        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        r.put(REQUEST_ID, uuidAsString)

        r
    }

    static String f0(String msisdn_) {
        String msisdn = msisdn_.replace("+", "")
        if (msisdn.startsWith("84") && msisdn.length() == 11) {
            return "0" + msisdn.substring(2)
        } else if ((msisdn.startsWith("9") || msisdn.startsWith("8")) && msisdn.length() == 9) {
            return "0" + msisdn
        }
        return msisdn
    }

    static String f84(String msisdn) {
        if (msisdn.startsWith("0") && msisdn.length() == 10) {
            return "84" + msisdn.substring(1);
        } else {
            if ((msisdn.startsWith("9") || msisdn.startsWith("8")) && msisdn.length() == 9) {
                return "84" + msisdn;
            }
        }
        return msisdn;
    }

    Map<String, String> getRequiredHeader(NativeWebRequest request) {
        ["Authorization": request.getHeader("Authorization"),
         "App-secret"   : request.getHeader("App-secret"),
         "Content-Type" : request.getHeader("Content-Type")
        ]

    }

    static String getIp(NativeWebRequest request) {
        String ip = request.getHeader("X-CLIENT-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            HttpServletRequest nrequest = (HttpServletRequest) request.getNativeRequest()
            ip = nrequest.getRemoteAddr()
        }
        return ip;
    }

    static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-CLIENT-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_FORWARDED");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_VIA");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr()
        }
        return ip;
    }
}
