package vn.vnptit.sapi.oneapp.models.bnm

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class XemTempInput {
    String idLenh
    String vtt
    String ip
    String telnetUser
    String tcon2_Profile
    int cardONU
    int queue0
    int queue1
    String telnetPass
    String enable_pass
    int frame
    int ponslotid
    int ponPort
    int onuid
    String ma_men
    String acc_kh
    String pass_kh
    String profileName
    String traffic_Prf_Up
    String traffic_Prf_Dw
    int vlan
    int vlan_vod
    String description
    int onuport_mytv
    int loai
    int brand
    String onu_type
    String mytvhd_up
    String mytvhd_dw
    String voip_tconProfile
    String gem_ims_profile_dwn
    String gem_ims_profile_up
    int voip_vlan
    String voip_userid
    String voip_username
    String voip_password
    int boSungMyTV
    int svlan
    int svlan_tr069
    String srvConProfile
    String outbound_ims_profile
    String inbound_ims_profile
    String ims_name
    String ims_servicename
    String ims_description
    String myTVDescription
    String ipBras
    String ipVPN
    String vlenh_bnm_id
}
