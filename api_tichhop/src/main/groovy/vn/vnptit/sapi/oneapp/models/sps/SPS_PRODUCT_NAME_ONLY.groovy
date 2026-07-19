package vn.vnptit.sapi.oneapp.models.sps

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.Max
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@JsonIgnoreProperties(["metaClass"])
class SPS_PRODUCT_NAME_ONLY {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @JsonProperty("operation")
    public String operation
    @JsonProperty("order_no")
    public String order_no
    @JsonProperty("item_type")
    public String item_type
    @JsonProperty("subtype")
    public String subtype
}

@JsonIgnoreProperties(["metaClass"])
class SPS_PRODUCT_SERVICE_ACTION {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @JsonProperty("service_action")
    public String service_action
    @JsonProperty("operation")
    public String operation
    @JsonProperty("order_no")
    public String order_no
    @JsonProperty("item_type")
    public String item_type
    @JsonProperty("ism_res_3")
    public String ism_res_3
    @JsonProperty("abbr_number")
    public String abbr_number
    @JsonProperty("stored_number")
    public String stored_number
    @JsonProperty("cfu_number")
    public String cfu_number
    @JsonProperty("cfb_number")
    public String cfb_number
    @JsonProperty("cfnry_number")
    public String cfnry_number
    @JsonProperty("noreply_timer")
    @Max(value = 60L, message = "noreply_timer phải nhỏ hơn 60")
    public Number noreply_timer
    @JsonProperty("number_1x00")
    public String number_1x00
    @JsonProperty("scf_address")
    public String scf_address
    @JsonProperty("orig_service_key")
    public String orig_service_key
    @JsonProperty("term_service_key")
    public String term_service_key
    @JsonProperty("call_handling")
    public String call_handling
    @JsonProperty("cfc10_number")
    public String cfc10_number
    @JsonProperty("block_list")
    public String block_list
    @JsonProperty("snd1")
    public String snd1
    @JsonProperty("groupname1")
    public String groupname1
    @JsonProperty("snd2")
    public String snd2
    @JsonProperty("groupname2")
    public String groupname2
    @JsonProperty("seq_ring_list")
    public String seq_ring_list
    @JsonProperty("ring_period")
    public String ring_period
    @JsonProperty("sim_ring_list")
    public String sim_ring_list
    @JsonProperty("mtas_res_1")
    public String mtas_res_1
    @JsonProperty("mtas_res_5")
    public String mtas_res_5
    @JsonProperty("ism_res_1")
    public String ism_res_1
    @JsonProperty("profile_name")
    public String profile_name
    @JsonProperty("mtas_res_3")
    public String mtas_res_3
}

@JsonIgnoreProperties(["metaClass"])
class SPS_PRODUCT_SUBTYPE {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @JsonProperty("operation")
    public String operation
    @JsonProperty("order_no")
    public String order_no
    @JsonProperty("item_type")
    public String item_type
    @JsonProperty("sub_type")
    public String sub_type
    @JsonProperty("mtas_res_1")
    public String mtas_res_1
    @JsonProperty("mtas_res_2")
    public String mtas_res_2
    @JsonProperty("mtas_res_3")
    public String mtas_res_3
    @JsonProperty("mtas_res_4")
    public String mtas_res_4
    @JsonProperty("ism_res_1")
    public String ism_res_1
    @JsonProperty("ism_res_2")
    public String ism_res_2
    @JsonProperty("imsi")
    public String imsi
    @JsonProperty("charging_prof_id")
    public String charging_prof_id
}

@JsonIgnoreProperties(["metaClass"])
class SPS_PRODUCT_MTA {
    @JsonProperty("msisdn")
    public String msisdn
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @JsonProperty("operation")
    public String operation
    @JsonProperty("order_no")
    public String order_no
    @JsonProperty("item_type")
    public String item_type
    @JsonProperty("sub_type")
    public String sub_type
    @JsonProperty("mtas_res_1")
    public String mtas_res_1
    @JsonProperty("mtas_res_2")
    public String mtas_res_2
}

