package vn.vnptit.sapi.oneapp.model.request
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.Canonical
import groovy.transform.ToString

/**
 * UpdateStatusVNN Request, cần bổ sung thêm input, hiện tại chưa chuẩn input
 */
@Canonical
@ToString(includeFields = true, includeNames = true)
@JsonIgnoreProperties(value = ["metaClass"])
class UpdateStatusVNNRequest {
    Account_Model accountModel
    String note
    String reason

}

class Account_Model {
    String accType
    String accountName
    String accountPassword
    String status
}
