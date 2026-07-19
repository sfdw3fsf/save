package vn.vnptit.sapi.oneapp.util;

public class Constant {

    public class Smcs {
        public static final String API_KEY = "ed618a93c11c0cec404dedddfe8d0ac0b3a6ecf7f423a5cf745e192251dca5f4";
        public static final String STAFF_INFO = "ONEBSS_GET_STAFF_INFO";
        public static final String PROVINCE_LIST = "ONEBSS_SMSS_GETPROVINCE";
        public static final String DISTRICT_LIST = "ONEBSS_SMSS_GETSTATE";
        public static final String WARD_LIST = "ONEBSS_SMSS_GETCOUNTY";
        public static final String CHECK_HRM = "onebss_check_hrm_code";

        public class Sale {
            public static final String SEARCH_SERIAL = "ONEBSS_SMSS_SEARCHSERIAL";
            public static final String SALE = "ONEBSS_SMSS_CREATESALE_CK";
            public static final String SALE_PROMOTION = "ONEBSS_SMSS_CREATESALE_KM";
        }

        public class MobileMoney {
            public static final String AGENT_LIST = "ONEBSS_MM_LIST_AGENT";
            public static final String MOBILE_MONEY_LIST = "ONEBSS_MM_GET_LIST_AGENT";
            public static final String CHANNEL_TYPE_LIST = "ONEBSS_MM_GET_TYPE_LIST";
            public static final String CHANNEL_LIST = "ONEBSS_MM_GET_CHANNEL_AGENT";
            public static final String CHECK_PERMISSION = "ONEBSS_MM_CHECK_PERMISSION";
            public static final String EDIT = "ONEBSS_MM_EDIT_AGENT";
            public static final String CANCEL = "ONEBSS_MM_CANCEL_AGENT";
            public static final String REGISTER = "ONEBSS_MM_CREATE_AGENT";
            public static final String MEDIA = "ONEBSS_MM_OPEN_WEB_MEDIA";
        }

        public class Order {
            public static final String LIST = "ONEBSS_STAFF_ORDER_LIST";
            public static final String DETAIL = "ONEBSS_AGENT_ORDER_DETAIL";
            public static final String APPROVE = "ONEBSS_AGENT_ORDER_UPDATE";
        }

        public class Chanel {
            public static final String LIST = "ONEBSS_SMSS_LISTAGENTCO";
            public static final String LIST_FULL = "ONEBSS_SMSS_LISTAGENTFULL";
            public static final String CARE = "ONEBSS_SMSS_AGENTCARE";
            public static final String CARE_USSD = "ONEBSS_SMSS_AGENTCARE_USSD";
            public static final String DETAIL = "ONEBSS_SMSS_GETAGENTDETAIL";
            public static final String ADD = "ONEBSS_SMSS_CREATEAGENT";
            public static final String EDIT = "ONEBSS_SMSS_MODIFYAGENT";
        }

        public class Mnp {
            public static final String WARD_LIST = "ONEBSS_MNP_RESELLER_AREAS";
            public static final String STATUS_LIST = "ONEBSS_TTTT_DIRECTORY";
            public static final String MNP_LIST = "ONEBSS_MNP_PORT_OUT_LIST";
            public static final String REASON_LIST = "ONEBSS_TTTT_DIRECTORY";
            public static final String PKG_VOICE_LIST = "ONEBSS_TTTT_DIRECTORY";
            public static final String APPROVE = "ONEBSS_MNP_PO_UPDATE";
            public static final String BACK = "ONEBSS_MNP_PO_UPDATE2";
            public static final String PKG_LIST = "ONEBSS_MNP_PKG_OUT";
            public static final String OTP = "ONEBSS_MNP_OTP";
            public static final String REG_PACKAGE = "ONEBSS_OB_REG_PACKAGE";
            public static final String DISCOUNT = "ONEBSS_OB_DISCOUNT_MNP";
            public static final String ADD_CASH_BACK = "ONEBSS_OB_ADD_CASHBACK";
            public static final String ADD_COMMIT = "ONEBSS_OB_ADD_MNP_COMMIT";
            public static final String DK_VINA_690 = "ONEBSS_OB_VINA690";
            public static final String QR_COMMIT = "ONEBSS_OB_QR_COMMIT_PO";
            public static final String CHECK_VINA_690 = "ONEBSS_OB_CHECK_APRU";
            public static final String QR_SERVICE = "ONEBSS_OB_QR_SERVICE_PO";
            public static final String ONEBSS_MNP_PORT_IN_REQUEST = "ONEBSS_MNP_PORT_IN_REQUEST";
            public static final String ONEBSS_MNP_GET_PI_BY_ID = "ONEBSS_MNP_GET_PI_BY_ID";
            public static final String ONEBSS_MNP_CHECK_GOICUOC = "ONEBSS_MNP_CHECK_GOICUOC";
            public static final String ONEBSS_MNP_CHECK_YCCM = "ONEBSS_MNP_CHECK_YCCM";
            public static final String ONEBSS_MNP_THU_PHI = "ONEBSS_MNP_THU_PHI";
            public static final String ONEBSS_MNP_PI_UPDATE = "ONEBSS_MNP_PI_UPDATE";
            public static final String ONEBSS_MNP_SEND_PIN_REQUEST = "ONEBSS_MNP_SEND_PIN_REQUEST";
            public static final String ONEBSS_MNP_PKG_IN = "ONEBSS_MNP_PKG_IN";
            public static final String ONEBSS_MNP_PKG_IN_PRE = "ONEBSS_MNP_PKG_IN_PRE";
        }

