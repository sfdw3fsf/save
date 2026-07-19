package com.vn.vnptit.Oneapp_Com.common;

public class ConstantString {

    private ConstantString() {
    }
    public static final String PODNAME = "PodName";
    
    public static final long TIMEOUT_NONBLOCK = 300000l;

    public static final int ZERO = 0;

    public static final int MAX_SIZE = 100;

    public static final String AUTHORIZATION = "Authorization";

    public static final String AUTHORIZATION_LOWER = "authorization";

    public static final String BEARER = "Bearer";

    public static final String EMAIL_CATEGORY_REGISTER = "1";

    public static final String P_IDENTIFY_CODE = "p_identify_code";

    public static final String OUTPUT = "output";

    public static final String INPUT = "input";

    public static final String REQUEST_ID = "request_id";

    public static final String ACTION = "action";
    public static final String THONGTIN_CONNECT = "thongtin_connect";

    public static final String LOG_RESPONE = "response";

    public static final String APPSECRET = "App-secret";

    public static final String HDDTFAIL = "Không xác định được lỗi hóa đơn điện tử";

    public static final String HDDTRESPONSE = "response";

    public static final String KEY_COMMON = "commonredis";

    public static final String KEY_AMOUNT = "<Amount>";
    public static final String KEY_CLOSEAMOUNT = "</Amount>";
    public static final String USERNAME = "username";
    public static final String IDENTIFYCODE = "identifycode";
    public static final String USERNAME_STRING = "username";
    public static final String PASS_STRING = "password";

