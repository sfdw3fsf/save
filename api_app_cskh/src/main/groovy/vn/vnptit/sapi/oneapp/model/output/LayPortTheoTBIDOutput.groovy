package vn.vnptit.sapi.oneapp.model.output
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class LayPortTheoTBIDOutput {
    String  tt
    String  ma_tb
    String  ten_tb
    String  hdtb_id
    String  ma_lt
    String  tendslam
    String  thuebao_id
    String  loaitbi_id
    String  port
    String  ip
    String  port_adsl
    String  port_visa
    String  port_visa_xtest
    String  onu_id
    String  account
    String  loai_dslam_id
    String  port_id
    String  ten_kieuld
    String  rack
    String  shelf
    String  slot
    String  sport
    String  vpi
    String  vci
    String  system
    String  frame
    String  card
    String  portvl
    String  thamso
    String  portinfo
    String  port_9ts
    String loai_gpon
    String port_bnm
    String ip_bras
    String vslid
    String doitac_id
}