        public class DoiTraHang {
            public static final String ONEBSS_CANCEL_CARD_SERIAL = "ONEBSS_CANCEL_CARD_SERIAL";
            public static final String ONEBSS_GET_APP_DOMAIN_LIST = "ONEBSS_GET_APP_DOMAIN_LIST";
            public static final String ONEBSS_GET_APP_DOMAIN_GROUP_TYPE = "ONEBSS_GET_APP_DOMAIN_GROUP_TYPE";
            public static final String ONEBSS_GET_REASON_LIST = "ONEBSS_GET_REASON_LIST";
            public static final String ONEBSS_GET_STOCK_LIST = "ONEBSS_GET_STOCK_LIST";
            public static final String ONEBSS_GET_STOCK_GOODS = "ONEBSS_GET_STOCK_GOODS";
            public static final String ONEBSS_GET_LIST_PRODUCT_CHANGE = "ONEBSS_GET_LIST_PRODUCT_CHANGE";
            public static final String ONEBSS_GET_PRODUCT_CHANGE_DETAIL = "ONEBSS_GET_PRODUCT_CHANGE_DETAIL";
            public static final String ONEBSS_PRODUCT_SAVE_CHANGE = "ONEBSS_PRODUCT_SAVE_CHANGE";
            public static final String ONEBSS_PRODUCT_CHANGE_SUCCESS = "ONEBSS_PRODUCT_CHANGE_SUCCESS";
            public static final String ONEBSS_GET_BBBG_DOIHONG = "ONEBSS_GET_BBBG_DOIHONG";
            public static final String ONEBSS_GET_PHIEUXK_DOIHONG = "ONEBSS_GET_PHIEUXK_DOIHONG";
        }
    }

    public class Khdn {
        public static final String query_mst = "query_mst";
    }

    public class VTC {
        public static final String getShippingData = "/api/subscription/getShippingData";
        public static final String resend = "/api/subscription/resend";
        public static final String activate = "/api/subscription/activate";

        public class vinaphone {
            public static final String kichoat_dichvu = "/api/v2/vinaphone/subscription/activate";
            public static final String thongtin_sp = "/api/v2/vinaphone/subscription/getShippingData";
            public static final String xacnhan_thongtin = "/api/v2/vinaphone/verifyInfo";
            public static final String ngungmua_baohiem = "/api/v2/vinaphone/suspend";
            public static final String phi_baohiem_nha = "/api/v2/vinaphone/getPrice/hci";
            public static final String ketqua_dangky_bh = "/api/v2/vinaphone/transaction";
        }

        public class vnptfiber {
            public static final String kichoat_dichvu = "/api/v1.2.3/vnptfiber/activate";
            public static final String thongtin_sp = "/api/v1.2.3/vnptfiber/getShippingData";
            public static final String kiemtra_key = "/api/v1.2.3/vnptfiber/verifyKey";
            public static final String guilai_sms_email = "/api/v1.2.3/vnptfiber/resend";
            public static final String giahan_dichvu = "/api/v1.2.3/vnptfiber/reactivate";
            public static final String ngung_dichvu = "/api/v1.2.3/vnptfiber/suspend";
            public static final String doi_goicuoc = "/api/v1.2.3/vnptfiber/switch";
        }
    }

    public static class Pattern {
        public static final String EMAIL = "^[\\w-_\\__\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        public static final String PHONE = "[0]\\d{9}";
    }

    public static final String BASE_IMAGE_CAB = "https://vnptcab.vn:8888/wsHandler.ashx?action=lay_anh&tinh=&ten_file=";
    public static final String MINIO_STORAGE_KEY = "dhsxkd";

    public static final String SERVICE = "tichhop";
    public final static String VNPT_PAY_CACHE_NAME = "VNPTPAY";
    public final static String HRM_CACHE_NAME = "HRM";
    public static final String FMIS_CACHE_NAME = "FMIS";
    public static final String FMIS_CACHE_NAME_V2 = "FMIS_V2";
}