@JsonIgnoreProperties(["metaClass"])
class SPS_PRODUCT_ROUTING_TABLE {
    @JsonProperty("order_no")
    public String order_no
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @JsonProperty("service_action")
    public String service_action
    @JsonProperty("acc_name")
    public String acc_name
    @JsonProperty("acc_routing_table")
    public String acc_routing_table
    @JsonProperty("item_type")
    public String item_type
    @JsonProperty("operation")
    public String operation
}

@JsonIgnoreProperties(["metaClass"])
class SPS_PRODUCT_RULE_SETS {
    @JsonProperty("order_no")
    public String order_no
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @JsonProperty("service_action")
    public String service_action
    @JsonProperty("acc_name")
    public String acc_name
    @JsonProperty("acc_rulesets")
    public String acc_rulesets
    @JsonProperty("item_type")
    public String item_type
    @JsonProperty("operation")
    public String operation
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_PROFILE {
    @JsonProperty("order_no")
    public String order_no
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @JsonProperty("service_action")
    public String service_action
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("profile_name ")
    public String profile_name
    @NotNull
    @JsonProperty("profile_info ")
    public String profile_info
    @NotNull
    @JsonProperty("profile_sip_contact")
    public String profile_sip_contact
    @NotNull
    @JsonProperty("profile_endpoint")
    public String profile_endpoint
    @JsonProperty("profile_user_agent")
    public String profile_user_agent
    @JsonProperty("profile_q")
    public String profile_q
    @JsonProperty("profile_group")
    public String profile_group
    @JsonProperty("profile_auth")
    public String profile_auth
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_PROFILE_DELETE {
    @JsonProperty("order_no")
    public String order_no
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @JsonProperty("service_action")
    public String service_action
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("profile_name ")
    public String profile_name
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_ACCOUNT {
    @JsonProperty("order_no")
    public String order_no
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @JsonProperty("service_action")
    public String service_action
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
    @NotNull
    @JsonProperty("acc_info")
    public String acc_info
    @JsonProperty("acc_max_channels")
    public String acc_max_channels
    @JsonProperty("acc_routing_table")
    public String acc_routing_table
    @JsonProperty("acc_username")
    public String acc_username
    @JsonProperty("acc_password")
    public String acc_password
    @JsonProperty("acc_tenant")
    public String acc_tenant
    @JsonProperty("acc_rulesets")
    public String acc_rulesets
    @JsonProperty("acc_emergency_location")
    public String acc_emergency_location
    @JsonProperty("acc_use_media_server")
    public String acc_use_media_server
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_ACCOUNT_DELETE {
    @JsonProperty("order_no")
    public String order_no
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @JsonProperty("service_action")
    public String service_action
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_PROFILE_ACCOUNT {
    @JsonProperty("order_no")
    public String order_no
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @JsonProperty("service_action")
    public String service_action
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
    @NotNull
    @JsonProperty("profile_name ")
    public String profile_name
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_ADDRESS {
    @NotNull
    @JsonProperty("order_no")
    public String order_no
    @NotNull
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @NotNull
    @JsonProperty("service_action")
    public String service_action
    @NotNull
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("addr_domain")
    public String addr_domain
    @NotNull
    @JsonProperty("addr_number")
    public String addr_number
    @JsonProperty("addr_language")
    public String addr_language
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_ADDRESS_DELETE {
    @NotNull
    @JsonProperty("order_no")
    public String order_no
    @NotNull
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @NotNull
    @JsonProperty("service_action")
    public String service_action
    @NotNull
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("addr_number")
    public String addr_number
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_TAMNGUNG {
    @NotNull
    @JsonProperty("order_no")
    public String order_no
    @NotNull
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @NotNull
    @JsonProperty("service_action")
    public String service_action
    @NotNull
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
    @NotNull
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}\$", message = "Chưa đúng định dạng ngày dd/mm/yyyy hh:mm:ss")
    @JsonProperty("acc_valid_after")
    public String acc_valid_after
    @NotNull
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}\$", message = "Chưa đúng định dạng ngày dd/mm/yyyy hh:mm:ss")
    @JsonProperty("acc_valid_until")
    public String acc_valid_until
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_KHOA_GOIDI_QUOCTE {
    @NotNull
    @JsonProperty("order_no")
    public String order_no
    @NotNull
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @NotNull
    @JsonProperty("service_action")
    public String service_action
    @NotNull
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
    @NotNull
    @JsonProperty("acc_routing_table")
    public String acc_routing_table
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_KHOA_GOI_ACC_RULESETS {
    @NotNull
    @JsonProperty("order_no")
    public String order_no
    @NotNull
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @NotNull
    @JsonProperty("service_action")
    public String service_action
    @NotNull
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
    @NotNull
    @JsonProperty("acc_rulesets")
    public String acc_rulesets
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_CHAN_GOIDEN_KHONGMONGMUON {
    @NotNull
    @JsonProperty("order_no")
    public String order_no
    @NotNull
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @NotNull
    @JsonProperty("service_action")
    public String service_action
    @NotNull
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
    @NotNull
    @JsonProperty("acc_block_list")
    public String acc_block_list
    @JsonProperty("acc_block_dest_pattern")
    public String acc_block_dest_pattern
    @JsonProperty("acc_block_time_pattern")
    public String acc_block_time_pattern
    @JsonProperty("acc_block_dest_replace")
    public String acc_block_dest_replace
    @JsonProperty("acc_block_priority")
    public String acc_block_priority
    @JsonProperty("acc_block_type")
    public String acc_block_type
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_MO_CHAN_GOIDEN_KHONGMONGMUON_THEO_TB {
    @NotNull
    @JsonProperty("order_no")
    public String order_no
    @NotNull
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @NotNull
    @JsonProperty("service_action")
    public String service_action
    @NotNull
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
    @NotNull
    @JsonProperty("acc_block_name")
    public String acc_block_name
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_THAYDOI_SOKENH_CUOCGOI_DONGTHOI {
    @NotNull
    @JsonProperty("order_no")
    public String order_no
    @NotNull
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @NotNull
    @JsonProperty("service_action")
    public String service_action
    @NotNull
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
    @NotNull
    @JsonProperty("acc_max_channels")
    public String acc_max_channels
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_DANGKY_HANMUCCUOC {
    @NotNull
    @JsonProperty("order_no")
    public String order_no
    @NotNull
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @NotNull
    @JsonProperty("service_action")
    public String service_action
    @NotNull
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
    @JsonProperty("acc_topstop_type")
    public String acc_topstop_type
    @NotNull
    @JsonProperty("acc_topstop_max")
    public String acc_topstop_max
    @NotNull
    @JsonProperty("acc_topstop_alarm_level")
    public String acc_topstop_alarm_level
    @NotNull
    @JsonProperty("acc_topstop_alarm_email")
    public String acc_topstop_alarm_email
    @JsonProperty("acc_topstop_monthly_reset")
    public String acc_topstop_monthly_reset
}

@JsonIgnoreProperties(["metaClass"])
class SPS_AARANET_DIEUCHINH_HANMUCCUOC {
    @NotNull
    @JsonProperty("order_no")
    public String order_no
    @NotNull
    @JsonProperty("ll_product_name")
    public String ll_product_name
    @NotNull
    @JsonProperty("service_action")
    public String service_action
    @NotNull
    @JsonProperty("operation")
    public String operation
    @NotNull
    @JsonProperty("acc_name")
    public String acc_name
    @JsonProperty("acc_topstop_type")
    public String acc_topstop_type
    @NotNull
    @JsonProperty("acc_topstop_max")
    public String acc_topstop_max
}