    public static final String OUTPUTHELPER = "Output:";
    public static final String INPUTHELPER = "Input:";
    public static final String VARCHAR2 = "Varchar2";
    public static final String NUMBER = "Number";
    public static final String PHUONG_ID = "p_phuong_id";
    public static final String PHO_ID = "p_pho_id";
    public static final String AP_ID = "p_ap_id";
    public static final String KHU_ID = "p_khu_id";
    public static final String ONEAPP = "oneapp";
    public static final String SERVICE = "service";
    public static final String XMLDATA = "xmlData";
    public static final String URL = "url";
    public static final String HOADON_DIENTU = "hoadon_dientu";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "text/xml; charset=UTF-8";
    public static final String SOAPACTION = "SOAPAction";
    public static final String SOAPBODY_START = "<soap:Body>";
    public static final String SOAPBODY_END = "</soap:Body>";
    public static final String INVTOKEN_START = "<invToken>";
    public static final String INVTOKEN_END = "</invToken>";
    public static final String SOAPENVELOPE_START = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
    public static final String SOAPENVELOPE_END = "</soap:Envelope>";
    public static final String USERNAME_START = "<userName>";
    public static final String USERNAME_END = "</userName>";
    public static final String USERPASS_START = "<userPass>";
    public static final String USERPASS_END = "</userPass>";
    public static final String ACCOUNT_START = "<Account>";
    public static final String ACCOUNT_END = "</Account>";
    public static final String ACPASS_START = "<ACpass>";
    public static final String ACPASS_END = "</ACpass>";
    public static final String XMLINVDATA_START = "<xmlInvData>";
    public static final String XMLINVDATA_END = "</xmlInvData>";
    public static final String PASS_START = "<pass>";
    public static final String PASS_END = "</pass>";
    public static final String FKEY_SPACE_START = " <fkey>";
    public static final String FKEY_START = "<fkey>";
    public static final String FKEY_END = "</fkey>";
    public static final String CONVERT_START = "<convert>";
    public static final String CONVERT_END = "</convert>";
    public static final String PATTERN_START = "<pattern>";
    public static final String PATTERN_END = "</pattern>";
    public static final String SERIAL_START = "<serial>";
    public static final String SERIAL_END = "</serial>";
    public static final String LSTFKEY_START = "<lstFkey>";
    public static final String LSTFKEY_END = "</lstFkey>";
    public static final String TOKEN_END = "</token>";
    public static final String TODATE_START = "<toDate>";
    public static final String TODATE_END = "</toDate>";
    public static final String FROMDATE_START = "<fromDate>";
    public static final String FROMDATE_END = "</fromDate>";
    public static final String CUSCODE_END = "</cusCode>";
    public static final String XMLNS = "-xmlns";
    public static final String ERR1 = "ERR:1";
    public static final String ERR2 = "ERR:2";
    public static final String ERR3 = "ERR:3";
    public static final String ERR5 = "ERR:5";
    public static final String ERR6 = "ERR:6";
    public static final String ERR7 = "ERR:7";
    public static final String ERR8 = "ERR:8";
    public static final String ERR9 = "ERR:9";
    public static final String ERR20 = "ERR:20";
    public static final String MESSAGE_TK_DANGNHAP_SAI = "Tài khoản đăng nhập sai";
    public static final String MESSAGE_CTY_KHONG_TONTAI = "Công ty không tồn tại";
    public static final String MESSAGE_THANHCONG = "Thành công";
    public static final String MESSAGE_TK_KHONG_COQUYEN_THEM_KH = "Tài khoản đăng nhập sai hoặc không có quyền thêm khách hàng";
    public static final String MESSAGE_PATTERN_SERIAL_KHONG_PHUHOP = "Pattern và serial không phù hợp, hoặc không tồn tại hóa đơn đã đăng kí có sử dụng Pattern và serial truyền vào";
    public static final String MESSAGE_USERNAM_KHONG_PHUHOP = "User name không phù hợp, không tìm thấy company tương ứng cho user.";
    public static final String MESSAGE_XML_KHONG_DUNG_QUYDINH = "Dữ liệu xml đầu vào không đúng quy định";
    public static final String MESSAGE_KHONG_XACDINH_LOI_HDDT = "Không xác định được lỗi hóa đơn điện tử";
    public static final String MESSAGE_CHUOI_TOKEN_KHONG_CHINHXAC = "Chuỗi token không chính xác";
    public static final String MESSAGE_KHONG_TIMTHAY_HOADON = "Không tìm thấy hóa đơn tương ứng chuỗi đưa vào";
    public static final String MESSAGE_KHONG_BO_GACHNO = "Không bỏ gạch nợ được";
    public static final String MESSAGE_KHONG_THUOC_PHANQUYEN_KYHIEU = "Không thuộc phân quyền ký hiệu của user";
    public static final String MESSAGE_FKEY_CHUA_HD_CHUA_THANHTOAN = "Chuỗi fkey đúng nhưng chứa hóa đơn chưa thanh toán";
    public static final String MESSAGE_FKEY_KHONG_CHINHXAC = "Chuỗi fkey không chính xác, hóa đơn không tồn tại";
    public static final String MESSAGE_CTY_CHUA_DK_MAU_HD = "Công ty chưa được đăng kí mẫu hóa đơn nào";
    public static final String MESSAGE_TT_HD_KHONG_THAYTHE = "Trạng thái hóa đơn ko được thay thế.";
    public static final String MESSAGE_HD_DA_DUOC_THAYTHE = "Hóa đơn đã được thay thế rồi. Không thể thay thế nữa.";
    public static final String MESSAGE_DAI_HD_CU_HET = "Dải hóa đơn cũ đã hết";
    public static final String MESSAGE_THAYTHE_HD_LOI = "Có lỗi trong quá trình thay thế hóa đơn; Lỗi phát hành hóa đơn";
    public static final String MESSAGE_KHONG_TONTAI_HD_THAYTHE = "Không tồn tại hóa đơn cần thay thế";

    public static final String LOG_MESSAGE = "message";
    public static final String LOG_TIMESTAMP = "timestamp";
    public static final String LOG_EX_MESSAGE = "ex_message";
    public static final String LOG_LEVEL = "level";
    public static final String LOG_MANAGED = "Logmanaged";
    public static final String LOG_TIMESTAMP1 = "@timestamp";

    public static final String KHONG_TRAM = " không trăm";
    public static final String PRODUCT = "Product";
    public static final String LTSUAT = "LTSuat";
    public static final String HHDVU = "HHDVu";


}
