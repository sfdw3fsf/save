
package vn.vnptit.sapi.oneapp.models.fmsalarms

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

class UPDATE_IMS_PASSWORD_DHSX_REQ {
    String ma_tinh
    String ma_tb
    String pass_ims
}

class POST_NET_DHSX_TO_TTS_REQ {
    String jsonData
}

class PRECHECK_ACCESS_HW_KPI_REQ {
    String device_ip
    String alarm_id
    Integer frame_id
    Integer slot_id
    Integer port_id
}

class PRECHECK_XLSC_KPI_REQ {
    String device_ip
    String alarm_code
    String target
}

class SYNC_VIA_GW_OSS_REQ {
    String loaiphieu_id
    String tts_parent_id
    String id_tts
    String noi_dung_xu_ly
    String ma_nhanvien_xuly
    String ma_donvi_xuly_hrm
    String sla_date
    String sla_process_time
    String sla
    String quahan_taibuoc
    String quahan_toantrinh
}

class FMSBB_CLOSE_TICKET_REQ {
    String alarm_id
    String clear_time
    String clear_username
    String clear_note
}
