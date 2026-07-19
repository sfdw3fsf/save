package vn.vnptit.sapi.oneapp.model.input
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class DoKiemInput {
    int show_server_dk //_show_SERVER_DK
    int server_id //tscboSRV_DoKiem.ComboBox
    long vthuebao_id
    long vhdtb_id
    String vma_tb
    String vProfile
    String user_ont //txtUserOnt
    String pass_ont //txtPassONT
    boolean isGetMac //checkEdit1
    int vkieu
    long phanvung_id
    String ma_tinh
}
