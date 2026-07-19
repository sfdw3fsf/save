package vn.vnptit.sapi.oneapp.model.input

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class lay_chitiet_tb_didong_smp_req {
    Integer vdb_id
    Integer vkieu
}
