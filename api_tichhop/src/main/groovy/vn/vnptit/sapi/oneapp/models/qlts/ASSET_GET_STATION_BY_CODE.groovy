package vn.vnptit.sapi.oneapp.models.qlts

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.ToString

import javax.validation.constraints.NotNull
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@JsonIgnoreProperties(["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class ASSET_GET_STATION_BY_CODE {
    @NotNull
    @JsonProperty("ma_csht")
    public String ma_csht
    @JsonProperty("ma_tinh")
    public String ma_tinh
    @JsonProperty("rims_status")
    public String rims_status
    @JsonProperty("name")
    public String name
    @JsonProperty("page_index")
    public Long page_index
    @JsonProperty("page_size")
    public Long page_size
}

@JsonIgnoreProperties(["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_GET_DATA_CONFIG_REQ {
    @NotNull
    @JsonProperty("token")
    public String token
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_GET_ASSET_BY_CODE_SCAN_REQ {
    @NotNull
    String token
    String type
    String value
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_GET_ASSET_BY_DATA_INPUT_REQ {
    @NotNull
    String token
    String so_the
    String asset_code_number
    String serial
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_GET_INVENTORY_ROUND_REQ {
    @NotNull
    String token
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_GET_INVENTORY_FIXED_REQ {
    @NotNull
    String token
    String inventory_id
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_GET_INVENTORY_SCAN_REQ {
    @NotNull
    String token
    String inventory_id
    String type_code
    String value
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_GET_INVENTORY_SEARCH_REQ {
    @NotNull
    String token
    String inventory_id
    String so_the
    String asset_code_number
    String serial
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_GET_INVENTORY_UPDATE_ONLINE_REQ {
    @NotNull
    String token
    String inventory_id
    String asset_id
    String status_code
    String data_update
    String scene_photo
    String description
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_GET_INVENTORY_OFFLINE_REQ {
    @NotNull
    String token
    String inventory_id
    String fixed_id
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_SEARCH_ASSET_BY_CSHT_REQ {
    @NotNull
    String token
    String MaCSHT
    String PartNumber
    String TenVietTat
    String MaDonViQuanLy
    String MaDonViSuDung
    String page_index
    String page_size
    String show_total
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_UPDATE_ASSET_BROKEN_BY_ID_REQ {
    @NotNull
    String token
    String IDChiTiet
    String SoThe
    String MaDonViQuanLy
    String MaTinhTrang
    String NoiDung
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_UPDATE_ASSET_SERIAL_BY_ID_REQ {
    @NotNull
    String token
    String IDChiTiet
    String SoThe
    String MaDonViQuanLy
    String Serial
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_SEARCH_COMPANY_BY_KEY_REQ {
    @NotNull
    String token
    String TuKhoa
    String MaDonVi
    String page_index
    String page_size
    String show_total
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_SEARCH_CSHT_BY_KEY_REQ {
    @NotNull
    String token
    String TuKhoa
    String page_index
    String page_size
    String show_total
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class QLTS_ONE_CLICK_REQ {
    @NotNull
    String email_user
    String may_cn
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class GET_LIST_IDENTITY_BY_TYPE {
    String company_code
    String nghiepvu
    String type_in_out
}

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
class GET_ASSET_WAREHOUSE_INFO {
    String identity_name
    String nghiepvu
    String company_code
    String type_in_out
}