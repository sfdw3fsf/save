package vn.vnptit.sapi.oneapp.resources

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic

import javax.servlet.http.HttpServletRequest
import java.security.MessageDigest
import java.text.DateFormat
import java.text.SimpleDateFormat

@CompileStatic
class GenericApi {
    static final String CSS_SCH = "css_sch"
    static final String MA_NV = "ma_nv"
    static final String WALLET_TOKEN = "WalletToken"
    static final String GROUP_ID = "groupId"
    static final String SELLING_DEPARTMENT_ID = "sellingDepartmentId"
    static final String ID_NHANVIEN_DHSX = "id_nhanvien_dhsx"
    static final String ID_TINHTHANH = "id_tinhthanh"
    static final String ID_DONVI = "id_donvi"
    static final String MA_TINH = "ma_tinh"
    static final String DEVICE_ID = "device_id"
    static final String DEVICE_NAME = "device_name"
    static final String DEVICE_IP = "device_ip"
    static final String USERNAME = "user_name"
    static final String TOKEN = "token"
    static final String NGUOIDUNG_ID = "nguoidung_id"
    static final String MA_NV_CCBS = "ma_nhanvien_ccbs"
    static final String IDENTIFY = "identify"

    Map<String, Object> getSessionData(HttpServletRequest request) {
        Map<String, Object> r, r2
        try {
            String token = request.getHeader("Authorization")
            String fullToken = token
            if (token.startsWith("Bearer ")) token = token.substring(7)
            String sessionData = new String(token.replace('-', '+').replace('_', '/').split("\\.")[1].decodeBase64())

            r = (Map<String, Object>) new JsonSlurper().parseText(sessionData)
            r.put(TOKEN, fullToken)
            String ccsSch
            if (r.containsKey("css_sch")) {
                ccsSch = r.get('css_sch').toString().toLowerCase().take(7)
            } else {
                ccsSch = r.get('CSS_SCH').toString().toLowerCase().take(7)
            }
            r.put("CSS_SCH", ccsSch)
            r.put("css_sch", ccsSch)
            //default DB là ccs
            r.put("css_sch", "css")
            String maNv
            if (r.containsKey("ma_nv")) {
                maNv = r.get('ma_nv').toString().trim()
            } else {
                maNv = r.get('MA_NV').toString().trim()
            }
            r.put("ma_nv", maNv)
            r.put("MA_NV", maNv)

            String maNvCCBS
            if (r.containsKey("ma_nhanvien_ccbs")) {
                maNvCCBS = r.get('ma_nhanvien_ccbs').toString().trim()
            } else {
                maNvCCBS = r.get('ma_nhanvien_ccbs'.toUpperCase()).toString().trim()
            }
            r.put("ma_nhanvien_ccbs", maNvCCBS)
            r.put("ma_nhanvien_ccbs".toUpperCase(), maNvCCBS)

            String username
            if (r.containsKey(USERNAME)) {
                username = r.get(USERNAME).toString().trim()
            } else {
                username = r.get(USERNAME.toUpperCase()).toString().trim()
            }
            r.put(USERNAME, username)
            r.put(USERNAME.toUpperCase(), username)

            String employeeId
            if (r.containsKey("id_nhanvien_dhsx")) {
                employeeId = r.get('id_nhanvien_dhsx').toString().trim()
            } else {
                employeeId = r.get('id_nhanvien_dhsx'.toUpperCase()).toString().trim()
            }
            r.put("id_nhanvien_dhsx", employeeId)
            r.put("id_nhanvien_dhsx".toUpperCase(), employeeId)

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

            String walletToken = request.getHeader("WalletToken")
            r.put("WalletToken", walletToken)
            r.put("WalletToken".toUpperCase(), walletToken)

            String idTinhThanh
            if (r.containsKey("id_tinhthanh")) {
                idTinhThanh = r.get('id_tinhthanh').toString().trim()
            } else {
                idTinhThanh = r.get('id_tinhthanh'.toUpperCase()).toString().trim()
            }
            r.put("id_tinhthanh", idTinhThanh)
            r.put("id_tinhthanh".toUpperCase(), idTinhThanh)

            String idNguoiDung
            if (r.containsKey("id_nguoidung")) {
                idNguoiDung = r.get('id_nguoidung').toString().trim()
            } else {
                idNguoiDung = r.get('id_nguoidung'.toUpperCase()).toString().trim()
            }
            r.put("id_nguoidung", idNguoiDung)
            r.put("id_nguoidung".toUpperCase(), idNguoiDung)

            String nguoiDungId
            if (r.containsKey(NGUOIDUNG_ID)) {
                idNguoiDung = r.get(NGUOIDUNG_ID).toString().trim()
            } else {
                idNguoiDung = r.get(NGUOIDUNG_ID.toUpperCase()).toString().trim()
            }
            r.put(NGUOIDUNG_ID, idNguoiDung)
            r.put(NGUOIDUNG_ID.toUpperCase(), idNguoiDung)

            String idDonVi
            if (r.containsKey("id_donvi")) {
                idDonVi = r.get('id_donvi').toString().trim()
            } else {
                idDonVi = r.get('id_donvi'.toUpperCase()).toString().trim()
            }
            r.put("id_donvi", idDonVi)
            r.put("id_donvi".toUpperCase(), idDonVi)

            String maTinh
            if (r.containsKey("ma_tinh")) {
                maTinh = r.get('ma_tinh').toString().trim()
            } else {
                maTinh = r.get('ma_tinh'.toUpperCase()).toString().trim()
            }
            r.put("ma_tinh", maTinh)
            r.put("ma_tinh".toUpperCase(), maTinh)

            try {
                String secrect = request.getHeader("App-secret")
                String sessSecrect = new String(secrect.replace('-', '+').replace('_', '/').split("\\.")[0].decodeBase64())
                r2 = (Map<String, Object>) new JsonSlurper().parseText(sessSecrect)
                // Máy cập nhật
                String deviceId
                if (r2.containsKey("device_id")) {
                    deviceId = r2.get('device_id').toString().trim()
                } else {
                    deviceId = r2.get('device_id'.toUpperCase()).toString().trim()
                }
                r.put("device_id", deviceId)
                r.put("device_id".toUpperCase(), deviceId)

                r.put("device_id", "VNPT Employee")
                r.put("device_id".toUpperCase(), "VNPT Employee")

                // IP người cập nhật
                String deviceIp
                if (r2.containsKey("device_ip")) {
                    deviceIp = r2.get('device_ip').toString().trim()
                } else {
                    deviceIp = r2.get('device_ip'.toUpperCase()).toString().trim()
                }
                r.put("device_ip", deviceIp)
                r.put("device_ip".toUpperCase(), deviceIp)

                // Người cập nhật
                String deviceName
                if (r2.containsKey("device_name")) {
                    deviceName = r2.get('device_name').toString().trim()
                } else {
                    deviceName = r2.get('device_name'.toUpperCase()).toString().trim()
                }
                r.put("device_name", deviceName)
                r.put("device_name".toUpperCase(), deviceName)
            } catch (Exception ex) {
                ex.printStackTrace()
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        r
    }

    String generateRequestId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    String getFormattedCurrnetDate() {
        String date_format = "yyyyMMddHHmmss"
        synchronized (date_format) {
            DateFormat df = new SimpleDateFormat(date_format)
            return df.format(new Date(System.currentTimeMillis()))
        }
    }

    String getSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256")
            md.update(input.getBytes())

            byte[] byteData = md.digest()

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).
                        substring(1))
            }

            //convert the byte to hex format method 2
            StringBuffer hexString = new StringBuffer()
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0')
                }
                hexString.append(hex)
            }
            String output = hexString.toString();
            return output
        } catch (Exception ex) {
            return input
        }
    }
}
